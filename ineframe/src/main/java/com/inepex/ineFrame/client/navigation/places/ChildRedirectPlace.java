package com.inepex.ineFrame.client.navigation.places;

import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.page.InePage;


public class ChildRedirectPlace extends InePlace {

	final String childToken;
	
	public ChildRedirectPlace(String childToken) {
		this.childToken = childToken;
	}
	
	public String getChildToken() {
		return childToken;
	}

	@Override
	public InePage getAssociatedPage() {
		return null;
	}

}
