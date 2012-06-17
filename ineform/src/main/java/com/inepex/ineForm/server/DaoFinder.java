package com.inepex.ineForm.server;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class DaoFinder {
	
	private final Injector injector;
	private CustomDaoStore customDaoStore;
	
	@Inject
	DaoFinder(Injector injector, CustomDaoStore customDaoStore) {
		this.injector=injector;
		this.customDaoStore = customDaoStore;
	}
	
	private final Set<String> packageNamePrefixes = new TreeSet<String>();
	private final HashMap<String, Class<?>> foundClasses = new HashMap<String, Class<?>>();
	
	public void addPackageByName(String packageName) {
		packageNamePrefixes.add(packageName);
	}
	
	public String getDaoNameFormDescriptorName(String descriptor) {
		String daoJndiName = descriptor.replaceAll("Descriptor", "Dao");
		String firstLetter = daoJndiName.substring(0,1);
		daoJndiName = firstLetter.toUpperCase() + daoJndiName.substring(1);
		return daoJndiName;
	}
	
	public KVManipulatorDaoBase getDefaultDaoForDescriptor(String descriptor) {
		checkInitialized();
		String daoJndiName = getDaoNameFormDescriptorName(descriptor);
		
		Object o = lookupObject(daoJndiName);
		if (o == null){
			o = customDaoStore.getProvidersByDescriptor().get(descriptor);
		}
		
		if (o == null){
			throw new RuntimeException("Can not be found class definition for: "+daoJndiName+", you may forgot adding package name of class!");
		}
		
		return (KVManipulatorDaoBase) o;
	}
	
	public Object lookupObject(String objectName) {
		checkInitialized();
		
		//find in found classes
		Class<?> objectClass = foundClasses.get(objectName);
		
		if(objectClass==null) {
			//try to lacate class definition
			for(String packageName : packageNamePrefixes) {
				String canonName = packageName+"."+objectName;
				try {
					objectClass = Class.forName(canonName);
				} catch (ClassNotFoundException e) {
					//nothing to do
				}
				
				if(objectClass!=null) {
					foundClasses.put(objectName, objectClass);
					break;
				}
			}
			
		}
		
		if(objectClass==null) 
			return null;
		else return injector.getInstance(objectClass);
	}
	
	private void checkInitialized() throws RuntimeException {
		if (packageNamePrefixes.size() == 0 && 
			customDaoStore.getProvidersByDescriptor().size() == 0) {
			throw new RuntimeException("DaoInstancefinder not initialized." +
					" Please call for your dao packages the following: daoInstanceFinder.addPackageByName(\"com.inepex.example.daopackage\")");
		}
	}

}
