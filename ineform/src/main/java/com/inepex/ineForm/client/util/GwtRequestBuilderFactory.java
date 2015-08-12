package com.inepex.ineForm.client.util;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;

public class GwtRequestBuilderFactory implements RequestBuilderFactory {

    @Override
    public RequestBuilder createBuilder(Method method, String url) {
        return new RequestBuilder(method, url);
    }

}
