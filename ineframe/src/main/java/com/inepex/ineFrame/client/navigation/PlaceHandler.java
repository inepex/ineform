package com.inepex.ineFrame.client.navigation;

import static com.inepex.ineFrame.client.navigation.NavigationProperties.REDIRECT;
import static com.inepex.ineFrame.client.navigation.NavigationProperties.loginPlace;
import static com.inepex.ineFrame.client.navigation.NavigationProperties.wrongTokenPlace;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.inepex.ineFrame.client.async.ConnectionFailedHandler;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.ParamPlaceRedirectionCallbackHandler.AfterRedirectionLogic;
import com.inepex.ineFrame.client.navigation.places.ChildRedirectPlace;
import com.inepex.ineom.shared.descriptor.Node;

public abstract class PlaceHandler implements ValueChangeHandler<String>, PlaceRequestHandler {

	public static final String QUESTION_MARK = "?";
	public static final String AND_SIGN = "&";
	public static final String EQUALS_SIGN = "=";
	
	private final PlaceHierarchyProvider placeHierarchyProvider;
	private final MasterPage masterPage;
	private final AuthManager authManager;
	private final HistoryProvider historyProvider;
	private final ConnectionFailedHandler connectionFailedHandler;

	private String currentFullToken = null;

	public PlaceHandler(PlaceHierarchyProvider placeHierarchyProvider, MasterPage masterPage, AuthManager authManager,
			HistoryProvider historyProvider, EventBus eventBus, ConnectionFailedHandler connectionFailedHandler) {
		this.placeHierarchyProvider = placeHierarchyProvider;
		this.masterPage = masterPage;
		this.authManager = authManager;
		this.historyProvider = historyProvider;
		this.connectionFailedHandler = connectionFailedHandler;

		historyProvider.addHandler(this);
		eventBus.addHandler(PlaceRequestEvent.TYPE, this);

		placeHierarchyProvider.createPlaceHierarchy();
	}

