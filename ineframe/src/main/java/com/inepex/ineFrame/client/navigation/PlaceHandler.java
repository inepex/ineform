package com.inepex.ineFrame.client.navigation;

import static com.inepex.ineFrame.client.navigation.NavigationProperties.REDIRECT;
import static com.inepex.ineFrame.client.navigation.NavigationProperties.loginPlace;
import static com.inepex.ineFrame.client.navigation.NavigationProperties.wrongTokenPlace;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.inepex.ineFrame.client.async.ConnectionFailedHandler;
import com.inepex.ineFrame.client.auth.AuthManager;
import com.inepex.ineFrame.client.auth.NoAuthManager;
import com.inepex.ineFrame.client.navigation.places.ChildRedirectPlace;
import com.inepex.ineFrame.client.navigation.places.ParamPlace;
import com.inepex.ineFrame.client.navigation.places.WidgetPlace;
import com.inepex.ineFrame.client.page.InePage.UrlParamsParsedCallback;
import com.inepex.ineom.shared.descriptor.Node;

public abstract class PlaceHandler implements ValueChangeHandler<String>, PlaceRequestHandler {

    public static final String QUESTION_MARK = "?";
    public static final String AND_SIGN = "&";
    public static final String EQUALS_SIGN = "=";

    private static interface AfterRedirectionLogic {
        public void afterRedirectionHandled(String token);
    }

    private static class ParamPlaceRedirectionCallbackHandler {

        private final AfterRedirectionLogic afterRedirectionLogic;
        private final Map<String, String> urlParams;
        private final String requestedToken;

        private String firstRedirection = null;
        private int waitingCounter = 0;
        private boolean canFinish = false;
        private Node<InePlace> pointer;

        public ParamPlaceRedirectionCallbackHandler(
            String requestedToken,
            Node<InePlace> pointer,
            Map<String, String> urlParams,
            AfterRedirectionLogic afterRedirectionLogic) {
            this.afterRedirectionLogic = afterRedirectionLogic;
            this.requestedToken = requestedToken;
            this.pointer = pointer;
            this.urlParams = urlParams;
        }

        public void execute() {
            while (pointer != null) {
                InePlace pointerPlace = pointer.getNodeElement();
                if (pointerPlace instanceof ParamPlace) {
                    waitCallback((ParamPlace) pointerPlace);
                }

                pointer = pointer.getParent();

                if (pointer != null) {
                    for (Node<InePlace> sibling : pointer.getChildren()) {
                        if (sibling.getNodeElement() != null
                            && sibling.getNodeElement() != pointerPlace
                            && sibling.getNodeElement() instanceof WidgetPlace) {
                            ((WidgetPlace) sibling.getNodeElement()).update(urlParams);
                        }
                    }
                }
            }
            setCanFinish();
        }

        private void waitCallback(ParamPlace paramPlace) {
            waitingCounter++;
            paramPlace.processParams(requestedToken, urlParams, new UrlParamsParsedCallback() {

                @Override
                public void onUrlParamsParsed(String redirection) {
                    waitingCounter--;
                    if (firstRedirection == null)
                        firstRedirection = redirection;
                    finish();
                }

                @Override
                public void onUrlParamsParsed() {
                    onUrlParamsParsed(null);
                }
            });
        }

        private void finish() {
            if (canFinish && waitingCounter == 0 && afterRedirectionLogic != null) {
                afterRedirectionLogic.afterRedirectionHandled(firstRedirection);
            }
        }

        private void setCanFinish() {
            this.canFinish = true;
            finish();
        }

    }

    private final PlaceHierarchyProvider placeHierarchyProvider;
    private final MasterPage masterPage;
    private final AuthManager authManager;
    private final HistoryProvider historyProvider;
    private final EventBus eventBus;
    private final ConnectionFailedHandler connectionFailedHandler;

    private String previousToken = null;
    private String currentFullToken = null;

    private boolean lastRequestWasARedirect = false;

    public PlaceHandler(
        PlaceHierarchyProvider placeHierarchyProvider,
        MasterPage masterPage,
        AuthManager authManager,
        HistoryProvider historyProvider,
        EventBus eventBus,
        ConnectionFailedHandler connectionFailedHandler) {
        this.placeHierarchyProvider = placeHierarchyProvider;
        this.masterPage = masterPage;
        this.authManager = authManager;
        this.historyProvider = historyProvider;
        this.eventBus = eventBus;
        this.connectionFailedHandler = connectionFailedHandler;

        historyProvider.addHandler(this);
        eventBus.addHandler(PlaceRequestEvent.TYPE, this);

        placeHierarchyProvider.createPlaceHierarchy();
    }

