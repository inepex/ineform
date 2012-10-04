package com.inepex.ineom.shared.descriptor.fdesc;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

/**
 * @author Istvan Szoboszlai
 *
 */
public class StringFDesc extends FDesc implements Serializable, IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5521900795365354819L;

	public StringFDesc() {
		type = IneT.STRING;
	}
	
	public StringFDesc(String key, String defaultDisplayName, String... props) {
		super(key, IneT.STRING, defaultDisplayName, props);
	}

	public StringFDesc(String key, String defaultDisplayName) {
		super(key, IneT.STRING, defaultDisplayName);
	}
	
	public StringFDesc maxLength(int maxLenght)  {
		validatorNames.add(KeyValueObjectValidationManager.MAXLENGTH+":"+maxLenght);
		return this;
	}
	
	public StringFDesc minLength(int minLenght)  {
		validatorNames.add(KeyValueObjectValidationManager.MINLENGTH+":"+minLenght);
		return this;
	}

	public StringFDesc email() {
		validatorNames.add(KeyValueObjectValidationManager.EMAIL);
		return this;
	}
}
