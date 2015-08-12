package com.inepex.ineom.shared;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class GwtJsonDifference implements JsonDifference {

    @Override
    public String difference(String original, String modified) {
        JSONObject origJSON = new JSONObject();
        if (original != null)
            origJSON = JSONParser.parseStrict(original).isObject();
        JSONObject newJSON = new JSONObject();
        if (modified != null)
            newJSON = JSONParser.parseStrict(modified).isObject();
        JSONObject diffJSON = new JSONObject();

        for (String field : origJSON.keySet()) {
            if (!newJSON.containsKey(field)) { // deleted
                diffJSON.put(field, JSONNull.getInstance());
            } else if (!newJSON.get(field).toString().equals(origJSON.get(field).toString())) { // modifed
                diffJSON.put(field, newJSON.get(field));
            }
        }
        for (String field : newJSON.keySet()) {
            if (!origJSON.containsKey(field)) {
                diffJSON.put(field, newJSON.get(field)); // added
            }
        }
        return diffJSON.toString();
    }

}
