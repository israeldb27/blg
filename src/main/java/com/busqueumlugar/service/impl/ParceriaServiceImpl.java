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
import com.busqueumlugar.dao.ParceriaDao;
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
import com.busqueumlugar.form.ParceriaForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Parceria;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.ParceriaService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

@Service
public class ParceriaServiceImpl implements ParceriaService {
	
private static final Logger log = LoggerFactory.getLogger(ParceriaServiceImpl.class);
	
	@Autowired
	private ParceriaDao dao;
	
	@Autowired
	private ImovelDao imovelDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ContatoDao contatoDao;
	
	@Autowired
	private SeguidorDao seguidorDao;
	
	@Autowired
	private RecomendacaoDao recomendacaoDao;
	
	@Autowired
	private NotaService notaService;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private ImovelService imovelService;

	@Autowired
	private UsuarioService usuarioService;

	
	public Parceria recuperarParceriaId(Long id) {
		return dao.findParceriaById(id);
	}

	@Override
	@Transactional
	public void cadastrarSolicitacaoParceria(Long idUsuario, String usuarioSolicitante, Long idImovel, String descricaoCompart) {		
		Parceria imovelComp = new Parceria();
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
	public List<Parceria> recuperarMinhasSolicitacoesParcerias(Long idUsuarioSolicitante, ParceriaForm form) {	
        return dao.findParceriaByIdUsuarioSolicitanteByStatus(idUsuarioSolicitante, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(),   form);
	}
	
	@Override
	public List<Parceria> recuperarSolicitacoesParceriasRecebidas(Long idDonoImovel, ParceriaForm form) {
        return dao.findParceriaByIdDonoImovelByStatus(idDonoImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(),   form);
	}

	
	@Override
	public List<Parceria> selecionarNovasSolicitacoesParceria(List<Parceria> lista) {		
		List<Parceria> listaFinal = new ArrayList<Parceria>();
        if (! CollectionUtils.isEmpty(lista)){
        	for (Parceria imovel : lista){		            
	            if ( imovel.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()))
	                listaFinal.add(imovel);                    
	        }
        }
                
        return listaFinal;
	}

	
	@Override
	@Transactional
	public boolean atualizarStatusParceria(Parceria parceria) {		
		if ( parceria.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()))
			parceria.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
        else 
        	parceria.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        
        dao.save(parceria);
        return true;
	}
	
	
	@Override
	@Transactional
	public void excluirParceria(Long id){	
		dao.delete(Parceria.class, id);
	}
	
	@Override
	@Transactional
	public void excluirParceria(Parceria parceria){
		dao.delete(parceria);
	}
	
	
	@Override
	public long checarQuantidadeNovasSolParceria(Long idDonoImovel){
		return dao.checarQuantidadeParceriaSolRecebidaByDonoImovelByStatusByStatusLeitura(idDonoImovel, 
																						  StatusLeituraEnum.NOVO.getRotulo(),
																						  StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo());
	}

	
	@Override
	//@Transactional
	public void atualizarStatusParceria(Long idParceria, String status) {		
		Parceria imovel = this.recuperarParceriaId(idParceria);
        imovel.setStatus(status);
        if ( status.equals(StatusImovelCompartilhadoEnum.ACEITA.getRotulo())){
            imovel.setDataResposta(new Date());
            imovel.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());            
            this.dao.update(imovel);
            Usuario donoImovel = imovel.getUsuarioDonoImovel();      
        	Object[] params = {donoImovel.getNome()}; 
        	notificacaoService.cadastrarNotificacao(imovel.getUsuarioSolicitante().getId(), 
        			  								imovel.getImovel().getId(),
        			  								AcaoNotificacaoEnum.PARCERIA.getRotulo(),
													MessageUtils.getMessageParams("msg.notificacao.parceria.usuario.aceite", params),
													TipoNotificacaoEnum.IMOVEL.getRotulo(),
													imovel.getUsuarioDonoImovel().getId());													
														
            
            notaService.cadastrarNota(MessageUtils.getMessage("msg.usuario.para.comecar.trabalhar.nota.imovel"), 
            						  imovel.getUsuarioSolicitante(), 
            						  imovel.getImovel(), 
            						  new Date(),
            						  NotaAcaoEnum.PARCERIA.getRotulo());            
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
	private void atualizarParceria(Parceria parceria) {
		dao.save(parceria);
	}

	@Override
	public Parceria  recuperarImovelParceriaSelecionadoPorIdImovel(Long idImovel) {		
		List<Parceria> lista = dao.findParceriaByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
		if ( ! CollectionUtils.isEmpty(lista) ){
        	Parceria parceria = (Parceria) lista.get(0);        	
            return parceria;
        }    
        else
           return null;
	}

	@Override
	public List<Parceria> recuperarMinhasSolParceriaoAceitasPorUsuarioSolicitante(Long idUsuarioSolicitante, ParceriaForm form) {		
		return dao.findParceriaByIdUsuarioSolicitanteByStatus(idUsuarioSolicitante, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
	}

	@Override
	public List<Parceria> recuperarMinhasSolParceriaAceitasPorDonoImovel(Long idDonoImovel, ParceriaForm form) {		
		return  dao.findParceriaByIdDonoImovelByStatus(idDonoImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(),   form);          
	}

	@Override
	public List<Parceria> recuperarTodasSolParceriaAceitas(Long idUsuario, ParceriaForm form) {		
		List<Parceria> listaDonoImovel = dao.findParceriaByIdDonoImovelByStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(),   form);
        List<Parceria> listaUsuarioSolicitante = dao.findParceriaByIdUsuarioSolicitanteByStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(),   form);        
        List<Parceria> listaFinal = new ArrayList<Parceria>();        
        listaFinal.addAll(listaDonoImovel);     
        
        for (Parceria parceria : listaUsuarioSolicitante ){            
            if ( ! listaFinal.contains(parceria)){            	 
                 listaFinal.add(parceria);
            }     
        }
        return listaFinal; 
	}

	@Override
	public List<Parceria> recuperarTodasSolParceriasAceitasDistintas(Long idUsuario, ParceriaForm form) {		
		List lista = dao.findParceriaBySolAceitasDistintas(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
        List<Parceria> listaFinal = new ArrayList<Parceria>();
        TreeSet listaId = new TreeSet();
        String id = "";
        Parceria parceria = null;		
        for (Iterator iter = lista.iterator();iter.hasNext();){
            id = iter.next().toString();
            if ( ! listaId.contains(id)) {
            	parceria = recuperarImovelParceriaSelecionadoPorIdImovel(Long.parseLong(id));	            	
                listaFinal.add(parceria);    
            }            
        }
        return listaFinal;
	}
	
	@Override
	public Parceria  recuperarMinhasSolicitacoesPorUsuarioSolPorImovelParceria(Long idUsuario, Long idImovel) {		
		return dao.findParceriaByIdUsuarioSolicitanteByIdImovel(idUsuario, idImovel);
	}

	
	@Override
	public String isParceria(Long idImovel) {		
		List<Parceria> lista = dao.findParceriaByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo() );
        if (! CollectionUtils.isEmpty(lista))
            return "S";
        else
            return "N"; 
	}

	
	@Override
	public List<Usuario> recuperarUsuariosParceria(Long idImovel) {		
		List<Parceria> lista = dao.findParceriaByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
        List<Usuario> listaUsuario = new ArrayList<Usuario>();        
        for (Parceria imovel : lista){
            listaUsuario.add(imovel.getUsuarioSolicitante());
        }        
        return listaUsuario;
	}

	
	@Override
	public List<Imovel> checarImoveisMaisReceberamSolParceriaPorPeriodo(AdministracaoForm form) {		
		List lista = dao.checarImoveisComMaisSolParceriaPeriodo(form);
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
	public List<Parceria> recuperarSolicitacoesParceriaRecebidasPorIdImovelPorStatus(long idImovel) {		
		List<Parceria> lista = dao.findParceriaByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo());
		List<Parceria> listaFinal = new ArrayList<Parceria>();
        for (Parceria parceria : lista){ 
        	parceria.setQuantTotalParceiros(AppUtil.recuperarQuantidadeLista(recuperarUsuarioParceiroPorIdImovel(parceria.getImovel().getId() ,"")));                
            listaFinal.add(parceria);                         
        }
		return listaFinal;
	}
	
	
	@Override
	public List<Imovel> recuperarMinhasSolParceriaAceitasPorUsuarioSolicitantePorPerfil(Long idPerfil, PerfilForm frm) {		
		 return dao.findParceriaAceitosPorUsuarioSolicitantePorPerfil(idPerfil, StatusImovelCompartilhadoEnum.ACEITA.getRotulo() , frm);
	}

	
	@Override
	public boolean checarEnviouSolicitacaoParceria(Long idUsuario,Long idImovel) {		
		Parceria imovel = dao.findParceriaByIdUsuarioSolicitanteByIdImovel(idUsuario, idImovel);        
        if ( imovel != null )
            return true;
        else        
            return false;    
	}

	
	@Override
	public String validarSolicitacaoParceria(Long idUsuario, Long idImovel, String perfilDonoImovel, String descCompartilhamento) {		
		 
        if (StringUtils.isNullOrEmpty(descCompartilhamento))          
            return MessageUtils.getMessage("msg.erro.parceria.descricao.vazia");
        
        boolean jaEnviouSolicitacao = this.checarEnviouSolicitacaoParceria(idUsuario, idImovel);
        if (jaEnviouSolicitacao)                 
            return MessageUtils.getMessage("msg.parceria.sol.sucesso.enviada.antes");
        
        Imovel imovel = imovelDao.findImovelById(idImovel);
        if ( imovel.getAtivado().equals("N"))
        	return MessageUtils.getMessage("msg.parceria.intermediacao.sol.imovel.desativado");
                   
        boolean existeContato = contatoService.checarExisteContato(idUsuario, imovel.getUsuario().getId());    
        if (! existeContato )
        	return MessageUtils.getMessage("msg.parceria.intermediacao.sol.erro.enviada.sem.contato");                       
       
        if (imovel.getUsuario().getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
        	long quant = dao.findQuantParceriaByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
        	if ( quant > 0 )
        		return MessageUtils.getMessage("msg.parceria.sol.erro.imovel.existente");
        }                     
             
        return "";
	}

	
	@Override
	public EmailImovel notificarSolicitacaoParceria(Long idImovel) {		
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
	public EmailImovel notificarAceiteCompartilhamento(Parceria parceria) {		
		Imovel imovel = imovelDao.findImovelById(parceria.getImovel().getId());
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioSolicitante = parceria.getUsuarioSolicitante();
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
	public Parceria  recuperarUltimaSolicitacaoParceriaMeusImoveisPorIdImovel(Long idImovel) {
        return dao.findLastImovelParceriaMeusImoveisByIdImovel(idImovel);
	}

	
	@Override
	@Transactional
	public void atualizarPontuacaoNegocioParceria(Long idImovel, String novoStatus) {
		
		
	}

	
	@Override
	public List<Parceria> recuperarUsuarioParceiroPorIdImovel(Long idImovel, String status) {	
        return dao.findParceriaByIdImovelByStatus(idImovel, status);
	}

	
	@Override
	public boolean checarExisteParceriaAceitaPorIdUsuario(Long idUsuario, ParceriaForm form) {		
		List<Parceria> listaDonoImovel = dao.findParceriaByIdDonoImovelByStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo() , form);
        if ( !CollectionUtils.isEmpty(listaDonoImovel)  )
            return true;
        else{
            List<Parceria> listaUsuarioSolicitante = dao.findParceriaByIdUsuarioSolicitanteByStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo() , form);
            if ( !CollectionUtils.isEmpty(listaUsuarioSolicitante)  )            
                return true;                
        }            
        return false;
	}
	
	@Override
	public long checarQuantidadeParceriaAceitaPorIdUsuario(Long idUsuario) {		
		return dao.findQuantidadeParceriaPorUsuarioPorStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo() );	
	}

	
	@Override
	public List<Parceria> recuperarTodasParceriasPorUsuario(Long idPerfil, String status) {		
		return dao.findAllParceriaByIdUsuarioByStatus(idPerfil, status);
	}


	@Override
	public List<Parceria> filtrarParceria(Long idUsuario, ParceriaForm form) {
		List<Parceria> listaFinal = new ArrayList<Parceria>();
		List<Parceria> lista = null;		
		Imovel imovel = null;
		if ( form.getTipoLista().equals("parceriaSolRecebida")){
			return 	dao.filterSolParceria(idUsuario, form);			
		}	
		else if ( form.getTipoLista().equals("parceriaMinhasSol")) {
			return 	dao.filterMinhasSolParceria(idUsuario, form);
		}	
		else if ( form.getTipoLista().equals("parceriaAceita")) {
			lista = dao.filterParceriaAceitas(idUsuario, form );
			TreeSet listaId = new TreeSet();
			for (Parceria parceria : lista){
				 if (! listaId.contains(parceria.getImovel().getId())){		
					 listaFinal.add(parceria);
					 listaId.add(parceria.getImovel().getId());
				 }				
			}
			return listaFinal;
		}	
		else
			return null;
	}

	@Override
	public List<?> ordenarAgruparParcerias(Long idUsuario,	ParceriaForm form) {
		if ( form.getOpcaoVisualizacao().equals("agruparUsuarios"))
			return this.agruparUsuariosParceria(idUsuario, form);			
		else if ( form.getOpcaoVisualizacao().equals("agruparImoveis"))			
			return this.agruparImoveisParceria(idUsuario, form);
		else
			return null;
	}
	
	
	@Override
	public List<Imovel> agruparImoveisParceria(Long idUsuarioSessao, ParceriaForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = null;
		if ( form.getTipoLista().equals("parceriaAceita")){
			// lista dos IdImovel cujo usuario sessao tenha aceito parceria de um imovel (tanto como dono imovel como usuario solicitante da parceria)
			lista = dao.findParceriasByStatusByIdUsuarioDistinct(idUsuarioSessao, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);
		}
		else if ( form.getTipoLista().equals("parceriaSolRecebida")){
			// lista dos IdImovel cujo usuario sessao tenha solicitado parceria de um imovel 
			lista = dao.findParceriasSolicitacoesRecebidasByStatusByIdUsuarioDistinct(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(),   form);
			
		}
		else if ( form.getTipoLista().equals("parceriaMinhasSol")){
			// lista dos IdImovel cujo usuario sessao tenha enviado solicitacao parceria de um imovel 
			lista = dao.findParceriasMinhasSolicitacoesByStatusByIdUsuarioDistinct(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(),   form);
		}
		
		Object[] obj = null;		
		Imovel imovel = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next();			
			imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
			imovel.setQuantImovelParceria(Integer.parseInt(obj[1].toString()));
			imovel.setQuantNovosImovelParceria(dao.findQuantidadeNovosParcerias(Long.parseLong(obj[0].toString())));				
			listaFinal.add(imovel);
		}
		
		return listaFinal;
	}


	@Override
	public List<Usuario> agruparUsuariosParceria(Long idUsuarioSessao, ParceriaForm form) {

		List<Usuario> listaFinal = new ArrayList<Usuario>();
		List lista = null;
		if ( form.getTipoLista().equals("parceriaAceita")){
			lista = dao.findUsuariosImoveisCompartilhadosByStatusByIdUsuario(idUsuarioSessao, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(),   form);
		}
		else if ( form.getTipoLista().equals("parceriaSolRecebida")){
			// lista dos IdUsuario que solicitou parceria com o usuario sessao 			
			lista = dao.findUsuariosParceriaByStatusByIdDonoImovel(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(),   form);			
		}
		else if ( form.getTipoLista().equals("parceriaMinhasSol")){
			// lista dos IdUsuario que recebeu solicitacao de  parceria com o usuario sessao
			lista = dao.findUsuariosImoveisCompartilhadosByStatusByIdUsuario(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(),   form);			
		}
		
		Object[] obj = null;			 
		Usuario usuario = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next(); 				
			usuario = usuarioDao.findUsuario(Long.parseLong(obj[1].toString()));
			usuario.setQuantImovelParceria(Integer.parseInt(obj[0].toString()));
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
	public List<Parceria> recuperarParceriaPorUsuario(Long idUsuario, Long idUsuario2) {		
		return dao.findParceriasByIdUsuarioByIdUsuario2(idUsuario, idUsuario2);
	}

	
	@Override
	public List<Parceria> recuperarTodosImovelParceriaPorImovel(Long idImovel) {		
		return dao.findParceriaByIdImovel(idImovel);	
	}


	@Override
	@Transactional
	public void excluirSolicitacaoParceria(Long idUsuario, long idImovel) {
		Parceria imovel = dao.findParceriaByIdUsuarioSolicitanteByIdImovel(idUsuario, idImovel);
		dao.delete(imovel);		
	}


	@Override
	public Usuario recuperarUsuarioParceria(Long idImovel) {
		List<Parceria> lista = dao.findParceriaByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo() );
		if ( ! CollectionUtils.isEmpty(lista) ){
			Parceria imovel = (Parceria)lista.get(0);
			return imovel.getUsuarioSolicitante();
		}
		else
			return null;
	}
	
	@Override
	public List<Usuario> filtrarAgruparUsuario(Long idUsuario, ParceriaForm form){		
		return this.agruparUsuariosParceria(idUsuario, form);
	}
	
	
	@Override
	public List<Imovel>  filtrarAgruparImoveis(Long idUsuario,  ParceriaForm form){		
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = null;
	
		if ( form.getTipoLista().equals("parceriaAceita")){					
			lista = dao.filterFindParceriaByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), form);								
		}
		else if ( form.getTipoLista().equals("parceriaSolRecebida")){					
			lista = dao.filterFindParceriaSolicitacoesRecebidasByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);
			
		}
		else if ( form.getTipoLista().equals("parceriaMinhasSol")){					
			lista = dao.filterFindParceriaMinhasSolicitacoesByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), form);
		}
			
		
		if (! CollectionUtils.isEmpty(lista) ){
			Object[] obj = null;		
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();			
				imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
				imovel.setQuantImovelParceria(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovosImovelParceria(dao.findQuantidadeNovosParcerias(Long.parseLong(obj[0].toString())));
				listaFinal.add(imovel);
			}			
			return listaFinal;
		}
		else 
			return listaFinal;		
	}	

	@Override
	@Transactional
	public void atualizarStatusSolRecebidasParceria(Long idDonoImovel) {
		dao.updateStatusLeitura(idDonoImovel);
	}

	@Override
	public List<Parceria> recuperarTodosParceriaPorUsuario(Long idUsuario, Long idUsuario2) {
		List<Parceria> listaFinal = new ArrayList<Parceria>();
		List<Parceria> lista1 = dao.findParceriasByIdUsuarioByIdUsuario2(idUsuario,  idUsuario2);
		List<Parceria> lista2 = dao.findParceriasByIdUsuarioByIdUsuario2(idUsuario2, idUsuario);
		
		if ( ! CollectionUtils.isEmpty(lista1))
			listaFinal.addAll(lista1);
		
		if ( ! CollectionUtils.isEmpty(lista2))
			listaFinal.addAll(lista2);
		
		return listaFinal;
	}

}
