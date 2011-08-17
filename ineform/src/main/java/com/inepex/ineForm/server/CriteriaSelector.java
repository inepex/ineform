package com.inepex.ineForm.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.inject.Provider;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;


public class CriteriaSelector<ResultType, RootType> {
	public final Provider<EntityManager> em;
	public final CriteriaBuilder cb;
	public final CriteriaQuery<ResultType> cq;
	public final Root<RootType> root;
	public Boolean distinct = null;
	BaseQuery<RootType> query;
	
	public CriteriaSelector(Provider<EntityManager> em, BaseQuery<RootType> query, Class<ResultType> resultClass, Class<RootType> rootClass) {
		this.em = em;
		this.query = query;
		cb = em.get().getCriteriaBuilder();
		cq = cb.createQuery(resultClass);
		root = cq.from(rootClass);
	}
	
	public TypedQuery<ResultType> getTypedQuery(){
		return em.get().createQuery(cq);		
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
	
	public void buildDefaultQuery(AbstractSearchAction action) {
		if (action.getSearchParameters() != null) {
			Expression<Boolean> expr = null;
			expr = query.buildWhere(action, cb, root, expr);
			if (expr != null) {
				Predicate exisitngRestriction = cq.getRestriction();
				if (exisitngRestriction != null)
					expr = cb.and(expr, exisitngRestriction);
								
				cq.where(expr);
			}
		}
	}
	
	public void orderBy(AbstractSearchAction action) {
		cq.orderBy(query.getOrderExpression(action, cb, root));
	}

	
	
}
