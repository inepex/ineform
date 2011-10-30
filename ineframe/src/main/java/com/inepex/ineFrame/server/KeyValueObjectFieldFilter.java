package com.inepex.ineFrame.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class KeyValueObjectFieldFilter {
	
	/**
	 * copy id and enabled keys into an another kvo
	 *  !!!!!!!!! logic for RELATED fields is not implemented yet !!!!!!
	 *  
	 */
	public static AssistedObject filterKvo(DescriptorStore descStore, Collection<String> enabledKeys,
			KeyValueObject kvo, AssistedObjectHandlerFactory factory) {
		
		ObjectDesc od = descStore.getOD(kvo.getDescriptorName());
		
		AssistedObjectHandler ret = factory.createHandler(
				new KeyValueObject(kvo.getDescriptorName(), kvo.getId()));
				
		AssistedObjectHandler kvoChecker = factory.createHandler(kvo);
		
		List<String> copylist = new ArrayList<String>(enabledKeys);
		copylist.add(IFConsts.KEY_ID);
		
		for(String key : copylist) {
			FDesc fDesc = od.getField(key);
			
			if(fDesc!=null) {
				switch(fDesc.getType()) {
				case BOOLEAN:
					if(kvoChecker.containsBoolean(key))
						ret.set(key, kvoChecker.getBoolean(key));
					break;
				case DOUBLE:
					if(kvoChecker.containsDouble(key))
						ret.set(key, kvoChecker.getDouble(key));
					break;
				case LIST:
					if(kvoChecker.containsList(key))
						ret.set(key, kvoChecker.getList(key));
					break;
				case LONG:
					if(kvoChecker.containsLong(key))
						ret.set(key, kvoChecker.getLong(key));
					break;
				case STRING:
					if(kvoChecker.containsString(key))
						ret.set(key, kvoChecker.getString(key));
					break;
				case RELATION:
					if(kvoChecker.containsRelation(key))
						ret.set(key, kvoChecker.getRelation(key));
				}
			}
		}
		
		return ret.getAssistedObject();
	}
}
