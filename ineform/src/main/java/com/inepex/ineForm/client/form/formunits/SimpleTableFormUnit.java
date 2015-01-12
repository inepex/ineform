package com.inepex.ineForm.client.form.formunits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.error.ErrorMessageManagerInterface;
import com.inepex.ineForm.client.form.error.SimpleTableErrorMessageManager;
import com.inepex.ineForm.client.form.factories.FormWidgetFactory;
import com.inepex.ineForm.client.form.widgets.FormWidget;
import com.inepex.ineForm.client.form.widgets.RelationFW;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.validation.KeyValueObjectValidationManager;

/**
 * A simple form, that uses HTML table as base layout. The table has four columns, one
 * for description,one for mandatory sign,  one for the form widgets and one for validation error
 * 
 * @author istvan
 *
 */
public class SimpleTableFormUnit extends AbstractFormUnit {

	public static final String WIDTH="width";
	public static final String putNextWidgetIntoThisLineToo="putNextWidgetIntoThisLineToo"; 
	
	protected final Grid mainTable = new Grid();
	protected final CellFormatter cf = mainTable.getCellFormatter();
	protected final Map<String, Integer> rowsByKeys = new HashMap<String, Integer>();

	protected final FormContext formCtx;
	protected final FormWidgetFactory formWidgetFactory;
	protected final EventBus eventBus;
	protected final ValueRangeProvider valueRangeProvider;
	
	public SimpleTableFormUnit(FormContext formCtx,
						   FormRDesc formRDesc, 
						   String objectDescriptorsName, 
						   List<Node<FormRDescBase>> selectedFields) {
		super(objectDescriptorsName, formCtx.descStore);
		this.valueRangeProvider = formCtx.valueRangeProvider;
		this.eventBus=formCtx.eventBus;
		this.formWidgetFactory = formCtx.formWidgetFactory;
		this.formCtx = formCtx;
		
		insert(mainTable,0);
		
		//style
		mainTable.setStyleName(ResourceHelper.ineformRes().style().simpleTableFormUnit());
		mainTable.setCellPadding(5);
		mainTable.setCellSpacing(0);
		
		renderForm(selectedFields, formRDesc);
		
		mainTable.getColumnFormatter().setWidth(0, IneFormProperties.DEFAULT_FormLabelWidth);
	}

	private String getRowStyleName(boolean even) {
		return even ? ResourceHelper.ineformRes().style().evenCellContent() : ResourceHelper.ineformRes().style().cellContent();
	}
	
