package com.inepex.translatorapp.server.entity.dao.query;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.google.inject.Inject;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.util.SharedUtil;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.Lang_;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.ModuleRow_;
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.server.entity.TranslatedValue_;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.shared.assist.LangAssist;
import com.inepex.translatorapp.shared.assist.ModuleRowAssist;
import com.inepex.translatorapp.shared.assist.UserAssist;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueHandlerFactory;
import com.inepex.translatorapp.shared.kvo.TranslatedValueHandlerFactory.TranslatedValueSearchHandler;

public class TranslatedValueQuery extends BaseQuery<TranslatedValue> {

    private final TranslatedValueHandlerFactory handlerFactory;

    @Inject
    public TranslatedValueQuery(DescriptorStore descriptorStore) {
        this.handlerFactory = new TranslatedValueHandlerFactory(descriptorStore);
    }

    @Override
    public Expression<Boolean> buildWhere(
        AbstractSearch action,
        CriteriaBuilder cb,
        Root<TranslatedValue> from,
        Expression<Boolean> base) {

        TranslatedValueSearchHandler handler =
            handlerFactory.createSearchHandler(action.getSearchParameters());
        Long id = handler.getLong(TranslatedValueConsts.s_id);
        if (id != null)
            base = addAndExpression(cb, base, cb.equal(from.get(TranslatedValue_.id), id));
        Relation lang = handler.getRelation(TranslatedValueConsts.s_lang);
        if (lang != null)
            base =
                addAndExpression(
                    cb,
                    base,
                    cb.equal(from.get(TranslatedValue_.lang).get(Lang_.id), lang.getId()));
        Relation row = handler.getRelation(TranslatedValueConsts.s_row);
        if (row != null)
            base =
                addAndExpression(
                    cb,
                    base,
                    cb.equal(from.get(TranslatedValue_.row).get(ModuleRow_.id), row.getId()));
        return base;
    }

    @Override
    public Order getOrderExpression(
        AbstractSearch action,
        CriteriaBuilder cb,
        Root<TranslatedValue> from) {
        Order o;
        String orderKey = action == null ? null : action.getOrderKey();
        if (orderKey == null) {
            // default default order
            orderKey = IFConsts.KEY_ID;
            // default order specified:
        }
        Expression<?> orderExpr = null;
        List<String> idList = SharedUtil.listFromDotSeparated(orderKey);

        if (idList.get(0).equals(TranslatedValueConsts.k_lastModUser)) {
            if (idList.size() == 1) {
                Join<TranslatedValue, User> title = from.join(TranslatedValue_.lastModUser);
                orderExpr = title.get(UserAssist.getOrderKey());
            } else {
                Join<TranslatedValue, ?> orderPath = from.join(idList.get(0));
                for (int i = 1; i < idList.size() - 1; i++) {
                    orderPath = orderPath.join(idList.get(i));
                }
                orderExpr = orderPath.get(idList.get(idList.size() - 1));
            }
        } else if (idList.get(0).equals(TranslatedValueConsts.k_lang)) {
            if (idList.size() == 1) {
                Join<TranslatedValue, Lang> title = from.join(TranslatedValue_.lang);
                orderExpr = title.get(LangAssist.getOrderKey());
            } else {
                Join<TranslatedValue, ?> orderPath = from.join(idList.get(0));
                for (int i = 1; i < idList.size() - 1; i++) {
                    orderPath = orderPath.join(idList.get(i));
                }
                orderExpr = orderPath.get(idList.get(idList.size() - 1));
            }
        } else if (idList.get(0).equals(TranslatedValueConsts.k_row)) {
            if (idList.size() == 1) {
                Join<TranslatedValue, ModuleRow> title = from.join(TranslatedValue_.row);
                orderExpr = title.get(ModuleRowAssist.getOrderKey());
            } else {
                Join<TranslatedValue, ?> orderPath = from.join(idList.get(0));
                for (int i = 1; i < idList.size() - 1; i++) {
                    orderPath = orderPath.join(idList.get(i));
                }
                orderExpr = orderPath.get(idList.get(idList.size() - 1));
            }
        } else {
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
        Path<TranslatedValue> from,
        String value) {
        Expression<Boolean> expr = null;
        return expr;
    }

}