    public void fireInitialPlace() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                historyProvider.fireCurrentHistoryState();
            }
        });
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        if (event.getValue().equals(currentFullToken))
            return;
        else {
            if (event.getValue().equals("")) {
                eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.defaultPlace));
            } else {
                eventBus.fireEvent(new PlaceRequestEvent(event.getValue()));
            }

        }

    }

    @Override
    public void onPlaceRequest(PlaceRequestEvent e) {
        if (lastRequestWasARedirect) {
            lastRequestWasARedirect = false;
        } else {
            previousToken = currentFullToken;
        }

        currentFullToken = e.getHierarchicalTokensWithParam();

        if (e.isOpenInNewWindow()) {
            openInNewWindow("#" + currentFullToken);
        } else {
            realizePlaceChange(e.isNeedWindowReload());
        }
    }

    private void realizePlaceChange(final boolean needWindowReload) {
        String currentFullTokenWithoutRedirect =
            PlaceHandlerHelper.removeRedirect(currentFullToken);

        Node<InePlace> placeNode =
            placeHierarchyProvider.getPlaceRoot().findNodeByHierarchicalId(
                PlaceHandlerHelper.getPlacePart(currentFullTokenWithoutRedirect));

        if (placeNode == null) {
            if (!currentFullTokenWithoutRedirect.equals(wrongTokenPlace))
                onPlaceRequest(new PlaceRequestEvent(wrongTokenPlace));
            else
                GWT.log("Page for NavigationProperties.wrongTokenPlace("
                    + wrongTokenPlace
                    + ") does not exist");
            return;
        }

        PlaceHandlerHelper.updateHierarchicalTokens(
            currentFullTokenWithoutRedirect,
            placeHierarchyProvider.getPlaceRoot());

        final InePlace place = placeNode.getNodeElement();

        if (checkIfLoginPageAndLoggedIn(currentFullTokenWithoutRedirect, needWindowReload))
            return;

        if (!checkPermissionsAndRedirectIfNeeded(
            place,
            currentFullTokenWithoutRedirect,
            needWindowReload))
            return;

        if (place instanceof ChildRedirectPlace) {
            ChildRedirectPlace cdPlace = (ChildRedirectPlace) place;
            PlaceRequestEvent pre =
                new PlaceRequestEvent(PlaceHandlerHelper.appendChild(
                    currentFullTokenWithoutRedirect,
                    cdPlace.getChildToken()));
            pre.setNeedWindowReload(needWindowReload);
            lastRequestWasARedirect = true;
            eventBus.fireEvent(pre);
            return;
        }

        // selector widget update
        final Map<String, String> urlParams =
            PlaceHandlerHelper.getUrlParameters(currentFullTokenWithoutRedirect);
        Node<InePlace> pointer = placeNode;

        new ParamPlaceRedirectionCallbackHandler(
            currentFullTokenWithoutRedirect,
            pointer,
            urlParams,
            new AfterRedirectionLogic() {

                @Override
                public void afterRedirectionHandled(String token) {
                    if (token != null && !token.equals(currentFullToken)) {
                        PlaceRequestEvent pre = new PlaceRequestEvent(token);
                        pre.setNeedWindowReload(needWindowReload);
                        eventBus.fireEvent(pre);
                        return;
                    }

                    // change the browsers token if does not mach current token
                    if (needToRefreshToken())
                        historyProvider.newItem(currentFullToken);

                    if (needWindowReload) {
                        connectionFailedHandler.shutdown();
                        Window.Location.reload();
                    } else {
                        eventBus.fireEvent(new PlaceChangedEvent(currentFullToken));
                        if (specificAdjustPlaceShouldReturn(place))
                            return;
                        Document.get().setTitle(NavigationProperties.defaultWindowTitle);
                        masterPage.render(place, urlParams);
                    }
                }
            }).execute();

    }

    private boolean needToRefreshToken() {
        return (!historyProvider.getToken().equals(currentFullToken) && (!isWrongTokenPlaceSet() || (isWrongTokenPlaceSet() && !currentFullToken
            .equals(NavigationProperties.wrongTokenPlace))));
    }

    private boolean isWrongTokenPlaceSet() {
        return (!NavigationProperties.wrongTokenPlace.equals(NavigationProperties.defaultPlace));
    }

    protected abstract boolean specificAdjustPlaceShouldReturn(InePlace place);

    public void back() {
        historyProvider.back();
    }

    private void openInNewWindow(String url) {
        final String realURL;

        if (Window.Navigator.getUserAgent().contains("MSIE")) {
            realURL = "../" + url;
        } else {
            realURL = url;
        }

        // FIXME: the base browser window may get the focus back from popup in
        // inteble's userCommands,
        // timer is better than nothing, but not the perfect solution

        // without timer IE may drop the popup behind the actual window making
        // PlaceRequestEvent from an inetable's userCommand
        Timer t = new Timer() {

            @Override
            public void run() {
                Window.open(realURL, "_blank", "");
            }
        };
        t.schedule(500);
    }

    private boolean checkPermissionsAndRedirectIfNeeded(
        InePlace place,
        String currentFullTokenWithoutRedirect,
        boolean needWindowReload) {
        if (place.isAuthenticationNeeded()) {
            List<String> allowedRolesForPlace = place.getRolesAllowed();
            if (!authManager.isUserLoggedIn()) {
                PlaceRequestEvent pre =
                    new PlaceRequestEvent(loginPlace
                        + QUESTION_MARK
                        + REDIRECT
                        + EQUALS_SIGN
                        + currentFullTokenWithoutRedirect);
                pre.setNeedWindowReload(needWindowReload);
                lastRequestWasARedirect = true;
                eventBus.fireEvent(pre);

                return false;
            } else if (authManager.isUserLoggedIn()
                && (allowedRolesForPlace == null || allowedRolesForPlace.size() == 0)) {
                return true;
            } else if (authManager.doUserHaveAnyOfRoles(allowedRolesForPlace
                .toArray(new String[allowedRolesForPlace.size()]))) {
                return true;
            } else {
                eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.noRightPlace));
                return false;
            }
        } else
            return true;
    }

    private boolean checkIfLoginPageAndLoggedIn(
        String currentFullTokenWithoutRedirect,
        boolean needWindowReload) {
        if (authManager instanceof NoAuthManager)
            return false;
        if (currentFullTokenWithoutRedirect.startsWith(NavigationProperties.loginPlace)
            && authManager.isUserLoggedIn()) {
            if (currentFullTokenWithoutRedirect.startsWith(NavigationProperties.defaultPlace)) {
                GWT.log("Cannot redirect to NavigationProperties.defaultPlace. "
                    + "NavigationProperties.loginPlace("
                    + NavigationProperties.loginPlace
                    + ") and "
                    + "NavigationProperties.defaultPlace("
                    + NavigationProperties.defaultPlace
                    + ") is the same.");
                return false;
            }
            PlaceRequestEvent pre = new PlaceRequestEvent(NavigationProperties.defaultPlace);
            pre.setNeedWindowReload(needWindowReload);
            lastRequestWasARedirect = true;
            eventBus.fireEvent(pre);
            return true;
        } else
            return false;
    }

    public String getCurrentFullToken() {
        return currentFullToken;
    }

    public String getPreviousToken() {
        return previousToken;
    }

    // ------- ------------------------------
    // navigation helper methods
    // ------- ------------------------------
    public PlaceRequestEvent generateRefreshEvent() {
        return generateSubMenuEvent();
    }

    public PlaceRequestEvent generateSubMenuEvent(String... subMenuTokens) {
        PlaceRequestEvent event = new PlaceRequestEvent();
        event.setHierarchicalTokensWithParam(PlaceHandlerHelper.createSubMenuToken(
            currentFullToken,
            subMenuTokens));
        return event;
    }

    public PlaceRequestEvent generateJumpUpEvent() {
        PlaceRequestEvent event = new PlaceRequestEvent();

        Node<InePlace> placeNode =
            placeHierarchyProvider.getPlaceRoot().findNodeByHierarchicalId(
                PlaceHandlerHelper.getPlacePart(currentFullToken));

        event.setHierarchicalTokensWithParam(placeNode
            .getParent()
            .getNodeElement()
            .getHierarchicalToken());

        return event;
    }

    public PlaceRequestEvent generateSameLevelMenuEvent(String... subMenuTokens) {
        PlaceRequestEvent event = new PlaceRequestEvent();
        event.setHierarchicalTokensWithParam(PlaceHandlerHelper.createSameLevelMenuToken(
            currentFullToken,
            subMenuTokens));
        return event;
    }

    public PlaceHierarchyProvider getPlaceHierarchyProvider() {
        return placeHierarchyProvider;
    }

}
