package com.inepex.ineFrame.server.auth;

import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.LISTING;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.MANIPULATION_CREATE;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.MANIPULATION_DELETE;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.MANIPULATION_REFRESH;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.MANIPULATION_UNDELETE;
import static com.inepex.ineFrame.server.auth.RightDescriptor.ObjectRights.RELATION_LISTING;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.customware.gwt.dispatch.shared.Action;

/**
 * This class provides a convenient way for associating rights on IneForm handled domain objects.
 * For more details see <a href="http://code.google.com/p/ineform/wiki/PermissionHandlig">PermissionHandling</a>
 * @author istvan
 *
 */
public class RightDescriptor {
	
	/**
	 * Enum defining rights that can be granted for domain object usage.
	 * There are two type of elements in this listing:<br/>
	 *  - aggregator rights (like ALL): convenience right for granting more rights with one line <br/>
	 *  - concrete rights: right that can be queried for<br/>
	 *  
	 * @author istvan
	 *
	 */
	public static enum ObjectRights {
		/**
		 * Aggregating right, can not be used for query jet 
		 */
		ALL,
		/**
		 * Aggregating right, can not be used for query jet 
		 */		
		ALL_LISTING,
		/**
		 * Aggregating right, can not be used for query jet 
		 */
		 ALL_MANIPULATION,

		LISTING,
		RELATION_LISTING,
		MANIPULATION_CREATE,
		MANIPULATION_REFRESH,
		MANIPULATION_DELETE,
		MANIPULATION_UNDELETE;		
	}
	
	public class RightFragment {
		final String descName;
		
		RightFragment(String descriptorName) {
			this.descName = descriptorName;
		}
		

		/**
		 * Aggregator rights (see {@link ObjectRights}) can also be used here
		 * @param right
		 * @param roles
		 * @return
		 */
		public RightFragment grantRight(String role, ObjectRights... rights) {
			for (ObjectRights right : rights) {
				for (ObjectRights implicitRight : getImplicitRights(right)) {
					ensureGrantedObjectRights(descName, implicitRight);
					allowedRolesForRightByObject.get(descName).get(implicitRight).add(role);
				}			
			} 
			return this;
		}
		
		/**
		 * Aggregator rights (see {@link ObjectRights}) can also be used here
		 * @param right
		 * @param roles
		 * @return
		 */
		public RightFragment denyRight(String role, ObjectRights... rights) {
			for (ObjectRights right : rights) {
				for (ObjectRights implicitRight : getImplicitRights(right)) {
					ensureDeniedObjectRights(descName, implicitRight);
					deniedRolesForRightByObject.get(descName).get(implicitRight).add(role);
				}				
			} 

			return this;
		}
		
		/**
		 * Aggregator rights (see {@link ObjectRights}) can also be used here
		 * @param right
		 * @param roles
		 * @return
		 */
		public RightFragment grantRight(ObjectRights right, String... roles) {
			for (ObjectRights implicitRight : getImplicitRights(right)) {
				ensureGrantedObjectRights(descName, implicitRight);
				for (String role : roles) {
					allowedRolesForRightByObject.get(descName).get(implicitRight).add(role);
				}			
			} 
			return this;
		}
		
		/**
		 * Aggregator rights (see {@link ObjectRights}) can also be used here
		 * @param right
		 * @param roles
		 * @return
		 */
		public RightFragment denyRight(ObjectRights right, String... roles) {
			for (ObjectRights implicitRight : getImplicitRights(right)) {
				ensureDeniedObjectRights(descName, implicitRight);
				for (String role : roles) {
					deniedRolesForRightByObject.get(descName).get(implicitRight).add(role);
				}				
			} 

			return this;
		}
		
		public List<ObjectRights> getImplicitRights(ObjectRights right) {
			List<ObjectRights> implicitRights = new ArrayList<RightDescriptor.ObjectRights>();
			switch (right) {
			case ALL:
				implicitRights.add(LISTING);
				implicitRights.add(RELATION_LISTING);
				implicitRights.add(MANIPULATION_CREATE);
				implicitRights.add(MANIPULATION_REFRESH);
				implicitRights.add(MANIPULATION_DELETE);
				implicitRights.add(MANIPULATION_UNDELETE);
				break;
			case ALL_LISTING:
				implicitRights.add(LISTING);
				implicitRights.add(RELATION_LISTING);
				break;
			case ALL_MANIPULATION:
				implicitRights.add(MANIPULATION_CREATE);
				implicitRights.add(MANIPULATION_REFRESH);
				implicitRights.add(MANIPULATION_DELETE);
				implicitRights.add(MANIPULATION_UNDELETE);
				break;
			default:
				implicitRights.add(right);
				break;
			}
			return implicitRights;
		}
	}
	
	private final String adminRole;

