package com.inepex.ineom.shared;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@Singleton
public class AssistedObjectHandlerFactory {

	private final DescriptorStore descriptorStore;
	
	/**
	 * can be injected or created with 'new {@link AssistedObjectHandlerFactory}(decStore)'
	 */
	@Inject
	public AssistedObjectHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}

	public AssistedObjectHandler createHandler(AssistedObject assistedObject) {
		return new AssistedObjectHandler(assistedObject, descriptorStore);
	}
	
	public AssistedObjectHandler createHandler(String descriptorName) {
		return new AssistedObjectHandler(new KeyValueObject(descriptorName), descriptorStore);
	}
}
