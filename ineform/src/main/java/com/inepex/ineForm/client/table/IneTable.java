package com.inepex.ineForm.client.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Resources;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.EnumListFW;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.client.util.NumberUtilCln;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.util.SharedUtil;

/**
 * A CellTable that automatically renders itself according to the given ObjectRenderDescriptor
 * and TableRenderDescriptor
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
	public final static int DEFAULT_PAGE_SIZE = 10;
	public final static int DEFAULT_PAGE_SIZE_WITHOUT_PAGER = 1000;
	public final static int CROPSTRINGLENGTH = 30;

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
	
	public static enum SelectionBehaviour {
		NO_SELECTION,
		SINGLE_SELECTION;
	}
	
	public interface IneCellTableResources extends Resources {

        public IneCellTableResources INSTANCE =
                GWT.create(IneCellTableResources.class);

        /**
         * The styles used in this widget.
         */
        @Source("com/inepex/ineForm/client/STYLES/IneCellTable.css")
        CellTable.Style cellTableStyle();
	}
	
	private static TableRDesc getTRD(DescriptorStore descriptorStore, String objectDescName, String tableRenderDescriptorName) {
		if(tableRenderDescriptorName==null) {
			return descriptorStore.getDefaultTypedDesc(objectDescName, TableRDesc.class);
		} else {
			return descriptorStore.getNamedTypedDesc(objectDescName, tableRenderDescriptorName, TableRDesc.class);
		}
	
	}	
	
	final FlowPanel mainPanel = new FlowPanel();
	final CellTable<AssistedObject> cellTable;
	
	private DateTimeFormat defaultDateTimeFormat = DateTimeFormat.getFormat(IneFormProperties.INETABLE_DEFAULT_DATETIMEFORMAT);
	private DateTimeFormat defaultShortDateTimeFormat = DateTimeFormat.getFormat(IneFormProperties.INETABLE_DEFAULT_SHORT_DATETIMEFORMAT);
	private DateTimeFormat defaultSecDateTimeFormat = DateTimeFormat.getFormat(IneFormProperties.INETABLE_DEFAULT_SEC_DATETIMEFORMAT);
	
	protected String commandsTitle = "";
	protected List<UserCommand> commands = new ArrayList<IneTable.UserCommand>();
	protected RowStylesProvider rowStylesProvider = null; 
	protected Map<String, CustomCellContentDisplayer> cellContentDisplayers = null;
	
	// Properties
	protected final String objectDescriptorName;
	protected final TableRDesc tableRenderDescriptor;
	
	// Dependencies
	protected final IneDataConnector dataConnector;
	protected final DescriptorStore descStore;
	protected final AssistedObjectHandlerFactory handlerFactory;
	
	private SingleSelectionModel<AssistedObject> singleSelectionModel = null;
	protected SimplePager pager = null;
	private boolean showPager = true;
	
	private boolean rendered = false;
	
	private SelectionBehaviour selectionBehaviour = SelectionBehaviour.NO_SELECTION;
	
	protected Map<String, Header<String>> headers = new TreeMap<String, Header<String>>();
	
	DateProvider dateProvider;
	
	private boolean batchColumnAddig = false; //faster rendering

	public IneTable(DescriptorStore descriptorStore, String objectDescName,
			String tableRenderDescriptorName, IneDataConnector connector) {
		this(descriptorStore, objectDescName, getTRD(descriptorStore, objectDescName, tableRenderDescriptorName), connector);
	}
	
	/**
	 * Can not be injected yet. Do we need to make IneTable availale injected by a factory?
	 * Uses the default {@link TableRDesc}
	 * @param objectDescriptorName
	 * @param dataProvider
	 */
	public IneTable(DescriptorStore descStore,
					String objectDescriptorName,
					IneDataConnector dataProvider) {
		this(descStore, objectDescriptorName, (String)null, dataProvider);
	}
	
	/**
	 * IMPORTANT: Don't forget to call renderTable() before use!
	 * @param objectDescriptorName
	 * @param tableRenderDescriptor - add null to default value by DescriptorStore.get().getDefaultTRD(objectDescriptorName)
	 * @param dataProvider
	 */
	public IneTable(DescriptorStore descStore,
			String objectDescriptorName,
			TableRDesc tableRenderDescriptor,
			IneDataConnector dataProvider) {
		this.descStore = descStore;
		this.handlerFactory= new AssistedObjectHandlerFactory(descStore);
		
		cellTable = new CellTable<AssistedObject>(DEFAULT_PAGE_SIZE, IneCellTableResources.INSTANCE, KEY_PROVIDER) {
			@Override
			public void setRowData(int start, java.util.List<? extends AssistedObject> values) {
				super.setRowData(start, values);
				onRowDataChanged();
			};
			
			@Override
			public void redraw() {
				if(!batchColumnAddig) {
					super.redraw();
				} else {
					//dont need redraw while initializing table columns  
				}
			};
		};
		
		this.objectDescriptorName = objectDescriptorName;
		this.tableRenderDescriptor = tableRenderDescriptor != null  
				? tableRenderDescriptor
				: descStore.getDefaultTypedDesc(objectDescriptorName, TableRDesc.class);
		this.dataConnector = dataProvider;
		
		initWidget(mainPanel);
		mainPanel.add(cellTable);
	}
	
	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
	
