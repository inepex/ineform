package com.inepex.translatorapp;

import javax.persistence.EntityManager;

import com.inepex.ineForm.server.util.StringUtil;
import com.inepex.translatorapp.server.entity.Lang;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.server.entity.TranslatedValue;
import com.inepex.translatorapp.server.entity.User;
import com.inepex.translatorapp.server.entity.UserLang;
import com.inepex.translatorapp.shared.Consts;

public class TestDataCreator {

    private static EntityManager em;

    private static Lang en;
    private static Lang de;
    private static Lang hu;

    private static Module uiModule;
    private static Module coreModule;

    private static User dev;
    private static User trans;

    public static void main(String[] args) throws Exception {
        em = EntityManagerInitializier.initInDropCreateMode();

        createLangsAndModules();
        createUsers();
        createUiModuleRows();
        createCoreModuleRows();
    }

    private static void createUsers() throws Exception {
        em.getTransaction().begin();

        dev = new User();
        dev.setRole(Consts.Roles.developer);
        dev.setEmail("developer@inepex.com");
        dev.setPassword(StringUtil.hash("a"));
        dev.getTranslates().add(lang(dev, en));
        dev.getTranslates().add(lang(dev, hu));
        em.persist(dev);

        trans = new User();
        trans.setRole(Consts.Roles.translator);
        trans.setEmail("translator@inepex.com");
        trans.setPassword(StringUtil.hash("a"));
        trans.getTranslates().add(lang(trans, hu));
        trans.getTranslates().add(lang(trans, en));
        em.persist(trans);

        User inactive = new User();
        inactive.setEmail("inactive@inepex.com");
        inactive.setPassword(StringUtil.hash("a"));
        em.persist(inactive);

        em.getTransaction().commit();
    }

    private static UserLang lang(User u, Lang l) {
        UserLang lang = new UserLang();
        lang.setUser(u);
        lang.setLang(l);
        return lang;
    }

    private static void createLangsAndModules() {
        em.getTransaction().begin();

        en = new Lang("en", "gb");
        em.persist(en);

        de = new Lang("de", "de");
        em.persist(de);

        hu = new Lang("hu", "hu");
        em.persist(hu);

        uiModule = new Module();
        uiModule.setName("uiI18nModule");
        uiModule.getLangs().add(lang(uiModule, hu));
        uiModule.getLangs().add(lang(uiModule, de));
        uiModule.getLangs().add(lang(uiModule, en));
        em.persist(uiModule);

        coreModule = new Module();
        coreModule.setName("coreI18nModule");
        coreModule.getLangs().add(lang(coreModule, en));

        em.persist(coreModule);

        em.getTransaction().commit();
    }

    private static ModuleLang lang(Module module, Lang l) {
        ModuleLang ml = new ModuleLang();
        ml.setModule(module);
        ml.setLang(l);
        return ml;
    }

    private static void createCoreModuleRows() {
        em.getTransaction().begin();

        {
            ModuleRow row = new ModuleRow();
            row.setDescription("This is the subject of the daily summary analysis.");
            row.setKey("analysisMailSubject");
            row.setModule(coreModule);
            coreModule.getRows().add(row);
            row.getValues().add(devTranVal(row, en, "Daily summary analysis", dev, 0));
        }

        {
            ModuleRow row = new ModuleRow();
            row.setDescription("This is the body of the daily summary analysis.");
            row.setKey("analysisMailBody");
            row.setModule(coreModule);
            coreModule.getRows().add(row);
            row.getValues().add(
                devTranVal(row, en, "<b>System statistic:</b><br/>{statTable}<br/>"
                    + "<b>Peak usage period:</b> {peak}<br/>"
                    + "<b>Warnings:</b> {warnings}<br/>"
                    + "<b>Hints:</b> {hint}<br/>", dev, 0));
        }

        {
            ModuleRow row = new ModuleRow();
            row.setDescription("Warning message base");
            row.setKey("warning");
            row.setModule(coreModule);
            coreModule.getRows().add(row);
            row.getValues().add(
                devTranVal(
                    row,
                    en,
                    "Invalid usage of {function}. See details at {docReference}",
                    dev,
                    0));
        }

        em.merge(coreModule);
        em.getTransaction().commit();
    }

    private static TranslatedValue devTranVal(
        ModuleRow row,
        Lang lang,
        String value,
        User u,
        long creationMinus) {
        TranslatedValue v = new TranslatedValue();
        v.setLang(lang);
        v.setLastModTime(System.currentTimeMillis() - creationMinus);
        v.setLastModUser(u);
        v.setValue(value);
        v.setRow(row);
        return v;
    }

    private static void createUiModuleRows() {
        em.getTransaction().begin();

        {
            ModuleRow row = new ModuleRow();
            row.setDescription("Outdated1");
            row.setKey("outDated");
            row.setModule(uiModule);
            uiModule.getRows().add(row);
            row.getValues().add(devTranVal(row, hu, "Régi", dev, 0));
            row.getValues().add(devTranVal(row, en, "Outdated", dev, 0));
            row.getValues().add(devTranVal(row, de, "", dev, 0));
        }

        {
            ModuleRow row = new ModuleRow();
            row.setDescription("Outdated2");
            row.setKey("outDated2");
            row.setModule(uiModule);
            uiModule.getRows().add(row);
            row.getValues().add(devTranVal(row, hu, "Régi2", dev, -100));
            row.getValues().add(devTranVal(row, en, "Outdated", dev, 0));
            row.getValues().add(devTranVal(row, de, "", dev, 0));
        }

        {
            ModuleRow row = new ModuleRow();
            row.setDescription("Outdated3");
            row.setKey("outDated3");
            row.setModule(uiModule);
            uiModule.getRows().add(row);
            row.getValues().add(devTranVal(row, hu, "Régi2", dev, 0));
            row.getValues().add(devTranVal(row, en, "Outdated", dev, 0));
            row.getValues().add(devTranVal(row, de, null, dev, 0));
        }

        {
            ModuleRow row = new ModuleRow();
            row.setDescription("simple");
            row.setKey("simple");
            row.setModule(uiModule);
            uiModule.getRows().add(row);
            row.getValues().add(devTranVal(row, hu, "Jó", dev, Consts.recentTimeRange + 100));
            row.getValues().add(devTranVal(row, en, "Ok", dev, Consts.recentTimeRange + 99));
            row.getValues().add(devTranVal(row, de, "Richtig", dev, Consts.recentTimeRange + 99));
        }

        {
            ModuleRow row = new ModuleRow();
            row.setDescription("new");
            row.setKey("new");
            row.setModule(uiModule);
            uiModule.getRows().add(row);
            row.getValues().add(devTranVal(row, hu, "Új", dev, 0));
            row.getValues().add(devTranVal(row, en, "New", dev, -1));
            row.getValues().add(devTranVal(row, de, "Neu", dev, -1));
        }

        em.merge(uiModule);
        em.getTransaction().commit();
    }
}
