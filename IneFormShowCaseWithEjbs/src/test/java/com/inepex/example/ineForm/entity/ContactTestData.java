package com.inepex.example.ineForm.entity;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ContactTestData {
	EntityManager em = null;
	EntityTransaction et = null;
	
	public ContactType[] contactTypes = new ContactType[4];
	public Nationality[] nationalities = new Nationality[4];
	
	public Contact contact;

	public ContactTestData(EntityManager em, EntityTransaction et) {
		this.em = em;
		this.et = et;
	}
	
	public void addDefaultContactTypes() {
		ContactType ct = null;

		ct = new ContactType(0L);
		ct.setTypeName("Barát");
		ct.setDescription("Olyan ember akire bármikor számíthatsz");
		em.persist(ct);
		em.flush();
		contactTypes[0] = ct;

		ct = new ContactType(0L);
		ct.setTypeName("Ellenség");
		ct.setDescription("Rosszat akar neked");
		em.persist(ct);
		em.flush();
		contactTypes[1] = ct;

		ct = new ContactType(0L);
		ct.setTypeName("Ismeretlen");
		ct.setDescription("Semmi sem biztos");
		em.persist(ct);
		em.flush();
		contactTypes[2] = ct;

		ct = new ContactType(0L);
		ct.setTypeName("Üzleti partner");
		ct.setDescription("A barát és az ellenség között félúton");
		em.persist(ct);
		em.flush();
		contactTypes[3] = ct;
	}
	
	public void addDefaultNationalities() {
		Nationality nt = null;

		nt = new Nationality(0L);
		nt.setName("Magyar");
		nt.setDescription("Sírva vígad");
		em.persist(nt);
		nationalities[0] = nt;

		nt = new Nationality(0L);
		nt.setName("Angol");
		nt.setDescription("Csúnyák a nők");
		em.persist(nt);
		nationalities[0] = nt;

		nt = new Nationality(0L);
		nt.setName("Francia");
		nt.setDescription("Csigazabáló");
		em.persist(nt);
		nationalities[0] = nt;

		nt = new Nationality(0L);
		nt.setName("Német");
		nt.setDescription("Merev, sörivó");
		em.persist(nt);
		nationalities[0] = nt;
	}	
	
	public void addContact(){
		contact = new Contact();
		contact.setId(0L);
		contact.setFirstName("Péter");
		contact.setLastName("Pál");
		
		ContactCTypeRel ctrel = new ContactCTypeRel(0L
				, contact
				, contactTypes[0]
				, 0L);
		
		ContactCTypeRel ctrel2 = new ContactCTypeRel(0L
				, contact
				, contactTypes[1]
				, 1L); 
		contact.setContactTypes(new ArrayList<ContactCTypeRel>());
		contact.getContactTypes().add(ctrel);
		contact.getContactTypes().add(ctrel2);
		em.persist(contact);
		em.flush();
	}

	public void detach() {
		em.detach(contact);
	}
	
}
