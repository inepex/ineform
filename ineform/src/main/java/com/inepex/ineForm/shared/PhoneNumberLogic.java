package com.inepex.ineForm.shared;

public class PhoneNumberLogic {

    public final static String sample1 = "+234--232124";
    public final static String sample2 = "+23-342-2342343242";

    public final static String PART_SEPARATOR = "-";
    public final static String PLUS_SIGN = "+";

    public final static int MAX_COUNTRY_LENGTH = 3;
    public final static int MAX_AREA_LENGTH = 4;
    public final static int MAX_LOCAL_LENGTH = 10;

    public static String[] parsePhoneString(String value) {
        if (value == null || value.length() == 0)
            return null;

        String[] parts;
        if (value.matches("\\+([0-9]){11,14}")) {
            parts =
                new String[] { value.substring(0, 3), value.substring(3, 5), value.substring(5) };
        } else {
            parts = value.split("[" + PART_SEPARATOR + "]");
        }

        if (!parts[0].startsWith(PLUS_SIGN))
            return null;

        String p0 = parts[0].replace(PLUS_SIGN, "");
        if (p0.length() > MAX_COUNTRY_LENGTH)
            return null;

        String p1 = parts[1];
        if (p1.length() > MAX_AREA_LENGTH)
            return null;

        String p2 = parts[2];
        if (p2.length() > MAX_LOCAL_LENGTH)
            return null;

        try {
            return new String[] { parasbleString(p0), parasbleString(p1), parasbleString(p2) };
        } catch (Exception e) {
            return null;
        }
    }

    // Use for numbers like: 36704427522
    public static String convertSimpleToVodaStyle(String nonSeparatedSimpleFormat) {
        return PLUS_SIGN + nonSeparatedSimpleFormat;
    }

    // Use for numbers like: 36704427522
    public static String convertSimpleToIneStlye(String nonSeparatedSimpleFormat) {

        return PLUS_SIGN
            + nonSeparatedSimpleFormat.substring(0, 2)
            + PART_SEPARATOR
            + nonSeparatedSimpleFormat.substring(2, 4)
            + PART_SEPARATOR
            + nonSeparatedSimpleFormat.substring(4);
    }

    private static String parasbleString(String str) {
        if (str.length() == 0)
            return null;

        Long.parseLong(str);

        return str;
    }
}
