package com.busqueumlugar.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.ServletException;
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

import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.enumerador.TipoParamServicoOpcaoEnum;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.form.PlanousuarioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.form.ServicoForm;
import com.busqueumlugar.model.Paramservico;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.InfoservicoService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.PlanoService;
import com.busqueumlugar.service.PlanousuarioService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.UsuarioInterface;
import com.paypal.api.payments.Payment;

@Controller("servicoController")
@RequestMapping("/servico")
@SessionAttributes({"servicoForm"})
public class ServicoController {
	
	private static final Logger log = LoggerFactory.getLogger(ServicoController.class);
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private PlanousuarioService planousuarioService;
	
	@Autowired
	private PlanoService planoService;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ParamservicoService paramservicoService; 
	
	@Autowired
	private InfoservicoService infoservicoService; 
	
	
	private static final String DIR_PATH = "/servico/";
	private static final String DIR_PATH_USUARIO = "/usuario/";
	private static final String DIR_PATH_ASSINATURA_USUARIO = "/servico/assinatura/";
	private static final String DIR_PATH_PLANO = "/servico/plano/";
	private static final String DIR_PATH_CAD_ASSINATURA_USUARIO = "/usuario/assinatura/";
	private static final String DIR_PATH_REL_COBRANCA_SERVICO = "";	
	
	
	@RequestMapping(value = "/prepararRenovacaoAssinatura", method = RequestMethod.GET)
	public String prepararRenovacaoAssinatura(HttpSession session, ModelMap map) {  
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);			
			ServicoForm form = new ServicoForm();
			
