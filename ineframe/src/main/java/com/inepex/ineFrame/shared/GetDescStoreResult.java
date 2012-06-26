package com.inepex.ineFrame.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.TypedDescriptorMap;
import com.inepex.ineom.shared.dispatch.GenericResult;

public class GetDescStoreResult extends GenericResult {

	private List<ObjectDesc> objectDescs;
	private Map<String, TypedDescriptorMap<? extends DescriptorBase>> allTypedDescMap = new HashMap<String, TypedDescriptorMap<? extends DescriptorBase>>();
	
	
	public GetDescStoreResult() {
	}
	
	
	public List<ObjectDesc> getObjectDescs() {
		return objectDescs;
	}
		
	public void setObjectDescs(List<ObjectDesc> objectDescs) {
		this.objectDescs = objectDescs;
	}

	public void setAllTypedDescriptorMap(
			Map<String, TypedDescriptorMap<? extends DescriptorBase>> typedDescriptorMapWhole) {
		this.allTypedDescMap.putAll(typedDescriptorMapWhole);
		
	}

	public Map<String, TypedDescriptorMap<? extends DescriptorBase>> getAllTypedDescMap() {
		return allTypedDescMap;
	}
		
	
}
