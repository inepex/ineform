package com.inepex.ineForm.shared.dispatch;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.PropHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObjectBuilderFactoryBase;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;

public class OMABuilderFactory extends AssistedObjectBuilderFactoryBase {
	
	@Inject
	public OMABuilderFactory(DescriptorStore descStore, AssistedObjectHandlerFactory aoHandlerFactory, PropHandler propHandler) {
		super(descStore, aoHandlerFactory, propHandler);
	}
	
	/**
	 * set manipulation type to CREATE_OR_EDIT_REQUEST
	 * @param descriptor
	 * @return
	 */
	public Builder createEdit(String descriptor){
		return create(descriptor, ManipulationTypes.CREATE_OR_EDIT_REQUEST);
	}
	
	/**
	 * set manipulation type to CREATE_OR_EDIT_REQUEST
	 * @param descriptor
	 * @return
	 */
	public Builder createRefresh(String descriptor){
		return create(descriptor, ManipulationTypes.REFRESH);
	}
	
	public Builder create(String descriptor, ManipulationTypes type){
		return new Builder(descriptor, type);
	}
	
	public class Builder extends AssistedObjectBuilderFactoryBase.BuilderBase<Builder> {
		
		private final ObjectManipulationAction manipulation;
		
		private Builder(String descriptor, ManipulationTypes type) {
			super(aoHandlerFactory.createHandler(descriptor));
			manipulation = new ObjectManipulationAction(type, ao.getAssistedObject());
		}
		
		public ObjectManipulationAction build(){
			return manipulation;
		}
		
		
		public Builder propGroup(String propGroup){
			manipulation.getPropGroups().add(propGroup);
			return this;
		}
		
		public Builder as(Long userId){
			manipulation.setExecutingUser(userId);
			return this;
		}
		
		public Builder setIdToRefresh(Long id){
			manipulation.setIdToRefresh(id);
			return this;
		}
		
	}

}
