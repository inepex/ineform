package com.inepex.ineForm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Should be used in DTO-s tells the assist generator what kind of relation
 * should be assumed on a relation field
 * 
 * @author istvanszoboszlai
 *
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Kvo_RelationType {
    String name();
}
