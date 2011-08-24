package com.inepex.ineForm.server.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.google.inject.Singleton;
import com.inepex.ineForm.server.util.StringUtil;
import com.inepex.ineFrame.server.util.OnDemandProperties;
import com.inepex.ineom.shared.IFConsts;

@Singleton
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String PROPNAME = "conf/imageservice.properties";

	private OnDemandProperties props;
	private UploadProcessor uploadProc;
	private Logger logger;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
	}

	public void init() throws ServletException {
		logger = Logger.getLogger("com.inepex.image");
		props = new OnDemandProperties(PROPNAME);
		uploadProc = new UploadProcessor(props, logger);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO: sometimes it is not multipart, why?
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		/* Create a factory for disk-based file items */

		TreeMap<String, Object> query = new TreeMap<String, Object>();
		Map tbl = req.getParameterMap();
		query.putAll(tbl);

		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();

			/* Create a new file upload handler */
			ServletFileUpload upload = new ServletFileUpload(factory);

			/* Set overall request size constraint */
			upload.setSizeMax(Long.parseLong(props.getPropertiesInstance().getProperty(IFConsts.MAX_REQUEST_SIZE, "10485760")));

			/* Parse the request */
			try {
				List items = upload.parseRequest(req);

				// Process the uploaded items
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem fileItem = (FileItem) iter.next();
					String name = fileItem.getFieldName();

					if (!fileItem.isFormField()) {
						logger.fine("File field " + name + " with file name "
								+ fileItem.getName() + " detected.");

						InputStream inStream = fileItem.getInputStream();

						String extension = FilenameUtils.getExtension(fileItem
								.getName());
						boolean allowed = false;

						for (String s : ((String) props.getPropertiesInstance().get(IFConsts.ALLOWED_EXTENSIONS)).split(",")) {
							if (extension.equals(s)) {
								allowed = true;
								break;
							}
						}

						if (!allowed) {
							logger.info("Extension not allowed");
							PrintWriter out = resp.getWriter();
							out.print("-1");
						} else {
							String id = uploadProc.storeImage(
									inStream
									, fileItem.getName()
									, Boolean.parseBoolean(props.getPropertiesInstance().getProperty(IFConsts.RESIZEIMAGES, "false")));
							resp.setContentType("text/html");
							PrintWriter out = resp.getWriter();
							out.print(id);
						}
					}
				}
			} catch (Exception e) {
				logger.info(StringUtil.logStackTrace(e));
			}

		} else {
			logger.info("isMultipartContent = false");
		}
	}

}
