package com.inepex.ineForm.client.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class SearchForm extends IneForm {
	private FlowPanel mainPanel = new FlowPanel(); 
	private IneForm searchForm;
	private Grid buttons = new Grid(1, 3);
	private Button doSearch = new Button(IneFormI18n.SEARCH());
	private Button doReset = new Button(IneFormI18n.RESET());
	private Label message = new Label();
	
	private IneDataConnector dataConnector;

	/**
	 * a SimpleTableForm with a search and reset button...  
	 * 
	 * @param formCtx
	 * @param descriptorName
	 * @param formRDescName
	 * @param ineDataConnector
	 */
	@Inject
	public SearchForm(FormContext formCtx,
			@Assisted("dn") String descriptorName,
			@Assisted("frdn") String formRDescName,
			@Assisted IneDataConnector ineDataConnector) {
		super(formCtx, descriptorName, formRDescName);
		
		searchForm = this;
		dataConnector = ineDataConnector;
		
		renderForm();
		
		mainPanel.add(super.asWidget());
		mainPanel.add(buttons);
		buttons.setWidget(0, 0, doSearch);
		buttons.setWidget(0, 1, doReset);
		buttons.setWidget(0, 2, message);		
		
		doSearch.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setSearchParamsForDataConnector();
				message.setText(IneFormI18n.searchForm_filtered());
			}
		});
		
		doReset.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				resetFieldsAndSendSearch();
				message.setText("");
			}
		});
		
	}
	
	@Override
	public Widget asWidget() {
		return mainPanel;
	}
	
	private void setSearchParamsForDataConnector(){
		AssistedObject currentState;
		currentState = searchForm.getValues(searchForm.getInitialOrEmptyData());
		dataConnector.setSearchParametersAndUpdate(currentState);
	}
	
	private void resetFieldsAndSendSearch(){
		searchForm.resetValuesToInitialData();
		setSearchParamsForDataConnector();
	}

}
