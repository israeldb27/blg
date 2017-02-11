package com.busqueumlugar.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@PropertySource(
        {
            "classpath:jdbc.properties",
            "classpath:hibernate.properties"            
        })
@ComponentScan(value = "com.busqueumlugar")
@Import(value = { LoginSecurityConfig.class })
public class ApplicationContext
{

    public static final String hibernate_connection_autocommit = "hibernate.connection.autocommit";

    public static final String hibernate_format_sql = "hibernate.format_sql";

    public static final String hibernate_hbm2ddl_auto = "hibernate.hbm2ddl.auto";

    public static final String hibernate_show_sql = "hibernate.show_sql";

    public static final String hibernate_current_session_context_class = "hibernate.current_session_context_class";

    public static final String hibernate_dialect = "hibernate.dialect";

    public static final String package_to_scan = "com.busqueumlugar.model";

    @Autowired
    private Environment env;

//<editor-fold defaultstate="collapsed" desc="HikariCP Datasoure Configuration" >
    @Bean(destroyMethod = "close")
    @Autowired
    public DataSource dataSource()
	{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/home");
		//dataSource.setUrl("jdbc:mysql://162.243.62.122:3306/home");		
		//dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/home");		
		dataSource.setUsername("root");
		dataSource.setPassword("admin");
		//dataSource.setPassword("17891888");
		return dataSource;
	}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Hibernate Session factory configuration">
    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource datasouce)
    {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(datasouce);
        sessionFactory.setPackagesToScan(package_to_scan);
        sessionFactory.setHibernateProperties(hibernateProperties());        
        return sessionFactory;
    }
    
    /*@Bean
    @Autowired
    public SessionFactory sessionFactory(){
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan(package_to_scan);
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		return (SessionFactory) sessionFactoryBean;		
	}*/
    
    private Properties hibernateProperties()
    {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put(hibernate_dialect, env.getProperty(hibernate_dialect));
        hibernateProperties
                .put(hibernate_current_session_context_class, env.getProperty(hibernate_current_session_context_class));
        hibernateProperties.put(hibernate_connection_autocommit, env.getProperty(hibernate_connection_autocommit));
        hibernateProperties.put(hibernate_format_sql, env.getProperty(hibernate_format_sql));
        hibernateProperties.put(hibernate_hbm2ddl_auto, env.getProperty(hibernate_hbm2ddl_auto));
        hibernateProperties.put(hibernate_show_sql, env.getProperty(hibernate_show_sql));
//        hibernateProperties.put(hibernate_connection_provider_class, env.getProperty(hibernate_connection_provider_class));
        return hibernateProperties;

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Hibernate transaction manager">
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
    {
    	HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory);
        return txManager;
    }
//</editor-fold>
    
   
}
