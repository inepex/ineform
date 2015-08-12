package com.inepex.inei18n.util;

import java.util.List;
import java.util.Map;

import com.inepex.inei18n.shared.LocalizedString;

public class DownloadLocalizablesDto {

    private List<String> warning;
    private Map<String, LocalizedString> localizables;

    public List<String> getWarning() {
        return warning;
    }

    public void setWarning(List<String> warning) {
        this.warning = warning;
    }

    public Map<String, LocalizedString> getLocalizables() {
        return localizables;
    }

    public void setLocalizables(Map<String, LocalizedString> localizables) {
        this.localizables = localizables;
    }
}
