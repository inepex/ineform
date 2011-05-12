package com.inepex.ineFrame.client.navigation;

import com.inepex.ineFrame.client.page.InePage;

public abstract class CachablePagePlace<W extends InePage> extends InePlace{
		
	private W page;
	protected boolean doCaching = true;

	public CachablePagePlace() {
	}
	
	public CachablePagePlace(boolean doCaching) {
		this.doCaching = doCaching;
	}
	
	public CachablePagePlace(RequiresAuthentication requiresAuthentication) {
		super(requiresAuthentication);
	}

	public CachablePagePlace(RequiresAuthentication requiresAuthentication, String menuName) {
		super(requiresAuthentication, menuName);
	}
	
	public abstract W createPage();

	@Override
	public InePage getAssociatedPage() {
		if (!doCaching)
			return createPage();
		
		if (page == null)
			page = createPage();
		return page;	}
	
}
