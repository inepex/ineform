package com.inepex.ineForm.client.form.widgets.customkvo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.inepex.ineForm.server.i18n.ServerIneFormI18nProvider;
import com.inepex.ineForm.shared.customkvo.CreatedFdesc;
import com.inepex.ineForm.shared.types.ODFieldType;
import com.inepex.ineForm.test.DefaultIneFormClientSideTestBase;
import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.assistedobject.UncheckedKVO;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

public class ODAOCustomKVOMappingHelperTest extends DefaultIneFormClientSideTestBase {

	@Override
	public List<Class<? extends ServerI18nProvider<? extends I18nModule>>> listUsedI18nClasses() {
		List<Class<? extends ServerI18nProvider<? extends I18nModule>>> list =
				new ArrayList<Class<? extends ServerI18nProvider<? extends I18nModule>>>();
		list.add(ServerIneFormI18nProvider.class);
		return list;
	}
	
	@Test
	public void getRowsFromAoAndOdTest() {
		final String k_str="k_str";
		final String k_str2="k_str2";
		final String k_long="k_long";
		final String k_double="k_double";
		
		UncheckedKVO ao = new UncheckedKVO();
		ao.set(k_long, 123L);
		ao.set(k_str, "value 1");
		ao.set(k_str2, "value 2");
		ao.set(k_double, 123.123);
		
		ObjectDesc od = new ObjectDesc(IFConsts.customDescriptorName);
		od.addField(new CreatedFdesc(k_long, IneT.LONG));
		od.addField(new CreatedFdesc(k_str, IneT.STRING));
		od.addField(new CreatedFdesc(k_str2, IneT.STRING));
		od.addField(new CreatedFdesc(k_double, IneT.DOUBLE));
		
		List<CustomKVORow> rows = ODAOCustomKVOMappingHelper.getRowsFromAoAndOd(ao, od);
		
		Assert.assertEquals(4, rows.size());
		
		Assert.assertEquals(k_long, rows.get(0).getKey());
		Assert.assertEquals(ODFieldType.LONG, rows.get(0).getType());
		Assert.assertEquals("123", rows.get(0).getValue());
		
		Assert.assertEquals(k_str, rows.get(1).getKey());
		Assert.assertEquals(ODFieldType.STRING, rows.get(1).getType());
		Assert.assertEquals("value 1", rows.get(1).getValue());
		
		Assert.assertEquals(k_str2, rows.get(2).getKey());
		Assert.assertEquals(ODFieldType.STRING, rows.get(2).getType());
		Assert.assertEquals("value 2", rows.get(2).getValue());
		
		Assert.assertEquals(k_double, rows.get(3).getKey());
		Assert.assertEquals(ODFieldType.DOUBLE, rows.get(3).getType());
		Assert.assertEquals("123.123", rows.get(3).getValue());
	}
	
	@Test
	public void validateCorrectRowsTest() {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		rows.add(new CustomKVORow("k1", ODFieldType.DOUBLE, "")); //empty value is correct
		rows.add(new CustomKVORow("k2", ODFieldType.STRING, "123"));
		rows.add(new CustomKVORow("k3", ODFieldType.LONG, "123"));
		
		rows.add(new CustomKVORow("k4", ODFieldType.BOOLEAN, "true")); //case insensitive
		rows.add(new CustomKVORow("k5", ODFieldType.BOOLEAN, "tRuE"));
		rows.add(new CustomKVORow("k6", ODFieldType.BOOLEAN, "  tRUE "));
		rows.add(new CustomKVORow("k7", ODFieldType.BOOLEAN, "false"));
		rows.add(new CustomKVORow("k8", ODFieldType.BOOLEAN, "fAlSe"));
		rows.add(new CustomKVORow("k9", ODFieldType.BOOLEAN, "  falSE "));
		
		Assert.assertEquals(0, ODAOCustomKVOMappingHelper.validateRows(rows).size());
	}
	
	@Test
	public void validateKeyDuplicationTest() {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		rows.add(new CustomKVORow("k_dup", ODFieldType.DOUBLE, ""));
		rows.add(new CustomKVORow("k2", ODFieldType.STRING, "123"));
		rows.add(new CustomKVORow("k_dup", ODFieldType.LONG, "123"));
		rows.add(new CustomKVORow("k4", ODFieldType.BOOLEAN, "true"));
		
		Map<Long, String> res = ODAOCustomKVOMappingHelper.validateRows(rows);
		Assert.assertEquals(1, res.size());
		
		Assert.assertEquals(true, res.keySet().contains(rows.get(2).getInnerId()));
	}
	
	@Test
	public void validateEmptyKeyTest() {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		rows.add(new CustomKVORow("k1", ODFieldType.DOUBLE, ""));
		rows.add(new CustomKVORow("k2", ODFieldType.STRING, "123"));
		rows.add(new CustomKVORow("", ODFieldType.LONG, "123"));
		rows.add(new CustomKVORow("k4", ODFieldType.BOOLEAN, "true"));
		
		Map<Long, String> res = ODAOCustomKVOMappingHelper.validateRows(rows);
		Assert.assertEquals(1, res.size());
		
		Assert.assertEquals(true, res.keySet().contains(rows.get(2).getInnerId()));
	}
	
