package com.inepex.ineForm.client.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class IneFormI18n extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "IneFormI18n";
	
	static I18nModuleProvider<IneFormI18n> moduleProvider;
	
	public IneFormI18n() {
		super(MODULE_NAME);
	}
		
	public IneFormI18n(I18nModuleProvider<IneFormI18n> moduleProvider) {
		super(MODULE_NAME);
		IneFormI18n.moduleProvider = moduleProvider;
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}
	
	public void init(){
		textMap.put("ADD", "+");
		textMap.put("CANCEL", "Cancel");
		textMap.put("CSVEXPORT", "Export ");
		textMap.put("DELETE", "Delete");
		textMap.put("DESELECT", "Deselect");
		textMap.put("DESELECTALL", "Deselect all");
		textMap.put("DOWN", "Down");
		textMap.put("EDIT", "Edit");
		textMap.put("ERR_CouldNotRenderWidget", "Could not render widget");
		textMap.put("FILTER", "Filter");
		textMap.put("FINISH", "Finish");
		textMap.put("MOVEDOWN", "Down");
		textMap.put("MOVEUP", "Up");
		textMap.put("NEW", "New");
		textMap.put("NEXT", "Next page");
		textMap.put("ODFieldType_BOOLEAN", "Checkbox");
		textMap.put("ODFieldType_DOUBLE", "Fraction");
		textMap.put("ODFieldType_EMAIL", "E-mail");
		textMap.put("ODFieldType_LONG", "Number");
		textMap.put("ODFieldType_STRING", "Text");
		textMap.put("OK", "Ok");
		textMap.put("PREVIOUS", "Previous page");
		textMap.put("REMOVE", "-");
		textMap.put("RESET", "Reset");
		textMap.put("SAVE", "Save");
		textMap.put("SEARCH", "Search");
		textMap.put("SELECT", "Select");
		textMap.put("SELECTALL", "Select all");
		textMap.put("UP", "Up");
		textMap.put("cantDisplay", "can't display");
		textMap.put("change", "Change");
		textMap.put("csvComment", "Click here to download:");
		textMap.put("csvDownload", "Download");
		textMap.put("csvError", "Something went wrong. See server log!");
		textMap.put("csvInvalid", "Invalid request. Try to refresh!");
		textMap.put("custKVOValidateDot", "Name should not contain dot!");
		textMap.put("custKVOValidateDuplicate", "Duplicated name!");
		textMap.put("custKVOValidateEmpty", "Empty name!");
		textMap.put("custKVOValidateParse", "Value can not be parsed!");
		textMap.put("custKVOValidateSet", "Type must be set!");
		textMap.put("customKVO_key", "Name");
		textMap.put("customKVO_type", "Type");
		textMap.put("customKVO_value", "Value");
		textMap.put("day_friday", "Friday");
		textMap.put("day_monday", "Monday");
		textMap.put("day_saturday", "Saturday");
		textMap.put("day_sunday", "Sunday");
		textMap.put("day_thursday", "Thursday");
		textMap.put("day_tuesday", "Tuesday");
		textMap.put("day_wednesday", "Wednesday");
		textMap.put("dialogEditTitle", "Edit entry");
		textMap.put("dialogNewTitle", "New entry");
		textMap.put("falseText", "false");
		textMap.put("hours", "hours");
		textMap.put("imagefinderChoosefromgoogle", "Choose images from Google image search");
		textMap.put("imagefinderDowloading", "Downloading...");
		textMap.put("imagefinderFailed", "Failed to download...");
		textMap.put("imagefinderNoimage", "No file");
		textMap.put("imagefinderNoresult", "0 image found");
		textMap.put("imagefinderPage", "Page");
		textMap.put("imagefinderSuccess", "Downloaded");
		textMap.put("imagefinderUploadimage", "Upload");
		textMap.put("imagefinderUse", "Use");
		textMap.put("imageuploadBtn", "Upload");
		textMap.put("imageuploadBtn_change", "Change");
		textMap.put("imageuploadError", "Error during upload. See server log!");
		textMap.put("imageuploadInvalidFileFormat", "Invalid file format.");
		textMap.put("imageuploadTitle", "Upload");
		textMap.put("inedate_notset", "Not set");
		textMap.put("inetable_noresult", "No result found");
		textMap.put("infoDialogTitle", "Message");
		textMap.put("loading", "Loading data...");
		textMap.put("minutes", "minutes");
		textMap.put("month_april", "April");
		textMap.put("month_august", "August");
		textMap.put("month_december", "December");
		textMap.put("month_february", "Februar");
		textMap.put("month_january", "Januar");
		textMap.put("month_july", "July");
		textMap.put("month_june", "June");
		textMap.put("month_march", "March");
		textMap.put("month_may", "May");
		textMap.put("month_november", "November");
		textMap.put("month_october", "October");
		textMap.put("month_september", "September");
		textMap.put("nd", "nd");
		textMap.put("rd", "rd");
		textMap.put("reallyWantToDelete", "Do you really want to delete the selected element?");
		textMap.put("restRequestError", "Request error");
		textMap.put("searchForm_filtered", "Result is filtered!");
		textMap.put("shortday_friday", "F");
		textMap.put("shortday_monday", "M");
		textMap.put("shortday_saturday", "Sa");
		textMap.put("shortday_sunday", "Su");
		textMap.put("shortday_thursday", "Th");
		textMap.put("shortday_tuesday", "Tu");
		textMap.put("shortday_wednesday", "W");
		textMap.put("st", "st");
		textMap.put("th", "th");
		textMap.put("trueText", "true");
		textMap.put("validationEmail", "This is not valid e-mail address!");
		textMap.put("validationFieldError", "Field error");
		textMap.put("validationGeneralError", "Error:");
		textMap.put("validationNothingToSave", "Nothing to save");
		textMap.put("validatorEQ", "The number entered into field {fieldName} must be equal to {constvalAsString}!");
		textMap.put("validatorGE", "The number entered into field {fieldName} must be greater then or equal to {constvalAsString}!");
		textMap.put("validatorGT", "The number entered into field {fieldName} must be greater then {constvalAsString}!");
		textMap.put("validatorLE", "The number entered into field {fieldName} must be less then or equal to {constvalAsString}!");
		textMap.put("validatorLT", "he number entered into field {fieldName} must be less then {constvalAsString}!");
		textMap.put("validatorLength", "Maximum {maxLength} characters can be entered!");
		textMap.put("validatorShouldAfter", "This date should be after {otherFieldsName}!");
		textMap.put("validatorShouldBefore", "This date should be before {otherFieldsName}!");
		textMap.put("validatorUniqueRelList", "The {i}th and the {j}th items are equal.");
		textMap.put("validator_mandatory", "This field is required!");
	}

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> +
	* <u><i>Magyarul:</i></u> +
	*/
	public static String ADD() {
		return moduleProvider.get().getTextMap().get("ADD");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cancel
	* <u><i>Magyarul:</i></u> Mégse
	*/
	public static String CANCEL() {
		return moduleProvider.get().getTextMap().get("CANCEL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Export 
	* <u><i>Magyarul:</i></u> Excel export
	*/
	public static String CSVEXPORT() {
		return moduleProvider.get().getTextMap().get("CSVEXPORT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Delete
	* <u><i>Magyarul:</i></u> Töröl
	*/
	public static String DELETE() {
		return moduleProvider.get().getTextMap().get("DELETE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Deselect
	* <u><i>Magyarul:</i></u> Eltávolít
	*/
	public static String DESELECT() {
		return moduleProvider.get().getTextMap().get("DESELECT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Deselect all
	* <u><i>Magyarul:</i></u> Mindet eltávolít
	*/
	public static String DESELECTALL() {
		return moduleProvider.get().getTextMap().get("DESELECTALL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Down
	* <u><i>Magyarul:</i></u> Le
	*/
	public static String DOWN() {
		return moduleProvider.get().getTextMap().get("DOWN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Edit
	* <u><i>Magyarul:</i></u> Módosít
	*/
	public static String EDIT() {
		return moduleProvider.get().getTextMap().get("EDIT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Could not render widget
	* <u><i>Magyarul:</i></u> Hiba, nem lehet megjeleníteni a mezőt
	*/
	public static String ERR_CouldNotRenderWidget() {
		return moduleProvider.get().getTextMap().get("ERR_CouldNotRenderWidget");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Filter
	* <u><i>Magyarul:</i></u> Szűrés
	*/
	public static String FILTER() {
		return moduleProvider.get().getTextMap().get("FILTER");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Finish
	* <u><i>Magyarul:</i></u> Befejez
	*/
	public static String FINISH() {
		return moduleProvider.get().getTextMap().get("FINISH");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Down
	* <u><i>Magyarul:</i></u> Le
	*/
	public static String MOVEDOWN() {
		return moduleProvider.get().getTextMap().get("MOVEDOWN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Up
	* <u><i>Magyarul:</i></u> Fel
	*/
	public static String MOVEUP() {
		return moduleProvider.get().getTextMap().get("MOVEUP");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New
	* <u><i>Magyarul:</i></u> Új
	*/
	public static String NEW() {
		return moduleProvider.get().getTextMap().get("NEW");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Next page
	* <u><i>Magyarul:</i></u> Következő oldal
	*/
	public static String NEXT() {
		return moduleProvider.get().getTextMap().get("NEXT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Checkbox
	* <u><i>Magyarul:</i></u> Igaz-Hamis
	*/
	public static String ODFieldType_BOOLEAN() {
		return moduleProvider.get().getTextMap().get("ODFieldType_BOOLEAN");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Fraction
	* <u><i>Magyarul:</i></u> Tört szám
	*/
	public static String ODFieldType_DOUBLE() {
		return moduleProvider.get().getTextMap().get("ODFieldType_DOUBLE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> E-mail
	* <u><i>Magyarul:</i></u> E-mail
	*/
	public static String ODFieldType_EMAIL() {
		return moduleProvider.get().getTextMap().get("ODFieldType_EMAIL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Number
	* <u><i>Magyarul:</i></u> Szám
	*/
	public static String ODFieldType_LONG() {
		return moduleProvider.get().getTextMap().get("ODFieldType_LONG");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Text
	* <u><i>Magyarul:</i></u> Szöveg
	*/
	public static String ODFieldType_STRING() {
		return moduleProvider.get().getTextMap().get("ODFieldType_STRING");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String OK() {
		return moduleProvider.get().getTextMap().get("OK");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Previous page
	* <u><i>Magyarul:</i></u> Előző oldal
	*/
	public static String PREVIOUS() {
		return moduleProvider.get().getTextMap().get("PREVIOUS");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> -
	* <u><i>Magyarul:</i></u> -
	*/
	public static String REMOVE() {
		return moduleProvider.get().getTextMap().get("REMOVE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Reset
	* <u><i>Magyarul:</i></u> Visszaállít
	*/
	public static String RESET() {
		return moduleProvider.get().getTextMap().get("RESET");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Save
	* <u><i>Magyarul:</i></u> Mentés
	*/
	public static String SAVE() {
		return moduleProvider.get().getTextMap().get("SAVE");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Search
	* <u><i>Magyarul:</i></u> Keres
	*/
	public static String SEARCH() {
		return moduleProvider.get().getTextMap().get("SEARCH");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Select
	* <u><i>Magyarul:</i></u> Kiválaszt
	*/
	public static String SELECT() {
		return moduleProvider.get().getTextMap().get("SELECT");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Select all
	* <u><i>Magyarul:</i></u> Mindet kiválaszt
	*/
	public static String SELECTALL() {
		return moduleProvider.get().getTextMap().get("SELECTALL");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Up
	* <u><i>Magyarul:</i></u> Fel
	*/
	public static String UP() {
		return moduleProvider.get().getTextMap().get("UP");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> can't display
	* <u><i>Magyarul:</i></u> nem megjeleníthető
	*/
	public static String cantDisplay() {
		return moduleProvider.get().getTextMap().get("cantDisplay");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Change
	* <u><i>Magyarul:</i></u> Megváltoztat
	*/
	public static String change() {
		return moduleProvider.get().getTextMap().get("change");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Click here to download:
	* <u><i>Magyarul:</i></u> Az adatok letölthetőek: 
	*/
	public static String csvComment() {
		return moduleProvider.get().getTextMap().get("csvComment");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Download
	* <u><i>Magyarul:</i></u> Letöltés
	*/
	public static String csvDownload() {
		return moduleProvider.get().getTextMap().get("csvDownload");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Something went wrong. See server log!
	* <u><i>Magyarul:</i></u> Hiba történt, 
	*/
	public static String csvError() {
		return moduleProvider.get().getTextMap().get("csvError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Invalid request. Try to refresh!
	* <u><i>Magyarul:</i></u> Hibás kérés, próbálja meg frissíteni az oldalt!
	*/
	public static String csvInvalid() {
		return moduleProvider.get().getTextMap().get("csvInvalid");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name should not contain dot!
	* <u><i>Magyarul:</i></u> A név ne tartalmazzon pontot!
	*/
	public static String custKVOValidateDot() {
		return moduleProvider.get().getTextMap().get("custKVOValidateDot");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Duplicated name!
	* <u><i>Magyarul:</i></u> Egy név csak egyszer használható
	*/
	public static String custKVOValidateDuplicate() {
		return moduleProvider.get().getTextMap().get("custKVOValidateDuplicate");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Empty name!
	* <u><i>Magyarul:</i></u> A név mezőt ki kell tölteni!
	*/
	public static String custKVOValidateEmpty() {
		return moduleProvider.get().getTextMap().get("custKVOValidateEmpty");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Value can not be parsed!
	* <u><i>Magyarul:</i></u> Az érték mező nem megfelelő formátumú!
	*/
	public static String custKVOValidateParse() {
		return moduleProvider.get().getTextMap().get("custKVOValidateParse");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Type must be set!
	* <u><i>Magyarul:</i></u> A mező típusát be kell állítani!
	*/
	public static String custKVOValidateSet() {
		return moduleProvider.get().getTextMap().get("custKVOValidateSet");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> Név
	*/
	public static String customKVO_key() {
		return moduleProvider.get().getTextMap().get("customKVO_key");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Type
	* <u><i>Magyarul:</i></u> Típus
	*/
	public static String customKVO_type() {
		return moduleProvider.get().getTextMap().get("customKVO_type");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Value
	* <u><i>Magyarul:</i></u> Érték
	*/
	public static String customKVO_value() {
		return moduleProvider.get().getTextMap().get("customKVO_value");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Friday
	* <u><i>Magyarul:</i></u> péntek
	*/
	public static String day_friday() {
		return moduleProvider.get().getTextMap().get("day_friday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Monday
	* <u><i>Magyarul:</i></u> hétfő
	*/
	public static String day_monday() {
		return moduleProvider.get().getTextMap().get("day_monday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Saturday
	* <u><i>Magyarul:</i></u> szombat
	*/
	public static String day_saturday() {
		return moduleProvider.get().getTextMap().get("day_saturday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Sunday
	* <u><i>Magyarul:</i></u> vasárnap
	*/
	public static String day_sunday() {
		return moduleProvider.get().getTextMap().get("day_sunday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Thursday
	* <u><i>Magyarul:</i></u> csütörtök
	*/
	public static String day_thursday() {
		return moduleProvider.get().getTextMap().get("day_thursday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tuesday
	* <u><i>Magyarul:</i></u> kedd
	*/
	public static String day_tuesday() {
		return moduleProvider.get().getTextMap().get("day_tuesday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Wednesday
	* <u><i>Magyarul:</i></u> szerda
	*/
	public static String day_wednesday() {
		return moduleProvider.get().getTextMap().get("day_wednesday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Edit entry
	* <u><i>Magyarul:</i></u> Sor módosítása
	*/
	public static String dialogEditTitle() {
		return moduleProvider.get().getTextMap().get("dialogEditTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New entry
	* <u><i>Magyarul:</i></u> Új sor
	*/
	public static String dialogNewTitle() {
		return moduleProvider.get().getTextMap().get("dialogNewTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> false
	* <u><i>Magyarul:</i></u> nem
	*/
	public static String falseText() {
		return moduleProvider.get().getTextMap().get("falseText");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> hours
	* <u><i>Magyarul:</i></u> óra
	*/
	public static String hours() {
		return moduleProvider.get().getTextMap().get("hours");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Choose images from Google image search
	* <u><i>Magyarul:</i></u> Kép választása a Google képkereső segítségével
	*/
	public static String imagefinderChoosefromgoogle() {
		return moduleProvider.get().getTextMap().get("imagefinderChoosefromgoogle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Downloading...
	* <u><i>Magyarul:</i></u> Letöltés folyamatban...
	*/
	public static String imagefinderDowloading() {
		return moduleProvider.get().getTextMap().get("imagefinderDowloading");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Failed to download...
	* <u><i>Magyarul:</i></u> Hiba a letöltés során
	*/
	public static String imagefinderFailed() {
		return moduleProvider.get().getTextMap().get("imagefinderFailed");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> No file
	* <u><i>Magyarul:</i></u> nincs fájl
	*/
	public static String imagefinderNoimage() {
		return moduleProvider.get().getTextMap().get("imagefinderNoimage");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> 0 image found
	* <u><i>Magyarul:</i></u> 0 találat
	*/
	public static String imagefinderNoresult() {
		return moduleProvider.get().getTextMap().get("imagefinderNoresult");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Page
	* <u><i>Magyarul:</i></u> Oldal
	*/
	public static String imagefinderPage() {
		return moduleProvider.get().getTextMap().get("imagefinderPage");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Downloaded
	* <u><i>Magyarul:</i></u> Letöltve
	*/
	public static String imagefinderSuccess() {
		return moduleProvider.get().getTextMap().get("imagefinderSuccess");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Upload
	* <u><i>Magyarul:</i></u> Válassza ki a feltöltendő fájlt
	*/
	public static String imagefinderUploadimage() {
		return moduleProvider.get().getTextMap().get("imagefinderUploadimage");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Use
	* <u><i>Magyarul:</i></u> Kiválaszt
	*/
	public static String imagefinderUse() {
		return moduleProvider.get().getTextMap().get("imagefinderUse");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Upload
	* <u><i>Magyarul:</i></u> Feltöltés
	*/
	public static String imageuploadBtn() {
		return moduleProvider.get().getTextMap().get("imageuploadBtn");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Change
	* <u><i>Magyarul:</i></u> Csere
	*/
	public static String imageuploadBtn_change() {
		return moduleProvider.get().getTextMap().get("imageuploadBtn_change");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error during upload. See server log!
	* <u><i>Magyarul:</i></u> Hiba a feltöltés során. Ellenőrizze a szerver beállításait.
	*/
	public static String imageuploadError() {
		return moduleProvider.get().getTextMap().get("imageuploadError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Invalid file format.
	* <u><i>Magyarul:</i></u> Nem megfelelő fájlformátum
	*/
	public static String imageuploadInvalidFileFormat() {
		return moduleProvider.get().getTextMap().get("imageuploadInvalidFileFormat");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Upload
	* <u><i>Magyarul:</i></u> Feltöltés
	*/
	public static String imageuploadTitle() {
		return moduleProvider.get().getTextMap().get("imageuploadTitle");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> Not set
	* <u><i>Magyarul:</i></u> Nincs megadva
	*/
	public static String inedate_notset() {
		return moduleProvider.get().getTextMap().get("inedate_notset");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> No result found
	* <u><i>Magyarul:</i></u> Nincs találat
	*/
	public static String inetable_noresult() {
		return moduleProvider.get().getTextMap().get("inetable_noresult");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Message
	* <u><i>Magyarul:</i></u> Üzenet
	*/
	public static String infoDialogTitle() {
		return moduleProvider.get().getTextMap().get("infoDialogTitle");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Loading data...
	* <u><i>Magyarul:</i></u> Adatok betöltése...
	*/
	public static String loading() {
		return moduleProvider.get().getTextMap().get("loading");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> minutes
	* <u><i>Magyarul:</i></u> perc
	*/
	public static String minutes() {
		return moduleProvider.get().getTextMap().get("minutes");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> April
	* <u><i>Magyarul:</i></u> Április
	*/
	public static String month_april() {
		return moduleProvider.get().getTextMap().get("month_april");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> August
	* <u><i>Magyarul:</i></u> Augusztus
	*/
	public static String month_august() {
		return moduleProvider.get().getTextMap().get("month_august");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> December
	* <u><i>Magyarul:</i></u> December
	*/
	public static String month_december() {
		return moduleProvider.get().getTextMap().get("month_december");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Februar
	* <u><i>Magyarul:</i></u> Február
	*/
	public static String month_february() {
		return moduleProvider.get().getTextMap().get("month_february");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Januar
	* <u><i>Magyarul:</i></u> Január
	*/
	public static String month_january() {
		return moduleProvider.get().getTextMap().get("month_january");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> July
	* <u><i>Magyarul:</i></u> Július
	*/
	public static String month_july() {
		return moduleProvider.get().getTextMap().get("month_july");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> June
	* <u><i>Magyarul:</i></u> Június
	*/
	public static String month_june() {
		return moduleProvider.get().getTextMap().get("month_june");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> March
	* <u><i>Magyarul:</i></u> Március
	*/
	public static String month_march() {
		return moduleProvider.get().getTextMap().get("month_march");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> May
	* <u><i>Magyarul:</i></u> Május
	*/
	public static String month_may() {
		return moduleProvider.get().getTextMap().get("month_may");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> November
	* <u><i>Magyarul:</i></u> November
	*/
	public static String month_november() {
		return moduleProvider.get().getTextMap().get("month_november");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> October
	* <u><i>Magyarul:</i></u> Október
	*/
	public static String month_october() {
		return moduleProvider.get().getTextMap().get("month_october");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> September
	* <u><i>Magyarul:</i></u> Szeptember
	*/
	public static String month_september() {
		return moduleProvider.get().getTextMap().get("month_september");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> nd
	* <u><i>Magyarul:</i></u>  
	*/
	public static String nd() {
		return moduleProvider.get().getTextMap().get("nd");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> rd
	* <u><i>Magyarul:</i></u>   
	*/
	public static String rd() {
		return moduleProvider.get().getTextMap().get("rd");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Do you really want to delete the selected element?
	* <u><i>Magyarul:</i></u> Biztosan törölni kívánja a kiválaszott sort?
	*/
	public static String reallyWantToDelete() {
		return moduleProvider.get().getTextMap().get("reallyWantToDelete");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Request error
	* <u><i>Magyarul:</i></u> Hiba a kérés során
	*/
	public static String restRequestError() {
		return moduleProvider.get().getTextMap().get("restRequestError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Result is filtered!
	* <u><i>Magyarul:</i></u> Az eredmények szűrve vannak.
	*/
	public static String searchForm_filtered() {
		return moduleProvider.get().getTextMap().get("searchForm_filtered");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> F
	* <u><i>Magyarul:</i></u> P
	*/
	public static String shortday_friday() {
		return moduleProvider.get().getTextMap().get("shortday_friday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> M
	* <u><i>Magyarul:</i></u> H
	*/
	public static String shortday_monday() {
		return moduleProvider.get().getTextMap().get("shortday_monday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Sa
	* <u><i>Magyarul:</i></u> Szo
	*/
	public static String shortday_saturday() {
		return moduleProvider.get().getTextMap().get("shortday_saturday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Su
	* <u><i>Magyarul:</i></u> V
	*/
	public static String shortday_sunday() {
		return moduleProvider.get().getTextMap().get("shortday_sunday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Th
	* <u><i>Magyarul:</i></u> Cs
	*/
	public static String shortday_thursday() {
		return moduleProvider.get().getTextMap().get("shortday_thursday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tu
	* <u><i>Magyarul:</i></u> K
	*/
	public static String shortday_tuesday() {
		return moduleProvider.get().getTextMap().get("shortday_tuesday");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> W
	* <u><i>Magyarul:</i></u> Sze
	*/
	public static String shortday_wednesday() {
		return moduleProvider.get().getTextMap().get("shortday_wednesday");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> st
	* <u><i>Magyarul:</i></u>  
	*/
	public static String st() {
		return moduleProvider.get().getTextMap().get("st");
	}
	
	/**
	* <u><i>Description:</i></u> $loc.getDescription() <br />
	* <u><i>In English:</i></u> th
	* <u><i>Magyarul:</i></u>  
	*/
	public static String th() {
		return moduleProvider.get().getTextMap().get("th");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> true
	* <u><i>Magyarul:</i></u> igen
	*/
	public static String trueText() {
		return moduleProvider.get().getTextMap().get("trueText");
	}
	
	/**
	* <u><i>Description:</i></u>   <br />
	* <u><i>In English:</i></u> This is not valid e-mail address!
	* <u><i>Magyarul:</i></u> Nem megfelelő az e-mail cím!
	*/
	public static String validationEmail() {
		return moduleProvider.get().getTextMap().get("validationEmail");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Field error
	* <u><i>Magyarul:</i></u> Hibás mező - 
	*/
	public static String validationFieldError() {
		return moduleProvider.get().getTextMap().get("validationFieldError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error:
	* <u><i>Magyarul:</i></u> Hiba: 
	*/
	public static String validationGeneralError() {
		return moduleProvider.get().getTextMap().get("validationGeneralError");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Nothing to save
	* <u><i>Magyarul:</i></u> Nincs változás a formon
	*/
	public static String validationNothingToSave() {
		return moduleProvider.get().getTextMap().get("validationNothingToSave");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be equal to {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt szám értéke {constvalAsString} kell, hogy legyen!
	*/
	public static String validatorEQ(String constvalAsString, String fieldName) {
		return moduleProvider.get().getTextMap().get("validatorEQ").replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be greater then or equal to {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak nagyobb-egyenlőnek kell lennie, mint {constvalAsString}!
	*/
	public static String validatorGE(String constvalAsString, String fieldName) {
		return moduleProvider.get().getTextMap().get("validatorGE").replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be greater then {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak nagyobbnak kell lennie, mint {constvalAsString}!
	*/
	public static String validatorGT(String constvalAsString, String fieldName) {
		return moduleProvider.get().getTextMap().get("validatorGT").replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be less then or equal to {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak kisebb-egyenlőnek kell lennie, mint {constvalAsString}!
	*/
	public static String validatorLE(String constvalAsString, String fieldName) {
		return moduleProvider.get().getTextMap().get("validatorLE").replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> he number entered into field {fieldName} must be less then {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak kisebbnek kell lennie, mint {constvalAsString}!
	*/
	public static String validatorLT(String constvalAsString, String fieldName) {
		return moduleProvider.get().getTextMap().get("validatorLT").replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Maximum {maxLength} characters can be entered!
	* <u><i>Magyarul:</i></u> Legfeljebb {maxLength} karaktert lehet írni a mezőbe!
	*/
	public static String validatorLength(String maxLength) {
		return moduleProvider.get().getTextMap().get("validatorLength").replace("{maxLength}", maxLength);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This date should be after {otherFieldsName}!
	* <u><i>Magyarul:</i></u> A dámum {otherFieldsName} után kell, hogy legyen!
	*/
	public static String validatorShouldAfter(String otherFieldsName) {
		return moduleProvider.get().getTextMap().get("validatorShouldAfter").replace("{otherFieldsName}", otherFieldsName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This date should be before {otherFieldsName}!
	* <u><i>Magyarul:</i></u> A dámum {otherFieldsName} előtt kell, hogy legyen!
	*/
	public static String validatorShouldBefore(String otherFieldsName) {
		return moduleProvider.get().getTextMap().get("validatorShouldBefore").replace("{otherFieldsName}", otherFieldsName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The {i}th and the {j}th items are equal.
	* <u><i>Magyarul:</i></u> A listában a {i}. és a {j}. elem megegyezik.
	*/
	public static String validatorUniqueRelList(String i, String j) {
		return moduleProvider.get().getTextMap().get("validatorUniqueRelList").replace("{i}", i).replace("{j}", j);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This field is required!
	* <u><i>Magyarul:</i></u> A mező kitöltése kötelező!
	*/
	public static String validator_mandatory() {
		return moduleProvider.get().getTextMap().get("validator_mandatory");
	}
}
