package com.busqueumlugar.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
 




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.EmailJms;
import com.busqueumlugar.util.EnviaEmailHtml;
 

 
@Component
public class MessageSender {
	
	 @Autowired
	    JmsTemplate jmsTemplate;
	 
	    public void sendMessage(final Usuario usuario) {
	 
	        jmsTemplate.send(new MessageCreator(){
	                @Override
	                public Message createMessage(Session session) throws JMSException{
	                    ObjectMessage objectMessage = session.createObjectMessage(usuario);
	                    return objectMessage;
	                }
	            });
	    }
	    
	    public void sendMessage(final EmailJms email) {
	   	 
	        jmsTemplate.send(new MessageCreator(){
	                @Override
	                public Message createMessage(Session session) throws JMSException{
	                    ObjectMessage objectMessage = session.createObjectMessage(email);
	                    return objectMessage;
	                }
	            });
	    }

}
