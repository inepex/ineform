package com.inepex.ineom.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IneList implements Serializable, IsSerializable {

    /**
     *
     */
    private static final long serialVersionUID = -5029631076123849581L;

    private List<Relation> relationList = null;

    /**
     * copy constructor (deep)
     * 
     * @param other
     */
    public IneList(IneList other) {
        relationList = new ArrayList<Relation>();
        for (Relation r : other.getRelationList()) {
            relationList.add(new Relation(r));
        }

    }

    public IneList() {
        relationList = new ArrayList<Relation>();

    }

    public IneList(List<Relation> relationList) {
        this.relationList = relationList;

    }

    public List<Relation> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<Relation> relationList) {
        this.relationList = relationList;
    }

    @Override
    public String toString() {
        return relationList.size() + " items";
    }

    public static IneList zeroOutAllRelationIds(IneList list) {
        if (list == null || list.getRelationList() == null)
            return list;

        for (Relation rel : list.getRelationList()) {
            Relation.zeroOutIds(rel);
        }
        return list;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((relationList == null) ? 0 : relationList.hashCode());
        return result;
    }

    @Override
    /**
     * not generated
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IneList other = (IneList) obj;
        if (relationList == null) {
            if (other.relationList != null)
                return false;
        }
        if (relationList.size() != other.relationList.size())
            return false;

        for (int i = 0; i < relationList.size(); i++) {
            if (!relationList.get(i).equals(other.relationList.get(i)))
                return false;
        }

        return true;
    }

}
