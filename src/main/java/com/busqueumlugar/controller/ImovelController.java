package com.busqueumlugar.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.busqueumlugar.config.WebApplicationContext;
import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.AcaoNotificacaoEnum;
import com.busqueumlugar.enumerador.NotaAcaoEnum;
import com.busqueumlugar.enumerador.TipoNotificacaoEnum;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelMapaForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.service.AtividadesService;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelPropostasService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelcomentarioService;
import com.busqueumlugar.service.ImoveldestaqueService;
import com.busqueumlugar.service.ImovelfotosService;
import com.busqueumlugar.service.ImovelvisualizadoService;
import com.busqueumlugar.service.IntermediacaoService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.ParceriaService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;
import com.mysql.jdbc.StringUtils;


@Controller("imovelController")
@RequestMapping("/imovel")
@SessionAttributes({"imovelForm"})
public class ImovelController {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelController.class);
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ImovelvisualizadoService imovelvisitadoService;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private NotaService  notaService;
	
	@Autowired
	private AtividadesService atividadesService;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private WebApplicationContext  appContext;
	
	private static final String DIR_PATH = "/imovel/";
	private static final String DIR_PATH_EDICAO = "/imovel/edicao/";	
	private static final String DIR_PATH_EXPANSAO = "/imovel/expansao/";	
	private static final String DIR_PATH_CADASTRO = "/imovel/cadastro/";
	
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ImovelcomentarioService imovelComentarioService;
	
	@Autowired
	private  ImovelPropostasService imovelPropostasservice;
	
	@Autowired
	private IntermediacaoService intermediacaoService;
	
	@Autowired
	private ParceriaService parceriaService; 	
	
	@Autowired
	private ImovelfotosService imovelfotosService;	
	
	@Autowired
	private ImoveldestaqueService imoveldestaqueService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	
	@RequestMapping(value = "/buscarCidades/{idEstado}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaCidadePorEstado(@PathVariable("idEstado") Integer idEstado, 
    										  @ModelAttribute("imovelForm") ImovelForm form,	
											  ModelMap map)  {       
		try {		
			form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(idEstado));
	        return form.getListaCidades();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  populaCidadePorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }		
	
	@RequestMapping(value = "/buscarBairros/{idCidade}", method = RequestMethod.GET)
    @ResponseBody
    public List<Select> populaBairroPorEstado(@PathVariable("idCidade") Integer idCidade,
    										  @ModelAttribute("imovelForm") ImovelForm form,	
											  ModelMap map)  {
        
		try {			
			form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(idCidade));
	        return form.getListaBairros();
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  populaBairroPorEstado");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
    }
	
	
	@RequestMapping(value = "/carregaTimeline/{param}", method = RequestMethod.GET)
    @ResponseBody
    public String carregaTimeline(@PathVariable("param") String param)  {		
          
        return imovelService.carregarTimeLine2();
    }		
	
	
	@RequestMapping(value = "/adicionarConfiguracaoAnuncioImovel/{dataInicio}/{dataFim}")	
	@ResponseBody
	public String adicionarConfiguracaoAnuncioImovel(@PathVariable("dataInicio") String dataInicio,
											     	 @PathVariable("dataFim") String dataFim,											   
											     	 HttpSession session, 	
											     	 ModelMap map, 											   
											     	 @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			String msg = imoveldestaqueService.validarCadastroAnuncioImovel(dataInicio, dataFim, form);
			if ( msg.equals("")){
				imoveldestaqueService.cadastrarAnuncioImovel(dataInicio, dataFim, form);
				form.setListaImovelAnuncio(imoveldestaqueService.recuperarListaAnuncioPorImovel(form.getId()));
				map.addAttribute("imovelForm", form );
				return "ok";
			}
			else {
				return msg;
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  adicionarConfiguracaoAnuncioImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
	}

	
	@RequestMapping(value = "/carregaModalDetalhesImovel/{idImovel}")
	@ResponseBody
	public ImovelForm carregaModalDetalhesImovel(@PathVariable("idImovel") Long idImovel,
											 ModelMap map){	
		
		try {
			return imovelService.carregarImovelFormPorImovel(idImovel);
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  carregaModalDetalhesImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}
	}
	
	@RequestMapping(value = "/testeImovel")	
	public String testeImovel(ModelMap map){		
		ImovelForm form = new ImovelForm();
		map.addAttribute("imovelForm", form );
		return DIR_PATH + "testeImovel";
	}
	
	@RequestMapping(value = "/preparaAlteracaoGaleriaFotosImovel/{idImovel}")	
	public String preparaAlteracaoGaleriaFotosImovel(@PathVariable("idImovel") Long idImovel,			
		 	   										 HttpSession session,
												   	 ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			String msgRetorno = imovelService.validarPrepararImovel(idImovel, user);
			if (StringUtils.isNullOrEmpty(msgRetorno)){
				ImovelForm form = imovelService.carregaGaleriaFotosImovel(idImovel, true);
				map.addAttribute("imovelForm", form );
				return DIR_PATH_EDICAO + "alterarGaleriaFotosImovel";
			}
			else {			
				map.addAttribute("mensagemErro", msgRetorno);
				return ImovelService.PATH_ERRO_IMOVEL;
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  preparaAlteracaoGaleriaFotosImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
		
	}
	
	@RequestMapping(value = "/incluirFotoGaleriaImovel", method=RequestMethod.POST)	
	public String incluirFotoGaleriaImovel(@ModelAttribute("imovelForm") ImovelForm form,
									 	   @RequestParam("name") String name,
									 	   @RequestParam("file") MultipartFile file,
									 	   ModelMap map, 
									 	   HttpSession session){		
		
		try {
			String msgRetorno = imovelfotosService.validarAdicionarFotos(file);
			if ( msgRetorno.equals("")){
				try {
	            	imovelfotosService.adicionarFotos(form.getId(), file.getBytes(), "");
	            	form = imovelService.carregaGaleriaFotosImovel(form.getId(), true);
	                map.addAttribute("imovelForm", form );
	                map.addAttribute("msgSucesso", "S");
	            } catch (Exception e) {
	            	map.addAttribute("msgErro", MessageUtils.getMessage("msg.erro.add.foto.imovel.galeria"));
	            }
			}
			else 
				map.addAttribute("msgErro", msgRetorno);		
			
			return DIR_PATH_EDICAO + "alterarGaleriaFotosImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  incluirFotoGaleriaImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
				
	}
	
	@RequestMapping(value = "/excluirFotoGaleria")	
	public String excluirFotoGaleria(HttpServletRequest request,
									 ModelMap map){
		
		try {
			Long idImovelFoto = Long.valueOf(request.getParameter("idFotoImovel").toString());
			Long idImovel = Long.valueOf(request.getParameter("idImovel").toString());
			imovelfotosService.excluirFoto(idImovelFoto);
			ImovelForm form = imovelService.carregaGaleriaFotosImovel(idImovel, true);
			map.addAttribute("imovelForm", form );
			return DIR_PATH_EDICAO + "alterarGaleriaFotosImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  excluirFotoGaleria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/excluirFotoGaleriaModal")	
	public String excluirFotoGaleriaModal(HttpServletRequest request,
									 	  ModelMap map){
		
		try {
			Long idImovelFoto = Long.valueOf(request.getParameter("idFotoImovel").toString());
			Long idImovel = Long.valueOf(request.getParameter("idImovel").toString());
			imovelfotosService.excluirFoto(idImovelFoto);
			ImovelForm form = imovelService.carregaGaleriaFotosImovel(idImovel, true);
			map.addAttribute("imovelForm", form );
			return DIR_PATH_EDICAO + "alterarGaleriaFotosImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  excluirFotoGaleriaModal");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}
	
	@RequestMapping(value = "/preparaAlteraFotoPrincipalImovel/{idImovel}")	
	public String preparaAlteraFotoPrincipalImovel(@PathVariable("idImovel") Long idImovel,
												   HttpSession session,
												   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			String msgRetorno = imovelService.validarPrepararImovel(idImovel, user);
			if ( StringUtils.isNullOrEmpty(msgRetorno)){
				ImovelForm form = imovelService.carregarImovelFormPorImovel(idImovel);
				map.addAttribute("imovelForm", form );
				return DIR_PATH_EDICAO + "alterarFotoPrincipalImovel";
			}
			else {
				map.addAttribute("mensagemErro", msgRetorno);
				return ImovelService.PATH_ERRO_IMOVEL;
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  preparaAlteraFotoPrincipalImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/alterarFotoPrincipalImovel", method=RequestMethod.POST)	
	public String alterarFotoPrincipalImovel(@ModelAttribute("imovelForm") ImovelForm form,
									 		 @RequestParam("name") String name,
									 		 @RequestParam("file") MultipartFile file,
									 		 ModelMap map, 
									 		 HttpSession session){
		
	 	if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();                
                form.setFotoPrincipal(bytes);
                UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
                form = imovelService.atualizarImovel(form, user);
                map.addAttribute("imovelForm", form );
                return DIR_PATH_EDICAO + "alterarFotoPrincipalImovel";
            } catch (Exception e) {
            	log.error("Erro metodo - ImovelController -  alterarFotoPrincipalImovel");
    			log.error("Mensagem Erro: " + e.getMessage());
    			map.addAttribute("mensagemErroGeral", "S");
    			return ImovelService.PATH_ERRO_GERAL;            	
            }
        } 				
		return null;
	}
	
	
	@RequestMapping(value = "/testeAddFotoImovel", method=RequestMethod.POST)	
	public String testeAddFotoImovel(@ModelAttribute("imovelForm") ImovelForm form,
									 @RequestParam("name") String name,
									 @RequestParam("file") MultipartFile file,
									 ModelMap map, 
									 HttpSession session){		
		try {
			if (!file.isEmpty()) {
	            try {
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name)));
	                stream.write(bytes);
	                stream.close();
	                return "You successfully uploaded " + name + "!";
	            } catch (Exception e) {
	                return "You failed to upload " + name + " => " + e.getMessage();
	            }
	        } 		
			map.addAttribute("imovelForm", form );
			return DIR_PATH + "testeImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  testeAddFotoImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	 	
	}
	
	@RequestMapping(value = "/adicionarSolIntermediacaoDetalheImovel/{novaSolIntermediacao}" )
	public @ResponseBody String adicionarSolIntermediacao(@PathVariable("novaSolIntermediacao") String novaSolIntermediacao,
										    			  HttpSession session, 	
										    			  ModelMap map, 
										    			  @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			String msg = intermediacaoService.validarSolicitacaoIntermediacao(user.getId(), form.getId(), form.getPerfilUsuario(), novaSolIntermediacao);
			if ( StringUtils.isNullOrEmpty(msg) ) {
				intermediacaoService.cadastrarSolicitacaoIntermediacao(user.getId(), "", form.getId(), novaSolIntermediacao);
			//	form.setIntermediacaoEnviada(intermediacaoService.recuperarMinhasSolicitacoesPorUsuarioSolPorImovel(user.getId(), form.getId()));
				map.addAttribute("imovelForm", form );
				msg = "ok"; 
			}
			return msg;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  adicionarSolIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}	
	
	@RequestMapping(value = "/adicionarSolParceriaDetalheImovel/{novaSolParceria}")
	public @ResponseBody String adicionarSolParceriaDetalheImovel(@PathVariable("novaSolParceria") String novaSolParceria,
															      HttpSession session, 	
															      ModelMap map, 
															      @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			String msg = parceriaService.validarSolicitacaoParceria(user.getId(), form.getId(), form.getPerfilUsuario(), novaSolParceria);
			if ( StringUtils.isNullOrEmpty(msg) ) {
				parceriaService.cadastrarSolicitacaoParceria(user.getId(), "", form.getId(), novaSolParceria);
				//form.setParceriaEnviada(parceriaService.recuperarMinhasSolicitacoesPorUsuarioSolPorImovelParceria(user.getId(), form.getId()));
				map.addAttribute("imovelForm", form );
				msg = "ok"; 
			}
			return msg;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  adicionarSolParceriaDetalheImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/cancelarSolIntermediacaoDetalheImovel" , method = RequestMethod.POST)	
	@ResponseBody
	public String cancelarSolIntermediacao(HttpSession session, 	
										   ModelMap map, 
										   @ModelAttribute("imovelForm") ImovelForm form){
		
		try{
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);										   
			intermediacaoService.excluirSolicitacaoIntermediacao(user.getId(), form.getId() );
			form.setIntermediacaoEnviada(null);
			map.addAttribute("imovelForm", form );
			return "ok";
		}
		catch(Exception e) {
			log.error("Erro metodo - ImovelController -  cancelarSolIntermediacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return "erro";
		}		
	}
	
	@RequestMapping(value = "/cancelarSolParceriaDetalheImovel", method = RequestMethod.POST)	
	@ResponseBody
	public String cancelarSolParceria(HttpSession session, 	
									  ModelMap map, 
									  @ModelAttribute("imovelForm") ImovelForm form){
		try{
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);										   
			intermediacaoService.excluirSolicitacaoIntermediacao(user.getId(), form.getId() );
			form.setParceriaEnviada(null);
			map.addAttribute("imovelForm", form );
			return "ok";
		}catch(Exception e){
			log.error("Erro metodo - ImovelController -  cancelarSolParceria");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return "erro";
		}
	}
	
	@RequestMapping(value = "/notificarFecharNegocio" , method = RequestMethod.POST)	
	@ResponseBody			
	public String notificarFecharNegocio(HttpSession session, 	
										 ModelMap map, 
										 @ModelAttribute("imovelForm") ImovelForm form){
		
		try{	
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	
			notificacaoService.cadastrarNotificacao(form.getId(), 
													AcaoNotificacaoEnum.FECHAR_NEGOCIO.getRotulo(),
													user.getId(),
													TipoNotificacaoEnum.IMOVEL.getRotulo());
			map.addAttribute("imovelForm", form );
			return "ok";
		}
		catch(Exception e) {
			log.error("Erro metodo - NotificacaoController -  notificarFecharNegocio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return "erro";
		}		
	}
	
	@RequestMapping(value = "/notificarMarcarVisita" , method = RequestMethod.POST)	
	@ResponseBody
	public String notificarMarcarVisita(HttpSession session, 	
										 ModelMap map, 
										 @ModelAttribute("imovelForm") ImovelForm form){
		
		try{
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			notificacaoService.cadastrarNotificacao(form.getId(), 
													AcaoNotificacaoEnum.MARCAR_VISITA.getRotulo(),
													user.getId(),
													TipoNotificacaoEnum.IMOVEL.getRotulo());
			map.addAttribute("imovelForm", form );
			return "ok";
		}
		catch(Exception e) {
			log.error("Erro metodo - NotificacaoController -  notificarFecharNegocio");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return "erro";
		}		
	}
	
	@RequestMapping(value = "/procurarCompradores/{idImovel}" , method = RequestMethod.GET)	
	public String procurarCompradores(@PathVariable Long idImovel,
									  HttpSession session, 	
								      ModelMap map, 
								      @ModelAttribute("imovelForm") ImovelForm form){
		
		try{
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("imovelForm", form );
			map.addAttribute("listaUsuarios", imovelService.pesquisarPossiveisCompradores(user.getId(), form));
			return DIR_PATH + "resultadoProcurarPossiveisCompradores";
		}
		catch(Exception e) {
			log.error("Erro metodo - ImovelController -  procurarCompradores");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return "erro";
		}		
	}
	
	@RequestMapping(value = "/adicionarComentarioDetalheImovel")	
	public String adicionarComentarioDetalheImovel(String novoComentario,
												   HttpSession session, 	
												   ModelMap map, 
												   @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			imovelComentarioService.cadastrarComentario(form.getId(), user.getId(), novoComentario);
			form.setListaComentario(imovelComentarioService.listarComentarios(form.getId(), null)); 
			map.addAttribute("imovelForm", form );		
			return DIR_PATH + "visualizarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  adicionarComentarioDetalheImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/adicionarAtividadeDetalhesImovel/{novaAtividade}/{novaDescricaoAtividade}")
	@ResponseBody
	public String adicionarAtividadeDetalhesImovel(@PathVariable("novaAtividade") String novaAtividade,
											       @PathVariable("novaDescricaoAtividade") String novaDescricaoAtividade,											   
											       HttpSession session, 	
											       ModelMap map, 											   
											       @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			atividadesService.cadastrarAtividade(form, novaAtividade, novaDescricaoAtividade, user);
			form.setListaAtividades(atividadesService.recuperarAtividadesPorIdImovel(form.getId()));
			map.addAttribute("imovelForm", form );
			return "ok";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  adicionarAtividadeDetalhesImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/confirmarExclusaoAtividadeImovel/{idAtividade}", method = RequestMethod.GET)
	@ResponseBody
	public String confirmarExclusaoAtividadeImovel(@PathVariable("idAtividade") Long idAtividade,
											   	  ModelMap map, 											   
											      @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			atividadesService.excluirAtividade(idAtividade);
			form.setListaAtividades(atividadesService.recuperarAtividadesPorIdImovel(form.getId()));
			map.addAttribute("imovelForm", form );
			return "ok";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  confirmarExclusaoAtividadeImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}		
	}
	
	@RequestMapping(value = "/adicionarPropostaDetalheImovel/{novaProposta}/{novaDescricao}")
	@ResponseBody
	public String adicionarPropostaDetalheImovel(@PathVariable("novaProposta") String novaProposta,
											     @PathVariable("novaDescricao") String novaDescricao,											   
											     HttpSession session, 	
											     ModelMap map, 											   
											     @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			String msg = imovelPropostasservice.validarCadastroProposta(form.getId(), novaProposta, user);
			if ( msg.equals("")){
				imovelPropostasservice.cadastrarProposta(form.getId(), user.getId(), novaDescricao, AppUtil.formatarMoeda(novaProposta));				
				form.setListaPropostas(imovelPropostasservice.recuperarPropostasImovel(form.getId()));
				map.addAttribute("imovelForm", form );
				return "ok";
			}
			else {
				return msg;
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  adicionarPropostaDetalheImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/confirmarExclusaoPropostaImovel/{idProposta}", method = RequestMethod.GET)
	@ResponseBody
	public String confirmarExclusaoPropostaImovel(@PathVariable("idProposta") Long idProposta,
											   	  ModelMap map, 											   
											      @ModelAttribute("imovelForm") ImovelForm form){
		
		try {
			imovelPropostasservice.excluirProposta(idProposta);				
			form.setListaPropostas(imovelPropostasservice.recuperarPropostasImovel(form.getId()));
			map.addAttribute("imovelForm", form );
			return "ok";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  confirmarExclusaoPropostaImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}		
	}
	
	@RequestMapping(value = "/confirmarExclusaoMinhaPropostaDetalheImovel/{idImovelproposta}", method = RequestMethod.GET)
	@ResponseBody
	public String confirmarExclusaoMinhaPropostaDetalheImovel(@PathVariable("idImovelproposta") Long idImovelproposta,
											   				  ModelMap map, 											   
											   				  @ModelAttribute("imovelForm") ImovelForm form){
		
		try {			
			imovelPropostasservice.excluirProposta(idImovelproposta);
			form.setListaPropostas(imovelPropostasservice.recuperarPropostasImovel(form.getId()));
			map.addAttribute("imovelForm", form );
			return "ok";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  confirmarExclusaoMinhaPropostaDetalheImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return "erro";
		}
	}
	
	@RequestMapping(value = "/expandirListaImoveisDestaque/{idUsuario}", method = RequestMethod.GET)	
	public String expandirListaImoveisDestaque(@PathVariable Long idUsuario,
									 		   ModelMap map, 
									           HttpSession session){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaImoveisDestaque", imovelService.recuperarImoveisDestaquePorIdUsuario(idUsuario));		
			map.addAttribute("imovelForm", new ImovelForm());		
			map.addAttribute("usuarioForm", usuarioService.recuperarUsuarioPorId(idUsuario));		
			return DIR_PATH_EXPANSAO + "expandirListaImoveisDetalhesUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  expandirListaImoveisDestaque");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}		
	}
	
	@RequestMapping(value = "/expandirListaOutrosImoveis/{idUsuario}", method = RequestMethod.GET)	
	public String expandirListaOutrosImoveis(@PathVariable Long idUsuario,
									 		 ModelMap map, 
									         HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaImoveis", imovelService.listarMeusImoveis(idUsuario, new ImovelForm()));		
			map.addAttribute("imovelForm", new ImovelForm());		
			map.addAttribute("usuarioForm", usuarioService.recuperarUsuarioPorId(idUsuario));		
			return DIR_PATH_EXPANSAO + "expandirListaOutrosImoveisUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  expandirListaOutrosImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/visualizarImoveisPerfilUsuario/{idUsuario}", method = RequestMethod.GET)	
	public String visualizarImoveisPerfilUsuario(@PathVariable Long idUsuario,
									 			 ModelMap map, 
									 			 HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ImovelForm form = new ImovelForm();
			form.setIdUsuarioPerfil(idUsuario);		
		    map.addAttribute("usuarioForm", usuarioService.prepararDetalhesUsuarioForm(idUsuario, user.getId()));
			map.addAttribute("listaImoveisPerfilUsuario",  imovelService.listarMeusImoveis(idUsuario, form));		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("imovelForm", form);				
			return DIR_PATH + "listarImoveisPerfilUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  visualizarImoveisPerfilUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/filtrarImoveisPerfilUsuario", method = RequestMethod.POST)	
	public String filtrarImoveisPerfilUsuario(@ModelAttribute("imovelForm") ImovelForm form, 
									   		  ModelMap map, 
									   		  HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaImoveisPerfilUsuario",  imovelService.buscar(form, form.getIdUsuarioPerfil(), null));		
			map.addAttribute("imovelForm", form);				
			return DIR_PATH + "listarImoveisPerfilUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  filtrarImoveisPerfilUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value="/ordenarImoveisPerfilUsuario", method = RequestMethod.POST)	
	public String ordenarImoveisPerfilUsuario(ModelMap map, 
											  @ModelAttribute("imovelForm") ImovelForm form, 
										      HttpSession session){
		try {
			map.addAttribute("listaImoveisPerfilUsuario",  imovelService.buscar(form, form.getIdUsuarioPerfil(), form.getOpcaoOrdenacao()));								
			map.addAttribute("imovelForm", form);
			return DIR_PATH + "listarImoveisPerfilUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  ordenarImoveisPerfilUsuario");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/listarImoveisPerfilUsuarioPorMapa", method = RequestMethod.POST)	
	public String listarImoveisPerfilUsuarioPorMapa(@ModelAttribute("imovelForm") ImovelForm form, 
									   		  		ModelMap map, 
									   		  		HttpSession session){
		try {
			 ObjectMapper mapper = new ObjectMapper();				
			 String json = "";
			 List<ImovelMapaForm> lista = null;
			 int tam = 0;			 
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 map.addAttribute("usuarioForm", usuarioService.prepararDetalhesUsuarioForm(form.getIdUsuarioPerfil(), user.getId()));
			 lista = imovelService.buscarImoveisMapaPorIdUsuario(form, form.getIdUsuarioPerfil());
			 tam = AppUtil.recuperarQuantidadeLista(lista);
			 if (tam > 0 ){
				 json = mapper.writeValueAsString(lista);
				 map.addAttribute("listaImoveis", json );
			 }
			 else
				 map.addAttribute("listaImoveis", null );
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  listarImoveisPerfilUsuarioPorMapa");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}				
		return DIR_PATH + "listarImoveisPerfilUsuarioPorMapa";
	}
	
	
	@RequestMapping(value = "/filtrarMeusImoveis", method = RequestMethod.POST)	
	public String filtrarMeusImoveis(@ModelAttribute("imovelForm") ImovelForm form, 
									 ModelMap map, 
									 HttpSession session){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaMeusImoveis",  imovelService.buscar(form, user.getId(), form.getOpcaoOrdenacao()));		
			map.addAttribute("imovelForm", form);				
			return DIR_PATH + "listarMeusImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  filtrarMeusImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/filtrarMeusImoveisPorCodigoIdentificacao", method = RequestMethod.POST)	
	public String filtrarMeusImoveisPorCodigoIdentificacao(@ModelAttribute("imovelForm") ImovelForm form, 
									 					   ModelMap map, 
									 					   HttpSession session){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaMeusImoveis",  imovelService.recuperarImovelPorCodigoIdentificacaoPorUsuario(form, user.getId(), true));		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("imovelForm", form);				
			return DIR_PATH + "listarMeusImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  filtrarMeusImoveisPorCodigoIdentificacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/filtrarBuscaImoveisPorCodigoIdentificacao", method = RequestMethod.POST)	
	public String filtrarBuscaImoveisPorCodigoIdentificacao(@ModelAttribute("imovelForm") ImovelForm form, 
									 					    ModelMap map, 
									 					    HttpSession session){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaBuscarImoveis",  imovelService.recuperarImovelPorCodigoIdentificacaoPorUsuario(form, user.getId(), false));		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("imovelForm", form);				
			return DIR_PATH + "buscarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  filtrarBuscaImoveisPorCodigoIdentificacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	
	@RequestMapping(value = "/filtrarImoveisPerfilUsuarioPorCodigoIdentificacao", method = RequestMethod.POST)	
	public String filtrarImoveisPerfilUsuarioPorCodigoIdentificacao(@ModelAttribute("imovelForm") ImovelForm form, 
									 					    		ModelMap map, 
									 					    		HttpSession session){		
		try {			
			map.addAttribute("listaImoveisPerfilUsuario",  imovelService.recuperarImovelPorCodigoIdentificacaoPorUsuario(form, form.getIdUsuarioPerfil(), true));		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("imovelForm", form);				
			return DIR_PATH + "listarImoveisPerfilUsuario";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  filtrarImoveisPerfilUsuarioPorCodigoIdentificacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	
	@RequestMapping(value = "/filtrarImoveisPerfilUsuarioMapaPorCodigoIdentificacao", method = RequestMethod.POST)	
	public String filtrarImoveisPerfilUsuarioMapaPorCodigoIdentificacao(@ModelAttribute("imovelForm") ImovelForm form, 
									 					    			ModelMap map, 
									 					    			HttpSession session){		
		try {			
			
			 ObjectMapper mapper = new ObjectMapper();				
			 String json = "";
			 List<Imovel> lista = null;
			 int tam = 0;			 
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			 map.addAttribute("usuarioForm", usuarioService.prepararDetalhesUsuarioForm(form.getIdUsuarioPerfil(), user.getId()));
			 lista = imovelService.recuperarImovelPorCodigoIdentificacaoPorUsuario(form, form.getIdUsuarioPerfil(), true);
			 tam = AppUtil.recuperarQuantidadeLista(lista);
			 if (tam > 0 ){
				 json = mapper.writeValueAsString(lista);
				 map.addAttribute("listaImoveis", json );
			 }
			 else
				 map.addAttribute("listaImoveis", null );			
			
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("imovelForm", form);				
			return DIR_PATH + "listarImoveisPerfilUsuarioPorMapa";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  filtrarImoveisPerfilUsuarioPorCodigoIdentificacao");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/detalhesImovel/{idImovel}", method = RequestMethod.GET, produces = "application/json")
	public String detalhesImovel(@PathVariable Long idImovel,
								 ModelMap map, 
								 HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	
			ImovelForm form = new ImovelForm();		
			imovelService.preparaDetalhesImovelForm(idImovel, form, user);		
			map.addAttribute("imovelForm", form);
			map.addAttribute("comentarios", imovelComentarioService.listarComentarios(form.getId(), null));
			return DIR_PATH + "visualizarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  detalhesImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/analisarRecuperacaoUsuariosInteressados/{idImovel}")
	public String analisarRecuperacaoUsuariosInteressados(@PathVariable Long idImovel,
														  @ModelAttribute("imovelForm") ImovelForm form,
								 						  ModelMap map, 
								 						  HttpSession session){		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);					
			map.addAttribute("imovel", form);
			map.addAttribute("listaUsuarios", imovelService.pesquisarPossiveisCompradores(user.getId(), form));
			return DIR_PATH + "resultadoAnaliseRecuperacaoUsuariosInteressados";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  detalhesImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	
	
	@RequestMapping(value = "/detalhesImovel")
	public String detalhesImovel(HttpServletRequest request,
								 ModelMap map, 
								 HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);	
			ImovelForm form = new ImovelForm();		
			imovelService.preparaDetalhesImovelForm(Long.valueOf(request.getParameter("modIdImovel").toString()), form, user);		
			map.addAttribute("imovelForm", form);
			map.addAttribute("comentarios", imovelComentarioService.listarComentarios(form.getId(), null));
			return DIR_PATH + "visualizarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  detalhesImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/prepararExclusaoImovel/{idImovel}", method = RequestMethod.GET)	
	public String prepararExclusaoImovel(@PathVariable Long idImovel,
										 @ModelAttribute("imovelForm") ImovelForm form,
										 ModelMap map, 
										 HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);							
			imovelService.preparaDetalhesImovelForm(idImovel, form, user);		
			map.addAttribute("imovelForm", form);
			return DIR_PATH + "confirmaExclusaoImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  prepararExclusaoImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/confirmarExclusaoImovel", method = RequestMethod.POST)	
	public String confirmarExclusaoImovel( ModelMap map, 
										  @ModelAttribute("imovelForm") ImovelForm form,
										  HttpSession session){	
		
		try {
			imovelService.excluirImovel(form.getId());		
			map.addAttribute("imovelForm", form);
			map.addAttribute("msgSucesso", "S");
			return DIR_PATH + "confirmaExclusaoImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  confirmarExclusaoImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	//@RequestMapping(value = "/prepararCadastroImovel", method = RequestMethod.POST)
	@RequestMapping(value = "/prepararCadastroImovel")
	public String prepararCadastroImovel( ModelMap map, 
									 	  HttpSession session){		
		try {
			ImovelForm form = new ImovelForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("imovelForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "cadastroImovel");
			return DIR_PATH_CADASTRO + "cadastrarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  prepararCadastroImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/avancarCadastroImovel", method = RequestMethod.POST)	
	public String avancarCadastroImovel(@ModelAttribute("imovelForm") ImovelForm form, 
								  ModelMap map, 
								  @RequestParam("name") String name,
								  @RequestParam("file") MultipartFile file,
								  HttpSession session,
								  BindingResult result){
		
		try{
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
            form.setFotoPrincipal(file.getBytes());
			boolean possuiErro = imovelService.validarDadosCadastroImovel(form, result);
			if ( ! possuiErro ) {
				form.setIdUsuario(user.getId());
				form = imovelService.cadastrar(form);
				map.addAttribute("imovelForm", form);
				notaService.cadastrarNota(MessageUtils.getMessage("msg.usuario.para.cadastrar.nota.imovel") , 
																  usuarioService.recuperarUsuarioPorId(user.getId()),
																  imovelService.recuperarImovelPorid(form.getId()), new Date(), 
																  NotaAcaoEnum.IMOVEL.getRotulo());  
				return DIR_PATH_CADASTRO + "cadastrarGaleriaFotosImovel";	
			}
			else {				
				map.addAttribute("msgErro", "N");
				map.addAttribute("imovelForm", form);
				return DIR_PATH_CADASTRO + "cadastrarImovel";
			}			
		}
		catch(Exception e){
			log.error("Erro metodo - ImovelController -  avancarCadastroImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}				
	}
	
	@RequestMapping(value = "/avancarCadastroImovelMapa", method = RequestMethod.GET)	
	public String avancarCadastroImovelMapa(@ModelAttribute("imovelForm") ImovelForm form, 
								  		    ModelMap map, 
								  		    HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("imovelForm", form);
			return DIR_PATH_CADASTRO + "cadastrarImovelMapa";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  avancarCadastroImovelMapa");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}			 		
	}	
	
	
	@RequestMapping(value = "/cadastrarMapaImovel", method = RequestMethod.POST)	
	public String cadastrarMapaImovel(@ModelAttribute("imovelForm") ImovelForm form, 
								   	  ModelMap map, 
								      HttpSession session){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			form.setLatitude(Double.parseDouble(form.getLatitudeFmt()));
			form.setLongitude(Double.parseDouble(form.getLongitudeFmt()));		
			imovelService.atualizarImovel(form, user);		
			map.addAttribute("imovelForm", form);
			return DIR_PATH_CADASTRO + "cadastrarImovelMapa";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  cadastrarMapaImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}
	
	@RequestMapping(value = "/confirmarCadastroImovel/{idImovel}", method = RequestMethod.GET)	
	public String confirmarCadastroImovel(@PathVariable Long idImovel,
										  @ModelAttribute("imovelForm") ImovelForm form, 
								  		  ModelMap map, 
								  		  HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			form = new ImovelForm();
			imovelService.preparaDetalhesImovelForm(idImovel, form, user);		
			map.addAttribute("imovelForm", form);
			session.setAttribute(ImovelService.QUANT_MEUS_IMOVEIS, imovelService.checarQuantMeusImoveis(user.getId()));
			return DIR_PATH + "visualizarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  confirmarCadastroImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}			 		
	}

	
	@RequestMapping(value = "/incluirFotoGaleriaCadastroImovel", method=RequestMethod.POST)	
	public String incluirFotoGaleriaCadastroImovel(@ModelAttribute("imovelForm") ImovelForm form,
									 	   		   @RequestParam("name") String name,
									 	   		   @RequestParam("file") MultipartFile file,
									 	   		   ModelMap map, 
									 	   		   HttpSession session){
		
		try {
			if (!file.isEmpty()) {
	            try {
	            	imovelfotosService.adicionarFotos(form.getId(), file.getBytes(), "");
	            	form = imovelService.carregaGaleriaFotosImovel(form.getId(), true);
	                map.addAttribute("imovelForm", form );
	                return DIR_PATH_CADASTRO + "cadastrarGaleriaFotosImovel";
	            } catch (Exception e) {
	            	log.error("Erro metodo - ImovelController -  incluirFotoGaleriaCadastroImovel - parte 1");
	    			log.error("Mensagem Erro: " + e.getMessage());
	    			map.addAttribute("mensagemErroGeral", "S");
	    			return null;
	            }
	        } 				
			return null;
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  incluirFotoGaleriaCadastroImovel - parte 2");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return null;
		}				
	}
	
	@RequestMapping(value = "/excluirFotoGaleriaCadastroImovel/{idImovelFoto}/{idImovel}")	
	public String excluirFotoGaleriaCadastroImovel(@PathVariable("idImovelFoto") Long idImovelFoto,
									 			   @PathVariable("idImovel") Long idImovel,
									 			   ModelMap map){		
		try {
			imovelfotosService.excluirFoto(idImovelFoto);
			ImovelForm form = imovelService.carregaGaleriaFotosImovel(idImovel, true);
			map.addAttribute("imovelForm", form );
			return DIR_PATH_CADASTRO + "cadastrarGaleriaFotosImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  excluirFotoGaleriaCadastroImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		
	}

	
	@RequestMapping(value = "/prepararEditarImovel/{idImovel}", method = RequestMethod.GET)	
	public String prepararEditarImovel(@PathVariable Long idImovel, 
									   ModelMap map, 
									   HttpSession session) {
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			String msgRetorno = imovelService.validarPrepararImovel(idImovel, user);
			if ( StringUtils.isNullOrEmpty(msgRetorno)){
				session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "editarImovel");
				ImovelForm form = new ImovelForm();		
				imovelService.prepararImovelParaAtualizacao(idImovel, form);			
				map.addAttribute("imovelForm", form);
				return DIR_PATH_EDICAO + "editarImovel";
			}
			else {
				map.addAttribute("mensagemErro", msgRetorno);
				return ImovelService.PATH_ERRO_IMOVEL;
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  prepararEditarImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}	
	}
	
	@RequestMapping(value = "/editarImovel", method = RequestMethod.POST)	
	public String editarImovel(@ModelAttribute("imovelForm") ImovelForm form, 
			 				//   @RequestParam("name") String name,
			 				 //  @RequestParam("file") MultipartFile file,
			 				   BindingResult result,
			 				   HttpSession session,
							   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
		/*	if ( file != null && ! file.isEmpty())
				form.setFotoPrincipal(file.getBytes());  */
			
			boolean possuiErro = imovelService.validarDadosEdicaoImovel(form, result);
			if ( ! possuiErro ) {				
				imovelService.atualizarImovel(form, user);
				map.addAttribute("msgSucesso", "S");		
				notaService.cadastrarNota(MessageUtils.getMessage("msg.usuario.para.cadastrar.nota.atualizar.imovel"), 
										 usuarioService.recuperarUsuarioPorId(user.getId()),
										 imovelService.recuperarImovelPorid(form.getId()), 
										 new Date(),
										 NotaAcaoEnum.IMOVEL.getRotulo());
			}
			else 
				map.addAttribute("msgErro", "N");		
			
			imovelService.prepararImovelParaAtualizacao(form.getId(), form);			
			map.addAttribute("imovelForm", form);				
				
			} catch (Exception e) {
				log.error("Erro metodo - ImovelController -  editarImovel");
				log.error("Mensagem Erro: " + e.getMessage());
				map.addAttribute("mensagemErroGeral", "S");
				return ImovelService.PATH_ERRO_GERAL;
			}
		
		return DIR_PATH_EDICAO + "editarImovel";
	}	
	
	@RequestMapping(value = "/editarFotoPrincipal", method = RequestMethod.POST)	
	public String editarFotoPrincipal(@ModelAttribute("imovelForm") ImovelForm form, 
									  @RequestParam("name") String name,
									  @RequestParam("file") MultipartFile file,
									  BindingResult result,
			 				   HttpSession session,
							   ModelMap map){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			if ( file != null && ! file.isEmpty())
				form.setFotoPrincipal(file.getBytes());	
			
				imovelService.atualizarImovel(form, user);
				map.addAttribute("imovelForm", form);				
				
			} catch (Exception e) {
				log.error("Erro metodo - ImovelController -  editarImovel");
				log.error("Mensagem Erro: " + e.getMessage());
				map.addAttribute("mensagemErroGeral", "S");
				return ImovelService.PATH_ERRO_GERAL;
			}
		
		return DIR_PATH_EDICAO + "editarImovel";
	}

	
	@RequestMapping(value = "/editarFotoPrincipalImovel", method = RequestMethod.GET)
    @ResponseBody
    public void editarFotoPrincipalImovel(@ModelAttribute("imovelForm") ImovelForm form, 
							    		  @RequestParam("name") String name,
								 	   	  @RequestParam("file") MultipartFile file,
								 	   	  ModelMap map, 
								 	   	  HttpSession session)  {
        
		try {
        	form.setFotoPrincipal(file.getBytes());
        	form = imovelService.atualizarFotoImovel(form);            	
            map.addAttribute("imovelForm", form );            
        } catch (Exception e) {
        	log.error("Erro metodo - ImovelController -  editarFotoPrincipalImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
        }
    }
	
	@RequestMapping(value = "/prepararAtualizarMapaImovel/{idImovel}", method = RequestMethod.GET)	
	public String prepararAtualizarMapaImovel(@PathVariable Long idImovel, 
										  	  ModelMap map, 
										  	  HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			String msgRetorno = imovelService.validarPrepararImovel(idImovel, user);
			if ( StringUtils.isNullOrEmpty(msgRetorno)){
				ImovelForm form = new ImovelForm();		
				imovelService.prepararImovelParaAtualizacao(idImovel, form);		
				map.addAttribute("imovelForm", form);
				return DIR_PATH_EDICAO + "editarImovelMapa";
			}
			else {
				map.addAttribute("mensagemErro", msgRetorno);
				return ImovelService.PATH_ERRO_IMOVEL;
			}
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  prepararAtualizarMapaImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}		 		
	}
	
	@RequestMapping(value = "/editarMapaImovel", method = RequestMethod.POST)	
	public String salvarMapaImovel(@ModelAttribute("imovelForm") ImovelForm form, 
								   ModelMap map, 
								   HttpSession session){
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			form.setLatitude(Double.parseDouble(form.getLatitudeFmt()));
			form.setLongitude(Double.parseDouble(form.getLongitudeFmt()));		
			imovelService.atualizarImovel(form, user);		
			map.addAttribute("imovelForm", form);
			map.addAttribute("msgSucesso", "S");
			return DIR_PATH_EDICAO + "editarImovelMapa";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  salvarMapaImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");			
			return ImovelService.PATH_ERRO_GERAL;
		} 		
	}	
	
	@RequestMapping(value = "/buscarImovelMapa", method = RequestMethod.POST)	
	public String buscarImovelMapa(@ModelAttribute("imovelForm") ImovelForm form, 
								   ModelMap map, 
								   HttpSession session){
		
		try {
			 ObjectMapper mapper = new ObjectMapper();
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			 String json = "";
			 List<ImovelMapaForm> lista = null;
			 int tam = 0;
			 lista = imovelService.buscarImoveisMapa(form, user.getId());
			 tam = AppUtil.recuperarQuantidadeLista(lista);
			 if (tam > 0 ){
				 json = mapper.writeValueAsString(lista);
				 map.addAttribute("listaImoveis", json );
			 }
			 else
				 map.addAttribute("listaImoveis", null );
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  buscarImovelMapa");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
		return DIR_PATH + "buscarImovelPorMapa";       
	}
	
	@RequestMapping(value = "/listarMeusImoveisMapa", method = RequestMethod.POST)	
	public String listarMeusImoveisMapa(@ModelAttribute("imovelForm") ImovelForm form, 
								   		ModelMap map, 
								   		HttpSession session){
		
		try {
			 ObjectMapper mapper = new ObjectMapper();
			 UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			 String json = "";
			 List<ImovelMapaForm> lista = null;
			 int tam = 0;
			 lista = imovelService.buscarImoveisMapa(form, user.getId());
			 tam = AppUtil.recuperarQuantidadeLista(lista);
			 if (tam > 0 ){
				 json = mapper.writeValueAsString(lista);
				 map.addAttribute("listaImoveisMapa", json );
			 }
			 else
				 map.addAttribute("listaImoveisMapa", null );
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  listarMeusImoveisMapa");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
		
		return DIR_PATH + "listarMeusImoveisPorMapa";       
	}
		
	
	
	
	
	
	@RequestMapping(value = "/buscarImovel", method = RequestMethod.POST)	
	public String buscarImovel(@ModelAttribute("imovelForm") ImovelForm form, 
							   ModelMap map){
		
		try {
			form.setOpcaoPaginacao("");
			map.addAttribute("listaBuscarImoveis", imovelService.buscar(form, null, null));
			map.addAttribute("imovelForm", form);
			map.addAttribute("func", "buscarImovel");			
			map.addAttribute("listaAcaoImovel", AcaoImovelEnum.values());
			return DIR_PATH + "buscarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  buscarImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value = "/paginarBuscarImovel", method = RequestMethod.POST)	
	public String paginarBuscarImovel(@ModelAttribute("imovelForm") ImovelForm form, 
							   		  ModelMap map){
		
		try {
			map.addAttribute("listaBuscarImoveis", imovelService.buscar(form, null, form.getOpcaoOrdenacao()));
			map.addAttribute("imovelForm", form);
			map.addAttribute("func", "buscarImovel");
			map.addAttribute("listaAcaoImovel", AcaoImovelEnum.values());
			return DIR_PATH + "buscarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  paginarBuscarImovel");
			log.error("Mensagem Erro: " + e.getMessage());
			return ImovelService.PATH_ERRO_GERAL;
		}
		    
	}
	
	@RequestMapping(value = "/prepararBuscaImoveis", method = RequestMethod.GET)
	public String goBuscarImoveis(ModelMap map, HttpSession session){	
		
		try {
			ImovelForm form = new ImovelForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("imovelForm", form);
			map.addAttribute("func", "buscarImovel");
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "buscarImoveis");
			return DIR_PATH + "buscarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  goBuscarImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}    
	}
	
	@RequestMapping(value = "/prepararBuscaImoveisPorMapa", method = RequestMethod.GET)
	public String prepararBuscaImoveisPorMapa(ModelMap map, HttpSession session){
		
		try {
			ImovelForm form = new ImovelForm();
			form.setListaEstados(estadosService.listarTodosEstadosSelect());
			map.addAttribute("listaImoveis", null );
			map.addAttribute("imovelForm", form);
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "buscarImoveisMapa");
			return DIR_PATH + "buscarImovelPorMapa";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  prepararBuscaImoveisPorMapa");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}    
	}
	
	@RequestMapping(value = "/listarMeusImoveis", method = RequestMethod.GET)
	public String listarMeusImoveis(HttpSession session, 
									ModelMap map){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			ImovelForm form = new ImovelForm();		
			form.setListaEstados(estadosService.listarTodosEstadosSelect());					
			map.addAttribute("listaMeusImoveis", imovelService.listarMeusImoveis(user.getId(), form));						
			map.addAttribute("imovelForm", form);				
			session.setAttribute(UsuarioInterface.FUNCIONALIDADE, "listarMeusImoveis");
			return DIR_PATH + "listarMeusImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  listarMeusImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}	
		
	@RequestMapping(value="/ordenaMeusImoveis", method = RequestMethod.POST)	
	public String ordenaListaMeusImoveis(ModelMap map, 
										 @ModelAttribute("imovelForm") ImovelForm form, 
										 HttpSession session){
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);		
			map.addAttribute("listaMeusImoveis", imovelService.buscar(form, user.getId(), form.getOpcaoOrdenacao()));					
			map.addAttribute("imovelForm", form);
			return DIR_PATH + "listarMeusImoveis";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  ordenaListaMeusImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}
	
	@RequestMapping(value="/ordenaBuscarImoveis", method = RequestMethod.POST)	
	public String ordenaBuscarImoveis(ModelMap map, 
									  @ModelAttribute("imovelForm") ImovelForm form, 
									  HttpSession session){		
		
		try {
			UsuarioForm user = (UsuarioForm)session.getAttribute(UsuarioInterface.USUARIO_SESSAO);
			map.addAttribute("listaBuscarImoveis", imovelService.buscar(form, null, form.getOpcaoOrdenacao()));
			form.setOpcaoPaginacao("");
			map.addAttribute("imovelForm", form);		 		
			return DIR_PATH + "buscarImovel";
		} catch (Exception e) {
			log.error("Erro metodo - ImovelController -  ordenaBuscarImoveis");
			log.error("Mensagem Erro: " + e.getMessage());
			map.addAttribute("mensagemErroGeral", "S");
			return ImovelService.PATH_ERRO_GERAL;
		}
	}

}
