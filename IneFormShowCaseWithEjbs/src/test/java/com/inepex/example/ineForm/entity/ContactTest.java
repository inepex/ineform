package com.inepex.example.ineForm.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inepex.example.ineForm.EntityManagerInitializier;


public class ContactTest {
	
	EntityManager em = null;
	EntityTransaction et = null;
	
	ContactTestData data;
	
	@Before
	public void getEntityManager() {
		em = EntityManagerInitializier.initInDropCreateMode();
		et = em.getTransaction();
		et.begin();
		
		data = new ContactTestData(em, et);
		data.addDefaultContactTypes();
		data.addDefaultNationalities();
		
		em.flush();
		et.commit();
	}
	

	
	@Test
	public void cascadeTest(){
		
		et = em.getTransaction();
		et.begin();

		Contact c = testCascadePersist();
		testCascadeRefresh(c);
		testCascadeListItemRemove(c.getId());
		testListUpdate(c.getId());
		testModifyContactTypeOnContactTypeRel(c.getId());
//		testCascadeRemove(c.getId());
		
		em.flush();
		et.commit();		
		
	}
	
	private Contact testCascadePersist() {
		Contact c = new Contact(0L);
		
		c.setFirstName("FN");
		c.setLastName("LN");
		
		c.contactTypes = new ArrayList<ContactCTypeRel>();
		ContactCTypeRel ctrel = new ContactCTypeRel(0L
									, c 
									, new ContactType(data.contactTypes[0].getId())
									, 0L); 
		c.contactTypes.add(ctrel);
		
		ctrel = new ContactCTypeRel(0L
				, c 
				, new ContactType(data.contactTypes[1].getId())
				, 0L);
		c.contactTypes.add(ctrel);

		em.persist(c);
		em.flush();
			
		assertNotSame(c.getId(), 0L);
		assertNotSame(ctrel.getId(), 0L);
		
		return c;
	}
	
	private void testModifyContactTypeOnContactTypeRel(Long id){
		Contact c = em.find(Contact.class, id);
		c.getContactTypes().get(0).setContactType(new ContactType(data.contactTypes[2].getId()));
		
		em.merge(c);
		em.flush();
	}
	
	private void testCascadeRefresh(Contact c) {
		em.refresh(c);
//		assertEquals("Barát", c.getContactTypes().get(0).getContactType().getTypeName());
		assertEquals(c.getContactTypes().size(), 2);
	}
	
	private void testCascadeListItemRemove(Long contactId) {
		Contact c = em.find(Contact.class, contactId);
		c.contactTypes.remove(0);
		em.persist(c);
		em.flush();

		String hql = "select ctrel from ContactCTypeRel ctrel";
		List<ContactCTypeRel> crels = em.createQuery(hql, ContactCTypeRel.class).getResultList();
		
		assertEquals(1 , crels.size());
		
	}
	
	private void testCascadeRemove(Long contactId){
		Contact c = em.find(Contact.class, contactId);
		em.remove(c);
		em.flush();
	}
	
	private void testListUpdate(Long contactId){
		Contact c = em.find(Contact.class, contactId);
		
		c.contactTypes.get(0).setOrderNum(1L);
		
		em.persist(c);
		em.flush();
		
		String hql = "select ctrel from ContactCTypeRel ctrel";
		List<ContactCTypeRel> crels = em.createQuery(hql, ContactCTypeRel.class).getResultList();
		
		assertEquals(1 , crels.size());
		assertEquals(1L , crels.get(0).getOrderNum().longValue());
	}
	
	@After
	public void dispose() {
		em.close();
	}
	
}
