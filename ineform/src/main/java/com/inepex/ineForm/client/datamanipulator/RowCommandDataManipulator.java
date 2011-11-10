package com.inepex.ineForm.client.datamanipulator;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.IneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.IneTable.UserCommand;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class RowCommandDataManipulator extends DataManipulator {
	private final RightSideButtonsPanel rightSideButtonsPanel = new RightSideButtonsPanel();

	protected final Button newButton = new Button(IneFormI18n.NEW());
	
	private String editText = IneFormI18n.EDIT();
	private String deleteText = IneFormI18n.DELETE();
	
	@Inject
	public RowCommandDataManipulator(FormContext formCtx
			, FormFactory formFactory
			, @Assisted String objectDescriptorName
			, @Assisted IneDataConnector ineDataConnector
			, @Assisted boolean sortable
			, AssistedObjectTableFieldRenderer fieldRenderer) {
		super(formCtx, formFactory, objectDescriptorName, ineDataConnector, sortable, fieldRenderer);
		
	}

	@Override
	protected void initilaizeIneTableAndBuildCustom() {
		ineTable.setSelectionBehaviour(SelectionBehaviour.NO_SELECTION);
		
		if(commands()!=null) {
			for (UserCommand command : commands()) {
				ineTable.addCommand(command);		
			}
		}
		
		mainPanel.add(rightSideButtonsPanel);
		rightSideButtonsPanel.getCenterPanel().add(ineTable);
		setTopPanelWidget(newButton);
		newButton.addStyleName("newBtn");
	}
	
	/**
	 * Override this to set custom commands
	 */
	protected List<UserCommand> commands(){
		UserCommand[] commands = {new EditCommand()
		   , new DeleteCommand()};
		return Arrays.asList(commands);

	}
	
	@Override
	protected void onAttach() {
		registerHandler(newButton.addClickHandler(new AddDataClickHandler()));
		super.onAttach();
	}
	
	protected class AddDataClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			onAddDataClicked();
		}
	}

	protected class EditCommand implements UserCommand {
		public EditCommand() {
		}

		@Override
		public String getCommandCellText() {
			return editText;
		}

		@Override
		public void onCellClicked(AssistedObject kvoOfRow) {
			onEditClicked(kvoOfRow);
		}

		@Override
		public boolean visible(AssistedObject kvoOfRow) {
			return true;
		}
	}

	protected class DeleteCommand implements UserCommand {
		public DeleteCommand() {
		}
		
		@Override
		public String getCommandCellText() {
			return deleteText;
		}

		@Override
		public void onCellClicked(AssistedObject kvoOfRow) {
			onDeleteClicked(kvoOfRow);
		}

		@Override
		public boolean visible(AssistedObject kvoOfRow) {
			return true;
		}
	}

	@Override
	public void setNewText(String newText) {
		newButton.setText(newText);
	}

	@Override
	public void setEditText(String editText) {
		this.editText = editText;
	}

	@Override
	public void setDeleteText(String deleteText) {
		this.deleteText = deleteText;
	}
	
	public void setNewBtnStyle(String styleName){
		newButton.setStyleName(styleName);
	}

	
}
