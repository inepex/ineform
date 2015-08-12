package com.inepex.example.ContactManager.entity.kvo;

public class PhoneNumberTypeConsts {

    public static final String descriptorName = "phoneNumberTypeDescriptor";
    public static final String searchDescriptor = "phoneNumberTypeSearchDescriptor";

    // field contsts
    public static final String k_id = "id";
    public static final String k_name = "name";

    public static String k_id() {
        return k_id;
    }

    public static String k_name() {
        return k_name;
    }

    // search consts
    public static final String s_id = "id";
    public static final String s_name = "name";

    public static String s_id() {
        return s_id;
    }

    public static String s_name() {
        return s_name;
    }
}