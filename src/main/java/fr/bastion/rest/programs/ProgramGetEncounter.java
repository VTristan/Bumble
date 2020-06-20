package fr.bastion.rest.programs;

import fr.bastion.rest.RestServiceManagement;

public class ProgramGetEncounter {

	public static void main(String[] args) {
		RestServiceManagement rest = new RestServiceManagement();
		
		rest.setParameter("src/main/resources/messages/encounters.xml");
		rest.messaging();
	}

}
