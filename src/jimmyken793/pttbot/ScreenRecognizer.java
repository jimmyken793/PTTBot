package jimmyken793.pttbot;

public class ScreenRecognizer {
	public static final String[] mainmenu_pattern={"【主功能表】 +批踢踢兔.+","\\[.+ 線上[0-9]+人, 我是[a-zA-z][a-zA-Z0-9 ]{1,11}.+"};;
	public static boolean is_mainMenu(TextArray tarray){
		if(!tarray.getLine(1).matches(mainmenu_pattern[0])){
			return false;
		}
		if(!tarray.getLine(tarray.getMrow()).matches(mainmenu_pattern[1])){
			return false;
		}
		return true;
	}
}
