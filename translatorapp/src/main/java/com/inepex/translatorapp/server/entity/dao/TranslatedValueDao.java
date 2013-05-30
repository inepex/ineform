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
	public List<TranslatedValue> listForTranslatorPage(List<Long> userLangs, Integer firstResult, Integer maxResult) {
		String query = "select tv from TranslatedValue tv " +
				"where tv.lang.id in "+langIdListForQuery(userLangs);
		
		return em.get().createQuery(query)
				.setFirstResult(firstResult)
				.setMaxResults(maxResult)
				
				.getResultList();
	}

	private String langIdListForQuery(List<Long> userLangs) {
		if(userLangs==null || userLangs.isEmpty())
			return "(-1)";
		
		StringBuffer sb = new StringBuffer("(");
		for(Long l : userLangs) {
			if(sb.length()>1)
				sb.append(",");
			sb.append(l);
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	/*hc:customMethods*/
	//overrides and custom methods
	/*hc*/
		
}