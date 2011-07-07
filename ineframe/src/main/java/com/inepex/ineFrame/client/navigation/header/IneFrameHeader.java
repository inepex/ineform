package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.PlaceHierarchyProvider;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineom.shared.descriptor.Node;

@Singleton
public class IneFrameHeader extends FlowPanel {
	
	//FIXME
//	@Inject(optional=true)
	private AuthManager<AuthStatusResultBase> authManager;
	
	@Inject
	PlaceHierarchyProvider placeHierarchyProvider;
	
	private boolean inited=false;
	private HTML userName;
	private Image settingsImg;
	
	
	private void init() {
		inited=true;
		
		setStyleName(ResourceHelper.getRes().style().header());
		
		Image logo = new Image(ResourceHelper.getRes().logo());
		logo.setStyleName(ResourceHelper.getRes().style().logo());
		add(logo);
		
		settingsImg = new Image(ResourceHelper.getRes().settings());
		settingsImg.getElement().getStyle().setFloat(Float.RIGHT);
		settingsImg.getElement().getStyle().setCursor(Cursor.POINTER);
		add(settingsImg);
		
		userName=new HTML("buzag@inepex.com");
		userName.setStyleName(ResourceHelper.getRes().style().settingsUserName());
		add(userName);
	}

	public void refresh() {
		if(!inited) { 
			init();
		}
		
//		//user name
//		if(authManager!=null && authManager.isUserLoggedIn()) {
//			userName.setVisible(true);
//			userName.setHTML(authManager.getLastAuthStatusResult().getFirstName()
//					+"&nbps;"+authManager.getLastAuthStatusResult().getLastName());
//		} else {
//			userName.setVisible(false);
//		}
//		
//		//settings img
//		Node<InePlace> settingsNode = placeHierarchyProvider.getPlaceRoot().findNodeById(DefaultPlaceHierarchyProvider.SETTINGS);
//		if(settingsNode!=null && settingsNode.getChildren()!=null && settingsNode.getChildren().size()>0) {
//			settingsImg.setVisible(true);
//		} else {
//			settingsImg.setVisible(false);
//		}
		
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		//TODO settings img
	}

}
