package com.inepex.example.ContactManager.entity.dao.ext;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.User_;
import com.inepex.example.ContactManager.entity.dao.UserDao;

@Singleton
public class UserDaoExt extends UserDao{

	@Inject
	public UserDaoExt(Provider<EntityManager> em) {
		super(em);
	}

	public User findByEmail(String email) {
		createDefaultSelector();
		
		sel.cq.select(sel.root);
		sel.cq.from(User.class);
		sel.cq.where(sel.cb.equal(sel.root.get(User_.email), email));
		sel.cq.distinct(true);
		
		try {
			return sel.getTypedQuery().getSingleResult();
		} catch (NoResultException ex) {
			return null;
		} catch (NonUniqueResultException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