	@Test
	public void validateEmptyTypeTest() {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		rows.add(new CustomKVORow("k1", ODFieldType.DOUBLE, ""));
		rows.add(new CustomKVORow("k2", ODFieldType.STRING, "123"));
		rows.add(new CustomKVORow("k3", null, "123"));
		rows.add(new CustomKVORow("k4", ODFieldType.BOOLEAN, "true"));
		
		Map<Long, String> res = ODAOCustomKVOMappingHelper.validateRows(rows);
		Assert.assertEquals(1, res.size());
		
		Assert.assertEquals(true, res.keySet().contains(rows.get(2).getInnerId()));
	}
	
	@Test
	public void validateUnparseblaStuff() {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		rows.add(new CustomKVORow("k1", ODFieldType.DOUBLE, "3.34"));
		rows.add(new CustomKVORow("k2", ODFieldType.BOOLEAN, "truee"));
		
		Map<Long, String> res = ODAOCustomKVOMappingHelper.validateRows(rows);
		Assert.assertEquals(1, res.size());
		
		Assert.assertEquals(true, res.keySet().contains(rows.get(1).getInnerId()));
	}
	
	@Test 
	public void getOdFromRowsTest() {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		rows.add(new CustomKVORow("k1", ODFieldType.DOUBLE, ""));
		rows.add(new CustomKVORow("k2", ODFieldType.STRING, "123"));
		rows.add(new CustomKVORow("k3", ODFieldType.EMAIL, "alma@korte.er"));
		
		ObjectDesc od = ODAOCustomKVOMappingHelper.getOdFromRows(rows, "test");
		
		Assert.assertEquals(IFConsts.customDescriptorName, od.getName());
		Assert.assertEquals(3, od.getFieldCount());
		
		Assert.assertEquals(true, od.getFields().containsKey("k1"));
		Assert.assertEquals("k1", od.getFields().get("k1").getKey());
		Assert.assertEquals(0, od.getFields().get("k1").getValidatorNames().size());
		Assert.assertEquals(IneT.DOUBLE, od.getFields().get("k1").getType());
		
		Assert.assertEquals(true, od.getFields().containsKey("k2"));
		Assert.assertEquals("k2", od.getFields().get("k2").getKey());
		Assert.assertEquals(0, od.getFields().get("k2").getValidatorNames().size());
		Assert.assertEquals(IneT.STRING, od.getFields().get("k2").getType());
		
		Assert.assertEquals(true, od.getFields().containsKey("k3"));
		Assert.assertEquals("k3", od.getFields().get("k3").getKey());
		Assert.assertEquals(1, od.getFields().get("k3").getValidatorNames().size());
		Assert.assertEquals(true, od.getFields().get("k3").getValidatorNames().contains(KeyValueObjectValidationManager.EMAIL));
		Assert.assertEquals(IneT.STRING, od.getFields().get("k3").getType());
	}
	
	@Test
	public void getAoFromEmptyRows() {
		Assert.assertNull(ODAOCustomKVOMappingHelper.getAoFromRows(new ArrayList<CustomKVORow>()));
	}
	
	@Test 
	public void getAoFromRowsTest() {
		List<CustomKVORow> rows = new ArrayList<CustomKVORow>();
		rows.add(new CustomKVORow("k1", ODFieldType.DOUBLE, ""));
		rows.add(new CustomKVORow("k2", ODFieldType.STRING, "123"));
		rows.add(new CustomKVORow("k3", ODFieldType.EMAIL, "alma@korte.er"));
		
		rows.add(new CustomKVORow("k4", ODFieldType.DOUBLE, "abc")); //can not parse so it will null
		rows.add(new CustomKVORow("k5", ODFieldType.LONG, "abc")); //can not parse so it will null
		
		ObjectDesc od = ODAOCustomKVOMappingHelper.getOdFromRows(rows, "test");
		AssistedObject ao = ODAOCustomKVOMappingHelper.getAoFromRows(rows);
		AssistedObjectChecker checker = new AssistedObjectChecker(ao, IFConsts.customDescriptorName, od);
		
		Assert.assertEquals(IFConsts.customDescriptorName, ao.getDescriptorName());
		Assert.assertEquals(IFConsts.NEW_ITEM_ID.intValue(), ao.getId().intValue());
		Assert.assertEquals(5, ao.getKeys().size());
		
		Assert.assertEquals(true, checker.containsDouble("k1"));
		Assert.assertEquals(true, checker.containsString("k2"));
		Assert.assertEquals(true, checker.containsString("k3"));
		Assert.assertEquals(true, checker.containsDouble("k4"));
		Assert.assertEquals(true, checker.containsLong("k5"));
		
		Assert.assertNull(checker.getDouble("k1"));
		Assert.assertEquals("123", checker.getString("k2"));
		Assert.assertEquals("alma@korte.er", checker.getString("k3"));
		Assert.assertNull(checker.getDouble("k4"));
		Assert.assertNull(checker.getLong("k5"));
	}
}


