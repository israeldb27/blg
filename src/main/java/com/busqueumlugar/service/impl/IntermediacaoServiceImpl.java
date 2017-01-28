package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.IntermediacaoDao;
import com.busqueumlugar.dao.RecomendacaoDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.AcaoNotificacaoEnum;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.NotaAcaoEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusImovelCompartilhadoEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.TipoNotificacaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.IntermediacaoForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Intermediacao;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.IntermediacaoService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.RecomendacaoService;
import com.busqueumlugar.service.SeguidorService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

@Service
public class IntermediacaoServiceImpl implements IntermediacaoService {
	
private static final Logger log = LoggerFactory.getLogger(IntermediacaoServiceImpl.class);
	
	@Autowired
	private IntermediacaoDao dao;
	
	@Autowired
	private ImovelDao imovelDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ContatoDao contatoDao;
	
	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private SeguidorDao seguidorDao;
	
	@Autowired
	private RecomendacaoDao recomendacaoDao;
	
	@Autowired
	private NotaService notaService;
	
	@Autowired
	private NotificacaoService notificacaoService;


	
	public Intermediacao recuperarIntermediacaoPorId(Long id) {
		return dao.findIntermediacaoById(id);
	}

	@Override
	@Transactional
	public void cadastrarSolicitacaoIntermediacao(Long idUsuario, String usuarioSolicitante, Long idImovel, String descricaoCompart) {		
		Intermediacao imovelComp = new Intermediacao();
        imovelComp.setDataSolicitacao(new Date());
        imovelComp.setUsuarioSolicitante(usuarioDao.findUsuario(idUsuario));
        imovelComp.setStatus(StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo());
        imovelComp.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        imovelComp.setImovel(imovelDao.findImovelById(idImovel));
        imovelComp.setUsuarioDonoImovel(imovelComp.getImovel().getUsuario());        
        imovelComp.setDescricaoCompartilhamento(descricaoCompart);    
        dao.save(imovelComp);
	}

	
	@Override
	public List<Intermediacao> recuperarMinhasSolicitacoesIntermediacoes(Long idUsuarioSolicitante, IntermediacaoForm form) {
        return dao.findIntermediacaoByIdUsuarioSolicitanteByStatus(idUsuarioSolicitante, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);
	}

	
	@Override
	public List<Intermediacao> recuperarSolicitacoesIntermediacoesRecebidas(Long idDonoImovel, IntermediacaoForm form) {
        return dao.findIntermediacaoByIdDonoImovelByStatus(idDonoImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);
	}

	
	@Override
	public List<Intermediacao> selecionarNovasSolicitacoesCompartilhamento(List<Intermediacao> lista) {		
		List<Intermediacao> listaFinal = new ArrayList<Intermediacao>();
        if (! CollectionUtils.isEmpty(lista)){
        	for (Intermediacao imovel : lista){		            
	            if ( imovel.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()))
	                listaFinal.add(imovel);                    
	        }
        }
                
