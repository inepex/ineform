package com.inepex.ineom.shared.validation;

import static com.inepex.ineom.shared.util.SharedUtil.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneT;
import com.inepex.ineom.shared.kvo.Relation;
import com.inepex.ineom.shared.validation.basicvalidators.BeforeAfterValidator;
import com.inepex.ineom.shared.validation.basicvalidators.EmailValidator;
import com.inepex.ineom.shared.validation.basicvalidators.LengthValidator;
import com.inepex.ineom.shared.validation.basicvalidators.MandatoryValidator;
import com.inepex.ineom.shared.validation.basicvalidators.NumericRelationValidator;
import com.inepex.ineom.shared.validation.basicvalidators.NumericRelationValidator.RelType;

public class KeyValueObjectValidationManager {
	
	public final static String MANDATORY = "mandatory";
	public final static String LESSTHEN = "lessthen";
	public final static String GREATERTHEN = "greaterthen";
	public final static String LESSEQUAL = "lessequal";
	public final static String GREATEREQUAL = "greaterequal";
	public final static String EQUAL = "equal";
	public final static String BEFORE = "before";
	public final static String AFTER = "after";
	public final static String MAXLENGTH = "maxlength";
	public final static String EMAIL = "email";
	
	private final DescriptorStore descStore;
	
	@Inject
	protected KeyValueObjectValidationManager(DescriptorStore descStore) {
		this.descStore = descStore;
	}
	
	
//------------------------------ GETTING VALIDATORS
	
	/**
	 * override it for manage handmade validators 
	 * 
	 * @param validatorName
	 * @return
	 */
	protected KeyValueObjectValidator createCustomValidator(String validatorName) {
		return null;
	}
	
