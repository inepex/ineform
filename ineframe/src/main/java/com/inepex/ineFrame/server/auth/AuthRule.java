package com.inepex.ineFrame.server.auth;

import java.util.ArrayList;
import java.util.List;

public class AuthRule {
	
	public static AuthRule createPermission(String permission){
		AuthRule rule = new AuthRule();
		rule.setPermission(permission);
		return rule;
	}
	
	public static AuthRule createObjectPermission(Long objectId, String permission){
		AuthRule rule = new AuthRule();
		if (objectId != null)
			rule.getObjectIds().add(objectId);
		rule.setPermission(permission);
		return rule;
	}
	
	public static AuthRule createObjectPermission(List<Long> objectIds, String permission){
		AuthRule rule = new AuthRule();
		rule.setObjectIds(objectIds);
		rule.setPermission(permission);
		return rule;
	}

	private List<Long> objectIds = new ArrayList<Long>();
	private String permission;
	private String requestingCommandClassName;

	public AuthRule() {
	}

	public List<Long> getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(List<Long> objectIds) {
		this.objectIds = objectIds;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public String getRequestingCommandClassName() {
		return requestingCommandClassName;
	}

	public void setRequestingCommandClassName(String requestingCommandClassName) {
		this.requestingCommandClassName = requestingCommandClassName;
	}
}
