package com.inepex.example.ContactManager.client.page;

import java.util.Arrays;
import java.util.Map;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.i18n.CMI18n;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.assist.ContactAssist;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory.ContactSearchHandler;
import com.inepex.ineForm.client.datamanipulator.DataManipulator;
import com.inepex.ineForm.client.datamanipulator.RowCommandDataManipulator;
import com.inepex.ineForm.client.form.FormContext;
import com.inepex.ineForm.client.form.FormFactory;
import com.inepex.ineForm.client.form.SaveCancelForm;
import com.inepex.ineForm.client.form.events.BeforeSaveEvent;
import com.inepex.ineForm.client.pages.ConnectorPage;
import com.inepex.ineForm.client.table.AbstractIneTable.UserCommand;
import com.inepex.ineForm.client.table.IneDataConnector;
import com.inepex.ineForm.client.table.ServerSideDataConnector;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.dispatch.ObjectListAction;
import com.inepex.ineForm.shared.dispatch.ObjectManipulationAction;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineForm.shared.render.TableFieldRenderer.CustomCellContentDisplayer;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class CompanyContactEditPage extends ConnectorPage {

	private final ContactHandlerFactory contactHandlerFactory;
	
	private ContactSearchHandler searchKVO;
	private Long compId;
	
	@Inject
	CompanyContactEditPage(FormContext formCtx, FormFactory formFactory, ContactHandlerFactory contactHandlerFactory,
			TableFieldRenderer fieldRenderer) {
		this.contactHandlerFactory=contactHandlerFactory;
		
		searchKVO = contactHandlerFactory.createSearchHandler();
		
		ServerSideDataConnector connector = createConnector(formCtx.ineDispatch, formCtx.eventBus, ContactConsts.descriptorName);
		connector.setSearchParameters(searchKVO.getAssistedObject());
		connector.setAssociatedListAction(new ObjectListAction(ContactConsts.descriptorName, Arrays.asList(ContactConsts.props_user)));
		connector.setAssociatedManipulateAction(new ObjectManipulationAction(Arrays.asList(ContactConsts.props_user)));
		DataManipulator dm = new CompanyDataManipulator(formCtx, formFactory, 
				ContactConsts.descriptorName,connector, true, fieldRenderer);
		dm.render();
		dm.getIneTable().getFieldRenderer().setCustomFieldRenderer(ContactConsts.k_note, new CustomCellContentDisplayer() {
			
			@Override
			public String getCustomCellContent(AssistedObjectHandler rowKvo, String fieldId, ColRDesc colRDesc) {
				String userProps = rowKvo.getAssistedObject().getPropsJson(ContactConsts.props_user);
				if (userProps != null){
					JSONObject userPropsJson = JSONParser.parseStrict(userProps).isObject();
					if (userPropsJson != null){
						if (userPropsJson.containsKey(ContactConsts.k_note)){
							return userPropsJson.get(ContactConsts.k_note).isString().stringValue();
						}
					}
				}
				return "";
			}
		});
		
		mainPanel.add(dm);
	}	

	@Override
	protected boolean affectUrlParameters(Map<String, String> urlParams) {
		compId = Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_COMPANY));
		
		searchKVO.setCompany(
				new Relation(compId,""));
	
		return true;
	}
	
	private class CompanyDataManipulator extends RowCommandDataManipulator {
		
		private boolean isDisplayMode=true;

		public CompanyDataManipulator(FormContext formCtx,
				FormFactory formFactory, String objectDescriptorName,
				IneDataConnector ineDataConnector, boolean sortable, TableFieldRenderer fieldRenderer) {
			super(formCtx, formFactory, objectDescriptorName, ineDataConnector, sortable, fieldRenderer);
			userCommands.clear();
			userCommands.add(new DetailsCommand());
			userCommands.add(new ComEditCommand());
			userCommands.add(new DeleteCommand());
		}
		
		@Override
		protected void showForm(SaveCancelForm saveCancelForm) {
			super.showForm(saveCancelForm);
			saveCancelForm.setSaveButtonVisible(!isDisplayMode);
			if(!isDisplayMode)
				saveCancelForm.addBeforeSaveHandler(new BeforeSaveEvent.Handler() {
					
					@Override
					public void onBeforeSave(BeforeSaveEvent event) {
						contactHandlerFactory.createHandler(event.getKvo()).set(ContactConsts.k_company, new Relation(compId, ""));
					}
				});
		}
		
		
		@Override
		protected String getFRD() {
			if(!isDisplayMode)
				return null;
			else
				return ContactAssist.roFRD;
		}
		
		@Override
		protected void onAddDataClicked() {
			isDisplayMode=false;
			super.onAddDataClicked();
		}
		
		class ComEditCommand extends EditCommand {
			@Override
			public void onCellClicked(AssistedObject kvoOfRow) {
				isDisplayMode=false;
				super.onCellClicked(kvoOfRow);
			}
		}
		
		class DetailsCommand extends UserCommand {

			@Override
			public String getCommandCellText(AssistedObject kvoOfRow) {
				return CMI18n.details();
			}

			@Override
			public void onCellClicked(AssistedObject kvoOfRow) {
				isDisplayMode=true;
				onEditClicked(kvoOfRow);
			}

			@Override
			public boolean visible(AssistedObject kvoOfRow) {
				return true;
			}
		}
		
	}
}
