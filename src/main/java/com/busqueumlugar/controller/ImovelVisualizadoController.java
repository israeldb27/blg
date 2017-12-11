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






import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.form.ImovelvisualizadoForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelfavoritos;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelvisualizadoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("imovelVisualizadoController")
@RequestMapping("/imovelVisualizado")
@SessionAttributes({"imovelvisualizadoForm"})
public class ImovelVisualizadoController {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelVisualizadoController.class);
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ImovelvisualizadoService imovelVisualizadoService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	private static final String DIR_PATH = "/imovel/visualizacao/";	
	
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,	
											  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
		
	@RequestMapping(value = "/desmarcarCheck")	
	public void desmarcarCheck(Long idImovelvisualizado, HttpServletResponse response, HttpSession session){
		try {
			imovelVisualizadoService.atualizarStatus(imovelVisualizadoService.recuperarImovelvisualizadoPorId(idImovelvisualizado));
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_NOVOS_VISITANTES, imovelVisualizadoService.checarQuantidadeVisitantesPorUsuarioPorStatus(user.getId(), "novo"));
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController - populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}

	
	@RequestMapping(value = "/visualizarImoveisVisiteiUsuario/{idUsuario}", method = RequestMethod.GET)
    public String visualizarImoveisVisiteiUsuario(@ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,
    									 		  @PathVariable("idUsuario") Long idUsuario,
    									 		  HttpSession session, 
    									 		  ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("imovelvisualizadoForm", form);
	        map.addAttribute("usuarioVisitante", usuarioService.recuperarUsuarioPorId(idUsuario));
	        map.addAttribute("listaTodasVisitas", imovelVisualizadoService.recuperarImoveisVisitadosPorUsuario (idUsuario, user.getId()));
	        return DIR_PATH + "visualizarImoveisVisitadosUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController -  visualizarImoveisVisiteiUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}

	@RequestMapping(value = "/modoVisualizar", method = RequestMethod.POST)
    public String modoVisualizar(HttpSession session,
    							 @ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form, 
    							 ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("imovelvisualizadoForm", form);
			
			if ( form.getTipoLista().equals("meusImoveisVisitados")){ // visitas que eu recebi
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){				
					map.addAttribute("listaAgruposImoveis", imovelVisualizadoService.agruparImoveis(user.getId(), form));
					map.addAttribute("imovelvisualizadoForm", form);
					return DIR_PATH + "agruparImoveis";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){				
					map.addAttribute("listaAgruposUsuarios", imovelVisualizadoService.agruparUsuarios(user.getId(), form));
					map.addAttribute("imovelvisualizadoForm", form);
					return DIR_PATH + "agruparUsuarios";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")) {				 
					if ( form.getTipoLista().equals("imoveisVisitados")){										 
						 map.addAttribute("imoveisVisitados", imovelVisualizadoService.recuperarImoveisVisitadorPorIdUsuario(user.getId(), form));
						 map.addAttribute("imovelvisualizadoForm", form);
						 session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "minhasVisitas");
						 return DIR_PATH + "listarMinhasVisualizacoes";				 
					 }
					else if ( form.getTipoLista().equals("meusImoveisVisitados")){
						 map.addAttribute("meusImoveisVisitados", imovelVisualizadoService.recuperarUsuariosVisitantesPorImovel(user.getId(), form));
						 map.addAttribute("imovelvisualizadoForm", form);			 
						 session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "meusImoveisVisitados");
						 return DIR_PATH + "listarVisualizacoesRecebidas";			 					 
					 }					
				}
			}		
			else {  // imoveisvisitados - imoveis que eu visite
				if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){				
					map.addAttribute("listaAgruposUsuarios", imovelVisualizadoService.agruparUsuarios(user.getId(), form));
					map.addAttribute("imovelvisualizadoForm", form);
					return DIR_PATH + "agruparUsuarios";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")) {
					if ( form.getTipoLista().equals("imoveisVisitados")){					 
						 map.addAttribute("imoveisVisitados", imovelVisualizadoService.recuperarImoveisVisitadorPorIdUsuario(user.getId(), form));
						 map.addAttribute("imovelvisualizadoForm", form);
						 return DIR_PATH + "listarMinhasVisualizacoes";
					 }
					else if ( form.getTipoLista().equals("meusImoveisVisitados")){
						 map.addAttribute("meusImoveisVisitados", imovelVisualizadoService.recuperarUsuariosVisitantesPorImovel(user.getId(), form));
						 map.addAttribute("imovelvisualizadoForm", form);					 
						 return DIR_PATH + "listarVisualizacoesRecebidas";
					 }			
				}						 
			}
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController -  modoVisualizar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/listarUsuariosVisitantes/{tipoLista}", method = RequestMethod.GET)
	public String listarUsuariosVisitantes(@PathVariable String tipoLista,
									   	   HttpSession session,
									   	   HttpServletRequest request,
									       ModelMap map){
		 
			try {
				UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		 		 
				 ImovelvisualizadoForm form = new ImovelvisualizadoForm();
				 form.setTipoLista(tipoLista);
				 form.setListaEstados(estadosService.listarTodosEstadosSelect());		 
				 if ( tipoLista.equals("meusImoveisVisitados")){
					 long quantNovas = Long.parseLong(request.getSession().getAttribute("quantNovosVisitantes").toString());
					 if ( quantNovas > 0 ){
						 imovelVisualizadoService.atualizarStatusLeituraMeusImoveisVisitados(user.getId());
						 session.setAttribute(ImovelService.QUANT_NOVOS_VISITANTES,  0);
					 }			 
					 map.addAttribute("meusImoveisVisitados", imovelVisualizadoService.recuperarUsuariosVisitantesPorImovel(user.getId(), form));
					 map.addAttribute("imovelvisualizadoForm", form);			 
					 session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "meusImoveisVisitados");
					 return DIR_PATH + "listarVisualizacoesRecebidas";
				 }
				 else if ( tipoLista.equals("imoveisVisitados")){			 
					 map.addAttribute("imoveisVisitados", imovelVisualizadoService.recuperarImoveisVisitadorPorIdUsuario(user.getId(), form));
					 map.addAttribute("imovelvisualizadoForm", form);
					 session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "minhasVisitas");
					 return DIR_PATH + "listarMinhasVisualizacoes";
				 }
				 return null;
			} catch (Exception e) {
				log.error("Erro metodo - ImovelVisualizadoController - listarUsuariosVisitantes");
				log.error("Mensagem Erro: " + e.getMessage());
				map.addAttribute("mensagemErroGeral", "S");
				return ImovelService.PATH_ERRO_GERAL;
			}
	}
	
	@RequestMapping(value = "/paginarFiltrar", method = RequestMethod.POST)
	public String paginarFiltrar(HttpSession session,
								 @ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,
								 HttpServletRequest request,
								 ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 if ( form.getTipoLista().equals("meusImoveisVisitados")){
				 map.addAttribute("meusImoveisVisitados", imovelVisualizadoService.recuperarUsuariosVisitantesPorImovel(user.getId(), form));
				 map.addAttribute("imovelvisualizadoForm", form);
				 return DIR_PATH + "listarVisualizacoesRecebidas";
			 }
			 else if ( form.getTipoLista().equals("imoveisVisitados")){
				 map.addAttribute("imoveisVisitados", imovelVisualizadoService.recuperarImoveisVisitadorPorIdUsuario(user.getId(), form));
				 map.addAttribute("imovelvisualizadoForm", form);
				 return DIR_PATH + "listarMinhasVisualizacoes";
			 }
			 return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController -  paginarFiltrar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		 
	}
	
	
	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String goUsuariosVisitantes(@ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,
									   HttpSession session, 
									   ModelMap map){
		
		try {
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 form.setOpcaoOrdenacao("");
			 if ( form.getTipoLista().equals("meusImoveisVisitados")){
				 map.addAttribute("meusImoveisVisitados", imovelVisualizadoService.filtrarUsuariosVisitantes(user.getId(), form));
				 map.addAttribute("imovelvisualizadoForm", form);
				 return DIR_PATH + "listarVisualizacoesRecebidas";
			 }
			 else if ( form.getTipoLista().equals("imoveisVisitados")){			 
				 map.addAttribute("imoveisVisitados", imovelVisualizadoService.filtrarImoveisVisitados(user.getId(), form));
				 map.addAttribute("imovelvisualizadoForm", form);
				 return DIR_PATH + "listarMinhasVisualizacoes";
			 }
			 return null;	
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController -  goUsuariosVisitantes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	 
	}
	
	@RequestMapping(value = "/filtrarAgruparUsuarios", method = RequestMethod.POST)
	public String filtrarAgruparUsuarios(@ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,
									     HttpSession session, 
									     ModelMap map){
		try {
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 map.addAttribute("listaAgruposUsuarios", imovelVisualizadoService.filtrarAgruparUsuarios(user.getId(), form));
			 map.addAttribute("imovelvisualizadoForm", form);		
			 return DIR_PATH + "agruparUsuarios";	
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController -  filtrarAgruparUsuarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}			 
	}
	
	@RequestMapping(value = "/filtrarAgruparImoveis", method = RequestMethod.POST)
	public String filtrarAgruparImoveis(@ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,
									    HttpSession session, 
									    ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		 
			 map.addAttribute("listaAgruposImoveis", imovelVisualizadoService.filtrarAgruparImoveis(user.getId(), form));
			 map.addAttribute("imovelvisualizadoForm", form);		
			 return DIR_PATH + "agruparImoveis";	
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController -  filtrarAgruparImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 			 
	}
	
	@RequestMapping(value="/ordenar", method = RequestMethod.POST)
	public String ordenar(@ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,
						  HttpSession session,
						  ModelMap map){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getTipoLista().equals("meusImoveisVisitados")) {
				 map.addAttribute("meusImoveisVisitados", imovelVisualizadoService.ordenarImoveis(user.getId(), form, "meusImoveisVisitados"));
				 map.addAttribute("imovelvisualizadoForm", form);
				return DIR_PATH + "listarVisualizacoesRecebidas";			
			}	
			else {  			 
				 map.addAttribute("imoveisVisitados", imovelVisualizadoService.ordenarImoveis(user.getId(), form, "imoveisVisitados"));			 
				 map.addAttribute("imovelvisualizadoForm", form);			
				 return DIR_PATH + "listarMinhasVisualizacoes";			
			}	
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController - ordenar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/ordenarAgrupar", method = RequestMethod.POST)
	public String ordenarAgrupar(HttpSession session,
								ModelMap map,
								@ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm form){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("imovelvisualizadoForm", form);
			if ( form.getOpcaoVisualizacao().equals("agruparImoveis")) {			
				map.addAttribute("listaAgruposImoveis", imovelVisualizadoService.ordenarAgruparImoveisVisitados(user.getId(), form));
				return DIR_PATH + "agruparImoveis";
			}	
			else {			
				map.addAttribute("listaAgruposUsuarios", imovelVisualizadoService.ordenarAgruparImoveisVisitados(user.getId(), form));
				return DIR_PATH + "agruparUsuarios";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController - ordenarAgrupar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	@RequestMapping(value = "/visualizarTodosUsuariosVisitantes/{idImovel}", method = RequestMethod.GET)
	public String visualizarTodosUsuariosVisitantes(@PathVariable Long idImovel, 
													ModelMap map,
													@ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form){
		try {
			List<Imovelvisualizado> lista = imovelVisualizadoService.recuperarUsuariosVisitantesPorIdImovel(idImovel);
			map.addAttribute("listaTodasVisitasImovel", lista);
			map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
			map.addAttribute("imovelvisualizadoForm", form);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			return DIR_PATH + "todasVisitasImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController - visualizarTodosUsuariosVisitantes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/listarTodosUsuariosVisitantes/{idImovel}")
	public String listarTodosUsuariosVisitantes(@PathVariable Long idImovel, 
													ModelMap map){
		try {
			List<Imovelvisualizado> lista = imovelVisualizadoService.recuperarUsuariosVisitantesPorIdImovel(idImovel);
			map.addAttribute("listaTodasVisitasImovel", lista);
			map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			return DIR_PATH + "todasVisitasImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController - listarTodosUsuariosVisitantes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/todosUsuariosVisitantesCompartilhado/{idImovel}", method = RequestMethod.GET)
	public String visualizarTodosUsuariosVisitantesCompartilhado(@PathVariable Long idImovel, 
																 ModelMap map){
		try {
			List<Imovelvisualizado> lista = imovelVisualizadoService.recuperarUsuariosVisitantesPorIdImovel(idImovel);
			map.addAttribute("listaTodasVisitasImovel", lista);
			map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			return DIR_PATH + "todasVisitasImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController - visualizarTodosUsuariosVisitantesCompartilhado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/todasVisitasUsuario/{idUsuario}", method = RequestMethod.GET)
    public String todasVisitasUsuario(@ModelAttribute("imovelvisualizadoForm") ImovelvisualizadoForm  form,
    								  @PathVariable("idUsuario") Long idUsuario,
    								  HttpSession session, 
    								  ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Imovelvisualizado> lista = null;
			if ( form.getTipoLista().equals("imoveisVisitados"))
				lista = imovelVisualizadoService.recuperarImoveisVisitadosPorUsuario(idUsuario, user.getId());
			else
				lista = imovelVisualizadoService.recuperarImoveisVisitadosPorUsuario(user.getId(), idUsuario);
			map.addAttribute("listaTodasVisitasUsuario", lista);
			map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));
			map.addAttribute("imovelvisualizadoForm", form);		
	        map.addAttribute("usuarioVisitante", usuarioService.recuperarUsuarioPorId(idUsuario));
	        return DIR_PATH + "todasVisitasUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelVisualizadoController - todasVisitasUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
}
