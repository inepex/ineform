package com.inepex.ineFrame.client.navigation;

import static com.inepex.ineFrame.client.navigation.NavigationProperties.REDIRECT;
import static com.inepex.ineFrame.client.navigation.NavigationProperties.loginPlace;
import static com.inepex.ineFrame.client.navigation.NavigationProperties.wrongTokenPlace;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.places.ChildRedirectPlace;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineom.shared.descriptor.Node;

public abstract class PlaceHandler implements ValueChangeHandler<String>, PlaceRequestHandler {

	public static final String QUESTION_MARK = "?";
	public static final String AND_SIGN = "&";
	public static final String EQUALS_SIGN = "=";

	protected PlaceHierarchyProvider placeHierarchyProvider;
	protected MasterPage masterPage;
	protected AuthManager authManager;
	protected HistoryProvider historyProvider;
	protected EventBus eventBus;

	private String previousToken = null;
	private String currentFullToken = null;
	
	private boolean lastRequestWasARedirect = false;

	public PlaceHandler(PlaceHierarchyProvider placeHierarchyProvider, MasterPage masterPage, AuthManager authManager,
			HistoryProvider historyProvider, EventBus eventBus) {
		this.placeHierarchyProvider = placeHierarchyProvider;
		this.masterPage = masterPage;
		this.authManager = authManager;
		this.historyProvider = historyProvider;
		this.eventBus = eventBus;

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
		else
			eventBus.fireEvent(new PlaceRequestEvent(event.getValue()));
	}

	@Override
	public void onPlaceRequest(PlaceRequestEvent e) {
		if (lastRequestWasARedirect){
			lastRequestWasARedirect = false;
		} else {
			previousToken = currentFullToken;
		}		
		
		currentFullToken =  e.getHierarchicalTokensWithParam();		
	
		if (e.isOpenInNewWindow()){
			openInNewWindow("#" + currentFullToken);
		} else {
			realizePlaceChange(e.isNeedWindowReload());
		}
	}

	private void realizePlaceChange(boolean needWindowReload) {
		String currentFullTokenWithoutRedirect;
		if(currentFullToken.contains(PlaceHandler.QUESTION_MARK+NavigationProperties.REDIRECT))
			currentFullTokenWithoutRedirect=currentFullToken.substring(0,
					currentFullToken.indexOf(PlaceHandler.QUESTION_MARK+NavigationProperties.REDIRECT));
		else
			currentFullTokenWithoutRedirect=currentFullToken;
		
		
		Node<InePlace> placeNode = placeHierarchyProvider.getPlaceRoot().findNodeByHierarchicalId(
				PlaceHandlerHelper.getPlacePart(currentFullTokenWithoutRedirect));
		
		if (placeNode == null) {
			historyProvider.newItem(wrongTokenPlace);
			return;
		}
		
		PlaceHandlerHelper.updateHierarchicalTokens(currentFullTokenWithoutRedirect, placeHierarchyProvider.getPlaceRoot());

		InePlace place = placeNode.getNodeElement();

		if (!checkPermissionsAndRedirectIfNeeded(place, currentFullTokenWithoutRedirect, needWindowReload)) return;
		
		if (place instanceof ChildRedirectPlace) {
			ChildRedirectPlace cdPlace = (ChildRedirectPlace) place;
			PlaceRequestEvent pre = new PlaceRequestEvent(PlaceHandlerHelper.appendChild(currentFullTokenWithoutRedirect, cdPlace.getChildToken()));
			pre.setNeedWindowReload(needWindowReload);
			lastRequestWasARedirect = true;
			eventBus.fireEvent(pre);
			return;
		}
		
		//param place checking
		Map<String, String> urlParams = PlaceHandlerHelper.getUrlParameters(currentFullTokenWithoutRedirect);
		String firstIncorrecParamPlaceFullToken = PlaceHandlerHelper
					.getFirstIncorrectParamPlace(currentFullTokenWithoutRedirect, placeHierarchyProvider.getPlaceRoot());
		
		if(firstIncorrecParamPlaceFullToken!=null && !firstIncorrecParamPlaceFullToken.equals(currentFullToken)) {
			PlaceRequestEvent pre = new PlaceRequestEvent(firstIncorrecParamPlaceFullToken);
			pre.setNeedWindowReload(needWindowReload);
			eventBus.fireEvent(pre);
			return;
		}
			
		//param place redirect
		if(place instanceof ParamPlace && firstIncorrecParamPlaceFullToken==null) {
			eventBus.fireEvent(generateSubMenuEvent(((ParamPlace) place).getChildToken()));
			return;
		}
		
		//selector widget update
		Node<InePlace> pointer= placeNode;
		while(pointer!=null) {
			InePlace pointerPlace = pointer.getNodeElement();
			if(pointerPlace instanceof ParamPlace && ((ParamPlace) pointerPlace).getSelectorPresenter()!=null) {
				((ParamPlace) pointerPlace).getSelectorPresenter().realizeUrlParams(urlParams);
			}
			
			pointer=pointer.getParent();
		}

		// change the browsers token if does not mach current token
		if (!historyProvider.getToken().equals(currentFullToken))
			historyProvider.newItem(currentFullToken);
		
		if(needWindowReload) {
			Window.Location.reload();
			
		} else {
			
			if (specificAdjustPlaceShouldReturn(place))
				return;
			
			masterPage.render(place, urlParams);
		}

	}

	protected abstract boolean specificAdjustPlaceShouldReturn(InePlace place);

	public void back() {
		historyProvider.back();
	}
	
	
	private void openInNewWindow(String url) {
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
				PlaceRequestEvent pre = new PlaceRequestEvent(loginPlace +
								QUESTION_MARK +
								REDIRECT +
								EQUALS_SIGN +
								currentFullTokenWithoutRedirect);
				pre.setNeedWindowReload(needWindowReload);
				eventBus.fireEvent(pre);
				
				return false;
			}else if (authManager.isUserLoggedIn() && (allowedRolesForPlace == null || allowedRolesForPlace.size() == 0)) {
				return true;
			} else if (authManager.doUserHaveAnyOfRoles(allowedRolesForPlace.toArray(new String[allowedRolesForPlace.size()]))){
				return true;				
			} else {
				masterPage.renderForbidden(place);
				return false;
			}
		} else return true;
	}
	
//------- ------------------------------
//        navigation helper methods
//------- ------------------------------
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
	
	public String getCurrentFullToken(){
		return currentFullToken;
	}

	public String getPreviousToken() {
		return previousToken;
	}

}
