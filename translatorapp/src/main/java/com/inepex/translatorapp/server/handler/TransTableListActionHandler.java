package com.inepex.translatorapp.server.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.server.entity.UserLang;
import com.inepex.translatorapp.server.entity.dao.TranslatedValueDao;
import com.inepex.translatorapp.server.entity.dao.UserDao;
import com.inepex.translatorapp.server.entity.mapper.TranslatedValueMapper;
import com.inepex.translatorapp.shared.Consts;
import com.inepex.translatorapp.shared.action.TransTableListAction;
import com.inepex.translatorapp.shared.kvo.TranslateTableRowConsts;

public class TransTableListActionHandler
    extends
    AbstractIneHandler<TransTableListAction, ObjectListActionResult> {

    @Inject
    TranslatedValueDao translatedValueDao;
    @Inject
    Provider<SessionScopedAuthStat> authStatProv;
    @Inject
    UserDao userDao;
    @Inject
    AssistedObjectHandlerFactory assistedObjectHandlerFactory;
    @Inject
    TranslatedValueMapper translatedValueMapper;

    private static AtomicLong dummyId = new AtomicLong(0);

    @Override
    protected
        ObjectListActionResult
        doExecute(TransTableListAction action, ExecutionContext context)
            throws AuthenticationException,
            DispatchException {
        List<Long> userLangs = fetchUserLangs();

        ObjectListActionResult res = new ObjectListActionResult();
        if (action.isQueryResultCount()) {
            List<TranslatedValue> values =
                translatedValueDao.listForTranslatorPage(
                    false,
                    userLangs,
                    0,
                    100000,
                    action.getModuleName(),
                    action.getListType());
            res.setAllResultCount((long) values.size());
        }

        if (action.getNumMaxResult() > 0) {
            List<TranslatedValue> values =
                translatedValueDao.listForTranslatorPage(
                    true,
                    userLangs,
                    action.getFirstResult(),
                    action.getNumMaxResult(),
                    action.getModuleName(),
                    action.getListType());
            res.setList(map(values));
        }

        return res;
    }

    private List<Long> fetchUserLangs() throws AuthenticationException {
        Long userId;
        SessionScopedAuthStat stat = authStatProv.get();
        synchronized (stat) {
            userId = stat.getUserId();
        }

        if (userId == null)
            throw new AuthenticationException();

        User u = userDao.findById(userId);
        if (u == null)
            throw new AuthenticationException();

        List<Long> langIds = new ArrayList<>();
        for (UserLang ul : u.getTranslates()) {
            langIds.add(ul.getLang().getId());
        }
        return langIds;
    }

    private List<AssistedObject> map(List<TranslatedValue> values) {
        List<AssistedObject> retList = new ArrayList<>(values.size());
        for (TranslatedValue val : values)
            retList.add(map(val));

        return retList;
    }

    private AssistedObject map(TranslatedValue val) {
        AssistedObjectHandler h =
            assistedObjectHandlerFactory.createHandler(TranslateTableRowConsts.descriptorName);

        h.setId(dummyId.incrementAndGet());

        boolean isRecent =
            val.getLastModTime() + Consts.recentTimeRange > System.currentTimeMillis();
        h.set(TranslateTableRowConsts.k_recent, isRecent);

        h.set(TranslateTableRowConsts.k_description, val.getRow().getDescription());
        h.set(
            TranslateTableRowConsts.k_translatedValue,
            translatedValueMapper.toRelation(val, true));

        TranslatedValue engVal = TranslatorAppUtil.findEngValFor(val);

        h.set(TranslateTableRowConsts.k_outDated, TranslatorAppUtil.isOutdated(val, engVal));

        h.set(TranslateTableRowConsts.k_invalid, TranslatorAppUtil.isInvalid(val, engVal));
        h.set(TranslateTableRowConsts.k_engVal, engVal == null ? null : engVal.getValue());

        h.set(TranslateTableRowConsts.k_key, val.getRow().getKey());
        h
            .set(
                TranslateTableRowConsts.k_engModTime,
                engVal == null ? null : engVal.getLastModTime());

        return h.getAssistedObject();
    }

    @Override
    public Class<TransTableListAction> getActionType() {
        return TransTableListAction.class;
    }
}
