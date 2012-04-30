package com.inepex.ineFrame.server.auth;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class AuthRuleSet {
	
	public static class AuthObject {
		public static String device = "device";
		public static String user = "user";
	}
	
	public static class Permissions {
		public static String view = "view";
		public static String modify = "modify";		
	}
	
	/**
	 * AuthRules by objectName
	 */
	private ListMultimap<String, AuthRule> rules = ArrayListMultimap.create();
	
	public AuthRuleSet() {
	}
	
	public AuthRuleSet ADD(String objectName, Long objectId, String permission){
		rules.put(objectName, AuthRule.createObjectPermission(objectId, permission));		
		return this;
	}
	
	public AuthRuleSet ADD(String objectName, List<Long> objectIds, String permission){
		rules.put(objectName, AuthRule.createObjectPermission(objectIds, permission));		
		return this;
	}

	public ListMultimap<String, AuthRule> getRules() {
		return rules;
	}

	public void setRules(ListMultimap<String, AuthRule> rules) {
		this.rules = rules;
	}	
	
}
