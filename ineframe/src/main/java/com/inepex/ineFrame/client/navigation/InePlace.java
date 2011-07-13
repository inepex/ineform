package com.inepex.ineFrame.client.navigation;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineFrame.client.page.InePage;

/**
 * Place Object is responsible for binding a page to a place in the navigation hierarchy.
 * Place itself does not contain the token needed to access it, because the menu hierarchy
 * should be built using the {@link Node} class. In the node object the nodeId determines the
 * URL part that belongs to this page.
 * 
 * @author istvanszoboszlai
 *
 */
public abstract class InePlace {
	
	private String menuName = null;
	private boolean renderOnRightSide = false;

	private RequiresAuthentication requiresAuthentication = RequiresAuthentication.FALSE;
	private List<String> rolesNeeded = null;
	
	private boolean onlyVisibleWhenActive = false;
	private boolean showChildreWhenActive = false;
	
	public abstract InePage getAssociatedPage();
	
	private String hierarchicalToken;
		
	public InePlace(){
		
	}

	public InePlace(RequiresAuthentication requiresAuthentication) {
		this.requiresAuthentication = requiresAuthentication;
	}

	public InePlace(RequiresAuthentication requiresAuthentication, String menuName) {
		this(requiresAuthentication);
		this.menuName = menuName;
	}
	
	public InePlace addAllowedRoles(String... roles) {
		if (rolesNeeded == null)
			rolesNeeded = new ArrayList<String>(3);
		
		for (String role : roles) {
			rolesNeeded.add(role);
		}
		
		return this;
	}
	
	public boolean isMenuVisible() {
		return menuName != null;
	}
	
	public String getMenuName() {
		return menuName;
	}
	
	public InePlace setMenuName(String menuName) {
		this.menuName = menuName;
		return this;
	}
	
	public boolean isAuthenticationNeeded() {
		return requiresAuthentication == RequiresAuthentication.TRUE;
	}
	
	public RequiresAuthentication getRequiresAuthentication() {
		return requiresAuthentication;
	}

	public void setRequiresAuthentication(RequiresAuthentication requiresAuthentication) {
		this.requiresAuthentication = requiresAuthentication;
	}
	
	public List<String> getRolesAllowed() {
		return rolesNeeded;
	}
	
	public String[] getRolesAllowedInArray() {
		if(rolesNeeded==null)
			return new String[]{};
		
		String[] aR = new String[rolesNeeded.size()];
		rolesNeeded.toArray(aR);
		return aR;
	}

	public void setRolesNeeded(List<String> rolesNeeded) {
		this.rolesNeeded = rolesNeeded;
	}

	public boolean isRenderOnRightSide() {
		return renderOnRightSide;
	}

	public InePlace setRenderOnRightSide(boolean renderOnRightSide) {
		this.renderOnRightSide = renderOnRightSide;
		return this;
	}

	public InePlace setOnlyVisibleWhenActive(boolean onlyVisibleWhenActive) {
		this.onlyVisibleWhenActive = onlyVisibleWhenActive;
		return this;
	}

	public boolean isOnlyVisibleWhenActive() {
		return onlyVisibleWhenActive;
	}

	public void setHierarchicalToken(String hierarchicalToken) {
		this.hierarchicalToken = hierarchicalToken;
	}

	/**
	 * WARNING!!!
	 * 
	 * do not cache it, it changes by place requests
	 * 
	 */
	public String getHierarchicalToken() {
		return hierarchicalToken;
	}
	
	public InePlace putRight() {
		setRenderOnRightSide(true);
		return this;
	}
	
	public InePlace setShowChildrenWhenActive(boolean showChildreWhenActive) {
		this.showChildreWhenActive = showChildreWhenActive;
		return this;
	}
	
	public boolean isShowChildreWhenActive() {
		return showChildreWhenActive;
	}
	
}
