package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.BairrosDao;
import com.busqueumlugar.dao.CidadesDao;
import com.busqueumlugar.dao.EstadosDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ItemMensagemAdminDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.FiltroRelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.RelatorioQuantAssinatura;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.AdministracaoService;
import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelcomentarioService;
import com.busqueumlugar.service.ImovelindicadoService;
import com.busqueumlugar.service.ImovelPropostasService;
import com.busqueumlugar.service.ImovelvisualizadoService;
import com.busqueumlugar.service.IntermediacaoService;
import com.busqueumlugar.service.ItemMensagemAdminService;
import com.busqueumlugar.service.MensagemAdminService;
import com.busqueumlugar.service.MensagemService;
import com.busqueumlugar.service.ParceriaService;
import com.busqueumlugar.service.PlanousuarioService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.UsuarioInterface;
import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum;
import com.busqueumlugar.enumerador.StatusImovelEnum;
import com.busqueumlugar.enumerador.StatusPagtoEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;


@Service
public class AdministracaoServiceImpl implements AdministracaoService {

	private static final Logger log = LoggerFactory.getLogger(AdministracaoServiceImpl.class);	
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ImovelDao imovelDao;

	@Autowired
	private PlanousuarioService  planoUsuarioService;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ParceriaService parceriaService;
	
	@Autowired
	private IntermediacaoService intermediacaoService;
		
	@Autowired
	private ImovelcomentarioService imovelcomentarioService;
	
	@Autowired
	private ImovelindicadoService imovelindicadoService;
	
	@Autowired
	private ImovelFavoritosService imovelFavoritosService;
	
	@Autowired
	private ImovelPropostasService imovelPropostasService;
	
	@Autowired
	private ImovelvisualizadoService imovelvisitadoService;
	
	@Autowired
	private EstadosDao estadosDao;
	
	@Autowired
	private CidadesDao cidadesDao;
	
	@Autowired
	private BairrosDao bairrosDao;
	
	@Autowired
	private MensagemAdminService mensagemAdminService;
	
	@Autowired
	private  ItemMensagemAdminDao itemMensagemAdminDao;
	

	public List<Usuario> consultaUsuarios(String valorBusca, UsuarioForm usuarioForm) {
		List<Usuario> lista = new ArrayList<Usuario>();            
        usuarioForm.setValorBusca(valorBusca);
        if ( usuarioForm != null )
            lista = usuarioService.buscarUsuarios(usuarioForm, "infoPessoais");

        return lista;
	}


	public UsuarioForm detalhesUsuario(Usuario usuario) {
	 UsuarioForm frm = new UsuarioForm();
        if ( usuario != null)
            BeanUtils.copyProperties(frm, usuario);
        
        return frm;
	}


	public List<Imovel> buscarImovel(AdministracaoForm admForm) {
        return imovelDao.buscarImoveis(admForm, null, null);
	}



	@Override
	public void carregaInfoDadosUsuarioAdmin(UsuarioForm user, HttpSession session) {		
		session.setAttribute(UsuarioInterface.USUARIO_SESSAO, user);
		session.setAttribute(MensagemAdminService.QUANT_NOVAS_MENSAGENS_ADMIN, itemMensagemAdminDao.findQuantidadeMensagensAdminEnviadasParaAdmin());			
	}


	@Override
	public void gerarRelatorioPlanos(AdministracaoForm form, String tipoRelatorio) {
		
		if (tipoRelatorio.equals("totalPlano"))
			form.setQuantTotal(planoUsuarioService.checarQuantTotalPlanosPorData(form));
		else if (tipoRelatorio.equals("buscaPlanoUsuario")) {
			List<Usuario> listaUsuario = usuarioDao.findUsuarios(form);
			List<Planousuario> listaPlano = new ArrayList<Planousuario>();
			form.setListaPlanoUsuario(new ArrayList<Planousuario>());
			for (Usuario usuario: listaUsuario ){
				listaPlano.addAll(planoUsuarioService.recuperarPlanosPorUsuarioPorStatus(usuario.getId(), form));
			}	
			if (!CollectionUtils.isEmpty(listaPlano))
				form.getListaPlanoUsuario().addAll(listaPlano);
			
		}
		else if (tipoRelatorio.equals("planosVolFinanceiro")) {			
			form.setListaRelatorioPlano(planoUsuarioService.checarVolumeFinanceirosPlanoPorDataPorStatus(form));
		}	
		else if (tipoRelatorio.equals("usuariosPlanosvolFinanceiro")) {			
			form.setListaRelatorioPlano(planoUsuarioService.recuperarUsuariosPlanosVolFinanceiro(form));
		}		
	}

