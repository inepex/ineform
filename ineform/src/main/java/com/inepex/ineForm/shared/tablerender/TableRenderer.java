package com.inepex.ineForm.shared.tablerender;

import java.util.List;

import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineForm.shared.render.TableFieldRenderer.CustomCellContentDisplayer;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;

public abstract class TableRenderer {

	protected DescriptorStore descStore;
	
	protected ObjectDesc objectDesc;
	protected TableRDesc tableRDesc;
	protected StringBuffer sb;
	
	protected boolean renderHeader = false;
	protected boolean renderLastFieldEnd = false;

	protected final TableFieldRenderer fieldRenderer;
	
	public TableRenderer(DescriptorStore descStore
			, String objectDescName
			, String tableRDescName
			, TableFieldRenderer assistedObjectTableFieldRenderer) {
		this.descStore = descStore;
		this.fieldRenderer = assistedObjectTableFieldRenderer;
		
		objectDesc = descStore.getOD(objectDescName);
		if (tableRDescName == null)
			tableRDesc = descStore.getDefaultTypedDesc(objectDescName, TableRDesc.class);
		else tableRDesc = descStore.getNamedTypedDesc(objectDescName, tableRDescName, TableRDesc.class);		
	}
	
	public TableRenderer(DescriptorStore descStore
			, ObjectDesc objectDesc
			, TableRDesc tableRDesc
			, TableFieldRenderer assistedObjectTableFieldRenderer) {
		super();
		this.objectDesc = objectDesc;
		this.tableRDesc = tableRDesc;
		this.descStore = descStore;
		this.fieldRenderer = assistedObjectTableFieldRenderer;
	}

	public void addCellContentDisplayer(String columnId, CustomCellContentDisplayer cellContentDisplayer) {
		fieldRenderer.setCustomFieldRenderer(columnId, cellContentDisplayer);
	}


	public String render(List<AssistedObject> kvos){
		AssistedObjectHandlerFactory factory = new AssistedObjectHandlerFactory(descStore);
		sb = new StringBuffer();
		renderStart();
		if (renderHeader) renderHeader();
		
		for (AssistedObject kvo : kvos){
			renderLineStart();
			for (Node<TableRDescBase> columnNode : tableRDesc.getRootNode()
					.getChildren()) {
				ColRDesc colRenderDesc = (ColRDesc)columnNode.getNodeElement();
				if (!IneFormProperties.showIds && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
					continue;
				renderFieldStart();
				
				AssistedObjectHandler kvoOrRelatedKvoChecker = factory.createHandler(kvo).getRelatedKVOMultiLevel(
						SharedUtil.listFromDotSeparated(columnNode.getNodeId()));
				renderField(renderField(columnNode.getNodeId(), colRenderDesc, kvoOrRelatedKvoChecker));
								
				if (renderLastFieldEnd 
						|| !columnNode.equals(tableRDesc.getRootNode().getChildren().get(tableRDesc.getRootNode().getChildren().size()-1)))
					renderFieldEnd();
				
			}
			renderLineEnd();
		}
		renderEnd();
		return sb.toString();
	}
	
	protected abstract void renderStart();
	protected abstract void renderEnd();
	protected abstract void renderLineStart();
	protected abstract void renderLineEnd();
	protected abstract void renderFieldStart();
	protected abstract void renderFieldEnd();
	
	/**
	 * ovveride when you need the content in descendant
	 * @param content
	 */
	protected void renderField(String content){
		sb.append(content);
	}
	
	protected void renderHeader() {
		renderHeaderStart();
		for (Node<TableRDescBase> columnNode : tableRDesc.getRootNode()
				.getChildren()) {
			
			if (!IneFormProperties.showIds && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
				continue;
			ColRDesc colRenderDesc = (ColRDesc)columnNode.getNodeElement();
			FDesc fieldDesc = getFieldDescForColumn(columnNode);
			renderHeaderFieldStart(colRenderDesc, fieldDesc);
			String headerText = colRenderDesc.getDisplayName() != null ? colRenderDesc.getDisplayName() : null;
			if (headerText == null) {
				if (fieldDesc != null)
					headerText = fieldDesc.getDefaultDisplayName();
				else
					headerText = "";
			}
			renderHeaderField(columnNode.getNodeId(), headerText);
			
			if (renderLastFieldEnd 
					|| !columnNode.equals(tableRDesc.getRootNode().getChildren().get(tableRDesc.getRootNode().getChildren().size()-1)))
				renderHeaderFieldEnd();
		}
		renderHeaderEnd();
	}
	
	protected abstract void renderHeaderStart();
	protected abstract void renderHeaderEnd();
	protected abstract void renderHeaderFieldStart(ColRDesc colRDesc, FDesc fDesc);
	protected abstract void renderHeaderFieldEnd();
	protected void renderHeaderField(String key, String content){
		sb.append(content);
	}
	
	
	protected String renderField(String key, ColRDesc colRdesc, AssistedObjectHandler rowValue){
		fieldRenderer.setObjectAndDescriptor(rowValue.getAssistedObject(), tableRDesc);
		return fieldRenderer.getField(key);
	}
	
	protected FDesc getFieldDescForColumn(Node<TableRDescBase> columnNode){
		FDesc fieldDesc = null;
		List<String> nodeIdAsList = columnNode.getNodeIdAsList();

		if (nodeIdAsList.size() == 1)
			fieldDesc = objectDesc.getField(columnNode.getNodeId());
		else {
			try {
				fieldDesc = descStore.getRelatedFieldDescrMultiLevel(objectDesc, nodeIdAsList);
			} catch (Exception e) {
				fieldDesc = objectDesc.getField(nodeIdAsList.get(0));
				logException(e, nodeIdAsList.get(0));
			}
		}
		return fieldDesc;
	}
	
	private void logException(Exception e, String nodeId) {
		
		//TODO log it on the server side
//		if(GWT.isClient()) {
			System.out.println("You set complex id for a field, which is not a relation. "
					+ "(" + nodeId + ")");
//		} else {
//			org.slf4j.LoggerFactory.getLogger(TableRenderer.class)
//				.error("You set complex id for a field, which is not a relation. ({})", nodeId);
//		}
	}

	public boolean isRenderHeader() {
		return renderHeader;
	}

	public void setRenderHeader(boolean renderHeader) {
		this.renderHeader = renderHeader;
	}

	public boolean isRenderLastFieldEnd() {
		return renderLastFieldEnd;
	}

	public void setRenderLastFieldEnd(boolean renderLastFieldEnd) {
		this.renderLastFieldEnd = renderLastFieldEnd;
	}
	
	public TableFieldRenderer getFieldRenderer(){
		return fieldRenderer;
	}

}
