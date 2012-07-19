package com.inepex.ineForm.client.form.widgets.customkvo;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.customkvoeditor.CustomOdFinder;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineFrame.shared.CustomObjectDescAction;
import com.inepex.ineFrame.shared.CustomObjectDescResult;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class ActionBasedCustomOdFinder implements CustomOdFinder{

	private final IneDispatch ineDispatch;
	
	@Inject
	ActionBasedCustomOdFinder(IneDispatch ineDispatch) {
		this.ineDispatch=ineDispatch;
	}

	@Override
	public void getCustomOd(Long id, final OdFoundCallback callback) {
		if(id==null || IFConsts.NEW_ITEM_ID.equals(id)) {
			callback.onFound(new ObjectDesc(IFConsts.customDescriptorName));
			return;
		}
		
		ineDispatch.execute(new CustomObjectDescAction(id, false), new IneDispatchBase.SuccessCallback<CustomObjectDescResult>() {

			@Override
			public void onSuccess(CustomObjectDescResult result) {
				callback.onFound(result.getOd());
			}
		});
	}

	@Override
	public void getCustomOdWithData(Long id, final OdAndDataFoundCallback callback) {
		if(id==null || IFConsts.NEW_ITEM_ID.equals(id)) {
			callback.onFound(new ObjectDesc(IFConsts.customDescriptorName), new KeyValueObject(IFConsts.customDescriptorName));
			return;
		}
		
		ineDispatch.execute(new CustomObjectDescAction(id, true), new IneDispatchBase.SuccessCallback<CustomObjectDescResult>() {

			@Override
			public void onSuccess(CustomObjectDescResult result) {
				callback.onFound(result.getOd(), result.getAssistedObject());
			}
		});
	}
}
