package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class SettingsPopup extends DialogBox {

	@Inject PlaceHierarchyProvider hierarchyProvider;
	@Inject EventBus eventBus;
	
	boolean inited = false;
	VerticalPanel panel;
	
	@Inject
	SettingsPopup() {
		super(false, false);
	}
	
	private void init() {
		inited=true;
		
		panel = new VerticalPanel();
		for(Node<InePlace> placeNode : hierarchyProvider.getPlaceRoot().findNodeById(NavigationProperties.SETTINGS).getChildren()) {
				panel.add(new PlaceButton(placeNode.getHierarchicalId(), placeNode.getNodeElement()));
		}
		
		add(panel);
	}
	
	@Override
	public void show() {
		if(!inited)
			init();
		
		super.show();
	}
	
	
	private class PlaceButton extends HandlerAwareComposite {
		
		private final Label label;
		private final String hierarchicalId;
		
		public PlaceButton(String hierarchicalId, InePlace nodeElement) {
			this.hierarchicalId=hierarchicalId;
			
			label=new Label(nodeElement.getMenuName());
			label.getElement().getStyle().setCursor(Cursor.POINTER);
			initWidget(label);
		}
		
		@Override
		protected void onAttach() {
			super.onAttach();
			
			registerHandler(label.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					eventBus.fireEvent(new PlaceRequestEvent(hierarchicalId));
				}
			}));
		}
		
	}
}
