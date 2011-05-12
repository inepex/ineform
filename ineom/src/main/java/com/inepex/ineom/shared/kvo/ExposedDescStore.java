package com.inepex.ineom.shared.kvo;

import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ExposedDescStore {
	
	private static DescriptorStore exposedStore = null;
	
	public static DescriptorStore get() {
		if (exposedStore == null)
			throw new RuntimeException("DescriptorStore have not been exposed! Application is not set up properly!" +
					" On gwt side it is exposed automatically with instantiating ClientDescriptorStore, on server side with" +
					" ServerDescriptorStore");
		return exposedStore;
	}

	public static void setExposedStore(DescriptorStore descriptorStore) {
		exposedStore = descriptorStore;
	}
}
