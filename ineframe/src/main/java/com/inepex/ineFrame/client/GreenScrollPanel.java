package com.inepex.ineFrame.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CustomScrollPanel;
import com.google.gwt.user.client.ui.HasScrolling;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.NativeHorizontalScrollbar;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Use the {@link GreenScrollPanel#setScrollContent(Widget)} method!
 * 
 */
public class GreenScrollPanel implements HasScrolling, IsWidget {

	private final ScrollPanel impl;
	private final ResizeLayoutPanel mainPanel;

	public GreenScrollPanel() {
		this(false);
	}
	
	public GreenScrollPanel(boolean withHorizontalScroll) {
		mainPanel = new ResizeLayoutPanel() {
			@Override
			protected void onAttach() {
				super.onAttach();
				// hack to hide scrollbar in chrome when leaflet map is also displayed (check GreenScrollImpl.onAttach too)
				if (isChrome()) {
					try {
						((Element) (getElement().getChild(1))).getStyle().setZIndex(-1000);
						((Element) (getElement().getChild(2))).getStyle().setZIndex(-1000);
					} catch (Exception e) {
						GWT.log("", e);
					}
				}

			}
		};
		
		if (hasMacNanoScrollBar()) {
			this.impl = new ScrollPanel();
		} else {
			this.impl = new GreenScrollImpl(withHorizontalScroll);
		}
		
		mainPanel.setWidget(impl);
		
		impl.addDomHandler(new MouseWheelHandler() {
			
			@Override
			public void onMouseWheel(MouseWheelEvent event) {
				event.stopPropagation();
			}
		}, MouseWheelEvent.getType());
		
	}
	
	

	private static boolean hasMacNanoScrollBar() {
		String userAgent = Window.Navigator.getUserAgent();
		if (userAgent == null || userAgent.length() < 1)
			return false;

		String lowerCaseAgent = userAgent.toLowerCase();

		return (has(lowerCaseAgent, "chrome") || has(lowerCaseAgent, "safari")) && has(lowerCaseAgent, "mac os x 10")
				&& doesntHave(lowerCaseAgent, "mac os x 10_1") && doesntHave(lowerCaseAgent, "mac os x 10_2")
				&& doesntHave(lowerCaseAgent, "mac os x 10_3") && doesntHave(lowerCaseAgent, "mac os x 10_4")
				&& doesntHave(lowerCaseAgent, "mac os x 10_5") && doesntHave(lowerCaseAgent, "mac os x 10_6");
	}

	private static boolean isChrome() {
		String userAgent = Window.Navigator.getUserAgent();
		if (userAgent == null || userAgent.length() < 1)
			return false;
		String lowerCaseAgent = userAgent.toLowerCase();
		return has(lowerCaseAgent, "chrome");
	}

	private static boolean doesntHave(String lowerUserAgent, String part) {
		return lowerUserAgent.indexOf(part) == -1;
	}

	private static boolean has(String lowerUserAgent, String part) {
		return lowerUserAgent.indexOf(part) != -1;
	}

	public void setScrollContent(Widget w) {
		impl.setWidget(w);
	}
	
	public GreenScrollPanel scrollContent(Widget w) {
		setScrollContent(w);
		return this;
	}
	
	@Override
	public void fireEvent(GwtEvent<?> event) {
		mainPanel.fireEvent(event);
	}

	@Override
	public int getHorizontalScrollPosition() {
		return impl.getHorizontalScrollPosition();
	}

	@Override
	public int getMaximumHorizontalScrollPosition() {
		return impl.getMaximumHorizontalScrollPosition();
	}

	@Override
	public int getMinimumHorizontalScrollPosition() {
		return impl.getMaximumHorizontalScrollPosition();
	}

	@Override
	public void setHorizontalScrollPosition(int position) {
		impl.setHorizontalScrollPosition(position);
	}

	@Override
	public int getMaximumVerticalScrollPosition() {
		return impl.getMaximumVerticalScrollPosition();
	}

	@Override
	public int getMinimumVerticalScrollPosition() {
		return impl.getMaximumVerticalScrollPosition();
	}

	@Override
	public int getVerticalScrollPosition() {
		return impl.getVerticalScrollPosition();
	}

	@Override
	public void setVerticalScrollPosition(int position) {
		impl.setVerticalScrollPosition(position);
	}

	@Override
	public HandlerRegistration addScrollHandler(ScrollHandler handler) {
		return impl.addScrollHandler(handler);
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}
	
	public void scrollToTop() {
		impl.scrollToTop();
	}

	public void scrollToBottom() {
		impl.scrollToBottom();
	}

	public void scrollToPosition(int position) {
		impl.setVerticalScrollPosition(position);
	}

	public void recalculateScrollPaneHeight() {
		impl.onResize();
	}

	protected static class GreenScrollImpl extends CustomScrollPanel {
		private HandlerRegistration hr_over;
		private HandlerRegistration hr_out;
		private boolean withHorizontalScroll;

		public GreenScrollImpl(boolean withHorizontalScroll) {
			this.withHorizontalScroll = withHorizontalScroll;
			if (withHorizontalScroll) {
				setHorizontalScrollbar(new GreenHorizontalScroll(this, getContainerElement()), 8);
			} else {
				setHorizontalScrollbar(new NativeHorizontalScrollbar(), 0);
			}
			setVerticalScrollbar(new GreenVerticalScroll(this, getContainerElement()), 8);
			setAlwaysShowScrollBars(true);
			setScrollVisible(false);
			if (!withHorizontalScroll) { //important hack to avoid random scrollbars
				getContainerElement().getStyle().setWidth(100, Unit.PCT);
			}
		}

		@Override
		protected void onAttach() {
			super.onAttach();

			hr_over = addDomHandler(new MouseOverHandler() {

				@Override
				public void onMouseOver(MouseOverEvent event) {
					setScrollVisible(true);
				}

			}, MouseOverEvent.getType());

			hr_out = addDomHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {
					setScrollVisible(false);
				}

			}, MouseOutEvent.getType());

			// hack to hide scrollbar in chrome when leaflet map is also (check GreenScrollPanel.onAttach too)
			// displayed
			if (isChrome()) {
				try {
					((Element) (getElement().getChild(1).getChild(0).getChild(0).getChild(0))).getStyle().setZIndex(-1000);
					((Element) (getElement().getChild(1).getChild(0).getChild(0).getChild(1))).getStyle().setZIndex(-1000);
				} catch (Exception e) {
					GWT.log("", e);
				}
			}
		}

		@Override
		protected void onDetach() {
			super.onDetach();

			if (hr_over != null) {
				hr_over.removeHandler();
				hr_over = null;
			}

			if (hr_out != null) {
				hr_out.removeHandler();
				hr_out = null;
			}
		}

		private void setScrollVisible(boolean visible) {
			((GreenVerticalScroll) getVerticalScrollbar()).setScrollPaneVisible(visible);
			if (withHorizontalScroll){
				((GreenHorizontalScroll) getHorizontalScrollbar()).setScrollPaneVisible(visible);
			}
		}
	}
}
