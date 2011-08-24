package com.inepex.ineForm.client.form.formunits;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeEvent;
import com.inepex.ineForm.client.form.widgets.event.FormWidgetChangeHandler;
import com.inepex.ineForm.client.general.ErrorMessageManagerInterface;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineFrame.client.misc.HandlerAwareFlowPanel;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.util.SharedUtil;

/**
 * manage data flow and widget registration (everything except concrete display)
 *
 */
public abstract class AbstractFormUnit extends HandlerAwareFlowPanel {
	
	//initial
	protected final String objectDescriptorsName;
	protected final ObjectDesc objectDescriptor;
	
	//data flow
	protected TreeMap<String, HTML> titlesByKey = new TreeMap<String, HTML>();
	protected TreeMap<String, FormWidget> widgetsByKey = new TreeMap<String, FormWidget>();
	protected TreeMap<String, ErrorMessageManagerInterface> errormanagersByKey = new TreeMap<String, ErrorMessageManagerInterface>();
	
	//gui behaviour
	protected FormWidget firstFocusableWidget = null;
	protected List<FormWidget> 	renderedWidgets = new ArrayList<FormWidget>();
	
	private final FormWidgetChangeHandler formChangeHandler = new FormWidgetChangeHandler() {
		@Override
		public void onFormWidgetChange(FormWidgetChangeEvent e) {
			fireEvent(e);
		}
	};
	
//********************* join methods
	
	public void joinToDataFlow(String key, FormWidget fw) {
		widgetsByKey.put(key, fw);
	}
	
	public void joinAndAddToMainPanel(String key, FormWidget fw) {
		joinToDataFlow(key, fw);
		add(fw);
	}
	
	public void addToMainPanel(Widget w) {
		add(w);
	}
	
	public void insertIntoMainPanel(Widget w, int beforeIndex) {
		insert(w, beforeIndex);
	}
	
	public void joinToDataFlow(String key, FormWidget fw, ErrorMessageManagerInterface errorMessageManager) {
		widgetsByKey.put(key, fw);
		errormanagersByKey.put(key, errorMessageManager);
	}
	
	public void joinAndAddToMainPanel(String key, FormWidget fw, ErrorMessageManagerInterface errorMessageManager) {
		joinToDataFlow(key, fw, errorMessageManager);
		add(fw);
	}

//********************* initialize form
	
	DescriptorStore descStore;
	
	public AbstractFormUnit(String objectDescriptorsName, DescriptorStore ds) {
		this.objectDescriptorsName=objectDescriptorsName;
		this.descStore = ds;
		this.objectDescriptor=ds.getOD(objectDescriptorsName);
	}

	public void registerErrorMessegeManager(String key, ErrorMessageManagerInterface errorMessageManager) {
		errormanagersByKey.put(key, errorMessageManager);
	}

	public void registerWidgetToDataFlow(String key, FormWidget widget) {
		widgetsByKey.put(key, widget);
	}
	
	public void registerTitle(String key, HTML title) {
		titlesByKey.put(key, title);
	}
	
	public void registerRenderedWidget(FormWidget widget) {
		renderedWidgets.add(widget);

		if (firstFocusableWidget == null && widget.isFocusable())
			firstFocusableWidget = widget;

		widget.addFormWidgetChangeHandler(formChangeHandler);
	}
	
//********************* for rendering
	
	/**
	 * @param nodeName - the name of node
	 * @return - a fielddescriptor belongs to the name or null
	 */
	public FDesc getFieldDesct(Node<FormRDescBase> node) {
		if (SharedUtil.isMultilevelKey(node.getNodeId()))			
			return descStore.getRelatedFieldDescrMultiLevel(objectDescriptor, node.getNodeIdAsList());
		else
			return objectDescriptor.getField(node.getNodeId());
	}
	
//********************* gui behaviour
 
	/**
	 * to handle change of form (and subforms, and sub-subforms...)
	 */
	public HandlerRegistration addFormWidgetChangeHandler(FormWidgetChangeHandler h) {
		return addHandler(h, FormWidgetChangeEvent.TYPE);
	}
	
	public void focusFirstWidget() {
		if (firstFocusableWidget != null)
			firstFocusableWidget.setFocus(true);
	}

	public void setEnabled(boolean enabled) {
		for (FormWidget widget : renderedWidgets) {
			widget.setEnabled(enabled);
		}
	}
	
	public abstract void setFWVisible(String key, boolean visible);
	
	public void setRowTitle(String key, String titleText) {
		if(titlesByKey.get(key)!=null) 
			titlesByKey.get(key).setHTML(titleText);
	}

//********************* data flow for - objectEditor's data management 
	
	public Set<String> getFormWidgetKeySet() {
		return widgetsByKey.keySet();
	}

	public Set<String> getErrorManagerKeySet() {
		return errormanagersByKey.keySet();
	}
	
	
	
	public TreeMap<String, ErrorMessageManagerInterface> getErrormanagersByKey() {
		return errormanagersByKey;
	}

	public FormWidget getWidgetByKey(String key) {
		return widgetsByKey.get(key);
	}

	public void setSingleWidgetValue(String key, Boolean value){
		FormWidget widget = widgetsByKey.get(key);
		if (widget == null)
			return;
		
		if (widget.handlesBoolean())
			widget.setBooleanValue(value);
	}

	public void setSingleWidgetValue(String key, Double value){
		FormWidget widget = widgetsByKey.get(key);
		if (widget == null)
			return;
		
		if (widget.handlesRelation())
			widget.setDoubleValue(value);
	}
	
	public void setSingleWidgetValue(String key, Long value){
		FormWidget widget = widgetsByKey.get(key);
		if (widget == null)
			return;
		
		if (widget.handlesLong())
			widget.setLongValue(value);
	}
	
	public void setSingleWidgetValue(String key, IneList value) {
		FormWidget widget = widgetsByKey.get(key);
		if (widget == null)
			return;
		
		if (widget.handlesList())
			widget.setListValue(value);		
	}

	public void setSingleWidgetValue(String key, Relation value){
		FormWidget widget = widgetsByKey.get(key);
		if (widget == null)
			return;
		
		if (widget.handlesRelation())
			widget.setRelationValue(value);
	}
	
	public void setSingleWidgetValue(String key, String value){
		FormWidget widget = widgetsByKey.get(key);
		if (widget == null)
			return;
		
		if (widget.handlesString())
			widget.setStringValue(value);
	}	
}
