package com.inepex.ineom.shared.assistedobject.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.inject.Inject;
import com.inepex.ineom.shared.Relation;

public class RelationListSorter {

    private static class RelComparator implements Comparator<Relation> {

        public RelComparator() {}

        @Override
        public int compare(Relation rel1, Relation rel2) {
            if (rel1.getDisplayName() == null && rel2.getDisplayName() == null)
                return 0;
            if (rel1.getDisplayName() == null)
                return 1;
            if (rel2.getDisplayName() == null)
                return -1;
            return rel1.getDisplayName().compareTo(rel2.getDisplayName());
        }

    }

    @Inject
    public RelationListSorter() {}

    public List<Relation> sort(List<Relation> list, Boolean isDescending) {
        if (list.size() == 0)
            return list;
        if (isDescending == null)
            isDescending = false;
        RelComparator comparator = new RelComparator();
        Collections.sort(list, comparator);
        if (isDescending)
            Collections.reverse(list);
        return list;
    }
}
