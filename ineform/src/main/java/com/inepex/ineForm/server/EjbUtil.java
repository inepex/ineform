package com.inepex.ineForm.server;

import javax.naming.InitialContext;
import javax.naming.NamingException;


public class EjbUtil {
		
	static EjbUtil _instance;
	
	public static EjbUtil get() {
		if (_instance == null)
			_instance = new EjbUtil();
		
		return _instance;
	}
	
	private static String lookupBase = null;

	public static void setTestInstance(EjbUtil testIntance) {
		_instance = testIntance;
	}
	
	public void Init(String lookupBase) {
		EjbUtil.lookupBase =lookupBase;
	}
	
	public String getDaoNameFormDescriptorName(String descriptor) {
		String daoJndiName = descriptor.replaceAll("Descriptor", "Dao");
		String firstLetter = daoJndiName.substring(0,1);
		daoJndiName = firstLetter.toUpperCase() + daoJndiName.substring(1);
		return daoJndiName;
	}
	
	public KVManipulatorDaoBase getDefaultDaoForDescriptor(String descriptor) throws NamingException {
		checkInitialized();
		String daoJndiName = getDaoNameFormDescriptorName(descriptor);
		return (KVManipulatorDaoBase) lookupObject(daoJndiName);
	}
	
	public Object lookupObject(String objectName) throws NamingException {
		checkInitialized();
		return InitialContext.doLookup(lookupBase + objectName);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Object> T lookupObject(Class<T> clazz) throws NamingException {
		return (T) lookupObject(clazz.getSimpleName());
	}
	
	private void checkInitialized() throws RuntimeException {
		if (lookupBase == null) {
			throw new RuntimeException("EjbUtil not initialized. Please call EjbUtil.Init(\"java:global/YourEarProjectName/YourEjbProjectName/\") ");
		}
	}

}
