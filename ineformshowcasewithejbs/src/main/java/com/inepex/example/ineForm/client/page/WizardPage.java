package com.inepex.example.ineForm.client.page;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.example.ineForm.entity.kvo.ContactConsts;
import com.inepex.ineForm.client.form.CustomCode;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.WizardForm;
import com.inepex.ineForm.client.form.WizardForm.CustomClickHandler;
import com.inepex.ineForm.client.form.formunits.AbstractFormUnit;
import com.inepex.ineForm.client.form.panelwidgets.DisplayedFormUnitChangeHandler.DisplayedFormUnitChangeResponse;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;
import com.inepex.ineForm.client.form.widgets.StringListBoxFw;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineom.shared.validation.ValidationResult;

public class WizardPage extends FlowPanel {

	@Inject
	public WizardPage(IneDispatch dispatcher, EventBus eventBus, DataConnectorFactory dataConnectorFactory, FormFactory formFactory
			) {
		add(new HTML("<h2>Example wizard for contact editing</h2>"));
		
		WizardForm wizardForm = formFactory.createWizard(ContactConsts.descriptorName, "wizard"
				, dataConnectorFactory.createServerSide(ContactConsts.descriptorName), null);
		
		wizardForm.renderForm();
		
		((StringListBoxFw)wizardForm.getFormUnitByName("step1").getWidgetByKey(ContactConsts.k_address)).setValueRange(Arrays.asList("egy", "ketto"));
		
		wizardForm.setCustomClickHandler(new CustomClickHandler() {
			
			@Override
			public void onCustomClick(Integer displayedPage, String label) {
				if (label.equals("cust1")) Window.alert("cust1 pressed");
				else if (label.equals("cust2")) Window.alert("cust2 pressed");
			}
		});
		
		add(wizardForm.asWidget());
		
		wizardForm.setCustomCode(new CustomCode() {
			
			@Override
			public void execute(
					IneForm form,
					PanelWidget sourcePanel,
					List<AbstractFormUnit> exForms,
					DisplayedFormUnitChangeResponse<?> responseObject) {
				int from =  (Integer) responseObject.getFrom();
				int to =  (Integer) responseObject.getTo();
				
				if (from == 0 && to == 1) responseObject.onSuccess();
				
				else {
					ValidationResult vr = new ValidationResult();
					vr.setGeneralErrors(Arrays.asList("Hello world"));
					form.dealValidationResult(vr);
					responseObject.onCancel();
				}
				
			}
		});
		
	}
}
