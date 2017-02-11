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

import com.busqueumlugar.form.ImovelfavoritosForm;

import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelfavoritos;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("imovelFavoritosController")
@RequestMapping("/imovelFavoritos")
@SessionAttributes({"imovelFavoritoForm"})
public class ImovelFavoritosController {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelFavoritosController.class);
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ImovelFavoritosService imovelFavoritosService;
	
	@Autowired
	private EstadosService estadosService;	
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private static final String DIR_PATH = "/imovel/listaFavoritos/";
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelfavoritosForm -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form,	
											  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelfavoritosForm -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	@RequestMapping(value = "/desmarcarCheck")	
	public void desmarcarCheck(Long idImovelfavoritos,
							   HttpServletResponse response, 
							   HttpSession session){
		try {
			imovelFavoritosService.atualizarStatusUsuarioInteressado(idImovelfavoritos);
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_NOVOS_USUARIOS_INTERESSADOS, imovelFavoritosService.checarExisteNovoUsuarioInteressado(user.getId()));
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  desmarcarCheck");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}    	  
	
	
	@RequestMapping(value = "/visualizarTodosFavoritosImovel/{idImovel}", method = RequestMethod.GET)
	public String visualizarTodosComentariosUsuario(@PathVariable Long idImovel, 
													@ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form,
												    HttpSession session,
												    ModelMap map){
		
		try {
			map.addAttribute("imovelFavoritoForm", form);
			List<Imovelfavoritos> lista = imovelFavoritosService.recuperarUsuariosInteressadosPorIdImovel(idImovel);
			map.addAttribute("listaTodosUsuariosInteresados", lista);
			map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));		   
			return DIR_PATH + "todosUsuariosListaInteresse";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  visualizarTodosComentariosUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/visualizarTodosFavoritosImovelCompartilhado/{idImovel}", method = RequestMethod.GET)
	public String visualizarTodosComentariosUsuarioCompartilhado(@PathVariable Long idImovel,
												    			 HttpSession session,
												    			 ModelMap map){
		
		try {
			ImovelfavoritosForm form = new ImovelfavoritosForm();		
			List<Imovelfavoritos> lista = imovelFavoritosService.recuperarUsuariosInteressadosPorIdImovel(idImovel);
			map.addAttribute("listaTodosUsuariosInteresados", lista);		
			map.addAttribute("imovelFavoritoForm", form);		
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
			return DIR_PATH + "todosUsuariosListaInteresse";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  visualizarTodosComentariosUsuarioCompartilhado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/visualizarTodosFavoritosUsuario/{idUsuario}", method = RequestMethod.GET)
	public String visualizarTodosFavoritosUsuario(@PathVariable Long idUsuario,												   
												   @ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form,
												   HttpSession session,
												   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			List<Imovel> lista = null;
			if ( form.getTipoLista().equals("usuariosInteressados")) {			
				lista = imovelFavoritosService.recuperarImoveisInteressadosPorIdUsuarioPorDonoImovel(idUsuario, user.getId());
				map.addAttribute("listaTodoImoveisFavoritosUsuario", lista);
				map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));
			}	
			else {
				lista = imovelFavoritosService.recuperarImoveisInteressadosPorIdUsuarioPorDonoImovel(user.getId(), idUsuario);
				map.addAttribute("listaTodoImoveisFavoritosUsuario", lista);
				map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));
			}	
				
			map.addAttribute("imovelFavoritoForm", form);
			map.addAttribute("usuarioInteresse", usuarioService.recuperarUsuarioPorId(idUsuario));		
			return DIR_PATH + "todosImoveisListaInteressePorUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  visualizarTodosFavoritosUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	@RequestMapping(value = "/modoVisualizar", method = RequestMethod.POST)
    public String modoVisualizar(HttpSession session,
    							 @ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form, 
    							 ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("imovelFavoritoForm", form);		
			if ( form.getTipoLista().equals("usuariosInteressados")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){				
					map.addAttribute("listaAgruposImoveis", imovelFavoritosService.agruparImoveis(user.getId(), form));
					map.addAttribute("imovelFavoritoForm", form);
					return DIR_PATH + "agruparImoveisListaFavoritos";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){
					map.addAttribute("listaAgruposUsuarios", imovelFavoritosService.agruparUsuarios(user.getId(), form));
					map.addAttribute("imovelFavoritoForm", form);
					return DIR_PATH + "agruparUsuariosListaFavoritos";
				}			
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					if ( form.getTipoLista().equals("meuInteresse")){
						map.addAttribute("listaMeusInteresse", imovelFavoritosService.listarImoveisPorUsuario(user.getId(), form));
						map.addAttribute("imovelFavoritoForm", form);
						return DIR_PATH + "listarImovelFavoritosMeusInteresse";
					}
					else {
						map.addAttribute("listaUsuarioInteressado", imovelFavoritosService.listarUsuariosInteressadosMeusImoveis(user.getId(), form));
						map.addAttribute("imovelFavoritoForm", form);
						return DIR_PATH + "listarImovelFavoritosUsuarioInteressado";
					}	
				}
				else {
					map.addAttribute("listaAgruparUsuariosListaInteresseUsuariosInteressados", null);
					return DIR_PATH + "agruparUsuariosListaFavoritos";
				}
			}		
			else { // imoveis meu interesse
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){				
					map.addAttribute("listaAgruposImoveis", imovelFavoritosService.agruparImoveis(user.getId(), form));
					map.addAttribute("imovelFavoritoForm", form);
					return DIR_PATH + "agruparImoveisListaFavoritos";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){
					if ( form.getTipoLista().equals("meuInteresse")){
						map.addAttribute("listaMeusInteresse", imovelFavoritosService.listarImoveisPorUsuario(user.getId(), form));
						map.addAttribute("imovelFavoritoForm", form);
						return DIR_PATH + "listarImovelFavoritosMeusInteresse";
					}
					else {					
						map.addAttribute("listaUsuarioInteressado", imovelFavoritosService.listarUsuariosInteressadosMeusImoveis(user.getId(), form));
						map.addAttribute("imovelFavoritoForm", form);
						return DIR_PATH + "listarImovelFavoritosUsuarioInteressado";
					}
				}
				else {
					map.addAttribute("listaAgruposUsuarios", imovelFavoritosService.agruparUsuarios(user.getId(), form));
					map.addAttribute("imovelFavoritoForm", form);
					return DIR_PATH + "agruparUsuariosListaFavoritos";
				}				
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  modoVisualizar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value="/ordenar", method = RequestMethod.POST)
	public String ordenar(HttpSession session, 
						  @ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form, 
						  ModelMap map){	
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			if ( form.getTipoLista().equals("usuariosInteressados")) {			
				map.addAttribute("listaUsuarioInteressado", imovelFavoritosService.ordenarImoveisFavoritos(user.getId(), form) );
				map.addAttribute("imovelFavoritoForm", form);
				return DIR_PATH + "listarImovelFavoritosUsuarioInteressado";
			}	
			else {			
				map.addAttribute("listaMeusInteresse", imovelFavoritosService.ordenarImoveisFavoritos(user.getId(), form));
				map.addAttribute("imovelFavoritoForm", form);
				return DIR_PATH + "listarImovelFavoritosMeusInteresse";			
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  ordenar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}				
	}
	
	@RequestMapping(value="/ordenarAgrupar", method = RequestMethod.POST)
	public String ordenarAgrupar(HttpSession session,
								ModelMap map,
								@ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getOpcaoVisualizacao().equals("agruparImoveis")) {			
				map.addAttribute("listaAgruposImoveis", imovelFavoritosService.ordenarAgruparImoveisFavoritos(user.getId(), form));
				map.addAttribute("imovelFavoritoForm", form);
				return DIR_PATH + "agruparImoveisListaFavoritos";
			}	
			else {
				map.addAttribute("listaAgruposUsuarios", imovelFavoritosService.ordenarAgruparImoveisFavoritos(user.getId(), form));
				map.addAttribute("imovelFavoritoForm", form);
				return DIR_PATH + "agruparUsuariosListaFavoritos";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  ordenarAgrupar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	
	@RequestMapping(value="/filtrar", method = RequestMethod.POST)	
	public String filtrar(@ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form, 
						  ModelMap map, 
						  HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			if ( form.getTipoLista().equals("meuInteresse")){			
				map.addAttribute("listaMeusInteresse", imovelFavoritosService.filtrarImoveisInteresse(user.getId(), form ));
				map.addAttribute("imovelFavoritoForm", form);
				return DIR_PATH + "listarImovelFavoritosMeusInteresse";
			}	
			else if ( form.getTipoLista().equals("usuariosInteressados")) {			
				map.addAttribute("listaUsuarioInteressado", imovelFavoritosService.filtrarUsuariosInteressado(user.getId(), form ));
				map.addAttribute("imovelFavoritoForm", form);
				return DIR_PATH + "listarImovelFavoritosUsuarioInteressado";
			}			
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  filtrar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}
	
	@RequestMapping(value="/filtrarAgruparUsuarios", method = RequestMethod.POST)	
	public String filtrarAgruparUsuarios(@ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form, 
						  				 ModelMap map, 
						  				 HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaAgruposUsuarios", imovelFavoritosService.filtrarAgruparUsuario(user.getId(), form));
			map.addAttribute("imovelFavoritoForm", form);		
			return DIR_PATH + "agruparUsuariosListaFavoritos";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  filtrarAgruparUsuarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/filtrarAgruparImoveis", method = RequestMethod.POST)	
	public String filtrarAgruparImoveis(@ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form, 
						  				ModelMap map, 
						  				HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaAgruposImoveis", imovelFavoritosService.filtrarAgruparImoveis(user.getId(), form));
			map.addAttribute("imovelFavoritoForm", form);		
			return DIR_PATH + "agruparImoveisListaFavoritos";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  filtrarAgruparImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	
	@RequestMapping(value="/adicionarFavoritos/{idImovel}")
	@ResponseBody
	public void adicionarFavoritos(@PathVariable Long idImovel, 
								   HttpServletResponse response, 
								   HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelFavoritosService.adicionarFavoritosPorUsuario(user.getId(), idImovel);	
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  adicionarFavoritos");
			log.error("Mensagem Erro: " + e.getMessage());
		}	
	}
	
	@RequestMapping(value="/retirarFavoritos/{idImovel}")
	@ResponseBody
	public void retirarFavoritos(@PathVariable Long idImovel, 
								 HttpServletResponse response, 
								 HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelFavoritosService.retirarFavoritosPorUsuario(user.getId(), idImovel);	
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  retirarFavoritos");
			log.error("Mensagem Erro: " + e.getMessage());
		}		
	}
	
	@RequestMapping(value = "/confirmarExclusaoFavorito/{idImovelfavoritos}")	
	public String confirmarExclusaoFavorito(@PathVariable Long idImovelfavoritos,
											@ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form,
											HttpSession session,	
									      	ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelFavoritosService.retirarFavoritosPorIdImovelfavoritos(idImovelfavoritos);		
			List<Imovelfavoritos> listaTodos = imovelFavoritosService.listarImoveisPorUsuario(user.getId(), form);
			map.addAttribute("quantTotalInteresse", AppUtil.recuperarQuantidadeLista(listaTodos));
			map.addAttribute("listaMeusInteresse", listaTodos);		
			return DIR_PATH + "listarImovelFavoritosMeusInteresse";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  confirmarExclusaoFavorito");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}			
	}


	
	@RequestMapping(value = "/listarInteresse/{tipoLista}", method = RequestMethod.GET)
	public String listarInteresse(@PathVariable String tipoLista, 
								  HttpSession session, 
								  HttpServletRequest request,  	
								  ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ImovelfavoritosForm form = new ImovelfavoritosForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoLista(tipoLista);	
			form.setOpcaoPaginacao("");		
			if ( tipoLista.equals("meuInteresse")){
				map.addAttribute("listaMeusInteresse", imovelFavoritosService.listarImoveisPorUsuario(user.getId(), form));
				map.addAttribute("imovelFavoritoForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarImovelFavoritosMeusInteresse");
				return DIR_PATH + "listarImovelFavoritosMeusInteresse";
			}
			else {			
				long quantNovas = Long.valueOf(request.getSession().getAttribute(ImovelService.QUANT_NOVOS_USUARIOS_INTERESSADOS).toString());
				if ( quantNovas > 0 ){
					imovelFavoritosService.atualizarStatusLeitura(user.getId());
					session.setAttribute(ImovelService.QUANT_NOVOS_USUARIOS_INTERESSADOS, 0);
				}			
				map.addAttribute("listaUsuarioInteressado", imovelFavoritosService.listarUsuariosInteressadosMeusImoveis(user.getId(), form));
				map.addAttribute("imovelFavoritoForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarImovelFavoritosUsuarioInteressado");
				return DIR_PATH + "listarImovelFavoritosUsuarioInteressado";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelFavoritosController -  listarInteresse");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	/*@RequestMapping(value = "/excluirImovelInteresse/{idImovel}", method = RequestMethod.GET)
	public String excluirImovelInteresse(@PathVariable Long idImovel, 
										 @ModelAttribute("imovelFavoritoForm") ImovelfavoritosForm form,
										 HttpSession session,
										 ModelMap map){
		
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
		session.setAttribute(ParametrosUtils.TIPO_VISUALIZACAO, TIPO_VISUALIZACAO);
		imovelFavoritosService.excluirImovelFavoritos(idImovel, user.getId());
		form.setListaMeusInteresse(imovelFavoritosService.listarImoveisPorUsuario(user.getId()));
		form.setListaUsuariosInteresados(imovelFavoritosService.listarUsuariosInteressadosMeusImoveis(user.getId()));
		map.addAttribute("listaImoveisMeuInteresse", form.getListaMeusInteresse());
		map.addAttribute("listaUsuariosInteresados", form.getListaUsuariosInteresados());
		map.addAttribute("imovelFavoritoForm", form);		
		return DIR_PATH + "listarImovelFavoritos";
	}*/
	

}
