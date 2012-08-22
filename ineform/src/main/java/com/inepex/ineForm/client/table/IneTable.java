package com.inepex.ineForm.client.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer.CustomCellContentDisplayer;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

/**
 * A CellTable that automatically renders itself according to the given
 * ObjectRenderDescriptor and TableRenderDescriptor
 * 
 * IMPORTANT: Don't forget to call renderTable() before use!
 * 
 * @author SoTi
 * @author Sebi
 * @author Szobi
 * 
 */

public class IneTable extends HandlerAwareComposite {

	// Constants
	public final static int DEFAULT_PAGE_SIZE = 20;
	public final static int DEFAULT_PAGE_SIZE_WITHOUT_PAGER = 1000;

	// Class variables
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * The key provider that provides the unique ID of a AssistedObject.
	 */
	public static final ProvidesKey<AssistedObject> KEY_PROVIDER = new ProvidesKey<AssistedObject>() {
		public Object getKey(AssistedObject item) {
			return item == null ? null : item.getId();
		}
	};

	/**
	 * When use custom, use getCellTable.setSelectionModel() to set selection model
	 *
	 */
	public static enum SelectionBehaviour {
		NO_SELECTION, SINGLE_SELECTION, MULTIPLE_SELECTION, CUSTOM;
	}

	private static TableRDesc getTRD(DescriptorStore descriptorStore, String objectDescName, String tableRenderDescriptorName) {
		if (tableRenderDescriptorName == null) {
			return descriptorStore.getDefaultTypedDesc(objectDescName, TableRDesc.class);
		} else {
			return descriptorStore.getNamedTypedDesc(objectDescName, tableRenderDescriptorName, TableRDesc.class);
		}

	}

	RowCountChangeEvent.Handler defaultEmptyRowsHandler = new RowCountChangeEvent.Handler() {

		@Override
		public void onRowCountChange(RowCountChangeEvent event) {
			if (event.getNewRowCount() == 0) {
				onEmptyRows();
			} else {
				cellTable.setVisible(true);
				if (showPager)
					pager.setVisible(true);
				if (emptyRowsWidget != null)
					emptyRowsWidget.setVisible(false);
			}
		}

	};

	final FlowPanel mainPanel = new FlowPanel();
	final CellTable<AssistedObject> cellTable;
	private Widget emptyRowsWidget = null;

	protected String commandsTitle = "";
	protected List<UserCommand> commands = new ArrayList<IneTable.UserCommand>();
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

	protected SimplePager pager = null;
	private boolean showPager = true;

	private boolean rendered = false;

	private SelectionBehaviour selectionBehaviour = SelectionBehaviour.NO_SELECTION;

	protected Map<String, Header<String>> headers = new TreeMap<String, Header<String>>();

	private boolean batchColumnAddig = false; // faster rendering

	protected final AssistedObjectTableFieldRenderer fieldRenderer;

	@AssistedInject
	public IneTable(
			DescriptorStore descriptorStore,
			@Assisted("od") String objectDescName,
			@Assisted("trd") String tableRenderDescriptorName,
			@Assisted IneDataConnector connector,
			AssistedObjectTableFieldRenderer fieldRenderer) {
		this(
				descriptorStore,
				objectDescName,
				getTRD(descriptorStore, objectDescName, tableRenderDescriptorName),
				connector,
				fieldRenderer);

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
			AssistedObjectTableFieldRenderer fieldRenderer) {
		this(descStore, objectDescriptorName, (String) null, dataProvider, fieldRenderer);
	}

