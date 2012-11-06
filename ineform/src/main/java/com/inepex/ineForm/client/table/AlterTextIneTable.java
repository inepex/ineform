package com.inepex.ineForm.client.table;

import com.google.gwt.user.client.ui.HTML;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

/**
 * displaying an HTML element with the "alterText" instead of an empty table
 * 
 */
public class AlterTextIneTable extends IneTable {
	
	private final HTML alterHtml = new HTML();
	
	public AlterTextIneTable(DescriptorStore descStore, String objectDescriptorName
						   , TableRDesc tableRenderDescriptor, IneDataConnector dataProvider, String alterText,
						   TableFieldRenderer fieldRenderer) {
		super(descStore, objectDescriptorName, tableRenderDescriptor, dataProvider, fieldRenderer);
		initAlterText(alterText);
	}
	
	private void initAlterText(String alterText) {
		mainPanel.add(this.alterHtml);
		alterHtml.setHTML(alterText);
		alterHtml.setVisible(false);
	}

	@Override
	protected void onRowDataChanged() {
		if(cellTable.getVisibleItemCount()<1) {
			cellTable.setVisible(false);
			if(pager!=null) pager.setVisible(false);
			alterHtml.setVisible(true);
		} else {
			cellTable.setVisible(true);
			if(pager!=null) pager.setVisible(true);
			alterHtml.setVisible(false);
		}
	}
}
