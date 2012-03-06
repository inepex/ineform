package com.inepex.ineFrame.client.navigation.places;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Provider;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.navigation.PlaceToken;
import com.inepex.ineFrame.client.page.InePage;
import com.inepex.ineFrame.client.page.InePage.UrlParamsParsedCallback;

/**
 * Handles one parameter.
 * If parameter set, automatically redirects to defaultChild. Else page can provide redirection.
 * @author SoTi
 *
 */
public class OneParamPlace extends ParamPlace {
	
	/**
	 * Following methods never called: setCurrentPlace, setUrlParameters
	 * @author SoTi
	 *
	 */
	public static interface OneParamPresenter extends InePage {
		public void getDefaultSelection(AsyncCallback<String> callback);
		public void setSelection(String selected);
		public void setOneParamPlace(OneParamPlace oneParamPlace);
	}
	
	private final String placeId; 
	private final String paramName;
	private final String descriptorName;
	private final String defaultChildName;
	private final Provider<? extends OneParamPresenter> pageProvider;
	private OneParamPresenter page;
	
	public OneParamPlace(String placeId, String descriptorName, String paramName, String defaultChildName,
			boolean isSelectorPage, 
			Provider<? extends OneParamPresenter> pageProvider) {
		super(isSelectorPage);
		this.placeId = placeId;
		this.paramName = paramName;
		this.defaultChildName = defaultChildName;
		this.pageProvider = pageProvider;
		this.descriptorName = descriptorName;
	}
	
	private OneParamPresenter getAndInitPage(){
		if (page == null) {
			page = pageProvider.get();
			page.setOneParamPlace(this);
		}
		return page;
	}	
	
	@Override
	public void processParams(String requestedToken, Map<String, String> urlParams, final UrlParamsParsedCallback callback) {
		getAndInitPage().onShow();
		try {
			if (!PlaceHandlerHelper.getPlacePart(requestedToken).endsWith(placeId)){
				//param and childpage set, not need redirection
				getAndInitPage().setSelection(urlParams.get(paramName));
				callback.onUrlParamsParsed(null);
				return;
			} else {
				String param = urlParams.get(paramName);			
				if (param == null){
					//param not set, can redirect through callback to a default selection
					getAndInitPage().getDefaultSelection(new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable ex) {
							handleException(ex, callback);
						}

						@Override
						public void onSuccess(String defaultValue) {
							try {
								callback.onUrlParamsParsed(getChildPlaceToken(defaultValue));
							} catch (Exception ex){
								handleException(ex, callback);
							}
						}
					});
				} else {
					callback.onUrlParamsParsed();
				}
			}
		} catch (Exception ex){
			handleException(ex, callback);
			ex.printStackTrace();
		}
	}
	
	public String getChildPlaceToken(String param){
		if (param == null){
			return new PlaceToken(getHierarchicalToken()).getToken();
		} else {
			return new PlaceToken(getHierarchicalToken())
				.appendParam(paramName, "" + param)
				.appendChild(defaultChildName).getToken();
		}
	}
	
	private void handleException(Throwable ex, UrlParamsParsedCallback callback){
		System.out.println("Param not set correctly to place: " + placeId 
				+ " Redirected to NavigationProperties.pageNotFoundPlace");
		callback.onUrlParamsParsed(NavigationProperties.pageNotFoundPlace);
	}
	
	@Override
	public OneParamPresenter getAssociatedPage() {
		return getAndInitPage();
	}

	public String getDescriptorName() {
		return descriptorName;
	}

}
