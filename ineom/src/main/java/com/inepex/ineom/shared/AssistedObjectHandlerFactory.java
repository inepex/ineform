package com.inepex.ineom.shared;


import com.google.inject.Inject;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class AssistedObjectHandlerFactory {

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new AIHF(decStore)'
	 */
	@Inject
	public AssistedObjectHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}

	public AssistedObjectHandler createHandler(AssistedObject assistedObject) {
		return new AssistedObjectHandler(assistedObject, descriptorStore);
	}
}
