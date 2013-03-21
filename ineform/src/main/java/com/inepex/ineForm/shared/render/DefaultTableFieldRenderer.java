package com.inepex.ineForm.shared.render;

import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.EnumListFW;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.util.SharedUtil;

public class DefaultTableFieldRenderer implements TableFieldRenderer {
	
	protected final AssistedObjectHandlerFactory handlerFactory;
	private final DateFormatter dateFormatter;
	private NumberUtil numberUtil;
	
	private TableRDesc tableRDesc;
	private AssistedObject object;
	private Map<String, CustomCellContentDisplayer> customizers = new TreeMap<String, TableFieldRenderer.CustomCellContentDisplayer>();

	@Inject
	public DefaultTableFieldRenderer(AssistedObjectHandlerFactory handlerFactory, DateFormatter dateFormatter,
			NumberUtil numberUtil) {
		this.handlerFactory = handlerFactory;
		this.dateFormatter = dateFormatter;
		this.numberUtil=numberUtil;
		
	}
	
	/* (non-Javadoc)
	 * @see com.inepex.ineForm.shared.render.TableFieldRenderer#setObjectAndDescriptor(com.inepex.ineom.shared.assistedobject.AssistedObject, com.inepex.ineForm.shared.descriptorext.TableRDesc)
	 */
	@Override
	public void setObjectAndDescriptor(AssistedObject object, TableRDesc tableRDesc){
		this.tableRDesc = tableRDesc;
		this.object = object;
	}
	
	/* (non-Javadoc)
	 * @see com.inepex.ineForm.shared.render.TableFieldRenderer#setCustomFieldRenderer(java.lang.String, com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer.CustomCellContentDisplayer)
	 */
	@Override
	public void setCustomFieldRenderer(String key, CustomCellContentDisplayer customCellContentDisplayer){
		customizers.put(key, customCellContentDisplayer);
		
	}
	
	/* (non-Javadoc)
	 * @see com.inepex.ineForm.shared.render.TableFieldRenderer#containsCustomizer(java.lang.String)
	 */
	@Override
	public boolean containsCustomizer(String key){
		return customizers.get(key)!=null;
	}
	
	/* (non-Javadoc)
	 * @see com.inepex.ineForm.shared.render.TableFieldRenderer#getFieldByCustomizer(java.lang.String)
	 */
	@Override
	public String getFieldByCustomizer(String key){
		ColRDesc colRdesc = (ColRDesc)tableRDesc.getRootNode().findNodeByHierarchicalId(key).getNodeElement();
		AssistedObjectHandler rowHandler = handlerFactory.createHandler(object);
		String customHtmlContent = customizers.get(key).getCustomCellContent(rowHandler, key, colRdesc);
		return customHtmlContent;
	}
	
	/* (non-Javadoc)
	 * @see com.inepex.ineForm.shared.render.TableFieldRenderer#getField(java.lang.String)
	 */
	@Override
	public String getField(String key){
		ColRDesc colRdesc = (ColRDesc)tableRDesc.getRootNode().findNodeByHierarchicalId(key).getNodeElement();
		String deepestKey = SharedUtil.deepestKey(key);
		StringBuffer sb = new StringBuffer();
		
		
		AssistedObjectHandler rowHandler = handlerFactory.createHandler(object);
		//custom cell display
		if(customizers.get(key)!=null) {
			String customHtmlContent = customizers.get(key).getCustomCellContent(rowHandler, key, colRdesc);
				if(customHtmlContent!=null) {
					sb.append(customHtmlContent);
				}
			return sb.toString();
		} else {
			try {	
				
				String result = null;
	
				if(SharedUtil.isMultilevelKey(key))
					rowHandler = rowHandler.getRelatedKVOMultiLevel(SharedUtil.listFromDotSeparated(key));
				
				//default cell display
				if (colRdesc.getPropValue(ColRDesc.AS_DATE) != null) {
					Long date = rowHandler.getLong(deepestKey);
					if (date != null)
						result = dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_DATETIMEFORMAT, date);
				} else if (colRdesc.getPropValue(ColRDesc.AS_SHORTDATE) != null) {
					Long date = rowHandler.getLong(deepestKey);
					if (date != null)
						result = dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_SHORT_DATETIMEFORMAT, date);
				} else if (colRdesc.getPropValue(ColRDesc.AS_HOURMIN) != null) {
					Long date = rowHandler.getLong(deepestKey);
					if (date != null)
						result = dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_HOURMIN, date);
				} else if (colRdesc.getPropValue(ColRDesc.AS_HOURMINSEC) != null) {
					Long date = rowHandler.getLong(deepestKey);
					if (date != null)
						result = dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_HOURMINSEC, date);
				} else if (colRdesc.getPropValue(ColRDesc.AS_FRACTIALDIGITCOUNT) != null) {
					Double val = rowHandler.getDouble(deepestKey);
					if (val != null) {
						result = numberUtil.formatNumberToFractial(val, Integer.parseInt(colRdesc.getPropValue(ColRDesc.AS_FRACTIALDIGITCOUNT)));
					}
				} else if (colRdesc.getPropValue(ColRDesc.AS_DATE_WITHSEC) != null) {
					Long date = rowHandler.getLong(deepestKey);
					if (date != null)
						result = dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_SEC_DATETIMEFORMAT, date);
				} else if (colRdesc.getPropValue(EnumListFW.enumValues) != null) {
					String[] enumValues = colRdesc.getPropValue(
							EnumListFW.enumValues).split(IFConsts.enumValueSplitChar);
					if (null!=rowHandler.getLong(deepestKey))
						result = enumValues[rowHandler.getLong(deepestKey).intValue()];
				} else if (colRdesc.getPropValue(ColRDesc.AS_GROUPTHOUSANDS) != null) {
					Long val = rowHandler.getLong(deepestKey);
					if (val != null)
						result = numberUtil.formatNumberGroupThousands(val);
				} else if (colRdesc.getPropValue(ColRDesc.AS_FORMATTEDDOUBLE)!=null) {
					Double val = rowHandler.getDouble(deepestKey);
					if (val != null)
						result = numberUtil.formatNumberToFractial(val);	
				} else if (colRdesc.getPropValue(ColRDesc.AS_FRACTIALDIGITCOUNT) != null) {
					Double val = rowHandler.getDouble(deepestKey);
					if (val != null) {
						result = numberUtil.csvFormatNumberToFractial(val, Integer.parseInt(colRdesc.getPropValue(ColRDesc.AS_FRACTIALDIGITCOUNT)));
					}
				} else
					result = rowHandler.getValueAsString(deepestKey);
	
				if (result == null)
					result = "";
	
				// Crop result
				int usingCropsLeng = colRdesc.getCropWidth();
				if (usingCropsLeng != 0) {
					result = result.length() > usingCropsLeng ? result.substring(
							0, usingCropsLeng) + "..." : result;
				}
				return result;
				
			} catch (Exception e) {
				logException(e, key);
				return "";
			}
		}
	}
	
	private void logException(Exception e, String key) {
		//TODO log it on the server side
//		if(GWT.isClient()) {
			System.out.println("Value or custom displayer not found for key: " + key + ". Defaulted to \"\"");
//		} else {
//			org.slf4j.LoggerFactory.getLogger(TableRenderer.class)
//				.warn("Value or custom displayer not found for key: {}. Defaulted to \"\"", key);
//		}
	}
	

}
