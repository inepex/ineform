package com.inepex.ineFrame.client.navigation.header;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;

@Singleton
public class IneFrameHeaderView extends HandlerAwareFlowPanel implements IneFrameHeader.View {
	
	private Widget logo;
	private Image menuIcon = new Image(ResourceHelper.getRes().headerDrawerIcon());
	private OnClickedLogic menuIconClickedLogic;
	private OnClickedLogic logoClickedLogic;
	private FlexTable menuTable = new FlexTable();

	
	@Inject
	IneFrameHeaderView(HeaderViewLogo logoCreator) {
		logo = logoCreator.createLogo();
		buildStructure();
		setStyleNames();
	}
	
	private void buildStructure() {
		add(menuTable);
		menuTable.setWidget(0, 0, menuIcon);
		menuTable.setWidget(0, 1, logo);
	}
	
	private void setStyleNames(){
		setStyleName(ResourceHelper.getRes().style().header());
		menuTable.setCellPadding(0);
		menuTable.setCellSpacing(0);
		menuTable.setStyleName( ResourceHelper.getRes().style().menuTable());
		menuTable.getFlexCellFormatter().setStyleName(0, 0, ResourceHelper.getRes().style().menuCell());
		menuTable.getFlexCellFormatter().setStyleName(0, 1, ResourceHelper.getRes().style().menuCell());
	}

	
	@Override
	protected void onAttach() {
		super.onAttach();
		registerHandler(menuIcon.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (menuIconClickedLogic != null) {
					menuIconClickedLogic.doLogic();
				}
			}
		}));
		if(logo instanceof HasClickHandlers){
			registerHandler(((HasClickHandlers) logo).addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if(logoClickedLogic != null){
						logoClickedLogic.doLogic();
					}
				}
			}));
		}
	}
	
	@Override
	public void setMenuIconClickedLogic(OnClickedLogic logic) {
		menuIconClickedLogic = logic;
	}

	@Override
	public void setLanguageSelectorVisible(boolean visible) {
		
	}

	@Override
	public void setLogoClickedLogic(OnClickedLogic logic) {
		this.logoClickedLogic = logic;
	}	
}
