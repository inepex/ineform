package com.inepex.ineForm.client.datamanipulator;

import com.google.inject.name.Named;
import com.inepex.ineForm.client.table.IneDataConnector;

public interface ManipulatorFactory {
    @Named("rowCommand")
    RowCommandDataManipulator createRowCommand(
        String descriptorName,
        IneDataConnector ineDataConnector,
        boolean sortable);

    @Named("singleSelect")
    SingleSelectDataManipulator createSingleSelect(
        String descriptorName,
        IneDataConnector ineDataConnector,
        boolean sortable);
}