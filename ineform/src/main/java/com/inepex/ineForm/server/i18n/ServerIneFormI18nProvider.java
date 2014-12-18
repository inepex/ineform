package com.inepex.ineForm.server.i18n;
import com.inepex.ineForm.client.i18n.IneFormI18n;

import com.inepex.inei18n.server.ServerI18nProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.google.inject.Provider;

public class ServerIneFormI18nProvider extends ServerI18nProvider<IneFormI18n> {

	private static final long serialVersionUID = 1L;

	public ServerIneFormI18nProvider(Provider<CurrentLang> currentLangProvider) {
		super(currentLangProvider);
	}

	@Override
	protected Class<IneFormI18n> getModuleClass() {
		return IneFormI18n.class;
	}

	@Override
	public IneFormI18n getVirgineI18nModule() {
		IneFormI18n module = new IneFormI18n(this);
		initTexts(module);
		return module;
	}
	
	public void initTexts(IneFormI18n module){
		module.getTextMap().put("ADD", "+");
		module.getTextMap().put("CANCEL", "Cancel");
		module.getTextMap().put("CSVEXPORT", "Export ");
		module.getTextMap().put("DELETE", "Delete");
		module.getTextMap().put("DESELECT", "Deselect");
		module.getTextMap().put("DESELECTALL", "Deselect all");
		module.getTextMap().put("DOWN", "Down");
		module.getTextMap().put("EDIT", "Edit");
		module.getTextMap().put("ERR_CouldNotRenderWidget", "Could not render widget");
		module.getTextMap().put("FILTER", "Filter");
		module.getTextMap().put("FINISH", "Finish");
		module.getTextMap().put("MOVEDOWN", "Down");
		module.getTextMap().put("MOVEUP", "Up");
		module.getTextMap().put("NEW", "New");
		module.getTextMap().put("NEXT", "Next page");
		module.getTextMap().put("ODFieldType_BOOLEAN", "Checkbox");
		module.getTextMap().put("ODFieldType_DOUBLE", "Fraction");
		module.getTextMap().put("ODFieldType_EMAIL", "E-mail");
		module.getTextMap().put("ODFieldType_LONG", "Number");
		module.getTextMap().put("ODFieldType_STRING", "Text");
		module.getTextMap().put("OK", "Ok");
		module.getTextMap().put("PREVIOUS", "Previous page");
		module.getTextMap().put("REMOVE", "-");
		module.getTextMap().put("RESET", "Reset");
		module.getTextMap().put("SAVE", "Save");
		module.getTextMap().put("SEARCH", "Search");
		module.getTextMap().put("SELECT", "Select");
		module.getTextMap().put("SELECTALL", "Select all");
		module.getTextMap().put("UP", "Up");
		module.getTextMap().put("cantDisplay", "can't display");
		module.getTextMap().put("change", "Change");
		module.getTextMap().put("csvComment", "Click here to download:");
		module.getTextMap().put("csvDownload", "Download");
		module.getTextMap().put("csvError", "Something went wrong. See server log!");
		module.getTextMap().put("csvInvalid", "Invalid request. Try to refresh!");
		module.getTextMap().put("custKVOValidateDot", "Name should not contain dot!");
		module.getTextMap().put("custKVOValidateDuplicate", "Duplicated name!");
		module.getTextMap().put("custKVOValidateEmpty", "Empty name!");
		module.getTextMap().put("custKVOValidateParse", "Value can not be parsed!");
		module.getTextMap().put("custKVOValidateSet", "Type must be set!");
		module.getTextMap().put("customKVO_key", "Name");
		module.getTextMap().put("customKVO_type", "Type");
		module.getTextMap().put("customKVO_value", "Value");
		module.getTextMap().put("day_friday", "Friday");
		module.getTextMap().put("day_monday", "Monday");
		module.getTextMap().put("day_saturday", "Saturday");
		module.getTextMap().put("day_sunday", "Sunday");
		module.getTextMap().put("day_thursday", "Thursday");
		module.getTextMap().put("day_tuesday", "Tuesday");
		module.getTextMap().put("day_wednesday", "Wednesday");
		module.getTextMap().put("dialogEditTitle", "Edit entry");
		module.getTextMap().put("dialogNewTitle", "New entry");
		module.getTextMap().put("falseText", "false");
		module.getTextMap().put("hours", "hours");
		module.getTextMap().put("imagefinderChoosefromgoogle", "Choose images from Google image search");
		module.getTextMap().put("imagefinderDowloading", "Downloading...");
		module.getTextMap().put("imagefinderFailed", "Failed to download...");
		module.getTextMap().put("imagefinderNoimage", "No file");
		module.getTextMap().put("imagefinderNoresult", "0 image found");
		module.getTextMap().put("imagefinderPage", "Page");
		module.getTextMap().put("imagefinderSuccess", "Downloaded");
		module.getTextMap().put("imagefinderUploadimage", "Upload");
		module.getTextMap().put("imagefinderUse", "Use");
		module.getTextMap().put("imageuploadBtn", "Upload");
		module.getTextMap().put("imageuploadBtn_change", "Change");
		module.getTextMap().put("imageuploadError", "Error during upload. See server log!");
		module.getTextMap().put("imageuploadInvalidFileFormat", "Invalid file format.");
		module.getTextMap().put("imageuploadTitle", "Upload");
		module.getTextMap().put("inedate_notset", "Not set");
		module.getTextMap().put("inetable_noresult", "No result found");
		module.getTextMap().put("infoDialogTitle", "Message");
		module.getTextMap().put("loading", "Loading data...");
		module.getTextMap().put("minutes", "minutes");
		module.getTextMap().put("month_april", "April");
		module.getTextMap().put("month_august", "August");
		module.getTextMap().put("month_december", "December");
		module.getTextMap().put("month_february", "Februar");
		module.getTextMap().put("month_january", "Januar");
		module.getTextMap().put("month_july", "July");
		module.getTextMap().put("month_june", "June");
		module.getTextMap().put("month_march", "March");
		module.getTextMap().put("month_may", "May");
		module.getTextMap().put("month_november", "November");
		module.getTextMap().put("month_october", "October");
		module.getTextMap().put("month_september", "September");
		module.getTextMap().put("nd", "nd");
		module.getTextMap().put("rd", "rd");
		module.getTextMap().put("reallyWantToDelete", "Do you really want to delete the selected element?");
		module.getTextMap().put("restRequestError", "Request error");
		module.getTextMap().put("searchForm_filtered", "Result is filtered!");
		module.getTextMap().put("shortday_friday", "F");
		module.getTextMap().put("shortday_monday", "M");
		module.getTextMap().put("shortday_saturday", "Sa");
		module.getTextMap().put("shortday_sunday", "Su");
		module.getTextMap().put("shortday_thursday", "Th");
		module.getTextMap().put("shortday_tuesday", "Tu");
		module.getTextMap().put("shortday_wednesday", "W");
		module.getTextMap().put("st", "st");
		module.getTextMap().put("th", "th");
		module.getTextMap().put("trueText", "true");
		module.getTextMap().put("validationEmail", "This is not valid e-mail address!");
		module.getTextMap().put("validationFieldError", "Field error");
		module.getTextMap().put("validationGeneralError", "Error:");
		module.getTextMap().put("validationNothingToSave", "Nothing to save");
		module.getTextMap().put("validatorEQ", "The number entered into field {fieldName} must be equal to {constvalAsString}!");
		module.getTextMap().put("validatorGE", "The number entered into field {fieldName} must be greater then or equal to {constvalAsString}!");
		module.getTextMap().put("validatorGT", "The number entered into field {fieldName} must be greater then {constvalAsString}!");
		module.getTextMap().put("validatorLE", "The number entered into field {fieldName} must be less then or equal to {constvalAsString}!");
		module.getTextMap().put("validatorLT", "he number entered into field {fieldName} must be less then {constvalAsString}!");
		module.getTextMap().put("validatorLength", "Maximum {maxLength} characters can be entered!");
		module.getTextMap().put("validatorShouldAfter", "This date should be after {otherFieldsName}!");
		module.getTextMap().put("validatorShouldBefore", "This date should be before {otherFieldsName}!");
		module.getTextMap().put("validatorUniqueRelList", "The {i}th and the {j}th items are equal.");
		module.getTextMap().put("validator_mandatory", "This field is required!");
	}
}
