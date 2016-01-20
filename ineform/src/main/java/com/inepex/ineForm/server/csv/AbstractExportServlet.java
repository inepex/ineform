package com.inepex.ineForm.server.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.server.handler.SetActionForExportServletHandler;
import com.inepex.ineForm.server.tablerenderer.excel.ExcelRenderer.ExcelRendererFactory;
import com.inepex.ineForm.server.tablerenderer.pdf.PdfRenderer;
import com.inepex.ineForm.server.tablerenderer.pdf.PdfRenderer.PdfRendererFactory;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction.Renderer;
import com.inepex.ineForm.shared.tablerender.CsvRenderer.CsvRendererFactory;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer.HtmlRendererFactory;
import com.inepex.ineForm.shared.tablerender.TableRenderer;
import com.inepex.ineForm.shared.tablerender.TrtdRenderer.TrtdRendererFactory;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

@SuppressWarnings("serial")
public abstract class AbstractExportServlet extends HttpServlet {

    protected static final Logger _logger = LoggerFactory.getLogger(AbstractExportServlet.class);

    protected final Provider<CurrentLang> currLangProvider;
    protected final TrtdRendererFactory trtdRendererFactory;
    protected final HtmlRendererFactory htmlRendererFactory;
    protected final CsvRendererFactory csvRendererFactory;
    protected final ExcelRendererFactory excelRendererFactory;
    protected final PdfRendererFactory pdfRendererFactory;
    protected final ExportCustomizerStore exportCustomizerStore;

    protected abstract void customizeAction(HttpServletRequest req, ObjectList action);

    protected abstract ObjectListResult getResult(ObjectList action) throws Exception;

