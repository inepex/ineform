package com.inepex.ineFrame.shared.util;

import java.util.Comparator;

import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.Relation;

public class Comparators {
	public static class RelationOrderComparator implements Comparator<Relation> {
		final DescriptorStore descStore;
		public RelationOrderComparator(DescriptorStore descStore) {
			this.descStore = descStore;
		}
		

		@Override
		public int compare(Relation o1, Relation o2) {
			if (descStore.getOD(o1.getKvo().getDescriptorName()).containsKey(IFConsts.KEY_ORDERNUM)) {
				return o1.getKvo().getLong(IFConsts.KEY_ORDERNUM)
					.compareTo(o2.getKvo().getLong(IFConsts.KEY_ORDERNUM));
			}				
			else return o1.getId().compareTo(o2.getId());
		}
	};
}
