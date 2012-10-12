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
	
	public String ADD = "+";
	public String CANCEL = "Cancel";
	public String CSVEXPORT = "Export ";
	public String DELETE = "Delete";
	public String DESELECT = "Deselect";
	public String DESELECTALL = "Deselect all";
	public String DOWN = "Down";
	public String EDIT = "Edit";
	public String ERR_CouldNotRenderWidget = "Could not render widget";
	public String FILTER = "Filter";
	public String FINISH = "Finish";
	public String MOVEDOWN = "Down";
	public String MOVEUP = "Up";
	public String NEW = "New";
	public String NEXT = "Next page";
	public String ODFieldType_BOOLEAN = "Checkbox";
	public String ODFieldType_DOUBLE = "Fraction";
	public String ODFieldType_EMAIL = "E-mail";
	public String ODFieldType_LONG = "Number";
	public String ODFieldType_STRING = "Text";
	public String OK = "Ok";
	public String PREVIOUS = "Previous page";
	public String REMOVE = "-";
	public String RESET = "Reset";
	public String SAVE = "Save";
	public String SEARCH = "Search";
	public String SELECT = "Select";
	public String SELECTALL = "Select all";
	public String UP = "Up";
	public String cantDisplay = "can't display";
	public String change = "Change";
	public String csvComment = "Click here to download:";
	public String csvDownload = "Download";
	public String csvError = "Something went wrong. See server log!";
	public String csvInvalid = "Invalid request. Try to refresh!";
	public String custKVOValidateDuplicate = "Duplicated key!";
	public String custKVOValidateEmpty = "Empty key!";
	public String custKVOValidateParse = "Value can not be parsed!";
	public String custKVOValidateSet = "Type must be set!";
	public String customKVO_key = "Name";
	public String customKVO_type = "Type";
	public String customKVO_value = "Value";
	public String day_friday = "Friday";
	public String day_monday = "Monday";
	public String day_saturday = "Saturday";
	public String day_sunday = "Sunday";
	public String day_thursday = "Thursday";
	public String day_tuesday = "Tuesday";
	public String day_wednesday = "Wednesday";
	public String dialogEditTitle = "Edit entry";
	public String dialogNewTitle = "New entry";
	public String falseText = "false";
	public String hours = "hours";
	public String imagefinderChoosefromgoogle = "Choose images from Google image search";
	public String imagefinderDowloading = "Downloading...";
	public String imagefinderFailed = "Failed to download...";
	public String imagefinderNoimage = "No file";
	public String imagefinderNoresult = "0 image found";
	public String imagefinderPage = "Page";
	public String imagefinderSuccess = "Downloaded";
	public String imagefinderUploadimage = "Upload";
	public String imagefinderUse = "Use";
	public String imageuploadBtn = "Upload";
	public String imageuploadBtn_change = "Change";
	public String imageuploadError = "Error during upload. See server log!";
	public String imageuploadInvalidFileFormat = "Invalid file format.";
	public String imageuploadTitle = "Upload";
	public String infoDialogTitle = "Message";
	public String loading = "Loading data...";
	public String minutes = "minutes";
	public String month_april = "April";
	public String month_august = "August";
	public String month_december = "December";
	public String month_february = "Februar";
	public String month_january = "Januar";
	public String month_july = "July";
	public String month_june = "June";
	public String month_march = "March";
	public String month_may = "May";
	public String month_november = "November";
	public String month_october = "October";
	public String month_september = "September";
	public String reallyWantToDelete = "Do you really want to delete the selected element?";
	public String restRequestError = "Request error";
	public String searchForm_filtered = "Result is filtered!";
	public String shortday_friday = "F";
	public String shortday_monday = "M";
	public String shortday_saturday = "Sa";
	public String shortday_sunday = "Su";
	public String shortday_thursday = "Th";
	public String shortday_tuesday = "Tu";
	public String shortday_wednesday = "W";
	public String trueText = "true";
	public String validationEmail = "This is not valid e-mail address!";
	public String validationFieldError = "Field error";
	public String validationGeneralError = "Error:";
	public String validationNothingToSave = "Nothing to save";
	public String validatorEQ = "The number entered into field {fieldName} must be equal to {constvalAsString}!";
	public String validatorGE = "The number entered into field {fieldName} must be greater then or equal to {constvalAsString}!";
	public String validatorGT = "The number entered into field {fieldName} must be greater then {constvalAsString}!";
	public String validatorLE = "The number entered into field {fieldName} must be less then or equal to {constvalAsString}!";
	public String validatorLT = "he number entered into field {fieldName} must be less then {constvalAsString}!";
	public String validatorLength = "Maximum {maxLength} characters can be entered!";
	public String validatorShouldAfter = "This date should be after {otherFieldsName}!";
	public String validatorShouldBefore = "This date should be before {otherFieldsName}!";
	public String validatorUniqueRelList = "The {i}th ant the {j}th items are equal.";
	public String validator_mandatory = "This field is required!";

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> +
	* <u><i>Magyarul:</i></u> +
	*/
	public static String ADD() {
		return moduleProvider.get().ADD;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Cancel
	* <u><i>Magyarul:</i></u> Mégse
	*/
	public static String CANCEL() {
		return moduleProvider.get().CANCEL;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Export 
	* <u><i>Magyarul:</i></u> Excel export
	*/
	public static String CSVEXPORT() {
		return moduleProvider.get().CSVEXPORT;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Delete
	* <u><i>Magyarul:</i></u> Töröl
	*/
	public static String DELETE() {
		return moduleProvider.get().DELETE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Deselect
	* <u><i>Magyarul:</i></u> Eltávolít
	*/
	public static String DESELECT() {
		return moduleProvider.get().DESELECT;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Deselect all
	* <u><i>Magyarul:</i></u> Mindet eltávolít
	*/
	public static String DESELECTALL() {
		return moduleProvider.get().DESELECTALL;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Down
	* <u><i>Magyarul:</i></u> Le
	*/
	public static String DOWN() {
		return moduleProvider.get().DOWN;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Edit
	* <u><i>Magyarul:</i></u> Módosít
	*/
	public static String EDIT() {
		return moduleProvider.get().EDIT;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Could not render widget
	* <u><i>Magyarul:</i></u> Hiba, nem lehet megjeleníteni a mezőt
	*/
	public static String ERR_CouldNotRenderWidget() {
		return moduleProvider.get().ERR_CouldNotRenderWidget;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Filter
	* <u><i>Magyarul:</i></u> Szűrés
	*/
	public static String FILTER() {
		return moduleProvider.get().FILTER;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Finish
	* <u><i>Magyarul:</i></u> Befejez
	*/
	public static String FINISH() {
		return moduleProvider.get().FINISH;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Down
	* <u><i>Magyarul:</i></u> Le
	*/
	public static String MOVEDOWN() {
		return moduleProvider.get().MOVEDOWN;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Up
	* <u><i>Magyarul:</i></u> Fel
	*/
	public static String MOVEUP() {
		return moduleProvider.get().MOVEUP;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New
	* <u><i>Magyarul:</i></u> Új
	*/
	public static String NEW() {
		return moduleProvider.get().NEW;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Next page
	* <u><i>Magyarul:</i></u> Következő oldal
	*/
	public static String NEXT() {
		return moduleProvider.get().NEXT;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Checkbox
	* <u><i>Magyarul:</i></u> Igaz-Hamis
	*/
	public static String ODFieldType_BOOLEAN() {
		return moduleProvider.get().ODFieldType_BOOLEAN;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Fraction
	* <u><i>Magyarul:</i></u> Tört szám
	*/
	public static String ODFieldType_DOUBLE() {
		return moduleProvider.get().ODFieldType_DOUBLE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> E-mail
	* <u><i>Magyarul:</i></u> E-mail
	*/
	public static String ODFieldType_EMAIL() {
		return moduleProvider.get().ODFieldType_EMAIL;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Number
	* <u><i>Magyarul:</i></u> Szám
	*/
	public static String ODFieldType_LONG() {
		return moduleProvider.get().ODFieldType_LONG;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Text
	* <u><i>Magyarul:</i></u> Szöveg
	*/
	public static String ODFieldType_STRING() {
		return moduleProvider.get().ODFieldType_STRING;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Ok
	* <u><i>Magyarul:</i></u> Ok
	*/
	public static String OK() {
		return moduleProvider.get().OK;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Previous page
	* <u><i>Magyarul:</i></u> Előző oldal
	*/
	public static String PREVIOUS() {
		return moduleProvider.get().PREVIOUS;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> -
	* <u><i>Magyarul:</i></u> -
	*/
	public static String REMOVE() {
		return moduleProvider.get().REMOVE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Reset
	* <u><i>Magyarul:</i></u> Visszaállít
	*/
	public static String RESET() {
		return moduleProvider.get().RESET;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Save
	* <u><i>Magyarul:</i></u> Mentés
	*/
	public static String SAVE() {
		return moduleProvider.get().SAVE;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Search
	* <u><i>Magyarul:</i></u> Keres
	*/
	public static String SEARCH() {
		return moduleProvider.get().SEARCH;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Select
	* <u><i>Magyarul:</i></u> Kiválaszt
	*/
	public static String SELECT() {
		return moduleProvider.get().SELECT;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Select all
	* <u><i>Magyarul:</i></u> Mindet kiválaszt
	*/
	public static String SELECTALL() {
		return moduleProvider.get().SELECTALL;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Up
	* <u><i>Magyarul:</i></u> Fel
	*/
	public static String UP() {
		return moduleProvider.get().UP;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> can't display
	* <u><i>Magyarul:</i></u> nem megjeleníthető
	*/
	public static String cantDisplay() {
		return moduleProvider.get().cantDisplay;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Change
	* <u><i>Magyarul:</i></u> Megváltoztat
	*/
	public static String change() {
		return moduleProvider.get().change;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Click here to download:
	* <u><i>Magyarul:</i></u> Az adatok letölthetőek: 
	*/
	public static String csvComment() {
		return moduleProvider.get().csvComment;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Download
	* <u><i>Magyarul:</i></u> Letöltés
	*/
	public static String csvDownload() {
		return moduleProvider.get().csvDownload;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Something went wrong. See server log!
	* <u><i>Magyarul:</i></u> Hiba történt, 
	*/
	public static String csvError() {
		return moduleProvider.get().csvError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Invalid request. Try to refresh!
	* <u><i>Magyarul:</i></u> Hibás kérés, próbálja meg frissíteni az oldalt!
	*/
	public static String csvInvalid() {
		return moduleProvider.get().csvInvalid;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Duplicated key!
	* <u><i>Magyarul:</i></u> Egy kulcs csak egyszer használható
	*/
	public static String custKVOValidateDuplicate() {
		return moduleProvider.get().custKVOValidateDuplicate;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Empty key!
	* <u><i>Magyarul:</i></u> A kulcs mezőt ki kell tölteni!
	*/
	public static String custKVOValidateEmpty() {
		return moduleProvider.get().custKVOValidateEmpty;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Value can not be parsed!
	* <u><i>Magyarul:</i></u> Az érték mező nem megfelelő formázumú!
	*/
	public static String custKVOValidateParse() {
		return moduleProvider.get().custKVOValidateParse;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Type must be set!
	* <u><i>Magyarul:</i></u> A mező típusát be kell állítani!
	*/
	public static String custKVOValidateSet() {
		return moduleProvider.get().custKVOValidateSet;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Name
	* <u><i>Magyarul:</i></u> Név
	*/
	public static String customKVO_key() {
		return moduleProvider.get().customKVO_key;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Type
	* <u><i>Magyarul:</i></u> Típus
	*/
	public static String customKVO_type() {
		return moduleProvider.get().customKVO_type;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Value
	* <u><i>Magyarul:</i></u> Érték
	*/
	public static String customKVO_value() {
		return moduleProvider.get().customKVO_value;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Friday
	* <u><i>Magyarul:</i></u> péntek
	*/
	public static String day_friday() {
		return moduleProvider.get().day_friday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Monday
	* <u><i>Magyarul:</i></u> hétfő
	*/
	public static String day_monday() {
		return moduleProvider.get().day_monday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Saturday
	* <u><i>Magyarul:</i></u> szombat
	*/
	public static String day_saturday() {
		return moduleProvider.get().day_saturday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Sunday
	* <u><i>Magyarul:</i></u> vasárnap
	*/
	public static String day_sunday() {
		return moduleProvider.get().day_sunday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Thursday
	* <u><i>Magyarul:</i></u> csütörtök
	*/
	public static String day_thursday() {
		return moduleProvider.get().day_thursday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tuesday
	* <u><i>Magyarul:</i></u> kedd
	*/
	public static String day_tuesday() {
		return moduleProvider.get().day_tuesday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Wednesday
	* <u><i>Magyarul:</i></u> szerda
	*/
	public static String day_wednesday() {
		return moduleProvider.get().day_wednesday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Edit entry
	* <u><i>Magyarul:</i></u> Sor módosítása
	*/
	public static String dialogEditTitle() {
		return moduleProvider.get().dialogEditTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> New entry
	* <u><i>Magyarul:</i></u> Új sor
	*/
	public static String dialogNewTitle() {
		return moduleProvider.get().dialogNewTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> false
	* <u><i>Magyarul:</i></u> nem
	*/
	public static String falseText() {
		return moduleProvider.get().falseText;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> hours
	* <u><i>Magyarul:</i></u> óra
	*/
	public static String hours() {
		return moduleProvider.get().hours;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Choose images from Google image search
	* <u><i>Magyarul:</i></u> Kép választása a Google képkereső segítségével
	*/
	public static String imagefinderChoosefromgoogle() {
		return moduleProvider.get().imagefinderChoosefromgoogle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Downloading...
	* <u><i>Magyarul:</i></u> Letöltés folyamatban...
	*/
	public static String imagefinderDowloading() {
		return moduleProvider.get().imagefinderDowloading;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Failed to download...
	* <u><i>Magyarul:</i></u> Hiba a letöltés során
	*/
	public static String imagefinderFailed() {
		return moduleProvider.get().imagefinderFailed;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> No file
	* <u><i>Magyarul:</i></u> nincs fájl
	*/
	public static String imagefinderNoimage() {
		return moduleProvider.get().imagefinderNoimage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> 0 image found
	* <u><i>Magyarul:</i></u> 0 találat
	*/
	public static String imagefinderNoresult() {
		return moduleProvider.get().imagefinderNoresult;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Page
	* <u><i>Magyarul:</i></u> Oldal
	*/
	public static String imagefinderPage() {
		return moduleProvider.get().imagefinderPage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Downloaded
	* <u><i>Magyarul:</i></u> Letöltve
	*/
	public static String imagefinderSuccess() {
		return moduleProvider.get().imagefinderSuccess;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Upload
	* <u><i>Magyarul:</i></u> Válassza ki a feltöltendő fájlt
	*/
	public static String imagefinderUploadimage() {
		return moduleProvider.get().imagefinderUploadimage;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Use
	* <u><i>Magyarul:</i></u> Kiválaszt
	*/
	public static String imagefinderUse() {
		return moduleProvider.get().imagefinderUse;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Upload
	* <u><i>Magyarul:</i></u> Feltöltés
	*/
	public static String imageuploadBtn() {
		return moduleProvider.get().imageuploadBtn;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Change
	* <u><i>Magyarul:</i></u> Csere
	*/
	public static String imageuploadBtn_change() {
		return moduleProvider.get().imageuploadBtn_change;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error during upload. See server log!
	* <u><i>Magyarul:</i></u> Hiba a feltöltés során. Ellenőrízze a szerver beállításait.
	*/
	public static String imageuploadError() {
		return moduleProvider.get().imageuploadError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Invalid file format.
	* <u><i>Magyarul:</i></u> Nem megfelelő fájlformátum
	*/
	public static String imageuploadInvalidFileFormat() {
		return moduleProvider.get().imageuploadInvalidFileFormat;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Upload
	* <u><i>Magyarul:</i></u> Feltöltés
	*/
	public static String imageuploadTitle() {
		return moduleProvider.get().imageuploadTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Message
	* <u><i>Magyarul:</i></u> Üzenet
	*/
	public static String infoDialogTitle() {
		return moduleProvider.get().infoDialogTitle;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Loading data...
	* <u><i>Magyarul:</i></u> Adatok betöltése...
	*/
	public static String loading() {
		return moduleProvider.get().loading;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> minutes
	* <u><i>Magyarul:</i></u> perc
	*/
	public static String minutes() {
		return moduleProvider.get().minutes;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> April
	* <u><i>Magyarul:</i></u> Április
	*/
	public static String month_april() {
		return moduleProvider.get().month_april;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> August
	* <u><i>Magyarul:</i></u> Augusztus
	*/
	public static String month_august() {
		return moduleProvider.get().month_august;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> December
	* <u><i>Magyarul:</i></u> December
	*/
	public static String month_december() {
		return moduleProvider.get().month_december;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Februar
	* <u><i>Magyarul:</i></u> Február
	*/
	public static String month_february() {
		return moduleProvider.get().month_february;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Januar
	* <u><i>Magyarul:</i></u> Január
	*/
	public static String month_january() {
		return moduleProvider.get().month_january;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> July
	* <u><i>Magyarul:</i></u> Július
	*/
	public static String month_july() {
		return moduleProvider.get().month_july;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> June
	* <u><i>Magyarul:</i></u> Június
	*/
	public static String month_june() {
		return moduleProvider.get().month_june;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> March
	* <u><i>Magyarul:</i></u> Március
	*/
	public static String month_march() {
		return moduleProvider.get().month_march;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> May
	* <u><i>Magyarul:</i></u> Május
	*/
	public static String month_may() {
		return moduleProvider.get().month_may;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> November
	* <u><i>Magyarul:</i></u> November
	*/
	public static String month_november() {
		return moduleProvider.get().month_november;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> October
	* <u><i>Magyarul:</i></u> Október
	*/
	public static String month_october() {
		return moduleProvider.get().month_october;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> September
	* <u><i>Magyarul:</i></u> Szeptember
	*/
	public static String month_september() {
		return moduleProvider.get().month_september;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Do you really want to delete the selected element?
	* <u><i>Magyarul:</i></u> Biztosan törölni kívánja a kiválaszott sort?
	*/
	public static String reallyWantToDelete() {
		return moduleProvider.get().reallyWantToDelete;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Request error
	* <u><i>Magyarul:</i></u> Hiba a kérés során
	*/
	public static String restRequestError() {
		return moduleProvider.get().restRequestError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Result is filtered!
	* <u><i>Magyarul:</i></u> Az eredmények szűrve vannak.
	*/
	public static String searchForm_filtered() {
		return moduleProvider.get().searchForm_filtered;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> F
	* <u><i>Magyarul:</i></u> P
	*/
	public static String shortday_friday() {
		return moduleProvider.get().shortday_friday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> M
	* <u><i>Magyarul:</i></u> H
	*/
	public static String shortday_monday() {
		return moduleProvider.get().shortday_monday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Sa
	* <u><i>Magyarul:</i></u> Szo
	*/
	public static String shortday_saturday() {
		return moduleProvider.get().shortday_saturday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Su
	* <u><i>Magyarul:</i></u> V
	*/
	public static String shortday_sunday() {
		return moduleProvider.get().shortday_sunday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Th
	* <u><i>Magyarul:</i></u> Cs
	*/
	public static String shortday_thursday() {
		return moduleProvider.get().shortday_thursday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Tu
	* <u><i>Magyarul:</i></u> K
	*/
	public static String shortday_tuesday() {
		return moduleProvider.get().shortday_tuesday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> W
	* <u><i>Magyarul:</i></u> Sze
	*/
	public static String shortday_wednesday() {
		return moduleProvider.get().shortday_wednesday;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> true
	* <u><i>Magyarul:</i></u> igen
	*/
	public static String trueText() {
		return moduleProvider.get().trueText;
	}
	
	/**
	* <u><i>Description:</i></u>   <br />
	* <u><i>In English:</i></u> This is not valid e-mail address!
	* <u><i>Magyarul:</i></u> Nem megfelelő az e-mail cím!
	*/
	public static String validationEmail() {
		return moduleProvider.get().validationEmail;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Field error
	* <u><i>Magyarul:</i></u> Hibás mező - 
	*/
	public static String validationFieldError() {
		return moduleProvider.get().validationFieldError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Error:
	* <u><i>Magyarul:</i></u> Hiba: 
	*/
	public static String validationGeneralError() {
		return moduleProvider.get().validationGeneralError;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Nothing to save
	* <u><i>Magyarul:</i></u> Nincs változás a formon
	*/
	public static String validationNothingToSave() {
		return moduleProvider.get().validationNothingToSave;
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be equal to {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt szám értéke {constvalAsString} kell, hogy legyen!
	*/
	public static String validatorEQ(String constvalAsString, String fieldName) {
		return moduleProvider.get().validatorEQ.replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be greater then or equal to {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak nagyobb-egyenlőnek kell lennie, mint {constvalAsString}!
	*/
	public static String validatorGE(String constvalAsString, String fieldName) {
		return moduleProvider.get().validatorGE.replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be greater then {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak nagyobbnak kell lennie, mint {constvalAsString}!
	*/
	public static String validatorGT(String constvalAsString, String fieldName) {
		return moduleProvider.get().validatorGT.replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be less then or equal to {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak kisebb-egyenlőnek kell lennie, mint {constvalAsString}!
	*/
	public static String validatorLE(String constvalAsString, String fieldName) {
		return moduleProvider.get().validatorLE.replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> he number entered into field {fieldName} must be less then {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak kisebbnek kell lennie, mint {constvalAsString}!
	*/
	public static String validatorLT(String constvalAsString, String fieldName) {
		return moduleProvider.get().validatorLT.replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Maximum {maxLength} characters can be entered!
	* <u><i>Magyarul:</i></u> Legfeljebb {maxLength} karaktert lehet írni a mezőbe!
	*/
	public static String validatorLength(String maxLength) {
		return moduleProvider.get().validatorLength.replace("{maxLength}", maxLength);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This date should be after {otherFieldsName}!
	* <u><i>Magyarul:</i></u> A dámum {otherFieldsName} után kell, hogy legyen!
	*/
	public static String validatorShouldAfter(String otherFieldsName) {
		return moduleProvider.get().validatorShouldAfter.replace("{otherFieldsName}", otherFieldsName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This date should be before {otherFieldsName}!
	* <u><i>Magyarul:</i></u> A dámum {otherFieldsName} előtt kell, hogy legyen!
	*/
	public static String validatorShouldBefore(String otherFieldsName) {
		return moduleProvider.get().validatorShouldBefore.replace("{otherFieldsName}", otherFieldsName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> The {i}th ant the {j}th items are equal.
	* <u><i>Magyarul:</i></u> A listában a {i}. és a {j}. elem megegyezik.
	*/
	public static String validatorUniqueRelList(String j, String i) {
		return moduleProvider.get().validatorUniqueRelList.replace("{j}", j).replace("{i}", i);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This field is required!
	* <u><i>Magyarul:</i></u> A mező kitöltése kötelező!
	*/
	public static String validator_mandatory() {
		return moduleProvider.get().validator_mandatory;
	}
}
