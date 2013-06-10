package com.inepex.ineom;

import com.inepex.inei18n.util.LocalizationUtilLogic;
import com.inepex.ineom.shared.i18n.IneOmI18n;

public class LocalizationUtil {
	
	public static void main(String[] args) {
		new LocalizationUtilLogic(IneOmI18n.class, IneOmI18n.MODULE_NAME)
			.generateIneFormSourceFromCsv();
	}
}
