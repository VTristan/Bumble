package fr.bastion.utiles;

import java.util.Timer;

public class TaskManager {
	
	private Timer timer = new Timer();
	
	public void startBumbleTask(Quadrillage location, byte nbBoucle, long delay, long period) {
		BumbleTask bumble = new BumbleTask(timer, (byte) nbBoucle, location);
		
		timer.schedule(bumble, delay, period);
	}	
	
//	public void startTinderTask() {
//		timer.schedule(new TinderTask(timer, (byte) 1), 5000, 10000);
//		//TODO:
//		//timer.cancel();
//		//timer.purge();
//	}

}