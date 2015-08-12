package com.inepex.translatorapp.server.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;
import com.inepex.ineom.shared.validation.ValidationResult;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.server.entity.dao.UserDao;
import com.inepex.translatorapp.shared.action.RegAction;
import com.inepex.translatorapp.shared.kvo.RegConsts;

public class RegActionHandler extends AbstractIneHandler<RegAction, ObjectManipulationActionResult> {

    @Inject
    KeyValueObjectValidationManager validationManager;
    @Inject
    UserDao userDao;

    @Override
    protected ObjectManipulationActionResult doExecute(RegAction action, ExecutionContext context)
        throws AuthenticationException,
        DispatchException {
        if (action == null || action.getRegKvo() == null)
            return createFailResult("Invalid action!");

        ObjectManipulationActionResult res = checkRegistrationReq(action.getRegKvo());
        if (res != null)
            return res;

        return doRegistration(action.getRegKvo());
    }

    private ObjectManipulationActionResult createFailResult(String msg) {
        ObjectManipulationActionResult res = new ObjectManipulationActionResult();
        res.setSuccess(false);
        res.setError(msg);
        return res;
    }

    private ObjectManipulationActionResult checkRegistrationReq(AssistedObject regKvo) {
        ValidationResult vr = validationManager.validate(regKvo);
        if (!vr.isValid()) {
            return new ObjectManipulationActionResult(vr);
        }

        String email = regKvo.getStringUnchecked(RegConsts.k_email);
        if (userDao.findByEmail(email) != null) {
            vr.addFieldError(RegConsts.k_email, translatorappI18n.registeredEmail());
        }

        String pw = regKvo.getStringUnchecked(RegConsts.k_password);
        if (pw.length() < 6 || !pw.matches(".*[\\D].*") || !pw.matches(".*[\\d].*")) {
            vr.addFieldError(RegConsts.k_password, translatorappI18n.weakPassword());
        }

        String pwAgain = regKvo.getStringUnchecked(RegConsts.k_passwordAgain);
        if (!pw.equals(pwAgain)) {
            vr.addFieldError(RegConsts.k_passwordAgain, translatorappI18n.nonMatchingPws());
        }

        if (!vr.isValid())
            return new ObjectManipulationActionResult(vr);
        else
            return null;
    }

    private ObjectManipulationActionResult doRegistration(AssistedObject regKvo) {
        String email = regKvo.getStringUnchecked(RegConsts.k_email);
        String pw = regKvo.getStringUnchecked(RegConsts.k_password);

        try {
            userDao.createNewUser(email, pw);
            return new ObjectManipulationActionResult();
        } catch (Exception e) {
            e.printStackTrace();
            return createFailResult(e.getMessage());
        }
    }

    @Override
    public Class<RegAction> getActionType() {
        return RegAction.class;
    }

}
