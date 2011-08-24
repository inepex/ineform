package com.inepex.ineom.shared.descriptor;

import java.io.Serializable;

import com.inepex.ineom.shared.IneT;

public class LongFDesc extends NumericFDesc implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6165436932939533212L;

	public LongFDesc() {
		type = IneT.LONG;
	}

	public LongFDesc(String key, String defaultDisplayName) {
		super(key, IneT.LONG, defaultDisplayName);
	}
}
