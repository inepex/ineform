package com.inepex.ineForm.server.tablerenderer.pdf;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfAssistedObjectRenderer {

	private final TableFieldRenderer fieldRenderer;
	private final PdfStyle pdfStyle;
	private final DescriptorStore descStore;
	private Font keyFont;
	private Font valueFont;
	private PdfPTable table;
	
	
	@Inject
	public PdfAssistedObjectRenderer(TableFieldRenderer fieldRenderer, 
			PdfStyle pdfStyle,
			DescriptorStore descStore) {
		this.fieldRenderer = fieldRenderer;
		this.pdfStyle = pdfStyle;
		this.descStore = descStore;
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
		keyFont = pdfStyle.getHeaderFont();
		valueFont = pdfStyle.getDataCellFont();
	}
	
	public void render(AssistedObject ao){
		render(ao, (String)null);
	}
	
	public void render(AssistedObject ao, String tableRDesc){
		if (tableRDesc == null || tableRDesc.equals(DescriptorStore.DEFAULT_DESC_KEY)){
			render(ao, descStore.getDefaultTypedDesc(ao.getDescriptorName(), TableRDesc.class));	
		} else {
			render(ao, descStore.getNamedTypedDesc(ao.getDescriptorName(), tableRDesc, TableRDesc.class));
		}		
	}
	
	public void render(AssistedObject ao, TableRDesc trd){
		fieldRenderer.setObjectAndDescriptor(ao, trd);
		ObjectDesc od = descStore.getOD(ao.getDescriptorName());
		table = new PdfPTable(2);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		for (Node<TableRDescBase> columnNode : trd.getRootNode()
				.getChildren()) {
			String keyText = columnNode.getNodeElement().getDisplayName();
			if (keyText == null){
				keyText = od.getField(columnNode.getHierarchicalId()).getDefaultDisplayName();
			}
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
