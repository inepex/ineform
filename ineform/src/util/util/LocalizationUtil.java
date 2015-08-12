package util;

import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.inei18n.util.LocalizationUtilLogic;

public class LocalizationUtil {

    public static void main(String[] args) {

        new LocalizationUtilLogic(IneFormI18n.class, IneFormI18n.MODULE_NAME)
            .generateIneFormSourceFromCsv();

    }
}
