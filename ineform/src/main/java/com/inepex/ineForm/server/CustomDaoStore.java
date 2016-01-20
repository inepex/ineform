package com.inepex.ineForm.server;

import java.util.HashMap;
import java.util.Map;

public class CustomDaoStore {

    private Map<String, KVManipulatorDaoBase> providersByDescriptor = new HashMap<String, KVManipulatorDaoBase>();

    public CustomDaoStore() {

    }

    public void registerDao(String descriptorName, KVManipulatorDaoBase dao) {
        providersByDescriptor.put(descriptorName, dao);
    }

    public Map<String, KVManipulatorDaoBase> getProvidersByDescriptor() {
        return providersByDescriptor;
    }
}
