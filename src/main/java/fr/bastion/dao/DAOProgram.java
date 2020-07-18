package fr.bastion.dao;

public class DAOProgram {

	public static void main(String[] args) {
		DaoMongo dao = DaoMongo.getInstance();
		//dao.insertOneString("test", "BumbleGetEncouters","{'key':'value'}");
		dao.find("test", "BumbleGetEncouters");
	}
}
