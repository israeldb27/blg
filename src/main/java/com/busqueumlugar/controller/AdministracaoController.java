package com.busqueumlugar.controller;


import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.annotate.JsonMethod;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.FormapagamentoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelMapaForm;
import com.busqueumlugar.form.ImoveldestaqueForm;
import com.busqueumlugar.form.ParametrosIniciaisForm;
import com.busqueumlugar.form.ParamservicoForm;
import com.busqueumlugar.form.PlanoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Paramservico;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.AdministracaoService;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImoveldestaqueService;
import com.busqueumlugar.service.MensagemService;
import com.busqueumlugar.service.ParametrosIniciaisService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.PlanoService;
import com.busqueumlugar.service.PlanousuarioService;
import com.busqueumlugar.service.RelatorioService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;
import com.mysql.jdbc.StringUtils;


@Controller("adminController")
@RequestMapping("/admin")
@SessionAttributes({"imovelForm","usuarioForm", "administracaoForm", "parametrosIniciaisForm"})
public class AdministracaoController {
	
	private static final Logger log = LoggerFactory.getLogger(AdministracaoController.class);
	
	@Autowired
	private MensagemService mensagemService;
	
	@Autowired
	private AdministracaoService administracaoService;
	
	@Autowired
	private  EstadosService estadosService;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private  UsuarioService usuarioService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	@Autowired
	private ParamservicoService paramservicoService;
	
	@Autowired
	private ParametrosIniciaisService parametrosIniciaisService;

	@Autowired
	private PlanoService planoService;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private PlanousuarioService planoUsuarioService;
	
	@Autowired
	private RelatorioService relatorioService;
	
	@Autowired
	private ImoveldestaqueService imoveldestaqueService;
	
