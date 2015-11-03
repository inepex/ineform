package com.inepex.ineForm.client.table;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;

public interface IneTableFactory {

    @Named("simple")
    IneTable createSimple(
        @Assisted("od") String objectDescName,
        @Assisted("trd") String tableRenderDescriptorName,
        IneDataConnector connector);

    @Named("simple2")
    IneTable createSimple(
        String objectDescName,
        IneDataConnector connector);

    @Named("sortable")
    SortableIneTable createSortable(
        String descriptorName,
        IneDataConnector ineDataConnector);

    @Named("sortable2")
    SortableIneTable createSortable(
        @Assisted("od") String descriptorName,
        @Assisted("trd") String tableRenderDescriptorName,
        IneDataConnector ineDataConnector);

    @Named("sortable3")
    SortableIneTable createSortable(
        @Assisted("od") String descriptorName,
        @Assisted("trd") String tableRenderDescriptorName,
        IneDataConnector ineDataConnector,
        Boolean rerunQueryOnUpdate);

}
