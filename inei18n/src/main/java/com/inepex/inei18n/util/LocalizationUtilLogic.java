package com.inepex.inei18n.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inepex.inei18n.server.I18nModuleConverter;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.server.ModuleProperties;
import com.inepex.inei18n.shared.I18nModule;
import com.inepex.inei18n.shared.LocalizedString;

public class LocalizationUtilLogic {

    private final Class<? extends I18nModule> i18nClass;
    private final String moduleName;

    public LocalizationUtilLogic(Class<? extends I18nModule> i18nClass, String name) {
        this.i18nClass = i18nClass;
        this.moduleName = name;

    }

    public void generateIneFormSourceFromCsv() {
        Map<String, LocalizedString> localizables;
        String downloadUrl = getDownloadUrl();
        if (downloadUrl != null)
            localizables = downloadLocalizables(downloadUrl);
        else
            localizables = loadLocalizablesFormCsv();

        try {

            I18nStore_Server serverStore = new I18nStore_Server();
            serverStore.registerModule(i18nClass.newInstance());

            serverStore.addLocalizables(moduleName, localizables.values());
            I18nModuleConverter srvI18nConverter =
                new I18nModuleConverter(i18nClass, serverStore.getLocalizablesForModule(moduleName));
            srvI18nConverter.saveCsvToDefaultPath();
            srvI18nConverter.generateModuleFile();
            srvI18nConverter.generateServerModuleProviderFile();
            System.out.println("Generation finished!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDownloadUrl() {
        return new ModuleProperties(i18nClass.getClassLoader(), moduleName).downloadUrl;
    }

    private Map<String, LocalizedString> downloadLocalizables(String downloadUrl) {
        System.out.println("Downloading rows from: " + downloadUrl);
        try {
            URL url = new URL(downloadUrl);
            InputStream is = url.openStream();

            StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append(System.getProperty("line.separator"));
                line = bufferedReader.readLine();
            }
            String json = inputStringBuilder.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            DownloadLocalizablesDto dto =
                objectMapper.readValue(json, DownloadLocalizablesDto.class);

            if (dto.getWarning() != null) {
                for (String str : dto.getWarning())
                    System.err.println(str);
            }

            if (dto.getLocalizables() == null || dto.getLocalizables().isEmpty()) {
                throw new RuntimeException("No localizables in the downloaded object.");
            }

            return new TreeMap<>(dto.getLocalizables());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, LocalizedString> loadLocalizablesFormCsv() {
        I18nModuleConverter converter = new I18nModuleConverter(i18nClass);
        converter.loadDataFromDefaultCsvDev();
        return converter.getLocalizablesMap();
    }

    public void generateHelperForEnums(List<Class<?>> enums) {
        if (enums == null || enums.size() == 0)
            return;

        List<EnumI18nExtractor.EnumClassWithPostfix> enumList =
            new ArrayList<EnumI18nExtractor.EnumClassWithPostfix>(enums.size());
        for (Class<?> e : enums)
            enumList.add(new EnumI18nExtractor.EnumClassWithPostfix(e));

        EnumI18nExtractor.generateI18nAccessHelpersForEnums(Thread
            .currentThread()
            .getContextClassLoader(), moduleName, enumList);
    }
}
