package com.inepex.ineForm.server.tablerenderer.pdf;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;
import com.google.inject.Singleton;
import com.itextpdf.text.pdf.BaseFont;

@Singleton
public class PdfFontLoader {

    private static final Logger _logger = LoggerFactory.getLogger(PdfFontLoader.class);
    private static final String fontName = "FreeSans.ttf";

    private BaseFont baseFont;
    private boolean inited = false;

    public BaseFont getBaseFont() {
        mayDoInit();
        return baseFont;
    }

    private void mayDoInit() {
        if (inited)
            return;

        inited = true;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fontName)) {
            baseFont =
                BaseFont.createFont(
                    fontName,
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED,
                    false,
                    ByteStreams.toByteArray(is),
                    new byte[0],
                    false,
                    true);

            return;
        } catch (Exception e) {
            _logger.error("Default font is used. May cause problems with unicode characters", e);
        }

        try {
            baseFont = BaseFont.createFont();
            return;
        } catch (Exception e) {
            _logger.error("Something weird happend. Basefont not found for pdf export", e);
        }
    }
}
