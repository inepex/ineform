package com.inepex.ineForm.shared.customkvoeditor;

import java.util.Collection;

import com.google.inject.Inject;
import com.inepex.ineForm.server.customkvo.CustomKVO;
import com.inepex.ineFrame.shared.util.NumberUtil;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

/**
 * It's a shared class for reading fields of {@link CustomKVO}. 
 * 
 * @author sebi
 */
public class CustomKVOReader {
	
	private final CustomOdFinder customOdFinder;
	private final NumberUtil numberUtil;
	
	@Inject
	CustomKVOReader(CustomOdFinder customOdFinder, NumberUtil numberUtil) {
		this.customOdFinder=customOdFinder;
		this.numberUtil=numberUtil;
	}
	
	/**
	 * Method for async reader creation. {@link ReadyCallback#onReady(Reader)} 
	 * will be always invoked with a real reader or null if error happened.    
	 * 
	 * @param id
	 * @param readyCallback 
	 */
	public void createReader(Long id, final ReadyCallback readyCallback) {
		customOdFinder.getCustomOdWithData(id, new CustomOdFinder.OdAndDataFoundCallback() {
			
			@Override
			public void onFound(ObjectDesc od, AssistedObject kvo) {
				if(od==null || kvo==null)
					readyCallback.onReady(null);
				else
					readyCallback.onReady(new ReaderImpl(od, kvo, numberUtil));
			}
		});
	}
	
	public static interface Reader {
		
		/**
		 * @return defined keys
		 */
		public Collection<String> getKeys();
		
		/**
		 * @return human readable value of field or null 
		 */
		public String getValueAsString(String key);
	}
	
	public static interface ReadyCallback {
		public void onReady(Reader reader);
	}
	
}
