package com.inepex.ineForm.server.customkvo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.inepex.ineForm.shared.types.ODFieldType;

/**
 * 
 * key set by parent is unique
 * 
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"parent","key"})}) 
public class PersistField {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable=false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private CustomKVO parent;
	
	@Column(nullable=false)
	private String keyField;
	
	@Column(nullable=false)
	private ODFieldType fieldType;
	
	/**
	 * only one can holds value depending on fieldType
	 * 
	 * see {@link com.inepex.ineForm.server.customkvo.PersistField#checkfields()}
	 */
	private Boolean booleanVal;
	private Long longVal;
	private Double doubleVal;
	private String stringVal;
	
	public PersistField(){
	}
	
	public PersistField(Long id, CustomKVO parent, String key, ODFieldType fieldType, Object value) {
		this.id = id;
		this.parent = parent;
		this.keyField = key;
		this.fieldType = fieldType;
		
		if(value!=null) {
			switch (fieldType.ineT) {
			case BOOLEAN:
				this.booleanVal=(Boolean) value;
				break;
			case DOUBLE:
				this.doubleVal=(Double) value;
				break;
			case LONG:
				this.longVal=(Long) value;
				break;
			case STRING:
				this.stringVal=(String) value;
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return keyField;
	}

	public void setKey(String key) {
		this.keyField = key;
	}

	public ODFieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(ODFieldType fieldType) {
		this.fieldType = fieldType;
	}

	public Boolean getBooleanVal() {
		return booleanVal;
	}

	public void setBooleanVal(Boolean booleanVal) {
		this.booleanVal = booleanVal;
	}

	public Long getLongVal() {
		return longVal;
	}

	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}

	public Double getDoubleVal() {
		return doubleVal;
	}

	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}

	public String getStringVal() {
		return stringVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}
	
	public CustomKVO getParent() {
		return parent;
	}
	
	public void setParent(CustomKVO parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return keyField+" ("+fieldType.toString()+")";
	}
	
	/**
	 * NOT GENERATED,
	 * equality doesn't depends on id
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersistField other = (PersistField) obj;
		if (booleanVal == null) {
			if (other.booleanVal != null)
				return false;
		} else if (!booleanVal.equals(other.booleanVal))
			return false;
		if (doubleVal == null) {
			if (other.doubleVal != null)
				return false;
		} else if (!doubleVal.equals(other.doubleVal))
			return false;
		if (fieldType != other.fieldType)
			return false;
		if (keyField == null) {
			if (other.keyField != null)
				return false;
		} else if (!keyField.equals(other.keyField))
			return false;
		if (longVal == null) {
			if (other.longVal != null)
				return false;
		} else if (!longVal.equals(other.longVal))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (stringVal == null) {
			if (other.stringVal != null)
				return false;
		} else if (!stringVal.equals(other.stringVal))
			return false;
		return true;
	}
	
	
}
