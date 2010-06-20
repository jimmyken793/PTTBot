package idv.jimmyken793.pttbot.controller;

public class ControllerThread extends Thread {
	HumanControl controller;
	public ControllerThread(HumanControl controller){
		super(controller);
		this.controller=controller;
	}
	public HumanControl getController() {
		return this.controller;
	}
}
