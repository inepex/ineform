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
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.dispatch.interfaces.AbstractSearch;
import com.inepex.ineom.shared.util.SharedUtil;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.server.entity.ModuleLang_;
import com.inepex.translatorapp.shared.assist.LangAssist;
import com.inepex.translatorapp.shared.assist.ModuleAssist;
import com.inepex.translatorapp.shared.kvo.ModuleLangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleLangHandlerFactory;
import com.inepex.translatorapp.shared.kvo.ModuleLangHandlerFactory.ModuleLangSearchHandler;

public class ModuleLangQuery extends BaseQuery<ModuleLang> {

    private final ModuleLangHandlerFactory handlerFactory;

    @Inject
    public ModuleLangQuery(DescriptorStore descriptorStore) {
        this.handlerFactory = new ModuleLangHandlerFactory(descriptorStore);
    }

    @Override
    public Expression<Boolean> buildWhere(
        AbstractSearch action,
        CriteriaBuilder cb,
        Root<ModuleLang> from,
        Expression<Boolean> base) {

        ModuleLangSearchHandler handler =
            handlerFactory.createSearchHandler(action.getSearchParameters());
        Long id = handler.getLong(ModuleLangConsts.s_id);
        if (id != null)
            base = addAndExpression(cb, base, cb.equal(from.get(ModuleLang_.id), id));
        return base;
    }

    @Override
    public
        Order
        getOrderExpression(AbstractSearch action, CriteriaBuilder cb, Root<ModuleLang> from) {
        Order o;
        String orderKey = action == null ? null : action.getOrderKey();
        if (orderKey == null) {
            // default default order
            orderKey = IFConsts.KEY_ID;
            // default order specified:
        }
        Expression<?> orderExpr = null;
        List<String> idList = SharedUtil.listFromDotSeparated(orderKey);

        if (idList.get(0).equals(ModuleLangConsts.k_lang)) {
            if (idList.size() == 1) {
                Join<ModuleLang, Lang> title = from.join(ModuleLang_.lang);
                orderExpr = title.get(LangAssist.getOrderKey());
            } else {
                Join<ModuleLang, ?> orderPath = from.join(idList.get(0));
                for (int i = 1; i < idList.size() - 1; i++) {
                    orderPath = orderPath.join(idList.get(i));
                }
                orderExpr = orderPath.get(idList.get(idList.size() - 1));
            }
        } else if (idList.get(0).equals(ModuleLangConsts.k_module)) {
            if (idList.size() == 1) {
                Join<ModuleLang, Module> title = from.join(ModuleLang_.module);
                orderExpr = title.get(ModuleAssist.getOrderKey());
            } else {
                Join<ModuleLang, ?> orderPath = from.join(idList.get(0));
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
        Path<ModuleLang> from,
        String value) {
        Expression<Boolean> expr = null;
        return expr;
    }

}