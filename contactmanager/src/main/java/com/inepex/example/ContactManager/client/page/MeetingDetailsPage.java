package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.assist.MeetingAssist;
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class MeetingDetailsPage extends FlowPanelBasedPage {
	
	private final FormContext formContext;
	private final IneForm form;
	
	@Inject
	MeetingDetailsPage(FormContext formContext) {
		this.formContext=formContext;
		
		form= new IneForm(formContext, MeetingConsts.descriptorName, MeetingAssist.roFRD);
		form.renderForm();
		mainPanel.add(form.asWidget());
	}
	
	@Override
	public void setUrlParameters(Map<String, String> urlParams, final UrlParamsParsedCallback callback) throws Exception {
		new ObjectFinder<AssistedObject>(MeetingConsts.descriptorName,
				Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_MEETING)), formContext.ineDispatch)
					.executeFind(new ObjectFinder.Callback<AssistedObject>() {

						@Override
						public void onObjectFound(AssistedObject foundObject) {
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
