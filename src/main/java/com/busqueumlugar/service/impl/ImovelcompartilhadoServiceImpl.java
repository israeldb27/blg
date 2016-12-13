package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ImovelcompartilhadoDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.AcaoNotificacaoEnum;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.NotaAcaoEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusImovelCompartilhadoEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.TipoImovelCompartilhadoEnum;
import com.busqueumlugar.enumerador.TipoNotificacaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelcompartilhadoForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelcompartilhado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelcompartilhadoService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.RecomendacaoService;
import com.busqueumlugar.service.SeguidorService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.MessageUtils;

@Service
public class ImovelcompartilhadoServiceImpl implements ImovelcompartilhadoService {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelcompartilhadoServiceImpl.class);
	
	@Autowired
	private ImovelcompartilhadoDao dao;

	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ImovelDao imovelDao;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private NotaService notaService;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private SeguidorService seguidorService;
	
	@Autowired
	private RecomendacaoService recomendacaoService;


	
	public Imovelcompartilhado recuperarImovelcompartilhadoPorId(Long id) {
		return dao.findImovelcompartilhadoById(id);
	}

	@Override
	@Transactional
	public void cadastrarSolicitacaoCompartilhamento(Long idUsuario, String usuarioSolicitante, Long idImovel, String descricaoCompart) {		
		Imovelcompartilhado imovelComp = new Imovelcompartilhado();
        imovelComp.setDataSolicitacao(new Date());
        imovelComp.setUsuarioSolicitante(usuarioDao.findUsuario(idUsuario));
        imovelComp.setStatus(StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo());
        imovelComp.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        imovelComp.setImovel(imovelDao.findImovelById(idImovel));
        imovelComp.setUsuarioDonoImovel(imovelComp.getImovel().getUsuario());
        
        imovelComp.setDescricaoCompartilhamento(descricaoCompart);
        
        if ( imovelComp.getImovel().getUsuario().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
            imovelComp.setTipoImovelCompartilhado(TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
        else
            imovelComp.setTipoImovelCompartilhado(TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
        dao.save(imovelComp);
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarMinhasSolicitacoesCompartilhamento(Long idUsuarioSolicitante, ImovelcompartilhadoForm form) {
		return dao.findImovelcompartilhadoByIdUsuarioSolicitanteByStatusTipoCompartilhado(idUsuarioSolicitante, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarMinhasSolicitacoesIntermediacoes(Long idUsuarioSolicitante, ImovelcompartilhadoForm form) {
        return dao.findImovelcompartilhadoByIdUsuarioSolicitanteByStatusTipoCompartilhado(idUsuarioSolicitante, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo(), form);
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarSolicitacoesCompartilhamentoRecebidas(Long idDonoImovel, ImovelcompartilhadoForm form) {
        return dao.findImovelcompartilhadoByIdDonoImovelByStatusTipoCompartilhamento(idDonoImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(),TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarSolicitacoesIntermediacoesRecebidas(Long idDonoImovel, ImovelcompartilhadoForm form) {
        return dao.findImovelcompartilhadoByIdDonoImovelByStatusTipoCompartilhamento(idDonoImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo(), form);
	}

	
	@Override
	public List<Imovelcompartilhado> selecionarNovasSolicitacoesCompartilhamento(List<Imovelcompartilhado> lista) {		
		List<Imovelcompartilhado> listaFinal = new ArrayList<Imovelcompartilhado>();
        if (! CollectionUtils.isEmpty(lista)){
        	for (Imovelcompartilhado imovel : lista){		            
	            if ( imovel.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()))
	                listaFinal.add(imovel);                    
	        }
        }
                
        return listaFinal;
	}

	
	@Override
	@Transactional
	public boolean atualizarStatusImovelCompartilhado(Imovelcompartilhado imovelcompartilhado) {		
		if ( imovelcompartilhado.getStatusLeitura().equals(StatusLeituraEnum.NOVO.getRotulo()))
            imovelcompartilhado.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());
        else 
            imovelcompartilhado.setStatusLeitura(StatusLeituraEnum.NOVO.getRotulo());
        
        dao.save(imovelcompartilhado);
        return true;
	}
	
	
	@Override
	@Transactional
	public void excluiImovelCompartilhado(Long id){
		//Imovelcompartilhado imovelcompartilhado = dao.findImovelcompartilhadoById(id);
		//dao.delete(imovelcompartilhado);
		dao.delete(Imovelcompartilhado.class, id);
	}
	
	@Override
	@Transactional
	public void excluiImovelCompartilhado(Imovelcompartilhado imovelcompartilhado){
		dao.delete(imovelcompartilhado);
	}
	
	
	@Override
	public long checarQuantidadeNovasSolImoveisCompartilhamentoPorTipoCompartilhamento(Long idDonoImovel, String tipoCompartilhamento){
		return dao.checarQuantidadeImovelCompartilhadoSolRecebidaByDonoImovelByStatusByStatusLeituraByTipoCompart(idDonoImovel, 
																												 StatusLeituraEnum.NOVO.getRotulo(),
																												 StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), 
																												 tipoCompartilhamento);
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarSolicitacoesCompartilhamentoRecebidasPorIdImovel(Long idImovel) {
        return dao.findImovelcompartilhadoByIdImovel(idImovel);	
	}

	
	@Override
	//@Transactional
	public void atualizarStatusCompartilhamento(Long idImovelcompartilhado, String status , String tipoCompart) {		
		Imovelcompartilhado imovel = this.recuperarImovelcompartilhadoPorId(idImovelcompartilhado);
        imovel.setStatus(status);
        if ( status.equals(StatusImovelCompartilhadoEnum.ACEITA.getRotulo())){
            imovel.setDataResposta(new Date());
            imovel.setStatusLeitura(StatusLeituraEnum.LIDO.getRotulo());            
            this.dao.update(imovel);
            Usuario donoImovel = imovel.getUsuarioDonoImovel();            
            if ( tipoCompart.equals(TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo())) {
            	   Object[] params = {donoImovel.getNome()};            	   
            	   notificacaoService.cadastrarNotificacao(imovel.getUsuarioSolicitante().getId(), 
														   imovel.getImovel().getId(),
														   AcaoNotificacaoEnum.INTERMEDIACAO.getRotulo(),
														   MessageUtils.getMessageParams("msg.notificacao.intermediacao.usuario.aceite", params),														   	            										
														   TipoNotificacaoEnum.IMOVEL.getRotulo(),
														   imovel.getUsuarioDonoImovel().getId()); 
            }
            else if ( tipoCompart.equals(TipoImovelCompartilhadoEnum.PARCERIA.getRotulo())) {
            	Object[] params = {donoImovel.getNome()}; 
            	notificacaoService.cadastrarNotificacao(imovel.getUsuarioSolicitante().getId(), 
            			  								imovel.getImovel().getId(),
            			  								AcaoNotificacaoEnum.PARCERIA.getRotulo(),
														MessageUtils.getMessageParams("msg.notificacao.parceria.usuario.aceite", params),
														TipoNotificacaoEnum.IMOVEL.getRotulo(),
														imovel.getUsuarioDonoImovel().getId());													
														
            }
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
	private void atualizarImovelCompartilhado(Imovelcompartilhado imovelcompartilhado) {
		dao.save(imovelcompartilhado);
	}

	@Override
	public Imovelcompartilhado recuperarImovelCompartilhadoPorIdImovel(Long idImovelCompartilhado) {		
		return dao.findImovelcompartilhadoById(idImovelCompartilhado);
	}

	@Override
	public Imovelcompartilhado recuperarImovelCompartilhadoSelecionadoPorIdImovel(Long idImovel) {		
		List<Imovelcompartilhado> lista = dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, 
																											  StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), 
																											  TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
        if ( ! CollectionUtils.isEmpty(lista) )
            return lista.get(0);
        else
           return null;
	}

	@Override
	public Imovelcompartilhado recuperarImovelIntermediadoSelecionadoPorIdImovel(Long idImovel) {		
		List<Imovelcompartilhado> lista = dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, 
																											  StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), 
																											  TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
		if ( ! CollectionUtils.isEmpty(lista) ){
        	Imovelcompartilhado imovel = (Imovelcompartilhado) lista.get(0);        	
            return imovel;
        }    
        else
           return null;
	}

	@Override
	public List<Imovelcompartilhado> recuperarMinhasSolCompartilhamentoAceitasPorUsuarioSolicitante(Long idUsuarioSolicitante, ImovelcompartilhadoForm form) {		
		return dao.findImovelcompartilhadoByIdUsuarioSolicitanteByStatusTipoCompartilhado(idUsuarioSolicitante, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
	}

	@Override
	public List<Imovelcompartilhado> recuperarMinhasSolCompartilhamentoAceitasPorDonoImovel(Long idDonoImovel, ImovelcompartilhadoForm form) {		
		return  dao.findImovelcompartilhadoByIdDonoImovelByStatusTipoCompartilhamento(idDonoImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);          
	}

	@Override
	public List<Imovelcompartilhado> recuperarTodasSolCompartilhamentoAceitas(Long idUsuario, ImovelcompartilhadoForm form) {		
		List<Imovelcompartilhado> listaDonoImovel = dao.findImovelcompartilhadoByIdDonoImovelByStatusTipoCompartilhamento(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
        List<Imovelcompartilhado> listaUsuarioSolicitante = dao.findImovelcompartilhadoByIdUsuarioSolicitanteByStatusTipoCompartilhado(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);        
        List<Imovelcompartilhado> listaFinal = new ArrayList<Imovelcompartilhado>();        
        listaFinal.addAll(listaDonoImovel);     
        
        for (Imovelcompartilhado imovelcompartilhado : listaUsuarioSolicitante ){            
            if ( ! listaFinal.contains(imovelcompartilhado)){            	 
                 listaFinal.add(imovelcompartilhado);
            }     
        }
        return listaFinal; 
	}

	@Override
	public List<Imovelcompartilhado> recuperarTodasSolCompartilhamentoAceitasDistintas(Long idUsuario, ImovelcompartilhadoForm form) {		
		List lista = dao.findImovelcompartilhadoBySolAceitasDistintasTipoImovelCompartilhado(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
        List<Imovelcompartilhado> listaFinal = new ArrayList<Imovelcompartilhado>();
        TreeSet listaId = new TreeSet();
        String id = "";
        Imovelcompartilhado imovelcompartilhado = null;		
        for (Iterator iter = lista.iterator();iter.hasNext();){
            id = iter.next().toString();
            if ( ! listaId.contains(id)) {
				imovelcompartilhado = recuperarImovelCompartilhadoSelecionadoPorIdImovel(Long.parseLong(id));							
                listaFinal.add(imovelcompartilhado);    
            }            
        }
        return listaFinal;
	}

	@Override
	public List<Imovelcompartilhado> recuperarTodasSolIntermediacoesAceitasDistintas(Long idUsuario, ImovelcompartilhadoForm form) {		
		List lista = dao.findImovelcompartilhadoBySolAceitasDistintasTipoImovelCompartilhado(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo(), form);
        List<Imovelcompartilhado> listaFinal = new ArrayList<Imovelcompartilhado>();
        TreeSet listaId = new TreeSet();
        String id = "";
        Imovelcompartilhado imovelcompartilhado = null;		
        for (Iterator iter = lista.iterator();iter.hasNext();){
            id = iter.next().toString();
            if ( ! listaId.contains(id)) {
                imovelcompartilhado = recuperarImovelIntermediadoSelecionadoPorIdImovel(Long.parseLong(id));									
                listaFinal.add(imovelcompartilhado);    
            }            
        }
        return listaFinal;
	}

	@Override
	public Imovelcompartilhado recuperarMinhasSolicitacoesPorUsuarioSolPorImovel(Long idUsuario, Long idImovel) {		
		return dao.findImovelcompartilhadoByIdUsuarioSolicitanteByIdImovelTipoCompart(idUsuario, idImovel, TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
	}

	
	@Override
	public Imovelcompartilhado recuperarMinhasSolicitacoesPorUsuarioSolPorImovelIntermediacao(Long idUsuario, Long idImovel) {		
		return dao.findImovelcompartilhadoByIdUsuarioSolicitanteByIdImovelTipoCompart(idUsuario, idImovel, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
	}

	
	@Override
	public String isImovelCompartilhado(Long idImovel) {		
		List<Imovelcompartilhado> lista = dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
        if (! CollectionUtils.isEmpty(lista))
            return "S";
        else
            return "N"; 
	}

	
	@Override
	public List<Usuario> recuperarUsuariosImoveisCompartilhados(Long idImovel) {		
		List<Imovelcompartilhado> lista = dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, 
																											  StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), 
																											  TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
        List<Usuario> listaUsuario = new ArrayList<Usuario>();        
        for (Imovelcompartilhado imovel : lista){
            listaUsuario.add(imovel.getUsuarioSolicitante());
        }        
        return listaUsuario;
	}

	
	@Override
	public List<Imovel> checarImoveisMaisReceberamSolCompartilhamentoPorPeriodo(AdministracaoForm form, String tipoCompartilhamento) {		
		List lista = dao.checarImoveisComMaisSolCompartilhamentoPeriodo(form, tipoCompartilhamento);
        List<Imovel> listaFinal = new ArrayList<Imovel>();        
        if ( ! CollectionUtils.isEmpty(lista) ){
            Imovel imovel = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));
                listaFinal.add(imovel);
            }
        }
        return listaFinal;
	}
	
	@Override
	public List<Imovelcompartilhado> recuperarTodosImoveisCompartilhadosAceitos(Imovelcompartilhado imovelCompartilhado) {		
		return dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(imovelCompartilhado.getImovel().getId(), StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarTodosImoveisCompartilhadosAceitos(Long idImovel) {		
		return dao.findImovelCompartilhadoByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(long idImovel) {		
		List<Imovelcompartilhado> lista = dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
		List<Imovelcompartilhado> listaFinal = new ArrayList<Imovelcompartilhado>();
        for (Imovelcompartilhado imovelcompartilhado : lista){ 
            imovelcompartilhado.setQuantTotalParceiros(AppUtil.recuperarQuantidadeLista(recuperarUsuarioParceiroImovelCompartilhadoPorIdImovel(imovelcompartilhado.getImovel().getId() ,"")));                
            listaFinal.add(imovelcompartilhado);                         
        }
		return listaFinal;
	}
	
	@Override
	public List<Imovelcompartilhado> recuperarTodasSolicitacoesPorIdImovel(Long idImovel) {
		return dao.findImovelcompartilhadoByIdImovel(idImovel);
	}
	
	
	@Override
	public List<Imovelcompartilhado> recuperarSolicitacoesParceriasRecebidasPorIdImovelPorStatus(long idImovel) {
		return dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
	}
	
	
	@Override
	public List<Imovel> recuperarMinhasSolCompartilhamentoAceitasPorUsuarioSolicitantePorPerfil(Long idPerfil, PerfilForm frm) {		
		 return dao.findImovelCompartilhadoAceitosPorUsuarioSolicitantePorPerfil(idPerfil,  StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), frm);
	}

	
	@Override
	public boolean checarEnviouSolicitacaoCompartilhamento(Long idUsuario,Long idImovel, String tipoCompart) {		
		Imovelcompartilhado imovel = dao.findImovelcompartilhadoByIdUsuarioSolicitanteByIdImovelTipoCompart(idUsuario, idImovel, tipoCompart);        
        if ( imovel != null )
            return true;
        else        
            return false;    
	}

	
	@Override
	public String validarSolicitacaoCompartilhamento(Long idUsuario, Long idImovel, String perfilDonoImovel, String descCompartilhamento) {		
		String msg = "";
        
        String tipoCompart = null;
        if ( perfilDonoImovel.equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
            tipoCompart = TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo();
        else
            tipoCompart = TipoImovelCompartilhadoEnum.PARCERIA.getRotulo();
        
        if (( descCompartilhamento == null ) || ( descCompartilhamento != null && descCompartilhamento.equals("")))
            if ( tipoCompart.equals("P"))
            	msg = MessageUtils.getMessage("msg.erro.parceria.descricao.vazia");
            else
            	msg =  MessageUtils.getMessage("msg.erro.intermediacao.descricao.vazia");
        
        if ( msg.equals("")){
            Imovel imovelAtiv = imovelService.recuperarImovelPorid(idImovel);
            if ( imovelAtiv.getAtivado().equals("N"))
            	msg = MessageUtils.getMessage("msg.parceria.intermediacao.sol.imovel.desativado");
        }
            
        if ( msg.equals("")){
            boolean jaEnviouSolicitacao = this.checarEnviouSolicitacaoCompartilhamento(idUsuario, idImovel, tipoCompart);
            if (jaEnviouSolicitacao) {
                if ( tipoCompart.equals("P"))
                	msg = MessageUtils.getMessage("msg.parceria.sol.sucesso.enviada.antes");   
                else
                	msg = MessageUtils.getMessage("msg.intermediacao.sol.sucesso.enviada.antes");
            }
        }
        
        if ( msg.equals("")){            
            boolean existeContato = contatoService.checarExisteContato(idUsuario, imovelService.recuperarImovelPorid(idImovel).getUsuario().getId());    
            if (! existeContato )
            	msg = MessageUtils.getMessage("msg.parceria.intermediacao.sol.erro.enviada.sem.contato");                        
        }
        
        if ( msg.equals("")){
            Usuario usuarioDonoImovel = usuarioService.recuperarUsuarioPorId(imovelService.recuperarImovelPorid(idImovel).getUsuario().getId());
            if (usuarioDonoImovel.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
                List<Imovelcompartilhado> lista = dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
                if ( ! CollectionUtils.isEmpty(lista)  )
                	msg = MessageUtils.getMessage("msg.parceria.sol.erro.imovel.existente");
            }                     
        }     
        return msg;
	}

	
	@Override
	public EmailImovel notificarSolicitacaoCompartilhamento(Long idImovel) {		
		Imovel imovel = imovelService.recuperarImovelPorid(idImovel);
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
	public EmailImovel notificarAceiteCompartilhamento(Imovelcompartilhado imovelCompartilhado) {		
		Imovel imovel = imovelService.recuperarImovelPorid(imovelCompartilhado.getImovel().getId());
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioSolicitante = imovelCompartilhado.getUsuarioSolicitante();
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
	public Imovelcompartilhado recuperarUltimaSolicitacaoParceriaMeusImoveisPorIdImovel(Long idImovel) {
        return dao.findLastImovelParceriaMeusImoveisByIdImovel(idImovel);
	}

	
	@Override
	@Transactional
	public void atualizarPontuacaoNegocioParceria(Long idImovel, String novoStatus) {
		Imovel imovel = imovelService.recuperarImovelPorid(idImovel);
        // checando se o imovel tem parceria
        List<Imovelcompartilhado> lista = recuperarTodosImoveisCompartilhadosAceitos(idImovel);
        // cliente possui parceria com 1 corretor ou imobiliaria. Ent�o o usuario parceiro estar� recebendo 1 ponto de negocioacao de imovel com sucesso
        if (! CollectionUtils.isEmpty(lista)){ 
            for (Imovelcompartilhado imovelcompartilhado : lista ){
                if ( novoStatus.equals("fechado") )
                    usuarioService.atualizarPontuacaoNegociacaoImovel(imovelcompartilhado.getUsuarioSolicitante(), "sucesso");
                 else if ( novoStatus.equals("aberto"))
                    usuarioService.atualizarPontuacaoNegociacaoImovel(imovelcompartilhado.getUsuarioSolicitante(), "revogado");
            }
        }
		
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarUsuarioParceiroImovelCompartilhadoPorIdImovel(Long idImovel, String aceita) {		
		return dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
	}

	
	@Override
	public boolean checarExisteParceriaAceitaPorIdUsuario(Long idUsuario, ImovelcompartilhadoForm form) {		
		List<Imovelcompartilhado> listaDonoImovel = dao.findImovelcompartilhadoByIdDonoImovelByStatusTipoCompartilhamento(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
        if ( !CollectionUtils.isEmpty(listaDonoImovel)  )
            return true;
        else{
            List<Imovelcompartilhado> listaUsuarioSolicitante = dao.findImovelcompartilhadoByIdUsuarioSolicitanteByStatusTipoCompartilhado(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
            if ( !CollectionUtils.isEmpty(listaUsuarioSolicitante)  )            
                return true;                
        }            
        return false;
	}
	
	@Override
	public long checarQuantidadeImovelCompartilhadoAceitaPorIdUsuarioPorTipoCompartilhamento(Long idUsuario, String tipoCompartilhado) {		
		return dao.findQuantidadeImovelCompartilhadoPorUsuarioPorStatusPorTipoCompartilhado(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), tipoCompartilhado);	
	}

	
	@Override
	public List<Imovelcompartilhado> recuperarTodosImoveisCompartilhadosPorUsuario(Long idPerfil, String status) {		
		return dao.findAllImovelCompartilhadoByIdUsuarioByStatus(idPerfil, status);
	}


	@Override
	public List<Imovelcompartilhado> filtrarIntermediacao(Long idUsuario, ImovelcompartilhadoForm form) {		
		if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
			return dao.filterSolImoveisCompartilhado(idUsuario, form, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());		
		}	
		else if ( form.getTipoLista().equals("intermediacaoMinhasSol")) {
			return dao.filterMinhasSolImoveisCompartilhado(idUsuario, form, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());			 
		}	
		else if ( form.getTipoLista().equals("intermediacaoAceita")) {			
			return dao.filterImoveisCompartilhadoAceitas(idUsuario,  form, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());				
		}	
		else
			return null;
	}


	@Override
	public List<Imovelcompartilhado> filtrarParceria(Long idUsuario, ImovelcompartilhadoForm form) {
		List<Imovelcompartilhado> listaFinal = new ArrayList<Imovelcompartilhado>();
		List<Imovelcompartilhado> lista = null;		
		Imovel imovel = null;
		if ( form.getTipoLista().equals("parceriaSolRecebida")){
			return dao.filterSolImoveisCompartilhado(idUsuario, form, TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());			
		}	
		else if ( form.getTipoLista().equals("parceriaMinhasSol")) {
			return dao.filterMinhasSolImoveisCompartilhado(idUsuario, form, TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
		}	
		else if ( form.getTipoLista().equals("parceriaAceita")) {
			lista = dao.filterImoveisCompartilhadoAceitas(idUsuario, form , TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
			TreeSet listaId = new TreeSet();
			for (Imovelcompartilhado imovelcompartilhado : lista){
				 if (! listaId.contains(imovelcompartilhado.getImovel().getId())){
					 listaFinal.add(imovelcompartilhado);
					 listaId.add(imovelcompartilhado.getImovel().getId());
				 }				
			}
			return listaFinal;
		}	
		else
			return null;
	}
	
	
	@Override
	public List<?> ordenarAgruparIntermediacoes(Long idUsuario,	ImovelcompartilhadoForm form) {
		if ( form.getOpcaoVisualizacao().equals("agruparUsuarios"))
			return this.agruparUsuariosIntermediacao(idUsuario, form);
		else if ( form.getOpcaoVisualizacao().equals("agruparImoveis"))
			return this.agruparImoveisIntermediacao(idUsuario, form);		
		else
			return null;
	}
	
	@Override
	public List<?> ordenarAgruparParcerias(Long idUsuario,	ImovelcompartilhadoForm form) {
		if ( form.getOpcaoVisualizacao().equals("agruparUsuarios"))
			return this.agruparUsuariosParceria(idUsuario, form);			
		else if ( form.getOpcaoVisualizacao().equals("agruparImoveis"))			
			return this.agruparImoveisParceria(idUsuario, form);
		else
			return null;
	}
	
	
	@Override
	public List<Imovel> agruparImoveisParceria(Long idUsuarioSessao, ImovelcompartilhadoForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = null;
		if ( form.getTipoLista().equals("parceriaAceita")){
			// lista dos IdImovel cujo usuario sessao tenha aceito parceria de um imovel (tanto como dono imovel como usuario solicitante da parceria)
			lista = dao.findImoveisCompartilhadoByStatusByIdUsuarioDistinct(idUsuarioSessao, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
		}
		else if ( form.getTipoLista().equals("parceriaSolRecebida")){
			// lista dos IdImovel cujo usuario sessao tenha solicitado parceria de um imovel 
			lista = dao.findImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
			
		}
		else if ( form.getTipoLista().equals("parceriaMinhasSol")){
			// lista dos IdImovel cujo usuario sessao tenha enviado solicitação parceria de um imovel 
			lista = dao.findImoveisCompartilhadoMinhasSolicitacoesByStatusByIdUsuarioDistinct(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
		}
		
		Object[] obj = null;		
		Imovel imovel = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next();			
			imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));
			imovel.setQuantImovelParceria(Integer.parseInt(obj[1].toString()));
			imovel.setQuantNovosImovelParceria(dao.findQuantidadeNovosImoveisCompartilhados(Long.parseLong(obj[0].toString())));			
			listaFinal.add(imovel);
		}
		
		return listaFinal;
	}


	@Override
	public List<Usuario> agruparUsuariosParceria(Long idUsuarioSessao, ImovelcompartilhadoForm form) {

		List<Usuario> listaFinal = new ArrayList<Usuario>();
		List lista = null;
		if ( form.getTipoLista().equals("parceriaAceita")){
			lista = dao.findUsuariosImoveisCompartilhadosByStatusByIdUsuario(idUsuarioSessao, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
		}
		else if ( form.getTipoLista().equals("parceriaSolRecebida")){
			// lista dos IdUsuario que solicitou parceria com o usuario sessao 			
			lista = dao.findUsuariosImoveisCompartilhadosByStatusByIdDonoImovel(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);			
		}
		else if ( form.getTipoLista().equals("parceriaMinhasSol")){
			// lista dos IdUsuario que recebeu solicitação de  parceria com o usuario sessao
			lista = dao.findUsuariosImoveisCompartilhadosByStatusByIdUsuario(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);			
		}
		
		Object[] obj = null;			 
		Usuario usuario = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next(); 				
			usuario = usuarioService.recuperarUsuarioPorId(Long.parseLong(obj[1].toString()));
			usuario.setQuantImovelParceria(Integer.parseInt(obj[0].toString()));
			usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
			usuario.setQuantTotalContatos(contatoService.checarTotalContatosPorUsuarioPorStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
			usuario.setQuantTotalRecomendacoes(recomendacaoService.checarQuantidadeTotalRecomendacaoRecebidaPorStatus(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo()));
			usuario.setQuantTotalImoveis(imovelService.checarQuantMeusImoveis(usuario.getId()));
			usuario.setQuantTotalSeguidores(seguidorService.checarQuantidadeSeguidores(usuario.getId()));
			listaFinal.add(usuario);
		}
		
		return listaFinal;
	}


	@Override
	public List<Imovel> agruparImoveisIntermediacao(Long idUsuarioSessao,	ImovelcompartilhadoForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = null;
		if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
			// lista dos IdImovel cujo usuario sessao tenha solicitado intermediacao de um imovel 
			lista = dao.findImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo(), form);			
		}
		
		Object[] obj = null;		
		Imovel imovel = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next();			
			imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));
			imovel.setQuantImovelIntermediacao(Integer.parseInt(obj[1].toString()));
			imovel.setQuantNovosImovelIntermediacao(dao.findQuantidadeNovosImoveisCompartilhados(Long.parseLong(obj[0].toString())));
			listaFinal.add(imovel);
		}
		
		return listaFinal;
	}


	@Override
	public List<Usuario> agruparUsuariosIntermediacao(Long idUsuarioSessao,	ImovelcompartilhadoForm form) {

		List<Usuario> listaFinal = new ArrayList<Usuario>();
		List lista = null;
		if ( form.getTipoLista().equals("intermediacaoAceita")){							
			lista = dao.findUsuariosImoveisCompartilhadosByStatusByIdUsuario(idUsuarioSessao, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo(), form);
		}
		else if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
			// lista dos IdUsuario que solicitou intermediacao com o usuario sessao 			
			lista = dao.findUsuariosImoveisCompartilhadosByStatusByIdDonoImovel(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo(), form);			
		}
		else if ( form.getTipoLista().equals("intermediacaoMinhasSol")){
			// lista dos IdUsuario que recebeu solicitação de  intermediacao com o usuario sessao
			lista = dao.findUsuariosImoveisCompartilhadosByStatusByIdUsuario(idUsuarioSessao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo(), form);			
		}
		
		Object[] obj = null;			 
		Usuario usuario = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next(); 				
			usuario = usuarioService.recuperarUsuarioPorId(Long.parseLong(obj[1].toString()));
			usuario.setQuantImovelIntermediacao(Integer.parseInt(obj[0].toString()));
			usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
			usuario.setQuantTotalContatos(contatoService.checarTotalContatosPorUsuarioPorStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
			usuario.setQuantTotalRecomendacoes(recomendacaoService.checarQuantidadeTotalRecomendacaoRecebidaPorStatus(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo()));
			usuario.setQuantTotalImoveis(imovelService.checarQuantMeusImoveis(usuario.getId()));
			usuario.setQuantTotalSeguidores(seguidorService.checarQuantidadeSeguidores(usuario.getId()));
			listaFinal.add(usuario);
		}
		
		return listaFinal;
	}


	@Override
	public List<Imovelcompartilhado> recuperarImoveisCompartilhadoPorUsuario(Long idUsuario, Long idUsuario2, String tipoCompart) {		
		return dao.findImoveisCompartilhadosByIdUsuarioByIdUsuario2(idUsuario, idUsuario2, tipoCompart);
	}


	@Override
	public List<Imovelcompartilhado> recuperarUsuariosCompartilhadoPorImovel(Long idImovel) {		
		return dao.findImovelcompartilhadoByIdImovel(idImovel);
	}


	@Override
	public List<Imovelcompartilhado> recuperarTodosImovelParceriaPorImovel(Long idImovel) {		
		return dao.findImovelcompartilhadoByIdImovel(idImovel);	
	}


	@Override
	@Transactional
	public void excluirSolicitacaoImovelCompartilhado(Long idUsuario, long idImovel, String tipoCompart) {
		Imovelcompartilhado imovel = dao.findImovelcompartilhadoByIdUsuarioSolicitanteByIdImovelTipoCompart(idUsuario, idImovel, tipoCompart);
		dao.delete(imovel);		
	}


	@Override
	public Usuario recuperarUsuarioIntermediador(Long idImovel) {
		List<Imovelcompartilhado> lista = dao.findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
		if ( ! CollectionUtils.isEmpty(lista) ){
			Imovelcompartilhado imovel = (Imovelcompartilhado)lista.get(0);
			return imovel.getUsuarioSolicitante();
		}
		else
			return null;
	}
	
	@Override
	public List<Usuario> filtrarAgruparUsuario(Long idUsuario, ImovelcompartilhadoForm form, String tipoCompart){
		
		List<Usuario> listaAgruposUsuarios = null;
		if ( tipoCompart.equals("I")){
			listaAgruposUsuarios = this.agruparUsuariosIntermediacao(idUsuario, form);		
		}
		else if ( tipoCompart.equals("P")){
			listaAgruposUsuarios = this.agruparUsuariosParceria(idUsuario, form);
		}
		
		return listaAgruposUsuarios;		
	}
	
	
	@Override
	public List<Imovel>  filtrarAgruparImoveis(Long idUsuario, ImovelcompartilhadoForm form, String tipoCompart){		
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		List lista = null;
		
		if ( tipoCompart.equals(TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo())){
			if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
				lista = dao.filterFindImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo(), form);			
			}
		}
		else if ( tipoCompart.equals(TipoImovelCompartilhadoEnum.PARCERIA.getRotulo())){

				if ( form.getTipoLista().equals("parceriaAceita")){					
					lista = dao.filterFindImoveisCompartilhadoByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);								
				}
				else if ( form.getTipoLista().equals("parceriaSolRecebida")){					
					lista = dao.filterFindImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
					
				}
				else if ( form.getTipoLista().equals("parceriaMinhasSol")){					
					lista = dao.filterFindImoveisCompartilhadoMinhasSolicitacoesByStatusByIdUsuarioDistinct(idUsuario, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo(), form);
				}
		}	
		
		if (! CollectionUtils.isEmpty(lista) ){
			Object[] obj = null;		
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();			
				imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));
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
	public void atualizarStatusSolRecebidasImovelCompartilhado(Long idDonoImovel, String tipoCompart) {
		dao.updateStatusLeitura(idDonoImovel, tipoCompart);
	}

	@Override
	public List<Imovelcompartilhado> recuperarTodosImoveisCompartilhadoPorUsuario(Long idUsuario, Long idUsuario2, String tipoCompart) {
		List<Imovelcompartilhado> listaFinal = new ArrayList<Imovelcompartilhado>();
		List<Imovelcompartilhado> lista1 = dao.findImoveisCompartilhadosByIdUsuarioByIdUsuario2(idUsuario,  idUsuario2, tipoCompart);
		List<Imovelcompartilhado> lista2 = dao.findImoveisCompartilhadosByIdUsuarioByIdUsuario2(idUsuario2, idUsuario, tipoCompart);
		
		if ( ! CollectionUtils.isEmpty(lista1))
			listaFinal.addAll(lista1);
		
		if ( ! CollectionUtils.isEmpty(lista2))
			listaFinal.addAll(lista2);
		
		return listaFinal;
	}
}
