package com.inepex.ineForm.server;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;

public abstract class BaseQuery<E> {

    public static Expression<Boolean> addAndExpression(
        CriteriaBuilder cb,
        Expression<Boolean> base,
        Expression<Boolean> toAdd) {
        if (base == null)
            base = toAdd;
        else
            base = cb.and(base, toAdd);
        return base;
    }

    public static Expression<Boolean> addOrExpression(
        CriteriaBuilder cb,
        Expression<Boolean> base,
        Expression<Boolean> toAdd) {
        if (base == null)
            base = toAdd;
        else
            base = cb.or(base, toAdd);
        return base;
    }

    public abstract Expression<Boolean> buildWhere(
        AbstractSearch action,
        CriteriaBuilder cb,
        Root<E> from,
        Expression<Boolean> base);

    public abstract
        Order
        getOrderExpression(AbstractSearch action, CriteriaBuilder cb, Root<E> from);

    public abstract
        Expression<Boolean>
        getSearchExpression(CriteriaBuilder cb, Path<E> from, String value);

}
