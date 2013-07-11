package com.inepex.ineom.shared.descriptorstore;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.ValidatorDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;

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

	private final DescriptorStoreMapCreator mapCreator;
	private final Map<String, ODescMarkerPair> objectDescriptorMap;
	private final Map<String, TypedDescriptorMap<? extends DescriptorBase>> typedDescMap;
	
	@Inject
    public ClientDescriptorStore(DescriptorStoreMapCreator mapCreator) {
    	this.mapCreator=mapCreator;
    	this.objectDescriptorMap=mapCreator.createMap(new DescriptorStoreMapCreator.GenParam<String, ODescMarkerPair>());
    	this.typedDescMap=mapCreator.createMap(new DescriptorStoreMapCreator.GenParam<String, TypedDescriptorMap<? extends DescriptorBase>>());
    }
    
	@Override
	public ObjectDesc getOD(String objectDescriptorName) {
		ODescMarkerPair pair = objectDescriptorMap.get(objectDescriptorName);
		if(pair==null)
			return null;
		else
			return pair.getObjectDesc();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz) {
		ensureDescriptorForClass(clazz);
		return (D) typedDescMap.get(typeToKey(clazz)).getNamedDescriptor(objDescName, namedDescName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <D extends DescriptorBase> void addNamedTypedDesc(Marker marker, String objDescName, String namedDescName, D namedDesc) {
		ensureDescriptorForClass(namedDesc.getClass());
		
		((TypedDescriptorMap<D>) typedDescMap.get(typeToKey(namedDesc.getClass())))
			.addNamedDescriptor(objDescName, namedDescName, namedDesc);
	}
	
	private <D extends DescriptorBase> void ensureDescriptorForClass(Class<D> clazz) {
		if (!typedDescMap.containsKey(typeToKey(clazz)))
			typedDescMap.put(typeToKey(clazz), new TypedDescriptorMap<D>(mapCreator));
	}
	
	@Override
	public void registerDescriptors(Marker marker, ObjectDesc objDesc, DescriptorBase... defaultDescriptors) {
		registerObjectDesc(marker, objDesc);

		for (DescriptorBase descriptorBase : defaultDescriptors) {
			if (descriptorBase == null)
				continue;
			addDefaultTypedDesc(marker, objDesc.getName(), descriptorBase);
		}
	}

	@Override
	public void registerObjectDesc(Marker marker, ObjectDesc objDesc){
		String oDName = objDesc.getName();
		objectDescriptorMap.put(oDName, new ODescMarkerPair(objDesc, marker));
	}
	
	@Override
	public FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path) {
		ObjectDesc relObjectDesc = baseOD;
		for (int i = 0; i < path.size()-1; i++){
			RelationFDesc relFieldDescr = 
				(RelationFDesc)relObjectDesc.getField(path.get(i));
			relObjectDesc = getOD(relFieldDescr.getRelatedDescriptorName());
		}
		return relObjectDesc.getField(path.get(path.size()-1));
	}
	
	public Map<String, ODescMarkerPair> getOjectDescriptorMap(){
		return objectDescriptorMap;
	}
	
	public Map<String, TypedDescriptorMap<? extends DescriptorBase>> getTypedDescMap() {
		return typedDescMap;
	}


	@Override
	public String getOdNames(Decoration decorator) {
		StringBuilder sb = new StringBuilder();
		for(Entry<String, ODescMarkerPair> entry : objectDescriptorMap.entrySet()) {
			if(sb.length()>0)
				sb.append(", ");
			
			sb.append(beforeKey(decorator, entry.getValue().getMarker()));
			sb.append(entry.getKey());
			sb.append(afterKey(decorator, entry.getValue().getMarker()));
		}
		
		return sb.toString();
	}
	
	private Object afterKey(Decoration decorator, Marker marker) {
		switch(decorator) {
		case html:
			return "</font>";
		case javaNewLine:
			return "("+marker.shortName+")";
		default:
			return "";
		}
	}

	private String beforeKey(Decoration decorator, Marker marker) {
		switch(decorator) {
		case html:
			return "<font style='color: "+marker.color+"'>";
		case javaNewLine:
		default:
			return "";
		}
	}
}
