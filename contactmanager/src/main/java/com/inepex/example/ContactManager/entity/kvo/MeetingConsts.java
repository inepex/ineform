package com.inepex.example.ContactManager.entity.kvo;

public class MeetingConsts {

	public static final String descriptorName = "meetingDescriptor";
	public static final String searchDescriptor = "meetingSearchDescriptor";

	//field contsts
	public static final String k_id = "id";	
	public static final String k_meetingTimestamp = "meetingTimestamp";	
	public static final String k_user = "user";	
	public static final String k_company = "company";	
	public static final String k_contact = "contact";	
	public static final String k_meetingType = "meetingType";	
	public static final String k_description = "description";	

	public static String k_id() {
		return k_id;
	}	
	public static String k_meetingTimestamp() {
		return k_meetingTimestamp;
	}	
	public static String k_user() {
		return k_user;
	}	
	public static String k_company() {
		return k_company;
	}	
	public static String k_contact() {
		return k_contact;
	}	
	public static String k_meetingType() {
		return k_meetingType;
	}	
	public static String k_description() {
		return k_description;
	}	

	
	//search consts
	public static final String s_id = "id";
	public static final String s_meetingTimestamp = "meetingTimestamp";
	public static final String s_user = "user";
	public static final String s_company = "company";
	public static final String s_contact = "contact";
	public static final String s_meetingType = "meetingType";

	public static String s_id() {
		return s_id;
	}
	public static String s_meetingTimestamp() {
		return s_meetingTimestamp;
	}
	public static String s_user() {
		return s_user;
	}
	public static String s_company() {
		return s_company;
	}
	public static String s_contact() {
		return s_contact;
	}
	public static String s_meetingType() {
		return s_meetingType;
	}
}