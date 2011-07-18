package com.inepex.ineForm.client.form;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelPageWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelWidget;
import com.inepex.ineForm.client.i18n.IneFormI18n_old;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.ineom.shared.kvo.IFConsts;

public class WizardForm extends SaveCancelForm {

	private class CustomBtnClickHandler implements ClickHandler {

		String btnLabel;

		public CustomBtnClickHandler(String btnLabel) {
			super();
			this.btnLabel = btnLabel;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (customClickHandler != null) customClickHandler.onCustomClick(rootStepper.getDisplayedPageNumber(), btnLabel);
		}
	}
	
	public interface CustomClickHandler {
		public void onCustomClick(Integer displayedPage, String label);
	}
	
	private StepperPanelWidget rootStepper;
	
	private String nextButtonText=IneFormI18n_old.NEXT();
	private String previousButtonText=IneFormI18n_old.PREVIOUS();
	
	private NavWidget navWidget;
	private Button nextButton = new Button();
	private Button previousButton = new Button();
	
	private List<Button> custButtons = new ArrayList<Button>();
	private List<HandlerRegistration> custHandlerRegs = new ArrayList<HandlerRegistration>();
	
	private CustomClickHandler customClickHandler;
	
	private String wizardBtnStyleName = "wizardBtn";
	
	private String customBtnStyleName = "custBtn";
	
	@Inject
	public WizardForm(FormContext formCtx,
					@Assisted("dn") String descriptorName,
					@Assisted("frdn") String formRDescName,
					@Assisted IneDataConnector ineDataConnector) {
		super(formCtx, descriptorName, formRDescName, ineDataConnector);
	}
	
	//-------------------- buttons text

	public void setNextButtonText(String nextButtonText) {
		this.nextButtonText = nextButtonText;
	}

	public void setPreviousButtonText(String previousButtonText) {
		this.previousButtonText = previousButtonText;
	}
	
	public void setNavWidget(NavWidget navWidget) {
		this.navWidget = navWidget;
	}

	@Override
	public void renderForm() {
		if (navWidget == null)
			navWidget = new LabelNavWidget();
		
		mainPanel.add(navWidget);
		super.renderForm();
		buttonPanel.insert(previousButton, 1);
		buttonPanel.insert(nextButton, 2);
		
		rootStepper=(StepperPanelWidget) rootPanel;
						
		refreshButtonsAndNavWidget();
	}
	
	@Override
	protected void onMainPanelAttach(MainPanel panel) {
		super.onMainPanelAttach(panel);
		panel.registerHandler(previousButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				rootStepper.setDisplayedPage(rootStepper.getDisplayedPageNumber()-1, true);
			}
		}));
		
		panel.registerHandler(nextButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				rootStepper.setDisplayedPage(rootStepper.getDisplayedPageNumber()+1, true);
			}
		}));	
	}
	
	
	/**
	 * this method uses standard Ineform resetValues and set the displayed page to the first
	 */
	@Override
	public void resetValuesToInitialData() {
		super.resetValuesToInitialData();
		rootStepper.setDisplayedPage(0, false);
		refreshButtonsAndNavWidget();
	}
	
	public void showPage(int i) {
		rootStepper.setDisplayedPage(i, false);
		refreshButtonsAndNavWidget();
	}
	
	
	public void refreshButtonsAndNavWidget() {
		//default behaviour
		nextButton.setText(nextButtonText);
		previousButton.setText(previousButtonText);
		
		nextButton.setStyleName(wizardBtnStyleName);
		previousButton.setStyleName(wizardBtnStyleName);
		
		saveButton.setEnabled(rootStepper.getDisplayedPageNumber()+1==rootStepper.getPageCount());
		nextButton.setEnabled(rootStepper.getDisplayedPageNumber()+1!=rootStepper.getPageCount());
		previousButton.setEnabled(rootStepper.getDisplayedPageNumber()!=0);
		navWidget.showPage(rootStepper.getDisplayedPageNumber(), rootStepper.getPageCount());
		
		nextButton.setVisible(true);
		previousButton.setVisible(true);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);
		
		if ((rootStepper.getDisplayedPage() != null && rootStepper.getDisplayedPage().getDescriptor().getProps().keySet().size() > 0))
		{			
			PanelWidgetRDesc desc = rootStepper.getDisplayedPage().getDescriptor();
			processButtonProperties(desc, 
					StepperPanelPageWidget.Param.nextVisible, 
					StepperPanelPageWidget.Param.nextLabel,
					nextButton);
			processButtonProperties(desc, 
					StepperPanelPageWidget.Param.prevVisible, 
					StepperPanelPageWidget.Param.prevLabel,
					previousButton);
			processButtonProperties(desc, 
					StepperPanelPageWidget.Param.saveVisible, 
					StepperPanelPageWidget.Param.saveLabel,
					saveButton);
			processButtonProperties(desc, 
					StepperPanelPageWidget.Param.cancelVisible, 
					StepperPanelPageWidget.Param.cancelLabel,
					cancelButton);
			
			for (HandlerRegistration hr : custHandlerRegs){
				hr.removeHandler();
			}
			custHandlerRegs.clear();
			
			for (Button btn : custButtons){
				btn.removeFromParent();
			}
			custButtons.clear();
	
			if (desc.hasProp(StepperPanelPageWidget.Param.custButtons)){
				String[] custButtons = desc.getPropValue(StepperPanelPageWidget.Param.custButtons).split(",");
				for (String btnLabel : custButtons){
					Button btn = new Button(btnLabel);
					btn.setStyleName(customBtnStyleName);
					this.custButtons.add(btn);
					custHandlerRegs.add(btn.addClickHandler(new CustomBtnClickHandler(btnLabel)));
					buttonPanel.add(btn);
				}
			}
		}
	}
	
	private void processButtonProperties(PanelWidgetRDesc desc, String visibleParam, String labelParam, Button btn){
		if (desc.hasProp(visibleParam) 
				|| desc.hasProp(labelParam)) {
			boolean visible = desc.getPropValue(visibleParam).equals(IFConsts.TRUE) 
				|| desc.hasProp(labelParam);
			btn.setVisible(visible);
			btn.setEnabled(visible);
			if (desc.hasProp(labelParam)) {
				btn.setHTML(desc.getPropValue(labelParam));
			}
		}
	}
	
	@Override
	public void onPanelWidgetRefreshed(PanelWidget sourcePanel) {
		super.onPanelWidgetRefreshed(sourcePanel);
		if (sourcePanel==rootStepper) 
			refreshButtonsAndNavWidget();
	}
	
	public interface NavWidget extends IsWidget {
		public void showPage(int displayedPage, int pageCount);
	}
	
	public class LabelNavWidget extends Label implements NavWidget {

		@Override
		public void showPage(int displayedPage, int pageCount) {
			setText((displayedPage+1)+"/"+pageCount);
		}
	}

	public void setCustomClickHandler(CustomClickHandler customClickHandler) {
		this.customClickHandler = customClickHandler;
	}

	public void setCustomBtnStyleName(String customBtnStyleName) {
		this.customBtnStyleName = customBtnStyleName;
	}

	public void setWizardBtnStyleName(String wizardBtnStyleName) {
		this.wizardBtnStyleName = wizardBtnStyleName;
	}	
	

}
