package com.inepex.ineom.shared.descriptorstore;

import java.util.List;
import java.util.Map;

import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public abstract class DescriptorStore {

	public static final String DEFAULT_DESC_KEY = "default";
	
	/**
	 * Helps in {@link DescriptorStore#getOdNames(Decoration)} method to define string format.
	 */
	public static enum Decoration {
		javaNewLine, html;
	}
	
	/**
	 * Describes what program phase added the descriptor.
	 */
	public static enum Marker {
		/**
		 * on the start of program
		 */
		registered("black", "reg"), 
		
		/**
		 * loaded from other module for further usage
		 */
		precached("blue", "pre"), 
		
		/**
		 * by auto download
		 */
		ondemand("red", "od"),
		
		/**
		 * manually
		 */
		otherWay("gray", "other");
		
		public final String color;
		public final String shortName;
		
		private Marker(String color, String shortName) {
			this.color=color;
			this.shortName=shortName;
		}
	}
	
	/**
	 * Some store implementations use {@link Map} and it's the key maker method for {@link DescriptorBase} types.
	 */
	public static <B  extends DescriptorBase> String typeToKey(Class<B> clazz) {
		return clazz.getName();
	}
	
	public final <D extends DescriptorBase> D getDefaultTypedDesc(String objDescName, Class<D> clazz) {
		return getNamedTypedDesc(objDescName, DEFAULT_DESC_KEY, clazz);
	}

	public final <D extends DescriptorBase> void addDefaultTypedDesc(Marker marker, String objDescName, D defaultDesc){
		addNamedTypedDesc(marker, objDescName, DEFAULT_DESC_KEY, defaultDesc);
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
	 * @param decor returned string can be decorated pending on {@link Decoration}
	 * @return the names of registered {@link ObjectDesc}s
	 */
	public abstract String getOdNames(Decoration decor);
	
	public abstract <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz);
	
	public abstract <D extends DescriptorBase> void addNamedTypedDesc(Marker marker, String objDescName, String namedDescName, D namedDesc);
	
	/**
	 * Registers the ObjectDescriptor with any number of related descriptors.
	 */
	public abstract void registerDescriptors(Marker marker, ObjectDesc descriptor, DescriptorBase... defaultDescriptors);

	public abstract void registerObjectDesc(Marker marker, ObjectDesc descriptor);
	
	public abstract FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path);
}