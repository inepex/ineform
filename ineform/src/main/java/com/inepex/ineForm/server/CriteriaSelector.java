package com.inepex.ineForm.server;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.google.inject.Provider;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearchAction;

public class CriteriaSelector<ResultType, RootType> extends CustomDaoCriteriaSelector<ResultType, RootType> {
	
	BaseQuery<RootType> query;
	
	public CriteriaSelector(Provider<EntityManager> em, BaseQuery<RootType> query, Class<ResultType> resultClass, Class<RootType> rootClass) {
		super(em, resultClass, rootClass);
		
		this.query = query;
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
