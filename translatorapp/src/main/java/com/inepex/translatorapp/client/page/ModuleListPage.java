package com.inepex.translatorapp.client.page;

import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.datamanipulator.RowCommandDataManipulator;
import com.inepex.ineForm.client.datamanipulator.RowCommandDataManipulator.DeleteCommand;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;

public class ModuleListPage extends FlowPanelBasedPage {

	private final ServerSideDataConnector connector;
	
	@Inject
	ModuleListPage(DataConnectorFactory connectorFactory,
			ManipulatorFactory manipulatorFactory,
			final AssistedObjectHandlerFactory handlerFactory) {
		connector=connectorFactory.createServerSide(ModuleConsts.descriptorName);
		
		mainPanel.add(new HTML(translatorappI18n.moduleListTitle()));
		
		final RowCommandDataManipulator manipulator=manipulatorFactory.createRowCommand(ModuleConsts.descriptorName, connector, true);
		manipulator.getUserCommands().clear();
		manipulator.getUserCommands().add(manipulator.new EditCommand());
		manipulator.getUserCommands().add(new IneTable.UserCommand() {
			
			DeleteCommand origDeletCmd = manipulator.new DeleteCommand();
			
			@Override
			public boolean visible(AssistedObject kvoOfRow) {
				IneList list = handlerFactory.createHandler(kvoOfRow).getList(ModuleConsts.k_langs);
				return list==null || list.getRelationList()==null || list.getRelationList().isEmpty();
			}
			
			@Override
			public void onCellClicked(AssistedObject kvoOfRow) {
				origDeletCmd.onCellClicked(kvoOfRow);
			}
			
			@Override
			public String getCommandCellText() {
				return origDeletCmd.getCommandCellText();
			}
		});
		
		manipulator.render();
		mainPanel.add(manipulator);
	}
	
	@Override
	protected void onShow(boolean isFirstShow) {
		if(!isFirstShow)
			connector.update();
	}

}
