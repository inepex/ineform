package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;

import com.inepex.ineom.shared.kvo.IneT;

public class BooleanFDesc extends FDesc implements
		Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2885140949910002444L;

	public BooleanFDesc() {
		type = IneT.BOOLEAN;
	}

	public BooleanFDesc(String key, String defaultDisplayName) {
		super(key, IneT.BOOLEAN, defaultDisplayName);
	}


}
