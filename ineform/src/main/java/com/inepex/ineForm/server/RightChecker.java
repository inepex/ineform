package com.inepex.ineForm.server;

import java.util.Set;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineFrame.server.UserHasRequiredRoleVerifier;
import com.inepex.ineFrame.server.auth.RightDescriptor;
import com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights;
import com.inepex.ineFrame.shared.exceptions.AuthorizationException;
import com.inepex.ineom.shared.dispatch.ObjectListAction;

public class RightChecker {
	final Action<?> action;
	final RightDescriptor rightDescriptor;
	final UserHasRequiredRoleVerifier userHasRequiredRoleVerifier;

	public RightChecker(Action<?> action, RightDescriptor rightDescriptor,
			UserHasRequiredRoleVerifier userHasRequiredRoleVerifier) {
		super();
		this.action = action;
		this.rightDescriptor = rightDescriptor;
		this.userHasRequiredRoleVerifier = userHasRequiredRoleVerifier;
	}

	public boolean isActionAllowed() throws AuthorizationException {
		
		Set<String> rolesAllowedToCallAction = null;
		
		if (action instanceof ObjectManipulationAction) {
			if (action.getClass().getName().equals(ObjectManipulationAction.class.getName())) {
				ObjectManipulationAction omAction = (ObjectManipulationAction) action;
				rolesAllowedToCallAction = rightDescriptor.getAllowedRolesForObjectRight(
															omAction.getObject().getDescriptorName()
															, getNeededRightByAction(omAction));
			}
		} else if (action instanceof ObjectListAction) {
			if (action.getClass().getName().equals(ObjectListAction.class.getName())) {
				ObjectListAction olAction = (ObjectListAction) action;
				rolesAllowedToCallAction = rightDescriptor.getAllowedRolesForObjectRight(
						olAction.getDescriptorName()
						, ObjectRights.LISTING);
			}
		} else if (action instanceof RelationListAction) {
			if (action.getClass().getName().equals(RelationListAction.class.getName())) {
				RelationListAction rlAction = (RelationListAction) action;
				rolesAllowedToCallAction = rightDescriptor.getAllowedRolesForObjectRight(
						rlAction.getDescriptorName()
						, ObjectRights.RELATION_LISTING);
			}
		} else {
			rolesAllowedToCallAction = rightDescriptor.getAlloweRolesForAction(action.getClass().toString());
		}
		
		
		return userHasRequiredRoleVerifier.userHasAnyOfListedRoles(rolesAllowedToCallAction);
	}
	
	private ObjectRights getNeededRightByAction(ObjectManipulationAction omAction) {
		
		switch (omAction.getManipulationType()) {
		case CREATE_OR_EDIT_REQUEST:
			return ObjectRights.MANIPULATION_CREATE;
		case DELETE:
			return ObjectRights.MANIPULATION_DELETE;
		case REFRESH:
			return ObjectRights.MANIPULATION_REFRESH;
		case UNDELETE:
			return ObjectRights.MANIPULATION_UNDELETE;
		}
		return null;
	}

	
}
