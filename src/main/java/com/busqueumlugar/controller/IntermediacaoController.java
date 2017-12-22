package com.busqueumlugar.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.busqueumlugar.enumerador.StatusImovelCompartilhadoEnum;
import com.busqueumlugar.form.IntermediacaoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Intermediacao;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.IntermediacaoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("intermediacaoController")
@RequestMapping("/intermediacao")
@SessionAttributes({"intermediacaoForm"})
public class IntermediacaoController {
	
	private static final Logger log = LoggerFactory.getLogger(IntermediacaoController.class);
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private IntermediacaoService intermediacaoService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	private static final String DIR_PATH_INTERMEDIACOES = "/imovel/intermediacoes/";
	
	
	
	@RequestMapping(value = "/buscarCidadesIntermediacao/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstadoIntermediacao(@PathVariable("idEstado") Integer idEstado, 
    										  			   @ModelAttribute("intermediacaoForm") IntermediacaoForm form,	
    										  			   ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairrosIntermediacao/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstadoIntermediacao(@PathVariable("idCidade") Integer idCidade,
    										  			   @ModelAttribute("intermediacaoForm") IntermediacaoForm form,	
    										  			   ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
		
	
	
	@RequestMapping(value = "/desmarcarCheckIntermediacao")	
	public void desmarcarCheckIntermediacao(Long idImovelcompartilhado, HttpServletResponse response, HttpSession session){
		
		try {
			intermediacaoService.atualizarStatusIntermediacao(intermediacaoService.recuperarIntermediacaoPorId(idImovelcompartilhado));
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, intermediacaoService.checarQuantidadeNovasSolIntermediacao(user.getId()) );
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  desmarcarCheckIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/desmarcarCheckParceria")	
	public void desmarcarCheckParceria(Long idImovelcompartilhado, HttpServletResponse response, HttpSession session){
		
		try {
			intermediacaoService.atualizarStatusIntermediacao(intermediacaoService.recuperarIntermediacaoPorId(idImovelcompartilhado));
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_NOVAS_PARCERIAS, intermediacaoService.checarQuantidadeNovasSolIntermediacao(user.getId()) );
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  desmarcarCheckParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}

	
	// inicio intermediacao ----------------------------------------------------------
	@RequestMapping(value = "/visualizarImoveisIntermediacaoPorUsuario/{idUsuario}/{tipoLista}", method = RequestMethod.GET)
    public String visualizarImoveisIntermediacaoPorUsuario(@ModelAttribute("intermediacaoForm") IntermediacaoForm form,
    									 				   @PathVariable("idUsuario") Long idUsuario,
    									 				   @PathVariable("tipoLista") String tipoLista,
    									 				   HttpSession session, 
    									 				   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("intermediacaoForm", form);			
	        map.addAttribute("usuarioIntermediacao", usuarioService.recuperarUsuarioPorId(idUsuario));
	        if ( tipoLista.equals("aceita"))
	        	map.addAttribute("listaTodasIntermediacoes", intermediacaoService.recuperarImoveisCompartilhadoPorUsuario(user.getId(), idUsuario));
	        else if ( tipoLista.equals("minhaSol"))
	        	map.addAttribute("listaTodasIntermediacoes", intermediacaoService.recuperarImoveisCompartilhadoPorUsuario(idUsuario, user.getId()));
	        else if ( tipoLista.equals("solRecebida"))
	        	map.addAttribute("listaTodasIntermediacoes", intermediacaoService.recuperarImoveisCompartilhadoPorUsuario(user.getId(), idUsuario));
	        
	        return DIR_PATH_INTERMEDIACOES + "visualizarImoveisIntermediacaoPorUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  visualizarImoveisIntermediacaoPorUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/todosUsuariosIntermediacoes/{idImovel}", method = RequestMethod.GET)
    public String todosUsuariosIntermediacoes(@ModelAttribute("intermediacaoForm") IntermediacaoForm form,
    									 	  @PathVariable("idImovel") Long idImovel,
    									 	  HttpSession session, 
    									 	  ModelMap map){
		
		try {
			map.addAttribute("listaTodasIntermediacoes", intermediacaoService.recuperarTodosImovelIntermediacaoPorImovel(idImovel));
			map.addAttribute("intermediacaoForm", form);		
	        map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	        return DIR_PATH_INTERMEDIACOES + "todosUsuariosIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  todosUsuariosIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/todosImoveisIntermediacoesPorUsuario/{idUsuario}", method = RequestMethod.GET)
    public String todosImoveisIntermediacoesPorUsuario(@ModelAttribute("intermediacaoForm") IntermediacaoForm form,
    									 	  		   @PathVariable("idUsuario") Long idUsuario,
    									 	  		   HttpSession session, 
    									 	  		   ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Intermediacao> lista = intermediacaoService.recuperarTodosIntermediacaoPorUsuario(idUsuario, user.getId());
			map.addAttribute("listaTodosImoveisIntermediacoes", lista);
			map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));  
			map.addAttribute("intermediacaoForm", form);		
	        map.addAttribute("usuarioIntermediacao", usuarioService.recuperarUsuarioPorId(idUsuario));
	        return DIR_PATH_INTERMEDIACOES + "todosImoveisIntermediacaoPorUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  todosImoveisIntermediacoesPorUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/modoVisualizarIntermediacao", method = RequestMethod.POST)
    public String modoVisualizarIntermediacao(HttpSession session,
    										  @ModelAttribute("intermediacaoForm") IntermediacaoForm form, 
    							 		 	  ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getTipoLista().equals("intermediacaoAceita")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", intermediacaoService.agruparImoveisIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", intermediacaoService.agruparUsuariosIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaIntermediacaoAceita", intermediacaoService.recuperarTodasSolIntermediacoesAceitasDistintas(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
			    	return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesAceitas";
				}
			}		
			else if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", intermediacaoService.agruparImoveisIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", intermediacaoService.agruparUsuariosIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaIntermediacaoSolRecebida", intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidas(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";			
				}			
			}
			else if ( form.getTipoLista().equals("intermediacaoMinhasSol")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", intermediacaoService.agruparImoveisIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", intermediacaoService.agruparUsuariosIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listarIntermediacaoMinhasSol", intermediacaoService.recuperarMinhasSolicitacoesIntermediacoes(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";		
				}		 
			}		
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  modoVisualizarIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }	
	

	
	@RequestMapping(value="/ordenarIntermediacao", method = RequestMethod.POST)
	public String ordenarIntermediacoes(@ModelAttribute("intermediacaoForm") IntermediacaoForm form,										
										ModelMap map,
										HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getTipoLista().equals("intermediacaoSolRecebida")) {
				map.addAttribute("listaIntermediacaoSolRecebida", intermediacaoService.filtrarIntermediacao(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";
			}	
			else if ( form.getTipoLista().equals("intermediacaoMinhasSol")) {			
				map.addAttribute("listarIntermediacaoMinhasSol", intermediacaoService.filtrarIntermediacao(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";
			}	
			else {			
				map.addAttribute("listaIntermediacaoAceita", intermediacaoService.filtrarIntermediacao(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesAceitas";
			}
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  ordenarIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/ordenarAgruparIntermediacoes", method = RequestMethod.POST)
	public String ordenarAgruparIntermediacoes(HttpSession session,
											   ModelMap map,
											   @ModelAttribute("intermediacaoForm") IntermediacaoForm form) {
		 
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			if ( form.getOpcaoVisualizacao().equals("agruparImoveis")) {			
				map.addAttribute("listaAgruposImoveis", intermediacaoService.ordenarAgruparIntermediacoes(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
			}	
			else {			
				map.addAttribute("listaAgruposUsuarios", intermediacaoService.ordenarAgruparIntermediacoes(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
			}
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  ordenarAgruparIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}


	@RequestMapping(value = "/analisarSolicitacoesIntermediacoesRecebidas/{idImovel}", method = RequestMethod.GET)
    public String goAnalisarSolIntermediacoes(@PathVariable Long idImovel, ModelMap map){
		try {
			map.addAttribute("intermediacaoSelecionadaForm", intermediacaoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel));
			map.addAttribute("listaImovelIntermediacaoSelecionados", intermediacaoService.recuperarIntermediacoesPorIdImovel(idImovel,StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));
			map.addAttribute("listaSolImovelIntermediacao", intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  goAnalisarSolIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
	@RequestMapping(value = "/limparImovelIntermediacaoSelecionada/{idIntermediacao}/{idImovel}", method = RequestMethod.GET)
    public String limparImovelIntermediacoesSelecionado(@PathVariable Long idIntermediacao,
    													@PathVariable Long idImovel, 
    													@ModelAttribute("intermediacaoSelecionadaForm") IntermediacaoForm form,
    													ModelMap map,
    													HttpSession session){
		
		try {
			intermediacaoService.atualizarStatusIntermediacao(idIntermediacao, "rejeitada");
			List<Intermediacao> lista = intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel);
			map.addAttribute("intermediacaoSelecionadaForm", intermediacaoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel) );
			map.addAttribute("listaSolImovelIntermediacao", lista);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, AppUtil.recuperarQuantidadeLista(lista));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  limparImovelIntermediacoesSelecionado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
	@RequestMapping(value = "/aceitarSolImovelIntermediacao/{idImovelIntermediacao}/{idImovel}")
    public String aceitarSolImovelIntermediacoes(@PathVariable Long idImovelIntermediacao, 
											     @PathVariable Long idImovel, 
												 ModelMap map,
												 HttpSession session){
		
		try {
			intermediacaoService.atualizarStatusIntermediacao(idImovelIntermediacao, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
			map.addAttribute("listaImovelIntermediacaoSelecionados", intermediacaoService.recuperarIntermediacoesPorIdImovel(idImovel,StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));
			List<Intermediacao> lista = intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel);		
			map.addAttribute("intermediacaoSelecionadaForm", intermediacaoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel) );
			map.addAttribute("listaSolImovelIntermediacao", lista);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, AppUtil.recuperarQuantidadeLista(lista));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  aceitarSolImovelIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/limparUsuarioImovelIntermediacao/{idImovelIntermediacao}/{idImovel}")
    public String limparUsuarioImovelIntermediacao(@PathVariable Long idImovelIntermediacao, 
												   @PathVariable Long idImovel, 
												   ModelMap map,
												   HttpSession session){
		
		try {
			intermediacaoService.atualizarStatusIntermediacao(idImovelIntermediacao, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo());
			List<Intermediacao> lista = intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel);
			map.addAttribute("intermediacaoSelecionadaForm", intermediacaoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel) );
			map.addAttribute("listaSolImovelIntermediacao", lista);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));			
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  limparUsuarioImovelIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/excluirSolAnaliseImovelIntermediacao/{idImovelIntermediacao}/{idImovel}", method = RequestMethod.GET)
    public String excluirSolAnaliseImovelIntermediacao(@PathVariable Long idImovelIntermediacao, 
													   @PathVariable Long idImovel, 	
													   ModelMap map ,
													   HttpSession session){
		
		try {
			intermediacaoService.excluiIntermediacao(idImovelIntermediacao);
			List<Intermediacao> lista = intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel);
			map.addAttribute("intermediacaoSelecionadaForm", intermediacaoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel) );
			map.addAttribute("listaSolImovelIntermediacao", lista);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, AppUtil.recuperarQuantidadeLista(lista));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  excluirSolAnaliseImovelIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
    
    @RequestMapping(value = "/filtrarIntermediacao", method = RequestMethod.POST)
    public String filtrarIntermediacao(@ModelAttribute("intermediacaoForm") IntermediacaoForm form, 
    								   ModelMap map,
    								   HttpSession session){
    	
    	try {
    		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
        	if ( form.getTipoLista().equals("intermediacaoSolRecebida")) {      		
        		map.addAttribute("listaIntermediacaoSolRecebida", intermediacaoService.filtrarIntermediacao(user.getId(), form));							   		
        		map.addAttribute("intermediacaoForm", form);			
        		return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";
        	}	
        	else if ( form.getTipoLista().equals("intermediacaoMinhasSol")) {    		
    			map.addAttribute("listarIntermediacaoMinhasSol", intermediacaoService.filtrarIntermediacao(user.getId(), form));
        		map.addAttribute("intermediacaoForm", form);
        		return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";
        	}	
        	else if ( form.getTipoLista().equals("intermediacaoAceita")) {
    			map.addAttribute("listaIntermediacaoAceita", intermediacaoService.filtrarIntermediacao(user.getId(), form));
    			map.addAttribute("intermediacaoForm", form);
        		return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesAceitas";
        	}	    	
    		return null;
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  filtrarIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value="/filtrarAgruparUsuariosIntermediacao", method = RequestMethod.POST)	
	public String filtrarAgruparUsuariosIntermediacao(@ModelAttribute("intermediacaoForm") IntermediacaoForm form, 
													  ModelMap map, 
													  HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaAgruposUsuarios", intermediacaoService.filtrarAgruparUsuario(user.getId(), form));
			map.addAttribute("intermediacaoForm", form);
			return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  filtrarAgruparUsuariosIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/filtrarAgruparImoveisIntermediacao", method = RequestMethod.POST)	
	public String filtrarAgruparImoveis(@ModelAttribute("intermediacaoForm") IntermediacaoForm form, 
						  				ModelMap map, 
						  				HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaAgruposImoveis", intermediacaoService.filtrarAgruparImoveis(user.getId(), form));
			map.addAttribute("intermediacaoForm", form);
			return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  filtrarAgruparImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/excluiSolIntermediacao/{idImovelIntermediacao}", method = RequestMethod.GET)
    public String excluiSolIntermediacao(@PathVariable Long idImovelIntermediacao, 
    									 @ModelAttribute("intermediacaoSelecionadaForm") IntermediacaoForm form, 
										 ModelMap map){
		
		try {					
			intermediacaoService.excluiIntermediacao(idImovelIntermediacao);
		/*	if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())) {
				form.setListaSolIntermediacoesRecebidas(intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidas(usuario.getId()));
				map.addAttribute("listaSolIntermediacoesRecebidas", form.getListaSolIntermediacoesRecebidas());
			}	
			else {
				form.setListaMinhasSolIntermediacoes(intermediacaoService.recuperarMinhasSolicitacoesIntermediacoes(usuario.getId()));
				map.addAttribute("listaMinhasSolIntermediacoes", form.getListaMinhasSolIntermediacoes());
			}
			
			form.setListaSolIntermediacoesAceitas(intermediacaoService.recuperarTodasSolIntermediacoesAceitasDistintas(usuario.getId()));
			map.addAttribute("listaSolIntermediacoesAceitas", form.getListaSolIntermediacoesAceitas());*/
			map.addAttribute("intermediacaoForm", form);		
	    	return DIR_PATH_INTERMEDIACOES + "listarMinhasIntermediacoesImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  excluiSolIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/listarImoveisIntermediacoes/{tipoLista}", method = RequestMethod.GET)
    public String listarImoveisIntermediacoes(@PathVariable String tipoLista,
    											HttpSession session, 
    											HttpServletRequest request,
    											ModelMap map){
		
		try {
			UsuarioForm usuario = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			IntermediacaoForm form = new IntermediacaoForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoLista(tipoLista);
			if ( tipoLista.equals("intermediacaoAceita")){			
				map.addAttribute("listaIntermediacaoAceita", intermediacaoService.recuperarTodasSolIntermediacoesAceitasDistintas(usuario.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "intermediacaoAceita");
		    	return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesAceitas";
			}		
			else if ( tipoLista.equals("intermediacaoSolRecebida")){			
				long quantNovas = Long.parseLong(request.getSession().getAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES).toString());
				if ( quantNovas > 0 ){
					intermediacaoService.atualizarStatusSolRecebidasImovelCompartilhado(usuario.getId());				
					session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, 0 );			
				}
				map.addAttribute("listaIntermediacaoSolRecebida", intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidas(usuario.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "intermediacaoSolRecebida");
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";
			}	
			else {
				map.addAttribute("listarIntermediacaoMinhasSol", intermediacaoService.recuperarMinhasSolicitacoesIntermediacoes(usuario.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "intermediacaoMinhasSol");
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";
			}
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  listarImoveisIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/confirmarExclusaoSolIntermediacao/{idSolIntermediacao}")	
	public String confirmarExclusaoSolIntermediacao(@PathVariable Long idSolIntermediacao,
												 	HttpSession session,	
												 	@ModelAttribute("intermediacaoSelecionadaForm") IntermediacaoForm form,
												 	ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			intermediacaoService.excluiIntermediacao(idSolIntermediacao);
			List<Intermediacao> listaTodos = intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidas(user.getId(), form);
			map.addAttribute("quantTotalIntermediacao", AppUtil.recuperarQuantidadeLista(listaTodos));							
			map.addAttribute("listaIntermediacaoSolRecebida", listaTodos);
			return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";	
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  confirmarExclusaoSolIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/confirmarExclusaoEnviadaIntermediacao/{idEnviadaIntermediacao}")	
	public String confirmarExclusaoEnviadaIntermediacao(@PathVariable Long idEnviadaIntermediacao,
														@ModelAttribute("intermediacaoSelecionadaForm") IntermediacaoForm form,
												 		HttpSession session,	
												 		ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			intermediacaoService.excluiIntermediacao(idEnviadaIntermediacao);
			List<Intermediacao> listaTodos = intermediacaoService.recuperarMinhasSolicitacoesIntermediacoes(user.getId(), form);
			map.addAttribute("quantTotalIntermediacao", AppUtil.recuperarQuantidadeLista(listaTodos));
			map.addAttribute("listarIntermediacaoMinhasSol", listaTodos);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "intermediacaoMinhasSol");
			return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  confirmarExclusaoEnviadaIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/confirmarExclusaoSolIntermediacaoAnaliseSol/{idSolIntermediacao}/{idImovel}")	
	public String confirmarExclusaoSolIntermediacaoAnaliseSol(@PathVariable Long idSolIntermediacao,
															  @PathVariable Long idImovel,			
												 			  ModelMap map){
		
		try {
			intermediacaoService.excluiIntermediacao(idSolIntermediacao);
			map.addAttribute("intermediacaoSelecionadaForm", intermediacaoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel));
			map.addAttribute("listaSolImovelIntermediacao", intermediacaoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - IntermediacaoController -  confirmarExclusaoSolIntermediacaoAnaliseSol");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}			
	}
}
