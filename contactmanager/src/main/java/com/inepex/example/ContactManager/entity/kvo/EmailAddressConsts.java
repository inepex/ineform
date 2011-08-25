package com.inepex.example.ContactManager.entity.kvo;

public class EmailAddressConsts {

	public static final String descriptorName = "emailAddressDescriptor";
	public static final String searchDescriptor = "emailAddressSearchDescriptor";

	//field contsts
	public static final String k_id = "id";	
	public static final String k_email = "email";	

	public static String k_id() {
		return k_id;
	}	
	public static String k_email() {
		return k_email;
	}	

	
	//search consts
	public static final String s_id = "id";
	public static final String s_email = "email";

	public static String s_id() {
		return s_id;
	}
	public static String s_email() {
		return s_email;
	}
}