	package com.inepex.example.ineForm.client.page;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.core.module.client.CommandDataConnectorFactory;
import com.inepex.example.ineForm.entity.kvo.ContactKVO;
import com.inepex.example.ineForm.entity.kvo.ContactTypeKVO;
import com.inepex.example.ineForm.entity.kvo.NationalityKVO;
import com.inepex.example.ineForm.entity.kvo.search.ContactSearchKVO;
import com.inepex.ineForm.client.datamanipulator.DataManipulator;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.SearchForm;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.util.ExportUtil;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction.Renderer;
import com.inepex.ineFrame.client.async.IneDispatch;

public class CrudPage extends FlowPanel {
	
	final IneDispatch dispatcher;
	final EventBus eventBus;
	final DataConnectorFactory dataConnectorFactory;
	final FormFactory formFactory;
	final ManipulatorFactory manipulatorFactory;
	
	FlowPanel searchPanel = new FlowPanel();
	FlowPanel contactPanel = new FlowPanel();
	FlowPanel contactTypePanel = new FlowPanel();
	FlowPanel nationalityPanel = new FlowPanel();

	
	
	@Inject
	public CrudPage(IneDispatch dispatcher, EventBus eventBus, DataConnectorFactory dataConnectorFactory, FormFactory formFactory,
			ManipulatorFactory manipulatorFactory) {
		super();
		this.dispatcher = dispatcher;
		this.eventBus = eventBus;
		this.dataConnectorFactory = dataConnectorFactory;
		this.formFactory = formFactory;
		this.manipulatorFactory = manipulatorFactory;
		add(new HTML("<h2>Manage contacts</h2>"));
		add(searchPanel);
		add(contactPanel);
		add(new HTML("<h2>Manage contact types</h2>"));
		add(contactTypePanel);
		add(new HTML("<h2>Manage nationalities</h2>"));
		add(nationalityPanel);
				
		// Contact manipulator
		IneDataConnector contactDataConn = dataConnectorFactory.createServerSide(ContactKVO.descriptorName);
//		IneDataConnector contactDataConn = dataConnectorFactory.create(ContactKVO.descriptorName);
		
		SearchForm searchForm = formFactory.createSearch(ContactSearchKVO.descriptorName
				, null , contactDataConn);
		
		Button exportContacts = new Button("Export");
		exportContacts.addClickHandler(new ExportUtil.ExportClickHandler(
				dispatcher
				, new ObjectListAction(ContactKVO.descriptorName)
				, "contacts.csv"
				, Renderer.CSV
				, true
				, true));
		
		DataManipulator contactManipulator 
			= manipulatorFactory.createRowCommand(ContactKVO.descriptorName, contactDataConn, true);
		
		searchPanel.add(searchForm.asWidget());		
		contactPanel.add(contactManipulator);
		contactPanel.add(exportContacts);
		contactManipulator.setPageSize(10);
		contactManipulator.render();
		
		// ContactType manipulator
		IneDataConnector contactTypeDataConn = 
			dataConnectorFactory.createServerSide(ContactTypeKVO.descriptorName);

		DataManipulator contactTypeManipulator 
			= manipulatorFactory.createRowCommand(ContactTypeKVO.descriptorName
															, contactTypeDataConn
															, true);		
		contactTypePanel.add(contactTypeManipulator);
		contactTypeManipulator.setPageSize(10);
		contactTypeManipulator.render();
		
		// Nationality manipulator
		IneDataConnector nationalityDataConn = 
			dataConnectorFactory.createServerSide(NationalityKVO.descriptorName);

		DataManipulator nationalityManipulator 
			= manipulatorFactory.createRowCommand(NationalityKVO.descriptorName
															, nationalityDataConn
															, true);
		Button exportNats = new Button("Export");
		exportNats.addClickHandler(new ExportUtil.ExportClickHandler(
				dispatcher
				, new ObjectListAction(NationalityKVO.descriptorName)
				, "nationalities.html"
				, Renderer.HTML
				, true
				, true));
		nationalityPanel.add(nationalityManipulator);
		nationalityPanel.add(exportNats);
		nationalityManipulator.setPageSize(10);
		nationalityManipulator.render();
	}
	
}
