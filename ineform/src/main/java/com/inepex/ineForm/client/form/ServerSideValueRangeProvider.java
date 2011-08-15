package com.inepex.ineForm.client.form;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListActionResult;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatchBase;
import com.inepex.ineom.shared.dispatch.interfaces.RelationList;

public class ServerSideValueRangeProvider extends DefaultValueRangeProvider {
	
	protected final IneDispatch dispatch;
	
	@Inject
	public ServerSideValueRangeProvider(IneDispatch dispatch) {
		this.dispatch = dispatch;
	}

	@Override
	protected void execute(RelationList relationList, final RelationListResultCallback callback) {
		dispatch.execute(
				(RelationListAction)relationList, 
				new IneDispatchBase.SuccessCallback<RelationListActionResult>(){

					@Override
					public void onSuccess(RelationListActionResult result) {
						callback.onSuccess(result);
					}
					
		});
	}

	@Override
	protected RelationList createNew() {
		return new RelationListAction();
	}
}
