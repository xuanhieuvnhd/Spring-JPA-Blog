package com.codegym.repository.impl;

import com.codegym.model.Blog;
import com.codegym.repository.BlogRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class BlogRepositoryImpl implements BlogRepository {
    public static final String FIND_ALL = "select c from Blog c";
    public static final String FIND_BY_ID = "select c from Blog c where c.id=:id";
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Blog> findAll() {
        TypedQuery<Blog> query = em.createQuery(FIND_ALL, Blog.class);
        return query.getResultList();
    }

    @Override
    public Blog findById(Long id) {
        TypedQuery<Blog> query = em.createQuery(FIND_BY_ID, Blog.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
        return null;}
    }

    @Override
    public void save(Blog model) {
        if (model.getId() != null) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public void remove(Long id) {
        Blog blog = findById(id);
        if (blog !=null) {
            em.remove(blog);
        }
    }
}

