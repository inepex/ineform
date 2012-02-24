package com.inepex.ineForm.server.upload;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.inepex.ineForm.shared.upload.UrlDownloadService;
import com.inepex.ineFrame.server.util.OnDemandProperties;

public class UrlDownloadServiceImpl extends RemoteServiceServlet implements UrlDownloadService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7537449572086698368L;
	private OnDemandProperties props;
	private UploadProcessor uploadProc;
	
	public void init() throws ServletException {
		props = new OnDemandProperties(UploadServlet.PROPNAME);
		uploadProc = new UploadProcessor(props);

	}
	
	@Override
	public String downloadImageFromUrl(String urlString) throws Exception {
		URL url = new URL(urlString);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setAllowUserInteraction(false); // no user interact [like pop up]
		conn.setRequestProperty("Content-Type", "image");
		
		String[] urlArr = urlString.split("[/]");
		String filename = urlArr[urlArr.length-1];
		
		String uploadedFileName = uploadProc.storeImage(conn.getInputStream(), filename, true);
		return uploadedFileName;
	}

}
