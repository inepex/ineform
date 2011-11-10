package com.inepex.ineForm.client.form.widgets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.chooser.ChooserFw;
import com.inepex.ineForm.client.form.widgets.chooser.RelationChooser;
import com.inepex.ineForm.server.util.JavaDateFormatter;
import com.inepex.ineForm.server.util.NumberUtilSrv;
import com.inepex.ineForm.test.TestIneFormClientGuiceModule;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

@RunWith(JukitoRunner.class)
public class ChooserTest2 extends DefaultIneFrameClientSideTestBase {
	
	public static class Module extends JukitoModule {
		protected void configureTest() {
			install(new TestIneFormClientGuiceModule());
			bind(DateFormatter.class).to(JavaDateFormatter.class);
			bind(NumberUtil.class).to(NumberUtilSrv.class);
		}
	}

	RelationTestData data;
	RelationChooser chooser;
	
	
	@Before
	public void before(DescriptorStore descriptorStore, FormContext formCtx){
		data = new RelationTestData(descriptorStore);
		
		ChooserFw chooserFw = mock(ChooserFw.class);
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
