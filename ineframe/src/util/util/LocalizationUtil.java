package util;

import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.inei18n.util.LocalizationUtilLogic;

public class LocalizationUtil {
	
	public static void main(String[] args) {
		new LocalizationUtilLogic(IneFrameI18n.class, IneFrameI18n.MODULE_NAME)
			.generateIneFormSourceFromCsv();
	}
}
