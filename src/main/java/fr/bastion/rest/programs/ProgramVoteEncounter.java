package fr.bastion.rest.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.bastion.rest.RestServiceManagement;
import fr.bastion.rest.specificMessages.VoteEncounter;

public class ProgramVoteEncounter {

	public static void main(String[] args) {
		/* Imaginons que nous arrivions à trier depuis le fichier encouters.json 
		les profiles qui ont "has_user_voted=yes".
		Dès lors, nous créons une liste des "user_id" ayant voté "yes".
		Cette liste ne contient que les id des filles qui ont déjà voté positivement pour le profil du programme.
		Antoine ou moi créerons le programme pour trier les profiles plus tard.
		Je vais donc inventer une liste en attendant. */
		List<String> listUserId = new ArrayList<>(Arrays.asList("azertyuiop369","qsdfghjklm258","obff5b362880e6595555e8d57270391af933a5f984745808f"));

		RestServiceManagement rest = new RestServiceManagement();
		rest.setParameter("src/main/resources/messages/vote.xml");
		rest.messaging(new VoteEncounter(listUserId.get(1), false).getBody());
	}
}
