package com.inepex.ineForm.client.widget;

import java.util.Date;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.widgets.CaptchaFW;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.types.FWTypes;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.HistoryProvider;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceHandler;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;
import com.inepex.ineFrame.shared.auth.CaptchaInfoAction;
import com.inepex.ineFrame.shared.auth.CaptchaInfoResult;
import com.inepex.ineFrame.shared.util.date.DateHelper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore.Marker;

public abstract class LoginBox extends HandlerAwareComposite {

    protected final static String email = "email";
    protected final static String pass = "pass";
    protected final static String captcha = "captcha";
    protected final static String loginObjectDesc = "loginObjectDesc";

    protected final VerticalPanel mainPanel = new VerticalPanel();
    protected FormPanel formPanel;
    protected final VerticalPanel formContent = new VerticalPanel();
    protected Button loginButton;

    protected IneForm ineForm;

    protected final AuthManager authManager;
    protected final HistoryProvider historyProvider;
    protected final EventBus eventBus;
    protected final IneDispatch ineDispatch;
    protected final DescriptorStore descriptorStore;
    protected final FormFactory formFactory;

    protected LoginBox(
        AuthManager authManager,
        HistoryProvider historyProvider,
        EventBus eventBus,
        IneDispatch ineDispatch,
        DescriptorStore descriptorStore,
        FormFactory formFactory) {
        this.authManager = authManager;
        this.historyProvider = historyProvider;
        this.eventBus = eventBus;
        this.ineDispatch = ineDispatch;
        this.descriptorStore = descriptorStore;
        this.formFactory = formFactory;

        createUI();
        initWidget(mainPanel);
    }

    /**
     * this method initializes the protected fields: labels and button
     */
    protected void createUI() {
        ObjectDesc desc =
            new ObjectDesc(
                loginObjectDesc,
                new StringFDesc(email, IneFrameI18n.USERNAME()),
                new StringFDesc(pass, IneFrameI18n.PASSWORD()),
                new StringFDesc(captcha, IneFrameI18n.CAPTCHA()));
        descriptorStore.registerObjectDesc(Marker.registered, desc);
        FormRDesc fDesc = new FormRDesc(loginObjectDesc);
        fDesc.getRootNode().addChild(
            email,
            new WidgetRDesc(FWTypes.TEXTBOXBYDOMID).prop(IneFormProperties.domId, "username"));
        fDesc.getRootNode().addChild(
            pass,
            new WidgetRDesc(FWTypes.PASSWORDTEXTBOXBYDOMID).prop(
                IneFormProperties.domId,
                "password"));
        fDesc.getRootNode().addChild(
            captcha,
            new WidgetRDesc(FWTypes.CAPTCHA).width(100).prop(
                CaptchaFW.PROP_RENDERING,
                CaptchaFW.HORIZONTAL));
        descriptorStore.addDefaultTypedDesc(Marker.registered, loginObjectDesc, fDesc);

        ineForm = formFactory.createSimple(loginObjectDesc, null);
        ineForm.renderForm();
        formContent.add(ineForm.asWidget());
        ButtonElement submitEl = (ButtonElement) Document.get().getElementById("loginSubmit");
        Element formEl = DOM.getElementById("loginform");
        loginButton = (submitEl == null ? new SubmitButton() : Button.wrap(submitEl));
        loginButton.setText(IneFrameI18n.LOGIN());

        formContent.add(loginButton);

        formPanel = (formEl == null ? new FormPanel() : FormPanel.wrap(formEl));
        formEl.getStyle().setDisplay(Display.BLOCK);
        formPanel.add(formContent);
        mainPanel.add(formPanel);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setTextBoxesEnabled(true);

        registerHandler(formPanel.addSubmitHandler(new SubmitHandler() {

            @Override
            public void onSubmit(SubmitEvent event) {
                doLogin();
            }
        }));

        registerHandler(getCheckBox().addValueChangeHandler(new ValueChangeHandler<Boolean>() {

            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                setStaySignedInCookie(event.getValue());
            }
        }));

