package com.inepex.ineForm.server.tablerenderer.pdf;

import com.google.inject.Inject;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

public class PdfStyle {

    private BaseFont baseFont;
    private Font headerFont;
    private Font dataCellFont;

    @Inject
    public PdfStyle(PdfFontLoader pdfFontLoader) {
        baseFont = pdfFontLoader.getBaseFont();
        headerFont = new Font(baseFont, 9, Font.BOLD, new BaseColor(102, 102, 102));
        dataCellFont = new Font(baseFont, 8.7f, Font.NORMAL, new BaseColor(102, 102, 102));
    }

    public BaseFont getBaseFont() {
        return baseFont;
    }

    public void setBaseFont(BaseFont baseFont) {
        this.baseFont = baseFont;
    }

    public Font getHeaderFont() {
        return headerFont;
    }

    public void setHeaderFont(Font headerFont) {
        this.headerFont = headerFont;
    }

    public Font getDataCellFont() {
        return dataCellFont;
    }

    public void setDataCellFont(Font dataCellFont) {
        this.dataCellFont = dataCellFont;
    }

}
