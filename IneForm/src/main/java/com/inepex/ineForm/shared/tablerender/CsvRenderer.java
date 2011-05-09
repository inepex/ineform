package com.inepex.ineForm.shared.tablerender;

import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class CsvRenderer extends TableRenderer{

	String separator = ",";
	
	public CsvRenderer(DescriptorStore descStore
			, String objectDescName
			, String tableRDescName
			, DateFormatter dateFormatter
			, NumberUtil numberUtil
			, DateProvider dateProvider) {
		super(descStore, objectDescName, tableRDescName, dateFormatter, numberUtil, dateProvider);
		setRenderLastFieldEnd(false);
	}
	
	public CsvRenderer(DescriptorStore descStore
			, ObjectDesc objectDesc
			, TableRDesc tableRDesc
			, DateFormatter dateFormatter
			, NumberUtil numberUtil) {
		super(descStore, objectDesc, tableRDesc, dateFormatter, numberUtil);
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
	
	
	
	
}
