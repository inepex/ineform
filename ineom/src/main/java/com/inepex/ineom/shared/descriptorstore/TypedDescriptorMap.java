package com.inepex.ineom.shared.descriptorstore;

import java.util.Map;

import com.inepex.ineom.shared.descriptor.DescriptorBase;

/**
 * Helper class for string Typed Descriptors. This class is used in {@link ClientDescriptorStore}.
 * {@link ClientDescriptorStore} creates a new {@link TypedDescriptorMap} for each {@link DescriptorBase} type, that is
 * added to the {@link DescriptorStore}
 * 
 * @author istvanszoboszlai
 *
 * @param <D> must be subclass of {@link DescriptorBase}
 */
public class TypedDescriptorMap<D extends DescriptorBase > {
	
	private final DescriptorStoreMapCreator mapCreator;
	private final Map<String, Map<String, D>> namedDescriptorsByOdName;
	
	public TypedDescriptorMap(DescriptorStoreMapCreator mapCreator) {
		this.mapCreator=mapCreator;
		this.namedDescriptorsByOdName=mapCreator.createMap(new DescriptorStoreMapCreator.GenParam<String, Map<String, D>>());
	}
	
	public void addNamedDescriptor(String odName, String descName, D descriptor){
		
		Map<String, D> descriptors = namedDescriptorsByOdName.get(odName);
		
		if (descriptors == null) {
			descriptors = mapCreator.createMap(new DescriptorStoreMapCreator.GenParam<String, D>());
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
	
	public Map<String, Map<String, D>> getNamedDescriptorsByOdName() {
		return namedDescriptorsByOdName;
	}
}
