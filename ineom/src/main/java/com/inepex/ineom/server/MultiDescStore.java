package com.inepex.ineom.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

/**
 * Abstract class for {@link MultiLangDescStore} without {@link CurrentLang} dependency.
 */
public abstract class MultiDescStore extends DescriptorStore {
	
	private static final Logger _logger = LoggerFactory.getLogger(MultiDescStore.class);
	private final Map<String, DescriptorStore> storeByKey = new HashMap<String, DescriptorStore>();
	
	protected abstract String key();
	
	public void addStore(String key, DescriptorStore store) {
		storeByKey.put(key, store);
	}
	
	private DescriptorStore get(String key) {
		if (!storeByKey.containsKey(key)){
			_logger.warn("MultiDescStore hasn't been initialized for key: {}", key);
			ClientDescriptorStore localizedDescStore = new ClientDescriptorStore();
			storeByKey.put(key, localizedDescStore);			
		}
		return storeByKey.get(key);
	}

	// Bridge methods follow
	
	@Override
	public ObjectDesc getOD(String objectDescriptorName) {
		return get(key()).getOD(objectDescriptorName);
	}


	@Override
	public void registerObjectDesc(ObjectDesc descriptor) {
		get(key()).registerObjectDesc(descriptor);
	}

	@Override
	public FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path) {
		return get(key()).getRelatedFieldDescrMultiLevel(baseOD, path);
	}

	@Override
	public <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz) {
		return get(key()).getNamedTypedDesc(objDescName, namedDescName, clazz);
	}

	@Override
	public <D extends DescriptorBase> void addNamedTypedDesc(String objDescName, String namedDescName, D namedDesc) {
		get(key()).addNamedTypedDesc(objDescName, namedDescName, namedDesc);
	}

	@Override
	public void registerDescriptors(ObjectDesc descriptor, DescriptorBase... defaultDescriptors) {
		get(key()).registerDescriptors(descriptor, defaultDescriptors);
	}
	
	public DescriptorStore getCurrentLanguageDescriptorStore(){
		return get(key());
	}

	@Override
	public String getOdNames(Separator separator) {
		StringBuilder sb = new StringBuilder();
		for(String key : storeByKey.keySet()) {
			if(sb.length()>0) {
				switch (separator) {
				case htmlBR:
					sb.append("<BR />");
					break;
				case javaNewLine:
				default:
					sb.append("\n");
					break;
				}
			}
			sb.append(key);
			sb.append(": (");
			sb.append(storeByKey.get(key).getOdNames(separator));
			sb.append(")");
		}
		
		return sb.toString();
	}
}

