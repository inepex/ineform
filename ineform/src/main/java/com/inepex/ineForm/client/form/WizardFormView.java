package com.inepex.ineForm.client.form;

import com.inepex.ineForm.client.form.WizardForm.NavWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelWidget;

public interface WizardFormView extends SaveCancelFormView {

    public static interface Delegate extends SaveCancelFormView.Delegate {
        void prevClick();

        void nextClick();

        void customClick(Integer displayedPage, String label);
    }

    public void turnOn();

    public void setNextButtonText(String nextButtonText);

    public void setPreviousButtonText(String previousButtonText);

    public void setNavWidget(NavWidget navWidget);

    public void setRootStepper(StepperPanelWidget rootStepper);

    public void setDisplayedPage(int index, boolean needCustomCodeCallback);

    public void refreshButtonsAndNavWidget();

    public StepperPanelWidget getRootStepper();

}
