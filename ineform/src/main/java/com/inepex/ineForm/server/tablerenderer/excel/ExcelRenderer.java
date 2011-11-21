package com.inepex.ineForm.server.tablerenderer.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineForm.shared.tablerender.TableRenderer;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ExcelRenderer extends TableRenderer{

	public static interface ExcelRendererFactory {
		public ExcelRenderer create(@Assisted("od") String objectDescName,
				@Assisted("td") @Nullable String tableRDescName, @Assisted Sheet sheet);
	}
	
	private final Sheet sheet;
	private int startRowNr;
	private int actualRowNr;
	private Row actualRow;
	private int actualCellNr;
	private Cell actualCell;
	
	@Inject
	public ExcelRenderer(DescriptorStore descStore,
			@Assisted("od") String objectDescName,
			@Assisted("td") @Nullable String tableRDescName,
			@Assisted Sheet sheet,
			AssistedObjectTableFieldRenderer fieldRenderer
			) {
		super(descStore, objectDescName, tableRDescName, fieldRenderer);
		this.sheet = sheet;
		startRowNr = 0;
		setRenderLastFieldEnd(true);
	}
	
	public void setStartRowNr(int rowNr){
		this.startRowNr = rowNr;
	}
	
	@Override
	protected void renderStart() {
		actualRowNr = startRowNr;		
	}

	@Override
	protected void renderEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderLineStart() {
		actualRow = sheet.createRow((short)actualRowNr);
		actualCellNr = 0;
	}

	@Override
	protected void renderLineEnd() {
		actualRowNr++;		
	}

	@Override
	protected void renderFieldStart() {
		actualCell = actualRow.createCell(actualCellNr);
		
	}
	
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
	protected void renderHeaderFieldStart() {
		renderFieldStart();
	}
	
	protected void renderHeaderField(String content){
		renderField(content);
	}

	@Override
	protected void renderHeaderFieldEnd() {
		renderFieldEnd();	
	}

	public int getActualRowNr() {
		return actualRowNr;
	}
	
}
