package com.inepex.translatorapp.client.page;

import com.google.inject.Inject;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.client.table.IneTableFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.translatorapp.shared.action.TransTableListAction;
import com.inepex.translatorapp.shared.kvo.TranslateTableRowConsts;

public class TranslatorPage extends FlowPanelBasedPage {

	private final ServerSideDataConnector connector;
	private final IneTable table;
	
	@Inject
	public TranslatorPage(DataConnectorFactory connectorFactory, IneTableFactory tableFactory) {
		connector = connectorFactory.createServerSide(TranslateTableRowConsts.descriptorName);
		connector.setAssociatedListAction(new TransTableListAction());
		
		table = tableFactory.createSimple(TranslateTableRowConsts.descriptorName, connector);
		table.renderTable();
		
		mainPanel.add(table);
	}

	@Override
	protected void onShow(boolean isFirstShow) {
		if(!isFirstShow)
			connector.update();
	}
}
