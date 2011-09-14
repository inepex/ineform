package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.CompanyConsts;
import com.inepex.example.ContactManager.entity.kvo.CompanyHandlerFactory;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.navigation.places.WidgetPlace;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class CompanyWidgetPlace extends WidgetPlace {
	
	private final ObjectFinder objectFinder;
	private final CompanyHandlerFactory companyHandlerFactory;
	
	private HTML html;
	
	@Inject 
	private CompanyWidgetPlace(ObjectFinder objectFinder, CompanyHandlerFactory companyHandlerFactory){
		this.objectFinder=objectFinder;
		this.companyHandlerFactory=companyHandlerFactory;
		
		html = new HTML();
		html.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		html.getElement().getStyle().setPaddingTop(3, Unit.PX);
		
	}

	@Override
	public Widget getWidget(Map<String, String> urlParams) {
		Long id = Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_COMPANY));
		
		objectFinder.executeFind(CompanyConsts.descriptorName, id, new ObjectFinder.Callback() {

					@Override
					public void onObjectFound(AssistedObject foundObject) {
						html.setHTML(companyHandlerFactory.createHandler(foundObject).getName()+"&nbsp;");
					}
				});
		
		return html;
	}

	@Override
	public boolean isWidget(Map<String, String> urlParams) {
		return urlParams.containsKey(AppPlaceHierarchyProvider.PARAM_COMPANY);
	}

}
