package fr.bastion.rest;

public class Program {

	public static void main(String[] args) {
		RestServiceManagement getEncounter = new RestServiceManagement();
		getEncounter.setParameter("src/main/resources/messages/location.xml");
		getEncounter.messaging();
		
		RestServiceManagement setLocation = new RestServiceManagement();
		setLocation.setParameter("src/main/resources/messages/encounters.xml");
		setLocation.messaging();
	}

}
