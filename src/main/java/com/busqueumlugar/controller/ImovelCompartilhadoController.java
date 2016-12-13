package com.busqueumlugar.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.busqueumlugar.enumerador.StatusImovelCompartilhadoEnum;
import com.busqueumlugar.enumerador.TipoImovelCompartilhadoEnum;
import com.busqueumlugar.form.ImovelcompartilhadoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovelcompartilhado;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelcompartilhadoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("imovelCompartilhadoController")
@RequestMapping("/imovelCompartilhado")
@SessionAttributes(value={"intermediacaoForm", "parceriaForm"})
public class ImovelCompartilhadoController {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelCompartilhadoController.class);
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ImovelcompartilhadoService imovelCompartilhadoService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;

	
	private static final String DIR_PATH_PARCERIA = "/imovel/parceria/";
	private static final String DIR_PATH_INTERMEDIACOES = "/imovel/intermediacoes/";
	
	
	
	@RequestMapping(value = "/buscarCidadesIntermediacao/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstadoIntermediacao(@PathVariable("idEstado") Integer idEstado, 
    										  			   @ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form,	
    										  			   ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - intermediacaoForm -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairrosIntermediacao/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstadoIntermediacao(@PathVariable("idCidade") Integer idCidade,
    										  			   @ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form,	
    										  			   ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - intermediacaoForm -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	@RequestMapping(value = "/buscarCidadesParceria/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstadoParceria(@PathVariable("idEstado") Integer idEstado, 
    										  		  @ModelAttribute("parceriaForm") ImovelcompartilhadoForm form,	
    										  		  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - parceriaForm -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairrosParceria/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstadoParceria(@PathVariable("idCidade") Integer idCidade,
    										  		  @ModelAttribute("parceriaForm") ImovelcompartilhadoForm form,	
    										  		  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - parceriaForm -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	
	@RequestMapping(value = "/desmarcarCheckIntermediacao")	
	public void desmarcarCheckIntermediacao(Long idImovelcompartilhado, HttpServletResponse response, HttpSession session){
		
		try {
			imovelCompartilhadoService.atualizarStatusImovelCompartilhado(imovelCompartilhadoService.recuperarImovelcompartilhadoPorId(idImovelcompartilhado));
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, imovelCompartilhadoService.checarQuantidadeNovasSolImoveisCompartilhamentoPorTipoCompartilhamento(user.getId(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo()) );
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  desmarcarCheckIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/desmarcarCheckParceria")	
	public void desmarcarCheckParceria(Long idImovelcompartilhado, HttpServletResponse response, HttpSession session){
		
		try {
			imovelCompartilhadoService.atualizarStatusImovelCompartilhado(imovelCompartilhadoService.recuperarImovelcompartilhadoPorId(idImovelcompartilhado));
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_NOVAS_PARCERIAS, imovelCompartilhadoService.checarQuantidadeNovasSolImoveisCompartilhamentoPorTipoCompartilhamento(user.getId(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo()) );
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  desmarcarCheckParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/visualizarImoveisParceriaPorUsuario/{idUsuario}/{tipoLista}", method = RequestMethod.GET)
    public String visualizarImoveisParceriaPorUsuario(@ModelAttribute("parceriaForm") ImovelcompartilhadoForm form,
    									 			  @PathVariable("idUsuario") Long idUsuario,
    									 			  @PathVariable("tipoLista") String tipoLista,
    									 			  HttpSession session, 
    									 			  ModelMap map){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("parceriaForm", form);
	        map.addAttribute("usuarioParceria", usuarioService.recuperarUsuarioPorId(idUsuario));
	        if ( tipoLista.equals("parceriaAceita"))
	        	map.addAttribute("listaTodasParcerias", imovelCompartilhadoService.recuperarImoveisCompartilhadoPorUsuario(idUsuario, user.getId(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo()));
	        else if ( tipoLista.equals("solRecebida"))
	        	map.addAttribute("listaTodasParcerias", imovelCompartilhadoService.recuperarImoveisCompartilhadoPorUsuario(user.getId(), idUsuario, TipoImovelCompartilhadoEnum.PARCERIA.getRotulo()));
	        else if ( tipoLista.equals("minhasSol"))
	        	map.addAttribute("listaTodasParcerias", imovelCompartilhadoService.recuperarImoveisCompartilhadoPorUsuario(idUsuario, user.getId(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo()));
	        
	        return DIR_PATH_PARCERIA + "visualizarImoveisParceriaPorUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  visualizarImoveisParceriaPorUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/visualizarUsuariosParceriaPorImovel/{idImovel}/{tipoLista}", method = RequestMethod.GET)
    public String visualizarUsuariosParceriaPorImovel(@ModelAttribute("parceriaForm") ImovelcompartilhadoForm form,
    									 			  @PathVariable("idImovel") Long idImovel,
    									 			  @PathVariable("tipoLista") String tipoLista,    									 			  
    									 			  ModelMap map){
		
		try {
			map.addAttribute("parceriaForm", form);			
	        map.addAttribute("imovelParceria", imovelService.recuperarImovelPorid(idImovel));
	        if ( tipoLista.equals("aceita"))
	        	map.addAttribute("listaTodasParcerias", imovelCompartilhadoService.recuperarUsuariosCompartilhadoPorImovel(idImovel));
	        else if ( tipoLista.equals("solRecebida"))
	        	map.addAttribute("listaTodasParcerias", imovelCompartilhadoService.recuperarUsuariosCompartilhadoPorImovel(idImovel));
	        else if ( tipoLista.equals("minhaSol"))
	        	map.addAttribute("listaTodasParcerias", imovelCompartilhadoService.recuperarUsuariosCompartilhadoPorImovel(idImovel));
	        
	        return DIR_PATH_PARCERIA + "visualizarUsuariosParceriaPorImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  visualizarUsuariosParceriaPorImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	// inicio intermediacao ----------------------------------------------------------
	@RequestMapping(value = "/visualizarImoveisIntermediacaoPorUsuario/{idUsuario}/{tipoLista}", method = RequestMethod.GET)
    public String visualizarImoveisIntermediacaoPorUsuario(@ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form,
    									 				   @PathVariable("idUsuario") Long idUsuario,
    									 				   @PathVariable("tipoLista") String tipoLista,
    									 				   HttpSession session, 
    									 				   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("intermediacaoForm", form);			
	        map.addAttribute("usuarioIntermediacao", usuarioService.recuperarUsuarioPorId(idUsuario));
	        if ( tipoLista.equals("aceita"))
	        	map.addAttribute("listaTodasIntermediacoes", imovelCompartilhadoService.recuperarImoveisCompartilhadoPorUsuario(user.getId(), idUsuario, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo()));
	        else if ( tipoLista.equals("minhaSol"))
	        	map.addAttribute("listaTodasIntermediacoes", imovelCompartilhadoService.recuperarImoveisCompartilhadoPorUsuario(idUsuario, user.getId(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo()));
	        else if ( tipoLista.equals("solRecebida"))
	        	map.addAttribute("listaTodasIntermediacoes", imovelCompartilhadoService.recuperarImoveisCompartilhadoPorUsuario(user.getId(), idUsuario, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo()));
	        
	        return DIR_PATH_INTERMEDIACOES + "visualizarImoveisIntermediacaoPorUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  visualizarImoveisIntermediacaoPorUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/todosUsuariosIntermediacoes/{idImovel}", method = RequestMethod.GET)
    public String todosUsuariosIntermediacoes(@ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form,
    									 	  @PathVariable("idImovel") Long idImovel,
    									 	  HttpSession session, 
    									 	  ModelMap map){
		
		try {
			map.addAttribute("listaTodasIntermediacoes", imovelCompartilhadoService.recuperarUsuariosCompartilhadoPorImovel(idImovel));
			map.addAttribute("intermediacaoForm", form);		
	        map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	        return DIR_PATH_INTERMEDIACOES + "todosUsuariosIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  todosUsuariosIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/todosImoveisIntermediacoesPorUsuario/{idUsuario}", method = RequestMethod.GET)
    public String todosImoveisIntermediacoesPorUsuario(@ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form,
    									 	  		   @PathVariable("idUsuario") Long idUsuario,
    									 	  		   HttpSession session, 
    									 	  		   ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Imovelcompartilhado> lista = imovelCompartilhadoService.recuperarTodosImoveisCompartilhadoPorUsuario(idUsuario, user.getId(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
			map.addAttribute("listaTodosImoveisIntermediacoes", lista);
			map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));  
			map.addAttribute("intermediacaoForm", form);		
	        map.addAttribute("usuarioIntermediacao", usuarioService.recuperarUsuarioPorId(idUsuario));
	        return DIR_PATH_INTERMEDIACOES + "todosImoveisIntermediacaoPorUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  todosImoveisIntermediacoesPorUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/todosUsuariosParcerias/{idImovel}", method = RequestMethod.GET)
    public String todosUsuariosParcerias(@ModelAttribute("parceriaForm") ImovelcompartilhadoForm form,
    									 @PathVariable("idImovel") Long idImovel,
    									 HttpSession session, 
    									 ModelMap map){
		try {
			map.addAttribute("listaTodasParcerias", imovelCompartilhadoService.recuperarUsuariosCompartilhadoPorImovel(idImovel));		
			map.addAttribute("parceriaForm", form);		
	        map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	        return DIR_PATH_PARCERIA  + "todosUsuariosParcerias";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  todosUsuariosParcerias");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/todosImoveisParceriasPorUsuario/{idUsuario}", method = RequestMethod.GET)
    public String todosImoveisParceriasPorUsuario(@ModelAttribute("parceriaForm") ImovelcompartilhadoForm form,
    									 	  	  @PathVariable("idUsuario") Long idUsuario,
    									 	      HttpSession session, 
    									 	  	  ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Imovelcompartilhado> lista = imovelCompartilhadoService.recuperarTodosImoveisCompartilhadoPorUsuario(idUsuario, user.getId(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
			map.addAttribute("listaTodosImoveisParcerias", lista);
			map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));  
			map.addAttribute("parceriaForm", form);		
	        map.addAttribute("usuarioParceria", usuarioService.recuperarUsuarioPorId(idUsuario));
	        return DIR_PATH_PARCERIA + "todosImoveisParceriaPorUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  todosImoveisParceriasPorUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/modoVisualizarIntermediacao", method = RequestMethod.POST)
    public String modoVisualizarIntermediacao(HttpSession session,
    										  @ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form, 
    							 		 	  ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getTipoLista().equals("intermediacaoAceita")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.agruparImoveisIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.agruparUsuariosIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaIntermediacaoAceita", imovelCompartilhadoService.recuperarTodasSolIntermediacoesAceitasDistintas(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
			    	return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesAceitas";
				}
			}		
			else if ( form.getTipoLista().equals("intermediacaoSolRecebida")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.agruparImoveisIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.agruparUsuariosIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaIntermediacaoSolRecebida", imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidas(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";			
				}			
			}
			else if ( form.getTipoLista().equals("intermediacaoMinhasSol")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.agruparImoveisIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.agruparUsuariosIntermediacao(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listarIntermediacaoMinhasSol", imovelCompartilhadoService.recuperarMinhasSolicitacoesIntermediacoes(user.getId(), form));
					map.addAttribute("intermediacaoForm", form);
					return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";		
				}		 
			}		
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  modoVisualizarIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }	
	
	@RequestMapping(value = "/modoVisualizarParceria", method = RequestMethod.POST)
    public String modoVisualizarParceria(HttpSession session,
    							 		 @ModelAttribute("parceriaForm") ImovelcompartilhadoForm form, 
    							 		 ModelMap map){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getTipoLista().equals("parceriaAceita")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.agruparImoveisParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparImoveisParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.agruparUsuariosParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaParceriaAceita", imovelCompartilhadoService.recuperarTodasSolCompartilhamentoAceitasDistintas(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "listarImoveisParceriaAceita";		
				}
			}		
			else if ( form.getTipoLista().equals("parceriaSolRecebida")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.agruparImoveisParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparImoveisParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){				
					map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.agruparUsuariosParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaParceriaSolRecebida", imovelCompartilhadoService.recuperarSolicitacoesCompartilhamentoRecebidas(user.getId(), form));			
					map.addAttribute("parceriaForm", form);			
					return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";
				}			
			}
			else if ( form.getTipoLista().equals("parceriaMinhasSol")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.agruparImoveisParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparImoveisParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.agruparUsuariosParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaParceriaMinhasSol", imovelCompartilhadoService.recuperarMinhasSolicitacoesCompartilhamento(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "listarImoveisParceriaEnviada";		
				}			
			}		
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  modoVisualizarParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }

	
	@RequestMapping(value="/ordenarIntermediacao", method = RequestMethod.POST)
	public String ordenarIntermediacoes(@ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form,										
										ModelMap map,
										HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getTipoLista().equals("intermediacaoSolRecebida")) {
				map.addAttribute("listaIntermediacaoSolRecebida", imovelCompartilhadoService.filtrarIntermediacao(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";
			}	
			else if ( form.getTipoLista().equals("intermediacaoMinhasSol")) {			
				map.addAttribute("listarIntermediacaoMinhasSol", imovelCompartilhadoService.filtrarIntermediacao(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";
			}	
			else {			
				map.addAttribute("listaIntermediacaoAceita", imovelCompartilhadoService.filtrarIntermediacao(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesAceitas";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  ordenarIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/ordenarAgruparIntermediacoes", method = RequestMethod.POST)
	public String ordenarAgruparIntermediacoes(HttpSession session,
											   ModelMap map,
											   @ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form){
		 
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			if ( form.getOpcaoVisualizacao().equals("agruparImoveis")) {			
				map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.ordenarAgruparIntermediacoes(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
			}	
			else {			
				map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.ordenarAgruparIntermediacoes(user.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  ordenarAgruparIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	@RequestMapping(value="/ordenarParceria", method = RequestMethod.POST)
	public String ordenarParceria(@ModelAttribute("parceriaForm") ImovelcompartilhadoForm form, 
								  ModelMap map,	
								 HttpSession session){	
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			
			if ( form.getTipoLista().equals("parceriaMinhasSol")){
				map.addAttribute("listaParceriaMinhasSol", imovelCompartilhadoService.filtrarParceria(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaEnviada";
			}	
			else if ( form.getTipoLista().equals("parceriaSolRecebida")){
				map.addAttribute("listaParceriaSolRecebida", imovelCompartilhadoService.filtrarParceria(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";			
			}	
			else {
				map.addAttribute("listaParceriaAceita", imovelCompartilhadoService.filtrarParceria(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaAceita";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  ordenarParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/ordenarAgruparParcerias", method = RequestMethod.POST)
	public String ordenarAgruparParcerias(HttpSession session,
									      ModelMap map,
									      @ModelAttribute("parceriaForm") ImovelcompartilhadoForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);

			if ( form.getOpcaoVisualizacao().equals("agruparImoveis")) {			
				map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.ordenarAgruparParcerias(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "agruparImoveisParceria";
			}	
			else {			
				map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.ordenarAgruparParcerias(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  ordenarAgruparParcerias");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
   @RequestMapping(value = "/filtrarParceria", method = RequestMethod.POST)
    public String filtrarParceria(@ModelAttribute("parceriaForm") ImovelcompartilhadoForm form, 
    							  ModelMap map,
    							  HttpSession session){    	
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);    	
	    	if ( form.getTipoLista().equals("parceriaSolRecebida")){			
				map.addAttribute("listaParceriaSolRecebida", imovelCompartilhadoService.filtrarParceria(user.getId(), form));						
	    		map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";
	    	}    	
	    	else if ( form.getTipoLista().equals("parceriaMinhasSol")) {
				map.addAttribute("listaParceriaMinhasSol", imovelCompartilhadoService.filtrarParceria(user.getId(), form));
	    		map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaEnviada";
	    	}	
	    	else if ( form.getTipoLista().equals("parceriaAceita")) {
				map.addAttribute("listaParceriaAceita", imovelCompartilhadoService.filtrarParceria(user.getId(), form));
	    		map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaAceita";
	    	}	    	
	    	return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  filtrarParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
    }
            
	@RequestMapping(value = "/analisarSolicitacoesParceriasRecebidas/{idImovel}", method = RequestMethod.GET)
    public String goAnalisarSolCompartilhamento(@PathVariable Long idImovel, ModelMap map){
		
		try {
			map.addAttribute("listaSolImovelParceria", imovelCompartilhadoService.recuperarSolicitacoesParceriasRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("listaImovelParceriaSelecionados", imovelCompartilhadoService.recuperarTodosImoveisCompartilhadosAceitos(idImovel) );
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	    	return DIR_PATH_PARCERIA + "analisarSolicitacoesParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  goAnalisarSolCompartilhamento");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/aceitarSolImovelParceria/{idImovelParceria}/{idImovel}", method = RequestMethod.GET)
    public String aceitarSolImovelParceria(@PathVariable Long idImovelParceria, 
    									   @PathVariable Long idImovel, 
    									   ModelMap map){
		
		try {
			imovelCompartilhadoService.atualizarStatusCompartilhamento(idImovelParceria, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
			map.addAttribute("listaSolImovelParceria", imovelCompartilhadoService.recuperarSolicitacoesParceriasRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("listaImovelParceriaSelecionados", imovelCompartilhadoService.recuperarTodosImoveisCompartilhadosAceitos(idImovel) );
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	    	return DIR_PATH_PARCERIA + "analisarSolicitacoesParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  aceitarSolImovelParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/excluirSolAnaliseImovelParceria/{idImovelParceria}/{idImovel}", method = RequestMethod.GET)
    public String excluirSolAnaliseImovelParceria(@PathVariable Long idImovelParceria, 
												  @PathVariable Long idImovel, 
												  ModelMap map){
		try {
			Imovelcompartilhado imovelcompartilhado = imovelCompartilhadoService.recuperarImovelcompartilhadoPorId(idImovelParceria) ;
			imovelCompartilhadoService.excluiImovelCompartilhado(imovelcompartilhado);
			map.addAttribute("listaSolImovelParceria", imovelCompartilhadoService.recuperarSolicitacoesParceriasRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("listaImovelParceriaSelecionados", imovelCompartilhadoService.recuperarTodosImoveisCompartilhadosAceitos(idImovel) );
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	    	return DIR_PATH_PARCERIA + "analisarSolicitacoesParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  excluirSolAnaliseImovelParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/rejeitarImovelParceria/{idImovelParceria}/{idImovel}", method = RequestMethod.GET)
    public String rejeitarImovelParceria(@PathVariable Long idImovelParceria, 
    									 @PathVariable Long idImovel, 
    									 ModelMap map){		
		try {
			imovelCompartilhadoService.atualizarStatusCompartilhamento(idImovelParceria, "rejeitada", TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());
			map.addAttribute("listaSolImovelParceria", imovelCompartilhadoService.recuperarSolicitacoesParceriasRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("listaImovelParceriaSelecionados", imovelCompartilhadoService.recuperarTodosImoveisCompartilhadosAceitos(idImovel) );
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	    	return DIR_PATH_PARCERIA + "analisarSolicitacoesParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  rejeitarImovelParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	

	@RequestMapping(value = "/analisarSolicitacoesIntermediacoesRecebidas/{idImovel}", method = RequestMethod.GET)
    public String goAnalisarSolIntermediacoes(@PathVariable Long idImovel, ModelMap map){
		try {
			map.addAttribute("intermediacaoSelecionadaForm", imovelCompartilhadoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel));
			map.addAttribute("listaSolImovelIntermediacao", imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  goAnalisarSolIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
	@RequestMapping(value = "/limparImovelIntermediacaoSelecionada/{idIntermediacao}/{idImovel}", method = RequestMethod.GET)
    public String limparImovelIntermediacoesSelecionado(@PathVariable Long idIntermediacao,
    													@PathVariable Long idImovel, 
    													@ModelAttribute("intermediacaoSelecionadaForm") ImovelcompartilhadoForm form,
    													ModelMap map,
    													HttpSession session){
		
		try {
			imovelCompartilhadoService.atualizarStatusCompartilhamento(idIntermediacao, "rejeitada", TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
			List<Imovelcompartilhado> lista = imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel);
			map.addAttribute("intermediacaoSelecionadaForm", imovelCompartilhadoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel) );
			map.addAttribute("listaSolImovelIntermediacao", lista);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, AppUtil.recuperarQuantidadeLista(lista));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  limparImovelIntermediacoesSelecionado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
	@RequestMapping(value = "/aceitarSolImovelIntermediacao/{idImovelIntermediacao}/{idImovel}", method = RequestMethod.GET)
    public String aceitarSolImovelIntermediacoes(@PathVariable Long idImovelIntermediacao, 
											     @PathVariable Long idImovel, 
												 ModelMap map,
												 HttpSession session){
		
		try {
			imovelCompartilhadoService.atualizarStatusCompartilhamento(idImovelIntermediacao, StatusImovelCompartilhadoEnum.ACEITA.getRotulo(), TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());
			List<Imovelcompartilhado> lista = imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel);
			map.addAttribute("intermediacaoSelecionadaForm", imovelCompartilhadoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel) );
			map.addAttribute("listaSolImovelIntermediacao", lista);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, AppUtil.recuperarQuantidadeLista(lista));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  aceitarSolImovelIntermediacoes");
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
			imovelCompartilhadoService.excluiImovelCompartilhado(idImovelIntermediacao);
			List<Imovelcompartilhado> lista = imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel);
			map.addAttribute("intermediacaoSelecionadaForm", imovelCompartilhadoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel) );
			map.addAttribute("listaSolImovelIntermediacao", lista);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, AppUtil.recuperarQuantidadeLista(lista));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  excluirSolAnaliseImovelIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
    
    @RequestMapping(value = "/filtrarIntermediacao", method = RequestMethod.POST)
    public String filtrarIntermediacao(@ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form, 
    								   ModelMap map,
    								   HttpSession session){
    	
    	try {
    		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
        	if ( form.getTipoLista().equals("intermediacaoSolRecebida")) {      		
        		map.addAttribute("listaIntermediacaoSolRecebida", imovelCompartilhadoService.filtrarIntermediacao(user.getId(), form));							   		
        		map.addAttribute("intermediacaoForm", form);			
        		return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";
        	}	
        	else if ( form.getTipoLista().equals("intermediacaoMinhasSol")) {    		
    			map.addAttribute("listarIntermediacaoMinhasSol", imovelCompartilhadoService.filtrarIntermediacao(user.getId(), form));
        		map.addAttribute("intermediacaoForm", form);
        		return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";
        	}	
        	else if ( form.getTipoLista().equals("intermediacaoAceita")) {
    			map.addAttribute("listaIntermediacaoAceita", imovelCompartilhadoService.filtrarIntermediacao(user.getId(), form));
    			map.addAttribute("intermediacaoForm", form);
        		return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesAceitas";
        	}	    	
    		return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  filtrarIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value="/filtrarAgruparUsuariosIntermediacao", method = RequestMethod.POST)	
	public String filtrarAgruparUsuariosIntermediacao(@ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form, 
													  ModelMap map, 
													  HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.filtrarAgruparUsuario(user.getId(), form, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo()));
			map.addAttribute("intermediacaoForm", form);
			return DIR_PATH_INTERMEDIACOES + "agruparUsuariosIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  filtrarAgruparUsuariosIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/filtrarAgruparUsuariosParceria", method = RequestMethod.POST)	
	public String filtrarAgruparUsuariosParceria(@ModelAttribute("parceriaForm") ImovelcompartilhadoForm form, 
												 ModelMap map, 
												 HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaAgruposUsuarios", imovelCompartilhadoService.filtrarAgruparUsuario(user.getId(), form, TipoImovelCompartilhadoEnum.PARCERIA.getRotulo()));
			map.addAttribute("parceriaForm", form);
			return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  filtrarAgruparUsuariosParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/filtrarAgruparImoveisIntermediacao", method = RequestMethod.POST)	
	public String filtrarAgruparImoveis(@ModelAttribute("intermediacaoForm") ImovelcompartilhadoForm form, 
						  				ModelMap map, 
						  				HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaAgruposImoveis", imovelCompartilhadoService.filtrarAgruparImoveis(user.getId(), form, TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo()));
			map.addAttribute("intermediacaoForm", form);
			return DIR_PATH_INTERMEDIACOES + "agruparImoveisIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  filtrarAgruparImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
    
	@RequestMapping(value = "/listarImoveisParcerias/{tipoLista}", method = RequestMethod.GET)
    public String listarImoveisParcerias(@PathVariable String tipoLista,    									 
    									 HttpSession session, 
    									 HttpServletRequest request,
    									 ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			ImovelcompartilhadoForm form = new ImovelcompartilhadoForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoLista(tipoLista);				
			if ( tipoLista.equals("parceriaAceita")){
				map.addAttribute("parceriaForm", form);
				map.addAttribute("listaParceriaAceita", imovelCompartilhadoService.recuperarTodasSolCompartilhamentoAceitasDistintas(user.getId(), form));
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "parceriaAceita");
				return DIR_PATH_PARCERIA + "listarImoveisParceriaAceita";
			}
			else if ( tipoLista.equals("parceriaSolRecebida")){		
				long quantNovas = Long.parseLong(request.getSession().getAttribute(ImovelService.QUANT_NOVAS_PARCERIAS).toString());
				if ( quantNovas > 0 ){
					imovelCompartilhadoService.atualizarStatusSolRecebidasImovelCompartilhado(user.getId(), TipoImovelCompartilhadoEnum.PARCERIA.getRotulo());				
					session.setAttribute(ImovelService.QUANT_NOVAS_PARCERIAS, 0 );			
				}					
				map.addAttribute("listaParceriaSolRecebida", imovelCompartilhadoService.recuperarSolicitacoesCompartilhamentoRecebidas(user.getId(), form));
				map.addAttribute("parceriaForm", form);	
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "parceriaSolRecebida");
				return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";
			}
			else {
				map.addAttribute("listaParceriaMinhasSol", imovelCompartilhadoService.recuperarMinhasSolicitacoesCompartilhamento(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "parceriaMinhasSol");
				return DIR_PATH_PARCERIA + "listarImoveisParceriaEnviada";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  listarImoveisParcerias");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
    }
	
	@RequestMapping(value = "/excluirSolParceria/{idImovelParceria}", method = RequestMethod.GET)
    public String excluirSolParceria(HttpSession session, 
									 @PathVariable Long idImovelParceria, 
									 @ModelAttribute("parceriaForm") ImovelcompartilhadoForm form, 
									 ModelMap map){        
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			imovelCompartilhadoService.excluiImovelCompartilhado(idImovelParceria);
			map.addAttribute("listaMinhaSolImovelParceria", imovelCompartilhadoService.recuperarMinhasSolicitacoesCompartilhamento(user.getId(), form));
			map.addAttribute("listaSolRecebidasImovelParceria", imovelCompartilhadoService.recuperarSolicitacoesCompartilhamentoRecebidas(user.getId(), form));
			map.addAttribute("listaAceitaImovelParceria", imovelCompartilhadoService.recuperarTodasSolCompartilhamentoAceitasDistintas(user.getId(), form));		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("parceriaForm", form);	
			return DIR_PATH_PARCERIA + "listarImoveisParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  excluirSolParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/excluiSolIntermediacao/{idImovelIntermediacao}", method = RequestMethod.GET)
    public String excluiSolIntermediacao(@PathVariable Long idImovelIntermediacao, 
    									 @ModelAttribute("intermediacaoSelecionadaForm") ImovelcompartilhadoForm form, 
										 ModelMap map){
		
		try {					
			imovelCompartilhadoService.excluiImovelCompartilhado(idImovelIntermediacao);
		/*	if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())) {
				form.setListaSolIntermediacoesRecebidas(imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidas(usuario.getId()));
				map.addAttribute("listaSolIntermediacoesRecebidas", form.getListaSolIntermediacoesRecebidas());
			}	
			else {
				form.setListaMinhasSolIntermediacoes(imovelCompartilhadoService.recuperarMinhasSolicitacoesIntermediacoes(usuario.getId()));
				map.addAttribute("listaMinhasSolIntermediacoes", form.getListaMinhasSolIntermediacoes());
			}
			
			form.setListaSolIntermediacoesAceitas(imovelCompartilhadoService.recuperarTodasSolIntermediacoesAceitasDistintas(usuario.getId()));
			map.addAttribute("listaSolIntermediacoesAceitas", form.getListaSolIntermediacoesAceitas());*/
			map.addAttribute("intermediacaoForm", form);		
	    	return DIR_PATH_INTERMEDIACOES + "listarMinhasIntermediacoesImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  excluiSolIntermediacao");
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
			ImovelcompartilhadoForm form = new ImovelcompartilhadoForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoLista(tipoLista);
			if ( tipoLista.equals("intermediacaoAceita")){			
				map.addAttribute("listaIntermediacaoAceita", imovelCompartilhadoService.recuperarTodasSolIntermediacoesAceitasDistintas(usuario.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "intermediacaoAceita");
		    	return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesAceitas";
			}		
			else if ( tipoLista.equals("intermediacaoSolRecebida")){			
				long quantNovas = Long.parseLong(request.getSession().getAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES).toString());
				if ( quantNovas > 0 ){
					imovelCompartilhadoService.atualizarStatusSolRecebidasImovelCompartilhado(usuario.getId(),TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo());				
					session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, 0 );			
				}
				map.addAttribute("listaIntermediacaoSolRecebida", imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidas(usuario.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "intermediacaoSolRecebida");
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";
			}	
			else {
				map.addAttribute("listarIntermediacaoMinhasSol", imovelCompartilhadoService.recuperarMinhasSolicitacoesIntermediacoes(usuario.getId(), form));
				map.addAttribute("intermediacaoForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "intermediacaoMinhasSol");
				return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  listarImoveisIntermediacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/confirmarExclusaoSolIntermediacao/{idSolIntermediacao}")	
	public String confirmarExclusaoSolIntermediacao(@PathVariable Long idSolIntermediacao,
												 	HttpSession session,	
												 	@ModelAttribute("intermediacaoSelecionadaForm") ImovelcompartilhadoForm form,
												 	ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelCompartilhadoService.excluiImovelCompartilhado(idSolIntermediacao);
			List<Imovelcompartilhado> listaTodos = imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidas(user.getId(), form);
			map.addAttribute("quantTotalIntermediacao", AppUtil.recuperarQuantidadeLista(listaTodos));							
			map.addAttribute("listaIntermediacaoSolRecebida", listaTodos);
			return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesSolicitadas";	
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  confirmarExclusaoSolIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/confirmarExclusaoEnviadaIntermediacao/{idEnviadaIntermediacao}")	
	public String confirmarExclusaoEnviadaIntermediacao(@PathVariable Long idEnviadaIntermediacao,
														@ModelAttribute("intermediacaoSelecionadaForm") ImovelcompartilhadoForm form,
												 		HttpSession session,	
												 		ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelCompartilhadoService.excluiImovelCompartilhado(idEnviadaIntermediacao);
			List<Imovelcompartilhado> listaTodos = imovelCompartilhadoService.recuperarMinhasSolicitacoesIntermediacoes(user.getId(), form);
			map.addAttribute("quantTotalIntermediacao", AppUtil.recuperarQuantidadeLista(listaTodos));
			map.addAttribute("listarIntermediacaoMinhasSol", listaTodos);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "intermediacaoMinhasSol");
			return DIR_PATH_INTERMEDIACOES + "listarIntermediacoesEnviadas";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  confirmarExclusaoEnviadaIntermediacao");
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
			imovelCompartilhadoService.excluiImovelCompartilhado(idSolIntermediacao);
			map.addAttribute("intermediacaoSelecionadaForm", imovelCompartilhadoService.recuperarImovelIntermediadoSelecionadoPorIdImovel(idImovel));
			map.addAttribute("listaSolImovelIntermediacao", imovelCompartilhadoService.recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			return DIR_PATH_INTERMEDIACOES + "analisarSolicitacoesIntermediacoes";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  confirmarExclusaoSolIntermediacaoAnaliseSol");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}			
	}
	
	@RequestMapping(value = "/confirmarExclusaoSolParceria/{idSolParceria}")	
	public String confirmarExclusaoSolParceria(@PathVariable Long idSolParceria,
												 	HttpSession session,	
												 	@ModelAttribute("parceriaSelecionadaForm") ImovelcompartilhadoForm form,
												 	ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelCompartilhadoService.excluiImovelCompartilhado(idSolParceria);							
			map.addAttribute("listaParceriaSolRecebida", imovelCompartilhadoService.recuperarSolicitacoesCompartilhamentoRecebidas(user.getId(), form));		
			return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  adicionarComentario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/confirmarExclusaoEnviadaParceria/{idEnviadaParceria}")	
	public String confirmarExclusaoEnviadaParceria(@PathVariable Long idEnviadaParceria,
												   @ModelAttribute("parceriaSelecionadaForm") ImovelcompartilhadoForm form,	
												   HttpSession session,	
												   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelCompartilhadoService.excluiImovelCompartilhado(idEnviadaParceria);
			map.addAttribute("listaParceriaMinhasSol", imovelCompartilhadoService.recuperarMinhasSolicitacoesCompartilhamento(user.getId(), form));
			return DIR_PATH_PARCERIA + "listarImoveisParceriaEnviada";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  adicionarComentario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}			
	}
	
	
	@RequestMapping(value = "/confirmarExclusaoSolParceriaAnaliseSol/{idSolParceria}/{idImovel}")	
	public String confirmarExclusaoSolParceriaAnaliseSol(@PathVariable Long idSolParceria,
														 @PathVariable Long idImovel,			
												 		 ModelMap map){
		
		try {
			imovelCompartilhadoService.excluiImovelCompartilhado(idSolParceria);		
			map.addAttribute("listaSolImovelParceria", imovelCompartilhadoService.recuperarSolicitacoesParceriasRecebidasPorIdImovelPorStatus(idImovel));
			map.addAttribute("listaImovelParceriaSelecionados", imovelCompartilhadoService.recuperarTodosImoveisCompartilhadosAceitos(idImovel) );
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	    	return DIR_PATH_PARCERIA + "analisarSolicitacoesParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  adicionarComentario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}

}
