package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.page.InePage;

public class CompanySelectorPlace extends ParamPlace{

	private	Provider<CompanySelectorPage> companySelectorPageProvider;
	private CompanySelectorPage companySelectorPage;

	
	@Inject
	public CompanySelectorPlace(Provider<CompanySelectorPage> companySelectorPageProvider) {
		this.companySelectorPageProvider = companySelectorPageProvider;
	}

	@Override
	public String getChildToken() {
		return AppPlaceHierarchyProvider.COMPDETAILS;
	}

	@Override
	public boolean notifyParamChangedReturnIsParamSet(
			Map<String, String> urlParams) {
		
		if(urlParams.get(AppPlaceHierarchyProvider.PARAM_COMPANY)==null)
			return false;
		
		try {
			Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_COMPANY));
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
		if(companySelectorPage==null)
			companySelectorPage=companySelectorPageProvider.get();
		
		return companySelectorPage;
	}
}