	/**
	 * IMPORTANT: Don't forget to call renderTable() before use!
	 * 
	 * @param objectDescriptorName
	 * @param tableRenderDescriptor
	 *            - add null to default value by
	 *            DescriptorStore.get().getDefaultTRD(objectDescriptorName)
	 * @param dataProvider
	 */
	protected IneTable(
			DescriptorStore descStore,
			String objectDescriptorName,
			TableRDesc tableRenderDescriptor,
			IneDataConnector dataProvider,
			AssistedObjectTableFieldRenderer fieldRenderer) {
		this.fieldRenderer = fieldRenderer;
		this.descStore = descStore;
		this.handlerFactory = new AssistedObjectHandlerFactory(descStore);

		cellTable = new CellTable<AssistedObject>(DEFAULT_PAGE_SIZE, ResourceHelper.cellTableResources(), KEY_PROVIDER) {
			@Override
			public void setRowData(int start, java.util.List<? extends AssistedObject> values) {
				super.setRowData(start, values);
				onRowDataChanged(values);
			};

			@Override
			public void redraw() {
				if (!batchColumnAddig) {
					super.redraw();
				} else {
					// dont need redraw while initializing table columns
				}
			};
		};

		cellTable.addRowCountChangeHandler(defaultEmptyRowsHandler);

		this.objectDescriptorName = objectDescriptorName;
		this.tableRenderDescriptor = tableRenderDescriptor != null ? tableRenderDescriptor : descStore.getDefaultTypedDesc(
				objectDescriptorName,
				TableRDesc.class);
		this.dataConnector = dataProvider;

		initWidget(mainPanel);
		mainPanel.add(cellTable);
	}

	// **** Set behaviour properties ****//
	protected void onRowDataChanged() {
	}

	private void onRowDataChanged(java.util.List<? extends AssistedObject> values) {
		if (values.size() == 0) {
			onEmptyRows();
		}
	}

	private void onEmptyRows() {
		if (emptyRowsWidget != null) {
			emptyRowsWidget.setVisible(true);
			cellTable.setVisible(false);
			if (showPager)
				pager.setVisible(false);
		}
	}

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

