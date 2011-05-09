package com.inepex.example.ineForm.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.inepex.example.ineForm.dto.assist.ContactDtoAssist;
import com.inepex.example.ineForm.entity.assist.ContactAddresDetailAssist;
import com.inepex.example.ineForm.entity.assist.ContactAssist;
import com.inepex.example.ineForm.entity.assist.ContactCTypeRelAssist;
import com.inepex.example.ineForm.entity.assist.ContactNatRelAssist;
import com.inepex.example.ineForm.entity.assist.ContactTypeAssist;
import com.inepex.example.ineForm.entity.assist.Contact_ContactRoleAssist;
import com.inepex.example.ineForm.entity.assist.Contact_ContactStateAssist;
import com.inepex.example.ineForm.entity.assist.NationalityAssist;
import com.inepex.ineFrame.client.IneFrameEntryPoint;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class Main extends IneFrameEntryPoint {
	DescriptorStore descStore = null;
	private HorizontalPanel pages = new HorizontalPanel();
	private FlowPanel content = new FlowPanel();
		
	public static ShowcaseGinjector INJECTOR = GWT.create(ShowcaseGinjector.class);
	
	public Main() {
		super(INJECTOR.getDispatchAsync(), INJECTOR.getEventBus());
		descStore = INJECTOR.getDescriptorStore();
		

	}
	
	@Override
	public void onIneModuleLoad() {		
		new ContactAddresDetailAssist(descStore).registerDescriptors();
		new ContactAssist(descStore).registerDescriptors();
		new ContactCTypeRelAssist(descStore).registerDescriptors();
		new ContactNatRelAssist(descStore).registerDescriptors();
		new ContactTypeAssist(descStore).registerDescriptors();
		new NationalityAssist(descStore).registerDescriptors();
		new ContactDtoAssist(descStore).registerDescriptors();
		new Contact_ContactRoleAssist(descStore).registerDescriptors();
		new Contact_ContactStateAssist(descStore).registerDescriptors();
		
		RootPanel.get().add(pages);
		RootPanel.get().add(content);
		initPages();
	}

	
	private void initPages(){
		Button showCrud = new Button("Crud page");
		showCrud.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				content.clear();
				content.add(INJECTOR.getCrudPage());
			}
		});
		Button showTester = new Button("Tester page");
		showTester.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				content.clear();
				content.add(INJECTOR.getCrudPage());				
			}
		});

		Button showDtoTest = new Button("Dto Test page");
		showDtoTest.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				content.clear();
				content.add(INJECTOR.getDtoTestPage());				
			}
		});
		
		pages.add(showCrud);
		pages.add(showTester);
		pages.add(showDtoTest);
		
	}

	@Override
	protected void registerAdditionalI18nModules() {
	}

	@Override
	protected AuthManager<?> getAuthManager() {
		return null;
	}
	
	
}