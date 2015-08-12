package com.inepex.example.ContactManager.client.i18n;

import com.inepex.example.ContactManager.shared.MeetingType;

public class CMI18nExt {

    public static String getName(MeetingType item) {
        switch (item) {
            case INE_OFFICE:
                return CMI18n.meetingType_INE_OFFICE();
            case PARNER_OFFICE:
                return CMI18n.meetingType_PARNER_OFFICE();
            case TELE_CONFERENCE:
                return CMI18n.meetingType_TELE_CONFERENCE();
            case VIDEO_CONFERENCE:
                return CMI18n.meetingType_VIDEO_CONFERENCE();
        }
        return null;
    }

}
