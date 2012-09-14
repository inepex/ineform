package com.inepex.ineom.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Istv�n Szoboszlai
 * Lists available Types provided by IneForm Core
 *
 */
public enum IneT implements IsSerializable {
	BOOLEAN,
	DOUBLE,
	LIST,
	LONG,
	RELATION,
	STRING,
	UNDEFINED
}
