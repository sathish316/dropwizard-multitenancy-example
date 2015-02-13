package com.multitenancy.example;

import com.fk.dropwizard.multitenancy.TenantResolver;
import com.fk.dropwizard.multitenancy.hibernate.bundle.MultitenantHibernateBundle;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import org.hibernate.SessionFactory;

public class ExampleModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public MigrationsBundle provideMigrationsBundle(){
        return new MigrationsBundle<ExampleConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Provides
    @Singleton
    public HibernateBundle provideHibernateBundle(TenantResolver tenantResolver){
        return new MultitenantHibernateBundle<ExampleConfiguration>("com.multitenancy.example", tenantResolver) {
            @Override
            public DataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory(HibernateBundle bundle){
        return bundle.getSessionFactory();
    }
}

