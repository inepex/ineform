package com.inepex.ineForm.shared.upload;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("urldownloadService")
public interface UrlDownloadService extends RemoteService {

    public String downloadImageFromUrl(String url) throws Exception;
}
