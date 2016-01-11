package com.inepex.ineForm.client.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.events.CheckBoxValueChangeListener;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineForm.shared.render.TableFieldRenderer.CustomCellContentDisplayer;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;

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
public abstract class AbstractIneTable {

    // Constants
    public final static int DEFAULT_PAGE_SIZE = 20;
    public final static int DEFAULT_PAGE_SIZE_WITHOUT_PAGER = 1000;

    // Class variables
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * The key provider that provides the unique ID of a AssistedObject.
     */
    public static final ProvidesKey<AssistedObject> KEY_PROVIDER =
        new ProvidesKey<AssistedObject>() {
            @Override
            public Object getKey(AssistedObject item) {
                return item == null ? null : item.getId();
            }
        };

    /**
     * When use custom, use getCellTable.setSelectionModel() to set selection
     * model
     *
     */
    public static enum SelectionBehaviour {
        NO_SELECTION,
        SINGLE_SELECTION,
        MULTIPLE_SELECTION,
        CUSTOM;
    }

    public static enum PagerPosition {
        TOP,
        BOTTOM,
        BOTH;
    }

    protected static TableRDesc getTRD(
        DescriptorStore descriptorStore,
        String objectDescName,
        String tableRenderDescriptorName) {
        if (tableRenderDescriptorName == null) {
            return descriptorStore.getDefaultTypedDesc(objectDescName, TableRDesc.class);
        } else {
            return descriptorStore.getNamedTypedDesc(
                objectDescName,
                tableRenderDescriptorName,
                TableRDesc.class);
        }

    }

    protected final AbstractCellTable<AssistedObject> cellTable;

    protected String commandsTitle = "";
    protected List<UserCommand> commands = new ArrayList<AbstractIneTable.UserCommand>();
    protected RowStylesProvider rowStylesProvider = null;

    // Properties
    protected final String objectDescriptorName;
    protected final TableRDesc tableRenderDescriptor;

    // Dependencies
    protected final IneDataConnector dataConnector;
    protected final DescriptorStore descStore;
    protected final AssistedObjectHandlerFactory handlerFactory;

    private SingleSelectionModel<AssistedObject> singleSelectionModel = null;
    private MultiSelectionModel<AssistedObject> multiSelectionModel = null;
    private SelectAllHeader selectAllHeader = null;

    protected SimplePager topPager = null;
    protected SimplePager pager = null;
    private boolean showPager = true;

    private PagerPosition pagerPos = PagerPosition.BOTTOM;

    private boolean rendered = false;

    private SelectionBehaviour selectionBehaviour = SelectionBehaviour.NO_SELECTION;
    private CheckBoxValueChangeListener checkBoxValueChangeListener;

    protected Map<String, Header<String>> headers = new TreeMap<String, Header<String>>();

    protected final TableFieldRenderer fieldRenderer;

    protected String checkboxActiveHtml = "<input type=\"checkbox\" tabindex=\"-1\" checked/>";
    protected String checkboxInactiveHtml = "<input type=\"checkbox\" tabindex=\"-1\"/>";

    /**
     * IMPORTANT: Don't forget to call renderTable() before use!
     * 
     * @param objectDescriptorName
     * @param tableRenderDescriptor
     *            - add null to default value by
     *            DescriptorStore.get().getDefaultTRD(objectDescriptorName)
     * @param dataProvider
     */
    protected AbstractIneTable(
        DescriptorStore descStore,
        String objectDescriptorName,
        TableRDesc tableRenderDescriptor,
        IneDataConnector dataProvider,
        TableFieldRenderer fieldRenderer) {
        this.fieldRenderer = fieldRenderer;
        this.descStore = descStore;
        this.handlerFactory = new AssistedObjectHandlerFactory(descStore);

        cellTable = createTable();

        this.objectDescriptorName = objectDescriptorName;
        this.tableRenderDescriptor =
            tableRenderDescriptor != null ? tableRenderDescriptor : descStore.getDefaultTypedDesc(
                objectDescriptorName,
                TableRDesc.class);
        this.dataConnector = dataProvider;
    }

