package com.inepex.example.ContactManager.entity.kvo;

public class UserConsts {

	public static final String descriptorName = "userDescriptor";
	public static final String searchDescriptor = "userSearchDescriptor";

	//field contsts
	public static final String k_id = "id";	
	public static final String k_firstName = "firstName";	
	public static final String k_lastName = "lastName";	
	public static final String k_email = "email";	

	public static String k_id() {
		return k_id;
	}	
	public static String k_firstName() {
		return k_firstName;
	}	
	public static String k_lastName() {
		return k_lastName;
	}	
	public static String k_email() {
		return k_email;
	}	

	
	//search consts
	public static final String s_id = "id";
	public static final String s_firstName = "firstName";
	public static final String s_lastName = "lastName";
	public static final String s_email = "email";

	public static String s_id() {
		return s_id;
	}
	public static String s_firstName() {
		return s_firstName;
	}
	public static String s_lastName() {
		return s_lastName;
	}
	public static String s_email() {
		return s_email;
	}
}