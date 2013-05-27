package com.inepex.translatorapp.client.page;

import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;

public class DummyPage extends FlowPanelBasedPage {

	@Inject
	public DummyPage(DataConnectorFactory dataConnectorFactory, 
			ManipulatorFactory manipulatorFactory, 
			FormFactory formFactory) {
		mainPanel.add(new Label("Hello world from ineformproject"));
		
//		ServerSideDataConnector dataConnector = dataConnectorFactory.createServerSide(<xyKVO>.descriptorName);
//		RowCommandDataManipulator dataManipulator = manipulatorFactory.createRowCommand(
//				<xyKVO>.descriptorName, dataConnector, true);
//		dataManipulator.render();
//		mainPanel.add(dataManipulator.asWidget());
		
	}

	@Override
	protected void onShow(boolean isFirstShow) {
	}
	
}
