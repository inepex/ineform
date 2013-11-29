package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.PropHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObjectBuilderBase;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class OLABuilder extends AssistedObjectBuilderBase<OLABuilder>{

	private ObjectListAction objectList;
	
	@Inject
	public OLABuilder(DescriptorStore descStore, AssistedObjectHandlerFactory aoHandlerFactory, PropHandler propHandler) {
		super(descStore, aoHandlerFactory, propHandler);
	}
	
	public OLABuilder create(String descriptor){
		return create(descriptor, descriptor);
	}
	
	public OLABuilder create(String descriptor, String searchDescriptor){
		objectList = new ObjectListAction(descriptor);
		ao = aoHandlerFactory.createHandler(searchDescriptor);
		objectList.setSearchParameters(ao.getAssistedObject());
		return this;
	}
	
	public ObjectListAction build(){
		return objectList;
	}
	
	
	public OLABuilder propGroup(String propGroup){
		objectList.getPropGroups().add(propGroup);
		return this;
	}
	
	public OLABuilder propGroups(String propGroupsCommaSeparated){
		if (propGroupsCommaSeparated == null) return this;
		for (String propGroup : propGroupsCommaSeparated.split(IFConsts.comma)){
			objectList.getPropGroups().add(propGroup);
		}
		return this;
	}
	
	public OLABuilder propGroups(List<String> propGroups){
		for (String propGroup : propGroups){
			objectList.getPropGroups().add(propGroup);
		}
		return this;
	}
	
	public OLABuilder as(Long userId){
		objectList.setExecutingUser(userId);
		return this;
	}

}
