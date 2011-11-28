package com.inepex.ineForm.shared.tablerender;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class CsvRenderer extends TableRenderer{

	String separator = ",";
	
	public static interface CsvRendererFactory {
		public CsvRenderer create(@Assisted("od") String objectDescName,
				@Assisted("td") @Nullable String tableRDescName);
	}
	
	@Inject
	public CsvRenderer(DescriptorStore descStore,
			@Assisted("od") String objectDescName,
			@Assisted("td") @Nullable String tableRDescName,
			AssistedObjectTableFieldRenderer fieldRenderer
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
	protected void renderHeaderFieldStart() {
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
	protected void renderHeaderField(String content){
		sb.append("\"" + content + "\"");
	}
	
	
	
	
}
