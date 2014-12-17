package com.inepex.ineom.shared.i18n;

import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.I18nModuleProvider;

public class IneOmI18n extends I18nModule {

	private static final long serialVersionUID = 1L;

	public static final String MODULE_NAME = "IneOmI18n";
	
	static I18nModuleProvider<IneOmI18n> moduleProvider;
	
	public IneOmI18n() {
		super(MODULE_NAME);
	}
		
	public IneOmI18n(I18nModuleProvider<IneOmI18n> moduleProvider) {
		super(MODULE_NAME);
		IneOmI18n.moduleProvider = moduleProvider;
	}

	@Override
	public I18nModuleProvider<?> getI18nProvider() {
		return moduleProvider;
	}
	
	public void init(){
		textMap.put("validationAlphanum", "This field should contain only numbers and characters!");
		textMap.put("validationAlphanumOrSpace", "This field should contain only numbers, characters or space!");
		textMap.put("validationEmail", "This is not valid e-mail address!");
		textMap.put("validationFieldError", "Field error");
		textMap.put("validationGeneralError", "Error:");
		textMap.put("validatorEQ", "The number entered into field {fieldName} must be equal to {constvalAsString}!");
		textMap.put("validatorGE", "The number entered into field {fieldName} must be greater then or equal to {constvalAsString}!");
		textMap.put("validatorGT", "The number entered into field {fieldName} must be greater then {constvalAsString}!");
		textMap.put("validatorLE", "The number entered into field {fieldName} must be less then or equal to {constvalAsString}!");
		textMap.put("validatorLT", "The number entered into field {fieldName} must be less then {constvalAsString}!");
		textMap.put("validatorMaxLength", "Maximum {limit} characters can be entered!");
		textMap.put("validatorMinLength", "At least {limit} characters have to be entered!");
		textMap.put("validatorShouldAfter", "This date should be after {otherFieldsName}!");
		textMap.put("validatorShouldBefore", "This date should be before {otherFieldsName}!");
		textMap.put("validatorUniqueRelList", "The {i}th and the {j}th items are equal.");
		textMap.put("validator_mandatory", "This field is required!");
	}

	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This field should contain only numbers and characters!
	* <u><i>Magyarul:</i></u> A mező csak betűket és számokat tartalmazhat!
	*/
	public static String validationAlphanum() {
		return moduleProvider.get().getTextMap().get("validationAlphanum");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This field should contain only numbers, characters or space!
	* <u><i>Magyarul:</i></u> A mező csak betűket, számokat és szóközt tartalmazhat!
	*/
	public static String validationAlphanumOrSpace() {
		return moduleProvider.get().getTextMap().get("validationAlphanumOrSpace");
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> This is not valid e-mail address!
	* <u><i>Magyarul:</i></u> Nem megfelelő e-mail cím!
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
	* <u><i>In English:</i></u> The number entered into field {fieldName} must be less then {constvalAsString}!
	* <u><i>Magyarul:</i></u> A {fieldName} mezőbe beírt számnak kisebbnek kell lennie, mint {constvalAsString}!
	*/
	public static String validatorLT(String constvalAsString, String fieldName) {
		return moduleProvider.get().getTextMap().get("validatorLT").replace("{constvalAsString}", constvalAsString).replace("{fieldName}", fieldName);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> Maximum {limit} characters can be entered!
	* <u><i>Magyarul:</i></u> Legfeljebb {limit} karaktert lehet írni a mezőbe!
	*/
	public static String validatorMaxLength(String limit) {
		return moduleProvider.get().getTextMap().get("validatorMaxLength").replace("{limit}", limit);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> At least {limit} characters have to be entered!
	* <u><i>Magyarul:</i></u> Legalább {limit} karaktert kell írni a mezőbe!
	*/
	public static String validatorMinLength(String limit) {
		return moduleProvider.get().getTextMap().get("validatorMinLength").replace("{limit}", limit);
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
