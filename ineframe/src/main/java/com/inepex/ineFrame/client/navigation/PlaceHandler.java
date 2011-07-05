package com.inepex.ineFrame.client.navigation;

import static com.inepex.ineFrame.client.navigation.NavigationProperties.REDIRECT;
import static com.inepex.ineFrame.client.navigation.NavigationProperties.defaultPlace;
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
	protected AuthManager<?> authManager;
	protected HistoryProvider historyProvider;
	protected EventBus eventBus;

	private String currentFullToken = null;

	public PlaceHandler(PlaceHierarchyProvider placeHierarchyProvider, MasterPage masterPage, AuthManager<?> authManager,
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
		currentFullToken =  e.getHierarchicalTokensWithParam();
		if (e.isOpenInNewWindow()){
			openInNewWindow("#" + currentFullToken);
		} else {
			realizePlaceChange();
		}
	}

	private void realizePlaceChange() {
		Node<InePlace> placeNode = placeHierarchyProvider.getPlaceRoot().findNodeByHierarchicalId(
				PlaceHandlerHelper.getPlacePart(currentFullToken));
		
		if (placeNode == null) {
			historyProvider.newItem(wrongTokenPlace);
			return;
		}
		
		PlaceHandlerHelper.updateHierarchicalTokens(currentFullToken, placeHierarchyProvider.getPlaceRoot());

		InePlace place = placeNode.getNodeElement();

		if (place.isAuthenticationNeeded() && !authManager.isUserLoggedIn()) {
			eventBus.fireEvent(new PlaceRequestEvent(
					defaultPlace+
					QUESTION_MARK+
					REDIRECT+
					EQUALS_SIGN+
					currentFullToken));
			return;
		}

		if (place.isAuthenticationNeeded()) {
			List<String> allowedRolesForPlace = place.getRolesAllowed();

			if (allowedRolesForPlace == null || allowedRolesForPlace.size() == 0
					|| !authManager.doUserHaveAnyOfRoles(allowedRolesForPlace.toArray(new String[allowedRolesForPlace.size()]))) {
				masterPage.renderForbidden(place);
			}
			
			return;
		}
		
		if (place instanceof ChildRedirectPlace) {
			ChildRedirectPlace cdPlace = (ChildRedirectPlace) place;
			eventBus.fireEvent(new PlaceRequestEvent(PlaceHandlerHelper.appendChild(currentFullToken, cdPlace.getChildToken())));
			return;
		}
		
		//param place checking
		Map<String, String> urlParams = PlaceHandlerHelper.getUrlParameters(currentFullToken);
		String firstIncorrecParamPlaceFullToken = PlaceHandlerHelper
					.getFirstIncorrectParamPlace(currentFullToken, placeHierarchyProvider.getPlaceRoot());
		
		if(firstIncorrecParamPlaceFullToken!=null && !firstIncorrecParamPlaceFullToken.equals(currentFullToken)) {
			eventBus.fireEvent(new PlaceRequestEvent(firstIncorrecParamPlaceFullToken));
			return;
		}
		
		//selector widget update
		if(place instanceof ParamPlace && firstIncorrecParamPlaceFullToken==null) {
			eventBus.fireEvent(generateSubMenuEvent(((ParamPlace) place).getChildToken()));
			return;
		}

		if (specificAdjustPlaceShouldReturn(place))
			return;
		
		masterPage.render(place, urlParams);
		
		// change the browsers token if does not mach current token
		if (!historyProvider.getToken().equals(currentFullToken))
			historyProvider.newItem(currentFullToken);

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
	
	
//------- ------------------------------
//        navigation helper methods
//------- ------------------------------
	public PlaceRequestEvent generateSubMenuEvent(String... subMenuTokens) {
		PlaceRequestEvent event = new PlaceRequestEvent();
		event.setHierarchicalTokensWithParam(PlaceHandlerHelper.createSubMenuToken(currentFullToken, subMenuTokens));
		return event;
	}

	public PlaceRequestEvent generateJumpUpEvent() {
		PlaceRequestEvent event = new PlaceRequestEvent();
		event.setHierarchicalTokensWithParam(PlaceHandlerHelper.createUpToken(currentFullToken));
		return event;
	}
	
	public PlaceRequestEvent generateSameLevelMenuEvent(String... subMenuTokens) {
		PlaceRequestEvent event = new PlaceRequestEvent();
		event.setHierarchicalTokensWithParam(PlaceHandlerHelper.createSameLevelMenuToken(currentFullToken, subMenuTokens));
		return event;
	}
}
