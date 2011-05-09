package com.inepex.ineom.shared.validation.basicvalidators;

import com.inepex.ineom.shared.i18n.IneOmI18n;
import com.inepex.ineom.shared.kvo.AssistedObject;
import com.inepex.ineom.shared.kvo.IneT;
import com.inepex.ineom.shared.util.DoubleUtil;
import com.inepex.ineom.shared.validation.KeyValueObjectValidator;
import com.inepex.ineom.shared.validation.ValidationResult;

public class NumericRelationValidator implements KeyValueObjectValidator{

	private final String firstFieldName;
	private String secondFieldName;
	private Double constval;
	private final IneT type;
	private final RelType reltype;
	
	private final String fieldDisplayname;
	
	public enum RelType {
		gt, lt, ge, le, eq;
	}
	
	public NumericRelationValidator(IneT type, String fieldName, String numberOrFieldname, RelType reltype, String fieldDisplayname) {
		this.firstFieldName=fieldName;
		this.type=type;
		this.reltype=reltype;
		this.fieldDisplayname=fieldDisplayname;
		
		if(type!=IneT.DOUBLE && type!=IneT.LONG ) 
			throw new RuntimeException("NumericRelationValidator on "+fieldName+" which type is "+type.toString());
		
		try {
			this.constval=Double.parseDouble(numberOrFieldname);
			this.secondFieldName = null;
		} catch (NumberFormatException e) {
			this.secondFieldName = numberOrFieldname;
			this.constval = null;
		}	
	}
	

	@Override
	public void doValidation(AssistedObject kvo, ValidationResult validationResult) {
		double firstVal = 0;
		double secondVal = 0;
		String constvalAsString ="";
		
		switch (type) {
			case DOUBLE:
				Double tmp = kvo.getDouble(firstFieldName);
				if(tmp==null) return;
				else firstVal=tmp;
				
				if (secondFieldName != null){
					tmp = kvo.getDouble(secondFieldName);
					if(tmp==null) return;
					else secondVal=tmp;
				} else {
					secondVal = constval;
				}
				constvalAsString = Double.toString(DoubleUtil.stubToMaxDigits(2, secondVal));
				break;
				
			case LONG:
				Long tmp2 = kvo.getLong(firstFieldName);
				if(tmp2==null) return;
				else firstVal=tmp2;
				
				if (secondFieldName != null){
					tmp2 = kvo.getLong(secondFieldName);
					if (tmp2 == null) return;
					else secondVal = tmp2;
				} else {
					secondVal = constval;
				}
				
				constvalAsString = Long.toString(new Double(secondVal).longValue());				
				break;
		}
		
		switch (reltype) {
			case eq:
				if(firstVal!=secondVal) validationResult.addFieldError(firstFieldName, IneOmI18n.validatorEQ(fieldDisplayname, constvalAsString));
				return;
			case gt:
				if(firstVal<=secondVal) validationResult.addFieldError(firstFieldName, IneOmI18n.validatorGT(fieldDisplayname, constvalAsString));
				return;
			case lt:
				if(firstVal>=secondVal) validationResult.addFieldError(firstFieldName, IneOmI18n.validatorLT(fieldDisplayname, constvalAsString));
				return;
			case ge:
				if(firstVal<secondVal) validationResult.addFieldError(firstFieldName, IneOmI18n.validatorGE(fieldDisplayname, constvalAsString));
				return;
			case le:
				if(firstVal>secondVal) validationResult.addFieldError(firstFieldName, IneOmI18n.validatorLE(fieldDisplayname, constvalAsString));
				return;
		
		}
		
	}
	
}
