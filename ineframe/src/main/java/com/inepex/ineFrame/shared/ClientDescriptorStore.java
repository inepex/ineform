package com.inepex.ineFrame.shared;

import com.google.inject.Inject;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStoreBase;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class ClientDescriptorStore extends ClientDescriptorStoreBase{

	private final IneDispatch ineDispatch;
	
	@Inject
	public ClientDescriptorStore(IneDispatch ineDispatch) {
		this.ineDispatch=ineDispatch;
	}

	@Override
	public void getCustomOd(Long id, final OdFoundCallback callback) {
		if(id==null || IFConsts.NEW_ITEM_ID.equals(id)) {
			callback.onFound(new ObjectDesc(IFConsts.customDescriptorName));
			return;
		}
		
		ineDispatch.execute(new ObjectDescAction(id), new IneDispatchBase.SuccessCallback<ObjectDescResult>() {

			@Override
			public void onSuccess(ObjectDescResult result) {
				callback.onFound(result.getOd());
			}
		});
	}

}
