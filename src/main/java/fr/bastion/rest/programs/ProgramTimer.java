package fr.bastion.rest.programs;

import fr.bastion.utiles.Quadrillage;
import fr.bastion.utiles.TaskManager;

public class ProgramTimer {

	public static void main(String[] args) {
//		new Monitoring();
		Quadrillage bajolet = new Quadrillage(null, 48.6078 , 2.0595 , 5, 1);
		new TaskManager().startBumbleTask(bajolet, (byte) 1, 5000, 10000);
	}

}