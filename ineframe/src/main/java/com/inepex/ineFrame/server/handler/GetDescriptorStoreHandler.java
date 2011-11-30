package com.inepex.ineFrame.server.handler;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.inject.Inject;
import com.inepex.ineFrame.server.dispatch.AbstractIneHandler;
import com.inepex.ineFrame.shared.GetDescStore;
import com.inepex.ineFrame.shared.GetDescStoreResult;
import com.inepex.ineFrame.shared.exceptions.AuthenticationException;
import com.inepex.ineom.server.MultiLangDescStore;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class GetDescriptorStoreHandler extends AbstractIneHandler<GetDescStore, GetDescStoreResult> {
	private DescriptorStore descStore;
	
	public GetDescriptorStoreHandler() {
	}
	
	@Inject
	public GetDescriptorStoreHandler(DescriptorStore descStore) {
		this.descStore = descStore;
	}
	
	@Override
	public Class<GetDescStore> getActionType() {
		return GetDescStore.class;
	}

	@Override
	protected GetDescStoreResult doExecute(GetDescStore action,	ExecutionContext context) throws AuthenticationException,
			DispatchException {
		
		GetDescStoreResult result = new GetDescStoreResult();
		
		if(descStore instanceof MultiLangDescStore){
			handleMultiLangDescStore(descStore, result);			
		}
		
		return result;
	}

	private void handleMultiLangDescStore(DescriptorStore descStore, GetDescStoreResult result) {
		MultiLangDescStore multiLangDescStore = (MultiLangDescStore) descStore;
		ClientDescriptorStore clientDescStore = (ClientDescriptorStore)multiLangDescStore.getCurrentLanguageDescriptorStore();
		
		//set untyped descriptors
		result.setObjectDescriptorMap( clientDescStore.getDescriptorsMap() );
		
		//set typed descriptors
		result.setAllTypedDescriptorMap( clientDescStore.getAllTypedDescriptorMap() );
	}
}
