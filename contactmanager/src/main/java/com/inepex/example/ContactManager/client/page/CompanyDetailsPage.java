package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.assist.CompanyAssist;
import com.inepex.example.ContactManager.entity.kvo.CompanyKVO;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;

public class CompanyDetailsPage extends FlowPanelBasedPage {
	
	private final FormContext formContext;
	private final IneForm form;
	
	@Inject
	CompanyDetailsPage(FormContext formContext) {
		this.formContext=formContext;
		
		form= new IneForm(formContext, CompanyKVO.descriptorName, CompanyAssist.roFRD);
		form.renderForm();
		mainPanel.add(form.asWidget());
	}
	
	@Override
	public void setUrlParameters(Map<String, String> urlParams, final UrlParamsParsedCallback callback) throws Exception {
		new ObjectFinder<CompanyKVO>(CompanyKVO.descriptorName,
				Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_COMPANY)), formContext.ineDispatch)
					.executeFind(new ObjectFinder.Callback<CompanyKVO>() {

						@Override
						public void onObjectFound(CompanyKVO foundObject) {
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
