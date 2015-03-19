package com.inepex.translatorapp.server.entity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.IneList;
import com.inepex.ineom.shared.Relation;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.translatorapp.server.entity.Module;
import com.inepex.translatorapp.server.entity.ModuleLang;
import com.inepex.translatorapp.server.entity.ModuleRow;
import com.inepex.translatorapp.shared.kvo.ModuleConsts;
import com.inepex.translatorapp.shared.kvo.ModuleHandlerFactory;
import com.inepex.translatorapp.shared.kvo.ModuleHandlerFactory.ModuleHandler;

public class ModuleMapper extends BaseMapper<Module>{
	
	private final DescriptorStore descriptorStore;
	private final ModuleHandlerFactory handlerFactory;
	
	@Inject
	public ModuleMapper(DescriptorStore descriptorStore) {
		this.descriptorStore=descriptorStore;
		this.handlerFactory=new ModuleHandlerFactory(descriptorStore);
	}

	@Override
	public Module kvoToEntity(AssistedObject fromKvo, Module to) {
		ModuleHandler fromHandler = handlerFactory.createHandler(fromKvo);
		
		if (to == null)
			to = new Module();
		if (!fromHandler.isNew()) 
			to.setId(fromHandler.getId());
		if (fromHandler.containsString(ModuleConsts.k_name)) 
			to.setName(fromHandler.getName());
		if (fromHandler.containsList(ModuleConsts.k_rows)) {
		//TODO
		//TODO saving skipped, because saving a IneList (which not contains KVO-s) causes removing!!!
		}
		if (fromHandler.containsList(ModuleConsts.k_langs)) {
			if (to.getLangs() == null)
				to.setLangs(new ArrayList<ModuleLang>());

    		Map<Long,ModuleLang> origItems = new HashMap<Long, ModuleLang>();
			for (ModuleLang item : to.getLangs()) {
				origItems.put(item.getId(), item);
			}
			
			ModuleLangMapper mapper = new ModuleLangMapper(descriptorStore);
			for (Relation rel : fromHandler.getLangs().getRelationList()) {
				if (rel == null)
					continue;
				if (rel.getId().equals(IFConsts.NEW_ITEM_ID)) { // create new item
					ModuleLang entity = new ModuleLang(IFConsts.NEW_ITEM_ID);
					mapper.kvoToEntity(rel.getKvo(), entity);
					entity.setModule(to);
					to.getLangs().add(entity);
				} else {
					ModuleLang origItem = origItems.get(rel.getId());
					if (rel.getKvo() == null) { 			    // delete item
						to.getLangs().remove(origItem);
					} else {									// edit item
						mapper.kvoToEntity(rel.getKvo(), origItem);
					}
				}
			}
		}

		/*hc:customToEntity*/
		//custom mappings to Entity comes here.
		/*hc*/
		
		return to;
	}
	
	@Override
	public AssistedObject entityToKvo(Module entity) {
		ModuleHandler handler = handlerFactory.createHandler();
	
		if (entity.getId() != null) 
			handler.setId(entity.getId());
		if (entity.getName() != null && !"".equals(entity.getName())) 
			handler.setName(entity.getName());
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getRows() != null)
    			for (ModuleRow item : entity.getRows()) {
    				relationList.add(new ModuleRowMapper(descriptorStore).toRelation(item, false));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setRows(ineList);
    		}
		}
		{
    		IneList ineList = new IneList();
    		List<Relation> relationList = new ArrayList<Relation>();
    		if (entity.getLangs() != null)
    			for (ModuleLang item : entity.getLangs()) {
    				relationList.add(new ModuleLangMapper(descriptorStore).toRelation(item, true));
    			}
    		if (relationList.size() > 0) {
    			ineList.setRelationList(relationList);
    			handler.setLangs(ineList);
    		}
		}

		/*hc:customToKvo*/
		//custom mappings to Kvo comes here. Eg. when some properties should not be sent to the UI
		/*hc*/

		return handler.getAssistedObject();
	}
	
	@Override
	public Relation toRelation(Module entity, boolean includeKvo){
		if (entity == null)
			return null;
		return new Relation(entity.getId(), entity.toString(), includeKvo ? entityToKvo(entity) : null);
	}
	
}