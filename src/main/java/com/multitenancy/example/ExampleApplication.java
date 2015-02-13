package com.multitenancy.example;

import com.multitenancy.example.dao.ProjectDAO;
import com.multitenancy.example.resource.ProjectResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleApplication extends Application<ExampleConfiguration> {
    private MigrationsBundle<ExampleConfiguration> migrations = new MigrationsBundle<ExampleConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private final HibernateBundle<ExampleConfiguration> hibernate = new ScanningHibernateBundle<ExampleConfiguration>("com.multitenancy.example.core") {
        @Override
        public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-multitenancy-example";
    }

    @Override
    public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
        bootstrap.addBundle(migrations);
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(ExampleConfiguration configuration, Environment environment) throws Exception {
        ProjectDAO projectDAO = new ProjectDAO(hibernate.getSessionFactory());
        environment.jersey().register(new ProjectResource(projectDAO));
    }
}
