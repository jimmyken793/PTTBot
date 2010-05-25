package jimmyken793.pttbot.terminal;

import java.awt.event.KeyEvent;

import org.zhouer.vt.VT100;

public class Keyboard {
	public static final char[] K_UP={0x1b,0x4f,'A'};
	public static final char[] K_DOWN={0x1b,0x4f,'B'};
	public static final char[] K_RIGHT={0x1b,0x4f,'C'};
	public static final char[] K_LEFT={0x1b,0x4f,'D'};
	public static final char[] K_INSERT={0x1b,0x5b2,'~'};
	public static final char[] K_HOME={0x1b,0x4f};
	public static final char[] K_PAGE_UP={0x1b,0x5b5,'~'};
	public static final char[] K_DELETE={0x1b,0x5b3,'~'};
	public static final char[] K_END={0x1b,0x4fF};
	public static final char[] K_PAGE_DOWN={0x1b,0x5b,'6','~'};
	public static final char[] K_ESCAPE={0x1b};
	public static final char[] K_ENTER={0x0d};
}
