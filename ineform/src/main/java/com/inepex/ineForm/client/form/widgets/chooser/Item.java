package com.inepex.ineForm.client.form.widgets.chooser;

import com.inepex.ineom.shared.Relation;

public class Item {

	Object o;
	String id;
	String displayName;
	
	/**
	 * for strings
	 * @param value
	 */
	public Item(String value) {
		o = value;
		id = value;
		displayName = value;
	}
	
	/**
	 * for enums
	 * @param num
	 * @param value
	 */
	public Item(int num, String value) {
		o = value;
		id = "" + num;
		displayName = value;
	}
	
	public Item(Relation rel) {
		o = rel;
		id = "" + rel.getId();
		displayName = rel.getDisplayName();
	}
	
	public Item(Object o, String id, String displayName) {
		super();
		this.o = o;
		this.id = id;
		this.displayName = displayName;
	}
	
	public Object getO() {
		return o;
	}
	public void setO(Object o) {
		this.o = o;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
