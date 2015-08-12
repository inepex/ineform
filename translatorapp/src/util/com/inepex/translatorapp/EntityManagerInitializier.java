package com.inepex.translatorapp;

import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_DATABASE_GENERATION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION_MODE;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DROP_AND_CREATE;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TRANSACTION_TYPE;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;

public class EntityManagerInitializier {

    private final static String PROJECT_NAME = "translatorapp";

    private static Map<String, String> getDbProperties() {
        Map<String, String> properties = new HashMap<String, String>();

        // Ensure RESOURCE_LOCAL transactions is used.
        properties.put(TRANSACTION_TYPE, PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

        // Ensure that no server-platform is configured
        properties.put(TARGET_SERVER, TargetServer.None);

        // give the package name of metaentities
        properties.put("eclipselink.canonicalmodel.subpackage", "metaentity");
        return properties;
    }

    public static EntityManager initSimple() {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PROJECT_NAME, getDbProperties());
        EntityManager em = emf.createEntityManager();
        return em;
    }

    public static EntityManager initInDropCreateMode() {
        Map<String, String> properties = getDbProperties();

        properties.put(DDL_GENERATION, DROP_AND_CREATE);
        properties.put(DDL_GENERATION_MODE, DDL_DATABASE_GENERATION);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PROJECT_NAME, properties);
        EntityManager em = emf.createEntityManager();
        return em;
    }

    public static void main(String[] args) {
        initInDropCreateMode();
    }
}
