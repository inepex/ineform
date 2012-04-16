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
	
	public String validationEmail = "This is not valid e-mail address!";
	public String validationFieldError = "Field error";
	public String validationGeneralError = "Error:";
	public String validatorEQ = "The number entered into field {fieldName} must be equal to {constvalAsString}!";
	public String validatorGE = "The number entered into field {fieldName} must be greater then or equal to {constvalAsString}!";
	public String validatorGT = "The number entered into field {fieldName} must be greater then {constvalAsString}!";
	public String validatorLE = "The number entered into field {fieldName} must be less then or equal to {constvalAsString}!";
	public String validatorLT = "he number entered into field {fieldName} must be less then {constvalAsString}!";
	public String validatorMaxLength = "Maximum {limit} characters can be entered!";
	public String validatorMinLength = "At least {limit} characters have to be entered!";
	public String validatorShouldAfter = "This date should be after {otherFieldsName}!";
	public String validatorShouldBefore = "This date should be before {otherFieldsName}!";
	public String validatorUniqueRelList = "The {i}th and the {j}th items are equal.";
	public String validator_mandatory = "This field is required!";

	
	/**
	* <u><i>Description:</i></u>   <br />
	* <u><i>In English:</i></u> This is not valid e-mail address!
	* <u><i>Magyarul:</i></u> Meg megfelelő e-mail címed adott meg!
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
	* <u><i>In English:</i></u> Maximum {limit} characters can be entered!
	* <u><i>Magyarul:</i></u> Legfeljebb {limit} karaktert lehet írni a mezőbe!
	*/
	public static String validatorMaxLength(String limit) {
		return moduleProvider.get().validatorMaxLength.replace("{limit}", limit);
	}
	
	/**
	* <u><i>Description:</i></u>  <br />
	* <u><i>In English:</i></u> At least {limit} characters have to be entered!
	* <u><i>Magyarul:</i></u> Legalább {limit} karaktert kell írni a mezőbe!
	*/
	public static String validatorMinLength(String limit) {
		return moduleProvider.get().validatorMinLength.replace("{limit}", limit);
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
	* <u><i>In English:</i></u> The {i}th and the {j}th items are equal.
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
