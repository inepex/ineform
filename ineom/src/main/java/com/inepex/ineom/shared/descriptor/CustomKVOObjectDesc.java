package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IFConsts;


@SuppressWarnings("serial")
public class CustomKVOObjectDesc extends ObjectDesc implements Serializable, IsSerializable {
	
	/**
	 *  field key of ObjectDec
	 */
	private String key;
	
	
	public CustomKVOObjectDesc() {
		super(IFConsts.customDescriptorName);
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "CustomKVOObjectDesc [key=" + key + ", getName()=" + getName() + "]";
	}
}
