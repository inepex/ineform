package com.inepex.ineom.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.DescriptorStore;
import com.inepex.ineom.shared.descriptor.FDesc;
import com.inepex.ineom.shared.descriptor.ObjectDesc;

public class MultiLangDescStore implements DescriptorStore {

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
		addDefaultTypedDesc(objDescName, defaultDesc);
	}

	@Override
	public <D extends DescriptorBase> void addNamedTypedDesc(String objDescName, String namedDescName, D namedDesc) {
		addNamedTypedDesc(objDescName, namedDescName, namedDesc);
	}

	@Override
	public void registerDescriptors(ObjectDesc descriptor, DescriptorBase... defaultDescriptors) {
		registerDescriptors(descriptor, defaultDescriptors);
	}

	@Override
	public void getCustomOd(Long id, OdFoundCallback callback) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
	}
}

