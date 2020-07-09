package fr.bastion.utiles;

import java.util.Timer;

public class TaskManager {
	
	public Timer timer = new Timer();
	
	public void startBumbleTask() {
		timer.schedule(new BumbleTask(timer, (byte) 1), 5000, 10000);
	}	
	
//	public void startTinderTask() {
//		timer.schedule(new TinderTask(timer, (byte) 1), 5000, 10000);
//		//TODO:
//		//timer.cancel();
//		//timer.purge();
//	}

}