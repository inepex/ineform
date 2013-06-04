package com.inepex.translatorapp.client.page;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineFrame.client.dialog.ConfirmDialogBox;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.action.LangChangeAction;
import com.inepex.translatorapp.shared.action.TestLangChangeAction;
import com.inepex.translatorapp.shared.action.TestLangChangeResult;
import com.inepex.translatorapp.shared.kvo.LangConsts;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.ModuleLangConsts;

public class ChangeModuleLangPopup {
	
	public static interface Callback {
		void onChanged();
	}
	
	public static interface ChangeModuleLangPopupFactory {
		ChangeModuleLangPopup create(@Assisted AssistedObject moduleKvo);
	}
	
	private class DialogContent extends HandlerAwareFlowPanel {

		private final Callback callback;
		
		private final Button btn = new Button(IneFormI18n.OK());
		
		public DialogContent(Callback callback) {
			this.callback=callback;
			add(new Label(IneFormI18n.loading()));
			ineDispatch.execute(new RelationListAction(LangConsts.descriptorName, null, 0, 10000, false), 
					new IneDispatchBase.SuccessCallback<RelationListResult>() {

						@Override
						public void onSuccess(RelationListResult result) {
							clear();
							
							if(!result.isSuccess() || result.getList()==null || result.getList().isEmpty()) {
								add(new Label(IneFormI18n.cantDisplay()));
								return;
							}
							
							for(Relation r : result.getList()) {
								add(new LangRelCheckbox(r));
							}
							
							add(btn);
						}
					});
		}
		
		@Override
		protected void onLoad() {
			super.onLoad();
			
			registerHandler(btn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					dialogBox.hide();
					if(changed)
						callback.onChanged();
				}
			}));
		}
		
	}
	
	private class LangRelCheckbox extends HandlerAwareComposite{

		private final Relation langRelation;
		private final CheckBox cb;
		
		public LangRelCheckbox(Relation langRelation) {
			this.langRelation=langRelation;
			this.cb=new CheckBox(langRelation.getDisplayName());
			cb.setValue(isModuleLang(langRelation.getId()));
			cb.getElement().getStyle().setPadding(5, Unit.PX);
			initWidget(cb);
		}
		
		private boolean isModuleLang(Long id) {
			AssistedObjectHandler modHandler = assistedObjectHandlerFactory.createHandler(moduleKvo);
			IneList mLangs = modHandler.getList(ModuleConsts.k_langs);
			if(mLangs==null || mLangs.getRelationList()==null || mLangs.getRelationList().isEmpty())
				return false;
			
			for(Relation r : mLangs.getRelationList()) {
				Relation lang = assistedObjectHandlerFactory.createHandler(r.getKvo()).getRelation(ModuleLangConsts.k_lang);
				if(lang!=null && lang.getId().equals(id))
					return true;
			}
			
			return false;
		}

		@Override
		protected void onLoad() {
			super.onLoad();

			registerHandler(cb.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					event.stopPropagation();
					event.preventDefault();
					
					final boolean currentState = !cb.getValue();
					final long moduleId = moduleKvo.getId();
					final long langId = langRelation.getId();
					
					if(currentState==true) {
						ineDispatch.execute(new TestLangChangeAction(currentState, moduleId, langId), new IneDispatchBase.SuccessCallback<TestLangChangeResult>() {

							@Override
							public void onSuccess(TestLangChangeResult result) {
								ConfirmDialogBox dialog = new ConfirmDialogBox();
								dialog.show(translatorappI18n.moduleLangDelQuestion(
										""+result.getWillBeDeletedWithText(),
										""+result.getWillBeDeletedWithEmpty()),
										new ClickHandler() {
											
											@Override
											public void onClick(ClickEvent event) {
												doChange(currentState, moduleId, langId);
											}
										});
							}
						});
					} else {
						doChange(currentState, moduleId, langId);
					}
				}

				private void doChange(final boolean currentState, long moduleId, long langId) {
					ineDispatch.execute(new LangChangeAction(currentState, moduleId, langId), new IneDispatchBase.SuccessCallback<GenericActionResult>() {

						@Override
						public void onSuccess(GenericActionResult result) {
							changed=true;
							cb.setValue(!cb.getValue());
						}
					});
				}
			}));
		}
		
	}

	private final AssistedObject moduleKvo;
	private final AssistedObjectHandlerFactory assistedObjectHandlerFactory;
	private final IneDispatch ineDispatch;
	
	private DialogBox dialogBox;
	private boolean changed = false;
	
	@Inject
	public ChangeModuleLangPopup(@Assisted AssistedObject moduleKvo,
			AssistedObjectHandlerFactory assistedObjectHandlerFactory,
			IneDispatch ineDispatch) {
		this.moduleKvo=moduleKvo;
		this.ineDispatch=ineDispatch;
		this.assistedObjectHandlerFactory=assistedObjectHandlerFactory;
	}

	public void show(Callback callback) {
		dialogBox = new DialogBox(false, true);
		dialogBox.setWidget(new DialogContent(callback));
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText(IneFormI18n.EDIT());
		dialogBox.center();
	}
}
