package com.inepex.ineom.shared.descriptorstore;

import java.util.Map;
import java.util.TreeMap;

public class TreeDescriptorStoreMapCreator implements DescriptorStoreMapCreator{

	@Override
	public <K, V> Map<K, V> createMap(GenParam<K, V> genParams) {
		return new TreeMap<K, V>();
	}

}
