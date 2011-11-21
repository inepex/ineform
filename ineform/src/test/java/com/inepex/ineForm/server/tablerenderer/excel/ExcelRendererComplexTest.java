package com.inepex.ineForm.server.tablerenderer.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.widgets.assist.NationalityAssist;
import com.inepex.ineForm.client.form.widgets.kvo.NationalityKVO;
import com.inepex.ineForm.server.guice.IneFormExportGuiceModule;
import com.inepex.ineForm.server.tablerenderer.excel.ExcelRenderer;
import com.inepex.ineForm.server.tablerenderer.excel.ExcelRenderer.ExcelRendererFactory;
import com.inepex.ineForm.server.util.JavaDateFormatter;
import com.inepex.ineForm.server.util.NumberUtilSrv;
import com.inepex.ineForm.test.TestIneFormClientGuiceModule;
import com.inepex.ineFrame.shared.util.DateFormatter;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

@RunWith(JukitoRunner.class)
public class ExcelRendererComplexTest extends DefaultIneFrameClientSideTestBase {

	public static class Module extends JukitoModule {
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
	public void renderTest(ExcelRendererFactory rendererFactory) throws Exception {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		ExcelRenderer renderer = rendererFactory.create(NationalityKVO.descriptorName, (String) null, sheet);
		renderer.render(kvos);
		
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
