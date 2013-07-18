package com.inepex.translatorapp.client.page.popup;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.IneButtonType;
import com.inepex.ineForm.client.general.IneDialogBox;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineFrame.client.dialog.ErrorDialogBox;
import com.inepex.ineFrame.client.misc.HandlerHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.dispatch.GenericActionResult;
import com.inepex.translatorapp.client.i18n.translatorappI18n;
import com.inepex.translatorapp.shared.Consts;
import com.inepex.translatorapp.shared.action.RowUploadAction;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;

public class ModuleUploadPopup {

	private class DialogContent extends VerticalPanel {
		
		private final ChangedCallback callback;
		private final HandlerHandler hh = new HandlerHandler();
		
		private final TextBox header;
		private final TextArea rows;
		
		private final IneButton cancel;
		private final IneButton ok;
		
		DialogContent(ChangedCallback callback) {
			this.callback=callback;
			
			add(new HTML(translatorappI18n.upload_header()));
			
			header=new TextBox();
			header.getElement().getStyle().setWidth(600, Unit.PX);
			header.setValue(createDefaultHeaderText());
			add(header);
			
			add(new HTML(translatorappI18n.upload_rows()));
			
			rows = new TextArea();
			rows.getElement().getStyle().setWidth(600, Unit.PX);
			rows.getElement().getStyle().setHeight(400, Unit.PX);
			add(rows);
			
			HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(10);
			
			cancel=new IneButton(IneButtonType.CANCEL, IneFormI18n.CANCEL());
			hp.add(cancel);
			
			ok = new IneButton(IneButtonType.ACTION, IneFormI18n.OK());
			hp.add(ok);
			
			add(hp);
		}
		
		private String createDefaultHeaderText() {
			StringBuffer sb = new StringBuffer();
			sb.append(Consts.Upload.key);
			sb.append(Consts.Upload.SEP);
			sb.append(Consts.Upload.desc);
			sb.append(Consts.Upload.SEP);
			
			for(Relation r : assistedObjectHandlerFactory.createHandler(moduleKvo).getList(ModuleConsts.k_langs).getRelationList()) {
				sb.append(r.getDisplayName());
				sb.append(Consts.Upload.SEP);
			}
			
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}

		@Override
		protected void onLoad() {
			super.onLoad();
			
			hh.registerHandler(ok.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					if(rows.getText()==null || rows.getText().isEmpty()) {
						dialogBox.hide();
						return;
					}
						
					ineDispatch.execute(new RowUploadAction(moduleKvo.getId(), header.getText(), rows.getText()), new IneDispatchBase.SuccessCallback<GenericActionResult>() {

						@Override
						public void onSuccess(GenericActionResult result) {
							if(result.isSuccess()) {
								dialogBox.hide();
								callback.onChanged();
								return;
							}
							
							ErrorDialogBox.showError(result.getMessage());
						}
					});
				}
			}));
			
			hh.registerHandler(cancel.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					dialogBox.hide();
				}
			}));
		}
		
		@Override
		protected void onDetach() {
			super.onDetach();
			
			hh.unregister();
		}
	}
	
	@Inject AssistedObjectHandlerFactory assistedObjectHandlerFactory;
	@Inject IneDispatch ineDispatch;
	
	private IneDialogBox dialogBox;
	private AssistedObject moduleKvo;
	
	public void show(AssistedObject kvoOfRow, ChangedCallback callback) {
		this.moduleKvo=kvoOfRow;
		
		dialogBox = new IneDialogBox(false, true);
		dialogBox.setWidget(new DialogContent(callback));
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		dialogBox.setText(IneFormI18n.EDIT());
		dialogBox.center();
	}
	
	
}
