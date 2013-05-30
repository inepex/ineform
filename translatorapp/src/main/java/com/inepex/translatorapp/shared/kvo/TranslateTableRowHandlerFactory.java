package com.inepex.translatorapp.shared.kvo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.HandlerFactoryI;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@Singleton
public class TranslateTableRowHandlerFactory implements HandlerFactoryI<TranslateTableRowHandlerFactory.TranslateTableRowHandler> { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new TranslateTableRowHandlerFactory(decStore)'
	 */
	@Inject
	public TranslateTableRowHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	@Override
	public TranslateTableRowHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(TranslateTableRowConsts.descriptorName);
		return new TranslateTableRowHandler(assistedObject, descriptorStore);
	}
	
	@Override
	public TranslateTableRowHandler createHandler(AssistedObject assistedObject) {
		if(!TranslateTableRowConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+TranslateTableRowConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new TranslateTableRowHandler(assistedObject, descriptorStore);
	}
	
	public TranslateTableRowSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(TranslateTableRowConsts.searchDescriptor);
		return new TranslateTableRowSearchHandler(assistedObject, descriptorStore);
	}
	
	public TranslateTableRowSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!TranslateTableRowConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+TranslateTableRowConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new TranslateTableRowSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class TranslateTableRowHandler extends AssistedObjectHandler {
	
		private TranslateTableRowHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public Boolean getRecent() {
        	return getBoolean(TranslateTableRowConsts.k_recent);
        }

        public void setRecent(Boolean recent) {
    		set(TranslateTableRowConsts.k_recent, recent);
        }
		
			
        public Boolean getOutDated() {
        	return getBoolean(TranslateTableRowConsts.k_outDated);
        }

        public void setOutDated(Boolean outDated) {
    		set(TranslateTableRowConsts.k_outDated, outDated);
        }
		
			
        public String getDescription() {
        	return getString(TranslateTableRowConsts.k_description);
        }

        public void setDescription(String description) {
    		set(TranslateTableRowConsts.k_description, description);
        }
		
			
        public String getEngVal() {
        	return getString(TranslateTableRowConsts.k_engVal);
        }

        public void setEngVal(String engVal) {
    		set(TranslateTableRowConsts.k_engVal, engVal);
        }
		
			
        public Relation getTranslatedValue() {
    		return getRelation(TranslateTableRowConsts.k_translatedValue);
        }	
        public void setTranslatedValue(Relation translatedValue) {
    		set(TranslateTableRowConsts.k_translatedValue, translatedValue);
        }
	}

	public static class TranslateTableRowSearchHandler extends AssistedObjectHandler {
	
		private TranslateTableRowSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}