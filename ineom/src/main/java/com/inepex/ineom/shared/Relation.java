package com.inepex.ineom.shared;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class Relation implements Serializable, IsSerializable {

    private static final long serialVersionUID = 4137968016737976837L;

    private Long id;
    private String displayName;
    private AssistedObject kvo;

    public Relation() {}

    /**
     * copy constructor (deep)
     * 
     * @param other
     */
    public Relation(Relation other) {
        this.id = new Long(other.id);
        if (other.displayName != null)
            this.displayName = new String(other.displayName);
        this.kvo = (other.kvo == null ? null : other.kvo.clone());
    }

    public Relation(Long id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public Relation(AssistedObject kvo) {
        this.kvo = kvo;
        this.id = kvo.getId();
    }

    public Relation(String displayName, AssistedObject kvo) {
        this(kvo);
        this.displayName = displayName;
    }

    public Relation(Long id, String displayName, AssistedObject kvo) {
        this(id, displayName);
        this.kvo = kvo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public AssistedObject getKvo() {
        return kvo;
    }

    public void setKvo(AssistedObject kvo) {
        this.kvo = kvo;
    }

    @Override
    public String toString() {
        if (kvo != null)
            return kvo.toString();

        return displayName; // + (IneFormProperties.showIds ? "(" + id + ")" :
                            // "");
    }

    public static Relation zeroOutIds(Relation rel) {
        if (rel == null)
            return null;
        rel.setId(IFConsts.NEW_ITEM_ID);
        if (rel.getKvo() != null)
            rel.getKvo().setId(IFConsts.NEW_ITEM_ID);
        return rel;
    }

    @JsonIgnore
    public boolean isNew() {
        return getKvo() != null && getKvo().isNew();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Relation other = (Relation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (kvo == null) {
            if (other.kvo != null)
                return false;
        } else if (!kvo.equals(other.kvo))
            return false;
        return true;
    }
}
