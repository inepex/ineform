package com.inepex.ineForm.server.tablerenderer.pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.itextpdf.text.pdf.BaseFont;

public class PdfFontLoader {

	private static final Logger _logger = LoggerFactory.getLogger(PdfFontLoader.class);
	
	private BaseFont baseFont;
	private boolean isDefault = true;
	
	@Inject
	public PdfFontLoader() {
		try {
			baseFont = BaseFont.createFont();
		} catch (Exception e) {
			_logger.warn("Something weird happend. Basefont not found for pdf export", e);
		}
	}
	
	public void init(String baseUrl){
		if (!isDefault) return;
		
		String fontUrl = baseUrl + "fonts/FreeSans.ttf";
		try {	
			baseFont = BaseFont.createFont(fontUrl, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			isDefault = false;
		} catch (Exception e) {
			_logger.warn("Font not found for pdf export: {}", fontUrl);
			
		}
	}

	public BaseFont getBaseFont() {
		if (isDefault) _logger.warn("Default font is used. May cause problems with unicode characters");
		return baseFont;
	}
	
}
