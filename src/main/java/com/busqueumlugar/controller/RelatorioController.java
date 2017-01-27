package com.busqueumlugar.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.enumerador.TipoImovelCompartilhadoEnum;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Relatorio;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelcomentarioService;
import com.busqueumlugar.service.ImovelPropostasService;
import com.busqueumlugar.service.ImovelvisualizadoService;
import com.busqueumlugar.service.InfoservicoService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.RelatorioService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;
import com.paypal.api.payments.Payment;


@Controller("relatorioController")
@RequestMapping("/relatorio")
@SessionAttributes({"relatorioForm", "servicoRelatorioForm"})
public class RelatorioController {
	
	private static final Logger log = LoggerFactory.getLogger(RelatorioController.class);
	
	@Autowired
	private RelatorioService relatorioService;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ParamservicoService paramservicoService;
	
	@Autowired
	private InfoservicoService infoservicoService;	
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	@Autowired
	private ImovelvisualizadoService imovelvisualizadoService;
	
	@Autowired
	private ImovelPropostasService imovelPropostasService;
	
	@Autowired
	private ImovelcomentarioService imovelcomentarioService;	
	
	@Autowired
	private ImovelFavoritosService imovelFavoritosService;
	
	private static final String DIR_PATH = "/relatorio/";
	private static final String DIR_PATH_COBRANCA_SERVICO = "/relatorio/cobrancaServico/"; 
	private static final String DIR_PATH_FILTRO_REL = "/relatorio/geracao/filtroRelatorio";
	private static final String DIR_PATH_REL_RESULTADO = "/relatorio/geracao/resultado/";
	private static final String  DIR_PATH_ADMIN_RELATORIOS = "/administracao/relatorios/";
	private static final String DIR_PATH_REL_COBRANCA_SERVICO = "/relatorio/cobrancaServico/";
	private static final String MENU = "menu";
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("relatorioForm") RelatorioForm form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("relatorioForm") RelatorioForm form,	
											  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
		
	
	@RequestMapping(value = "/buscarRelatorio/{item}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> buscarRelatorio(@PathVariable("item") String  item, HttpSession session)  {
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json; charset=utf-8");
	        return relatorioService.listarRelatoriosUsuarioSistemaPorOpcaoRelatorio(item, user.getPerfil());
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - buscarRelatorio");
			log.error("Mensagem Erro: " + e.getMessage());			
			return null;
		}
    }
	
	
    @RequestMapping(value = "/prepararRelatorios", method = RequestMethod.GET)
    public String goListarRelatorios(HttpSession session, ModelMap map){
    	
    	try {
    		  UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
    		  RelatorioForm form = new RelatorioForm(); 
    		  session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "relatorio");	  
    		  boolean seraCobrado = relatorioService.checarCobrancaModuloRelatorios(user.getId(), 
    				  															    user.getPerfil(), 
    				  															    user.getDataCadastro());
    		  if ( seraCobrado ){
    			 form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());		 
    			 
    			 if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
    				 form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.RELATORIO_PADRAO.getRotulo()));
    				 form.setAcao(ServicoValueEnum.RELATORIO_PADRAO.getRotulo());
    				 form.setOpcao(ServicoValueEnum.RELATORIO_PADRAO.getRotulo());			 
    			 }	 
    			 else if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo())) {
    				 form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.RELATORIO_CORRETOR.getRotulo()));
    				 form.setAcao(ServicoValueEnum.RELATORIO_CORRETOR.getRotulo());
    				 form.setOpcao(ServicoValueEnum.RELATORIO_CORRETOR.getRotulo());			 
    			 }	 
    			 else if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo())){
    				 form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo()));
    				 form.setAcao(ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo());
    				 form.setOpcao(ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo());			 
    			 }	 

    			 map.addAttribute("relatorioForm", form);
    			 return DIR_PATH_COBRANCA_SERVICO + "inicioCobrancaRelatorio";
    		  }
    		  else {
    			  form.setTipo("");
    			  form.setIdRelatorioSelecionado("");
    	          map.addAttribute("relatorioForm", form);
    	          return DIR_PATH + "inicio";
    		  }  
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - goListarRelatorios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
    @RequestMapping(value = "/selecionarRelatorioCobranca", method = RequestMethod.POST)
	public String selecionarRelatorio(HttpSession session, 
									  ModelMap map,
									  @ModelAttribute("relatorioForm") RelatorioForm form,
									  BindingResult result){
    	
    	try {
    		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
    		form.setIdUsuarioSelecionado(user.getId());
    		boolean possuiErro = servicoService.validarSelecaoPagamentoServico(form, result);
    		if ( ! possuiErro ){
    			servicoService.selecionarPagamentoServico(form, form.getIdInfoServicoPagamentoSelecionada(), form.getIdFormapagamentoSelecionada());		
    			if ( form.getFormaPagtoSelecionado().equals("Pag Seguro")){                  
    	            servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());            
    	            form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(form.getIdUsuarioSelecionado(), form.getAcao()));
    	            map.addAttribute("relatorioForm", form);
    	            return DIR_PATH_REL_COBRANCA_SERVICO + "confirmarPagtoPagSeguroRelatorio";                                            
    	        }
    	        else if ( form.getFormaPagtoSelecionado().equals("Pay Pal")){                                
    	        	servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());            
    	        	form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(form.getIdUsuarioSelecionado(), form.getAcao()));
    	        	map.addAttribute("relatorioForm", form);
    	            return DIR_PATH_REL_COBRANCA_SERVICO + "confirmarPagtoPayPalRelatorio";
    	        } 
    		}
    		            
    		 map.addAttribute("relatorioForm", form);
    		 return DIR_PATH_COBRANCA_SERVICO + "inicioCobrancaRelatorio";
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - selecionarRelatorio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
    @RequestMapping(value = "/confirmarPagamentoRelatorioPayPal", method = RequestMethod.POST)    
    public String confirmarPagamentoRelatorio(HttpSession session, 
											 HttpServletRequest req,	
											 HttpServletResponse resp,
											 ModelMap map,
											 @ModelAttribute("relatorioForm") RelatorioForm form){
    			
		try {
			Payment payment = null;
			payment = relatorioService.confirmarPagamentoPayPal(req, resp, form);
			if ( payment != null) {
				resp.sendRedirect(req.getAttribute("redirectURL").toString());
				return "response";
			}	
			else 
				return null;
		} catch (IOException e) {
			log.error("Erro metodo - RelatorioController - selecionarRelatorio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
    }

    @RequestMapping(value = "/voltarFiltroRelatorio", method = RequestMethod.POST)    
    public String voltarFiltroRelatorio(@ModelAttribute("relatorioForm") RelatorioForm form, ModelMap map){
    	try {
    		Relatorio relatorio = relatorioService.recuperarRelatorioPorId(form.getId());
        	BeanUtils.copyProperties(relatorio, form);
        	form.setListaEstados(estadosService.listarTodosEstadosSelect());
        	form.setOpcaoFiltroContato("");
        	map.addAttribute("relatorioForm", form);
        	map.addAttribute(MENU, relatorio.getItem());
        	return DIR_PATH_FILTRO_REL;
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - voltarFiltroRelatorio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
    @RequestMapping(value = "/voltarInicioRelatorio", method = RequestMethod.POST)    
    public String voltarInicioRelatorio(@ModelAttribute("relatorioForm") RelatorioForm form, ModelMap map){
    	try {
    		form.setTipo("");
        	form.setNome("");
        	form.setOpcaoFiltroContato("");
        	map.addAttribute("relatorioForm", form);
        	return DIR_PATH + "inicio";
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - voltarInicioRelatorio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
    @RequestMapping(value = "/simularRetornoPagto", method = RequestMethod.POST)    
    public String simularRetornoPagto(@ModelAttribute("relatorioForm") RelatorioForm form, ModelMap map){
    	try {
    		servicoService.simulaRetornoPagto(Long.parseLong(form.getIdSimulaServicoRelatorio()));
        	return DIR_PATH + "inicio";
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - simularRetornoPagto");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}    	
    }
    
    @RequestMapping(value = "/comboTipoRelatorioOnChange", method = RequestMethod.POST)    
    public String comboTipoRelatorioOnChange(@ModelAttribute("relatorioForm") RelatorioForm form){
    	return DIR_PATH + "inicio";
    }
    
    /**     
     * quantitativo == analitico
     * 
     * qualitativo = tendencia mercado
     */
    
    @RequestMapping(value = "/selecionarRelatorio", method = RequestMethod.POST)    
    public String selecionarRelatorio(ModelMap map, 
    								 @ModelAttribute("relatorioForm") RelatorioForm form,
    								 HttpSession session,
    								 BindingResult result){   
    	
    	try {
    		if (dadosValidaSelecaoRelatorio(form, result, map)){
        		Relatorio relatorio = relatorioService.recuperarRelatorioPorId(form.getId());
            	BeanUtils.copyProperties(relatorio, form);
            	form.setListaEstados(estadosService.listarTodosEstadosSelect());
            	session.setAttribute(UsuarioInterface.FUNCIONALIDADE, relatorio.getItem());
            	map.addAttribute("relatorioForm", form);
            	map.addAttribute(MENU, relatorio.getItem());
        		return DIR_PATH_FILTRO_REL;
        	}
        	else
        		map.addAttribute("relatorioForm", form);        	
        	
        	return DIR_PATH + "inicio";
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - selecionarRelatorio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}   	
    }
  

    @RequestMapping(value = "/gerarRelatorio", method = RequestMethod.POST)
    public String gerarRelatorio(ModelMap map, 
    							 @ModelAttribute("relatorioForm") RelatorioForm form,
    							 BindingResult result,
			  					 HttpSession session){
    	
    	try {
    		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
        	form.setUsuarioSessao(user);
        	if ( ! dadosValidaFiltroRelatorio(form, result, map, user)){
        		map.addAttribute(MENU, form.getItem());
        		map.addAttribute("relatorioForm", form);
        		return DIR_PATH_FILTRO_REL;
        	}
        	else {	    	
    	    	ObjectMapper mapper = new ObjectMapper();
    	    	try {
    	    		if ( form.getItem().equals("sobreEstados") ) { 		
    		            map.addAttribute(form.getItem(), estadosService.relatorioSobreEstados(form));    		            
    	    		}    
    		        else if ( form.getItem().equals("sobreCidades") ){ 
    		        	map.addAttribute(form.getItem(), cidadesService.relatorioSobreCidades(form));    		        	
    		        }	
    		        else if ( form.getItem().equals("sobreBairros") ) {
    		        	map.addAttribute(form.getItem(), bairrosService.relatorioSobreBairros(form));    		        	
    		        }	
    		        else if ( form.getItem().equals("quantImoveisPorLocalizacaoAcaoTipoImovel") ) {
    		        	map.addAttribute(form.getPath(), relatorioService.listarQuantidadeImoveisPorLocalAcaoTipoImovel(user.getId(), form));
    		        	map.addAttribute(form.getItem(), mapper.writeValueAsString(map.get(form.getPath())));
    		        }
    		        else if ( form.getItem().equals("tipoImoveisMaisProcuradoPorLocalizacao") )	 {        
    			        map.addAttribute(form.getPath(), relatorioService.listarTipoImoveisMaisProcuradosPorLocalizacao(user.getId(), form));
    			        map.addAttribute(form.getItem(), mapper.writeValueAsString(map.get(form.getPath())));
    		        }    
    		        else if ( form.getItem().equals("variacaoPrecosPorTipoImovelPorLocalizacao") ) {
    		            map.addAttribute(form.getPath(), relatorioService.recuperarVariacaoPrecoTipoImovel(user.getId(), form));
    		            map.addAttribute(form.getItem(), mapper.writeValueAsString(map.get(form.getPath())));
    		        }    
    		    	if ( form.getItem().equals("imoveisMaisVisualizados") ) {			
    		           	map.addAttribute(form.getPath(), imovelvisualizadoService.relatorioImoveisMaisVisitadosPorPeriodo(form));    		           	
    		    	}
    		        else if ( form.getItem().equals("imoveisMaisPropostados") ) {       	
    		            map.addAttribute(form.getPath(), imovelPropostasService.relatorioImoveisMaisPropostadosPorPeriodo(form));
    		        }    
    		        else if ( form.getItem().equals("imoveisMaisComentados") ) {       	
    		            map.addAttribute(form.getPath(), imovelcomentarioService.relatorioImoveisMaisComentadosPorPeriodo(form));
    		        }    
    		        else if ( form.getItem().equals("imoveisMaisAdotadosInteressados") ) {
    		            map.addAttribute(form.getPath(), imovelFavoritosService.relatorioImoveisMaisAdotadosInteressadosPorPeriodo(form));
    		        }
    		        // novo - 12/01/2015
    		        else if ( form.getItem().equals("usuariosMaisParceriasAceitas") ) {
    		            map.addAttribute(form.getPath(), usuarioService.relatorioUsuarioMaisCompartilhamentosAceitos(form, TipoImovelCompartilhadoEnum.PARCERIA.getRotulo()));    		            
    		        }    
    		        else if ( form.getItem().equals("usuariosMaisIntermediacoesAceitas") ) {
    		            map.addAttribute(form.getPath(), usuarioService.relatorioUsuarioMaisCompartilhamentosAceitos(form, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo()));    		            
    		        } 
    		        else if ( form.getItem().equals("usuariosImoveisMaisVisualizados") ) {
    		            map.addAttribute(form.getPath(), usuarioService.relatorioUsuariosImoveisMaisVisualizados(form));    		            
    		        }
    		        else if ( form.getItem().equals("usuariosImoveisMaisFavoritos") ) {
    		            map.addAttribute(form.getPath(), usuarioService.relatorioUsuariosImoveisMaisFavoritos(form));    		            
    		        }
    		        else if ( form.getItem().equals("usuariosImoveisMaisPropostas") ) {
    		            map.addAttribute(form.getPath(), usuarioService.relatorioUsuariosImoveisMaisPropostas(form));    		            
    		        } 
    		    	
    		    	map.addAttribute("filtrosUsados", relatorioService.checarFiltrosUtilizados(form));
    		    	map.addAttribute(MENU, form.getItem());
    		    	map.addAttribute("relatorioForm", form);		    	
    			} catch (Exception e) {
    				log.error("Erro metodo - RelatorioController - gerarRelatorio - p1");
    				log.error("Mensagem Erro: " + e.getMessage());
    				map.addAttribute("mensagemErroGeral", "S");
    				return ImovelService.PATH_ERRO_GERAL;
    			}
    	    	
        	} 
        	return DIR_PATH_REL_RESULTADO + form.getPath();	
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - gerarRelatorio - p2");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}   	
    }	
	
    private boolean dadosValidaFiltroRelatorio(RelatorioForm form, BindingResult result, ModelMap map, UsuarioForm usuarioForm) {
    	try {
    		boolean filtroValido = true;
        	if (result.hasErrors()) {
                map.addAttribute("relatorioForm", form);
                return false;        
        	}    
        	else 
        		filtroValido = relatorioService.validarFiltrosRelatorio(form, result, usuarioForm);
        	
    		return filtroValido;
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - dadosValidaFiltroRelatorio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return false;
		}
	}
    
    private boolean dadosValidaSelecaoRelatorio(RelatorioForm form,  BindingResult result, ModelMap map) {
    	
    	try {
    		boolean filtroValido = true;
        	if (result.hasErrors()) {            
                return false;        
        	}
        	else {
        		if (( form.getTipo() == null ) || (form.getTipo() != null && form.getTipo().equals("") )){
        			result.rejectValue("tipo", "opcao.selecao.tipo.relatorio.obrigatorio");
                	filtroValido = false;
        		}
        		
        		if (( form.getId() == null ) || (form.getId() != null && form.getId().intValue() == -1)){    		
        			result.rejectValue("id", "opcao.selecao.relatorio.obrigatorio");
                	filtroValido = false;
        		}	
        	}    	
        	return filtroValido;
        	
		} catch (Exception e) {
			log.error("Erro metodo - RelatorioController - ordenarUsuariosContatosIndicar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return false;
		}
  	}
	
}
