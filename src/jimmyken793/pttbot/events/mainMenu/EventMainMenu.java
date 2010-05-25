package jimmyken793.pttbot.events.mainMenu;

import java.util.HashMap;

import jimmyken793.pttbot.controller.PTTBot;
import jimmyken793.pttbot.events.EventHandler;
import jimmyken793.pttbot.resource.ResourceMap;
import jimmyken793.pttbot.terminal.Keyboard;

public class EventMainMenu extends EventHandler {
	HashMap<String, Integer> menu=new HashMap<String, Integer>();
	public EventMainMenu(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		super(bot, sresource, sconfig);
		
	}

	@Override
	public boolean check(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		System.out.println("mainmenu");
		if(!tarray.getLine(1).contains("【主功能表】")){
			return false;
		}
		return true;
	}

	@Override
	public boolean perform(PTTBot bot, ResourceMap sresource, ResourceMap sconfig) {
		menu.clear();
		int dest=0;
		int cur=0;
		for(int i=13;i<=23;i++){
			String line=tarray.getLine(i);
			if(line.contains("●")){
				cur=i;
			}else if(line.contains("(F)avorite")){
				dest=i;
			}
		}
		System.out.println("cur:"+cur);
		System.out.println("dest:"+dest);
		if(dest!=0){
			if(dest<tarray.getCrow()){
				System.out.println("UP");
				terminal.pasteText(new String(Keyboard.K_UP));
			}else if(dest>tarray.getCrow()){
				System.out.println("DOWN");
				terminal.pasteText(new String(Keyboard.K_DOWN));
			}
		}
		return false;
	}

}
