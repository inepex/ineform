package com.inepex.ineForm.client.form;

import com.google.inject.Inject;
import com.inepex.ineForm.client.datamanipulator.ValueRangeProvider;
import com.inepex.ineForm.client.datamanipulator.ValueRangeResultCallback;
import com.inepex.ineFrame.client.async.IneDispatchBase.SuccessCallback;
import com.inepex.ineom.shared.IneT;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.dispatch.interfaces.RelationList;
import com.inepex.ineom.shared.dispatch.interfaces.RelationListResult;

public abstract class DefaultValueRangeProvider implements ValueRangeProvider {

	@Inject
	public DefaultValueRangeProvider() {
	}
	
	@Override
	public void getRelationValueRange(FDesc fieldDesc, ValueRangeResultCallback callback) {
		RelationFDesc castedFieldDesc = castDescriptorCheckType(fieldDesc, callback);
		if (castedFieldDesc == null) {
			return;
		}
		
		execute(getActionForDescriptorName(castedFieldDesc.getRelatedDescriptorName())
				   , new RelationListResultCallback(callback));
	}
	
	protected abstract void execute(RelationList relationList, RelationListResultCallback callback);
	
	protected abstract RelationList createNew();
	
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
	
	protected RelationList getActionForDescriptorName(String descriptorName) {
		RelationList relationList = createNew();
		relationList.setDescriptorName(descriptorName);
		relationList.setFirstResult(0);
		relationList.setNumMaxResult(10000);
		relationList.setQueryResultCount(false);
		return relationList;
	}
		
	protected class RelationListResultCallback extends SuccessCallback<RelationListResult> {
		
		final ValueRangeResultCallback valueRagecallback;
		
		public RelationListResultCallback(ValueRangeResultCallback valueRagecallback) {
			this.valueRagecallback = valueRagecallback;
		}

		public void onSuccess(RelationListResult result) {
			valueRagecallback.onValueRangeResultReady(result.getList());
		}
	}
}
