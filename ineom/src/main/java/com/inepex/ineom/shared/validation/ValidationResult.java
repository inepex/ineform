package com.inepex.ineom.shared.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inepex.ineom.shared.i18n.IneOmI18n;

public class ValidationResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean isValid = true;
	
	List<String> generalErrors;
	Map<String, List<String>> fieldErrors;
	

	public ValidationResult() {
	}

	public void addGeneralError(String error) {
		isValid = false;
		if (generalErrors == null)
			generalErrors = new ArrayList<String>();
		generalErrors.add(error);
	}

	public void addFieldError(String fieldKey, String error) {
		isValid = false;
		if (fieldErrors == null)
			fieldErrors = new HashMap<String, List<String>>();
		
		List<String> fieldErrorList = fieldErrors.get(fieldKey);
		if (fieldErrorList == null) {			
			fieldErrorList = new ArrayList<String>();
			fieldErrors.put(fieldKey, fieldErrorList);
		}
		
		fieldErrorList.add(error);
	}
	
	public void merge(ValidationResult vResult)
	{
		if (vResult.generalErrors != null)
			for (String s : vResult.generalErrors)
				addGeneralError(s);
		if (vResult.fieldErrors != null && vResult.fieldErrors.keySet() != null)
			for (String key : vResult.fieldErrors.keySet())
				if (vResult.fieldErrors.get(key) != null)
					for (String s : vResult.fieldErrors.get(key))
						addFieldError(key, s);
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		
		sB.append("isValid: ").append(isValid).append("\r\n");
		
		sB.append(getGeneralErrorString());

		if (fieldErrors != null && fieldErrors.keySet() != null)
			for (String key : fieldErrors.keySet())
				sB.append(getFieldErrorString(key));

		return sB.toString();
	}
	
/****** Getters/Setters ******/
	
	public boolean isValid() {
		return isValid;
	}
	
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	public List<String> getGeneralErrors() {
		return generalErrors;
	}
	
	public void setGeneralErrors(List<String> generalErrors) {
		this.generalErrors = generalErrors;
	}
	
	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}
	
	public void setFieldErrors(Map<String, List<String>> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
	
	public String getFieldErrorString(String key){
		StringBuilder sB = new StringBuilder();
		if (fieldErrors != null && fieldErrors.get(key) != null)
			for (String s : fieldErrors.get(key))
				sB.append(IneOmI18n.validationGeneralError()).append(key).append(": ").append(s).append("\r\n");
		return sB.toString();
	}
	
	public String getGeneralErrorString(){
		StringBuilder sB = new StringBuilder();
		if (generalErrors != null)
			for (String s : generalErrors)
				sB.append(IneOmI18n.validationGeneralError()).append(s).append("\r\n");
		return sB.toString();
	}
	
}
