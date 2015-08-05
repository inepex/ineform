package com.inepex.ineForm.server.tablerenderer.pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineForm.shared.tablerender.TableRenderer;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfRenderer extends TableRenderer {

	private static final Logger _logger = LoggerFactory.getLogger(PdfRenderer.class);
	
	public static interface PdfRendererFactory {
		public PdfRenderer create(@Assisted("od") String objectDescName,
				@Assisted("td") @Nullable String tableRDescName);
	}
	
	protected PdfStyle pdfStyle;
	protected PdfPTable table;
	protected int[] colWidthPctgs;
	protected boolean withNoBorders = true;
	private float[] colRelativeWidths;
	
	@Inject
	public PdfRenderer(DescriptorStore descStore,
			@Assisted("od") String objectDescName,
			@Assisted("td") @Nullable String tableRDescName,
			TableFieldRenderer fieldRenderer,
			PdfFontLoader pdfFontLoader
			) {
		super(descStore, objectDescName, tableRDescName, fieldRenderer);
		pdfStyle = new PdfStyle(pdfFontLoader);
		setRenderLastFieldEnd(false);
	}
		
	public void setColWidthPctgs(int[] colWidthPctgs) {
		this.colWidthPctgs = colWidthPctgs;
	}
	
	public void setColWidthRelative(float[] colRelativeWidths) {
		this.colRelativeWidths = colRelativeWidths;
		
	}
	
	public void setWithNoBorders(boolean withNoBorders) {
		this.withNoBorders = withNoBorders;
	}

	@Override
	protected void renderStart() {
		table = new PdfPTable(tableRDesc.getRootNode().getChildren().size());
		table.setWidthPercentage(100f);
		if (colWidthPctgs != null){
			try {
				table.setWidths(colWidthPctgs);
			} catch (DocumentException e){
				_logger.info("Exception:", e);
			}
		}
		
		if (colRelativeWidths != null){
			try {
				table.setWidths(colRelativeWidths);
			} catch (DocumentException e){
				_logger.info("Exception:", e);
			}
		}
	}

	@Override
	protected void renderEnd() {
		
	}

	@Override
	protected void renderLineStart() {
	}

	@Override
	protected void renderLineEnd() {
		
	}

	@Override
	protected void renderFieldStart() {
	}
	
	@Override
	protected void renderField(String content, ColRDesc colRDesc){
		PdfPCell cell = new PdfPCell(new Paragraph(content, pdfStyle.getDataCellFont()));
		cell.setColspan(1);
		
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		if (colRDesc != null && colRDesc.getHAlign() != null) {
			int alignment = Element.ALIGN_CENTER;
			switch (colRDesc.getHAlign()) {
			case LEFT:
				alignment = Element.ALIGN_LEFT;
				break;
			case RIGHT:
				alignment = Element.ALIGN_RIGHT;
				break;
			case CENTER:
				alignment = Element.ALIGN_CENTER;
				break;
			default:
				break;
			}
			
			cell.setHorizontalAlignment(alignment);
		}
		
		if (withNoBorders) {
			cell.setBorder(Rectangle.NO_BORDER);			
		}
		customizeFieldCell(cell);
		table.addCell(cell);
	}
	
	protected void customizeFieldCell(PdfPCell cell){
	}

	@Override
	protected void renderFieldEnd() {

	}

	@Override
	protected void renderHeaderStart() {
	}

	@Override
	protected void renderHeaderEnd() {
	}
	
	@Override
	protected void renderHeaderFieldStart(ColRDesc colRDesc, FDesc fDesc) {
	}
	
	@Override
	protected void renderHeaderField(String key, String content){
		if (renderHeader){
			PdfPCell cell = new PdfPCell(new Paragraph(content, pdfStyle.getHeaderFont()));
			cell.setColspan(1);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			customizeHeaderCell(cell);
			table.addCell(cell);

		}
	}

	protected void customizeHeaderCell(PdfPCell cell){
	}
	
	@Override
	protected void renderHeaderFieldEnd() {
	}

	public PdfPTable getTable() {
		return table;
	}

	public PdfStyle getPdfStyle() {
		return pdfStyle;
	}

	

	

}
