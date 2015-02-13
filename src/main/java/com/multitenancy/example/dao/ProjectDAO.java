package com.multitenancy.example.dao;

import com.google.inject.Inject;
import com.multitenancy.example.core.Project;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProjectDAO extends AbstractDAO<Project> {

    @Inject
    public ProjectDAO(SessionFactory factory) {
        super(factory);
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

