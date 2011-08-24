package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;

import com.inepex.ineom.shared.IneT;

public class RelationFDesc extends FDesc implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2284738151622982360L;
	
	private String relatedDescriptorName;

	public RelationFDesc() {
		type = IneT.RELATION;
	}

	public RelationFDesc(String key, String defaultDisplayName, String relatedDescriptorName) {
		super(key, IneT.RELATION, defaultDisplayName);
		this.relatedDescriptorName = relatedDescriptorName;
	}
	
	public String getRelatedDescriptorName() {
		return relatedDescriptorName;
	}

	public void setRelatedDescriptorName(String relatedDescriptorName) {
		this.relatedDescriptorName = relatedDescriptorName;
	}
	
	
}
