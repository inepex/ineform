package com.inepex.ineForm.shared.upload;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UrlDownloadServiceAsync {

    public void downloadImageFromUrl(String url, AsyncCallback<String> callback);

}
