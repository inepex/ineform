package com.inepex.ineForm.client.resources;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.DataGrid;

public class DataGridStyleAdapter implements DataGrid.Style{
	
	private CellTable.Style cellStyle() {
		return ResourceHelper.cellTableResources().cellTableStyle();
	}

	@Override
	public boolean ensureInjected() {
		return cellStyle().ensureInjected();
	}

	@Override
	public String getText() {
		return cellStyle().getText();
	}

	@Override
	public String getName() {
		return cellStyle().getName();
	}

	@Override
	public String dataGridCell() { return cellStyle().cellTableCell();}

	@Override
	public String dataGridEvenRow() { return cellStyle().cellTableEvenRow();}

	@Override
	public String dataGridEvenRowCell() { return cellStyle().cellTableEvenRowCell();}

	@Override
	public String dataGridFirstColumn() { return cellStyle().cellTableFirstColumn();}

	@Override
	public String dataGridFirstColumnFooter() { return cellStyle().cellTableFirstColumnFooter();}

	@Override
	public String dataGridFirstColumnHeader() { return cellStyle().cellTableFirstColumnHeader();}

	@Override
	public String dataGridFooter() { return cellStyle().cellTableFooter();}

	@Override
	public String dataGridHeader() { return cellStyle().cellTableHeader();}

	@Override
	public String dataGridHoveredRow() { return cellStyle().cellTableHoveredRow();}

	@Override
	public String dataGridHoveredRowCell() { return cellStyle().cellTableHoveredRowCell();}

	@Override
	public String dataGridKeyboardSelectedCell() { return cellStyle().cellTableKeyboardSelectedCell();}

	@Override
	public String dataGridKeyboardSelectedRow() { return cellStyle().cellTableKeyboardSelectedRow();}

	@Override
	public String dataGridKeyboardSelectedRowCell() { return cellStyle().cellTableKeyboardSelectedRowCell();}

	@Override
	public String dataGridLastColumn() { return cellStyle().cellTableLastColumn();}

	@Override
	public String dataGridLastColumnFooter() { return cellStyle().cellTableLastColumnFooter();}

	@Override
	public String dataGridLastColumnHeader() { return cellStyle().cellTableLastColumnHeader();}

	@Override
	public String dataGridOddRow() { return cellStyle().cellTableOddRow();}

	@Override
	public String dataGridOddRowCell() { return cellStyle().cellTableOddRowCell();}
	
	@Override
	public String dataGridSelectedRow() { return cellStyle().cellTableSelectedRow();}

	@Override
	public String dataGridSelectedRowCell() { return cellStyle().cellTableSelectedRowCell();}

	@Override
	public String dataGridSortableHeader() { return cellStyle().cellTableSortableHeader();}

	@Override
	public String dataGridSortedHeaderAscending() { return cellStyle().cellTableSortedHeaderAscending();}

	@Override
	public String dataGridSortedHeaderDescending() { return cellStyle().cellTableSortedHeaderDescending();}

	@Override
	public String dataGridWidget() { return cellStyle().cellTableWidget();}

}
