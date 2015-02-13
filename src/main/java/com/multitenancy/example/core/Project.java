package com.multitenancy.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="projects")
@JsonSnakeCase
public class Project {
    enum Status {
        Started, Completed, Failed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @Column
    private Long id;

    @JsonProperty
    @Column(name="name", nullable = false)
    @NotEmpty
    private String name;

    @JsonProperty
    @Column(name="description", nullable = false)
    @NotEmpty
    private String description;

    @JsonProperty
    @Column(name="tenant_id")
    private String tenantId;

    public Project() {
    }

    public Project(Long id, String name, String description, String tenantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tenantId = tenantId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
