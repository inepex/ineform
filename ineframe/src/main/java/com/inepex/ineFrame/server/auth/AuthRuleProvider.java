package com.inepex.ineFrame.server.auth;

import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.RelationList;

public interface AuthRuleProvider {

	public boolean authorize(Long userId, ObjectManipulation objectManipulation, AuthRuleSet authRuleSet);
	
	public boolean authorize(Long userId, ObjectList objectList, AuthRuleSet authRuleSet);
	
	public boolean authorize(Long userId, RelationList relationList, AuthRuleSet authRuleSet);
	
	

}
