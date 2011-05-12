package com.inepex.ineFrame.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.kvo.IFConsts;
import com.inepex.ineom.shared.kvo.KeyValueObject;

public class KeyValueObjcetHidingUtil {
	
	/**
	 * copy id and enabled keys into an another kvo
	 * 
	 *  !!!!!!!!! logic for RELATED fields and RELATION are not implemented yet !!!!!!
	 *  
	 *  TODO: SEBI: rename (class: KeyValueObjectFieldFilter, method: filterKvo)
	 */
	public static KeyValueObject createHidedKVO(DescriptorStore descStore, Collection<String> enabledKeys, KeyValueObject kvo) {
		
		KeyValueObject ret = new KeyValueObject(kvo.getDescriptorName(), kvo.getId());
		ObjectDesc od = descStore.getOD(kvo.getDescriptorName());
		
		List<String> copylist = new ArrayList<String>(enabledKeys);
		copylist.add(IFConsts.KEY_ID);
		
		for(String key : copylist) {
			FDesc fDesc = od.getField(key);
			
			if(fDesc!=null) {
				switch(fDesc.getType()) {
				case BOOLEAN:
					ret.set(key, kvo.getBoolean(key));
					break;
				case DOUBLE:
					ret.set(key, kvo.getDouble(key));
					break;
				case LIST:
					ret.set(key, kvo.getList(key));
					break;
				case LONG:
					ret.set(key, kvo.getLong(key));
					break;
				case STRING:
					ret.set(key, kvo.getString(key));
					break;
				case RELATION:
					//TODO implements
				}
			}
			
		}
		
		return ret;
	}
}
