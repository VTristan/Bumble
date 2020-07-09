package fr.bastion.rest.programs;

import fr.bastion.rest.RestServiceManagement;
import fr.bastion.rest.specificMessages.GetCity;
import fr.bastion.rest.specificMessages.UpdateLocation;

public class ProgramUpdateLocation {

	public static void main(String[] args) {
		RestServiceManagement rest = new RestServiceManagement();
		rest.setParameter(new UpdateLocation());
		rest.messaging();
		
		rest.setParameter(new GetCity());
		rest.messaging();
		
	}

}
