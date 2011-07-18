package com.inepex.ineForm.client.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.util.RequestBuilderFactory;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.kvo.KvoJsonParser;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneT;
import com.inepex.ineom.shared.kvo.Relation;

public class RestValueRangeProvider implements ValueRangeProvider {

	public static interface RelationResultExtractor {
		
		public JSONArray extract(String text);
		
	}
	
	AsyncStatusIndicator statusIndicator;
	RequestBuilderFactory requestBuilderFactory;
	Map<String, String> descriptorToUrlMapping;
	Map<String, RelationResultExtractor> descriptorToExtractorMapping = new HashMap<String, RestValueRangeProvider.RelationResultExtractor>();
	Map<String, String> descriptorToDisplayNameFieldMapping = new HashMap<String, String>();

	@Inject
	public RestValueRangeProvider(
			AsyncStatusIndicator statusIndicator,
			RequestBuilderFactory requestBuilderFactory,
			@Assisted Map<String, String> descriptorToUrlMapping) {
		this.statusIndicator = statusIndicator;
		this.requestBuilderFactory = requestBuilderFactory;
		this.descriptorToUrlMapping = descriptorToUrlMapping;
	}

	public Map<String, String> getDescriptorToUrlMapping() {
		return descriptorToUrlMapping;
	}

	public Map<String, RelationResultExtractor> getDescriptorToExtractorMapping() {
		return descriptorToExtractorMapping;
	}
	
	public Map<String, String> getDescriptorToDisplayNameFieldMapping() {
		return descriptorToDisplayNameFieldMapping;
	}

	public void setStatusIndicator(AsyncStatusIndicator statusIndicator) {
		this.statusIndicator = statusIndicator;
	}

	@Override
	public void getRelationValueRange(FDesc fieldDesc, final ValueRangeResultCallback callback) {
		statusIndicator.onAsyncRequestStarted(IneFormI18n.loading());
		final RelationFDesc castedFieldDesc = castDescriptorCheckType(fieldDesc, callback);

		String url = descriptorToUrlMapping.get(castedFieldDesc.getRelatedDescriptorName());
		RequestBuilder builder = requestBuilderFactory.createBuilder(RequestBuilder.GET, url);
		try {
			builder.sendRequest("", new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == Response.SC_OK) {
						parseListAndDoCallback(castedFieldDesc.getRelatedDescriptorName(), 
								response.getText(), 
								callback);
						statusIndicator.onSuccess("");
					} else {
						statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
				}
			});
		} catch (Exception e) {
			statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
		}

	}

	protected void parseListAndDoCallback(String descriptorName, String text, ValueRangeResultCallback callback) {
		if (text.equals("")) statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
		JSONArray jsonList = null;
		if (!descriptorToExtractorMapping.containsKey(descriptorName)){
			jsonList = JSONParser.parseStrict(text).isArray();
		} else {
			jsonList = descriptorToExtractorMapping.get(descriptorName).extract(text);
		}
 
		List<Relation> relationList = new ArrayList<Relation>();
		
		for (int i = 0; i < jsonList.size(); i++){
			AssistedObject kvo = new KvoJsonParser(jsonList.get(i).isObject(), descriptorName).parse();
			String displayName = kvo.toString();
			if (descriptorToUrlMapping.containsKey(descriptorName)) 
				displayName = kvo.getString(descriptorToDisplayNameFieldMapping.get(descriptorName));
			relationList.add(new Relation(kvo.getId(), displayName, kvo));			 
		}
		
		callback.onValueRangeResultReady(relationList);
	}

	/**
	 * Checks weather the fieldDescriptor received is valid. Also calls the
	 * callback with null if it is invalid!
	 * 
	 * @param fieldDesc
	 * @param callback
	 * @return
	 */
	protected RelationFDesc castDescriptorCheckType(FDesc fieldDesc, ValueRangeResultCallback callback) {
		if (fieldDesc.getType() != IneT.RELATION) {
			System.out.println("ValueRangeProvider.getRelationValueRange was called with a FieldDescriptor(" + fieldDesc.getKey()
					+ ") that" + " is not a RelatonFieldDescriptor");
			callback.onValueRangeResultReady(null);
			return null;
		}

		return (RelationFDesc) fieldDesc;
	}

}
