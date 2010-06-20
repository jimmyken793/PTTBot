package idv.jimmyken793.pttbot.events;

import idv.jimmyken793.pttbot.controller.PTTBot;
import idv.jimmyken793.pttbot.resource.ResourceMap;

public class UserPassword extends EventHandler {
	public UserPassword(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		super(bot,sresource,sconfig);
	}

	static int call = 0;
	static final String password_prompt="請輸入您的密碼: ";
	public boolean perform(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		if (UserPassword.call == 0) {
			if (sconfig.get("Password") != null){
				System.out.print("password\n");
				terminal.pasteText(sconfig.get("Password") + "\n");
			}else{
				System.err.print("Password Not Found");
			}
			UserPassword.call++;
		}
		return false;
	}

	public boolean check(PTTBot bot,ResourceMap sresource,ResourceMap sconfig) {
		if(tarray.getLine(tarray.getCrow()).matches(password_prompt)){
			return true;
		}
		return false;
	}
}
