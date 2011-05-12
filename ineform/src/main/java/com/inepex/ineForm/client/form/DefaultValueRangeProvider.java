package com.inepex.ineForm.client.form;

import net.customware.gwt.dispatch.shared.Action;

import com.google.inject.Inject;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineForm.shared.dispatch.RelationListAction;
import com.inepex.ineForm.shared.dispatch.RelationListResult;
import com.inepex.ineFrame.client.async.IneDispatch;
import com.inepex.ineFrame.client.async.IneDispatch.SuccessCallback;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.RelationFDesc;
import com.inepex.ineom.shared.kvo.IneT;

public class DefaultValueRangeProvider implements ValueRangeProvider {
	
	protected final IneDispatch dispatch;
	
	
	@Inject
	public DefaultValueRangeProvider(IneDispatch dispatch) {
		this.dispatch = dispatch;
	}
	
	@Override
	public void getRelationValueRange(FDesc fieldDesc, ValueRangeResultCallback callback) {
		RelationFDesc castedFieldDesc = castDescriptorCheckType(fieldDesc, callback);
		if (castedFieldDesc == null) {
			return;
		}
		
		dispatch.execute(getActionForDescriptorName(castedFieldDesc.getRelatedDescriptorName())
					   , new RelationListResultCallback(callback));
	}
	
	/**
	 * Checks weather the fieldDescriptor received is valid. Also calls the callback with null if it is invalid!
	 * @param fieldDesc
	 * @param callback 
	 * @return
	 */
	protected RelationFDesc castDescriptorCheckType(FDesc fieldDesc, ValueRangeResultCallback callback){
		if (fieldDesc.getType() != IneT.RELATION) {
			System.out.println("ValueRangeProvider.getRelationValueRange was called with a FieldDescriptor("+fieldDesc.getKey()+") that" +
					" is not a RelatonFieldDescriptor");
			callback.onValueRangeResultReady(null);
			return null;
		}
		
		return (RelationFDesc) fieldDesc;
	}
	
	protected Action<RelationListResult> getActionForDescriptorName(String descriptorName) {
		return new RelationListAction(descriptorName, null, 0, 10000, false);
	}
		
	class RelationListResultCallback extends SuccessCallback<RelationListResult> {
		
		final ValueRangeResultCallback valueRagecallback;
		
		public RelationListResultCallback(ValueRangeResultCallback valueRagecallback) {
			this.valueRagecallback = valueRagecallback;
		}

		public void onSuccess(RelationListResult result) {
			valueRagecallback.onValueRangeResultReady(result.getList());
		}
	}
}
