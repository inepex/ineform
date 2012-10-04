package com.inepex.ineFrame.shared.util;

import java.util.Comparator;

import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class Comparators {
	
	public static class RelationOrderComparator implements Comparator<Relation> {
		private final DescriptorStore descStore;
		private final AssistedObjectHandlerFactory objectHandlerFactory;
		
		public RelationOrderComparator(DescriptorStore descStore, AssistedObjectHandlerFactory objectHandlerFactory) {
			this.descStore = descStore;
			this.objectHandlerFactory=objectHandlerFactory;
		}

		@Override
		public int compare(Relation o1, Relation o2) {
			if (descStore.getOD(o1.getKvo().getDescriptorName()).containsKey(IFConsts.KEY_ORDERNUM)) {
				return objectHandlerFactory.createHandler(o1.getKvo()).getLong(IFConsts.KEY_ORDERNUM)
					.compareTo(objectHandlerFactory.createHandler(o2.getKvo()).getLong(IFConsts.KEY_ORDERNUM));
			}				
			else return o1.getId().compareTo(o2.getId());
		}
	};
}
