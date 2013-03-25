package com.inepex.ineForm.shared.tablerender;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class CsvRenderer extends TableRenderer{

	public static interface CustomLineStart {
		public void renderLineStart(StringBuffer sb);
	}
	
	String separator = ",";
	
	protected CustomLineStart customLineStart;
	
	public static interface CsvRendererFactory {
		public CsvRenderer create(@Assisted("od") String objectDescName,
				@Assisted("td") @Nullable String tableRDescName);
	}
	
	@Inject
	public CsvRenderer(DescriptorStore descStore,
			@Assisted("od") String objectDescName,
			@Assisted("td") @Nullable String tableRDescName,
			TableFieldRenderer fieldRenderer
			) {
		super(descStore, objectDescName, tableRDescName, fieldRenderer);
		setRenderLastFieldEnd(false);
	}
	
	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	@Override
	protected void renderLineStart() {
		if (customLineStart != null){
			customLineStart.renderLineStart(sb);
		}
	}

	@Override
	protected void renderLineEnd() {
		sb.append("\n");
		
	}

	@Override
	protected void renderFieldStart() {
		
	}

	@Override
	protected void renderFieldEnd() {
		sb.append(separator);
		
	}

	@Override
	protected void renderStart() {
	}

	@Override
	protected void renderEnd() {
		
	}

	@Override
	protected void renderHeaderStart() {
		
	}

	@Override
	protected void renderHeaderEnd() {
		sb.append("\n");
	}


	@Override
	protected void renderHeaderFieldEnd() {
		sb.append(separator);
		
	}
	@Override
	protected void renderField(String content){
		sb.append("\"" + content + "\"");
	}
	@Override
	protected void renderHeaderField(String key, String content){
		sb.append("\"" + content + "\"");
	}

	@Override
	protected void renderHeaderFieldStart(ColRDesc colRDesc, FDesc fDesc) {
		// TODO Auto-generated method stub
		
	}

	public void setCustomLineStart(CustomLineStart customLineStart) {
		this.customLineStart = customLineStart;
	}
	
	
}
