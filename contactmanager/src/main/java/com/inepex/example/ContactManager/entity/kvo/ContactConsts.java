package com.inepex.example.ContactManager.entity.kvo;

public class ContactConsts {

	public static final String descriptorName = "contactDescriptor";
	public static final String searchDescriptor = "contactSearchDescriptor";

	//field contsts
	public static final String k_id = "id";	
	public static final String k_name = "name";	
	public static final String k_phone = "phone";	
	public static final String k_email = "email";	
	public static final String k_company = "company";	

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
	public static String k_company() {
		return k_company;
	}	

	
	//search consts
	public static final String s_id = "id";
	public static final String s_name = "name";
	public static final String s_company = "company";

	public static String s_id() {
		return s_id;
	}
	public static String s_name() {
		return s_name;
	}
	public static String s_company() {
		return s_company;
	}
}