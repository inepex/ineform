package com.inepex.ineForm.server.csv;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.server.handler.SetActionForExportServletHandler;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectListActionResult;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction.Renderer;
import com.inepex.ineForm.shared.tablerender.CsvRenderer.CsvRendererFactory;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer.HtmlRendererFactory;
import com.inepex.ineForm.shared.tablerender.TableRenderer;
import com.inepex.ineForm.shared.tablerender.TrtdRenderer.TrtdRendererFactory;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

@Singleton
public class ExportServlet extends HttpServlet{
	
	private static final Logger _logger = LoggerFactory
			.getLogger(ExportServlet.class);


	private static final long serialVersionUID = 9213396221597526509L;
	
	final Provider<DescriptorStore> descStoreProvider;
	final Provider<CurrentLang> currLangProvider;
	final Dispatch dispatcher;
	final DateProvider dateProvider;
	private final TrtdRendererFactory trtdRendererFactory;
	private final HtmlRendererFactory htmlRendererFactory;
	private final CsvRendererFactory csvRendererFactory;
	
	@Inject
	public ExportServlet(Provider<DescriptorStore> descStoreProvider, Provider<CurrentLang> currLangProvider
						, Dispatch dispatcher, DateProvider dateProvider,
						TrtdRendererFactory trtdRendererFactory, 
						HtmlRendererFactory htmlRendererFactory,
						CsvRendererFactory csvRendererFactory) {
		this.descStoreProvider = descStoreProvider;
		this.currLangProvider = currLangProvider;
		this.dispatcher = dispatcher;
		this.dateProvider = dateProvider;
		this.trtdRendererFactory = trtdRendererFactory;
		this.htmlRendererFactory = htmlRendererFactory;
		this.csvRendererFactory = csvRendererFactory;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		try {
			if (req.getSession().getAttribute(SetActionForExportServletHandler.actionForCsvKey) == null
					|| req.getSession().getAttribute(SetActionForExportServletHandler.filenameForCsvKey) == null
					|| req.getSession().getAttribute(SetActionForExportServletHandler.rendererForCsvKey) == null){
				resp.getWriter().write(IneFormI18n.csvInvalid());
				resp.getWriter().close();
				return;
			}
			ObjectListAction action = (ObjectListAction)req.getSession()
				.getAttribute(SetActionForExportServletHandler.actionForCsvKey);
			req.getSession().removeAttribute(SetActionForExportServletHandler.actionForCsvKey);
			String fileName = (String)req.getSession()
				.getAttribute(SetActionForExportServletHandler.filenameForCsvKey);
			
			if(Boolean.TRUE.equals(req.getSession()
					.getAttribute(SetActionForExportServletHandler.appendDateToFileName))) {
				fileName = fileName +"_"+new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
					
			req.getSession().removeAttribute(SetActionForExportServletHandler.filenameForCsvKey);
			String tableRDescName = (String)req.getSession()
				.getAttribute(SetActionForExportServletHandler.tableRDescForCsvKey);
			Boolean withHeader = (Boolean)req.getSession()
				.getAttribute(SetActionForExportServletHandler.withHeaderForCsvKey);
			Renderer rendererType = (Renderer)req.getSession()
				.getAttribute(SetActionForExportServletHandler.rendererForCsvKey);
			
			action.setFirstResult(0);
			action.setNumMaxResult(1000000);
			
			
			currLangProvider.get().setLangOverride((String) req.getSession()
				.getAttribute(SetActionForExportServletHandler.rendererLanguage));
			
			ObjectListActionResult listResult = (ObjectListActionResult)dispatcher.execute(action);
			
			TableRenderer renderer = null;
			switch (rendererType) {
			case CSV:
				renderer = csvRendererFactory.create(action.getDescriptorName(), tableRDescName);
				renderer.setRenderHeader(withHeader);
				break;
			case HTML:
				renderer = htmlRendererFactory.create(action.getDescriptorName(), tableRDescName);
				renderer.setRenderHeader(withHeader);
				break;
				
			case TRTD:
				renderer = trtdRendererFactory.create(action.getDescriptorName(), tableRDescName);
				renderer.setRenderHeader(withHeader);
				break;
			}
			
			String csvString = renderer.render(listResult.getList());

			switch (rendererType) {
				case CSV:
					resp.setContentType("text/csv");
					resp.setCharacterEncoding("UTF-8");
					break;
				case HTML:
					resp.setContentType("text/html");
					resp.setCharacterEncoding("UTF-8");
					break;
				case TRTD:
					resp.setContentType("application/vnd.ms-excel");
					resp.setCharacterEncoding("ISO-8859-2");
					break;
			}
			
			resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			resp.getWriter().write(csvString);
			resp.getWriter().close();
		} catch (DispatchException e) {
			_logger.warn(e.getMessage(), e);
			resp.getWriter().write(IneFormI18n.csvError());
			resp.getWriter().close();
		}
		
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
	}

}
