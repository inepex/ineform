package com.inepex.ineForm.client.form.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.chooser.ChooserFw;
import com.inepex.ineForm.client.form.widgets.chooser.Item;
import com.inepex.ineForm.client.form.widgets.chooser.RelationChooser;
import com.inepex.ineForm.client.form.widgets.kvo.ContactNatRelKVO;
import com.inepex.ineForm.server.util.JavaDateFormatter;
import com.inepex.ineForm.test.TestIneFormClientGuiceModule;
import com.inepex.ineFrame.server.NumberUtilSrv;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineFrame.shared.util.date.DateFormatter;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@RunWith(JukitoRunner.class)
public class ChooserTest extends DefaultIneFrameClientSideTestBase {
	
	public static class Module extends JukitoModule {
		@Override
		protected void configureTest() {
			install(new TestIneFormClientGuiceModule());
			bind(DateFormatter.class).to(JavaDateFormatter.class);
			bind(NumberUtil.class).to(NumberUtilSrv.class);
		}
	}

	RelationTestData data;
	RelationChooser chooser;
	
	AssistedObjectHandlerFactory factory;
	
	@Before
	public void before(DescriptorStore descriptorStore, FormContext formCtx){
		data = new RelationTestData(descriptorStore);
		
		ChooserFw chooserFw = mock(ChooserFw.class);
		formCtx.valueRangeProvider = RelationTestData.valueRangeProvider;
		factory= new AssistedObjectHandlerFactory(formCtx.descStore);
		
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
		assertEquals(2L, factory.createHandler(chooser.getChanged().get(0).getKvo())
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
		
		assertEquals(1L, factory.createHandler(chooser.getChanged().get(1).getKvo())
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
	
	@Test
	public void selectNewItemTest() {
		chooser.select(new Item(new Relation(IFConsts.NEW_ITEM_ID, "newitem")), true, true);
		assertEquals(1, chooser.getChanged().size());
		assertEquals(IFConsts.NEW_ITEM_ID.longValue(), chooser.getChanged().get(0).getId().longValue());
		assertEquals(IFConsts.NEW_ITEM_ID.longValue(), factory.createHandler(chooser.getChanged().get(0).getKvo())
								.getRelation(ContactNatRelKVO.k_nationality).getId().longValue());
		
		assertSelected(chooser, "1L", "newitem");
		assertValueRange(chooser, "2L", "3L");
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
