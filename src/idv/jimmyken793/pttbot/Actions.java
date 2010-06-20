package idv.jimmyken793.pttbot;

import idv.jimmyken793.pttbot.terminal.Keyboard;
import idv.jimmyken793.pttbot.terminal.Terminal;

public class Actions {
	public static boolean moveCursor(Terminal terminal,TextArray tarray,String cursor,String target){
		int dest=0;
		int cur=tarray.getCrow();
		for(int i=13;i<=23;i++){
			String line=tarray.getLine(i);
			if(line.contains(target)){
				dest=i;
			}
		}
		if(dest!=0){
			if(dest<cur){
				terminal.pasteText(Keyboard.K_UP);
			}else if(dest>cur){
				terminal.pasteText(Keyboard.K_DOWN);
			}else{
				return true;
			}
		}
		return false;
	}
}
