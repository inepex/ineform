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
public class ModuleLangHandlerFactory implements HandlerFactoryI<ModuleLangHandlerFactory.ModuleLangHandler> { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new ModuleLangHandlerFactory(decStore)'
	 */
	@Inject
	public ModuleLangHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	@Override
	public ModuleLangHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(ModuleLangConsts.descriptorName);
		return new ModuleLangHandler(assistedObject, descriptorStore);
	}
	
	@Override
	public ModuleLangHandler createHandler(AssistedObject assistedObject) {
		if(!ModuleLangConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ModuleLangConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ModuleLangHandler(assistedObject, descriptorStore);
	}
	
	public ModuleLangSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(ModuleLangConsts.searchDescriptor);
		return new ModuleLangSearchHandler(assistedObject, descriptorStore);
	}
	
	public ModuleLangSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!ModuleLangConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ModuleLangConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ModuleLangSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class ModuleLangHandler extends AssistedObjectHandler {
	
		private ModuleLangHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public Relation getLang() {
    		return getRelation(ModuleLangConsts.k_lang);
        }	
        public void setLang(Relation lang) {
    		set(ModuleLangConsts.k_lang, lang);
        }
			
        public Relation getModule() {
    		return getRelation(ModuleLangConsts.k_module);
        }	
        public void setModule(Relation module) {
    		set(ModuleLangConsts.k_module, module);
        }
	}

	public static class ModuleLangSearchHandler extends AssistedObjectHandler {
	
		private ModuleLangSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
	}
}