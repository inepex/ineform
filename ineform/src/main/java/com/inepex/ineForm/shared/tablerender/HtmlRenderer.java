package com.inepex.ineForm.shared.tablerender;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class HtmlRenderer extends TableRenderer{

	public static interface HtmlRendererFactory {
		public HtmlRenderer create(@Assisted("od") String objectDescName,
				@Assisted("td") @Nullable String tableRDescName);
	}	
	
	@Inject
	public HtmlRenderer(DescriptorStore descStore
			, @Assisted("od") String objectDescName
			, @Assisted("td") @Nullable String tableRDescName
			, AssistedObjectTableFieldRenderer fieldRenderer) {
		super(descStore, objectDescName, tableRDescName, fieldRenderer);
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
