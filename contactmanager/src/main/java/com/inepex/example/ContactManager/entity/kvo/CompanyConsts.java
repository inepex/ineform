package com.inepex.example.ContactManager.entity.kvo;

public class CompanyConsts {

	public static final String descriptorName = "companyDescriptor";
	public static final String searchDescriptor = "companySearchDescriptor";

	//field contsts
	public static final String k_id = "id";	
	public static final String k_name = "name";	
	public static final String k_phone = "phone";	
	public static final String k_email = "email";	
	public static final String k_webPage = "webPage";	
	public static final String k_contacts = "contacts";	
	public static final String k_extData = "extData";	
	public static final String k_propsUser = "propsUser";
	public static final String propUser = "user";
	
	public static String k_id() {
		return k_id;
	}	
	public static String k_name() {
		return k_name;
	}	
	public static String k_phone() {
		return k_phone;
	}	
	public static String k_email() {
		return k_email;
	}	
	public static String k_webPage() {
		return k_webPage;
	}	
	public static String k_contacts() {
		return k_contacts;
	}	
	public static String k_extData() {
		return k_extData;
	}	

	
	//search consts
	public static final String s_id = "id";
	public static final String s_name = "name";
	public static final String s_phone = "phone";
	public static final String s_email = "email";
	public static final String s_webPage = "webPage";

	public static String s_id() {
		return s_id;
	}
	public static String s_name() {
		return s_name;
	}
	public static String s_phone() {
		return s_phone;
	}
	public static String s_email() {
		return s_email;
	}
	public static String s_webPage() {
		return s_webPage;
	}
}