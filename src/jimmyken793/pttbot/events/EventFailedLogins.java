package jimmyken793.pttbot.events;

import jimmyken793.pttbot.TextArray;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.terminal.Terminal;

public class EventFailedLogins extends EventHandler {

	static String[] lines = new String[4];

	public EventFailedLogins(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		super(t, terminal, sresource, sconfig);
		lines[0] = "通常並沒有辦法知道該ip是誰所有, 以及其意圖(是不小心按錯或有意測您密碼)";
		lines[1] = "若您有帳號被盜用疑慮, 請經常更改您的密碼或使用加密連線";
		lines[2] = "";
		lines[3] = "您要刪除以上錯誤嘗試的記錄嗎? [y/N]    ";
	}
	private int length=4;


	public boolean check(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		int max = t.getMrow();
		for (int i = max; i >max-length; i--) {
			if (!(lines[length-1-max+i]==null||t.getLine(i).regionMatches(false,0,lines[length-1-max+i],0,lines[length-1-max+i].length()))) {
				return false;
			}
		}
		return true;
	}

	public void perform(TextArray t, Terminal terminal, ResourceMap sresource, ResourceMap sconfig) {
		int max = t.getMrow();
		for (int i = 1; i < max - 3; i++) {
			System.out.println(t.getLine(i));
		}
		System.out.println("EventFailedLogins");
		terminal.pasteText("\n");
	}

}