			if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
				form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.ASSINATURA_PADRAO.getRotulo()));
				form.setAcao(ServicoValueEnum.ASSINATURA_PADRAO.getRotulo());
			}	
			else if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo())) {
				form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo()));
				form.setAcao(ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo());
			}	
			else {
				form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo()));
				form.setAcao(ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo());
			}	
			
			form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());
			map.addAttribute("servicoForm", form);
			return DIR_PATH_ASSINATURA_USUARIO + "inicioAssinaturaUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - prepararRenovacaoAssinatura");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/trocarAssinatura", method = RequestMethod.POST)
    public String trocarAssinatura(HttpSession session, 
    							   ModelMap map,
    							   @ModelAttribute("servicoForm") ServicoForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			servicoService.selecionarPagamentoServico(form, form.getIdInfoServicoPagamentoSelecionada(), form.getIdFormapagamentoSelecionada());
			form.setIdUsuario(user.getId());
			servicoService.cadastrarSolicitacaoPagtoAssinatura(form);
	        form.setOpcao("assinatura");
	        form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(user.getId(), form.getAcao()));
	        map.addAttribute("servicoForm", form);
			if ( form.getFormaPagtoSelecionado().equals("Pag Seguro"))
				return DIR_PATH_ASSINATURA_USUARIO + "confirmarPagtoPagSeguroAssinaturaUsuario";		
			else if ( form.getFormaPagtoSelecionado().equals("Pay Pal"))
				return DIR_PATH_ASSINATURA_USUARIO + "confirmarPagtoPayPalAssinaturaUsuario";
			
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - trocarAssinatura");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/listarServicosUsuario", method = RequestMethod.GET)
    public String goListarServicosUsuario(HttpSession session, ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ServicoForm form = new ServicoForm();
			form.setListaServicosDisponiveis(paramservicoService.recuperaTodosParametrosPorTipoSemAssinatura(user));		
			map.addAttribute("listaMeusServicos", servicoService.recuperarServicosDisponiveisPorUsuario(user.getId()));
			map.addAttribute("servicoForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarServicosUsuario");
			return DIR_PATH + "listarMeusServicos";
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - goListarServicosUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/listarPlanosUsuario", method = RequestMethod.GET)
    public String listarPlanosUsuario(HttpSession session, ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ServicoForm form = new ServicoForm();		
			form.setListaPlanosDisponiveis(planoService.listarTodosDisponiveis());
			map.addAttribute("listaMeusPlanos", planousuarioService.recuperarPlanosPorUsuario(user.getId()));
			map.addAttribute("servicoForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarPlanosUsuario");
			return DIR_PATH + "listarMeusPlanos";
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - listarPlanosUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/selecionarServicoDisponivel", method = RequestMethod.POST)
    public String selecionarServicoDisponivel(HttpSession session, 
			   								  ModelMap map,
			   								  @ModelAttribute("servicoForm") ServicoForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		    String msgRetorno = servicoService.validarSolicitacaoServico(form.getIdServicoSelecionado(), 
		    															 user.getId(), 
		    															 new Date());
			if ( msgRetorno.equals("")){
				 form.setIdUsuario(user.getId());
	  			 form.setDataSolicitacao(new Date());
				 form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());
				 Paramservico paramservico = paramservicoService.recuperarParamServicoPorId(form.getIdServicoSelecionado());
				 form.setAcao(paramservico.getValueServico());
				 form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(paramservico.getValueServico()));
				 map.addAttribute("servicoForm", form);
				 return DIR_PATH + "cadastrarPagtoServico";
			}
			else {					
				map.addAttribute("msgValidacaoListaMeusServicos", msgRetorno);
				map.addAttribute("servicoForm", form);
				return DIR_PATH + "listarMeusServicos";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - selecionarServicoDisponivel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/selecionarServicoDisponivelPesquisa/{idServico}", method = RequestMethod.GET)
    public String selecionarServicoDisponivelPesquisa(HttpSession session, 
    												  @PathVariable("idServico") Long idServico,
			   								  		  ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		    String msgRetorno = servicoService.validarSolicitacaoServico(idServico, 
		    															 user.getId(), 
		    															 new Date());
			if ( msgRetorno.equals("")){
				 ServicoForm form = new ServicoForm();
				 form.setIdUsuario(user.getId());
	  			 form.setDataSolicitacao(new Date());
				 form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());
				 Paramservico paramservico = paramservicoService.recuperarParamServicoPorId(idServico);
				 form.setAcao(paramservico.getValueServico());
				 form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(paramservico.getValueServico()));
				 map.addAttribute("servicoForm", form);
				 return DIR_PATH + "cadastrarPagtoServico";
			}
			else {					
				map.addAttribute("msgValidacaoListaMeusServicos", msgRetorno);			
				return DIR_PATH_USUARIO + "resultadoPesquisa";
			}
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - selecionarServicoDisponivelPesquisa");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	
	@RequestMapping(value = "/selecionarPlanoDisponivel", method = RequestMethod.POST)
    public String selecionarPlanoDisponivel(HttpSession session, 
			   								ModelMap map,
			   								@ModelAttribute("servicoForm") ServicoForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	   
			 String msgRetorno = planousuarioService.validarSolicitacaoPlano(user.getId(), form);
			 if ( msgRetorno.equals("")){
				 servicoService.carregarInfoPlanoSelecionado(form);  
			     form.setIdUsuario(user.getId());
			     form.setDataSolicitacao(new Date());
			     form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());
				 map.addAttribute("servicoForm", form);           
		        return DIR_PATH_PLANO + "cadastrarPagtoPlano";
			 }
			 else {
				 map.addAttribute("msgValidacaoListaMeusPlanos", msgRetorno);
				 map.addAttribute("servicoForm", form);
				 return DIR_PATH + "listarMeusPlanos";
			 }
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - selecionarPlanoDisponivel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}    
	}
	
	@RequestMapping(value = "/selecionarPlanoDisponivelPesquisa/{idPlano}", method = RequestMethod.GET)
    public String selecionarPlanoDisponivelPesquisa(HttpSession session, 
    												@PathVariable("idPlano") Long idPlano,
			   									    ModelMap map){
		
		try {
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	   
			 String msgRetorno = planousuarioService.validarSolicitacaoPlano(user.getId(), idPlano);
			 if ( msgRetorno.equals("")){
				 ServicoForm form = new ServicoForm();  
				 form = servicoService.carregarInfoPlanoSelecionadoPorId(idPlano);  
			     form.setIdUsuario(user.getId());
			     form.setDataSolicitacao(new Date());
			     form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());
				 map.addAttribute("servicoForm", form);           
		        return DIR_PATH_PLANO + "cadastrarPagtoPlano";
			 }
			 else {
				 map.addAttribute("msgValidacaoListaMeusPlanos", msgRetorno);		
				 return DIR_PATH_USUARIO + "resultadoPesquisa";
			 }
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - selecionarPlanoDisponivelPesquisa");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}   
	}
	
	@RequestMapping(value = "/selecionarAssinaturaDisponivel", method = RequestMethod.POST)
    public String selecionarAssinaturaDisponivel(HttpSession session, 
			   						  			 ModelMap map,
			   						  			 @ModelAttribute("servicoForm") ServicoForm form){   
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 String msg = servicoService.validarPagamentoServico(form.getIdInfoServicoPagamentoSelecionada(), form.getIdFormapagamentoSelecionada() );
			 form.setIdUsuario(user.getId());
			 if ( msg.equals("") ){
				servicoService.selecionarPagamentoServico(form, form.getIdInfoServicoPagamentoSelecionada(), form.getIdFormapagamentoSelecionada());
				 if ( form.getFormaPagtoSelecionado().equals("Pag Seguro")){   
					 if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServicoImovel(form, form.getIdImovel() );					 
						 form.setOpcao("servicoImovel");
					 }
					 else if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()) || form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());					 
						 form.setOpcao("servico");
					 }
					 form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(user.getId(), form.getAcao() ));
					 map.addAttribute("servicoForm", form);
					 return DIR_PATH_CAD_ASSINATURA_USUARIO + "confirmarPagtoPagSeguro";                                            
				 }
				 else if ( form.getFormaPagtoSelecionado().equals("Pay Pal")){  
					 if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServicoImovel(form, form.getIdImovel() );					 
						 form.setOpcao("servicoImovel");
					 }
					 else if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()) || form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());					
						 form.setOpcao("servico");
					 }
					 form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(user.getId(), form.getAcao() ));
					 map.addAttribute("servicoForm", form);
					 return DIR_PATH_ASSINATURA_USUARIO + "confirmarPagtoPayPalAssinaturaUsuario";
				 }
			 }
			 else
				 return null;		 
			              
			return DIR_PATH_CAD_ASSINATURA_USUARIO + "confirmarPagtoServico";
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - selecionarAssinaturaDisponivel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}   
    }
	
	@RequestMapping(value = "/confirmarPagtoAssinaturaPayPal", method = RequestMethod.POST)
    public String confirmarPagtoAssinaturaPayPal(HttpSession session, 
    										     HttpServletRequest req,	
    										     HttpServletResponse resp,
			   								     ModelMap map,
			   								     @ModelAttribute("servicoForm") ServicoForm form){		
		try {
			Payment payment = null;
			payment = servicoService.confirmarPagamentoPayPal(req, resp, form);
			if ( payment != null) {
				resp.sendRedirect(req.getAttribute("redirectURL").toString());
				return "response";
			}	
			else 
				return null;
		} catch (IOException e) {
			log.error("Erro metodo - ServicoController - selecionarAssinaturaDisponivel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
    }
	
	
	@RequestMapping(value = "/selecionarPagamentoPlano", method = RequestMethod.POST)
    public String selecionarPagamentoPlano(HttpSession session, 
			   						       ModelMap map,
			   						       @ModelAttribute("servicoForm") ServicoForm form){ 		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 form.setIdUsuario(user.getId());
			 String msgRetorno = planousuarioService.validarCadastroSolicitacaoPlano(user.getId(), form);
			 if ( msgRetorno.equals("")){
				 servicoService.selecionarPagamentoPlano(form, form.getIdFormapagamentoSelecionada());
				 planousuarioService.cadastrarSolicitacaoPlano(form.getIdPlanoSelecionado(), user.getId(), form.getNomeFormaPagto());
				 form.setOpcao("plano");			 
				 map.addAttribute("servicoForm", form);
				 form.setIdPlanoSelecionado(planousuarioService.recuperarUltimoIdPlanoGerado(user.getId()));
				 if ( form.getFormaPagtoSelecionado().equals("Pag Seguro"))				 					 
					 return DIR_PATH_PLANO + "confirmarPagtoPlanoPagSeguro";
				 else if ( form.getFormaPagtoSelecionado().equals("Pay Pal"))
					 return DIR_PATH_PLANO + "confirmarPagtoPayPalPlano";
			 }
			 else {
				 map.addAttribute("msgValidacaoCadastroSolicitacao", msgRetorno);
				 return DIR_PATH_PLANO + "cadastrarPagtoPlano";
			 }
			              
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - selecionarPagamentoPlano");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
		 
	}
	
	@RequestMapping(value = "/selecionarPagamentoServico", method = RequestMethod.POST)
    public String selecionarPagamento(HttpSession session, 
			   						  ModelMap map,
			   						  @ModelAttribute("servicoForm") ServicoForm form){   
		
		try {
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 String msg = servicoService.validarPagamentoServico(form.getIdInfoServicoPagamentoSelecionada(), form.getIdFormapagamentoSelecionada() );
			 form.setIdUsuario(user.getId());
			 if ( msg.equals("") ){
				servicoService.selecionarPagamentoServico(form, form.getIdInfoServicoPagamentoSelecionada(), form.getIdFormapagamentoSelecionada());
				 if ( form.getFormaPagtoSelecionado().equals("Pag Seguro")){   
					 if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServicoImovel(form, form.getIdImovel() );					 
						 form.setOpcao("servicoImovel");
					 }
					 else if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()) || form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());					 
						 form.setOpcao("servico");
					 }
					 form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(user.getId(), form.getAcao() ));
					 map.addAttribute("servicoForm", form);
					 return DIR_PATH + "confirmarPagtoPagSeguro";                                            
				 }
				 else if ( form.getFormaPagtoSelecionado().equals("Pay Pal")){  
					 if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServicoImovel(form, form.getIdImovel() );					 
						 form.setOpcao("servicoImovel");
					 }
					 else if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()) || form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
						 servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());					
						 form.setOpcao("servico");
					 }
					 form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(user.getId(), form.getAcao() ));
					 map.addAttribute("servicoForm", form);
					 return DIR_PATH + "confirmarPagtoPayPalServico";
				 }
			 }
			 else {			
				 map.addAttribute("msgValidacaoCadastroPagtoServico", msg);	
				 return DIR_PATH + "cadastrarPagtoServico";
			 }
			              
			return DIR_PATH + "confirmarPagtoServico";
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - selecionarPagamento");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
		
    }
	
	@RequestMapping(value = "/confirmarPagtoServicoPayPal", method = RequestMethod.POST)
    public String confirmarPagtoServicoPayPal(HttpSession session, 
    										  HttpServletRequest req,	
    										  HttpServletResponse resp,
			   								  ModelMap map,
			   								  @ModelAttribute("servicoForm") ServicoForm form){		
				
		try {
			Payment payment = null;
			payment = servicoService.confirmarPagamentoPayPal(req, resp, form);
			if ( payment != null) {
				resp.sendRedirect(req.getAttribute("redirectURL").toString());
				return "response";
			}	
			else 
				return null;
		} catch (IOException e) {
			log.error("Erro metodo - ServicoController - confirmarPagtoServicoPayPal");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/confirmarPagtoPlanoPayPal", method = RequestMethod.POST)
    public String confirmarPagtoPlanoPayPal(HttpSession session, 
    										HttpServletRequest req,	
    										HttpServletResponse resp,
			   								ModelMap map,
			   								@ModelAttribute("servicoForm") ServicoForm form){		
		try {
			Payment payment = null;
			payment = planousuarioService.confirmarPagamentoPayPalPlano(req, resp, form);
			if ( payment != null) {
				resp.sendRedirect(req.getAttribute("redirectURL").toString());
				return "response";
			}	
			else 
				return null;
		} catch (IOException e) {
			log.error("Erro metodo - ServicoController - confirmarPagtoPlanoPayPal");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/excluirSolServico", method = RequestMethod.GET)	
    public String excluirSolServico(@PathVariable("idServico") Long idServico,
    								HttpSession session, 
			   						ModelMap map){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ServicoForm form = new ServicoForm();
			servicoService.excluirServico(idServico);		
			form.setListaServicosDisponiveis(paramservicoService.recuperaTodosParametrosPorTipoSemAssinatura(user));		
			map.addAttribute("listaMeusServicos", servicoService.recuperarServicosDisponiveisPorUsuario(user.getId()));
			map.addAttribute("servicoForm", form);
			return DIR_PATH + "listarMeusServicos";
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - excluirSolServico");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/cancelarServico", method = RequestMethod.GET)	
    public String cancelarServico(HttpSession session,
    							  HttpServletRequest request,
			   					  ModelMap map){		
		try {
			ServicoForm form = new ServicoForm();
			servicoService.cancelarServico(request);
			map.addAttribute("servicoForm", form);
			return DIR_PATH + "listarMeusServicos";
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - cancelarServico");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/excluirSolPlano/{idPlano}", method = RequestMethod.GET)	
    public String excluirSolPlano(@PathVariable("idPlano") Long idPlano,
    							  HttpSession session, 
			   					  ModelMap map){	
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ServicoForm form = new ServicoForm();		
			planousuarioService.excluirPlanousuario(idPlano);		
			form.setListaPlanosDisponiveis(planoService.listarTodosDisponiveis());
			map.addAttribute("listaMeusPlanos", planousuarioService.recuperarPlanosPorUsuario(user.getId()));
			map.addAttribute("servicoForm", form);
			return DIR_PATH + "listarMeusPlanos";
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - excluirSolPlano");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/efetuarPagtoSolServico/{idServico}", method = RequestMethod.GET)	
    public String efetuarPagtoSolServico(@PathVariable("idServico") Long idServico,
    								     HttpSession session, 
			   						     ModelMap map){	
		
		try {
			ServicoForm form = servicoService.carregaServicoForm(idServico);		
			 if ( form.getFormaPagtoSelecionado().equals("Pag Seguro")){   
				 if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo()))				 					 
					 form.setOpcao("servicoImovel");			 
				 else if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()) || form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo()))								 
					 form.setOpcao("servico");
				 
				 map.addAttribute("servicoForm", form);
				 return DIR_PATH + "confirmarPagtoPagSeguro";                                            
			 }
			 else if ( form.getFormaPagtoSelecionado().equals("Pay Pal")){  
				 if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo()))				 					 
					 form.setOpcao("servicoImovel");
				 
				 else if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()) || form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo()))				 					
					 form.setOpcao("servico");			 
				 
				 map.addAttribute("servicoForm", form);
				 return DIR_PATH + "confirmarPagtoPayPalServico";
			 }
			 return null;
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - excluirSolPlano");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }	
	
	
	
	@RequestMapping(value = "/efetuarPagtoSolPlano/{idPlano}", method = RequestMethod.GET)
    public String efetuarPagtoPlano(@PathVariable("idPlano") Long idPlano,
    							    HttpSession session, 
			   						ModelMap map){ 		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 ServicoForm form = planousuarioService.carregarPlanousuario(idPlano);
			 form.setIdUsuario(user.getId());
			 form.setOpcao("plano");		 
			 map.addAttribute("servicoForm", form);
			 
			 if ( form.getFormaPagtoSelecionado().equals("Pag Seguro"))				 					 
				 return DIR_PATH_PLANO + "confirmarPagtoPlanoPagSeguro";
			 else if ( form.getFormaPagtoSelecionado().equals("Pay Pal"))
				 return DIR_PATH_PLANO + "confirmarPagtoPayPalPlano";
			 
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - efetuarPagtoSolPlano");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 
	}
	
	@RequestMapping(value = "/simularRetornoPagtoAssinatura", method = RequestMethod.POST)
    public String simularRetornoPagtoAssinatura(HttpSession session, 
			   									ModelMap map,
			   									@ModelAttribute("servicoForm") ServicoForm form){
		
		try {
			servicoService.simulaRetornoPagtoAssinaturaUsuario(new Long(form.getIdSimulaServico()));  
			if ( form.getFormaPagtoSelecionado().equals("Pag Seguro"))
				return DIR_PATH_ASSINATURA_USUARIO + "confirmarPagtoPagSeguroAssinaturaUsuario";		
			else if ( form.getFormaPagtoSelecionado().equals("Pay Pal"))
				return DIR_PATH_ASSINATURA_USUARIO + "confirmarPagtoPayPalAssinaturaUsuario";
			
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - simularRetornoPagtoAssinatura");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/selecionarRelatorio", method = RequestMethod.POST)
	public String selecionarRelatorio(HttpSession session, 
									  ModelMap map,
									  @ModelAttribute("servicoForm") ServicoForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			form.setIdUsuario(user.getId());
			servicoService.selecionarPagamentoServico(form, form.getIdInfoServico(), Long.parseLong(form.getFormaPagtoServico()));
			form.setOpcao("relatorio");
			if ( form.getFormaPagtoSelecionado().equals("Pag Seguro")){                  
	            servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());            
	            form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(form.getIdUsuario(), form.getAcao()));                
	            return DIR_PATH_REL_COBRANCA_SERVICO + "confirmarPagtoPagSeguroRelatorio";                                            
	        }
	        else if ( form.getFormaPagtoSelecionado().equals("Pay Pal")){                                
	        	servicoService.cadastrarSolicitacaoPagtoServico(form, form.getOpcaoTempoPagto());            
	        	form.setIdServicoGerado(servicoService.recuperarUltimoIdServicoPorUsuario(form.getIdUsuario(), form.getAcao()));                
	            return DIR_PATH_REL_COBRANCA_SERVICO + "confirmarPagtoPayPalRelatorio";
	        }             
	        return null;
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - selecionarRelatorio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/simularRetornoPagto/{idServico}", method = RequestMethod.GET)    
    public String simularRetornoPagto(@PathVariable("idServico") Long idServico,    		
    								  HttpSession session, 
			  						  ModelMap map){
		
		try {
			servicoService.simulaRetornoPagto(idServico);    	
	    	UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ServicoForm form = new ServicoForm();		
			form.setListaServicosDisponiveis(paramservicoService.recuperaTodosParametrosPorTipoSemAssinatura(user));		
			map.addAttribute("listaMeusServicos", servicoService.recuperarServicosDisponiveisPorUsuario(user.getId()));
			map.addAttribute("servicoForm", form);
			return DIR_PATH + "listarMeusServicos";	
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - simularRetornoPagto");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
	@RequestMapping(value = "/simularRetornoPagtoPlano/{idPlano}", method = RequestMethod.GET)    
    public String simularRetornoPagtoPlano(@PathVariable("idPlano") Long idPlano,
    									   HttpSession session,
    									   ModelMap map,
    									   @ModelAttribute("servicoForm") ServicoForm form){ 		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			planousuarioService.simulaRetornoPagtoPlano(idPlano);
			map.addAttribute("listaMeusPlanos", planousuarioService.recuperarPlanosPorUsuario(user.getId()));
			map.addAttribute("servicoForm", form);    	
	    	return DIR_PATH + "listarMeusPlanos";	
		} catch (Exception e) {
			log.error("Erro metodo - ServicoController - simularRetornoPagtoPlano");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
    
}
