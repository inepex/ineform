package com.inepex.ineom.shared;

import java.util.List;

import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.util.SharedUtil;

public class AssistedObjectHandler extends AssistedObjectChecker {
	
	private final DescriptorStore descriptorStore;

	public AssistedObjectHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
		super(assistedObject, assistedObject.getDescriptorName(), descriptorStore.getOD(assistedObject.getDescriptorName()));
		this.descriptorStore=descriptorStore;
		
		String descriptorName = assistedObject.getDescriptorName();
		if (descriptorName == null) {
			throw new IllegalArgumentException();
		}

		ObjectDesc objectDescriptor = descriptorStore.getOD(assistedObject
				.getDescriptorName());
		if (objectDescriptor == null) {
			throw new IllegalArgumentException("No object descriptor registered for '"+descriptorName+"'");
		}
		
		
	}

	// -------------------------------------------------------
	// related methods
	// -------------------------------------------------------
	public Boolean getRelatedBoolean(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
		return handler.getBoolean(id.get(id.size() - 1));
	}

	public Double getRelatedDouble(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
		return handler.getDouble(id.get(id.size() - 1));
	}

	public IneList getRelatedList(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
		return handler.getList(id.get(id.size() - 1));
	}

	public Long getRelatedLongUnchecked(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
		return handler.getLongUnchecked(id.get(id.size() - 1));
	}

	public Long getRelatedLong(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
		return handler.getLong(id.get(id.size() - 1));
	}

	public Relation getRelatedRelation(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
		return handler.getRelation(id.get(id.size() - 1));
	}

	public String getRelatedString(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
		return handler.getString(id.get(id.size() - 1));
	}

	public String getRelatedStringUnchecked(String dotSeparetedKeys) {
		List<String> id = SharedUtil.listFromDotSeparated(dotSeparetedKeys);
		AssistedObjectChecker handler = getRelatedKVOMultiLevel(id);
		return handler.getStringUnchecked(id.get(id.size() - 1));
	}

	// -------------------------------------------------------
	// helper methods
	// -------------------------------------------------------

	public AssistedObjectHandler getRelatedKVOMultiLevel(List<String> path) {
		AssistedObjectHandler actual = this;
		for (int i = 0; i < path.size() - 1; i++) {
			Relation rel = actual.getRelation(path.get(i));
			if (rel == null) {
				rel = new Relation(IFConsts.NEW_ITEM_ID, "");
				actual.set(path.get(i), rel);
			}

			if (rel.getKvo() == null) {
				RelationFDesc relDescriptor = (RelationFDesc) descriptorStore
						.getOD(actual.getDescriptorName()).getField(
								path.get(i));
				rel.setKvo(new KeyValueObject(relDescriptor
						.getRelatedDescriptorName()));
			}

			actual = new AssistedObjectHandler(rel.getKvo(), descriptorStore);
		}
		
		return actual;
	}

	public AssistedObjectHandler getDifference(AssistedObjectHandler original) {
		if (original == null)
			return this;

		if (!descriptorName.equals(original.getDescriptorName()))
			throw new IllegalArgumentException();

		AssistedObjectHandler difference =
			new AssistedObjectHandler(new KeyValueObject(original.getDescriptorName(), original.getId()), descriptorStore);

		for (String key : objectDescriptor.getFields().keySet()) {
			FDesc fieldDesc = objectDescriptor.getFields().get(key);
			
			if(fieldDesc.getType()==IneT.RELATION &&
					IFConsts.customDescriptorName.equals(((RelationFDesc) fieldDesc).getRelatedDescriptorName())) {
				
				Relation orig = original.getRelation(key);
				Relation chng = getRelation(fieldDesc.getKey());
				if (orig == null && chng == null) {
					continue;
				} else {
					difference.set(key, chng);
					continue;
				}
			}
			
			switch (fieldDesc.getType()) {
			case BOOLEAN: {
				Boolean orig = original.getBoolean(key);
				Boolean chng = getBoolean(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng))
						difference.set(key, chng);
				}
				break;
			}
			case DOUBLE: {
				Double orig = original.getDouble(key);
				Double chng = getDouble(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng))
						difference.set(key, chng);
				}
				break;
			}
			case LIST: {
				// IMPORTANT: IneList always just contains the difference
				// after
				// edited in a form!
				IneList chng = getList(key);
				if (chng == null || chng.getRelationList() == null
						|| chng.getRelationList().size() == 0)
					continue;
				difference.set(key, chng);
				break;
			}
			case LONG: {
				Long orig = original.getLong(key);
				Long chng = getLong(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng))
						difference.set(key, chng);
				}
				break;
			}
			case RELATION: {
				Relation orig = original.getRelation(key);
				Relation chng = getRelation(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng)) {
						if (chng == null)
							difference.set(key, (Relation) null);
						else {
							// We should only add the fields to the
							// relations kvo that differ!
							Relation rel = new Relation(chng.getId(),
									chng.getDisplayName());
							if (chng.getKvo() != null) {
								AssistedObjectHandler hChng = new AssistedObjectHandler(chng
										.getKvo(), descriptorStore);
								AssistedObjectHandler hOrig = new AssistedObjectHandler(orig.getKvo(), descriptorStore);
								rel.setKvo(hChng.getDifference(hOrig).getAssistedObject());
							}
							difference.set(key, rel);
						}
					}
				}
				break;
			}
			case STRING: {
				String orig = original.getString(key);
				String chng = getString(key);
				if (orig == null) {
					if (chng == null)
						continue;
					difference.set(key, chng);
				} else {
					if (!orig.equals(chng))
						difference.set(key, chng);
				}
				break;
			}
			}

		}
		return difference;
	}

	
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
		Long l = getLong(key);
		if (l == null)
			return null;
		else
			return enumType.getEnumConstants()[l.intValue()];
	}
}