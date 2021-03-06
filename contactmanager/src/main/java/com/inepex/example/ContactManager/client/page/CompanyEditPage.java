package com.inepex.example.ContactManager.client.page;

import java.util.Arrays;
import java.util.Map;

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
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class CompanyEditPage extends FlowPanelBasedPage
        implements SavedEvent.Handler, CancelledEvent.Handler {

    private final FormContext formContext;
    private final PlaceHandler placeHandler;

    private final ServerSideDataConnector connector;
    private final SaveCancelForm form;

    @Inject
    CompanyEditPage(FormContext formContext, PlaceHandler placeHandler, FormFactory formFactory) {
        this.formContext = formContext;
        this.placeHandler = placeHandler;

        connector = new ServerSideDataConnector(
            formContext.ineDispatch,
            formContext.eventBus,
            CompanyConsts.descriptorName);
        connector.setAssociatedManipulateAction(
            new ObjectManipulationAction(Arrays.asList(CompanyConsts.propUser)));
        form = formFactory.createSaveCancel(CompanyConsts.descriptorName, null, connector, null);
        form.setValidateData(ValidateMode.PARTIAL);
        form.renderForm();
        mainPanel.add(form.asWidget());
    }

    @Override
    public void setUrlParameters(
        Map<String, String> urlParams,
        final UrlParamsParsedCallback callback) throws Exception {
        formContext.objectFinder.executeFind(
            CompanyConsts.descriptorName,
            Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_COMPANY)),
            Arrays.asList(CompanyConsts.propUser),
            new ObjectFinder.Callback() {

                @Override
                public void onObjectFound(AssistedObject foundObject) {
                    form.resetValuesToEmpty();
                    form.setInitialData(foundObject);
                    callback.onUrlParamsParsed();
                }
            });
    }

    @Override
    protected void onShow(boolean isFirstShow) {}

    @Override
    protected void onLoad() {
        super.onLoad();
        registerHandler(form.addSavedHandler(this));
        registerHandler(form.addCancelledHandler(this));
    }

    @Override
    public void onCancelled(CancelledEvent event) {
        formContext.eventBus.fireEvent(
            placeHandler.generateSameLevelMenuEvent(AppPlaceHierarchyProvider.COMPDETAILS));
    }

    @Override
    public void onSaved(SavedEvent event) {
        formContext.eventBus.fireEvent(
            placeHandler.generateSameLevelMenuEvent(AppPlaceHierarchyProvider.COMPDETAILS));
    }

}