	@Override
	public void gerarRelatorioServicos(AdministracaoForm form, String tipoRelatorio) {

		if ( tipoRelatorio.equals("totalServicos")){
			List<Servico> lista = servicoService.checarTotalServicos(form);					
			if ( lista != null && lista.size() > 0){
				long total = 0l;
				for (Servico serv : lista){
					total+= serv.getQuantidade();
				}				
				form.setQuantTotal(total);
			}	
			else
				form.setQuantTotal(0);
		}
		else if ( tipoRelatorio.equals("usuariosVolFinancServicos")){
			form.setListaServicos(servicoService.checarUsuariosVolFinanceiroServicoPorStatus(form));	 
		}
		else if ( tipoRelatorio.equals("volFinanceiroServicos")){
			form.setListaServicos(servicoService.checarVolumeFinanceirosServicos(form));
		}
		
	}


	@Override
	public void gerarRelatorioUsuarios(AdministracaoForm form, String tipoRelatorio) {
		
		if ( tipoRelatorio.equals("quantTotal"))
			form.setQuantTotal(usuarioDao.findQuantidadeTotalUsuarioPorPeriodo(form));
		else if ( tipoRelatorio.equals("acessoUsuario")){			
			form.setQuantTotal(usuarioDao.findQuantidadeUsuariosByDataVisita(form));			
		}
		else if ( tipoRelatorio.equals("ultimoAcessoUsuarioPeriodo"))
			form.setListaUsuarios(usuarioDao.findUsuariosByDataVisita(form));	
	}


	@Override
	public void gerarRelatorioAssinaturas(AdministracaoForm form, String tipoRelatorio) {

		if ( tipoRelatorio.equals("totalAssinaturas"))
			form.setQuantTotal(servicoService.checarTotalAssinaturas(form));					
		else if ( tipoRelatorio.equals("assinaturasVolFinanceiro"))
			form.setListaRelatorioQuantAssinatura(servicoService.checarVolFinanceiroAssinaturasPorPeriodo(form));
		else if ( tipoRelatorio.equals("buscaAssinaturasUsuario")){
			List<Usuario> listaUsuario = usuarioDao.findUsuarios(form);
			List<RelatorioQuantAssinatura> listaFinal = new ArrayList<RelatorioQuantAssinatura>(); 
			for (Usuario usuario : listaUsuario){
				listaFinal.addAll(servicoService.checarTotalAssinaturasPorUsuario(usuario.getId(), form ));
			}			
			form.setListaRelatorioQuantAssinatura(listaFinal);
		}			
	}


	@Override
	public void gerarRelatorioImoveis(AdministracaoForm form, String tipoRelatorio) {

		if ( tipoRelatorio.equals("maisComentarios"))
			form.setListaImoveis(imovelcomentarioService.relatorioImoveisMaisComentadosPorPeriodo(form));
		else if ( tipoRelatorio.equals("maisIndicacoes"))
			form.setListaImoveis(imovelindicadoService.checarImoveisMaisReceberamIndicacoesPorPeriodo(form));
		else if ( tipoRelatorio.equals("maisInteressados"))
			form.setListaImoveis(imovelFavoritosService.checarImoveisMaisInteressadosPorPeriodo(form));
		else if ( tipoRelatorio.equals("maisPropostas"))
			form.setListaImoveis(imovelPropostasService.checarImoveisMaisReceberamPropostasPorPeriodo(form));
		else if ( tipoRelatorio.equals("maisSolParceria"))
			form.setListaImoveis(parceriaService.checarImoveisMaisReceberamSolParceriaPorPeriodo(form));	
		else if ( tipoRelatorio.equals("maisSolIntermediacao"))
			form.setListaImoveis(intermediacaoService.checarImoveisMaisReceberamSolIntermediacaoPorPeriodo(form));
		else if ( tipoRelatorio.equals("maisVisitas"))
			form.setListaImoveis(imovelvisitadoService.relatorioImoveisMaisVisitadosPorPeriodo(form));
		else if ( tipoRelatorio.equals("quantTotal"))
			form.setQuantTotal(imovelService.checarQuantidadeTotalImoveisPorPeriodo(form));
			
	}
	
