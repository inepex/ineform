package com.inepex.ineFrame.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;


public abstract class CriteriaSelector<ResultType, RootType> {
	public final EntityManager em;
	public final CriteriaBuilder cb;
	public final CriteriaQuery<ResultType> cq;
	public final Root<RootType> root;
	public Boolean distinct = null;
	
	public CriteriaSelector(EntityManager em, Class<ResultType> resultClass, Class<RootType> rootClass) {
		this.em = em;
		cb = em.getCriteriaBuilder();
		cq = cb.createQuery(resultClass);
		root = cq.from(rootClass);
	}
	
	public TypedQuery<ResultType> getTypedQuery(){
		return em.createQuery(cq);		
	}
	
	public List<ResultType> executeSelect() {
		return getTypedQuery().getResultList();
	}

	public List<ResultType> executeRangeSelect(int firstResult, int numMaxResult) {
		return getTypedQuery().setFirstResult(firstResult)
							  .setMaxResults(numMaxResult)
							  .getResultList();
	}
	
	public void setDistinctIfNotForcedFalse() {
		cq.distinct(distinct == null || distinct);
	}
	
	/**
	 * Should only be called when ResultType is Long
	 */
	public Expression<Long> getCountExpression() {
		Path<Long> path = root.get("id");
		return cb.count(path);
	}

	
	
}
