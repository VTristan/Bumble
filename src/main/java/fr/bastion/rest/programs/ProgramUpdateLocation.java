package fr.bastion.rest.programs;

import fr.bastion.rest.RestServiceManagement;
import fr.bastion.rest.specificMessages.GetCity;
import fr.bastion.rest.specificMessages.UpdateLocation;

public class ProgramUpdateLocation {

	public static void main(String[] args) {
		RestServiceManagement rest = new RestServiceManagement();
		rest.setParameter(new UpdateLocation(48.7054463, 2.54068));
		rest.messaging();
		
		rest.setParameter(new GetCity(48.7054463, 2.54068));
		rest.messaging();
		
		
	}

}
