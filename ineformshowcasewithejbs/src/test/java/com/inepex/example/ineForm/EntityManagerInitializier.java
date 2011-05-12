package com.inepex.example.ineForm;

import static org.eclipse.persistence.config.PersistenceUnitProperties.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;

public class EntityManagerInitializier {
	
	private final static String PROJECT_NAME = "IneFormShowCaseWithEjbs";

	private static Map<String, String> getDbProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		 
	    // Ensure RESOURCE_LOCAL transactions is used.
	    properties.put(TRANSACTION_TYPE,
	        PersistenceUnitTransactionType.RESOURCE_LOCAL.name());
	 
	    // Configure the internal EclipseLink connection pool
	    properties.put(JDBC_DRIVER, "org.postgresql.Driver");
//	    properties.put(JDBC_URL, "jdbc:postgresql://localhost/IneFormTest");
//	    properties.put(JDBC_USER, "postgres");
//	    properties.put(JDBC_PASSWORD, "ine123pex");
	    properties.put(JDBC_URL, "jdbc:postgresql://localhost/IneFormTest");
	    properties.put(JDBC_USER, "IneFormTest");
	    properties.put(JDBC_PASSWORD, "IneFormTest");

	 
	    // Configure logging. FINE ensures all SQL is shown
	    properties.put(LOGGING_LEVEL, "FINE");
	    properties.put(LOGGING_TIMESTAMP, "false");
	    properties.put(LOGGING_THREAD, "false");
	    properties.put(LOGGING_SESSION, "false");
	 
	    // Ensure that no server-platform is configured
	    properties.put(TARGET_SERVER, TargetServer.None);
	    
	    //give the package name of metaentities
	    properties.put("eclipselink.canonicalmodel.subpackage", "metaentity");
	    return properties;		
	}
	
	public static EntityManager initSimple(){
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory(PROJECT_NAME
	    		, getDbProperties());
	    EntityManager em = emf.createEntityManager();
	    return em;
	}
	
	public static EntityManager initInDropCreateMode(){
		Map<String, String> properties = getDbProperties();

	    properties.put(DDL_GENERATION, DROP_AND_CREATE);
	    properties.put(DDL_GENERATION_MODE, DDL_DATABASE_GENERATION);
	 	    
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory(PROJECT_NAME, properties);
	    EntityManager em = emf.createEntityManager();
	    return em;
	}
}
