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
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.inei18n.client.IneFormI18n_old;
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
	public void resetValues() {
		super.resetValues();
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
			if (desc.hasProp(StepperPanelPageWidget.Param.nextVisible)
					|| desc.hasProp(StepperPanelPageWidget.Param.nextLabel)) {
				boolean visible = desc.getPropValue(StepperPanelPageWidget.Param.nextVisible).equals(IFConsts.TRUE) 
					|| desc.hasProp(StepperPanelPageWidget.Param.nextLabel);
				nextButton.setVisible(visible);
				nextButton.setEnabled(visible);
				if (desc.hasProp(StepperPanelPageWidget.Param.nextLabel)) {
					nextButton.setHTML(desc.getPropValue(StepperPanelPageWidget.Param.nextLabel));
				}
			}
			if (desc.hasProp(StepperPanelPageWidget.Param.prevVisible) 
					|| desc.hasProp(StepperPanelPageWidget.Param.prevLabel)) {
				boolean visible = desc.getPropValue(StepperPanelPageWidget.Param.prevVisible).equals(IFConsts.TRUE) 
					|| desc.hasProp(StepperPanelPageWidget.Param.prevLabel);
				previousButton.setVisible(visible);
				previousButton.setEnabled(visible);
				if (desc.hasProp(StepperPanelPageWidget.Param.prevLabel)) {
					previousButton.setHTML(desc.getPropValue(StepperPanelPageWidget.Param.prevLabel));
				}
			}
			if (desc.hasProp(StepperPanelPageWidget.Param.saveVisible) 
					|| desc.hasProp(StepperPanelPageWidget.Param.saveLabel)) {
				boolean visible = desc.getPropValue(StepperPanelPageWidget.Param.saveVisible).equals(IFConsts.TRUE) 
					|| desc.hasProp(StepperPanelPageWidget.Param.saveLabel);
				saveButton.setVisible(visible);
				saveButton.setEnabled(visible);
				if (desc.hasProp(StepperPanelPageWidget.Param.saveLabel)) {
					saveButton.setHTML(desc.getPropValue(StepperPanelPageWidget.Param.saveLabel));
				}
			}
			
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
					this.custButtons.add(btn);
					custHandlerRegs.add(btn.addClickHandler(new CustomBtnClickHandler(btnLabel)));
					buttonPanel.add(btn);
				}
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
	
	

}
