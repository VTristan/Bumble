package fr.bastion.rest;

public class Program {

	public static void main(String[] args) {
		RestServiceManagement rest = new RestServiceManagement();
		rest.setParameter("src/main/resources/messages/encounters.xml");
		rest.messaging();
	}

}
