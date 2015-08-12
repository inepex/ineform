package com.inepex.ineom.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptorstore.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class MultiLangDescStore extends DescriptorStore {

    private static final Logger _logger = LoggerFactory.getLogger(MultiLangDescStore.class);

    private final ConcurrentHashMap<String, DescriptorStore> storeByLang =
        new ConcurrentHashMap<String, DescriptorStore>();
    private final Provider<CurrentLang> currLangProvider;
    private DescStoreCreator descStoreCreator;

    @Inject
    public MultiLangDescStore(
        Provider<CurrentLang> currLangProvider,
        DescStoreCreator descStoreCreator) {
        this.currLangProvider = currLangProvider;
        this.descStoreCreator = descStoreCreator;
    }

    public DescriptorStore get(String lang) {
        if (storeByLang.containsKey(lang)) {
            return storeByLang.get(lang);
        } else {
            synchronized (storeByLang) {
                if (!storeByLang.containsKey(lang)) {
                    _logger.trace("MultiDescStore has just created desc store for lang: {}", lang);
                    ClientDescriptorStore localizedDescStore =
                        descStoreCreator.createDescStore(lang);
                    storeByLang.put(lang, localizedDescStore);
                }
                return storeByLang.get(lang);
            }
        }
    }

    public String currentLang() {
        try {
            return currLangProvider.get().getCurrentLang();
        } catch (Exception e) {
            _logger.warn("Lang can not found:", e);
            return CurrentLang.DEFAULT_LANG;
        }
    }

    // Bridge methods follow

    @Override
    public ObjectDesc getOD(String objectDescriptorName) {
        return getCurrentDescriptorStore().getOD(objectDescriptorName);
    }

    @Override
    public void registerObjectDesc(Marker marker, ObjectDesc descriptor) {
        getCurrentDescriptorStore().registerObjectDesc(marker, descriptor);
    }

    @Override
    public FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD, List<String> path) {
        return getCurrentDescriptorStore().getRelatedFieldDescrMultiLevel(baseOD, path);
    }

    @Override
    public <D extends DescriptorBase> D getNamedTypedDesc(
        String objDescName,
        String namedDescName,
        Class<D> clazz) {
        return getCurrentDescriptorStore().getNamedTypedDesc(objDescName, namedDescName, clazz);
    }

    @Override
    public <D extends DescriptorBase> void addNamedTypedDesc(
        Marker marker,
        String objDescName,
        String namedDescName,
        D namedDesc) {
        getCurrentDescriptorStore()
            .addNamedTypedDesc(marker, objDescName, namedDescName, namedDesc);
    }

    @Override
    public void registerDescriptors(
        Marker marker,
        ObjectDesc descriptor,
        DescriptorBase... defaultDescriptors) {
        getCurrentDescriptorStore().registerDescriptors(marker, descriptor, defaultDescriptors);
    }

    public DescriptorStore getCurrentDescriptorStore() {
        return get(currentLang());
    }

    @Override
    public String getOdNames(Decoration decorator) {
        StringBuilder sb = new StringBuilder();
        for (String lang : storeByLang.keySet()) {
            if (sb.length() > 0) {
                switch (decorator) {
                    case html:
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
            sb.append(storeByLang.get(lang).getOdNames(decorator));
            sb.append(")");
        }

        return sb.toString();
    }

    public Map<String, DescriptorStore> getStoreByKey() {
        return storeByLang;
    }
}
