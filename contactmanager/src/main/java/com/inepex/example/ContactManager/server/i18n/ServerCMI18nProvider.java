package com.inepex.example.ContactManager.server.i18n;
import com.inepex.example.ContactManager.client.i18n.CMI18n;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.google.inject.Provider;

public class ServerCMI18nProvider extends ServerI18nProvider<CMI18n> {

	private static final long serialVersionUID = 1L;

	public ServerCMI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<CMI18n> getModuleClass() {
		return CMI18n.class;
	}

	@Override
	public CMI18n getVirgineI18nModule() {
		CMI18n module = new CMI18n(this);
		initTexts(module);
		return module;
	}
	
	public void initTexts(CMI18n module){
		module.getTextMap().put("company_contacts", "Contacts");
		module.getTextMap().put("company_email", "Email");
		module.getTextMap().put("company_extdata", "Ext. data");
		module.getTextMap().put("company_id", "Id");
		module.getTextMap().put("company_name", "Name");
		module.getTextMap().put("company_phone", "Phone");
		module.getTextMap().put("company_propsUser", "Properties");
		module.getTextMap().put("company_webPage", "WebPage");
		module.getTextMap().put("contact_company", "Company");
		module.getTextMap().put("contact_email", "Email");
		module.getTextMap().put("contact_id", "Id");
		module.getTextMap().put("contact_name", "Name");
		module.getTextMap().put("contact_phone", "Phone");
		module.getTextMap().put("details", "Details");
		module.getTextMap().put("emailAddress_contact", "Contact");
		module.getTextMap().put("emailAddress_email", "Email");
		module.getTextMap().put("emailAddress_id", "Id");
		module.getTextMap().put("meetingType_INE_OFFICE", "Inepex office");
		module.getTextMap().put("meetingType_PARNER_OFFICE", "Partner's office");
		module.getTextMap().put("meetingType_TELE_CONFERENCE", "Tele conference");
		module.getTextMap().put("meetingType_VIDEO_CONFERENCE", "Video conference");
		module.getTextMap().put("meeting_company", "Company");
		module.getTextMap().put("meeting_contact", "Contact");
		module.getTextMap().put("meeting_description", "Description");
		module.getTextMap().put("meeting_id", "Id");
		module.getTextMap().put("meeting_meetingTimestamp", "When");
		module.getTextMap().put("meeting_meetingType", "MeetingType");
		module.getTextMap().put("meeting_user", "User");
		module.getTextMap().put("menu_COMPANIES", "Companies");
		module.getTextMap().put("menu_COMPCONTEDIT", "Contacts");
		module.getTextMap().put("menu_COMPDELETE", "Delete");
		module.getTextMap().put("menu_COMPDETAILS", "Details");
		module.getTextMap().put("menu_COMPEDIT", "Edit");
		module.getTextMap().put("menu_COMPNEW", "New company");
		module.getTextMap().put("menu_CONTACTDETAILS", "Contact details");
		module.getTextMap().put("menu_CONTACTS", "Contacts");
		module.getTextMap().put("menu_MEETINGDETAILS", "Meeting details");
		module.getTextMap().put("menu_MEETINGNEW", "New meeting");
		module.getTextMap().put("menu_MEETINGS", "Meetings");
		module.getTextMap().put("pageNotFound", "Page not found");
		module.getTextMap().put("phoneNumberType_id", "Id");
		module.getTextMap().put("phoneNumberType_name", "Name");
		module.getTextMap().put("phoneNumber_contact", "Contact");
		module.getTextMap().put("phoneNumber_id", "Id");
		module.getTextMap().put("phoneNumber_number", "Number");
		module.getTextMap().put("phoneNumber_type", "Type");
		module.getTextMap().put("reallyWantToDeleteCompany", "Do you really want to delete this company?");
		module.getTextMap().put("user_email", "Email");
		module.getTextMap().put("user_firstName", "FirstName");
		module.getTextMap().put("user_id", "Id");
		module.getTextMap().put("user_lastName", "LastName");
	}
}
