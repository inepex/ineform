package com.inepex.translatorapp.server;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Provider;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;

public class TransValueModInterceptor implements MethodInterceptor {

    private final Provider<SessionScopedAuthStat> authStatProv;
    private final Provider<AssistedObjectHandlerFactory> hfProvider;

    public TransValueModInterceptor(
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
        if (action.getManipulationType() != ManipulationTypes.CREATE_OR_EDIT_REQUEST
            || action.getObject() == null) {
            throw new IllegalStateException();
        }

        AssistedObjectHandler handler = hfProvider.get().createHandler(action.getObject());

        handler.set(TranslatedValueConsts.k_lastModTime, System.currentTimeMillis());
        handler.set(TranslatedValueConsts.k_lastModUser, new Relation(userId, ""));

        return invocation.proceed();
    }

}
