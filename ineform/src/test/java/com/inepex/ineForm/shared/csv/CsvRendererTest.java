package com.inepex.ineForm.shared.csv;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.assist.NationalityAssist;
import com.inepex.ineForm.client.form.widgets.kvo.NationalityKVO;
import com.inepex.ineForm.server.util.JavaDateFormatter;
import com.inepex.ineForm.server.util.NumberUtilSrv;
import com.inepex.ineForm.shared.tablerender.CsvRenderer;
import com.inepex.ineForm.test.DefaultIneFormClientSideTestBase;
import com.inepex.ineFrame.server.util.CETDateProviderSrv;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.kvo.AssistedObject;

public class CsvRendererTest extends DefaultIneFormClientSideTestBase {

	List<AssistedObject> kvos;
	
	@Before
	public void init(){
		NationalityAssist.registerDescriptors(getDefaultInjector().getInstance(DescriptorStore.class));
		IneFormProperties.showIds = true;

		kvos = new ArrayList<AssistedObject>();
		NationalityKVO nat1 = new NationalityKVO();
		nat1.setId(1L);
		nat1.setName("Nat1");
		NationalityKVO nat2 = new NationalityKVO();
		nat2.setId(2L);
		nat2.setName("Nat2");
		kvos.add(nat1);
		kvos.add(nat2);
	}
	
	
	@Test
	public void renderTest(){
		CsvRenderer csvRenderer = new CsvRenderer(
				getDefaultInjector().getInstance(DescriptorStore.class)
				, NationalityKVO.descriptorName
				, null
				, new JavaDateFormatter()
				, new NumberUtilSrv()
				, new CETDateProviderSrv());
		
		String csvString = csvRenderer.render(kvos);
		
		Assert.assertEquals("1,Nat1\n2,Nat2\n", csvString);
	}
	
	@Test
	public void renderWithHeaderTest(){
		CsvRenderer csvRenderer = new CsvRenderer(
				getDefaultInjector().getInstance(DescriptorStore.class)
				, NationalityKVO.descriptorName
				, null
				, new JavaDateFormatter()
				, new NumberUtilSrv()
				, new CETDateProviderSrv());
		csvRenderer.setRenderHeader(true);
		
		String csvString = csvRenderer.render(kvos);
		
		Assert.assertEquals("Id,Name\n1,Nat1\n2,Nat2\n", csvString);
	}

	
}
