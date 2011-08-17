package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.assist.ContactAssist;
import com.inepex.example.ContactManager.entity.kvo.ContactKVO;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;

public class ContactDetailsPage extends FlowPanelBasedPage {
	
	private final FormContext formContext;
	private final IneForm form;
	
	@Inject
	ContactDetailsPage(FormContext formContext) {
		this.formContext=formContext;
		
		form= new IneForm(formContext, ContactKVO.descriptorName, ContactAssist.roFRD);
		form.renderForm();
		mainPanel.add(form.asWidget());
	}
	
	@Override
	public void setUrlParameters(Map<String, String> urlParams, final UrlParamsParsedCallback callback) throws Exception {
		new ObjectFinder<ContactKVO>(ContactKVO.descriptorName,
				Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_CONTACT)), formContext.ineDispatch)
					.executeFind(new ObjectFinder.Callback<ContactKVO>() {

						@Override
						public void onObjectFound(ContactKVO foundObject) {
							form.resetValuesToEmpty();
							form.setInitialData(foundObject);
							callback.onUrlParamsParsed();
						}
				});
	}

	@Override
	protected void onShow(boolean isFirstShow) {
	}
}