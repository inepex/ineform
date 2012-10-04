package com.inepex.ineom.shared.validation;

import static com.inepex.ineom.shared.util.SharedUtil.listFromDotSeparated;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;
import com.inepex.ineom.shared.validation.basicvalidators.BeforeAfterValidator;
import com.inepex.ineom.shared.validation.basicvalidators.EmailValidator;
import com.inepex.ineom.shared.validation.basicvalidators.LengthValidator;
import com.inepex.ineom.shared.validation.basicvalidators.LengthValidator.Type;
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
	public final static String MINLENGTH = "minlength";
	public final static String EMAIL = "email";
	
	private final DescriptorStore descStore;
	private final AssistedObjectHandlerFactory objectHandlerFactory; 
	
	@Inject
	protected KeyValueObjectValidationManager(DescriptorStore descStore, AssistedObjectHandlerFactory objectHandlerFactory) {
		this.objectHandlerFactory=objectHandlerFactory;
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
	public KeyValueObjectValidator createBaseValidator(IneT type, String fieldName, String validatorName, String fieldDisplayname, ObjectDesc od) {
		
		//mandatory
		if(validatorName.contains(MANDATORY))
			return new MandatoryValidator(type, fieldName, od);
				
		//relation
		if(validatorName.contains(LESSTHEN))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(LESSTHEN+":", ""), RelType.lt, fieldDisplayname, od);
		if(validatorName.contains(GREATERTHEN))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(GREATERTHEN+":", ""), RelType.gt, fieldDisplayname, od);
		if(validatorName.contains(LESSEQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(LESSEQUAL+":", ""), RelType.le, fieldDisplayname, od);
		if(validatorName.contains(GREATEREQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(GREATEREQUAL+":", ""), RelType.ge, fieldDisplayname, od);
		if(validatorName.contains(EQUAL))
			return new NumericRelationValidator(type, fieldName, validatorName.replaceAll(EQUAL+":", ""), RelType.eq, fieldDisplayname, od);
		

		//timeline
		if(validatorName.contains(BEFORE))
			return new BeforeAfterValidator(fieldName, validatorName.replaceAll(BEFORE+":", ""), false, fieldDisplayname, od);
		if(validatorName.contains(AFTER))
			return new BeforeAfterValidator(validatorName.replaceAll(AFTER+":", ""), fieldName, true, fieldDisplayname, od);
		
		//length of a string
		if(validatorName.contains(MAXLENGTH))
			return new LengthValidator(type, fieldName, Type.MAXLENGTH, Integer.parseInt(validatorName.replaceAll(MAXLENGTH+":", "")), od);
		
		if(validatorName.contains(MINLENGTH))
			return new LengthValidator(type, fieldName, Type.MINLENGTH, Integer.parseInt(validatorName.replaceAll(MINLENGTH+":", "")), od);
		
		//e-mail
		if(validatorName.contains(EMAIL))
			return new EmailValidator(type, fieldName, od);
		
		
		return null;
	}
	
//------------------------------PROCESSING
	
	public ValidationResult validate(AssistedObject kvo) {
		return validate(kvo, ArrayListMultimap.<String, KeyValueObjectValidator>create());
	}
	
	/**
	 * validate all the KVO with its relations
	 * 
	 */
	public ValidationResult validate(AssistedObject kvo, Multimap<String, KeyValueObjectValidator> customValidators) {
		if(kvo==null)
			return new ValidationResult();
		
		ObjectDesc od = descStore.getOD(kvo.getDescriptorName());
		return validate(kvo, od, customValidators);
	}
	
	public ValidationResult validate(AssistedObject kvo, ObjectDesc od){
		return validate(kvo, od,  ArrayListMultimap.<String, KeyValueObjectValidator>create());
	}
	
	public ValidationResult validate(AssistedObject kvo, ObjectDesc od, Multimap<String, KeyValueObjectValidator> customValidators) {
		return validatePartial(kvo, od.getKeys(), od, customValidators);
	}

	public ValidationResult validatePartial(AssistedObject kvo, Collection<String> fieldNames){
		return validatePartial(kvo, fieldNames, ArrayListMultimap.<String, KeyValueObjectValidator>create());
	}
	
	public ValidationResult validatePartial(AssistedObject kvo, Collection<String> fieldNames,
			Multimap<String, KeyValueObjectValidator> customValidators) {
		if(kvo==null)
			return new ValidationResult();
		
		return validatePartial(kvo, fieldNames, descStore.getOD(kvo.getDescriptorName()), customValidators);
	}
	
	/**
	 * 
	 * @param extravalidators - never will check extravalidators' getUsedFields, just they will be ran 
	 * @return
	 */
	public ValidationResult validatePartial(AssistedObject kvo, Collection<String> fieldNames, ObjectDesc od,
			Multimap<String, KeyValueObjectValidator> customValidators) {
		ValidationResult result = new ValidationResult();
		
		if(kvo == null)
			return result;
		
		
		for(String fieldName : fieldNames) {
			List<String> nameAsList = listFromDotSeparated(fieldName);
			AssistedObjectChecker actual;
			
			if(nameAsList.size()>1) {
				AssistedObjectHandler handler = objectHandlerFactory.createHandler(kvo);
				actual = handler.getRelatedKVOMultiLevel(nameAsList);
			} else {
				actual = new AssistedObjectChecker(kvo, kvo.getDescriptorName(), od);
			}
			
			FDesc fDesc = descStore.getRelatedFieldDescrMultiLevel(od, nameAsList);
			
			if(fDesc==null) continue;
			
			if(fDesc instanceof RelationFDesc) {
				if(IFConsts.customDescriptorName.equals(((RelationFDesc) fDesc).getRelatedDescriptorName()))
					//TODO
					continue;
				
				AssistedObjectHandler checker = objectHandlerFactory.createHandler(kvo);
				Relation rel = checker.getRelation(fDesc.getKey());
				if (rel != null && rel.getKvo()!=null) {
					ValidationResult vr = validate(rel.getKvo());
					if(!vr.isValid()) {
						if(vr.getGeneralErrors()!=null) result.getGeneralErrors().addAll(vr.getGeneralErrors());
						if(vr.getFieldErrors()!=null) {
							Map<String, List<String>> fieldErrors = vr.getFieldErrors();
							for(String key : fieldErrors.keySet()) {
								String newKey = fDesc.getKey() + SharedUtil.ID_PART_SEPARATOR + key;
								for(String error : fieldErrors.get(key)) {
									result.addFieldError(newKey, error);
								}
							}
						}
					}
				}			
			}
			
			//findig the suitable od
			ObjectDesc odOfRelatedKVO = od;
			if(SharedUtil.isMultilevelKey(fieldName)) {
				for(int i=0; i<nameAsList.size()-1; i++) {
					RelationFDesc fd = (RelationFDesc) odOfRelatedKVO.getField(nameAsList.get(i));
					odOfRelatedKVO=descStore.getOD(fd.getRelatedDescriptorName());
				}
			}
			
			//validation
			ValidationResult vr = new ValidationResult();
			for(String validatorName : fDesc.getValidatorNames()) {
				KeyValueObjectValidator validator = createBaseValidator(fDesc.getType(), fDesc.getKey(), validatorName, fDesc.getDefaultDisplayName(), odOfRelatedKVO);
				if(validator!=null) validator.doValidation(actual.getAssistedObject(), vr);
			}
			
			for (KeyValueObjectValidator validator : customValidators.get(fieldName)){
				validator.doValidation(actual.getAssistedObject(), vr);
			}
			
			if(!vr.isValid()) {
				if(vr.getGeneralErrors()!=null) result.getGeneralErrors().addAll(vr.getGeneralErrors());
				for(Entry<String, List<String>> entry : vr.getFieldErrors().entrySet()) {
					for(String error : entry.getValue()) {
						result.addFieldError(fieldName, error);
					}
				}
			}
			
			//TODO relation list: there is some rel list validation in SaveCancelForm
		}
		
		return result;
	}
	
}