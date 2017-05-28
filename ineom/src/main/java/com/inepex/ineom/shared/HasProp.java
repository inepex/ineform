package com.inepex.ineom.shared;

import java.util.Map;

public interface HasProp {

    Map<String, String> getAllPropsJson();

    String getPropsJson(String id);

    void setPropsJson(String id, String json);

}
