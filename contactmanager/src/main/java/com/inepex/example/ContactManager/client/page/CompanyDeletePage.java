package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.ineForm.client.general.IFButton;
import com.inepex.ineForm.client.general.IFButton.IFButtonType;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.IneDataConnector.ManipulateResultCallback;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulationResult;

public class CompanyDeletePage extends FlowPanelBasedPage {

	private final ServerSideDataConnector connector;
	
	private final EventBus eventBus;
	private final PlaceHandler placeHandler;
	
	private final HTML html;
	private final IFButton button;
	
	private Long companyId;
	
	@Inject
	CompanyDeletePage(IneDispatch dispatcher, EventBus eventBus, PlaceHandler placeHandler) {
		this.placeHandler=placeHandler;
		this.eventBus=eventBus;
		
		connector = new ServerSideDataConnector(dispatcher, eventBus, CompanyConsts.descriptorName);
		
		html= new HTML(CMI18n.reallyWantToDeleteCompany());
		mainPanel.add(html);
		
		mainPanel.add(new HTML("<br />"));
		
		button= new IFButton(IFButtonType.ACTION, IneFormI18n.DELETE());
		mainPanel.add(button);
	}
	
	@Override
	public void setUrlParameters(Map<String, String> urlParams,
			UrlParamsParsedCallback callback) throws Exception {
		
		companyId=Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_COMPANY));
		callback.onUrlParamsParsed();
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		registerHandler(button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				KeyValueObject kvo = new KeyValueObject(CompanyConsts.descriptorName);
				kvo.setId(companyId);
				connector.objectDeleteRequested(kvo,new ManipulateResultCallback() {

					@Override
					public void onManipulationResult(ObjectManipulationResult result) {
						if(result.isSuccess()) {
							eventBus.fireEvent(placeHandler.generateJumpUpEvent());
						}
					}
					
				});
			}
		}));
	}
	
	
	@Override
	protected void onShow(boolean isFirstShow) {
	}

}
