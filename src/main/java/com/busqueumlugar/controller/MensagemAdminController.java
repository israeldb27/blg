package com.busqueumlugar.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.busqueumlugar.form.MensagemAdminForm;
import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.ItemMensagemAdmin;
import com.busqueumlugar.model.MensagemAdmin;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ItemMensagemAdminService;
import com.busqueumlugar.service.MensagemAdminService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("mensagemAdminController")
@RequestMapping("/mensagemAdmin")
@SessionAttributes({"mensagemAdminForm", "itemMensagemAdminForm"})
public class MensagemAdminController {
	
	private static final Logger log = LoggerFactory.getLogger(MensagemAdminController.class);
	
	@Autowired
	private MensagemAdminService mensagemAdminService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ItemMensagemAdminService itemMensagemAdminService;
	
	
	private static final String DIR_PATH = "/mensagem/admin/";
	private static final String DIR_PATH_ADMIN = "/admin/mensagem/";
	
	@RequestMapping(value = "/filtrarTipoMensagem", method = RequestMethod.POST)
	public String filtrarTipoMensagem(@ModelAttribute("mensagemAdminForm") MensagemAdminForm form,
									   HttpSession session, 
								 	   ModelMap map){
		try {
			map.addAttribute("listaMinhasMensagens", mensagemAdminService.filtrarMensagensPorTipo(form.getOpcaoOrdenacao()));	
			map.addAttribute("mensagemAdminForm", form);
			return DIR_PATH_ADMIN + "listaMensagensAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController -  filtrarTipoMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/listarMensagensAdmin", method = RequestMethod.GET)
	public String listarMensagensAdmin(HttpSession session, 
								 	   ModelMap map){
		try {
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			map.addAttribute("listaMinhasMensagens", mensagemAdminService.listarTodasMensagensOrdenadasPorData());		
			map.addAttribute("mensagemAdminForm", new MensagemAdminForm());
			return DIR_PATH_ADMIN + "listaMensagensAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - listarMensagensAdmin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/visualizarMaisMensagem/{idMensagemAdmin}", method = RequestMethod.GET)
	public String visualizarMaisMensagem(@PathVariable Long idMensagemAdmin,
										 HttpSession session, 
								 	     ModelMap map){		
		
		try {
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			map.addAttribute("listaMinhasMensagens", itemMensagemAdminService.recuperaItemMensagensPorIdMensagemAdminParaAdministrador(idMensagemAdmin));		
			map.addAttribute("mensagemAdminForm", mensagemAdminService.carregaFormPorIdMensagemAdmin(idMensagemAdmin));
			session.setAttribute(MensagemAdminService.QUANT_NOVAS_MENSAGENS_ADMIN, AppUtil.recuperarQuantidadeLista(itemMensagemAdminService.recuperarQuantidadesNovasMensagensEnviadasParaAdmin()));
			return DIR_PATH_ADMIN + "maisMensagensAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - visualizarMaisMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/adicionarNovaMaisMensagemPorAdmin", method = RequestMethod.POST)
	public String adicionarNovaMaisMensagemPorAdmin(@ModelAttribute("mensagemAdminForm") MensagemAdminForm form,
											 		HttpSession session, 
											 		BindingResult result,
											 		ModelMap map){
		
		try {
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			boolean isValido = itemMensagemAdminService.validarAdicionarNovoItemMensagemAdminEnviadoPorAdministrador(form, result);
			if ( isValido )
				itemMensagemAdminService.adicionarNovoItemMensagemAdminPorAdministrador(form);			
			
			map.addAttribute("listaMinhasMensagens", itemMensagemAdminService.recuperaItemMensagensPorIdMensagemAdminParaAdministrador(form.getId()));
			map.addAttribute("mensagemAdminForm", form);
			return DIR_PATH_ADMIN + "maisMensagensAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - adicionarNovaMaisMensagemPorAdmin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/adicionarNovaMaisMensagemEnviadoPorAdmin", method = RequestMethod.POST)
	public String adicionarNovaMaisMensagemEnviadoPorAdmin(@ModelAttribute("mensagemAdminForm") MensagemAdminForm form,
															HttpSession session, 
															BindingResult result,
															ModelMap map){
		
		try {
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			boolean isValido = itemMensagemAdminService.validarAdicionarNovoItemMensagemAdminEnviadoPorAdministrador(form, result);
			if ( isValido )
				itemMensagemAdminService.adicionarNovoItemMensagemAdminEnviadoPorAdministrador(form);
			
			map.addAttribute("listaMinhasMensagens", itemMensagemAdminService.recuperaItemMensagensPorIdMensagemAdminParaAdministrador(form.getId()));
			map.addAttribute("mensagemAdminForm", form);
			return DIR_PATH_ADMIN + "maisMensagens";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - adicionarNovaMaisMensagemEnviadoPorAdmin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/adicionarNovaMaisMensagem", method = RequestMethod.POST)
	public String adicionarNovaMaisMensagem(@ModelAttribute("mensagemAdminForm") MensagemAdminForm form,
											 HttpSession session, 
											 BindingResult result,
								 	 		 ModelMap map){
		
		try {
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			boolean isValido = itemMensagemAdminService.validarAdicionarNovoItemMensagemAdmin(form, result);
			if ( isValido )	{
				itemMensagemAdminService.adicionarNovoItemMensagemAdmin(form, user.getId());
			}
			
			map.addAttribute("mensagemAdminForm", form);
			map.addAttribute("listaMinhasMensagens", itemMensagemAdminService.recuperaItemMensagensPorIdMensagemAdmin(form.getId()));
			
			return DIR_PATH + "maisMensagensAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - adicionarNovaMaisMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	
	@RequestMapping(value = "/prepararMaisMensagensAdmin/{idMensagemAdmin}", method = RequestMethod.GET)
	public String prepararMaisMensagensAdmin(@PathVariable Long idMensagemAdmin,
											 HttpServletRequest request,
											 HttpSession session,
								 	 		 ModelMap map){		
		
		try {
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			int quantNovas = Integer.parseInt(request.getSession().getAttribute("quantNovasMensagensAdmin").toString());
			if (quantNovas > 0){
				itemMensagemAdminService.atualizarItemMensagensPorId(idMensagemAdmin);
				UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
				List<MensagemAdmin> listaNovasMensagensAdmin = mensagemAdminService.recuperaTodasMensagensNovasPorUsuario(user.getId());
				session.setAttribute(MensagemAdminService.LISTA_NOVAS_MENSAGENS_ADMIN, listaNovasMensagensAdmin);
				session.setAttribute(MensagemAdminService.QUANT_NOVAS_MENSAGENS_ADMIN, AppUtil.recuperarQuantidadeLista(itemMensagemAdminService.recuperarItemMensagemAdminPorIdUsuarioStatusLeitura(user.getId(), "novo")));
			}		
			
			map.addAttribute("listaMinhasMensagens", itemMensagemAdminService.recuperaItemMensagensPorIdMensagemAdmin(idMensagemAdmin));
			map.addAttribute("mensagemAdminForm", mensagemAdminService.carregaFormPorIdMensagemAdmin(idMensagemAdmin));
			return DIR_PATH + "maisMensagensAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - prepararMaisMensagensAdmin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/prepararNovaMensagem", method = RequestMethod.GET)
	public String prepararNovaMensagem(ModelMap map, HttpSession session){
		
		try {	
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			map.addAttribute("mensagemAdminForm", new MensagemAdminForm());
			return DIR_PATH + "criarMensagemAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - prepararNovaMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/prepararNovaMensagemPorAdmin/{idUsuario}", method = RequestMethod.GET)
	public String prepararNovaMensagemPorAdmin(@PathVariable Long idUsuario,
												HttpSession session, 
											    ModelMap map){
		try {			
			MensagemAdminForm form = new MensagemAdminForm();
			form.setIdUsuario(idUsuario);
			form.setNomeUsuarioPara(usuarioService.recuperarUsuarioPorId(idUsuario).getNome());
			map.addAttribute("mensagemAdminForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			return DIR_PATH_ADMIN + "criarMensagemAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - prepararNovaMensagemPorAdmin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/enviarMensagem", method = RequestMethod.POST)
	public String enviarMensagem(@ModelAttribute("mensagemAdminForm") MensagemAdminForm form,
								 HttpSession session, 
								 BindingResult result,
								 ModelMap map){
		
		try {			
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			boolean isValido = mensagemAdminService.validarCriarMensagemParaAdmin(form, result);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			if ( isValido ){
				mensagemAdminService.criarMensagemParaAdmin(form, user.getId());
				map.addAttribute("msgSucesso", "S");
				map.addAttribute("mensagemAdminForm", new MensagemAdminForm());
			}
			else
				map.addAttribute("mensagemAdminForm", form);
			
			return DIR_PATH + "criarMensagemAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - enviarMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/enviarMensagemPorAdmin", method = RequestMethod.POST)
	public String enviarMensagemPorAdmin(@ModelAttribute("mensagemAdminForm") MensagemAdminForm form,
										 HttpSession session, 
										 BindingResult result,
								         ModelMap map){
		
		try {			
			boolean isValido = mensagemAdminService.validarCriarMensagemAdmin(form, result);
			if ( isValido ){
				mensagemAdminService.criarMensagemAdminEnviadoPorAdministrador(form);			
				map.addAttribute("msgSucesso", MessageUtils.getMessage("msg.envio.sucesso.pelo.admin"));
			}
			map.addAttribute("listaMinhasMensagens", itemMensagemAdminService.recuperaItemMensagensPorIdMensagemAdmin(form.getId()));
			map.addAttribute("mensagemAdminForm", form);		
			return DIR_PATH_ADMIN + "criarMensagemAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - enviarMensagemPorAdmin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	
	@RequestMapping(value = "/minhasNovasMensagensAdmin", method = RequestMethod.GET)
	public String minhasNovasMensagensAdmin(HttpSession session, ModelMap map){
		try {			
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaMinhasMensagens", mensagemAdminService.recuperaTodasMensagensNovasPorUsuario(user.getId()));
			map.addAttribute("mensagemAdminForm", new MensagemAdminForm());
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			return DIR_PATH + "listaMensagensAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - minhasMensagensAdmin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}				
	}
	
	@RequestMapping(value = "/minhasMensagensAdmin", method = RequestMethod.GET)
	public String minhasMensagensAdmin(HttpSession session, ModelMap map){
		try {			
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	
			map.addAttribute("listaMinhasMensagens", mensagemAdminService.recuperaTodasMensagensPorUsuario(user.getId()));
			map.addAttribute("mensagemAdminForm", new MensagemAdminForm());
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagensAdmin");
			return DIR_PATH + "listaMensagensAdmin";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemAdminController - minhasMensagensAdmin");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}				
	}
}
