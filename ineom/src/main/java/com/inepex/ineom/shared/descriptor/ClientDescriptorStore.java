package com.inepex.ineom.shared.descriptor;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
public class ClientDescriptorStore extends DescriptorStore {

	protected final Map<String, ObjectDesc>	objectDescriptorMap = new TreeMap<String, ObjectDesc>();
	
    protected final Map<String, TypedDescriptorMap<? extends DescriptorBase>>
    	typedDescMap = new TreeMap<String, TypedDescriptorMap<? extends DescriptorBase>>();
	
	@Override
	public ObjectDesc getOD(String objectDescriptorName) {
		return objectDescriptorMap.get(objectDescriptorName);
	}

	@SuppressWarnings("unchecked")
	public <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz) {
		ensureDescriptorForClass(clazz);
		return (D) typedDescMap.get(clazz.getName()).getNamedDescriptor(objDescName, namedDescName);
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
	
	public Map<String, ObjectDesc> getOjectDescriptorMap(){
		return objectDescriptorMap;
	}

	public Map<String, TypedDescriptorMap<? extends DescriptorBase>> getAllTypedDescriptorMap() {
		return typedDescMap;
	}

	@Override
	public String getOdNames(Separator separator) {
		StringBuilder sb = new StringBuilder();
		for(String key : objectDescriptorMap.keySet()) {
			if(sb.length()>0)
				sb.append(", ");
			sb.append(key);
		}
		
		return sb.toString();
	}
}
