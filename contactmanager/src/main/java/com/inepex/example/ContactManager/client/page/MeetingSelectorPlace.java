package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.page.InePage;

public class MeetingSelectorPlace extends ParamPlace{
	
	private	Provider<MeetingSelectorPage> meetingSelectorPageProvider;
	
	private MeetingSelectorPage meetingSelectorPage;
	
	@Inject
	public MeetingSelectorPlace(Provider<MeetingSelectorPage> meetingSelectorPageProvider) {
		this.meetingSelectorPageProvider = meetingSelectorPageProvider;
	}

	@Override
	public String getChildToken() {
		return AppPlaceHierarchyProvider.MEETINGDETAILS;
	}

	@Override
	public boolean notifyParamChangedReturnIsParamSet(
			Map<String, String> urlParams) {
		
		if(urlParams.get(AppPlaceHierarchyProvider.PARAM_MEETING)==null)
			return false;
		
		try {
			Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_MEETING));
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
		if(meetingSelectorPage==null)
			meetingSelectorPage=meetingSelectorPageProvider.get();
		
		return meetingSelectorPage;
	}
}
