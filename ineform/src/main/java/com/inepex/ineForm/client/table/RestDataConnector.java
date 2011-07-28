package com.inepex.ineForm.client.table;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.util.RequestBuilderFactory;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.ineFrame.client.kvo.KvoJsonParser;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.IneList;
import com.inepex.ineom.shared.kvo.KeyValueObject;
import com.inepex.ineom.shared.kvo.KeyValueObjectSerializer;
import com.inepex.ineom.shared.kvo.KeyValueObjectSerializer.ListSerializer;
import com.inepex.ineom.shared.kvo.KeyValueObjectSerializer.RelationSerializer;
import com.inepex.ineom.shared.kvo.Relation;
import com.inepex.ineom.shared.validation.ValidationResult;

/**
 * DataConnector implementation using http requests
 * 
 * TODO: undelete and table update are not yet implemented
 * 
 * @author SoTi
 *
 */
public class RestDataConnector extends IneDataConnector {

	private ListSerializer listSerializer = new ListSerializer() {
		
		@Override
		public String serialize(IneList list) {
			StringBuffer sb = new StringBuffer();
			for (Relation rel : list.getRelationList()){
				sb.append(rel.getKvo().getId());
				sb.append(",");
				
			}
			return sb.substring(0, sb.length()-1);
		}
	};
	
	private RelationSerializer relationSerializer = new RelationSerializer() {
		
		@Override
		public String serialize(Relation relation) {
			return "" + relation.getKvo().getId();
		}
	};
	
	public static interface ResultExtractor {
		
		public JSONObject extract(String text);
		
	}
	
	public static interface ErrorExtractor {
		
		public ValidationResult extract(String json);
		
	}
	
	AsyncStatusIndicator statusIndicator;
	RequestBuilderFactory requestBuilderFactory;
	String getUrl;
	String newUrl;
	String modifyUrl;
	String deleteUrl;
	boolean serializeId;
	Map<String, ResultExtractor> descriptorToExtractorMapping = new HashMap<String, ResultExtractor>();
	ErrorExtractor errorExtractor;
	
	@Inject
	public RestDataConnector(EventBus eventBus, 
			AsyncStatusIndicator statusIndicator,
			RequestBuilderFactory requestBuilderFactory,
			@Assisted("descriptorName") String descriptorName,
			@Assisted("getUrl") String getUrl,
			@Assisted("newUrl") String newUrl, 
			@Assisted("modifyUrl") String modifyUrl,
			@Assisted("deleteUrl") String deleteUrl,
			@Assisted boolean serializeId
			) {
		super(eventBus, descriptorName);
		this.statusIndicator = statusIndicator;
		this.requestBuilderFactory = requestBuilderFactory;
		this.getUrl = getUrl;
		this.newUrl = newUrl;
		this.modifyUrl = modifyUrl;
		this.deleteUrl = deleteUrl;
		this.serializeId = serializeId;
	}
	
	public void setListSerializer(ListSerializer listSerializer) {
		this.listSerializer = listSerializer;
	}

	public void setRelationSerializer(RelationSerializer relationSerializer) {
		this.relationSerializer = relationSerializer;
	}

	public Map<String, ResultExtractor> getDescriptorToExtractorMapping() {
		return descriptorToExtractorMapping;
	}
	
	public void setStatusIndicator(AsyncStatusIndicator statusIndicator) {
		this.statusIndicator = statusIndicator;
	}

	@Override
	public void objectCreateOrEditRequested(final AssistedObject object, final ManipulateResultCallback callback) {
		statusIndicator.onAsyncRequestStarted(IneFormI18n.loading());
		String url = "";
		if (object.isNew()){
			url = newUrl;
		} else {
			url = modifyUrl.replace("{id}", "" + object.getId());
		}
		
		RequestBuilder builder = requestBuilderFactory.createBuilder(RequestBuilder.POST, url);
		builder.setHeader("Content-Type",
			"application/x-www-form-urlencoded");
		try {
		builder.sendRequest(getSerializedValue(object), new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getStatusCode() == Response.SC_OK){
					ValidationResult vr = null;
					if (errorExtractor != null){
						vr = errorExtractor.extract(response.getText());
					}
					
					ObjectManipulationResult omr = null;
					if (vr == null){
						omr = new ObjectManipulationResult(
								getObjectFromJSON(response.getText(), object.getDescriptorName()));
					} else {
						omr = new ObjectManipulationResult();
						omr.setSuccess(false);
						omr.setValidationResult(vr);
					}
					callback.onManipulationResult(omr);
					statusIndicator.onSuccess("");
					
				} else {
					System.out.println("Status: " + response.getStatusCode() + "; " + response.getText());
					statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
				}				
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				exception.printStackTrace();
				statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
				
			}
		});
		} catch (RequestException e) {
			e.printStackTrace();
			statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
		}
	}

	@Override
	public void objectDeleteRequested(final AssistedObject object, final ManipulateResultCallback callback) {
		statusIndicator.onAsyncRequestStarted(IneFormI18n.loading());
		RequestBuilder builder = requestBuilderFactory.createBuilder(RequestBuilder.POST, deleteUrl);
		builder.setHeader("Content-Type",
			"application/x-www-form-urlencoded");
		try {
			builder.sendRequest(IFConsts.KEY_ID + "=" + object.getId(), new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == Response.SC_OK){						
						callback.onManipulationResult(new ObjectManipulationResult());
						statusIndicator.onSuccess("");
					} else {
						System.out.println("Status: " + response.getStatusCode() + "; " + response.getText());
						statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
					}				
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					exception.printStackTrace();
					statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
					
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
			statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
		}
	}

	@Override
	public void objectUnDeleteRequested(AssistedObject object, ManipulateResultCallback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(boolean updateDisplays) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onRangeChanged(HasData<AssistedObject> display) {
		// TODO Auto-generated method stub
		
	}
	
	private String getSerializedValue(AssistedObject object){
		return new KeyValueObjectSerializer(object, "&", "=")
		.setListSerializer(listSerializer)
		.setRelationSerializer(relationSerializer)
		.setIncludeId(serializeId)
		.serializeToString();
	}
	
	private KeyValueObject getObjectFromJSON(String jsonString, String descriptorName){
		JSONObject jso = null;
		if (descriptorToExtractorMapping.containsKey(descriptorName)){
			jso = descriptorToExtractorMapping.get(descriptorName).extract(jsonString);
		} else {
			jso = JSONParser.parseStrict(jsonString).isObject();
		}
		return new KvoJsonParser(jso, descriptorName).parse();
	}

	public void setErrorExtractor(ErrorExtractor errorExtractor) {
		this.errorExtractor = errorExtractor;
	}
	
	

}
