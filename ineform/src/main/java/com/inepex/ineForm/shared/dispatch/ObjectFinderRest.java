package com.inepex.ineForm.shared.dispatch;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.util.RequestBuilderFactory;
import com.inepex.ineFrame.client.kvo.KvoJsonParser;
import com.inepex.ineFrame.client.kvo.KvoJsonParser.ResultObjectExtractor;
import com.inepex.ineom.shared.kvo.AssistedObject;

public class ObjectFinderRest {
	public static interface ResultExtractor {
		
		public JSONObject extract(String text);
		
	}
	
	String descriptorName;
	String url;
	RequestBuilderFactory requestBuilderFactory;
	ResultExtractor resultExtractor;
	Map<String, ResultObjectExtractor> customResultExtractors = new HashMap<String, KvoJsonParser.ResultObjectExtractor>();

	@Inject
	public ObjectFinderRest(RequestBuilderFactory requestBuilderFactory
			, @Assisted("descriptorName") String descriptorName
			, @Assisted("url") String url
			, @Assisted ResultExtractor resultExtractor 
			, @Assisted Map<String, ResultObjectExtractor> customResultExtractors
			) {
		this.requestBuilderFactory = requestBuilderFactory;
		this.url = url;
		this.descriptorName = descriptorName;
		this.resultExtractor = resultExtractor;
		this.customResultExtractors = customResultExtractors;
	}

	public void get(Long id, final AsyncCallback<AssistedObject> callback) {
		RequestBuilder builder = requestBuilderFactory.createBuilder(RequestBuilder.GET, url.replace("{id}", "" + id));
		try {
		builder.sendRequest("", new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == Response.SC_OK) {
						
						
						AssistedObject kvo = new KvoJsonParser(
								resultExtractor.extract(response.getText())
								, descriptorName).setCustomResultExtractors(customResultExtractors).parse();
						callback.onSuccess(kvo);
					} else {
						callback.onFailure(new Exception("status code:" + response.getStatusCode()));
					}
			}
			
			@Override
				public void onError(Request request, Throwable e) {
					callback.onFailure(e);
			}
		});
		} catch (Exception e) {
			callback.onFailure(e);
		}
		
	}
}
