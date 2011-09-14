package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.customkvo.CreatedFdesc;
import com.inepex.ineForm.shared.types.ODFieldType;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.assistedobject.UncheckedKVO;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class ODAOCustomKVOMappingHelper {
	
	public static List<CustomKVORow> getRowsFromAoAndOd(AssistedObject ao, ObjectDesc od) {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		AssistedObjectChecker aoc = new AssistedObjectChecker(ao, IFConsts.customDescriptorName, od);
		
		for(FDesc fd : od.getFields().values()) {
			rows.add(new CustomKVORow(fd.getKey(), ODFieldType.searchByFDesc(fd), aoc.getValueAsString(fd.getKey())));
		}
		
		return rows;
	}
	
	/**
	 * testing:
	 * 
	 * key (duplication, mandatory)
	 * type(mandatory)
	 * value(parsable) 
	 */
	public static Map<Long, String> validateRows(List<CustomKVORow> rows) {
		Map<Long, String> ret = new TreeMap<Long, String>();
		Set<String> keys = new TreeSet<String>();
		
		for(CustomKVORow r : rows) {
			String key = r.getKey();
			
			if(key.length()==0) {
				ret.put(r.getInnerId(), IneFormI18n.custKVOValidateEmpty());
				continue;
			}
			
			if(keys.contains(key)) {
				ret.put(r.getInnerId(), IneFormI18n.custKVOValidateDuplicate());
				continue;
			}
			keys.add(key);
			
			if(r.getType()==null) {
				ret.put(r.getInnerId(), IneFormI18n.custKVOValidateSet());
				continue;
			}
			
			if(r.getValue()!=null && r.getValue().length()>0) {
				try {
					switch (r.getType().ineT) {
					case BOOLEAN:
						String v = r.getValue().toLowerCase().trim();
						if(!"true".equals(v) && !"false".equals(v))
							throw new NumberFormatException();
						break;
					case LONG:
						Long.parseLong(r.getValue());
						break;
					case DOUBLE:
						Double.parseDouble(r.getValue());
						break;
					}
				} catch (NumberFormatException e) {
					ret.put(r.getInnerId(), IneFormI18n.custKVOValidateParse());
					continue;
				}
			}
		}
		
		return ret;
	}
	
	public static CustomKVOObjectDesc getOdFromRows(List<CustomKVORow> rows) {
		CustomKVOObjectDesc od = new CustomKVOObjectDesc(IFConsts.customDescriptorName);
		for(CustomKVORow r : rows) {
			od.addField(new CreatedFdesc(r.getKey(), r.getType().ineT, r.getType().validators));
		}
		
		return od;
	}
	
	public static AssistedObject getAoFromRows(List<CustomKVORow> rows) {
		UncheckedKVO kvo = new UncheckedKVO(IFConsts.customDescriptorName);
		for(CustomKVORow r : rows) {
			String stringVal =
				(r.getValue()==null || r.getValue().length()==0)
				? null
				: r.getValue();
			
			switch (r.getType().ineT) {
			case BOOLEAN:
				try {
					kvo.set(r.getKey(), 
							stringVal!=null  ? Boolean.parseBoolean(r.getValue()) : null);
				} catch (NumberFormatException e) {
					kvo.set(r.getKey(), (Boolean)null);
				}
				
				break;
			case DOUBLE:
				try {
					kvo.set(r.getKey(), 
							stringVal!=null  ? Double.parseDouble(r.getValue()) : null);
				} catch (NumberFormatException e) {
					kvo.set(r.getKey(), (Double)null);
				}
				
				break;
			case LONG:
				try {
					kvo.set(r.getKey(), 
							stringVal!=null  ? Long.parseLong(r.getValue()) : null);
				} catch (NumberFormatException e) {
					kvo.set(r.getKey(), (Long)null);
				}
				
				break;
			case STRING:
				kvo.set(r.getKey(), 
						stringVal!=null  ? r.getValue() : null);
				break;
			default:
				throw new RuntimeException(r.getType().ineT.toString());
			}
		}
		return kvo;
	}
}