	@Override
	public List<FiltroRelatorioForm> checarFiltrosUtilizados(AdministracaoForm form) {

		FiltroRelatorioForm filtro = new FiltroRelatorioForm();
		List<FiltroRelatorioForm> lista = new ArrayList<FiltroRelatorioForm>();
		if ( form.getIdEstado() > 0  ){
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.estado"));
			filtro.setValorFiltro(estadosDao.findEstadosById(form.getIdEstado()).getNome() );
			lista.add(filtro);
			if (form.getIdCidade() > 0 ){
				filtro = new FiltroRelatorioForm();
				filtro.setNomeFiltro(MessageUtils.getMessage("lbl.cidade"));
				filtro.setValorFiltro(cidadesDao.findCidadesById(form.getIdCidade()).getNome());
				lista.add(filtro);
				if (form.getIdBairro() > 0 ){
					filtro = new FiltroRelatorioForm();
					filtro.setNomeFiltro(MessageUtils.getMessage("lbl.bairro"));
					filtro.setValorFiltro(bairrosDao.findBairrosById(form.getIdBairro()).getNome());
					lista.add(filtro);
				}
			}
		}
		
		if (! StringUtils.isEmpty(form.getAcao())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.acao.imovel"));
			filtro.setValorFiltro(AcaoImovelEnum.valueOf(form.getAcao()).getRotulo());
			lista.add(filtro);
		}
		
		if (! StringUtils.isEmpty(form.getTipoImovel())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.tipo.imovel"));
			filtro.setValorFiltro(TipoImovelEnum.valueOf(form.getTipoImovel()).getRotulo());
			lista.add(filtro);
		}
		
		if (! StringUtils.isEmpty(form.getPerfilImovel())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.status.imovel"));
			filtro.setValorFiltro(StatusImovelEnum.valueOf(form.getPerfilImovel()).getRotulo());
			lista.add(filtro);
		}	
		
		if (! StringUtils.isEmpty(form.getPerfilUsuario())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.filtro.perfil.usuario"));
			filtro.setValorFiltro(PerfilUsuarioNormalEnum.valueOf(form.getPerfilUsuario()).getRotulo());
			lista.add(filtro);
		}
		
		if (! StringUtils.isEmpty(form.getDataInicio())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.relatorio.data.inicio"));
			filtro.setValorFiltro((form.getDataInicio()));
			lista.add(filtro);
		}	
		
		if (! StringUtils.isEmpty(form.getDataFim())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.relatorio.data.fim"));
			filtro.setValorFiltro(form.getDataFim());
			lista.add(filtro);
		}
		
		if (! StringUtils.isEmpty(form.getStatus())){
			filtro = new FiltroRelatorioForm();
			filtro.setNomeFiltro(MessageUtils.getMessage("lbl.admin.status.rel.sistema.resumido"));
			filtro.setValorFiltro(StatusPagtoEnum.valueOf(form.getStatus()).getRotulo());
			lista.add(filtro);
		}
		
		return lista;
	}


	@Override
	public boolean validarGerarRelatorios(AdministracaoForm form, String tipoRelatorio , BindingResult result) {
		
		
		boolean filtroValido = true;
		
		if ( tipoRelatorio.equals("relatorioUsuario") || tipoRelatorio.equals("relatorioImovel")) {
			// validação campos Data Inicio e Data Fim são para todos os relatorios
			boolean isValidoDataInicio = true;
			if ( StringUtils.isEmpty(form.getDataInicio())) {
				result.rejectValue("dataInicio", "opcao.periodo.obrigatorio");
				isValidoDataInicio = false;
	        	filtroValido = false;
			}
			
			boolean isValidoDataFim = true;
			if ( StringUtils.isEmpty(form.getDataFim())) {
				result.rejectValue("dataFim", "opcao.periodo.obrigatorio");
				isValidoDataFim = false;
	        	filtroValido = false;
			}
			
			if ( isValidoDataInicio ){
				if ( ! DateUtil.formatoDataValido(form.getDataInicio())) {
					result.rejectValue("dataInicio", "msg.erro.data.invalida");
		        	filtroValido = false;
		        	isValidoDataInicio = false;
				}
			}
			
			if ( isValidoDataFim ){
				if ( ! DateUtil.formatoDataValido(form.getDataFim())) {
					result.rejectValue("dataFim", "msg.erro.data.invalida");
		        	filtroValido = false;
		        	isValidoDataFim = false;
				}
			}				
			
			if ( isValidoDataInicio && isValidoDataFim ){
				Calendar dtIncio = Calendar.getInstance();
				dtIncio.setTime(DateUtil.formataDataBanco(form.getDataInicio()));
				
				Calendar dtFim = Calendar.getInstance();
				dtFim.setTime(DateUtil.formataDataBanco(form.getDataFim()));
				
				if ( dtFim.compareTo(dtIncio) < 0 ){
					result.rejectValue("dataInicio", "msg.erro.data.inicio.maior.data.fim");
		        	filtroValido = false;
				}
			}
		}
		else if ( tipoRelatorio.equals("relatorioServico") || tipoRelatorio.equals("relatorioPlano")){			
			if ( StringUtils.isEmpty(form.getStatus())){
				result.rejectValue("status", "msg.erro.campo.obrigatorio");
				filtroValido = false;
			}			
		}
		
		return filtroValido;
	}	

}
