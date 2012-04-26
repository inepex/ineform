package com.inepex.ineom.shared.descriptor;

import java.util.List;

public interface DescriptorStore {

	public static final String DEFAULT_DESC_KEY = "default";
	
	/**
	 * Returns the {@link ObjectDesc} belonging to the given name
	 * 
	 * BE CAREFUL! The CustomKVOs' objectdescriptors can not be given by this method 
	 * 
	 * @param name The name of the {@link ObjectDesc}
	 * @return The {@link ObjectDesc} instance if found
	 */
	public abstract ObjectDesc getOD(String objectDescriptorName);
	
	/**
	 * For debug and logging.
	 * @return the names of registered {@link ObjectDesc}s
	 */
	public abstract String getOdNames();
	
	public <D extends DescriptorBase> D getDefaultTypedDesc(String objDescName, Class<D> clazz);

	public <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz);
	
	public <D extends DescriptorBase> void addDefaultTypedDesc(String objDescName, D defaultDesc);

	public <D extends DescriptorBase> void addNamedTypedDesc(String objDescName, String namedDescName, D namedDesc);
	
	/**
	 * Registers the ObjectDescriptor with any number of related descriptors.
	 */
	public abstract void registerDescriptors(ObjectDesc descriptor, DescriptorBase... defaultDescriptors);

	public abstract void registerObjectDesc(ObjectDesc descriptor);
	
	public FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path);
}