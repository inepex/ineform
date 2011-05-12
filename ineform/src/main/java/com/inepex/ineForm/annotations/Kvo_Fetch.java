package com.inepex.ineForm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Usage: </br>
 * - If annotation <b>not</b> specified lazy fetch will be assumed </br>
 * - If annotation <b>specified without {@link Mode} parameter</b> eager mode will be assumed
 * - If {@link Mode} parameter is specified obviously given mode will be processed.
 * 
 * @author istvanszoboszlai
 *
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Kvo_Fetch {
	public static enum Mode {
		lazy,
		eager,
		relation;
	}
	
	Mode mode() default Mode.eager;
}
