package com.inepex.ineom.shared;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.AssistedObjectChecker;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.util.SharedUtil;

@Singleton
public class AssistedObjectHandlerFactory {

	private final DescriptorStore descriptorStore;

	@Inject
	public AssistedObjectHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}

	public AssistedObjectHandler createHandler(AssistedObject assistedObject) {
		return new AssistedObjectHandler(assistedObject);
	}
	
	public AssistedObjectChecker createChecker(AssistedObject assistedObject) {
		String descriptorName = assistedObject.getDescriptorName();
		if (descriptorName == null) {
			// TODO some logic
		}

		ObjectDesc objectDescriptor = descriptorStore.getOD(assistedObject
				.getDescriptorName());
		if (objectDescriptor == null) {
			// TODO some logic
		}
		
		return new AssistedObjectChecker(assistedObject, descriptorName, objectDescriptor);
	}

	public class AssistedObjectHandler {

		private final AssistedObjectChecker checker;

		private AssistedObjectHandler(AssistedObject assistedObject) {
			this.checker = createChecker(assistedObject);
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
		public String getValueAsString(String key) {
			Object o;

			switch (checker.objectDescriptor.getField(key).getType()) {
			case BOOLEAN:
				o = checker.getBoolean(key) ? IFConsts.TRUE
						: IFConsts.FALSE;
				return o == null ? null : o.toString();

			case DOUBLE:
				o = checker.getDouble(key);
				return o == null ? null : o.toString();

			case LIST:
				o = checker.getList(key);
				return o == null ? null : o.toString();

			case LONG:
				o = checker.getLong(key);
				return o == null ? null : o.toString();

			case RELATION:
				Relation r = checker.getRelation(key);
				return r == null ? null : r.getDisplayName();

			case STRING:
				o = checker.getString(key);
				return o == null ? null : o.toString();
			}

			return null;
		}

		public AssistedObjectChecker getRelatedKVOMultiLevel(List<String> path) {
			AssistedObjectChecker actual = checker;
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

				actual = new AssistedObjectChecker(rel.getKvo(), rel.getKvo().getDescriptorName(),
						descriptorStore.getOD(rel.getKvo().getDescriptorName()));
			}
			
			return actual;
		}

		public AssistedObjectChecker getDifference(AssistedObjectChecker original) {
			if (original == null)
				return checker;

			if (!checker.descriptorName.equals(original.getDescriptorName()))
				throw new IllegalArgumentException();

			AssistedObjectChecker difference =
				new AssistedObjectChecker(new KeyValueObject(
						original.getDescriptorName(), original.getId()), original.getDescriptorName(),
						descriptorStore.getOD(original.getDescriptorName()));

			for (String key : checker.objectDescriptor.getFields().keySet()) {
				FDesc fieldDesc = checker.objectDescriptor.getFields().get(key);
				switch (fieldDesc.getType()) {
				case BOOLEAN: {
					Boolean orig = original.getBoolean(key);
					Boolean chng = checker.getBoolean(key);
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
					Double chng = checker.getDouble(key);
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
					IneList chng = checker.getList(key);
					if (chng == null || chng.getRelationList() == null
							|| chng.getRelationList().size() == 0)
						continue;
					difference.set(key, chng);
					break;
				}
				case LONG: {
					Long orig = original.getLong(key);
					Long chng = checker.getLong(key);
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
					Relation chng = checker.getRelation(key);
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
									AssistedObjectHandler hChng = createHandler(chng
											.getKvo());
									AssistedObjectChecker hOrig = createChecker(orig.getKvo());
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
					String chng = checker.getString(key);
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
			Long l = checker.getLong(key);
			if (l == null)
				return null;
			else
				return enumType.getEnumConstants()[l.intValue()];
		}
	}
}
