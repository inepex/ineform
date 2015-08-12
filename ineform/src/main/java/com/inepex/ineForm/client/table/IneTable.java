package com.inepex.ineForm.client.table;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

/**
 * A CellTable that automatically renders itself according to the given
 * ObjectRenderDescriptor and TableRenderDescriptor
 * 
 * Usage: 1. set attributes 2. call renderTable() 3. call dataConnector.update()
 * to set or refresh table data
 * 
 * IMPORTANT: Don't forget to call renderTable() before use!
 * 
 * @author SoTi
 * @author Sebi
 * @author Szobi
 * 
 */

public class IneTable extends AbstractIneTable implements IsWidget {

    private final FlowPanel mainPanel = new FlowPanel();

    @AssistedInject
    public IneTable(
        DescriptorStore descriptorStore,
        @Assisted("od") String objectDescName,
        @Assisted("trd") String tableRenderDescriptorName,
        @Assisted IneDataConnector connector,
        TableFieldRenderer fieldRenderer) {
        super(descriptorStore, objectDescName, getTRD(
            descriptorStore,
            objectDescName,
            tableRenderDescriptorName), connector, fieldRenderer);
    }

    /**
     * Uses the default {@link TableRDesc}
     * 
     * @param objectDescriptorName
     * @param dataProvider
     */
    @AssistedInject
    public IneTable(
        DescriptorStore descStore,
        @Assisted String objectDescriptorName,
        @Assisted IneDataConnector dataProvider,
        TableFieldRenderer fieldRenderer) {
        this(descStore, objectDescriptorName, (String) null, dataProvider, fieldRenderer);
    }

    @Override
    protected AbstractCellTable<AssistedObject> createTable() {
        return new CellTable<AssistedObject>(
            DEFAULT_PAGE_SIZE,
            ResourceHelper.cellTableResources(),
            KEY_PROVIDER) {

            @Override
            public void setRowData(int start, java.util.List<? extends AssistedObject> values) {
                super.setRowData(start, values);
                if (values.size() == 0) {
                    if (pager != null)
                        pager.setVisible(false);
                    if (topPager != null)
                        topPager.setVisible(false);
                    getTableHeadElement().getStyle().setVisibility(Visibility.HIDDEN);
                } else {
                    if (pager != null)
                        pager.setVisible(true);
                    if (topPager != null)
                        topPager.setVisible(true);
                    getTableHeadElement().getStyle().setVisibility(Visibility.VISIBLE);
                }
            }

        };
    }

    @Override
    protected void addToMainPanel(Widget w) {
        mainPanel.add(w);
    }

    @Override
    public Widget asWidget() {
        return mainPanel;
    }

    public FlowPanel getMainPanel() {
        return mainPanel;
    }

}
