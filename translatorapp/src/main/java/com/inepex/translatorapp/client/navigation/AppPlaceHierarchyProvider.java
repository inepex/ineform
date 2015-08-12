package com.inepex.translatorapp.client.navigation;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.navigation.DefaultPlaceHierarchyProvider;
import com.inepex.ineFrame.client.navigation.InePlace;
import com.inepex.ineFrame.client.navigation.RequiresAuthentication;
import com.inepex.ineFrame.client.navigation.places.ChildRedirectPlace;
import com.inepex.ineFrame.client.navigation.places.SimpleCachingPlace;
import com.inepex.ineom.shared.util.SharedUtil;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.client.page.InactivePage;
import com.inepex.translatorapp.client.page.LoginPage;
import com.inepex.translatorapp.client.page.ModuleListPage;
import com.inepex.translatorapp.client.page.ModuleRowListPage;
import com.inepex.translatorapp.client.page.PageNotFoundPage;
import com.inepex.translatorapp.client.page.RegPage;
import com.inepex.translatorapp.client.page.TranslatorPage;
import com.inepex.translatorapp.client.page.UserListPage;
import com.inepex.translatorapp.shared.Consts;

@Singleton
public class AppPlaceHierarchyProvider extends DefaultPlaceHierarchyProvider {

    public static final String LOGIN = "login";
    public static final String REGISTER = "register";

    public static final String LOGGEDIN = "loggedin";
    public static final String PAGENOTFOUND = "notfound";
    public static final String TRANSLATOR = "translator";
    public static final String INACTIVE = "inactive";
    public static final String USERLIST = "userList";
    public static final String MODULELIST = "moduleList";
    public static final String MODULEROWLIST = "rowList";

    @Inject
    AuthManager authManager;

    @Inject
    Provider<LoginPage> loginProvider;
    @Inject
    Provider<PageNotFoundPage> pageNotFoundProvider;
    @Inject
    Provider<InactivePage> inactiveProvider;
    @Inject
    Provider<TranslatorPage> translatorProvider;
    @Inject
    Provider<RegPage> regProvider;
    @Inject
    Provider<UserListPage> userListProv;
    @Inject
    Provider<ModuleListPage> moduleListProvider;
    @Inject
    Provider<ModuleRowListPage> rowListProvider;

    @Override
    public void createPlaceHierarchy() {
        placeRoot
            .addChild(LOGIN, new SimpleCachingPlace(loginProvider))
            .addChild(REGISTER, new SimpleCachingPlace(regProvider))
            .addChildGC(LOGGEDIN, new ChildRedirectPlace(TRANSLATOR))
            .addChild(PAGENOTFOUND, auth(new SimpleCachingPlace(pageNotFoundProvider)))
            .addChild(INACTIVE, auth(new SimpleCachingPlace(inactiveProvider)))
            .addChild(
                TRANSLATOR,
                usr(new SimpleCachingPlace(translatorProvider)).setMenuName(
                    translatorappI18n.translatorPage()))
            .addChild(
                MODULEROWLIST,
                dev(new SimpleCachingPlace(rowListProvider)).setMenuName(
                    translatorappI18n.rowListPage()))
            .addChild(
                USERLIST,
                dev(new SimpleCachingPlace(userListProv)).setMenuName(
                    translatorappI18n.userListPage()))
            .addChild(
                MODULELIST,
                dev(new SimpleCachingPlace(moduleListProvider)).setMenuName(
                    translatorappI18n.moduleListPage()))
            .getParent();
    }

    private static <E extends InePlace> E auth(E place) {
        place.setRequiresAuthentication(RequiresAuthentication.TRUE);
        return place;
    }

    private static <E extends InePlace> E usr(E place) {
        place.setRequiresAuthentication(RequiresAuthentication.TRUE);
        place.addAllowedRoles(Consts.Roles.developer, Consts.Roles.translator);
        return place;
    }

    private static <E extends InePlace> E dev(E place) {
        place.setRequiresAuthentication(RequiresAuthentication.TRUE);
        place.addAllowedRoles(Consts.Roles.developer);
        return place;
    }

    @Override
    public List<String> getCurrentMenuRoot() {
        if (authManager.isUserLoggedIn()) {
            return SharedUtil.Li(LOGGEDIN);
        } else {
            return null;
        }
    }
}
