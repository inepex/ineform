package com.inepex.example.ContactManager.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.example.ContactManager.entity.EmailAddress;
import com.inepex.example.ContactManager.entity.EmailAddress_;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressConsts;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.EmailAddressHandlerFactory.EmailAddressSearchHandler;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.util.SharedUtil;

public class EmailAddressQuery extends BaseQuery<EmailAddress> {

    private final EmailAddressHandlerFactory handlerFactory;

    @Inject
    public EmailAddressQuery(DescriptorStore descriptorStore) {
        this.handlerFactory = new EmailAddressHandlerFactory(descriptorStore);
    }

    @Override
    public Expression<Boolean> buildWhere(
        AbstractSearch action,
        CriteriaBuilder cb,
        Root<EmailAddress> from,
        Expression<Boolean> base) {

        EmailAddressSearchHandler handler = handlerFactory
            .createSearchHandler(action.getSearchParameters());
        Long id = handler.getLong(EmailAddressConsts.s_id);
        if (id != null)
            base = addAndExpression(cb, base, cb.equal(from.get(EmailAddress_.id), id));
        String email = handler.getString(EmailAddressConsts.s_email);
        if (email != null)
            base = addAndExpression(
                cb,
                base,
                cb.like(cb.upper(from.get(EmailAddress_.email)), email.toUpperCase() + "%"));
        return base;
    }

    @Override
    public Order getOrderExpression(
        AbstractSearch action,
        CriteriaBuilder cb,
        Root<EmailAddress> from) {
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

    @Override
    public Expression<Boolean> getSearchExpression(
        CriteriaBuilder cb,
        Path<EmailAddress> from,
        String value) {
        Expression<Boolean> expr = null;
        expr = addOrExpression(
            cb,
            expr,
            cb.like(cb.upper(from.get(EmailAddress_.email)), value.toUpperCase() + "%"));
        return expr;
    }

}