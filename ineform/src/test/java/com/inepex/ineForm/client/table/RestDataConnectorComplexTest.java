package com.inepex.ineForm.client.table;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.inepex.ineForm.client.table.IneDataConnector.ManipulateResultCallback;
import com.inepex.ineForm.client.util.RequestBuilderFactory;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationResult;
import com.inepex.ineFrame.client.async.AsyncStatusIndicator;
import com.inepex.inei18n.client.DummyI18nProvider;
import com.inepex.inei18n.client.IneFormI18n_old;
import com.inepex.ineom.shared.T_e_s_tUtil;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.kvo.KeyValueObject;

/**
 * Not works because of json classes which tied to gwt
 * @author Tibor
 *
 */
public class RestDataConnectorComplexTest {

	KeyValueObject testKvo;
	
	@Mock RequestBuilderFactory requestBuilderFactory;
	@Mock RequestBuilder requestBuilder;
	ClientDescriptorStore descriptorStore;
	RestDataConnector restDataConnector;
	@Mock ManipulateResultCallback callback;
	String response = "{'stringField': 'hello', 'relField': {'longField': 3}, 'listField': [{'longField': 4}, {'longField': 5}]}";

	@Before
	public void init() throws Exception {
		IneFormI18n_old i18n = new IneFormI18n_old(new DummyI18nProvider());
		MockitoAnnotations.initMocks(this);
		descriptorStore = new ClientDescriptorStore();
		testKvo = T_e_s_tUtil.getTestKvo(descriptorStore);		
		when(requestBuilderFactory.createBuilder(any(Method.class), anyString())).thenReturn(requestBuilder);
		restDataConnector = new RestDataConnector(
				Mockito.mock(EventBus.class),
				Mockito.mock(AsyncStatusIndicator.class),
				requestBuilderFactory,
				"testKvo",
				"getUrl",
				"newUrl",
				"modifyUrl",
				"deleteUrl",
				false);

	}

	private void prepareRequestBuilder(RequestBuilder requestBuilder, String responseText, int statusCode) throws Exception {
		final Response response = mock(Response.class);
		when(response.getStatusCode()).thenReturn(statusCode);
		when(response.getText()).thenReturn(responseText);

		when(requestBuilder.sendRequest(anyString(), any(RequestCallback.class))).thenAnswer(new Answer<Request>() {

			@Override
			public Request answer(InvocationOnMock invocation) throws Throwable {
				((RequestCallback) invocation.getArguments()[1]).onResponseReceived(null, response);
				return null;
			}
		});
	}

	@Test
	public void createRequest() throws Exception {
		prepareRequestBuilder(requestBuilder, response.replace("'", "\""), 200);
		
		restDataConnector.objectCreateOrEditRequested(testKvo, callback);

		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		verify(requestBuilder).sendRequest(argument.capture(), any(RequestCallback.class));
		Assert.assertEquals(response, argument.getValue());
		ArgumentCaptor<ObjectManipulationResult> omrCapture = ArgumentCaptor.forClass(ObjectManipulationResult.class);
		verify(callback).onManipulationResult(omrCapture.capture());
		
		T_e_s_tUtil.assertEquals(testKvo, omrCapture.getValue().getObjectsNewState());
		

	}
}
