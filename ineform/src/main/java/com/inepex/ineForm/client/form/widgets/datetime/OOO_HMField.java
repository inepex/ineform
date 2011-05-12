package com.inepex.ineForm.client.form.widgets.datetime;

import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;


class OOO_HMField extends AbstractField {
	
	public OOO_HMField(IneDateGWT date, boolean showstepbutton, int stepcount, boolean usetextbox, DateTimeFieldParentInterface parent, boolean enableselectmanager) {
		super(date, Precision.OOO_HM, showstepbutton, stepcount, usetextbox, parent, enableselectmanager);
	}

}
