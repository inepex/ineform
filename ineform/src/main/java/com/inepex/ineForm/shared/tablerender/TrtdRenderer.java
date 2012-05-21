package com.inepex.ineForm.shared.tablerender;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;


/**
 * it's MS Excel's favorite food
 * 
 */
public class TrtdRenderer extends TableRenderer{

	public static interface TrtdRendererFactory {
		public TrtdRenderer create(@Assisted("od") String objectDescName,
				@Assisted("td") @Nullable String tableRDescName);
	}
	
	@Inject
	public TrtdRenderer(DescriptorStore descStore,
			@Assisted("od") String objectDescName,
			@Assisted("td") @Nullable String tableRDescName,
			AssistedObjectTableFieldRenderer fieldRenderer) {
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
	protected void renderHeaderFieldEnd() {
		sb.append("</th>");
	}

	@Override
	protected void renderHeaderFieldStart(ColRDesc colRDesc, FDesc fDesc) {
		sb.append("<th>");
	}

}
