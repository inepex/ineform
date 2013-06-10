package com.inepex.translatorapp.client.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.datamanipulator.DataManipulator;
import com.inepex.ineForm.client.datamanipulator.ManipulatorFactory;
import com.inepex.ineForm.client.datamanipulator.RowCommandDataManipulator;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.events.CancelledEvent;
import com.inepex.ineForm.client.form.events.RenderedEvent;
import com.inepex.ineForm.client.form.events.SavedEvent;
import com.inepex.ineForm.client.form.widgets.ListBoxFW;
import com.inepex.ineForm.client.form.widgets.RelationListFW;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineForm.client.table.DataConnectorFactory;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationActionResult;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineFrame.client.navigation.NavigationProperties;
import com.inepex.ineFrame.client.navigation.PlaceRequestEvent;
import com.inepex.ineFrame.client.page.FlowPanelBasedPage;
import com.inepex.ineFrame.shared.util.date.DateFormatter;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.Consts;
import com.inepex.translatorapp.shared.action.RowListAction;
import com.inepex.translatorapp.shared.assist.ModuleRowAssist;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.ModuleLangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleRowConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueConsts;
import com.inepex.translatorapp.shared.kvo.TranslatedValueHandlerFactory;
import com.inepex.translatorapp.shared.kvo.TranslatedValueHandlerFactory.TranslatedValueHandler;

public class ModuleRowListPage extends FlowPanelBasedPage {
	
	private final TranslatedValueHandlerFactory translatedValueHandlerFactory;
	private final DateFormatter dateFormatter;
	private final AssistedObjectHandlerFactory objectHandlerFactory;
	private final FormContext formCtx;
	
	private final ServerSideDataConnector connector;
	private final RowCommandDataManipulator manipulator;
	private final RowListAction action;
	
	private Grid filterGrid;
	private ListBoxFW moduleListBox;
	private TextBox textBox;
	
	@Inject
	public ModuleRowListPage(ManipulatorFactory manipulatorFactory,
			DataConnectorFactory connectorFactory,
			TranslatedValueHandlerFactory translatedValueHandlerFactory,
			DateFormatter dateFormatter,
			AssistedObjectHandlerFactory objectHandlerFactory,
			FormContext formCtx) {
		this.translatedValueHandlerFactory=translatedValueHandlerFactory;
		this.dateFormatter=dateFormatter;
		this.objectHandlerFactory=objectHandlerFactory;
		this.formCtx=formCtx;
		
		createAndAddFilterGrid();
		
		action = new RowListAction();
		
		connector=connectorFactory.createServerSide(ModuleRowConsts.descriptorName);
		connector.setAssociatedListAction(action);
		
		manipulator=manipulatorFactory.createRowCommand(ModuleRowConsts.descriptorName, connector, true);
		setupTranslatedValueCreator();
		manipulator.setPageSize(800);
		manipulator.render();
		setCellContentDisplayers();
		mainPanel.add(manipulator);
	}

	private void createAndAddFilterGrid() {
		filterGrid = new Grid(2, 2);
		
		filterGrid.setHTML(0, 0, translatorappI18n.transPage_moduleSelect());
		moduleListBox = new ListBoxFW(formCtx, new RelationFDesc("", "", ModuleConsts.descriptorName).setNullable(true), new WidgetRDesc());
		filterGrid.setWidget(0, 1, moduleListBox);
		
		filterGrid.setHTML(1, 0, translatorappI18n.rowListPage_magicFilter());
		textBox=new TextBox();
		filterGrid.setWidget(1, 1, textBox);
		
		filterGrid.getElement().getStyle().setMarginBottom(25, Unit.PX);
		filterGrid.getElement().getStyle().setMarginLeft(5, Unit.PX);
		mainPanel.add(filterGrid);
	}

