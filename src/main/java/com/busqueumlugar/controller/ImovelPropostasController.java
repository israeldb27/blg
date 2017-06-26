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

import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.ImovelPropostasForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelPropostasService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;


@Controller("imovelPropostasController")
@RequestMapping("/imovelPropostas")
@SessionAttributes({"imovelPropostaForm"})
public class ImovelPropostasController {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelPropostasController.class);
	
	@Autowired
	private  ImovelPropostasService imovelPropostasservice;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ImovelFavoritosService imovelFavoritosService;
	
	@Autowired
	private EstadosService estadosService;	
	
	private static final String DIR_PATH = "/imovel/propostas/";	
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,	
											  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	
	@RequestMapping(value = "/desmarcarCheck")	
	public void desmarcarCheck(Long idImovelPropostas, HttpServletResponse response, HttpSession session){		
		
		try {
			imovelPropostasservice.atualizarStatusImovelProposta(imovelPropostasservice.recuperarImovelImovelPropostasPorId(idImovelPropostas));
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ImovelService.QUANT_IMOVEIS_PropostaS, imovelPropostasservice.checarQuantidadesPropostasRecebidasPorUsuarioPorStatus(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController -  desmarcarCheck");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}	
	
	@RequestMapping(value = "/modoVisualizar", method = RequestMethod.POST)
    public String modoVisualizar(HttpSession session,
    							 @ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form, 
    							 ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			
			if ( form.getTipoLista().equals("propostasRecebidas")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){				
					map.addAttribute("listaAgruposImoveis", imovelPropostasservice.agruparImoveis(user.getId(), form));
					map.addAttribute("imovelPropostaForm", form);
					return DIR_PATH + "agruparImoveisPropostas";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){				
					map.addAttribute("listaAgruposUsuarios", imovelPropostasservice.agruparUsuarios(user.getId(), form));
					map.addAttribute("imovelPropostaForm", form);
					return DIR_PATH + "agruparUsuariosPropostas";
				}
				else if ( form.getOpcaoVisualizacao().equals("todos")){					
					if ( form.getTipoLista().equals("propostasRecebidas")){					
						map.addAttribute("listaPropostasRecebidas", imovelPropostasservice.recuperarPropostasRecebidasPorUsuario(user.getId(), form));
						map.addAttribute("imovelPropostaForm", form);
				    	return DIR_PATH + "listarPropostasRecebidas";
					}
					else if ( form.getTipoLista().equals("propostasLancadas")){					
						map.addAttribute("listaPropostasLancadas", imovelPropostasservice.recuperarPropostasLancadasPorUsuario(user.getId(), form));
						map.addAttribute("imovelPropostaForm", form);
				    	return DIR_PATH + "listarMinhasImoveisPropostas";
					}				
				}
			}		
			else if ( form.getTipoLista().equals("propostasLancadas")){
				if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){	
					map.addAttribute("listaAgruposImoveis", imovelPropostasservice.agruparImoveis(user.getId(), form));
					map.addAttribute("imovelPropostaForm", form);
					return DIR_PATH + "agruparImoveisPropostas";
				}
				else if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){				
					map.addAttribute("listaAgruposUsuarios", imovelPropostasservice.agruparUsuarios(user.getId(), form));
					map.addAttribute("imovelPropostaForm", form);
					return DIR_PATH + "agruparUsuariosPropostas";
				} 
				else if ( form.getOpcaoVisualizacao().equals("todos")){				
					if ( form.getTipoLista().equals("propostasRecebidas")){					
						map.addAttribute("listaPropostasRecebidas", imovelPropostasservice.recuperarPropostasRecebidasPorUsuario(user.getId(), form));
						map.addAttribute("imovelPropostaForm", form);
				    	return DIR_PATH + "listarPropostasRecebidas";
					}
					else if ( form.getTipoLista().equals("propostasLancadas")){
						map.addAttribute("listaPropostasLancadas", imovelPropostasservice.recuperarPropostasLancadasPorUsuario(user.getId(), form));
						map.addAttribute("imovelPropostaForm", form);
				    	return DIR_PATH + "listarMinhasImoveisPropostas";
					}	
				}				
			}		
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - modoVisualizar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	

	@RequestMapping(value = "/filtrar", method = RequestMethod.POST)
    public String filtrar(HttpSession session,
    					  @ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form, 
    					  ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			form.setOpcaoOrdenacao("");
			if ( form.getTipoLista().equals("propostasRecebidas")) {			
				map.addAttribute("listaPropostasRecebidas", imovelPropostasservice.filterPropostasRecebidas(user.getId(), form));
				map.addAttribute("imovelPropostaForm", form);
				return DIR_PATH + "listarPropostasRecebidas";
			}
			else if ( form.getTipoLista().equals("propostasLancadas")) {
				map.addAttribute("listaPropostasLancadas", imovelPropostasservice.filterPropostasEnviadas(user.getId(), form));
				map.addAttribute("imovelPropostaForm", form);
				return DIR_PATH + "listarMinhasImoveisPropostas";
			}	
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - filtrar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/filtrarAgruparUsuarios", method = RequestMethod.POST)
	public String filtrarAgruparUsuarios(@ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,
									     HttpSession session, 
									     ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 map.addAttribute("listaAgruposUsuarios", imovelPropostasservice.filtrarAgruparUsuarios(user.getId(), form));
			 map.addAttribute("imovelPropostaForm", form);	
			 return DIR_PATH + "agruparUsuariosPropostas";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - filtrarAgruparUsuarios");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		 
	}
	
	@RequestMapping(value="/filtrarAgruparImoveis", method = RequestMethod.POST)	
	public String filtrarAgruparImoveis(@ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form, 
						  				ModelMap map, 
						  				HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaAgruposImoveis", imovelPropostasservice.filtrarAgruparImoveis(user.getId(), form));
			map.addAttribute("imovelPropostaForm", form);		
			return DIR_PATH + "agruparImoveisPropostas";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - filtrarAgruparImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}	
	
	
	@RequestMapping(value="/ordenar", method = RequestMethod.POST)
	public String ordenar(HttpSession session, 
						  @ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form, 
						  ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);				
			if ( form.getTipoLista().equals("propostasRecebidas")) {			
				map.addAttribute("listaPropostasRecebidas", imovelPropostasservice.ordenarImoveisPropostas(user.getId(), form));
				map.addAttribute("imovelPropostaForm", form);
				return DIR_PATH + "listarPropostasRecebidas";
			}	
			else {
				map.addAttribute("listaPropostasLancadas", imovelPropostasservice.ordenarImoveisPropostas(user.getId(), form));
				map.addAttribute("imovelPropostaForm", form);
				return DIR_PATH + "listarMinhasImoveisPropostas";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - ordenar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}						
	}
	
	@RequestMapping(value="/ordenarAgrupar", method = RequestMethod.POST)
	public String ordenarAgrupar(HttpSession session,
								ModelMap map,
								@ModelAttribute("imovelPropostaForm") ImovelPropostasForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( form.getOpcaoVisualizacao().equals("agruparImoveis")) {
				map.addAttribute("listaAgruposImoveis", imovelPropostasservice.ordenarAgruparImoveisProposta(user.getId(), form));
				map.addAttribute("imovelPropostaForm", form);
				return DIR_PATH + "agruparImoveisPropostas";
			}	
			else {			
				map.addAttribute("listaAgruposUsuarios", imovelPropostasservice.ordenarAgruparImoveisProposta(user.getId(), form));
				map.addAttribute("imovelPropostaForm", form);
				return DIR_PATH + "agruparUsuariosPropostas";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - ordenarAgrupar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	 // permite visualizar todas as Propostas sobre um imovel
	@RequestMapping(value = "/visualizarPropostasPorImovel/{idImovel}", method = RequestMethod.GET)
    public String visualizarTodasPropostasImovel(@ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,
    										     @PathVariable Long idImovel, 
    										     ModelMap map){
		
		try {
			List<ImovelPropostas> lista = imovelPropostasservice.recuperarPropostasImovel(idImovel); 
			map.addAttribute("listaTodasPropostas", lista);
			map.addAttribute("quantTotalUsuarios", AppUtil.recuperarQuantidadeLista(lista));
			map.addAttribute("imovelPropostaForm", form );
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	        return DIR_PATH + "visualizarTodasPropostasImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - visualizarTodasPropostasImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
    }
	
	@RequestMapping(value = "/visualizarPropostasPorImovelCompartilhado/{idImovel}", method = RequestMethod.GET)
    public String visualizarTodasPropostasImovelCompartilhado(@PathVariable Long idImovel, 
    										   				  ModelMap map){
		
		try {
			ImovelPropostasForm  form = new ImovelPropostasForm();		
			form.setTipoLista("propostasRecebidas");			
			map.addAttribute("imovelPropostaForm", form );			
			List<ImovelPropostas> lista = imovelPropostasservice.recuperarPropostasImovel(idImovel);
			map.addAttribute("listaTodasPropostas",  lista);
			map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista) );
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	        return DIR_PATH + "visualizarTodasPropostasImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - visualizarTodasPropostasImovelCompartilhado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	 // permite visualizar todas as Propostas de um usuario sobre os imoveis do usuario sessao
		@RequestMapping(value = "/visualizarTodasPropostasPorUsuario/{idUsuario}", method = RequestMethod.GET)
	    public String visualizarTodasPropostasPorUsuario(@ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,
	    											   @PathVariable Long idUsuario, 
	    								   			   ModelMap map,
	    								   			   HttpSession session){
			
			try {
				UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	
				List<ImovelPropostas> lista = null;
				if (form.getTipoLista().equals("propostasRecebidas")){
					lista = imovelPropostasservice.recuperarUsuarioPropostasImovelUsuarioSessaoRecebidas(user.getId(), idUsuario);
					map.addAttribute("listaTodosImoveisPropostasUsuario", lista);
					map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));					
				}	
				else if (form.getTipoLista().equals("propostasLancadas")) {
					lista = imovelPropostasservice.recuperarUsuarioPropostasImovelUsuarioSessaoRecebidas(idUsuario, user.getId());
					map.addAttribute("listaTodosImoveisPropostasUsuario", lista);
					map.addAttribute("quantTotalImoveis", AppUtil.recuperarQuantidadeLista(lista));
				}	
				
				map.addAttribute("imovelPropostaForm", form );
				map.addAttribute("usuarioProposta", usuarioService.recuperarUsuarioPorId(idUsuario));
		        return DIR_PATH + "visualizarTodasPropostasUsuario";
			} catch (Exception e) {
				log.error("Erro metodo - ImovelPropostasController - visualizarTodasPropostasPorUsuario");
				log.error("Mensagem Erro: " + e.getMessage());
				map.addAttribute("mensagemErroGeral", "S");
				return ImovelService.PATH_ERRO_GERAL;
			}
	    }
	
	
	 // permite visualizar todas as Propostas sobre um imovel
		@RequestMapping(value = "/visualizarPropostasImovelPorUsuario/{idImovel}", method = RequestMethod.GET)
	    public String visualizarPropostasLancadasPorImovel(HttpSession session,
	    										   		   @ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,
	    										   		   @PathVariable Long idImovel, 
	    										   		   ModelMap map){
			try {
				UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);			
				map.addAttribute("listaTodasPropostas", imovelPropostasservice.recuperarPropostasImovelPorUsuario(user.getId(), idImovel) );			
				map.addAttribute("imovelPropostaForm", form );			
				map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
		        return DIR_PATH + "visualizarTodasPropostasImovel";
			} catch (Exception e) {
				log.error("Erro metodo - ImovelPropostasController - visualizarTodasPropostasPorUsuario");
				log.error("Mensagem Erro: " + e.getMessage());
				map.addAttribute("mensagemErroGeral", "S");
				return ImovelService.PATH_ERRO_GERAL;
			}
	    }
	
	 // permite visualizar todas as Propostas sobre um imovel por um usuario
	@RequestMapping(value = "/visualizarTodasPropostasImovel/{idImovel}", method = RequestMethod.GET)
    public String visualizarTodasPropostasImovel(@PathVariable Long idImovel, 
    										     ModelMap map,
    										     HttpSession session){
	
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaTodasPropostas", imovelPropostasservice.recuperarPropostasImovelPorUsuario(user.getId(), idImovel));
			map.addAttribute("imovel", imovelService.recuperarImovelPorid(idImovel));
	        return DIR_PATH + "visualizarImoveisPropostas";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - visualizarTodasPropostasImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }

	@RequestMapping(value = "/listarImoveisPropostas/{tipoLista}", method = RequestMethod.GET)
    public String listarImoveisPropostas(@PathVariable String tipoLista,
    									 HttpSession session,
    									 HttpServletRequest request,  									 
    									 ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			ImovelPropostasForm form = new ImovelPropostasForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			form.setTipoLista(tipoLista);			
			if ( tipoLista.equals("propostasRecebidas")){	
				long quantNovas = Long.valueOf(request.getSession().getAttribute("quantNovasImoveisPropostas").toString());
				if (quantNovas > 0 ){
					imovelPropostasservice.atualizarStatusLeituraImoveisPropostas(user.getId());
					session.setAttribute(ImovelService.QUANT_IMOVEIS_PropostaS, 0);				
				}			
				map.addAttribute("listaPropostasRecebidas", imovelPropostasservice.recuperarPropostasRecebidasPorUsuario(user.getId(), form));
				map.addAttribute("imovelPropostaForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "propostasRecebidas");
		    	return DIR_PATH + "listarPropostasRecebidas";
			}
			else if ( tipoLista.equals("propostasLancadas")){
				map.addAttribute("listaPropostasLancadas", imovelPropostasservice.recuperarPropostasLancadasPorUsuario(user.getId(), form));
				map.addAttribute("imovelPropostaForm", form);
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "propostasLancadas");
		    	return DIR_PATH + "listarMinhasImoveisPropostas";
			}		
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - listarImoveisPropostas");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/paginarFiltrar", method = RequestMethod.GET)
    public String paginarFiltrar(HttpSession session,
    							 @ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,
    							 HttpServletRequest request,  									 
    							 ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("imovelPropostaForm", form);
			if ( form.getTipoLista().equals("propostasRecebidas")){			
				map.addAttribute("listaPropostasRecebidas", imovelPropostasservice.recuperarPropostasRecebidasPorUsuario(user.getId(), form));			
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "propostasRecebidas");
		    	return DIR_PATH + "listarPropostasRecebidas";
			}
			else if ( form.getTipoLista().equals("propostasLancadas")){			
				map.addAttribute("listaPropostasLancadas", imovelPropostasservice.recuperarPropostasLancadasPorUsuario(user.getId(), form));			
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "propostasLancadas");
		    	return DIR_PATH + "listarMinhasImoveisPropostas";
			}		
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - paginarFiltrar");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	
	@RequestMapping(value = "/excluirProposta/{idImovelProposta}", method = RequestMethod.GET)
    public String excluirProposta(@PathVariable Long idImovelProposta,
								@ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,
								HttpSession session, 
								ModelMap map){
		
		try {
			imovelPropostasservice.excluirProposta(idImovelProposta);		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("imovelPropostaForm", form);
	    	return DIR_PATH + "listarMinhasImoveisPropostas";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - excluirProposta");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/confirmarExclusaoMinhaProposta/{idImovelProposta}")	
	public String confirmarExclusaoMinhaProposta(@PathVariable Long idImovelProposta,
												 @ModelAttribute("imovelPropostaForm") ImovelPropostasForm  form,	
												 HttpSession session,	
												 ModelMap map){
			
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelPropostasservice.excluirProposta(idImovelProposta);		
			List<ImovelPropostas> listaTodos  = imovelPropostasservice.recuperarPropostasLancadasPorUsuario(user.getId(), form);			
			map.addAttribute("quantTotalPropostas", AppUtil.recuperarQuantidadeLista(listaTodos));
			map.addAttribute("listaPropostasLancadas", listaTodos);		
			return DIR_PATH + "listarMinhasImoveisPropostas";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelPropostasController - confirmarExclusaoMinhaProposta");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}



}
