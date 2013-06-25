package com.inepex.ineForm.client.form.formunits;

import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.client.form.widgets.DummyFW;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.client.general.SimpleTableErrorMessageManager;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

public abstract class UIBinderFormUnitSkin extends Composite {

	protected UIBinderFormUnit uiBinderFormUnit;
	
	protected final FormContext formCtx;
	public UIBinderFormUnitSkin(FormContext formCtx) {
		this.formCtx = formCtx;
	}
	
	public void renderSkin(UIBinderFormUnit uiBinderFormUnit) {
		this.uiBinderFormUnit=uiBinderFormUnit;
		createUi();
	}
	
	public abstract void createUi();
	
	@UiFactory
	public FormWidget createFw(String key) {
		Node<FormRDescBase> descNode = uiBinderFormUnit.getSelectedFields().get(key);
		if(descNode==null) 
			return new CouldNotRenderFW();
		
		FDesc fDesc = uiBinderFormUnit.getFieldDesct(descNode);
	
		FormWidget createdWidget = formCtx.formWidgetFactory.createWidget(formCtx, uiBinderFormUnit, fDesc
				,(WidgetRDesc) descNode.getNodeElement(), formCtx.odFinder, formCtx.customKvoFwViewProvider);
		createdWidget = formCtx.formWidgetFactory.createDecorator(createdWidget, fDesc, (WidgetRDesc) descNode.getNodeElement());
		
		if (createdWidget != null) {
			uiBinderFormUnit.registerWidgetToDataFlow(descNode.getNodeId(), createdWidget);
			
			// if the widget should not be rendered, move on
			if (createdWidget.isShouldRender()) {
				uiBinderFormUnit.registerRenderedWidget(createdWidget);
				return createdWidget;
			} else {
				return new DummyFW();
			}
			
		} else {
			return new CouldNotRenderFW();
		}
	}
	
	@UiFactory
	public UIFWTitle createTitle(String key) {
		Node<FormRDescBase> descNode = uiBinderFormUnit.getSelectedFields().get(key);
		if(descNode==null) 
			return new UIFWTitle(IneFormI18n.ERR_CouldNotRenderWidget());
		
		if(((WidgetRDesc) descNode.getNodeElement()).getDisplayName()!=null) {
			return new UIFWTitle(((WidgetRDesc) descNode.getNodeElement()).getDisplayName());
		}
		
		FDesc fDesc = uiBinderFormUnit.getFieldDesct(descNode);
		return new UIFWTitle(fDesc.getDefaultDisplayName());
	}
	
	@UiFactory
	public UIMandatory createMandatorySign(String key) {
		Node<FormRDescBase> descNode = uiBinderFormUnit.getSelectedFields().get(key);
		FDesc fDesc = uiBinderFormUnit.getFieldDesct(descNode);
		return new UIMandatory(fDesc.getValidatorNames().contains(KeyValueObjectValidationManager.MANDATORY));
	}
	
	@UiFactory
	public UIFieldError createFieldError(String key) {
		UIFieldError fe = new UIFieldError();
		SimpleTableErrorMessageManager manager = new SimpleTableErrorMessageManager(fe.getElement());
		uiBinderFormUnit.registerErrorMessegeManager(key, manager);
		return fe;
	}
			
	private static class CouldNotRenderFW extends DenyingFormWidget {
		
		private CouldNotRenderFW() {
			super(null);
			initWidget(new InlineLabel(IneFormI18n.ERR_CouldNotRenderWidget()));
		}
		
	}
}
