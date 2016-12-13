package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.RelatorioEnum;
import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.enumerador.StatusImovelEnum;
import com.busqueumlugar.enumerador.StatusPagtoOpcaoEnum;
import com.busqueumlugar.enumerador.TipoContatoEnum;
import com.busqueumlugar.enumerador.TipoContatoOpcaoEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.busqueumlugar.enumerador.TipoRelatorioSobreLocalidadeEnum;
import com.busqueumlugar.dao.RelatorioDao;
import com.busqueumlugar.dao.RelatorioacessoperfilDao;
import com.busqueumlugar.form.FiltroRelatorioForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Relatorio;
import com.busqueumlugar.model.RelatorioQuantAssinatura;
import com.busqueumlugar.model.RelatorioQuantidadeImoveisCriados;
import com.busqueumlugar.model.RelatorioVariacaoPrecoTipoImovel;
import com.busqueumlugar.model.Relatorioacessoperfil;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ParametrosIniciaisService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.RelatorioService;
import com.busqueumlugar.service.SeguidorService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.GenerateAccessToken;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.Select;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class RelatorioServiceImpl implements RelatorioService {
	
	private static final Logger log = LoggerFactory.getLogger(RelatorioServiceImpl.class);

	@Autowired
	private RelatorioDao dao;
	
	@Autowired
	private RelatorioacessoperfilDao relatorioacessoperfilDao;

	@Autowired
	private EstadosService estadosService;

	@Autowired
	private BairrosService bairrosService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private ContatoService contatoService;

	@Autowired
	private ParamservicoService paramservicoService;

	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private SeguidorService seguidorService;
	
	@Autowired
	private ParametrosIniciaisService parametrosIniciaisService;
	
	public Relatorio recuperarRelatorioPorId(Long id) {
		return dao.findRelatorioById(id);
	}

	
	public List<RelatorioQuantidadeImoveisCriados> listarQuantidadeImoveisPorLocalAcaoTipoImovel(Long idUsuario, RelatorioForm frm) {

        // Criar uma classe como objeto da lista retornada. Sugestao nome classe: RelatorioQuantidadeImoveisCriados
        List<RelatorioQuantidadeImoveisCriados> listaFinal = new ArrayList<RelatorioQuantidadeImoveisCriados>();
        List lista = null;
        if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo()))
        	lista = dao.listarQuantImoveisCriadosLocalizacaoAcaoTipoImovel(frm, null);        
        else if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){ // utilizar aqui Restrictions.in(..) na classe DAO
            List listaIds = contatoService.recuperarIDsMeusContatos(idUsuario);              
            if ( ! CollectionUtils.isEmpty(listaIds))
            	lista = dao.listarQuantImoveisCriadosLocalizacaoAcaoTipoImovel(frm, listaIds);
        }
        else if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){ 
        	List listaIds = seguidorService.recuperarIdsSeguidores(idUsuario);
        	if ( ! CollectionUtils.isEmpty(listaIds))
            	lista = dao.listarQuantImoveisCriadosLocalizacaoAcaoTipoImovel(frm, listaIds);  
        }           
        
        if ( ! CollectionUtils.isEmpty(lista)){
            RelatorioQuantidadeImoveisCriados relatorio = null;            
            int max = checarIndiceMaximo(frm) + 3;
            Object[] obj = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){                
            	obj = (Object[]) iter.next();
                relatorio = new RelatorioQuantidadeImoveisCriados();
                if ( max == 6 ) {
                	relatorio.setEstado(obj[max - 6].toString());
                	relatorio.setCidade(obj[max - 5].toString());
                	relatorio.setBairro(obj[max - 4].toString());
                }
                else if ( max == 5 ) {
                	relatorio.setEstado(obj[max - 5].toString());
                	relatorio.setCidade(obj[max - 4].toString());
                }
                else if ( max == 4 ) {
                	relatorio.setEstado(obj[max - 4].toString());
                }
                relatorio.setAcao(obj[max - 3].toString());
                relatorio.setTipoImovel(obj[max - 2].toString());
                relatorio.setQuantidade(Integer.parseInt(obj[max - 1].toString()));
                listaFinal.add(relatorio);
            }		
        }	
        return listaFinal;	
	}
	
	public List<RelatorioQuantidadeImoveisCriados> listarTipoImoveisMaisProcuradosPorLocalizacao(Long idUsuario, RelatorioForm frm) {			
        List<RelatorioQuantidadeImoveisCriados> listaFinal = new ArrayList<RelatorioQuantidadeImoveisCriados>();
        List lista = null;
        if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo()))
        	lista = dao.recuperarTiposImoveisQuantidade(frm, null);        
        else if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){ // utilizar aqui Restrictions.in(..) na classe DAO
            List listaIds = contatoService.recuperarIDsMeusContatos(idUsuario);              
            if ( ! CollectionUtils.isEmpty(listaIds))
            	lista = dao.recuperarTiposImoveisQuantidadesPorIdsUsuarios(frm, listaIds);
        }
        else if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){ 
        	List listaIds = seguidorService.recuperarIdsSeguidores(idUsuario);
        	if ( ! CollectionUtils.isEmpty(listaIds))
            	lista = dao.recuperarTiposImoveisQuantidadesPorIdsUsuarios(frm, listaIds);  
        }        
        
        if ( ! CollectionUtils.isEmpty(lista)){
            RelatorioQuantidadeImoveisCriados relatorio = null;            
            int max = checarIndiceMaximo(frm) + 3;
            Object[] obj = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){                
            	obj = (Object[]) iter.next();
                relatorio = new RelatorioQuantidadeImoveisCriados();
                if ( max == 6 ) {
                	relatorio.setEstado(obj[max - 6].toString());
                	relatorio.setCidade(obj[max - 5].toString());
                	relatorio.setBairro(obj[max - 4].toString());
                }
                else if ( max == 5 ) {
                	relatorio.setEstado(obj[max - 5].toString());
                	relatorio.setCidade(obj[max - 4].toString());
                }
                else if ( max == 4 ) {
                	relatorio.setEstado(obj[max - 4].toString());
                }
                relatorio.setAcao(obj[max - 3].toString());
                relatorio.setTipoImovel(obj[max - 2].toString());
                relatorio.setQuantidade(Integer.parseInt(obj[max - 1].toString()));
                listaFinal.add(relatorio);
            }		
        }
        return listaFinal;	
	}

	
	private int checarIndiceMaximo(RelatorioForm frm) {		
		int i = 0;
		if (frm.getIdEstado() > 0 ){
			i = 1;			
			if (frm.getIdCidade() > 0 )
				i++;
			
			if (frm.getIdBairro() > 0 )
				i++;
		}		
		return i;
	}


	public List<RelatorioVariacaoPrecoTipoImovel> recuperarVariacaoPrecoTipoImovel(Long idUsuario, RelatorioForm frm) {	

        List<RelatorioVariacaoPrecoTipoImovel> listaFinal = new ArrayList<RelatorioVariacaoPrecoTipoImovel>();
        List lista = null;
        if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo()))
        	lista = dao.recuperarVariacaoPrecoImovel(frm, null);        
        else if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){ // utilizar aqui Restrictions.in(..) na classe DAO
            List listaIds = contatoService.recuperarIDsMeusContatos(idUsuario);              
            if ( ! CollectionUtils.isEmpty(listaIds))
            	lista = dao.recuperarVariacaoPrecoImovel(frm, listaIds);
        }
        else if ( frm.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){ 
        	List listaIds = seguidorService.recuperarIdsSeguidores(idUsuario);
        	if ( ! CollectionUtils.isEmpty(listaIds))
            	lista = dao.recuperarVariacaoPrecoImovel(frm, listaIds);  
        }       

        if ( ! CollectionUtils.isEmpty(lista)){
                RelatorioVariacaoPrecoTipoImovel relatorio = null;
                Object[] obj = null;
                int max = checarIndiceMaximo(frm) + 5;
                for (Iterator iter = lista.iterator();iter.hasNext();){                
                    obj = (Object[]) iter.next();
                    relatorio = new RelatorioVariacaoPrecoTipoImovel();
                    if ( max == 8 ) {
                    	relatorio.setEstado(obj[max - 8].toString());
                    	relatorio.setCidade(obj[max - 7].toString());
                    	relatorio.setBairro(obj[max - 6].toString());
                    }
                    else if ( max == 7 ) {
                    	relatorio.setEstado(obj[max - 7].toString());
                    	relatorio.setCidade(obj[max - 6].toString());
                    }
                    else if ( max == 6 ) {
                    	relatorio.setEstado(obj[max - 6].toString());
                    }                    
                    
                    relatorio.setAcao(obj[max - 5].toString());
                    relatorio.setPerfilImovel(obj[max - 4].toString());
                    relatorio.setTipoImovel(obj[max - 3].toString());                    
                    relatorio.setValorImovelTotal(Double.parseDouble(obj[max - 2].toString()));
                    relatorio.setQuantidade(Integer.parseInt(obj[max - 1].toString()));
                    relatorio.setVariacaoPreco(relatorio.getValorImovelTotal() / relatorio.getQuantidade() );
                    listaFinal.add(relatorio);
                }		
        }
	
        return listaFinal;	
	}

	
	public List<Select> listarRelatoriosUsuarioSistemaPorOpcaoRelatorio(String itemRelatorio, String perfilUsuario) {
		List<Relatorio> lista = dao.findRelatorioUsuarioSistemaByItem(itemRelatorio, perfilUsuario);
		List<Select> listaFinal = new ArrayList<Select>();
		for (Relatorio relatorio : lista)			
			listaFinal.add(new Select(String.valueOf(relatorio.getId()), relatorio.getNome() ));
		
		return listaFinal;
	}

	
	public List<Relatorioacessoperfil> listarAcessoPerfisRelatorioSistema(Long idRelatorio) {		
	     return relatorioacessoperfilDao.findRelatorioAcessoPerfilByIdRelatorio(idRelatorio);
	}

	
	@Transactional
	public void adicionarPerfilRelatorio(RelatorioForm frm) {		
		// criar ainda a tabela que vai conter o relacionamento entre Relatorio e perfis associados
        if ( frm.getPerfilSelecionado().equals("todos") ){
            // adicionando perfil cliente
            Relatorioacessoperfil relAcessoPerfilCliente = new Relatorioacessoperfil();
            relAcessoPerfilCliente.setIdRelatorio(frm.getId());
            relAcessoPerfilCliente.setDataAcesso(new Date());
            relAcessoPerfilCliente.setPerfilUsuario(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo());
            relatorioacessoperfilDao.save(relAcessoPerfilCliente);

            // adicionando perfil corretor
            Relatorioacessoperfil relAcessoPerfilCorretor = new Relatorioacessoperfil();
            relAcessoPerfilCorretor.setIdRelatorio(frm.getId());
            relAcessoPerfilCorretor.setDataAcesso(new Date());
            relAcessoPerfilCorretor.setPerfilUsuario(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo());
            relatorioacessoperfilDao.save(relAcessoPerfilCorretor);

            // adicionando perfil imobiliaria
            Relatorioacessoperfil relAcessoPerfilImobiliaria = new Relatorioacessoperfil();
            relAcessoPerfilImobiliaria.setIdRelatorio(frm.getId());
            relAcessoPerfilImobiliaria.setDataAcesso(new Date());
            relAcessoPerfilImobiliaria.setPerfilUsuario(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo());
            relatorioacessoperfilDao.save(relAcessoPerfilImobiliaria);
        }
        else {
            Relatorioacessoperfil relAcessoPerfil = new Relatorioacessoperfil();
            relAcessoPerfil.setIdRelatorio(Long.parseLong(frm.getIdRelatorioSelecionado()));
            relAcessoPerfil.setDataAcesso(new Date());
            relAcessoPerfil.setPerfilUsuario(frm.getPerfilSelecionado());
            relatorioacessoperfilDao.save(relAcessoPerfil);            
        }
	}

	
	public String validarAdicionarPerfilRelatorio(RelatorioForm frm) {		
		String msgRetorno = "";
        // checar se o perfil selecionado estï¿½ vazio ou nï¿½o
        if (( frm.getPerfilSelecionado() == null ) || 
            ( frm.getPerfilSelecionado() != null && frm.getPerfilSelecionado().equals("")))
            msgRetorno  = MessageUtils.getMessage("msg.erro.selecionar.relatorio.obrigatorio");                    

        return msgRetorno;	
	}

	@Transactional
	public void cadastrarRelatorioSistema(RelatorioForm frm) {		
		Relatorio relatorio = new Relatorio();
        BeanUtils.copyProperties(frm, relatorio);
        relatorio.setStatus("criado");
        relatorio.setDataCriacao(new Date());		
        dao.save(relatorio);
	}

	
	public String validarCadastroNovoRelatorioSistema(RelatorioForm frm) {		
		String msgRetorno = "";
        // checar se o tipo relatorio estï¿½ vazio ou nï¿½o
        if (( frm.getTipo() == null ) || 
            ( frm.getTipo() != null && frm.getTipo().equals("")))
           msgRetorno = MessageUtils.getMessage("msg.erro.selecionar.tipo.relatorio.obrigatorio");
                   
        // checar se existe relatorio com o nome informado
        List<Relatorio> lista = dao.findRelatorioSistemaByNome(frm.getNome());
        if (! CollectionUtils.isEmpty(lista))
        	msgRetorno = MessageUtils.getMessage("msg.erro.existe.relatorio.sistema");        

        return msgRetorno;
	}

	
	@Transactional
	public void atualizarRelatorioUsuarioSistema(RelatorioForm frm) {		
		Relatorio relatorio = new Relatorio();
        BeanUtils.copyProperties(frm, relatorio);
        relatorio.setDataCriacao(DateUtil.formataDataBanco(relatorio.getDataCriacaoFmt()));
        dao.save(relatorio);
	}

	@Transactional
	public void excluirRelatorioUsuarioSistema(RelatorioForm frm) {
		Relatorio relatorio = dao.findRelatorioById(frm.getId());
		dao.delete(relatorio);
	}
	
	public List<Relatorio> listarTodosRelatoriosSistemaDisponiveis() {		
		return dao.listAll();
	}

	public RelatorioForm recuperarRelatorioSistemaPorIdRelatorio(Long idRelatorioSelecionado) {		
		RelatorioForm frm = new RelatorioForm();
        Relatorio relatorio = dao.findRelatorioById(idRelatorioSelecionado);
        BeanUtils.copyProperties(frm, relatorio);
        return frm;
	}
	
	@Transactional
	public void excluirPerfilRelatorio(Long idRelatorioPerfil) {
		Relatorioacessoperfil relatorioacessoperfil = relatorioacessoperfilDao.findRelatorioacessoperfilById(idRelatorioPerfil); 
		relatorioacessoperfilDao.delete(relatorioacessoperfil);
	}
	
	public boolean checarCobrancaModuloRelatorios(Long idUsuario,String perfil, Date dataCadastroUsuario) {		
		Servico servico = null;
        Calendar dtAtual = Calendar.getInstance();
        boolean cobrado = false;
        
        String cobrarAssinatura = parametrosIniciaisService.findParametroInicialPorNome("cobrarAssinatura");
        if ( cobrarAssinatura.equals("S")){
        	// checando se o parametro para o servico estï¿½ marcando ou nao gratuidade
            if ( perfil.equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
                cobrado = paramservicoService.checarServicoCobrado(ServicoValueEnum.RELATORIO_PADRAO.getRotulo());
            else if ( perfil.equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
                cobrado = paramservicoService.checarServicoCobrado(ServicoValueEnum.RELATORIO_CORRETOR.getRotulo());
            else if ( perfil.equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
                cobrado = paramservicoService.checarServicoCobrado(ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo());       
            
            // mesmo se o parametro indicar para cobrar, checar se estï¿½ no periodo de gratuidade do usuario
            if ( cobrado ){
                DateUtil dtCadastro = new DateUtil();
                dtCadastro.setTime(dataCadastroUsuario);
                DateUtil dtCorrente = new DateUtil();
                //if ( dtCorrente.daysBetween(dtCadastro) <= UsuarioInterface.QUANT_DIAS_GRATUIDADE )
                if ( dtCorrente.daysBetween(dtCadastro) <= 10 )
                    cobrado = false;
            }        
            
            if ( cobrado ){ // se o servico ï¿½ cobrado entao checar se o usuï¿½rio pagou pelo servico para usar no periodo corrente
                if ( perfil.equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
                    servico = servicoService.checarHabilitadoModuloRelatorio(idUsuario,ServicoValueEnum.RELATORIO_PADRAO.getRotulo(), dtAtual.getTime());
                else if ( perfil.equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
                    servico = servicoService.checarHabilitadoModuloRelatorio(idUsuario,ServicoValueEnum.RELATORIO_CORRETOR.getRotulo(), dtAtual.getTime());
                else if ( perfil.equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
                    servico = servicoService.checarHabilitadoModuloRelatorio(idUsuario,ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo(), dtAtual.getTime());

                if ( servico == null ){
                    return true;
                }    
                else
                    return false; 
            }
            else
                return false;
        }
        else 
        	return false;        
	}
	

	
	public List<RelatorioQuantAssinatura> gerarRelatorioAdminQuantTotalAssinaturas(RelatorioForm frm) {		
		List<RelatorioQuantAssinatura> listaFinal = new ArrayList<RelatorioQuantAssinatura>();     
        if ( frm.getStatusAssinatura().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
            List lista = dao.findQuantidadeTotalAssinaturasByStatus(frm, StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()); 
            if ( ! CollectionUtils.isEmpty(lista)){
                RelatorioQuantAssinatura relatorio = null;
                Object[] obj = null;
                for (Iterator iter = lista.iterator();iter.hasNext();){
                    obj = (Object[]) iter.next();
                    relatorio = new RelatorioQuantAssinatura();
                    relatorio.setTipoAssinatura(obj[0].toString());
                    relatorio.setQuantidade(Integer.parseInt(obj[1].toString()));
                    listaFinal.add(relatorio);
                }                        
            }            
        }
        else if ( frm.getStatusAssinatura().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
            List lista = dao.findQuantidadeTotalAssinaturasByStatus(frm, StatusPagtoOpcaoEnum.PAGO.getRotulo());
            if ( ! CollectionUtils.isEmpty(lista)){
                RelatorioQuantAssinatura relatorio = null;
                Object[] obj = null;
                for (Iterator iter = lista.iterator();iter.hasNext();){
                    obj = (Object[]) iter.next();
                    relatorio = new RelatorioQuantAssinatura();
                    relatorio.setTipoAssinatura(obj[0].toString());
                    relatorio.setQuantidade(Integer.parseInt(obj[1].toString()));
                    listaFinal.add(relatorio);
                }                        
            }            
        }
        return listaFinal;
	}

	
	public List<RelatorioQuantAssinatura> gerarRelatorioAdminAssinaturaVolFinanceiro(RelatorioForm frm) {		
		List<RelatorioQuantAssinatura> listaFinal = new ArrayList<RelatorioQuantAssinatura>();
        double quant = 0d;        
        if ( frm.getStatusAssinatura().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
            List lista = dao.findAssinaturaVolFinanceiroByStatus(frm, StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());
            if ( ! CollectionUtils.isEmpty(lista)){
                RelatorioQuantAssinatura relatorio = null;
                Object[] obj = null;
                for (Iterator iter = lista.iterator();iter.hasNext();){
                    obj = (Object[])iter.next(); 
                    relatorio = new RelatorioQuantAssinatura();
                    relatorio.setTipoAssinatura(obj[1].toString());
                    relatorio.setValorFinanceiro(Double.parseDouble(obj[0].toString()));
                    listaFinal.add(relatorio);
                }
            }            
        }
        else if ( frm.getStatusAssinatura().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
            List lista = dao.findAssinaturaVolFinanceiroByStatus(frm, StatusPagtoOpcaoEnum.PAGO.getRotulo());
            if (! CollectionUtils.isEmpty(lista)){
                RelatorioQuantAssinatura relatorio = null;
                Object[] obj = null;
                for (Iterator iter = lista.iterator();iter.hasNext();){
                    obj = (Object[])iter.next(); 
                    relatorio = new RelatorioQuantAssinatura();
                    relatorio.setTipoAssinatura(obj[1].toString());
                    relatorio.setValorFinanceiro(Double.parseDouble(obj[0].toString()));
                    listaFinal.add(relatorio);
                }           
            }            
        }
        return listaFinal;
	}

	
	public List<RelatorioQuantAssinatura> gerarRelatorioAdminAssinaturasPorUsuario(RelatorioForm frm) {		
		 List<RelatorioQuantAssinatura> listaFinal = new ArrayList<RelatorioQuantAssinatura>();
	        List<RelatorioQuantAssinatura> lista = dao.findAssinaturasByUsuario(frm.getBuscarUsuario());
	        if ( ! CollectionUtils.isEmpty(lista)){
	                RelatorioQuantAssinatura relatorio = null;
	                Object[] obj = null;
	                for (Iterator iter = lista.iterator();iter.hasNext();){
	                    obj = (Object[])iter.next(); 
	                    relatorio = new RelatorioQuantAssinatura();
	                    relatorio.setNomeUsuario(obj[0].toString());
	                    relatorio.setTipoAssinatura(obj[1].toString());
	                    relatorio.setQuantidade(Integer.parseInt(obj[2].toString()));
	                    listaFinal.add(relatorio);
	                }           
	        }         
	        return listaFinal;
	}

	
	public List<RelatorioQuantidadeImoveisCriados> listarTipoImoveisMaisProcuradosPorTodosUsuarios( Long idUsuario, RelatorioForm frm) {		
	 		List<RelatorioQuantidadeImoveisCriados> listaFinal = new ArrayList<RelatorioQuantidadeImoveisCriados>();
            // recuperando os tipos de imoveis mais visitados pelo usuario da iteracao
            List lista = dao.recuperarTiposImoveisQuantidade(frm, null);
            if ( ! CollectionUtils.isEmpty(lista)){
                RelatorioQuantidadeImoveisCriados relatorio = null;       
                for (Iterator iter2 = lista.iterator();iter2.hasNext();){                
                    Object[] obj = (Object[]) iter2.next();
                    relatorio = new RelatorioQuantidadeImoveisCriados(); 						
                    relatorio.setAcao(obj[0].toString());
                    relatorio.setTipoImovel(obj[1].toString());
                    relatorio.setQuantidade(Integer.parseInt(obj[2].toString()));
                    listaFinal.add(relatorio);
                }
            }
	        return listaFinal;
	}


	@Override
	public void carregaRelatorioFormPorPerfilAcessoRelatorio(Long idPerfilAcessoRelatorio, RelatorioForm form) {

		Relatorioacessoperfil relatorioacessoperfil = relatorioacessoperfilDao.findRelatorioacessoperfilById(idPerfilAcessoRelatorio);
		Relatorio relatorio = dao.findRelatorioById(relatorioacessoperfil.getIdRelatorio());
		BeanUtils.copyProperties(relatorio, form);
		form.setIdRelatorioSelecionado(relatorio.getId().toString());
	}


	@Override
	public Payment confirmarPagamentoPayPal(HttpServletRequest req,  HttpServletResponse resp, RelatorioForm form) {

		Payment createdPayment = null;
		Map<String, String> map = new HashMap<String, String>();
		// ###AccessToken
		// Retrieve the access token from
		// OAuthTokenCredential by passing in
		// ClientID and ClientSecret
		APIContext apiContext = null;
		String accessToken = null;
		try {
			accessToken = GenerateAccessToken.getAccessToken();

			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			apiContext = new APIContext(accessToken);
			// Use this variant if you want to pass in a request id
			// that is meaningful in your application, ideally
			// a order id.
			/*
			 * String requestId = Long.toString(System.nanoTime(); APIContext
			 * apiContext = new APIContext(accessToken, requestId ));
			 */
		} catch (PayPalRESTException e) {
			req.setAttribute("error", e.getMessage());
		}
		if (req.getParameter("PayerID") != null) {
			Payment payment = new Payment();
			if (req.getParameter("guid") != null) {
				payment.setId(map.get(req.getParameter("guid")));
			}

			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(req.getParameter("PayerID"));
			try {
				createdPayment = payment.execute(apiContext, paymentExecution);				
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
		} else {

			// ###Details
			// Let's you specify details of a payment amount.
			Details details = new Details();
			details.setShipping("0");			
			details.setSubtotal(String.format(Locale.US, "%.2f", form.getValorServico()));
			details.setTax("0");

			// ###Amount
			// Let's you specify a payment amount.
			Amount amount = new Amount();
			amount.setCurrency("BRL");
			// Total must be equal to sum of shipping, tax and subtotal.
			amount.setTotal(String.format(Locale.US, "%.2f", form.getValorServico()));
			amount.setDetails(details);

			// ###Transaction
			// A transaction defines the contract of a
			// payment - what is the payment for and who
			// is fulfilling it. Transaction is created with
			// a `Payee` and `Amount` types
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setDescription("This is the payment transaction description.");

			// ### Items
			Item item = new Item();			
			item.setName("Relatorio").setQuantity("1").setCurrency("BRL").setPrice(String.format(Locale.US, "%.2f", form.getValorServico()));
			ItemList itemList = new ItemList();
			List<Item> items = new ArrayList<Item>();
			items.add(item);
			itemList.setItems(items);
			
			transaction.setItemList(itemList);
			
			
			// The Payment creation API requires a list of
			// Transaction; add the created `Transaction`
			// to a List
			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			// ###Payer
			// A resource representing a Payer that funds a payment
			// Payment Method
			// as 'paypal'
			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			// ###Payment
			// A Payment Resource; create one using
			// the above types and intent as 'sale'
			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);

			// ###Redirect URLs
			RedirectUrls redirectUrls = new RedirectUrls();
			String guid = UUID.randomUUID().toString().replaceAll("-", "");
			redirectUrls.setCancelUrl(req.getScheme() + "://"
					+ req.getServerName() + ":" + req.getServerPort()
					+ req.getContextPath() + "/servico/cancelarServico?guid=" + guid);
			redirectUrls.setReturnUrl(req.getScheme() + "://"
					+ req.getServerName() + ":" + req.getServerPort()
					+ req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
			payment.setRedirectUrls(redirectUrls);

			
			// Create a payment by posting to the APIService
			// using a valid AccessToken
			// The return object contains the status;
			try {
				createdPayment = payment.create(apiContext);				
				// ###Payment Approval Url
				Iterator<Links> links = createdPayment.getLinks().iterator();
				while (links.hasNext()) {
					Links link = links.next();
					if (link.getRel().equalsIgnoreCase("approval_url")) {
						req.setAttribute("redirectURL", link.getHref());
					}
				}
				
				map.put(guid, createdPayment.getId());
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
			// atualizar status na tabela Servico
			Servico servico = servicoService.recuperarServicoPorId(form.getIdServicoGerado());
			servico.setStatusPgto(StatusPagtoOpcaoEnum.AGUARDANDO.getRotulo());
			servico.setDataAguardandoPagto(new Date());
			servico.setToken(guid);
			servicoService.atualizarServico(servico);
			
		}
		return createdPayment;
	}


	@Override
	public List<FiltroRelatorioForm> checarFiltrosUtilizados(RelatorioForm form) {

		FiltroRelatorioForm filtro = new FiltroRelatorioForm();
		List<FiltroRelatorioForm> lista = new ArrayList<FiltroRelatorioForm>();
		if ( form.getIdEstado() > 0  ){
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.estado"));
			filtro.setValorFiltro(estadosService.rescuperarEstadosPorId(form.getIdEstado()).getNome() );
			lista.add(filtro);
			if (form.getIdCidade() > 0 ){
				filtro = new FiltroRelatorioForm();
				filtro.setNomeFiltro(MessageUtils.getMessage("lbl.cidade"));
				filtro.setValorFiltro(cidadesService.recuperarCidadesPorId(form.getIdCidade()).getNome());
				lista.add(filtro);
				if (form.getIdBairro() > 0 ){
					filtro = new FiltroRelatorioForm();
					filtro.setNomeFiltro(MessageUtils.getMessage("lbl.bairro"));
					filtro.setValorFiltro(bairrosService.recuperarBairrosPorId(form.getIdBairro()).getNome());
					lista.add(filtro);
				}
			}
		}
		
		if (! StringUtils.isEmpty(form.getAcao())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.acao.imovel"));
			filtro.setValorFiltro(AcaoImovelEnum.valueOf(form.getAcao()).getRotulo());
			lista.add(filtro);
		}
		
		if (! StringUtils.isEmpty(form.getTipoImovel())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.tipo.imovel"));
			filtro.setValorFiltro(TipoImovelEnum.valueOf(form.getTipoImovel()).getRotulo());
			lista.add(filtro);
		}
		
		if (! StringUtils.isEmpty(form.getPerfilImovel())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.status.imovel"));
			filtro.setValorFiltro(StatusImovelEnum.valueOf(form.getPerfilImovel()).getRotulo());
			lista.add(filtro);
		}	
		
		if (! StringUtils.isEmpty(form.getDataInicio())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.relatorio.data.inicio"));
			filtro.setValorFiltro(form.getDataInicio());
			lista.add(filtro);
		}	
		
		if (! StringUtils.isEmpty(form.getDataFim())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.relatorio.data.fim"));
			filtro.setValorFiltro(form.getDataFim());
			lista.add(filtro);
		}
		
		if (! StringUtils.isEmpty(form.getOpcaoFiltroContato())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.relatorio.tipo.contato"));
			filtro.setValorFiltro(TipoContatoEnum.getLabel(form.getOpcaoFiltroContato()));
			lista.add(filtro);
		}
		
		if (! StringUtils.isEmpty(form.getOpcaoRelatorioSobreLocalidade())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.rel.opcao.relatorio.sobre.localidade"));
			filtro.setValorFiltro(TipoRelatorioSobreLocalidadeEnum.getLabel(form.getOpcaoRelatorioSobreLocalidade()));
			lista.add(filtro);			
		}
		
		if ( form.getQuantMaxRegistrosResultado() > 0 ){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.relatorio.quant.registros"));
			filtro.setValorFiltro(String.valueOf(form.getQuantMaxRegistrosResultado()));
			lista.add(filtro);
		}
			
		return lista;
	}


	@Override
	public boolean validarFiltrosRelatorio(RelatorioForm form, BindingResult result, UsuarioForm user) {
		
		boolean filtroValido = true;
		
		// validação campos Data Inicio e Data Fim são para todos os relatorios
		boolean isValidoDataInicio = true;
		if ( StringUtils.isEmpty(form.getDataInicio())) {
			result.rejectValue("dataInicio", "opcao.periodo.obrigatorio");
			isValidoDataInicio = false;
        	filtroValido = false;
		}
		
		boolean isValidoDataFim = true;
		if ( StringUtils.isEmpty(form.getDataFim())) {
			result.rejectValue("dataFim", "opcao.periodo.obrigatorio");
			isValidoDataFim = false;
        	filtroValido = false;
		}
		
		if ( isValidoDataInicio ){
			if ( ! DateUtil.formatoDataValido(form.getDataInicio())) {
				result.rejectValue("dataInicio", "msg.erro.data.invalida");
	        	filtroValido = false;
	        	isValidoDataInicio = false;
			}
		}
		
		if ( isValidoDataFim ){
			if ( ! DateUtil.formatoDataValido(form.getDataFim())) {
				result.rejectValue("dataFim", "msg.erro.data.invalida");
	        	filtroValido = false;
	        	isValidoDataFim = false;
			}
		}				
		
		if ( isValidoDataInicio && isValidoDataFim ){
			Calendar dtIncio = Calendar.getInstance();
			dtIncio.setTime(DateUtil.formataDataBanco(form.getDataInicio()));
			
			Calendar dtFim = Calendar.getInstance();
			dtFim.setTime(DateUtil.formataDataBanco(form.getDataFim()));
			
			if ( dtFim.compareTo(dtIncio) < 0 ){
				result.rejectValue("dataInicio", "msg.erro.data.inicio.menor.data.atual");
	        	filtroValido = false;
			}
		}
		
		if (! user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
			
			if ((form.getItem().equals(RelatorioEnum.imoveisMaisAdotadosInteressados.getIdentificador()) ||
				 form.getItem().equals(RelatorioEnum.imoveisMaisVisualizados.getIdentificador()) ||	
			     form.getItem().equals(RelatorioEnum.imoveisMaisPropostados.getIdentificador()) ||			    
			     form.getItem().equals(RelatorioEnum.imoveisMaisComentados.getIdentificador()) ) )  {
				
				if ( StringUtils.isEmpty(form.getOpcaoFiltroContato())) {
					result.rejectValue("opcaoFiltroContato", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}				
				
			}
			
			if ((form.getItem().equals(RelatorioEnum.sobreEstados.getIdentificador()) ||
				 form.getItem().equals(RelatorioEnum.sobreCidades.getIdentificador()) ||	
				 form.getItem().equals(RelatorioEnum.sobreBairros.getIdentificador()) ) )  {
				
				if ( StringUtils.isEmpty(form.getOpcaoRelatorioSobreLocalidade())) {
					result.rejectValue("opcaoRelatorioSobreLocalidade", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}
				
				if ( StringUtils.isEmpty(form.getTipoImovel())) {
					result.rejectValue("tipoImovel", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}
				
				if ( StringUtils.isEmpty(form.getAcao())) {
					result.rejectValue("acao", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}
			}
			
			if ((form.getItem().equals(RelatorioEnum.tipoImoveisMaisProcuradoPorLocalizacao.getIdentificador())   ||
				 form.getItem().equals(RelatorioEnum.quantImoveisPorLocalizacaoAcaoTipoImovel.getIdentificador()) ||	
			     form.getItem().equals(RelatorioEnum.variacaoPrecosPorTipoImovelPorLocalizacao.getIdentificador()) ) )  {
				
				if ( StringUtils.isEmpty(form.getOpcaoFiltroContato())) {
					result.rejectValue("opcaoFiltroContato", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}	
				
				if (! (form.getItem().equals(RelatorioEnum.tipoImoveisMaisProcuradoPorLocalizacao.getIdentificador()))){
					if ( StringUtils.isEmpty(form.getTipoImovel())) {
						result.rejectValue("tipoImovel", "msg.erro.campo.obrigatorio");
			        	filtroValido = false;
					}
				}
			
				
				if ( StringUtils.isEmpty(form.getAcao())) {
					result.rejectValue("acao", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}
			}			
			
		}
		else if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
			
			if ((form.getItem().equals(RelatorioEnum.sobreEstados.getIdentificador()) ||
				 form.getItem().equals(RelatorioEnum.sobreCidades.getIdentificador()) ||	
				 form.getItem().equals(RelatorioEnum.sobreBairros.getIdentificador()) ) )  {
					
					if ( StringUtils.isEmpty(form.getOpcaoRelatorioSobreLocalidade())) {
						result.rejectValue("opcaoRelatorioSobreLocalidade", "msg.erro.campo.obrigatorio");
			        	filtroValido = false;
					}
					
					if ( StringUtils.isEmpty(form.getTipoImovel())) {
						result.rejectValue("tipoImovel", "msg.erro.campo.obrigatorio");
			        	filtroValido = false;
					}
					
					if ( StringUtils.isEmpty(form.getAcao())) {
						result.rejectValue("acao", "msg.erro.campo.obrigatorio");
			        	filtroValido = false;
					}
				}
			
			if ((form.getItem().equals(RelatorioEnum.quantImoveisPorLocalizacaoAcaoTipoImovel.getIdentificador()) ||	
			     form.getItem().equals(RelatorioEnum.variacaoPrecosPorTipoImovelPorLocalizacao.getIdentificador()) ) )  {
				
				if ( StringUtils.isEmpty(form.getTipoImovel())) {
					result.rejectValue("tipoImovel", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}
				
				if ( StringUtils.isEmpty(form.getAcao())) {
					result.rejectValue("acao", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}
			}
			
			if ( (form.getItem().equals(RelatorioEnum.tipoImoveisMaisProcuradoPorLocalizacao.getIdentificador()))){
				if ( StringUtils.isEmpty(form.getAcao())) {
					result.rejectValue("acao", "msg.erro.campo.obrigatorio");
		        	filtroValido = false;
				}
			}			
		}		
		return filtroValido;
		
	}
}