	/**
	 * First key is ObjectDescriptorName, second is ObjectRight. 
	 */
	Map<String, Map<ObjectRights, Set<String>>> allowedRolesForRightByObject 
		= new TreeMap<String, Map<ObjectRights, Set<String>>>();
	
	/**
	 * First key is ObjectDescriptorName, second is ObjectRight. 
	 */
	Map<String, Map<ObjectRights, Set<String>>> deniedRolesForRightByObject 
	= new TreeMap<String, Map<ObjectRights, Set<String>>>();
	
	/**
	 * Lists which roles are allowed to call the action
	 */
	Map<String, Set<String>> allowedRolesByActionClassName 
			= new TreeMap<String, Set<String>>();
	
	/**
	 * Lists which roles are denied to call the action
	 */
	Map<String, Set<String>> deniedRolesByActionClassName 
			= new TreeMap<String, Set<String>>();
	
	public RightDescriptor(String adminRole) {
		this.adminRole = adminRole;
	}
	
	public RightDescriptor add(RightFragment fragment) {
		return this;
	}
	
	public RightFragment newRightFramgent(String descName) {
		return new RightFragment(descName);
	}
	
	public RightDescriptor allowRolesOnAction(Class<? extends Action<?>> actionClass, String... roles) {
		ensureGrantedActions(actionClass.toString());
		for (String role : roles) {
			allowedRolesByActionClassName.get(actionClass.toString()).add(role);
		}
		return this;
	}

	public RightDescriptor denyRolesOnAction(Class<? extends Action<?>> actionClass, String... roles) {
		ensureDeniedActions(actionClass.toString());
		for (String role : roles) {
			allowedRolesByActionClassName.get(actionClass.toString()).add(role);
		}		
		return this;
	}
	
	public Set<String> getAllowedRolesForObjectRight(String descName, ObjectRights right) {
		Set<String> allowedRoles = new HashSet<String>();

		switch (right) {
		case ALL:
		case ALL_LISTING:
		case ALL_MANIPULATION:
			throw new RuntimeException("You can't check right 'ALL', 'ALL_LISTING' and 'ALL_MANIPULATION'. These are only convenience types");
		}
		
		// adminRole is by default granted!
		allowedRoles.add(adminRole);
		allowedRoles.addAll(getAllowedRolesSafe(descName, right));
		allowedRoles.removeAll(getDeniedRolesSafe(descName, right));
		
		return allowedRoles;
	}
	
	private Set<String> getAllowedRolesSafe(String descName, ObjectRights right) {
		
		if (!allowedRolesForRightByObject.containsKey(descName))
			return new HashSet<String>();
		
		Set<String> result = allowedRolesForRightByObject.get(descName).get(right);
		if (result == null)
			return new HashSet<String>();
		
		return result;
	}
	
	private Set<String> getDeniedRolesSafe(String descName, ObjectRights right) {
		
		if (!deniedRolesForRightByObject.containsKey(descName))
			return new HashSet<String>();
		
		Set<String> result = deniedRolesForRightByObject.get(descName).get(right);
		if (result == null)
			return new HashSet<String>();
		
		return result;
	}
	
	public Set<String> getAlloweRolesForAction(String actionClass) {
		Set<String> allowedRoles = new HashSet<String>();
		
		// adminRole is by default granted!
		allowedRoles.add(adminRole);
		allowedRoles.addAll(allowedRolesByActionClassName.containsKey(actionClass) ?
				allowedRolesByActionClassName.get(actionClass) :
							new HashSet<String>());
		allowedRoles.removeAll(deniedRolesByActionClassName.containsKey(actionClass) ?
				deniedRolesByActionClassName.get(actionClass) :
							   new HashSet<String>());
		return allowedRoles;
	}
	
	
	private void ensureGrantedObjectRights(String descName, ObjectRights right) {
		if (!allowedRolesForRightByObject.containsKey(descName))
			allowedRolesForRightByObject.put(descName, new TreeMap<ObjectRights, Set<String>>());
		
		if (!allowedRolesForRightByObject.get(descName).containsKey(right))
			allowedRolesForRightByObject.get(descName).put(right, new TreeSet<String>());
	}
	
	private void ensureDeniedObjectRights(String descName, ObjectRights right) {
		if (!deniedRolesForRightByObject.containsKey(descName))
			deniedRolesForRightByObject.put(descName, new TreeMap<ObjectRights, Set<String>>());
		
		if (!deniedRolesForRightByObject.get(descName).containsKey(right))
			deniedRolesForRightByObject.get(descName).put(right, new TreeSet<String>());
	}
	
	private void ensureGrantedActions(String actionClass) {
		if (!allowedRolesByActionClassName.containsKey(actionClass))
			allowedRolesByActionClassName.put(actionClass, new TreeSet<String>());
	}
	
	private void ensureDeniedActions(String actionClass) {
		if (!deniedRolesByActionClassName.containsKey(actionClass))
			deniedRolesByActionClassName.put(actionClass, new TreeSet<String>());
	}
	
}
