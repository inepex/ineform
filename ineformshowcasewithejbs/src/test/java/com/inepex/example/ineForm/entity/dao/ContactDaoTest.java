package com.inepex.example.ineForm.entity.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Provider;
import com.inepex.example.ineForm.EntityManagerInitializier;
import com.inepex.example.ineForm.entity.ContactTestData;
import com.inepex.example.ineForm.entity.assist.ContactAssist;
import com.inepex.example.ineForm.entity.assist.ContactCTypeRelAssist;
import com.inepex.ineForm.shared.dispatch.ActionObjectFactory;
import com.inepex.ineForm.test.DefaultIneFormClientSideTestBase;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

public class ContactDaoTest extends DefaultIneFormClientSideTestBase{

	EntityManager em = null;
	EntityTransaction et = null;
	ContactTestData data;
	
	ContactDao dao;
	DescriptorStore descStore;
	
	@Before
	public void getEntityManager() {
		descStore = getDefaultInjector().getInstance(DescriptorStore.class);
		new ContactAssist(descStore).registerDescriptors();
		new ContactCTypeRelAssist(descStore).registerDescriptors();
		
		em = EntityManagerInitializier.initInDropCreateMode();
		et = em.getTransaction();
		et.begin();
		
		data = new ContactTestData(em, et);
		data.addDefaultContactTypes();
		data.addDefaultNationalities();
		data.addContact();
		
		em.flush();
		et.commit();

		data.detach();
		
		dao = new ContactDao(new Provider<EntityManager>() {
			
			@Override
			public EntityManager get() {
				return em;
			}
		}, new ActionObjectFactory(), new AssistedObjectHandlerFactory(descStore), descStore);
	}
	
//	@Test
	public void testSequence() {
		createNewRelationTest();
		modifyTest();
		removeTest();
	}
	
	void modifyTest(){
//		
//		RelationList relationList = new RelationList(
//				descStore
//				, ContactCTypeRelConsts.descriptorName
//				, true);
//		
//		List<Relation> relations = new ContactCTypeRelMapper(descStore).toRelationList(
//				data.contact.getContactTypes(), true);
//		
//		relationList.setRelations(relations);
//		relationList.move(relations.get(0), 1);
//		
//		List<Relation> changes = relationList.getChanges();
//		
//		ContactKVO kvo = new ContactKVO();
//		kvo.setId(data.contact.getId());
//		IneList contactTypes = new IneList(changes);
//		kvo.setContactTypes(contactTypes);
//		
//		et.begin();
//		dao.doCreateOrEdit(kvo);
//		em.flush();
//		et.commit();
		
	}
	
	void removeTest(){
		et.begin();
		dao.removeTrans(data.contact.getId());
		et.commit();
	}
	
	void createNewRelationTest(){
		
//		ContactKVO kvo = new ContactKVO();
//		kvo.setId(data.contact.getId());
//		
//		List<Relation> changes =  new ArrayList<Relation>();
//		Relation newRel = new Relation();
//		newRel.setId(IFConsts.NEW_ITEM_ID);
//		ContactCTypeRelKVO contactCTypeRelKVO = new ContactCTypeRelKVO();
//		contactCTypeRelKVO.setId(data.contactTypes[0].getId());
//		newRel.setKvo(contactCTypeRelKVO);
//		
//		IneList contactTypes = new IneList(changes);
//		kvo.setContactTypes(contactTypes);
//		
//		et.begin();
//		ContactMapper mapper = new ContactMapper();
//		ContactKVO result = mapper.entityToKvo(dao.doCreateOrEdit(kvo));
//		em.flush();
//		
//		em.detach(data.contact);
//		
//		et.commit();
//		
//		et.begin();
//		
//		assertNotSame(IFConsts.NEW_ITEM_ID.longValue(), result.getContactTypes().getRelationList().get(0).getId().longValue());
//		assertNotSame(IFConsts.NEW_ITEM_ID.longValue(), result.getContactTypes().getRelationList().get(1).getId().longValue());
//	
//		ObjectListAction searchAction = new ObjectListAction(ContactKVO.descriptorName);
//		searchAction.setQueryResultCount(false);
//		searchAction.setNumMaxResult(10);
//		searchAction.setFirstResult(0);
//		ObjectListResult searchRes = dao.search(searchAction);
//		assertEquals(1, searchRes.getList().size());
//		
//		assertNotSame(IFConsts.NEW_ITEM_ID.longValue(), searchRes.getList().get(0)
//				.getList(ContactKVO.k_contactTypes)
//				.getRelationList().get(0).getId());
//		
//		assertNotSame(IFConsts.NEW_ITEM_ID.longValue(), searchRes.getList().get(0)
//				.getList(ContactKVO.k_contactTypes)
//				.getRelationList().get(1).getId());
//		
//		assertNotSame(IFConsts.NEW_ITEM_ID.longValue(), data.contact.getContactTypes().get(0).getId().longValue());
//		assertNotSame(IFConsts.NEW_ITEM_ID.longValue(), data.contact.getContactTypes().get(1).getId().longValue());
//		
//		
//		em.flush();
//		et.commit();		
	}
	
	
}
