package com.inepex.ineFrame.shared;

import java.util.HashMap;
import java.util.Map;

import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.TypedDescriptorMap;
import com.inepex.ineom.shared.dispatch.GenericResult;

public class GetDescStoreResult extends GenericResult {

	private Map<String, ObjectDesc> objectDescs = new HashMap<String, ObjectDesc>();
	private Map<String, TypedDescriptorMap<? extends DescriptorBase>> allTypedDescMap = new HashMap<String, TypedDescriptorMap<? extends DescriptorBase>>();
	
	
	public GetDescStoreResult() {
	}
	
	
	public Map<String, ObjectDesc> getObjectDescs() {
		return objectDescs;
	}


	public void setObjectDescriptorMap(Map<String, ObjectDesc> objectDescriptorMap) {
		this.objectDescs.putAll( objectDescriptorMap );
	}


	public void setAllTypedDescriptorMap(
			Map<String, TypedDescriptorMap<? extends DescriptorBase>> typedDescriptorMapWhole) {
		this.allTypedDescMap.putAll(typedDescriptorMapWhole);
		
	}


	public Map<String, TypedDescriptorMap<? extends DescriptorBase>> getAllTypedDescMap() {
		return allTypedDescMap;
	}
		
	
}
