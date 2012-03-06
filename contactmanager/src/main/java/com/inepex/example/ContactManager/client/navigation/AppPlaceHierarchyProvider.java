package com.inepex.example.ContactManager.client.navigation;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.client.page.CompanyContactEditPage;
import com.inepex.example.ContactManager.client.page.CompanyDeletePage;
import com.inepex.example.ContactManager.client.page.CompanyDetailsPage;
import com.inepex.example.ContactManager.client.page.CompanyEditPage;
import com.inepex.example.ContactManager.client.page.CompanyWidgetPlace;
import com.inepex.example.ContactManager.client.page.ContactDetailsPage;
import com.inepex.example.ContactManager.client.page.ContactSelectorPage;
import com.inepex.example.ContactManager.client.page.ContactWidgetPlace;
import com.inepex.example.ContactManager.client.page.LoginPage;
import com.inepex.example.ContactManager.client.page.MeetingDetailsPage;
import com.inepex.example.ContactManager.client.page.MeetingSelectorPage;
import com.inepex.example.ContactManager.client.page.MeetingWidgetPlace;
import com.inepex.example.ContactManager.client.page.NewCompanyPage;
import com.inepex.example.ContactManager.client.page.NewMeetingPage;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.ineForm.client.places.DefaultOneParamPresenter;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.RequiresAuthentication;
import com.inepex.ineFrame.client.navigation.places.ChildRedirectPlace;
import com.inepex.ineFrame.client.navigation.places.OneParamPlace;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineom.shared.util.SharedUtil;

@Singleton
public class AppPlaceHierarchyProvider extends DefaultPlaceHierarchyProvider {
	
	public final static String PARAM_COMPANY="compId";
	public final static String PARAM_MEETING="meetingId";
	public final static String PARAM_CONTACT = "contactId";
	
	public final static String LOGIN="login";
	public final static String LOGGEDIN="loggedin";
	
	public final static String MEETINGNEW="meetingNew";
	public final static String MEETINGS="meetings";
	public final static String MEETINGDETAILS="meetingDetails";
	
	public final static String COMPANIES="companies";
	public final static String COMPDETAILS="compDetails";
	public final static String COMPNEW="compNew";
	public final static String COMPEDIT="compEdit";
	public final static String COMPCONTEDIT="compContEdit";
	public final static String COMPDELETE="compDelete";
	
	public static final String CONTACTS = "contacts";
	public static final String CONTACTDETAILS = "contactDetails";

	@Inject AuthManager authManager;
	
	@Inject Provider<LoginPage> loginProvider;
	
	@Inject CompanyWidgetPlace companyWidgetPlace;
	@Inject MeetingWidgetPlace meetingWidgetPlace;
	
	@Inject Provider<DefaultOneParamPresenter> oneParamPresenter;
	@Inject Provider<NewCompanyPage> companyNewProvider;	
	@Inject Provider<CompanyDetailsPage> companyDetials;
	@Inject Provider<CompanyEditPage> companyEdit;
	@Inject Provider<CompanyContactEditPage> companyContactEditProvider;
	@Inject Provider<CompanyDeletePage> companyDelete;
	@Inject Provider<MeetingSelectorPage> meetingSelectorPage;
	@Inject Provider<ContactSelectorPage> contactSelectorPage;
	
	@Inject Provider<NewMeetingPage> meetingNewProvider;
	@Inject Provider<MeetingDetailsPage> meetingDetailsProvider;
	
	@Inject ContactWidgetPlace contactWidgetPlace;
	@Inject Provider<ContactDetailsPage> contactDetailsProvider;
	
	@Override
	public void createPlaceHierarchy() {
		
		placeRoot.addChild(LOGIN, new SimpleCachingPlace(loginProvider))
				 .addChildGC(LOGGEDIN, usr(new ChildRedirectPlace(COMPANIES)))
					.addChildGC(COMPANIES, usr(new OneParamPlace(COMPANIES, CompanyConsts.descriptorName, PARAM_COMPANY, 
							COMPDETAILS, true, oneParamPresenter)
						.setMenuName(CMI18n.menu_COMPANIES())))
						.addChild(companyWidgetPlace)
						.addChild(COMPDETAILS, usr(new SimpleCachingPlace(companyDetials))
							.setMenuName(CMI18n.menu_COMPDETAILS()))
						.addChild(COMPEDIT, usr(new SimpleCachingPlace(companyEdit))
							.setMenuName(CMI18n.menu_COMPEDIT()))
						.addChild(COMPCONTEDIT, usr(new SimpleCachingPlace(companyContactEditProvider))
							.setMenuName(CMI18n.menu_COMPCONTEDIT()))	
						.addChild(COMPDELETE, usr(new SimpleCachingPlace(companyDelete))
							.setMenuName(CMI18n.menu_COMPDELETE()))
						.getParent()
					.addChildGC(CONTACTS, usr(new OneParamPlace(CONTACTS, ContactConsts.descriptorName, PARAM_CONTACT, 
							CONTACTDETAILS, true, contactSelectorPage)
						.setMenuName(CMI18n.menu_CONTACTS())))
						.addChild(contactWidgetPlace)
						.addChild(CONTACTDETAILS, usr(new SimpleCachingPlace(contactDetailsProvider))
							.setMenuName(CMI18n.menu_CONTACTDETAILS()))
						.getParent()
					.addChildGC(MEETINGS, usr(new OneParamPlace(MEETINGS, MeetingConsts.descriptorName, PARAM_MEETING, 
							MEETINGDETAILS, false, meetingSelectorPage)
							.setMenuName(CMI18n.menu_MEETINGS())))
							.addChild(meetingWidgetPlace)
							.addChild(MEETINGDETAILS, usr(new SimpleCachingPlace(meetingDetailsProvider))
								.setMenuName(CMI18n.menu_MEETINGDETAILS()))
							.getParent()
							
					.addChild(MEETINGNEW, usr(new SimpleCachingPlace(meetingNewProvider))
							.setMenuName(CMI18n.menu_MEETINGNEW()).putRight())
					.addChild(COMPNEW, usr(new SimpleCachingPlace(companyNewProvider))
				 			.setMenuName(CMI18n.menu_COMPNEW()).putRight())
				.getParent()
				 ;
	}
	
	@Override
	public List<String> getCurrentMenuRoot() {
		if(authManager.isUserLoggedIn())
			return SharedUtil.Li(LOGGEDIN);
		else
			return null;
	}
	
	private static <E extends InePlace> E usr(E place) {
		place.setRequiresAuthentication(RequiresAuthentication.TRUE);
		return place;
	}
}