        return listaFinal;
	}

	
	@Override
	@Transactional
	public boolean atualizarStatusIntermediacao(Intermediacao intermediacao) {		
		if ( intermediacao.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()))
			intermediacao.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
        else 
        	intermediacao.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        
        dao.save(intermediacao);
        return true;
	}
	
	
	@Override
	@Transactional
	public void excluiIntermediacao(Long id){
		dao.delete(Intermediacao.class, id);
	}
	
	@Override
	@Transactional
	public void excluirIntermediacao(Intermediacao intermediacao){
		dao.delete(intermediacao);
	}
	
	
	@Override
	public long checarQuantidadeNovasSolIntermediacao(Long idDonoImovel){
		return dao.checarQuantidadeIntermediacaoSolRecebidaByDonoImovelByStatusByStatusLeitura(idDonoImovel, 
																							   StatusLeituraEnum.NOVO.getRotulo(),
																							   StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo());
	}
	
	@Override
	//@Transactional
	public void atualizarStatusIntermediacao(Long idIntermediacao, String status ) {		
		Intermediacao imovel = this.recuperarIntermediacaoPorId(idIntermediacao);
        imovel.setStatus(status);
        if ( status.equals(StatusImovelCompartilhadoEnum.ACEITA.getRotulo())){
            imovel.setDataResposta(new Date());
            imovel.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());            
            this.dao.update(imovel);
            Usuario donoImovel = imovel.getUsuarioDonoImovel();      
        	Object[] params = {donoImovel.getNome()}; 
        	notificacaoService.cadastrarNotificacao(imovel.getUsuarioSolicitante().getId(), 
        			  								imovel.getImovel().getId(),
        			  								AcaoNotificacaoEnum.INTERMEDIACAO.getRotulo(),
													MessageUtils.getMessageParams("msg.notificacao.intermediacao.usuario.aceite", params),
													TipoNotificacaoEnum.IMOVEL.getRotulo(),
													imovel.getUsuarioDonoImovel().getId());
            
            notaService.cadastrarNota(MessageUtils.getMessage("msg.usuario.para.comecar.trabalhar.nota.imovel"), 
            						  imovel.getUsuarioSolicitante(), 
            						  imovel.getImovel(), 
            						  new Date(),
            						  NotaAcaoEnum.INTERMEDIACAO.getRotulo());            
        }
        else if ( status.equals("rejeitada")){
        	imovel.setDataResposta(new Date());
            imovel.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
            imovel.setStatus(StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo());
            this.dao.update(imovel);
        }    
        else if ( status.equals(StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo())){
            imovel.setDataResposta(null);
            this.dao.update(imovel);
        }
	}


	@Transactional
	private void atualizarIntermediacao(Intermediacao intermediacao) {
		dao.save(intermediacao);
	}

	@Override
	public Intermediacao recuperarIntermediacaoSelecionadoPorIdImovel(Long idImovel) {		
		List<Intermediacao> lista = dao.findIntermediacaoByIdImovelByStatus(idImovel,  StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
        if ( ! CollectionUtils.isEmpty(lista) )
            return lista.get(0);
        else
           return null;
	}

	@Override
	public Intermediacao recuperarImovelIntermediadoSelecionadoPorIdImovel(Long idImovel) {		
		List<Intermediacao> lista = dao.findIntermediacaoByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
		if ( ! CollectionUtils.isEmpty(lista) ){
        	Intermediacao imovel = (Intermediacao) lista.get(0);        	
            return imovel;
        }    
        else
           return null;
	}

	@Override
	public List<Intermediacao> recuperarMinhasSolIntermediacaoAceitasPorUsuarioSolicitante(Long idUsuarioSolicitante, IntermediacaoForm form) {		
		return dao.findIntermediacaoByIdUsuarioSolicitanteByStatus(idUsuarioSolicitante, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
	}

	@Override
	public List<Intermediacao> recuperarMinhasSolIntermediacaoAceitasPorDonoImovel(Long idDonoImovel, IntermediacaoForm form) {		
		return  dao.findIntermediacaoByIdDonoImovelByStatus(idDonoImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);          
	}

	@Override
	public List<Intermediacao> recuperarTodasSolIntermediacaoAceitas(Long idUsuario, IntermediacaoForm form) {		
		List<Intermediacao> listaDonoImovel = dao.findIntermediacaoByIdDonoImovelByStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
        List<Intermediacao> listaUsuarioSolicitante = dao.findIntermediacaoByIdUsuarioSolicitanteByStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);        
        List<Intermediacao> listaFinal = new ArrayList<Intermediacao>();        
        listaFinal.addAll(listaDonoImovel);     
        
        for (Intermediacao intermediacao : listaUsuarioSolicitante ){            
            if ( ! listaFinal.contains(intermediacao)){            	 
                 listaFinal.add(intermediacao);
            }     
        }
        return listaFinal; 
	}

	@Override
	public List<Intermediacao> recuperarTodasSolIntermediacoesAceitasDistintas(Long idUsuario, IntermediacaoForm form) {		
		List lista = dao.findIntermediacaoBySolAceitasDistintas(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
        List<Intermediacao> listaFinal = new ArrayList<Intermediacao>();
        TreeSet listaId = new TreeSet();
        String id = "";
        Intermediacao intermediacao = null;		
        for (Iterator iter = lista.iterator();iter.hasNext();){
            id = iter.next().toString();
            if ( ! listaId.contains(id)) {
            	intermediacao = recuperarImovelIntermediadoSelecionadoPorIdImovel(Long.parseLong(id));									
                listaFinal.add(intermediacao);    
            }            
        }
        return listaFinal;
	}

	@Override
	public Intermediacao recuperarMinhasSolicitacoesPorUsuarioSolPorImovel(Long idUsuario, Long idImovel) {		
		return dao.findIntermediacaoByIdUsuarioSolicitanteByIdImovel(idUsuario, idImovel);
	}

	
	@Override
	public String isIntermediacao(Long idImovel) {		
		List<Intermediacao> lista = dao.findIntermediacaoByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
        if (! CollectionUtils.isEmpty(lista))
            return "S";
        else
            return "N"; 
	}

	
	@Override
	public List<Usuario> recuperarUsuariosIntermediacao(Long idImovel) {		
		List<Intermediacao> lista = dao.findIntermediacaoByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
        List<Usuario> listaUsuario = new ArrayList<Usuario>();        
        for (Intermediacao imovel : lista){
            listaUsuario.add(imovel.getUsuarioSolicitante());
        }        
        return listaUsuario;
	}

	
	@Override
	public List<Imovel> checarImoveisMaisReceberamSolIntermediacaoPorPeriodo(AdministracaoForm form) {		
		List lista = dao.checarImoveisComMaisSolIntermediacaoPeriodo(form);
        List<Imovel> listaFinal = new ArrayList<Imovel>();        
        if ( ! CollectionUtils.isEmpty(lista) ){
            Imovel imovel = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
                listaFinal.add(imovel);
            }
        }
        return listaFinal;
	}
	
	@Override
	public List<Intermediacao> recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(long idImovel) {		
		List<Intermediacao> lista = dao.findIntermediacaoByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo());
		List<Intermediacao> listaFinal = new ArrayList<Intermediacao>();
        for (Intermediacao intermediacao : lista){ 
        	intermediacao.setQuantTotalParceiros(AppUtil.recuperarQuantidadeLista(recuperarIntermediacoesPorIdImovel(intermediacao.getImovel().getId() ,StatusImovelCompartilhadoEnum.ACEITA.getRotulo())));                
            listaFinal.add(intermediacao);                         
        }
		return listaFinal;
	}
	
	@Override
	public List<Imovel> recuperarMinhasSolIntermediacaoAceitasPorUsuarioSolicitantePorPerfil(Long idPerfil, PerfilForm frm) {		
		 return dao.findIntermediacaoAceitosPorUsuarioSolicitantePorPerfil(idPerfil, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), frm);
	}

	
	@Override
	public boolean checarEnviouSolicitacaoIntermediacao(Long idUsuario,Long idImovel) {		
		Intermediacao imovel = dao.findIntermediacaoByIdUsuarioSolicitanteByIdImovel(idUsuario, idImovel);        
        if ( imovel != null )
            return true;
        else        
            return false;    
	}

	
	@Override
	public String validarSolicitacaoIntermediacao(Long idUsuario, Long idImovel, String perfilDonoImovel, String descCompartilhamento) {		
		
		if (StringUtils.isNullOrEmpty(descCompartilhamento))
            return MessageUtils.getMessage("msg.erro.intermediacao.descricao.vazia");
        
        boolean jaEnviouSolicitacao = this.checarEnviouSolicitacaoIntermediacao(idUsuario, idImovel);
        if (jaEnviouSolicitacao) 
        	return MessageUtils.getMessage("msg.intermediacao.sol.sucesso.enviada.antes");
        
        Imovel imovel = imovelDao.findImovelById(idImovel);
        if ( imovel.getAtivado().equals("N"))
        	return MessageUtils.getMessage("msg.parceria.intermediacao.sol.imovel.desativado");
        
    	boolean existeContato = contatoService.checarExisteContato(idUsuario, imovel.getUsuario().getId());    
        if (! existeContato )
        	return MessageUtils.getMessage("msg.parceria.intermediacao.sol.erro.enviada.sem.contato");        
        
        if ( imovel.getUsuario().getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
        	long quant = dao.findQuantIntermediacaoByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
        	if ( quant > 0 )
        		return MessageUtils.getMessage("msg.parceria.sol.erro.imovel.existente");
        }       
             
        return "";
	}

	
	@Override
	public EmailImovel notificarSolicitacaoIntermediacao(Long idImovel) {		
		Imovel imovel = imovelDao.findImovelById(idImovel);
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioDonoImovel = imovel.getUsuario();
        // assunto        
        //email.setAssunto(getBundle().getString("msg.assunto.sol.parceria.imoveis"));                                        
        // para
        email.setTo(usuarioDonoImovel.getEmail());
        
        // Texto do Email        
        /*texto.append(getBundle().getString("msg.inicio.corpo.email.sol.parceria.imoveis") + "\n");        
        texto.append(getBundle().getString("msg.corpo.email.titulo.imovel") + imovel.getTitulo() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.tipo.imovel") + imovel.getTipoImovel() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.estado.imovel") + imovel.getEstado() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.cidade.imovel") + imovel.getCidade() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.bairro.imovel") + imovel.getBairro() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.data.sol.parceria.imoveis") + new DateUtil().getStrDate() + "\n");*/        
        email.setTextoEmail(texto.toString());      
        
        email.setAcao("solicitacaoCompartilhamento");
        return email;
	}

	
	@Override
	public EmailImovel notificarAceiteIntermediacao(Intermediacao intermediacao) {		
		Imovel imovel = imovelDao.findImovelById(intermediacao.getImovel().getId());
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioSolicitante = intermediacao.getUsuarioSolicitante();
        // assunto        
        //email.setAssunto(getBundle().getString("msg.assunto.aceite.parceria.imoveis"));                                
        // para
        email.setTo(usuarioSolicitante.getEmail());
        
        // Texto do Email
      /*  texto.append(getBundle().getString("msg.inicio.corpo.email.aceite.parceria.imoveis") + "\n");        
        texto.append(getBundle().getString("msg.corpo.email.titulo.imovel") + imovel.getTitulo() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.tipo.imovel") + imovel.getTipoImovel() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.estado.imovel") + imovel.getEstado() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.cidade.imovel") + imovel.getCidade() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.bairro.imovel") + imovel.getBairro() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.data.aceite.parceria.imoveis") + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());      
        */
        email.setAcao("aceiteCompartilhamento");
        return email;
	}
	
	@Override
	public Intermediacao recuperarUltimaSolicitacaoParceriaMeusImoveisPorIdImovel(Long idImovel) {
        return dao.findLastImovelParceriaMeusImoveisByIdImovel(idImovel);
	}

	
	@Override
	@Transactional
	public void atualizarPontuacaoNegocioParceria(Long idImovel, String novoStatus) {
		
		
	}

	
	@Override
	public List<Intermediacao> recuperarIntermediacoesPorIdImovel(Long idImovel, String status) {		
		return dao.findIntermediacaoByIdImovelByStatus(idImovel, status);
	}

	
	@Override
	public boolean checarExisteParceriaAceitaPorIdUsuario(Long idUsuario, IntermediacaoForm form) {		
		List<Intermediacao> listaDonoImovel = dao.findIntermediacaoByIdDonoImovelByStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
        if ( !CollectionUtils.isEmpty(listaDonoImovel)  )
            return true;
        else{
            List<Intermediacao> listaUsuarioSolicitante = dao.findIntermediacaoByIdUsuarioSolicitanteByStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
            if ( !CollectionUtils.isEmpty(listaUsuarioSolicitante)  )            
                return true;                
        }            
        return false;
	}
	
	@Override
	public long checarQuantidadeIntermediacaoAceitaPorIdUsuario(Long idUsuario) {		
		return dao.findQuantidadeIntermediacaoPorUsuarioPorStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());	
	}

	
	@Override
	public List<Intermediacao> recuperarTodosIntermediacaoPorUsuario(Long idPerfil, String status) {		
		return dao.findAllIntermediacaoByIdUsuarioByStatus(idPerfil, status);
	}


	@Override
	public List<Intermediacao> filtrarIntermediacao(Long idUsuario, IntermediacaoForm form) {		
		if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
			return dao.filterSolIntermediacao(idUsuario, form);		
		}	
		else if ( form.getTipoLista().equals("intermediacaoMinhasSol")) {
			return dao.filterMinhasSolIntermediacao(idUsuario, form);			 
		}	
		else if ( form.getTipoLista().equals("intermediacaoAceita")) {			
			return dao.filterIntermediacaoAceitas(idUsuario,  form);				
		}	
		else
			return null;
	}
	
	
	@Override
	public List<?> ordenarAgruparIntermediacoes(Long idUsuario,	IntermediacaoForm form) {
		if ( form.getOpcaoVisualizacao().equals("agruparUsuarios"))
			return this.agruparUsuariosIntermediacao(idUsuario, form);
		else if ( form.getOpcaoVisualizacao().equals("agruparImoveis"))
			return this.agruparImoveisIntermediacao(idUsuario, form);		
		else
			return null;
	}
	
	@Override
	public List<Imovel> agruparImoveisIntermediacao(Long idUsuarioSessao,	IntermediacaoForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = null;
		if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
			// lista dos IdImovel cujo usuario sessao tenha solicitado intermediacao de um imovel 
			lista = dao.findImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);			
		}
		
		Object[] obj = null;		
		Imovel imovel = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next();			
			imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
			imovel.setQuantImovelIntermediacao(Integer.parseInt(obj[1].toString()));
			imovel.setQuantNovosImovelIntermediacao(dao.findQuantidadeNovosImoveisCompartilhados(Long.parseLong(obj[0].toString())));
			listaFinal.add(imovel);
		}
		
		return listaFinal;
	}


	@Override
	public List<Usuario> agruparUsuariosIntermediacao(Long idUsuarioSessao,	IntermediacaoForm form) {

		List<Usuario> listaFinal = new ArrayList<Usuario>();
		List lista = null;
		if ( form.getTipoLista().equals("intermediacaoAceita")){							
			lista = dao.findUsuariosParceriaByStatusByIdUsuario(idUsuarioSessao, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
		}
		else if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
			// lista dos IdUsuario que solicitou intermediacao com o usuario sessao 			
			lista = dao.findUsuariosParceriaByStatusByIdDonoImovel(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);			
		}
		else if ( form.getTipoLista().equals("intermediacaoMinhasSol")){
			// lista dos IdUsuario que recebeu solicitacao de  intermediacao com o usuario sessao
			lista = dao.findUsuariosParceriaByStatusByIdUsuario(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);			
		}
		
		Object[] obj = null;			 
		Usuario usuario = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next(); 				
			usuario = usuarioDao.findUsuario(Long.parseLong(obj[1].toString()));
			usuario.setQuantImovelIntermediacao(Integer.parseInt(obj[0].toString()));
			usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
			usuario.setQuantTotalContatos(contatoDao.findQuantidadeTotalContatosByIdUsuarioByStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
			usuario.setQuantTotalRecomendacoes(recomendacaoDao.findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo(), null));
			usuario.setQuantTotalImoveis(imovelDao.findQuantMeusImoveis(usuario.getId()));
			usuario.setQuantTotalSeguidores(seguidorDao.findQuantSeguidoresByIdUsuarioSeguido(usuario.getId()));
			listaFinal.add(usuario);
		}
		
		return listaFinal;
	}


	@Override
	public List<Intermediacao> recuperarImoveisCompartilhadoPorUsuario(Long idUsuario, Long idUsuario2) {		
		return dao.findIntermediacaoByIdUsuarioByIdUsuario2(idUsuario, idUsuario2);
	}

	@Override
	public List<Intermediacao> recuperarTodosImovelIntermediacaoPorImovel(Long idImovel) {		
		return dao.findIntermediacaoByIdImovel(idImovel);	
	}

	@Override
	@Transactional
	public void excluirSolicitacaoIntermediacao(Long idUsuario, long idImovel) {
		Intermediacao imovel = dao.findIntermediacaoByIdUsuarioSolicitanteByIdImovel(idUsuario, idImovel);
		dao.delete(imovel);		
	}


	@Override
	public Usuario recuperarUsuarioIntermediador(Long idImovel) {
		List<Intermediacao> lista = dao.findIntermediacaoByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
		if ( ! CollectionUtils.isEmpty(lista) ){
			Intermediacao imovel = (Intermediacao)lista.get(0);
			return imovel.getUsuarioSolicitante();
		}
		else
			return null;
	}
	
	@Override
	public List<Usuario> filtrarAgruparUsuario(Long idUsuario, IntermediacaoForm form){		
		return this.agruparUsuariosIntermediacao(idUsuario, form);		
	}
	
	
	@Override
	public List<Imovel>  filtrarAgruparImoveis(Long idUsuario, IntermediacaoForm form){		
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = null;

		if ( form.getTipoLista().equals("intermediacaoAceita")){					
			lista = dao.filterFindIntermediacaoByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);								
		}
		else if ( form.getTipoLista().equals("intermediacaoSolRecebida")){					
			lista = dao.filterFindIntermediacaoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);
			
		}
		else if ( form.getTipoLista().equals("intermediacaoMinhasSol")){					
			lista = dao.filterFindIntermediacaoMinhasSolicitacoesByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);
		}
			
		
		if (! CollectionUtils.isEmpty(lista) ){
			Object[] obj = null;		
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();			
				imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
				imovel.setQuantImovelParceria(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovosImovelParceria(dao.findQuantidadeNovosImoveisCompartilhados(Long.parseLong(obj[0].toString())));				
				listaFinal.add(imovel);
			}			
			return listaFinal;
		}
		else 
			return listaFinal;		
	}	

	@Override
	@Transactional
	public void atualizarStatusSolRecebidasImovelCompartilhado(Long idDonoImovel) {
		dao.updateStatusLeitura(idDonoImovel);
	}

	@Override
	public List<Intermediacao> recuperarTodosIntermediacaoPorUsuario(Long idUsuario, Long idUsuario2) {
		List<Intermediacao> listaFinal = new ArrayList<Intermediacao>();
		List<Intermediacao> lista1 = dao.findIntermediacaoByIdUsuarioByIdUsuario2(idUsuario,  idUsuario2);
		List<Intermediacao> lista2 = dao.findIntermediacaoByIdUsuarioByIdUsuario2(idUsuario2, idUsuario);
		
		if ( ! CollectionUtils.isEmpty(lista1))
			listaFinal.addAll(lista1);
		
		if ( ! CollectionUtils.isEmpty(lista2))
			listaFinal.addAll(lista2);
		
		return listaFinal;
	}


}