//**** Set behaviour properties ****// 

	protected void onRowDataChanged() {
	}

	public void setCommandsTitle(String commandsTitle) {
		this.commandsTitle = commandsTitle;
	}
	
	public void addCommand(UserCommand command){
		this.commands.add(command);
	}

	public void addCommands(UserCommand... command){
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
		if(cellContentDisplayers==null) 
			cellContentDisplayers=new HashMap<String, CustomCellContentDisplayer>();
		cellContentDisplayers.put(columnId, cellContentDisplayer);
	}
	
	/**Also sets pagesize to DEFAULT_PAGE_SIZE, or DEFAULT_PAGE_SIZE_WITHOUT_PAGER 
	 * @param showPager
	 */
	public void setShowPager(boolean showPager) {
		if (showPager)
			pageSize = DEFAULT_PAGE_SIZE;
		else
			pageSize = DEFAULT_PAGE_SIZE_WITHOUT_PAGER;
		this.showPager = showPager;
	}

//**** Logic functions ****// 
	
	public void renderTable() {
		if (!rendered) {
			cellTable.addStyleName(ResourceHelper.getRes().style().ineTable());
			
			batchColumnAddig=true;
			initTableColumns();
			initTable();
			batchColumnAddig=false;
			cellTable.redraw();
			
			if (showPager)
				mainPanel.add(pager);
			
			rendered = true;
		}
	}
	
	private void initTable() {

		// Create a Pager to control the table.
		if (showPager) {
			SimplePager.Resources pagerResources = GWT
					.create(SimplePager.Resources.class);
			pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
					true);
			pager.setDisplay(cellTable);
		}
		cellTable.setPageSize(pageSize);

		if (selectionBehaviour == SelectionBehaviour.SINGLE_SELECTION) {
			// Add a selection model so that only single selection is available.
			singleSelectionModel = new SingleSelectionModel<AssistedObject>(KEY_PROVIDER);
			cellTable.setSelectionModel(singleSelectionModel);
			
		} else if (selectionBehaviour == SelectionBehaviour.NO_SELECTION) {
			cellTable.setSelectionModel(new NoSelectionModel<AssistedObject>());
		}
		
		// Add the CellList to the adapter in the database.
		dataConnector.addDataDisplay(cellTable);
		
		cellTable.setRowStyles(new PointerAndCustomRowStyleProvider());
	}

	private void initTableColumns() {

		ObjectDesc objectDesc = descStore.getOD(
		objectDescriptorName);

		for (Node<TableRDescBase> columnNode : tableRenderDescriptor.getRootNode()
				.getChildren()) {
			
			TableRDescBase colRenderDesc = columnNode.getNodeElement();

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
					System.out.println("You set complex id for a field, which is not a relation. "
									+ "(" + nodeIdAsList.get(0) + ")");
				}
			}
			
			IneTableColumn column = new IneTableColumn(new TextCell(), 
										columnNode.getNodeId(),
										(ColRDesc)columnNode.getNodeElement());
			
			String headerText = colRenderDesc.getDisplayName() != null ? colRenderDesc.getDisplayName() : null;
			if (headerText == null) {
				if (fieldDesc != null)
					headerText = fieldDesc.getDefaultDisplayName();
				else
					headerText = "";
			}
			
			Header<String> header = createHeader(
					((ColRDesc)columnNode.getNodeElement()).isSortable()
					, headerText
					, columnNode.getNodeId()
					, colRenderDesc.hasProp(ColRDesc.DEFAULTSORT)
					, colRenderDesc.hasProp(ColRDesc.DEFAULTSORTREVERSE));
			headers.put(columnNode.getNodeId(), header);
			cellTable.addColumn(column, header);

			//TODO setting width from colRenderDesc
