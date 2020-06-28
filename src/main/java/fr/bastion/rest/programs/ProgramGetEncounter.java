package fr.bastion.rest.programs;

import fr.bastion.rest.RestServiceManagement;
import fr.bastion.rest.specificMessages.GetEncounters;

public class ProgramGetEncounter {

	public static void main(String[] args) {
		RestServiceManagement rest = new RestServiceManagement();
		rest.setParameter(new GetEncounters());
		rest.messaging();
	}

}
