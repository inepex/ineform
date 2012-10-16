package com.inepex.ineFrame.shared;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.dispatch.GenericActionResult;

public class GetDescStoreResult extends GenericActionResult {

	private List<ObjectDesc> objectDescs;
	
	private List<String> odNames = new ArrayList<String>();
	private List<String> names = new ArrayList<String>();
	private List<DescriptorBase> typedDescrptors = new ArrayList<DescriptorBase>(); 
	
	public GetDescStoreResult() {
	}
	
	public List<ObjectDesc> getObjectDescs() {
		return objectDescs;
	}
		
	public void setObjectDescs(List<ObjectDesc> objectDescs) {
		this.objectDescs = objectDescs;
	}

	public List<String> getNames() {
		return names;
	}
	
	public List<String> getOdNames() {
		return odNames;
	}
	
	public List<? extends DescriptorBase> getTypedDescrptors() {
		return typedDescrptors;
	}
	
	public void addTypedDescriptor(String odName, String name, DescriptorBase typedDescriptor ) {
		odNames.add(odName);
		names.add(name);
		typedDescrptors.add(typedDescriptor);
	}
}
