package com.inepex.translatorapp.client.page;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.datamanipulator.RowCommandDataManipulator;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineFrame.shared.util.date.DateFormatter;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.Consts;
import com.inepex.translatorapp.shared.assist.ModuleRowAssist;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueHandlerFactory;
import com.inepex.translatorapp.shared.kvo.TranslatedValueHandlerFactory.TranslatedValueHandler;

public class RowListPage extends FlowPanelBasedPage {
	
	private final ServerSideDataConnector connector;
	private final RowCommandDataManipulator manipulator;
	private final TranslatedValueHandlerFactory translatedValueHandlerFactor;
	private final DateFormatter dateFormatter;  

	@Inject
	public RowListPage(ManipulatorFactory manipulatorFactory,
			DataConnectorFactory connectorFactory,
			TranslatedValueHandlerFactory translatedValueHandlerFactory,
			DateFormatter dateFormatter) {
		this.translatedValueHandlerFactor=translatedValueHandlerFactory;
		this.dateFormatter=dateFormatter;
		
		connector=connectorFactory.createServerSide(ModuleRowConsts.descriptorName);
		
		mainPanel.add(new HTML("<h2>"+translatorappI18n.rowListPage()+"</h2>"));
		
		manipulator=manipulatorFactory.createRowCommand(ModuleRowConsts.descriptorName, connector, true);
		
		manipulator.render();
		
		manipulator.getIneTable().addCellContentDisplayer(ModuleRowAssist.engVal, new TableFieldRenderer.CustomCellContentDisplayer() {
			
			@Override
			public String getCustomCellContent(AssistedObjectHandler rowKvo, String fieldId, ColRDesc colRDesc) {
				TranslatedValueHandler engVal = engVal(rowKvo);
				if(engVal==null || engVal.getValue()==null)
					return null;
				
				return SafeHtmlUtils.htmlEscape(subString(engVal.getValue(), 100, "..."));
				
			}
		});
		
		manipulator.getIneTable().addCellContentDisplayer(ModuleRowAssist.engModTime, new TableFieldRenderer.CustomCellContentDisplayer() {
			
			@Override
			public String getCustomCellContent(AssistedObjectHandler rowKvo, String fieldId, ColRDesc colRDesc) {
				TranslatedValueHandler engVal = engVal(rowKvo);
				if(engVal==null || engVal.getLastModTime()==null)
					return null;
				
				return RowListPage.this.dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_SEC_DATETIMEFORMAT,
						engVal.getLastModTime());
			}
		});
		
		mainPanel.add(manipulator);
	}

	private TranslatedValueHandler engVal(AssistedObjectHandler rowKvo) {
		IneList list = rowKvo.getList(ModuleRowConsts.k_values);
		if(list==null || list.getRelationList()==null || list.getRelationList().isEmpty())
			return null;
		
		for(Relation r : list.getRelationList()){
			if(r.getKvo()!=null) {
				if(Consts.defaultLang.equals(r.getKvo().getRelationUnchecked(TranslatedValueConsts.k_lang).getDisplayName())) {
					return translatedValueHandlerFactor.createHandler(r.getKvo());
				}
			}
		}
		
		return null;
	}
	
	@Override
	protected void onShow(boolean isFirstShow) {
		if(!isFirstShow)
			connector.update();
	}
	
	private static String subString(String s, int maxLength, String append) {
		if (s.length() > maxLength)
			s = s.substring(0, maxLength-append.length()) + append;
		return s;

	}
}
