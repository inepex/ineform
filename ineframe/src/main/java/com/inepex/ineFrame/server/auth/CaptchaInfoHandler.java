package com.inepex.ineFrame.server.auth;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.auth.CaptchaInfoAction;
import com.inepex.ineFrame.shared.auth.CaptchaInfoResult;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;

public class CaptchaInfoHandler extends AbstractIneHandler<CaptchaInfoAction, CaptchaInfoResult> {

    @Inject
    Provider<LoginCaptchaInfo> loginCaptchaInfoProvider;

    @Inject
    Provider<RegistrationCaptchaInfo> regCaptchaInfoProvider;

    @Override
    protected CaptchaInfoResult doExecute(CaptchaInfoAction action, ExecutionContext context)
        throws AuthenticationException,
        DispatchException {

        switch (action.getType()) {
            case 0:
                LoginCaptchaInfo loginInfo = loginCaptchaInfoProvider.get();
                synchronized (loginInfo) {
                    return new CaptchaInfoResult(loginInfo.needCaptcha());
                }
            case 1:
                RegistrationCaptchaInfo regInfo = regCaptchaInfoProvider.get();
                synchronized (regInfo) {
                    return new CaptchaInfoResult(regInfo.needCaptcha());

                }
            default:
                return new CaptchaInfoResult(false);
        }

    }

    @Override
    public Class<CaptchaInfoAction> getActionType() {
        return CaptchaInfoAction.class;
    }
}
