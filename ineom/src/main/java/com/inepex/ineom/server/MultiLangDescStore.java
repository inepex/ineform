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

public class MultiLangDescStore extends DescriptorStore {
	
	private static final Logger _logger = LoggerFactory.getLogger(MultiLangDescStore.class);
	
	private final Map<String, DescriptorStore> storeByLang = new HashMap<String, DescriptorStore>();
	protected final Provider<CurrentLang> currLangProvider;
	
	@Inject
	public MultiLangDescStore(Provider<CurrentLang> currLangProvider) {
		this.currLangProvider = currLangProvider;
	}
	
	public DescriptorStore get(String lang) {
		if (!storeByLang.containsKey(lang)){
			_logger.info("MultiDescStore has just created desc store for lang: {}", lang);
			ClientDescriptorStore localizedDescStore = createDescStore(lang);
			storeByLang.put(lang, localizedDescStore);
		}
		return storeByLang.get(lang);
	}
	
	public String currentLang() {
		try {
			return currLangProvider.get().getCurrentLang();
		} catch (Exception e) {
			_logger.warn("Lang can not found:", e);
			return CurrentLang.DEFAULT_LANG;
		}
	}
	
	protected ClientDescriptorStore createDescStore(String lang) {
		return new ClientDescriptorStore();
	}

	// Bridge methods follow
	
	@Override
	public ObjectDesc getOD(String objectDescriptorName) {
		return getCurrentDescriptorStore().getOD(objectDescriptorName);
	}


	@Override
	public void registerObjectDesc(ObjectDesc descriptor) {
		getCurrentDescriptorStore().registerObjectDesc(descriptor);
	}

	@Override
	public FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path) {
		return getCurrentDescriptorStore().getRelatedFieldDescrMultiLevel(baseOD, path);
	}

	@Override
	public <D extends DescriptorBase> D getNamedTypedDesc(String objDescName, String namedDescName, Class<D> clazz) {
		return getCurrentDescriptorStore().getNamedTypedDesc(objDescName, namedDescName, clazz);
	}

	@Override
	public <D extends DescriptorBase> void addNamedTypedDesc(String objDescName, String namedDescName, D namedDesc) {
		getCurrentDescriptorStore().addNamedTypedDesc(objDescName, namedDescName, namedDesc);
	}

	@Override
	public void registerDescriptors(ObjectDesc descriptor, DescriptorBase... defaultDescriptors) {
		getCurrentDescriptorStore().registerDescriptors(descriptor, defaultDescriptors);
	}
	
	public DescriptorStore getCurrentDescriptorStore(){
		return get(currentLang());
	}

	@Override
	public String getOdNames(Separator separator) {
		StringBuilder sb = new StringBuilder();
		for(String lang : storeByLang.keySet()) {
			if(sb.length()>0) {
				switch (separator) {
				case htmlBR:
					sb.append("<br /><br />");
					break;
				case javaNewLine:
				default:
					sb.append("\n");
					break;
				}
			}
			sb.append(lang);
			sb.append(": (");
			sb.append(storeByLang.get(lang).getOdNames(separator));
			sb.append(")");
		}
		
		return sb.toString();
	}
	
	public Map<String, DescriptorStore> getStoreByKey() {
		return storeByLang;
	}
}

