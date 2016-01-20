package com.inepex.example.ContactManager.client.page;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory.ContactSearchHandler;
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.SaveCancelForm.ValidateMode;
import com.inepex.ineForm.client.form.ServerSideValueRangeProvider;
import com.inepex.ineForm.client.form.events.CancelledEvent;
import com.inepex.ineForm.client.form.events.SavedEvent;
import com.inepex.ineForm.client.form.widgets.ListBoxFW;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceHandlerHelper;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.dispatch.interfaces.RelationList;

public class NewMeetingPage extends FlowPanelBasedPage
        implements SavedEvent.Handler, CancelledEvent.Handler {

    private final FormContext formContext;
    private final PlaceHandler placeHandler;
    private final AuthManager authManager;

    private final ServerSideDataConnector connector;
    private final SaveCancelForm form;
    private final ContactHandlerFactory contactHandlerFactory;

    @Inject
    NewMeetingPage(
        FormContext formContext,
        PlaceHandler placeHandler,
        AuthManager authManager,
        ContactHandlerFactory contactHandlerFactory,
        FormFactory formFactory) {
        this.formContext = formContext;
        this.placeHandler = placeHandler;
        this.authManager = authManager;
        this.contactHandlerFactory = contactHandlerFactory;

        formContext.valueRangeProvider = new MeetingValueRangeProvider(formContext.ineDispatch);

        connector = new ServerSideDataConnector(
            formContext.ineDispatch,
            formContext.eventBus,
            MeetingConsts.descriptorName);
        form = formFactory.createSaveCancel(MeetingConsts.descriptorName, null, connector, null);
        form.setValidateData(ValidateMode.PARTIAL);
        form.renderForm();
        mainPanel.add(form.asWidget());
    }

    @Override
    protected void onShow(boolean isFirstShow) {
        form.resetValuesToEmpty();
        form.getRootPanelWidget().getFormUnits().get(0).setSingleWidgetValue(
            MeetingConsts.k_user,
            new Relation(authManager.getLastAuthStatusResult().getUserId(), ""));

    }

    @Override
    protected void onAttach() {
        super.onAttach();

        registerHandler(form.addSavedHandler(this));
        registerHandler(form.addCancelledHandler(this));

        registerHandler(
            form
                .getRootPanelWidget()
                .getFormUnits()
                .get(0)
                .getWidgetByKey(MeetingConsts.k_company)
                .addFormWidgetChangeHandler(new FormWidgetChangeHandler() {

                    @Override
                    public void onFormWidgetChange(FormWidgetChangeEvent e) {
                        ((ListBoxFW) form.getRootPanelWidget().getFormUnits().get(0).getWidgetByKey(
                            MeetingConsts.k_contact)).reLoadListAndKeepSelectedOrSetToNull();
                    }
                }));
    }

    @Override
    public void onCancelled(CancelledEvent event) {
        formContext.eventBus.fireEvent(placeHandler.generateJumpUpEvent());
    }

    @Override
    public void onSaved(SavedEvent event) {
        formContext.eventBus.fireEvent(
            placeHandler.generateSameLevelMenuEvent(
                PlaceHandlerHelper.appendParam(
                    AppPlaceHierarchyProvider.MEETINGS,
                    AppPlaceHierarchyProvider.PARAM_MEETING,
                    event.getObjectManipulationResult().getObjectsNewState().getId().toString())));
    }

    private class MeetingValueRangeProvider extends ServerSideValueRangeProvider {

        public MeetingValueRangeProvider(IneDispatch dispatch) {
            super(dispatch);
        }

        @Override
        protected RelationList getActionForDescriptorName(String descriptorName) {
            if (!ContactConsts.descriptorName.equals(descriptorName)
                || form.getRootPanelWidget() == null)
                return super.getActionForDescriptorName(descriptorName);
            else {
                ContactSearchHandler searchKVO = contactHandlerFactory.createSearchHandler();
                searchKVO
                    .setCompany(
                        form
                            .getRootPanelWidget()
                            .getFormUnits()
                            .get(0)
                            .getWidgetByKey(MeetingConsts.k_company)
                            .getRelationValue());
                return new RelationListAction(
                    ContactConsts.descriptorName,
                    searchKVO.getAssistedObject(),
                    0,
                    1000,
                    false);
            }
        }
    }
}
