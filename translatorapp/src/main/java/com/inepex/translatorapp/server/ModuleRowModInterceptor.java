package com.inepex.translatorapp.server;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Provider;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;

public class ModuleRowModInterceptor implements MethodInterceptor {

    private final Provider<SessionScopedAuthStat> authStatProv;
    private final Provider<AssistedObjectHandlerFactory> hfProvider;

    public ModuleRowModInterceptor(
        Provider<SessionScopedAuthStat> authStatProv,
        Provider<AssistedObjectHandlerFactory> hfProvider) {
        this.authStatProv = authStatProv;
        this.hfProvider = hfProvider;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Long userId;
        SessionScopedAuthStat stat = authStatProv.get();
        synchronized (stat) {
            userId = stat.getUserId();
        }

        if (userId == null)
            throw new AuthenticationException();

        ObjectManipulationAction action = (ObjectManipulationAction) invocation.getArguments()[0];
        if (action.getManipulationType() == ManipulationTypes.CREATE_OR_EDIT_REQUEST
            && action.getObject() != null) {

            AssistedObjectHandler rowHandler = hfProvider.get().createHandler(action.getObject());

            IneList values = rowHandler.getList(ModuleRowConsts.k_values);
            if (values != null
                && values.getRelationList() != null
                && !values.getRelationList().isEmpty()) {

                long now = System.currentTimeMillis();

                for (Relation r : values.getRelationList()) {
                    if (r.getKvo() == null)
                        continue;

                    AssistedObjectHandler transValHandler =
                        hfProvider.get().createHandler(r.getKvo());
                    transValHandler.set(TranslatedValueConsts.k_lastModTime, now);
                    transValHandler.set(TranslatedValueConsts.k_lastModUser, new Relation(
                        userId,
                        ""));
                }
            }
        }

        return invocation.proceed();
    }

}
