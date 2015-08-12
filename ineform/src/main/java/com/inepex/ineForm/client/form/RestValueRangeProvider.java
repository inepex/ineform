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
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class RestValueRangeProvider implements ValueRangeProvider {

    public static interface RelationResultExtractor {

        public JSONArray extract(String text);

    }

    private final AssistedObjectHandlerFactory handlerFactory;
    private final DescriptorStore descriptorStore;
    private AsyncStatusIndicator statusIndicator;
    private final RequestBuilderFactory requestBuilderFactory;
    private Map<String, String> descriptorToUrlMapping;
    private Map<String, RelationResultExtractor> descriptorToExtractorMapping =
        new HashMap<String, RestValueRangeProvider.RelationResultExtractor>();
    private Map<String, String> descriptorToDisplayNameFieldMapping = new HashMap<String, String>();

    @Inject
    public RestValueRangeProvider(
        DescriptorStore descriptorStore,
        AsyncStatusIndicator statusIndicator,
        RequestBuilderFactory requestBuilderFactory,
        @Assisted Map<String, String> descriptorToUrlMapping) {
        this.descriptorStore = descriptorStore;
        this.statusIndicator = statusIndicator;
        this.requestBuilderFactory = requestBuilderFactory;
        this.descriptorToUrlMapping = descriptorToUrlMapping;
        this.handlerFactory = new AssistedObjectHandlerFactory(descriptorStore);
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
                        parseListAndDoCallback(
                            castedFieldDesc.getRelatedDescriptorName(),
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

    protected void parseListAndDoCallback(
        String descriptorName,
        String text,
        ValueRangeResultCallback callback) {
        if (text.equals(""))
            statusIndicator.onGeneralFailure(IneFormI18n.restRequestError());
        JSONArray jsonList = null;
        if (!descriptorToExtractorMapping.containsKey(descriptorName)) {
            jsonList = JSONParser.parseStrict(text).isArray();
        } else {
            jsonList = descriptorToExtractorMapping.get(descriptorName).extract(text);
        }

        List<Relation> relationList = new ArrayList<Relation>();

        for (int i = 0; i < jsonList.size(); i++) {
            AssistedObject kvo =
                new KvoJsonParser(descriptorStore, jsonList.get(i).isObject(), descriptorName)
                    .parse();
            String displayName = kvo.toString();
            if (descriptorToUrlMapping.containsKey(descriptorName))
                displayName =
                    handlerFactory.createHandler(kvo).getString(
                        descriptorToDisplayNameFieldMapping.get(descriptorName));
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
    protected RelationFDesc castDescriptorCheckType(
        FDesc fieldDesc,
        ValueRangeResultCallback callback) {
        if (fieldDesc.getType() != IneT.RELATION) {
            System.out
                .println("ValueRangeProvider.getRelationValueRange was called with a FieldDescriptor("
                    + fieldDesc.getKey()
                    + ") that"
                    + " is not a RelatonFieldDescriptor");
            callback.onValueRangeResultReady(null);
            return null;
        }

        return (RelationFDesc) fieldDesc;
    }

}
