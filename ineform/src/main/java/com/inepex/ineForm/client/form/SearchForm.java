package com.inepex.ineForm.client.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.IneButtonType;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class SearchForm extends IneForm {
	private FlowPanel mainPanel = new FlowPanel(); 
	private IneForm searchForm;
	private Grid buttons = new Grid(1, 3);
	private IneButton doSearch = new IneButton(IneButtonType.ACTION, IneFormI18n.SEARCH());
	private IneButton doReset = new IneButton(IneButtonType.ACTION, IneFormI18n.RESET());
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
		
		doReset.setVisible(false);
		
		doSearch.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				AssistedObject searchParams =  getSearchParams();
				if (!isEmpty(searchParams)){
					doReset.setVisible(true);
					message.setText(IneFormI18n.searchForm_filtered());	
				} else {
					doReset.setVisible(false);
					message.setText("");	
				}
				setSearchParamsForDataConnector(searchParams);				
			}
		});
		
		doReset.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				resetFieldsAndSendSearch();
				message.setText("");
				doReset.setVisible(false);
			}
		});
		
	}
	
	@Override
	public Widget asWidget() {
		return mainPanel;
	}
	
	private boolean isEmpty(AssistedObject searchParams){
		for (String key : searchParams.getBooleanKeys()){
			if (searchParams.getBooleanUnchecked(key) != null) return false;
		}
		for (String key : searchParams.getStringKeys()){
			if (searchParams.getStringUnchecked(key) != null) return false;
		}
		for (String key : searchParams.getLongKeys()){
			if (searchParams.getLongUnchecked(key) != null) return false;
		}
		
		return true;
	}
	
	private AssistedObject getSearchParams(){
		AssistedObject currentState;
		currentState = searchForm.getValues(searchForm.getInitialOrEmptyData());
		return currentState;
	}
	
	private void setSearchParamsForDataConnector(AssistedObject searchParams){
		dataConnector.setSearchParameters(searchParams);
		dataConnector.update();
	}
	
	private void resetFieldsAndSendSearch(){
		searchForm.resetValuesToEmpty();
		setSearchParamsForDataConnector(searchForm.getInitialOrEmptyData());
	}

}
