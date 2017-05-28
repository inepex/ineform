package com.inepex.ineom.shared.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SharedUtil {

    public static final String ID_PART_SEPARATOR = ".";

    public static List<String> Li(String... strings) {
        return Arrays.asList(strings);
    }

    public static boolean isValidIP(String ip, boolean acceptEmpty) {
        if (ip == null || ip.isEmpty()) {
            return acceptEmpty;
        }

        if (!ip.matches("(([0-9]){1,3}\\.){3}" + "([0-9]){1,3}"))
            return false;

        String[] parts = ip.split("\\.");
        for (String part : parts) {
            if (part.length() > 1 && part.charAt(0) == '0')
                return false;
            int i = Integer.parseInt(part);
            if (i < 0 || i > 255)
                return false;
        }

        return true;
    }

    public static String Str(Collection<String> args) {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (String s : args) {
            if (!first) {
                sb.append(ID_PART_SEPARATOR);
            } else {
                first = false;
            }
            sb.append(s);
        }

        return sb.toString();
    }

    public static String Str(String... args) {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (String s : args) {
            if (!first) {
                sb.append(ID_PART_SEPARATOR);
            } else {
                first = false;
            }
            sb.append(s);
        }

        return sb.toString();
    }

    public static List<String> listFromDotSeparated(String string) {
        return Arrays.asList(string.split("[" + ID_PART_SEPARATOR + "]"));
    }

    public static String deepestKey(String string) {
        if (!isMultilevelKey(string))
            return string;

        String[] splitKey = string.split("[" + ID_PART_SEPARATOR + "]");
        return splitKey[splitKey.length - 1];
    }

    public static boolean isMultilevelKey(String key) {
        return key.contains(ID_PART_SEPARATOR);
    }

    public static Set<String> Set(String... strings) {
        Set<String> set = new TreeSet<>();
        if (strings != null) {
            set.addAll(Arrays.asList(strings));
        }
        return set;
    }

    public static String escapeHtmlSpaces(String input) {
        if (input == null)
            return null;

        if (input.length() == 0)
            return input;

        StringBuffer sb = new StringBuffer();
        boolean inTag = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '<':
                    inTag = true;
                    sb.append(c);
                    break;
                case '>':
                    inTag = false;
                    sb.append(c);
                    break;
                case '-':
                    if (inTag)
                        sb.append(c);
                    else
                        sb.append("&#8209;");
                    break;
                case ' ':
                    if (inTag)
                        sb.append(c);
                    else
                        sb.append("&nbsp;");
                    break;
                default:
                    sb.append(c);
            }
        }

        return sb.toString();
    }
}
