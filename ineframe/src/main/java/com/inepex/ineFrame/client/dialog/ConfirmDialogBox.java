/**
 * 
 */
package com.inepex.ineFrame.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineFrame.client.i18n.IneFrameI18n;


/**
 * @author Horváth Szabolcs, Istvan Szoboszlai
 *
 */
public class ConfirmDialogBox extends DialogBoxBase {
	
	Button cancelButton;

	public ConfirmDialogBox() {
		super();
		setText(IneFrameI18n.confirmDialogTitle());
	}    
	
	@Override
	protected void configureButtonBar() {		
		cancelButton = new Button(IneFrameI18n.dialogCancelButton());		
        cancelButton.addClickHandler(new ClickHandler() {
        	@Override
            public void onClick(ClickEvent event) {
                hide();
            }            
        });
		
		buttonBar.add(cancelButton);
        panel.setCellHorizontalAlignment(buttonBar, VerticalPanel.ALIGN_RIGHT);        
	}
	
	@Override
	protected ImageResource getImageResource() {
		return DialogBoxBaseImages.INSTANCE.dialogConfirm();
	}
}
