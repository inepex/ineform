package com.inepex.ineom.server;

import com.inepex.ineom.shared.descriptorstore.ClientDescriptorStore;

public interface DescStoreCreator {

    ClientDescriptorStore createDescStore(String lang);

}
