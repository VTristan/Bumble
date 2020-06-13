package fr.bastion.rest;

public class Program {

	public static void main(String[] args) {
		RestServiceManagement rest = new RestServiceManagement();
		rest.setParameter("src/main/resources/messages/encounters.xml");
		System.out.println(rest.toString());;
		rest.messaging();
		//TODO:Result in JSON file
	}

}
