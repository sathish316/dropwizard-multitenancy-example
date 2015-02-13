package com.multitenancy.example;

import com.fk.dropwizard.multitenancy.filter.TenantFilter;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleApplication extends Application<ExampleConfiguration> {
    private GuiceBundle<ExampleConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-multitenancy-example";
    }

    @Override
    public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
        this.guiceBundle = GuiceBundle.<ExampleConfiguration>newBuilder()
                .addModule(new ExampleModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(ExampleConfiguration.class)
                .build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(getInjector().getInstance(MigrationsBundle.class));
        bootstrap.addBundle(getInjector().getInstance(HibernateBundle.class));
    }

    @Override
    public void run(ExampleConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().getResourceConfig().getContainerRequestFilters().add(getTenantFilter());
    }

    private TenantFilter getTenantFilter() {
        return getInjector().getInstance(TenantFilter.class);
    }

    public Injector getInjector(){
        return guiceBundle.getInjector();
    }
}
