package com.multitenancy.example.resource;

import com.codahale.metrics.annotation.Timed;
import com.multitenancy.example.core.Project;
import com.multitenancy.example.dao.ProjectDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {
    private final ProjectDAO dao;

    public ProjectResource(ProjectDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/")
    @Timed
    @UnitOfWork
    public List<Project> getAll(){
        return dao.findAll();
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Project get(@PathParam("id") LongParam runId){
        Project project = dao.findById(runId.get());
        if(project == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return project;
    }

    @POST
    @Path("/")
    @Timed
    @UnitOfWork
    public Response create(@Valid Project project){
        Long id = dao.create(project);
        return Response.created(uriFor(project)).build();
    }

    private URI uriFor(Project project) {
        return URI.create(String.format("/projects/%d", project.getId()));
    }

}
