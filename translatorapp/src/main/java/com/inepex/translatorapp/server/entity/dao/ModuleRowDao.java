package com.inepex.translatorapp.server.entity.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.dao.query.ModuleRowQuery;
import com.inepex.translatorapp.server.entity.mapper.ModuleRowMapper;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E) or remove(E). (Don't 
 * forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class ModuleRowDao extends BaseDao<ModuleRow> {
	
	private final DescriptorStore descStore;
	
	@Inject
	public ModuleRowDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<ModuleRow> getQuery() {
		return new ModuleRowQuery(descStore);
	}

	@Override
	public BaseMapper<ModuleRow> getMapper() {
		return new ModuleRowMapper(descStore);
	}

	@Override
	public CriteriaSelector<ModuleRow, ModuleRow> getSelector() {
		return new CriteriaSelector<ModuleRow, ModuleRow>(em, getQuery(), ModuleRow.class, ModuleRow.class);
	}

	@Override
	public CriteriaSelector<Long, ModuleRow> getCountSelector() {
		return new CriteriaSelector<Long, ModuleRow>(em, getQuery(), Long.class, ModuleRow.class);
	}

	@Override
	public Class<ModuleRow> getClazz() {
		return ModuleRow.class;
	}

	@Override
	public ModuleRow newInstance() {
		return new ModuleRow();
	}

	@SuppressWarnings("unchecked")
	public List<ModuleRow> listForPage(Integer firstResult, Integer maxResult, String magicString, Long moduleId) {
		StringBuffer query = new StringBuffer();
		
		query.append("select mr from ModuleRow mr where 1=1 ");
		
		if(moduleId!=null) {
			query.append("and mr.module.id = :moduleId ");
		}
		
		if(magicString!=null && magicString.length()>1) {
			query.append(" and (mr.key like :magicString or mr.description like :magicString " +
					"or exists (select 1 from TranslatedValue tv where tv.row.id=mr.id and " +
					"tv.value like :magicString)) ");
		}
		
		query.append("order by mr.key ");
		
		Query q = em.get().createQuery(query.toString());
		
		if(moduleId!=null) {
			q.setParameter("moduleId", moduleId);
		}
		
		if(magicString!=null && magicString.length()>1) {
			q.setParameter("magicString", "%"+magicString+"%");
		}
		
		return q.setFirstResult(firstResult)
				.setMaxResults(maxResult)
				.getResultList();
	}
	
	/*hc:customMethods*/
	//overrides and custom methods
	/*hc*/
		
}