package com.inepex.ineom.shared.assistedobject.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class AssistedObjectSorter {

	private static abstract class AbstractComparator implements Comparator<AssistedObject>{

		protected AssistedObjectHandlerFactory aoHandlerFactory;
		protected String key;
				
		public AbstractComparator(String key, AssistedObjectHandlerFactory aoHandlerFactory) {
			super();
			this.key = key;
			this.aoHandlerFactory = aoHandlerFactory;
		}

		@Override
		public int compare(AssistedObject o1Ao, AssistedObject o2Ao) {
			AssistedObjectHandler o1 = aoHandlerFactory.createHandler(o1Ao);
			AssistedObjectHandler o2 = aoHandlerFactory.createHandler(o2Ao);
			if (!o1.getKeys().contains(key)){
				return 1;
			} else if (!o2.getKeys().contains(key)){
				return -1;
			}
			return compareField(o1, o2);
		}
		
		protected abstract int compareField(AssistedObjectHandler o1, AssistedObjectHandler o2);
		
	}
	public static class StringComparator extends AbstractComparator {
				
		public StringComparator(String key, AssistedObjectHandlerFactory aoHandlerFactory) {
			super(key, aoHandlerFactory);
		}

		@Override
		protected int compareField(AssistedObjectHandler o1, AssistedObjectHandler o2) {
			if (o1.getString(key) == null) return 1;
			if (o2.getString(key) == null) return -1;
			return o1.getString(key).compareTo(o2.getString(key));
		}
		
	}
	
	public static class BooleanComparator extends AbstractComparator {
		
		public BooleanComparator(String key, AssistedObjectHandlerFactory aoHandlerFactory) {
			super(key, aoHandlerFactory);
		}

		@Override
		protected int compareField(AssistedObjectHandler o1, AssistedObjectHandler o2) {
			if (o1.getBoolean(key) == null) return 1;
			if (o2.getBoolean(key) == null) return -1;
			return o1.getBoolean(key).compareTo(o2.getBoolean(key));
		}
		
	}
	
	public static class LongComparator extends AbstractComparator {
		
		public LongComparator(String key, AssistedObjectHandlerFactory aoHandlerFactory) {
			super(key, aoHandlerFactory);
		}

		@Override
		protected int compareField(AssistedObjectHandler o1, AssistedObjectHandler o2) {
			if (o1.getLong(key) == null) return 1;
			if (o2.getLong(key) == null) return -1;
			return o1.getLong(key).compareTo(o2.getLong(key));
		}
		
	}
	
	public static class DoubleComparator extends AbstractComparator {
		
		public DoubleComparator(String key, AssistedObjectHandlerFactory aoHandlerFactory) {
			super(key, aoHandlerFactory);
		}

		@Override
		protected int compareField(AssistedObjectHandler o1, AssistedObjectHandler o2) {
			if (o1.getDouble(key) == null) return 1;
			if (o2.getDouble(key) == null) return -1;
			return o1.getDouble(key).compareTo(o2.getDouble(key));
		}
		
	}
	
	public static class RelationComparator extends AbstractComparator {
		
		public RelationComparator(String key, AssistedObjectHandlerFactory aoHandlerFactory) {
			super(key, aoHandlerFactory);
		}

		@Override
		protected int compareField(AssistedObjectHandler o1, AssistedObjectHandler o2) {
			if (o1.getRelation(key) == null) return 1;
			if (o2.getRelation(key) == null) return -1;
			return o1.getRelation(key).getDisplayName().compareTo(o2.getRelation(key).getDisplayName());
		}
		
	}
	
	private static final Logger _logger = LoggerFactory.getLogger(AssistedObjectSorter.class);
	
	private final DescriptorStore descriptorStore;
	private final AssistedObjectHandlerFactory aoHandlerFactory;
	private Map<String, Comparator<AssistedObject>> customComparatorByKey = new HashMap<>();
	
	@Inject
	public AssistedObjectSorter(DescriptorStore descriptorStore,
			AssistedObjectHandlerFactory aoHandlerFactory) {
		this.descriptorStore = descriptorStore;
		this.aoHandlerFactory = aoHandlerFactory;
	}
	
	@SuppressWarnings("incomplete-switch")
	public List<AssistedObject> sort(String key, List<AssistedObject> list, Boolean isDescending){
		if (list.size() == 0 || key == null) return list;
		if (isDescending == null) isDescending = false;
		
		ObjectDesc objectDesc = descriptorStore.getOD(list.get(0).getDescriptorName());
		if (objectDesc == null){
			_logger.error("ObjectDesc not found: {}", list.get(0).getDescriptorName());
			return list;
		}
		
		FDesc fieldDesc = objectDesc.getField(key);
		if (fieldDesc == null){
			_logger.error("FieldDesc not found {} ({})", new String[] {list.get(0).getDescriptorName(), key});
			return list;
		}
		
		Comparator<AssistedObject> comparator = new StringComparator(key, aoHandlerFactory);
		
		if (customComparatorByKey.containsKey(key)) {
			comparator = customComparatorByKey.get(comparator);
		}
		
		switch (fieldDesc.getType()){
			case BOOLEAN:
				comparator = new BooleanComparator(key, aoHandlerFactory);
				break;
			case LONG:
				comparator = new LongComparator(key, aoHandlerFactory);
				break;
			case DOUBLE:
				comparator = new DoubleComparator(key, aoHandlerFactory);
				break;
			case RELATION:
				comparator = new RelationComparator(key, aoHandlerFactory);
				break;
		
		}
		Collections.sort(list, comparator);
		if (isDescending) Collections.reverse(list);
		return list;
	}
	
	public void addCustomComparator(String key, Comparator<AssistedObject> comparator){
		customComparatorByKey.put(key, comparator);
	}
	
	
}
