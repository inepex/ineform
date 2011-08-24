package com.inepex.ineom.shared.validation;

import static com.inepex.ineom.shared.util.SharedUtil.listFromDotSeparated;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory.AssistedObjectHandler;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
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
	private final AssistedObjectHandlerFactory objectHandlerFactory;
	
	@Inject
	protected KeyValueObjectValidationManager(DescriptorStore descStore, AssistedObjectHandlerFactory objectHandlerFactory) {
		this.descStore = descStore;
		this.objectHandlerFactory=objectHandlerFactory;
	}
	
	//TODO create handmade validator for boolean list
//	public static void validateBoolean(String errorMsg, ValidationResult vr, AssistedObject kvo, String... fields) {
//		for(String field : fields) {
//			Boolean b = kvo.getBoolean(field);
//			if(b!=null && b) return;
//		}
//		
//		vr.addFieldError(fields[0], errorMsg);
//	}
	
	
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
			return new MandatoryValidator(type, fieldName, objectHandlerFactory);
				
		//relation
		if(validatorName.contains(LESSTHEN))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(LESSTHEN+":", ""), RelType.lt, fieldDisplayname, objectHandlerFactory);
		if(validatorName.contains(GREATERTHEN))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(GREATERTHEN+":", ""), RelType.gt, fieldDisplayname, objectHandlerFactory);
		if(validatorName.contains(LESSEQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(LESSEQUAL+":", ""), RelType.le, fieldDisplayname, objectHandlerFactory);
		if(validatorName.contains(GREATEREQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(GREATEREQUAL+":", ""), RelType.ge, fieldDisplayname, objectHandlerFactory);
		if(validatorName.contains(EQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(EQUAL+":", ""), RelType.eq, fieldDisplayname, objectHandlerFactory);
		

		//timeline
		if(validatorName.contains(BEFORE))
			return new BeforeAfterValidator(fieldName, validatorName.replaceAll(BEFORE+":", ""), false, fieldDisplayname, objectHandlerFactory);
		if(validatorName.contains(AFTER))
			return new BeforeAfterValidator(validatorName.replaceAll(AFTER+":", ""), fieldName, true, fieldDisplayname, objectHandlerFactory);
		
		//length of a string
		if(validatorName.contains(MAXLENGTH))
			return new LengthValidator(type, fieldName, Integer.parseInt(validatorName.replaceAll(MAXLENGTH+":", "")), objectHandlerFactory);
		
		//e-mail
		if(validatorName.contains(EMAIL))
			return new EmailValidator(type, fieldName, objectHandlerFactory);
		
		
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
					AssistedObjectHandler checker = objectHandlerFactory.createHandler(kvo);
					Relation rel = checker.getRelation(fDesc.getKey());
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
			AssistedObjectHandler handler = objectHandlerFactory.createHandler(kvo);
			AssistedObjectChecker actual = handler.getRelatedKVOMultiLevel(nameAsList);
			String lastKey = nameAsList.get(nameAsList.size()-1);
			FDesc fDesc = descStore.getRelatedFieldDescrMultiLevel(od, nameAsList);
			
			if(fDesc==null) continue;
			
			if(fDesc instanceof RelationFDesc) {
				AssistedObjectHandler checker = objectHandlerFactory.createHandler(kvo);
				Relation rel = checker.getRelation(fDesc.getKey());
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
				if(validator!=null) validator.doValidation(actual.getAssistedObject(), vr);
			}
			
			if(!vr.isValid()) {
				if(vr.getGeneralErrors()!=null) result.getGeneralErrors().addAll(vr.getGeneralErrors());
				if(vr.getFieldErrors().get(lastKey)!=null) {
					for(String error : vr.getFieldErrors().get(lastKey)) {
						result.addFieldError(fieldName, error);
					}
				}
			}
			
			//TODO relation list: there is some rel list validation in SaveCancelForm
		}
		
		//TODO handling handmade validators
		return result;
	}
	
}