package com.inepex.ineom.server;

import com.inepex.ineom.shared.descriptorstore.ClientDescriptorStore;

public interface DescStoreCreator {

    public ClientDescriptorStore createDescStore(String lang);

}
