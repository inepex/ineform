package com.inepex.ineForm.server.tablerenderer.pdf;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.Node;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfAssistedObjectRenderer {

	private final PdfFontLoader pdfFontLoader;
	private final TableFieldRenderer fieldRenderer;
	private Font keyFont;
	private Font valueFont;
	private PdfPTable table;
	
	
	@Inject
	public PdfAssistedObjectRenderer(PdfFontLoader pdfFontLoader, 
			TableFieldRenderer fieldRenderer) {
		this.pdfFontLoader = pdfFontLoader;
		this.fieldRenderer = fieldRenderer;
		initFonts();
	}
			
	public TableFieldRenderer getFieldRenderer() {
		return fieldRenderer;
	}
	
	public void setKeyFont(Font keyFont) {
		this.keyFont = keyFont;
	}

	public void setValueFont(Font valueFont) {
		this.valueFont = valueFont;
	}
	
	private void initFonts(){
		keyFont = new Font(pdfFontLoader.getBaseFont());
		valueFont = new Font(pdfFontLoader.getBaseFont());
	}
	
	public void render(AssistedObject ao, TableRDesc trd){
		fieldRenderer.setObjectAndDescriptor(ao, trd);
		table = new PdfPTable(2);
		for (Node<TableRDescBase> columnNode : trd.getRootNode()
				.getChildren()) {
			String keyText = columnNode.getNodeElement().getDisplayName();			
			String valueText = fieldRenderer.getField(columnNode.getNodeId());
			table.addCell(createCell(keyText, true));
			table.addCell(createCell(valueText, false));
		}
	}
	
	
	private PdfPCell createCell(String content, boolean key){
		PdfPCell cell = new PdfPCell(new Paragraph(content, key ? keyFont : valueFont));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	
	public PdfPTable getTable(){
		return table;
	}
}
