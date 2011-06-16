package com.inepex.ineForm.client.util;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;

public interface RequestBuilderFactory {

	public RequestBuilder createBuilder(Method method, String url);
	
}
