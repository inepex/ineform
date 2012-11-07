package com.inepex.ineForm.server.customkvo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.inepex.ineForm.shared.types.ODFieldType;

@Generated(value = "NOT GENERATED")
@StaticMetamodel(PersistField.class)
public class PersistField_ { 

	public static volatile SingularAttribute<PersistField, Long> id;
	public static volatile SingularAttribute<PersistField, CustomKVO> parent;
	public static volatile SingularAttribute<PersistField, String> keyField;
	public static volatile SingularAttribute<PersistField, ODFieldType> fieldType;
	public static volatile SingularAttribute<PersistField, Boolean> booleanVal;
	public static volatile SingularAttribute<PersistField, Long> longVal;
	public static volatile SingularAttribute<PersistField, Double> doubleVal;
	public static volatile SingularAttribute<PersistField, String> stringVal;

}