	private void setupTranslatedValueCreator() {
		manipulator.setFormCreationCallback(new DataManipulator.FormCreationCallback() {
			
			@Override
			public void onCreatingEditForm(IneForm ineForm) {
				hideFilterAddShowHandler((SaveCancelForm) ineForm);
				setupFormModuleValueListener(ineForm);
			}
			
			@Override
			public void onCreatingCreateForm(IneForm ineForm) {
				hideFilterAddShowHandler((SaveCancelForm) ineForm);
				setupFormModuleValueListener(ineForm);
			}

			private void hideFilterAddShowHandler(SaveCancelForm ineForm) {
				filterGrid.setVisible(false);
				
				ineForm.addSavedHandler(new SavedEvent.Handler() {
					
					@Override
					public void onSaved(SavedEvent event) {
						filterGrid.setVisible(true);
						connector.update();
					}
				});
				
				ineForm.addCancelledHandler(new CancelledEvent.Handler() {
					
					@Override
					public void onCancelled(CancelledEvent event) {
						filterGrid.setVisible(true);
					}
				});
			}

			private void setupFormModuleValueListener(final IneForm ineForm) {
				ineForm.addRenderedHandler(new RenderedEvent.Handler() {
					
					@Override
					public void onRendered(RenderedEvent event) {
						final ListBoxFW lb = (ListBoxFW) ineForm.getRootPanelWidget().getFormUnits().get(0).getWidgetByKey(ModuleRowConsts.k_module);
						final RelationListFW transValuesFw = (RelationListFW) ineForm.getRootPanelWidget().getFormUnits().get(0).getWidgetByKey(ModuleRowConsts.k_values);
						
						lb.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
							
							@Override
							public void onFormWidgetChange(FormWidgetChangeEvent e) {
								Long selecetedModuleId=null;
								if(lb.getRelationValue()!=null)
									selecetedModuleId=lb.getRelationValue().getId();
								
								if(selecetedModuleId==null) {
									transValuesFw.getRelationList().deleteAll();
									transValuesFw.reRenderRelations();
									return;
								}
								
								formCtx.ineDispatch.execute(new ObjectManipulationAction(ModuleConsts.descriptorName, selecetedModuleId), new IneDispatchBase.SuccessCallback<ObjectManipulationActionResult>() {

									@Override
									public void onSuccess(ObjectManipulationActionResult result) {
										if(!result.isSuccess() || result.getObjectsNewState()==null) {
											formCtx.eventBus.fireEvent(new PlaceRequestEvent(NavigationProperties.wrongTokenPlace));
											return;
										}
										
										Map<Long, String> langIds = new HashMap<Long, String>();
										IneList list = objectHandlerFactory.createHandler(result.getObjectsNewState()).getList(ModuleConsts.k_langs);
										for(Relation r : list.getRelationList()) {
											Relation realLang = r.getKvo().getRelationUnchecked(ModuleLangConsts.k_lang);
											langIds.put(realLang.getId(), realLang.getDisplayName());
										}
										
										for(Relation r : new ArrayList<Relation>(transValuesFw.getRelationList().getRelations())) {
											Long valueLang = r.getKvo().getRelationUnchecked(TranslatedValueConsts.k_lang).getId();
											if(langIds.containsKey(valueLang)) {
												langIds.remove(valueLang);
											} else {
												transValuesFw.getRelationList().delete(r);
											}
										}
										
										for(Long langId : langIds.keySet()) {
											AssistedObjectHandler newTranslated = objectHandlerFactory.createHandler(TranslatedValueConsts.descriptorName);
											newTranslated.set(TranslatedValueConsts.k_lang, new Relation(langId, langIds.get(langId)));
											Relation newValue = new Relation(newTranslated.getAssistedObject());
											transValuesFw.getRelationList().add(newValue);
											transValuesFw.getRelationList().change(newValue);
										}
										
										transValuesFw.reRenderRelations();
									}
								});
							}
						});
					}
				});
			}
		});
	}

	private void setCellContentDisplayers() {
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
				
				return ModuleRowListPage.this.dateFormatter.format(IneFormProperties.INETABLE_DEFAULT_SEC_DATETIMEFORMAT,
						engVal.getLastModTime());
			}
		});
	}

	private TranslatedValueHandler engVal(AssistedObjectHandler modulerRowKvo) {
		IneList list = modulerRowKvo.getList(ModuleRowConsts.k_values);
		if(list==null || list.getRelationList()==null || list.getRelationList().isEmpty())
			return null;
		
		for(Relation r : list.getRelationList()){
			if(r.getKvo()!=null) {
				if(Consts.defaultLang.equals(r.getKvo().getRelationUnchecked(TranslatedValueConsts.k_lang).getDisplayName())) {
					return translatedValueHandlerFactory.createHandler(r.getKvo());
				}
			}
		}
		
		return null;
	}
	
	private static String subString(String s, int maxLength, String append) {
		if (s.length() > maxLength)
			return s.substring(0, maxLength-append.length()) + append;
		
		return s;

	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		
		filterGrid.setVisible(true);
		
		registerHandler(moduleListBox.addFormWidgetChangeHandler(new FormWidgetChangeHandler() {
			
			@Override
			public void onFormWidgetChange(FormWidgetChangeEvent e) {
				fillActionAndUpdate();
			}
		}));
		
		
		registerHandler(textBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==KeyCodes.KEY_ENTER)
					fillActionAndUpdate();
			}
		}));
	}
	
	@Override
	protected void onShow(boolean isFirstShow) {
		fillActionAndUpdate();
	}

	private void fillActionAndUpdate() {
		if(moduleListBox.getRelationValue()!=null)
			action.setModuleId(moduleListBox.getRelationValue().getId());
		else
			action.setModuleId(null);
		
		if(textBox.getValue()!=null && textBox.getValue().length()>0)
			action.setMagicString(textBox.getValue());
		else
			action.setMagicString(null);
		
		connector.update();
	}
}
