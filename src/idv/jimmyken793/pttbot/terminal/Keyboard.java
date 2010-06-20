package idv.jimmyken793.pttbot.terminal;


public class Keyboard {
	public static final char[] K_SEQ_UP={0x1b,0x4f,'A'};
	public static final char[] K_SEQ_DOWN={0x1b,0x4f,'B'};
	public static final char[] K_SEQ_RIGHT={0x1b,0x4f,'C'};
	public static final char[] K_SEQ_LEFT={0x1b,0x4f,'D'};
	public static final char[] K_SEQ_INSERT={0x1b,0x5b2,'~'};
	public static final char[] K_SEQ_HOME={0x1b,0x4f};
	public static final char[] K_SEQ_PAGE_UP={0x1b,0x5b5,'~'};
	public static final char[] K_SEQ_DELETE={0x1b,0x5b3,'~'};
	public static final char[] K_SEQ_END={0x1b,0x4fF};
	public static final char[] K_SEQ_PAGE_DOWN={0x1b,0x5b,'6','~'};
	public static final char[] K_SEQ_ESCAPE={0x1b};
	public static final char[] K_SEQ_ENTER={0x0d};
	public static final String K_UP=new String(K_SEQ_UP);
	public static final String K_DOWN=new String(K_SEQ_DOWN);
	public static final String K_RIGHT=new String(K_SEQ_RIGHT);
	public static final String K_LEFT=new String(K_SEQ_LEFT);
	public static final String K_INSERT=new String(K_SEQ_INSERT);
	public static final String K_HOME=new String(K_SEQ_HOME);
	public static final String K_PAGE_UP=new String(K_SEQ_PAGE_UP);
	public static final String K_DELETE=new String(K_SEQ_DELETE);
	public static final String K_PAGE_DOWN=new String(K_SEQ_PAGE_DOWN);
	public static final String K_ESCAPE=new String(K_SEQ_ESCAPE);
	public static final String K_ENTER=new String(K_SEQ_ENTER);
}
