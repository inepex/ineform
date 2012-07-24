package com.inepex.ineForm.client.datamanipulator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.IneForm;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.events.CancelledEvent;
import com.inepex.ineForm.client.form.events.SavedEvent;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.IneTable;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.client.table.SortableIneTable;
import com.inepex.ineForm.shared.descriptorext.FormRDesc;
import com.inepex.ineForm.shared.descriptorext.TableRDesc;
import com.inepex.ineForm.shared.render.AssistedObjectTableFieldRenderer;
import com.inepex.ineFrame.client.dialog.ConfirmDialogBox;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.pushedevents.PushedEventProvider;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

/**
 * <p>DataManipulator is the base class for displaying a table and manipulating it's
 * rows with default {@link TableRDesc} and default {@link FormRDesc}.</p>
 * 
 * <p><b>This class is subject to change!</b> It should support both custom {@link TableRDesc} 
 * and custom {@link FormRDesc}. Also it would be good to support updating the place
 * according to what is displayed (table or form).</p>
 * 
 * @author istvanszoboszlai
 *
 */
public abstract class DataManipulator extends HandlerAwareComposite {

	// Components
	private final SimplePanel outerPanel = new SimplePanel();
	protected final FlowPanel mainPanel = new FlowPanel();
	private final SimplePanel topPlaceholder = new SimplePanel();
	
	protected IneTable ineTable;

	// Properties
	protected String objectDescriptorName;

	// Dependencies
	protected final IneDataConnector ineDataConnector;
	protected final ValueRangeProvider valueRangeProvider;
	protected final EventBus eventBus;
	protected final FormFactory formFactory;

	protected FormCreationCallback formCreationCallback = null;
	
	//State
	private boolean rendered = false;
	
	public DataManipulator(FormContext formCtx
			, FormFactory formFactory
			, String objectDescriptorName
			, IneDataConnector ineDataConnector
			, boolean sortable
			, AssistedObjectTableFieldRenderer fieldRenderer) {
		
		this.objectDescriptorName = objectDescriptorName;
		this.ineDataConnector = ineDataConnector;
		this.valueRangeProvider = formCtx.valueRangeProvider;
		this.eventBus = formCtx.eventBus;
		this.formFactory = formFactory;

		initWidget(outerPanel);
		outerPanel.setWidget(mainPanel);
		addTopPlaceholder();
		
		if(sortable)
			this.ineTable = new SortableIneTable(formCtx.descStore, objectDescriptorName, ineDataConnector, fieldRenderer);
		else 
			this.ineTable = new IneTable(formCtx.descStore, objectDescriptorName, ineDataConnector, fieldRenderer);
	}
	
	public void render() {
		if (!rendered) {
			buildManipulator();
			rendered = true;
		}
	}

	public void setTableParams(int pageSize, boolean sortable) {
		ineTable.setPageSize(pageSize);
		// if (sortable) ineTable.setSortPolicy(SortPolicy.SINGLE_CELL);
	}
	
	private void buildManipulator() {
		initilaizeIneTableAndBuildCustom();
		ineTable.renderTable();
	}
	
	public void setAutoUpdating(PushedEventProvider pEventProvider) {
		if (ineDataConnector != null
				&& ineDataConnector instanceof ServerSideDataConnector) {
			((ServerSideDataConnector) ineDataConnector).setAutoUpdating(
					pEventProvider, ineTable.getDataDisplay());
		}
	}

	@Override
	protected void onDetach() {
		if (ineDataConnector != null && ineDataConnector instanceof ServerSideDataConnector) {
			((ServerSideDataConnector)ineDataConnector).disableAutoUpdating();
			
		}
		super.onDetach();
	}
	
	protected abstract void initilaizeIneTableAndBuildCustom();
	
	public abstract void setNewText(String newText);
	public abstract void setEditText(String editText);
	public abstract void setDeleteText(String deleteText);
	
	private void addTopPlaceholder() {
		mainPanel.add(topPlaceholder);
	}

	public void setTopPanelWidget(Widget w) {
		topPlaceholder.setWidget(w);
	}

	protected void onAddDataClicked() {
		showObjectEditor(null);
	}
	
	protected void onEditClicked(final AssistedObject kvo) {
		showObjectEditor(kvo);		
	}
	
	protected void onDeleteClicked(final AssistedObject kvo) {
		ConfirmDialogBox cb = new ConfirmDialogBox();

		cb.show(IneFormI18n.reallyWantToDelete());

		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ineDataConnector.objectDeleteRequested(kvo, null);

			}
		});
	}
	
	protected String getFRD() {
		return null;
	}

	protected void showObjectEditor(AssistedObject selectedValue) {
		boolean isEditMode = selectedValue != null;

		SaveCancelForm saveCancelForm = formFactory.createSaveCancel(objectDescriptorName, getFRD(), ineDataConnector, null);

		showForm(saveCancelForm); // this is needed here to make event processing available!
		
		if (formCreationCallback != null) {
			if (isEditMode)
				formCreationCallback.onCreatingEditForm(saveCancelForm);
			else 
				formCreationCallback.onCreatingCreateForm(saveCancelForm);
		}
		
		saveCancelForm.renderForm();

		if (isEditMode) {
			saveCancelForm.setInitialData(selectedValue.clone());
		}
		
		saveCancelForm.addCancelledHandler(new FormCancelledHadler());
		saveCancelForm.addSavedHandler(new FormSavedHadler());
	}
	
	public class FormCancelledHadler implements CancelledEvent.Handler {
		@Override
		public void onCancelled(CancelledEvent event) {
			showTable();
		}
	}
	
	public class FormSavedHadler implements SavedEvent.Handler {
		@Override
		public void onSaved(SavedEvent event) {
			showTable();
		}
	}
	
	protected void showForm(SaveCancelForm saveCancelForm) {
		outerPanel.setWidget(saveCancelForm.asWidget());
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		showTable();
	}
	
	private void showTable() {
		if(ineTable.getSingleSelectionModel()!=null)
			if(ineTable.getSingleSelectionModel().getSelectedObject()!=null)
				ineTable.getSelectionModel().setSelected(ineTable.getSingleSelectionModel().getSelectedObject(), false);
		
		outerPanel.setWidget(mainPanel);
	}

	public IneTable getIneTable() {
		return ineTable;
	}

	public interface FormCreationCallback {
		void onCreatingEditForm(IneForm ineForm);
		void onCreatingCreateForm(IneForm ineForm);
	}

	public void setPageSize(int pageSize) {
		ineTable.setPageSize(pageSize);
	}

	public void setFormCreationCallback(FormCreationCallback formCreationCallback) {
		this.formCreationCallback = formCreationCallback;
	}
	
}
