package com.inepex.ineFrame.client.navigation;

import com.google.gwt.event.shared.GwtEvent;

public class PlaceChangedEvent extends GwtEvent<PlaceChangedHandler> {

    public static Type<PlaceChangedHandler> TYPE = new Type<PlaceChangedHandler>();

    private String fullToken;

    private String tokenWithoutParams;

    public PlaceChangedEvent(String fullToken) {
        super();
        this.fullToken = fullToken;
        this.tokenWithoutParams = PlaceHandlerHelper
            .getPlacePart(PlaceHandlerHelper.removeRedirect(fullToken));
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<PlaceChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PlaceChangedHandler handler) {
        handler.onPlaceChange(this);
    }

    public String getFullToken() {
        return fullToken;
    }

    public void setFullToken(String fullToken) {
        this.fullToken = fullToken;
    }

    public String getTokenWithoutParams() {
        return tokenWithoutParams;
    }

    public void setTokenWithoutParams(String tokenWithoutParams) {
        this.tokenWithoutParams = tokenWithoutParams;
    }

}
