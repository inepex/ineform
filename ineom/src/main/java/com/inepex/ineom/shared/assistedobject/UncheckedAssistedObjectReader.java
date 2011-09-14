package com.inepex.ineom.shared.assistedobject;

import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;

public class UncheckedAssistedObjectReader {

	private final AssistedObject kvo;

	public UncheckedAssistedObjectReader(AssistedObject kvo) {
		this.kvo = kvo;
	}
	
	public Boolean getBoolean(String key) {
		return kvo.getBoolean(key);
	}

	public Double getDouble(String key) {
		return kvo.getDouble(key);
	}

	public IneList getList(String key){
		return kvo.getList(key);
	}

	public Long getLong(String key) {
		return kvo.getLong(key);
	}

	public Relation getRelation(String key){
		return kvo.getRelation(key);
	}

	public String getString(String key){
		return kvo.getString(key);
	}
	
}
