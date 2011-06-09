package com.inepex.ineFrame.client.kvo;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ListFDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.kvo.ExposedDescStore;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.Relation;

public class KvoJsonParser {

	JSONObject jso;
	ObjectDesc od;

	public KvoJsonParser(JSONObject objectToParse, String descriptorName) {
		super();
		this.jso = objectToParse;
		od = ExposedDescStore.get().getOD(descriptorName);
	}
	
	public KeyValueObject parse() throws RuntimeException {
		KeyValueObject kvo = new KeyValueObject(od.getName());
		for (String key : od.getKeys()){
			if (jso.containsKey(key)){
				JSONValue childJso = jso.get(key); 
				FDesc fdesc = od.getField(key);
				switch (fdesc.getType()){
				case BOOLEAN:
					if (childJso.isNull() != null) kvo.set(key, (Boolean)null);
					else if (childJso.isBoolean() == null){
						throw getParseException(key);
					} else {
						kvo.set(key, childJso.isBoolean().booleanValue());
					}
					break;
				case DOUBLE:
					if (childJso.isNull() != null) kvo.set(key, (Double)null);
					else if (childJso.isNumber() == null){
						throw getParseException(key);
					} else {
						kvo.set(key, childJso.isNumber().doubleValue());
					}
					break;
				case LONG:
					if (childJso.isNull() != null) kvo.set(key, (Long)null);
					else if (childJso.isNumber() == null){
						throw getParseException(key);
					} else {
						kvo.set(key, new Double(childJso.isNumber().doubleValue()).longValue());
					}
					break;
				case STRING:
					if (childJso.isNull() != null) kvo.set(key, (String)null);
					else if (childJso.isString() == null){
						throw getParseException(key);
					} else {
						kvo.set(key, childJso.isString().stringValue());
					}
					break;
				case RELATION:
					if (childJso.isNull() != null) kvo.set(key, (Relation)null);
					else if (childJso.isObject() == null){
						throw getParseException(key);
					} else {
						KeyValueObject relKvo = 
							new KvoJsonParser(childJso.isObject(), ((RelationFDesc)fdesc).getRelatedDescriptorName())
							.parse();
						
						kvo.set(key, new Relation(relKvo));
					}
					break;
				case LIST:
					if (childJso.isNull() != null) kvo.set(key, (IneList)null);
					else if (childJso.isArray() == null){
						throw getParseException(key);
					} else {
						IneList list = new IneList();
						for (int i = 0; i < childJso.isArray().size(); i++){
							
							KeyValueObject relKvo = 
								new KvoJsonParser(childJso.isArray().get(i).isObject(), ((ListFDesc)fdesc).getRelatedDescriptorType())
								.parse();
							list.getRelationList().add(new Relation(relKvo));
						}
						kvo.set(key, list);
					}
					break;
					
				}
			}
		}
		return kvo;
	}
	
	private RuntimeException getParseException(String key) {
		return new RuntimeException("Could not parse object. Value with invalid type: " + key);
	}
	
}
