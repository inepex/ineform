package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;

public class ListFDesc extends FDesc implements
		Serializable, IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2922503316584128389L;
	
	private String relatedDescriptorType = null;

	public ListFDesc() {
		type = IneT.LIST;
	}

	public ListFDesc(String key
			, String defaultDisplayName
			, String relatedDescriptorType
			, String... propList) {
		this(key, defaultDisplayName, relatedDescriptorType);
		addProps(propList);
	}
	
	public ListFDesc(String key, String defaultDisplayName, String relatedDescriptorType) {
		super(key, IneT.LIST, defaultDisplayName);
		this.relatedDescriptorType = relatedDescriptorType;
	}

	public String getRelatedDescriptorType() {
		return relatedDescriptorType;
	}

	public void setRelatedDescriptorType(String relatedDescriptorType) {
		this.relatedDescriptorType = relatedDescriptorType;
	}

	
}
