package com.inepex.ineFrame.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;

/**
 * 
 * <b>if you want to use this widget, put these lines into your web.xml:</b>
 * 
 * <pre>
 * {@code
 * <servlet>
 * 	<servlet-name>simpleCaptcha</servlet-name>
 * 	<servlet-class>com.inepex.core.inetrack.server.CaptchaServlet</servlet-class>
 * 	<init-param>
 * 		<param-name>width</param-name>
 * 		<param-value>200</param-value>
 * 	</init-param>
 * 	<init-param>
 * 		<param-name>height</param-name>
 * 		<param-value>50</param-value>
 * 	</init-param>
 * </servlet>
 * <servlet-mapping>
 * 	<servlet-name>simpleCaptcha</servlet-name>
 * 	<url-pattern>/SimpleCaptcha.jpg</url-pattern>
 * </servlet-mapping>
 * }
 * </pre>
 * 
 * <b>or put these lines into your servletmodule:</b>
 * 
 * <pre>
 * {
 * 	&#064;code
 * 	Map&lt;String, String&gt; params = new HashMap&lt;String, String&gt;();
 * 	params.put(&quot;width&quot;, &quot;200&quot;);
 * 	params.put(&quot;height&quot;, &quot;50&quot;);
 * 	serve(&quot;/SimpleCaptcha.jpg&quot;).with(CaptchaServlet.class, params);
 * }
 * </pre>
 * 
 * <b>server side usage:</b>
 * 
 * <pre>
 * {@code
 * String readCapthaFromHttpSession(HttpSession session) {
 * 		return ((Captcha)session.getAttribute(nl.captcha.Captcha.NAME)).getAnswer())
 * }
 * }
 * </pre>
 */
public class CaptchaWidget extends Composite {

	private final Image img = new Image();
	private final TextBox tb = new TextBox();

	@Inject
	public CaptchaWidget() {
		this(Rendering.VERTICAL);
	}
	
	public CaptchaWidget(Rendering rendering) {
		buildStructure(rendering);
		reloadCaptcha();
	}

	public void reloadCaptcha() {
		img.setUrl("SimpleCaptcha.jpg" + "?"
				+ System.currentTimeMillis());
		tb.setText("");
	}

	private void buildStructure(Rendering rendering) {
		switch (rendering) {
		case HORIZONTAL:
			Grid g = new Grid(1, 2);
			g.setWidget(0, 0, tb);
			g.setWidget(0, 1, img);
			initWidget(g);
			
			g.setCellSpacing(0);
			g.setCellPadding(0);
			g.setBorderWidth(0);
			break;
		case VERTICAL:
		default:
			FlowPanel fp = new FlowPanel();
			fp.add(img);
			fp.add(tb);
			initWidget(fp);
			
			setPixelSize(150, 80);
			break;
		}
	}

	public TextBox getTextBox() {
		return tb;
	}

	public String getCaptchaText() {
		return tb.getText();
	}

	public static enum Rendering {
		/** 
		 * first line: captcha
		 * next line: textbox
		 */
		VERTICAL,
		
		/**
		 * one line: textbox, captcha
		 */
		HORIZONTAL;
	}
}
