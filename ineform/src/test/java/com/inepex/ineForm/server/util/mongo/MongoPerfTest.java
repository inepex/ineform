package com.inepex.ineForm.server.util.mongo;

import java.net.UnknownHostException;
import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MongoPerfTest {
    private final int nrOfDoc = 1000000;

    private DBCollection coll;
    private Random rand = new Random();
    private Integer nrOfMs;

    public MongoPerfTest() {}

    private void connect() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient();
        DB db = mongoClient.getDB("inetrack");
        coll = db.getCollection("perf");
    }

    private void createIndex() {
        coll.ensureIndex(new BasicDBObject("object_type", 1).append("user.onRoad", 1));
    }

    private void insertTestData() {
        for (int i = 0; i < nrOfDoc; i++) {
            Long objectId = rand.nextInt(nrOfDoc) + 100000L;
            Boolean onRoad = rand.nextBoolean();
            BasicDBObject doc =
                new BasicDBObject()
                    .append("object_type", "device")
                    .append("object_id", objectId)
                    .append(
                        "user",
                        new BasicDBObject().append("segmentationType", "ACC").append(
                            "onRoad",
                            onRoad));
            coll.insert(doc);
        }
    }

    private void measureQuery() {
        DBObject explain =
            coll
                .find((DBObject) JSON.parse("{object_type:'device', 'user.onRoad':true}"))
                .explain();
        nrOfMs = (Integer) explain.get("millis");
    }

    private void clear() {
        coll.drop();
    }

    private void report() {
        System.out.println("Query needed " + nrOfMs + "ms");
    }

    public void execute() {

        try {
            connect();
            createIndex();
            insertTestData();
            measureQuery();
            clear();
            report();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MongoPerfTest().execute();
    }

}
