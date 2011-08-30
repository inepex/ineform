package com.inepex.ineom.shared.descriptor;

import java.util.List;

public interface DescriptorStore {

	/**
	 * Returns the {@link ObjectDesc} belonging to the given name
	 * @param name The name of the {@link ObjectDesc}
	 * @return The {@link ObjectDesc} instance if found
	 */
	public abstract ObjectDesc getOD(String objectDescriptorName);

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

	public abstract ObjectDesc getCustomOd(Long id);

}