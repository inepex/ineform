package com.inepex.translatorapp.shared.kvo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.HandlerFactoryI;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@Singleton
public class LangHandlerFactory implements HandlerFactoryI<LangHandlerFactory.LangHandler> { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new LangHandlerFactory(decStore)'
	 */
	@Inject
	public LangHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	@Override
	public LangHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(LangConsts.descriptorName);
		return new LangHandler(assistedObject, descriptorStore);
	}
	
	@Override
	public LangHandler createHandler(AssistedObject assistedObject) {
		if(!LangConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+LangConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new LangHandler(assistedObject, descriptorStore);
	}
	
	public LangSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(LangConsts.searchDescriptor);
		return new LangSearchHandler(assistedObject, descriptorStore);
	}
	
	public LangSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!LangConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+LangConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new LangSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class LangHandler extends AssistedObjectHandler {
	
		private LangHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getIsoName() {
        	return getString(LangConsts.k_isoName);
        }

        public void setIsoName(String isoName) {
    		set(LangConsts.k_isoName, isoName);
        }
		
	}

	public static class LangSearchHandler extends AssistedObjectHandler {
	
		private LangSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getIsoName() {
        	return getString(LangConsts.s_isoName);
        }
        
        public void setIsoName(String isoName) {
    		set(LangConsts.s_isoName, isoName);
        }
	}
}