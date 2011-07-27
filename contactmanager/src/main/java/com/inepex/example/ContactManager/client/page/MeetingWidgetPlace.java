package com.inepex.example.ContactManager.client.page;

import java.util.Map;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.inepex.example.ContactManager.client.navigation.AppPlaceHierarchyProvider;
import com.inepex.example.ContactManager.entity.kvo.MeetingKVO;
import com.inepex.ineForm.shared.dispatch.ObjectFinder;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.navigation.places.WidgetPlace;

public class MeetingWidgetPlace extends WidgetPlace {
	
	private final IneDispatch dispatch;
	
	private HTML html;
	
	@Inject 
	private MeetingWidgetPlace(IneDispatch dispatch){
		this.dispatch=dispatch;
		
		html = new HTML();
		html.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		html.getElement().getStyle().setPaddingTop(3, Unit.PX);
		
	}

	@Override
	public Widget getWidget(Map<String, String> urlParams) {
		Long id = Long.parseLong(urlParams.get(AppPlaceHierarchyProvider.PARAM_MEETING));
		
		new ObjectFinder<MeetingKVO>(MeetingKVO.descriptorName, id, dispatch).executeFind(new ObjectFinder.Callback<MeetingKVO>() {

					@Override
					public void onObjectFound(MeetingKVO foundObject) {
						html.setHTML(
								foundObject.getCompany().getDisplayName()+"&nbsp;"+
								foundObject.getContact().getDisplayName()+"&nbsp;");
					}
				});
		
		return html;
	}

	@Override
	public boolean isWidget(Map<String, String> urlParams) {
		return urlParams.containsKey(AppPlaceHierarchyProvider.PARAM_MEETING);
	}

}
