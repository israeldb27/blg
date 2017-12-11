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

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelcomentarioForm;
import com.busqueumlugar.form.ImovelPropostasForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelcomentario;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelcomentarioService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("imovelComentarioController")
@RequestMapping("/imovelComentario")
@SessionAttributes({"imovelComentarioForm"})
public class ImovelcomentarioController {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelcomentarioController.class);
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private ImovelcomentarioService imovelComentarioService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	private static final String DIR_PATH = "/imovel/comentarios/";	
	
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("imovelComentarioForm") ImovelcomentarioForm form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("imovelComentarioForm") ImovelcomentarioForm form,	
											  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	
	@RequestMapping(value = "/desmarcarCheck")	
	public void desmarcarCheck(Long idImovelcomentario, HttpServletResponse response, HttpSession session){
		 
		imovelComentarioService.atualizarStatusImovelComentario(imovelComentarioService.recuperarImovelcomentarioPorId(idImovelcomentario));
		UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		session.setAttribute(ImovelService.QUANT_NOVOS_IMOVEIS_COMENTARIOS, imovelComentarioService.checaQuantidadeNovoComentario(user.getId()));
		response.setStatus(200);
	}
	
	
	@RequestMapping(value="/ordenar", method = RequestMethod.POST)
	public String ordenarComentarios(@ModelAttribute("imovelComentarioForm") ImovelcomentarioForm form,
									 HttpSession session,
									 ModelMap map){
		
		try {
			map.addAttribute("imovelComentarioForm", form);
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getTipoLista().equals("comentariosSobreMeusImoveis")) {
				List<Imovelcomentario> lista = imovelComentarioService.ordenarImoveisComentarios(user.getId(), form.getOpcaoOrdenacao(), form.getTipoLista());			
				map.addAttribute("listaComentariosRecebidos", lista);
				map.addAttribute("quantTotalComentarios", AppUtil.recuperarQuantidadeLista(lista));			
				map.addAttribute("imovelComentarioForm", form);
				return DIR_PATH + "listarMeusComentariosRecebidos";
			}	
			else
				return DIR_PATH + "listarMeusComentariosEnviados";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  ordenarComentarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}	
	
	@RequestMapping(value = "/visualizarTodosComentariosImovel/{idImovel}")
	public String visualizarTodosComentariosImovel(@PathVariable Long idImovel, 
												   HttpSession session, 
												   @ModelAttribute("imovelComentarioForm") ImovelcomentarioForm form,
												   ModelMap map){		
		try {
			List<Imovelcomentario> lista = imovelComentarioService.listarComentarios(idImovel, null);
			map.addAttribute("listaTodosComentarios", lista);
			map.addAttribute("quantTotalComentarios", AppUtil.recuperarQuantidadeLista(lista));
	        map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));        
	        map.addAttribute("imovelComentarioForm", form);
			return DIR_PATH + "visualizarComentariosImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  visualizarTodosComentariosImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/listarTodosComentariosImovel/{idImovel}")
	public String listarTodosComentariosImovel(@PathVariable Long idImovel, 
											   HttpSession session,
											   ModelMap map){	
		try {
			List<Imovelcomentario> lista = imovelComentarioService.listarComentarios(idImovel, null);
			map.addAttribute("listaTodosComentarios", lista);
			map.addAttribute("quantTotalComentarios", AppUtil.recuperarQuantidadeLista(lista));
	        map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));        
			return DIR_PATH + "visualizarComentariosImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  listarTodosComentariosImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/visualizarTodosComentariosImovelCompartilhado/{idImovel}")
	public String visualizarTodosComentariosImovelCompartilhado(@PathVariable Long idImovel, 
												   				HttpSession session,
												   				ModelMap map){
		
		try {			
			List<Imovelcomentario> lista = imovelComentarioService.listarComentarios(idImovel, null);
			map.addAttribute("listaTodosComentarios", lista);
			map.addAttribute("quantTotalComentarios", AppUtil.recuperarQuantidadeLista(lista));		
	        map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));  
			return DIR_PATH + "visualizarComentariosImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  visualizarTodosComentariosImovelCompartilhado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/visualizarTodosComentariosUsuario/{idUsuario}", method = RequestMethod.GET)
	public String visualizarTodosComentariosUsuario(@PathVariable Long idUsuario, 
												    @ModelAttribute("imovelComentarioForm") ImovelcomentarioForm form,
												    HttpSession session,
												    ModelMap map){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
	        
			if ( form.getTipoLista().equals("comentariosSobreMeusImoveis"))
				map.addAttribute("listaTodosComentarios", imovelComentarioService.listarComentariosSobreMeusImoveisPorusuario(idUsuario, user.getId() )); 
			else
				map.addAttribute("listaTodosComentarios", imovelComentarioService.listarComentariosSobreMeusImoveisPorusuario(user.getId(), idUsuario ));
			
	        map.addAttribute("usuarioComentario", usuarioService.recuperarUsuarioPorId(idUsuario));        
	        map.addAttribute("imovelComentarioForm", form);
			return DIR_PATH + "visualizarComentariosUsuarios";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  visualizarTodosComentariosUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/listarMeusComentarios/{tipoLista}", method = RequestMethod.GET)
	public String goListarMeusComentarios(@PathVariable String tipoLista,
										  HttpSession session, 
										  HttpServletRequest request,
										  ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ImovelcomentarioForm form = new ImovelcomentarioForm();
			form.setTipoLista(tipoLista);
			form.setListaEstados(estadosService.listarTodosEstadosSelect());		
			
			if ( tipoLista.equals("comentariosSobreMeusImoveis")){				
				long quantNovas = Long.parseLong(request.getSession().getAttribute(ImovelService.QUANT_NOVOS_IMOVEIS_COMENTARIOS).toString());
				if (quantNovas > 0){
					imovelComentarioService.atualizarStatusComentariosMeusImoveis(user.getId());
					session.setAttribute(ImovelService.QUANT_NOVOS_IMOVEIS_COMENTARIOS, 0);
				}
				
				map.addAttribute("listaComentariosRecebidos", imovelComentarioService.listarComentariosSobreMeuImovelOutros(form, user.getId()));			
				map.addAttribute("imovelComentarioForm", form);
				 session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "comentariosSobreMeusImoveis");
				return DIR_PATH + "listarMeusComentariosRecebidos";
			}
			else if ( tipoLista.equals("meusComentarios"))
				return DIR_PATH + "listarMeusComentariosEnviados";
			
			
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  goListarMeusComentarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
	public String filtrar(HttpSession session, 
						  @ModelAttribute("imovelComentarioForm") ImovelcomentarioForm form,
						  ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);				
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			
			if ( form.getTipoLista().equals("comentariosSobreMeusImoveis")){		
				List<Imovelcomentario> lista = imovelComentarioService.filtrarComentariosSobreMeusImoveis(user.getId(), form);
				map.addAttribute("listaComentariosRecebidos", lista);
				map.addAttribute("quantTotalComentarios", AppUtil.recuperarQuantidadeLista(lista));			
				map.addAttribute("imovelComentarioForm", form);
				return DIR_PATH + "listarMeusComentariosRecebidos";
			}
			else if ( form.getTipoLista().equals("meusComentarios")){
				/*form.setListaComentariosEnviados(imovelComentarioService.filtrarrMeusComentarios (user.getId(), form));
				map.addAttribute("imovelComentarioForm", form);*/
				return DIR_PATH + "listarMeusComentariosEnviados";
			}			
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  filtrar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	
	@RequestMapping(value = "/adicionarComentario/{idImovel}/{idUsuario}", method = RequestMethod.POST)	
	public String adicionarComentario(@PathVariable Long idImovel, 
									  @PathVariable Long idUsuario, 
									  ModelMap map, 
									  @ModelAttribute("imovelComentarioForm") ImovelcomentarioForm form){
		
		try {
			imovelComentarioService.cadastrarComentario(idImovel, idUsuario, form.getNovoComentario());
			List<Imovelcomentario> lista = imovelComentarioService.listarComentarios(idImovel, null);
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
			map.addAttribute("listaTodosComentarios", lista);
			return DIR_PATH + "visualizarComentariosImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelcomentarioController -  adicionarComentario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
}
