package com.inepex.translatorapp.server.handler;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.RollbackException;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.constraint.Unique;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.ineForm.server.util.StringUtil;
import com.inepex.ineFrame.server.auth.SessionScopedAuthStat;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.server.entity.dao.ModuleDao;
import com.inepex.translatorapp.shared.Consts;
import com.inepex.translatorapp.shared.action.RowUploadAction;

public class RowUploadActionHandler extends AbstractIneHandler<RowUploadAction, GenericActionResult>{

	@SuppressWarnings("serial")
	private static class ValException extends Exception {

		public ValException(String message) {
			super(message);
		}
	}
	
	@Inject ModuleDao moduleDao;
	@Inject Provider<SessionScopedAuthStat> authProv;
	
	@Override
	protected GenericActionResult doExecute(RowUploadAction action,
			ExecutionContext context) throws AuthenticationException,
			DispatchException {
		
		if(StringUtil.isNullOrEmpty(action.getHeader())
				|| StringUtil.isNullOrEmpty(action.getRows())
				|| action.getModuleId()==null)
			throw new IllegalArgumentException();
		
		Module module = moduleDao.findById(action.getModuleId());
		Map<String, Long> langIdsByName = fetchLangs(module);
		
		try {
			@SuppressWarnings("resource")
			CsvMapReader mapReader = new CsvMapReader(createActionContentReader(action), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			
			String[] header = mapReader.getHeader(true);
			validateHeader(langIdsByName, header);
			CellProcessor[] processors = createProcessors(header);
			Long userId = currentUser();
			
			Map<String, Object> rowMap;
			while( (rowMap = mapReader.read(header, processors)) != null ) {
                addRow(module, rowMap, langIdsByName, userId);
			}
			
			moduleDao.mergeTrans(module);
			
			return new GenericActionResult();
		} catch (RollbackException e) {
			return new GenericActionResult(
					translatorappI18n.rowUpload_rowDuplication()
					, false);
		} catch (SuperCsvException e) {
			return new GenericActionResult(
					translatorappI18n.rowUpload_invalidLine(""+(e.getCsvContext().getLineNumber()-1))
					, false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ValException e) {
			return new GenericActionResult(e.getMessage(), false);
		}
	}

	private Long currentUser() {
		SessionScopedAuthStat stat = authProv.get();
		synchronized (stat) {
			Long userId = stat.getUserId();
			if(userId==null)
				throw new IllegalStateException();
			
			return userId;
		}
	}

	private void addRow(Module module, Map<String, Object> rowMap, Map<String, Long> langIdsByName, Long userId) {
		ModuleRow row = new ModuleRow();
		row.setKey((String) rowMap.get(Consts.Upload.key));
		row.setDescription((String) rowMap.get(Consts.Upload.desc));
		row.setModule(module);
		module.getRows().add(row);
		
		long now = System.currentTimeMillis();
		
		for(Entry<String, Long> entry : langIdsByName.entrySet()) {
			TranslatedValue tv = new TranslatedValue();
			tv.setRow(row);
			tv.setLang(new Lang(entry.getValue()));
			tv.setLastModTime(now);
			tv.setLastModUser(new User(userId));
			tv.setValue((String) rowMap.get(entry.getKey()));
			
			row.getValues().add(tv);
		}
	}

	private CellProcessor[] createProcessors(String[] header) {
		CellProcessor[] procs = new CellProcessor[header.length];
		procs[0]= new Unique();
		for(int i=1; i< header.length; i++) {
			procs[i]=new ConvertNullTo("");
		}
		
		return procs;
	}

	private Map<String, Long> fetchLangs(Module module) {
		HashMap<String, Long> ret = new HashMap<>();
		
		for(ModuleLang ml : module.getLangs())
			ret.put(ml.getLang().getIsoName(), ml.getLang().getId());
		
		return ret;
	}

	private void validateHeader(Map<String, Long> langIdsByName, String[] header) throws ValException {
		boolean key=false;
		boolean desc=false;
		HashMap<String, Long> copyMap = new HashMap<>(langIdsByName);
		
		for(String s : header) {
			if(copyMap.containsKey(s)) {
				copyMap.remove(s);
				continue;
			}
			
			if(Consts.Upload.key.equals(s)) {
				if(key)
					throw new ValException(translatorappI18n.rowUpload_wasTwice(Consts.Upload.key));
				
				key=true;
				continue;
			}
			
			if(Consts.Upload.desc.equals(s)) {
				if(desc)
					throw new ValException(translatorappI18n.rowUpload_wasTwice(Consts.Upload.desc));
				
				desc=true;
				continue;
			}
			
			throw new ValException(translatorappI18n.rowUpload_extraColumn(s));
		}
		
		if(!key)
			throw new ValException(translatorappI18n.rowUpload_notInHeader(Consts.Upload.key));
		if(!desc)
			throw new ValException(translatorappI18n.rowUpload_notInHeader(Consts.Upload.desc));
		
		if(!copyMap.isEmpty())
			throw new ValException(translatorappI18n.rowUpload_notForEveryLang());
	}

	private Reader createActionContentReader(RowUploadAction action) {
		return new StringReader(action.getHeader()+"\n"+action.getRows());
	}

	@Override
	public Class<RowUploadAction> getActionType() {
		return RowUploadAction.class;
	}
}
