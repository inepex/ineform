package com.inepex.ineForm.client.form.widgets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class RelationList {

    private final boolean supportsOrdering;

    private LinkedList<Relation> relations = new LinkedList<Relation>();

    private List<Relation> changes = new ArrayList<Relation>();
    private final AssistedObjectHandlerFactory handlerFactory;

    public RelationList(
        DescriptorStore descStore,
        String relationDescriptorName,
        boolean allowOrdering) {
        this.handlerFactory = new AssistedObjectHandlerFactory(descStore);

        if (allowOrdering) {
            supportsOrdering = descStore
                .getOD(relationDescriptorName)
                .containsKey(IFConsts.KEY_ORDERNUM);
        } else
            supportsOrdering = false;
    }

    public void setRelations(List<Relation> relations) {
        changes.clear();
        this.relations.clear();

        if (supportsOrdering) {
            TreeMap<Long, Relation> orderedRelationList = new TreeMap<Long, Relation>();
            for (Relation relation : relations) {
                orderedRelationList.put(
                    handlerFactory.createHandler(relation.getKvo()).getLong(IFConsts.KEY_ORDERNUM),
                    relation);
            }
            this.relations.addAll(orderedRelationList.values());
        } else {
            this.relations.addAll(relations);
        }
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public List<Relation> getChanges() {
        return changes;
    }

    public void deleteAll() {
        List<Relation> relCopy = new ArrayList<Relation>(relations);
        for (Relation r : relCopy)
            delete(r);
    }

    public void delete(Relation rel) {
        relations.remove(rel);
        rel.setKvo(null);
        updateOrders();
        if (changes.contains(rel))
            changes.remove(rel);
        else
            changes.add(rel);

    }

    public void moveUp(Relation rel) {
        int index = relations.indexOf(rel);
        move(rel, index - 1);
    }

    public void moveDown(Relation rel) {
        int index = relations.indexOf(rel);
        move(rel, index + 1);
    }

    public void move(Relation rel, int to) {
        if (to < 0 || to > relations.size() - 1)
            return;
        relations.remove(rel);
        relations.add(to, rel);
        updateOrders();
        if (!changes.contains(rel))
            changes.add(rel);
    }

    public void add(Relation rel) {
        rel.setId(IFConsts.NEW_ITEM_ID);
        relations.add(rel);
        updateOrders();
    }

    public boolean isSupportsOrdering() {
        return supportsOrdering;
    }

    private void updateOrders() {
        if (supportsOrdering) {
            for (int i = 0; i < relations.size(); i++) {
                if (relations.get(i).getKvo() != null) {
                    Long prevValue = handlerFactory
                        .createHandler(relations.get(i).getKvo())
                        .getLong(IFConsts.KEY_ORDERNUM);
                    handlerFactory.createHandler(relations.get(i).getKvo()).set(
                        IFConsts.KEY_ORDERNUM,
                        new Long(i));

                    if (!changes.contains(relations.get(i))
                        && (prevValue == null || prevValue.longValue() != new Long(i).longValue()))
                        changes.add(relations.get(i));
                }
            }
        }
    }

    public void change(Relation rel) {
        int index = changes.indexOf(rel);
        if (index != -1) {
            changes.remove(index);
            changes.add(index, rel);
        } else {
            changes.add(rel);
        }

        updateOrders();
    }

}
