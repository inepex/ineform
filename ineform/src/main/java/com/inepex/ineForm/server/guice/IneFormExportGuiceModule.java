package com.inepex.ineForm.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.inepex.ineForm.server.csv.ExportCustomizerStore;
import com.inepex.ineForm.server.tablerenderer.excel.ExcelRenderer;
import com.inepex.ineForm.server.tablerenderer.excel.ExcelRenderer.ExcelRendererFactory;
import com.inepex.ineForm.server.tablerenderer.excel.ExcelRendererV2;
import com.inepex.ineForm.server.tablerenderer.excel.ExcelRendererV2.ExcelRendererV2Factory;
import com.inepex.ineForm.server.tablerenderer.pdf.PdfFontLoader;
import com.inepex.ineForm.server.tablerenderer.pdf.PdfRenderer;
import com.inepex.ineForm.server.tablerenderer.pdf.PdfRenderer.PdfRendererFactory;
import com.inepex.ineForm.shared.tablerender.CsvRenderer;
import com.inepex.ineForm.shared.tablerender.CsvRenderer.CsvRendererFactory;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer.HtmlRendererFactory;
import com.inepex.ineForm.shared.tablerender.TrtdRenderer;
import com.inepex.ineForm.shared.tablerender.TrtdRenderer.TrtdRendererFactory;

public class IneFormExportGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(CsvRenderer.class, CsvRenderer.class).build(
            CsvRendererFactory.class));

        install(new FactoryModuleBuilder().implement(TrtdRenderer.class, TrtdRenderer.class).build(
            TrtdRendererFactory.class));

        install(new FactoryModuleBuilder().implement(HtmlRenderer.class, HtmlRenderer.class).build(
            HtmlRendererFactory.class));

        install(new FactoryModuleBuilder()
            .implement(ExcelRenderer.class, ExcelRenderer.class)
            .build(ExcelRendererFactory.class));

        install(new FactoryModuleBuilder()
            .implement(ExcelRendererV2.class, ExcelRendererV2.class)
            .build(ExcelRendererV2Factory.class));

        install(new FactoryModuleBuilder().implement(PdfRenderer.class, PdfRenderer.class).build(
            PdfRendererFactory.class));

        bind(PdfFontLoader.class).in(Singleton.class);
        bind(ExportCustomizerStore.class).in(Singleton.class);
    }

}