    protected abstract AbstractCellTable<AssistedObject> createTable();

    protected abstract void addToMainPanel(Widget w);

    public void setCommandsTitle(String commandsTitle) {
        this.commandsTitle = commandsTitle;
    }

    public void addCommand(UserCommand command) {
        this.commands.add(command);
    }

    public void addCommands(UserCommand... command) {
        for (UserCommand userCommand : command) {
            this.commands.add(userCommand);
        }
    }

    public void setRowStylesProvider(RowStylesProvider rowStylesProvider) {
        this.rowStylesProvider = rowStylesProvider;
    }

    public void setSelectionBehaviour(SelectionBehaviour selectionBehaviour) {
        this.selectionBehaviour = selectionBehaviour;
    }

    public void addCellContentDisplayer(
        String columnId,
        CustomCellContentDisplayer cellContentDisplayer) {
        fieldRenderer.setCustomFieldRenderer(columnId, cellContentDisplayer);
    }

    /**
     * Also sets pagesize to DEFAULT_PAGE_SIZE, or
     * DEFAULT_PAGE_SIZE_WITHOUT_PAGER
     * 
     * @param showPager
     */
    public void setShowPager(boolean showPager) {
        if (showPager)
            pageSize = DEFAULT_PAGE_SIZE;
        else
            pageSize = DEFAULT_PAGE_SIZE_WITHOUT_PAGER;
        this.showPager = showPager;
    }

    public void setPagerPosition(PagerPosition position) {
        this.pagerPos = position;
    }

    // **** Logic functions ****//

    /**
     * initialize inetable columns. To set or refresh table data, call
     * dataConnector.update().
     */
    public void renderTable() {
        renderTable(true);
    }

    public void renderTable(boolean init) {
        if (!rendered) {
            if (init)
                initTable();
            if (showPager == true && (pagerPos == PagerPosition.TOP)) {
                addToMainPanel(pager);
            }
            if (showPager && pagerPos == PagerPosition.BOTH) {
                addToMainPanel(topPager);
            }
            addToMainPanel(cellTable);

            cellTable.addStyleName(ResourceHelper.ineformRes().style().ineTable());

            initTableColumns();
            cellTable.redraw();

            if (showPager && (pagerPos == PagerPosition.BOTTOM || pagerPos == PagerPosition.BOTH)) {
                addToMainPanel(pager);
            }

            rendered = true;
        }
    }

    public void initTable() {

        // Create a Pager to control the table.
        if (showPager) {
            pager = PagerCreator.create();
            pager.setDisplay(cellTable);
            if (pagerPos == PagerPosition.BOTH) {
                topPager = PagerCreator.create();
                topPager.setDisplay(cellTable);
            }
        }
        cellTable.setPageSize(pageSize);

        if (selectionBehaviour == SelectionBehaviour.SINGLE_SELECTION) {
            // Add a selection model so that only single selection is available.
            singleSelectionModel = new SingleSelectionModel<AssistedObject>(KEY_PROVIDER);
            cellTable.setSelectionModel(singleSelectionModel);

        } else if (selectionBehaviour == SelectionBehaviour.NO_SELECTION) {
            cellTable.setSelectionModel(new NoSelectionModel<AssistedObject>());
        } else if (selectionBehaviour == SelectionBehaviour.MULTIPLE_SELECTION) {
            multiSelectionModel = new MultiSelectionModel<AssistedObject>(KEY_PROVIDER);
            cellTable.setSelectionModel(
                multiSelectionModel,
                DefaultSelectionEventManager.<AssistedObject> createCheckboxManager());
        }

        // Add the CellList to the adapter in the database.
        dataConnector.addDataDisplay(cellTable);

        cellTable.setRowStyles(new PointerAndCustomRowStyleProvider());

        // Set default empty widget
        cellTable.setEmptyTableWidget(new Label(IneFormI18n.inetable_noresult()));
    }

