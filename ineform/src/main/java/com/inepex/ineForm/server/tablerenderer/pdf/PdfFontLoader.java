package com.inepex.ineForm.server.tablerenderer.pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;
import com.itextpdf.text.pdf.BaseFont;

@Singleton
public class PdfFontLoader {

	private static final Logger _logger = LoggerFactory.getLogger(PdfFontLoader.class);
	
	private BaseFont baseFont;
	private boolean inited=true;

	
	public BaseFont getBaseFont() {
		init();
		return baseFont;
	}
	
	private void init() {
		if(inited)
			return;
		
		inited=true;
		
		String fontUrl = getClass().getClassLoader().getResource("FreeSans.ttf").getFile();
		
		try {	
			baseFont = BaseFont.createFont(fontUrl, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			return;
		} catch (Exception e) {
			_logger.error("Font not found for pdf export: "+fontUrl, e);
			_logger.error("Default font is used. May cause problems with unicode characters");
		}
		
		try {
			baseFont = BaseFont.createFont();
			return;
		} catch (Exception e) {
			_logger.error("Something weird happend. Basefont not found for pdf export", e);
		}
	}
}
