package com.inepex.ineFrame.shared;

import java.util.List;

import com.inepex.ineom.shared.dispatch.GenericActionResult;

public class GetTimeZoneNamesResult extends GenericActionResult {

    private List<String> timeZoneNames;

    public GetTimeZoneNamesResult() {}

    public GetTimeZoneNamesResult(List<String> timeZoneNames) {
        this.timeZoneNames = timeZoneNames;
    }

    public List<String> getTimeZoneNames() {
        return timeZoneNames;
    }

    public void setTimeZoneNames(List<String> timeZoneNames) {
        this.timeZoneNames = timeZoneNames;
    }

}
