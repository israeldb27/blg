package com.busqueumlugar.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusUsuarioEnum;
import com.busqueumlugar.enumerador.TipoParamServicoOpcaoEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Recomendacao;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.AdministracaoService;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.PlanoService;
import com.busqueumlugar.service.PreferencialocalidadeService;
import com.busqueumlugar.service.RecomendacaoService;
import com.busqueumlugar.service.SeguidorService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;
import com.mysql.jdbc.StringUtils;
import com.paypal.api.payments.Payment;

@Controller("usuarioController")
@RequestMapping("/usuario")
@SessionAttributes({"usuarioForm"})
public class UsuarioController {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private ParamservicoService paramservicoService;
	
	@Autowired
	private PlanoService planoService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private AdministracaoService administracaoService;
	
	@Autowired
	private PreferencialocalidadeService prefLocalidadeService;
	
	@Autowired
	private SeguidorService seguidorService;
	
	@Autowired
	private RecomendacaoService recomendacaoService;
	
	private static final String DIR_PATH = "/usuario/";
	private static final String DIR_PATH_EDICAO = "/usuario/edicao/";
	private static final String DIR_PATH_CADASTRO = "/usuario/cadastro/";
	private static final String DIR_PATH_ASSINATURA = "/usuario/assinatura/";
	private static final String DIR_PATH_ESQUECEU_SENHA = "/usuario/esqueceuSenha/";
	private static final String DIR = "/";
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("usuarioForm") UsuarioForm form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("usuarioForm") UsuarioForm form,	
											  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	
	@RequestMapping(value = "/carregarTimeLine")
	@ResponseBody
	public List<String> carregarTimeline( HttpSession session,										 	
										  ModelMap map){	
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			return usuarioService.carregarTimeline(user, session);
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  carregarTimeline");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
	}
	
	
	@RequestMapping(value = "/adicionarRecomendacaoDetalheUsuario")	
	public String adicionarRecomendacaoDetalheUsuario(String novaRecomendacao,
												   	  HttpSession session, 	
												   	  ModelMap map, 
												   	  @ModelAttribute("usuarioForm") UsuarioForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			//a recomendacao é enviada para o usuario e o usuario recomendado deve ainda aprová-lo
			recomendacaoService.cadastrarRecomendacao(form.getId(), user.getId(), novaRecomendacao);
			form.setListaRecomendacoes(recomendacaoService.recuperarRecomendacoesPorIdUsuarioRecomendado(form.getId()) ); 
			map.addAttribute("usuarioForm", form );		
			return DIR_PATH + "visualizarDetalhesUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  adicionarRecomendacaoDetalheUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	
	@RequestMapping(value = "/responderRecomendacao/{respostaRecomendacao}/{idRecomendacao}", method = RequestMethod.GET)
	@ResponseBody
    public String responderRecomendacao(@PathVariable Long idRecomendacao, 
    							   		@PathVariable String respostaRecomendacao, 
    							   		ModelMap map, 
    							   		HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( respostaRecomendacao.equals("aceitar") || respostaRecomendacao.equals("recusar")) {
				recomendacaoService.respostaRecomendacao(user.getId(), idRecomendacao, respostaRecomendacao);
				long quantNovasRecomendacoes = recomendacaoService.checarNovasRecomendacoesRecebidas(user.getId());
				session.setAttribute(RecomendacaoService.QUANT_NOVAS_RECOMENDACOES, quantNovasRecomendacoes);
				if ( quantNovasRecomendacoes > 0 ){
					session.setAttribute(RecomendacaoService.LISTA_RECOMENDACOES, recomendacaoService.recuperarRecomendacoesPorIdUsuarioRecomendadoPorStatus(user.getId(), RecomendacaoStatusEnum.ENVIADO.getRotulo()));
					session.setAttribute(RecomendacaoService.QUANT_RECOMENDACOES,0); 
				}
				else if ( quantNovasRecomendacoes <= 0 ){
					List<Recomendacao> lista =  recomendacaoService.recuperarRecomendacoesPorIdUsuarioRecomendadoPorStatus(user.getId(), RecomendacaoStatusEnum.ENVIADO.getRotulo());
					session.setAttribute(RecomendacaoService.LISTA_RECOMENDACOES, lista);
					session.setAttribute(RecomendacaoService.QUANT_RECOMENDACOES, AppUtil.recuperarQuantidadeLista(lista));
				}
				return "ok";
			}
			return "erro";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  responderRecomendacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return "erro";
		}
		
		
    }
	
