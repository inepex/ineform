package com.inepex.ineFrame.server.auth;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

public class AuthRuleStore {

	private Map<String, AuthRuleProvider> providersByDescriptor = new HashMap<String, AuthRuleProvider>();
	
	@Inject
	public AuthRuleStore() {
	}
	
	public void registerAuth(String descriptorName, AuthRuleProvider authRuleProvider){
		providersByDescriptor.put(descriptorName, authRuleProvider);
	}

	public Map<String, AuthRuleProvider> getProvidersByDescriptor() {
		return providersByDescriptor;
	}
}
