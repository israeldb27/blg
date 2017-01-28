package com.busqueumlugar.util;

import java.io.Serializable;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.busqueumlugar.model.Imovel;

public class EnviaEmailHtml implements Serializable {
	
	private static final long serialVersionUID = -1047840909099108730L;
	
	private HtmlEmail email; 
	private String authuser;
	private String authpwd;
	
	private String subject;
	private String to;
	private String texto;	
	
   public EnviaEmailHtml() {
		this.email = new HtmlEmail();                
		this.authuser = "lyotomachidarj@gmail.com";
		this.authpwd = "israel27";
		this.email.setSmtpPort(587); //Porta do servidor smtp
		//this.email.setSmtpPort(465); //Porta do servidor smtp
		this.email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
		this.email.setDebug(true);
		this.email.setHostName("smtp.gmail.com");  //troque pelo endereco de seu servidor smtp
		//email.setPopBeforeSmtp(false, "pop.servidor", "login", "senha"); //Troque pelos seus dados                                
	}
   
	public Email sessionProperties(Email email) {
		try {
			this.getEmail().getMailSession().getProperties().put("mail.smtps.auth", "true");
			this.getEmail().getMailSession().getProperties().put("mail.debug", "false");
			this.getEmail().getMailSession().getProperties().put("mail.smtps.port", "587");
			this.getEmail().getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
			this.getEmail().getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
			this.getEmail().getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
			this.getEmail().getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
			this.getEmail().setFrom("admin@busqueumlugar.com.br", "BusqueUmLugar");
			//this.email.addCc("israeldb27@gmail.com");
			//this.email.addCc("adalburq@gmail.com");
			//email.setTLS(true);
			return this.getEmail();
		} catch (EmailException e) {
			System.out.println("Problema montagem EnviaEmail.sessionProperties() :" + e.toString());
			return null;
		}
	}
	
	public void enviaEmail(Email email) throws EmailException {
		sessionProperties(this.getEmail());
		this.getEmail().addTo(this.getTo());
		this.getEmail().setSubject(this.getSubject());
		this.getEmail().setHtmlMsg(this.montaTextoEmail());
		
		System.out.println("enviando email...");
		this.getEmail().send();                        
		System.out.println("Email enviado!");
	}	
	
	
	public String montaTextoEmail(){		
		StringBuilder texto = new StringBuilder();
		texto.append("<html> ");
		texto.append("<head> "); 
		texto.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /> ");
		texto.append("<title>A Simple Responsive HTML Email</title> ");
		texto.append(" <style type=\"text/css\"> ");
		texto.append("        body {margin: 0; padding: 0; min-width: 100%!important;} ");
		texto.append("        .content {width: 100%; max-width: 600px;} ");
		texto.append("        </style> ");
		texto.append("    </head> ");
	    texto.append("    <body yahoo bgcolor=\"#f6f8f1\"> ");
	    texto.append("        <div style=\"color:#000 !important; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; width:100%; background: #eee;\"> ");
	    texto.append("  	  <div style=\"width:600px; margin: 0 auto; background: #fff;\"> ");
	    texto.append("						<center> ");
	    texto.append("							<div style=\"padding: 1em 0; background: #f44336;\"> </div> ");
	    texto.append("							<h2 style=\"letter-spacing: 3px;\">BusqueUmLugar</h2> ");
	    texto.append("		");
	    texto.append("							<div style=\"margin: 1em !important; line-height: 150%;\"> ");
	    //texto.append("							<h4 style=\"letter-spacing: 1px; color: #000;\">Thank you for registering on our site. To login, you need to activate your account.</h4> ");
	    
	    texto.append("							<h4 style=\"letter-spacing: 1px; color: #000;\">" + this.getTexto() + "</h4> ");
	    
	    //texto.append("							<p style=\"color: #999;\">Importante: Desconsidere este email se voce ja obteve anteriormente a informacao sobre o alerta deste email.</p> ");
	    texto.append("							<p style=\"color: #999;\">" + MessageUtils.getMessage("msg.email.aviso.importante") + "</p> ");
	    
	    texto.append("							<div style=\"margin: 2em; color: #000;\"> ");
	    texto.append("							<a style=\"-webkit-border-radius: 28;-moz-border-radius: 28;border-radius: 28px;font-family: Arial;color: #ffffff;font-size: 20px;background: #f44336;padding: 10px 20px 10px 20px;text-decoration: none;\" href=\"#\">" + MessageUtils.getMessage("msg.email.link.acessar.plataforma") + "</a> ");
	    texto.append("								</div> ");
	    
	    texto.append("								<br> <br> <br>");
	    texto.append("								<p></p> ");
	    texto.append("							</div> ");
	    texto.append("						<center> ");
	    texto.append("		        	</center></center> ");
	    texto.append("		         </div> ");
	    texto.append("		</div> ");
	    texto.append("    </body> ");
	    texto.append("  </html> ");
		
		return texto.toString();
	}
	
	public HtmlEmail getEmail() {
		return email;
	}
	public void setEmail(HtmlEmail email) {
		this.email = email;
	}
	public String getAuthuser() {
		return authuser;
	}
	public void setAuthuser(String authuser) {
		this.authuser = authuser;
	}
	public String getAuthpwd() {
		return authpwd;
	}
	public void setAuthpwd(String authpwd) {
		this.authpwd = authpwd;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
