package com.inepex.ineFrame.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;

/**
 * 
 * <b>if you want to use this widget, put these lines into your web.xml:</b>
 * <pre>
 * {@code
 * <servlet>
 *		<servlet-name>simpleCaptcha</servlet-name>
 *		<servlet-class>com.inepex.core.inetrack.server.CaptchaServlet</servlet-class>
 *		<init-param>
 *			<param-name>width</param-name>
 *			<param-value>200</param-value>
 *		</init-param>
 *		<init-param>
 *			<param-name>height</param-name>
 *			<param-value>50</param-value>
 *		</init-param>
 *	</servlet>
 *	<servlet-mapping>
 *		<servlet-name>simpleCaptcha</servlet-name>
 *		<url-pattern>/SimpleCaptcha.jpg</url-pattern>
 *	</servlet-mapping>
 * }
 * </pre>
 * 
 * <b>server side usage:</b>
 * <pre>
 * {@code
 * String readCapthaFromHttpSession(HttpSession session) {
 * 		return ((Captcha)session.getAttribute(nl.captcha.Captcha.NAME)).getAnswer())
 * }
 * }
 * </pre>
 *
 */
public class CaptchaWidget extends Composite {
	
	//widgets
	private final FlowPanel fp_main = new FlowPanel();
	private final Image img_captcha = new Image();
	private final TextBox tb_captcha = new TextBox();
	
	@Inject
	public CaptchaWidget(){
		initWidget(fp_main);
		
		buildStructure();
		initEventHandlers();
		setStyles();
		
		reloadCaptcha();
	}
	
	public void reloadCaptcha() {
		img_captcha.setUrl("SimpleCaptcha.jpg" + "?" + System.currentTimeMillis());
		tb_captcha.setText("");
	}

	private void buildStructure() {
		fp_main.add(img_captcha);
		fp_main.add(tb_captcha);
	}

	private void initEventHandlers() {
		
	}

	private void setStyles() {
		setPixelSize(150, 80);
	}

	public String getCaptchaText(){
		return tb_captcha.getText();
	}
}
