package com.inepex.translatorapp.server.entity.dao;

import java.util.Iterator;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineForm.server.BaseDao;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineForm.server.CriteriaSelector;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.server.entity.dao.query.ModuleQuery;
import com.inepex.translatorapp.server.entity.mapper.ModuleMapper;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E) or remove(E). (Don't 
 * forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class ModuleDao extends BaseDao<Module> {
	
	private final DescriptorStore descStore;
	
	@Inject
	public ModuleDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<Module> getQuery() {
		return new ModuleQuery(descStore);
	}

	@Override
	public BaseMapper<Module> getMapper() {
		return new ModuleMapper(descStore);
	}

	@Override
	public CriteriaSelector<Module, Module> getSelector() {
		return new CriteriaSelector<Module, Module>(em, getQuery(), Module.class, Module.class);
	}

	@Override
	public CriteriaSelector<Long, Module> getCountSelector() {
		return new CriteriaSelector<Long, Module>(em, getQuery(), Long.class, Module.class);
	}

	@Override
	public Class<Module> getClazz() {
		return Module.class;
	}

	@Override
	public Module newInstance() {
		return new Module();
	}

	public void removeLang(Long langId, Long moduleId) {
		if(langId==null || moduleId==null)
			throw new IllegalArgumentException();
		
		Module module = findById(moduleId);
		
		Iterator<ModuleLang> mlIterator = module.getLangs().iterator();
		while(mlIterator.hasNext()) {
			ModuleLang ml = mlIterator.next();
			if(ml.getLang().getId().equals(langId)) {
				mlIterator.remove();
				break;
			}
		}
		
		for(ModuleRow mr : module.getRows()) {
			Iterator<TranslatedValue> tvIterator = mr.getValues().iterator();
			while(tvIterator.hasNext()) {
				TranslatedValue tv = tvIterator.next();
				if(tv.getLang().getId().equals(langId)) {
					tvIterator.remove();
					break;
				}
			}
		}
		
		mergeTrans(module);
	}

	public void addLang(Long langId, Long moduleId, Long userId) {
		if(userId==null || langId==null || moduleId==null)
			throw new IllegalArgumentException();
		
		Module module = findById(moduleId);
		ModuleLang ml = new ModuleLang();
		ml.setModule(module);
		ml.setLang(new Lang(langId));
		module.getLangs().add(ml);

		long now = System.currentTimeMillis();
		for(ModuleRow mr : module.getRows()) {
			TranslatedValue tv = new TranslatedValue();
			tv.setLang(new Lang(langId));
			tv.setLastModTime(now);
			tv.setLastModUser(new User(userId));
			tv.setValue("");
			tv.setRow(mr);
			
			mr.getValues().add(tv);
		}
		
		mergeTrans(module);
	}
	
	/*hc:customMethods*/
	//overrides and custom methods
	/*hc*/
		
}