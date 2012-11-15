package com.inepex.ineForm.server.tablerenderer.pdf;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.assist.NationalityAssist;
import com.inepex.ineForm.client.form.widgets.kvo.NationalityKVO;
import com.inepex.ineForm.server.guice.IneFormExportGuiceModule;
import com.inepex.ineForm.server.tablerenderer.pdf.PdfRenderer.PdfRendererFactory;
import com.inepex.ineForm.server.util.JavaDateFormatter;
import com.inepex.ineForm.test.TestIneFormClientGuiceModule;
import com.inepex.ineFrame.server.NumberUtilSrv;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

@RunWith(JukitoRunner.class)
public class PdfRendererComplexTest {
	public static class Module extends JukitoModule {
		@Override
		protected void configureTest() {
			install(new TestIneFormClientGuiceModule());
			install(new IneFormExportGuiceModule());
			bind(DateFormatter.class).to(JavaDateFormatter.class);
			bind(NumberUtil.class).to(NumberUtilSrv.class);
		}
	}

	List<AssistedObject> kvos;

	@Before
	public void init(DescriptorStore descriptorStore) {
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
	public void renderTest(PdfRendererFactory rendererFactory) throws Exception {
		PdfRenderer renderer = rendererFactory.create(NationalityKVO.descriptorName, (String) null);
		renderer.render(kvos);
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("workbook.pdf"));
		document.open();
		document.add(renderer.getTable());
		document.close();
	}
}
