package com.inepex.example.ContactManager.entity.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.inepex.example.ContactManager.entity.User;
import com.inepex.example.ContactManager.entity.User_;
import com.inepex.example.ContactManager.entity.dao.query.UserQuery;
import com.inepex.example.ContactManager.entity.mapper.UserMapper;
import com.inepex.ineForm.server.BaseDao;
import com.inepex.ineForm.server.BaseQuery;
import com.inepex.ineForm.server.CriteriaSelector;
import com.inepex.ineForm.shared.BaseMapper;
import com.inepex.ineForm.shared.dispatch.ManipulationObjectFactory;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;

/**
 * Just generated once, don't need to regenerate after modifying attributes!
 * 
 * To customize persist, merge or remove behaviour override persist(E), merge(E)
 * or remove(E). (Don't forget to call super.persist, super.merge ...)
 * 
 */
@Singleton
public class UserDao extends BaseDao<User> {

    private final DescriptorStore descStore;

    @Inject
    public UserDao(
        Provider<EntityManager> em,
        ManipulationObjectFactory objectFactory,
        AssistedObjectHandlerFactory handlerFactory,
        DescriptorStore descStore) {
        super(em, objectFactory, handlerFactory);
        this.descStore = descStore;
    }

    @Override
    public BaseQuery<User> getQuery() {
        return new UserQuery(descStore);
    }

    @Override
    public BaseMapper<User> getMapper() {
        return new UserMapper(descStore);
    }

    @Override
    public CriteriaSelector<User, User> getSelector() {
        return new CriteriaSelector<User, User>(em, getQuery(), User.class, User.class);
    }

    @Override
    public CriteriaSelector<Long, User> getCountSelector() {
        return new CriteriaSelector<Long, User>(em, getQuery(), Long.class, User.class);
    }

    @Override
    public Class<User> getClazz() {
        return User.class;
    }

    @Override
    public User newInstance() {
        return new User();
    }

    public User findByEmail(String email) {
        CriteriaSelector<User, User> sel = getSelector();

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