package fr.bastion.rest.programs;

import fr.bastion.rest.RestServiceManagement;
import fr.bastion.rest.specificMessages.GetEncounters;
import fr.bastion.utiles.programs.ProgramJsonParser;

public class ProgramGetEncounter {

	public static void main(String[] args) {
		RestServiceManagement rest = new RestServiceManagement();
		rest.setParameter(new GetEncounters());
		rest.messaging();
		
		ProgramJsonParser Parser = new ProgramJsonParser();
		Parser.exeJsonParser();
		
		System.out.println("Fin du programme");
	}
}
