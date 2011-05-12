package com.inepex.ineFrame.client.navigation;

import static com.inepex.ineFrame.client.navigation.NavigationProperties.*;

import java.util.HashMap;
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

	public static String getFullTokenFromPlaceRequestEvent(PlaceRequestEvent pre) {
		StringBuffer sb = new StringBuffer();
		
		appendTokenPart(sb, pre);

		Map<String, String> paramMap = getParamMapFromEvent(pre);
		if (paramMap == null)
			return sb.toString();
		
		sb.append(QUESTION_MARK);

		appendParameters(sb, paramMap);
		
		return sb.toString();
	}
	
	private static void appendTokenPart(StringBuffer sb, PlaceRequestEvent pre) {
		for (String hierarchicalTokenParts : pre.getHierarchicalToken()) {
			sb.append(hierarchicalTokenParts).append(Node.ID_SEPARATOR);
		}
		sb.delete(sb.length() - 1, sb.length());
	}
	
	private static Map<String, String> getParamMapFromEvent(PlaceRequestEvent pre) {
		String[] parameters = pre.getParameters();
		if (parameters == null || parameters.length == 0)
			return null;
		
		if (parameters.length % 2 != 0)
			throw new RuntimeException("number of parameters in PlaceRequestEvent must be multiple of 2");

		Map<String, String> paramMap = null;
		paramMap = new HashMap<String, String>(parameters.length / 2);
		for (int i = 0; i < parameters.length; i += 2) {
			paramMap.put(parameters[i], parameters[i + 1]);
		}
		
		return paramMap;
	}
	
	private static void appendParameters(StringBuffer sb, Map<String, String> paramMap) {
		if (paramMap == null || paramMap.size() == 0)
			return;
		
		for (String key : paramMap.keySet()) {
			sb.append(key).append(EQUALS_SIGN);
			sb.append(paramMap.get(key)).append(AND_SIGN);
		}
		sb.delete(sb.length() - 1, sb.length());
	}

	public void firePlaceRequestEvent(String fullTokenWithParams) {
		PlaceRequestEvent pre = new PlaceRequestEvent(fullTokenWithParams);
		eventBus.fireEvent(pre);
	}

	private String getPlacePart() {
		String[] parts = currentFullToken.split(regExp(QUESTION_MARK));
		if (parts.length == 0)
			return "";
		return parts[0];
	}

	private Map<String, String> getUrlParameters() {
		Map<String, String> urlParams = new HashMap<String, String>();
		String[] parts = currentFullToken.split(regExp(QUESTION_MARK));
		if (parts.length < 2)
			return urlParams;

		// parameter "redirect" behaves another way!
		if (parts[1].indexOf(REDIRECT) == 0) {
			String redirectParam = parts[1].substring(REDIRECT.length() + 1);
			if (parts.length > 2)
				redirectParam = redirectParam + QUESTION_MARK + parts[2];
			urlParams.put(REDIRECT, redirectParam);
			return urlParams;
		}

		String params[] = parts[1].split(regExp(AND_SIGN));
		for (String string : params) {
			String[] keyValue = string.split(regExp(EQUALS_SIGN));
			if (keyValue.length == 2)
				urlParams.put(keyValue[0], keyValue[1]);
		}
		return urlParams;
	}

	private String regExp(String str) {
		return "[" + str + "]";
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (event.getValue().equals(currentFullToken))
			return;
		else
			firePlaceRequestEvent(event.getValue());
	}

	@Override
	public void onPlaceRequest(PlaceRequestEvent e) {
		currentFullToken =  getFullTokenFromPlaceRequestEvent(e);
		if (e.isOpenInNewWindow()){
			openInNewWindow("#" + currentFullToken);
		} else {
			realizePlaceChange();
		}
	}

	private void realizePlaceChange() {
		Node<InePlace> placeNode = placeHierarchyProvider.getPlaceRoot().findNodeByHierarchicalId(getPlacePart());

		if (placeNode == null) {
			historyProvider.newItem(wrongTokenPlace);
			return;
		}

		InePlace place = placeNode.getNodeElement();

		// TODO: too specific functionality!
		if (place.isAuthenticationNeeded() && !authManager.isUserLoggedIn()) {
			eventBus.fireEvent(new PlaceRequestEvent(defaultPlace, REDIRECT, historyProvider.getToken()));
			return;
		}

		checkRoleIfAuthNeeded(place);

		if (place instanceof ChildRedirectPlace) {
			ChildRedirectPlace cdPlace = (ChildRedirectPlace) place;
			firePlaceRequestEvent(getPlacePart() + Node.ID_SEPARATOR + cdPlace.getChildToken());
			return;
		}

		if (specificAdjustPlaceShouldReturn(place))
			return;

		masterPage.render(place, getUrlParameters());
		
		// change the browsers token if does not mach current token
		if (!historyProvider.getToken().equals(currentFullToken))
			historyProvider.newItem(currentFullToken);

	}

	protected abstract boolean specificAdjustPlaceShouldReturn(InePlace place);

	private void checkRoleIfAuthNeeded(InePlace place) {
		if (place.isAuthenticationNeeded()) {
			List<String> allowedRolesForPlace = place.getRolesAllowed();

			if (allowedRolesForPlace == null || allowedRolesForPlace.size() == 0
					|| !authManager.doUserHaveAnyOfRoles(allowedRolesForPlace.toArray(new String[allowedRolesForPlace.size()]))) {
				masterPage.renderForbidden(place);
			}
		}
	}

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
}