	/**
	 * Renders the widgets to the form. Either using the default {@link FormRDesc}
	 * or a custom one if provided by {@link #setCustomRenderDescriptor(FormRDesc)}
	 */
	protected void renderForm(List<Node<FormRDescBase>> selectedNodes, FormRDesc formRDesc) {
		int row = 0;
		boolean even = false;
		
		//contents of a line
		List<Widget> widgetsInThisLine = new ArrayList<Widget>();
		List<String> modelNameKeySet = new ArrayList<String>();
		boolean mandatory=false;
		String titleOfLine=null;
		
		//maximum size
		mainTable.resize(selectedNodes.size(), 4);

		for (int i=0; i<selectedNodes.size(); i++) {
			Node<FormRDescBase> descNode = selectedNodes.get(i);
			DescriptorBase nodeElement=descNode.getNodeElement();
			
			if(nodeElement instanceof WidgetRDesc) {
				FDesc fDesc = getFieldDesct(descNode);
				
				if (!(fDesc == null  || 
						(!(IneFormProperties.showIds || formRDesc.hasProp(FormRDescBase.prop_showIDs)) && IFConsts.KEY_ID.equals(descNode.getNodeId())) ||
						IFConsts.KEY_ORDERNUM.equals(descNode.getNodeId()) ||
						IFConsts.KEY_ISDELETED.equals(descNode.getNodeId()))) {
				
						FormWidget createdWidget = formWidgetFactory.createWidget(formCtx, this, fDesc
								,(WidgetRDesc) nodeElement,
								formCtx.propFwViewProvider);
						createdWidget = formWidgetFactory.createDecorator(createdWidget, fDesc, (WidgetRDesc) nodeElement);
						
						modelNameKeySet.add(descNode.getNodeId());
						
						if (createdWidget != null) {
							registerWidgetToDataFlow(descNode.getNodeId(), createdWidget);							
							
							if(createdWidget instanceof RelationFW)
								modelNameKeySet.addAll(((RelationFW) createdWidget).getErrorManagerKeySet(descNode.getNodeId()));
							
							// if the widget should not be rendered, move on
							if (createdWidget.isShouldRender()) {
								registerRenderedWidget(createdWidget);
							}
						}
						
						widgetsInThisLine.add(createdWidget!=null ? createdWidget : new Label(IneFormI18n.ERR_CouldNotRenderWidget()));
						if(titleOfLine==null) {
							String titleFromProp = ((WidgetRDesc) nodeElement).getDisplayName();
							if(titleFromProp==null) {
								titleOfLine=fDesc.getDefaultDisplayName();
							} else {			
								titleOfLine=titleFromProp;
							}
						}
						if(!mandatory) mandatory=fDesc.hasValidator(KeyValueObjectValidationManager.MANDATORY) && (createdWidget==null || !createdWidget.isReadOnlyWidget());
				}
			} 
			
			//create the line
			if(!nodeElement.hasProp(putNextWidgetIntoThisLineToo)) {
				
				if(widgetsInThisLine.size()>0) {
					cf.setStyleName(row, 0, ResourceHelper.ineformRes().style().cellTitle());
					String titleText = nodeElement.getDisplayName();
					if (titleText == null)
						titleText = titleOfLine==null ? "" : titleOfLine;
					
					HTML titleWidget = new HTML(titleText);
					registerTitle(modelNameKeySet.get(0), titleWidget);
					mainTable.setWidget(row, 0, titleWidget);
				
					cf.setStyleName(row, 1, ResourceHelper.ineformRes().style().mandatorySign());
					if(mandatory) mainTable.setText(row, 1, "*");
				
					cf.setStyleName(row, 2, ResourceHelper.ineformRes().style().cellContent());
					cf.addStyleName(row, 2, getRowStyleName(even));
					
					if(widgetsInThisLine.size()==1) {
						mainTable.setWidget(row, 2, widgetsInThisLine.get(0));
					} else {
						FlowPanel fp = new FlowPanel();
						mainTable.setWidget(row, 2, fp);
						for(Widget w : widgetsInThisLine) {
							fp.add(w);
						}
					}
					
					if(((WidgetRDesc)nodeElement).getFormWidgetType().isRenderIntoTwoColumn()) {
						cf.getElement(row, 2).setAttribute("colspan", "2");
					} else {
						ErrorMessageManagerInterface emm = createEMMI(cf.getElement(row, 3));
						for(String modelName : modelNameKeySet) {
							registerErrorMessegeManager(modelName, emm);
							rowsByKeys.put(modelName, row);
						}
					}
					
					widgetsInThisLine.clear();
					modelNameKeySet.clear();
					titleOfLine=null;
					mandatory=false;
					row++;
					even = !even;
				}
			}
		}
		
		if(widgetsInThisLine.size()>0) throw new RuntimeException("SimpleTableFormUnit: can't set 'putNextWidgetIntoThisLineToo' on the last line");
		//correct size
		mainTable.resizeRows(row);
	}
	
	protected ErrorMessageManagerInterface createEMMI(Element holder) {
		return new SimpleTableErrorMessageManager(holder);
	}

	public void addStyleName(String key, int column, String additionalStyleName) {
		cf.addStyleName(rowsByKeys.get(key), column, additionalStyleName);
	}
	
	public void removeStyleName(String key, int column, String additionalStyleName) {
		cf.removeStyleName(rowsByKeys.get(key), column, additionalStyleName);
	}
	
	@Override
	public void setFWVisible(String key, boolean visible) {
		Integer row = rowsByKeys.get(key);
		if(row==null) return;
		
		cf.setVisible(row, 0, visible);
		cf.setVisible(row, 1, visible);
		cf.setVisible(row, 2, visible);
		
		cf.setVisible(row, 3, visible);
		errormanagersByKey.get(key).setVisible(visible);
	}
}