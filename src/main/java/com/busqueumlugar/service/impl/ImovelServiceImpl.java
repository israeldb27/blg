package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ImovelPropostasDao;
import com.busqueumlugar.dao.ImovelcomentarioDao;
import com.busqueumlugar.dao.ImoveldestaqueDao;
import com.busqueumlugar.dao.ImovelfavoritosDao;
import com.busqueumlugar.dao.IntermediacaoDao;
import com.busqueumlugar.dao.ParceriaDao;
import com.busqueumlugar.dao.PreferencialocalidadeDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.StatusImovelCompartilhadoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelMapaForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Bairros;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.model.Estados;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imoveldestaque;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelfotosService;
import com.busqueumlugar.service.ImovelvisualizadoService;
import com.busqueumlugar.service.IntermediacaoService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.MessageUtils;

@Service
public class ImovelServiceImpl implements ImovelService{
	
	private static final Logger log = LoggerFactory.getLogger(ImovelServiceImpl.class);
	
	@Autowired
	private ImovelDao dao;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ImovelFavoritosService imovelFavoritosService;	
	
	@Autowired
	private IntermediacaoService intermediacaoService;
	
	@Autowired
	private IntermediacaoDao intermediacaoDao;
	
	@Autowired
	private ParceriaDao parceriaDao;
	
	@Autowired
	private ImovelcomentarioDao imovelcomentarioDao;
	
	@Autowired
	private ImovelPropostasDao imovelPropostasDao;
	
	@Autowired
	private ImovelvisualizadoService imovelvisualizadoService;
	
	@Autowired
	private NotaService notaService;	
	
	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private ImovelfavoritosDao imovelfavoritosDao;
	
	@Autowired
	private ImovelfotosService imovelfotosService;
	
	@Autowired
	private ImoveldestaqueDao imoveldestaqueDao;	
	
	@Autowired
	private PreferencialocalidadeDao preferenciaLocalidadeDao;
	
	@Autowired
	private ServletContext context;	
	
	@Autowired
	private ServletRequest request;	

	
	public Imovel recuperarImovelPorid(Long idImovel) {	
		return dao.findImovelById(idImovel);
	}
	
	@Transactional
	public void atualizarStatusNegociacaoImovelCliente(Long idImovel, String novoStatus) {		
		Imovel imovel = dao.findImovelById(idImovel);
		imovel.setNegociacaoImovel(novoStatus);
		dao.save(imovel);
	}
	
