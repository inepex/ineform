package com.inepex.ineForm.server.tablerenderer.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelHelper {

	public static void writeCell(Sheet sheet, int rowNo, int col, String text){
		Cell cell = getOrCreateRow(sheet, rowNo).createCell(col);
		cell.setCellValue(text);
	}
	
	public static Row getOrCreateRow(Sheet sheet, int rowNo){
		Row row;
		if (sheet.getRow(rowNo) == null){
			row = sheet.createRow(rowNo++);				
		} else {
			row = sheet.getRow(rowNo);
		}
		return row;
	}
}
