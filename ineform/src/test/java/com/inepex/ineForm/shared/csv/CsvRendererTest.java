package com.inepex.ineForm.shared.csv;

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
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.render.DefaultTableFieldRenderer;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineForm.shared.render.TableFieldRenderer.CustomCellContentDisplayer;
import com.inepex.ineForm.shared.tablerender.CsvRenderer;
import com.inepex.ineForm.shared.tablerender.CsvRenderer.CsvRendererFactory;
import com.inepex.ineForm.test.TestIneFormClientGuiceModule;
import com.inepex.ineFrame.server.NumberUtilSrv;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineFrame.shared.util.date.DateFormatter;
import com.inepex.ineFrame.test.DefaultIneFrameClientSideTestBase;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@RunWith(JukitoRunner.class)
public class CsvRendererTest extends DefaultIneFrameClientSideTestBase {

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            install(new TestIneFormClientGuiceModule());
            install(new IneFormExportGuiceModule());
            bind(DateFormatter.class).to(JavaDateFormatter.class);
            bind(NumberUtil.class).to(NumberUtilSrv.class);
            bind(TableFieldRenderer.class).to(DefaultTableFieldRenderer.class);
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
    public void renderTest(CsvRendererFactory csvRendererFactory) {
        CsvRenderer csvRenderer = csvRendererFactory
            .create(NationalityKVO.descriptorName, (String) null);

        String csvString = csvRenderer.render(kvos);

        Assert.assertEquals("\"1\",\"Nat1\"\n\"2\",\"Nat2\"\n", csvString);
    }

    @Test
    public void renderWithHeaderTest(CsvRendererFactory csvRendererFactory) {
        CsvRenderer csvRenderer = csvRendererFactory
            .create(NationalityKVO.descriptorName, (String) null);

        csvRenderer.setRenderHeader(true);

        String csvString = csvRenderer.render(kvos);

        Assert.assertEquals("\"Id\",\"Name\"\n\"1\",\"Nat1\"\n\"2\",\"Nat2\"\n", csvString);
    }

    @Test
    public void renderWithCustomDescTest(CsvRendererFactory csvRendererFactory) {
        CsvRenderer csvRenderer = csvRendererFactory
            .create(NationalityKVO.descriptorName, "custom");

        String csvString = csvRenderer.render(kvos);

        Assert.assertEquals("\"1\",\"Nat1\",\"\"\n\"2\",\"Nat2\",\"\"\n", csvString);
    }

    @Test
    public void renderWithCustomDescTestAndCustomDisplayer(CsvRendererFactory csvRendererFactory) {
        CsvRenderer csvRenderer = csvRendererFactory
            .create(NationalityKVO.descriptorName, "custom");
        csvRenderer.addCellContentDisplayer("customfield", new CustomCellContentDisplayer() {

            @Override
            public String getCustomCellContent(
                AssistedObjectHandler rowKvo,
                String fieldId,
                ColRDesc colRDesc) {
                return "a";
            }
        });

        String csvString = csvRenderer.render(kvos);

        Assert.assertEquals("\"1\",\"Nat1\",\"a\"\n\"2\",\"Nat2\",\"a\"\n", csvString);
    }

}