	public void addCellContentDisplayer(String columnId, CustomCellContentDisplayer cellContentDisplayer) {
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

	// **** Logic functions ****//

	public void renderTable() {
		if (!rendered) {
			cellTable.addStyleName(ResourceHelper.ineformRes().style().ineTable());

			batchColumnAddig = true;
			initTable();
			initTableColumns();

			batchColumnAddig = false;
			cellTable.redraw();

			if (showPager)
				mainPanel.add(pager);

			rendered = true;
		}
	}

	private void initTable() {

		// Create a Pager to control the table.
		if (showPager) {
			SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
			pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
			pager.setDisplay(cellTable);
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
	}

	private void initTableColumns() {

		ObjectDesc objectDesc = descStore.getOD(objectDescriptorName);

		if (selectionBehaviour != null && selectionBehaviour == SelectionBehaviour.MULTIPLE_SELECTION) {
			Column<AssistedObject, Boolean> checkColumn = new Column<AssistedObject, Boolean>(new CheckboxCell(true, false)) {
				@Override
				public Boolean getValue(AssistedObject object) {
					return multiSelectionModel.isSelected(object);
				}
			};
			cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		}

		for (Node<TableRDescBase> columnNode : tableRenderDescriptor.getRootNode().getChildren()) {

			ColRDesc colRenderDesc = (ColRDesc) columnNode.getNodeElement();

			if (!IneFormProperties.showIds && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
				continue;

			FDesc fieldDesc = null;
			List<String> nodeIdAsList = columnNode.getNodeIdAsList();

			if (nodeIdAsList.size() == 1)
				fieldDesc = objectDesc.getField(columnNode.getNodeId());
			else {
				try {
					fieldDesc = descStore.getRelatedFieldDescrMultiLevel(objectDesc, nodeIdAsList);
				} catch (Exception e) {
					fieldDesc = objectDesc.getField(nodeIdAsList.get(0));
					System.out.println("You set complex id for a field, which is not a relation. " + "(" + nodeIdAsList.get(0)
							+ ")");
				}
			}

			IneTableColumn column = new IneTableColumn(new TextCell(), columnNode.getNodeId());

			String headerText = colRenderDesc.getDisplayName() != null ? colRenderDesc.getDisplayName() : null;
			if (headerText == null) {
				if (fieldDesc != null)
					headerText = fieldDesc.getDefaultDisplayName();
				else
					headerText = "";
			}

			Header<String> header = createHeader(
					colRenderDesc.isSortable(),
					headerText,
					columnNode.getNodeId(),
					colRenderDesc.hasProp(ColRDesc.DEFAULTSORT),
					colRenderDesc.hasProp(ColRDesc.DEFAULTSORTREVERSE));
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

		if (commands != null) {

			List<HasCell<AssistedObject, ?>> commandList = new ArrayList<HasCell<AssistedObject, ?>>();

			for (int i = 0; i < commands.size(); i++) {
				commandList.add(new UserCommandColumnPart(new LinkActionCell(commands.get(i).getCommandCellText(), commands
						.get(i), i != commands.size() - 1)));
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

	private class IneTableColumn extends Column<AssistedObject, String> {

		private final String key;

		public IneTableColumn(Cell<String> cell, String key) {
			super(cell);
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
			// do nothing
			return "";
		}
	};

	private class PointerAndCustomRowStyleProvider implements RowStyles<AssistedObject> {

		@Override
		public String getStyleNames(AssistedObject row, int rowIndex) {
			String extraStyle = null;
			if (rowStylesProvider != null) {
				extraStyle = rowStylesProvider.getStyleNames(row, rowIndex);
			}

			return ((selectionBehaviour == SelectionBehaviour.SINGLE_SELECTION) ? ResourceHelper.ineformRes().style().clickable()
					: "") + ((extraStyle == null) ? ("") : (" " + extraStyle));
		}

	}

	public static interface RowStylesProvider extends RowStyles<AssistedObject> {
	}

	public static interface UserCommand {
		public String getCommandCellText();

		public void onCellClicked(AssistedObject kvoOfRow);

		public boolean visible(AssistedObject kvoOfRow);
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
		public void onBrowserEvent(Context context, Element elem, AssistedObject object, NativeEvent event) {
			super.onBrowserEvent(context, elem, object, event);
		}

		@Override
		public String getValue(AssistedObject object) {
			return "";
		}
	}

	private class LinkActionCell extends AbstractCell<String> {

		private final SafeHtml html;
		private final UserCommand userCommand;

		public LinkActionCell(String message, UserCommand userCommand, boolean appendSeparator) {
			super("click");
			this.userCommand = userCommand;
			SafeHtmlBuilder hmtlBuilder = new SafeHtmlBuilder()
					.appendHtmlConstant("<a class = 'ineTable-ActionCell'>")
					.appendHtmlConstant(message)
					.appendHtmlConstant("</a>");
			if (appendSeparator) {
				hmtlBuilder.appendHtmlConstant("&nbsp;|&nbsp;").toSafeHtml();
			}

			this.html = hmtlBuilder.toSafeHtml();
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
			if (userCommand.visible(getDisplayedKvoById((Long) context.getKey())))
				sb.append(html);

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

	public AssistedObjectTableFieldRenderer getFieldRenderer() {
		return fieldRenderer;
	}

	public void addColumnStyle(int col, String style) {
		cellTable.addColumnStyleName(col, style);
	}

	public CellTable<AssistedObject> getCellTable() {
		return cellTable;
	}

	public MultiSelectionModel<AssistedObject> getMultiSelectionModel() {
		return multiSelectionModel;
	}

	public void setEmptyRowsWidget(Widget widget) {
		emptyRowsWidget = widget;
		emptyRowsWidget.setVisible(false);
		mainPanel.add(emptyRowsWidget);
	}

	public HandlerRegistration addRowCountChangedHandler(RowCountChangeEvent.Handler handler) {
		return cellTable.addRowCountChangeHandler(handler);
	}

	public void setSingleSelectionModel(SingleSelectionModel<AssistedObject> singleSelectionModel) {
		this.singleSelectionModel = singleSelectionModel;
	}
	
}
