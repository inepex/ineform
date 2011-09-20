package com.inepex.example.ineForm.entity.kvo;

public class Contact_ContactStateConsts {

	public static final String descriptorName = "contact_ContactStateDescriptor";
	public static final String searchDescriptor = "contact_ContactStateSearchDescriptor";

	//field contsts
	public static final String k_id = "id";	
	public static final String k_state = "state";	
	public static final String k_orderNum = "orderNum";	

	public static String k_id() {
		return k_id;
	}	
	public static String k_state() {
		return k_state;
	}	
	public static String k_orderNum() {
		return k_orderNum;
	}	

	
	//search consts
	public static final String s_state = "state";

	public static String s_state() {
		return s_state;
	}
}