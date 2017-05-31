package com.busqueumlugar.controller;

import java.util.List;
import java.util.ResourceBundle;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.busqueumlugar.form.ContatoForm;
import com.busqueumlugar.form.NotificacaoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Notificacao;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("notificacaoController")
@RequestMapping("/notificacao")
@SessionAttributes({"notificacaoForm"})
public class NotificacaoController {
	
	private static final Logger log = LoggerFactory.getLogger(NotificacaoController.class);

	@Autowired
	private NotificacaoService notificacaoService;
	
	private static final String DIR_PATH = "/notificacao/";	
	
	@RequestMapping(value = "/desmarcarCheck")	
	public void desmarcarCheck(Long idNotificacao, HttpServletResponse response, HttpSession session){
		try {
			notificacaoService.atualizarStatus(notificacaoService.recuperarNotificacaoPorId(idNotificacao));
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(NotificacaoService.QUANT_NOVAS_NOTIFICACOES , notificacaoService.checarQuantidadeNovasNotificacoes(user.getId()));
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - NotificacaoController - desmarcarCheck");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value = "/listarMinhasNotificacoes", method = RequestMethod.GET)
	public String goListarMinhasNotificacoes(HttpSession session, 
											 HttpServletRequest request,
											 ModelMap map){
		
		try {
			UsuarioForm usuario = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			NotificacaoForm form = new NotificacaoForm();
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasNotificacoes");
			int quantNovas = Integer.parseInt(request.getSession().getAttribute("quantNovasNotificacoes").toString());
			if ( quantNovas > 0 ){
				notificacaoService.atualizarStatusLeituraNotificacaoByIdUsuario(usuario.getId());			
				session.setAttribute(ImovelService.LISTA_NOVAS_NOTIFICACOES, null);
				session.setAttribute(ImovelService.QUANT_NOVAS_NOTIFICACOES, 0);
			}
			map.addAttribute("listaNotificacoes", notificacaoService.recuperarMinhasNotificacoes(usuario.getId(), form));		
			map.addAttribute("notificacaoForm", form);
			return DIR_PATH + "listarMinhasNotificacoes";
		} catch (Exception e) {
			log.error("Erro metodo - NotificacaoController - goListarMinhasNotificacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/paginarMinhasNotificacoes", method = RequestMethod.POST)
	public String paginarMinhasNotificacoes(HttpSession session, 
											HttpServletRequest request,
											@ModelAttribute("notificacaoForm") NotificacaoForm form,
											ModelMap map){
		
		try {
			UsuarioForm usuario = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaNotificacoes", notificacaoService.recuperarMinhasNotificacoes(usuario.getId(), form));		
			map.addAttribute("notificacaoForm", form);
			return DIR_PATH + "listarMinhasNotificacoes";
		} catch (Exception e) {
			log.error("Erro metodo - NotificacaoController - paginarMinhasNotificacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
	}
	
	@RequestMapping(value = "/selecionarNotificacao/{idNotificacao}", method = RequestMethod.GET)
	public String selecionarNotificacao(@PathVariable Long idNotificacao,
										HttpSession session,
										HttpServletRequest request,
										ModelMap map){
		
		try {
			UsuarioForm usuario = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			notificacaoService.atualizarStatusLeituraNotificacaoByIdUsuario(usuario.getId());
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasNotificacoes");
			List<Notificacao> lista = notificacaoService.recuperarNotificacaoPorIdSelecionada(idNotificacao);
			map.addAttribute("listaNotificacoes", lista);
			List<Notificacao> listaNovasNotificacoes = notificacaoService.recuperarListaNotificacoesNovas(usuario.getId());
			session.setAttribute(ImovelService.LISTA_NOVAS_NOTIFICACOES, listaNovasNotificacoes);
			session.setAttribute(ImovelService.QUANT_NOVAS_NOTIFICACOES, AppUtil.recuperarQuantidadeLista(listaNovasNotificacoes));
			map.addAttribute("notificacaoForm", new NotificacaoForm());
			return DIR_PATH + "visualizarMinhasNotificacaoSelecionada";
		} catch (Exception e) {
			log.error("Erro metodo - NotificacaoController - selecionarNotificacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	
	
	@RequestMapping(value = "/ordenarMinhasNotificacoes", method = RequestMethod.POST)
	public String ordenarMinhasNotificacoes(@ModelAttribute("notificacaoForm") NotificacaoForm form,
											HttpSession session, 
											ModelMap map){
		try {
			UsuarioForm usuario = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaNotificacoes", notificacaoService.filtrarNotificacoes(usuario.getId(), form));
			//form.setListaNotificacoes(notificacaoService.ordenarMinhasNotificacoes(form.getListaNotificacoes(), usuario.getId(), form.getOpcaoOrdenacao()));
			map.addAttribute("notificacaoForm", form);
			return DIR_PATH + "listarMinhasNotificacoes";
		} catch (Exception e) {
			log.error("Erro metodo - NotificacaoController - ordenarMinhasNotificacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/filtrarNotificacoes", method = RequestMethod.POST)
	public String filtrarNotificacoes(@ModelAttribute("notificacaoForm") NotificacaoForm form,
									  HttpSession session, 
									  ModelMap map){
		
		try {
			UsuarioForm usuario = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			//form.setListaNotificacoes(notificacaoService.filtrarNotificacoes(usuario.getId(), form.getOpcaoFiltro()));
			map.addAttribute("listaNotificacoes", notificacaoService.filtrarNotificacoes(usuario.getId(), form));
			map.addAttribute("notificacaoForm", form);
			return DIR_PATH + "listarMinhasNotificacoes";
		} catch (Exception e) {
			log.error("Erro metodo - NotificacaoController - filtrarNotificacoes");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}	
}
