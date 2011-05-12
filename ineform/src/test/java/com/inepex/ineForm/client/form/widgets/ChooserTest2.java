package com.inepex.ineForm.client.form.widgets;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.chooser.ChooserFw;
import com.inepex.ineForm.client.form.widgets.chooser.RelationChooser;
import com.inepex.ineForm.test.DefaultIneFormClientSideTestBase;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.kvo.Relation;

public class ChooserTest2 extends DefaultIneFormClientSideTestBase {

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
		relations.add(data.rel2);
		relations.add(data.rel3);
		chooser.setSelected(relations);
	}
	
	@Test
	public void deSelectAllTest(){
		chooser.deselectAll();
		assertEquals(3, chooser.getChanged().size());
		
		ChooserTest.assertSelected(chooser);
		ChooserTest.assertValueRange(chooser, "1L", "2L", "3L");
		
	}
}
