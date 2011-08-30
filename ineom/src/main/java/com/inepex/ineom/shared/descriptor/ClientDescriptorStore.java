package com.inepex.ineom.shared.descriptor;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;

/**
 * This class is used for storing {@link ObjectDesc}s, {@link FormRDesc}s, {@link ValidatorDesc}s
 * {@link TableRDesc}s by descriptorName
 * 
 * RenderDescriptors can have specific names, although to all ObjectDescriptors a default instants from all
 * 3 types of descriptors should exist.
 * 
 * @author istvan
 *
 */
public class ClientDescriptorStore implements DescriptorStore {

	public static final String DEFAULT_DESC_KEY = "default";
	
	@Inject
	public ClientDescriptorStore() {
	}

	protected final Map<String, ObjectDesc>	objectDescriptorMap = new TreeMap<String, ObjectDesc>();
	
    protected final Map<String, TypedDescriptorMap<? extends DescriptorBase>>
    	typedDescMap = new TreeMap<String, TypedDescriptorMap<? extends DescriptorBase>>();
		
	
	@Override
	public ObjectDesc getOD(String objectDescriptorName) {
		return objectDescriptorMap.get(objectDescriptorName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends DescriptorBase> D getDefaultTypedDesc(String objDescName, Class<D> clazz) {
		ensureDescriptorForClass(clazz);
		return (D) typedDescMap.get(clazz.getName()).getDefaultDescriptor(objDescName);
	}

	@SuppressWarnings("unchecked")
	public <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz) {
		ensureDescriptorForClass(clazz);
		return (D) typedDescMap.get(clazz.getName()).getNamedDescriptor(objDescName, namedDescName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <D extends DescriptorBase> void addDefaultTypedDesc(String objDescName, D defaultDesc) {
		ensureDescriptorForClass(defaultDesc.getClass());
		((TypedDescriptorMap<D>) typedDescMap.get(defaultDesc.getClass().getName()))
			.addDefaultDescriptor(objDescName, defaultDesc);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <D extends DescriptorBase> void addNamedTypedDesc(String objDescName, String namedDescName, D namedDesc) {
		ensureDescriptorForClass(namedDesc.getClass());
		
		((TypedDescriptorMap<D>) typedDescMap.get(namedDesc.getClass().getName()))
			.addNamedDescriptor(objDescName, namedDescName, namedDesc);
	}
	
	private <D extends DescriptorBase> void ensureDescriptorForClass(Class<D> clazz) {
		if (!typedDescMap.containsKey(clazz.getName()))
			typedDescMap.put(clazz.getName(), new TypedDescriptorMap<D>());
	}
	
	@Override
	public void registerDescriptors(ObjectDesc objDesc, DescriptorBase... defaultDescriptors) {
		registerObjectDesc(objDesc);

		for (DescriptorBase descriptorBase : defaultDescriptors) {
			if (descriptorBase == null)
				continue;
			addDefaultTypedDesc(objDesc.getName(), descriptorBase);
		}
	}

	@Override
	public void registerObjectDesc(ObjectDesc objDesc){
		String oDName = objDesc.getName();
		objectDescriptorMap.put(oDName, objDesc);
	}
	
	public FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path) {
		ObjectDesc relObjectDesc = baseOD;
		for (int i = 0; i < path.size()-1; i++){
			RelationFDesc relFieldDescr = 
				(RelationFDesc)relObjectDesc.getField(path.get(i));
			relObjectDesc = this.getOD(relFieldDescr.getRelatedDescriptorName());
		}
		return relObjectDesc.getField(path.get(path.size()-1));
	}
}
