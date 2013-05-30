package com.inepex.translatorapp.client.page;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.ListBoxFW;
import com.inepex.ineForm.client.form.widgets.RadioEnumSelectorFW;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.client.table.IneTableFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.translatorapp.shared.action.TransTableListAction;
import com.inepex.translatorapp.shared.action.TranslateListingType;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.TranslateTableRowConsts;

public class TranslatorPage extends FlowPanelBasedPage {

	private final RadioEnumSelectorFW listTypeRadioButton;
	private final ListBoxFW moduleListBox;
	private final Button filterBtn;
	
	private final ServerSideDataConnector connector;
	private final TransTableListAction action;
	
	private final IneTable table;	
	
	@Inject
	public TranslatorPage(DataConnectorFactory connectorFactory, IneTableFactory tableFactory, FormContext formCtx) {
		listTypeRadioButton = new RadioEnumSelectorFW(
				new LongFDesc().setNullable(false),
				TranslateListingType.getValuesAsString(),
				new WidgetRDesc());
		
		mainPanel.add(listTypeRadioButton);
		
		moduleListBox = new ListBoxFW(formCtx, new RelationFDesc("", "", ModuleConsts.descriptorName).setNullable(true), new WidgetRDesc());
		mainPanel.add(moduleListBox);
		
		filterBtn= new Button(IneFormI18n.FILTER());
		mainPanel.add(filterBtn);
		
		action=new TransTableListAction();
		
		connector = connectorFactory.createServerSide(TranslateTableRowConsts.descriptorName);
		connector.setAssociatedListAction(action);
		
		table = tableFactory.createSimple(TranslateTableRowConsts.descriptorName, connector);
		table.renderTable();
		
		mainPanel.add(table);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		
		registerHandler(filterBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fillActionAndUpdate();
			}
		}));
	}
	

	@Override
	protected void onShow(boolean isFirstShow) {
		fillActionAndUpdate();
	}

	private void fillActionAndUpdate() {
		action.setListType(TranslateListingType.values()[listTypeRadioButton.getLongValue().intValue()]);
		if(moduleListBox.getRelationValue()==null)
			action.setModuleName(null);
		else
			action.setModuleName(moduleListBox.getRelationValue().getDisplayName());
		
		connector.update();
	}
	
}
