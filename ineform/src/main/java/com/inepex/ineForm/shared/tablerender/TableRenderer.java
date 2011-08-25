package com.inepex.ineForm.shared.tablerender;

import java.util.List;

import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.EnumListFW;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.util.SharedUtil;

public abstract class TableRenderer {

	DescriptorStore descStore;
	
	protected ObjectDesc objectDesc;
	protected TableRDesc tableRDesc;
	protected StringBuffer sb;
	protected final DateFormatter dateFormatter;
	protected final NumberUtil numberUtil;
	
	
	boolean renderHeader = false;
	boolean renderLastFieldEnd = false;
	DateProvider dateProvider;

	public TableRenderer(DescriptorStore descStore
			, String objectDescName
			, String tableRDescName
			, DateFormatter dateFormatter
			, NumberUtil numberUtil
			, DateProvider dateProvider) {
		this.dateFormatter = dateFormatter;
		this.numberUtil=numberUtil;
		this.descStore = descStore;
		this.dateProvider = dateProvider;
		
		objectDesc = descStore.getOD(objectDescName);
		if (tableRDescName == null)
			tableRDesc = descStore.getDefaultTypedDesc(objectDescName, TableRDesc.class);
		else tableRDesc = descStore.getNamedTypedDesc(objectDescName, tableRDescName, TableRDesc.class);	
		
	}
	
	public TableRenderer(DescriptorStore descStore
			, ObjectDesc objectDesc
			, TableRDesc tableRDesc
			, DateFormatter dateFormatter
			, NumberUtil numberUtil) {
		super();
		this.numberUtil=numberUtil;
		this.objectDesc = objectDesc;
		this.tableRDesc = tableRDesc;
		this.dateFormatter = dateFormatter;
		this.descStore = descStore;
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
				renderFieldStart();
				ColRDesc colRenderDesc = (ColRDesc)columnNode.getNodeElement();
				if (!IneFormProperties.showIds && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
					continue;
				
				FDesc fieldDesc = getFieldDescForColumn(columnNode);
				
				AssistedObjectHandler kvoOrRelatedKvoChecker = factory.createHandler(kvo).getRelatedKVOMultiLevel(
						SharedUtil.listFromDotSeparated(columnNode.getNodeId()));
				sb.append(renderField(fieldDesc, colRenderDesc, kvoOrRelatedKvoChecker));
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
	
	protected void renderHeader() {
		renderHeaderStart();
		for (Node<TableRDescBase> columnNode : tableRDesc.getRootNode()
				.getChildren()) {
			renderHeaderFieldStart();
			if (!IneFormProperties.showIds && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
				continue;
			ColRDesc colRenderDesc = (ColRDesc)columnNode.getNodeElement();
			FDesc fieldDesc = getFieldDescForColumn(columnNode);
			
			String headerText = colRenderDesc.getDisplayName() != null ? colRenderDesc.getDisplayName() : null;
			if (headerText == null) {
				if (fieldDesc != null)
					headerText = fieldDesc.getDefaultDisplayName();
				else
					headerText = "";
			}
			
			sb.append(headerText);
			if (renderLastFieldEnd 
					|| !columnNode.equals(tableRDesc.getRootNode().getChildren().get(tableRDesc.getRootNode().getChildren().size()-1)))
				renderHeaderFieldEnd();
		}
		renderHeaderEnd();
	}
	
	protected abstract void renderHeaderStart();
	protected abstract void renderHeaderEnd();
	protected abstract void renderHeaderFieldStart();
	protected abstract void renderHeaderFieldEnd();
	
	
	protected String renderField(FDesc fieldDesc, ColRDesc colRdesc, AssistedObjectHandler rowValue){
		try {	
	
			String deepestKey = SharedUtil.deepestKey(fieldDesc.getKey());
			String result = null;

			//default cell display
			if (colRdesc.getPropValue(ColRDesc.AS_DATE) != null) {
				Long date = rowValue.getLong(deepestKey);
				if (date != null)
					result = dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_DATETIMEFORMAT, dateProvider.getDate(date));
			} else if (colRdesc.getPropValue(ColRDesc.AS_SHORTDATE) != null) {
				Long date = rowValue.getLong(deepestKey);
				if (date != null)
					result = dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_SHORT_DATETIMEFORMAT, dateProvider.getDate(date));
			} else if (colRdesc.getPropValue(ColRDesc.AS_DATE_WITHSEC) != null) {
				Long date = rowValue.getLong(deepestKey);
				if (date != null)
					result = dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_SEC_DATETIMEFORMAT, dateProvider.getDate(date));
			} else if (colRdesc.getPropValue(EnumListFW.enumValues) != null) {
				String[] enumValues = colRdesc.getPropValue(
						EnumListFW.enumValues).split(IFConsts.enumValueSplitChar);
				if (null!=rowValue.getLong(deepestKey))
					result = enumValues[rowValue.getLong(deepestKey).intValue()];
			} else if (colRdesc.getPropValue(ColRDesc.AS_GROUPTHOUSANDS) != null) {
				Long val = rowValue.getLong(deepestKey);
				if (val != null)
					result = numberUtil.formatNumberGroupThousands(val);
			} else if (colRdesc.getPropValue(ColRDesc.AS_FORMATTEDDOUBLE)!=null) {
				Double val = rowValue.getDouble(deepestKey);
				if (val != null)
					result = numberUtil.csvNumberToMin2Fractial(val);	
			} else if (colRdesc.getPropValue(ColRDesc.AS_FRACTIALDIGITCOUNT) != null) {
				Double val = rowValue.getDouble(deepestKey);
				if (val != null) {
					result = numberUtil.csvFormatNumberToFractial(val, Integer.parseInt(colRdesc.getPropValue(ColRDesc.AS_FRACTIALDIGITCOUNT)));
				}
			} else
				result = rowValue.getValueAsString(deepestKey);

			if (result == null)
				result = "";

			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
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
				System.out.println("You set complex id for a field, which is not a relation. "
								+ "(" + nodeIdAsList.get(0) + ")");
			}
		}
		return fieldDesc;
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
	
	
}
