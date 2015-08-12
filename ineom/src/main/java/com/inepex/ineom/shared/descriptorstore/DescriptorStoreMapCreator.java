package com.inepex.ineom.shared.descriptorstore;

import java.util.Map;

public interface DescriptorStoreMapCreator {

    public <K, V> Map<K, V> createMap(GenParam<K, V> genParams);

    public static class GenParam<K, V> {}
}
