package com.inepex.translatorapp;

import com.inepex.inei18n.util.LocalizationUtilLogic;
import com.inepex.translatorapp.client.i18n.translatorappI18n;

public class LocalizationUtil {
	
	public static void main(String[] args) {
		new LocalizationUtilLogic(translatorappI18n.class, translatorappI18n.MODULE_NAME)
			.generateIneFormSourceFromCsv();
	}
	
}
