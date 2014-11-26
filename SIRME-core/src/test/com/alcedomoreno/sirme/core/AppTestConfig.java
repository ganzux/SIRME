package com.alcedomoreno.sirme.core;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.alcedomoreno.sirme.core.dao" })
public class AppTestConfig {
	
	@Bean
    public BasicDataSource dataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) throws IOException {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public DatabasePopulator databasePopulator() {
    	
    	ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    	DefaultResourceLoader resource = new DefaultResourceLoader(); 
    	databasePopulator.setScripts(new Resource[]{resource.getResource("classpath:test.sql")});
    	
    	return databasePopulator;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
    	
    	DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    	dataSourceInitializer.setDataSource(dataSource());
    	dataSourceInitializer.setDatabasePopulator(databasePopulator());
    	return dataSourceInitializer;
    }

    @SuppressWarnings("serial")
	final Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
                setProperty("hibernate.show_sql", "true");
                setProperty("hibernate.hbm2ddl.auto", "create");
                setProperty("hibernate.connection.pool_size", "4");
                
            }
        };
    }
    
    @Bean
    public AnnotationSessionFactoryBean sessionFactory()  {
    	AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.alcedomoreno.sirme.core.data",
				 "com.alcedomoreno.sirme.core.dao",
				 "com.alcedomoreno.sirme.core.util" });
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }
}
