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
import com.busqueumlugar.form.ParceriaForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Parceria;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ParceriaService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("parceriaController")
@RequestMapping("/parceria")
@SessionAttributes({"parceriaForm"})
public class ParceriaController {
	
private static final Logger log = LoggerFactory.getLogger(ParceriaController.class);
	
	@Autowired
	private ParceriaService parceriaService;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;

	
	private static final String DIR_PATH_PARCERIA = "/imovel/parceria/";


	
	@RequestMapping(value = "/buscarCidadesParceria/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstadoParceria(@PathVariable("idEstado") Integer idEstado, 
    										  		  @ModelAttribute("parceriaForm") ParceriaForm form,	
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
    										  		  @ModelAttribute("parceriaForm") ParceriaForm form,	
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

	
	@RequestMapping(value = "/desmarcarCheckParceria")	
	public void desmarcarCheckParceria(Long idImovelcompartilhado, HttpServletResponse response, HttpSession session){
		
		try {
			parceriaService.atualizarStatusParceria(parceriaService.recuperarParceriaId(idImovelcompartilhado));
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_NOVAS_PARCERIAS, parceriaService.checarQuantidadeNovasSolParceria(user.getId()) );
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  desmarcarCheckParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/visualizarImoveisParceriaPorUsuario/{idUsuario}/{tipoLista}", method = RequestMethod.GET)
    public String visualizarImoveisParceriaPorUsuario(@ModelAttribute("parceriaForm") ParceriaForm form,
    									 			  @PathVariable("idUsuario") Long idUsuario,
    									 			  @PathVariable("tipoLista") String tipoLista,
    									 			  HttpSession session, 
    									 			  ModelMap map){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("parceriaForm", form);
	        map.addAttribute("usuarioParceria", usuarioService.recuperarUsuarioPorId(idUsuario));
	        if ( tipoLista.equals("parceriaAceita"))
	        	map.addAttribute("listaTodasParcerias", parceriaService.recuperarParceriaPorUsuario(idUsuario, user.getId()));
	        else if ( tipoLista.equals("solRecebida"))
	        	map.addAttribute("listaTodasParcerias", parceriaService.recuperarParceriaPorUsuario(user.getId(), idUsuario));
	        else if ( tipoLista.equals("minhasSol"))
	        	map.addAttribute("listaTodasParcerias", parceriaService.recuperarParceriaPorUsuario(idUsuario, user.getId()));
	        
	        return DIR_PATH_PARCERIA + "visualizarImoveisParceriaPorUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  visualizarImoveisParceriaPorUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/visualizarUsuariosParceriaPorImovel/{idImovel}/{tipoLista}", method = RequestMethod.GET)
    public String visualizarUsuariosParceriaPorImovel(@ModelAttribute("parceriaForm") ParceriaForm form,
    									 			  @PathVariable("idImovel") Long idImovel,
    									 			  @PathVariable("tipoLista") String tipoLista,    									 			  
    									 			  ModelMap map){
		
		try {
			map.addAttribute("parceriaForm", form);			
	        map.addAttribute("imovelParceria", imovelService.recuperarImovelPorid(idImovel));
	        if ( tipoLista.equals("aceita"))
	        	map.addAttribute("listaTodasParcerias", parceriaService.recuperarTodosImovelParceriaPorImovel(idImovel));
	        else if ( tipoLista.equals("solRecebida"))
	        	map.addAttribute("listaTodasParcerias", parceriaService.recuperarTodosImovelParceriaPorImovel(idImovel));
	        else if ( tipoLista.equals("minhaSol"))
	        	map.addAttribute("listaTodasParcerias", parceriaService.recuperarTodosImovelParceriaPorImovel(idImovel));
	        
	        return DIR_PATH_PARCERIA + "visualizarUsuariosParceriaPorImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  visualizarUsuariosParceriaPorImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	
	@RequestMapping(value = "/todosUsuariosParcerias/{idImovel}", method = RequestMethod.GET)
    public String todosUsuariosParcerias(@ModelAttribute("parceriaForm") ParceriaForm form,
    									 @PathVariable("idImovel") Long idImovel,
    									 HttpSession session, 
    									 ModelMap map){
		try {
			map.addAttribute("listaTodasParcerias", parceriaService.recuperarTodosImovelParceriaPorImovel(idImovel));		
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
    public String todosImoveisParceriasPorUsuario(@ModelAttribute("parceriaForm") ParceriaForm form,
    									 	  	  @PathVariable("idUsuario") Long idUsuario,
    									 	      HttpSession session, 
    									 	  	  ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Parceria> lista = parceriaService.recuperarTodosParceriaPorUsuario(idUsuario, user.getId());
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
	
	
	@RequestMapping(value = "/modoVisualizarParceria", method = RequestMethod.POST)
    public String modoVisualizarParceria(HttpSession session,
    							 		 @ModelAttribute("parceriaForm") ParceriaForm form, 
    							 		 ModelMap map){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getTipoLista().equals("parceriaAceita")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", parceriaService.agruparImoveisParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparImoveisParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", parceriaService.agruparUsuariosParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaParceriaAceita", parceriaService.recuperarTodasSolParceriasAceitasDistintas(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "listarImoveisParceriaAceita";		
				}
			}		
			else if ( form.getTipoLista().equals("parceriaSolRecebida")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", parceriaService.agruparImoveisParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparImoveisParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){				
					map.addAttribute("listaAgruposUsuarios", parceriaService.agruparUsuariosParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaParceriaSolRecebida", parceriaService.recuperarSolicitacoesParceriasRecebidas(user.getId(), form));			
					map.addAttribute("parceriaForm", form);			
					return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";
				}			
			}
			else if ( form.getTipoLista().equals("parceriaMinhasSol")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){
					map.addAttribute("listaAgruposImoveis", parceriaService.agruparImoveisParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparImoveisParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", parceriaService.agruparUsuariosParceria(user.getId(), form));
					map.addAttribute("parceriaForm", form);
					return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					map.addAttribute("listaParceriaMinhasSol", parceriaService.recuperarMinhasSolicitacoesParcerias(user.getId(), form));
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

		
	@RequestMapping(value="/ordenarParceria", method = RequestMethod.POST)
	public String ordenarParceria(@ModelAttribute("parceriaForm") ParceriaForm form, 
								  ModelMap map,	
								 HttpSession session){	
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			
			if ( form.getTipoLista().equals("parceriaMinhasSol")){
				map.addAttribute("listaParceriaMinhasSol", parceriaService.filtrarParceria(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaEnviada";
			}	
			else if ( form.getTipoLista().equals("parceriaSolRecebida")){
				map.addAttribute("listaParceriaSolRecebida", parceriaService.filtrarParceria(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";			
			}	
			else {
				map.addAttribute("listaParceriaAceita", parceriaService.filtrarParceria(user.getId(), form));
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
									      @ModelAttribute("parceriaForm") ParceriaForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);

			if ( form.getOpcaoVisualizacao().equals("agruparImoveis")) {			
				map.addAttribute("listaAgruposImoveis", parceriaService.ordenarAgruparParcerias(user.getId(), form));
				map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "agruparImoveisParceria";
			}	
			else {			
				map.addAttribute("listaAgruposUsuarios", parceriaService.ordenarAgruparParcerias(user.getId(), form));
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
    public String filtrarParceria(@ModelAttribute("parceriaForm") ParceriaForm form, 
    							  ModelMap map,
    							  HttpSession session){    	
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);    	
	    	if ( form.getTipoLista().equals("parceriaSolRecebida")){			
				map.addAttribute("listaParceriaSolRecebida", parceriaService.filtrarParceria(user.getId(), form));						
	    		map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";
	    	}    	
	    	else if ( form.getTipoLista().equals("parceriaMinhasSol")) {
				map.addAttribute("listaParceriaMinhasSol", parceriaService.filtrarParceria(user.getId(), form));
	    		map.addAttribute("parceriaForm", form);
				return DIR_PATH_PARCERIA + "listarImoveisParceriaEnviada";
	    	}	
	    	else if ( form.getTipoLista().equals("parceriaAceita")) {
				map.addAttribute("listaParceriaAceita", parceriaService.filtrarParceria(user.getId(), form));
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
			map.addAttribute("listaSolImovelParceria", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
			map.addAttribute("listaImovelParceriaSelecionados", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()) );
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
			parceriaService.atualizarStatusParceria(idImovelParceria, StatusImovelCompartilhadoEnum.ACEITA.getRotulo());
			map.addAttribute("listaSolImovelParceria", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
			map.addAttribute("listaImovelParceriaSelecionados", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));
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
			parceriaService.excluirParceria(idImovelParceria);
			map.addAttribute("listaSolImovelParceria", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
			map.addAttribute("listaImovelParceriaSelecionados", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()) );
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
			parceriaService.atualizarStatusParceria(idImovelParceria, "rejeitada");
			map.addAttribute("listaSolImovelParceria", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
			map.addAttribute("listaImovelParceriaSelecionados", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()) );
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	    	return DIR_PATH_PARCERIA + "analisarSolicitacoesParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  rejeitarImovelParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	   
   	
	@RequestMapping(value="/filtrarAgruparUsuariosParceria", method = RequestMethod.POST)	
	public String filtrarAgruparUsuariosParceria(@ModelAttribute("parceriaForm") ParceriaForm form, 
												 ModelMap map, 
												 HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaAgruposUsuarios", parceriaService.filtrarAgruparUsuario(user.getId(), form));
			map.addAttribute("parceriaForm", form);
			return DIR_PATH_PARCERIA + "agruparUsuariosParceria";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelCompartilhadoController -  filtrarAgruparUsuariosParceria");
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
			ParceriaForm form = new ParceriaForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoLista(tipoLista);				
			if ( tipoLista.equals("parceriaAceita")){
				map.addAttribute("parceriaForm", form);
				map.addAttribute("listaParceriaAceita", parceriaService.recuperarTodasSolParceriasAceitasDistintas(user.getId(), form));
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "parceriaAceita");
				return DIR_PATH_PARCERIA + "listarImoveisParceriaAceita";
			}
			else if ( tipoLista.equals("parceriaSolRecebida")){		
				long quantNovas = Long.parseLong(request.getSession().getAttribute(ImovelService.QUANT_NOVAS_PARCERIAS).toString());
				if ( quantNovas > 0 ){
					parceriaService.atualizarStatusSolRecebidasParceria(user.getId());				
					session.setAttribute(ImovelService.QUANT_NOVAS_PARCERIAS, 0 );			
				}					
				map.addAttribute("listaParceriaSolRecebida", parceriaService.recuperarSolicitacoesParceriasRecebidas(user.getId(), form));
				map.addAttribute("parceriaForm", form);	
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "parceriaSolRecebida");
				return DIR_PATH_PARCERIA + "listarImoveisParceriaSolicitada";
			}
			else {
				map.addAttribute("listaParceriaMinhasSol", parceriaService.recuperarMinhasSolicitacoesParcerias(user.getId(), form));
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
									 @ModelAttribute("parceriaForm") ParceriaForm form, 
									 ModelMap map){        
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			parceriaService.excluirParceria(idImovelParceria);
			map.addAttribute("listaMinhaSolImovelParceria", parceriaService.recuperarMinhasSolicitacoesParcerias(user.getId(), form));
			map.addAttribute("listaSolRecebidasImovelParceria", parceriaService.recuperarSolicitacoesParceriasRecebidas(user.getId(), form));
			map.addAttribute("listaAceitaImovelParceria", parceriaService.recuperarTodasSolParceriasAceitasDistintas(user.getId(), form));		
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

	
	@RequestMapping(value = "/confirmarExclusaoSolParceria/{idSolParceria}")	
	public String confirmarExclusaoSolParceria(@PathVariable Long idSolParceria,
												 	HttpSession session,	
												 	@ModelAttribute("parceriaSelecionadaForm") ParceriaForm form,
												 	ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			parceriaService.excluirParceria(idSolParceria);							
			map.addAttribute("listaParceriaSolRecebida", parceriaService.recuperarSolicitacoesParceriasRecebidas(user.getId(), form));		
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
												   @ModelAttribute("parceriaSelecionadaForm") ParceriaForm form,	
												   HttpSession session,	
												   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			parceriaService.excluirParceria(idEnviadaParceria);
			map.addAttribute("listaParceriaMinhasSol", parceriaService.recuperarMinhasSolicitacoesParcerias(user.getId(), form));
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
			parceriaService.excluirParceria(idSolParceria);		
			map.addAttribute("listaSolImovelParceria", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
			map.addAttribute("listaImovelParceriaSelecionados", parceriaService.recuperarUsuarioParceiroPorIdImovel(idImovel, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()) );
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