	/**
	 * override it for manage objectdesc validators 
	 * 
	 * @param validatorName
	 * @return
	 */
	protected KeyValueObjectValidator createBaseValidator(IneT type, String fieldName, String validatorName, String fieldDisplayname) {
		
		//mandatory
		if(validatorName.contains(MANDATORY))
			return new MandatoryValidator(type, fieldName);
				
		//relation
		if(validatorName.contains(LESSTHEN))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(LESSTHEN+":", ""), RelType.lt, fieldDisplayname);
		if(validatorName.contains(GREATERTHEN))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(GREATERTHEN+":", ""), RelType.gt, fieldDisplayname);
		if(validatorName.contains(LESSEQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(LESSEQUAL+":", ""), RelType.le, fieldDisplayname);
		if(validatorName.contains(GREATEREQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(GREATEREQUAL+":", ""), RelType.ge, fieldDisplayname);
		if(validatorName.contains(EQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(EQUAL+":", ""), RelType.eq, fieldDisplayname);
		

		//timeline
		if(validatorName.contains(BEFORE))
			return new BeforeAfterValidator(fieldName, validatorName.replaceAll(BEFORE+":", ""), false, fieldDisplayname);
		if(validatorName.contains(AFTER))
			return new BeforeAfterValidator(validatorName.replaceAll(AFTER+":", ""), fieldName, true, fieldDisplayname);
		
		//length of a string
		if(validatorName.contains(MAXLENGTH))
			return new LengthValidator(type, fieldName, Integer.parseInt(validatorName.replaceAll(MAXLENGTH+":", "")));
		
		//e-mail
		if(validatorName.contains(EMAIL))
			return new EmailValidator(type, fieldName);
		
		
		return null;
	}
	
//------------------------------PROCESSING
	
	/**
	 * validate all the KVO with its relations
	 * 
	 */
	public ValidationResult validate(AssistedObject kvo) {
		ValidationResult result = new ValidationResult();

		if(kvo == null)
			return result;

		String descName = kvo.getDescriptorName();
		if (descName == null)
			return result;

		ObjectDesc objDesc = descStore.getOD(kvo.getDescriptorName());
		ValidatorDesc valDesc = descStore.getDefaultTypedDesc(kvo.getDescriptorName(), ValidatorDesc.class);

		//validators from objectdescriptor
		if (objDesc != null)
			for (FDesc fDesc : objDesc.getFields().values()) {
				for (String validatorName : fDesc.getValidatorNames()) {
					KeyValueObjectValidator validator = createBaseValidator(fDesc.getType(), fDesc.getKey(), validatorName, fDesc.getDefaultDisplayName());
					if (validator != null)
						validator.doValidation(kvo, result);
					else {
						//TODO logging bad string usage
					}
				}
			}
		
		//handmade validators
		if (valDesc != null && valDesc.getValidatorNames() != null)
			for (String valName : valDesc.getValidatorNames()) {
				
				KeyValueObjectValidator validator = createCustomValidator(valName);
				if (validator != null)
					validator.doValidation(kvo, result);
				else {
					//TODO logging bad string usage
				}
			}
		
		//validate relations
		if (objDesc != null && objDesc.getFields() != null && objDesc.getFields().values() != null)
			for (FDesc fDesc : objDesc.getFields().values()) {
				if (fDesc == null)
					continue;

				if (fDesc instanceof RelationFDesc) {
					Relation rel = kvo.getRelation(fDesc.getKey());
					if (rel == null) {
						//FIXME
//						rel = new Relation();
//						RelationFDesc relDescriptor = (RelationFDesc)fDesc;
//						rel.setKvo(new KeyValueObject(relDescriptor.getRelatedDescriptorName()));
						
						continue;
						
					}
					
					if (rel.getKvo() != null) {
						ValidationResult vr = validate(rel.getKvo());
						if (!vr.isValid()) {
							if (vr.getGeneralErrors() != null)
								result.getGeneralErrors().addAll(vr.getGeneralErrors());
							if (vr.getFieldErrors() != null) {
								Map<String, List<String>> fieldErrors = vr.getFieldErrors();
								for (String key : fieldErrors.keySet()) {
									String newKey = fDesc.getKey() + "." + key;
									for (String error : fieldErrors.get(key)) {
										result.addFieldError(newKey, error);
									}
								}
							}
						}
					}
				}
				
				//TODO relation list
			}
		
		return result;
	}
	
	/**
	 * 
	 * @param kvo
	 * @param fieldNames
	 * @param extravalidators - never will check extravalidators' getUsedFields, just they will be ran 
	 * @return
	 */
	public ValidationResult validatePartial(AssistedObject kvo, Collection<String> fieldNames) {
		ValidationResult result = new ValidationResult();
		ObjectDesc od = descStore.getOD(kvo.getDescriptorName());
		
		for(String fieldName : fieldNames) {
			List<String> nameAsList = listFromDotSeparated(fieldName);
			AssistedObject actual = kvo.getRelatedKVOMultiLevel(nameAsList);
			String lastKey = nameAsList.get(nameAsList.size()-1);
			FDesc fDesc = descStore.getRelatedFieldDescrMultiLevel(od, nameAsList);
			
			if(fDesc==null) continue;
			
			if(fDesc instanceof RelationFDesc) {
				Relation rel = kvo.getRelation(fDesc.getKey());
				if (rel != null && rel.getKvo()!=null) {
					ValidationResult vr = validate(rel.getKvo());
					if(!vr.isValid()) {
						if(vr.getGeneralErrors()!=null) result.getGeneralErrors().addAll(vr.getGeneralErrors());
						if(vr.getFieldErrors()!=null) {
							Map<String, List<String>> fieldErrors = vr.getFieldErrors();
							for(String key : fieldErrors.keySet()) {
								String newKey = fDesc.getKey() + "." + key;
								for(String error : fieldErrors.get(key)) {
									result.addFieldError(newKey, error);
								}
							}
						}
					}
				}			
			}
			
			
			ValidationResult vr = new ValidationResult();
			
			for(String validatorName : fDesc.getValidatorNames()) {
				KeyValueObjectValidator validator = createBaseValidator(fDesc.getType(), fDesc.getKey(), validatorName, fDesc.getDefaultDisplayName());
				if(validator!=null) validator.doValidation(actual, vr);
			}
			
			if(!vr.isValid()) {
				if(vr.getGeneralErrors()!=null) result.getGeneralErrors().addAll(vr.getGeneralErrors());
				if(vr.getFieldErrors().get(lastKey)!=null) {
					for(String error : vr.getFieldErrors().get(lastKey)) {
						result.addFieldError(fieldName, error);
					}
				}
			}
			
			//TODO relation list
		}
		
		//TODO handling handmade validators
		return result;
	}
	
}