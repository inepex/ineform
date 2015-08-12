package com.inepex.ineom.shared;

import com.google.inject.Inject;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.PropFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class AssistedObjectDifference {

    private final JsonDifference jsonDifference;
    private final DescriptorStore descriptorStore;
    private final AssistedObjectHandlerFactory aoHandlerFactory;

    @Inject
    public AssistedObjectDifference(
        JsonDifference jsonDifference,
        DescriptorStore descriptorStore,
        AssistedObjectHandlerFactory aoHandlerFactory) {
        super();
        this.jsonDifference = jsonDifference;
        this.descriptorStore = descriptorStore;
        this.aoHandlerFactory = aoHandlerFactory;
    }

    public AssistedObjectHandler getDifference(AssistedObject original, AssistedObject modified) {
        return getDifference(
            aoHandlerFactory.createHandler(original),
            aoHandlerFactory.createHandler(modified));

    }

    public AssistedObjectHandler getDifference(
        AssistedObjectHandler original,
        AssistedObjectHandler modified) {
        if (original == null)
            return modified;

        if (!modified.getDescriptorName().equals(original.getDescriptorName()))
            throw new IllegalArgumentException();

        AssistedObjectHandler difference =
            new AssistedObjectHandler(new KeyValueObject(
                original.getDescriptorName(),
                original.getId()), descriptorStore);

        for (String key : modified.objectDescriptor.getFields().keySet()) {
            FDesc fieldDesc = modified.objectDescriptor.getFields().get(key);

            switch (fieldDesc.getType()) {
                case BOOLEAN: {
                    Boolean orig = original.getBoolean(key);
                    Boolean chng = modified.getBoolean(key);
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
                    Double chng = modified.getDouble(key);
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
                    IneList chng = modified.getList(key);
                    if (chng == null
                        || chng.getRelationList() == null
                        || chng.getRelationList().size() == 0)
                        continue;
                    difference.set(key, chng);
                    break;
                }
                case LONG: {
                    Long orig = original.getLong(key);
                    Long chng = modified.getLong(key);
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
                    Relation chng = modified.getRelation(key);
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
                                Relation rel = new Relation(chng.getId(), chng.getDisplayName());
                                if (chng.getKvo() != null) {
                                    if (orig.getKvo() == null) {
                                        throw new RuntimeException(
                                            "Corrupted widget. Dont modify the related object through the entity!");
                                    }
                                    AssistedObjectHandler hChng =
                                        new AssistedObjectHandler(chng.getKvo(), descriptorStore);
                                    AssistedObjectHandler hOrig =
                                        new AssistedObjectHandler(orig.getKvo(), descriptorStore);
                                    rel.setKvo(getDifference(hOrig, hChng).getAssistedObject());
                                }
                                difference.set(key, rel);
                            }
                        }
                    }
                    break;
                }
                case STRING: {
                    String orig = original.getString(key);
                    String chng = modified.getString(key);
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
                case PROP: {
                    if (jsonDifference != null) {
                        String id = ((PropFDesc) fieldDesc).getId();
                        String origString = original.getAssistedObject().getPropsJson(id);
                        String newString = modified.getAssistedObject().getPropsJson(id);
                        difference.getAssistedObject().setPropsJson(
                            id,
                            jsonDifference.difference(origString, newString));
                    }
                    break;
                }
            }

        }
        return difference;
    }

}
