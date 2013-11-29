package com.inepex.ineom.shared.assistedobject;

import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.PropHandler;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.RelationFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

public class AssistedObjectBuilderBase <T>{

	protected AssistedObjectHandler ao;
	
	protected final DescriptorStore descStore;
	
	protected final AssistedObjectHandlerFactory aoHandlerFactory;
	
	protected final PropHandler propHandler;
	
	protected final T object;

	@SuppressWarnings("unchecked")
	public AssistedObjectBuilderBase(DescriptorStore descStore, AssistedObjectHandlerFactory aoHandlerFactory,
			PropHandler propHandler) {
		super();
		this.descStore = descStore;
		this.aoHandlerFactory = aoHandlerFactory;
		this.propHandler = propHandler;
		object = (T)this;
	}

	public T set(String key, Boolean value){
		ao.set(key, value);
		return object;
	}

	public T set(String key, Double value){
		ao.set(key, value);
		return object;
	}

	public T set(String key, IneList value){
		ao.set(key, value);
		return object;
	}

	public T set(String key, Long value) {
		ao.set(key, value);
		return object;
	}

	public T set(String key, Relation value){
		ao.set(key, value);
		return object;
	}
	
	public T set(String key, String relationKey, String value){
		checkAndCreateRelation(key);		
		aoHandlerFactory.createHandler(ao.getRelation(key).getKvo()).set(relationKey, value);
		return object;
	}
	
	public T set(String key, String relationKey, Long value){
		checkAndCreateRelation(key);		
		aoHandlerFactory.createHandler(ao.getRelation(key).getKvo()).set(relationKey, value);
		if (relationKey.equals(IFConsts.KEY_ID)) ao.getRelation(key).setId(value);
		return object;
	}
	
	public T set(String key, String relationKey, Boolean value){
		checkAndCreateRelation(key);		
		aoHandlerFactory.createHandler(ao.getRelation(key).getKvo()).set(relationKey, value);
		return object;
	}
	
	public T set(String key, String relationKey, Double value){
		checkAndCreateRelation(key);		
		aoHandlerFactory.createHandler(ao.getRelation(key).getKvo()).set(relationKey, value);
		return object;
	}
	
	private void checkAndCreateRelation(String key){
		if (ao.getRelation(key) == null){
			FDesc fDesc = descStore.getOD(ao.getDescriptorName()).getField(key);
			String relDesc = ((RelationFDesc)fDesc).getRelatedDescriptorName();
			Relation rel = new Relation(aoHandlerFactory.createHandler(relDesc).getAssistedObject());
			ao.set(key, rel);
		}
	}
	
	public T set(String key, String value){
		ao.set(key, value);
		return object;
	}
	
	public T prop(String group, String key, String value){
		propHandler.setProp(ao.getAssistedObject(), group, key, value);
		return object;
	}
}
