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


@Component
public class MessageReceiver implements MessageListener{
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
	
	private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";
	     
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
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	         
	    }
	    
	    @JmsListener(destination = ORDER_RESPONSE_QUEUE)
	    public void receiveMessage(final org.springframework.messaging.Message<Usuario> message) throws JMSException {
	        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	        MessageHeaders headers =  message.getHeaders();
	        LOG.info("Application : headers received : {}", headers);
	         
	        Usuario response = message.getPayload();
	        LOG.info("Application : response received : {}",response);
	        
	        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	    }
	    
	    
	    

}
