package com.inepex.ineForm.server.prop.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.BSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@Singleton
public class PropDao {

	private static final Logger _logger = LoggerFactory.getLogger(PropDao.class);
	private static final String DB = "inetrack";
	private static final String COLLECTION = "props";
	
	private static final String k_objectType = "object_type";
	private static final String k_objectId = "object_id";
	
	private final String mongoUrl;
	private final String mongoUser;
	private final String mongoPass;
	private MongoClient mongoClient;
	
	@Inject
	public PropDao(@Named(IFConsts.prop_mongoUrl) String mongoUrl,
			@Named(IFConsts.prop_mongoUser) String mongoUser,
			@Named(IFConsts.prop_mongoPass) String mongoPass
			) {
		this.mongoUrl = mongoUrl;
		this.mongoUser = mongoUser;
		this.mongoPass = mongoPass;
	}
	
	public DBCollection getMongoDb(){
		if (mongoClient == null){
			synchronized (this) {
				if (mongoClient == null){ //double check to avoid creating more than one instance
					try {
						_logger.info("Creating mongoclient");
						mongoClient = new MongoClient(mongoUrl);
					} catch (UnknownHostException e) {
						_logger.error("Unknown host: " + mongoUrl);
						return null;
					}
				}
			}
				

		}
		com.mongodb.DB db = mongoClient.getDB(DB);
		if (mongoUser != null && !mongoUser.equals("")){
			db.authenticate(mongoUser, mongoPass.toCharArray());
		}
		DBCollection collection = db.getCollection(COLLECTION);
		try {
			checkDefaultIndex(collection);
		} catch (Exception e){
			_logger.error("Mongodb not found on: " + mongoUrl);
			return null;
		}
		return collection;
	}
	
	private void checkDefaultIndex(DBCollection collection){
		BasicDBObject index = new BasicDBObject();
		index.append(k_objectType, 1);
		index.append(k_objectId, 1);
		collection.ensureIndex(index);
	}
	
	public void manipulate(AssistedObject objectWithChanges, AssistedObject objectToMap, List<String> groups){
		if (getMongoDb() == null) return;
		doCreateOrEdit(objectWithChanges);
		mapPropGroup(objectToMap, groups);
		closeConnection();
	}
	
	public void doCreateOrEdit(AssistedObject objectWithChanges){
		if (getMongoDb() == null || objectWithChanges.getAllPropsJson() == null) return;
		for (Entry<String, String> entry : objectWithChanges.getAllPropsJson().entrySet()){
			setProp(objectWithChanges.getDescriptorName(), objectWithChanges.getId(), 
					entry.getKey(), entry.getValue());
		}
	}
	
	public void mapPropGroupsAndCloseConn(AssistedObject object, List<String> groups){
		mapPropGroup(object, groups);
		closeConnection();
	}
	
	public void mapPropGroup(AssistedObject object, List<String> groups){
		if (getMongoDb() == null || groups == null) return;
		
		BasicDBObject document = getDocument(object.getDescriptorName(), object.getId());
		if (document == null) return;
		for (String group : groups){
			if (document.containsField(group)){
				object.setPropsJson(group, JSON.serialize(document.get(group)));
			}
		}
	}
	
	public void mapPropGroups(List<AssistedObject> objects, List<String> groups){
		if (getMongoDb() == null || groups == null) return;
		List<Long> ids = AssistedObjectUtil.getObjectIds(objects);
		Map<Long, BasicDBObject> documentMap = getDocument(objects.get(0).getDescriptorName(), ids);
		if (documentMap.size() == 0) return;
		for(AssistedObject obj : objects){
			for (String group : groups){
				BasicDBObject document = documentMap.get(obj.getId());
				if(document != null){
					obj.setPropsJson(group, JSON.serialize(document.get(group)));
				}
			}
		}
	}
	
