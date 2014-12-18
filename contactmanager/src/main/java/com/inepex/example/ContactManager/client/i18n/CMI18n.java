package com.inepex.example.ContactManager.client.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class CMI18n extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "CMI18n";
	
	static I18nModuleProvider<CMI18n> moduleProvider;
	
	public CMI18n() {
		super(MODULE_NAME);
	}
		
	public CMI18n(I18nModuleProvider<CMI18n> moduleProvider) {
		super(MODULE_NAME);
		CMI18n.moduleProvider = moduleProvider;
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contacts
	* <u><i>Magyarul:</i></u> Kapcsolattartók
	*/
	public static String company_contacts() {
		return moduleProvider.get().getText("company_contacts");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Email
	* <u><i>Magyarul:</i></u> Email
	*/
	public static String company_email() {
		return moduleProvider.get().getText("company_email");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ext. data
	* <u><i>Magyarul:</i></u> Egyéb adatok
	*/
	public static String company_extdata() {
		return moduleProvider.get().getText("company_extdata");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String company_id() {
		return moduleProvider.get().getText("company_id");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> Néc
	*/
	public static String company_name() {
		return moduleProvider.get().getText("company_name");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Phone
	* <u><i>Magyarul:</i></u> Telefon
	*/
	public static String company_phone() {
		return moduleProvider.get().getText("company_phone");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Properties
	* <u><i>Magyarul:</i></u> Adatok
	*/
	public static String company_propsUser() {
		return moduleProvider.get().getText("company_propsUser");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> WebPage
	* <u><i>Magyarul:</i></u> Honlap
	*/
	public static String company_webPage() {
		return moduleProvider.get().getText("company_webPage");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Company
	* <u><i>Magyarul:</i></u> Cég
	*/
	public static String contact_company() {
		return moduleProvider.get().getText("contact_company");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Email
	* <u><i>Magyarul:</i></u> Email
	*/
	public static String contact_email() {
		return moduleProvider.get().getText("contact_email");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String contact_id() {
		return moduleProvider.get().getText("contact_id");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> Név
	*/
	public static String contact_name() {
		return moduleProvider.get().getText("contact_name");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Phone
	* <u><i>Magyarul:</i></u> Telefon
	*/
	public static String contact_phone() {
		return moduleProvider.get().getText("contact_phone");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Details
	* <u><i>Magyarul:</i></u> Részletek
	*/
	public static String details() {
		return moduleProvider.get().getText("details");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contact
	* <u><i>Magyarul:</i></u> Kapcsolattartó
	*/
	public static String emailAddress_contact() {
		return moduleProvider.get().getText("emailAddress_contact");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Email
	* <u><i>Magyarul:</i></u> Email
	*/
	public static String emailAddress_email() {
		return moduleProvider.get().getText("emailAddress_email");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String emailAddress_id() {
		return moduleProvider.get().getText("emailAddress_id");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Inepex office
	* <u><i>Magyarul:</i></u> Inepex iroda
	*/
	public static String meetingType_INE_OFFICE() {
		return moduleProvider.get().getText("meetingType_INE_OFFICE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Partner's office
	* <u><i>Magyarul:</i></u> Partner iroda
	*/
	public static String meetingType_PARNER_OFFICE() {
		return moduleProvider.get().getText("meetingType_PARNER_OFFICE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tele conference
	* <u><i>Magyarul:</i></u> Telefon konferencia
	*/
	public static String meetingType_TELE_CONFERENCE() {
		return moduleProvider.get().getText("meetingType_TELE_CONFERENCE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Video conference
	* <u><i>Magyarul:</i></u> Video conferencia
	*/
	public static String meetingType_VIDEO_CONFERENCE() {
		return moduleProvider.get().getText("meetingType_VIDEO_CONFERENCE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Company
	* <u><i>Magyarul:</i></u> Cég
	*/
	public static String meeting_company() {
		return moduleProvider.get().getText("meeting_company");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contact
	* <u><i>Magyarul:</i></u> Kapcsolattartó
	*/
	public static String meeting_contact() {
		return moduleProvider.get().getText("meeting_contact");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Description
	* <u><i>Magyarul:</i></u> Leírás
	*/
	public static String meeting_description() {
		return moduleProvider.get().getText("meeting_description");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String meeting_id() {
		return moduleProvider.get().getText("meeting_id");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> When
	* <u><i>Magyarul:</i></u> Mikor
	*/
	public static String meeting_meetingTimestamp() {
		return moduleProvider.get().getText("meeting_meetingTimestamp");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> MeetingType
	* <u><i>Magyarul:</i></u> Típus
	*/
	public static String meeting_meetingType() {
		return moduleProvider.get().getText("meeting_meetingType");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User
	* <u><i>Magyarul:</i></u> Felhasználó
	*/
	public static String meeting_user() {
		return moduleProvider.get().getText("meeting_user");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Companies
	* <u><i>Magyarul:</i></u> Cégek
	*/
	public static String menu_COMPANIES() {
		return moduleProvider.get().getText("menu_COMPANIES");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contacts
	* <u><i>Magyarul:</i></u> Kapcsolattartók
	*/
	public static String menu_COMPCONTEDIT() {
		return moduleProvider.get().getText("menu_COMPCONTEDIT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Delete
	* <u><i>Magyarul:</i></u> Törlés
	*/
	public static String menu_COMPDELETE() {
		return moduleProvider.get().getText("menu_COMPDELETE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Details
	* <u><i>Magyarul:</i></u> Részletek
	*/
	public static String menu_COMPDETAILS() {
		return moduleProvider.get().getText("menu_COMPDETAILS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Edit
	* <u><i>Magyarul:</i></u> Szerkesztés
	*/
	public static String menu_COMPEDIT() {
		return moduleProvider.get().getText("menu_COMPEDIT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New company
	* <u><i>Magyarul:</i></u> Új cég
	*/
	public static String menu_COMPNEW() {
		return moduleProvider.get().getText("menu_COMPNEW");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contact details
	* <u><i>Magyarul:</i></u> Kapcsolattartó adatai
	*/
	public static String menu_CONTACTDETAILS() {
		return moduleProvider.get().getText("menu_CONTACTDETAILS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contacts
	* <u><i>Magyarul:</i></u> Kapcsolattartók
	*/
	public static String menu_CONTACTS() {
		return moduleProvider.get().getText("menu_CONTACTS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Meeting details
	* <u><i>Magyarul:</i></u> Találkozó részletei
	*/
	public static String menu_MEETINGDETAILS() {
		return moduleProvider.get().getText("menu_MEETINGDETAILS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New meeting
	* <u><i>Magyarul:</i></u> Új találkozó
	*/
	public static String menu_MEETINGNEW() {
		return moduleProvider.get().getText("menu_MEETINGNEW");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Meetings
	* <u><i>Magyarul:</i></u> Találkozók
	*/
	public static String menu_MEETINGS() {
		return moduleProvider.get().getText("menu_MEETINGS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Page not found
	* <u><i>Magyarul:</i></u> Az oldal nem található
	*/
	public static String pageNotFound() {
		return moduleProvider.get().getText("pageNotFound");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String phoneNumberType_id() {
		return moduleProvider.get().getText("phoneNumberType_id");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> Név
	*/
	public static String phoneNumberType_name() {
		return moduleProvider.get().getText("phoneNumberType_name");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contact
	* <u><i>Magyarul:</i></u> Kapcsolattartó
	*/
	public static String phoneNumber_contact() {
		return moduleProvider.get().getText("phoneNumber_contact");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String phoneNumber_id() {
		return moduleProvider.get().getText("phoneNumber_id");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Number
	* <u><i>Magyarul:</i></u> Telefonszám
	*/
	public static String phoneNumber_number() {
		return moduleProvider.get().getText("phoneNumber_number");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Type
	* <u><i>Magyarul:</i></u> Típus
	*/
	public static String phoneNumber_type() {
		return moduleProvider.get().getText("phoneNumber_type");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Do you really want to delete this company?
	* <u><i>Magyarul:</i></u> Tényleg törölni akarja ezt a céget?
	*/
	public static String reallyWantToDeleteCompany() {
		return moduleProvider.get().getText("reallyWantToDeleteCompany");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Email
	* <u><i>Magyarul:</i></u> Email
	*/
	public static String user_email() {
		return moduleProvider.get().getText("user_email");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> FirstName
	* <u><i>Magyarul:</i></u> Keresztnév
	*/
	public static String user_firstName() {
		return moduleProvider.get().getText("user_firstName");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String user_id() {
		return moduleProvider.get().getText("user_id");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> LastName
	* <u><i>Magyarul:</i></u> Vezetéknév
	*/
	public static String user_lastName() {
		return moduleProvider.get().getText("user_lastName");
	}
}
