package com.inepex.ineForm.client.form.widgets.datetime;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;
import com.inepex.ineForm.client.resources.IneResources;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;

abstract class AbstractField extends HandlerAwareComposite implements DateTimeFieldInterface{
	
	protected final FlowPanel panel_main=new FlowPanel();
	
	protected final Precision PRECISION;
	protected final int stepcount;
	protected final IneDateGWT inedate;
	protected final DateTimeFieldParentInterface parent;

	protected final boolean usetextbox;
	protected InlineLabel lbl_datetime;
	protected DateTimeTextBox tb_datetime;

	protected final boolean showstepbuttons;
	protected Image img_step_fwd;
	protected Image img_step_bck;

	protected boolean enabled;
	
	protected String prevValue="";

	public AbstractField(IneDateGWT date, Precision precision, boolean showstepbuttons, int stepcount,
			boolean usetextbox, DateTimeFieldParentInterface parent, boolean enableselectmanager) {
		this.PRECISION=precision;
		this.inedate=date;
		this.usetextbox=usetextbox;
		this.parent=parent;
		this.enabled=true;
		this.showstepbuttons=showstepbuttons;
		this.stepcount=stepcount;
		
		initWidget(panel_main);

		if(showstepbuttons) {
			img_step_bck=new Image();
			img_step_fwd=new Image();
		}

		if(usetextbox) {
			tb_datetime = new DateTimeTextBox(precision, enableselectmanager);
			tb_datetime.addStyleName("tb");
		} else {
			lbl_datetime=new InlineLabel();
			lbl_datetime.addStyleName("lbl");
		}

		if(showstepbuttons) panel_main.add(img_step_bck);

		if(usetextbox) panel_main.add(tb_datetime);
		else panel_main.add(lbl_datetime);

		if(showstepbuttons) panel_main.add(img_step_fwd);

		setEnabled(true);
	}
	
	@Override
	public boolean isInReadOnlyMode() {
		return !showstepbuttons && !usetextbox;
	}
	
	
	@Override
	public void refresh(boolean empty, boolean initialValue) {
		if(usetextbox) {
			if(empty) {
				tb_datetime.setStringValue("");
			} else {
				tb_datetime.setStringValue(inedate.getText(PRECISION));
			}
			
			prevValue=tb_datetime.getStringValue();
			
		} else {
			lbl_datetime.setText(inedate.getText(PRECISION));
		}
	}

	@Override
	protected void onAttach() {
		super.onAttach();

		if(showstepbuttons) {
			registerHandler(img_step_bck.addClickHandler(new StepDateTimeClickHandler(false)));
			registerHandler(img_step_fwd.addClickHandler(new StepDateTimeClickHandler(true)));
		}

		if(usetextbox) {
			registerHandler(tb_datetime.addDateTimeTextBoxEventHandler(new DateTimeTextBoxEventHandler()));
		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	private class StepDateTimeClickHandler implements ClickHandler {

		private final boolean forward;

		public StepDateTimeClickHandler(boolean forward) {
			this.forward=forward;
		}

		@Override
		public void onClick(ClickEvent event) {
			if(!enabled) return;
			
			if(forward) {
				inedate.stepForward(PRECISION, stepcount);
				parent.childValueChanged(true, false);
			} else {
				inedate.stepForward(PRECISION, -stepcount);
				parent.childValueChanged(true, false);
			}
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled=enabled;

		if(usetextbox) {
			tb_datetime.setEnabled(enabled);
		}
		
		if(showstepbuttons) {
			if(enabled) {
				img_step_bck.setResource(IneResources.INSTANCE.arrowLeft());
				img_step_fwd.setResource(IneResources.INSTANCE.arrowRight());
				img_step_bck.addStyleName("clickable");
				img_step_fwd.addStyleName("clickable");
			} else {
				img_step_bck.setResource(IneResources.INSTANCE.arrowLeft_disabled());
				img_step_fwd.setResource(IneResources.INSTANCE.arrowRight_disabled());
				img_step_bck.removeStyleName("clickable");
				img_step_fwd.removeStyleName("clickable");
			}
		}

	}

	private class DateTimeTextBoxEventHandler implements DateTimeTextBox.DateTimeTextBoxEventHandler {
		
		public DateTimeTextBoxEventHandler() {
		}

		@Override
		public void onChange() {
			if(prevValue.equals(tb_datetime.getStringValue())) return;
			
			if(tb_datetime.getStringValue().length()==0) {
				inedate.setDateNull(PRECISION);				
			} else  {
				try {
					inedate.setDate(PRECISION, PRECISION.getFormatter().parse(tb_datetime.getStringValue()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			parent.childValueChanged(true, false);
		}

	}

	@Override
	public boolean isEmpty() {
		return inedate.isEmpty(PRECISION) ;
	}
	
	@Override
	public boolean isNull() {
		//TODO implement more suitable behave
		return inedate.isEmpty(PRECISION) ;
	}
	
	@Override
	public boolean isFocusable() {
		return usetextbox;
	}
	
	@Override
	public void setFocus(boolean focused) {
		tb_datetime.setFocus(focused);
	}
	
	@Override
	public boolean isTextBox() {
		return usetextbox;
	}
}
