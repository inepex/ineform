package com.inepex.ineFrame.client.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.page.InePage.UrlParamsParsedCallback;
import com.inepex.ineom.shared.descriptor.Node;

class ParamPlaceRedirectionCallbackHandler {
	
	public static interface AfterRedirectionLogic {
		public void afterRedirectionHandled(String token);
	}
	
	private final List<String> redirections = new ArrayList<String>();
	private final AfterRedirectionLogic afterRedirectionLogic;
	private final Map<String, String> urlParams;
	private final String requestedToken;
	
	private int waitingCounter = 0;
	private boolean canFinish = false;
	private Node<InePlace> pointer;
	
	public ParamPlaceRedirectionCallbackHandler(String requestedToken, Node<InePlace> pointer, Map<String, String> urlParams
			, AfterRedirectionLogic afterRedirectionLogic) {
		this.afterRedirectionLogic = afterRedirectionLogic;
		this.requestedToken = requestedToken;
		this.pointer=pointer;
		this.urlParams=urlParams;
	}
	
	public void execute() {
		while(pointer!=null) {
			InePlace pointerPlace = pointer.getNodeElement();
			if(pointerPlace instanceof ParamPlace) {
				waitCallback((ParamPlace) pointerPlace);
			}			
			pointer=pointer.getParent();
		}
		setCanFinish();
	}
	
	private void waitCallback(ParamPlace paramPlace){
		waitingCounter++;
		paramPlace.processParams(requestedToken, urlParams, new UrlParamsParsedCallback() {
			
			@Override
			public void onUrlParamsParsed(String redirection) {
				waitingCounter--;
				redirections.add(redirection);
				finish();
			}

			@Override
			public void onUrlParamsParsed() {
				onUrlParamsParsed(null);
			}
		});
	}
	
	private void finish(){
		if (canFinish && waitingCounter == 0 && afterRedirectionLogic != null){
			afterRedirectionLogic.afterRedirectionHandled(getFirstRedirection());
		}	
	}
	
	private String getFirstRedirection(){
		if (redirections.size() > 0)
			return redirections.get(0);
		else return null;
	}

	private void setCanFinish() {
		this.canFinish = true;
		finish();
	}
	
}