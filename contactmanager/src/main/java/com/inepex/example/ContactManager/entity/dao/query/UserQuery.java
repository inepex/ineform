package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.User_;
import com.inepex.example.ContactManager.entity.kvo.UserConsts;
import com.inepex.example.ContactManager.entity.kvo.UserHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.UserHandlerFactory.UserSearchHandler;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.util.SharedUtil;

public class UserQuery extends BaseQuery<User> {

    private final UserHandlerFactory handlerFactory;

    @Inject
    public UserQuery(DescriptorStore descriptorStore) {
        this.handlerFactory = new UserHandlerFactory(descriptorStore);
    }

    @Override
    public Expression<Boolean> buildWhere(
        AbstractSearch action,
        CriteriaBuilder cb,
        Root<User> from,
        Expression<Boolean> base) {

        UserSearchHandler handler = handlerFactory
            .createSearchHandler(action.getSearchParameters());
        Long id = handler.getLong(UserConsts.s_id);
        if (id != null)
            base = addAndExpression(cb, base, cb.equal(from.get(User_.id), id));
        String firstName = handler.getString(UserConsts.s_firstName);
        if (firstName != null)
            base = addAndExpression(
                cb,
                base,
                cb.like(cb.upper(from.get(User_.firstName)), firstName.toUpperCase() + "%"));
        String lastName = handler.getString(UserConsts.s_lastName);
        if (lastName != null)
            base = addAndExpression(
                cb,
                base,
                cb.like(cb.upper(from.get(User_.lastName)), lastName.toUpperCase() + "%"));
        String email = handler.getString(UserConsts.s_email);
        if (email != null)
            base = addAndExpression(
                cb,
                base,
                cb.like(cb.upper(from.get(User_.email)), email.toUpperCase() + "%"));
        return base;
    }

    @Override
    public Order getOrderExpression(AbstractSearch action, CriteriaBuilder cb, Root<User> from) {
        Order o;
        String orderKey = action == null ? null : action.getOrderKey();
        if (orderKey == null) {
            // default default order
            orderKey = IFConsts.KEY_ID;
            // default order specified:
        }
        Expression<?> orderExpr = null;
        List<String> idList = SharedUtil.listFromDotSeparated(orderKey);

        {
            orderExpr = from.get(orderKey);
        }
        if (action.isDescending() == null)
            // default order
            o = cb.asc(orderExpr);
        else if (action.isDescending())
            o = cb.desc(orderExpr);
        else
            o = cb.asc(orderExpr);
        return o;
    }

    public
        Expression<Boolean>
        getSearchExpression(CriteriaBuilder cb, Path<User> from, String value) {
        Expression<Boolean> expr = null;
        expr = addOrExpression(
            cb,
            expr,
            cb.like(cb.upper(from.get(User_.firstName)), value.toUpperCase() + "%"));
        expr = addOrExpression(
            cb,
            expr,
            cb.like(cb.upper(from.get(User_.lastName)), value.toUpperCase() + "%"));
        expr = addOrExpression(
            cb,
            expr,
            cb.like(cb.upper(from.get(User_.email)), value.toUpperCase() + "%"));
        return expr;
    }
}