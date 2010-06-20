package idv.jimmyken793.pttbot.events;

import idv.jimmyken793.pttbot.controller.PTTBot;
import idv.jimmyken793.pttbot.resource.ResourceMap;

public class UserLogin extends EventHandler {
	public UserLogin(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		super(bot,sresource,sconfig);
	}

	static int call = 0;
	static final String login_prompt="請輸入代號，或以 guest 參觀，或以 new 註冊:               ";
	public boolean perform(PTTBot bot, ResourceMap sresource,ResourceMap sconfig) {
		if (UserLogin.call == 0) {
			if (sconfig.containsKey("Username")){
				terminal.pasteText(sconfig.get("Username")+"\n");
			}else{
				System.out.println("Username Not Found");
			}
			UserLogin.call++;
		}
		return false;
	}
	public boolean check(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		if(tarray.getLine(tarray.getCrow()).matches(login_prompt)){
			return true;
		}
		return false;
	}
}