	public void mapPropGroupsAndCloseConn(List<AssistedObject> objects, List<String> groups){
		mapPropGroups(objects, groups);
		closeConnection();
	}
	
	public void removeProps(String type, Long id){
		if (getMongoDb() == null) return;
		getMongoDb().remove(searchParamJson(type, id));
		closeConnection();
	}
	
	public void setProp(String type, Long id, String group, String changesJsonObj){
		if (getMongoDb() == null) return;
		if (changesJsonObj == null || changesJsonObj.equals("")) return;
		BasicDBObject changes = (BasicDBObject)JSON.parse(changesJsonObj);
		BasicDBObject document = getDocument(type, id);
		if (document == null){
			document = createDocument(type, id);
		}
		BasicDBObject groupJson = null;
		if (document.containsField(group)){
			groupJson = (BasicDBObject)document.get(group);
		} else {
			groupJson = new BasicDBObject();
			document.append(group, groupJson);
		}
		for (String key : changes.keySet()){
			if (changes.get(key) == null){
				groupJson.remove(key);
			} else {
				groupJson.append(key, changes.get(key));
			}
		}
		getMongoDb().save(document);
	}
	
	public String getPropGroup(String type, Long id, String group){
		if (getMongoDb() == null) return "{}";
		BasicDBObject document = getDocument(type, id);
		if (document != null){
			if (document.keySet().contains(group)){
				return JSON.serialize(document.get(group));
			}
		}
		return "{}";
	}
	
	public List<String> getPropGroup(String type, List<Long> ids, String group){
		if (getMongoDb() == null) return new ArrayList<>();
		
		List<String> propGroups = new ArrayList<>();
		for (Long id : ids){
			propGroups.add(getPropGroup(type, id, group));
		}
		
		return propGroups;
	}
	
	public List<Long> findObjectIds(String type, String searchJson){
		if (getMongoDb() == null) return null;
		BasicDBObject searchObj = new BasicDBObject(k_objectType, type);
		searchObj.putAll((BSONObject)JSON.parse(searchJson));
		
		DBCursor cursor = getMongoDb().find(searchObj, new BasicDBObject(k_objectId, 1).append("_id", 0));
		List<Long> result = new ArrayList<>();
		try {
		   while(cursor.hasNext()) {
			   BasicDBObject o = (BasicDBObject)cursor.next();
		       result.add(o.getLong(k_objectId));
		   }
		} finally {
		   cursor.close();
		}
		return result;
	}
	
	private BasicDBObject searchParamJson(String type, Long id){
		return new BasicDBObject(k_objectType, type).append(k_objectId, id);
	}
	
	private BasicDBObject getDocument(String type, Long id){
		if (getMongoDb() == null) return null;
		Object o = getMongoDb().findOne(searchParamJson(type, id));
		if (o == null) return null;
		else return (BasicDBObject)o;
	}
	
	private Map<Long, BasicDBObject> getDocument(String type, List<Long> ids){
		if (getMongoDb() == null) return null;
		BasicDBObject basicObj = new BasicDBObject(k_objectType, type);
		BasicDBList orDbList = new BasicDBList();
		for(Long id : ids){
			orDbList.add(new BasicDBObject(k_objectId, id));
		}
		basicObj.append("$or", orDbList);
		DBCursor cursor = getMongoDb().find(basicObj);
		Map<Long, BasicDBObject> map = new HashMap<>();
		try {
			while (cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject)cursor.next();
				map.put(obj.getLong(k_objectId), obj);
			}
		} catch (Exception e) {
			cursor.close();
		}
		return map;
	}
	
	private BasicDBObject createDocument(String type, Long id){
		if (getMongoDb() == null) return null;
		getMongoDb().insert(searchParamJson(type, id));
		return getDocument(type, id);
	}
	
	public synchronized void closeConnection(){
		if (mongoClient != null){
			mongoClient.close();
			mongoClient = null;
		}
	}
	
}
