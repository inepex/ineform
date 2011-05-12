package com.inepex.ineForm.shared.tablerender;

import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.descriptor.DescriptorStore;


/**
 * it's MS Excel's favorite food
 * 
 */
public class TrtdRenderer extends TableRenderer{

	public TrtdRenderer(DescriptorStore descStore
			, String objectDescName
			, String tableRDescName
			, DateFormatter dateFormatter
			, NumberUtil numberUtil
			, DateProvider dateProvider) {
		super(descStore, objectDescName, tableRDescName, dateFormatter, numberUtil, dateProvider);
		setRenderLastFieldEnd(true);
	}

	@Override
	protected void renderLineStart() {
		sb.append("<tr>");
	}

	@Override
	protected void renderLineEnd() {
		sb.append("</tr>\n");
	}

	@Override
	protected void renderFieldStart() {
		sb.append("<td>");
	}

	@Override
	protected void renderFieldEnd() {
		sb.append("</td>");
		
	}

	@Override
	protected void renderStart() {
		sb.append("<table>\n");
	}

	@Override
	protected void renderEnd() {
		sb.append("</table>");
		
	}

	@Override
	protected void renderHeaderStart() {
		sb.append("<tr>");
	}

	@Override
	protected void renderHeaderEnd() {
		sb.append("</tr>\n");		
	}

	@Override
	protected void renderHeaderFieldStart() {
		sb.append("<th>");
	}

	@Override
	protected void renderHeaderFieldEnd() {
		sb.append("</th>");
	}

}
