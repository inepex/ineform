package com.inepex.ineFrame.server.auth;

import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.RelationList;

public interface AuthRuleProvider {
	
	/**
	 * Authorize objectManipulation execution. Add rules to AuthRuleSet by AuthRuleSet.ADD()
	 * Check https://trac.inetrack.com/wiki/PermissionHandling#Logicofauthorizemethod
	 */
	public boolean authorize(Long userId, ObjectManipulation objectManipulation, AuthRuleSet authRuleSet);
	
	/**
	 * Authorize objectList execution. Add rules to AuthRuleSet by AuthRuleSet.ADD()
	 * Check https://trac.inetrack.com/wiki/PermissionHandling#Logicofauthorizemethod
	 */
	public boolean authorize(Long userId, ObjectList objectList, AuthRuleSet authRuleSet);
	
	/**
	 * Authorize relationList execution. Add rules to AuthRuleSet by AuthRuleSet.ADD()
	 * Check https://trac.inetrack.com/wiki/PermissionHandling#Logicofauthorizemethod
	 */
	public boolean authorize(Long userId, RelationList relationList, AuthRuleSet authRuleSet);
	
}