        setVisible(false);
        ineDispatch.execute(
            new CaptchaInfoAction(0),
            new IneDispatchBase.SuccessCallback<CaptchaInfoResult>() {

                @Override
                public void onSuccess(CaptchaInfoResult result) {
                    if (result.isNeedCaptcha()) {
                        ineForm.setFWVisible(captcha, true);
                    } else {
                        ineForm.setFWVisible(captcha, false);
                    }

                    setVisible(true);
                }
            });
    }

    protected void setStaySignedInCookie(boolean staySignedIn) {
        if (staySignedIn) {
            Cookies.setCookie(IFConsts.COOKIE_NEEDSTAYSIGNEDIN, IFConsts.COOKIE_TRUE, new Date(
                System.currentTimeMillis() + DateHelper.dayInMs * 30));
        } else {
            Cookies.setCookie(IFConsts.COOKIE_NEEDSTAYSIGNEDIN, IFConsts.COOKIE_FALSE, new Date(
                System.currentTimeMillis() + DateHelper.dayInMs * 30));
        }
    }

    private void doLogin() {
        setTextBoxesEnabled(false);
        loginButton.setEnabled(false);
        loginButton.setText(IneFrameI18n.LOGGINGIN());
        String userName = ineForm.getValues().getStringUnchecked(email);
        String passWord = ineForm.getValues().getStringUnchecked(pass);
        authManager.doLogin(
            userName,
            passWord,
            ineForm.getValues().getStringUnchecked(captcha),
            new LoginCallback());
    }

    protected abstract void doLoggedinLogic(AuthStatusResultBase result);

    protected void doRedirectLogic(AuthStatusResultBase result) {
        PlaceRequestEvent pre = getRedirectPlaceReqEvt();
        modifiyRedirectPlaceRequestEvent(result, pre);
        eventBus.fireEvent(pre);
    }

    public PlaceRequestEvent getRedirectPlaceReqEvt() {
        PlaceRequestEvent pre = new PlaceRequestEvent();
        pre.setHierarchicalTokensWithParam(historyProvider.getToken().substring(
            historyProvider.getToken().indexOf(NavigationProperties.REDIRECT)
                + NavigationProperties.REDIRECT.length()
                + PlaceHandler.EQUALS_SIGN.length(),
            historyProvider.getToken().length()));
        return pre;
    }

    protected void modifiyRedirectPlaceRequestEvent(
        AuthStatusResultBase result,
        PlaceRequestEvent pre) {}

    /**
     * override to display more sophisticated errors
     * 
     */
    protected void onInvalidLogin() {
        Window.alert("Invalid user or password (or captcha)!");
    }

    class LoginCallback implements AuthActionCallback {
        @Override
        public void onAuthCheckDone(AuthStatusResultBase result) {
            if (result != null && result.isSuccess()) {
                if (historyProvider.getToken().contains((NavigationProperties.REDIRECT))) {
                    doRedirectLogic(result);
                } else {
                    doLoggedinLogic(result);
                }

                Scheduler.get().scheduleFinally(new Scheduler.ScheduledCommand() {

                    @Override
                    public void execute() {
                        ineForm.resetValuesToEmpty();
                        ineForm.setFWVisible(captcha, false);
                        ((CaptchaFW) ineForm.getFormWidget(captcha))
                            .getCaptchaWidget()
                            .reloadCaptcha();
                        loginButton.setEnabled(true);
                        loginButton.setText(IneFrameI18n.LOGIN());
                        setTextBoxesEnabled(true);
                    }
                });

            } else {
                ineForm.getFormWidget(pass).setStringValue("");
                ((CaptchaFW) ineForm.getFormWidget(captcha)).getCaptchaWidget().reloadCaptcha();

                if (result != null && result.isNeedCaptcha()) {
                    ineForm.setFWVisible(captcha, true);
                } else {
                    ineForm.setFWVisible(captcha, false);
                }

                loginButton.setEnabled(true);
                loginButton.setText(IneFrameI18n.LOGIN());
                setTextBoxesEnabled(true);
                onInvalidLogin();
            }
        }
    }

    private void setTextBoxesEnabled(boolean enabled) {
        ineForm.setEnabled(enabled);
        ((CaptchaFW) ineForm.getFormWidget(captcha))
            .getCaptchaWidget()
            .getTextBox()
            .setEnabled(enabled);
    }

    public void init() {
        ineForm.resetValuesToEmpty();
        ineForm.setFWVisible(captcha, false);
    }

    // these helper methods are used for assigning functionality to the checkbox
    // in the derived class
    protected abstract HasValue<Boolean> getCheckBox();

    protected abstract IsWidget getCheckBoxAsWidget();

}
