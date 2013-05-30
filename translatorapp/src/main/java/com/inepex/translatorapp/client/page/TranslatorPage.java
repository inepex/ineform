package com.inepex.translatorapp.client.page;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.ListBoxFW;
import com.inepex.ineForm.client.form.widgets.RadioEnumSelectorFW;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.client.table.IneTableFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;
import com.inepex.translatorapp.shared.action.TransTableListAction;
import com.inepex.translatorapp.shared.action.TranslateListingType;
import com.inepex.translatorapp.shared.assist.TranslateTableRowAssist;
import com.inepex.translatorapp.shared.kvo.LangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.TranslateTableRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;

public class TranslatorPage extends FlowPanelBasedPage {

	private final RadioEnumSelectorFW listTypeRadioButton;
	private final ListBoxFW moduleListBox;
	private final Button filterBtn;
	
	private final ServerSideDataConnector connector;
	private final TransTableListAction action;
	
	private final IneTable table;	
	
	@Inject
	public TranslatorPage(DataConnectorFactory connectorFactory, IneTableFactory tableFactory, FormContext formCtx,
			final AssistedObjectHandlerFactory handlerFactory,
			final IneDispatch ineDispatch) {
		listTypeRadioButton = new RadioEnumSelectorFW(
				new LongFDesc().setNullable(false),
				TranslateListingType.getValuesAsString(),
				new WidgetRDesc());
		
		mainPanel.add(listTypeRadioButton);
		
		moduleListBox = new ListBoxFW(formCtx, new RelationFDesc("", "", ModuleConsts.descriptorName).setNullable(true), new WidgetRDesc());
		mainPanel.add(moduleListBox);
		
		filterBtn= new Button(IneFormI18n.FILTER());
		mainPanel.add(filterBtn);
		
		action=new TransTableListAction();
		
		connector = connectorFactory.createServerSide(TranslateTableRowConsts.descriptorName);
		connector.setAssociatedListAction(action);
		
		table = tableFactory.createSimple(TranslateTableRowConsts.descriptorName, connector);
		table.addCellContentDisplayer(TranslateTableRowAssist.tv(TranslatedValueConsts.k_lang), new TableFieldRenderer.CustomCellContentDisplayer() {
			
			@Override
			public String getCustomCellContent(AssistedObjectHandler rowKvo, String fieldId, ColRDesc colRDesc) {
				String multiKey = TranslateTableRowAssist.tv(TranslatedValueConsts.k_lang);
				Relation langRelation = rowKvo.getRelatedRelation(multiKey);
				if(langRelation==null)
					return null;
				
				String lang = langRelation.getDisplayName();
				if(langRelation.getKvo()==null)
					return lang;
				
				String countryCode = langRelation.getKvo().getStringUnchecked(LangConsts.k_countryCode); 
				if(countryCode==null)
					return lang;
				
				return "<img src='flags/png/"+countryCode+".png' title='"+lang+"' />";
			}
		});
		
		table.addCellContentDisplayer(TranslateTableRowAssist.flags, new TableFieldRenderer.CustomCellContentDisplayer() {
			
			@Override
			public String getCustomCellContent(AssistedObjectHandler rowKvo, String fieldId, ColRDesc colRDesc) {
				StringBuffer sb = new StringBuffer();
				if(Boolean.TRUE.equals(rowKvo.getBoolean(TranslateTableRowConsts.k_recent))) {
					sb.append("<img src='icons/png/new.png' />");
				}
				
				if(Boolean.TRUE.equals(rowKvo.getBoolean(TranslateTableRowConsts.k_outDated))) {
					sb.append("<img src='icons/png/cross.png' />");
				}
				
				if(sb.length()<1)
					return null;
				else
					return sb.toString();
			}
		});
		
		table.addCommand(new IneTable.UserCommand() {
			
			@Override
			public boolean visible(AssistedObject kvoOfRow) {
				return true;
			}
			
			@Override
			public void onCellClicked(AssistedObject kvoOfRow) {
				Relation transValue = handlerFactory.createHandler(kvoOfRow).getRelation(TranslateTableRowConsts.k_translatedValue);
				AssistedObjectHandler manhandler = handlerFactory.createHandler(TranslatedValueConsts.descriptorName);
				manhandler.setId(transValue.getId());
				manhandler.set(TranslatedValueConsts.k_value, transValue.getKvo().getStringUnchecked(TranslatedValueConsts.k_value));
				
				ObjectManipulationAction oma = new ObjectManipulationAction(ManipulationTypes.CREATE_OR_EDIT_REQUEST, manhandler.getAssistedObject());
				ineDispatch.execute(oma, new IneDispatchBase.SuccessCallback<ObjectManipulationActionResult>() {

					@Override
					public void onSuccess(ObjectManipulationActionResult result) {
						connector.update();
					}
				});
			}
			
			@Override
			public String getCommandCellText() {
				return IneFormI18n.SAVE();
			}
		});
		
		table.renderTable();
		
		mainPanel.add(table);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		
		registerHandler(filterBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fillActionAndUpdate();
			}
		}));
	}
	

	@Override
	protected void onShow(boolean isFirstShow) {
		fillActionAndUpdate();
	}

	private void fillActionAndUpdate() {
		action.setListType(TranslateListingType.values()[listTypeRadioButton.getLongValue().intValue()]);
		if(moduleListBox.getRelationValue()==null)
			action.setModuleName(null);
		else
			action.setModuleName(moduleListBox.getRelationValue().getDisplayName());
		
		connector.update();
	}
	
}
