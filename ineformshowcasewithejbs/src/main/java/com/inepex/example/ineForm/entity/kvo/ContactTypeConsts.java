package com.inepex.example.ineForm.entity.kvo;

public class ContactTypeConsts {

	public static final String descriptorName = "contactTypeDescriptor";
	public static final String searchDescriptor = "contactTypeSearchDescriptor";

	//field contsts
	public static final String k_id = "id";	
	public static final String k_typeName = "typeName";	
	public static final String k_description = "description";	

	public static String k_id() {
		return k_id;
	}	
	public static String k_typeName() {
		return k_typeName;
	}	
	public static String k_description() {
		return k_description;
	}	

	
	//search consts
	public static final String s_typeName = "typeName";

	public static String s_typeName() {
		return s_typeName;
	}
}