package com.inepex.ineForm.server.util.mongo;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class LearningTest {

	@Test
	public void first() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB( "inetrack" );
		DBCollection coll = db.getCollection("first");
		coll.insert((DBObject)JSON.parse("{type:'alma', prop1: 2}"));
		DBCursor cursor = coll.find();
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
	}
	
}
