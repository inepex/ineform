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
public class TranslatedValueHandlerFactory implements HandlerFactoryI<TranslatedValueHandlerFactory.TranslatedValueHandler> { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new TranslatedValueHandlerFactory(decStore)'
	 */
	@Inject
	public TranslatedValueHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	@Override
	public TranslatedValueHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(TranslatedValueConsts.descriptorName);
		return new TranslatedValueHandler(assistedObject, descriptorStore);
	}
	
	@Override
	public TranslatedValueHandler createHandler(AssistedObject assistedObject) {
		if(!TranslatedValueConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+TranslatedValueConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new TranslatedValueHandler(assistedObject, descriptorStore);
	}
	
	public TranslatedValueSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(TranslatedValueConsts.searchDescriptor);
		return new TranslatedValueSearchHandler(assistedObject, descriptorStore);
	}
	
	public TranslatedValueSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!TranslatedValueConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+TranslatedValueConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new TranslatedValueSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class TranslatedValueHandler extends AssistedObjectHandler {
	
		private TranslatedValueHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public Long getLastModTime() {
        	return getLong(TranslatedValueConsts.k_lastModTime);
        }

        public void setLastModTime(Long lastModTime) {
    		set(TranslatedValueConsts.k_lastModTime, lastModTime);
        }
		
			
        public Relation getLastModUser() {
    		return getRelation(TranslatedValueConsts.k_lastModUser);
        }	
        public void setLastModUser(Relation lastModUser) {
    		set(TranslatedValueConsts.k_lastModUser, lastModUser);
        }
			
        public String getValue() {
        	return getString(TranslatedValueConsts.k_value);
        }

        public void setValue(String value) {
    		set(TranslatedValueConsts.k_value, value);
        }
		
			
        public Relation getLang() {
    		return getRelation(TranslatedValueConsts.k_lang);
        }	
        public void setLang(Relation lang) {
    		set(TranslatedValueConsts.k_lang, lang);
        }
			
        public Relation getRow() {
    		return getRelation(TranslatedValueConsts.k_row);
        }	
        public void setRow(Relation row) {
    		set(TranslatedValueConsts.k_row, row);
        }
	}

	public static class TranslatedValueSearchHandler extends AssistedObjectHandler {
	
		private TranslatedValueSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public Relation getLang() {
    		return getRelation(TranslatedValueConsts.s_lang);
        }	
        public void setLang(Relation lang) {
    		set(TranslatedValueConsts.s_lang, lang);
        }
        public Relation getRow() {
    		return getRelation(TranslatedValueConsts.s_row);
        }	
        public void setRow(Relation row) {
    		set(TranslatedValueConsts.s_row, row);
        }
	}
}