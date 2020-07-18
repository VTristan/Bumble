package fr.bastion.dao;

import java.net.UnknownHostException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

public final class DaoMongo {
	static MongoClient mongoClient;

	private static volatile DaoMongo instance = null;
	
	private DaoMongo() {}
	public static DaoMongo getInstance() {
		if (instance == null) {
			synchronized (DaoMongo.class) {
				if (instance==null) {
					instance = new DaoMongo();
					try {
						mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					System.out.println("New instance...");
				}
			}
		}
		return instance;
	}
	

	public void find(String databaseName, String collectionName) {
		DB database = mongoClient.getDB(databaseName);
		DBCollection collection = database.getCollection(collectionName);
		DBCursor cursor = collection.find();
		while(cursor.hasNext())
		{
			System.out.println(cursor.next());
		}
	}
	
	public void insertOneString(String databaseName, String collectionName, String json) {
		DB database = mongoClient.getDB(databaseName);
		DBCollection collection = database.getCollection(collectionName);
		DBObject dbObject = (DBObject) JSON.parse(json);
		collection.insert(dbObject);
		System.out.println("element inserted!");
	}
	
	
	
	}
