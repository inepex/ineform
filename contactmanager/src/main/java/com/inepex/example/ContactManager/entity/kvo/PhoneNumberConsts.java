package com.inepex.example.ContactManager.entity.kvo;

public class PhoneNumberConsts {

    public static final String descriptorName = "phoneNumberDescriptor";
    public static final String searchDescriptor = "phoneNumberSearchDescriptor";

    // field contsts
    public static final String k_id = "id";
    public static final String k_number = "number";
    public static final String k_type = "type";

    public static String k_id() {
        return k_id;
    }

    public static String k_number() {
        return k_number;
    }

    public static String k_type() {
        return k_type;
    }

    // search consts
    public static final String s_id = "id";
    public static final String s_number = "number";
    public static final String s_type = "type";

    public static String s_id() {
        return s_id;
    }

    public static String s_number() {
        return s_number;
    }

    public static String s_type() {
        return s_type;
    }
}