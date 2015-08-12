package com.inepex.ineom.shared;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;

public class GwtPropHandler extends PropHandler {

    @Inject
    public GwtPropHandler() {}

    @Override
    public void setProp(HasProp o, String group, String key, Boolean value) {
        JSONObject groupJSON = getOrCreateJsonGroup(o, group);
        if (value == null) {
            groupJSON.put(key, JSONNull.getInstance());
        } else {
            groupJSON.put(key, JSONBoolean.getInstance(value));
        }
        o.setPropsJson(group, groupJSON.toString());
    }

    @Override
    public void setProp(HasProp o, String group, String key, Double value) {
        JSONObject groupJSON = getOrCreateJsonGroup(o, group);
        if (value == null) {
            groupJSON.put(key, JSONNull.getInstance());
        } else {
            groupJSON.put(key, new JSONNumber(value));
        }
        o.setPropsJson(group, groupJSON.toString());
    }

    @Override
    public void setProp(HasProp o, String group, String key, String value) {
        JSONObject groupJSON = getOrCreateJsonGroup(o, group);
        if (value == null) {
            groupJSON.put(key, JSONNull.getInstance());
        } else {
            groupJSON.put(key, new JSONString(value));
        }
        o.setPropsJson(group, groupJSON.toString());
    }

    @Override
    public void setProp(HasProp o, String group, String key, Object value) {
        JSONObject groupJSON = getOrCreateJsonGroup(o, group);
        if (value == null) {
            groupJSON.put(key, JSONNull.getInstance());
        } else if (value instanceof Long) {
            groupJSON.put(key, new JSONNumber((Long) value));
        } else if (value instanceof Double) {
            groupJSON.put(key, new JSONNumber((Double) value));
        } else if (value instanceof Integer) {
            groupJSON.put(key, new JSONNumber((Integer) value));
        } else if (value instanceof Float) {
            groupJSON.put(key, new JSONNumber((Float) value));
        } else if (value instanceof String) {
            groupJSON.put(key, new JSONString((String) value));
        } else if (value instanceof Boolean) {
            groupJSON.put(key, JSONBoolean.getInstance((Boolean) value));
        }
        o.setPropsJson(group, groupJSON.toString());
    }

    private JSONObject getOrCreateJsonGroup(HasProp o, String group) {
        JSONObject groupJSON = new JSONObject();
        if (o.getPropsJson(group) != null) {
            groupJSON = JSONParser.parseStrict(o.getPropsJson(group)).isObject();
        }
        return groupJSON;
    }

    @Override
    public Boolean getBooleanProp(HasProp o, String group, String key) {
        try {
            return getProp(o, group, key).isBoolean().booleanValue();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Double getNumberProp(HasProp ao, String group, String key) {
        try {
            return getProp(ao, group, key).isNumber().doubleValue();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getStringProp(HasProp ao, String group, String key) {
        try {
            return getProp(ao, group, key).isString().stringValue();
        } catch (Exception e) {
            return null;
        }
    }

    private JSONValue getProp(HasProp ao, String group, String key) {
        if (ao.getPropsJson(group) == null)
            return null;
        JSONObject groupJSON = getOrCreateJsonGroup(ao, group);
        if (groupJSON.containsKey(key)) {
            try {
                return groupJSON.get(key);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getStringPropFromGroupJson(String key, String json) {
        if (json == null)
            return null;
        JSONObject obj = JSONParser.parseStrict(json).isObject();
        if (obj != null && obj.get(key) != null) {
            JSONString value = obj.get(key).isString();
            if (value != null) {
                return value.stringValue();
            }
        }
        return null;
    }

    @Override
    public Boolean getBooleanPropFromGroupJson(String key, String json) {
        if (json == null)
            return null;
        JSONObject obj = JSONParser.parseStrict(json).isObject();
        if (obj != null && obj.get(key) != null) {
            JSONBoolean value = obj.get(key).isBoolean();
            if (value != null) {
                return value.booleanValue();
            }
        }
        return null;
    }

    @Override
    public Double getNumberPropFromGroupJson(String key, String json) {
        if (json == null)
            return null;
        JSONObject obj = JSONParser.parseStrict(json).isObject();
        if (obj != null && obj.get(key) != null) {
            JSONNumber value = obj.get(key).isNumber();
            if (value != null) {
                return value.doubleValue();
            }
        }
        return null;
    }

    @Override
    public
        void
        setProp(HasProp hasProp, String group, String key, String value, boolean strictMatch) {
        setProp(hasProp, group, key, value);
        setProp(hasProp, group, getStrictMatchKey(key), strictMatch);
    }

    @Override
    public Map<String, Object> getPropMap(HasProp hasProp, String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (hasProp.getPropsJson(id) == null)
            return map;
        JSONObject groupJSON = getOrCreateJsonGroup(hasProp, id);
        for (String key : groupJSON.keySet()) {
            JSONNumber numObj = groupJSON.get(key).isNumber();
            if (numObj != null) {
                map.put(key, numObj.doubleValue());
                continue;
            }
            JSONString stringObj = groupJSON.get(key).isString();
            if (stringObj != null) {
                map.put(key, stringObj.stringValue());
                continue;
            }
            JSONBoolean boolObj = groupJSON.get(key).isBoolean();
            if (boolObj != null) {
                map.put(key, boolObj.booleanValue());
                continue;
            }
        }
        return map;
    }

}
