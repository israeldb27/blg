package com.busqueumlugar.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.TipoContatoEnum;
import com.busqueumlugar.form.ContatoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.UsuarioInterface;


@Controller("contatoController")
@RequestMapping("/contato")
public class ContatoController {
	
	private static final Logger log = LoggerFactory.getLogger(ContatoController.class);
	
	@Autowired
	private  ContatoService contatoService;	

	private static final String DIR_PATH = "/contato/";
	
	
	@RequestMapping(value = "/filtrarContato", method = RequestMethod.POST)
    public String filtrarContato(@ModelAttribute("contatoForm") ContatoForm form,
			  					 HttpSession session, 
			  					 ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getOpcaoFiltroTipoContato().equals("meusContatos")){
//				List<Contato> lista = contatoService.filtrarContatos(user.getId(), form);		
				map.addAttribute("listaContatos", contatoService.filtrarContatosPaginacao(user.getId(), form));	
				form.setTipoListaContato("N");			
			}
			else {
				List<Usuario> lista = contatoService.filtrarUsuariosTipoContato(user.getId(), form);
				form.setTipoListaContato("S");
				map.addAttribute("listaUsuarios", lista);					
			}
			
			map.addAttribute("contatoForm", form);		
	        return DIR_PATH + "listaContatos";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  filtrarContato");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/paginarFiltroContato", method = RequestMethod.POST)
    public String paginarFiltroContato(@ModelAttribute("contatoForm") ContatoForm form,
			  					       HttpSession session, 
			  					       ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getOpcaoFiltroTipoContato().equals("meusContatos")){
				//List<Contato> lista = contatoService.filtrarContatos(user.getId(), form);			
				map.addAttribute("listaContatos", contatoService.filtrarContatosPaginacao(user.getId(), form));
				form.setTipoListaContato("N");				
			}
			else {
				List<Usuario> lista = contatoService.filtrarUsuariosTipoContato(user.getId(), form);
				form.setTipoListaContato("S");
				map.addAttribute("listaUsuarios", lista);					
			}		
			map.addAttribute("contatoForm", form);		
	        return DIR_PATH + "listaContatos";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  filtrarContato");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/filtrarTipoContato", method = RequestMethod.POST)
    public String filtrarTipoContato(@ModelAttribute("contatoForm") ContatoForm form,
			  					 	 HttpSession session, 
			  					 	 ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getOpcaoFiltroTipoContato().equals("meusContatos")){
				//List<Contato> lista = contatoService.filtrarContatos(user.getId(), form);			
				map.addAttribute("listaContatos", contatoService.filtrarContatosPaginacao(user.getId(), form));
				form.setTipoListaContato("N");				
			}
			else {
				List<Usuario> lista = contatoService.filtrarUsuariosTipoContato(user.getId(), form);
				form.setTipoListaContato("S");
				map.addAttribute("listaUsuarios", lista);					
			}
				
			map.addAttribute("contatoForm", form);
	        return DIR_PATH + "listaContatos";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  filtrarTipoContato");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/desmarcarCheck")	
	public void desmarcarCheck(Long idUsuarioHost, HttpServletResponse response, HttpSession session){
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		contatoService.atualizarStatusLeituraContato(user.getId(), idUsuarioHost);		
		session.setAttribute(ContatoService.QUANT_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), null));
		response.setStatus(200);
	}
	
	@RequestMapping(value = "/ordenar", method = RequestMethod.POST)
    public String ordenarContatos(@ModelAttribute("contatoForm") ContatoForm form,
    							  HttpSession session, 
    							  ModelMap map){  
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Contato> lista = contatoService.filtrarContatos(user.getId(), form);
			map.addAttribute("listaContatos", lista);
			map.addAttribute("quantTotalContatos", AppUtil.recuperarQuantidadeLista(lista));		
			map.addAttribute("contatoForm", form);
	        return DIR_PATH + "listaContatos";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  ordenarContatos"); 
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/ordenarConvites", method = RequestMethod.POST)
    public String ordenarConvites(@ModelAttribute("contatoForm") ContatoForm form,
    							  HttpSession session, 
    							  ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);			
			map.addAttribute("listaConvitesRecebidos", contatoService.filtrarContatos(user.getId(), form));
	        return DIR_PATH + "listarConvites";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  ordenarConvites"); 
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }  
	
	
	@RequestMapping(value = "/excluirContato/{idUsuarioContato}", method = RequestMethod.GET)
    public String excluirContato(@PathVariable Long idUsuarioContato, HttpSession session, ModelMap map){ 
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);			
			contatoService.excluirContato(idUsuarioContato, user.getId());
			map.addAttribute("listaContatos", contatoService.recuperarConvidados(user.getId(), new ContatoForm()));
	        return DIR_PATH + "listaContatos";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  ordenarConvites"); 
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }  
	
	@RequestMapping(value = "/responderConvite/{respostaConvite}/{idUsuarioContato}", method = RequestMethod.GET)
	@ResponseBody
    public String responderConvite(@PathVariable Long idUsuarioContato, 
    							   @PathVariable String respostaConvite, 
    							   ModelMap map, 
    							   HttpSession session){
		
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		if ( respostaConvite.equals(ContatoStatusEnum.OK.getRotulo()) || respostaConvite.equals(ContatoStatusEnum.RECUSADO.getRotulo())) {
			contatoService.responderConvite(user.getId(), idUsuarioContato, respostaConvite);
			if ( respostaConvite.equals(ContatoStatusEnum.OK.getRotulo()) )
				map.addAttribute("msgConviteAceito", "S");
			else
				map.addAttribute("msgConviteRecusado", "S");
		}	
		else
			contatoService.responderConvite(idUsuarioContato, user.getId(), respostaConvite);		
		
		session.setAttribute(ContatoService.QUANT_TOTAL_CONTATOS, contatoService.checarTotalContatosPorUsuarioPorStatus(user.getId(), ContatoStatusEnum.OK.getRotulo()));	
		session.setAttribute(ContatoService.LISTA_CONVITES_RECEBIDOS, contatoService.recuperarConvites(user.getId(), 4));		
		session.setAttribute(ContatoService.QUANT_NOVOS_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));
		session.setAttribute(ContatoService.QUANT_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), null));
		map.addAttribute("contatoForm", new ContatoForm());
        return "ok";
    }
	
	
	@RequestMapping(value = "/responderConviteSelecionado/{idUsuarioContato}/{respostaConvite}", method = RequestMethod.GET)
    public String responderConviteSelecionado(@PathVariable Long idUsuarioContato, @PathVariable String respostaConvite, ModelMap map, HttpSession session){        
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		map.addAttribute("listaConvitesRecebidos", contatoService.recuperarConviteSelecionado(user.getId(), idUsuarioContato));
		if ( respostaConvite.equals(ContatoStatusEnum.OK.getRotulo()) || respostaConvite.equals(ContatoStatusEnum.RECUSADO.getRotulo())) {
			contatoService.responderConvite(user.getId(), idUsuarioContato, respostaConvite);
			if ( respostaConvite.equals(ContatoStatusEnum.OK.getRotulo()) )
				map.addAttribute("msgConviteAceito", "S");
			else
				map.addAttribute("msgConviteRecusado", "S");
		}	
		else
			contatoService.responderConvite(idUsuarioContato, user.getId(), respostaConvite);
		
		session.setAttribute(ContatoService.QUANT_TOTAL_CONTATOS, contatoService.checarTotalContatosPorUsuarioPorStatus(user.getId(), ContatoStatusEnum.OK.getRotulo()));	
		session.setAttribute(ContatoService.LISTA_CONVITES_RECEBIDOS, contatoService.recuperarConvites(user.getId(), 4));		
		session.setAttribute(ContatoService.QUANT_NOVOS_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));
		session.setAttribute(ContatoService.QUANT_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), null));
		return DIR_PATH + "visualizarConviteSelecionado";
    }
	
	@RequestMapping(value = "/expandirListaContatos/{idUsuario}", method = RequestMethod.GET)
    public String expandirListaContatos(@PathVariable Long idUsuario,    	 
    									ModelMap map){        
		
		List<Contato> listaContatos = contatoService.recuperarConvidados(idUsuario, new ContatoForm());
		map.addAttribute("listaContatos", listaContatos);
		map.addAttribute("quantTotalContatos", AppUtil.recuperarQuantidadeLista(listaContatos));
		map.addAttribute("contatoForm", new ContatoForm());
        return DIR_PATH + "expandirListaContatosDetalhesUsuario";
    } 
    

	@RequestMapping(value = "/listarContatos", method = RequestMethod.GET)
    public String listarContatos(HttpSession session, ModelMap map){        
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarContatos");
			ContatoForm form = new ContatoForm();
			List<Contato> listaContatos = contatoService.recuperarConvidadosPaginacao(user.getId(), form);
			map.addAttribute("listaContatos", listaContatos);
			map.addAttribute("quantTotalContatos", AppUtil.recuperarQuantidadeLista(listaContatos));
			form.setTipoListaContato("N");
			map.addAttribute("contatoForm", form);
	        return DIR_PATH + "listaContatos";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  listarContatos"); 
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
    }  
	
	
    
	@RequestMapping(value = "/convites", method = RequestMethod.GET)
    public String convites(HttpSession session, 
    					   ModelMap map){ 
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaConvitesRecebidos", contatoService.recuperarConvites(user.getId()));
			session.setAttribute(ContatoService.QUANT_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), null));
			map.addAttribute("contatoForm", new ContatoForm());
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarConvites");
			return DIR_PATH + "listarConvites";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  convites"); 
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
    }
	
	@RequestMapping(value = "/visualizarConvite/{idContatoConvite}", method = RequestMethod.GET)
    public String visualizarConvite(@PathVariable Long idContatoConvite, 
    								HttpSession session, 
    								ModelMap map){     
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaConvitesRecebidos", contatoService.recuperarConviteSelecionado(user.getId(), idContatoConvite));
			session.setAttribute(ContatoService.LISTA_CONVITES_RECEBIDOS, contatoService.recuperarConvites(user.getId(), 4));
			session.setAttribute(ContatoService.QUANT_NOVOS_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));
			map.addAttribute("contatoForm", new ContatoForm());
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarConvites");
			return DIR_PATH + "visualizarConviteSelecionado";
		} catch (Exception e) {
			log.error("Erro metodo - ContatoController -  visualizarConvite"); 
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
    }
	
	
	@RequestMapping(value = "/enviarConvite/{idUsuario}")
	@ResponseBody
	public void enviarConvite(@PathVariable Long idUsuario, 
							  HttpServletResponse response, 
							  HttpSession session,
							  ModelMap map) {
		
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		String msg = contatoService.validarEnvioConvite(user, idUsuario);
		if ( msg.equals("")){
			contatoService.enviarConvite(user, idUsuario);
			map.addAttribute("msgConvite", "Convite enviado com sucesso");
		}	
		else
			map.addAttribute("msgConvite", msg);	
    }
	
	@RequestMapping(value = "/cancelarConvite/{idUsuario}")
	@ResponseBody
	public void cancelarConvite(@PathVariable Long idUsuario, 
							  	HttpServletResponse response, 
							  	HttpSession session,
							  	ModelMap map) {
		
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		contatoService.cancelarConviteEnviado(user.getId(), idUsuario);
		map.addAttribute("msgConvite", "Convite enviado com sucesso");
    }
	
	@RequestMapping(value = "/cancelarContato/{idUsuario}")
	@ResponseBody
	public void cancelarContato(@PathVariable Long idUsuario, 
							  	HttpServletResponse response, 
							  	HttpSession session,
							  	ModelMap map) {
		
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		contatoService.cancelarContato(user.getId(), idUsuario);
		map.addAttribute("msgConvite", "Convite enviado com sucesso");
    }
	
	
	
	
	
	/*   public void sendJMS(EmailImovel email) throws EmailException{
    
    try {          
        //sendJMSMessageToMyQueue(email);
        sendJMSMessageToMyQueue(email);
       
    } catch (JMSException jmse) {             
        jmse.printStackTrace();
    }   
}



private Message createJMSMessageForjmsMyQueue(Session session, Object messageData) throws JMSException {
    // TODO create and populate message to send
    TextMessage tm = session.createTextMessage();
    tm.setText(messageData.toString());
    return tm;
}

private ObjectMessage createJMSObjectMessageForjmsMyQueue(Session session, EmailImovel email) throws JMSException {
    // TODO create and populate message to send
    //TextMessage tm = session.createTextMessage();
    try {
        ObjectMessage obj = session.createObjectMessage();        
        obj.setObject(email);
        return obj;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    
    
}

private void sendJMSMessageToMyQueue(EmailImovel messageData) throws JMSException {
    Connection connection = null;
    Session session = null;
    try {
        connection = myQueueFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(myQueue);
        //messageProducer.send(createJMSMessageForjmsMyQueue(session, messageData));
        messageProducer.send(createJMSObjectMessageForjmsMyQueue(session, messageData));
    } finally {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
            }
        }
        if (connection != null) {
            connection.close();
        }
    }
}
*/
}
