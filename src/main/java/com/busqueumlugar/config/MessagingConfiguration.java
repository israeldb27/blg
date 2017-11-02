package com.busqueumlugar.config;

import java.util.Arrays;

import javax.jms.ConnectionFactory;
 


import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import com.busqueumlugar.controller.ImovelController;
import com.busqueumlugar.messaging.MessageReceiver;
 

 
@Configuration
public class MessagingConfiguration {
 
	 private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
	// private static final String DEFAULT_BROKER_URL = "tcp://138.197.78.218:61616"; 

	
	
    private static final String ORDER_QUEUE = "order-queue";
    private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";
    
    private static final Logger log = LoggerFactory.getLogger(MessagingConfiguration.class);
     
    @Autowired
    MessageReceiver messageReceiver;
     
    @Bean
    public ConnectionFactory connectionFactory(){
    	log.info("MessagingConfiguration - connectionFactory() - inicio");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
        connectionFactory.setTrustedPackages(Arrays.asList("com.busqueumlugar"));      
        log.info("MessagingConfiguration - connectionFactory() - fim");
        return connectionFactory;
    }
    /*
     * Optionally you can use cached connection factory if performance is a big concern.
     */
 
    @Bean
    public ConnectionFactory cachingConnectionFactory(){
    	log.info("MessagingConfiguration - cachingConnectionFactory() - inicio");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setTargetConnectionFactory(connectionFactory());
        connectionFactory.setSessionCacheSize(10);
        log.info("MessagingConfiguration - cachingConnectionFactory() - fim");
        return connectionFactory;
    }
     
    /*
     * Message listener container, used for invoking messageReceiver.onMessage on message reception.
     */
    @Bean
    public MessageListenerContainer getContainer(){
    	log.info("MessagingConfiguration - getContainer() - inicio");
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName(ORDER_RESPONSE_QUEUE);
        container.setMessageListener(messageReceiver);
        log.info("MessagingConfiguration - getContainer() - fim");
        return container;
    }
 
    /*
     * Used for Sending Messages.
     */
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(ORDER_QUEUE);
        return template;
    }
     
     
    @Bean
    MessageConverter converter(){
        return new SimpleMessageConverter();
    }

}