    public AbstractExportServlet(
        Provider<CurrentLang> currLangProvider,
        TrtdRendererFactory trtdRendererFactory,
        HtmlRendererFactory htmlRendererFactory,
        CsvRendererFactory csvRendererFactory,
        ExcelRendererFactory excelRendererFactory,
        PdfRendererFactory pdfRendererFactory,
        ExportCustomizerStore exportCustomizerStore) {
        this.currLangProvider = currLangProvider;
        this.trtdRendererFactory = trtdRendererFactory;
        this.htmlRendererFactory = htmlRendererFactory;
        this.csvRendererFactory = csvRendererFactory;
        this.excelRendererFactory = excelRendererFactory;
        this.pdfRendererFactory = pdfRendererFactory;
        this.exportCustomizerStore = exportCustomizerStore;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,
        IOException {

        try {
            if (req
                .getSession()
                .getAttribute(SetActionForExportServletHandler.actionForCsvKey) == null
                || req.getSession().getAttribute(
                    SetActionForExportServletHandler.filenameForCsvKey) == null
                || req.getSession().getAttribute(
                    SetActionForExportServletHandler.rendererForCsvKey) == null) {
                resp.getWriter().write(IneFormI18n.csvInvalid());
                resp.getWriter().close();
                return;
            }
            ObjectList action = (ObjectList) req
                .getSession()
                .getAttribute(SetActionForExportServletHandler.actionForCsvKey);
            req.getSession().removeAttribute(SetActionForExportServletHandler.actionForCsvKey);
            String fileName = (String) req
                .getSession()
                .getAttribute(SetActionForExportServletHandler.filenameForCsvKey);

            if (Boolean.TRUE.equals(
                req.getSession().getAttribute(
                    SetActionForExportServletHandler.appendDateToFileName))) {
                fileName = fileName + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
            }

            req.getSession().removeAttribute(SetActionForExportServletHandler.filenameForCsvKey);
            String tableRDescName = (String) req
                .getSession()
                .getAttribute(SetActionForExportServletHandler.tableRDescForCsvKey);
            Boolean withHeader = (Boolean) req
                .getSession()
                .getAttribute(SetActionForExportServletHandler.withHeaderForCsvKey);
            Renderer rendererType = (Renderer) req
                .getSession()
                .getAttribute(SetActionForExportServletHandler.rendererForCsvKey);

            action.setFirstResult(0);
            action.setNumMaxResult(1000000);

            customizeAction(req, action);

            ObjectListResult listResult = getResult(action);

            TableRenderer renderer = null;
            String extension = "";
            ByteArrayOutputStream baos;
            switch (rendererType) {
                case CSV:
                    renderer = csvRendererFactory
                        .create(getResultDescName(req, action), tableRDescName);
                    renderer.setRenderHeader(withHeader);
                    resp.setContentType("text/csv");
                    resp.setCharacterEncoding("UTF-8");
                    extension = ".csv";
                    resp.addHeader(
                        "Content-Disposition",
                        "attachment; filename=" + fileName + extension);
                    exportCustomizerStore
                        .customize(getResultDescName(req, action), renderer.getFieldRenderer());
                    resp.getWriter().write(renderer.render(listResult.getList()));
                    resp.getWriter().close();
                    break;
                case HTML:
                    renderer = htmlRendererFactory
                        .create(getResultDescName(req, action), tableRDescName);
                    renderer.setRenderHeader(withHeader);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    extension = ".html";
                    resp.addHeader(
                        "Content-Disposition",
                        "attachment; filename=" + fileName + extension);
                    exportCustomizerStore
                        .customize(getResultDescName(req, action), renderer.getFieldRenderer());
                    resp.getWriter().write(renderer.render(listResult.getList()));
                    resp.getWriter().close();
                    break;
                case TRTD:
                    renderer = trtdRendererFactory
                        .create(getResultDescName(req, action), tableRDescName);
                    renderer.setRenderHeader(withHeader);
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    extension = ".html";
                    resp.addHeader(
                        "Content-Disposition",
                        "attachment; filename=" + fileName + extension);
                    exportCustomizerStore
                        .customize(getResultDescName(req, action), renderer.getFieldRenderer());
                    resp.getWriter().write(renderer.render(listResult.getList()));
                    resp.getWriter().close();
                    break;

                case PDF:
                    renderer = pdfRendererFactory
                        .create(getResultDescName(req, action), tableRDescName);
                    renderer.setRenderHeader(withHeader);
                    resp.setContentType("application/octet-stream");
                    extension = ".pdf";
                    resp.addHeader(
                        "Content-Disposition",
                        "attachment; filename=" + fileName + extension);
                    Document document = new Document();
                    baos = new ByteArrayOutputStream();
                    PdfWriter.getInstance(document, baos);
                    document.open();
                    exportCustomizerStore
                        .customize(getResultDescName(req, action), renderer.getFieldRenderer());
                    renderer.render(listResult.getList());
                    document.add(((PdfRenderer) renderer).getTable());
                    document.close();
                    resp.getOutputStream().write(baos.toByteArray());
                    resp.getOutputStream().close();
                    break;

                case EXCEL:
                    Workbook wb = new HSSFWorkbook();
                    Sheet sheet = wb.createSheet(fileName);
                    renderer = excelRendererFactory
                        .create(getResultDescName(req, action), tableRDescName, sheet, true);
                    renderer.setRenderHeader(withHeader);
                    resp.setContentType("application/vnd.ms-excel");
                    resp.setCharacterEncoding("UTF-8");
                    extension = ".xls";
                    resp.addHeader(
                        "Content-Disposition",
                        "attachment; filename=" + fileName + extension);
                    exportCustomizerStore
                        .customize(getResultDescName(req, action), renderer.getFieldRenderer());
                    renderer.render(listResult.getList());
                    baos = new ByteArrayOutputStream();
                    wb.write(baos);
                    resp.getOutputStream().write(baos.toByteArray());
                    resp.getOutputStream().close();
                    break;
            }

        } catch (Exception e) {
            _logger.warn(e.getMessage(), e);
            resp.getWriter().write(IneFormI18n.csvError());
            resp.getWriter().close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,
        IOException {}

    private String getResultDescName(HttpServletRequest req, ObjectList action) {
        String respDescName = (String) req
            .getSession()
            .getAttribute(SetActionForExportServletHandler.responseDescriptorName);
        if (respDescName != null)
            return respDescName;
        return action.getDescriptorName();
    }

}
