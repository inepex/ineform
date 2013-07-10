package com.inepex.ineForm.client.form;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelWidget;
import com.inepex.ineForm.client.table.IneDataConnector;

public class WizardForm extends SaveCancelForm implements WizardFormView.Delegate {
	
	public interface CustomClickHandler {
		public void onCustomClick(Integer displayedPage, String label);
	}
	
	private CustomClickHandler customClickHandler;
	
	@Inject
	public WizardForm(FormContext formCtx,
					@Assisted("dn") String descriptorName,
					@Assisted("frdn") String formRDescName,
					@Assisted IneDataConnector ineDataConnector,
					@Assisted SaveCancelFormView view) {
		super(formCtx, descriptorName, formRDescName, ineDataConnector, view);
		getView().turnOn();
	}
	
	//-------------------- buttons text

	public void setNextButtonText(String nextButtonText) {
		getView().setNextButtonText(nextButtonText);
	}

	public void setPreviousButtonText(String previousButtonText) {
		getView().setPreviousButtonText(previousButtonText);
	}
	
	public void setNavWidget(NavWidget navWidget) {
		getView().setNavWidget(navWidget);
	}

	@Override
	public void renderForm() {
		super.renderForm();
		getView().setRootStepper((StepperPanelWidget)rootPanel);
		getView().refreshButtonsAndNavWidget();
	}
		
	/**
	 * this method uses standard Ineform resetValues and set the displayed page to the first
	 */
	@Override
	public void resetValuesToInitialData() {
		super.resetValuesToInitialData();
		getView().setDisplayedPage(0, false);
		getView().refreshButtonsAndNavWidget();
	}
	
	public void showPage(int i) {
		getView().setDisplayedPage(i, false);
		getView().refreshButtonsAndNavWidget();
	}
	
	@Override
	public void onPanelWidgetRefreshed(PanelWidget sourcePanel) {
		super.onPanelWidgetRefreshed(sourcePanel);
		if (sourcePanel==getView().getRootStepper()) 
			getView().refreshButtonsAndNavWidget();
	}
	
	public interface NavWidget extends IsWidget {
		public void showPage(int displayedPage, int pageCount);
	}

	public void setCustomClickHandler(CustomClickHandler customClickHandler) {
		this.customClickHandler = customClickHandler;
	}	
	
	private WizardFormView getView(){
		if (view instanceof WizardFormView) return (WizardFormView)view;
		else throw new RuntimeException("To use WizardForm you have to implement WizardFormView in your view");
	}

	@Override
	public void prevClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customClick(Integer displayedPage, String label) {
		if (customClickHandler != null) customClickHandler.onCustomClick(displayedPage, label);
	}

}
