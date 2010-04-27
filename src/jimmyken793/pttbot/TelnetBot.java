package jimmyken793.pttbot;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Date;

import javax.swing.Timer;

import jimmyken793.pttbot.controller.HumanControl;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.SiteConfig;
import jimmyken793.pttbot.resource.SiteHandlerBinding;
import jimmyken793.pttbot.resource.SiteResource;
import jimmyken793.pttbot.terminal.PTTTerminal;
import jimmyken793.pttbot.terminal.Terminal;

import org.zhouer.protocol.Protocol;
import org.zhouer.protocol.SSH2;
import org.zhouer.protocol.Telnet;
import org.zhouer.utils.Convertor;
import org.zhouer.utils.TextUtils;
import org.zhouer.vt.Application;
import org.zhouer.zterm.Messages;
import org.zhouer.zterm.Resource;
import org.zhouer.zterm.Site;

public class TelnetBot implements Runnable, HumanControl {
	private static final long serialVersionUID = 2180544188833033537L;

	private Resource resource;
	private Convertor conv;
	private Site site;

	private String socks_host;
	private int socks_port;

	private PTTTerminal vt;

	private String iconname;
	private String windowtitle;

	// 與遠端溝通用的物件
	private Protocol network;
	private InputStream is;
	private OutputStream os;
	private PrintStream err = System.out;

	// 自動重連用
	private long startTime;

	// 防閒置用
	private boolean antiidle;
	private Timer ti;
	private long lastInputTime, antiIdleInterval;

	// 連線狀態
	public int state;

	// 連線狀態常數
	public static final int STATE_TRYING = 1;
	public static final int STATE_CONNECTED = 2;
	public static final int STATE_CLOSED = 3;
	public static final int STATE_ALERT = 4;

	/*
	 * 送往上層的
	 */

	public void setState(int s) {

	}

	public boolean isTabForeground() {
		return true;
	}

	// TODO: 設定Bell Handler
	public void bell() {

	}

	// TODO: 設定Copy
	public void copy() {
	}

	// TODO: 設定colorCopy
	public void colorCopy() {
	}

	// TODO: 設定paste
	public void paste() {
	}

	// TODO: 設定colorPaste
	public void colorPaste() {
	}

	/*
	 * 送往下層的
	 */

	public void resetSelected() {
		vt.resetSelected();
	}

	public void pasteText(String str) {
		vt.pasteText(str);
	}

	public void pasteColorText(String str) {
		vt.pasteColorText(str);
	}

	public boolean requestFocusInWindow() {
		return vt.requestFocusInWindow();
	}

	/*
	 * 送到 network 的
	 */

	public int readBytes(byte[] buf) {
		try {
			return is.read(buf);
		} catch (IOException e) {
			// e.printStackTrace();
			// 可能是正常中斷，也可能是異常中斷，在下層沒有區分
			close(true);
			return -1;
		}
	}

	public void writeByte(byte b) {
		lastInputTime = new Date().getTime();

		try {
			os.write(b);
		} catch (IOException e) {
			// e.printStackTrace();
			err.println("Caught IOException in Session::writeByte(...)");
			close(true);
		}
	}

	public void writeBytes(byte[] buf, int offset, int len) {
		lastInputTime = new Date().getTime();
		try {
			os.write(buf, offset, len);
		} catch (IOException e) {
			// e.printStackTrace();
			err.println("Caught IOException in Session::writeBytes(...)");
			close(true);
		}
	}

	public void writeChar(char c) {
		byte[] buf;

		buf = conv.charToBytes(c, site.encoding);

		writeBytes(buf, 0, buf.length);
	}

	public void writeChars(char[] buf, int offset, int len) {
		int count = 0;
		// FIXME: magic number
		byte[] tmp = new byte[len * 4];
		byte[] tmp2;

		for (int i = 0; i < len; i++) {
			tmp2 = conv.charToBytes(buf[offset + i], site.encoding);
			for (int j = 0; j < tmp2.length; j++) {
				tmp[count++] = tmp2[j];
			}
		}

		writeBytes(tmp, 0, count);
	}

	public boolean isConnected() {
		// 如果 network 尚未建立則也當成尚未 connect.
		if (network == null) {
			return false;
		}
		return network.isConnected();
	}

	public boolean isClosed() {
		// 如果 network 尚未建立則也當成 closed.
		if (network == null) {
			return true;
		}
		return network.isClosed();
	}

	/*
	 * 自己的
	 */

	public Site getSite() {
		return site;
	}

	public void setEncoding(String enc) {
		site.encoding = enc;
		vt.setEncoding(site.encoding);
	}

	public void setEmulation(String emu) {
		site.emulation = emu;

		// 通知遠端 terminal type 已改變
		network.setTerminalType(emu);

		vt.setEmulation(emu);
	}

	public String getEmulation() {
		return site.emulation;
	}

	public String getURL() {
		return site.getURL();
	}

	public void setIconName(String in) {
		// TODO: 未完成
	}

	public void setWindowTitle(String wt) {
		// TODO: 未完成
	}

	public String getIconName() {
		return iconname;
	}

	public String getWindowTitle() {
		return windowtitle;
	}

