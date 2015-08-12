package com.inepex.ineForm.client.form;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.IneTable;

public interface FormFactory {
    @Named("simple")
    IneForm createSimple(
        @Assisted("dn") String descriptorName,
        @Assisted("frdn") String formRDescName);

    @Named("saveCancel")
    SaveCancelForm createSaveCancel(
        @Assisted("dn") String descriptorName,
        @Assisted("frdn") String formRDescName,
        IneDataConnector ineDataConnector,
        @Assisted SaveCancelFormView view);

    @Named("wizard")
    WizardForm createWizard(
        @Assisted("dn") String descriptorName,
        @Assisted("frdn") String formRDescName,
        IneDataConnector ineDataConnector,
        @Assisted SaveCancelFormView view);

    @Named("search")
    SearchForm createSearch(
        @Assisted("dn") String descriptorName,
        @Assisted("frdn") String formRDescName,
        IneDataConnector ineDataConnector,
        IneTable ineTable);
}