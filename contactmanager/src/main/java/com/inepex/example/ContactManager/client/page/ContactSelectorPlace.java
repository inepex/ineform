package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.page.InePage;

public class ContactSelectorPlace extends ParamPlace{

	private	Provider<ContactSelectorPage> contactSelectorPageProvider;
	private ContactSelectorPage contactSelectorPage;

	
	@Inject
	public ContactSelectorPlace(Provider<ContactSelectorPage> contactSelectorPageProvider) {
		this.contactSelectorPageProvider = contactSelectorPageProvider;
	}

	@Override
	public String getChildToken() {
		return AppPlaceHierarchyProvider.CONTACTDETAILS;
	}

	@Override
	public boolean notifyParamChangedReturnIsParamSet(
			Map<String, String> urlParams) {
		
		if(urlParams.get(AppPlaceHierarchyProvider.PARAM_CONTACT)==null)
			return false;
		
		try {
			Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_CONTACT));
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	@Override
	public ParamPlacePresenter getSelectorPresenter() {
		return null;
	}

	@Override
	public InePage getAssociatedPage() {
		if(contactSelectorPage==null)
			contactSelectorPage=contactSelectorPageProvider.get();
		
		return contactSelectorPage;
	}
}
