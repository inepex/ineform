package com.inepex.example.ineForm.client.page;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.kvo.ContactConsts;
import com.inepex.example.ineForm.entity.kvo.ContactHandlerFactory;
import com.inepex.example.ineForm.entity.kvo.ContactHandlerFactory.ContactHandler;
import com.inepex.ineForm.client.datamanipulator.DataManipulator;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.client.table.IneTable.RowStylesProvider;
import com.inepex.ineForm.client.table.IneTable.UserCommand;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class TesterPage extends FlowPanel {
	
	ContactHandlerFactory contactHandlerFactory;

	@Inject
	public TesterPage(IneDispatch dispatcher
					, FormContext formCtx
					, DescriptorStore descStore
					, DataConnectorFactory connectorFactory
					, ManipulatorFactory manipulatorFactory
					, ContactHandlerFactory contactHandlerFactory) {
		this.contactHandlerFactory = contactHandlerFactory;
		IneDataConnector dataConnector = connectorFactory.createServerSide(ContactConsts.descriptorName);
		
		IneTable ineTable = new IneTable(descStore
				, ContactConsts.descriptorName
				, descStore.getDefaultTypedDesc(ContactConsts.descriptorName, TableRDesc.class)
				, dataConnector);
	
		ineTable.setRowStylesProvider(new CustomRowDisplayModifier());
		ineTable.setCommandsTitle("Actions");
		ineTable.addCommands(new CustomAlertUserCommand()
							, new CustomAlertUserCommand2()
							, new CustomAlertUserCommand());
		
		ineTable.renderTable();
		
		add(ineTable);
				
		DataManipulator contactDataManipulator 
		 = manipulatorFactory.createRowCommand(ContactConsts.descriptorName, dataConnector, true);
		contactDataManipulator.render();
	
		add(contactDataManipulator);
	}
	
	private class CustomAlertUserCommand implements UserCommand {

		@Override
		public String getCommandCellText() {
			return "Alert me!";
		}

		@Override
		public void onCellClicked(AssistedObject kvoOfRow) {
			ContactHandler handler = contactHandlerFactory.createHandler(kvoOfRow);
			Window.alert("Clicked line: "+kvoOfRow.getId()+":"+handler.getString(ContactConsts.k_firstName));
		}

		@Override
		public boolean visible(AssistedObject kvoOfRow) {
			return true;
		}
		
	}
	
	private class CustomAlertUserCommand2 implements UserCommand {

		@Override
		public String getCommandCellText() {
			return "Say hello!";
		}

		@Override
		public void onCellClicked(AssistedObject kvoOfRow) {
			Window.alert("Hello!");
		}

		@Override
		public boolean visible(AssistedObject kvoOfRow) {
			return true;
		}
		
	}
	
	private class CustomRowDisplayModifier implements RowStylesProvider {

		@Override
		public String getStyleNames(AssistedObject row, int rowIndex) {
			if(row.getId()==2) 
				return "MainID2_CustomRowDisplayModifier";
			return null;
		}
		
	}
}
