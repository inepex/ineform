package com.inepex.ineFrame.client.navigation;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AnalyticsHandler implements PlaceChangedHandler {

    private static String NAVIGATION = "navigation";

    private String accountId;
    private String universalAccountId;

    @Inject
    public AnalyticsHandler(EventBus eventBus) {
        eventBus.addHandler(PlaceChangedEvent.TYPE, this);
    }

    @Override
    public void onPlaceChange(PlaceChangedEvent e) {
        if (accountId != null)
            trackPageview(e.getTokenWithoutParams());
        if (universalAccountId != null)
            trackUniversalPageview(e.getTokenWithoutParams());
    }

    public void setUniversalAnalyticsAccount(String accountId) {
        this.universalAccountId = accountId;
        setUniversalAccount(accountId);
    }

    public void setAccount(String accountId) {
        this.accountId = accountId;
        setAnalyticsAccount(accountId);
    }

    public void trackEvent(String action) {
        if (accountId != null)
            trackEvent(NAVIGATION, action);
        if (universalAccountId != null)
            trackUniversalEvent(NAVIGATION, action);
    }

    private native void setAnalyticsAccount(String accountId) /*-{
                                                              $wnd._gaq.push([ '_setAccount', accountId ]);
                                                              }-*/;

    private native void trackPageview(String page) /*-{
                                                   $wnd._gaq.push([ '_trackPageview', page ]);
                                                   }-*/;

    private native void trackEvent(String category, String action) /*-{
                                                                   $wnd._gaq.push([ '_trackEvent', category, action ]);
                                                                   }-*/;

    private native void setUniversalAccount(String accountId) /*-{
                                                              $wnd.ga('create', accountId, 'auto');
                                                              }-*/;

    private native void trackUniversalPageview(String page) /*-{
                                                            $wnd.ga('send', 'pageview', page);
                                                            }-*/;

    private native void trackUniversalEvent(String category, String action) /*-{
                                                                            $wnd.ga('send', 'event', category, action);
                                                                            }-*/;
}
