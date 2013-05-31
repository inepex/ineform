package com.inepex.ineForm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * If annotation <b>not</b> specified fetch will be assumed as {@link Mode#fullObject}
 * 
 * @author istvanszoboszlai
 *
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Kvo_Fetch {
	public static enum Mode {		
		noFetch,
		fullObject,
		idRelation;
	}
	
	Mode mode() default Mode.fullObject;
}
