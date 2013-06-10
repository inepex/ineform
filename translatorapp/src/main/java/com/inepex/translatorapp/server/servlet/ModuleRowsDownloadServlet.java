package com.inepex.translatorapp.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.inepex.inei18n.shared.LocalizedString;
import com.inepex.inei18n.util.DownloadLocalizablesDto;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.server.entity.dao.ModuleDao;
import com.inepex.translatorapp.server.handler.TranslatorAppUtil;

@Singleton
@SuppressWarnings("serial")
public class ModuleRowsDownloadServlet extends HttpServlet {

	private static final String param_moduleName = "moduleName";
	private static final String param_langs = "langs";
	
	private static class Params {
		String moduleName;
		String[] langs;
	}
	
	@Inject ModuleDao moduleDao;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DownloadLocalizablesDto dto = new DownloadLocalizablesDto();
		
		Params params = readParams(req, dto);
		if(dto.getWarning()==null) {
			fillDto(params, dto);
		}
		
		writeResponse(resp, dto);
	}

	private Params readParams(HttpServletRequest req, DownloadLocalizablesDto dto) {
		Params p = new Params();
		p.moduleName = req.getParameter(param_moduleName);
		if(p.moduleName==null || p.moduleName.length()<1 || notAlnum(p.moduleName)) {
			addWarning(dto, param_moduleName+" is not correctly set.");
		}
		
		String tmp = req.getParameter(param_langs);
		if(tmp==null || tmp.length()<1 || !tmp.matches("([a-z]+)(,[a-z]+)*")) {
			addWarning(dto, param_langs+" is not correctly set. (sample format: ...&"+param_langs+"=en,hu&... )");
		} else {
			p.langs=tmp.split(",");
		}
		
		return p;
	}

	private boolean notAlnum(String string) {
		return !string.matches("[\\p{Alnum}]+");
	}

	private void fillDto(Params params, DownloadLocalizablesDto dto) {
		Module module = moduleDao.findByName(params.moduleName);
		if(module==null) {
			addWarning(dto, "No module for name:"+params.moduleName);
			return;
		}
		
		Set<String> moduleLangs = new HashSet<>();
		for(ModuleLang ml : module.getLangs()) {
			moduleLangs.add(ml.getLang().getIsoName());
		}
		
		Set<String> reqLangs = new HashSet<>(params.langs.length);
		for(String s : params.langs) {
			reqLangs.add(s);
			if(!moduleLangs.contains(s)) {
				addWarning(dto, "Module doesn't support lang: "+s);
				return;
			}
		}
		
		if(moduleLangs.size()>reqLangs.size()) {
			addWarning(dto, "Module has got more langs than requested! ");
		}
		
		fillDto(module.getRows(), dto, reqLangs);
	}

	private void fillDto(List<ModuleRow> rows, DownloadLocalizablesDto dto, Set<String> reqLangs) {
		dto.setLocalizables(new HashMap<String, LocalizedString>());
		AtomicBoolean hasOutdatedRows = new AtomicBoolean(false);
		AtomicBoolean hasInvalidRows = new AtomicBoolean(false);
		for(ModuleRow row : rows) {
			LocalizedString ls = createLocalizedValue(row, reqLangs, hasOutdatedRows, hasInvalidRows);
			dto.getLocalizables().put(row.getKey(), ls);
		}
		
		if(hasOutdatedRows.get())
			addWarning(dto, "Module has outdated translated rows!");
		
		if(hasInvalidRows.get())
			addWarning(dto, "Module has invalid translated rows (bad parameters or brackets somewhere!)");
	}

	private LocalizedString createLocalizedValue(ModuleRow row, Set<String> reqLangs, AtomicBoolean hasOutdatedRows, AtomicBoolean hasInvalidRows) {
		LocalizedString ls = new LocalizedString(row.getKey(), row.getDescription());
		
		TranslatedValue engValue = TranslatorAppUtil.findEngValFor(row.getValues().get(0));
		for(TranslatedValue tv : row.getValues()) {
			if(reqLangs.contains(tv.getLang().getIsoName())) {
				ls.putString(tv.getLang().getIsoName(), tv.getValue());
				
				if(!hasOutdatedRows.get()) {
					hasInvalidRows.set(TranslatorAppUtil.isOutdated(tv, engValue));
				}
				
				if(!hasInvalidRows.get()) {
					hasInvalidRows.set(TranslatorAppUtil.isInvalid(tv, engValue));
				}
			}
		}
		
		return ls;
	}

	private void addWarning(DownloadLocalizablesDto dto, String msg) {
		if(dto.getWarning()==null)
			dto.setWarning(new ArrayList<String>());
		
		dto.getWarning().add(msg);
	}

	private void writeResponse(HttpServletResponse resp, DownloadLocalizablesDto dto) throws JsonGenerationException, JsonMappingException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		ObjectMapper objectMapper = new ObjectMapper();
		resp.getWriter().write(objectMapper.writeValueAsString(dto));
		resp.getWriter().close();
	}
}
