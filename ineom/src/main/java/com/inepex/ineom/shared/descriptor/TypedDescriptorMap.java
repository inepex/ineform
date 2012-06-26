package com.inepex.ineom.shared.descriptor;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.descriptor.DescriptorStore.Marker;

/**
 * Helper class for string Typed Descriptors. This class is used in {@link ClientDescriptorStore}.
 * {@link ClientDescriptorStore} creates a new {@link TypedDescriptorMap} for each {@link DescriptorBase} type, that is
 * added to the {@link DescriptorStore}
 * 
 * @author istvanszoboszlai
 *
 * @param <D> must be subclass of {@link DescriptorBase}
 */
public class TypedDescriptorMap<D> implements IsSerializable {
	
	private Map<String, Map<String, D>> namedDescriptorsByOdName = new TreeMap<String, Map<String,D>>();
	
	public void addNamedDescriptor(Marker marker, String odName, String descName, D descriptor){
		
		Map<String, D> descriptors = namedDescriptorsByOdName.get(odName);
		
		if (descriptors == null) {
			descriptors =  new TreeMap<String, D>();
			namedDescriptorsByOdName.put(odName, descriptors);
		}
		
		descriptors.put(descName, descriptor);
	}
	
	public D getNamedDescriptor(String odName, String descName) {
		Map<String, D> namedDescriptors = namedDescriptorsByOdName.get(odName);
		if(namedDescriptors==null)
			return null;
		else
			return namedDescriptors.get(descName);
	}
	
	public Map<String, Map<String, D>> getNamedDescriptors(){
		return namedDescriptorsByOdName;
	}
}
