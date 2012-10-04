package com.inepex.ineFrame.client.kvo;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.ListFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class KvoJsonParser {
	
	public static interface ResultObjectExtractor {
		public JSONObject extract(JSONObject jso);
	}

	private final JSONObject jso;
	private final ObjectDesc od;
	private final AssistedObjectHandlerFactory factory;
	private final DescriptorStore descriptorStore;
	
	private Map<String, ResultObjectExtractor> customResultExtractors = new HashMap<String, ResultObjectExtractor>();

	public KvoJsonParser(DescriptorStore descriptorStore, JSONObject objectToParse, String descriptorName) {
		super();
		this.jso = objectToParse;
		this.descriptorStore=descriptorStore;
		this.factory= new AssistedObjectHandlerFactory(descriptorStore);
		
		od = descriptorStore.getOD(descriptorName);
	}
	
	public KvoJsonParser setCustomResultExtractors(Map<String, ResultObjectExtractor> customResultExtractors){
		this.customResultExtractors = customResultExtractors;
		return this;
	}
	
	public AssistedObject parse() throws RuntimeException {
		AssistedObjectHandler kvoChecker = 
			factory.createHandler(new KeyValueObject(od.getName()));
					
		for (String key : od.getKeys()){
			if (jso.containsKey(key)){
				JSONValue childJso = jso.get(key); 
				FDesc fdesc = od.getField(key);
				switch (fdesc.getType()){
				case BOOLEAN:
					if (childJso.isNull() != null) kvoChecker.set(key, (Boolean)null);
					else if (childJso.isBoolean() == null){
						throw getParseException(key);
					} else {
						kvoChecker.set(key, childJso.isBoolean().booleanValue());
					}
					break;
				case DOUBLE:
					if (childJso.isNull() != null) kvoChecker.set(key, (Double)null);
					else if (childJso.isNumber() == null){
						throw getParseException(key);
					} else {
						kvoChecker.set(key, childJso.isNumber().doubleValue());
					}
					break;
				case LONG:
					if (childJso.isNull() != null) kvoChecker.set(key, (Long)null);
					else if (childJso.isNumber() == null){
						throw getParseException(key);
					} else {
						kvoChecker.set(key, new Double(childJso.isNumber().doubleValue()).longValue());
					}
					break;
				case STRING:
					if (childJso.isNull() != null) kvoChecker.set(key, (String)null);
					else if (childJso.isString() == null){
						throw getParseException(key);
					} else {
						kvoChecker.set(key, childJso.isString().stringValue());
					}
					break;
				case RELATION:
					if (childJso.isNull() != null) kvoChecker.set(key, (Relation)null);
					else if (childJso.isObject() == null && childJso.isNumber() == null){
						throw getParseException(key);
					} else if (childJso.isObject() != null){
						AssistedObject relKvo = 
							new KvoJsonParser(descriptorStore, childJso.isObject(), ((RelationFDesc)fdesc).getRelatedDescriptorName())
							.parse();
						
						kvoChecker.set(key, new Relation(relKvo));
					} else {
						kvoChecker.set(key, new Relation(new Double(childJso.isNumber().doubleValue()).longValue(), ""));
					}
					break;
				case LIST:
					if (childJso.isNull() != null) kvoChecker.set(key, (IneList)null);
					else if (childJso.isArray() == null){
						throw getParseException(key);
					} else {
						IneList list = new IneList();
						for (int i = 0; i < childJso.isArray().size(); i++){
							String relatedDescName = ((ListFDesc)fdesc).getRelatedDescriptorType();
							JSONObject relJso = null;
							if (customResultExtractors.containsKey(relatedDescName)){
								relJso = customResultExtractors.get(relatedDescName).extract(childJso.isArray().get(i).isObject());
							} else {
								relJso = childJso.isArray().get(i).isObject();
							}
							
							AssistedObject relKvo = 
								new KvoJsonParser(descriptorStore, relJso, relatedDescName)
								.parse();
							list.getRelationList().add(new Relation(relKvo));
						}
						kvoChecker.set(key, list);
					}
					break;
					
				}
			}
		}
		return kvoChecker.getAssistedObject();
	}
	
	private RuntimeException getParseException(String key) {
		return new RuntimeException("Could not parse object. Value with invalid type: " + key);
	}
	
}
