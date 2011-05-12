package com.inepex.ineFrame.server;

import java.util.Set;

public interface UserHasRequiredRoleVerifier {
	boolean userHasAnyOfListedRoles(Set<String> allowedRoles);
}