	public void close(boolean fromRemote) {
		if (isClosed()) {
			return;
		}

		// 中斷連線
		network.disconnect();

		// 停止防閒置用的 timer
		if (ti != null) {
			ti.stop();
		}

		// 通知 vt 停止運作
		if (vt != null) {
			vt.close();
		}

		// 將連線狀態改為斷線
		setState(STATE_CLOSED);

		// 若遠端 server 主動斷線則判斷是否需要重連
		boolean autoreconnect = resource.getBooleanValue(Resource.AUTO_RECONNECT);
		if (autoreconnect && fromRemote) {
			long reopenTime = resource.getIntValue(Resource.AUTO_RECONNECT_TIME);
			long reopenInterval = resource.getIntValue(Resource.AUTO_RECONNECT_INTERVAL);
			long now = new Date().getTime();

			// 判斷連線時間距現在時間是否超過自動重連時間
			// 若設定自動重連時間為 0 則總是自動重連
			if ((now - startTime <= reopenTime * 1000) || reopenTime == 0) {
				try {
					Thread.sleep(reopenInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void updateAntiIdleTime() {
		// 更新是否需要啟動防閒置
		antiidle = resource.getBooleanValue(Resource.ANTI_IDLE);

		// 防閒置的作法是定時檢查距上次輸入是否超過 interval,
		// 所以這裡只要設定 antiIdleTime 就自動套用新的值了。
		antiIdleInterval = resource.getIntValue(Resource.ANTI_IDLE_INTERVAL) * 1000;
	}

	public void showMessage(String msg) {
		System.err.print(msg);
	}

	public void showPopup(int x, int y) {
	}

	public void openExternalBrowser(String url) {
	}

	class AntiIdleTask implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 如果超過 antiIdelInterval milliseconds 沒有送出東西，
			// lastInputTime 在 writeByte, writeBytes 會被更新。
			long now = new Date().getTime();
			if (antiidle && isConnected() && (now - lastInputTime > antiIdleInterval)) {
				// err.println( "Sent antiidle char" );
				// TODO: 設定 antiidle 送出的字元
				if (site.protocol.equalsIgnoreCase(Protocol.TELNET)) {

					String buf = TextUtils.BSStringToString(resource.getStringValue(Resource.ANTI_IDLE_STRING));
					char[] ca = buf.toCharArray();
					writeChars(ca, 0, ca.length);

					// 較正規的防閒置方式
					// writeByte( Telnet.IAC );
					// writeByte( Telnet.NOP );
				}
			}
		}
	}

	public void run() {
		// 設定連線狀態為 trying
		setState(STATE_TRYING);
		// 新建連線
		if (site.protocol.equalsIgnoreCase(Protocol.TELNET)) {
			if (resource.getBooleanValue(Resource.USING_SOCKS) && site.usesocks) {
				socks_host = resource.getStringValue(Resource.SOCKS_HOST);
				socks_port = resource.getIntValue(Resource.SOCKS_PORT);
				network = new Telnet(site.host, site.port, socks_host, socks_port);
			} else {
				network = new Telnet(site.host, site.port);
			}
			network.setTerminalType(site.emulation);
		} else if (site.protocol.equalsIgnoreCase(Protocol.SSH)) {
			network = new SSH2(site.host, site.port, site.username);
			network.setTerminalType(site.emulation);
		} else {
			System.err.println("Unknown protocol: " + site.protocol);
		}

		// 連線失敗
		if (network.connect() == false) {
			// 設定連線狀態為 closed
			setState(STATE_CLOSED);
			showMessage("連線失敗！");
			return;
		}
		is = network.getInputStream();
		os = network.getOutputStream();

		// TODO: 如果需要 input filter or trigger 可以在這邊套上

		// 設定連線狀態為 connected
		setState(STATE_CONNECTED);

		// 連線成功，更新或新增連線紀錄
		resource.addFavorite(site);

		// 防閒置用的 Timer
		updateAntiIdleTime();
		lastInputTime = new Date().getTime();
		ti = new Timer(1000, new AntiIdleTask());
		ti.start();

		// 記錄連線開始的時間
		startTime = new Date().getTime();

		vt.run();
	}

	public TelnetBot(String h) {
		String host;
		int port, pos;
		String prot;
		// 如果開新連線時按了取消則傳回值為 null
		if (h == null || h.length() == 0) {
			return;
		}

		do {
			pos = h.indexOf("://"); //$NON-NLS-1$
			// Default 就是 telnet
			prot = Protocol.TELNET;
			if (pos != -1) {
				if (h.substring(0, pos).equalsIgnoreCase(Protocol.SSH)) {
					prot = Protocol.SSH;
				} else if (h.substring(0, pos).equalsIgnoreCase(Protocol.TELNET)) {
					prot = Protocol.TELNET;
				} else {
					showMessage(Messages.getString("ZTerm.Message_Wrong_Protocal")); //$NON-NLS-1$
					return;
				}
				// 將 h 重設為 :// 後的東西
				h = h.substring(pos + 3);
			}

			// 取得 host:port, 或 host(:23)
			pos = h.indexOf(':');
			if (pos == -1) {
				host = h;
				if (prot.equalsIgnoreCase(Protocol.TELNET)) {
					port = 23;
				} else {
					port = 22;
				}
			} else {
				host = h.substring(0, pos);
				port = Integer.parseInt(h.substring(pos + 1));
			}
			site = new Site(host, host, port, prot);
		} while (false);
		err.print("init\n");
		// host 長度為零則不做事
		if (h.length() == 0) {
			return;
		}
		resource = new Resource();
		conv = new Convertor();
		initialize();
	}

	public TelnetBot(Site s, Resource r, Convertor c, BufferedImage bi) {
		site = s;
		resource = r;
		conv = c;
		initialize();
	}

	private void initialize() {

		// FIXME: 預設成 host
		windowtitle = site.host;
		iconname = site.host;
		// VT100
		vt = new PTTTerminal((Application) this, this, resource, conv);

		// FIXME: 是否應該在這邊設定？
		vt.setEncoding(site.encoding);
		vt.setEmulation(site.emulation);
	}

	public Dimension getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String args[]) {
		TelnetBot bot = new TelnetBot("ptt2.cc");
		bot.run();
	}

	public void scroll(int lines) {
		// TODO Auto-generated method stub

	}

	public void react() {
	}

}