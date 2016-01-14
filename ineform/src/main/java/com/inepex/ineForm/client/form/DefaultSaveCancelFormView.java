package com.inepex.ineForm.client.form;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.WizardForm.NavWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelPageWidget;
import com.inepex.ineForm.client.form.panelwidgets.StepperPanelWidget;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.Color;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.PanelWidgetRDesc;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineom.shared.IFConsts;

public class DefaultSaveCancelFormView extends HandlerAwareFlowPanel implements WizardFormView {

    private class SaveClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            delegate.saveClicked();
        }
    }

    private class CancelClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            delegate.cancelClicked();
        }
    }

    public class LabelNavWidget extends Label implements NavWidget {

        @Override
        public void showPage(int displayedPage, int pageCount) {
            setText((displayedPage + 1) + "/" + pageCount);
        }
    }

    private class CustomBtnClickHandler implements ClickHandler {

        String btnLabel;

        public CustomBtnClickHandler(String btnLabel) {
            super();
            this.btnLabel = btnLabel;
        }

        @Override
        public void onClick(ClickEvent event) {
            ((WizardFormView.Delegate) delegate).customClick(
                rootStepper.getDisplayedPageNumber(),
                btnLabel);
        }
    }

    protected final FlowPanel buttonPanel = new FlowPanel();

    private String saveButtonText = IneFormI18n.SAVE();
    private String cancelButtonText = IneFormI18n.CANCEL();

    protected final IneButton saveButton = new IneButton(Color.GREEN);
    protected final IneButton cancelButton = new IneButton(Color.GRAY);
    private final IneButton nextButton = new IneButton(Color.BLUE);
    private final IneButton previousButton = new IneButton(Color.BLUE);

    private final List<IneButton> custButtons = new ArrayList<IneButton>();
    private final List<HandlerRegistration> custHandlerRegs = new ArrayList<HandlerRegistration>();

    private SaveCancelFormView.Delegate delegate;

    private boolean isWizardOn = false;

    private StepperPanelWidget rootStepper;

    private String nextButtonText = IneFormI18n.NEXT();
    private String previousButtonText = IneFormI18n.PREVIOUS();

    private NavWidget navWidget;

    public DefaultSaveCancelFormView() {
        for (IneButton btn : new IneButton[] { cancelButton, previousButton, nextButton, saveButton }) {
            btn.getElement().getStyle().setMarginRight(5, Unit.PX);
            btn.getElement().getStyle().setMarginBottom(3, Unit.PX);
        }
    }

    @Override
    public void setDelegate(SaveCancelFormView.Delegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void setFormWidget(Widget formwidget) {
        if (isWizardOn) {
            if (navWidget == null)
                navWidget = new LabelNavWidget();

            add(navWidget);
        }

        add(formwidget);
        add(buttonPanel);
        buttonPanel.setStyleName(ResourceHelper.ineformRes().style().saveCancelButtonDiv());
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        saveButton.setText(saveButtonText);
        cancelButton.setText(cancelButtonText);

        if (isWizardOn) {
            buttonPanel.insert(previousButton, 1);
            buttonPanel.insert(nextButton, 2);
        }
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        registerHandler(saveButton.addClickHandler(new SaveClickHandler()));
        registerHandler(cancelButton.addClickHandler(new CancelClickHandler()));
        registerHandler(previousButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                rootStepper.setDisplayedPage(rootStepper.getDisplayedPageNumber() - 1, true);
            }
        }));
        registerHandler(nextButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                rootStepper.setDisplayedPage(rootStepper.getDisplayedPageNumber() + 1, true);
            }
        }));

    }

    @Override
    public void setSaveButtonText(String saveButtonText) {
        this.saveButtonText = saveButtonText;
    }

    @Override
    public void setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
    }

    @Override
    public void setSaveBtnStyle(String style) {
        saveButton.setStyleName(style);
    }

    @Override
    public void setCancelBtnStyle(String style) {
        cancelButton.setStyleName(style);
    }

    @Override
    public void setSaveButtonVisible(boolean visible) {
        saveButton.setVisible(visible);
    }

    @Override
    public void setCancelButtonVisible(boolean visible) {
        cancelButton.setVisible(visible);
    }

    @Override
    public void setNextButtonText(String nextButtonText) {
        this.nextButtonText = nextButtonText;
    }

    @Override
    public void setPreviousButtonText(String previousButtonText) {
        this.previousButtonText = previousButtonText;
    }

    @Override
    public void setNavWidget(NavWidget navWidget) {
        this.navWidget = navWidget;
    }

    @Override
    public void turnOn() {
        isWizardOn = true;
    }

    @Override
    public void setRootStepper(StepperPanelWidget rootStepper) {
        this.rootStepper = rootStepper;
    }

    @Override
    public void refreshButtonsAndNavWidget() {
        // default behaviour
        nextButton.setText(nextButtonText);
        previousButton.setText(previousButtonText);

        saveButton.setEnabled(rootStepper.getDisplayedPageNumber() + 1 == rootStepper
            .getPageCount());
        nextButton.setEnabled(rootStepper.getDisplayedPageNumber() + 1 != rootStepper
            .getPageCount());
        previousButton.setEnabled(rootStepper.getDisplayedPageNumber() != 0);
        navWidget.showPage(rootStepper.getDisplayedPageNumber(), rootStepper.getPageCount());

        nextButton.setVisible(true);
        previousButton.setVisible(true);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);

        if ((rootStepper.getDisplayedPage() != null && rootStepper
            .getDisplayedPage()
            .getDescriptor()
            .getProps()
            .keySet()
            .size() > 0)) {
            PanelWidgetRDesc desc = rootStepper.getDisplayedPage().getDescriptor();
            processButtonProperties(
                desc,
                StepperPanelPageWidget.Param.nextVisible,
                StepperPanelPageWidget.Param.nextLabel,
                nextButton);
            processButtonProperties(
                desc,
                StepperPanelPageWidget.Param.prevVisible,
                StepperPanelPageWidget.Param.prevLabel,
                previousButton);
            processButtonProperties(
                desc,
                StepperPanelPageWidget.Param.saveVisible,
                StepperPanelPageWidget.Param.saveLabel,
                saveButton);
            processButtonProperties(
                desc,
                StepperPanelPageWidget.Param.cancelVisible,
                StepperPanelPageWidget.Param.cancelLabel,
                cancelButton);

            for (HandlerRegistration hr : custHandlerRegs) {
                hr.removeHandler();
            }
            custHandlerRegs.clear();

            for (IneButton btn : custButtons) {
                btn.removeFromParent();
            }
            custButtons.clear();

            if (desc.hasProp(StepperPanelPageWidget.Param.custButtons)) {
                String[] custButtons =
                    desc.getPropValue(StepperPanelPageWidget.Param.custButtons).split(",");
                for (String btnLabel : custButtons) {
                    IneButton btn = new IneButton(Color.GRAY, btnLabel);
                    this.custButtons.add(btn);
                    custHandlerRegs.add(btn.addClickHandler(new CustomBtnClickHandler(btnLabel)));
                    buttonPanel.add(btn);
                }
            }
        }
    }

    private void processButtonProperties(
        PanelWidgetRDesc desc,
        String visibleParam,
        String labelParam,
        IneButton btn) {
        if (desc.hasProp(visibleParam) || desc.hasProp(labelParam)) {
            boolean visible =
                (desc.hasProp(visibleParam) && desc
                    .getPropValue(visibleParam)
                    .equals(IFConsts.TRUE)) || desc.hasProp(labelParam);
            btn.setVisible(visible);
            btn.setEnabled(visible);
            if (desc.hasProp(labelParam)) {
                btn.setHTML(desc.getPropValue(labelParam));
            }
        }
    }

    @Override
    public void setDisplayedPage(int index, boolean needCustomCodeCallback) {
        rootStepper.setDisplayedPage(0, false);
    }

    @Override
    public StepperPanelWidget getRootStepper() {
        return rootStepper;
    }

    @Override
    public void addSaveBtnStyle(String style) {
        saveButton.addStyleName(style);
    }

    @Override
    public void addCancelBtnStyle(String style) {
        cancelButton.addStyleName(style);
    }

    @Override
    public void setFormValidationSuccess(boolean isSuccess) {}

    @Override
    public void dataReseted() {}

    @Override
    public void forceLoadingOnSaveBtn(boolean loading) {
        saveButton.setEnabled(!loading);
    }

}
