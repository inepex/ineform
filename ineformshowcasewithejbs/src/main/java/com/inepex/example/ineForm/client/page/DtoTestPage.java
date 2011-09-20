package com.inepex.example.ineForm.client.page;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineFrame.client.async.IneDispatch;

public class DtoTestPage extends FlowPanel {

	@Inject
	public DtoTestPage(IneDispatch dispatcher, DataConnectorFactory connectorFactory, FormFactory formFactory) {
//		IneDataConnector connector =
//			connectorFactory.createServerSide(ContactDtoKVO.descriptorName);
//		
//		SaveCancelForm form = 
//			formFactory.createSaveCancel(ContactDtoKVO.descriptorName, null, connector, null);
//		
//		form.renderForm();
//		this.add(form.asWidget());
//		
//		ContactDtoKVO contact = new ContactDtoKVO();
//		contact.setName("Dto test contact");
//		contact.setRelatedRandomValue(431434124321L);
//		contact.setVerySecretParameter("very secret paramter");
//		
//		form.setInitialData(contact);
	}
}
