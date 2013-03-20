package com.inepex.ineForm.server.tablerenderer.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class ExcelAssistedObjectTRDRenderer {

	private final TableFieldRenderer fieldRenderer;
	private final DescriptorStore descriptorStore;

	@Inject
	public ExcelAssistedObjectTRDRenderer(TableFieldRenderer fieldRenderer,
			DescriptorStore descriptorStore) {
		this.fieldRenderer = fieldRenderer;
		this.descriptorStore = descriptorStore;
	}
		
	public TableFieldRenderer getFieldRenderer() {
		return fieldRenderer;
	}

	public void render(AssistedObject ao, String tableRDescName, Sheet sheet, int rowNo, int colNo){
		TableRDesc tableRDesc;
		if (tableRDescName == null){
			tableRDesc = descriptorStore.getDefaultTypedDesc(ao.getDescriptorName(), TableRDesc.class);
		} else {
			tableRDesc = descriptorStore.getNamedTypedDesc(ao.getDescriptorName(), tableRDescName, TableRDesc.class);
		}
		for (Node<TableRDescBase> columnNode : tableRDesc.getRootNode()
				.getChildren()) {
			String keyText = columnNode.getNodeElement().getDisplayName();
			fieldRenderer.setObjectAndDescriptor(ao, tableRDesc);
			String valueText = fieldRenderer.getField(columnNode.getNodeId());
			Row row = ExcelHelper.getOrCreateRow(sheet, rowNo++); 
			Cell cell = row.createCell(colNo);
			cell.setCellValue(keyText);
			cell = row.createCell(colNo + 1);
			cell.setCellValue(valueText);
		}
	}

}
