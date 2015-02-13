package com.multitenancy.example.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fk.dropwizard.multitenancy.hibernate.TenantEntity;
import io.dropwizard.jackson.JsonSnakeCase;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="projects")
@FilterDef(name = "tenant", parameters = {@ParamDef(name = "tenant_id", type = "string")}, defaultCondition = ":tenant_id = tenant_id")
@Filter(name = "tenant")
@JsonSnakeCase
public class Project implements TenantEntity {
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
