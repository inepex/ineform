package com.inepex.ineFrame.server.auth;

import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;
import com.inepex.ineom.shared.dispatch.interfaces.RelationList;

public interface AuthRuleProvider {

	public boolean authorize(ObjectList objectList, AuthRuleSet authRuleSet);
	
	public boolean authorize(ObjectManipulation objectManipulation, AuthRuleSet authRuleSet);
	
	public boolean authorize(RelationList relationList, AuthRuleSet authRuleSet);

}
