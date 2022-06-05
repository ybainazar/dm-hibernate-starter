package com.hibernate.dao;

import com.hibernate.entity.BaseEntity;
import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    @Getter
    private final EntityManager entityManager;
    private final Class<E> clazz;

    @Override
    public E save(E entity) {
//        var session = sessionFactory.getCurrentSession();
//        session.save(entity);

        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(K id) {
//        var session = sessionFactory.getCurrentSession();
//        session.delete(id);
//        session.flush();

        entityManager.remove(id);
        entityManager.flush();
    }

    @Override
    public void update(E entity) {
//        var session = sessionFactory.getCurrentSession();
        entityManager.merge(entity);
    }

    @Override
    public Optional<E> findById(K id, Map<String, Object> properties) {
//        var session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(entityManager.find(clazz, id, properties));
    }

    @Override
    public List<E> findAll() {
//        var session = sessionFactory.getCurrentSession();
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(clazz);
        criteria.from(clazz);

        return entityManager.createQuery(criteria)
                .getResultList();
    }

}
