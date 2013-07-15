package com.inepex.ineForm.client.datamanipulator;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.client.table.AbstractIneTable.SelectionBehaviour;
import com.inepex.ineForm.client.table.AbstractIneTable.UserCommand;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class RowCommandDataManipulator extends DataManipulator {
	private final RightSideButtonsPanel rightSideButtonsPanel = new RightSideButtonsPanel();

	protected final IneButton newButton = new IneButton(IneButton.IFButtonType.ACTION, IneFormI18n.NEW());
	
	private String editText;
	private String deleteText;
	
	protected List<UserCommand> userCommands = new ArrayList<UserCommand>();
	
	@Inject
	public RowCommandDataManipulator(FormContext formCtx
			, FormFactory formFactory
			, @Assisted String objectDescriptorName
			, @Assisted IneDataConnector ineDataConnector
			, @Assisted boolean sortable
			, TableFieldRenderer fieldRenderer) {
		super(formCtx, formFactory, objectDescriptorName, ineDataConnector, sortable, fieldRenderer);
		
		if(IneFormProperties.OLD_STYLE_COMPATIBILITY) {
			editText = IneFormI18n.EDIT();
			deleteText = IneFormI18n.DELETE();
		} else {
			editText = "<div class='"+ResourceHelper.ineformRes().style().tableEditIcon()+"' title='"+IneFormI18n.EDIT()+"'></div>";
			deleteText = "<div class='"+ResourceHelper.ineformRes().style().tableDeleteIcon()+"' title='"+IneFormI18n.DELETE()+"'></div>";
		}
		
		userCommands.add(new EditCommand());
		userCommands.add(new DeleteCommand());
	}

	@Override
	protected void initilaizeIneTableAndBuildCustom() {
		ineTable.setSelectionBehaviour(SelectionBehaviour.NO_SELECTION);
		
		if(userCommands!=null) {
			for (UserCommand command : userCommands) {
				ineTable.addCommand(command);		
			}
		}
		
		mainPanel.add(rightSideButtonsPanel);
		rightSideButtonsPanel.getCenterPanel().add(ineTable);
		setTopPanelWidget(newButton);
		newButton.addStyleName("newBtn");
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

	public class EditCommand implements UserCommand {
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

	public class DeleteCommand implements UserCommand {
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
	
	public void setNewBtnVisible(boolean visible) {
		newButton.setVisible(visible);
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

	/**
	 * edit userCommands list before call render()
	 * by default it contains edit and delete
	 * @return
	 */
	public List<UserCommand> getUserCommands() {
		return userCommands;
	}

	
}
