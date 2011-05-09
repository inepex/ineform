package com.inepex.example.ineForm.dto.kvo;

import java.util.List;

import com.inepex.example.ineForm.dto.ContactDto;
import com.inepex.example.ineForm.enums.SpecialContactType;
import com.inepex.ineom.shared.kvo.DtoAdapter;
import com.inepex.ineom.shared.kvo.Relation;

public class ContactDtoKVO extends ContactDto {

	private static final long serialVersionUID = 1L;

	public static final String descriptorName = "contactDtoDescriptor";

	List<String> keys = null;

	public static final String k_name = "name";
	public static final String k_relatedRandomValue = "relatedRandomValue";
	public static final String k_verySecretParameter = "verySecretParameter";
	public static final String k_specCont = "specCont";
	public static final String k_contactDetails = "contactDetails";

	@Override
	public String getDescriptorName() {
		return descriptorName;
	}
	
	@Override
	public List<String> getKeys() {
		if (keys == null) {
			keys.add(k_name);
			keys.add(k_relatedRandomValue);
			keys.add(k_verySecretParameter);
			keys.add(k_specCont);
			keys.add(k_contactDetails);
		}	
		return keys;
	}
	
		@Override
	public DtoAdapter clone() {
		ContactDtoKVO cloned = new ContactDtoKVO();
		cloned.setName(getName() == null ? null : new String(getName()));
		cloned.setRelatedRandomValue(getRelatedRandomValue() == null ? null : new Long(getRelatedRandomValue()));
		cloned.setVerySecretParameter(getVerySecretParameter() == null ? null : new String(getVerySecretParameter()));
		cloned.setSpecCont(getSpecCont());
		cloned.setContactDetails(getContactDetails() == null ? null : new Relation(getContactDetails()));
		return cloned;
	}

	@Override
	protected void setObject(String key, Object o) {
		if (key.equals(k_name)) {
			setName((String)o);
			return;
		}
		if (key.equals(k_name)) {
			setRelatedRandomValue((Long)o);
			return;
		}
		if (key.equals(k_name)) {
			setVerySecretParameter((String)o);
			return;
		}
		if (key.equals(k_name)) {
			setSpecCont(SpecialContactType.values()[((Long)o).intValue()]);
		}
		if (key.equals(k_name)) {
			setContactDetails((Relation)o);
			return;
		}
	}
	
	
	@Override
	protected Object getObject(String key) {
		if (key.equals(k_name))
			return getName();
		if (key.equals(k_name))
			return getRelatedRandomValue();
		if (key.equals(k_name))
			return getVerySecretParameter();
		if (key.equals(k_name))
			return new Long(getSpecCont().ordinal());
		if (key.equals(k_name))
			return getContactDetails();
		return null;
	}
}
