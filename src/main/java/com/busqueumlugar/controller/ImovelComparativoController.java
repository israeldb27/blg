package com.busqueumlugar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.UsuarioInterface;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;

@Controller("imovelComparativoController")
@RequestMapping("/imovelComparativo")
public class ImovelComparativoController {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelComparativoController.class);
	
	@Autowired
	private ImovelService imovelService;
	
	private static final String DIR_PATH = "/imovel/comparativo/";
	
	
	@RequestMapping(value = "/listarImoveisComparativos", method = RequestMethod.GET)
	public String goListarImoveisComparativos(HttpSession session, ModelMap map){
		
		try {
			@SuppressWarnings("unchecked")
			List<Long> lista = (List<Long>)session.getAttribute(ImovelService.LISTA_IMOVEL_COMPARATIVO);
			if ( ! CollectionUtils.isEmpty(lista)  ){
				List<Imovel> listaImovel = imovelService.carregaListaImoveisComparativos(lista);
				map.addAttribute("listaMeusComparativos", listaImovel);
			}		
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarImoveisComparativos");
			return DIR_PATH + "listaImovelComparativo";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelComparativoController -  goListarImoveisComparativos");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/excluirImovelComparativo/{idImovel}", method = RequestMethod.GET)
	@ResponseBody
	public String excluirImovelComparativo(@PathVariable("idImovel") Long idImovel,
										   HttpSession session, 
										   ModelMap map){		
		try {
			List<Long> lista = (List<Long>)session.getAttribute(ImovelService.LISTA_IMOVEL_COMPARATIVO);
			lista.remove(idImovel);
			if ( lista != null && lista.size() > 0 ){
				session.setAttribute(ImovelService.LISTA_IMOVEL_COMPARATIVO, lista );
				session.setAttribute(ImovelService.QUANT_LISTA_IMOVEL_COMPARATIVO, lista.size());			
				List<Imovel> listaImovel = imovelService.carregaListaImoveisComparativos(lista);
				map.addAttribute("listaMeusComparativos", listaImovel);
			}		
			else {
				session.setAttribute(ImovelService.LISTA_IMOVEL_COMPARATIVO, null );
				session.setAttribute(ImovelService.QUANT_LISTA_IMOVEL_COMPARATIVO, 0);
			}		
			return "ok";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelComparativoController -  excluirImovelComparativo");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return "erro";
		}
		
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/adicionarImovelComparativo/{idImovel}",  method = RequestMethod.GET)
	@ResponseBody 
	public String adicionarImovelComparativo(@PathVariable Long idImovel, 
				   		   				     HttpServletResponse response, 
				   		   				     ModelMap map,
				   		   				     HttpSession session){
		
		try {
			List<Long> listaIdImovelComparativo = (List<Long>)session.getAttribute(ImovelService.LISTA_IMOVEL_COMPARATIVO);
			if (listaIdImovelComparativo == null ){
				listaIdImovelComparativo = new ArrayList<Long>();			
				listaIdImovelComparativo.add(idImovel);
				session.setAttribute(ImovelService.LISTA_IMOVEL_COMPARATIVO, listaIdImovelComparativo );
				session.setAttribute(ImovelService.QUANT_LISTA_IMOVEL_COMPARATIVO, listaIdImovelComparativo.size());			
			}	
			else{
				if ((! listaIdImovelComparativo.contains(idImovel) ) && (listaIdImovelComparativo.size() < 2 )){ // tamanho maximo provisorio
					listaIdImovelComparativo.add(idImovel);
					session.setAttribute(ImovelService.LISTA_IMOVEL_COMPARATIVO, listaIdImovelComparativo );
					session.setAttribute(ImovelService.QUANT_LISTA_IMOVEL_COMPARATIVO, listaIdImovelComparativo.size());	
				}
				else {
					if (listaIdImovelComparativo.size() >= 2 )
						return MessageUtils.getMessage("lbl.msg.limite.add.comparativo");
				}
			}
			return "ok";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelComparativoController -  adicionarImovelComparativo");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}	
	
}
