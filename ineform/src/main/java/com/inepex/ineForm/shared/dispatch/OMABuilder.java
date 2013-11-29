package com.inepex.ineForm.shared.dispatch;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.PropHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObjectBuilderBase;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;

public class OMABuilder extends AssistedObjectBuilderBase<OMABuilder>{

	private ObjectManipulationAction manipulation;
	
	@Inject
	public OMABuilder(DescriptorStore descStore, AssistedObjectHandlerFactory aoHandlerFactory, PropHandler propHandler) {
		super(descStore, aoHandlerFactory, propHandler);
	}
	
	/**
	 * set manipulation type to CREATE_OR_EDIT_REQUEST
	 * @param descriptor
	 * @return
	 */
	public OMABuilder createEdit(String descriptor){
		return create(descriptor, ManipulationTypes.CREATE_OR_EDIT_REQUEST);
	}
	
	/**
	 * set manipulation type to CREATE_OR_EDIT_REQUEST
	 * @param descriptor
	 * @return
	 */
	public OMABuilder createRefresh(String descriptor){
		return create(descriptor, ManipulationTypes.REFRESH);
	}
	
	public OMABuilder create(String descriptor, ManipulationTypes type){
		ao = aoHandlerFactory.createHandler(descriptor);
		manipulation = new ObjectManipulationAction(type, ao.getAssistedObject());
		return this;
	}
	
	public ObjectManipulationAction build(){
		return manipulation;
	}
	
	
	public OMABuilder propGroup(String propGroup){
		manipulation.getPropGroups().add(propGroup);
		return this;
	}
	
	public OMABuilder as(Long userId){
		manipulation.setExecutingUser(userId);
		return this;
	}

}
