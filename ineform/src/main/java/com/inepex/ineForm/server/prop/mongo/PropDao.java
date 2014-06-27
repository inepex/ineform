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
import com.mongodb.MongoClientOptions;
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
		System.setProperty("DEBUG.MONGO", "true");
		System.setProperty("DB.TRACE", "true");
	}
	
	private DBCollection getMongoDb(){
		if (mongoClient == null){
			synchronized (this) {
				if (mongoClient == null){ //double check to avoid creating more than one instance
					try {
						_logger.info("Creating mongoclient");
						MongoClientOptions options = MongoClientOptions.builder()
//								.writeConcern(WriteConcern.FSYNCED)
								.build();
						mongoClient = new MongoClient(mongoUrl, options);
						
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
	
	
	public void ensureIndex(String field){
		getMongoDb().ensureIndex(field);
	}
	
	public void manipulate(AssistedObject objectWithChanges, AssistedObject objectToMap, List<String> groups){
		if (getMongoDb() == null) return;
		objectWithChanges.setId(objectToMap.getId());
		doCreateOrEdit(objectWithChanges);
		mapPropGroup(objectToMap, groups);
	}
	
	public void manipulate(String descriptorName, List<Long> ids, Map<String, String> propJson){
		for(Long id: ids){
			doCreateOrEdit(descriptorName, id, propJson);
		}
		
	}
	
	private void doCreateOrEdit(AssistedObject objectWithChanges){
		if (getMongoDb() == null || objectWithChanges.getAllPropsJson() == null) return;
		for (Entry<String, String> entry : objectWithChanges.getAllPropsJson().entrySet()){
			setProp(objectWithChanges.getDescriptorName(), objectWithChanges.getId(), 
					entry.getKey(), entry.getValue());
		}
	}
	
	private void doCreateOrEdit(String descriptorName, Long id, Map<String, String> propJson){
		if (getMongoDb() == null || propJson == null || propJson.isEmpty()) return;
		for(String group : propJson.keySet()){
			setProp(descriptorName, id,	group, propJson.get(group));
		}
		
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
		if (getMongoDb() == null || groups == null || objects.isEmpty())
			return;
		
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
	
	public void removeProps(String type, Long id){
		if (getMongoDb() == null) return;
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		getMongoDb().remove(searchParamJson(type, id));
		mongoClient.getDB(DB).requestDone();
	}
	
	public void remove(BasicDBObject criteria){
		if (getMongoDb() == null) return;
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		getMongoDb().remove(criteria);
		mongoClient.getDB(DB).requestDone();
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
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		getMongoDb().save(document);
		mongoClient.getDB(DB).requestDone();
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
	
	public BasicDBObject getPropGroupBasicDbObject(String type, Long id, String group){
		if (getMongoDb() == null)
			return new BasicDBObject();
		
		BasicDBObject document = getDocument(type, id);
		if (document != null){
			if (document.keySet().contains(group)){
				return (BasicDBObject) document.get(group);
			}
		}
		return new BasicDBObject();
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
		
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		DBCursor cursor = getMongoDb().find(searchObj, new BasicDBObject(k_objectId, 1).append("_id", 0));
		List<Long> result = new ArrayList<>();
		try {
		   while(cursor.hasNext()) {
			   BasicDBObject o = (BasicDBObject)cursor.next();
		       result.add(o.getLong(k_objectId));
		   }
		} finally {
		   cursor.close();
		   mongoClient.getDB(DB).requestDone();
		}
		return result;
	}
	
	private BasicDBObject searchParamJson(String type, Long id){
		return new BasicDBObject(k_objectType, type).append(k_objectId, id);
	}
	
	private BasicDBObject getDocument(String type, Long id){
		if (getMongoDb() == null) return null;
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		Object o = getMongoDb().findOne(searchParamJson(type, id));
		mongoClient.getDB(DB).requestDone();
		if (o == null) return null;
		else return (BasicDBObject)o;
	}
	
	public Map<Long, BasicDBObject> getDocument(String type, List<Long> ids){
		if (getMongoDb() == null) return null;
		BasicDBObject basicObj = new BasicDBObject(k_objectType, type);
		BasicDBList entityIds = new BasicDBList();
		entityIds.addAll(ids);
		BasicDBObject inClause = new BasicDBObject("$in", entityIds);
		basicObj.append(k_objectId, inClause);
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		DBCursor cursor = getMongoDb().find(basicObj);
		Map<Long, BasicDBObject> map = new HashMap<>();
		try {
			while (cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject)cursor.next();
				map.put(obj.getLong(k_objectId), obj);
			}
		} catch (Exception e) {
		} finally {
			cursor.close();
			mongoClient.getDB(DB).requestDone();
		}		
		return map;
	}
	
	private BasicDBObject createDocument(String type, Long id){
		if (getMongoDb() == null) return null;
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		getMongoDb().insert(searchParamJson(type, id));
		mongoClient.getDB(DB).requestDone();
		return getDocument(type, id);
	}
	
	
	/**
	 * don't use for update! Only for creating new document.
	 */
	public void createDocumentWithProperties(String type, Long id, Map<String, String> props){
		if (getMongoDb() == null) return;
		if (props == null || props.size() == 0) return;
		BasicDBObject doc = new BasicDBObject(k_objectType, type).append(k_objectId, id);
		for (Entry<String, String> propEntry : props.entrySet()){
			String group = propEntry.getKey();
			String jsonValue = propEntry.getValue();
			doc.append(group, (BasicDBObject)JSON.parse(jsonValue));
		}
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		getMongoDb().save(doc);
		mongoClient.getDB(DB).requestDone();
	}	

	public void deleteProps(String objectType, List<Long> idLists,
			Map<String, String> propsToDeleteJson) {
		if (getMongoDb() == null || 
			propsToDeleteJson.isEmpty() ||
			idLists.isEmpty()) {
			return;
		}
		BasicDBObject updateQuery = new BasicDBObject();
		for(String group : propsToDeleteJson.keySet()){
			String keyValue = propsToDeleteJson.get(group);
			BasicDBObject obj = (BasicDBObject) JSON.parse(keyValue);
			for(String key : obj.keySet()){
				Object value = propsToDeleteJson.get(key);
				updateQuery.append("$unset", new BasicDBObject().append(group + "." + key, value));
			}
		}
		BasicDBObject searchObj = new BasicDBObject(k_objectType, objectType);
		BasicDBList entityIds = new BasicDBList();
		entityIds.addAll(idLists);
		BasicDBObject inClause = new BasicDBObject("$in", entityIds);
		searchObj.append(k_objectId, inClause);
		mongoClient.getDB(DB).requestStart();
		mongoClient.getDB(DB).requestEnsureConnection();
		getMongoDb().updateMulti(searchObj, updateQuery);
		mongoClient.getDB(DB).requestDone();
	}
	
}
