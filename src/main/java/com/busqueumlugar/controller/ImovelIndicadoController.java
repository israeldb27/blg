package com.busqueumlugar.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.form.ServicoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelindicado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelindicadoService;
import com.busqueumlugar.service.InfoservicoService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;
import com.mysql.jdbc.StringUtils;

@Controller("imovelIndicadoController")
@RequestMapping("/imovelIndicado")
@SessionAttributes({"imovelIndicadoForm", "servicoForm"})
public class ImovelIndicadoController {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelIndicadoController.class);

	@Autowired
	private ImovelindicadoService imovelIndicadoservice;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ContatoService contatoService;

    @Autowired
    private EstadosService estadosService;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private ParamservicoService paramservicoService;
	
	@Autowired
	private InfoservicoService infoservicoService;
	
	
	private static final String DIR_PATH = "/imovel/indicados/";
	private static final String DIR_PATH_SERVICO = "/servico/";
	private static final String TIPO_VISUALIZACAO = "imoveisIndicados";
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form,	
											  ModelMap map)  {        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	
	@RequestMapping(value = "/desmarcarCheck")	
	public void desmarcarCheck(Long idImovelIndicado, HttpServletResponse response, HttpSession session){
		
		try {
			imovelIndicadoservice.atualizarStatusIndicado(idImovelIndicado);
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_IMOVEIS_INDICADOS, imovelIndicadoservice.checarQuantidadeNovosImoveisIndicados(user.getId()));
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  desmarcarCheck");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/ordenarUsuariosContatosIndicar", method = RequestMethod.POST)
    public String ordenarUsuariosContatosIndicar(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form, 
    										     HttpSession session,	
    									         ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(form.getIdImovel()));
			map.addAttribute("listaUsuariosContatos", contatoService.filtrarRecuperacaoConvidadosParaIndicacao(user.getId(), form));		
			map.addAttribute("imovelIndicadoForm", form);		
	    	return DIR_PATH + "indicarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  ordenarUsuariosContatosIndicar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
    }
	
	@RequestMapping(value = "/filtrarUsuariosContatosIndicar", method = RequestMethod.POST)
    public String filtrarUsuariosContatosIndicar(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form, 
    										     HttpSession session,	
    									         ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(form.getIdImovel()));
			map.addAttribute("listaUsuariosContatos", contatoService.filtrarRecuperacaoConvidadosParaIndicacao(user.getId(), form));		
			map.addAttribute("imovelIndicadoForm", form);		
	    	return DIR_PATH + "indicarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  filtrarUsuariosContatosIndicar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/selecionarParaIndicarImovel/{idImovel}", method = RequestMethod.GET)
    public String selecionarParaIndicarImovel(@PathVariable Long idImovel, 
    										  HttpSession session,	
    									      ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		 
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));		
			ImovelindicadoForm form = new ImovelindicadoForm();
			form.setListaTodosContatos(contatoService.recuperarConvidadosIndicacaoImovel(user.getId() , idImovel, null));
			form.setIdImovel(idImovel);
			map.addAttribute("imovelIndicadoForm", form);		
	    	return DIR_PATH + "indicarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  selecionarParaIndicarImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/marcarTodosContatos")
	@ResponseBody
    public void marcarTodosContatosParaIndicarImovel(Long idImovel, 
    												 Model map,	
    												 HttpServletResponse response, 
    												 HttpSession session){
		
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json; charset=utf-8");
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		 
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));		
			map.addAttribute("listaUsuariosContatos", contatoService.recuperarConvidadosMarcandoCheck(user.getId()));
			map.addAttribute("marcarTodos", true);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  marcarTodosContatosParaIndicarImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
		}
    }	

	
	@RequestMapping(value = "/desmarcarTodosContatos", method = RequestMethod.POST)
    public String desmarcarTodosContatosParaIndicarImovel(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form, 
    										  		      HttpSession session,	
    										  		      ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		 
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(form.getIdImovel()));
			map.addAttribute("listaUsuariosContatos", contatoService.filtrarRecuperacaoConvidadosParaIndicacao(user.getId(), form));
			map.addAttribute("marcarTodos", false);
			map.addAttribute("imovelIndicadoForm", form);		
	    	return DIR_PATH + "indicarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  desmarcarTodosContatosParaIndicarImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/indicarImovel/{idUsuario}/{idImovel}")
	@ResponseBody
    public String indicarImovel(@PathVariable Long idImovel, 
    							@PathVariable Long idUsuario,
			  					HttpSession session,	
			  					ModelMap map) throws Exception{
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			String msgRetorno = imovelIndicadoservice.validarIndicavaoImovel(idImovel, idUsuario);
			if ( msgRetorno.equals("")){
				// checar se o usuÃ¡rio ultrapassou ou nao o seu limite de indicacoes de imoveis
				boolean ultrapassou = imovelIndicadoservice.checarPermissaoIndicarImoveis(user.getId(), 1);
				map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
				if ( ! ultrapassou ){
					imovelIndicadoservice.cadastrarIndicacao(idImovel, idUsuario, user.getId());
					return "ok";
				}
				else				
					return MessageUtils.getMessage("msg.erro.indicacoes.quant.usuarios");	
			}
			else
				return msgRetorno;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  indicarImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		}		
    }
	
	@RequestMapping(value = "/prepararPagtoServicoIndicacoes", method = RequestMethod.GET)
    public String prepararPagtoServicoIndicacoes(HttpSession session,	
			  									 ModelMap map){
		
		try {
			session.setAttribute(ParametrosUtils.TIPO_VISUALIZACAO, TIPO_VISUALIZACAO);
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ServicoForm form = new ServicoForm();	   
			form.setIdUsuario(user.getId());
			form.setDataSolicitacao(new Date());
			form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());			 
			form.setAcao(ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo());
			form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo())); 
			map.addAttribute("servicoForm", form);		
			return DIR_PATH_SERVICO + "cadastrarPagtoServico";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  prepararPagtoServicoIndicacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	
	@RequestMapping(value = "/indicarImovelSelecionados", method = RequestMethod.POST)
    public String indicarImovelSelecionados(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form,
			  								HttpSession session,	
			  								ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			// validar se usuario ultrapassou o limite de indicações
			String msg = imovelIndicadoservice.validarIndicavaoImovelUsuariosSelecionados(form, user.getId());
			if ( msg.equals("")){
				imovelIndicadoservice.cadastrarIndicacaoSelecionados(form, user.getId());			
				form.setListaTodosContatos(contatoService.recuperarConvidadosIndicacaoImovel(user.getId() , form.getIdImovel(), form));
			}
			else 
				map.addAttribute("msgErro", msg);
					
			form.setListaTodosContatos(contatoService.recuperarConvidadosIndicacaoImovel(user.getId() , form.getIdImovel(), form));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(form.getIdImovel()));
			map.addAttribute("imovelIndicadoForm", form);		
			return DIR_PATH + "indicarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  indicarImovelSelecionados");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
		
	
	@RequestMapping(value = "/indicarImovelPorEmail", method = RequestMethod.POST)
    public String indicarImovelPorEmail(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form,
			  							HttpSession session,	
			  							ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if (! StringUtils.isNullOrEmpty(form.getEmailIndicado())){
				imovelIndicadoservice.cadastrarIndicacaoPorEmail(form.getIdImovel(), form.getEmailIndicado() , user.getId());
				map.addAttribute("msgSucessoEnviadoEmail", MessageUtils.getMessage("msg.email.indicacao.enviado.sucesso"));
			}
			else 
				map.addAttribute("msgErroEnviadoEmail", MessageUtils.getMessage("msg.email.indicacao.enviado.erro"));
				
			map.addAttribute("imovelIndicadoForm", form);
			return DIR_PATH + "indicarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  indicarImovelPorEmail");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		}
    }

	
	@RequestMapping(value = "/todosUsuariosIndicadosImovel/{idUsuario}", method = RequestMethod.GET)
    public String todosUsuariosIndicadosImovel(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form,
    										   @PathVariable("idUsuario") Long idUsuario,
    										   HttpSession session, 
    										   ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Imovelindicado> lista = null;
	        if ( form.getTipoLista().equals("indicado"))
	        	lista = imovelIndicadoservice.recuperarSolicitacoesIndicadosParaMimPorIdUsuario(idUsuario, user.getId());				        	
	        else
	        	lista = imovelIndicadoservice.recuperarSolicitacoesIndicadosParaMimPorIdUsuario(user.getId(), idUsuario);				
			
	        map.addAttribute("listaTodasIndicacoes", lista);
	        map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));
	        map.addAttribute("imovelIndicadoForm", form);
	        map.addAttribute("usuarioIndicado", usuarioService.recuperarUsuarioPorId(idUsuario));
	        return DIR_PATH + "todosImoveisIndicadosUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  todosUsuariosIndicadosImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	
	
	@RequestMapping(value = "/todosImoveisIndicados/{idImovel}", method = RequestMethod.GET)
    public String todosImoveisIndicados(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form,
    									@PathVariable("idImovel") Long idImovel,  
    									HttpSession session,
    									ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			List<Imovelindicado> lista = imovelIndicadoservice.recuperarSolicitacoesIndicacoesPorImovelPorUsuario(idImovel, user.getId());
			map.addAttribute("listaTodasIndicacoesUsuario", lista);
			map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
	        map.addAttribute("imovelIndicadoForm", form);
	        map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));        
	        return DIR_PATH + "todosUsuariosIndicadosImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  todosImoveisIndicados");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/todosImoveisIndicadosCompartilhado/{idImovel}", method = RequestMethod.GET)
    public String todosImoveisIndicadosCompartilhado(@PathVariable("idImovel") Long idImovel,    
    												 HttpSession session,	
    												 ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ImovelindicadoForm form = new ImovelindicadoForm();		
			map.addAttribute("listaTodasIndicacoesUsuario", imovelIndicadoservice.recuperarSolicitacoesIndicacoesPorImovelPorUsuario(idImovel, user.getId()));
	        map.addAttribute("imovelIndicadoForm", form);
	        map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));        
	        return DIR_PATH + "todosUsuariosIndicadosImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  todosImoveisIndicadosCompartilhado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	

    @RequestMapping(value = "/modoVisualizar", method = RequestMethod.POST)
    public String modoVisualizar(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form,
                                           HttpSession session,
                                           ModelMap map){
        
    	try {
    		  UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
    			
    	        if ( form.getTipoLista().equals("indicado") ){
    	            if (form.getOpcaoVisualizacao().equals("todos")) {				
    	            	 if ( form.getTipoLista().equals("indicado")){					                                 
    						 map.addAttribute("listarImoveisIndicados", imovelIndicadoservice.listarImovelIndicado(user.getId(), form));
    	                     map.addAttribute("imovelIndicadoForm", form);
    	                     return DIR_PATH + "listarImoveisIndicados";
    	                 }
    	                 else if ( form.getTipoLista().equals("indicacoes")){					 
    	                     map.addAttribute("imovelIndicadoForm", form);
    						 map.addAttribute("listarImoveisIndicacoes", imovelIndicadoservice.listarImoveisIndicacoes(user.getId(), form));
    	                     return DIR_PATH + "listarImoveisIndicacoes";
    	                 }
    	            }
    	            else if (form.getOpcaoVisualizacao().equals("agruparUsuarios")) {				
    					map.addAttribute("listaAgruposUsuarios", imovelIndicadoservice.agruparUsuariosImoveisIndicados(user.getId(), form));
    	                map.addAttribute("imovelIndicadoForm", form);
    	                return DIR_PATH + "agruparUsuariosIndicados";
    	            }
    	        }
    	        else if ( form.getTipoLista().equals("indicacoes") ){
    	            if (form.getOpcaoVisualizacao().equals("todos")) {
    	            	 if ( form.getTipoLista().equals("indicado")){					 
    						 map.addAttribute("listarImoveisIndicados", imovelIndicadoservice.listarImovelIndicado(user.getId(), form));
    	                     map.addAttribute("imovelIndicadoForm", form);
    	                     return DIR_PATH + "listarImoveisIndicados";
    	                 }
    	                 else if ( form.getTipoLista().equals("indicacoes")){
    						 map.addAttribute("listarImoveisIndicacoes", imovelIndicadoservice.listarImoveisIndicacoes(user.getId(), form));
    	                     map.addAttribute("imovelIndicadoForm", form);
    	                     return DIR_PATH + "listarImoveisIndicacoes";
    	                 }
    	            }
    	            else if (form.getOpcaoVisualizacao().equals("agruparUsuarios")) {				
    					map.addAttribute("listaAgruposUsuarios", imovelIndicadoservice.agruparUsuariosImoveisIndicados(user.getId(), form));
    	                map.addAttribute("imovelIndicadoForm", form);
    	                return DIR_PATH + "agruparUsuariosIndicados";
    	            }
    	            else if (form.getOpcaoVisualizacao().equals("agruparImoveis")) {				
    					map.addAttribute("listaAgruposImoveis", imovelIndicadoservice.agruparImoveis(user.getId(), form));
    	                map.addAttribute("imovelIndicadoForm", form);
    	                return DIR_PATH + "agruparImoveisIndicados";
    	            }
    	        }
    	        return DIR_PATH + "listarImoveisIndicados";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  modoVisualizar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }

    @RequestMapping(value = "/filtrar", method = RequestMethod.POST)
    public String filtrar(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form, 
    					  HttpSession session, 
    					  ModelMap map){
    	
    	try {
    		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
            if (form.getTipoLista().equals("indicacoes")) {
    			map.addAttribute("listarImoveisIndicacoes", imovelIndicadoservice.filtrar(form.getTipoLista(), user.getId(), form));
                map.addAttribute("imovelIndicadoForm", form);
                return DIR_PATH + "listarImoveisIndicacoes";
            }     
            else if (form.getTipoLista().equals("indicado")) {
    			map.addAttribute("listarImoveisIndicados", imovelIndicadoservice.filtrar(form.getTipoLista(), user.getId(), form));
                map.addAttribute("imovelIndicadoForm", form);
                return DIR_PATH + "listarImoveisIndicados";
            }    
            return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  filtrar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value="/filtrarAgruparUsuarios", method = RequestMethod.POST)	
	public String filtrarAgruparUsuarios(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form, 
						  				 ModelMap map, 
						  				 HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Usuario> listaAgruposUsuarios = imovelIndicadoservice.filtrarAgruparUsuario(user.getId(), form);				
			map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(listaAgruposUsuarios));
			map.addAttribute("listaAgruposUsuarios", listaAgruposUsuarios);
			map.addAttribute("imovelIndicadoForm", form);	
			return DIR_PATH + "agruparUsuariosIndicados";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController -  filtrarAgruparUsuarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/filtrarAgruparImoveis", method = RequestMethod.POST)	
	public String filtrarAgruparImoveis(@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form, 
						  				ModelMap map, 
						  				HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Imovel> listaAgruposImoveis  = imovelIndicadoservice.filtrarAgruparImoveis(user.getId(), form);				
			map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(listaAgruposImoveis));
			map.addAttribute("listaAgruposImoveis", listaAgruposImoveis);
			map.addAttribute("imovelIndicadoForm", form);
			return DIR_PATH + "agruparImoveisIndicados";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController - filtrarAgruparImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}

    @RequestMapping(value = "/listarImoveisIndicados/{tipoLista}", method = RequestMethod.GET)
    public String listarImoveisIndicados(@PathVariable String tipoLista,
    									 HttpSession session,
    									 HttpServletRequest request,
    									 ModelMap map){
    	
    	try {
    		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
            ImovelindicadoForm form = new ImovelindicadoForm();
            form.setListaEstados(estadosService.listarTodosEstadosSelect());
            form.setTipoLista(tipoLista);
            
            if ( tipoLista.equals("indicado")){        	
            	long quantNovas = Long.parseLong(request.getSession().getAttribute("quantNovoImovelIndicado").toString());
            	if ( quantNovas > 0 ){
            		imovelIndicadoservice.atualizarStatusImoveisIndicadosSolRecebidas(user.getId());
            		session.setAttribute(ImovelService.QUANT_IMOVEIS_INDICADOS, 0);
            	}			
    			map.addAttribute("listarImoveisIndicados", imovelIndicadoservice.listarImovelIndicado(user.getId(), form));
                map.addAttribute("imovelIndicadoForm", form);
                session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "indicado");
                return DIR_PATH + "listarImoveisIndicados";
            }
            else if ( tipoLista.equals("indicacoes")){
            	map.addAttribute("listarImoveisIndicacoes", imovelIndicadoservice.listarImoveisIndicacoes(user.getId(), form));
                map.addAttribute("imovelIndicadoForm", form);
                session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "indicacoes");
                return DIR_PATH + "listarImoveisIndicacoes";
            }
            return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController - listarImoveisIndicados");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/excluirImovelIndicado/{idImovelIndicado}", method = RequestMethod.GET)
    public String excluirImovelIndicado(HttpSession session, 
										@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form,
										@PathVariable("idImovelIndicado") Long idImovelIndicado,
										ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelIndicadoservice.excluirImovelIndicado(idImovelIndicado);			
	        map.addAttribute("imovelIndicadoForm", form);
	        return DIR_PATH + "listarImoveisIndicados";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController - excluirImovelIndicado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value="/ordenar", method = RequestMethod.POST)
	public String ordenar(HttpSession session,
						  ModelMap map,
						  @ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			
			if ( form.getTipoLista().equals("indicacoes")) {
				map.addAttribute("listarImoveisIndicacoes", imovelIndicadoservice.ordenarImoveisIndicados(user.getId(), form));
				map.addAttribute("imovelIndicadoForm", form);
				return DIR_PATH + "listarImoveisIndicacoes";
			}	
			else {
				map.addAttribute("listarImoveisIndicados", imovelIndicadoservice.ordenarImoveisIndicados(user.getId(), form));
				map.addAttribute("imovelIndicadoForm", form);
				return DIR_PATH + "listarImoveisIndicados";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController - ordenar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/ordenarAgrupar", method = RequestMethod.POST)
	public String ordenarAgrupar(HttpSession session,
								ModelMap map,
								@ModelAttribute("imovelIndicadoForm") ImovelindicadoForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			
			if ( form.getOpcaoVisualizacao().equals("agruparImoveis")) {
				map.addAttribute("listaAgruposImoveis", imovelIndicadoservice.ordenarAgruparImoveisIndicados(user.getId(), form));
				map.addAttribute("imovelIndicadoForm", form);
				return DIR_PATH + "agruparImoveisIndicados";
			}	
			else {
				map.addAttribute("listaAgruposUsuarios", imovelIndicadoservice.ordenarAgruparImoveisIndicados(user.getId(), form));
				map.addAttribute("imovelIndicadoForm", form);
				return DIR_PATH + "agruparUsuariosIndicados";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelIndicadoController - ordenarAgrupar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	@RequestMapping(value = "/selecionarAcao", method = RequestMethod.POST)
	public String selecionarAcaoImoveisCompartilhamento(){   
		return DIR_PATH;
	}
    
}
