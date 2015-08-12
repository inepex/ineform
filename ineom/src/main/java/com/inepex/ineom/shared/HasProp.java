package com.inepex.ineom.shared;

import java.util.Map;

public interface HasProp {

    public Map<String, String> getAllPropsJson();

    public String getPropsJson(String id);

    public void setPropsJson(String id, String json);

}
