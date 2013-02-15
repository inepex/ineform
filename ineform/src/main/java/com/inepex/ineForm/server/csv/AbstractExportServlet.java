package com.inepex.ineForm.server.csv;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provider;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.server.handler.SetActionForExportServletHandler;
import com.inepex.ineForm.shared.dispatch.SetActionForExportServletAction.Renderer;
import com.inepex.ineForm.shared.tablerender.CsvRenderer.CsvRendererFactory;
import com.inepex.ineForm.shared.tablerender.HtmlRenderer.HtmlRendererFactory;
import com.inepex.ineForm.shared.tablerender.TableRenderer;
import com.inepex.ineForm.shared.tablerender.TrtdRenderer.TrtdRendererFactory;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectList;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectListResult;

@SuppressWarnings("serial")
public abstract class AbstractExportServlet extends HttpServlet{
	
	protected static final Logger _logger = LoggerFactory
			.getLogger(AbstractExportServlet.class);

	protected final Provider<CurrentLang> currLangProvider;
	protected final TrtdRendererFactory trtdRendererFactory;
	protected final HtmlRendererFactory htmlRendererFactory;
	protected final CsvRendererFactory csvRendererFactory;
	
	protected abstract void customizeAction(HttpServletRequest req, ObjectList action);
	protected abstract ObjectListResult getResult(ObjectList action) throws Exception;  
	
	public AbstractExportServlet(Provider<CurrentLang> currLangProvider,
						TrtdRendererFactory trtdRendererFactory, 
						HtmlRendererFactory htmlRendererFactory,
						CsvRendererFactory csvRendererFactory) {
		this.currLangProvider = currLangProvider;
		this.trtdRendererFactory = trtdRendererFactory;
		this.htmlRendererFactory = htmlRendererFactory;
		this.csvRendererFactory = csvRendererFactory;
	}
	
	@Override
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
			ObjectList action = (ObjectList)req.getSession()
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
			
			customizeAction(req, action);			
		
			ObjectListResult listResult = getResult(action);
			
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

			String extension = "";
			switch (rendererType) {
				case CSV:
					resp.setContentType("text/csv");
					resp.setCharacterEncoding("UTF-8");
					extension = ".csv";
					break;
				case HTML:
					resp.setContentType("text/html");
					resp.setCharacterEncoding("UTF-8");
					extension = ".html";
					break;
				case TRTD:
					resp.setContentType("application/vnd.ms-excel");
					resp.setCharacterEncoding("ISO-8859-2");
					extension = ".html";
					break;
			}
			
			resp.addHeader("Content-Disposition", "attachment; filename=" + fileName + extension);
			resp.getWriter().write(csvString);
			resp.getWriter().close();
		} catch (Exception e) {
			_logger.warn(e.getMessage(), e);
			resp.getWriter().write(IneFormI18n.csvError());
			resp.getWriter().close();
		}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
	}

}
