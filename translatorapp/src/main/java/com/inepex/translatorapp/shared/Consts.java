package com.inepex.translatorapp.shared;

import java.util.Arrays;
import java.util.List;

import com.inepex.ineFrame.shared.util.date.DateHelper;

public class Consts {

    public static final String defaultLang = "en";

    public static class Roles {
        public static final String developer = "developer";
        public static final String translator = "translator";

        public static List<String> all() {
            return Arrays.asList(developer, translator);
        }
    }

    public static final long recentTimeRange = DateHelper.dayInMs * 7;

    public static class Upload {
        public static final String SEP = ";";
        public static final String key = "key";
        public static final String desc = "desc";
    }

}
