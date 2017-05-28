package com.inepex.ineom.shared.descriptorstore;

import java.util.Map;

public interface DescriptorStoreMapCreator {

    <K, V> Map<K, V> createMap(GenParam<K, V> genParams);

    class GenParam<K, V> {}
}
