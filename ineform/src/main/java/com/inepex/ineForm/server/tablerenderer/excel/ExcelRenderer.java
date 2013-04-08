package com.inepex.ineForm.server.tablerenderer.excel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineForm.shared.tablerender.TableRenderer;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;

public class ExcelRenderer extends TableRenderer{

	public static interface ExcelRendererFactory {
		public ExcelRenderer create(@Assisted("od") String objectDescName,
				@Assisted("td") @Nullable String tableRDescName, @Assisted Sheet sheet, @Assisted boolean setCellTypes);
	}

	protected final Sheet sheet;
	protected int startRowNr;
	protected int actualRowNr;
	protected Row actualRow;
	protected int startCellNr;
	protected int actualCellNr;
	protected Cell actualCell;
	protected boolean setCellTypes;
	
	protected HashMap<String, CellStyle> definedStyles;

	@Inject
	public ExcelRenderer(DescriptorStore descStore,
			@Assisted("od") String objectDescName,
			@Assisted("td") @Nullable String tableRDescName,
			@Assisted Sheet sheet,
			TableFieldRenderer fieldRenderer,
			@Assisted boolean setCellTypes
			) {
		super(descStore, objectDescName, tableRDescName, fieldRenderer);
		this.sheet = sheet;
		startRowNr = startCellNr = 0;
		setRenderLastFieldEnd(true);
		this.setCellTypes = setCellTypes;
	}

	public void setStartRowNr(int rowNr){
		this.startRowNr = rowNr;
	}
	
	public void setStartCellNr(int cellNr){
		this.startCellNr = cellNr;
	}

	@Override
	protected void renderStart() {
		actualRowNr = startRowNr;		
	}

	@Override
	protected void renderEnd() {
		for(int i = startCellNr; i <= actualCellNr; i++){
			sheet.autoSizeColumn(i);
		}
	}

	@Override
	protected void renderLineStart() {
		actualRow = sheet.createRow((short)actualRowNr);
		actualCellNr = startCellNr;
	}

	@Override
	protected void renderLineEnd() {
		actualRowNr++;		
	}

	@Override
	protected void renderFieldStart() {
		actualCell = actualRow.createCell(actualCellNr);

	}

	@Override
	protected void renderField(String content){
		actualCell.setCellValue(content);
	}

	@Override
	protected void renderFieldEnd() {
		actualCellNr++;		
	}

	@Override
	protected void renderHeaderStart() {
		renderLineStart();
	}

	@Override
	protected void renderHeaderEnd() {
		renderLineEnd();
	}

	@Override
	protected void renderHeaderField(String key, String content){
		renderField(content);
	}

	@Override
	protected void renderHeaderFieldEnd() {
		renderFieldEnd();	
	}

	public int getActualRowNr() {
		return actualRowNr;
	}

	@Override
	protected void renderHeaderFieldStart(ColRDesc colRDesc, FDesc fDesc) {
		renderFieldStart();
	}

	@Override
	public String render(List<? extends AssistedObject> kvos){
		if(!setCellTypes){
			return super.render(kvos);		
		}
		AssistedObjectHandlerFactory factory = new AssistedObjectHandlerFactory(descStore);
		renderStart();
		if (renderHeader) renderHeader();

		for (AssistedObject kvo : kvos){
			renderLineStart();
			for (Node<TableRDescBase> columnNode : tableRDesc.getRootNode()
					.getChildren()) {
				renderFieldStart();	
				ColRDesc colRenderDesc = (ColRDesc)columnNode.getNodeElement();
				if (!IneFormProperties.showIds && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
					continue;

				String key = columnNode.getNodeId();
				
				AssistedObjectHandler kvoOrRelatedKvoChecker = factory.createHandler(kvo).getRelatedKVOMultiLevel(
						SharedUtil.listFromDotSeparated(key));
				
				fieldRenderer.setObjectAndDescriptor(kvoOrRelatedKvoChecker.getAssistedObject(), tableRDesc);
				
				if(fieldRenderer.containsCustomizer(key)){
					actualCell.setCellValue(fieldRenderer.getFieldByCustomizer(key));
				}
				else{ 
					String deepestKey = SharedUtil.deepestKey(key);
					if(SharedUtil.isMultilevelKey(key)){
						kvoOrRelatedKvoChecker = kvoOrRelatedKvoChecker.getRelatedKVOMultiLevel(SharedUtil.listFromDotSeparated(key));
					}
					
					if (colRenderDesc.getPropValue(ColRDesc.EXCEL_NUMBERFORMAT) != null) {
						Double num = kvoOrRelatedKvoChecker.getDouble(deepestKey);
						if(num != null){
							actualCell.setCellValue(num);
						}
						setDataFormatForActualCell(colRenderDesc.getPropValue(ColRDesc.EXCEL_NUMBERFORMAT));
						
					}
					else if (colRenderDesc.getPropValue(ColRDesc.EXCEL_DATETIMEFORMAT) != null) {
						Long date = kvoOrRelatedKvoChecker.getLong(deepestKey);
						if(date != null){
							actualCell.setCellValue(getGMTCalendar(date));
						}
						setDataFormatForActualCell(colRenderDesc.getPropValue(ColRDesc.EXCEL_DATETIMEFORMAT));
					}
					else{
						actualCell.setCellValue(fieldRenderer.getField(deepestKey));
					}
					
				}
				cellValueSet(key, kvoOrRelatedKvoChecker);
				
				if (renderLastFieldEnd 
						|| !columnNode.equals(tableRDesc.getRootNode().getChildren().get(tableRDesc.getRootNode().getChildren().size()-1)))
					renderFieldEnd();

			}
			renderLineEnd();
		}
		
		renderEnd();
		return "";
	}
	
	protected Calendar getGMTCalendar(long timeInMillis){
		Calendar c =  Calendar.getInstance();
		c.setTimeInMillis(timeInMillis);
//		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		return c;
	}
	
	protected void setDataFormatForActualCell(String format){
		actualCell.setCellStyle(getOrCreateCellStyle(format));
	}
	
	public CellStyle getOrCreateCellStyle(String format){
		if(definedStyles == null){
			definedStyles = new HashMap<String, CellStyle>();
		}
		if(!definedStyles.containsKey(format)){
			CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(format));
			definedStyles.put(format, cellStyle);
		}
		return definedStyles.get(format);
	}
	
	protected void cellValueSet(String key, AssistedObjectHandler rowKvo){}

}
