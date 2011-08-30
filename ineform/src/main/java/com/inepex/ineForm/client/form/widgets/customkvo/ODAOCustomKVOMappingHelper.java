package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.List;

import com.inepex.ineForm.shared.types.ODFieldType;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.validation.ValidationResult;

public class ODAOCustomKVOMappingHelper {
	
	public static List<CustomKVORow> getRowsFromAoAndOd(AssistedObject ao, ObjectDesc od) {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		AssistedObjectChecker aoc = new AssistedObjectChecker(ao, IFConsts.customDescriptorName, od);
		
		for(FDesc fd : od.getFields().values()) {
			rows.add(new CustomKVORow(fd.getKey(), ODFieldType.searchByFDesc(fd), aoc.getValueAsString(fd.getKey())));
		}
		
		return rows;
	}
	
	public static ValidationResult validateRows(List<CustomKVORow> rows) {
		//TODO implement
		return null;
	}
	
	public static ObjectDesc getOdFromRows(List<CustomKVORow> rows) {
		//TODO
		ObjectDesc od = new ObjectDesc(IFConsts.customDescriptorName);
		for(CustomKVORow r : rows) {
			//od.addField(fieldDescriptor)
		}
		
		return od;
	}
	
	public static AssistedObject getAoFromRows(List<CustomKVORow> rows) {
		return null;
	}
}