	@Transactional
	public ImovelForm cadastrar(ImovelForm frm) {	
         Imovel imovel = new Imovel();              
         BeanUtils.copyProperties(frm, imovel);  
         Usuario usuario = usuarioDao.findUsuario(frm.getIdUsuario());
         imovel.setUsuario(usuario);
         imovel.setDataCadastro(new Date());
         imovel.setDataUltimaAtualizacao(new Date());  
         imovel.setQuantMaxFotos(5);
         imovel.setQuantMaxCompartilhamento(3);
         imovel.setDestaque(frm.getDestaque());
	
         imovel.setFotoPrincipal(frm.getFotoPrincipal());
         if ( ! imovel.getUsuario().getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
             imovel.setHabilitaInfoDonoImovel("S");
         
         if (! StringUtils.isEmpty(frm.getValorImovelFmt()))
         	imovel.setValorImovel(AppUtil.formatarMoeda(frm.getValorImovelFmt()));
         
         if (! StringUtils.isEmpty(frm.getValorCondominioFmt()))
         	imovel.setValorCondominio(AppUtil.formatarMoeda(frm.getValorCondominioFmt()));
         
         if (! StringUtils.isEmpty(frm.getValorIptuFmt()))
         	imovel.setValorIptu(AppUtil.formatarMoeda(frm.getValorIptuFmt()));  
         
         Estados estado = estadosService.selecionaEstadoPorId(frm.getIdEstado());
         Cidades cidade = cidadesService.selecionarCidadesPorId(frm.getIdCidade());
         Bairros bairro = bairrosService.selecionarBairroPorId(frm.getIdBairro());
         
         imovel.setUf(estado.getUf());
         imovel.setEstado(estado.getNome());
         imovel.setCidade(cidade.getNome());
         imovel.setBairro(bairro.getNome());         
         
         imovel.setHabilitaInfoDonoImovel("S");
         imovel.setAutorizacaoPropostas("S");         
         imovel.setHabilitaBusca("S");
         imovel.setAcessoVisualizacao("S");
         
         String codigoId = this.gerarCodigoIdentificacao(imovel, estado);
         imovel.setCodigoIdentificacao(codigoId);            
         dao.save(imovel);   
         frm.setId(imovel.getId());         
         imovel.getUsuario().setQuantCadastroImoveis(imovel.getUsuario().getQuantCadastroImoveis() - 1);
         usuarioDao.save(imovel.getUsuario());
         return frm;
	}


	
	public List<Imovel> buscar(ImovelForm imovelForm, Long idUsuario, String opcaoOrdenacao) {
		
		List<Imovel> listaImovel = dao.buscarImoveis(imovelForm, idUsuario, opcaoOrdenacao);
		List<Imovel> listaFinal = new ArrayList();
		if ( ! CollectionUtils.isEmpty(listaImovel) ){			
			for (Imovel imovel : listaImovel){
				imovel.setInteressadoImovel(imovelFavoritosService.checarUsuarioEstaInteressadoImovel(idUsuario, imovel.getId()));
				listaFinal.add(imovel);
			}
		}
		return listaFinal;
	}

	
	public List<Imovel> refinarBuscarPorUsuario(PerfilForm perfilForm, long idUsuario) {

		
		List<Imovel> listaImovel = dao.buscarImoveis(perfilForm, idUsuario, null);
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		UsuarioForm user = null;
		if (! CollectionUtils.isEmpty(listaImovel)){		
			for (Imovel imovel :  listaImovel ){				
				imovel.setInteressadoImovel(imovelFavoritosService.checarUsuarioEstaInteressadoImovel(idUsuario, imovel.getId()));
				listaFinal.add(imovel);
			}
		}            
		
		List<Imovel> listaImoveisCompartilhadoUsuarioSolicitante = intermediacaoDao.findIntermediacaoAceitosPorUsuarioSolicitantePorPerfil(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(),perfilForm);                                
		if (! CollectionUtils.isEmpty(listaImoveisCompartilhadoUsuarioSolicitante)) 
			listaFinal.addAll(listaImoveisCompartilhadoUsuarioSolicitante);
		
		listaFinal = eliminarDuplicidades(listaFinal);
		listaFinal = acessoVisualizacao(listaFinal, user, idUsuario);
		return this.eliminarDuplicidades(listaFinal);
	}

	public List<Imovel> listarMeusImoveis(Long idUsuario, ImovelForm form) {
		/*List<Imovel> lista = dao.findImovelByUsuario(idUsuario, form);
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		for (Imovel imovel : lista){
			imovel.setImagemArquivo(this.carregaFotoPrincipalImovel(imovel));
			listaFinal.add(imovel);
		}
        return listaFinal;*/
		return dao.findImovelByUsuario(idUsuario, form);
	}

	public List<Imovel> listarMeusImoveis(Long idUsuario) {
        return dao.findImovelByUsuario(idUsuario);
	}

	
	public int quantMeusImoveis(Long idUsuario, ImovelForm form) {
		return  AppUtil.recuperarQuantidadeLista(dao.findImovelByUsuario(idUsuario, form));		
	}


	@Transactional
	public void excluirImovel(Imovel imovel) {		
		dao.delete(imovel);
	}


	@Transactional
	public void excluirImovel(Long idImovel) {		
		Imovel imovel = dao.findImovelById(idImovel);
		dao.delete(imovel);
	}

	@Transactional
	public ImovelForm atualizarImovel(ImovelForm frm, UsuarioForm user) {		
		Imovel imovel = new Imovel();
        BeanUtils.copyProperties(frm, imovel);
        
        imovel.setDataUltimaAtualizacao(new Date());
        Usuario usuario = usuarioDao.findUsuario(user.getId());
        imovel.setUsuario(usuario);        
        Estados estado = estadosService.selecionaEstadoPorId(frm.getIdEstado());
        Cidades cidade = cidadesService.selecionarCidadesPorId(frm.getIdCidade());
        Bairros bairro = bairrosService.selecionarBairroPorId(frm.getIdBairro());
        
        if (! StringUtils.isEmpty(frm.getValorImovelFmt()))
        	imovel.setValorImovel(AppUtil.formatarMoeda(frm.getValorImovelFmt()));
        
        if (! StringUtils.isEmpty(frm.getValorCondominioFmt()))
        	imovel.setValorCondominio(AppUtil.formatarMoeda(frm.getValorCondominioFmt()));
        
        if (! StringUtils.isEmpty(frm.getValorIptuFmt()))
        	imovel.setValorIptu(AppUtil.formatarMoeda(frm.getValorIptuFmt()));        
        
        imovel.setUf(estado.getUf());
        imovel.setEstado(estado.getNome());
        imovel.setCidade(cidade.getNome());
        imovel.setBairro(bairro.getNome());            
      //  dao.save(imovel);
        dao.update(imovel);
        BeanUtils.copyProperties(imovel, frm );
        return frm;
	}

	 
	public boolean checaStatusLiberadoUsuarioPorImovel(Long idImovel) {		
		Imovel imovel = dao.findImovelById(idImovel);        
        if ( imovel.getUsuario().getStatusUsuario().equals("liberado") || 
        	 imovel.getUsuario().getStatusUsuario().equals("criado") || 
        	 imovel.getUsuario().getStatusUsuario().equals("assinaturaSolicitada"))
            return true;
        else
            return false;
	}


	@Transactional
	public void adicionarFotoPrincipalImovel(Long idImovel, byte[] foto) {		
		Imovel imovel = dao.findImovelById(idImovel);
        imovel.setFotoPrincipal(foto);
        dao.save(imovel);
	}

	
	public int checarQuantCadastradaFotosImovel(Long idImovel) {		
		Imovel imovel = dao.findImovelById(idImovel);
        return imovel.getQuantMaxFotos();
	}


	
	public void adicionarMaisFotos(long idImovel, int quantFotoPaga) {		
		Imovel imovel = dao.findImovelById(idImovel);
        int quant = imovel.getQuantMaxFotos();
        imovel.setQuantMaxFotos(quant + quantFotoPaga);
        dao.save(imovel);
	}


	
	public List<Imovel> recuperarImovelPorCodigoIdentificacao(String codIdentificacao) {
		
		Imovel imovel = dao.findImovelByCodigoIdentificacao(codIdentificacao);
        List<Imovel> lista = new ArrayList<Imovel>();
        if ( imovel != null )                        
            lista.add(imovel);
               
        return lista;
	}


	
	public List<Imovel> recuperarImovelPorCodigoIdentificacaoPorIdUsuario( Long idUsuario, ImovelForm form) {
		
		 Imovel imovel = dao.findImovelByCodigoIdentificacaoBydIdUsuario(idUsuario, form );
	        List<Imovel> lista = new ArrayList<Imovel>();
	        if ( imovel != null ){	                        
	            lista.add(imovel);
	        }        
	        return lista;
	}


	
	public String gerarCodigoIdentificacao(Imovel imovel, Estados estado) {
		
		String uf = estado.getUf().toUpperCase();
        DateUtil dt = new DateUtil();
        int ano = dt.getYear();
        int mes = dt.getMonth();
        int seq =  (int)(Math.random() * 1000000);
        String resultado = uf + ano + mes + seq;
        Imovel imovelExiste = dao.findImovelByCodigoIdentificacao(resultado);
        if (  imovelExiste == null )
            return resultado;
        else  {      
            return this.gerarCodigoIdentificacao(imovel, estado);        
        }
	}
	
	public int checarQuantidadeTotalImoveis() {		
		return 0;
	}
	
	public int checarQuantidadeTotalImoveisPorPeriodo(AdministracaoForm form) {
         return AppUtil.recuperarQuantidadeLista(dao.findImoveisByDataCadastro(form));
	}


	@Transactional
	public void adicionarMaisCompartilhamento(long idImovel, int quantCompartilhamento) {		
		Imovel imovel = dao.findImovelById(idImovel);
        int quant = imovel.getQuantMaxCompartilhamento();
        imovel.setQuantMaxCompartilhamento(quant + quantCompartilhamento);
        dao.save(imovel);
	}

	
	@Override
	public List<Imovel> recuperarImoveisDestaquePorIdUsuario(Long idUsuario) {		
		List<Imovel> lista = dao.findImoveisDestaqueByIdUsuario(idUsuario);
        List<Imovel> listaFinal = new ArrayList<Imovel>();       
        for (Imovel imovel : lista){   
            imovel.setInteressadoImovel(imovelFavoritosService.checarUsuarioEstaInteressadoImovel(idUsuario, imovel.getId()));
            listaFinal.add(imovel);
        }        
        return listaFinal;
	}

	
	public void atualizarMapaImovel(Long idImovelSelecionado, 
									double latitude,
								    double longitude) {
		
		Imovel imovel = dao.findImovelById(idImovelSelecionado);
        imovel.setLatitude(latitude);
        imovel.setLongitude(longitude);
        dao.save(imovel);
	}
	
	@Override
	public ImovelForm carregaGaleriaFotosImovel(Long idImovel, boolean carregaFotoPrincipal) {
		ImovelForm frm = new ImovelForm(); 
		Imovel imovel = dao.findImovelById(idImovel);
		BeanUtils.copyProperties(imovel, frm);
		frm.setListaImovelFotos(imovelfotosService.recuperarFotosImovel(idImovel));		
		return frm;
	}

	public ImovelForm carregarImovelFormPorImovel(Long idImovel) {		
		ImovelForm frm = new ImovelForm(); 
		Imovel imovel = dao.findImovelById(idImovel);        
        BeanUtils.copyProperties(imovel, frm);
        return frm;
	}

	public boolean existeImovelComMapa(List<Imovel> listaImoveis) {		
		boolean existe = false;
        if (! CollectionUtils.isEmpty(listaImoveis)){            
            for (Imovel imovel : listaImoveis){                
                if (( imovel.getLatitude() != null && imovel.getLatitude().intValue() != 0 ) &&
                    ( imovel.getLongitude() != null && imovel.getLongitude().intValue() != 0 ))    {
                    return true;
                } 
            }
        }    
        return existe;
	}


	@Transactional
	public void reativaImoveisUsuario(long idUsuario) {		
		dao.reativaImoveisUsuario(idUsuario);          
	}


	@Transactional
	public void desativarImoveisUsuario(Long idUsuario) {		
	    List<Imovel> lista = this.listarMeusImoveis(idUsuario, new ImovelForm());
        for (Imovel imovel : lista) {
            imovel.setAtivado("N");
            dao.save(imovel);
        }
	}

	
	  public ArrayList<Imovel> eliminarDuplicidades(List<Imovel> lista) {	        
	        if (( lista == null ) || ( lista != null && lista.size() == 0 ))
	            return new ArrayList<Imovel>();
	        else {        
	            HashMap map = new HashMap();
	            for (Imovel imovel : lista){
	                map.put(imovel.getId(), imovel);
	            }
	            
	            Imovel imovel2 = null;            
	            ArrayList<Imovel> listaFinal = new ArrayList<Imovel>();
	            for (Iterator iter = map.values().iterator();iter.hasNext();){
	                imovel2 = (Imovel) iter.next();
	                listaFinal.add(imovel2);
	            }
	            return listaFinal;
	        }        
	    }


	
	public List<Imovel> acessoVisualizacao(List<Imovel> listaImoveis,
										   UsuarioForm user, 
										   Long idPerfil) {		
		if (CollectionUtils.isEmpty(listaImoveis))
            return listaImoveis;
        else {
            List<Imovel> listaFinal = new ArrayList<Imovel>();
            boolean existeContato = contatoService.checarExisteContato(user.getId(), idPerfil);
            for (Imovel imovel : listaImoveis){
                if ( imovel.getAcessoVisualizacao().equals("T"))
                    listaFinal.add(imovel);
                else if  ( imovel.getAcessoVisualizacao().equals("C") && existeContato )
                    listaFinal.add(imovel);                    
            }
            return listaFinal;
        }
	}


	
	public List<Imovel> recuperarImovelPorCodigoIdentificacaoPorUsuario(ImovelForm form, Long idUsuario, boolean isUsuarioSessao) {		
		Imovel imovel = null;
		if ( isUsuarioSessao ) 
			imovel = dao.findImovelByCodigoIdentificacaoBydIdUsuario(idUsuario, form);			
		else
			imovel = dao.findImovelByCodigoIdentificacaoBydIdUsuario(null, form);
		
        if (imovel == null)
            return null;
        else {
            if ( imovel.getUsuario().getId().longValue() == idUsuario.longValue() ){            	
            	imovel.setInteressadoImovel(imovelFavoritosService.checarUsuarioEstaInteressadoImovel(idUsuario, imovel.getId()));            	
                List<Imovel> lista = new ArrayList<Imovel>();
                lista.add(imovel);
                return lista;
            }    
            else {
            	List<Imovel> lista = new ArrayList<Imovel>();
                lista.add(imovel);
                return lista;
            }    
        }
	}


	@Override
	public long checarQuantMeusImoveis(Long idUsuario) {
		return dao.findQuantMeusImoveis(idUsuario);						
	}

	@Override
	public void prepararImovelParaAtualizacao(Long idImovel, ImovelForm form) {
		Imovel imovel = dao.findImovelById(idImovel);		
		BeanUtils.copyProperties(imovel, form);	
		
		if ( form.getValorImovel()!= null && form.getValorImovel().longValue() > 0d) 
	        form.setValorImovelFmt(AppUtil.formataMoedaString(form.getValorImovel()));
		
		if ( form.getValorCondominio()!= null && form.getValorCondominio().longValue() > 0d) 
	        form.setValorCondominioFmt(AppUtil.formataMoedaString(form.getValorCondominio()));

		if ( form.getValorIptu()!= null && form.getValorIptu().longValue() > 0d) 
	        form.setValorIptuFmt(AppUtil.formataMoedaString(form.getValorIptu()));
	
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(form.getIdEstado()));
		form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(form.getIdCidade()));
	}

	@Override
	public void preparaCadastroImovel(ImovelForm form) {
		form.setEstado(estadosService.rescuperarEstadosPorId(form.getIdEstado()).getNome());
		form.setCidade(cidadesService.recuperarCidadesPorId(form.getIdCidade()).getNome());
		form.setBairro(bairrosService.recuperarBairrosPorId(form.getIdBairro()).getNome());		
	}
	
	@Override
	public void preparaImovelDestaqueFormAdmin(Long idImovel, ImovelForm form){
		Imovel imovel = dao.findImovelById(idImovel);		
		BeanUtils.copyProperties(imovel, form);
		form.setListaImoveisDestaque(imoveldestaqueDao.findImoveldestaqueByIdImovel(idImovel));
	}
	
	@Override
	public boolean validarDadosCadastroImovel(ImovelForm form, BindingResult result) {
	
		boolean filtroValido = false;

        if (result.hasErrors())             
            return false;        
 		
		// inicio - validacao informacoes localidade
	    if ( form.getIdEstado() == -1 ) {
			result.rejectValue("idEstado", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
	    }
		 
		if ( form.getIdCidade() == -1 ) {
		   result.rejectValue("idCidade", "msg.erro.campo.obrigatorio");
		   filtroValido = true;                   
		}
		
		if ( form.getIdBairro() == -1 ) {
		   result.rejectValue("idBairro", "msg.erro.campo.obrigatorio");
		   filtroValido = true;                   
		}
		
		if (( form.getFotoPrincipal() == null ) || (form.getFotoPrincipal() != null && form.getFotoPrincipal().length == 0)){
			result.rejectValue("fotoPrincipal", "msg.erro.campo.obrigatorio");
			filtroValido = true;
		}
				                   
		
		// fim - validacao informacoes localidade	
		
		// inicio - validacao informacoes basicas	
		if ( StringUtils.isEmpty(form.getTitulo())){
			result.rejectValue("titulo", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getTipoImovel())){
			result.rejectValue("tipoImovel", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getAcao())){
			result.rejectValue("acao", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( ! StringUtils.isEmpty(form.getValorImovel()) && ! AppUtil.formatarMoedaValido(form.getValorImovelFmt())){
			result.rejectValue("valorImovel", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		
		if ( StringUtils.isEmpty(form.getDescricao())){
			result.rejectValue("descricao", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
				
		// fim - validacao informacoes basicas
		
		// inicio - validacao caracteristicas imovel
		
		if ( StringUtils.isEmpty(form.getPerfilImovel())){
			result.rejectValue("perfilImovel", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if (( form.getArea() == null) || (form.getArea() != null && form.getArea().doubleValue() == 0.0d)){
			result.rejectValue("area", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		// fim - validacao caracteristicas imovel
		
		// inicio - validacao permissoes
		if ( StringUtils.isEmpty(form.getDestaque())){
			result.rejectValue("destaque", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getAceitaFinanciamento())){
			result.rejectValue("aceitaFinanciamento", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}

		if ( StringUtils.isEmpty(form.getAutorizacaoOutroUsuario())){
			result.rejectValue("autorizacaoOutroUsuario", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		
		// fim - validacao permissoes
		
		return filtroValido;
	}
	
	
	@Override
	public boolean validarDadosEdicaoImovel(ImovelForm form, BindingResult result) {
	
		boolean filtroValido = false;

        if (result.hasErrors())             
            return false;        
 		
		// inicio - validacao informacoes localidade
	    if ( form.getIdEstado() == -1 ) {
			result.rejectValue("idEstado", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
	    }
		 
		if ( form.getIdCidade() == -1 ) {
				   result.rejectValue("idCidade", "msg.erro.campo.obrigatorio");
				   filtroValido = true;                   
		}
		
		if ( form.getIdBairro() == -1 ) {
					   result.rejectValue("idBairro", "msg.erro.campo.obrigatorio");
					   filtroValido = true;                   
		}
		
		if (( form.getFotoPrincipal() == null ) || (form.getFotoPrincipal() != null && form.getFotoPrincipal().length == 0)){
			result.rejectValue("fotoPrincipal", "msg.erro.campo.obrigatorio");
			filtroValido = true;
		}
		// fim - validacao informacoes localidade	
		
		// inicio - validacao informacoes basicas	
		if ( StringUtils.isEmpty(form.getTitulo())){
			result.rejectValue("titulo", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getTipoImovel())){
			result.rejectValue("tipoImovel", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getAcao())){
			result.rejectValue("acao", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if (( form.getValorImovel() == null) || (form.getValorImovel() != null && form.getValorImovel().doubleValue() == 0.0d)){
			result.rejectValue("valorImovel", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getDescricao())){
			result.rejectValue("descricao", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
				
		// fim - validacao informacoes basicas
		
		// inicio - validacao caracteristicas imovel
		
		if ( StringUtils.isEmpty(form.getPerfilImovel())){
			result.rejectValue("perfilImovel", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if (( form.getArea() == null) || (form.getArea() != null && form.getArea().doubleValue() == 0.0d)){
			result.rejectValue("area", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		// fim - validacao caracteristicas imovel
		
		// inicio - validacao permissoes
		if ( StringUtils.isEmpty(form.getDestaque())){
			result.rejectValue("destaque", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getHabilitaInfoDonoImovel())){
			result.rejectValue("habilitaInfoDonoImovel", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getAutorizacaoPropostas())){
			result.rejectValue("autorizacaoPropostas", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getAutorizaComentario())){
			result.rejectValue("autorizaComentario", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getAceitaFinanciamento())){
			result.rejectValue("aceitaFinanciamento", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getHabilitaBusca())){
			result.rejectValue("habilitaBusca", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getAcessoVisualizacao())){
			result.rejectValue("acessoVisualizacao", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}		
		// fim - validacao permissoes
		
		return filtroValido;
	}

	@Override
	public List<Imovel> recuperarImoveisNaoDestaquePorIdUsuario(Long idUsuario) {
		List<Imovel> lista = dao.findImoveisNaoDestaqueByIdUsuario(idUsuario);
        List<Imovel> listaFinal = new ArrayList<Imovel>();       
        for (Imovel imovel : lista){
            imovel.setInteressadoImovel(imovelFavoritosService.checarUsuarioEstaInteressadoImovel(idUsuario, imovel.getId()));
            listaFinal.add(imovel);
        }        
        return listaFinal;
	}


	@Override
	public void preparaDetalhesImovelForm(Long idImovel, ImovelForm form, UsuarioForm usuarioSessao) {
		Imovel imovel = dao.findImovelById(idImovel);	
		BeanUtils.copyProperties(imovel, form);			
		form.setListaFotos(imovelfotosService.recuperarFotosImovel(imovel.getId()));		
		form.setListaComentario(imovelcomentarioDao.findImovelcomentariosByIdImovel(idImovel, null));
		
		if (usuarioSessao.getId().equals(imovel.getUsuario().getId())){
			form.setListaPropostas(imovelPropostasDao.findImovelPropostaByIdImovel(idImovel));
			form.setListaComentario(imovelcomentarioDao.findImovelcomentariosByIdImovel(idImovel, null));
			form.setListaUsuariosInteressados(imovelFavoritosService.recuperarUsuariosInteressadosPorIdImovel(idImovel));
			form.setUsuarioDonoImovel(imovel.getUsuario());
			form.setListaVisita(imovelvisualizadoService.recuperarUsuariosVisitantesPorIdImovel(idImovel));
			
			if (usuarioSessao.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
				form.setListaIntermediacao(intermediacaoDao.findIntermediacaoByIdImovelByStatus(idImovel, 
																								StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));	
			
				form.setUsuarioIntermediador(intermediacaoService.recuperarUsuarioIntermediador(form.getId()));
				form.setIntermediacaoEnviada(intermediacaoDao.findIntermediacaoByIdUsuarioSolicitanteByIdImovel(usuarioSessao.getId(), form.getId()));	
			}
			else {
				form.setListaParceria(parceriaDao.findParceriaByIdImovelByStatus(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
				form.setParceriaEnviada(parceriaDao.findParceriaByIdUsuarioSolicitanteByIdImovel(usuarioSessao.getId(), form.getId()));				
			}
		}
		else {
			imovelvisualizadoService.adicionarVisitaImovel(idImovel, usuarioSessao.getId());
			form.setInteressadoImovel(imovelFavoritosService.checarUsuarioEstaInteressadoImovel(usuarioSessao.getId(), idImovel));			
			if (imovel.getHabilitaInfoDonoImovel().equals("S")) { 
				form.setUsuarioDonoImovel(imovel.getUsuario());
				form.getUsuarioDonoImovel().setIsContato(contatoService.checarTipoContato(imovel.getUsuario().getId(), usuarioSessao.getId()));
			}
			
			if ( imovel.getAutorizacaoPropostas().equals("S"))
				form.setListaPropostas(imovelPropostasDao.findImoveisPropostasLancadasByIdUsuarioByIdImovel(usuarioSessao.getId(), idImovel));			
			
			if (imovel.getUsuario().getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))	{	
				form.setUsuarioIntermediador(intermediacaoService.recuperarUsuarioIntermediador(form.getId()));
				if ( ! usuarioSessao.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) )						
					form.setIntermediacaoEnviada(intermediacaoDao.findIntermediacaoByIdUsuarioSolicitanteByIdImovel(usuarioSessao.getId(), form.getId()));						
			}	
			else {
				if ( ! usuarioSessao.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) ) 
					form.setParceriaEnviada(parceriaDao.findParceriaByIdUsuarioSolicitanteByIdImovel(usuarioSessao.getId(), form.getId()));	
			}
			
			form.setQuantVisualizacoesImovel(imovelvisualizadoService.checarQuantidadeImoveisVisualizadosPorImovel(idImovel, null));
			form.setQuantUsuariosInteressados(imovelFavoritosService.checarQuantidadeUsuariosInteressadosPorIdImovel(idImovel));
		}			
	}
	
	@Override
	public void preparaDetalhesImovelFormAdmin(Long idImovel, ImovelForm form) {
		Imovel imovel = dao.findImovelById(idImovel);		
		BeanUtils.copyProperties(imovel, form);	
		form.setListaFotos(imovelfotosService.recuperarFotosImovel(imovel.getId()));
		form.setUsuarioDonoImovel(imovel.getUsuario());
		form.setListaComentario(imovelcomentarioDao.findImovelcomentariosByIdImovel(idImovel, null));
		form.setQuantVisualizacoesImovel(imovelvisualizadoService.checarQuantidadeVisitantesPorUsuarioPorStatus(idImovel, null));
		form.setQuantUsuariosInteressados(imovelFavoritosService.checarQuantidadeUsuariosInteressadosPorIdImovel(idImovel));
		form.setListaVisita(imovelvisualizadoService.recuperarUsuariosVisitantesPorIdImovel(idImovel));
		form.setListaPropostas(imovelPropostasDao.findImovelPropostaByIdImovel(idImovel));
		form.setListaUsuariosInteressados(imovelFavoritosService.recuperarUsuariosInteressadosPorIdImovel(idImovel));
		form.setListaImovelAnuncio(imoveldestaqueDao.findImoveisAnunciosPorImovel(idImovel));
		
		if (imovel.getUsuario().getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))			
			form.setListaIntermediacao(intermediacaoDao.findIntermediacaoByIdImovel(idImovel));
		else
			form.setListaParceria(parceriaDao.findParceriaByIdImovel(idImovel));	
	}


	@Override
	public ImovelForm atualizarFotoImovel(ImovelForm form) {
		Imovel imovel = dao.findImovelById(form.getId());
		imovel.setFotoPrincipal(form.getFotoPrincipal());		
		dao.save(imovel);
		BeanUtils.copyProperties(imovel, form);
		return form;
	}


	@Override
	public List<Imovel> carregaListaImoveisComparativos(List<Long> lista) {
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		Imovel imovel = null;
		for (Long idImovel : lista){
			imovel = dao.findImovelById(idImovel);			
			listaFinal.add(imovel);
		}
		return listaFinal;
	}

	@Override
	@Transactional
	public void cadastrarImovelDestaque(ImovelForm form){
		Imoveldestaque imovelDestaque = new Imoveldestaque();
		imovelDestaque.setDataCadastro(new Date());		
		imovelDestaque.setDataDestaque(DateUtil.formataDataBanco(form.getDataDestaque())); // formatar ainda o campo Data Destaque para o tipo Date
		imovelDestaque.setStatus("criado");
		imovelDestaque.setTipo(form.getOpcaoDestaque());
		imovelDestaque.setImovel(dao.findImovelById(form.getId()));
		imoveldestaqueDao.save(imovelDestaque);		
	}	
	
	@Override
	public List<Imovel> recuperarImovelDestaqueParaAnuncio(int quant){
		Calendar cal = Calendar.getInstance();
		List<Imoveldestaque> listaImoveisDestaque = imoveldestaqueDao.findImoveisDestaqueByDataDestaqueByQuantidade(cal.getTime(), quant);
		List<Imovel> listaFinal = new ArrayList<Imovel>();		
		for (Imoveldestaque imoveldestaque : listaImoveisDestaque){				
			listaFinal.add(imoveldestaque.getImovel());
		}
		return listaFinal;
	}
	
	@Override
	public List<Imovel> recuperarImovelDestaqueParaTelaInicial(int quant){		
		List<Imoveldestaque> listaImoveisDestaque = imoveldestaqueDao.findImoveisDestaqueAnuncioByDataDestaqueByQuantidade(quant);
		List<Imovel> listaFinal = new ArrayList<Imovel>();		
		for (Imoveldestaque imoveldestaque : listaImoveisDestaque){
			listaFinal.add(imoveldestaque.getImovel());
		}
		return listaFinal;
	}


	@Override
	public List<ImovelMapaForm> buscarImoveisMapa(ImovelForm form, Long idUsuario) {
		List<Imovel> lista = dao.buscarImoveis(form, idUsuario, null);
		List<ImovelMapaForm> listaFinal =  new ArrayList<ImovelMapaForm>();
		ImovelMapaForm imovelMapa = null;
		for (Imovel imovel : lista ){
			imovelMapa = new ImovelMapaForm();
			imovelMapa.setIdImovel(imovel.getId());
			imovelMapa.setLatitude(imovel.getLatitude());
			imovelMapa.setLongitude(imovel.getLongitude());
			imovelMapa.setTitulo(imovel.getTitulo());
			listaFinal.add(imovelMapa);			
		}		
		return listaFinal;
	}
	
	@Override
	public List<ImovelMapaForm> buscarImoveisMapaPorIdUsuario(ImovelForm form, Long idUsuario) {
		List<Imovel> lista = dao.buscarImoveis(form, idUsuario, null);
		List<ImovelMapaForm> listaFinal =  new ArrayList<ImovelMapaForm>();
		ImovelMapaForm imovelMapa = null;
		for (Imovel imovel : lista ){
			imovelMapa = new ImovelMapaForm();
			imovelMapa.setIdImovel(imovel.getId());
			imovelMapa.setLatitude(imovel.getLatitude());
			imovelMapa.setLongitude(imovel.getLongitude());
			imovelMapa.setTitulo(imovel.getTitulo());
			listaFinal.add(imovelMapa);			
		}		
		return listaFinal;
	}
	
	@Override
	public List<Imovel> pesquisarTodosImoveis (String valor){		
		return dao.findImoveisByValor(valor);
	}


	@Override
	public String carregarTimeLine() {		
		
		String str = "${context}${usuario.imagemArquivo}";
		StringBuffer buf = new StringBuffer("");		
		buf.append(" <div class='timeline-item last-timeline'>");
		buf.append("   <div class='timeline-badge'> ");                                               
	    buf.append("       <img class='timeline-badge-userpic' src="+ str +" style='width: 72px; height: 72px; ' />");
	    buf.append("   </div>");
	    buf.append("   <div class='timeline-body'>");
	    buf.append("     <div class='timeline-body-arrow'>");
	    buf.append("       </div>");
	    buf.append("       <div class='timeline-body-head'>");
	    buf.append("           <div class='timeline-body-head-caption'>");
	    buf.append("               <a href='javascript:void(0);' class='timeline-body-title font-blue-madison'>Teste Israel</a>");
	    buf.append("               <span class='timeline-body-time font-grey-cascade'>Posted at 4:10 PM . <i class='fa fa-group'></i></span>");
	    buf.append("           </div>");
	    buf.append("           <div class='timeline-body-head-actions'>");
	    buf.append("               <div class='btn-group'>");
	    buf.append("                   <button class='btn btn-circle green-haze btn-sm dropdown-toggle' type='button' data-toggle='dropdown' data-hover='dropdown' data-close-others='true'>");
	    buf.append("                       Actions <i class='fa fa-angle-down'></i>");
	    buf.append("                   </button>");
	    buf.append("                   <ul class='dropdown-menu pull-right' role='menu'>");
	    buf.append("                   </ul>");
   		buf.append("               </div>");
   		buf.append("                   </div>");
   		buf.append("       </div> ");
   		buf.append("        <div class='timeline-body-content'>");                   
   		buf.append("           <p>");
   		buf.append("               Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.");
   		buf.append("           </p>");
   		buf.append("           <div class='media'>");
   		buf.append("               <div class='media-left'>");
   		buf.append("                   <a href='#'>");
   		buf.append("                       <img src='http://img.djavaui.com/?create=115x65,4888E1?f=ffffff' alt='...'/>");
   		buf.append("                   </a>");
   		buf.append("               </div>");
   		buf.append("               <div class='media-body'>");
   		buf.append("                   Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.");
   		buf.append("               </div>");
   		buf.append("           </div>");
   		buf.append("       </div>");
   		buf.append("   </div>");
   		buf.append("</div>");
		
		
		return buf.toString();
	}
	
	@Override
	public String carregarTimeLine2() {
		   
		   Imovel imovel1 = dao.findImovelById(4l);
		   
		   StringBuffer buf = new StringBuffer("");
		   buf.append("<div class='timeline-item last-timeline'>");
		   buf.append("   <div class='timeline-badge'> ");
		   buf.append("       <img class='timeline-badge-userpic' src='data:image/jpeg;base64," + imovel1.getImagemArquivo() + "' style='width: 72px; height: 72px; ' /> ");
		   buf.append("   </div> ");
		   buf.append("   <div class='timeline-body'> ");
		   buf.append("      <div class='timeline-body-head'> ");
		   buf.append("           <div class='timeline-body-head-caption'> ");
		   buf.append("               <a href='javascript:void(0);' class='timeline-body-title font-blue-madison'>Ashley Johnston</a> ");
		   buf.append("               <span class='timeline-body-time font-grey-cascade'>Posted at 4:10 PM . <i class='fa fa-group'></i></span> ");
		   buf.append("           </div> ");
		   buf.append("      </div> ");
		   buf.append("       <div class='timeline-body-content'> ");
		   buf.append("          <div class='panel panel-theme shadow blog-list-slider'> ");
		   buf.append("                <div id='carousel-blog-list' class='carousel slide' data-ride='carousel'> ");		   
		   buf.append("                    <div class='carousel-inner'> ");
		   buf.append("                       <div class='item active'> ");
		   buf.append("                           <div class='blog-list'> ");
		   buf.append("                                <div class='media-list list-search'> ");
		   buf.append("                                   <div class='media rounded shadow no-overflow'> ");
		   buf.append("                                        <div class='media-left'> ");
		   buf.append("                                            <a href='/imovel/detalhesImovel/13' > ");
		   buf.append("                                               <span class='meta-provider' style='font-size:15px;'>Venda <br><strong>  R$ 560000</strong></span><br> ");
		   buf.append("                                                <img src='data:image/jpeg;base64," + imovel1.getImagemArquivo() + "' class='img-responsive' style='width: 200px; height: 190px; alt='admin'/> ");
		   buf.append("                                            </a> ");
		   buf.append("                                        </div> ");
		   buf.append("                              <div class='media-body'> ");
		   buf.append("                                           <span class='label pull-right' style='background-color: #03A9F4; font-size: 12px'>Casa</span></br> ");
		   String str = "/imovel/detalhesImovel/11";
		   buf.append("                                            <h4 class='media-heading' style='margin-bottom:20px;'><a href='" + context.getContextPath() + str + "' style='color : #03A9F4;'>"+ imovel1.getTitulo() + "</a></h4> ");
		   buf.append("                                            <h5 class='media-heading' style='margin-bottom:12px;'><i class='fa fa-map-marker'></i> " + imovel1.getEndereco() + " - " + imovel1.getBairro()  + " - " +  imovel1.getCidade() + " - " + imovel1.getUf() + " </h1> ");
		   buf.append("                                 <div class='col-md-5' > ");
		   buf.append("                                               <div class='media-body' > ");
		   buf.append("                                                <em class='text-xs text-muted'> <font style='font-size:13px; font-style: normal;'><spring:message code='lbl.data.cadastro.imovel' />: </font><span class='text-success'><font style='font-size:11px; font-style: normal;'><fmt:formatDate value='${imovel.dataCadastro}' pattern='dd/MM/yyyy'/></font></span></em> ");
		   buf.append("                                             </div> ");
		   buf.append("                                           </div> ");
		   buf.append("                                            <div class='col-md-7'> ");
		   buf.append("                                               <table class='table table-condensed'> ");
		   buf.append("                                                   <tbody style='font-size: 13px;'> ");
		   buf.append("                                                       <tr> ");
		   buf.append("                                                           <td class='text-left'>" + MessageUtils.getMessage("lbl.area.m2.resum") + "</td> ");
		   buf.append("                                                            <td class='text-right'>100 m<sup>2</sup></td> ");
		   buf.append("                                                        </tr> ");
		   buf.append("                                                        <tr> ");
		   buf.append("                                                           <td class='text-left'> "+ MessageUtils.getMessage("lbl.quartos.dormitorios.resum") + "</td> ");
		   buf.append("                                                            <td class='text-right'>2</td> ");
		   buf.append("                                                        </tr> ");
		   buf.append("                                                        <tr> ");
		   buf.append("                                                           <td class='text-left'><spring:message code='lbl.suites'/></td> ");
		   buf.append("                                                            <td class='text-right'><1</td> ");
		   buf.append("                                                        </tr> ");
		   buf.append("                                                        <tr> ");
		   buf.append("                                                           <td class='text-left'><spring:message code='lbl.vagas.garagem.resum'/></td> ");
		   buf.append("                                                            <td class='text-right'>"+ MessageUtils.getMessage("lbl.num.vagas") +" </td> ");
		   buf.append("                                                        </tr> ");
		   buf.append("                                                    </tbody> ");
		   buf.append("                                                </table> ");
		   buf.append("                                            </div> ");
		   buf.append("                                        </div> ");
		   buf.append("                                    </div> ");
		   buf.append("                            </div> ");
		   buf.append("                    </div> ");
		   buf.append("            </div> ");
		   buf.append("      </div> ");
		   buf.append("   </div> ");
		   buf.append("</div> ");

		   return buf.toString();
		}

	@Override
	public List<Imovel> recuperarImovelPorIdsUsuarioPorPosicao(List<Long> listaIds, int index) {
		return dao.findImoveisByListaIdsByIndex(listaIds, index);
	}

	@Override
	public List<Imovel> recuperarImoveisAleatoriamente(UsuarioForm user, int index) {
		return dao.findImoveisAleatoriamenteByUsuarioByIndex(user, index);
	}

	@Override
	public List<Imovel> recuperarImovelPorIdsUsuarioPorPosicaoPorAceitaCompartilhado(List<Long> listaIds, int index, String aceitaCompartilhado) {
		return dao.findImoveisByListaIdsByIndexByAceitaCompartilhado(listaIds, index, aceitaCompartilhado);
	}

	@Override
	public List<Imovel> recuperarImoveisAleatoriamente(List<Long> listaIds, int index) {
		return dao.findImoveisAleatoriamenteByListaIdsByIndex(listaIds, index);
	}

	@Override
	public List<Imovel> recuperarImovelPorIdsUsuarioPorPosicaoPorAceitaCompartilhado(Long idUsuario, int index, String aceitaCompartilhado) {
		return dao.findImoveisByListaIdsByIndexByAceitaCompartilhado(idUsuario, index, aceitaCompartilhado);
	}

	@Override
	public List<Imovel> recuperarImovelPorIdsUsuarioPorPosicao(Long idUsuario, int index) {
		return dao.findImoveisByListaIdsByIndex(idUsuario, index);
	}

	@Override
	public String validarPrepararImovel(Long idImovel, UsuarioForm user) {		
		String msg = "";
		Imovel imovel = dao.findImovelById(idImovel);
		if ( imovel.getUsuario().getId().longValue() != user.getId().longValue()) {
			msg = MessageUtils.getMessage("msg.erro.nao.autorizado.editar.imovel");
		}
		
		return msg;
	}

	@Override
	public List<Imovel> buscarImovelParaTimeline(ImovelForm form, Long idUsuario) {	
		return dao.findImoveisTimeLine(form, idUsuario);
	}

	@Override 
	public List<Usuario> analisarUsuariosInteressados(Long idUsuario, ImovelForm form) {		
		
		List<Usuario> listaFinal = new ArrayList<Usuario>();	
		TreeSet<Long> listaIdsFinal = new TreeSet<Long>();		
		
		//Recuperar usuários de acordo com a preferencia imovel
		List listaIdsUsuariosPrefImoveis = preferenciaLocalidadeDao.findUsuariosPreferenciaisImoveisSemelhantes(idUsuario, form);
		
		// Recuperar usuários que visitaram imóveis semelhantes a este imóvel selecionado
		List listaIdsUsuariosImovelVistado = imovelvisualizadoService.recuperarUsuariosVisitouImoveisSemelhantes(idUsuario, form);
				
		// Recuperar usuários que adotaram como favorito imóveis que são semelhantes a este 
		List listaIdsUsuariosImoveisFavoritos = imovelfavoritosDao.findUsuariosImoveisFavoritosSemelhantes(idUsuario, form);
		
		if (!CollectionUtils.isEmpty(listaIdsUsuariosPrefImoveis))
			listaIdsFinal.addAll(listaIdsUsuariosPrefImoveis);
		
		if (!CollectionUtils.isEmpty(listaIdsUsuariosImovelVistado))
			listaIdsFinal.addAll(listaIdsUsuariosImovelVistado);
		
		if (!CollectionUtils.isEmpty(listaIdsUsuariosImoveisFavoritos))
			listaIdsFinal.addAll(listaIdsUsuariosImoveisFavoritos);
		
		if (!CollectionUtils.isEmpty(listaIdsFinal)){
			Usuario usuario = null;
			for (Long idUsuarioRec : listaIdsFinal){
				usuario = usuarioService.recuperarUsuarioPorId(idUsuarioRec);
				if ( usuario != null)
					listaFinal.add(usuario);
			}
		}			
			
		return listaFinal;
	}

}
