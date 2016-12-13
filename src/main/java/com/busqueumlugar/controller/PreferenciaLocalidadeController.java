package com.busqueumlugar.controller;

import java.util.List;

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
import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelcomentarioForm;
import com.busqueumlugar.form.PreferencialocalidadeForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Preferencialocalidade;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.PreferencialocalidadeService;
import com.busqueumlugar.util.ParametrosUtils;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;
import com.mysql.jdbc.StringUtils;

@Controller("preferenciaLocalidadeController")
@RequestMapping("/preferencia")
@SessionAttributes({"preferenciaLocalidadeForm"})
public class PreferenciaLocalidadeController {
	
	private static final Logger log = LoggerFactory.getLogger(PreferenciaLocalidadeController.class);
	
	@Autowired
	private PreferencialocalidadeService prefLocalidadeService;
	
	@Autowired
	private EstadosService estadosService;

	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	private static final String DIR_PATH = "/imovel/preferencia/";
	private static final String DIR_PATH_CADASTRO_USUARIO = "/usuario/cadastro/";
	
	private static final String TIPO_VISUALIZACAO = "preferenciaImoveis";
	
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("preferenciaLocalidadeForm") PreferencialocalidadeForm form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("preferenciaLocalidadeForm") PreferencialocalidadeForm form,	
											  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	
	@RequestMapping(value = "/listarPreferencias", method = RequestMethod.GET)
	public String listarPreferencias(ModelMap map, 
									 HttpSession session){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			PreferencialocalidadeForm form = new PreferencialocalidadeForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("listaPreferenciaImoveis", prefLocalidadeService.listarPreferenciaPorUsuario(user.getId()));
			map.addAttribute("preferenciaLocalidadeForm", form);		
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "preferenciasImoveis");
			return DIR_PATH + "visualizarPreferenciaImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController - listarPreferencias");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}    
	}
	
	@RequestMapping(value = "/inicioCadastroUsuarioPreferenciaImoveis/{idUsuario}")
    public String inicioCadastroUsuarioPreferenciaImoveis(@PathVariable("idUsuario") Long idUsuario,
    										  			  ModelMap map, 
    										  			  HttpSession session){
		
		try {
			PreferencialocalidadeForm prefForm = new PreferencialocalidadeForm();
			prefForm.setListaEstados(estadosService.listarTodosEstadosSelect());
			prefForm.setIdUsuario(idUsuario);
			map.addAttribute("listaPreferenciaImoveis", prefLocalidadeService.listarPreferenciaPorUsuario(idUsuario));
			map.addAttribute("preferenciaLocalidadeForm", prefForm);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "preferenciasImoveis");		
	    	return DIR_PATH_CADASTRO_USUARIO + "inicioCadastrarPreferencias";
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController - inicioCadastroUsuarioPreferenciaImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
    }
	
	@RequestMapping(value = "/adicionarPreferencia", method = RequestMethod.POST)
	public String adicionarPreferencia(@ModelAttribute("preferenciaLocalidadeForm") PreferencialocalidadeForm form,
									   ModelMap map, 
									   BindingResult result,										
									   HttpSession session){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			boolean possuiErro = prefLocalidadeService.validarCadastroPreferencia(form, result);
			if ( ! possuiErro)		
				prefLocalidadeService.adicionarPreferencia(user.getId(), form);
			
			map.addAttribute("listaPreferenciaImoveis", prefLocalidadeService.listarPreferenciaPorUsuario(user.getId()));
			map.addAttribute("preferenciaLocalidadeForm", form);		
			return DIR_PATH + "visualizarPreferenciaImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController - adicionarPreferencia");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}    
	}
	
	@RequestMapping(value = "/direcionarMain")
	public String direcionarMain(@ModelAttribute("preferenciaLocalidadeForm") PreferencialocalidadeForm form,
								 ModelMap map, 
								 HttpSession session){
		try {
			List<Preferencialocalidade> lista = prefLocalidadeService.listarPreferenciaPorUsuario(form.getIdUsuario());
			if (( lista == null ) || ( lista != null && lista.size() == 0 )){
				map.addAttribute("preferenciaLocalidadeForm", form);
				map.addAttribute("msgErro", "S");
		    	return DIR_PATH_CADASTRO_USUARIO + "inicioCadastrarPreferencias";
			}
			else 
				return "forward:/usuario/submitLoginFirst"; 
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController - direcionarMain");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	
	
	@RequestMapping(value = "/adicionarPreferenciaInicioCadastroUsuario", method = RequestMethod.POST)
	public String adicionarPreferenciaInicioCadUsuario(@ModelAttribute("preferenciaLocalidadeForm") PreferencialocalidadeForm form,
													   ModelMap map){		
		try {
			prefLocalidadeService.adicionarPreferencia(form.getIdUsuario(), form);
			map.addAttribute("listaPreferenciaImoveis", prefLocalidadeService.listarPreferenciaPorUsuario(form.getIdUsuario()));
			map.addAttribute("preferenciaLocalidadeForm", form);
			return DIR_PATH_CADASTRO_USUARIO + "inicioCadastrarPreferencias"; 
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController - adicionarPreferenciaInicioCadUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}    
	}
	
	@RequestMapping(value = "/finalizarCadastroUsuario", method = RequestMethod.POST)
	public String finalizarCadastroUsuario(@ModelAttribute("preferenciaLocalidadeForm") PreferencialocalidadeForm form,
										   ModelMap map){		
		try {
			map.addAttribute("msgSucesso", "S");
			return DIR_PATH_CADASTRO_USUARIO + "inicioCadastrarPreferencias"; 
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController - finalizarCadastroUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}   
	}
	
	@RequestMapping(value = "/excluirPreferencia/{idPreferencia}", method = RequestMethod.GET)
	public String excluirPreferencia(@ModelAttribute("preferenciaLocalidadeForm") PreferencialocalidadeForm form, 
									 @PathVariable Long idPreferencia, 
									 ModelMap map, 
									 HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			session.setAttribute(ParametrosUtils.TIPO_VISUALIZACAO, TIPO_VISUALIZACAO);
			String msgRetorno = prefLocalidadeService.validarExclusaoPrefencia(user);
			if ( StringUtils.isNullOrEmpty(msgRetorno) ){
				prefLocalidadeService.excluirPreferencia(idPreferencia);		
				map.addAttribute("listaPreferenciaImoveis", prefLocalidadeService.listarPreferenciaPorUsuario(user.getId()));				
			}
			else
				map.addAttribute("msgError", msgRetorno);
			
			map.addAttribute("preferenciaLocalidadeForm", form);
			return DIR_PATH + "visualizarPreferenciaImoveis"; 
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController - excluirPreferencia");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/confirmarExclusaoPreferncia/{idPreferencia}", method = RequestMethod.GET)
	public String confirmarExclusaoPreferncia(@PathVariable Long idPreferencia, 
											  @ModelAttribute("preferenciaLocalidadeForm") PreferencialocalidadeForm form,		
									 		  ModelMap map, 
									 		  HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			String msgRetorno = prefLocalidadeService.validarExclusaoPrefencia(user);
			if ( StringUtils.isNullOrEmpty(msgRetorno) ){
				prefLocalidadeService.excluirPreferencia(idPreferencia);
				map.addAttribute("listaPreferenciaImoveis", prefLocalidadeService.listarPreferenciaPorUsuario(user.getId()));
			}
			else
				map.addAttribute("msgError", msgRetorno);
			
			map.addAttribute("preferenciaLocalidadeForm", form);		
			return DIR_PATH + "visualizarPreferenciaImoveis"; 
		} catch (Exception e) {
			log.error("Erro metodo - PreferenciaLocalidadeController - confirmarExclusaoPreferncia");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}   
	}
}
