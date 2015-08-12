package com.inepex.ineom.shared.validation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Provider;
import com.inepex.inei18n.server.I18nStore_Server;
import com.inepex.inei18n.shared.CurrentLang;
import com.inepex.ineom.server.ServerIneOmI18nProvider;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.StringFDesc;
import com.inepex.ineom.shared.descriptorstore.ClientDescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore.Marker;
import com.inepex.ineom.shared.descriptorstore.TreeDescriptorStoreMapCreator;
import com.inepex.ineom.shared.i18n.IneOmI18n;

public class KeyValueObjectValidationManagerTest {

    private final String odName = "odName";
    private final String f1 = "f1";
    private final String f2 = "f2";

    private final ObjectDesc od = new ObjectDesc(odName).addField(
        new StringFDesc(f1, "f1").maxLength(10).mandatory()).addField(
        new StringFDesc(f2, "f2").email());

    private final DescriptorStore descriptorStore = new ClientDescriptorStore(
        new TreeDescriptorStoreMapCreator());
    private final AssistedObjectHandlerFactory objectHandlerFactory =
        new AssistedObjectHandlerFactory(descriptorStore);
    private final KeyValueObjectValidationManager validationManager =
        new KeyValueObjectValidationManager(descriptorStore, objectHandlerFactory);

    @Before
    public void init() {
        I18nStore_Server store = new I18nStore_Server();
        store.registerModule(new IneOmI18n(new ServerIneOmI18nProvider(
            new TestCurrentLangProvider())));
        store.loadAllModulesDataFormCsv(false);
        store.initI18nModules();

        descriptorStore.registerObjectDesc(Marker.otherWay, od);
    }

    @Test
    public void testBase() {
        ValidationResult v1 = validationManager.validate(null);
        assertNotNull(v1);
        assertTrue(v1.isValid());

        assertVR(validationManager.validate(createKvo(null, null)), false, true);
        assertVR(validationManager.validate(createKvo(null, "a")), false, false);
        assertVR(validationManager.validate(createKvo(null, "alma@alma.hu")), false, true);
        assertVR(
            validationManager.validate(createKvo("1234567890_1234123", "alma@alma.hu")),
            false,
            true);
        assertVR(validationManager.validate(createKvo("aa", "alma@alma.hu")), true, true);
        assertVR(validationManager.validate(createKvo("aa", "a")), true, false);
    }

    @Test
    public void testCustom() {
        Multimap<String, KeyValueObjectValidator> mm1 = ArrayListMultimap.create();
        mm1.put(f1, new StartsWithValidator(f1, "a"));
        mm1.put(f1, new StartsWithValidator(f1, "aa"));
        mm1.put(f1, new StartsWithValidator(f1, "aaa"));

        assertVR(validationManager.validate(createKvo("", ""), mm1), false, true);
        assertVR(validationManager.validate(createKvo("a", ""), mm1), false, true);
        assertVR(validationManager.validate(createKvo("aa", ""), mm1), false, true);
        assertVR(validationManager.validate(createKvo("aaa", ""), mm1), true, true);
        assertVR(validationManager.validate(createKvo("aaa alm", ""), mm1), true, true);

        Multimap<String, KeyValueObjectValidator> mm2 = ArrayListMultimap.create();
        mm2.put(f1, new StartsWithValidator(f1, "aaa"));
        mm2.put(f1, new StartsWithValidator(f1, "aa"));
        mm2.put(f1, new StartsWithValidator(f1, "a"));

        assertVR(validationManager.validate(createKvo("", ""), mm2), false, true);
        assertVR(validationManager.validate(createKvo("a", ""), mm2), false, true);
        assertVR(validationManager.validate(createKvo("aa", ""), mm2), false, true);
        assertVR(validationManager.validate(createKvo("aaa", ""), mm2), true, true);
        assertVR(validationManager.validate(createKvo("aaa alm", ""), mm2), true, true);

        Multimap<String, KeyValueObjectValidator> mm3 = ArrayListMultimap.create();
        mm3.put(f1, new StartsWithValidator(f1, "a"));
        mm3.put(f1, new StartsWithValidator(f1, "b"));

        assertVR(validationManager.validate(createKvo("a", ""), mm3), false, true);
        assertVR(validationManager.validate(createKvo("b", ""), mm3), false, true);
        assertVR(validationManager.validate(createKvo("c", ""), mm3), false, true);
    }

    private void assertVR(ValidationResult vr, boolean f1Valid, boolean f2Valid) {
        assertNotNull(vr);
        assertEquals(f1Valid && f2Valid, vr.isValid());
        assertEquals((f1Valid ? 0 : 1) + (f2Valid ? 0 : 1), vr.getFieldErrors().size());
        if (!f1Valid)
            assertTrue(vr.getFieldErrors().keySet().contains(f1));
        if (!f2Valid)
            assertTrue(vr.getFieldErrors().keySet().contains(f2));

    }

    private AssistedObject createKvo(String f1Content, String f2Content) {
        KeyValueObject kvo = new KeyValueObject(odName);
        kvo.setUnchecked(f1, f1Content);
        kvo.setUnchecked(f2, f2Content);
        return kvo;
    }

    private class TestCurrentLangProvider implements Provider<CurrentLang> {
        @Override
        public CurrentLang get() {
            return new CurrentLang() {
                @Override
                public String getCurrentLang() {
                    return CurrentLang.DEFAULT_LANG;
                }

                @Override
                public void setLangOverride(String langOverride) {}

                @Override
                public void resetLangOverride() {

                }

                @Override
                public void setSessionLang(String lang) {}
            };
        }
    }

    static class StartsWithValidator implements KeyValueObjectValidator {

        private final String field;
        private final String startsWith;

        public StartsWithValidator(String field, String startsWith) {
            this.field = field;
            this.startsWith = startsWith;
        }

        @Override
        public void doValidation(AssistedObject kvo, ValidationResult validationResult) {
            if (!kvo.getStringUnchecked(field).startsWith(startsWith))
                validationResult.addFieldError(field, "It's not correct!");
        }
    }
}
