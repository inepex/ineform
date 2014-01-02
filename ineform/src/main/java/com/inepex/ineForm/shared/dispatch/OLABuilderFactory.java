package com.inepex.ineForm.shared.dispatch;

import java.util.List;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.PropHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObjectBuilderFactoryBase;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class OLABuilderFactory extends AssistedObjectBuilderFactoryBase {
	
	@Inject
	public OLABuilderFactory(DescriptorStore descStore, AssistedObjectHandlerFactory aoHandlerFactory, PropHandler propHandler) {
		super(descStore, aoHandlerFactory, propHandler);
	}
	
	public Builder create(String descriptor){
		return create(descriptor, descriptor);
	}
	
	public Builder create(String descriptor, String searchDescriptor){
		return new Builder(descriptor, searchDescriptor);
	}
	
	public class Builder extends AssistedObjectBuilderFactoryBase.BuilderBase<Builder> {
		
		private final ObjectListAction objectList;
	
		private Builder(String descriptor, String searchDescriptor) {
			super(aoHandlerFactory.createHandler(searchDescriptor));
			objectList = new ObjectListAction(descriptor);
			objectList.setSearchParameters(ao.getAssistedObject());
		}
		
		public ObjectListAction build(){
			return objectList;
		}
		
		
		public Builder propGroup(String propGroup){
			objectList.getPropGroups().add(propGroup);
			return this;
		}
		
		public Builder propGroups(String propGroupsCommaSeparated){
			if (propGroupsCommaSeparated == null) return this;
			for (String propGroup : propGroupsCommaSeparated.split(IFConsts.comma)){
				objectList.getPropGroups().add(propGroup);
			}
			return this;
		}
		
		public Builder propGroups(List<String> propGroups){
			for (String propGroup : propGroups){
				objectList.getPropGroups().add(propGroup);
			}
			return this;
		}
		
		public Builder as(Long userId){
			objectList.setExecutingUser(userId);
			return this;
		}
	}
}
