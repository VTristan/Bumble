package fr.bastion.utiles;

import java.util.List;
import java.util.TimerTask;

import fr.bastion.rest.RestServiceManagement;
import fr.bastion.rest.specificMessages.UpdateLocation;

public class Task extends TimerTask {
	List<Double> ordLat;
	List<Double> absLong;
	int incrementLat=0;
	int incrementLong=0;
	RestServiceManagement rest;
	UpdateLocation location;
	
	
	public Task(double latitude, double longitude) {
		Quadrillage q = new Quadrillage(null);
		q.drawSquare(latitude, longitude, 10, 1);
		ordLat = q.getOrdo();
		absLong = q.getAbsc();
		
		rest = new RestServiceManagement();
		location = new UpdateLocation(latitude, longitude);
		
	}

	@Override
	public void run() {
		try {
			System.out.println(ordLat.get(incrementLat)+" "+absLong.get(incrementLong));
			incrementLat ++;

			if (incrementLat == ordLat.size()) {
				incrementLat = 0;
				incrementLong++;
			}
			if (incrementLong == absLong.size()) {
				throw new Exception();
			}
			
			location.setLongitude(absLong.get(incrementLat));
			location.setLatitude(ordLat.get(incrementLat));
			
			//rest.setParameter("src/main/resources/messages/location.xml");
			//rest.messaging(location.getBodyUpdateLocation());
			
			//rest.setParameter("src/main/resources/messages/city.xml");
			//rest.messaging(location.getBodyCity());
			
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
