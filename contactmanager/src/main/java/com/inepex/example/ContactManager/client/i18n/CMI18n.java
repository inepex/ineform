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
	
	public String company_contacts = "Contacts";
	public String company_email = "Email";
	public String company_extdata = "Ext. data";
	public String company_id = "Id";
	public String company_name = "Name";
	public String company_phone = "Phone";
	public String company_propsUser = "Properties";
	public String company_webPage = "WebPage";
	public String contact_company = "Company";
	public String contact_email = "Email";
	public String contact_id = "Id";
	public String contact_name = "Name";
	public String contact_phone = "Phone";
	public String details = "Details";
	public String emailAddress_contact = "Contact";
	public String emailAddress_email = "Email";
	public String emailAddress_id = "Id";
	public String meetingType_INE_OFFICE = "Inepex office";
	public String meetingType_PARNER_OFFICE = "Partner's office";
	public String meetingType_TELE_CONFERENCE = "Tele conference";
	public String meetingType_VIDEO_CONFERENCE = "Video conference";
	public String meeting_company = "Company";
	public String meeting_contact = "Contact";
	public String meeting_description = "Description";
	public String meeting_id = "Id";
	public String meeting_meetingTimestamp = "When";
	public String meeting_meetingType = "MeetingType";
	public String meeting_user = "User";
	public String menu_COMPANIES = "Companies";
	public String menu_COMPCONTEDIT = "Contacts";
	public String menu_COMPDELETE = "Delete";
	public String menu_COMPDETAILS = "Details";
	public String menu_COMPEDIT = "Edit";
	public String menu_COMPNEW = "New company";
	public String menu_CONTACTDETAILS = "Contact details";
	public String menu_CONTACTS = "Contacts";
	public String menu_MEETINGDETAILS = "Meeting details";
	public String menu_MEETINGNEW = "New meeting";
	public String menu_MEETINGS = "Meetings";
	public String pageNotFound = "Page not found";
	public String phoneNumberType_id = "Id";
	public String phoneNumberType_name = "Name";
	public String phoneNumber_contact = "Contact";
	public String phoneNumber_id = "Id";
	public String phoneNumber_number = "Number";
	public String phoneNumber_type = "Type";
	public String reallyWantToDeleteCompany = "Do you really want to delete this company?";
	public String user_email = "Email";
	public String user_firstName = "FirstName";
	public String user_id = "Id";
	public String user_lastName = "LastName";

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contacts
	* <u><i>Magyarul:</i></u> Kapcsolattartók
	*/
	public static String company_contacts() {
		return moduleProvider.get().company_contacts;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Email
	* <u><i>Magyarul:</i></u> Email
	*/
	public static String company_email() {
		return moduleProvider.get().company_email;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ext. data
	* <u><i>Magyarul:</i></u> Egyéb adatok
	*/
	public static String company_extdata() {
		return moduleProvider.get().company_extdata;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String company_id() {
		return moduleProvider.get().company_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> Néc
	*/
	public static String company_name() {
		return moduleProvider.get().company_name;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Phone
	* <u><i>Magyarul:</i></u> Telefon
	*/
	public static String company_phone() {
		return moduleProvider.get().company_phone;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Properties
	* <u><i>Magyarul:</i></u> Adatok
	*/
	public static String company_propsUser() {
		return moduleProvider.get().company_propsUser;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> WebPage
	* <u><i>Magyarul:</i></u> Honlap
	*/
	public static String company_webPage() {
		return moduleProvider.get().company_webPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Company
	* <u><i>Magyarul:</i></u> Cég
	*/
	public static String contact_company() {
		return moduleProvider.get().contact_company;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Email
	* <u><i>Magyarul:</i></u> Email
	*/
	public static String contact_email() {
		return moduleProvider.get().contact_email;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String contact_id() {
		return moduleProvider.get().contact_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> Név
	*/
	public static String contact_name() {
		return moduleProvider.get().contact_name;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Phone
	* <u><i>Magyarul:</i></u> Telefon
	*/
	public static String contact_phone() {
		return moduleProvider.get().contact_phone;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Details
	* <u><i>Magyarul:</i></u> Részletek
	*/
	public static String details() {
		return moduleProvider.get().details;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contact
	* <u><i>Magyarul:</i></u> Kapcsolattartó
	*/
	public static String emailAddress_contact() {
		return moduleProvider.get().emailAddress_contact;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Email
	* <u><i>Magyarul:</i></u> Email
	*/
	public static String emailAddress_email() {
		return moduleProvider.get().emailAddress_email;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String emailAddress_id() {
		return moduleProvider.get().emailAddress_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Inepex office
	* <u><i>Magyarul:</i></u> Inepex iroda
	*/
	public static String meetingType_INE_OFFICE() {
		return moduleProvider.get().meetingType_INE_OFFICE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Partner's office
	* <u><i>Magyarul:</i></u> Partner iroda
	*/
	public static String meetingType_PARNER_OFFICE() {
		return moduleProvider.get().meetingType_PARNER_OFFICE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tele conference
	* <u><i>Magyarul:</i></u> Telefon konferencia
	*/
	public static String meetingType_TELE_CONFERENCE() {
		return moduleProvider.get().meetingType_TELE_CONFERENCE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Video conference
	* <u><i>Magyarul:</i></u> Video conferencia
	*/
	public static String meetingType_VIDEO_CONFERENCE() {
		return moduleProvider.get().meetingType_VIDEO_CONFERENCE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Company
	* <u><i>Magyarul:</i></u> Cég
	*/
	public static String meeting_company() {
		return moduleProvider.get().meeting_company;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contact
	* <u><i>Magyarul:</i></u> Kapcsolattartó
	*/
	public static String meeting_contact() {
		return moduleProvider.get().meeting_contact;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Description
	* <u><i>Magyarul:</i></u> Leírás
	*/
	public static String meeting_description() {
		return moduleProvider.get().meeting_description;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String meeting_id() {
		return moduleProvider.get().meeting_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> When
	* <u><i>Magyarul:</i></u> Mikor
	*/
	public static String meeting_meetingTimestamp() {
		return moduleProvider.get().meeting_meetingTimestamp;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> MeetingType
	* <u><i>Magyarul:</i></u> Típus
	*/
	public static String meeting_meetingType() {
		return moduleProvider.get().meeting_meetingType;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> User
	* <u><i>Magyarul:</i></u> Felhasználó
	*/
	public static String meeting_user() {
		return moduleProvider.get().meeting_user;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Companies
	* <u><i>Magyarul:</i></u> Cégek
	*/
	public static String menu_COMPANIES() {
		return moduleProvider.get().menu_COMPANIES;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contacts
	* <u><i>Magyarul:</i></u> Kapcsolattartók
	*/
	public static String menu_COMPCONTEDIT() {
		return moduleProvider.get().menu_COMPCONTEDIT;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Delete
	* <u><i>Magyarul:</i></u> Törlés
	*/
	public static String menu_COMPDELETE() {
		return moduleProvider.get().menu_COMPDELETE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Details
	* <u><i>Magyarul:</i></u> Részletek
	*/
	public static String menu_COMPDETAILS() {
		return moduleProvider.get().menu_COMPDETAILS;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Edit
	* <u><i>Magyarul:</i></u> Szerkesztés
	*/
	public static String menu_COMPEDIT() {
		return moduleProvider.get().menu_COMPEDIT;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New company
	* <u><i>Magyarul:</i></u> Új cég
	*/
	public static String menu_COMPNEW() {
		return moduleProvider.get().menu_COMPNEW;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contact details
	* <u><i>Magyarul:</i></u> Kapcsolattartó adatai
	*/
	public static String menu_CONTACTDETAILS() {
		return moduleProvider.get().menu_CONTACTDETAILS;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contacts
	* <u><i>Magyarul:</i></u> Kapcsolattartók
	*/
	public static String menu_CONTACTS() {
		return moduleProvider.get().menu_CONTACTS;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Meeting details
	* <u><i>Magyarul:</i></u> Találkozó részletei
	*/
	public static String menu_MEETINGDETAILS() {
		return moduleProvider.get().menu_MEETINGDETAILS;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New meeting
	* <u><i>Magyarul:</i></u> Új találkozó
	*/
	public static String menu_MEETINGNEW() {
		return moduleProvider.get().menu_MEETINGNEW;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Meetings
	* <u><i>Magyarul:</i></u> Találkozók
	*/
	public static String menu_MEETINGS() {
		return moduleProvider.get().menu_MEETINGS;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Page not found
	* <u><i>Magyarul:</i></u> Az oldal nem található
	*/
	public static String pageNotFound() {
		return moduleProvider.get().pageNotFound;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String phoneNumberType_id() {
		return moduleProvider.get().phoneNumberType_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> Név
	*/
	public static String phoneNumberType_name() {
		return moduleProvider.get().phoneNumberType_name;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Contact
	* <u><i>Magyarul:</i></u> Kapcsolattartó
	*/
	public static String phoneNumber_contact() {
		return moduleProvider.get().phoneNumber_contact;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String phoneNumber_id() {
		return moduleProvider.get().phoneNumber_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Number
	* <u><i>Magyarul:</i></u> Telefonszám
	*/
	public static String phoneNumber_number() {
		return moduleProvider.get().phoneNumber_number;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Type
	* <u><i>Magyarul:</i></u> Típus
	*/
	public static String phoneNumber_type() {
		return moduleProvider.get().phoneNumber_type;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Do you really want to delete this company?
	* <u><i>Magyarul:</i></u> Tényleg törölni akarja ezt a céget?
	*/
	public static String reallyWantToDeleteCompany() {
		return moduleProvider.get().reallyWantToDeleteCompany;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Email
	* <u><i>Magyarul:</i></u> Email
	*/
	public static String user_email() {
		return moduleProvider.get().user_email;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> FirstName
	* <u><i>Magyarul:</i></u> Keresztnév
	*/
	public static String user_firstName() {
		return moduleProvider.get().user_firstName;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Id
	* <u><i>Magyarul:</i></u> Id
	*/
	public static String user_id() {
		return moduleProvider.get().user_id;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> LastName
	* <u><i>Magyarul:</i></u> Vezetéknév
	*/
	public static String user_lastName() {
		return moduleProvider.get().user_lastName;
	}
}
