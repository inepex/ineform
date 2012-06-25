package com.inepex.ineom.shared.descriptor;

import java.util.List;

public abstract class DescriptorStore {

	public static final String DEFAULT_DESC_KEY = "default";
	public static enum Separator {
		javaNewLine, htmlBR;
	}
	
	public static String typeToKey(Class<?> clazz) {
		return clazz.getName();
	}
	
	public final <D extends DescriptorBase> D getDefaultTypedDesc(String objDescName, Class<D> clazz) {
		return getNamedTypedDesc(objDescName, DEFAULT_DESC_KEY, clazz);
	}

	public final <D extends DescriptorBase> void addDefaultTypedDesc(String objDescName, D defaultDesc){
		addNamedTypedDesc(objDescName, DEFAULT_DESC_KEY, defaultDesc);
	}
	
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
	 * @param separator when returned string contains more than one line, can be separated pending on {@link Separator}
	 * @return the names of registered {@link ObjectDesc}s
	 */
	public abstract String getOdNames(Separator separator);
	
	public abstract <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz);
	
	public abstract <D extends DescriptorBase> void addNamedTypedDesc(String objDescName, String namedDescName, D namedDesc);
	
	/**
	 * Registers the ObjectDescriptor with any number of related descriptors.
	 */
	public abstract void registerDescriptors(ObjectDesc descriptor, DescriptorBase... defaultDescriptors);

	public abstract void registerObjectDesc(ObjectDesc descriptor);
	
	public abstract FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path);
}