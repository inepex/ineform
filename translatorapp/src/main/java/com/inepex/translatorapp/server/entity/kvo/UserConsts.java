package com.inepex.translatorapp.server.entity.kvo;

public class UserConsts {

	public static final String descriptorName = "userDescriptor";
	public static final String searchDescriptor = "userSearchDescriptor";

	//field contsts
	public static final String k_id = "id";	
	public static final String k_email = "email";	
	public static final String k_roles = "roles";	
	public static final String k_translates = "translates";	

	public static String k_id() {
		return k_id;
	}	
	public static String k_email() {
		return k_email;
	}	
	public static String k_roles() {
		return k_roles;
	}	
	public static String k_translates() {
		return k_translates;
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