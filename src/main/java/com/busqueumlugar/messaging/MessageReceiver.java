package com.busqueumlugar.messaging;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.MessageHeaders;

import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.EmailJms;
import com.busqueumlugar.util.EnviaEmailHtml;
import com.busqueumlugar.util.MessageUtils;


@Component
public class MessageReceiver implements MessageListener{
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
	
	private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";
	private static final String ORDER_QUEUE = "order-queue";     
	    @Autowired
	    MessageConverter messageConverter;
	     
	    @Override
	    public void onMessage(Message message) {
	        try {
	            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	            Usuario usuario = (Usuario) messageConverter.fromMessage(message);
	            LOG.info("Application : order response received : {}",usuario);    
	            LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	        } catch (JMSException e) {
	          System.out.println("Message error - onMessage: " + e.getMessage());
	          LOG.error("Message error - onMessage: " + e.getMessage());
	          e.printStackTrace();
	        }
	         
	    }
	    
	    @JmsListener(destination = ORDER_QUEUE)
	    public void receiveMessage(final org.springframework.messaging.Message<EmailJms> message) throws JMSException {
	    	
	    	try {
	    		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		        MessageHeaders headers =  message.getHeaders();
		        LOG.info("Application : headers received : {}", headers);
		         
		        EmailJms response = message.getPayload();
		        LOG.info("Application : response received : {}",response.getTexto());
		        
		        EnviaEmailHtml enviaEmail = new EnviaEmailHtml();
	            enviaEmail.setSubject(response.getSubject());
	            enviaEmail.setTo(response.getTo());
	            enviaEmail.setTexto(response.getTexto());		            	
	            enviaEmail.enviaEmail(enviaEmail.getEmail());
		        
		        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
			} catch (Exception e) {
				 System.out.println("Message error - receiveMessage: " + e.getMessage());
		          LOG.error("Message error  - receiveMessage: " + e.getMessage());
		          e.printStackTrace();
			}
	        
	    }
	    
	    
	    

}
