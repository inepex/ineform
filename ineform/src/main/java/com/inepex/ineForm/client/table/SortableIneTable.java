package com.inepex.ineForm.client.table;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.user.cellview.client.Header;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class SortableIneTable extends IneTable {

    private String orderKey;
    private boolean descending = false;

    @AssistedInject
    public SortableIneTable(
        DescriptorStore descStore,
        @Assisted String objectDescriptorName,
        @Assisted IneDataConnector dataProvider,
        @Assisted Boolean rerunQueryOnUpdate,
        TableFieldRenderer fieldRenderer) {
        super(descStore, objectDescriptorName, dataProvider, fieldRenderer);
    }

    @AssistedInject
    public SortableIneTable(
        DescriptorStore descriptorStore,
        @Assisted("od") String objectDescName,
        @Assisted("trd") String tableRenderDescriptor,
        @Assisted IneDataConnector connector,
        @Assisted Boolean rerunQueryOnUpdate,
        TableFieldRenderer fieldRenderer) {
        super(descriptorStore, objectDescName, tableRenderDescriptor, connector, fieldRenderer);
    }

    @Override
    protected Header<String> createHeader(
        boolean sortable,
        String text,
        final String key,
        boolean defaultSort,
        boolean defaultSortReverse) {

        if (!sortable)
            return new CustomTextHeader(text);

        final SortableHeader header = new SortableHeader(text);
        if (defaultSort) {
            orderKey = key;
            header.setSorted(true);
            header.setReverseSort(defaultSortReverse);
            dataConnector.setOrderKey(orderKey);
            dataConnector.setOrderDescending(defaultSortReverse);
        }
        header.setUpdater(new ValueUpdater<String>() {
            @Override
            public void update(String value) {
                header.setSorted(true);
                header.toggleReverseSort();

                for (Header<String> otherHeader : headers.values()) {
                    if (otherHeader instanceof SortableHeader) {
                        if (otherHeader != header) {
                            ((SortableHeader) otherHeader).setSorted(false);
                            ((SortableHeader) otherHeader).setReverseSort(true);
                        }
                    }

                }
                cellTable.redrawHeaders();

                // Request sorted rows.
                orderKey = key;
                descending = header.getReverseSort();

                // Go to the first page of the newly-sorted results
                if (pager != null)
                    pager.firstPage();
                if (topPager != null)
                    topPager.firstPage();
                dataConnector.setOrderKey(orderKey);
                dataConnector.setOrderDescending(descending);
                dataConnector.update();
            }
        });
        return header;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public boolean isDescending() {
        return descending;
    }

}
