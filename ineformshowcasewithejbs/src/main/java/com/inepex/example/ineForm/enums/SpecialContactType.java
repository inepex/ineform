package com.inepex.example.ineForm.enums;



public enum SpecialContactType {
	Best,
	Good,
	ShouldBeKept,
	CanBeGetRidOf;
	
	public long getValueAsLong() {
		return (long)this.ordinal();
	}
	
	public static SpecialContactType valueOf(Long ordinalAsLong) {
		if (ordinalAsLong != null)
			for (SpecialContactType item : values())
				if (item.getValueAsLong() == ordinalAsLong)
					return item;

		throw new IllegalArgumentException();
	}
	
	public static String getValuesAsString(){
		String s = "";
		for (SpecialContactType item : values()){
			s += item.name() + ",";
		}
		if (s.length() > 0) s = s.substring(0, s.length()-1);
		return s;
	}
}
