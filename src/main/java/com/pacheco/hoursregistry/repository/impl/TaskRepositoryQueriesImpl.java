package com.pacheco.hoursregistry.repository.impl;

import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.repository.TaskRepository;
import com.pacheco.hoursregistry.repository.TaskRepositoryQueries;
import com.pacheco.hoursregistry.util.AuthorizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TaskRepositoryQueriesImpl implements TaskRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private TaskRepository taskRepository;

    @Autowired
    private AuthorizationUtil auth;

    @Override
    public List<Task> findByQuery(Boolean done) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);

        criteria.where(builder.equal(root.get("user").get("username"), auth.currentUsername()));

        if (done != null) {
            criteria.where(builder.equal(root.get("done"), done));
        }

        TypedQuery<Task> query = manager.createQuery(criteria);
        return query.getResultList();
    }
}
