package com.inepex.ineFrame.client.navigation.menu;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.inepex.ineFrame.client.RESOURCES.ResourceHelper;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.navigation.OnClickedLogic;
import com.inepex.ineFrame.client.navigation.menu.MenuRenderer.View.Tab;
import com.inepex.ineFrame.client.widgets.AnchorWidget;
import com.inepex.ineFrame.client.widgets.ListItemWidget;

public class MenuItemView extends HandlerAwareComposite implements Tab {

	private final ListItemWidget listWidget;
	private final AnchorWidget a;

	private boolean clickable = true;
	private boolean enabled = true;

	private OnClickedLogic onClickedLogic;

	public MenuItemView(String menuName) {
		a = new AnchorWidget(menuName);
		listWidget = new ListItemWidget(a);
		initWidget(listWidget);
	}

	public MenuItemView(String menuName, Image icon) {
		a = new AnchorWidget(menuName);
		if (icon != null) {
			a.add(icon);
		}
		listWidget = new ListItemWidget(a);
		initWidget(listWidget);
	}

	@Override
	protected void onAttach() {
		super.onAttach();

		registerHandler(listWidget.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (clickable && onClickedLogic != null)
					onClickedLogic.doLogic();
			}
		}));
	}

	@Override
	public void setOnClickedLogic(OnClickedLogic logic) {
		this.onClickedLogic = logic;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (this.enabled) {
			// TODO additional style name
		} else {
		}

		this.enabled = enabled;
	}

	@Override
	public void setClickable(boolean clickable) {
		if (this.clickable) {
			getElement().getStyle().setCursor(Cursor.POINTER);
		} else {
			getElement().getStyle().setCursor(Cursor.DEFAULT);
		}
		this.clickable = clickable;
	}

	@Override
	public void setSelected(boolean selected) {
		if (selected) {
			addStyleName(ResourceHelper.getRes().style().current_page_item());
		}
	}

	@Override
	public void setItemVisible(boolean visible) {
		setVisible(visible);
	}

	@Override
	public void renderToRightSide() {
		getElement().getStyle().setFloat(Float.RIGHT);
	}
}