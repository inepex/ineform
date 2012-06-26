package com.inepex.ineFrame.server.handler;

import java.util.ArrayList;

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
import com.inepex.ineom.shared.descriptor.ODescMarkerPair;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class GetDescriptorStoreHandler extends AbstractIneHandler<GetDescStore, GetDescStoreResult> {
	
	private final DescriptorStore descStore;
	
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
		
		if(descStore instanceof MultiLangDescStore){
			GetDescStoreResult result = new GetDescStoreResult();
			handleMultiLangDescStore(descStore, result);
			return result;
		}
		
		throw new UnsupportedOperationException("unsupported DescStore! implement it for: "+descStore.getClass().getName());
	}

	private void handleMultiLangDescStore(DescriptorStore descStore, GetDescStoreResult result) {
		MultiLangDescStore multiLangDescStore = (MultiLangDescStore) descStore;
		ClientDescriptorStore clientDescStore = (ClientDescriptorStore) multiLangDescStore.getCurrentDescriptorStore();
		
		//set untyped descriptors
		if(clientDescStore.getOjectDescriptorMap()!=null && clientDescStore.getOjectDescriptorMap().size()>0) {
			ArrayList<ObjectDesc> odList = new ArrayList<ObjectDesc>(clientDescStore.getOjectDescriptorMap().size());
			for(ODescMarkerPair p : clientDescStore.getOjectDescriptorMap().values()) 
				odList.add(p.getObjectDesc());
			
			result.setObjectDescs(odList);
		}
		
		//set typed descriptors
		result.setAllTypedDescriptorMap(clientDescStore.getAllTypedDescriptorMap() );
	}
}
