package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.ContactConsts;
import com.inepex.example.ContactManager.entity.kvo.ContactHandlerFactory;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.places.WidgetPlace;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class ContactWidgetPlace extends WidgetPlace {
	
	private final IneDispatch dispatch;
	private final ContactHandlerFactory contactHandlerFactory;
	
	private HTML html;
	
	@Inject 
	private ContactWidgetPlace(IneDispatch dispatch, ContactHandlerFactory contactHandlerFactory){
		this.dispatch=dispatch;
		this.contactHandlerFactory=contactHandlerFactory;
		
		html = new HTML();
		html.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		html.getElement().getStyle().setPaddingTop(3, Unit.PX);
		
	}

	@Override
	public Widget getWidget(Map<String, String> urlParams) {
		Long id = Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_CONTACT));
		
		new ObjectFinder(ContactConsts.descriptorName, id, dispatch).executeFind(new ObjectFinder.Callback() {

					@Override
					public void onObjectFound(AssistedObject foundObject) {
						html.setHTML(contactHandlerFactory.createHandler(foundObject).getName()+"&nbsp;");
					}
				});
		
		return html;
	}

	@Override
	public boolean isWidget(Map<String, String> urlParams) {
		return urlParams.containsKey(AppPlaceHierarchyProvider.PARAM_CONTACT);
	}

}
