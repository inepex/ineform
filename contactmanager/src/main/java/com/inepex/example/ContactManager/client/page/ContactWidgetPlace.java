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
import com.inepex.ineFrame.client.navigation.places.WidgetPlace;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class ContactWidgetPlace extends WidgetPlace {
	
	private final ObjectFinder objectFinder;
	private final ContactHandlerFactory contactHandlerFactory;
	
	private HTML html;
	
	@Inject 
	private ContactWidgetPlace(ObjectFinder objectFinder, ContactHandlerFactory contactHandlerFactory){
		this.objectFinder=objectFinder;
		this.contactHandlerFactory=contactHandlerFactory;
		
		html = new HTML();
		html.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		html.getElement().getStyle().setPaddingTop(3, Unit.PX);
		
	}

	@Override
	public Widget getWidget(Map<String, String> urlParams) {
		update(urlParams);
		return html;
	}

	@Override
	public boolean isWidget(Map<String, String> urlParams) {
		return urlParams.containsKey(AppPlaceHierarchyProvider.PARAM_CONTACT);
	}

	@Override
	public void update(Map<String, String> urlParams) {
		Long id = Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_CONTACT));
		
		objectFinder.executeFind(ContactConsts.descriptorName, id, new ObjectFinder.Callback() {

					@Override
					public void onObjectFound(AssistedObject foundObject) {
						html.setHTML(contactHandlerFactory.createHandler(foundObject).getName()+"&nbsp;");
					}
				});
	}

}
