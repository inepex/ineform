package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.MeetingConsts;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.places.WidgetPlace;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.assistedobject.AssistedObject;

public class MeetingWidgetPlace extends WidgetPlace {
	
	private final IneDispatch dispatch;
	private final AssistedObjectHandlerFactory handlerFactory;
	
	private HTML html;
	
	
	@Inject 
	private MeetingWidgetPlace(IneDispatch dispatch, AssistedObjectHandlerFactory handlerFactory){
		this.dispatch=dispatch;
		this.handlerFactory=handlerFactory;
		
		html = new HTML();
		html.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		html.getElement().getStyle().setPaddingTop(3, Unit.PX);
		
	}

	@Override
	public Widget getWidget(Map<String, String> urlParams) {
		Long id = Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_MEETING));
		
		new ObjectFinder(MeetingConsts.descriptorName, id, dispatch).executeFind(new ObjectFinder.Callback() {

					@Override
					public void onObjectFound(AssistedObject foundObject) {
						AssistedObjectHandler handler = handlerFactory.createHandler(foundObject);
						
						html.setHTML(
								handler.getRelation(MeetingConsts.k_company).getDisplayName()+"&nbsp;"+
								handler.getRelation(MeetingConsts.k_contact).getDisplayName()+"&nbsp;");
					}
				});
		
		return html;
	}

	@Override
	public boolean isWidget(Map<String, String> urlParams) {
		return urlParams.containsKey(AppPlaceHierarchyProvider.PARAM_MEETING);
	}

}
