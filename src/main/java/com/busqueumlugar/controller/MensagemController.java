package com.busqueumlugar.controller;

import java.io.IOException;

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
import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.InfoservicoService;
import com.busqueumlugar.service.MensagemService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.UsuarioInterface;
import com.paypal.api.payments.Payment;

@Controller("mensagemController")
@RequestMapping("/mensagem")
@SessionAttributes({"mensagemForm"})
public class MensagemController {
	
	private static final Logger log = LoggerFactory.getLogger(MensagemController.class);
	
	@Autowired
	private MensagemService mensagemService;
	
	@Autowired
	private ServicoService servicoService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ParamservicoService paramservicoService;
	
	@Autowired
	private InfoservicoService infoservicoService;	
	
	
	private static final String DIR_PATH = "/mensagem/";
	private static final String DIR_PATH_COBRANCA_SERVICO = "/mensagem/cobranca/";


	@RequestMapping(value = "/desmarcarCheck")	
	public void desmarcarCheck(Long idMensagem, HttpServletResponse response, HttpSession session){
		
		try {
			mensagemService.atualizarStatusMensagem(idMensagem);		
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(MensagemService.QUANT_NOVAS_MENSAGENS, mensagemService.checarQuantidadeNovasMensagens(user.getId()));
			response.setStatus(200);
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController -  desmarcarCheck");
			log.error("Mensagem Erro: " + e.getMessage());
			response.setStatus(500);
		}
	}
	
	
	
