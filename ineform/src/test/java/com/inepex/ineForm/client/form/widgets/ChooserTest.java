package com.inepex.ineForm.client.form.widgets;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.chooser.ChooserFw;
import com.inepex.ineForm.client.form.widgets.chooser.Item;
import com.inepex.ineForm.client.form.widgets.chooser.RelationChooser;
import com.inepex.ineForm.client.form.widgets.kvo.ContactNatRelKVO;
import com.inepex.ineForm.test.DefaultIneFormClientSideTestBase;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.Relation;

public class ChooserTest extends DefaultIneFormClientSideTestBase {

	RelationTestData data;
	RelationChooser chooser;
	
	
	@Before
	public void before(){
		data = new RelationTestData(getDefaultInjector().getInstance(DescriptorStore.class));
		
		ChooserFw chooserFw = mock(ChooserFw.class);
		FormContext formCtx = getDefaultInjector().getInstance(FormContext.class);
		formCtx.valueRangeProvider = RelationTestData.valueRangeProvider;
		
		chooser = new RelationChooser(formCtx, chooserFw, data.fieldDesc
				, data.fieldDesc.getRelatedDescriptorType());
		chooser.loadValueRange();
		ArrayList<Relation> relations = new ArrayList<Relation>();
		relations.add(data.rel1);
		chooser.setSelected(relations);
	}
	
	@Test
	public void createTest(){
		assertEquals(0, chooser.getChanged().size());
		
		assertSelected(chooser, data.rel1.getDisplayName());
		assertValueRange(chooser, "2L", "3L");
	}
	
	@Test
	public void selectTest() {
		chooser.select(new Item(new Relation(2L, "2L")), true, true);
		assertEquals(1, chooser.getChanged().size());
		assertEquals(IFConsts.NEW_ITEM_ID.longValue(), chooser.getChanged().get(0).getId().longValue());
		assertEquals(2L, chooser.getChanged().get(0).getKvo()
								.getRelation(ContactNatRelKVO.k_nationality).getId().longValue());
		
		assertSelected(chooser, "1L", "2L");
		assertValueRange(chooser, "3L");
	}
	
	@Test
	public void deselectTest() {
		chooser.deselect(new Item(new Relation(1L, "1L")), true);
		assertEquals(1, chooser.getChanged().size());
		assertNull(chooser.getChanged().get(0).getKvo());
		
		assertSelected(chooser);
		assertValueRange(chooser, "1L", "2L", "3L");
	}
	
	@Test
	public void selectDeselectTest(){
		chooser.select(new Item(new Relation(2L, "2L")), true, true);
		chooser.deselect(new Item(new Relation(2L, "2L")), true);
		assertEquals(0, chooser.getChanged().size());
		
		assertSelected(chooser, "1L");
		assertValueRange(chooser, "2L", "3L");
	}
	
	@Test
	public void deselectSelectTest(){
		chooser.deselect(new Item(new Relation(1L, "1L")), true);
		chooser.select(new Item(new Relation(1L, "1L")), true, true);
		assertEquals(2, chooser.getChanged().size());
		assertEquals(1L, chooser.getChanged().get(0).getId().longValue());
		
		assertNull(chooser.getChanged().get(0).getKvo());
		
		assertEquals(IFConsts.NEW_ITEM_ID.longValue()
				, chooser.getChanged().get(1).getId().longValue());
		
		assertEquals(1L, chooser.getChanged().get(1).getKvo()
				.getRelation(ContactNatRelKVO.k_nationality).getId().longValue());
		
		
		assertSelected(chooser, "1L");
		assertValueRange(chooser, "2L", "3L");
		
		
	}
	
	@Test
	public void selectAllTest(){
		chooser.selectAll();
		assertEquals(2, chooser.getChanged().size());
		
		assertSelected(chooser, "1L", "2L", "3L");
		assertValueRange(chooser);
		
	}
	
	@Test
	public void deSelectAllTest(){
		chooser.deselectAll();
		assertEquals(1, chooser.getChanged().size());
		
		assertSelected(chooser);
		assertValueRange(chooser, "1L", "2L", "3L");
		
	}
	
	@Test
	public void moveUpTest(){
		
	}
	
	
	public static void assertSelected(RelationChooser chooser, String ... expected){
		assertEquals(expected.length, chooser.getSelected().size());
		for (int i = 0; i < expected.length; i++){
			assertEquals(expected[i], chooser.getSelected().get(i).getDisplayName());
		}
		
	}
	
	public static void assertValueRange(RelationChooser chooser, String ... expected){
		assertEquals(expected.length, chooser.getValueRange().size());
		for (int i = 0; i < expected.length; i++){
			assertEquals(expected[i], chooser.getValueRange().get(i).getDisplayName());
		}
	}

}
