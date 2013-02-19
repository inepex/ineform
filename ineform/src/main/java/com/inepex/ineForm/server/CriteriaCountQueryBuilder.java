package com.inepex.ineForm.server;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class CriteriaCountQueryBuilder<BaseType> {
	
	public Long countForInterval(EntityManager em, Class<BaseType> baseClass, SingularAttribute<BaseType, Long> attribute, 
			long from, long to){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<BaseType> root = cq.from(baseClass);
		cq.select(cb.count(root));
		cq.where(cb.and(cb.greaterThanOrEqualTo(root.get(attribute), from),
				cb.lessThanOrEqualTo(root.get(attribute), to)));
		return em.createQuery(cq).getSingleResult();
	}

}
