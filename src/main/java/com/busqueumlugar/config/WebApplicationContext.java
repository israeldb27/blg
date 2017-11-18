package com.busqueumlugar.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import ml.rugal.sshcommon.springmvc.method.annotation.FormModelMethodArgumentResolver;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.core.io.Resource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
@EnableWebMvc
@PropertySource(
        {
            "classpath:messages_pt_BR.properties",
            "classpath:messages.properties",
            "classpath:messages_en_EN.properties",
            "classpath:jdbc.properties",
            "classpath:hibernate.properties", 
            "classpath:sdk_config.properties"            
        })
@ComponentScan(basePackages =
{
    "com.busqueumlugar"
})

public class WebApplicationContext extends WebMvcConfigurerAdapter
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

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        argumentResolvers.add(new FormModelMethodArgumentResolver());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        configurer.favorPathExtension(false).favorParameter(false);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        messageConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(messageConverter);
    }
    
    @Bean
    public MultipartResolver multipartResolver() {
      return new CommonsMultipartResolver();
    }

    @Bean
    public InternalResourceViewResolver viewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");        
        return resolver;
    }
    @Bean
    public AbstractHandlerMapping defaultAnnotationHandlerMapping()
    {
        RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
        mapping.setUseSuffixPatternMatch(false);
        return mapping;
    }
    
 // Only needed if we are using @Value and ${...} when referencing properties
 	@Bean
 	public static PropertySourcesPlaceholderConfigurer properties() {
 		PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
 		Resource[] resources = new ClassPathResource[] { 
 				new ClassPathResource("messages_pt_BR.properties"), new ClassPathResource("sdk_config.properties"), new ClassPathResource("messages.properties"), 
 				new ClassPathResource("messages_en_EN.properties")};
 		propertySources.setLocations(resources);
 		propertySources.setIgnoreUnresolvablePlaceholders(true);
 		return propertySources;
 	}
 	
 	// Provides internationalization of messages
 	@Bean
 	public ResourceBundleMessageSource messageSource() {
 		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
 		source.setBasename("messages");
 		return source;
 	}
 	

 	//<editor-fold defaultstate="collapsed" desc="HikariCP Datasoure Configuration" >
 	  @Bean(destroyMethod = "close")
 	    @Autowired
 	    public DataSource dataSource()
 		{
 			BasicDataSource dataSource = new BasicDataSource();
 			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
 			dataSource.setUrl("jdbc:mysql://localhost:3306/home");		
 			dataSource.setUsername("root");
 			dataSource.setPassword("admin");
 			return dataSource;
 		}

 	      
	/*    @Bean(destroyMethod = "close")
 	    @Autowired
 	    public DataSource dataSource()
 		{
 			BasicDataSource dataSource = new BasicDataSource();
 			dataSource.setDriverClassName("com.mysql.jdbc.Driver"); 	
 			dataSource.setUrl("jdbc:mysql://159.203.109.104:3306/home");		
 			dataSource.setUsername("root");
 			dataSource.setPassword("flamengo01"); 			
 			return dataSource;
 		}
 */
 	  
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
 	    //    hibernateProperties.put(hibernate_connection_provider_class, env.getProperty(hibernate_connection_provider_class));
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
