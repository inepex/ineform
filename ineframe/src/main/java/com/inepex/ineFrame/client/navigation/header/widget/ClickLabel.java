package com.inepex.ineFrame.client.navigation.header.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;

public class ClickLabel extends HandlerAwareComposite {
	
	private final Label lbl;
	private final OnClickedLogic logic;
	
	public ClickLabel(String text, OnClickedLogic logic) {
		lbl=new Label(text);
		this.logic=logic;
		
		initWidget(lbl);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(lbl.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(logic!=null)
					logic.doLogic();
			}
		}));
	}
}
