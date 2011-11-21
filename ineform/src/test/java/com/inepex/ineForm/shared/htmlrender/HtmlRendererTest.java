package com.inepex.ineForm.shared.htmlrender;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.assist.NationalityAssist;
import com.inepex.ineForm.client.form.widgets.kvo.NationalityKVO;
import com.inepex.ineForm.server.guice.IneFormExportGuiceModule;
import com.inepex.ineForm.server.util.JavaDateFormatter;
import com.inepex.ineForm.server.util.NumberUtilSrv;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer.HtmlRendererFactory;
import com.inepex.ineForm.test.TestIneFormClientGuiceModule;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

@RunWith(JukitoRunner.class)
public class HtmlRendererTest extends DefaultIneFrameClientSideTestBase {
	List<AssistedObject> kvos;
	
	public static class Module extends JukitoModule {
		protected void configureTest() {
			install(new TestIneFormClientGuiceModule());
			install(new IneFormExportGuiceModule());
			bind(DateFormatter.class).to(JavaDateFormatter.class);
			bind(NumberUtil.class).to(NumberUtilSrv.class);
		}
	}
	
	@Before
	public void init(DescriptorStore descriptorStore){
		NationalityAssist.registerDescriptors(descriptorStore);
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
	public void renderTest(HtmlRendererFactory htmlRendererFactory){
		
		HtmlRenderer renderer = htmlRendererFactory.create(NationalityKVO.descriptorName, null); 
		
		String csvString = renderer.render(kvos);
		
		Assert.assertEquals("<html>\n" +
				"<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
				"<style type=\"text/css\">td {border: 1px solid black;}\n" +
				"th {background-color: #f2f2f2; border: 1px solid black;}\n" +
				"table {text-align: left; empty-cells: show; border-collapse: collapse;}</style></head><table>\n" +
				"<tr><td>1</td><td>Nat1</td></tr>\n" +
				"<tr><td>2</td><td>Nat2</td></tr>\n" +
				"</table></html>", csvString);
	}
	
	@Test
	public void renderWithHeaderTest(HtmlRendererFactory htmlRendererFactory){
		HtmlRenderer renderer = htmlRendererFactory.create(NationalityKVO.descriptorName, null); 
		
		renderer.setRenderHeader(true);
		
		String csvString = renderer.render(kvos);
		
		Assert.assertEquals("<html>\n" +
				"<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
				"<style type=\"text/css\">td {border: 1px solid black;}\n" +
				"th {background-color: #f2f2f2; border: 1px solid black;}\n" +
				"table {text-align: left; empty-cells: show; border-collapse: collapse;}</style></head><table>\n" +
				"<tr><th>Id</th><th>Name</th></tr>\n" +
				"<tr><td>1</td><td>Nat1</td></tr>\n" +
				"<tr><td>2</td><td>Nat2</td></tr>\n" +
				"</table></html>", csvString);
	}


}
