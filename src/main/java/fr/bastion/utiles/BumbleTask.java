package fr.bastion.utiles;

import java.util.Timer;
import java.util.TimerTask;
import fr.bastion.rest.specificMessages.GetEncounters;
import fr.bastion.rest.specificMessages.UpdateLocation;

public class BumbleTask extends TimerTask {
	
	private Quadrillage quadrillage;
	private GetEncounters encounters;
	private UpdateLocation location;
	private Timer timer;
	private byte currentloop;
	private byte maxloop;
	private boolean stopProgram = false;
	private String bodyLocation;
	private double[][] coordinatesList;
	private short incrementCoordinates;
	
	{
		//TODO: GetCity?
		encounters = new GetEncounters();
		location = new UpdateLocation();
		//city = new GetCity();
		coordinatesList = quadrillage.getCoordinates();
		currentloop = 0;
		incrementCoordinates = 0;
		bodyLocation = null;
	}	
	
	
	public BumbleTask(Timer timer, byte maxloop, Quadrillage quadrillage) {
		this.timer = timer;
		this.maxloop = maxloop;
		this.quadrillage = quadrillage;
	}


	@Override
	public void run() {
		bodyLocation = updateBodyLocation();
		
		location.messaging(bodyLocation);
		
		encounters.messaging();
		
		incrementation();
		
		isEndOfTask();
	}

	private String updateBodyLocation() {
		location.setLatitude(coordinatesList[0][incrementCoordinates]);
		location.setLongitude(coordinatesList[1][incrementCoordinates]);
		return location.getBody();
	}
	
	private void incrementation() {
			incrementCoordinates++;
		if (incrementCoordinates == coordinatesList[0].length) {
			incrementCoordinates = 0;
			//Increment loop.
			currentloop ++;
		}
	}
	
	private void isEndOfTask(){
		if (currentloop == maxloop) {
			this.cancel();
			timer.cancel();
			timer.purge();
		}
		
		if (stopProgram) {
			//Stop all the threads
			this.cancel();
			timer.cancel();
			timer.purge();
		}
	}

}
