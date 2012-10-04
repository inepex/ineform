package com.inepex.ineom.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.inepex.ineom.shared.descriptorstore.DescriptorStoreMapCreator;

public class ConcurrentDescStoreMapCreator implements DescriptorStoreMapCreator {

	@Override
	public <K, V> Map<K, V> createMap(GenParam<K, V> genParams) {
		return new ConcurrentHashMap<K, V>();
	}

	
}
