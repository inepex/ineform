package com.inepex.ineForm.shared.tablerender;

import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class HtmlRenderer extends TableRenderer{

	public HtmlRenderer(DescriptorStore descStore
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
		sb.append("<html>\n" +
				"<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
				"<style type=\"text/css\">" +
					"td {border: 1px solid black;}\n"+
					"th {background-color: #f2f2f2; border: 1px solid black;}\n" +
					"table {text-align: left; empty-cells: show; border-collapse: collapse;}" +
				"</style>" +
				"</head>"+
				"<table>\n");
	}

	@Override
	protected void renderEnd() {
		sb.append("</table></html>");
		
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
