package com.inepex.ineForm.server.csv;

import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.Dispatch;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineForm.server.handler.SetActionForExportServletHandler;
import com.inepex.ineForm.server.tablerenderer.excel.ExcelRenderer.ExcelRendererFactory;
import com.inepex.ineForm.server.tablerenderer.pdf.PdfRenderer.PdfRendererFactory;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineForm.shared.tablerender.CsvRenderer.CsvRendererFactory;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer.HtmlRendererFactory;
import com.inepex.ineForm.shared.tablerender.TrtdRenderer.TrtdRendererFactory;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;

@SuppressWarnings("serial")
@Singleton
public class ExportServlet extends AbstractExportServlet {

    private final Dispatch dispatcher;

    @Inject
    public ExportServlet(
        Provider<CurrentLang> currLangProvider,
        Dispatch dispatcher,
        TrtdRendererFactory trtdRendererFactory,
        HtmlRendererFactory htmlRendererFactory,
        CsvRendererFactory csvRendererFactory,
        PdfRendererFactory pdfRendererFactory,
        ExcelRendererFactory excelRendererFactory,
        ExportCustomizerStore exportCustomizerStore) {
        super(
            currLangProvider,
            trtdRendererFactory,
            htmlRendererFactory,
            csvRendererFactory,
            excelRendererFactory,
            pdfRendererFactory,
            exportCustomizerStore);
        this.dispatcher = dispatcher;
    }

    @Override
    protected void customizeAction(HttpServletRequest req, ObjectList action) {
        currLangProvider.get().setLangOverride(
            (String) req
                .getSession()
                .getAttribute(SetActionForExportServletHandler.rendererLanguage));

    }

    @Override
    protected ObjectListResult getResult(ObjectList action) throws Exception {
        ObjectListActionResult listResult = dispatcher.execute((ObjectListAction) action);
        return listResult;
    }

}
