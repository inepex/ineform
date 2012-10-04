package com.inepex.ineom.shared.descriptorstore;

import com.google.inject.Inject;
import com.inepex.ineom.server.DescStoreCreator;

public class ClientDescStoreCreator implements DescStoreCreator{

	private final DescriptorStoreMapCreator mapCreator;
	
	@Inject
	public ClientDescStoreCreator(DescriptorStoreMapCreator mapCreator) {
		this.mapCreator=mapCreator;
	}
	
	@Override
	public ClientDescriptorStore createDescStore(String lang) {
		return new ClientDescriptorStore(mapCreator);
	}

}