	private static final String DIR_PATH_MENSAGEM  				= "/admin/mensagem/";
	private static final String DIR_PATH_IMOVEL    				= "/admin/imovel/";
	private static final String DIR_PATH_USUARIO   				= "/admin/usuarios/";
	private static final String DIR_PATH_SERVICO   				= "/admin/servico/";
	private static final String DIR_PATH_RELATORIO 				= "/admin/relatorios/";
	private static final String DIR_PATH_RELATORIO_CONTROLE 	= "/admin/relatorios/controle/"; 
	private static final String DIR_PATH_RELATORIO_USUARIO 		= "/admin/relatorios/relatoriosUsuarios/";
	private static final String DIR_PATH_RELATORIO_USUARIO_RESULTADO 		= "/admin/relatorios/relatoriosUsuarios/resultadoRelatorioUsuarios";
	private static final String DIR_PATH_RELATORIO_SERVICO 		= "/admin/relatorios/relatoriosServicos/";
	private static final String DIR_PATH_RELATORIO_SERVICO_RESULTADO 		= "/admin/relatorios/relatoriosServicos/resultadoRelatorioServicos";	
	private static final String DIR_PATH_RELATORIO_PLANO 		= "/admin/relatorios/relatoriosPlanos/";
	private static final String DIR_PATH_RELATORIO_PLANO_RESULTADO 		= "/admin/relatorios/relatoriosPlanos/resultadoRelatorioPlanos";
	private static final String DIR_PATH_RELATORIO_IMOVEL 		= "/admin/relatorios/relatoriosImoveis/";
	private static final String DIR_PATH_RELATORIO_IMOVEL_RESULTADO 		= "/admin/relatorios/relatoriosImoveis/resultadoRelatorioImoveis";
	private static final String DIR_PATH_RELATORIO_ASSINATURA 	= "/admin/relatorios/relatoriosAssinatura/";
	private static final String DIR_PATH_RELATORIO_ASSINATURA_RESULTADO 	= "/admin/relatorios/relatoriosAssinatura/resultadoRelatorioAssinaturas";
	private static final String DIR_PATH_PARAM_INICIAIS  				= "/admin/parametrosIniciais/";
	private static final String MENU = "menu";
	
	
	@RequestMapping(value = "/adicionarConfiguracaoAnuncioImovel/{dataInicio}/{dataFim}", method = RequestMethod.GET)	
	@ResponseBody
	public String adicionarConfiguracaoAnuncioImovel(@PathVariable("dataInicio") String dataInicio,
											     	 @PathVariable("dataFim") String dataFim,											   
											     	 HttpSession session, 	
											     	 ModelMap map, 											   
											     	 @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			String msg = imoveldestaqueService.validarCadastroAnuncioImovel(dataInicio, dataFim, form);
			if ( msg.equals("")){
				imoveldestaqueService.cadastrarAnuncioImovel(dataInicio, dataFim, form);
				form.setListaImovelAnuncio(imoveldestaqueService.recuperarListaAnuncioPorImovel(form.getId()));
				map.addAttribute("imovelForm", form );
				return "ok";
			}
			else {
				return msg;
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  adicionarConfiguracaoAnuncioImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
	}
	
	@RequestMapping(value = "/adicionarConfiguracao")	
	@ResponseBody
	public String adicionarConfiguracao(HttpSession session, 	
											     	 ModelMap map, 											   
											     	 @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			
				return "ok";
			
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  adicionarConfiguracaoAnuncioImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
	}
		
	
	@RequestMapping(value = "/prepararBuscaImoveisAnuncio", method = RequestMethod.GET)
	public String prepararBuscaImoveisAnuncio(ModelMap map, 
									   		  HttpSession session){
		
		ImoveldestaqueForm form = new ImoveldestaqueForm();
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		map.addAttribute("listaImoveisAnuncios", imoveldestaqueService.recuperarImoveisDestaqueAnuncioPorDataDestaque(form));
		map.addAttribute("imoveldestaqueForm", form);
		return DIR_PATH_IMOVEL + "buscarImovelAnuncioAdmin";    
	}
	
	@RequestMapping(value = "/buscaImoveisAnuncio", method = RequestMethod.POST)
	public String buscaImoveisAnuncio(ModelMap map, 
									  @ModelAttribute("ImoveldestaqueForm") ImoveldestaqueForm form,
									  HttpSession session){
		
		//form.setListaEstados(estadosService.listarTodosEstadosSelect());
		map.addAttribute("listaImoveisAnuncios", imoveldestaqueService.recuperarImoveisDestaqueAnuncioPorDataDestaque(form));
		map.addAttribute("imoveldestaqueForm", form);
		return DIR_PATH_IMOVEL + "buscarImovelAnuncioAdmin";    
	}
	
	@RequestMapping(value = "/pesquisarTudo", method = RequestMethod.POST)
    public String pesquisarTudo(@ModelAttribute("usuarioForm") UsuarioForm form,    							
							    HttpSession session, 	
							   	ModelMap map){
		
		if (! StringUtils.isNullOrEmpty(form.getValorBusca())){
			map.addAttribute("listaImoveis", imovelService.pesquisarTodosImoveis(form.getValorBusca()));
			map.addAttribute("listaUsuarios", usuarioService.pesquisarTodosUsuariosAdmin(form.getValorBusca()));
			map.addAttribute("listaServicos", paramservicoService.pesquisarTodosServicos(form.getValorBusca()));
			map.addAttribute("listaPlanos", planoService.pesquisarTodosPlanos(form.getValorBusca()));			
		}
		else 
			map.addAttribute("msgErroPesquisarTudo", MessageUtils.getMessage("msg.erro.busque.pessoas.imoveis.valor.busca.vazio"));
			
		return 	DIR_PATH_USUARIO + "resultadoPesquisa";	
    }
	
	@RequestMapping(value = "/buscarCidadesImovel/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaImovelCidadePorEstado(@ModelAttribute("administracaoForm") AdministracaoForm form, @PathVariable("idEstado") Integer idEstado, ModelMap map)  {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
        return form.getListaCidades() ;
    }
	
	
	@RequestMapping(value = "/buscarBairrosImovel/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaImovelBairroPorEstado(@ModelAttribute("administracaoForm") AdministracaoForm form, 
													@PathVariable("idCidade") Integer idCidade, 
													ModelMap map)  {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));        
        return form.getListaBairros();
    }
	
	@RequestMapping(value = "/listarParametrosIniciais")	
	public String listarParametrosIniciais(ModelMap map){	
		ParametrosIniciaisForm form = new ParametrosIniciaisForm();		
		map.addAttribute("listaParametrosIniciais", parametrosIniciaisService.listarTodosParametrosIniciais());
		map.addAttribute("parametrosIniciaisForm", form);	
		return DIR_PATH_PARAM_INICIAIS + "listarParametrosIniciais";			
	}
	
	@RequestMapping(value = "/excluirParametroInicial/{idParametro}", method = RequestMethod.GET)	
	public String excluirParametroInicial(@PathVariable Long idParametro,
									      ModelMap map){		
		parametrosIniciaisService.excluirParametroInicial(idParametro);
		map.addAttribute("listaParametrosIniciais", parametrosIniciaisService.listarTodosParametrosIniciais());			
		return DIR_PATH_PARAM_INICIAIS + "listarParametrosIniciais";			
	}
	
	@RequestMapping(value = "/confirmarExcluirParametroInicial/{idParametro}", method = RequestMethod.GET)	
	public String confirmarExcluirParametroInicial(@PathVariable Long idParametro,
									      		   ModelMap map){		
		parametrosIniciaisService.excluirParametroInicial(idParametro);		
		map.addAttribute("listaParametrosIniciais", parametrosIniciaisService.listarTodosParametrosIniciais());		
		return DIR_PATH_PARAM_INICIAIS + "listarParametrosIniciais";			
	}
	
	@RequestMapping(value = "/carregaModalDetalhesParametroInicial/{idParametro}")
	@ResponseBody
	public ParametrosIniciaisForm carregaModalDetalhesParametroInicial(@PathVariable("idParametro") Long idParametro,
											 			   				ModelMap map){	
		
		return parametrosIniciaisService.carregarParametrosIniciaisPorIdParametro(idParametro);
	}
	
	@RequestMapping(value = "/adicionarParametrosInicial")	
	public String adicionarParametrosInicial(@ModelAttribute("parametrosIniciaisForm") ParametrosIniciaisForm form,
											 HttpSession session,
											 BindingResult bindResult,
											 ModelMap map){	
		
		boolean isValido = parametrosIniciaisService.validarCadastroParametrosIniciais(form, bindResult);
		if ( isValido ){
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			parametrosIniciaisService.cadastrarParametroInicial(form, user.getId());
		}
		
		map.addAttribute("listaParametrosIniciais", parametrosIniciaisService.listarTodosParametrosIniciais());	
		map.addAttribute("parametrosIniciaisForm", form);	
		return DIR_PATH_PARAM_INICIAIS + "listarParametrosIniciais";
	}
	
	@RequestMapping(value = "/prepararEditarParametrosInicial/{idParametro}", method = RequestMethod.GET)	
	public String prepararEditarParametrosInicial(@ModelAttribute("parametrosIniciaisForm") ParametrosIniciaisForm form,
											 	  HttpSession session,
											 	  @PathVariable("idParametro") Long idParametro, 
											 	  ModelMap map){	
			
		map.addAttribute("parametrosIniciaisForm", parametrosIniciaisService.carregarParametrosIniciaisPorIdParametro(idParametro));	
		return DIR_PATH_PARAM_INICIAIS + "editarParametrosIniciais";
	}
	
	@RequestMapping(value = "/editarParametrosInicial")	
	public String editarParametrosInicial(@ModelAttribute("parametrosIniciaisForm") ParametrosIniciaisForm form,
											 HttpSession session,
											 BindingResult bindResult,
											 ModelMap map){	
		
		boolean isValido = parametrosIniciaisService.validarEdicaoParametrosIniciais(form, bindResult);
		if ( isValido ){
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			parametrosIniciaisService.editarParametroInicial(form, user.getId());
			map.addAttribute("listaParametrosIniciais", parametrosIniciaisService.listarTodosParametrosIniciais());	
			map.addAttribute("parametrosIniciaisForm", new ParametrosIniciaisForm());
			map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.edita.param.inicial"));			
			return DIR_PATH_PARAM_INICIAIS + "listarParametrosIniciais";
		}
		else {
			return DIR_PATH_PARAM_INICIAIS + "editarParametrosIniciais";
		}
	}
	 
	
	@RequestMapping(value = "/selecionarRelatorioUsuarios")	
	public String selecionarRelatorioUsuarios(@ModelAttribute("administracaoForm") AdministracaoForm form, 
											   ModelMap map,
											   BindingResult result){
	
		if (! StringUtils.isNullOrEmpty(form.getRelatorioSelecionado())){
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoRelatorio(form.getRelatorioSelecionado());
			form.setPerfilUsuario("");
			map.addAttribute("administracaoForm", form);	
			return DIR_PATH_RELATORIO_USUARIO + "filtroRelatorioUsuarios";
		}		
		else {
			result.rejectValue("relatorioSelecionado", "msg.erro.campo.obrigatorio");
			map.addAttribute("administracaoForm", form);
			return DIR_PATH_RELATORIO_USUARIO + "inicio";
		}
		
	}
	
	@RequestMapping(value = "/gerarRelatorioUsuarios")	
	public String gerarRelatorioUsuarios(@ModelAttribute("administracaoForm") AdministracaoForm form, 
									     ModelMap map,
									     BindingResult result){
		 
		boolean isValido = administracaoService.validarGerarRelatorios(form, "relatorioUsuario", result);
		map.addAttribute("administracaoForm", form);
		if ( isValido ){
			administracaoService.gerarRelatorioUsuarios(form, form.getTipoRelatorio());
			map.addAttribute("filtrosUsados", administracaoService.checarFiltrosUtilizados(form));			
			return DIR_PATH_RELATORIO_USUARIO_RESULTADO;
		}
		else 
			return DIR_PATH_RELATORIO_USUARIO + "filtroRelatorioUsuarios";
						
	}
	
	@RequestMapping(value = "/selecionarRelatorioServicos")	
	public String selecionarRelatorioServicos(@ModelAttribute("administracaoForm") AdministracaoForm form, 
											   ModelMap map,
											   BindingResult result){	
		
		if (! StringUtils.isNullOrEmpty(form.getRelatorioSelecionado())){
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoRelatorio(form.getRelatorioSelecionado());
			map.addAttribute("administracaoForm", form);	
			return DIR_PATH_RELATORIO_SERVICO + "filtroRelatorioServicos";
		}		
		else {
			result.rejectValue("relatorioSelecionado", "msg.erro.campo.obrigatorio");
			map.addAttribute("administracaoForm", form);
			return DIR_PATH_RELATORIO_SERVICO + "inicio";
		}
	}
	
	@RequestMapping(value = "/gerarRelatorioServicos")	
	public String gerarRelatorioServicos(@ModelAttribute("administracaoForm") AdministracaoForm form, 
									     ModelMap map,
									     BindingResult result){
		
		boolean isValido = administracaoService.validarGerarRelatorios(form, "relatorioServico", result);
		map.addAttribute("administracaoForm", form);
		if ( isValido ){
			//administracaoService.gerarRelatorioUsuarios(form, form.getTipoRelatorio());
			administracaoService.gerarRelatorioServicos(form, form.getTipoRelatorio());
			map.addAttribute("filtrosUsados", administracaoService.checarFiltrosUtilizados(form));			
			return DIR_PATH_RELATORIO_SERVICO_RESULTADO;
		}
		else 
			return DIR_PATH_RELATORIO_SERVICO + "filtroRelatorioServicos";		
		
	}
	
	@RequestMapping(value = "/selecionarRelatorioPlanos")	
	public String selecionarRelatorioPlanos(@ModelAttribute("administracaoForm") AdministracaoForm form, 
											ModelMap map,
										    BindingResult result){
	
		if (! StringUtils.isNullOrEmpty(form.getRelatorioSelecionado())){
			form.setTipoRelatorio(form.getRelatorioSelecionado());
			map.addAttribute("administracaoForm", form);
			return DIR_PATH_RELATORIO_PLANO + "filtroRelatorioPlanos";
		}
		else{
			result.rejectValue("relatorioSelecionado", "msg.erro.campo.obrigatorio");
			map.addAttribute("administracaoForm", form);
			return DIR_PATH_RELATORIO_PLANO + "inicio";
		}
					
	}
	
	@RequestMapping(value = "/gerarRelatorioPlanos")	
	public String gerarRelatorioPlanos(@ModelAttribute("administracaoForm") AdministracaoForm form, 
									    ModelMap map,
									    BindingResult result){
		
		boolean isValido = administracaoService.validarGerarRelatorios(form, "relatorioPlano", result);
		map.addAttribute("administracaoForm", form);
		if ( isValido ){
			administracaoService.gerarRelatorioPlanos(form, form.getTipoRelatorio());
			map.addAttribute("filtrosUsados", administracaoService.checarFiltrosUtilizados(form));			
			return DIR_PATH_RELATORIO_PLANO_RESULTADO;
		}
		else 
			return DIR_PATH_RELATORIO_PLANO + "filtroRelatorioPlanos";			
	
	}
	
	@RequestMapping(value = "/voltarFiltroRelatorio")	
	public String voltarFiltroRelatorio(@ModelAttribute("administracaoForm") AdministracaoForm form, 
										ModelMap map,
										BindingResult result){
		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoRelatorio(form.getRelatorioSelecionado());
			map.addAttribute("administracaoForm", form);	
			if (! StringUtils.isNullOrEmpty(form.getTipoFiltro())){
				if ( form.getTipoFiltro().equals("planos"))
					return DIR_PATH_RELATORIO_PLANO + "filtroRelatorioPlanos";
				else if ( form.getTipoFiltro().equals("imoveis"))
					return DIR_PATH_RELATORIO_IMOVEL + "filtroRelatorioImoveis";
				else if ( form.getTipoFiltro().equals("assinaturas"))					
					return DIR_PATH_RELATORIO_ASSINATURA + "filtroRelatorioAssinatura";
				else if ( form.getTipoFiltro().equals("usuarios"))					
					return DIR_PATH_RELATORIO_USUARIO + "filtroRelatorioUsuarios";
				else if ( form.getTipoFiltro().equals("servicos"))
					return DIR_PATH_RELATORIO_SERVICO + "filtroRelatorioServicos";				
			}			
			return null;					
	}
	
	@RequestMapping(value = "/voltarSelecaoRelatorio")	
	public String voltarSelecaoRelatorio(@ModelAttribute("administracaoForm") AdministracaoForm form, 
										ModelMap map,
										BindingResult result){
		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoRelatorio(form.getRelatorioSelecionado());
			map.addAttribute("administracaoForm", form);	
			if (! StringUtils.isNullOrEmpty(form.getTipoFiltro())){
				if ( form.getTipoFiltro().equals("planos"))
					return DIR_PATH_RELATORIO_PLANO + "inicio";
				else if ( form.getTipoFiltro().equals("imoveis"))
					return DIR_PATH_RELATORIO_IMOVEL + "inicio";
				else if ( form.getTipoFiltro().equals("assinaturas"))					
					return DIR_PATH_RELATORIO_ASSINATURA + "inicio";
				else if ( form.getTipoFiltro().equals("usuarios"))					
					return DIR_PATH_RELATORIO_USUARIO + "inicio";
				else if ( form.getTipoFiltro().equals("servicos"))
					return DIR_PATH_RELATORIO_SERVICO + "inicio";				
			}			
			return null;					
	}
	
	
	@RequestMapping(value = "/gerarRelatorioImoveis")	
	public String gerarRelatorioImoveis(@ModelAttribute("administracaoForm") AdministracaoForm form, 
										BindingResult result,
										ModelMap map){	
		
		
		boolean isValido = administracaoService.validarGerarRelatorios(form, "relatorioImovel", result);
		map.addAttribute("administracaoForm", form);
		if ( isValido ){
			if ( form.getTipoRelatorio().equals("quantTotal")) {
				administracaoService.gerarRelatorioImoveis(form, "quantTotal");					
			}	
			else {
				administracaoService.gerarRelatorioImoveis(form, form.getTipoRelatorio());
				map.addAttribute("listaImoveis", form.getListaImoveis());
			}
			
			map.addAttribute("filtrosUsados", administracaoService.checarFiltrosUtilizados(form));			
			return DIR_PATH_RELATORIO_IMOVEL_RESULTADO;
		}
		else 
			return DIR_PATH_RELATORIO_IMOVEL + "filtroRelatorioImoveis";	
		
	}
	
	@RequestMapping(value = "/selecionarRelatorioImoveis")	
	public String selecionarRelatorioImoveis(@ModelAttribute("administracaoForm") AdministracaoForm form, 
											 ModelMap map,
										     BindingResult result){
		
		if (! StringUtils.isNullOrEmpty(form.getRelatorioSelecionado())){
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoRelatorio(form.getRelatorioSelecionado());
			map.addAttribute("administracaoForm", form);
			return DIR_PATH_RELATORIO_IMOVEL + "filtroRelatorioImoveis";
		}
		else {
			result.rejectValue("relatorioSelecionado", "msg.erro.campo.obrigatorio");
			map.addAttribute("administracaoForm", form);
			return DIR_PATH_RELATORIO_IMOVEL + "inicio";
		}		
	}	


	@RequestMapping(value = "/selecionarRelatorioAssinaturas")	
	public String selecionarRelatorioAssinaturas(@ModelAttribute("administracaoForm") AdministracaoForm form, 
											     ModelMap map,
											     BindingResult result){
											
		if (! StringUtils.isNullOrEmpty(form.getRelatorioSelecionado())){
			form.setTipoRelatorio(form.getRelatorioSelecionado());
			map.addAttribute("administracaoForm", form);
			return DIR_PATH_RELATORIO_ASSINATURA + "filtroRelatorioAssinatura";
		}
		else {
			result.rejectValue("relatorioSelecionado", "msg.erro.campo.obrigatorio");
			map.addAttribute("administracaoForm", form);
			return DIR_PATH_RELATORIO_ASSINATURA + "inicio";
		}			
						
	}
	
	@RequestMapping(value = "/gerarRelatorioAssinaturas")	
	public String gerarRelatorioAssinaturas(@ModelAttribute("administracaoForm") AdministracaoForm form, 
									         ModelMap map){
		 
		administracaoService.gerarRelatorioAssinaturas(form, form.getTipoRelatorio());
		map.addAttribute("filtrosUsados", administracaoService.checarFiltrosUtilizados(form));
		map.addAttribute("administracaoForm", form);
		return DIR_PATH_RELATORIO_ASSINATURA_RESULTADO;				
	}
	
	@RequestMapping(value = "/prepararPermissionamentoRelatorioSistema/{idRelatorio}", method = RequestMethod.GET)
	public String prepararPermissionamentoRelatorioSistema(@PathVariable Long idRelatorio,
														   ModelMap map){
		RelatorioForm form = new RelatorioForm();
		form.setIdRelatorioSelecionado(String.valueOf(idRelatorio));
		BeanUtils.copyProperties(relatorioService.recuperarRelatorioPorId(idRelatorio), form);		
		map.addAttribute("listaPermissoes", relatorioService.listarAcessoPerfisRelatorioSistema(idRelatorio));
		map.addAttribute("relatorioForm", form);
		
		return DIR_PATH_RELATORIO_CONTROLE + "permissionamentoRelatorioSistema";		
	}
	
	@RequestMapping(value = "/adicionarPermissaoRelatorio", method = RequestMethod.POST)
	public String adicionarPermissaoRelatorio(@ModelAttribute("relatorioForm") RelatorioForm form, 
											  ModelMap map){
		
		form.setId(Long.valueOf(form.getIdRelatorioSelecionado()));
		relatorioService.adicionarPerfilRelatorio(form);
		BeanUtils.copyProperties(relatorioService.recuperarRelatorioPorId(Long.parseLong(form.getIdRelatorioSelecionado())), form);
		map.addAttribute("listaPermissoes", relatorioService.listarAcessoPerfisRelatorioSistema(Long.parseLong(form.getIdRelatorioSelecionado())));
		map.addAttribute("relatorioForm", form);
		
		return DIR_PATH_RELATORIO_CONTROLE + "permissionamentoRelatorioSistema";	
	}
	
	@RequestMapping(value = "/excluirPermissionamento/{idPerfilAcessoRelatorio}", method = RequestMethod.GET)
	public String excluirPermissionamento(@PathVariable Long idPerfilAcessoRelatorio,			
										  @ModelAttribute("relatorioForm") RelatorioForm form, 
										  ModelMap map){
		
		relatorioService.carregaRelatorioFormPorPerfilAcessoRelatorio(idPerfilAcessoRelatorio, form);
		relatorioService.excluirPerfilRelatorio(idPerfilAcessoRelatorio);		
		BeanUtils.copyProperties(relatorioService.recuperarRelatorioPorId(Long.parseLong(form.getIdRelatorioSelecionado())), form);
		map.addAttribute("listaPermissoes", relatorioService.listarAcessoPerfisRelatorioSistema(Long.parseLong(form.getIdRelatorioSelecionado())));
		map.addAttribute("relatorioForm", form);
		
		return DIR_PATH_RELATORIO_CONTROLE + "permissionamentoRelatorioSistema";	
	}
	
	@RequestMapping(value = "/adicionarRelatorioSistema", method = RequestMethod.POST)
	public String adicionarRelatorioSistema(@ModelAttribute("relatorioForm") RelatorioForm form, 
											ModelMap map){
		
		relatorioService.cadastrarRelatorioSistema(form);
		map.addAttribute("listaRelatorioSistema", relatorioService.listarTodosRelatoriosSistemaDisponiveis());
		map.addAttribute("relatorioForm", new RelatorioForm());
		return DIR_PATH_RELATORIO_CONTROLE + "listarRelatoriosSistema";				
	}
	
	@RequestMapping(value = "/manterRelatorioSistema", method = RequestMethod.POST)
	public String manterRelatorioSistema(@ModelAttribute("relatorioForm") RelatorioForm form, 
										 ModelMap map){
		
		
		relatorioService.atualizarRelatorioUsuarioSistema(form);			
		map.addAttribute("relatorioForm", form);
		map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.atualizar.relatorio"));
		return DIR_PATH_RELATORIO_CONTROLE + "manterRelatorioSistema";
		
	/*	else if ( form.getTipoAcao().equals("excluir")){
			relatorioService.excluirRelatorioUsuarioSistema(form);
			map.addAttribute("listaRelatorioSistema", relatorioService.listarTodosRelatoriosSistemaDisponiveis());
			map.addAttribute("relatorioForm", new RelatorioForm());
			return DIR_PATH_RELATORIO_CONTROLE + "listarRelatoriosSistema";
		}
		else
			return null;*/
	}
	
	@RequestMapping(value = "/prepararManterRelatorioSistema/{idRelatorio}/{tipoAcao}", method = RequestMethod.GET)
	public String prepararManterRelatorioSistema(@PathVariable Long idRelatorio, 
												 @PathVariable String tipoAcao, 
												 ModelMap map){
		RelatorioForm form = new RelatorioForm();
		BeanUtils.copyProperties(relatorioService.recuperarRelatorioPorId(idRelatorio), form);
		form.setTipoAcao(tipoAcao);
		map.addAttribute("relatorioForm", form);
		return DIR_PATH_RELATORIO_CONTROLE + "manterRelatorioSistema";		
	}
	
	@RequestMapping(value = "/prepararRelatorios/{tipoRelatorio}", method = RequestMethod.GET)
	public String prepararRelatorios(@PathVariable String tipoRelatorio, ModelMap map){	
		
		if ( tipoRelatorio.equals("relatorioSistema")){
			map.addAttribute("listaRelatorioSistema", relatorioService.listarTodosRelatoriosSistemaDisponiveis());
			map.addAttribute("relatorioForm", new RelatorioForm());
			return DIR_PATH_RELATORIO_CONTROLE + "listarRelatoriosSistema";
		}
		else  {
			map.addAttribute("administracaoForm", new AdministracaoForm());
			if ( tipoRelatorio.equals("relatoriosUsuarios"))
				return DIR_PATH_RELATORIO_USUARIO + "inicio";
			else if ( tipoRelatorio.equals("relatoriosImoveis"))
				return DIR_PATH_RELATORIO_IMOVEL + "inicio";
			else if ( tipoRelatorio.equals("relatoriosPlanos"))
				return DIR_PATH_RELATORIO_PLANO + "inicio";
			else if ( tipoRelatorio.equals("relatoriosAssinatura"))
				return DIR_PATH_RELATORIO_ASSINATURA + "inicio";
			else if ( tipoRelatorio.equals("relatoriosServicos"))
				return DIR_PATH_RELATORIO_SERVICO + "inicio";
			else
				return null;
		}
	}

	
	@RequestMapping(value = "/manterPlano/{tipoAcao}/{idPlano}", method = RequestMethod.GET)
	public String manterPlano(@PathVariable String tipoAcao, @PathVariable Long idPlano , ModelMap map){
		if ( tipoAcao.equals("atualizar")) {
			PlanoForm form = new PlanoForm();
			BeanUtils.copyProperties(planoService.recuperarPlanoPorId(idPlano), form);
			map.addAttribute("planoForm", form);
			return DIR_PATH_SERVICO + "atualizarPlanos"; 			
		}	
		else if ( tipoAcao.equals("excluir")) {
			planoService.excluirPlano(idPlano);	
			map.addAttribute("listaPlanos", planoService.listarTodosDisponiveis());
			map.addAttribute("planoForm", new PlanoForm());
			return DIR_PATH_SERVICO + "cadastrarPlanos"; 
		}
		return null;		
	}
	
	@RequestMapping(value = "/atualizarPlano", method = RequestMethod.POST)
	public String manterPlano(@ModelAttribute("planoForm") PlanoForm form, 
							  BindingResult result,	
							  ModelMap map){
		
		boolean isValido = planoService.validarEdicaoPlano(form, result);
		if ( isValido ){
			planoService.atualizarPlano(form);
			map.addAttribute("planoForm", new PlanoForm());		
		}
		else
			map.addAttribute("planoForm", form);		
		
		map.addAttribute("listaPlanos", planoService.listarTodosDisponiveis());		
		return DIR_PATH_SERVICO + "cadastrarPlanos";
	}
	
	@RequestMapping(value = "/carregaModalDetalhesPlano/{idPlano}")
	@ResponseBody
	public PlanoForm carregaModalDetalhesPlano(@PathVariable("idPlano") Long idPlano,
											   ModelMap map){	
		
		return planoService.carregaPlanoFormPorIdPlano(idPlano);
	}

	
	@RequestMapping(value = "/cadastrarPlano", method = RequestMethod.POST)
	public String cadastrarPlano(@ModelAttribute("planoForm") PlanoForm form,
								 BindingResult result,			
								 ModelMap map){			
		boolean isValido = planoService.validarCadastroPlano(form, result);
		if ( isValido ){
			planoService.cadastrarNovoPlano(form);
			map.addAttribute("planoForm", new PlanoForm());
			map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.cadastro.plano"));
		}
		else
			map.addAttribute("planoForm", form);
		
		map.addAttribute("listaPlanos", planoService.listarTodosDisponiveis() );		
		return DIR_PATH_SERVICO + "cadastrarPlanos";
	}
	
	@RequestMapping(value = "/confirmarExcluirPlano/{idPlano}", method = RequestMethod.GET)	
	public String confirmarExcluirPlano(@PathVariable Long idPlano,
									    ModelMap map){		
		planoService.excluirPlano(idPlano);		
		map.addAttribute("listaPlanos", planoService.listarTodosDisponiveis() );		
		return DIR_PATH_SERVICO + "cadastrarPlanos";			
	}
	
	@RequestMapping(value = "/prepararCadastroPlanos", method = RequestMethod.GET)
	public String goCadastrarPlano(ModelMap map){				
		map.addAttribute("listaPlanos", planoService.listarTodosDisponiveis() );
		map.addAttribute("planoForm", new PlanoForm());
		return DIR_PATH_SERVICO + "cadastrarPlanos";
	}
	
	
	@RequestMapping(value = "/excluirFormasPagto/{idFormaPagamento}", method = RequestMethod.GET)
	public String excluirFormasPagto(@PathVariable Long idFormaPagamento, 
									 ModelMap map){		
		paramservicoService.excluirTipoFormaPagamento(idFormaPagamento);
		map.addAttribute("listaFormasPagto", paramservicoService.listarTodasFormasPagamento());
		map.addAttribute("formaPagamentoForm", new FormapagamentoForm());
		return DIR_PATH_SERVICO + "cadastrarFormasPagamento";
	}
	

	
	@RequestMapping(value = "/cadastrarFormaPagamento", method = RequestMethod.POST)
	public String cadastrarFormaPagamento(@ModelAttribute("formaPagamentoForm") FormapagamentoForm form,
										  BindingResult result,	
										  ModelMap map){		
		boolean isValido = paramservicoService.validarCadastroTipoFormaPagamento(form, result);
		if ( isValido ){
			paramservicoService.cadastrarTipoFormaPagamento(form);
			map.addAttribute("formaPagamentoForm", new FormapagamentoForm());
			map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.cadastro.tipo.forma.pagto"));
		}
		else
			map.addAttribute("formaPagamentoForm", form);
		
		map.addAttribute("listaFormasPagto", paramservicoService.listarTodasFormasPagamento());		
		return DIR_PATH_SERVICO + "cadastrarFormasPagamento";
	}

	@RequestMapping(value = "/prepararAtualizacaoFormasPagto/{idFormaPagamento}", method = RequestMethod.GET)
	public String prepararAtualizacaoFormasPagto(@PathVariable Long idFormaPagamento, 
									 			 ModelMap map){	
		
		FormapagamentoForm form = new FormapagamentoForm();
		BeanUtils.copyProperties(paramservicoService.recuperaTipoFormaPagtoPorId(idFormaPagamento), form);
		map.addAttribute("formaPagamentoForm", form);
		return DIR_PATH_SERVICO + "atualizarFormasPagamento";
	}
	
	@RequestMapping(value = "/atualizarFormaPagamento", method = RequestMethod.POST)
	public String atualizarFormaPagamento(@ModelAttribute("formaPagamentoForm") FormapagamentoForm form,
										  BindingResult result,	
										  ModelMap map){
		
		boolean isValido = paramservicoService.validarAtualizarTipoFormaPagamento(form, result);		
		if ( isValido ){
			paramservicoService.atualizarFormasPagamento(form);
			map.addAttribute("formaPagamentoForm", new FormapagamentoForm());
			map.addAttribute("listaFormasPagto", paramservicoService.listarTodasFormasPagamento());
			return DIR_PATH_SERVICO + "cadastrarFormasPagamento";
		}
		else {
			map.addAttribute("formaPagamentoForm", form);
			map.addAttribute("listaFormasPagto", paramservicoService.listarTodasFormasPagamento());
			return DIR_PATH_SERVICO + "atualizarFormasPagamento";
		}
	}
	
	@RequestMapping(value = "/prepararCadastroFormasPagto", method = RequestMethod.GET)
	public String goCadastrarFormasPagto(ModelMap map){				
		map.addAttribute("listaFormasPagto", paramservicoService.listarTodasFormasPagamento());
		map.addAttribute("formaPagamentoForm", new FormapagamentoForm());
		return DIR_PATH_SERVICO + "cadastrarFormasPagamento";
	}
	
	@RequestMapping(value = "/manterParamServico/{tipoAcao}/{idParam}", method = RequestMethod.GET)
	public String manterParamServico(@PathVariable String tipoAcao, 
									 @PathVariable Long idParam, 
									 ModelMap map){
		if ( tipoAcao.equals("atualizar")){
			Paramservico param = paramservicoService.recuperarParamservicoPorId(idParam);
			ParamservicoForm form = new ParamservicoForm();
			BeanUtils.copyProperties(param, form);
			map.addAttribute("paramServicoForm", form);
			return DIR_PATH_SERVICO + "atualizarParametroServico";
		}
		else if ( tipoAcao.equals("excluir")){ 
			paramservicoService.excluirParametro(idParam);
			map.addAttribute("listaParamServico", paramservicoService.recuperaTodosParametros());
			map.addAttribute("paramServicoForm", new ParamservicoForm());
			return DIR_PATH_SERVICO + "cadastrarParametroServico";
		}
		return null;
	}
	
	
	@RequestMapping(value = "/confirmarExcluirParametroServico/{idParametro}", method = RequestMethod.GET)	
	public String confirmarExcluirParametroServico(@PathVariable Long idParametro,
									      		   ModelMap map){		
		paramservicoService.excluirParametro(idParametro);		
		map.addAttribute("listaParamServico", paramservicoService.recuperaTodosParametros());	
		return DIR_PATH_SERVICO + "cadastrarParametroServico";			
	}
	
	
	@RequestMapping(value = "/atualizarParametroServico", method = RequestMethod.POST)
	public String atualizarParametroServico(@ModelAttribute("paramServicoForm") ParamservicoForm form,
											BindingResult result,
											ModelMap map){
		
		boolean isValido = paramservicoService.validarAtualizacaoParamServic(form, result);
		if ( isValido ) {
			paramservicoService.atualizarParametroServico(form);			
			map.addAttribute("paramServicoForm", new ParamservicoForm());
			map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.edicao.param.servico"));
			map.addAttribute("listaParamServico", paramservicoService.recuperaTodosParametros());
			return DIR_PATH_SERVICO + "cadastrarParametroServico";
		}
		else {
			map.addAttribute("paramServicoForm", form);
			return DIR_PATH_SERVICO + "atualizarParametroServico";
		}
				
	}
	
	@RequestMapping(value = "/prepararCadastroParamServico", method = RequestMethod.GET)
	public String goCadastrarParametroServico(ModelMap map){				
		map.addAttribute("listaParamServico", paramservicoService.recuperaTodosParametros());
		map.addAttribute("paramServicoForm", new ParamservicoForm());
		return DIR_PATH_SERVICO + "cadastrarParametroServico";
	}
	
	@RequestMapping(value = "/cadastrarParametroServico", method = RequestMethod.POST)
	public String cadastrarParametroServico(@ModelAttribute("paramServicoForm") ParamservicoForm form, 
											BindingResult result,
											ModelMap map){	
		boolean isValido = paramservicoService.validarCadastroParamServico(form, result);
		if ( isValido ) {
			paramservicoService.cadastrarParametro(form);
			map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.cadastro.param.servico"));
			map.addAttribute("paramServicoForm", new ParamservicoForm());
		}
		else
			map.addAttribute("paramServicoForm", form);
		
		map.addAttribute("listaParamServico", paramservicoService.recuperaTodosParametros());		
		return DIR_PATH_SERVICO + "cadastrarParametroServico";
	}
	
	@RequestMapping(value = "/filtrarImoveisPerfilUsuario", method = RequestMethod.POST)
	public String filtrarImoveisPerfilUsuario(@ModelAttribute("imovelForm") ImovelForm form, 
							   				  ModelMap map, 
							   				  HttpSession session){
		
		map.addAttribute("listaImoveisPerfilUsuario", imovelService.buscar(form, form.getIdUsuarioPerfil(), form.getOpcaoOrdenacao()));
		map.addAttribute("imovelForm", form);
		return DIR_PATH_IMOVEL + "listarImoveisPerfilUsuarioAdmin";    
	}
	
	@RequestMapping(value = "/visualizarImoveisPerfilUsuarioAdmin/{idUsuario}", method = RequestMethod.GET)
	public String visualizarImoveisPerfilUsuarioAdmin(@PathVariable("idUsuario") Long idUsuario,  	
													  ModelMap map, 
													  HttpSession session){
		
		ImovelForm form = new ImovelForm();
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		form.setIdUsuarioPerfil(idUsuario);		
		map.addAttribute("listaImoveisPerfilUsuario", imovelService.buscar(form, idUsuario, null));
		map.addAttribute("imovelForm", form);
		map.addAttribute("usuarioForm", usuarioService.prepararDetalhesUsuarioForm(idUsuario, null));
		return DIR_PATH_IMOVEL + "listarImoveisPerfilUsuarioAdmin";    
	}
	

	
	@RequestMapping(value = "/buscarImovel", method = RequestMethod.POST)
	public String buscarImovel(@ModelAttribute("imovelForm") ImovelForm form, 
							   ModelMap map, 
							   HttpSession session){
		
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		List<Imovel> lista = imovelService.buscar(form, null, null);	
		map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));
		map.addAttribute("listaBuscarImoveis", lista);
		map.addAttribute("imovelForm", form);
		return DIR_PATH_IMOVEL + "buscarImovelAdmin";    
	}
	
	@RequestMapping(value = "/buscarImovelMapa", method = RequestMethod.POST)	
	public String buscarImovelMapa(@ModelAttribute("imovelForm") ImovelForm form, 
								   ModelMap map, 
								   HttpSession session){
									   
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		List<ImovelMapaForm> lista = null;
		int tam = 0;
		try {
			 lista = imovelService.buscarImoveisMapa(form, null);
			 tam = AppUtil.recuperarQuantidadeLista(lista);
			 if (tam > 0 ){
				 json = mapper.writeValueAsString(lista);
				 map.addAttribute("listaImoveis", json );
			 }
			 else
				 map.addAttribute("listaImoveis", null );
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.addAttribute("quantTotalImoveis", tam);		
		return DIR_PATH_IMOVEL + "buscarImovelPorMapaAdmin";       
	}
	
	@RequestMapping(value = "/prepararBuscaImoveisPorMapa", method = RequestMethod.GET)
	public String prepararBuscaImoveisPorMapa(ModelMap map, 
											  HttpSession session){		
		ImovelForm form = new ImovelForm();
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		map.addAttribute("listaImoveis", null );
		map.addAttribute("imovelForm", form);
		return DIR_PATH_IMOVEL + "buscarImovelPorMapaAdmin";    
	}	
	
	@RequestMapping(value = "/prepararImovelDestaque/{idImovel}", method = RequestMethod.GET)
	public String prepararImovelDestaque(@PathVariable("idImovel") Long idImovel,    	 
										 @ModelAttribute("imovelForm") ImovelForm form, 
										 ModelMap map, 
										 HttpSession session){
		// Criar a tabela ImovelDestaque
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		imovelService.preparaImovelDestaqueFormAdmin(idImovel, form);
		map.addAttribute("imovelForm", form);
		return DIR_PATH_IMOVEL + "cadastrarImovelDestaque";    
	}
	
	@RequestMapping(value = "/cadastrarAnuncioImovelDestaque", method = RequestMethod.POST)
	public String cadastrarAnuncioImovelDestaque(@ModelAttribute("imovelForm") ImovelForm form, 
										         ModelMap map, 
												 HttpSession session){				
												 
		imovelService.cadastrarImovelDestaque(form);
		form.setListaImoveisDestaque(imoveldestaqueService.recuperarImoveisDestaquePorIdImovel(form.getId()));
		map.addAttribute("imovelForm", form);
		return DIR_PATH_IMOVEL + "cadastrarImovelDestaque";    
	}
	
	@RequestMapping(value = "/excluirImoveldestaque/{idImovelDestaque}", method = RequestMethod.GET)
	public String excluirImoveldestaque( @PathVariable("idImovelDestaque") Long idImovelDestaque,    	 
										 @ModelAttribute("imovelForm") ImovelForm form, 
										 ModelMap map, 
										 HttpSession session){				
		imoveldestaqueService.excluirImovelDestaque(idImovelDestaque);
		form.setListaImoveisDestaque(imoveldestaqueService.recuperarImoveisDestaquePorIdImovel(form.getId()));
		map.addAttribute("imovelForm", form);
		return DIR_PATH_IMOVEL + "cadastrarImovelDestaque";    
	}	
	
	
	@RequestMapping(value="/ordenaBuscarImoveis", method = RequestMethod.POST)	
	public String ordenaBuscarImoveis(ModelMap map, 
									  @ModelAttribute("imovelForm") ImovelForm form, 
									  HttpSession session){
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		map.addAttribute("imovelForm", form);
		List<Imovel> lista = imovelService.buscar(form, user.getId(), form.getOpcaoOrdenacao());
		map.addAttribute("listaBuscarImoveis", lista); 	
		map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));		
		return DIR_PATH_IMOVEL + "buscarImovelAdmin";
	}	
	
	@RequestMapping(value="/detalhesImovel/{idImovel}", method = RequestMethod.GET)
	public String visualizarDetalhesImovel(@PathVariable Long idImovel, 
										   HttpSession session, 
										   ModelMap map){		
		ImovelForm form = new ImovelForm();		
		imovelService.preparaDetalhesImovelFormAdmin(idImovel, form);
		map.addAttribute("imovelForm", form);
		return DIR_PATH_IMOVEL + "detalhesImovel";
	}
	
   @RequestMapping(value = "/detalhesUsuario/{idUsuario}", method = RequestMethod.GET)    
    public String detalhesUsuario(@PathVariable("idUsuario") Long idUsuario,    	 
    						      ModelMap map){
	    
	    UsuarioForm form = usuarioService.prepararDetalhesUsuarioFormAdmin(idUsuario);
	    map.addAttribute("usuarioForm", form);
    	return DIR_PATH_USUARIO + "detalhesUsuario";        
    }
   
   @RequestMapping(value = "/detalhesUsuarioAdmin/{idUsuario}", method = RequestMethod.GET)    
   public String detalhesUsuarioAdmin(@PathVariable("idUsuario") Long idUsuario,    	 
   						      		  ModelMap map){
	    
	    map.addAttribute("usuarioForm", usuarioService.prepararDetalhesUsuarioFormAdmin(idUsuario));
	    return DIR_PATH_USUARIO + "detalhesUsuarioAdmin";        
   }
   
   @RequestMapping(value = "/concederPlano", method = RequestMethod.POST)    
   public String concederPlano(String idPlanoSelecionado,  
		   					   @ModelAttribute("usuarioForm") UsuarioForm form,  	 
   						       ModelMap map){
	    planoUsuarioService.concedePlano(form.getId(), Long.parseLong(idPlanoSelecionado));
	    form.setListaPlanos(planoUsuarioService.recuperarPlanosPorUsuario(form.getId()));
	    map.addAttribute("usuarioForm", form);
	    return DIR_PATH_USUARIO + "detalhesUsuario";        
   }
   
   @RequestMapping(value = "/revogarPlano/{idPlanoSelecionado}", method = RequestMethod.GET)    
   public String revogarPlano(@PathVariable("idPlanoSelecionado") Long idPlanoSelecionado,  
		   					  @ModelAttribute("usuarioForm") UsuarioForm form,  	 
   						      ModelMap map){

	    planoUsuarioService.revogarPlano(form.getId(), idPlanoSelecionado);
	    form.setListaPlanos(planoUsuarioService.recuperarPlanosPorUsuario(form.getId()));
	    form.setListaServicos(servicoService.recuperarServicosDisponiveisPorUsuario(form.getId()));
	    map.addAttribute("usuarioForm", form);
	    return DIR_PATH_USUARIO + "detalhesUsuario";        
   }
   
   @RequestMapping(value = "/concederServico", method = RequestMethod.POST)    
   public String concederServico(String servicoSelecionado, 
		   						 String quantidadeTempo,
		   						 @ModelAttribute("usuarioForm") UsuarioForm form,  	 
		   						 ModelMap map){
	    
	    servicoService.concederServicoUsuario(form.getId(), servicoSelecionado, quantidadeTempo, quantidadeTempo);
	    form.setListaServicos(servicoService.recuperarServicosDisponiveisPorUsuario(form.getId()));
	    map.addAttribute("usuarioForm", form);
	    return DIR_PATH_USUARIO + "detalhesUsuario";
   }
   
   @RequestMapping(value = "/revogarServico/{idServicoSelecionado}", method = RequestMethod.GET)    
   public String revogarServico(@PathVariable("idServicoSelecionado") Long idServicoSelecionado,
		   						@ModelAttribute("usuarioForm") UsuarioForm form,  	 
		   						ModelMap map){
	    
	    servicoService.revogarServicoUsuario(form.getId(), idServicoSelecionado);
	    form.setListaServicos(servicoService.recuperarServicosDisponiveisPorUsuario(form.getId()));
	    map.addAttribute("usuarioForm", form);
	    return DIR_PATH_USUARIO + "detalhesUsuario";        
   }
   
   @RequestMapping(value = "/suspenderUsuario", method = RequestMethod.POST)    
   public String suspenderUsuario(String tempoSuspensao, 
		   						  String motivoSuspensao,
		   						  @ModelAttribute("usuarioForm") UsuarioForm form,  	 
		   						  ModelMap map){
	    
	    usuarioService.suspenderUsuario(form.getId(), tempoSuspensao, motivoSuspensao);
	    map.addAttribute("usuarioForm", form);
	    return DIR_PATH_USUARIO + "detalhesUsuario";
   }
   
   @RequestMapping(value = "/cancelarSuspensaoUsuario", method = RequestMethod.POST)    
   public String cancelarSuspensaoUsuario(@ModelAttribute("usuarioForm") UsuarioForm form,  	 
		   						  	      ModelMap map){
	    
	    usuarioService.cancelarSuspensaoUsuario(form.getId());
	    map.addAttribute("usuarioForm", form);
	    return DIR_PATH_USUARIO + "detalhesUsuario";
   }
   
   
	
	@RequestMapping(value = "/prepararBuscaImoveis", method = RequestMethod.GET)
	public String goBuscarImoveis(ModelMap map, 
								  HttpSession session){		
		ImovelForm form = new ImovelForm();
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		map.addAttribute("imovelForm", form);
		return DIR_PATH_IMOVEL + "buscarImovelAdmin";    
	}
	
	@RequestMapping(value = "/prepararCadastroUsuarioAdmin", method = RequestMethod.GET)
	public String prepararCadastroUsuarioAdmin(ModelMap map, 
											   HttpSession session){		
		UsuarioForm form = new UsuarioForm();	
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		usuarioService.preparaCampoDataNascimento(form);
		map.addAttribute("usuarioForm", form);			
		return DIR_PATH_USUARIO + "cadastrarUsuarioAdmin";
	}
	
	@RequestMapping(value = "/cadastrarUsuarioAdmin", method = RequestMethod.POST)
	public String cadastrarUsuarioAdmin(@ModelAttribute("usuarioForm") UsuarioForm form,
										ModelMap map,
										BindingResult result,										
	    		 						@RequestParam("name") String name,
	    		 						@RequestParam("file") MultipartFile file,	  
	    								HttpSession session){			
		
		//inserir aqui algumas validacoes
		try {
			form.setFotoPrincipal(file.getBytes());
			boolean possuiErro = usuarioService.validarDadosCadastroEdicaoUsuarioAdmin(form, result);
			if ( ! possuiErro ){
				form = usuarioService.cadastrarUsuarioAdmin(form);
				map.addAttribute("msgSucesso", "S");
				map.addAttribute("usuarioForm", form);
			}
			else {			
				map.addAttribute("msgErro", "S");
				map.addAttribute("usuarioForm", form);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.addAttribute("msgErro", "S");
		}		
		return DIR_PATH_USUARIO + "cadastrarUsuarioAdmin";
	}
	
	@RequestMapping(value = "/prepararEdicaoUsuario/{idUsuarioAdmin}", method = RequestMethod.GET)
	public String prepararEdicaoUsuario(@PathVariable("idUsuarioAdmin") Long idUsuarioAdmin,
										HttpSession session,
										ModelMap map){
		
		
		UsuarioForm form = usuarioService.prepararEdicaoUsuarioAdmin(idUsuarioAdmin);		
    	map.addAttribute("usuarioForm", form);    	
    	return DIR_PATH_USUARIO + "editarUsuarioAdmin";
	}
	
	@RequestMapping(value = "/prepararEdicaoUsuarioAdmin")
	public String prepararEdicaoUsuarioAdmin( HttpSession session,
											  ModelMap map){
		
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		UsuarioForm form = usuarioService.prepararEdicaoUsuarioAdmin(user.getId());		
    	map.addAttribute("usuarioForm", form);    	
    	return DIR_PATH_USUARIO + "editarUsuarioAdmin";
	}
	
	@RequestMapping(value = "/editarUsuarioAdmin", method = RequestMethod.POST)
	public String editarUsuario(@ModelAttribute("usuarioForm") UsuarioForm form,
								BindingResult result,
								HttpSession session, 
								ModelMap map){		 
		 
		 //inserir ainda aqui algumas validacoes sobre os dados inseridos pelo usuario
		 boolean possuiErro = usuarioService.validarDadosCadastroEdicaoUsuarioAdmin(form, result);
		 if ( ! possuiErro ){
			 usuarioService.editarUsuario(form);
			 map.addAttribute("msgSucesso", "S");
		}
		else
			map.addAttribute("msgErro", "S");			
		
		 map.addAttribute("usuarioForm", form);
		 return DIR_PATH_USUARIO + "editarUsuarioAdmin";
	}
	
	
	@RequestMapping(value = "/buscarUsuarios", method = RequestMethod.POST)
	public String buscarUsuarios(@ModelAttribute("usuarioForm") UsuarioForm form,
							     HttpSession session, 
								 ModelMap map){
		 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		 
		 List<Usuario> lista = usuarioService.buscarUsuarios(form, "infoPessoais", user.getId());
		 map.addAttribute("listaBuscaUsuarios", lista);	
		 map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
	 	 map.addAttribute("usuarioForm", form);	
		 return DIR_PATH_USUARIO + "buscarUsuariosAdmin";
	}
	
	@RequestMapping(value = "/ordenarBuscarUsuarios", method = RequestMethod.POST)
	public String ordenarBuscarUsuarios(@ModelAttribute("usuarioForm") UsuarioForm form,
										HttpSession session,
									    ModelMap map){
		
	 	 map.addAttribute("usuarioForm", form);	 
	 	 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	
	 	 List<Usuario> lista = usuarioService.ordenarBuscaUsuarios(user, form, form.getOpcaoTipoBuscaUsuarios());
	 	 map.addAttribute("listaBuscaUsuarios", lista);	
		 map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
		 return DIR_PATH_USUARIO + "buscarUsuariosAdmin";
	}
	 
	@RequestMapping(value = "/buscarCidadesUsuario/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstadoUsuario(@ModelAttribute("usuarioForm") UsuarioForm form, 
													 @PathVariable("idEstado") Integer idEstado,
													 ModelMap map)  {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
        return form.getListaCidades() ;
    }
	
	
	@RequestMapping(value = "/buscarBairrosUsuario/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstadoUsuario(@ModelAttribute("usuarioForm") UsuarioForm form,
													 @PathVariable("idCidade") Integer idCidade, 
													 ModelMap map)  {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));        
        return form.getListaBairros();
    }
	
	@RequestMapping(value = "/prepararBuscaUsuarios", method = RequestMethod.GET)
    public String goBuscarUsuarios(ModelMap map, HttpSession session){
		UsuarioForm form = new UsuarioForm();
	//	form.setQuantTotalUsuarios(0);
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		map.addAttribute("usuarioForm", form);		
    	return DIR_PATH_USUARIO + "buscarUsuariosAdmin";
    }
	
	 
	@RequestMapping(value = "/minhasMensagens", method = RequestMethod.GET)
	public String goMensagens(HttpSession session, 
							  ModelMap map){
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
		map.addAttribute("listaMinhasMensagens", mensagemService.recuperaTodasMensagensPorDataMensagem(user.getId()));
		return DIR_PATH_MENSAGEM + "listarMensagens";
	}
	
	
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public ModelAndView inicio(ModelMap map, 
							   UsuarioForm usuarioForm, 
							   HttpSession session) {    	
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		session.setAttribute(ParametrosUtils.TIPO_VISUALIZACAO, null);
		administracaoService.carregaInfoDadosUsuarioAdmin(user, session);
		return new ModelAndView("/administracao/inicio");    	
    }
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(ModelMap map, HttpSession session) {    	
		session.invalidate();	    		
		return new ModelAndView("logout");    	
    }
	
}
