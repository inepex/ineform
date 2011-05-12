package com.inepex.ineFrame.client.navigation;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;

public class HistoryProvider {

	public HandlerRegistration addHandler(ValueChangeHandler<String> handler) {
		return History.addValueChangeHandler(handler);
	}
	
	public void newItem(String historyToken){
		History.newItem(historyToken);
	}
	
	public String getToken(){
		return History.getToken();
	}

	public void fireCurrentHistoryState() {
		History.fireCurrentHistoryState();
	}
	
	public void back() {
		History.back();
	}
	
}
