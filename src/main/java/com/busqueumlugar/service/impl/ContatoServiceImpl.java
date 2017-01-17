package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.AcaoNotificacaoEnum;
import com.busqueumlugar.enumerador.ContatoPerfilEnum;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.TipoNotificacaoEnum;
import com.busqueumlugar.form.ContatoForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.ImovelindicadoService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

@Service
public class ContatoServiceImpl implements ContatoService {
	
	private static final Logger log = LoggerFactory.getLogger(ContatoServiceImpl.class);

	@Autowired
	private ContatoDao dao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ImovelDao imovelDao;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	@Autowired
	private ImovelindicadoService imovelIndicadoService; 


	public Contato recuperarContatoPorId(Long id) {
		return dao.findContatoById(id);
	}
	
	public void enviarConvite(UsuarioForm user, Usuario usuarioConvidado) {
		Contato contato = new Contato();
        contato.setUsuarioHost(usuarioDao.findUsuario(user.getId()));
        contato.setUsuarioConvidado(usuarioDao.findUsuario(usuarioConvidado.getId()));
        contato.setStatus(ContatoStatusEnum.CONVIDADO.getRotulo());
        contato.setPerfilContato(recuperarEnumPerfilUsuario(user.getPerfil()));
        contato.setDataConvite(new Date());
        contato.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        dao.save(contato);		
	}

	
	public void enviarConvite(UsuarioForm user, Long idUsuarioConvidado) {
		Contato contato = new Contato();
		contato.setUsuarioHost(usuarioDao.findUsuario(user.getId()));
        contato.setUsuarioConvidado(usuarioDao.findUsuario(idUsuarioConvidado));		
        contato.setStatus(ContatoStatusEnum.CONVIDADO.getRotulo());
        contato.setPerfilContato(recuperarEnumPerfilUsuario(user.getPerfil()));
        contato.setDataConvite(new Date());
        contato.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo()	); 
        dao.save(contato);		
	}

	
	public List<Contato> recuperarConvites(Long idUsuario) {
        return dao.findConvites(idUsuario);
	}
	
	public List<Contato> recuperarConvidados(Long idUsuario, ContatoForm form) {	
        return dao.findContatos(idUsuario, form);
	}
	
	public List<Usuario> recuperarConvidadosIndicacaoImovel(Long idUsuario, Long idImovel , ImovelindicadoForm form) {		
		List<Contato> lista = dao.findContatosByIndicacao(idUsuario, new ImovelindicadoForm());
        List<Usuario> listaTodosContatos = new ArrayList<Usuario>();
        Imovel imovel = imovelDao.findImovelById(idImovel);
		if (! CollectionUtils.isEmpty(lista)){
        	Usuario usuario = null;
            for (Contato contato : lista){                
                usuario = new Usuario();
                if ( contato.getUsuarioConvidado().getId().longValue() == idUsuario.longValue())
                	usuario = contato.getUsuarioHost();                     
                else if ( contato.getUsuarioHost().getId().longValue() == idUsuario.longValue())
                	usuario = contato.getUsuarioConvidado();  
                
                if ( imovel.getUsuario().getId().longValue()  == usuario.getId().longValue())
                	usuario.setIndicadoImovel("S");
                else
                	usuario.setIndicadoImovel(imovelIndicadoService.checarUsuarioIndicacaoImovel(usuario.getId(), idImovel));
                
                listaTodosContatos.add(usuario);            
            }        
        }
        
        return listaTodosContatos;
	}
	
	public List<Contato> recuperarConvidadosHabilitados(Long idUsuario, ContatoForm form) {
        return dao.findContatos(idUsuario, form);
	}

	
	public List<Contato> recuperarConvidadosPorPerfil(Long idUsuario, String tipoPerfil) {		
        return dao.findContatosByPerfilUsuario(idUsuario, tipoPerfil);
	}

	
	public void responderConvite(Long idUsuarioConvidado, Long idUsuarioHost, String resposta) {
		Contato contato = dao.findContatosByStatus(idUsuarioConvidado, idUsuarioHost, ContatoStatusEnum.CONVIDADO.getRotulo());
        contato.setStatus(resposta);
        if ( ! StringUtils.isNullOrEmpty(resposta)){
            if ( resposta.equals(ContatoStatusEnum.RECUSADO.getRotulo()) || resposta.equals(ContatoStatusEnum.CANCELADO.getRotulo())) 
            	dao.delete(contato);
            else if ( resposta.equals(ContatoStatusEnum.OK.getRotulo())) {
                dao.save(contato);
                // inserir notificacao para o usuario que enviou o convite
                Usuario usuarioConvidado = usuarioDao.findUsuario(idUsuarioConvidado);
                notificacaoService.cadastrarNotificacao(idUsuarioHost,
                										AcaoNotificacaoEnum.CONVITE.getRotulo(),  
                										usuarioConvidado.getNome() + " " + MessageUtils.getMessage("msg.sucesso.aceitou.seu.convite"),
                										TipoNotificacaoEnum.CONVITE.getRotulo(), 
                										idUsuarioConvidado);
            }
        }		
	}

	
	public void excluirContato(Long idUsuarioContato, Long idUsuarioSessao) {		
		
        Contato contato = dao.findContatosByStatus(idUsuarioContato, idUsuarioSessao, ContatoStatusEnum.OK.getRotulo());        
        if (( contato == null ) || ( contato != null &&  contato.getId().intValue() == 0)) {
            contato = dao.findContatosByStatus(idUsuarioSessao, idUsuarioContato, ContatoStatusEnum.OK.getRotulo());
        }
        
        dao.delete(contato);
	}

	
	public List<Usuario> recuperarConvitesEnviados(Long idUsuario) {		
		List<Contato> lista = dao.findConvitesEnviados(idUsuario);  
        List<Usuario> listaHost = new ArrayList<Usuario>();
        Usuario usuario = null;
        for (Contato contato : lista){            
            usuario =  contato.getUsuarioConvidado();            
            listaHost.add(usuario);            
        }        
        return listaHost;
	}

	@Override
	@Transactional
	public void cancelarConviteEnviado(Long idUsuarioHost, Usuario usuarioConvidado) {
		Contato contato = dao.findContatosByStatus(usuarioConvidado.getId(), idUsuarioHost, ContatoStatusEnum.CONVIDADO.getRotulo());
        if (( contato == null ) || ( contato != null && contato.getId().intValue() == 0))
            contato = dao.findContatosByStatus(idUsuarioHost, usuarioConvidado.getId(), ContatoStatusEnum.CONVIDADO.getRotulo());
        
        dao.delete(contato);		
	}

	public List<Contato> recuperaContatosOkPorUsuarioFiltroRelatorio(RelatorioForm frm, Long idUsuario){
		List<Contato> listaContatosHost = dao.findContatosHostFiltroRelatorio(frm, idUsuario);		
		List<Contato> listaContatosConvidado = dao.findContatosConvidadoFiltroRelatorio(frm, idUsuario);
		List<Contato> listaFinal = new ArrayList<Contato>();
		if ( ! CollectionUtils.isEmpty(listaContatosHost) )
			listaFinal.addAll(listaContatosHost);
		
		if ( ! CollectionUtils.isEmpty(listaContatosConvidado) )
			listaFinal.addAll(listaContatosConvidado);
		
		return listaFinal;
	}
	
	public String validarEnvioConvite(UsuarioForm userSession, Usuario usuarioConvidado) {
		
        if ( userSession.getId().equals(usuarioConvidado.getId()))
            return MessageUtils.getMessage("msg.erro.enviar.convite.si.mesmo"); 
        
        
        if ( usuarioConvidado.getAtivado().equals("N"))
    	    return MessageUtils.getMessage("msg.erro.enviar.convite.usuario.desativado");      
                    

        // checando se jï¿½ existe um contato com o usuario                        
        if (( dao.findContatosByStatus(userSession.getId(), usuarioConvidado.getId(), ContatoStatusEnum.OK.getRotulo()) != null ) ||
            ( dao.findContatosByStatus(usuarioConvidado.getId(), userSession.getId(), ContatoStatusEnum.OK.getRotulo()) != null )){
            return MessageUtils.getMessage("msg.erro.enviar.convite.contato.feito");                               
        }   
        
        if (( dao.findContatosByStatus(userSession.getId(), usuarioConvidado.getId(), ContatoStatusEnum.CONVIDADO.getRotulo()) != null ) ||
            ( dao.findContatosByStatus(usuarioConvidado.getId(), userSession.getId(), ContatoStatusEnum.CONVIDADO.getRotulo()) != null )){
            return MessageUtils.getMessage("msg.erro.enviar.convite.ja.enviado");                                                   
        }                      
        
        return "";
	}

	
	public String validarEnvioConvite(UsuarioForm userSession, Long idUsuarioConvidado) {
        

        if ( userSession.getId().equals(idUsuarioConvidado))
        	return MessageUtils.getMessage("msg.erro.enviar.convite.si.mesmo");

        // checando se jï¿½ existe um contato com o usuario                        
        if (( dao.findContatosByStatus(userSession.getId(), idUsuarioConvidado, ContatoStatusEnum.OK.getRotulo()) != null ) ||
            ( dao.findContatosByStatus(idUsuarioConvidado, userSession.getId(), ContatoStatusEnum.OK.getRotulo()) != null )){
        	return MessageUtils.getMessage("msg.erro.enviar.convite.contato.feito");           	 
        }
        
        if (( dao.findContatosByStatus(userSession.getId(), idUsuarioConvidado, ContatoStatusEnum.CONVIDADO.getRotulo()) != null ) ||
            ( dao.findContatosByStatus(idUsuarioConvidado, userSession.getId(), ContatoStatusEnum.CONVIDADO.getRotulo()) != null )){
        	return  MessageUtils.getMessage("msg.erro.enviar.convite.ja.enviado");
        }

        return "";	
	}
	
	public boolean checarExisteContato(Long idUsuario, Long idUsuarioDonoImovel) {		
		 Contato contato = dao.findAnyContatoByStatus(idUsuarioDonoImovel, idUsuario, ContatoStatusEnum.OK.getRotulo());
	        if ( contato != null && contato.getId().longValue() > 0 )
	            return true;
	        else
	            return false;
	}
	
	public EmailImovel notificarSolicitacaoContato(Usuario usuario) {
        
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        // assunto           
        email.setAssunto(MessageUtils.getMessage("msg.email.assunto.sol.contato"));
        // para
        email.setTo(usuario.getEmail());
        
        // Texto do Email  
        texto.append(MessageUtils.getMessage("msg.email.texto.email.sol.contato") + "\n");
        texto.append(MessageUtils.getMessage("msg.email.texto.email.data.sol.contato") + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());      
        
        email.setAcao("solicitacaoContato");
        return email;        
    }

    public EmailImovel notificarSolicitacaoContato(Long idUsuario) {
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
        Usuario usuario = usuarioDao.findUsuario(idUsuario);        
        // assunto        
        email.setAssunto(MessageUtils.getMessage("msg.email.assunto.sol.contato"));
        // para
        email.setTo(usuario.getEmail());
        
        // Texto do Email
        texto.append(MessageUtils.getMessage("msg.email.texto.email.sol.contato") + "\n");
        texto.append(MessageUtils.getMessage("msg.email.texto.email.data.sol.contato") + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());      
        
        email.setAcao("solicitacaoContato");
        return email;
    }

    public EmailImovel notificarAceiteConvite(Usuario usuario) {
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
        
        // assunto    
        email.setAssunto(MessageUtils.getMessage("msg.email.confirmacao.aceite.contato"));
        
        // para
        email.setTo(usuario.getEmail());
        
        // Texto do Email 
        texto.append(MessageUtils.getMessage("msg.email.confirmacao.aceite.contato.nome.usuario") + "\n");
        texto.append(MessageUtils.getMessage("msg.email.confirmacao.aceite.contato.data.aceite") + new DateUtil().getStrDate() + "\n");
        
        texto.append("Data aceite: " + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());      
        
        email.setAcao("aceiteContato");
        return email;
    }

	@Override
	public long checarConvitesRecebidosPorUsuarioPorStatus(Long idUsuario, String status){
		return dao.findQuantidadeConvitesPorUsuarioPorStatus(idUsuario, status);
	}

	@Override
	public List<Contato> recuperarConvidadosMarcandoCheck(Long idUsuario) {
        return dao.findContatos(idUsuario, new ContatoForm());
	}


	@Override
	@Transactional
	public boolean atualizarStatusLeituraContato(Long idUsuarioConvidado, Long idUsuarioHost) {
		Contato contato = dao.findContatosByStatus(idUsuarioConvidado, idUsuarioHost, ContatoStatusEnum.CONVIDADO.getRotulo());
		if ( contato.getStatusLeitura().equals(StatusLeituraEnum.LIDO.getRotulo()))
			contato.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
		else
			contato.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
		
		dao.save(contato);
		return true;
	}


	@Override
	public String checarTipoContato(Long idUsuario, Long idUsuarioSessao) {

		// checando se já existe algum contato enviado
		Contato contatoOK = dao.findAnyContatoByStatus(idUsuario, idUsuarioSessao, ContatoStatusEnum.OK.getRotulo());
		if ( contatoOK != null )
			return "O";
		else {
			// checando se algum convite já foi enviado
			Contato contatoSol = dao.findAnyContatoByStatus(idUsuario, idUsuarioSessao, ContatoStatusEnum.CONVIDADO.getRotulo());
			if (contatoSol != null )
				return "C";
			else // nenhum contato foi formado ou foi enviado
				return "N";
		}		
	}


	@Override
	public List<Contato> filtrarContatos(Long idUsuario, ContatoForm form) {		
        return dao.findContatos(idUsuario, form);
	}


	@Override
	public List<Usuario> recuperarConvites(Long idUsuario, int quant) {
		List<Contato> lista = dao.findConvites(idUsuario, quant);
        List<Usuario> listaHost = new ArrayList<Usuario>();
        Usuario usuario = null;
        if ( ! CollectionUtils.isEmpty(lista) ){
        	for (Contato contato : lista) {            
                usuario = contato.getUsuarioHost();
                usuario.setStatusLeitura(contato.getStatusLeitura());
                usuario.setIdContatoConvite(contato.getId());
                usuario.setDataConvite(contato.getDataConvite());
                listaHost.add(usuario);            
            }
        }
                
        return listaHost;
	}

	@Override
	public List<Usuario> recuperarConviteSelecionado(Long idUsuario,Long idContatoConvite) {
		
		Contato contato = dao.findContatosByStatus(idUsuario, idContatoConvite, ContatoStatusEnum.CONVIDADO.getRotulo());
		List<Usuario> listaHost = new ArrayList<Usuario>();
		if (contato != null){
			contato.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
			dao.save(contato);			
			Usuario usuario = contato.getUsuarioHost();
            usuario.setStatusLeitura(contato.getStatusLeitura());            
            usuario.setIdContatoConvite(contato.getId());
            usuario.setDataConvite(contato.getDataConvite());
            listaHost.add(usuario);
		}
		
		return listaHost;
	}

	@Override
	@Transactional
	public void cancelarConviteEnviado(Long idUsuarioHost, Long idUsuarioConvidado) {

		Contato contato = dao.findContatosByStatus(idUsuarioConvidado, idUsuarioHost, ContatoStatusEnum.CONVIDADO.getRotulo());
        if (( contato == null ) || ( contato != null && contato.getId().intValue() == 0))
            contato = dao.findContatosByStatus(idUsuarioHost, idUsuarioHost, ContatoStatusEnum.CONVIDADO.getRotulo());
        
        dao.delete(contato);
	}

	@Override
	@Transactional
	public void cancelarContato(Long idUsuarioHost, Long idUsuarioConvidado) {
		Contato contato = dao.findAnyContatoByStatus(idUsuarioHost, idUsuarioConvidado, ContatoStatusEnum.OK.getRotulo()) ;
        if ( contato != null )
        	dao.delete(contato);		
	}
	

	@Override
	public List recuperarIDsMeusContatos(Long idUsuario) {		
		return dao.findIdsUsuariosContatosByIdUsuarioByStatus(idUsuario, ContatoStatusEnum.OK.getRotulo());
	}

	@Override
	public long checarTotalContatosPorUsuarioPorStatus(Long idUsuario, String status) {
		return dao.findQuantidadeTotalContatosByIdUsuarioByStatus(idUsuario, status);
	}
	
	private String recuperarEnumPerfilUsuario(String perfil){
		if (perfil.equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
			return ContatoPerfilEnum.PADRAO.getRotulo();
		else if (perfil.equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
			return ContatoPerfilEnum.IMOBILIARIA.getRotulo();
		else if (perfil.equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
			return ContatoPerfilEnum.CORRETOR.getRotulo();	
		
		return "";
	}

	@Override
	public List<Contato> filtrarRecuperacaoConvidadosParaIndicacao(	Long idUsuario, ImovelindicadoForm form) {	    
        return dao.findContatosByIndicacao(idUsuario, form);
	}

}
