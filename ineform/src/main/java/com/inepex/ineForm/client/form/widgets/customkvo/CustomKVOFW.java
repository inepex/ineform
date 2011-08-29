package com.inepex.ineForm.client.form.widgets.customkvo;

import com.google.gwt.user.client.ui.IsWidget;

public class CustomKVOFW {
	
	public static interface View extends IsWidget {
		public void setRemoveCallback(RemoveCallback removeCallback);
		public void setAddCallback(AddCallback addCallback);
	
		public void clearRows();
		public void addRow(CustomKVORow r);
		public void removeRow(CustomKVORow r);
	}
	
	public static interface RemoveCallback {
		public void onClick(CustomKVORow r);
	}
	
	public static interface AddCallback {
		public void onClick();
	}

}
