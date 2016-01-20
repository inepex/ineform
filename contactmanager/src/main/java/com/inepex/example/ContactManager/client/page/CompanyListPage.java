package com.inepex.example.ContactManager.client.page;

import static com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider.*;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.ineForm.client.table.AbstractIneTable.UserCommand;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.client.table.IneTableFactory;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.PlaceToken;
import com.inepex.ineFrame.client.page.FlowPanelBasedPageWithScroll;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class CompanyListPage extends FlowPanelBasedPageWithScroll {

    private IneTable table;
    private IneDataConnector dataConnector;
    private EventBus eventBus;

    @Inject
    public CompanyListPage(
        IneTableFactory tableFactory,
        DataConnectorFactory dataConnFactory,
        EventBus eventBus) {
        this.eventBus = eventBus;
        initTable(tableFactory, dataConnFactory);
    }

    private void initTable(IneTableFactory tableFactory, DataConnectorFactory dataConnFactory) {
        dataConnector = dataConnFactory.createServerSide(CompanyConsts.descriptorName); // 1
        table = tableFactory.createSimple(CompanyConsts.descriptorName, dataConnector);

        table.addCommand(getDetailsCommand()); // before render
        table.addCommand(getEditCommand());
        table.addCommand(getDeleteCommand());
        table.addCommand(getContactsCommand());
        table.renderTable(); // 2
        mainPanel.add(table);
    }

    @Override
    protected void onShow(boolean isFirstShow) {
        dataConnector.update(); // 3
    }

    private UserCommand getEditCommand() {
        return new UserCommand() {

            @Override
            public boolean visible(AssistedObject kvoOfRow) {
                return true;
            }

            @Override
            public void onCellClicked(AssistedObject kvoOfRow) {
                eventBus.fireEvent(
                    new PlaceRequestEvent(
                        new PlaceToken(LOGGEDIN)
                            .cld(COMPEDIT)
                            .appendParam(PARAM_COMPANY, "" + kvoOfRow.getId())));
            }

            @Override
            public String getCommandCellText(AssistedObject kvoOfRow) {
                return CMI18n.menu_COMPEDIT();
            }
        };
    }

    private UserCommand getDetailsCommand() {
        return new UserCommand() {

            @Override
            public boolean visible(AssistedObject kvoOfRow) {
                return true;
            }

            @Override
            public void onCellClicked(AssistedObject kvoOfRow) {
                eventBus.fireEvent(
                    new PlaceRequestEvent(
                        new PlaceToken(LOGGEDIN)
                            .cld(COMPDETAILS)
                            .appendParam(PARAM_COMPANY, "" + kvoOfRow.getId())));
            }

            @Override
            public String getCommandCellText(AssistedObject kvoOfRow) {
                return CMI18n.menu_COMPDETAILS();
            }
        };
    }

    private UserCommand getDeleteCommand() {
        return new UserCommand() {

            @Override
            public boolean visible(AssistedObject kvoOfRow) {
                return true;
            }

            @Override
            public void onCellClicked(AssistedObject kvoOfRow) {
                eventBus.fireEvent(
                    new PlaceRequestEvent(
                        new PlaceToken(LOGGEDIN)
                            .cld(COMPDELETE)
                            .appendParam(PARAM_COMPANY, "" + kvoOfRow.getId())));
            }

            @Override
            public String getCommandCellText(AssistedObject kvoOfRow) {
                return CMI18n.menu_COMPDELETE();
            }
        };
    }

    private UserCommand getContactsCommand() {
        return new UserCommand() {

            @Override
            public boolean visible(AssistedObject kvoOfRow) {
                return true;
            }

            @Override
            public void onCellClicked(AssistedObject kvoOfRow) {
                eventBus.fireEvent(
                    new PlaceRequestEvent(
                        new PlaceToken(LOGGEDIN)
                            .cld(COMPCONTEDIT)
                            .appendParam(PARAM_COMPANY, "" + kvoOfRow.getId())));
            }

            @Override
            public String getCommandCellText(AssistedObject kvoOfRow) {
                return CMI18n.menu_COMPCONTEDIT();
            }
        };
    }

}
