package com.inepex.example.ContactManager.client.page;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.SaveCancelForm.ValidateMode;
import com.inepex.ineForm.client.form.events.CancelledEvent;
import com.inepex.ineForm.client.form.events.SavedEvent;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;

public class NewCompanyPage extends FlowPanelBasedPage
    implements
    SavedEvent.Handler,
    CancelledEvent.Handler {

    private final FormContext formContext;
    private final PlaceHandler placeHandler;

    private final ServerSideDataConnector connector;
    private final SaveCancelForm form;

    @Inject
    NewCompanyPage(FormContext formContext, PlaceHandler placeHandler, FormFactory formFactory) {
        this.formContext = formContext;
        this.placeHandler = placeHandler;

        connector =
            new ServerSideDataConnector(
                formContext.ineDispatch,
                formContext.eventBus,
                CompanyConsts.descriptorName);
        form = formFactory.createSaveCancel(CompanyConsts.descriptorName, null, connector, null);
        form.setValidateData(ValidateMode.PARTIAL);
        form.renderForm();
        mainPanel.add(form.asWidget());
    }

    @Override
    protected void onShow(boolean isFirstShow) {
        form.resetValuesToEmpty();
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        registerHandler(form.addSavedHandler(this));
        registerHandler(form.addCancelledHandler(this));
    }

    @Override
    public void onCancelled(CancelledEvent event) {
        formContext.eventBus.fireEvent(placeHandler.generateJumpUpEvent());
    }

    @Override
    public void onSaved(SavedEvent event) {
        formContext.eventBus.fireEvent(placeHandler.generateSameLevelMenuEvent(PlaceHandlerHelper
            .appendParam(
                AppPlaceHierarchyProvider.COMPANIES,
                AppPlaceHierarchyProvider.PARAM_COMPANY,
                event.getObjectManipulationResult().getObjectsNewState().getId().toString())));
    }

}
