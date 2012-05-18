package com.inepex.ineom.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptor.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class MultiLangDescStore implements DescriptorStore {
	
	private static final Logger _logger = LoggerFactory.getLogger(MultiLangDescStore.class);

	private final String DEFAULT_LANG = "en"; 
	
	private Map<String, DescriptorStore> storeByLang = new HashMap<String, DescriptorStore>();
	private final Provider<CurrentLang> currLangProvider;
	
	@Inject
	public MultiLangDescStore(Provider<CurrentLang> currLangProvider) {
		this.currLangProvider = currLangProvider;
	}
	
	public void addStore(String lang, DescriptorStore store) {
		storeByLang.put(lang, store);
	}
	
	private DescriptorStore get() {
		String lang = null;
		if (currLangProvider == null)
			lang = DEFAULT_LANG;
		else
			lang = currLangProvider.get().getCurrentLang();
		if (!storeByLang.containsKey(lang)){
			_logger.warn("MultiLangDescStore hasn't been initialized");
			ClientDescriptorStore localizedDescStore = new ClientDescriptorStore();
			storeByLang.put(lang, localizedDescStore);			
		}
		return storeByLang.get(lang);
	}

	// Bridge methods follow
	
	@Override
	public ObjectDesc getOD(String objectDescriptorName) {
		return get().getOD(objectDescriptorName);
	}


	@Override
	public void registerObjectDesc(ObjectDesc descriptor) {
		get().registerObjectDesc(descriptor);
	}

	@Override
	public FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path) {
		return get().getRelatedFieldDescrMultiLevel(baseOD, path);
	}

	@Override
	public <D extends DescriptorBase> D getDefaultTypedDesc(String objDescName, Class<D> clazz) {
		return get().getDefaultTypedDesc(objDescName, clazz);
	}

	@Override
	public <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz) {
		return get().getNamedTypedDesc(objDescName, namedDescName, clazz);
	}

	@Override
	public <D extends DescriptorBase> void addDefaultTypedDesc(String objDescName, D defaultDesc) {
		get().addDefaultTypedDesc(objDescName, defaultDesc);
	}

	@Override
	public <D extends DescriptorBase> void addNamedTypedDesc(String objDescName, String namedDescName, D namedDesc) {
		get().addNamedTypedDesc(objDescName, namedDescName, namedDesc);
	}

	@Override
	public void registerDescriptors(ObjectDesc descriptor, DescriptorBase... defaultDescriptors) {
		get().registerDescriptors(descriptor, defaultDescriptors);
	}
	
	public DescriptorStore getCurrentLanguageDescriptorStore(){
		return get();
	}

	@Override
	public String getOdNames() {
		StringBuilder sb = new StringBuilder();
		for(String lang : storeByLang.keySet()) {
			if(sb.length()>0)
				sb.append("\n");
			sb.append(lang);
			sb.append(": (");
			sb.append(storeByLang.get(lang).getOdNames());
			sb.append(")");
		}
		
		return sb.toString();
	}
}

