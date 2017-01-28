package com.busqueumlugar.config;

import java.util.ArrayList;
import java.util.List;

import ml.rugal.sshcommon.springmvc.method.annotation.FormModelMethodArgumentResolver;

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

@Configuration
@EnableWebMvc
@PropertySource(
        {
            "classpath:messages_pt_BR.properties",
            "classpath:messages.properties",
            "classpath:messages_en_EN.properties",
            "classpath:sdk_config.properties"            
        })
@ComponentScan(basePackages =
{
    "com.busqueumlugar"
})
@Import(value = { LoginSecurityConfig.class })
public class WebApplicationContext extends WebMvcConfigurerAdapter
{

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
}
