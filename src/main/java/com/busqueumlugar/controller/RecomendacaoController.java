package com.busqueumlugar.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.form.RecomendacaoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Recomendacao;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.RecomendacaoService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.UsuarioInterface;



@Controller("recomendacaoController")
@RequestMapping("/recomendacao")
public class RecomendacaoController {
	
	private static final Logger log = LoggerFactory.getLogger(RecomendacaoController.class);
	
	private static final String DIR_PATH  = "/recomendacao/";
	
	
	@Autowired
	private RecomendacaoService recomendacaoService;
	
	
	@RequestMapping(value = "/listarRecomendacoesRecebidas", method = RequestMethod.GET)
	public String listarRecomendacoesRecebidas(ModelMap map, 
											   HttpSession session){
		
		try {
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasRecomendacoes");
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("recomendacaoForm", new RecomendacaoForm());		
			map.addAttribute("listaRecomendacoes", recomendacaoService.recuperarRecomendacoesPorIdUsuarioRecomendadoPorStatus(user.getId(), RecomendacaoStatusEnum.ENVIADO.getRotulo()));		
			int quantNovasRecomendacoes = Integer.parseInt(session.getAttribute(RecomendacaoService.QUANT_NOVAS_RECOMENDACOES).toString());
			if ( quantNovasRecomendacoes > 0 ){		
		//		recomendacaoService.atualizarStatusLeituraRecomendacoesPorIdUsuario(lista);
				session.setAttribute(RecomendacaoService.QUANT_NOVAS_RECOMENDACOES, 0);
			}		
			return DIR_PATH + "listarRecomendacoes";
		} catch (Exception e) {
			log.error("Erro metodo - RecomendacaoController - listarRecomendacoesRecebidas");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/visualizarRecomendacaoSelecionado/{idRecomendacao}", method = RequestMethod.GET)
	public String visualizarRecomendacaoSelecionado(@PathVariable Long idRecomendacao,
												    ModelMap map, 	
											   		HttpSession session) {
		try {
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMinhasRecomendacoes");
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			Recomendacao rec = recomendacaoService.recuperarRecomendacaoPorId(idRecomendacao);
			map.addAttribute("recomendacaoSelecionada", rec);
			recomendacaoService.atualizarStatusLeituraRecomendacaoSelecionada(rec);
			long quantNovasRecomendacoes = recomendacaoService.checarNovasRecomendacoesRecebidas(user.getId());
			session.setAttribute(RecomendacaoService.QUANT_NOVAS_RECOMENDACOES, quantNovasRecomendacoes);
			if ( quantNovasRecomendacoes > 0 ){
				session.setAttribute(RecomendacaoService.LISTA_RECOMENDACOES, recomendacaoService.recuperarRecomendacoesPorIdUsuarioRecomendadoPorStatus(user.getId(), RecomendacaoStatusEnum.ENVIADO.getRotulo()));
				session.setAttribute(RecomendacaoService.QUANT_RECOMENDACOES,0); 
			}
			else if ( quantNovasRecomendacoes <= 0 ){
				List<Recomendacao> lista =  recomendacaoService.recuperarRecomendacoesPorIdUsuarioRecomendadoPorStatus(user.getId(), RecomendacaoStatusEnum.ENVIADO.getRotulo());
				session.setAttribute(RecomendacaoService.LISTA_RECOMENDACOES, lista);
				session.setAttribute(RecomendacaoService.QUANT_RECOMENDACOES, AppUtil.recuperarQuantidadeLista(lista));
			}
			map.addAttribute("recomendacaoForm", new RecomendacaoForm());		
			return DIR_PATH + "visualizarRecomendacaoSelecionado";
		} catch (Exception e) {
			log.error("Erro metodo - RecomendacaoController - visualizarRecomendacaoSelecionado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/responderRecomendacao/{respostaRecomendacao}/{idRecomendacao}", method = RequestMethod.GET)
	@ResponseBody
    public String responderRecomendacao(@PathVariable Long idRecomendacao, 
    							   		@PathVariable String respostaRecomendacao, 
    							   		ModelMap map, 
    							   		HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			if ( respostaRecomendacao.equals("aceitar") || respostaRecomendacao.equals("recusar")) {
				recomendacaoService.respostaRecomendacao(user.getId(), idRecomendacao, respostaRecomendacao);			
				return "ok";
			}
			return "erro";
		} catch (Exception e) {
			log.error("Erro metodo - RecomendacaoController - responderRecomendacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return "erro";
		}
    }
	
	@RequestMapping(value = "/responderRecomendacaoSelecionada/{respostaRecomendacao}/{idRecomendacao}", method = RequestMethod.GET)
	@ResponseBody
    public String responderRecomendacaoSelecionada(@PathVariable Long idRecomendacao, 
    							   				   @PathVariable String respostaRecomendacao, 
    							   				   ModelMap map, 
    							   				   HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			//map.addAttribute("listaConvitesRecebidos", contatoService.recuperarConvites(user.getId()));		
			if ( respostaRecomendacao.equals("aceitar") || respostaRecomendacao.equals("recusar")) {
				recomendacaoService.respostaRecomendacao(user.getId(), idRecomendacao, respostaRecomendacao);
				int quantNovasRecomendacoes = Integer.parseInt(session.getAttribute(RecomendacaoService.QUANT_NOVAS_RECOMENDACOES).toString());
				if (quantNovasRecomendacoes > 0 )
					session.setAttribute(RecomendacaoService.QUANT_NOVAS_RECOMENDACOES, quantNovasRecomendacoes - 1);
				
				return "ok";
			}
			return "erro";
		} catch (Exception e) {
			log.error("Erro metodo - RecomendacaoController - responderRecomendacaoSelecionada");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return "erro";
		}
    }

}