//			String columnWidth = ((ColRDesc)columnNode.getNodeElement()).getColumnWidthAsString();
			
		}
		
		if(commands!=null) {
			
			List<HasCell<AssistedObject, ?>> commandList = new ArrayList<HasCell<AssistedObject, ?>>(); 
			
			for (int i = 0; i < commands.size(); i++) {
				commandList.add(new UserCommandColumnPart(
						  new LinkActionCell(commands.get(i).getCommandCellText()
								  		   , commands.get(i)
						                   , i != commands.size()-1)));
			}
			
			CompositeCell<AssistedObject> compCell = new CompositeCell<AssistedObject>(commandList);
			
			cellTable.addColumn(new UserCommandColumn(compCell),
								commandsTitle);
		}
		
	}
	
	protected Header<String> createHeader(boolean sortable, String text, String key, boolean defaultSort, boolean defaultSortReverse){
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
		//FIXME: valamiért kell ide null check... amikor egy 11 elemű táblában 2-11 van megjelenítve 
		//és visszafelé lapozunk(1-10es oldalra), akkor a displayed items olyan lista aminek a 0. eleme null így elszáll a kód
		//a if(kvo!=null && kvo.getId().equals(key))-es nullcheck úgy tűnik megoldja a dolgot, de valószínű, hogy vmi más hiba
		//indikátora a jelenség
		for(AssistedObject kvo : cellTable.getDisplayedItems()) {
			if(kvo!=null && kvo.getId().equals(key))
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
	
	private class IneTableColumn extends Column<AssistedObject, String>{
		
		private final String key;
		private final ColRDesc colRdesc;
		private final String deepestKey;
		private final CustomCellContentDisplayer customCellContentDisplayer;

		public IneTableColumn(Cell<String> cell, String key, ColRDesc colRdesc) {
			super(cell);
			this.key=key;
			this.colRdesc=colRdesc;
			this.deepestKey = SharedUtil.deepestKey(key);
			if(cellContentDisplayers!=null)
				this.customCellContentDisplayer=cellContentDisplayers.get(key);
			else
				customCellContentDisplayer=null;
		}
		
		
		
		@Override
		public void render(Context context, AssistedObject rowValue,
				 SafeHtmlBuilder sb) {
			AssistedObjectHandler rowHandler = handlerFactory.createHandler(rowValue);
			//custom cell display
			if(customCellContentDisplayer!=null) {
				String customHtmlContent = customCellContentDisplayer.getCustomCellContent(rowHandler, key, colRdesc);
					if(customHtmlContent!=null) {
						sb.appendHtmlConstant(customHtmlContent);
					}
			} else {
				try {	
					if (customCellContentDisplayer!=null)
						return;
					
					String result = null;
		
					if(SharedUtil.isMultilevelKey(key))
						rowHandler = rowHandler.getRelatedKVOMultiLevel(SharedUtil.listFromDotSeparated(key));
					
					//default cell display
					if (colRdesc.getPropValue(ColRDesc.AS_DATE) != null) {
						Long date = rowHandler.getLong(deepestKey);
						if (date != null)
							result = defaultDateTimeFormat.format(dateProvider.getDate(date));
					} else if (colRdesc.getPropValue(ColRDesc.AS_SHORTDATE) != null) {
						Long date = rowHandler.getLong(deepestKey);
						if (date != null)
							result = defaultShortDateTimeFormat.format(dateProvider.getDate(date));
					} else if (colRdesc.getPropValue(ColRDesc.AS_FRACTIALDIGITCOUNT) != null) {
						Double val = rowHandler.getDouble(deepestKey);
						if (val != null) {
							result = new NumberUtilCln().formatNumberToFractial(val, Integer.parseInt(colRdesc.getPropValue(ColRDesc.AS_FRACTIALDIGITCOUNT)));
						}
					} else if (colRdesc.getPropValue(ColRDesc.AS_DATE_WITHSEC) != null) {
						Long date = rowHandler.getLong(deepestKey);
						if (date != null)
							result = defaultSecDateTimeFormat.format(dateProvider.getDate(date));
					} else if (colRdesc.getPropValue(EnumListFW.enumValues) != null) {
						String[] enumValues = colRdesc.getPropValue(
								EnumListFW.enumValues).split(IFConsts.enumValueSplitChar);
						if (null!=rowHandler.getLong(deepestKey))
							result = enumValues[rowHandler.getLong(deepestKey).intValue()];
					} else if (colRdesc.getPropValue(ColRDesc.AS_GROUPTHOUSANDS) != null) {
						Long val = rowHandler.getLong(deepestKey);
						if (val != null)
							result = new NumberUtilCln().formatNumberGroupThousands(val);
					} else if (colRdesc.getPropValue(ColRDesc.AS_FORMATTEDDOUBLE)!=null) {
						Double val = rowHandler.getDouble(deepestKey);
						if (val != null)
							result = new NumberUtilCln().formatNumberToFractial(val);	
					} else
						result = rowHandler.getValueAsString(deepestKey);
		
					if (result == null)
						result = "";
		
					// Crop result
					int usingCorpsLeng = (colRdesc.getCustomCorpsWidth()!=null) ? 
							colRdesc.getCustomCorpsWidth() : CROPSTRINGLENGTH;
							
					result = result.length() > usingCorpsLeng ? result.substring(
							0, usingCorpsLeng) + "..." : result;
		
					sb.appendHtmlConstant(result);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public String getValue(AssistedObject rowValue) {
			//do nothing
			return "";
		}
	};
	
	
	private class PointerAndCustomRowStyleProvider implements RowStyles<AssistedObject> {

		@Override
		public String getStyleNames(AssistedObject row, int rowIndex) {
			String extraStyle = null;
			if(rowStylesProvider!=null) {
				extraStyle = rowStylesProvider.getStyleNames(row, rowIndex);
			}
			
			return 
				((selectionBehaviour == SelectionBehaviour.SINGLE_SELECTION) ? ResourceHelper.getRes().style().clickable() : "" ) +  
				((extraStyle == null) ? ("") : (" "+extraStyle));
		}
		
	}
	
	public static interface RowStylesProvider extends RowStyles<AssistedObject> {
	}
	
	public static interface CustomCellContentDisplayer {
		/**
		 * @return the html (or string) value of the pointed column's
		 */
		public String getCustomCellContent(AssistedObjectHandler rowKvo, String fieldId, ColRDesc colRDesc);
	}
	
	
	public static interface UserCommand {
		public String getCommandCellText();
		public void onCellClicked(AssistedObject kvoOfRow);
		public boolean visible(AssistedObject kvoOfRow);
	}
	
	private class UserCommandColumn  extends Column<AssistedObject, AssistedObject> {
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
		public void onBrowserEvent(Context context, Element elem,
				AssistedObject object, NativeEvent event) {
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
					.appendHtmlConstant("<a>").appendHtmlConstant(message)
					.appendHtmlConstant("</a>");
			if (appendSeparator) {
				hmtlBuilder.appendHtmlConstant("&nbsp;|&nbsp;").toSafeHtml();
			}
			
			this.html = hmtlBuilder.toSafeHtml();
		}
		
		@Override
		public void onBrowserEvent(Context context, Element parent, String value, 
				NativeEvent event, ValueUpdater<String> valueUpdater) {
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
			this.originalText=originalText;
			this.text=originalText;
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
			//nothing to do
			return "";
		}
		
	}
	
	public HasData<AssistedObject> getDataDisplay() {
		return cellTable;
	}
}
