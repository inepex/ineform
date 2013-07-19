package com.inepex.translatorapp.server.entity.dao;

import java.util.List;

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
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.server.entity.dao.query.TranslatedValueQuery;
import com.inepex.translatorapp.server.entity.mapper.TranslatedValueMapper;
import com.inepex.translatorapp.shared.Consts;
import com.inepex.translatorapp.shared.action.TranslateListingType;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E) or remove(E). (Don't 
 * forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class TranslatedValueDao extends BaseDao<TranslatedValue> {
	
	private final DescriptorStore descStore;
	
	@Inject
	public TranslatedValueDao(Provider<EntityManager> em, ManipulationObjectFactory objectFactory
		, AssistedObjectHandlerFactory handlerFactory
		, DescriptorStore descStore){
		super(em, objectFactory, handlerFactory);
		this.descStore=descStore;
	}

	@Override
	public BaseQuery<TranslatedValue> getQuery() {
		return new TranslatedValueQuery(descStore);
	}

	@Override
	public BaseMapper<TranslatedValue> getMapper() {
		return new TranslatedValueMapper(descStore);
	}

	@Override
	public CriteriaSelector<TranslatedValue, TranslatedValue> getSelector() {
		return new CriteriaSelector<TranslatedValue, TranslatedValue>(em, getQuery(), TranslatedValue.class, TranslatedValue.class);
	}

	@Override
	public CriteriaSelector<Long, TranslatedValue> getCountSelector() {
		return new CriteriaSelector<Long, TranslatedValue>(em, getQuery(), Long.class, TranslatedValue.class);
	}

	@Override
	public Class<TranslatedValue> getClazz() {
		return TranslatedValue.class;
	}

	@Override
	public TranslatedValue newInstance() {
		return new TranslatedValue();
	}

	@SuppressWarnings("unchecked")
	public List<TranslatedValue> listForTranslatorPage(boolean data, List<Long> userLangs, Integer firstResult, Integer maxResult, String moduleName, TranslateListingType listType) {
		StringBuffer query = new StringBuffer();
		
		if(data)
			query.append("select tv from TranslatedValue tv ");
		else
			query.append("select tv.id from TranslatedValue tv ");
		
		query.append("where 1=1 ");
		
		if(userLangs!=null && !userLangs.isEmpty()) {
			query.append("and tv.lang.id in ");
			query.append(longIdListForQuery(userLangs));
		}
		
		if(moduleName!=null) {
			if(!moduleName.matches("[\\p{Alnum}]+")) {
				throw new IllegalArgumentException("Invalid name:" + moduleName);
			}
			query.append("and tv.row.module.name = '");
			query.append(moduleName);
			query.append("' ");
		}
		
		switch (listType) {
		case Recent:
			query.append("and tv.lastModTime > ");
			query.append(System.currentTimeMillis()-Consts.recentTimeRange);
			query.append(" ");
			query.append("order by tv.lastModTime DESC");
			break;
			
		case Outdated:
			query.append("and (tv.value is NULL or length(tv.value) = 0 or ");
			
			query.append("not exists (select 1 from TranslatedValue tv2 where tv2.lang.isoName = '");
				query.append(Consts.defaultLang);
				query.append("' ");
				query.append("and tv2.row.id = tv.row.id) ");
				
			query.append("or exists (select 1 from TranslatedValue tv2 where tv2.lang.isoName = '");
				query.append(Consts.defaultLang);
				query.append("' ");
				query.append("and tv2.row.id = tv.row.id and tv2.lastModTime > tv.lastModTime) ");
			
			query.append(") ");
			query.append("order by tv.row.key ASC, tv.lang.isoName ASC");
			break;
			
		case All:
		default:
			query.append("order by tv.row.key ASC, tv.lang.isoName ASC");
			break;
		}
		
		return em.get().createQuery(query.toString())
				.setFirstResult(firstResult)
				.setMaxResults(maxResult)
				.getResultList();
	}
	
	private String longIdListForQuery(List<Long> ids) {
		StringBuffer sb = new StringBuffer("(");
		for(Long l : ids) {
			if(sb.length()>1)
				sb.append(",");
			sb.append(l);
		}
		sb.append(")");
		
		return sb.toString();
	}

	public Object[] countForLangAndModule(Long langId, Long moduleId) {
		if(langId==null || moduleId==null)
			throw new IllegalArgumentException();
		
		String query = "select " +
				"sum(case when tv.value is NULL or length(tv.value) = 0 then 1 else 0 end), " +
				"sum(case when tv.value is NULL or length(tv.value) = 0 then 0 else 1 end) " +
				"from TranslatedValue tv " +
				"where tv.row.module.id=:moduleId and tv.lang.id=:langId";
		
		return (Object[]) em.get().createQuery(query.toString())
				.setParameter("moduleId", moduleId)
				.setParameter("langId", langId)
				.getSingleResult();
	}
	
	/*hc:customMethods*/
	//overrides and custom methods
	/*hc*/
		
}