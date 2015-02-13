package com.multitenancy.example.dao;

import com.fk.dropwizard.multitenancy.TenantResolver;
import com.fk.dropwizard.multitenancy.hibernate.dao.AbstractMultitenantDAO;
import com.google.inject.Inject;
import com.multitenancy.example.core.Project;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProjectDAO extends AbstractMultitenantDAO<Project> {

    @Inject
    public ProjectDAO(SessionFactory factory, TenantResolver tenantResolver) {
        super(factory, tenantResolver);
    }

    public long create(Project project){
        return persist(project).getId();
    }

    public List<Project> findAll() {
        return currentSession().
                createCriteria(Project.class).
                list();
    }

    public Project findById(Long runId) {
        return (Project) currentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("id", runId)).
                uniqueResult();
    }
}

