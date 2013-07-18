package com.inepex.translatorapp.client.page;

import java.util.Arrays;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Grid;
import com.google.inject.Inject;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.ListBoxFW;
import com.inepex.ineForm.client.form.widgets.RadioEnumSelectorFW;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
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
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.client.page.popup.EditCallback;
import com.inepex.translatorapp.client.page.popup.TransRowEditPopup;
import com.inepex.translatorapp.shared.action.TransTableListAction;
import com.inepex.translatorapp.shared.action.TranslateListingType;
import com.inepex.translatorapp.shared.assist.TranslateTableRowAssist;
import com.inepex.translatorapp.shared.kvo.LangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.TranslateTableRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;

public class TranslatorPage extends FlowPanelBasedPage {

	private RadioEnumSelectorFW listTypeRadioButton;
	private ListBoxFW moduleListBox;
	
	private final FormContext formCtx;
	private final AssistedObjectHandlerFactory handlerFactory;
	private final IneDispatch ineDispatch;
	
	private final ServerSideDataConnector connector;
	private final TransTableListAction action;
	
	private final IneTable table;	
	
	@Inject
	public TranslatorPage(DataConnectorFactory connectorFactory, IneTableFactory tableFactory, FormContext formCtx,
			AssistedObjectHandlerFactory handlerFactory,
			IneDispatch ineDispatch) {
		this.handlerFactory=handlerFactory;
		this.formCtx=formCtx;
		this.ineDispatch=ineDispatch;
		
		createAndAddFilterGrid();
		
		action=new TransTableListAction();
		
		connector = connectorFactory.createServerSide(TranslateTableRowConsts.descriptorName);
		connector.setAssociatedListAction(action);
		
		table = tableFactory.createSimple(TranslateTableRowConsts.descriptorName, connector);
		table.setPageSize(200);
		addCellContentDisplayers();
		addUserCommands();
		table.renderTable();
		
		mainPanel.add(table);
	}
	
	private void addUserCommands() {
		table.addCommand(new IneTable.UserCommand() {
			
			@Override
			public boolean visible(AssistedObject kvoOfRow) {
				return true;
			}
			
			@Override
			public void onCellClicked(final AssistedObject kvoOfRow) {
				new TransRowEditPopup(kvoOfRow.getStringUnchecked(TranslateTableRowConsts.k_engVal),
						handlerFactory.createHandler(kvoOfRow).getRelatedString(TranslateTableRowAssist.tv(TranslatedValueConsts.k_value)))
					.show(new EditCallback() {
						
						@Override
						public void onCancelled() {
						}
						
						@Override
						public void onSave(String newTranslated) {
							handlerFactory.createHandler(kvoOfRow)
								.getRelatedKVOMultiLevel(Arrays.asList(TranslateTableRowConsts.k_translatedValue, TranslatedValueConsts.k_value))
								.setUnchecked(TranslatedValueConsts.k_value, newTranslated);
							
							saveRowChanges(kvoOfRow);
						}
					});
			}
			
			@Override
			public String getCommandCellText() {
				return translatorappI18n.showEditpopup();
			}
		});
		
		table.addCommand(new IneTable.UserCommand() {
			
			@Override
			public boolean visible(AssistedObject kvoOfRow) {
				return true;
			}
			
			@Override
			public void onCellClicked(AssistedObject kvoOfRow) {
				saveRowChanges(kvoOfRow);
			}

			@Override
			public String getCommandCellText() {
				return IneFormI18n.SAVE();
			}
		});
	}
	
	private void saveRowChanges(AssistedObject kvoOfRow) {
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

	private void addCellContentDisplayers() {
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
				
				return "<img src='flags/png/"+countryCode+".png' title='"+lang+"' style='display: block; margin: 0 auto;'/>";
			}
		});
		
		table.addCellContentDisplayer(TranslateTableRowAssist.flagsColumn, new TableFieldRenderer.CustomCellContentDisplayer() {
			
			@Override
			public String getCustomCellContent(AssistedObjectHandler rowKvo, String fieldId, ColRDesc colRDesc) {
				StringBuffer sb = new StringBuffer();
				if(Boolean.TRUE.equals(rowKvo.getBoolean(TranslateTableRowConsts.k_recent))) {
					sb.append("<img src='icons/png/new.png' title='"+translatorappI18n.recent()+"' />");
				}
				
				if(Boolean.TRUE.equals(rowKvo.getBoolean(TranslateTableRowConsts.k_outDated))) {
					sb.append("<img src='icons/png/warning.png' title='"+translatorappI18n.outdated()+"'/>");
				}
				
				if(Boolean.TRUE.equals(rowKvo.getBoolean(TranslateTableRowConsts.k_invalid))) {
					sb.append("<img src='icons/png/exclamation.png' title='"+translatorappI18n.invalid()+"'/>");
				}
				
				if(sb.length()<1)
					return null;
				else
					return sb.toString();
			}
		});
	}

	private void createAndAddFilterGrid() {
		Grid filterGrid = new Grid(2, 2);
		
		filterGrid.setHTML(0, 0, translatorappI18n.transPage_listmodeSelect());
		listTypeRadioButton = new RadioEnumSelectorFW(
				new LongFDesc().setNullable(false),
				TranslateListingType.getValuesAsString(),
				new WidgetRDesc());
		filterGrid.setWidget(0, 1, listTypeRadioButton);
		
		filterGrid.setHTML(1, 0, translatorappI18n.transPage_moduleSelect());
		moduleListBox = new ListBoxFW(formCtx, new RelationFDesc("", "", ModuleConsts.descriptorName).setNullable(true), new WidgetRDesc());
		filterGrid.setWidget(1, 1, moduleListBox);
		
		filterGrid.getElement().getStyle().setMarginBottom(25, Unit.PX);
		filterGrid.getElement().getStyle().setMarginLeft(5, Unit.PX);
		filterGrid.getElement().getStyle().setHeight(90, Unit.PX);
		filterGrid.getElement().getStyle().setWidth(700, Unit.PX);
		mainPanel.add(filterGrid);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		
		registerHandler(listTypeRadioButton.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
			
			@Override
			public void onFormWidgetChange(FormWidgetChangeEvent e) {
				fillActionAndUpdate();
			}
		}));
		
		registerHandler(moduleListBox.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
			
			@Override
			public void onFormWidgetChange(FormWidgetChangeEvent e) {
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
