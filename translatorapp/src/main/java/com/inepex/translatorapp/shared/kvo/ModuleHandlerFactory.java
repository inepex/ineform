package com.inepex.translatorapp.shared.kvo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.HandlerFactoryI;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

@Singleton
public class ModuleHandlerFactory implements HandlerFactoryI<ModuleHandlerFactory.ModuleHandler> { 

	private final DescriptorStore descriptorStore;

	/**
	 * can be injected or created with 'new ModuleHandlerFactory(decStore)'
	 */
	@Inject
	public ModuleHandlerFactory(DescriptorStore descriptorStore) {
		this.descriptorStore = descriptorStore;
	}
	
	@Override
	public ModuleHandler createHandler() {
		AssistedObject assistedObject = new KeyValueObject(ModuleConsts.descriptorName);
		return new ModuleHandler(assistedObject, descriptorStore);
	}
	
	@Override
	public ModuleHandler createHandler(AssistedObject assistedObject) {
		if(!ModuleConsts.descriptorName.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ModuleConsts.descriptorName+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ModuleHandler(assistedObject, descriptorStore);
	}
	
	public ModuleSearchHandler createSearchHandler() {
		AssistedObject assistedObject = new KeyValueObject(ModuleConsts.searchDescriptor);
		return new ModuleSearchHandler(assistedObject, descriptorStore);
	}
	
	public ModuleSearchHandler createSearchHandler(AssistedObject assistedObject) {
		if(!ModuleConsts.searchDescriptor.equals(assistedObject.getDescriptorName()))
			throw new IllegalArgumentException("Type incompatibility: handler: '"+ModuleConsts.searchDescriptor+
				"' assistedObject: '"+assistedObject.getDescriptorName()+"'");
		
		return new ModuleSearchHandler(assistedObject, descriptorStore);
	}
	
	public static class ModuleHandler extends AssistedObjectHandler {
	
		private ModuleHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
			
        public String getName() {
        	return getString(ModuleConsts.k_name);
        }

        public void setName(String name) {
    		set(ModuleConsts.k_name, name);
        }
		
			
    	public IneList getRows() {
    		return getList(ModuleConsts.k_rows);
        }
        public void setRows(IneList rows) {
    		set(ModuleConsts.k_rows, rows);
        }	
			
    	public IneList getLangs() {
    		return getList(ModuleConsts.k_langs);
        }
        public void setLangs(IneList langs) {
    		set(ModuleConsts.k_langs, langs);
        }	
	}

	public static class ModuleSearchHandler extends AssistedObjectHandler {
	
		private ModuleSearchHandler(AssistedObject assistedObject, DescriptorStore descriptorStore) {
			super(assistedObject, descriptorStore);
		}
	
        public String getName() {
        	return getString(ModuleConsts.s_name);
        }
        
        public void setName(String name) {
    		set(ModuleConsts.s_name, name);
        }
	}
}