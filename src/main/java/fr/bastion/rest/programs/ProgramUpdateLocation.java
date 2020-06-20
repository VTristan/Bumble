package fr.bastion.rest.programs;

import fr.bastion.rest.RestServiceManagement;

public class ProgramUpdateLocation {

	public static void main(String[] args) {
		RestServiceManagement rest = new RestServiceManagement();
		
		rest.setParameter("src/main/resources/messages/location.xml");
		rest.messaging();
		
		rest.setParameter("src/main/resources/messages/city.xml");
		rest.messaging();
	}

}