    private void initTableColumns() {
        ObjectDesc objectDesc = descStore.getOD(objectDescriptorName);

        if (selectionBehaviour == SelectionBehaviour.MULTIPLE_SELECTION) {
            Column<AssistedObject, Boolean> checkColumn =
                new Column<AssistedObject, Boolean>(new CheckboxCell(true, false)) {
                    @Override
                    public Boolean getValue(AssistedObject object) {
                        return multiSelectionModel.isSelected(object);
                    }
                };
            selectAllHeader = new SelectAllHeader(this);
            cellTable.addColumn(checkColumn, selectAllHeader);
        }

        for (Node<TableRDescBase> columnNode : tableRenderDescriptor.getRootNode().getChildren()) {

            ColRDesc colRenderDesc = (ColRDesc) columnNode.getNodeElement();

            if (!(IneFormProperties.showIds || tableRenderDescriptor
                .hasProp(FormRDescBase.prop_showIDs))
                && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
                continue;

            FDesc fieldDesc = null;
            List<String> nodeIdAsList = SharedUtil.listFromDotSeparated(columnNode.getNodeId());

            if (nodeIdAsList.size() == 1)
                fieldDesc = objectDesc.getField(columnNode.getNodeId());
            else {
                try {
                    fieldDesc = descStore.getRelatedFieldDescrMultiLevel(objectDesc, nodeIdAsList);
                } catch (Exception e) {
                    fieldDesc = objectDesc.getField(nodeIdAsList.get(0));
                    System.out.println("You set complex id for a field, which is not a relation. "
                        + "("
                        + nodeIdAsList.get(0)
                        + ")");
                }
            }

            Column<AssistedObject, ?> column =
                new IneTableColumnProvider(columnNode.getNodeId()).getColumn();

            String headerText =
                colRenderDesc.getDisplayName() != null ? colRenderDesc.getDisplayName() : null;
            if (headerText == null) {
                if (fieldDesc != null)
                    headerText = fieldDesc.getDefaultDisplayName();
                else
                    headerText = "";
            }

            Header<String> header =
                createHeader(
                    colRenderDesc.isSortable(),
                    headerText,
                    columnNode.getNodeId(),
                    colRenderDesc.hasProp(ColRDesc.DEFAULTSORT),
                    colRenderDesc.hasProp(ColRDesc.DEFAULTSORTREVERSE));
            if (colRenderDesc.hasProp(ColRDesc.COLSPAN)) {
                header = headers.get(colRenderDesc.getPropValue(ColRDesc.COLSPAN));
            }
            headers.put(columnNode.getNodeId(), header);
            cellTable.addColumn(column, header);

            if (colRenderDesc.hasColumnWidth())
                cellTable.setColumnWidth(column, colRenderDesc.getColumnWidthAsString());

            if (colRenderDesc.hasHAlign()) {
                HorizontalAlignmentConstant hAl = null;
                switch (colRenderDesc.getHAlign()) {

                    case RIGHT:
                        hAl = HasHorizontalAlignment.ALIGN_RIGHT;
                        break;

                    case LEFT:
                        hAl = HasHorizontalAlignment.ALIGN_LEFT;
                        break;

                    case CENTER:
                    default:
                        hAl = HasHorizontalAlignment.ALIGN_CENTER;
                        break;
                }
                cellTable.getColumn(cellTable.getColumnCount() - 1).setHorizontalAlignment(hAl);
            }
        }

        if (commands != null && commands.size() > 0) {

            List<HasCell<AssistedObject, ?>> commandList =
                new ArrayList<HasCell<AssistedObject, ?>>();

            for (int i = 0; i < commands.size(); i++) {
                commandList.add(new UserCommandColumnPart(new LinkActionCell(
                    commands.get(i),
                    i != commands.size() - 1)));
            }

            CompositeCell<AssistedObject> compCell = new CompositeCell<AssistedObject>(commandList);

            cellTable.addColumn(new UserCommandColumn(compCell), commandsTitle);
        }

    }

    protected Header<String> createHeader(
        boolean sortable,
        String text,
        String key,
        boolean defaultSort,
        boolean defaultSortReverse) {
        return new CustomTextHeader(text);
    }

    public SingleSelectionModel<AssistedObject> getSingleSelectionModel() {
        return singleSelectionModel;
    }

    public void redrawHeaders() {
        cellTable.redrawHeaders();
    }

    public SelectAllHeader getSelectAllHeader() {
        return selectAllHeader;
    }

    @SuppressWarnings("deprecation")
    public AssistedObject getDisplayedKvoById(Long key) {
        // FIXME: valamiért kell ide null check... amikor egy 11 elemű táblában
        // 2-11 van megjelenítve
        // és visszafelé lapozunk(1-10es oldalra), akkor a displayed items olyan
        // lista aminek a 0. eleme null így elszáll a kód
        // a if(kvo!=null && kvo.getId().equals(key))-es nullcheck úgy tűnik
        // megoldja a dolgot, de valószínű, hogy vmi más hiba
        // indikátora a jelenség
        for (AssistedObject kvo : cellTable.getDisplayedItems()) {
            if (kvo != null && kvo.getId().equals(key))
                return kvo;
        }
        return null;
    }

    public Header<String> getHeader(String key) {
        return headers.get(key);
    }

    public CustomTextHeader getCustomTextHeader(String key) {
        return (CustomTextHeader) headers.get(key);
    }

    private class StringTableColumn extends Column<AssistedObject, String> {

        private final String key;

        public StringTableColumn(String key) {
            super(new TextCell());
            this.key = key;
            setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        }

        @Override
        public void render(Context context, AssistedObject rowValue, SafeHtmlBuilder sb) {
            fieldRenderer.setObjectAndDescriptor(rowValue, tableRenderDescriptor);
            sb.appendHtmlConstant(fieldRenderer.getField(key));
        }

        @Override
        public String getValue(AssistedObject rowValue) {
            return "";
        }
    };

    private class IneCheckboxCell extends AbstractCell<Boolean> {

        public IneCheckboxCell() {
            super("click");
        }

        @Override
        public void onBrowserEvent(
            com.google.gwt.cell.client.Cell.Context context,
            Element parent,
            Boolean value,
            NativeEvent event,
            ValueUpdater<Boolean> valueUpdater) {
            int eventType = Event.as(event).getTypeInt();
            if (eventType == Event.ONCLICK) {
                AssistedObject ao = dataConnector.getAssistedObjectByKey((Long) context.getKey());
                if (checkBoxValueChangeListener != null && ao != null) {
                    List<Node<TableRDescBase>> descriptorNodes =
                        tableRenderDescriptor.getRootNode().getChildren();
                    Node<TableRDescBase> modifiedNode = descriptorNodes.get(context.getColumn());
                    AssistedObjectHandler handler = handlerFactory.createHandler(ao);
                    if (value == null)
                        value = false;
                    handler.set(modifiedNode.getNodeId(), !value);
                    checkBoxValueChangeListener.onCheckBoxValueChanged(
                        AbstractIneTable.this,
                        modifiedNode.getNodeId(),
                        !value,
                        ao);
                }
            }
            super.onBrowserEvent(context, parent, value, event, valueUpdater);
        }

        @Override
        public void render(
            com.google.gwt.cell.client.Cell.Context context,
            Boolean value,
            SafeHtmlBuilder sb) {
            if (Boolean.TRUE.equals(value)) {
                sb.append(SafeHtmlUtils.fromSafeConstant(checkboxActiveHtml));
            } else {
                sb.append(SafeHtmlUtils.fromSafeConstant(checkboxInactiveHtml));
            }
        }

    }

    private class BooleanTableColumn extends Column<AssistedObject, Boolean> {

        private final String key;

        public BooleanTableColumn(String key) {
            super(new IneCheckboxCell());
            this.key = key;
            setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        }

        @Override
        public Boolean getValue(AssistedObject rowValue) {
            return rowValue.getBooleanUnchecked(key);
        }
    }

    private class TextBoxTableColumn extends Column<AssistedObject, String> {

        private final String key;

        public TextBoxTableColumn(String key) {
            super(new TextInputCell());
            this.key = key;

            this.setFieldUpdater(new FieldUpdater<AssistedObject, String>() {

                @Override
                public void update(int index, AssistedObject ao, String value) {
                    List<String> ids = SharedUtil.listFromDotSeparated(TextBoxTableColumn.this.key);
                    handlerFactory
                        .createHandler(ao)
                        .getRelatedKVOMultiLevel(ids)
                        .setUnchecked(ids.get(ids.size() - 1), value);
                }
            });
        }

        @Override
        public String getValue(AssistedObject ao) {
            return handlerFactory.createHandler(ao).getRelatedString(key);
        }

    }

    private class IneTableColumnProvider {
        private String key;

        public IneTableColumnProvider(String key) {
            this.key = key;
        }

        public Column<AssistedObject, ?> getColumn() {
            ColRDesc colRdesc =
                (ColRDesc) tableRenderDescriptor
                    .getRootNode()
                    .findNodeByHierarchicalId(key)
                    .getNodeElement();
            if (colRdesc.hasProp(ColRDesc.AS_CB)) {
                return new BooleanTableColumn(key);
            } else if (colRdesc.hasProp(ColRDesc.AS_AO_EDITOR_TEXTBOX)) {
                return new TextBoxTableColumn(key);
            } else {
                return new StringTableColumn(key);
            }

        }
    }

    private class PointerAndCustomRowStyleProvider implements RowStyles<AssistedObject> {

        @Override
        public String getStyleNames(AssistedObject row, int rowIndex) {
            String extraStyle = null;
            if (rowStylesProvider != null) {
                extraStyle = rowStylesProvider.getStyleNames(row, rowIndex);
            }

            return ((selectionBehaviour == SelectionBehaviour.SINGLE_SELECTION) ? ResourceHelper
                .ineformRes()
                .style()
                .clickable() : "")
                + ((extraStyle == null) ? ("") : (" " + extraStyle));
        }

    }

    public static interface RowStylesProvider extends RowStyles<AssistedObject> {}

    public static abstract class UserCommand {
        public abstract String getCommandCellText(AssistedObject kvoOfRow);

        public abstract void onCellClicked(AssistedObject kvoOfRow);

        public abstract boolean visible(AssistedObject kvoOfRow);

        public Boolean overrideDefaultSeparatorUsage(AssistedObject kvoOfRow) {
            return null;
        }
    }

    public static abstract class VisibleUserCommand extends UserCommand {
        @Override
        public boolean visible(AssistedObject kvoOfRow) {
            return true;
        }
    }

    private class UserCommandColumn extends Column<AssistedObject, AssistedObject> {
        public UserCommandColumn(CompositeCell<AssistedObject> cell) {
            super(cell);
        }

        @Override
        public AssistedObject getValue(AssistedObject object) {
            return object;
        }
    }

    private class UserCommandColumnPart extends Column<AssistedObject, String> {

        public UserCommandColumnPart(LinkActionCell cell) {
            super(cell);
        }

        @Override
        public void onBrowserEvent(
            Context context,
            Element elem,
            AssistedObject object,
            NativeEvent event) {
            super.onBrowserEvent(context, elem, object, event);
        }

        @Override
        public String getValue(AssistedObject object) {
            return "";
        }
    }

    private class LinkActionCell extends AbstractCell<String> {

        private final UserCommand userCommand;
        private boolean isNotLast;

        public LinkActionCell(UserCommand userCommand, boolean isNotLast) {
            super("click");
            this.userCommand = userCommand;
            this.isNotLast = isNotLast;
        }

        @Override
        public void onBrowserEvent(
            Context context,
            Element parent,
            String value,
            NativeEvent event,
            ValueUpdater<String> valueUpdater) {
            super.onBrowserEvent(context, parent, value, event, valueUpdater);
            AssistedObject row = getDisplayedKvoById((Long) context.getKey());
            userCommand.onCellClicked(row);
        }

        @Override
        public void render(Context context, String value, SafeHtmlBuilder sb) {
            AssistedObject kvoOfRow = getDisplayedKvoById((Long) context.getKey());
            if (userCommand.visible(kvoOfRow)) {
                sb.append(buildHtml(
                    userCommand.getCommandCellText(kvoOfRow),
                    needsSeparator(kvoOfRow)));
            }
        }

        private SafeHtml buildHtml(String message, boolean needsSeparator) {
            SafeHtmlBuilder htmlBuilder =
                new SafeHtmlBuilder()
                    .appendHtmlConstant("<a class = 'ineTable-ActionCell'>")
                    .appendHtmlConstant(message)
                    .appendHtmlConstant("</a>");
            if (needsSeparator) {
                htmlBuilder.appendHtmlConstant("&nbsp;|&nbsp;").toSafeHtml();
            }

            return htmlBuilder.toSafeHtml();
        }

        private boolean needsSeparator(AssistedObject kvoOfRow) {
            if (userCommand.overrideDefaultSeparatorUsage(kvoOfRow) == null)
                return isNotLast;
            else
                return userCommand.overrideDefaultSeparatorUsage(kvoOfRow);
        }

    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        cellTable.setPageSize(pageSize);
    }

    public SelectionModel<? super AssistedObject> getSelectionModel() {
        return cellTable.getSelectionModel();
    }

    @SuppressWarnings("deprecation")
    public AssistedObject getDisplayedItem(int index) {
        return cellTable.getDisplayedItem(index);
    }

    public class CustomTextHeader extends Header<String> {

        private final String originalText;

        private String text;

        public CustomTextHeader(String originalText) {
            super(new TextCell());
            this.originalText = originalText;
            this.text = originalText;
        }

        public String getOriginalText() {
            return originalText;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public void render(Context context, SafeHtmlBuilder sb) {
            sb.appendHtmlConstant(text);
        }

        @Override
        public String getValue() {
            // nothing to do
            return "";
        }

    }

    public HasData<AssistedObject> getDataDisplay() {
        return cellTable;
    }

    public TableFieldRenderer getFieldRenderer() {
        return fieldRenderer;
    }

    public void addColumnStyle(int col, String style) {
        cellTable.addColumnStyleName(col, style);
    }

    public AbstractCellTable<AssistedObject> getCellTable() {
        return cellTable;
    }

    public MultiSelectionModel<AssistedObject> getMultiSelectionModel() {
        return multiSelectionModel;
    }

    public HandlerRegistration addRowCountChangedHandler(RowCountChangeEvent.Handler handler) {
        return cellTable.addRowCountChangeHandler(handler);
    }

    public void setSingleSelectionModel(SingleSelectionModel<AssistedObject> singleSelectionModel) {
        this.singleSelectionModel = singleSelectionModel;
    }

    public SelectionBehaviour getSelectionBehaviour() {
        return selectionBehaviour;
    }

    public void setCheckBoxValueChangeListener(CheckBoxValueChangeListener listener) {
        this.checkBoxValueChangeListener = listener;
    }

    public void removeCheckBoxValueChangeListener() {
        this.checkBoxValueChangeListener = null;
    }

    public void setCheckboxHtml(String activeHtml, String inactiveHtml) {
        this.checkboxActiveHtml = activeHtml;
        this.checkboxInactiveHtml = inactiveHtml;
    }

    public IneDataConnector getDataConnector() {
        return dataConnector;
    }

    public boolean isRendered() {
        return rendered;
    }

}