	@RequestMapping(value = "/carregaModalDetalhesUsuario/{idUsuario}")
	@ResponseBody
	public UsuarioForm carregaModalDetalhesUsuario(@PathVariable("idUsuario") Long idUsuario,
											     ModelMap map){	
		
		return usuarioService.carregaUsuario(idUsuario);
	}
	
	
	@RequestMapping(value = "/pesquisarTudo", method = RequestMethod.POST)
    public String pesquisarTudo(@ModelAttribute("usuarioForm") UsuarioForm form,    							
							    HttpSession session, 	
							   	ModelMap map){
		
		try {
			if (! StringUtils.isNullOrEmpty(form.getValorBusca())){
				map.addAttribute("listaImoveis", imovelService.pesquisarTodosImoveis(form.getValorBusca()));
				map.addAttribute("listaUsuarios", usuarioService.pesquisarTodosUsuarios(form));
				map.addAttribute("listaServicos", paramservicoService.pesquisarTodosServicos(form.getValorBusca()));
				map.addAttribute("listaPlanos", planoService.pesquisarTodosPlanos(form.getValorBusca()));			
			}
			else 
				map.addAttribute("msgErroPesquisarTudo", MessageUtils.getMessage("msg.erro.busque.pessoas.imoveis.valor.busca.vazio"));
				
			return 	DIR_PATH + "resultadoPesquisa";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  pesquisarTudo");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
				
    }
	
	@RequestMapping(value = "/aprovarUsuario/{avaliacao}")
    public String aprovarUsuario(Long idUsuario,
    							 @PathVariable("avaliacao") String avaliacao,
    							 ModelMap map, 
    							 HttpSession session){
    	
    	try {
    		usuarioService.avaliarServicoUsuario(idUsuario, avaliacao);		
        	return DIR_PATH + "detalhesUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  aprovarUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/iniciarSeguirUsuario/{idUsuario}", method = RequestMethod.GET)
    @ResponseBody
    public String iniciarSeguirUsuario(@ModelAttribute("usuarioForm") UsuarioForm form, 
    								   @PathVariable("idUsuario") Long idUsuario, 
    								   HttpSession session,
    								   ModelMap map)  {
		
		try {
			UsuarioForm usuarioSessao  = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
	        seguidorService.iniciarSeguidor(idUsuario, usuarioSessao.getId());        
	        return "ok";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  iniciarSeguirUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	@RequestMapping(value = "/cancelarSeguirUsuario/{idUsuario}", method = RequestMethod.GET)
    @ResponseBody
    public String cancelarSeguirUsuario(@ModelAttribute("usuarioForm") UsuarioForm form, 
    								    @PathVariable("idUsuario") Long idUsuario, 
    								    HttpSession session,
    								    ModelMap map)  {
		
		try {
			UsuarioForm usuarioSessao  = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
	        seguidorService.cancelarIniciarSeguidor(idUsuario, usuarioSessao.getId());        
	        return "ok";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  cancelarSeguirUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	
	@RequestMapping(value = "/meuPerfil", method = RequestMethod.GET)
    public String meuPerfil(ModelMap map, 
    						HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		    UsuarioForm form = usuarioService.prepararDetalhesUsuarioForm(user.getId(), user.getId());
		    session.setAttribute(UsuarioInterface.USUARIO_SESSAO, form);
		    map.addAttribute("usuarioForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "meuPerfilUsuario");	
	    	return DIR_PATH + "perfilUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  meuPerfil");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/detalhesUsuario/{idUsuario}", method = RequestMethod.GET)    
    public String verDetalhesUsuario(@PathVariable("idUsuario") Long idUsuario,
    								 HttpSession session, 
    								 ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		    UsuarioForm form = usuarioService.prepararDetalhesUsuarioForm(idUsuario, user.getId());
		    map.addAttribute("usuarioForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "detalhesUsuario");	
	    	return DIR_PATH + "visualizarDetalhesUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  verDetalhesUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}        
    }
	
	@RequestMapping(value = "/detalhesUsuario")    
    public String verDetalhesUsuario(HttpServletRequest request,
    								 HttpSession session, 
    								 ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	    
		    map.addAttribute("usuarioForm", usuarioService.prepararDetalhesUsuarioForm(Long.valueOf(request.getParameter("modIdUsuario").toString()), user.getId()));
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "detalhesUsuario");	
	    	return DIR_PATH + "visualizarDetalhesUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  verDetalhesUsuario");
			log.error("Mensagem Erro: " + e.getMessage());			
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}            
    }
	
	@RequestMapping(value = "/prepararCadastroUsuario", method = RequestMethod.POST)
    public String prepararCadastroUsuario(ModelMap map, HttpSession session){
		
		try {
			UsuarioForm form = new UsuarioForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			usuarioService.preparaCampoDataNascimento(form);
			map.addAttribute("usuarioForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "cadastroUsuario");	
	    	return DIR_PATH_CADASTRO + "cadastrarUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  prepararCadastroUsuario");
			log.error("Mensagem Erro: " + e.getMessage());			
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/avancarCadastro", method = RequestMethod.POST)
    public String avancarInicioCadastro(ModelMap map, 
    									@ModelAttribute("usuarioForm") UsuarioForm form,
										BindingResult result,										
	    		 						@RequestParam("name") String name,
	    		 						@RequestParam("file") MultipartFile file,	  
	    								HttpSession session) {
		//inserir aqui algumas validacoes
		try {
			form.setFotoPrincipal(file.getBytes());
			boolean possuiErro = usuarioService.validarDadosCadastroUsuario(form, result);
			if ( ! possuiErro ){
				form = usuarioService.cadastrarUsuario(form);
				map.addAttribute("msgSucesso", "S");
				map.addAttribute("usuarioForm", form);
			}
			else {			
				map.addAttribute("msgErro", "S");
				map.addAttribute("usuarioForm", form);
			}
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  avancarInicioCadastro");
			log.error("Mensagem Erro: " + e.getMessage());			
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
		return DIR_PATH_CADASTRO + "cadastrarUsuario";
				           
    }
	
	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
    public String cadastrarUsuario(ModelMap map, 
    							   @ModelAttribute("usuarioForm") UsuarioForm form,
    							   HttpSession session){
		
		try {
			form = usuarioService.cadastrarUsuario(form);
			map.addAttribute("usuarioForm", form);
			if ( form.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){			
				return "forward:/preferencia/inicioCadastroUsuarioPreferenciaImoveis/" + form.getId();
			}
			else {
				map.addAttribute("msgSucesso", "S");
				return DIR_PATH_CADASTRO + "confirmarCadastroUsuario";
			}
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  cadastrarUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	
	@RequestMapping(value = "/prepararCadastroFotoUsuario", method = RequestMethod.POST)
    public String prepararCadastroFotoUsuario(@ModelAttribute("usuarioForm") UsuarioForm form,
    										  ModelMap map, 
    										  HttpSession session){
		try {
			map.addAttribute("usuarioForm", form);		
	    	return DIR_PATH_CADASTRO + "cadastrarFotoUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  prepararCadastroFotoUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	
	@RequestMapping(value = "/cadastrarFotoUsuario", method = RequestMethod.POST)
    public String cadastrarFotoUsuario(@ModelAttribute("usuarioForm") UsuarioForm form,
    		 						   @RequestParam("name") String name,
    		 						   @RequestParam("file") MultipartFile file,	
    								   ModelMap map, 
    								   HttpSession session){
		
		try {
			if (!file.isEmpty()) {	          
            	usuarioService.adicionarFotoPrincipalUsuario(form.getId(), file.getBytes());            	
                map.addAttribute("usuarioForm", form );
                return DIR_PATH_CADASTRO + "cadastrarFotoUsuario";
			}       	
			else
				return null;
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  prepararCadastroFotoUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
    }	
	
	@RequestMapping(value = "/prepararBuscaUsuariosPreferencia", method = RequestMethod.GET)
    public String goBuscarUsuariosPreferenciaImoveis(ModelMap map, HttpSession session){
		
		try {
			UsuarioForm form = new UsuarioForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());		
			map.addAttribute("usuarioForm", form);		
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "buscarUsuariosPreferenciaImoveis");
	    	return DIR_PATH + "buscarUsuariosPreferenciaImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  goBuscarUsuariosPreferenciaImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/buscarUsuariosPreferenciaImoveis", method = RequestMethod.POST)
	public String buscarUsuariosPreferenciaImoveis(@ModelAttribute("usuarioForm") UsuarioForm form, 
												   ModelMap map, 
												   HttpSession session ){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		 	 map.addAttribute("listaBuscaUsuarios", usuarioService.buscarUsuarios(form, "infoPreferenciais", user.getId()));	 	 
		 	 map.addAttribute("usuarioForm", form);
			 return DIR_PATH + "buscarUsuariosPreferenciaImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  buscarUsuariosPreferenciaImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	@RequestMapping(value = "/ordenarBuscarUsuariosPreferenciaImoveis", method = RequestMethod.POST)
	public String ordenarBuscarUsuariosPreferenciaImoveis(@ModelAttribute("usuarioForm") UsuarioForm form, 
														  HttpSession session, 
														  ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	 	 
		 	 map.addAttribute("listaBuscaUsuarios", usuarioService.ordenarBuscaUsuarios(user, form, "infoPreferenciais"));
		 	 map.addAttribute("usuarioForm", form);
			 return DIR_PATH + "buscarUsuariosPreferenciaImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  ordenarBuscarUsuariosPreferenciaImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	
	@RequestMapping(value = "/prepararBuscaUsuarios", method = RequestMethod.GET)
    public String goBuscarUsuarios(ModelMap map, HttpSession session){
		
		try {
			UsuarioForm form = new UsuarioForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("usuarioForm", form);	
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "buscarUsuarios");
	    	return DIR_PATH + "buscarUsuarios";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  goBuscarUsuarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	
	@RequestMapping(value = "/buscarUsuarios", method = RequestMethod.POST)
	public String buscarUsuarios(@ModelAttribute("usuarioForm") UsuarioForm form,
								 BindingResult result,
								 ModelMap map, 
								 HttpSession session ){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 boolean possuiErro = usuarioService.validarBuscarUsuarios(form, result);
			 if ( !possuiErro){			 
				 List<Usuario> lista = null;
				 if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
					lista = usuarioService.buscarUsuarios(form, "infoPessoais", user.getId());			 
				 else 
					 lista = usuarioService.buscarUsuarios(form, form.getOpcaoTipoBuscaUsuarios(), user.getId());
				 
				 form.setOpcaoOrdenacao("");
				 map.addAttribute("listaBuscaUsuarios", lista);
			 }		 		 
		 	 map.addAttribute("usuarioForm", form);	 	 
			 return DIR_PATH + "buscarUsuarios";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  buscarUsuarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	@RequestMapping(value = "/paginarBuscaUsuarios", method = RequestMethod.POST)
	public String paginarBuscaUsuarios(@ModelAttribute("usuarioForm") UsuarioForm form,
								 	   BindingResult result,
								 	   ModelMap map, 
								 	   HttpSession session ){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
				 map.addAttribute("listaBuscaUsuarios", usuarioService.buscarUsuarios(form, "infoPessoais", user.getId()));			
			 else 
				 map.addAttribute("listaBuscaUsuarios", usuarioService.buscarUsuarios(form, form.getOpcaoTipoBuscaUsuarios(), user.getId()));			 
			 
			 form.setOpcaoOrdenacao("");		 
		 	 map.addAttribute("usuarioForm", form);	 	 
			 return DIR_PATH + "buscarUsuarios";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  paginarBuscaUsuarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	
	@RequestMapping(value = "/prepararAlterarFotoUsuario", method = RequestMethod.GET)
	public String prepararAlterarFotoUsuario(HttpSession session,
										     ModelMap map){
		
		try {
			UsuarioForm form = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
	    	map.addAttribute("usuarioForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "alterarFotoUsuario");
			return DIR_PATH + "alterarFotoUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  prepararAlterarFotoUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		 
	}
	
	@RequestMapping(value = "/alterarFoto", method=RequestMethod.POST)	
	public String alterarFoto(@ModelAttribute("usuarioForm") UsuarioForm form,
							  @RequestParam("name") String name,
							  @RequestParam("file") MultipartFile file,
							  ModelMap map, 
							  HttpSession session){
		
		try {
			if (!file.isEmpty()) {	                      
                form.setFotoPrincipal(file.getBytes());
                form = usuarioService.alterarFotoUsuario(form);
                map.addAttribute("usuarioForm", form );
                session.setAttribute(UsuarioInterface.USUARIO_SESSAO, form);                
                map.addAttribute("msgSucesso", "S" );
        		return DIR_PATH + "alterarFotoUsuario";	          
	        } 		
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  alterarFoto");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}
	
	@RequestMapping(value = "/prepararEdicaoUsuario", method = RequestMethod.GET)
	public String prepararEdicaoUsuario(HttpSession session,
										ModelMap map){
		
		try {
			UsuarioForm form = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			usuarioService.prepararEdicaoUsuario(form);
			if ( form.getDataNascimento() != null )
				usuarioService.preparaCampoDataNascimentoEditarUsuario(form);
	    	map.addAttribute("usuarioForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "editarUsuario");
			return DIR_PATH + "editarUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  prepararEdicaoUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		 
	}
	
	@RequestMapping(value = "/editarUsuario", method = RequestMethod.POST)
	public String editarUsuario(@ModelAttribute("usuarioForm") UsuarioForm form,
								BindingResult result,
								HttpSession session, 
								ModelMap map){		
		try {
			//inserir ainda aqui algumas validacoes sobre os dados inseridos pelo usuario
			 boolean possuiErro = usuarioService.validarDadosEdicaoUsuario(form, result);
			 if ( ! possuiErro ){
				 usuarioService.editarUsuario(form);
				 map.addAttribute("msgSucesso", "S");
			}
			else
				map.addAttribute("msgErro", "S");			
			
			 map.addAttribute("usuarioForm", form);
			 return DIR_PATH+ "editarUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  editarUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/ordenarBuscarUsuarios", method = RequestMethod.POST)
	public String ordenarBuscarUsuarios(@ModelAttribute("usuarioForm") UsuarioForm form, 
										HttpSession session, 
										ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 form.setOpcaoOrdenacao("");		 
			 map.addAttribute("listaBuscaUsuarios", usuarioService.ordenarBuscaUsuarios(user, form, form.getOpcaoTipoBuscaUsuarios()));
		 	 map.addAttribute("usuarioForm", form);	 	 
			 return DIR_PATH + "buscarUsuarios";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  ordenarBuscarUsuarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(ModelMap map, HttpSession session) {    	
		session.invalidate();	    		
		return new ModelAndView("logout");    	
    }
	
	@RequestMapping(value = "/submitLogin", method = RequestMethod.POST)
    public String submitLogin(ModelMap map, 
    						  UsuarioForm usuarioForm, 
    						  HttpSession session,
    						  HttpServletRequest request) {
		
		try {
			log.info("Host IP requisitante login: " + request.getLocalAddr());
			log.info("Host Name requisitante login: " + request.getLocalName());
			log.info("Login requisitante: " + usuarioForm.getLogin());
	    	boolean existe = usuarioService.validarLoginPassword(usuarioForm);
	    	if ( existe ){
	    		UsuarioForm user = usuarioService.carregaUsuarioByLogin(usuarioForm);
	    		session.setAttribute(UsuarioInterface.USUARIO_SESSAO, user);    		
	    		if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.ADMIN.getRotulo())){
	    			log.info("Acesso concedido admin: " + new DateUtil().getStrDate());
	    			AdministracaoForm form = new AdministracaoForm();
	    			map.addAttribute("administracaoForm", form);
	    			administracaoService.carregaInfoDadosUsuarioAdmin(user, session);
	    			return "/admin/inicio";
	    		}
	    		else {
	    			if ( user.getStatusUsuario().equals(StatusUsuarioEnum.CRIADO.getRotulo()) || user.getStatusUsuario().equals(StatusUsuarioEnum.LIBERADO.getRotulo())){
	    				if (( ! user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) ) || ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) && (user.getDataUltimoAcesso() != null ))) {
	        				usuarioService.carregarDadosInfoUsuario(user, session, true);        				
	        				//usuarioService.tratarTelaInicial(user, session, map);
	        				usuarioService.prepararParaCarregarTimeLine(user, session, map); // preparar configuraçoes para carregar a Timeline do sistema
	        				log.info("Acesso concedido: " + new DateUtil().getStrDate());
	        				return "main";
	        			}
	        			else { // se o usuario cliente está acessando primeira vez entao ele deverá ser adicionado na tela de preferencias 
	        				if ( ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) && (user.getDataUltimoAcesso() == null )) ) {
	        					log.info("Primeiro Acesso concedido: " + new DateUtil().getStrDate());
	        					return "forward:/preferencia/inicioCadastroUsuarioPreferenciaImoveis/" + user.getId();
	        				}	
	        			}
	    			}
	    			else if ( user.getStatusUsuario().equals(StatusUsuarioEnum.SUSPENSO.getRotulo())){
	    				map.addAttribute("msgError", "Voce esta com acesso suspenso");
	    	    		map.addAttribute("usuarioForm", usuarioForm);
	    	    		log.info("Usuario com acesso suspenso: " + new DateUtil().getStrDate());
	    	    		return "home";
	    			}    			    			
	    		}
	    	}	
	    	else {  
	    		map.addAttribute("msgError", MessageUtils.getMessage("msg.erro.submit.login"));
	    		log.info("Acesso negado: " + new DateUtil().getStrDate());
	    		map.addAttribute("usuarioForm", usuarioForm);
	    		return "home";
	    	}
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  submitLogin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
    }
	
	@RequestMapping(value = "/submitLoginFirst")
    public ModelAndView submitLoginPrimeiraVez(HttpSession session, ModelMap map) {
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			usuarioService.carregarDadosInfoUsuario(user, session, true);    		
			return new ModelAndView(DIR + "main");
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  carregarTimeline");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return new ModelAndView(ImovelService.PATH_ERRO_GERAL);
		}
	}
	
	@RequestMapping(value = "/selecionarAssinaturaDisponivel", method = RequestMethod.POST)
    public String selecionarAssinaturaDisponivel(HttpSession session, 
			   						  			 ModelMap map,
			   						  			 @ModelAttribute("usuarioForm") UsuarioForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 String msg = servicoService.validarPagamentoServico(form.getServicoForm().getIdInfoServicoPagamentoSelecionada(), form.getServicoForm().getIdFormapagamentoSelecionada() );
			 form.getServicoForm().setIdUsuario(user.getId());
			 if ( msg.equals("") ){
				servicoService.selecionarPagamentoServico(form.getServicoForm(), form.getServicoForm().getIdInfoServicoPagamentoSelecionada(), form.getServicoForm().getIdFormapagamentoSelecionada());
				 if ( form.getServicoForm().getFormaPagtoSelecionado().equals("Pag Seguro")){   
					 if ( form.getServicoForm().getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServicoImovel(form.getServicoForm(), form.getServicoForm().getIdImovel() );					 
						 form.getServicoForm().setOpcao("servicoImovel");
					 }
					 else if ( form.getServicoForm().getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()) || form.getServicoForm().getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServico(form.getServicoForm(), form.getServicoForm().getOpcaoTempoPagto());					 
						 form.getServicoForm().setOpcao("servico");
					 }
					 form.getServicoForm().setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(user.getId(), form.getServicoForm().getAcao() ));
					 map.addAttribute("servicoForm", form);
					 return DIR_PATH_ASSINATURA + "confirmarPagtoPagSeguro";                                            
				 }
				 else if ( form.getServicoForm().getFormaPagtoSelecionado().equals("Pay Pal")){  
					 if ( form.getServicoForm().getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServicoImovel(form.getServicoForm(), form.getServicoForm().getIdImovel() );					 
						 form.getServicoForm().setOpcao("servicoImovel");
					 }
					 else if ( form.getServicoForm().getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()) || form.getServicoForm().getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServico(form.getServicoForm(), form.getServicoForm().getOpcaoTempoPagto());					
						 form.getServicoForm().setOpcao("servico");
					 }
					 form.getServicoForm().setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(user.getId(), form.getServicoForm().getAcao() ));
					 map.addAttribute("servicoForm", form);
					 return DIR_PATH_ASSINATURA + "confirmarPagtoPayPalAssinaturaUsuario";
				 }
			 }
			 else
				 return null;		 
			              
			return DIR_PATH_ASSINATURA + "confirmarPagtoServico";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  selecionarAssinaturaDisponivel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/confirmarPagtoAssinaturaPayPal", method = RequestMethod.POST)
    public String confirmarPagtoAssinaturaPayPal(HttpSession session, 
    										     HttpServletRequest req,	
    										     HttpServletResponse resp,
			   								     ModelMap map,
			   								     @ModelAttribute("usuarioForm") UsuarioForm form){	
		
		
		try {
			Payment payment = null;
			payment = servicoService.confirmarPagamentoPayPal(req, resp, form.getServicoForm());
			if ( payment != null) {
				resp.sendRedirect(req.getAttribute("redirectURL").toString());
				return "response";
			}	
			else 
				return null;
			
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  carregarTimeline");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	

	
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public ModelAndView inicio(ModelMap map, UsuarioForm usuarioForm, HttpSession session) {
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			usuarioService.tratarTelaInicial(user, session, map);    		
			return new ModelAndView("main");
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  carregarTimeline");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return new ModelAndView(ImovelService.PATH_ERRO_GERAL);
		}    	
    }
	
	@RequestMapping(value = "/prepararEsqueceuSenha")
	public String prepararEsqueceuSenha(ModelMap map){
		try {
		 	 map.addAttribute("usuarioForm", new UsuarioForm());	 	 
			 return DIR_PATH_ESQUECEU_SENHA + "solicitaNovaSenha";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  prepararEsqueceuSenha");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		} 
	}
	
	@RequestMapping(value = "/enviarEmailTemporario")
	public String enviarEmailTemporario(HttpSession session, 
							  			ModelMap map,
							  			@ModelAttribute("usuarioForm") UsuarioForm form,
							  			BindingResult result){
		try {
			 boolean isValido = usuarioService.validarEnviarSenhaTemporarioEsqueceuSenha(form, result);
			 if ( isValido ){
				 usuarioService.enviarSenhaTemporarioEsqueceuSenha(form);
				 map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.esqueceu.senha"));			 
			 }
			 else {
				 map.addAttribute("msgErro", MessageUtils.getMessage("msg.erro.esqueceu.senha"));
				 return DIR_PATH_ESQUECEU_SENHA + "solicitaNovaSenha";
			 }	 
		 	 
		 	 map.addAttribute("usuarioForm", form);
			 return DIR_PATH_ESQUECEU_SENHA + "confirmarNovaSenha";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  enviarEmailTemporario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		} 
	}
	
	@RequestMapping(value = "/confirmarEnviarEmailTemporario")
	public String confirmarEnviarEmailTemporario(HttpSession session, 
							  					 ModelMap map,
							  					 @ModelAttribute("usuarioForm") UsuarioForm form,
							  					 BindingResult result){
		try {
			 boolean isValido = usuarioService.validarConfirmaEnviarSenhaTemporarioEsqueceuSenha(form, result);
			 if ( isValido ){
				 usuarioService.confirmarSenhaTemporarioEsqueceuSenha(form);
				 map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.confirma.esqueceu.senha"));			 
			 }
			 else 
				 map.addAttribute("msgErro", MessageUtils.getMessage("msg.erro.confirma.esqueceu.senha"));				 
		 	 
		 	 map.addAttribute("usuarioForm", form);
			 return DIR_PATH_ESQUECEU_SENHA + "confirmarNovaSenha";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  confirmarEnviarEmailTemporario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		} 
	}
	
	
	@RequestMapping(value = "/prepararEditarSenha")
	public String prepararEditarSenha(ModelMap map, 
									  HttpSession session){
		try {
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 user.setPassword("");
			 user.setConfirmaPassword("");			 
			 map.addAttribute("usuarioForm", user);
			 return DIR_PATH + "editarSenha";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  prepararEditarSenha");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	@RequestMapping(value = "/editarSenha")
	public String editarSenha(HttpSession session, 
							  ModelMap map,
							  @ModelAttribute("usuarioForm") UsuarioForm form,
							  BindingResult result){
						
		try {			
			 boolean isValido = usuarioService.validarEditarSenha(form, result);
			 if ( isValido ){
				 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
				 usuarioService.editarSenha(user.getId(), form);
				 map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.editar.senha"));			 
			 }
			 else 
				 map.addAttribute("msgErro", MessageUtils.getMessage("msg.erro.editar.senha"));				 
		 	 
		 	 map.addAttribute("usuarioForm", form);
		 	return DIR_PATH + "editarSenha";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  editarSenha");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	@RequestMapping(value = "/prepararIndicarAmigo")
	public String prepararIndicarAmigo(ModelMap map, 
									   HttpSession session){
		try {
			 UsuarioForm form = new UsuarioForm();
			 form.setEmailIndicaAmigos("");			 		 
			 map.addAttribute("usuarioForm", form);
			 return DIR_PATH + "indicarAmigo";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  prepararIndicarAmigo");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	@RequestMapping(value = "/indicarAmigo")
	public String indicarAmigo(ModelMap map, 
							   HttpSession session,
							   @ModelAttribute("usuarioForm") UsuarioForm form,
							   BindingResult result){
		try {
			 boolean isValidoEmail = usuarioService.validarIndicarAmigo(form, result);
			 if (isValidoEmail){
				 usuarioService.enviarIndicarAmigo(form);
				 map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.sucesso.indicar.amigo"));			 
			 }
			 else 
				 map.addAttribute("msgErro", MessageUtils.getMessage("msg.erro.indicar.amigo"));			 
			 			 		 
			 map.addAttribute("usuarioForm", form);
			 return DIR_PATH + "indicarAmigo";
		} catch (Exception e) {
			log.error("Erro metodo - UsuarioController -  indicarAmigo");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}

	
	
   public void criaArquivo(byte[] bytes, String arquivo) throws IOException {
        FileOutputStream fos;
        try {
           fos = new FileOutputStream(arquivo);
           fos.write(bytes);
           fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
     }
		 
    public void enviarImagem() throws IOException {       
             
    }
	 
    public void crop() {
      // setImagemEnviada(new DefaultStreamedContent(new ByteArrayInputStream(getCroppedImage().getBytes())));
    }

    
    /*
    public void sendJMS(EmailImovel email) throws EmailException{
       
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
