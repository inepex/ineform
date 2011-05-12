package com.inepex.ineForm.client.form;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.form.panelwidgets.PanelWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelWidget;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.inei18n.client.IneFormI18n_old;

public class WizardForm extends SaveCancelForm {

	private StepperPanelWidget rootStepper;
	
	private String nextButtonText=IneFormI18n_old.NEXT();
	private String previousButtonText=IneFormI18n_old.PREVIOUS();
	
	private NavWidget navWidget;
	private Button nextButton = new Button();
	private Button previousButton = new Button();
	
	
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
				
		nextButton.setText(nextButtonText);
		previousButton.setText(previousButtonText);
		
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
		saveButton.setEnabled(rootStepper.getDisplayedPageNumber()+1==rootStepper.getPageCount());
		nextButton.setEnabled(rootStepper.getDisplayedPageNumber()+1!=rootStepper.getPageCount());
		previousButton.setEnabled(rootStepper.getDisplayedPageNumber()!=0);
		navWidget.showPage(rootStepper.getDisplayedPageNumber(), rootStepper.getPageCount());
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

}
