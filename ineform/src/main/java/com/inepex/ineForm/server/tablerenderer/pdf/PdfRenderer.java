package com.inepex.ineForm.server.tablerenderer.pdf;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineForm.shared.tablerender.TableRenderer;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.itextpdf.text.pdf.PdfPTable;

public class PdfRenderer extends TableRenderer {

	public static interface PdfRendererFactory {
		public PdfRenderer create(@Assisted("od") String objectDescName,
				@Assisted("td") @Nullable String tableRDescName);
	}
	
	protected PdfPTable table;
	
	@Inject
	public PdfRenderer(DescriptorStore descStore,
			@Assisted("od") String objectDescName,
			@Assisted("td") @Nullable String tableRDescName,
			AssistedObjectTableFieldRenderer fieldRenderer
			) {
		super(descStore, objectDescName, tableRDescName, fieldRenderer);
		setRenderLastFieldEnd(false);
	}
	
	@Override
	protected void renderStart() {
		table = new PdfPTable(tableRDesc.getRootNode().getChildren().size());
		tableRDesc.getProps();
	}

	@Override
	protected void renderEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderLineStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderLineEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderFieldStart() {
		// TODO Auto-generated method stub
		
	}
	
	protected void renderField(String content){
		table.addCell(content);
	}

	@Override
	protected void renderFieldEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderHeaderStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void renderHeaderEnd() {
		// TODO Auto-generated method stub
		
	}
	
	protected void renderHeaderField(String content){
		renderField(content);
	}

	@Override
	protected void renderHeaderFieldEnd() {
		// TODO Auto-generated method stub
		
	}

	public PdfPTable getTable() {
		return table;
	}

	@Override
	protected void renderHeaderFieldStart(ColRDesc colRDesc, FDesc fDesc) {
		// TODO Auto-generated method stub
		
	}
	

}
