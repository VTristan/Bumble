package fr.bastion.utiles;

import java.util.Timer;

public class TaskManager {
	
	private Timer timer = new Timer();
	
	public void startBumbleTask(Quadrillage location, byte nbBoucle, long delay, long period) {

		timer.schedule(new BumbleTask(timer, (byte) nbBoucle, location), delay, period);
	}	
	
//	public void startTinderTask() {
//		timer.schedule(new TinderTask(timer, (byte) 1), 5000, 10000);
//		//TODO:
//		//timer.cancel();
//		//timer.purge();
//	}

}