package com.inepex.ineFrame.client.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class IneFrameI18n extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "IneFrameI18n";
	
	static I18nModuleProvider<IneFrameI18n> moduleProvider;
	
	public IneFrameI18n() {
		super(MODULE_NAME);
	}
		
	public IneFrameI18n(I18nModuleProvider<IneFrameI18n> moduleProvider) {
		super(MODULE_NAME);
		IneFrameI18n.moduleProvider = moduleProvider;
	}
	
	public static String getI18nText(String key){
		return moduleProvider.get().getText(key);
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Captcha:
	* <u><i>Magyarul:</i></u> Captcha:
	*/
	public static String CAPTCHA() {
		return moduleProvider.get().getText("CAPTCHA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Logging in...
	* <u><i>Magyarul:</i></u> Belépés...
	*/
	public static String LOGGINGIN() {
		return moduleProvider.get().getText("LOGGINGIN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Login
	* <u><i>Magyarul:</i></u> Belépés
	*/
	public static String LOGIN() {
		return moduleProvider.get().getText("LOGIN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Log out
	* <u><i>Magyarul:</i></u> Kijelentkezés
	*/
	public static String LOGOUT() {
		return moduleProvider.get().getText("LOGOUT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String OK() {
		return moduleProvider.get().getText("OK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Password:
	* <u><i>Magyarul:</i></u> Jelszó:
	*/
	public static String PASSWORD() {
		return moduleProvider.get().getText("PASSWORD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> E-mail:
	* <u><i>Magyarul:</i></u> E-mail:
	*/
	public static String USERNAME() {
		return moduleProvider.get().getText("USERNAME");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Question
	* <u><i>Magyarul:</i></u> Kérdés
	*/
	public static String confirmDialogTitle() {
		return moduleProvider.get().getText("confirmDialogTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Aruba
	* <u><i>Magyarul:</i></u> Aruba
	*/
	public static String countryABW() {
		return moduleProvider.get().getText("countryABW");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Afghanistan
	* <u><i>Magyarul:</i></u> Afganisztán
	*/
	public static String countryAFG() {
		return moduleProvider.get().getText("countryAFG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Angola
	* <u><i>Magyarul:</i></u> Angola
	*/
	public static String countryAGO() {
		return moduleProvider.get().getText("countryAGO");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Anguilla
	* <u><i>Magyarul:</i></u> Anguilla
	*/
	public static String countryAIA() {
		return moduleProvider.get().getText("countryAIA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Åland Islands
	* <u><i>Magyarul:</i></u> Aland-szigetek
	*/
	public static String countryALA() {
		return moduleProvider.get().getText("countryALA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Albania
	* <u><i>Magyarul:</i></u> Albánia
	*/
	public static String countryALB() {
		return moduleProvider.get().getText("countryALB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Andorra
	* <u><i>Magyarul:</i></u> Andorra
	*/
	public static String countryAND() {
		return moduleProvider.get().getText("countryAND");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Netherlands Antilles
	* <u><i>Magyarul:</i></u> Holland Antillák
	*/
	public static String countryANT() {
		return moduleProvider.get().getText("countryANT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> United Arab Emirates
	* <u><i>Magyarul:</i></u> Egyesült Arab Emírségek
	*/
	public static String countryARE() {
		return moduleProvider.get().getText("countryARE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Argentina
	* <u><i>Magyarul:</i></u> Argentína
	*/
	public static String countryARG() {
		return moduleProvider.get().getText("countryARG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Armenia
	* <u><i>Magyarul:</i></u> Örményország
	*/
	public static String countryARM() {
		return moduleProvider.get().getText("countryARM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> American Samoa
	* <u><i>Magyarul:</i></u> Amerikai Szamoa
	*/
	public static String countryASM() {
		return moduleProvider.get().getText("countryASM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Antarctica
	* <u><i>Magyarul:</i></u> Antarktisz
	*/
	public static String countryATA() {
		return moduleProvider.get().getText("countryATA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> French Southern and Antarctic Lands
	* <u><i>Magyarul:</i></u> Francia déli és antarktiszi területek
	*/
	public static String countryATF() {
		return moduleProvider.get().getText("countryATF");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Antigua and Barbuda
	* <u><i>Magyarul:</i></u> Antigua és Barbuda
	*/
	public static String countryATG() {
		return moduleProvider.get().getText("countryATG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Australia
	* <u><i>Magyarul:</i></u> Ausztrália
	*/
	public static String countryAUS() {
		return moduleProvider.get().getText("countryAUS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Austria
	* <u><i>Magyarul:</i></u> Ausztria
	*/
	public static String countryAUT() {
		return moduleProvider.get().getText("countryAUT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Azerbaijan
	* <u><i>Magyarul:</i></u> Azerbajdzsán
	*/
	public static String countryAZE() {
		return moduleProvider.get().getText("countryAZE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Burundi
	* <u><i>Magyarul:</i></u> Burundi
	*/
	public static String countryBDI() {
		return moduleProvider.get().getText("countryBDI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Belgium
	* <u><i>Magyarul:</i></u> Belgium
	*/
	public static String countryBEL() {
		return moduleProvider.get().getText("countryBEL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Benin
	* <u><i>Magyarul:</i></u> Benin
	*/
	public static String countryBEN() {
		return moduleProvider.get().getText("countryBEN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Burkina Faso
	* <u><i>Magyarul:</i></u> Burkina Faso
	*/
	public static String countryBFA() {
		return moduleProvider.get().getText("countryBFA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bangladesh
	* <u><i>Magyarul:</i></u> Banglades
	*/
	public static String countryBGD() {
		return moduleProvider.get().getText("countryBGD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bulgaria
	* <u><i>Magyarul:</i></u> Bulgária
	*/
	public static String countryBGR() {
		return moduleProvider.get().getText("countryBGR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bahrain
	* <u><i>Magyarul:</i></u> Bahrain
	*/
	public static String countryBHR() {
		return moduleProvider.get().getText("countryBHR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bahamas
	* <u><i>Magyarul:</i></u> Bahama-szigetek
	*/
	public static String countryBHS() {
		return moduleProvider.get().getText("countryBHS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bosnia and Herzegovina
	* <u><i>Magyarul:</i></u> Bosznia-Hercegovina
	*/
	public static String countryBIH() {
		return moduleProvider.get().getText("countryBIH");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> St. Barthélemy
	* <u><i>Magyarul:</i></u> Saint-Barthélemy
	*/
	public static String countryBLM() {
		return moduleProvider.get().getText("countryBLM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Belarus
	* <u><i>Magyarul:</i></u> Fehéroroszország
	*/
	public static String countryBLR() {
		return moduleProvider.get().getText("countryBLR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Belize
	* <u><i>Magyarul:</i></u> Belize
	*/
	public static String countryBLZ() {
		return moduleProvider.get().getText("countryBLZ");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bermuda
	* <u><i>Magyarul:</i></u> Bermuda
	*/
	public static String countryBMU() {
		return moduleProvider.get().getText("countryBMU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bolivia
	* <u><i>Magyarul:</i></u> Bolívia
	*/
	public static String countryBOL() {
		return moduleProvider.get().getText("countryBOL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Brazil
	* <u><i>Magyarul:</i></u> Brazília
	*/
	public static String countryBRA() {
		return moduleProvider.get().getText("countryBRA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Barbados
	* <u><i>Magyarul:</i></u> Barbados
	*/
	public static String countryBRB() {
		return moduleProvider.get().getText("countryBRB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Brunei
	* <u><i>Magyarul:</i></u> Brunei
	*/
	public static String countryBRN() {
		return moduleProvider.get().getText("countryBRN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bhutan
	* <u><i>Magyarul:</i></u> Bhután
	*/
	public static String countryBTN() {
		return moduleProvider.get().getText("countryBTN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Bouvet Island
	* <u><i>Magyarul:</i></u> Bouvet-sziget
	*/
	public static String countryBVT() {
		return moduleProvider.get().getText("countryBVT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Botswana
	* <u><i>Magyarul:</i></u> Botswana
	*/
	public static String countryBWA() {
		return moduleProvider.get().getText("countryBWA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Central African Republic
	* <u><i>Magyarul:</i></u> Közép-afrikai Köztársaság
	*/
	public static String countryCAF() {
		return moduleProvider.get().getText("countryCAF");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Canada
	* <u><i>Magyarul:</i></u> Kanada
	*/
	public static String countryCAN() {
		return moduleProvider.get().getText("countryCAN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cocos (Keeling) Islands
	* <u><i>Magyarul:</i></u> Kókusz (Keeling)-szigetek
	*/
	public static String countryCCK() {
		return moduleProvider.get().getText("countryCCK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Switzerland
	* <u><i>Magyarul:</i></u> Svájc
	*/
	public static String countryCHE() {
		return moduleProvider.get().getText("countryCHE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Chile
	* <u><i>Magyarul:</i></u> Chile
	*/
	public static String countryCHL() {
		return moduleProvider.get().getText("countryCHL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> China
	* <u><i>Magyarul:</i></u> Kína
	*/
	public static String countryCHN() {
		return moduleProvider.get().getText("countryCHN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Côte d'Ivoire
	* <u><i>Magyarul:</i></u> Elefántcsontpart
	*/
	public static String countryCIV() {
		return moduleProvider.get().getText("countryCIV");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cameroon
	* <u><i>Magyarul:</i></u> Kamerun
	*/
	public static String countryCMR() {
		return moduleProvider.get().getText("countryCMR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Congo (DRC)
	* <u><i>Magyarul:</i></u> Kongói Demokratikus Köztársaság
	*/
	public static String countryCOD() {
		return moduleProvider.get().getText("countryCOD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Congo
	* <u><i>Magyarul:</i></u> Kongó
	*/
	public static String countryCOG() {
		return moduleProvider.get().getText("countryCOG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cook Islands
	* <u><i>Magyarul:</i></u> Cook-szigetek
	*/
	public static String countryCOK() {
		return moduleProvider.get().getText("countryCOK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Colombia
	* <u><i>Magyarul:</i></u> Kolumbia
	*/
	public static String countryCOL() {
		return moduleProvider.get().getText("countryCOL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Comoros
	* <u><i>Magyarul:</i></u> Comore-szigetek
	*/
	public static String countryCOM() {
		return moduleProvider.get().getText("countryCOM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cape Verde
	* <u><i>Magyarul:</i></u> Zöld-foki Köztársaság
	*/
	public static String countryCPV() {
		return moduleProvider.get().getText("countryCPV");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Costa Rica
	* <u><i>Magyarul:</i></u> Costa Rica
	*/
	public static String countryCRI() {
		return moduleProvider.get().getText("countryCRI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cuba
	* <u><i>Magyarul:</i></u> Kuba
	*/
	public static String countryCUB() {
		return moduleProvider.get().getText("countryCUB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Christmas Island
	* <u><i>Magyarul:</i></u> Karácsony-sziget
	*/
	public static String countryCXR() {
		return moduleProvider.get().getText("countryCXR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cayman Islands
	* <u><i>Magyarul:</i></u> Kajmán-szigetek
	*/
	public static String countryCYM() {
		return moduleProvider.get().getText("countryCYM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cyprus
	* <u><i>Magyarul:</i></u> Ciprusi Köztársaság
	*/
	public static String countryCYP() {
		return moduleProvider.get().getText("countryCYP");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Czech Republic
	* <u><i>Magyarul:</i></u> Csehország
	*/
	public static String countryCZE() {
		return moduleProvider.get().getText("countryCZE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Germany
	* <u><i>Magyarul:</i></u> Németország
	*/
	public static String countryDEU() {
		return moduleProvider.get().getText("countryDEU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Djibouti
	* <u><i>Magyarul:</i></u> Dzsibuti
	*/
	public static String countryDJI() {
		return moduleProvider.get().getText("countryDJI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Dominica
	* <u><i>Magyarul:</i></u> Dominikai Közösség
	*/
	public static String countryDMA() {
		return moduleProvider.get().getText("countryDMA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Denmark
	* <u><i>Magyarul:</i></u> Dánia
	*/
	public static String countryDNK() {
		return moduleProvider.get().getText("countryDNK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Dominican Republic
	* <u><i>Magyarul:</i></u> Dominikai Köztársaság
	*/
	public static String countryDOM() {
		return moduleProvider.get().getText("countryDOM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Algeria
	* <u><i>Magyarul:</i></u> Algéria
	*/
	public static String countryDZA() {
		return moduleProvider.get().getText("countryDZA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ecuador
	* <u><i>Magyarul:</i></u> Ecuador
	*/
	public static String countryECU() {
		return moduleProvider.get().getText("countryECU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Egypt
	* <u><i>Magyarul:</i></u> Egyiptom
	*/
	public static String countryEGY() {
		return moduleProvider.get().getText("countryEGY");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Eritrea
	* <u><i>Magyarul:</i></u> Eritrea
	*/
	public static String countryERI() {
		return moduleProvider.get().getText("countryERI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Spain
	* <u><i>Magyarul:</i></u> Spanyolország
	*/
	public static String countryESP() {
		return moduleProvider.get().getText("countryESP");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Estonia
	* <u><i>Magyarul:</i></u> Észtország
	*/
	public static String countryEST() {
		return moduleProvider.get().getText("countryEST");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ethiopia
	* <u><i>Magyarul:</i></u> Etiópia
	*/
	public static String countryETH() {
		return moduleProvider.get().getText("countryETH");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Finland
	* <u><i>Magyarul:</i></u> Finnország
	*/
	public static String countryFIN() {
		return moduleProvider.get().getText("countryFIN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Fiji Islands
	* <u><i>Magyarul:</i></u> Fidzsi-szigetek
	*/
	public static String countryFJI() {
		return moduleProvider.get().getText("countryFJI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Falkland Islands (Islas Malvinas)
	* <u><i>Magyarul:</i></u> Falkland-szigetek
	*/
	public static String countryFLK() {
		return moduleProvider.get().getText("countryFLK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> France
	* <u><i>Magyarul:</i></u> Franciaország
	*/
	public static String countryFRA() {
		return moduleProvider.get().getText("countryFRA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Faroe Islands
	* <u><i>Magyarul:</i></u> Feröer
	*/
	public static String countryFRO() {
		return moduleProvider.get().getText("countryFRO");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Micronesia
	* <u><i>Magyarul:</i></u> Mikronézia
	*/
	public static String countryFSM() {
		return moduleProvider.get().getText("countryFSM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Gabon
	* <u><i>Magyarul:</i></u> Gabon
	*/
	public static String countryGAB() {
		return moduleProvider.get().getText("countryGAB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> United Kingdom
	* <u><i>Magyarul:</i></u> Egyesült Királyság
	*/
	public static String countryGBR() {
		return moduleProvider.get().getText("countryGBR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Georgia
	* <u><i>Magyarul:</i></u> Grúzia
	*/
	public static String countryGEO() {
		return moduleProvider.get().getText("countryGEO");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Guernsey
	* <u><i>Magyarul:</i></u> Guernsey
	*/
	public static String countryGGY() {
		return moduleProvider.get().getText("countryGGY");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ghana
	* <u><i>Magyarul:</i></u> Ghána
	*/
	public static String countryGHA() {
		return moduleProvider.get().getText("countryGHA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Gibraltar
	* <u><i>Magyarul:</i></u> Gibraltár
	*/
	public static String countryGIB() {
		return moduleProvider.get().getText("countryGIB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Guinea
	* <u><i>Magyarul:</i></u> Guinea
	*/
	public static String countryGIN() {
		return moduleProvider.get().getText("countryGIN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Guadeloupe
	* <u><i>Magyarul:</i></u> Guadeloupe
	*/
	public static String countryGLP() {
		return moduleProvider.get().getText("countryGLP");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Gambia
	* <u><i>Magyarul:</i></u> Gambia
	*/
	public static String countryGMB() {
		return moduleProvider.get().getText("countryGMB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Guinea-Bissau
	* <u><i>Magyarul:</i></u> Bissau-Guinea
	*/
	public static String countryGNB() {
		return moduleProvider.get().getText("countryGNB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Equatorial Guinea
	* <u><i>Magyarul:</i></u> Egyenlítői-Guinea
	*/
	public static String countryGNQ() {
		return moduleProvider.get().getText("countryGNQ");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Greece
	* <u><i>Magyarul:</i></u> Görögország
	*/
	public static String countryGRC() {
		return moduleProvider.get().getText("countryGRC");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Grenada
	* <u><i>Magyarul:</i></u> Grenada
	*/
	public static String countryGRD() {
		return moduleProvider.get().getText("countryGRD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Greenland
	* <u><i>Magyarul:</i></u> Grönland
	*/
	public static String countryGRL() {
		return moduleProvider.get().getText("countryGRL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Guatemala
	* <u><i>Magyarul:</i></u> Guatemala
	*/
	public static String countryGTM() {
		return moduleProvider.get().getText("countryGTM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> French Guiana
	* <u><i>Magyarul:</i></u> Francia Guyana
	*/
	public static String countryGUF() {
		return moduleProvider.get().getText("countryGUF");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Guam
	* <u><i>Magyarul:</i></u> Guam
	*/
	public static String countryGUM() {
		return moduleProvider.get().getText("countryGUM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Guyana
	* <u><i>Magyarul:</i></u> Guyana
	*/
	public static String countryGUY() {
		return moduleProvider.get().getText("countryGUY");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Hong Kong SAR
	* <u><i>Magyarul:</i></u> Hongkong
	*/
	public static String countryHKG() {
		return moduleProvider.get().getText("countryHKG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Heard Island and McDonald Islands
	* <u><i>Magyarul:</i></u> Heard-sziget és McDonald-szigetek
	*/
	public static String countryHMD() {
		return moduleProvider.get().getText("countryHMD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Honduras
	* <u><i>Magyarul:</i></u> Honduras
	*/
	public static String countryHND() {
		return moduleProvider.get().getText("countryHND");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Croatia
	* <u><i>Magyarul:</i></u> Horvátország
	*/
	public static String countryHRV() {
		return moduleProvider.get().getText("countryHRV");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Haiti
	* <u><i>Magyarul:</i></u> Haiti
	*/
	public static String countryHTI() {
		return moduleProvider.get().getText("countryHTI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Hungary
	* <u><i>Magyarul:</i></u> Magyarország
	*/
	public static String countryHUN() {
		return moduleProvider.get().getText("countryHUN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Indonesia
	* <u><i>Magyarul:</i></u> Indonézia
	*/
	public static String countryIDN() {
		return moduleProvider.get().getText("countryIDN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Isle of Man
	* <u><i>Magyarul:</i></u> Man-sziget
	*/
	public static String countryIMN() {
		return moduleProvider.get().getText("countryIMN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> India
	* <u><i>Magyarul:</i></u> India
	*/
	public static String countryIND() {
		return moduleProvider.get().getText("countryIND");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> British Indian Ocean Territory
	* <u><i>Magyarul:</i></u> Brit Indiai-óceáni Terület
	*/
	public static String countryIOT() {
		return moduleProvider.get().getText("countryIOT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ireland
	* <u><i>Magyarul:</i></u> Írország
	*/
	public static String countryIRL() {
		return moduleProvider.get().getText("countryIRL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Iran
	* <u><i>Magyarul:</i></u> Irán
	*/
	public static String countryIRN() {
		return moduleProvider.get().getText("countryIRN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Iraq
	* <u><i>Magyarul:</i></u> Irak
	*/
	public static String countryIRQ() {
		return moduleProvider.get().getText("countryIRQ");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Iceland
	* <u><i>Magyarul:</i></u> Izland
	*/
	public static String countryISL() {
		return moduleProvider.get().getText("countryISL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Israel
	* <u><i>Magyarul:</i></u> Izrael
	*/
	public static String countryISR() {
		return moduleProvider.get().getText("countryISR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Italy
	* <u><i>Magyarul:</i></u> Olaszország
	*/
	public static String countryITA() {
		return moduleProvider.get().getText("countryITA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Jamaica
	* <u><i>Magyarul:</i></u> Jamaica
	*/
	public static String countryJAM() {
		return moduleProvider.get().getText("countryJAM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Jersey
	* <u><i>Magyarul:</i></u> Jersey
	*/
	public static String countryJEY() {
		return moduleProvider.get().getText("countryJEY");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Jordan
	* <u><i>Magyarul:</i></u> Jordánia
	*/
	public static String countryJOR() {
		return moduleProvider.get().getText("countryJOR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Japan
	* <u><i>Magyarul:</i></u> Japán
	*/
	public static String countryJPN() {
		return moduleProvider.get().getText("countryJPN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Kazakhstan
	* <u><i>Magyarul:</i></u> Kazahsztán
	*/
	public static String countryKAZ() {
		return moduleProvider.get().getText("countryKAZ");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Kenya
	* <u><i>Magyarul:</i></u> Kenya
	*/
	public static String countryKEN() {
		return moduleProvider.get().getText("countryKEN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Kyrgyzstan
	* <u><i>Magyarul:</i></u> Kirgizisztán
	*/
	public static String countryKGZ() {
		return moduleProvider.get().getText("countryKGZ");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cambodia
	* <u><i>Magyarul:</i></u> Kambodzsa
	*/
	public static String countryKHM() {
		return moduleProvider.get().getText("countryKHM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Kiribati
	* <u><i>Magyarul:</i></u> Kiribati
	*/
	public static String countryKIR() {
		return moduleProvider.get().getText("countryKIR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> St. Kitts and Nevis
	* <u><i>Magyarul:</i></u> Saint Kitts és Nevis
	*/
	public static String countryKNA() {
		return moduleProvider.get().getText("countryKNA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> South Korea
	* <u><i>Magyarul:</i></u> Dél-Korea
	*/
	public static String countryKOR() {
		return moduleProvider.get().getText("countryKOR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Kuwait
	* <u><i>Magyarul:</i></u> Kuvait
	*/
	public static String countryKWT() {
		return moduleProvider.get().getText("countryKWT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Laos
	* <u><i>Magyarul:</i></u> Laosz
	*/
	public static String countryLAO() {
		return moduleProvider.get().getText("countryLAO");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Lebanon
	* <u><i>Magyarul:</i></u> Libanon
	*/
	public static String countryLBN() {
		return moduleProvider.get().getText("countryLBN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Liberia
	* <u><i>Magyarul:</i></u> Libéria
	*/
	public static String countryLBR() {
		return moduleProvider.get().getText("countryLBR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Libya
	* <u><i>Magyarul:</i></u> Líbia
	*/
	public static String countryLBY() {
		return moduleProvider.get().getText("countryLBY");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> St. Lucia
	* <u><i>Magyarul:</i></u> Saint Lucia
	*/
	public static String countryLCA() {
		return moduleProvider.get().getText("countryLCA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Liechtenstein
	* <u><i>Magyarul:</i></u> Liechtenstein
	*/
	public static String countryLIE() {
		return moduleProvider.get().getText("countryLIE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Sri Lanka
	* <u><i>Magyarul:</i></u> Srí Lanka
	*/
	public static String countryLKA() {
		return moduleProvider.get().getText("countryLKA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Lesotho
	* <u><i>Magyarul:</i></u> Lesotho
	*/
	public static String countryLSO() {
		return moduleProvider.get().getText("countryLSO");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Lithuania
	* <u><i>Magyarul:</i></u> Litvánia
	*/
	public static String countryLTU() {
		return moduleProvider.get().getText("countryLTU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Luxembourg
	* <u><i>Magyarul:</i></u> Luxemburg
	*/
	public static String countryLUX() {
		return moduleProvider.get().getText("countryLUX");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Latvia
	* <u><i>Magyarul:</i></u> Lettország
	*/
	public static String countryLVA() {
		return moduleProvider.get().getText("countryLVA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Macao SAR
	* <u><i>Magyarul:</i></u> Makaó
	*/
	public static String countryMAC() {
		return moduleProvider.get().getText("countryMAC");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> St. Martin
	* <u><i>Magyarul:</i></u> Saint-Martin
	*/
	public static String countryMAF() {
		return moduleProvider.get().getText("countryMAF");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Morocco
	* <u><i>Magyarul:</i></u> Marokkó
	*/
	public static String countryMAR() {
		return moduleProvider.get().getText("countryMAR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Monaco
	* <u><i>Magyarul:</i></u> Monaco
	*/
	public static String countryMCO() {
		return moduleProvider.get().getText("countryMCO");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Moldova
	* <u><i>Magyarul:</i></u> Moldova
	*/
	public static String countryMDA() {
		return moduleProvider.get().getText("countryMDA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Madagascar
	* <u><i>Magyarul:</i></u> Madagaszkár
	*/
	public static String countryMDG() {
		return moduleProvider.get().getText("countryMDG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Maldives
	* <u><i>Magyarul:</i></u> Maldív-szigetek
	*/
	public static String countryMDV() {
		return moduleProvider.get().getText("countryMDV");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Mexico
	* <u><i>Magyarul:</i></u> Mexikó
	*/
	public static String countryMEX() {
		return moduleProvider.get().getText("countryMEX");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Marshall Islands
	* <u><i>Magyarul:</i></u> Marshall-szigetek
	*/
	public static String countryMHL() {
		return moduleProvider.get().getText("countryMHL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Macedonia
	* <u><i>Magyarul:</i></u> Macedónia
	*/
	public static String countryMKD() {
		return moduleProvider.get().getText("countryMKD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Mali
	* <u><i>Magyarul:</i></u> Mali
	*/
	public static String countryMLI() {
		return moduleProvider.get().getText("countryMLI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Malta
	* <u><i>Magyarul:</i></u> Málta
	*/
	public static String countryMLT() {
		return moduleProvider.get().getText("countryMLT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Myanmar
	* <u><i>Magyarul:</i></u> Mianmar
	*/
	public static String countryMMR() {
		return moduleProvider.get().getText("countryMMR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Montenegro
	* <u><i>Magyarul:</i></u> Montenegró
	*/
	public static String countryMNE() {
		return moduleProvider.get().getText("countryMNE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Mongolia
	* <u><i>Magyarul:</i></u> Mongólia
	*/
	public static String countryMNG() {
		return moduleProvider.get().getText("countryMNG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Northern Mariana Islands
	* <u><i>Magyarul:</i></u> Északi-Mariana-szigetek
	*/
	public static String countryMNP() {
		return moduleProvider.get().getText("countryMNP");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Mozambique
	* <u><i>Magyarul:</i></u> Mozambik
	*/
	public static String countryMOZ() {
		return moduleProvider.get().getText("countryMOZ");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Mauritania
	* <u><i>Magyarul:</i></u> Mauritánia
	*/
	public static String countryMRT() {
		return moduleProvider.get().getText("countryMRT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Montserrat
	* <u><i>Magyarul:</i></u> Montserrat
	*/
	public static String countryMSR() {
		return moduleProvider.get().getText("countryMSR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Martinique
	* <u><i>Magyarul:</i></u> Martinique
	*/
	public static String countryMTQ() {
		return moduleProvider.get().getText("countryMTQ");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Mauritius
	* <u><i>Magyarul:</i></u> Mauritius
	*/
	public static String countryMUS() {
		return moduleProvider.get().getText("countryMUS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Malawi
	* <u><i>Magyarul:</i></u> Malawi
	*/
	public static String countryMWI() {
		return moduleProvider.get().getText("countryMWI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Malaysia
	* <u><i>Magyarul:</i></u> Malajzia
	*/
	public static String countryMYS() {
		return moduleProvider.get().getText("countryMYS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Mayotte
	* <u><i>Magyarul:</i></u> Mayotte
	*/
	public static String countryMYT() {
		return moduleProvider.get().getText("countryMYT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Namibia
	* <u><i>Magyarul:</i></u> Namíbia
	*/
	public static String countryNAM() {
		return moduleProvider.get().getText("countryNAM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New Caledonia
	* <u><i>Magyarul:</i></u> Új-Kaledónia
	*/
	public static String countryNCL() {
		return moduleProvider.get().getText("countryNCL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Niger
	* <u><i>Magyarul:</i></u> Niger
	*/
	public static String countryNER() {
		return moduleProvider.get().getText("countryNER");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Norfolk Island
	* <u><i>Magyarul:</i></u> Norfolk-sziget
	*/
	public static String countryNFK() {
		return moduleProvider.get().getText("countryNFK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Nigeria
	* <u><i>Magyarul:</i></u> Nigéria
	*/
	public static String countryNGA() {
		return moduleProvider.get().getText("countryNGA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Nicaragua
	* <u><i>Magyarul:</i></u> Nicaragua
	*/
	public static String countryNIC() {
		return moduleProvider.get().getText("countryNIC");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Niue
	* <u><i>Magyarul:</i></u> Niue
	*/
	public static String countryNIU() {
		return moduleProvider.get().getText("countryNIU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Netherlands
	* <u><i>Magyarul:</i></u> Hollandia
	*/
	public static String countryNLD() {
		return moduleProvider.get().getText("countryNLD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Norway
	* <u><i>Magyarul:</i></u> Norvégia
	*/
	public static String countryNOR() {
		return moduleProvider.get().getText("countryNOR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Nepal
	* <u><i>Magyarul:</i></u> Nepál
	*/
	public static String countryNPL() {
		return moduleProvider.get().getText("countryNPL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Nauru
	* <u><i>Magyarul:</i></u> Nauru
	*/
	public static String countryNRU() {
		return moduleProvider.get().getText("countryNRU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New Zealand
	* <u><i>Magyarul:</i></u> Új-Zéland
	*/
	public static String countryNZL() {
		return moduleProvider.get().getText("countryNZL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Oman
	* <u><i>Magyarul:</i></u> Omán
	*/
	public static String countryOMN() {
		return moduleProvider.get().getText("countryOMN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Pakistan
	* <u><i>Magyarul:</i></u> Pakisztán
	*/
	public static String countryPAK() {
		return moduleProvider.get().getText("countryPAK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Panama
	* <u><i>Magyarul:</i></u> Panama
	*/
	public static String countryPAN() {
		return moduleProvider.get().getText("countryPAN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Pitcairn Islands
	* <u><i>Magyarul:</i></u> Pitcairn-szigetek
	*/
	public static String countryPCN() {
		return moduleProvider.get().getText("countryPCN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Peru
	* <u><i>Magyarul:</i></u> Peru
	*/
	public static String countryPER() {
		return moduleProvider.get().getText("countryPER");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Philippines
	* <u><i>Magyarul:</i></u> Fülöp-szigetek
	*/
	public static String countryPHL() {
		return moduleProvider.get().getText("countryPHL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Palau
	* <u><i>Magyarul:</i></u> Palau
	*/
	public static String countryPLW() {
		return moduleProvider.get().getText("countryPLW");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Papua New Guinea
	* <u><i>Magyarul:</i></u> Pápua Új-Guinea
	*/
	public static String countryPNG() {
		return moduleProvider.get().getText("countryPNG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Poland
	* <u><i>Magyarul:</i></u> Lengyelország
	*/
	public static String countryPOL() {
		return moduleProvider.get().getText("countryPOL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Puerto Rico
	* <u><i>Magyarul:</i></u> Puerto Rico
	*/
	public static String countryPRI() {
		return moduleProvider.get().getText("countryPRI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> North Korea
	* <u><i>Magyarul:</i></u> Észak-Korea
	*/
	public static String countryPRK() {
		return moduleProvider.get().getText("countryPRK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Portugal
	* <u><i>Magyarul:</i></u> Portugália
	*/
	public static String countryPRT() {
		return moduleProvider.get().getText("countryPRT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Paraguay
	* <u><i>Magyarul:</i></u> Paraguay
	*/
	public static String countryPRY() {
		return moduleProvider.get().getText("countryPRY");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Palestinian Authority
	* <u><i>Magyarul:</i></u> Palesztin Nemzeti Hatóság
	*/
	public static String countryPSE() {
		return moduleProvider.get().getText("countryPSE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> French Polynesia
	* <u><i>Magyarul:</i></u> Francia Polinézia
	*/
	public static String countryPYF() {
		return moduleProvider.get().getText("countryPYF");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Qatar
	* <u><i>Magyarul:</i></u> Katar
	*/
	public static String countryQAT() {
		return moduleProvider.get().getText("countryQAT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Reunion
	* <u><i>Magyarul:</i></u> Réunion
	*/
	public static String countryREU() {
		return moduleProvider.get().getText("countryREU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Romania
	* <u><i>Magyarul:</i></u> Románia
	*/
	public static String countryROU() {
		return moduleProvider.get().getText("countryROU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Russia
	* <u><i>Magyarul:</i></u> Oroszország
	*/
	public static String countryRUS() {
		return moduleProvider.get().getText("countryRUS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Rwanda
	* <u><i>Magyarul:</i></u> Ruanda
	*/
	public static String countryRWA() {
		return moduleProvider.get().getText("countryRWA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Saudi Arabia
	* <u><i>Magyarul:</i></u> Szaúd-Arábia
	*/
	public static String countrySAU() {
		return moduleProvider.get().getText("countrySAU");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Sudan
	* <u><i>Magyarul:</i></u> Szudán
	*/
	public static String countrySDN() {
		return moduleProvider.get().getText("countrySDN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Senegal
	* <u><i>Magyarul:</i></u> Szenegál
	*/
	public static String countrySEN() {
		return moduleProvider.get().getText("countrySEN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Singapore
	* <u><i>Magyarul:</i></u> Szingapúr
	*/
	public static String countrySGP() {
		return moduleProvider.get().getText("countrySGP");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> South Georgia and the South Sandwich Islands
	* <u><i>Magyarul:</i></u> Déli-Georgia és Déli-Sandwich-szigetek
	*/
	public static String countrySGS() {
		return moduleProvider.get().getText("countrySGS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> St. Helena
	* <u><i>Magyarul:</i></u> Szent Ilona
	*/
	public static String countrySHN() {
		return moduleProvider.get().getText("countrySHN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Jan Mayen
	* <u><i>Magyarul:</i></u> Jan Mayen-sziget
	*/
	public static String countrySJM() {
		return moduleProvider.get().getText("countrySJM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Solomon Islands
	* <u><i>Magyarul:</i></u> Salamon-szigetek
	*/
	public static String countrySLB() {
		return moduleProvider.get().getText("countrySLB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Sierra Leone
	* <u><i>Magyarul:</i></u> Sierra Leone
	*/
	public static String countrySLE() {
		return moduleProvider.get().getText("countrySLE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> El Salvador
	* <u><i>Magyarul:</i></u> Salvador
	*/
	public static String countrySLV() {
		return moduleProvider.get().getText("countrySLV");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> San Marino
	* <u><i>Magyarul:</i></u> San Marino
	*/
	public static String countrySMR() {
		return moduleProvider.get().getText("countrySMR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Somalia
	* <u><i>Magyarul:</i></u> Szomália
	*/
	public static String countrySOM() {
		return moduleProvider.get().getText("countrySOM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> St. Pierre and Miquelon
	* <u><i>Magyarul:</i></u> Saint-Pierre és Miquelon
	*/
	public static String countrySPM() {
		return moduleProvider.get().getText("countrySPM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Serbia
	* <u><i>Magyarul:</i></u> Szerbia
	*/
	public static String countrySRB() {
		return moduleProvider.get().getText("countrySRB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> São Tomé and Príncipe
	* <u><i>Magyarul:</i></u> São Tomé és Príncipe
	*/
	public static String countrySTP() {
		return moduleProvider.get().getText("countrySTP");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Suriname
	* <u><i>Magyarul:</i></u> Suriname
	*/
	public static String countrySUR() {
		return moduleProvider.get().getText("countrySUR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Slovakia
	* <u><i>Magyarul:</i></u> Szlovákia
	*/
	public static String countrySVK() {
		return moduleProvider.get().getText("countrySVK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Slovenia
	* <u><i>Magyarul:</i></u> Szlovénia
	*/
	public static String countrySVN() {
		return moduleProvider.get().getText("countrySVN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Sweden
	* <u><i>Magyarul:</i></u> Svédország
	*/
	public static String countrySWE() {
		return moduleProvider.get().getText("countrySWE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Swaziland
	* <u><i>Magyarul:</i></u> Szváziföld
	*/
	public static String countrySWZ() {
		return moduleProvider.get().getText("countrySWZ");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Seychelles
	* <u><i>Magyarul:</i></u> Seychelle-szigetek
	*/
	public static String countrySYC() {
		return moduleProvider.get().getText("countrySYC");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Syria
	* <u><i>Magyarul:</i></u> Szíria
	*/
	public static String countrySYR() {
		return moduleProvider.get().getText("countrySYR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Turks and Caicos Islands
	* <u><i>Magyarul:</i></u> Turks- és Caicos-szigetek
	*/
	public static String countryTCA() {
		return moduleProvider.get().getText("countryTCA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Chad
	* <u><i>Magyarul:</i></u> Csád
	*/
	public static String countryTCD() {
		return moduleProvider.get().getText("countryTCD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Togo
	* <u><i>Magyarul:</i></u> Togo
	*/
	public static String countryTGO() {
		return moduleProvider.get().getText("countryTGO");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Thailand
	* <u><i>Magyarul:</i></u> Thaiföld
	*/
	public static String countryTHA() {
		return moduleProvider.get().getText("countryTHA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tajikistan
	* <u><i>Magyarul:</i></u> Tádzsikisztán
	*/
	public static String countryTJK() {
		return moduleProvider.get().getText("countryTJK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tokelau
	* <u><i>Magyarul:</i></u> Tokelau-szigetek
	*/
	public static String countryTKL() {
		return moduleProvider.get().getText("countryTKL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Turkmenistan
	* <u><i>Magyarul:</i></u> Türkmenisztán
	*/
	public static String countryTKM() {
		return moduleProvider.get().getText("countryTKM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Timor-Leste
	* <u><i>Magyarul:</i></u> Kelet-Timor
	*/
	public static String countryTLS() {
		return moduleProvider.get().getText("countryTLS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tonga
	* <u><i>Magyarul:</i></u> Tonga
	*/
	public static String countryTON() {
		return moduleProvider.get().getText("countryTON");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Trinidad and Tobago
	* <u><i>Magyarul:</i></u> Trinidad és Tobago
	*/
	public static String countryTTO() {
		return moduleProvider.get().getText("countryTTO");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tunisia
	* <u><i>Magyarul:</i></u> Tunézia
	*/
	public static String countryTUN() {
		return moduleProvider.get().getText("countryTUN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Turkey
	* <u><i>Magyarul:</i></u> Törökország
	*/
	public static String countryTUR() {
		return moduleProvider.get().getText("countryTUR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tuvalu
	* <u><i>Magyarul:</i></u> Tuvalu
	*/
	public static String countryTUV() {
		return moduleProvider.get().getText("countryTUV");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Taiwan
	* <u><i>Magyarul:</i></u> Kínai Köztársaság
	*/
	public static String countryTWN() {
		return moduleProvider.get().getText("countryTWN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tanzania
	* <u><i>Magyarul:</i></u> Tanzánia
	*/
	public static String countryTZA() {
		return moduleProvider.get().getText("countryTZA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Uganda
	* <u><i>Magyarul:</i></u> Uganda
	*/
	public static String countryUGA() {
		return moduleProvider.get().getText("countryUGA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ukraine
	* <u><i>Magyarul:</i></u> Ukrajna
	*/
	public static String countryUKR() {
		return moduleProvider.get().getText("countryUKR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> United States Minor Outlying Islands
	* <u><i>Magyarul:</i></u> Az Amerikai Egyesült Államok lakatlan külbirtokai
	*/
	public static String countryUMI() {
		return moduleProvider.get().getText("countryUMI");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Uruguay
	* <u><i>Magyarul:</i></u> Uruguay
	*/
	public static String countryURY() {
		return moduleProvider.get().getText("countryURY");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> United States
	* <u><i>Magyarul:</i></u> Amerikai Egyesült Államok
	*/
	public static String countryUSA() {
		return moduleProvider.get().getText("countryUSA");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Uzbekistan
	* <u><i>Magyarul:</i></u> Üzbegisztán
	*/
	public static String countryUZB() {
		return moduleProvider.get().getText("countryUZB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Vatican City
	* <u><i>Magyarul:</i></u> Vatikán
	*/
	public static String countryVAT() {
		return moduleProvider.get().getText("countryVAT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> St. Vincent and the Grenadines
	* <u><i>Magyarul:</i></u> Saint Vincent és a Grenadine-szigetek
	*/
	public static String countryVCT() {
		return moduleProvider.get().getText("countryVCT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Venezuela
	* <u><i>Magyarul:</i></u> Venezuela
	*/
	public static String countryVEN() {
		return moduleProvider.get().getText("countryVEN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Virgin Islands, British
	* <u><i>Magyarul:</i></u> Brit Virgin-szigetek
	*/
	public static String countryVGB() {
		return moduleProvider.get().getText("countryVGB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Virgin Islands, U.S.
	* <u><i>Magyarul:</i></u> Amerikai Virgin-szigetek
	*/
	public static String countryVIR() {
		return moduleProvider.get().getText("countryVIR");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Vietnam
	* <u><i>Magyarul:</i></u> Vietnam
	*/
	public static String countryVNM() {
		return moduleProvider.get().getText("countryVNM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Vanuatu
	* <u><i>Magyarul:</i></u> Vanuatu
	*/
	public static String countryVUT() {
		return moduleProvider.get().getText("countryVUT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Wallis and Futuna
	* <u><i>Magyarul:</i></u> Wallis és Futuna
	*/
	public static String countryWLF() {
		return moduleProvider.get().getText("countryWLF");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Samoa
	* <u><i>Magyarul:</i></u> Szamoa
	*/
	public static String countryWSM() {
		return moduleProvider.get().getText("countryWSM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Yemen
	* <u><i>Magyarul:</i></u> Jemen
	*/
	public static String countryYEM() {
		return moduleProvider.get().getText("countryYEM");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> South Africa
	* <u><i>Magyarul:</i></u> Dél-afrikai Köztársaság
	*/
	public static String countryZAF() {
		return moduleProvider.get().getText("countryZAF");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Zambia
	* <u><i>Magyarul:</i></u> Zambia
	*/
	public static String countryZMB() {
		return moduleProvider.get().getText("countryZMB");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Zimbabwe
	* <u><i>Magyarul:</i></u> Zimbabwe
	*/
	public static String countryZWE() {
		return moduleProvider.get().getText("countryZWE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> d
	* <u><i>Magyarul:</i></u> n
	*/
	public static String dayShort() {
		return moduleProvider.get().getText("dayShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} days ago
	* <u><i>Magyarul:</i></u> {number} napja
	*/
	public static String daysAgo(String number) {
		return moduleProvider.get().getText("daysAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cancel
	* <u><i>Magyarul:</i></u> Mégse
	*/
	public static String dialogCancelButton() {
		return moduleProvider.get().getText("dialogCancelButton");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String dialogOkButton() {
		return moduleProvider.get().getText("dialogOkButton");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> not implemented yet...
	* <u><i>Magyarul:</i></u> készülőben...
	*/
	public static String dummyPageText() {
		return moduleProvider.get().getText("dummyPageText");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error
	* <u><i>Magyarul:</i></u> Hiba
	*/
	public static String errorDialogTitle() {
		return moduleProvider.get().getText("errorDialogTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Unexpected error occurred. Please try refreshing the page. If the error persist, contact the administrator!
	* <u><i>Magyarul:</i></u> A szerveren nem várt hiba lépett fel! Próbálja meg újratölteni az oldalt. Ha a hibajelenséget továbbra is tapasztalja kérem értesítse a rendszergazdát!
	*/
	public static String generalError() {
		return moduleProvider.get().getText("generalError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> h
	* <u><i>Magyarul:</i></u> ó
	*/
	public static String hourShort() {
		return moduleProvider.get().getText("hourShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} hours ago
	* <u><i>Magyarul:</i></u> {number} órája
	*/
	public static String hoursAgo(String number) {
		return moduleProvider.get().getText("hoursAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Apps
	* <u><i>Magyarul:</i></u> Appok
	*/
	public static String inemenu_apps() {
		return moduleProvider.get().getText("inemenu_apps");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Help
	* <u><i>Magyarul:</i></u> Súgó
	*/
	public static String inemenu_help() {
		return moduleProvider.get().getText("inemenu_help");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Help & Settings
	* <u><i>Magyarul:</i></u> Súgó, beállítások
	*/
	public static String inemenu_helpsettings() {
		return moduleProvider.get().getText("inemenu_helpsettings");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Leave alias
	* <u><i>Magyarul:</i></u> Alias mód elhagyása
	*/
	public static String inemenu_leavealias() {
		return moduleProvider.get().getText("inemenu_leavealias");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Logout
	* <u><i>Magyarul:</i></u> Kijelentkezés
	*/
	public static String inemenu_logout() {
		return moduleProvider.get().getText("inemenu_logout");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Settings
	* <u><i>Magyarul:</i></u> Beállítások
	*/
	public static String inemenu_settings() {
		return moduleProvider.get().getText("inemenu_settings");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Loading data...
	* <u><i>Magyarul:</i></u> Adatok betöltése...
	*/
	public static String loading() {
		return moduleProvider.get().getText("loading");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> m
	* <u><i>Magyarul:</i></u> p
	*/
	public static String minShort() {
		return moduleProvider.get().getText("minShort");
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} mins ago
	* <u><i>Magyarul:</i></u> {number} perce
	*/
	public static String minsAgo(String number) {
		return moduleProvider.get().getText("minsAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one day ago
	* <u><i>Magyarul:</i></u> egy napja
	*/
	public static String oneDayAgo() {
		return moduleProvider.get().getText("oneDayAgo");
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one hour ago
	* <u><i>Magyarul:</i></u> egy órája
	*/
	public static String oneHourAgo() {
		return moduleProvider.get().getText("oneHourAgo");
	}
	
	/**
	* <u><i>Description:</i></u> in singular, must be short <br />
	* <u><i>In English:</i></u> one min ago
	* <u><i>Magyarul:</i></u> egy perce
	*/
	public static String oneMinAgo() {
		return moduleProvider.get().getText("oneMinAgo");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Connected
	* <u><i>Magyarul:</i></u> Csatlakozva
	*/
	public static String reconnected() {
		return moduleProvider.get().getText("reconnected");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> No network connection. We will try to reconnect in a few seconds.
	* <u><i>Magyarul:</i></u> Nincs kapcsolat. Néhány másodperc múlva újrapróbálkozunk.
	*/
	public static String reconnection() {
		return moduleProvider.get().getText("reconnection");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error occurred during save: <br /> {error}
	* <u><i>Magyarul:</i></u> A mentés során az alábbi hiba történt: <br /> {error}
	*/
	public static String saveError(String error) {
		return moduleProvider.get().getText("saveError").replace("{error}", error);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Save successful.
	* <u><i>Magyarul:</i></u> Sikeresen elmentve
	*/
	public static String saveSuccessful() {
		return moduleProvider.get().getText("saveSuccessful");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Unknown error occurred during save!
	* <u><i>Magyarul:</i></u> A mentés során ismeretlen hiba történt.
	*/
	public static String saveUnknownError() {
		return moduleProvider.get().getText("saveUnknownError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Saving of {savedThing} in progress
	* <u><i>Magyarul:</i></u> A(z) {savedThing} mentése folyamatban
	*/
	public static String savingInProgress(String savedThing) {
		return moduleProvider.get().getText("savingInProgress").replace("{savedThing}", savedThing);
	}
	
	/**
	* <u><i>Description:</i></u> in plural, must be short <br />
	* <u><i>In English:</i></u> {number} secs ago
	* <u><i>Magyarul:</i></u> {number} másodperce
	*/
	public static String secSAgo(String number) {
		return moduleProvider.get().getText("secSAgo").replace("{number}", number);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> s
	* <u><i>Magyarul:</i></u> mp
	*/
	public static String secShort() {
		return moduleProvider.get().getText("secShort");
	}
}
