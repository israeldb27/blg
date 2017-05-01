package com.busqueumlugar.controller;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.busqueumlugar.enumerador.NotaAcaoEnum;
import com.busqueumlugar.form.NotaForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.UsuarioInterface;

@Controller("notaController")
@RequestMapping("/nota")
@SessionAttributes({"notaForm"})
public class NotaController {
	
	private static final Logger log = LoggerFactory.getLogger(NotaController.class);
	
	@Autowired
	private NotaService notaService;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private static final String DIR_PATH = "/nota/";
	
	@RequestMapping(value="/escreverMinhaNota", method = RequestMethod.POST)
	public String escreverMinhaNota(HttpSession session,	
									ModelMap map,
							        @ModelAttribute("notaForm") NotaForm form){	
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			notaService.cadastrarNota(form.getEscreverNota(), user.getId(), new Date(), NotaAcaoEnum.PESSOAL.getRotulo());
			map.addAttribute("listaMinhasNotas", notaService.listarTodasNotasPorPerfil(user.getId(), form));
			map.addAttribute("notaForm", form);
			return DIR_PATH + "minhasNotas";
		} catch (Exception e) {
			log.error("Erro metodo - NotaController - escreverMinhaNota");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	
	
	@RequestMapping(value="/ordenarMinhasNotas", method = RequestMethod.POST)
	public String ordenarMinhasNotas(HttpSession session,	
									 ModelMap map,
							         @ModelAttribute("notaForm") NotaForm form){	
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaMinhasNotas", notaService.ordenarNotas("minhasNotas", user.getId(), user.getNome(), form));		
			map.addAttribute("notaForm", form);		
			return DIR_PATH + "minhasNotas";
		} catch (Exception e) {
			log.error("Erro metodo - NotaController - ordenarMinhasNotas");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	
	@RequestMapping(value="/ordenarNotasContatos", method = RequestMethod.POST)
	public String ordenarNotasContatos(HttpSession session,
							          ModelMap map,
							          @ModelAttribute("notaForm") NotaForm form){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaNotasContato", notaService.ordenarNotas("notasContato", user.getId(), user.getNome(), form));
			map.addAttribute("notaForm", form);		
			return DIR_PATH + "notasContatos";
		} catch (Exception e) {
			log.error("Erro metodo - NotaController - ordenarNotasContatos");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}	
	
	
	@RequestMapping(value = "/minhasNotas", method = RequestMethod.GET)
	public String minhasNotas(HttpSession session, 
							  ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			NotaForm form = new NotaForm();		
			map.addAttribute("listaMinhasNotas", notaService.listarTodasNotasPorPerfil(user.getId(), form));
			map.addAttribute("notaForm", form);			
			return DIR_PATH + "minhasNotas";
		} catch (Exception e) {
			log.error("Erro metodo - NotaController - minhasNotas");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		 
	}
	
	@RequestMapping(value = "/paginarMinhasNotas", method = RequestMethod.GET)
	public String paginarMinhasNotas(HttpSession session, 
									 @ModelAttribute("notaForm") NotaForm form,
							  	     ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);				
			map.addAttribute("listaMinhasNotas", notaService.listarTodasNotasPorPerfil(user.getId(), form));
			map.addAttribute("notaForm", form);		
			return DIR_PATH + "minhasNotas";
		} catch (Exception e) {
			log.error("Erro metodo - NotaController - paginarMinhasNotas");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}	
	
	
	@RequestMapping(value = "/filtrarMinhasNotas", method = RequestMethod.POST)
	public String filtrarMinhasNotas(HttpSession session,
									 @ModelAttribute("notaForm") NotaForm form,
							    	 ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaMinhasNotas", notaService.filtrarMinhasNotas(user.getId(), form));
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "minhasNotas");
			form.setOpcaoOrdenacao("");
			map.addAttribute("exibeMinhasNotas", "S");
			map.addAttribute("notaForm", form);		
			return DIR_PATH + "minhasNotas";
		} catch (Exception e) {
			log.error("Erro metodo - NotaController - filtrarMinhasNotas");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
		
	@RequestMapping(value = "/notasContatos", method = RequestMethod.GET)
	public String goNotasContatos(HttpSession session, ModelMap map){
		
		try {
			NotaForm form = new NotaForm();
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaNotasContato", notaService.recuperarNotasContatosUsuario(user.getId(), form));		
			map.addAttribute("notaForm", new NotaForm());
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "notasContato");
			return DIR_PATH + "notasContatos";
		} catch (Exception e) {
			log.error("Erro metodo - NotaController - goNotasContatos");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	@RequestMapping(value = "/filtrarNotasContatos", method = RequestMethod.POST)
	public String filtrarNotasContatos(HttpSession session,
									 @ModelAttribute("notaForm") NotaForm form,
							    	 ModelMap map){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaNotasContato", notaService.filtrarNotasContatos(user.getId(), form));
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "minhasNotas");
			form.setOpcaoOrdenacao("");
			map.addAttribute("notaForm", form);	
			map.addAttribute("exibeMinhasNotas", "S");
			return DIR_PATH + "notasContatos";
		} catch (Exception e) {
			log.error("Erro metodo - NotaController - filtrarNotasContatos");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
}
