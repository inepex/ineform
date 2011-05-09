package com.inepex.ineForm.client.form.widgets.datetime;


import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;


class YMD_HMField extends AbstractField {
	
	public YMD_HMField(IneDateGWT date,  boolean showstepbuttons, int stepcount, boolean usetextbox, DateTimeFieldParentInterface parent, boolean enableselectmanager) {
		super(date, Precision.YMD_HM, showstepbuttons, stepcount, usetextbox, parent,enableselectmanager);
	}

}
