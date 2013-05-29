package com.inepex.translatorapp.client.page;

import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.ineForm.client.datamanipulator.DataManipulator;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.datamanipulator.RowCommandDataManipulator;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.events.RenderedEvent;
import com.inepex.ineForm.client.form.widgets.StringListBoxFw;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.TXT;
import com.inepex.translatorapp.shared.kvo.UserConsts;

public class UserListPage extends FlowPanelBasedPage{

	private final ServerSideDataConnector connector;
	private final RowCommandDataManipulator rowCommandDataManipulator;
	
	@Inject
	public UserListPage(ManipulatorFactory manipulatorFactory, DataConnectorFactory connectorFactory) {
		connector=connectorFactory.createServerSide(UserConsts.descriptorName);
		rowCommandDataManipulator=manipulatorFactory.createRowCommand(UserConsts.descriptorName, connector, true);
		rowCommandDataManipulator.setTopPanelWidget(new HTML(translatorappI18n.userListTitle()));
		rowCommandDataManipulator.getUserCommands().clear();
		rowCommandDataManipulator.getUserCommands().add(rowCommandDataManipulator.new EditCommand());
		rowCommandDataManipulator.setFormCreationCallback(new DataManipulator.FormCreationCallback() {
			
			@Override
			public void onCreatingEditForm(final IneForm ineForm) {
				ineForm.addRenderedHandler(new RenderedEvent.Handler() {
					
					@Override
					public void onRendered(RenderedEvent event) {
						StringListBoxFw listbox =  (StringListBoxFw) ineForm.getRootPanelWidget().getFormUnits().get(0).getWidgetByKey(UserConsts.k_role);
						listbox.setAllowsNull(true);
						listbox.setValueRange(TXT.Roles.all());
					}
				});
			}
			
			@Override
			public void onCreatingCreateForm(IneForm ineForm) {
			}
		});
		
		rowCommandDataManipulator.setNewBtnVisible(false);
		
		rowCommandDataManipulator.render();
		
		
		mainPanel.add(rowCommandDataManipulator);
	}
	
	@Override
	protected void onShow(boolean isFirstShow) {
		if(!isFirstShow)
			connector.update();
	}
}
