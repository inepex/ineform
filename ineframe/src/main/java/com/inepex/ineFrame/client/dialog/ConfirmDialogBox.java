/**
 * 
 */
package com.inepex.ineFrame.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.inepex.ineFrame.client.i18n.IneFrameI18n_old;


/**
 * @author Horváth Szabolcs, Istvan Szoboszlai
 *
 */
public class ConfirmDialogBox extends DialogBoxBase {
	
	Button cancelButton;

	public ConfirmDialogBox() {
		super();
		setText(IneFrameI18n_old.confirmDialogTitle());
	}    
	
	@Override
	protected void configureButtonBar() {		
		cancelButton = new Button(IneFrameI18n_old.dialogCancelButton());		
        cancelButton.addClickHandler(new ClickHandler() {
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