	public void fireInitialPlace() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				historyProvider.fireCurrentHistoryState();
			}
		});
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (event.getValue().equals(currentFullToken))
			return;
		else {
			if (event.getValue().equals(""))
				realizePlaceChange(false, NavigationProperties.defaultPlace);
			else
				realizePlaceChange(false, event.getValue());
		}
			
	}

	@Override
	public void onPlaceRequest(PlaceRequestEvent e) {
		if (e.isOpenInNewWindow())
			openInNewWindow("#" + e.getHierarchicalTokensWithParam());
		else
			realizePlaceChange(e.isNeedWindowReload(), e.getHierarchicalTokensWithParam());
	}

	private void realizePlaceChange(final boolean needWindowReload, String newfullToken) {
		currentFullToken =  newfullToken;
		
		String currentFullTokenWithoutRedirect;
		if(currentFullToken.contains(PlaceHandler.QUESTION_MARK+NavigationProperties.REDIRECT))
			currentFullTokenWithoutRedirect=currentFullToken.substring(0,
					currentFullToken.indexOf(PlaceHandler.QUESTION_MARK+NavigationProperties.REDIRECT));
		else
			currentFullTokenWithoutRedirect=currentFullToken;
		
		
		Node<InePlace> placeNode = placeHierarchyProvider.getPlaceRoot().findNodeByHierarchicalId(
				PlaceHandlerHelper.getPlacePart(currentFullTokenWithoutRedirect));
		
		if (placeNode == null) {
			realizePlaceChange(needWindowReload, wrongTokenPlace);
			return;
		}
		
		PlaceHandlerHelper.updateHierarchicalTokens(currentFullTokenWithoutRedirect, placeHierarchyProvider.getPlaceRoot());

		final InePlace place = placeNode.getNodeElement();

		if(!(authManager instanceof NoAuthManager)
				&& currentFullTokenWithoutRedirect.startsWith(NavigationProperties.loginPlace)
				&& authManager.isUserLoggedIn()) {
			realizePlaceChange(needWindowReload, NavigationProperties.defaultPlace);
			return;
		}
		
		if (!checkPermissionsAndRedirectIfNeeded(place, currentFullTokenWithoutRedirect, needWindowReload))
			return;
		
		if (place instanceof ChildRedirectPlace) {
			ChildRedirectPlace cdPlace = (ChildRedirectPlace) place;
			realizePlaceChange(needWindowReload, PlaceHandlerHelper.appendChild(currentFullTokenWithoutRedirect, cdPlace.getChildToken()));
			return;
		}
		
		//selector widget update
		final Map<String, String> urlParams = PlaceHandlerHelper.getUrlParameters(currentFullTokenWithoutRedirect);
		Node<InePlace> pointer = placeNode;
		
		new ParamPlaceRedirectionCallbackHandler(currentFullTokenWithoutRedirect, pointer, urlParams, new AfterRedirectionLogic() {
			
			@Override
			public void afterRedirectionHandled(String token) {
				if (token != null && !token.equals(currentFullToken)){
					realizePlaceChange(needWindowReload, token);
					return;
				}
				
				// change the browsers token if does not mach current token
				if (needToRefresh())
					historyProvider.newItem(currentFullToken);
				
				if(needWindowReload) {
					connectionFailedHandler.shutdown();
					Window.Location.reload();			
				} else {			
					if (specificAdjustPlaceShouldReturn(place))
						return;
					Document.get().setTitle(NavigationProperties.defaultWindowTitle);					
					masterPage.render(place, urlParams);
				}
			}
		}).execute();

	}
	
	private boolean needToRefresh(){
		return (!historyProvider.getToken().equals(currentFullToken) 
				&& (!isWrongTokenPlaceSet() 
						|| (isWrongTokenPlaceSet() && !currentFullToken.equals(NavigationProperties.wrongTokenPlace))));
	}
	
	private boolean isWrongTokenPlaceSet(){
		return (!NavigationProperties.wrongTokenPlace.equals(NavigationProperties.defaultPlace));
	}

	protected abstract boolean specificAdjustPlaceShouldReturn(InePlace place);
	
	private static void openInNewWindow(String url) {
		final String realURL;
		
		if (Window.Navigator.getUserAgent().contains("MSIE")){
			realURL = "../" + url;
		} else {
			realURL = url;
		}
		

		//FIXME: the base browser window may get the focus back from popup in inteble's userCommands,
		//timer is better than nothing, but not the perfect solution
		
		//without timer IE may drop the popup behind the actual window making PlaceRequestEvent from an inetable's userCommand
		Timer t = new Timer() {
			
			@Override
			public void run() {
				Window.open(
						realURL
						, "_blank", "");
			}
		};
		t.schedule(500);
	}
	
	
	private boolean checkPermissionsAndRedirectIfNeeded(InePlace place, String currentFullTokenWithoutRedirect, boolean needWindowReload){
		if (place.isAuthenticationNeeded()) {
			List<String> allowedRolesForPlace = place.getRolesAllowed();
			if (!authManager.isUserLoggedIn()){
				realizePlaceChange(needWindowReload, 
						loginPlace +
						QUESTION_MARK +
						REDIRECT +
						EQUALS_SIGN +
						currentFullTokenWithoutRedirect);
				
				return false;
			} else if (authManager.isUserLoggedIn() && (allowedRolesForPlace == null || allowedRolesForPlace.size() == 0)) {
				return true;
			} else if (authManager.doUserHaveAnyOfRoles(allowedRolesForPlace.toArray(new String[allowedRolesForPlace.size()]))){
				return true;				
			} else {
				masterPage.renderForbidden(place);
				return false;
			}
		} else return true;
	}
	
	public void back() {
		historyProvider.back();
	}
	
	public String getCurrentFullToken(){
		return currentFullToken;
	}
	
//-------------------------------------
//        navigation helper methods
//-------------------------------------
	public PlaceRequestEvent generateRefreshEvent() {
		return generateSubMenuEvent();
	}
	
	public PlaceRequestEvent generateSubMenuEvent(String... subMenuTokens) {
		PlaceRequestEvent event = new PlaceRequestEvent();
		event.setHierarchicalTokensWithParam(PlaceHandlerHelper.createSubMenuToken(currentFullToken, subMenuTokens));
		return event;
	}

	public PlaceRequestEvent generateJumpUpEvent() {
		PlaceRequestEvent event = new PlaceRequestEvent();
		
		Node<InePlace> placeNode = placeHierarchyProvider.getPlaceRoot().findNodeByHierarchicalId(
				PlaceHandlerHelper.getPlacePart(currentFullToken));
		
		event.setHierarchicalTokensWithParam(placeNode.getParent().getNodeElement().getHierarchicalToken());
		
		return event;
	}
	
	public PlaceRequestEvent generateSameLevelMenuEvent(String... subMenuTokens) {
		PlaceRequestEvent event = new PlaceRequestEvent();
		event.setHierarchicalTokensWithParam(PlaceHandlerHelper.createSameLevelMenuToken(currentFullToken, subMenuTokens));
		return event;
	}
}
