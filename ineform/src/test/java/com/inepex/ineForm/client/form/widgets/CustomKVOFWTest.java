package com.inepex.ineForm.client.form.widgets;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVOFW;
import com.inepex.ineForm.client.form.widgets.customkvo.CustomKVORow;
import com.inepex.ineForm.shared.customkvo.CreatedFdesc;
import com.inepex.ineForm.shared.customkvoeditor.CustomOdFinder;
import com.inepex.ineForm.shared.customkvoeditor.CustomOdFinder.OdFoundCallback;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineForm.test.DefaultIneFormClientSideTestBase;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.UncheckedAssistedObjectReader;
import com.inepex.ineom.shared.assistedobject.UncheckedKVO;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;

public class CustomKVOFWTest extends DefaultIneFormClientSideTestBase {
	
	public static final String k_str="k_str";
	public static final String k_str2="k_str2";
	public static final String k_long="k_long";
	public static final String k_double="k_double";

	@BeforeClass
	public static void before() {
		GWTMockUtilities.disarm();
	}
	
	@AfterClass
	public static void after() {
		GWTMockUtilities.restore();
	}
	
	private CustomKVOFW fw;
	private CustomOdFinder finder;
	private CustomKVOFW.View view;
	
	private CustomKVORow longRow;
	private CustomKVORow doubleRow;
	
	@Before
	public void setUpVariables() {
		Widget w = mock(Widget.class);
		view = mock(CustomKVOFW.View.class);
		when(view.asWidget()).thenReturn(w);
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				CustomKVORow row = (CustomKVORow) invocation.getArguments()[0];
				
				if(row.getType().ineT==IneT.LONG)
					longRow=row;
				
				if(row.getType().ineT==IneT.DOUBLE)
					doubleRow=row;
				
				return null;
			}
		}).when(view).addRow(any(CustomKVORow.class));
		
		finder = mock(CustomOdFinder.class);
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				ObjectDesc od = new ObjectDesc(IFConsts.customDescriptorName);
				od.addField(new CreatedFdesc(k_long, IneT.LONG));
				od.addField(new CreatedFdesc(k_str, IneT.STRING));
				od.addField(new CreatedFdesc(k_str2, IneT.STRING));
				od.addField(new CreatedFdesc(k_double, IneT.DOUBLE));
				
				((OdFoundCallback) invocation.getArguments()[1]).onFound(od);
				return null;
			}
		}).when(finder).getCustomOd(anyLong(), any(OdFoundCallback.class));
		
		fw = new CustomKVOFW(
				new RelationFDesc("keyOfField", "disp name of field", IFConsts.customDescriptorName),
				new WidgetRDesc(FWTypes.CUSTOMKVO),
				finder,
				view);
	}
	
	@After
	public void cleanVariables() {
		
	}
	
	@Test
	public void testNull() {
		fw.setRelationValue(null);
		Assert.assertNull(fw.getRelationValue());
	}
	
	@Test
	public void testSetGet() {
		UncheckedKVO ao = new UncheckedKVO(IFConsts.customDescriptorName);
		ao.set(IFConsts.KEY_ID, 999L);
		ao.set(k_long, 123L);
		ao.set(k_str, "value 1");
		ao.set(k_str2, "value 2");
		ao.set(k_double, 123.123);
		Relation value = new Relation(ao);
		
		fw.setRelationValue(value);
		
		Relation result = fw.getRelationValue();
		UncheckedAssistedObjectReader reader = new UncheckedAssistedObjectReader(result.getKvo()); 
		
		Assert.assertEquals(false, result==value); //not the same object
		Assert.assertEquals(false, result.getKvo()==value.getKvo());  //not the same object
		
		Assert.assertEquals(5, result.getKvo().getKeys().size());
		
		Assert.assertEquals(999, result.getId().longValue());
		Assert.assertEquals(999, result.getKvo().getId().longValue());
		
		Assert.assertEquals(123, reader.getLong(k_long).longValue());
		Assert.assertEquals("value 1", reader.getString(k_str));
		Assert.assertEquals("value 2", reader.getString(k_str2));
		Assert.assertEquals(123.123, reader.getDouble(k_double).doubleValue());
	}
	
	@Test
	public void testDeleteRows() {
		UncheckedKVO ao = new UncheckedKVO(IFConsts.customDescriptorName);
		ao.set(IFConsts.KEY_ID, 999L);
		ao.set(k_long, 123L);
		ao.set(k_str, "value 1");
		ao.set(k_str2, "value 2");
		ao.set(k_double, 123.123);
		Relation value = new Relation(ao);
		
		fw.setRelationValue(value);
		
		fw.onRemove(longRow);
		fw.onRemove(doubleRow);
		
		Relation result = fw.getRelationValue();
		UncheckedAssistedObjectReader reader = new UncheckedAssistedObjectReader(result.getKvo()); 
		
		Assert.assertEquals(false, result==value); //not the same object
		Assert.assertEquals(false, result.getKvo()==value.getKvo());  //not the same object
		
		Assert.assertEquals(3, result.getKvo().getKeys().size());
		
		Assert.assertEquals(999, result.getId().longValue());
		Assert.assertEquals(999, result.getKvo().getId().longValue());
		
		Assert.assertEquals("value 1", reader.getString(k_str));
		Assert.assertEquals("value 2", reader.getString(k_str2));
	}
}