	@RequestMapping(value = "/prepararEnviarMensagem/{idUsuario}", method = RequestMethod.GET)
	public String goEnviarMensagem(@PathVariable Long idUsuario, 
								   ModelMap map, 
								   HttpSession session){
		
		try {
			UsuarioForm usuarioDe = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			MensagemForm form = new MensagemForm(); 
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagens");
			boolean pagouServico = servicoService.checarPossuiServicoPago(usuarioDe.getId(), ServicoService.SERVICO_MENSAGENS);
			if ( pagouServico ){
				form = mensagemService.prepararTodasMensagens(usuarioDe.getId(), idUsuario);
				map.addAttribute("listaMinhasMensagens", mensagemService.recuperaTodasMensagens(form.getIdUsuarioDe(), form.getIdUsuarioPara(), form.getLoginExibicao()) );
				map.addAttribute("mensagemForm", form);
				return DIR_PATH + "criarMensagem";
			}
			else {			
				form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());		 
				form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico("relatorioCorretor"));
				form.setAcao(ServicoService.SERVICO_MENSAGENS);
				form.setOpcao(ServicoService.SERVICO_MENSAGENS);
				map.addAttribute("mensagemForm", form);
				return DIR_PATH_COBRANCA_SERVICO + "inicioCobrancaMensagem";
			}
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController -  goEnviarMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/enviarMensagem", method = RequestMethod.POST)
	public String enviarMensagem(@ModelAttribute("mensagemForm") MensagemForm form,
								 HttpSession session, 
								 ModelMap map){
		try {
			mensagemService.criarMensagem(form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagens");
			map.addAttribute("listaMinhasMensagens", mensagemService.recuperaTodasMensagens(form.getIdUsuarioDe(), form.getIdUsuarioPara(), form.getLoginExibicao()) );
			map.addAttribute("mensagemForm", form);
			return DIR_PATH + "criarMensagem";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController -  enviarMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}
	
	@RequestMapping(value = "/minhasMensagens", method = RequestMethod.GET)
	public String minhasMensagens(HttpSession session, 
								 ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			MensagemForm form = new MensagemForm();		
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagens");
			boolean pagouServico = servicoService.checarPossuiServicoPago(user.getId(), ServicoService.SERVICO_MENSAGENS);
			if ( pagouServico ){			
				map.addAttribute("listaMinhasMensagens", mensagemService.recuperaTodasMensagensPorDataMensagem(user.getId()));
				map.addAttribute("mensagemForm", form);
				return DIR_PATH + "listaMensagens";
			}
			else {			
				form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());		 
				form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico("relatorioCorretor"));
				form.setAcao(ServicoService.SERVICO_MENSAGENS);
				form.setOpcao(ServicoService.SERVICO_MENSAGENS);
				map.addAttribute("mensagemForm", form);
				return DIR_PATH_COBRANCA_SERVICO + "inicioCobrancaMensagem";
			}
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController -  minhasMensagens");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/minhasNovasMensagens", method = RequestMethod.GET)
	public String minhasNovasMensagens(HttpSession session, 
								 	   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			MensagemForm form = new MensagemForm();		
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasMensagens");
			boolean pagouServico = servicoService.checarPossuiServicoPago(user.getId(), ServicoService.SERVICO_MENSAGENS);
			if ( pagouServico ){			
				map.addAttribute("listaMinhasMensagens", mensagemService.recuperaTodasMensagensNovasPorDataMensagem(user.getId()));
				map.addAttribute("mensagemForm", form);
				return DIR_PATH + "listaMensagens";
			}
			else {			
				form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());		 
				form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico("relatorioCorretor"));
				form.setAcao(ServicoService.SERVICO_MENSAGENS);
				form.setOpcao(ServicoService.SERVICO_MENSAGENS);
				map.addAttribute("mensagemForm", form);
				return DIR_PATH_COBRANCA_SERVICO + "inicioCobrancaMensagem";
			}
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController -  minhasNovasMensagens");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	
	
	@RequestMapping(value = "/selecionarMensagemCobranca", method = RequestMethod.POST)
	public String selecionarMensagemCobranca(HttpSession session, 
											 ModelMap map,
											 @ModelAttribute("mensagemForm") MensagemForm form,
											 BindingResult result){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			form.setIdUsuarioSelecionado(user.getId());
			boolean possuiErro = servicoService.validarSelecaoPagamentoServico(form, result);
			if (! possuiErro ){
				servicoService.selecionarPagamentoServico(form, form.getIdInfoServicoPagamentoSelecionada(), form.getIdFormapagamentoSelecionada());
				form.setAcao("mensagens");
				if ( form.getFormaPagtoSelecionado().equals("Pag Seguro")){                  
					servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());            
					form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(form.getIdUsuarioSelecionado(), form.getAcao()));
					map.addAttribute("mensagemForm", form);
					return DIR_PATH_COBRANCA_SERVICO + "confirmarPagtoPagSeguroMensagem";                                            
				}
				else if ( form.getFormaPagtoSelecionado().equals("Pay Pal")){                                
					servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());            
					form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(form.getIdUsuarioSelecionado(), form.getAcao()));
					map.addAttribute("mensagemForm", form);
					return DIR_PATH_COBRANCA_SERVICO + "confirmarPagtoPayPalMensagem";
				} 	
			}			            
	        return DIR_PATH_COBRANCA_SERVICO + "inicioCobrancaMensagem";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController -  selecionarMensagemCobranca");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	
	@RequestMapping(value = "/confirmarPagtoMensagem", method = RequestMethod.POST)    
    public String confirmarPagtoMensagem(HttpSession session, 
										 HttpServletRequest req,	
										 HttpServletResponse resp,
										 ModelMap map,
										 @ModelAttribute("mensagemForm") MensagemForm form){ 
				
		try {
			Payment payment = null;
			payment = mensagemService.confirmarPagamentoPayPal(req, resp, form);
			if ( payment != null) {
				resp.sendRedirect(req.getAttribute("redirectURL").toString());
				return "response";
			}	
			else 
				return null;
		} catch (IOException e) {
			log.error("Erro metodo - MensagemController - confirmarPagtoMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/simularRetornoPagto", method = RequestMethod.POST)    
    public String simularRetornoPagto(@ModelAttribute("mensagemForm") MensagemForm form, ModelMap map){
		
		try {
			servicoService.simulaRetornoPagto(Long.parseLong(form.getIdSimulaServicoMensagem()));
	    	return DIR_PATH + "listaMensagens";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController - simularRetornoPagto");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}    	
    }
	
	@RequestMapping(value = "/maisMensagens/{idUsuarioPara}", method = RequestMethod.GET)
	public String maisMensagens(@PathVariable Long idUsuarioPara, 
								ModelMap map, 
								HttpSession session){
		
		try {
			MensagemForm form = new MensagemForm();
			UsuarioForm usuarioDe = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			form = mensagemService.prepararTodasMensagens(usuarioDe.getId(), idUsuarioPara);		
			mensagemService.atualizarTodasMensagensRecebidas(form.getIdUsuarioDe(), form.getIdUsuarioPara());
			map.addAttribute("listaMensagens", mensagemService.recuperaTodasMensagens(form.getIdUsuarioDe(), form.getIdUsuarioPara(), form.getLoginExibicao()));		
			session.setAttribute(MensagemService.LISTA_MENSAGENS, mensagemService.recuperaTodasMensagensPorDataMensagem(usuarioDe.getId(), 4));
			session.setAttribute(MensagemService.QUANT_NOVAS_MENSAGENS, mensagemService.checarQuantidadeNovasMensagens(usuarioDe.getId()));
			map.addAttribute("mensagemForm", form);
			return DIR_PATH + "maisMensagens";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController - maisMensagens");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/adicionarMensagem", method = RequestMethod.POST)
	public String adicionarMensagem(@ModelAttribute("mensagemForm") MensagemForm form, 
									ModelMap map, 
									HttpSession session){
		
		try {			
			String msgRetorno = mensagemService.validarCriarMensagem(form);
			if ( msgRetorno.equals(""))
				mensagemService.criarMensagem(form);
			else
				map.addAttribute("msgErro", msgRetorno);
			
			map.addAttribute("listaMensagens", mensagemService.recuperaTodasMensagens(form.getIdUsuarioDe(), form.getIdUsuarioPara(), form.getLoginExibicao()));
			map.addAttribute("mensagemForm", form);
			return DIR_PATH + "maisMensagens";
		} catch (Exception e) {
			log.error("Erro metodo - MensagemController -  adicionarMensagem");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
}
