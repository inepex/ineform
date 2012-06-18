package com.inepex.ineForm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Kvo_SearchParam {
	public static enum FilterType {
		EXACTMATCH,
		OPENSTART,
		OPENEND,
		OPENBOTH
	}
	String secondLevelJoin() default ""; 
	FilterType type() default FilterType.OPENEND;
}
