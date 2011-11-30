package com.inepex.ineom.shared.descriptor;

import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Helper class for string Typed Descriptors. This class is used in {@link ClientDescriptorStore}.
 * {@link ClientDescriptorStore} creates a new {@link TypedDescriptorMap} for each {@link DescriptorBase} type, that is
 * added to the {@link DescriptorStore}
 * 
 * @author istvanszoboszlai
 *
 * @param <D> must be subclass of {@link DescriptorBase}
 */
public class TypedDescriptorMap<D extends DescriptorBase> implements IsSerializable {
	
	Map<String, D> defaultDescriptors = new TreeMap<String, D>();
	Map<String, Map<String, D>> namedDescriptorsForObject = new TreeMap<String, Map<String,D>>();
	
	public void addDefaultDescriptor(String objDescName, D descriptor) {
		defaultDescriptors.put(objDescName, descriptor);
	}
	
	public D getDefaultDescriptor(String objDescName) {
		return defaultDescriptors.get(objDescName);
	}
	
	public void addNamedDescriptor(String objDescName, String descName, D descriptor){
		
		Map<String, D> descriptors = namedDescriptorsForObject.get(objDescName);
		
		if (descriptors == null) {
			descriptors =  new TreeMap<String, D>();
			namedDescriptorsForObject.put(objDescName, descriptors);
		}
		
		descriptors.put(descName, descriptor);
		
	}
	
	public D getNamedDescriptor(String objDescName, String descName) {
		try {
			return namedDescriptorsForObject.get(objDescName).get(descName);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public Map<String, D> getDefaultDescriptors(){
		return defaultDescriptors;
	}	
	
	public Map<String, Map<String, D>> getNamedDescriptors(){
		return namedDescriptorsForObject;
	}
}
