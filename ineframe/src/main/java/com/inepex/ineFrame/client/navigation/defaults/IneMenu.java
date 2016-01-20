package com.inepex.ineFrame.client.navigation.defaults;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineFrame.client.auth.AbstractAuthManager.AuthActionCallback;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.auth.UserLoggedInEvent;
import com.inepex.ineFrame.client.auth.UserLoggedOutEvent;
import com.inepex.ineFrame.client.navigation.MasterPage.View;
import com.inepex.ineFrame.client.navigation.NavigationDrawer;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.navigation.messagepanel.MessagePanel;
import com.inepex.ineFrame.shared.IneformAsyncCallback;
import com.inepex.ineFrame.shared.auth.AuthStatusResultBase;

@Singleton
public class IneMenu implements NavigationDrawer {

    protected IneMenuView view;
    protected AuthManager authManager;
    protected Provider<View> masterPageView;
    private boolean appsDisplayed = false;

    @Inject
    public IneMenu(
        IneMenuView view,
        final EventBus eventBus,
        final AuthManager authManager,
        final Provider<MessagePanel> messagePanel,
        final Provider<View> masterPageView) {
        this.view = view;
        this.authManager = authManager;
        this.masterPageView = masterPageView;

        eventBus.addHandler(UserLoggedInEvent.TYPE, new UserLoggedInEvent.Handler() {

            @Override
            public void onUserLoggedIn() {
                refresh();
            }
        });

        eventBus.addHandler(UserLoggedOutEvent.TYPE, new UserLoggedOutEvent.Handler() {

            @Override
            public void onUserLoggedOut() {
                refresh();
            }
        });

        // for test only
        view.setHelpCallback(new IneformAsyncCallback<Void>() {

            @Override
            public void onResponse(Void response) {
                masterPageView.get().toggleNavigationDrawer();
                messagePanel.get().showMessage("Helló új design és animációk", false);
            }
        });

        view.setLogoutCbk(new IneformAsyncCallback<Void>() {

            @Override
            public void onResponse(Void response) {
                masterPageView.get().toggleNavigationDrawer();
                authManager.doLogout(new AuthActionCallback() {
                    @Override
                    public void onAuthCheckDone(AuthStatusResultBase result) {
                        PlaceRequestEvent event = new PlaceRequestEvent(
                            NavigationProperties.defaultPlace);
                        event.setNeedWindowReload(true);
                        eventBus.fireEvent(event);
                    }
                });
            }
        });
    }

    public void setAccountSettingsCallback(IneformAsyncCallback<Void> cbk) {
        view.setAccountSettingsCbk(cbk);
    }

    public void setHelpCallback(IneformAsyncCallback<Void> cbk) {
        view.setHelpCallback(cbk);
    }

    @Override
    public Widget asWidget() {
        return view;
    }

    private void refresh() {
        if (!(authManager instanceof NoAuthManager) && authManager.isUserLoggedIn()) {
            view.setUser(
                authManager.getLastAuthStatusResult().getDisplayName(),
                authManager.getLastAuthStatusResult().getUserEmail());
            showApps();

        } else {
            view.setUser(null, null);
        }
    }

    protected void showApps() {
        view.clearApps();
    }

    @Override
    public void enableAliasMode(String name, String email) {
        view.enableAliasMode(name, email);
    }

    @Override
    public void disableAliasMode(String name, String email) {
        view.disableAliasMode(name, email);
    }

    @Override
    public void onDisplayed() {
        if (!appsDisplayed) {
            refresh();
            appsDisplayed = true;
        }
        view.onDisplayed();
    }

    @Override
    public void onHidden() {
        view.onHidden();
    }
}
