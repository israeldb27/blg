package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ParamservicoDao;
import com.busqueumlugar.dao.ServicoDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.AcaoNotificacaoEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.enumerador.StatusPagtoOpcaoEnum;
import com.busqueumlugar.enumerador.TipoNotificacaoEnum;
import com.busqueumlugar.enumerador.TipoParamServicoOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.form.ParamservicoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.ServicoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Formapagamento;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Infoservico;
import com.busqueumlugar.model.Paramservico;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.RelatorioQuantAssinatura;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.InfoservicoService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.PlanoService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.GenerateAccessToken;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.busqueumlugar.util.MessageUtils;

@Service
public class ServicoServiceImpl implements ServicoService {
	
	private static final Logger log = LoggerFactory.getLogger(ServicoServiceImpl.class);
	
	@Autowired
	private ServicoDao dao;
	
	@Autowired
	private ParamservicoService paramservicoService;
	
	@Autowired
	private ParamservicoDao paramservicoDao;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ImovelDao imovelDao;
	
	@Autowired
	private InfoservicoService infoservicoService;
	
	@Autowired
	private PlanoService planoService;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	
	public Servico recuperarServicoPorId(Long id) {
		return dao.findServicoById(id);
	}

	
	@Transactional
	public void cadastrarSolicitacaoPagtoServico(ServicoForm frm, String opcaoTempoPagto) {		
		Servico servico = new Servico();
        servico.setDataSolicitacao(new Date());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());        
        servico.setFormaPgto(frm.getNomeFormaPagto());        
        servico.setUsuario(usuarioDao.findUsuario(frm.getIdUsuario()));
        servico.setOpcaoTempoPagto(frm.getOpcaoTempoPagto());
        servico.setDescServico(frm.getAcao());
        
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(frm.getAcao()));        
        servico.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo());
        servico.setAssociadoPlano("N");        
        dao.save(servico);
	}

	
	@Transactional
	public void cadastrarSolicitacaoPagtoAssinatura(ServicoForm frm) {
		Usuario usuario = usuarioService.recuperarUsuarioPorId(frm.getIdUsuario());
		if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) )
            frm.setAcao(ServicoValueEnum.ASSINATURA_PADRAO.getRotulo());         
         else if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) )            
            frm.setAcao(ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo());         
         else if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()) )            
            frm.setAcao(ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo());         
		
		Servico servico = new Servico();
        servico.setDataSolicitacao(new Date());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());
        servico.setFormaPgto(frm.getNomeFormaPagto());
        servico.setUsuario(usuario);
        servico.setOpcaoTempoPagto(frm.getOpcaoTempoPagto());
        servico.setDescServico(frm.getAcao());  
        
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(frm.getAcao()));
        servico.setTempoDuracao(frm.getTempoDuracao());
        servico.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo());
        servico.setAssociadoPlano("N");
        dao.save(servico);
        
        // atualizando status do usuario sinalizando que ele havia feito uma solicita��o de assinatura
        usuarioService.atualizarStatusUsuario(servico.getUsuario().getId(), "assinaturaSolicitada");
	}

	@Transactional
	public void cadastrarSolicitacaoPagtoServico(ServicoForm frm, ParamservicoForm paramservicoForm,  String opcaoQuantFoto, Long idImovel) {
		
		Servico servico = new Servico();
        servico.setDataSolicitacao(new Date());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());
        servico.setFormaPgto(frm.getNomeFormaPagto());        
        servico.setQuantidadeServico(frm.getQuantidadeServico());
        servico.setDescServico(frm.getAcao());
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(frm.getAcao()));
        
        servico.setImovel(imovelDao.findImovelById(idImovel));
        servico.setAssociadoPlano("N");
        
        if ( frm.getAcao().equals(ServicoValueEnum.ADICIONAR_FOTOS.getRotulo()) )
            servico.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo());
        else
            servico.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo());
        
        dao.save(servico);		
	}

	@Transactional
	public void cadastrarSolicitacaoPagtoServicoImovel(ServicoForm frm, Long idImovel) {		
		
		Servico servico = new Servico();
        servico.setDataSolicitacao(new Date());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());
        servico.setFormaPgto(frm.getNomeFormaPagto());
        servico.setUsuario(usuarioDao.findUsuario(frm.getIdUsuario()));
        servico.setQuantidadeServico(frm.getQuantidadeServico());
        servico.setDescServico(frm.getAcao());
        
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(frm.getAcao()));                
        servico.setImovel(imovelDao.findImovelById(idImovel));        
        servico.setAssociadoPlano("N");
        servico.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo());
        dao.save(servico);
        
	}

	@Transactional
	public void simulaRetornoPagto(long idServico) {
		DateUtil dtAtual = new DateUtil();
        Servico servico = dao.findServicoById(idServico);
        servico.setDataPagto(dtAtual.getTime());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());        
        int quantDiasFimServico = servico.getTempoDuracao();        
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(servico.getDataPagto()); 
        cal.add(Calendar.DAY_OF_MONTH, quantDiasFimServico);        

        servico.setDataFimServico(cal.getTime());        
        dao.save(servico); 		
        
     // inserindo notificacao para o usuario solicitante do servico
        notificacaoService.cadastrarNotificacao(servico.getUsuario().getId(), 
        										AcaoNotificacaoEnum.SERVICO.getRotulo(), 
        									    MessageUtils.getMessage("msg.sucesso.recebimento.pagto.servico")+": " +servico.getLabelServico(), 
        									    TipoNotificacaoEnum.SERVICO.getRotulo(),
        									    0l);
	}

	@Transactional
	public void simulaRetornoPagtoImovel(long idServico) {		
		DateUtil dtAtual = new DateUtil();
        Servico servico = dao.findServicoById(idServico);                
        if (servico.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
            Usuario usuario = servico.getUsuario();
            if ( servico.getDescServico().equals(ServicoValueEnum.ADICIONAR_FOTOS.getRotulo()))               
                usuario.setQuantMaxFotosPorImovel(usuario.getQuantMaxFotosPorImovel() + servico.getQuantidadeServico());
            else if ( servico.getDescServico().equals(ServicoValueEnum.ADICIONAR_IMOVEIS.getRotulo()))                
                usuario.setQuantCadastroImoveis(usuario.getQuantCadastroImoveis() + servico.getQuantidadeServico());
            else if ( servico.getDescServico().equals(ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo()))                
                usuario.setQuantMaxIndicacoesImovel(usuario.getQuantMaxIndicacoesImovel() + servico.getQuantidadeServico());
            else if ( servico.getDescServico().equals(ServicoValueEnum.INDICACOES_EMAIL.getRotulo()))                
                usuario.setQuantMaxIndicacoesImovelEmail(usuario.getQuantMaxIndicacoesImovelEmail() + servico.getQuantidadeServico());
                
            usuarioService.editarUsuario(usuario);
        }
        servico.setDataPagto(dtAtual.getTime());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo()); 
        dao.save(servico);  
        
     // inserindo notificacao para o usuario solicitante do servico
        notificacaoService.cadastrarNotificacao(servico.getUsuario().getId(), 
        										AcaoNotificacaoEnum.SERVICO.getRotulo(),  
        										MessageUtils.getMessage("msg.sucesso.recebimento.pagto.servico")+": " + servico.getLabelServico(), 
        										TipoNotificacaoEnum.SERVICO.getRotulo(),
        										0l);
	}

	
	public boolean checarPossuiServicoPago(Long idUsuario, String descServico) {		
		//checando se o servico est� sendo cobrado
        boolean cobrado = paramservicoService.checarServicoCobrado(descServico);
        if ( cobrado){
            DateUtil dtAtual = new DateUtil();
            List<Servico> lista = dao.findServicoPagoPorIdUsuario(idUsuario, descServico, dtAtual.getTime());            
            if (! CollectionUtils.isEmpty(lista)) 
                return true;
            else
                return false;
        }
        else
            return true;   
	}

	
	@Transactional
	public void concederServicoImovel(Long idImovel, String tipoServico, String tempoDuracao, int quant) {		
		Servico servico = new Servico();
        servico.setDataSolicitacao(new Date());
        servico.setDataPagto(new Date());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());
        servico.setFormaPgto("concessao");
        
        Imovel imovel = imovelDao.findImovelById(idImovel);
        servico.setUsuario(imovel.getUsuario()); 
        
        if ( tipoServico.equals("foto"))
            servico.setQuantFoto(quant);

        servico.setDescServico(ServicoValueEnum.ADICIONAR_FOTOS.getRotulo());
        servico.setImovel(imovel);
        servico.setValorServico(0);
        servico.setOpcaoTempoPagto(tempoDuracao); 
        servico.setLabelServico(MessageUtils.getMessage("lbl.servico.selecionado.adicionar.fotos"));
        
        int quantDiasFimServico = 0;
        if ( tempoDuracao.equals("dia"))
            quantDiasFimServico = 2;
        else if ( tempoDuracao.equals("semana"))
            quantDiasFimServico = 7;
        else if ( tempoDuracao.equals("mes"))
            quantDiasFimServico = 30;
        else if ( tempoDuracao.equals("ano"))
            quantDiasFimServico = 365;
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(servico.getDataPagto()); 
        cal.add(Calendar.DAY_OF_MONTH, quantDiasFimServico);        

        servico.setDataFimServico(cal.getTime());
        servico.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo());
        dao.save(servico);        
        imovelService.adicionarMaisFotos(servico.getImovel().getId(), servico.getQuantFoto());
	}

	
	public List<Servico> recuperarServicosDisponiveisPorImovel(Long idImovel) {		
		return dao.findServicoPorIdImovel(idImovel);
	}

	
	@Transactional
	public void excluirServicoDisponivelImovel(Servico servico) {		
		dao.delete(servico);
	}
	
	@Transactional
	public void excluirServico(Long idServico) {
		Servico servico = dao.findServicoById(idServico);
		dao.delete(servico);
	}


	
	public List<Servico> recuperarServicosDisponiveisPorUsuario(Long idUsuario) {		
		List<Servico> lista = dao.findServicosByIdUsuario(idUsuario);
        List<Servico> listaFinal = new ArrayList<Servico>();
        for (Servico servico : lista ){
            if ( servico.getDescServico().contains("relatorio") || servico.getDescServico().contains("assinatura"))
                servico.setTipoServicoComum("S");
            else
                servico.setTipoServicoComum("N");
            
            listaFinal.add(servico);
        }
        return listaFinal;
	}

	@Transactional
	public void concederServicoUsuario(Long idUsuario, String opcaoServico,	String opcaoTempo, String opcaoQuantidade) {		
		Servico servico = new Servico();
        servico.setDataSolicitacao(new Date());
        servico.setDataPagto(new Date());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo());
        servico.setFormaPgto(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo());
        servico.setUsuario(usuarioDao.findUsuario(idUsuario));        
        servico.setDescServico(opcaoServico);        
        
        int quantDiasFimServico = 0;
        if ( opcaoTempo.equals("dia"))
            quantDiasFimServico = 2;
        else if ( opcaoTempo.equals("semana"))
            quantDiasFimServico = 7;
        else if ( opcaoTempo.equals("mes"))
            quantDiasFimServico = 30;
        else if ( opcaoTempo.equals("ano"))
            quantDiasFimServico = 365;

        Calendar cal = Calendar.getInstance(); 
        cal.setTime(servico.getDataPagto()); 
        cal.add(Calendar.DAY_OF_MONTH, quantDiasFimServico); 
               
            
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(opcaoServico));        
        servico.setAssociadoPlano("N");
        if ( servico.getParamServico().getTipoParamServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo())   ||
        	 servico.getParamServico().getTipoParamServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo()) ){
        	
            servico.setOpcaoTempoPagto(opcaoTempo);    
            servico.setDataFimServico(cal.getTime());   
        }
        else if ( servico.getParamServico().getTipoParamServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
            servico.setQuantidadeServico(Integer.parseInt(opcaoQuantidade));
        }

        servico.setValorServico(0d);
        dao.save(servico);
        
        if ( servico.getParamServico().getTipoParamServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){                
            Usuario usuario = servico.getUsuario();                
            if ( servico.getParamServico().getValueServico().equals(ServicoValueEnum.ADICIONAR_FOTOS.getRotulo())) {                     
                 usuario.setQuantMaxFotosPorImovel(usuario.getQuantMaxFotosPorImovel() + servico.getQuantidadeServico());
            }
            else if ( servico.getParamServico().getValueServico().equals(ServicoValueEnum.ADICIONAR_IMOVEIS.getRotulo())) {                    
                usuario.setQuantCadastroImoveis(usuario.getQuantCadastroImoveis() + servico.getQuantidadeServico());
            }
            else if ( servico.getParamServico().getValueServico().equals(ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo())) {                    
                usuario.setQuantMaxIndicacoesImovel(usuario.getQuantMaxIndicacoesImovel() + servico.getQuantidadeServico());
            }
            else if ( servico.getParamServico().getValueServico().equals(ServicoValueEnum.INDICACOES_EMAIL.getRotulo())) {                    
                usuario.setQuantMaxIndicacoesImovelEmail(usuario.getQuantMaxIndicacoesImovelEmail() + servico.getQuantidadeServico());
            }
            usuarioService.editarUsuario(usuario);
        }
        else if ( servico.getParamServico().getTipoParamServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo())){
           // atualizando a data validade de acesso do usuario
            usuarioService.atualizarAssinaturaUsuario(servico.getUsuario().getId(), servico.getDataFimServico()); 
        }
	}

	@Transactional
	public void revogarServicoDisponivelUsuario(Servico servico) {		
		dao.delete(servico);
	}

	
	public void selecionarPagamentoServico(ServicoForm frm, long idInfoServico,	Long idFormaPagto) {		
		Infoservico infoservico = infoservicoService.recuperarInfoServicoPorId(idInfoServico);         
        frm.setValorServico(infoservico.getValorServico());        
        if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo());
        }    
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo());
        }
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
            frm.setQuantidadeServico(infoservico.getQuantidade());  
            frm.setLabelImovelQuantidade(String.valueOf(infoservico.getQuantidade()) );
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo());
        }
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo());
        }   
        Formapagamento formapagamento = paramservicoService.recuperaTipoFormaPagtoPorId(idFormaPagto);
        frm.setFormaPagtoSelecionado(formapagamento.getLabel() );
        frm.setNomeFormaPagto(formapagamento.getNome());
	}

	
	public void selecionarPagamentoPlano(ServicoForm frm, Long idformaPagto) {		
		Formapagamento formapagamento = paramservicoService.recuperaTipoFormaPagtoPorId(idformaPagto);
        frm.setFormaPagtoSelecionado(formapagamento.getLabel() );
        frm.setNomeFormaPagto(formapagamento.getNome());
	}



	public List<Servico> checarServicosMaisPorStatus(Date dataInicio,	Date dataFim, String statusServico) {
		List lista = dao.findServicosMaisPorPeriodo(dataInicio, dataFim, statusServico);
        List<Servico> listaFinal = new ArrayList<Servico>();
        Servico serv = null;
        if ( !CollectionUtils.isEmpty(lista)){
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                serv = new Servico();
                serv.setLabelServico(obj[0].toString());
                serv.setQuantidade(Integer.parseInt(obj[1].toString()));
                listaFinal.add(serv);
            }
        }        
        return listaFinal;
		
	}
	
   public double checarVolumeFnanceiroServMaisPorStatus(Date dataInicio,	Date dataFim, String statusServico) {		
		return dao.calcularVolumeFinanceiroMaisSolicitadosMaisPagosPorPeriodoPorTipoServico(dataInicio, dataFim, statusServico, null);
	}
	
	public double checarBalancoFinanceiro(Date dataInicio, Date dataFim) {
		double vlTotalSol = dao.calcularVolumeFinanceiroMaisSolicitadosMaisPagosPorPeriodoPorTipoServico(dataInicio, dataFim, StatusPagtoOpcaoEnum.SOLICITADO.getRotulo(), null);
        double vlTotalPago = dao.calcularVolumeFinanceiroMaisSolicitadosMaisPagosPorPeriodoPorTipoServico(dataInicio, dataFim, StatusPagtoOpcaoEnum.PAGO.getRotulo(), null);
        return vlTotalPago - vlTotalSol;
	}

	public List<Servico> checarUsuariosVolFinanceiroServicoPorStatus(AdministracaoForm form) {
		List lista = dao.findUsuariosVolFinanceiroServico(form);
        List<Servico> listaFinal = new ArrayList<Servico>();        
        if ( !CollectionUtils.isEmpty(lista)){
            Servico serv = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                serv = new Servico();
                serv.setUsuario(usuarioDao.findUsuario(Long.parseLong(obj[0].toString())));                
                serv.setValorServico(Double.parseDouble(obj[1].toString()));
                listaFinal.add(serv);
            }
        }        
        return listaFinal;
	}

	public List<Servico> checarServicosVolFinanceiroServicoPorStatus(Date dataInicio, Date dataFim, String statusServico) {		
		List lista = dao.findServicosVolFinanceiroServico(dataInicio, dataFim, statusServico);
        List<Servico> listaFinal = new ArrayList<Servico>();
        
        if ( !CollectionUtils.isEmpty(lista)){
            Servico serv = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                serv = new Servico();
                serv.setLabelServico(obj[0].toString());
                serv.setValorServico(Double.parseDouble(obj[1].toString()));
                listaFinal.add(serv);
            }
        }        
        return listaFinal;
	}
	
	
	public void simulaRetornoPagtoAdicionarCompartilhamento(Long idServico) {		
		DateUtil dtAtual = new DateUtil();
        Servico servico = dao.findServicoById(idServico);
        servico.setDataPagto(dtAtual.getTime());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());        
        dao.save(servico);  
        imovelService.adicionarMaisCompartilhamento(servico.getImovel().getId(), servico.getQuantidadeServico());
	}

	
	public String validarPagamentoServico(long idInfoServico, Long formaPgto) {		
		 
		 if (( formaPgto == null ) || (formaPgto != null && formaPgto.longValue() == -1))
			 return MessageUtils.getMessage("msg.erro.selecionar.pagamento");	   
		 
		 
		if ( idInfoServico <= 0 )
		     return MessageUtils.getMessage("msg.erro.nenhum.valor.servico.plano");
		 
		 return "";
	}

	
	// esta validacao so envolve servicos que solicitem Tempo de um servico como Relatorio e nao quantidade como Adicionar Imoveis 
	public String validarSolicitacaoServico(long idParamServico, long idUsuario, Date dtCorrente) {		
		// checando se existe um periodo contrato no periodo        
        Servico servico = null;
        try {   
        	
        	if ( idParamServico == -1 )
        		return MessageUtils.getMessage("msg.erro.selecionar.um.servico");
        	
        	// checando se o servico ja foi solicitado anteriormente
        	servico = dao.findServicoByIdParamServicoByIdUsuarioByStatusByTipoServico(idParamServico, idUsuario, StatusPagtoOpcaoEnum.SOLICITADO.getRotulo(), TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo());            
            if ( servico != null)            	
            	return MessageUtils.getMessage("msg.erro.servico.sol.anteriormente");
            else { // checando se o servico esta aguardando pagamento
            	servico = dao.findServicoByIdParamServicoByIdUsuarioByStatusByTipoServico(idParamServico, idUsuario, StatusPagtoOpcaoEnum.AGUARDANDO.getRotulo(), TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo());
            	if ( servico != null )            		
            		return MessageUtils.getMessage("msg.erro.servico.aguarde.pagto");
            	else { 
            		servico = dao.findServicoByIdParamServicoByData(idParamServico, idUsuario, dtCorrente);
            		if ( servico != null )            			
            			return MessageUtils.getMessage("msg.erro.servico.pago.periodo");
            	}	
            }
             
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } 
	}

	
	public String validarSolicitacaoServicoPorLabelServico(String nomeServico, long idUsuario, Date dtCorrente) {		
		 // checando se existe um periodo contrato no periodo
        
        Servico servico = null;
        try {            
            servico = dao.findServicoByLabelServicoServicoByData(nomeServico, idUsuario, dtCorrente);
            if ( servico != null )
                return MessageUtils.getMessage("msg.erro.selecionar.servico.contratado");            
            
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } 
	}

	
	public List<Servico> checarServicosMaisConcedidos(Date dataInicio, Date dataFim) {		
		 List lista = dao.findServicosMaisPorPeriodo(dataInicio, dataFim, StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo());
	        List<Servico> listaFinal = new ArrayList<Servico>();
	        if (!CollectionUtils.isEmpty(lista)){
	            Servico serv = null;
	            for (Iterator iter = lista.iterator();iter.hasNext();){
	                Object[] obj = (Object[]) iter.next();
	                serv = new Servico();
	                serv.setLabelServico(obj[0].toString());
	                serv.setQuantidade(Integer.parseInt(obj[1].toString()));
	                listaFinal.add(serv);
	            }
	        }        
	        return listaFinal;
	}

	public double checarVolumeFinanceiroServMaisConcedidos(Date dataInicio, Date dataFim) {
		return dao.calcularVolumeFinanceiroMaisConcedidosPorPeriodo(dataInicio, dataFim);
	}
	
	
	public List<Servico> recuperarServicosDisponiveisPorUsuarioAssociadoPlano(Long idUsuario, String assocPlano) {		
		List<Servico> lista =  dao.findServicosByIdUsuarioByAssocPlano(idUsuario, assocPlano);
        List<Servico> listaFinal = new ArrayList<Servico>();
        for (Servico servico : lista ){
            if ( servico.getDescServico().contains("relatorio") || servico.getDescServico().contains("assinatura"))
                servico.setTipoServicoComum("S");
            else
                servico.setTipoServicoComum("N");
            
            listaFinal.add(servico);
        }
        return listaFinal; 
	}
	
	public List<Servico> recuperarServicosDisponiveisPorUsuarioPorTipo(Long idUsuario, String tipoServico) {		
	     return dao.findServicosByIdUsuarioByTipoServico(idUsuario, tipoServico);
	}

	@Transactional
	public void atualizarServicoImovelPlano(Planousuario p, Plano plano, String descServico) {
		
		Servico servico = new Servico();
        servico.setDataSolicitacao(p.getDataSolicitacao());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());
        servico.setFormaPgto(p.getFormaPagto()); 

        servico.setParamServico(paramservicoDao.findParamservicoPorNome(descServico));        
        servico.setValorServico(0); // tratar ainda
        servico.setTempoDuracao(0);
        servico.setDataPagto(new Date());           
        
        servico.setAssociadoPlano("S");
        Usuario usuario = servico.getUsuario();
        if ( descServico.equals(ServicoValueEnum.ADICIONAR_FOTOS.getRotulo())) {
             servico.setQuantidadeServico(plano.getQuantFotosImovel());                 
             usuario.setQuantMaxFotosPorImovel(usuario.getQuantMaxFotosPorImovel() + servico.getQuantidadeServico());
        }
        else if ( descServico.equals(ServicoValueEnum.ADICIONAR_IMOVEIS.getRotulo())) {
            servico.setQuantidadeServico(plano.getQuantCadastroImoveis());
            usuario.setQuantCadastroImoveis(usuario.getQuantCadastroImoveis() + servico.getQuantidadeServico());
        }
        else if ( descServico.equals(ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo())) {
            servico.setQuantidadeServico(plano.getQuantImoveisIndicacao());
            usuario.setQuantMaxIndicacoesImovel(usuario.getQuantMaxIndicacoesImovel() + servico.getQuantidadeServico());
        }
        else if ( descServico.equals(ServicoValueEnum.INDICACOES_EMAIL.getRotulo())) {
            servico.setQuantidadeServico(plano.getQuantEmailsPorImovel());
            usuario.setQuantMaxIndicacoesImovelEmail(usuario.getQuantMaxIndicacoesImovelEmail() + servico.getQuantidadeServico());
        }
        else if ( descServico.equals(ServicoValueEnum.RELATORIOS.getRotulo())){
            DateUtil dtAtual = new DateUtil();                
            int quantDias = plano.getDuracaoPlano() * 30 ; // duracao do plano est� em meses
            Calendar cal = Calendar.getInstance(); 
            cal.setTime(new Date()); 
            cal.add(Calendar.DAY_OF_MONTH, quantDias);
            servico.setDataPagto(dtAtual.getTime());
            servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());        
            servico.setDataFimServico(cal.getTime());        
            dao.save(servico);                
        }
        usuarioService.editarUsuario(usuario);
        dao.save(servico);
	}

	@Transactional
	public void simulaRetornoPagtoAssinaturaUsuario(Long idServico) {		
		DateUtil dtAtual = new DateUtil();
        Servico servico = dao.findServicoById(idServico);
        servico.setDataPagto(dtAtual.getTime());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());        
        int quantDiasFimServico = servico.getTempoDuracao();        
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(servico.getDataPagto()); 
        cal.add(Calendar.DAY_OF_MONTH, quantDiasFimServico); 
        servico.setDataFimServico(cal.getTime());        
        dao.save(servico);
        
        // atualizando a data validade de acesso do usuario
        usuarioService.atualizarAssinaturaUsuario(servico.getUsuario().getId(), servico.getDataFimServico());
        
        // inserindo notificacao para o usuario solicitante da assinatura
        notificacaoService.cadastrarNotificacao(servico.getUsuario().getId(), 
        										AcaoNotificacaoEnum.SERVICO.getRotulo(),  
        										MessageUtils.getMessage("msg.sucesso.recebimento.pagto.assinatura"),
        										TipoNotificacaoEnum.SERVICO.getRotulo(),
        										0l);
	}

	
	public ServicoForm recuperarAssinaturaSolicitaPorIdUsuario(Long idUsuario, String perfilUsuario) {		
		Servico servico = null;
        ServicoForm frm = new ServicoForm();
        
        if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
            servico = dao.findServicoAssinaturaSolicitadaByIdUsuario(idUsuario, ServicoValueEnum.ASSINATURA_PADRAO.getRotulo());
        else if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
            servico = dao.findServicoAssinaturaSolicitadaByIdUsuario(idUsuario, ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo());
        else if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
            servico = dao.findServicoAssinaturaSolicitadaByIdUsuario(idUsuario, ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo());
        
        if ( servico != null )
            BeanUtils.copyProperties(frm, servico);
        
        return frm;
	}

	@Transactional
	public void excluirAssinaturaUsuarioSolicitada(Long idUsuario, String perfilUsuario) {
		Servico servico = null;
        
        if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
            servico = dao.findServicoAssinaturaSolicitadaByIdUsuario(idUsuario, ServicoValueEnum.ASSINATURA_PADRAO.getRotulo());
        else if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
            servico = dao.findServicoAssinaturaSolicitadaByIdUsuario(idUsuario, ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo());
        else if ( perfilUsuario.equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
            servico = dao.findServicoAssinaturaSolicitadaByIdUsuario(idUsuario, ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo());
        
        dao.delete(servico); 		
	}

	
	public Servico checarHabilitadoModuloRelatorio(Long idUsuario, String tipoRelatorio, Date dtCorrente) {		
		return dao.findServicoRelatorioByIdUsuario(idUsuario, tipoRelatorio, dtCorrente);
	}

	
	@Transactional
	public void atualizarServicoRelatorioPlano(Planousuario p, Plano plano) {		
		 Servico servico = new Servico();
         servico.setDataSolicitacao(p.getDataSolicitacao());
         servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());
         servico.setFormaPgto(p.getFormaPagto());
         
         UsuarioForm user = null;
         String descServico = "";
         if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
             descServico = (ServicoValueEnum.RELATORIO_PADRAO.getRotulo());
         else if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
             descServico = (ServicoValueEnum.RELATORIO_CORRETOR.getRotulo());
         else if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
             descServico = (ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo());
         
         servico.setParamServico(paramservicoDao.findParamservicoPorNome(descServico));
         servico.setValorServico(0); // tratar ainda            
         servico.setDataPagto(new Date());
         servico.setAssociadoPlano("S");            
        
         DateUtil dtAtual = new DateUtil();                
         int quantDias = plano.getDuracaoPlano() * 30 ; // duracao do plano est� em meses
         Calendar cal = Calendar.getInstance(); 
         cal.setTime(new Date()); 
         cal.add(Calendar.DAY_OF_MONTH, quantDias);
         servico.setDataPagto(dtAtual.getTime());     
         servico.setDataFimServico(cal.getTime());                    
         dao.save(servico);
	}

	
	public List<Servico> checarTotalServicos(AdministracaoForm form) {		
		List<Servico> listaFinal = new ArrayList<Servico>();       
        List lista = dao.findTotalServicosPorPeriodo(form);
        if (!CollectionUtils.isEmpty(lista)){
			Servico serv = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				Object[] obj = (Object[]) iter.next();
				serv = new Servico();
				serv.setLabelServico(obj[0].toString());
				serv.setQuantidade(Integer.parseInt(obj[1].toString()));
				listaFinal.add(serv);
			}
		}
            
        return listaFinal;  
	}

	
	public List<Servico> checarVolumeFinanceirosServicos(AdministracaoForm form) {		
		 List<Servico> listaFinal = new ArrayList<Servico>();   
		 List lista = dao.findVolumeFinanceiroServicos(form);
		 if (!CollectionUtils.isEmpty(lista)){
			Servico serv = null;  
			for (Iterator iter = lista.iterator();iter.hasNext();){
				Object[] obj = (Object[]) iter.next();
				serv = new Servico();
				serv.setLabelServico(obj[0].toString());
				serv.setValorFinanceiro(Double.parseDouble(obj[1].toString()));
				listaFinal.add(serv);
			}
		}
	    return listaFinal;
	}
	
	
	public Long recuperarUltimoIdServicoPorUsuario(long idUsuario, String descServico) {
        return dao.findLastServicoGeradoByIdUsuario(idUsuario, descServico).getId();
	}

	
	public List<Servico> recuperarServicosDisponiveisPorUsuarioPorStatus(Long idUsuario, String status) {
		List<Servico> lista = dao.findServicoByIdUsuarioByStatus(idUsuario, status) ;
        List<Servico> listaFinal = new ArrayList<Servico>();
        for (Servico servico : lista ){
            if ( servico.getDescServico().contains("relatorio") || servico.getDescServico().contains("assinatura"))
                servico.setTipoServicoComum("S");
            else
                servico.setTipoServicoComum("N");
            
            listaFinal.add(servico);
        }
        return listaFinal;
	}

	@Override
	public void selecionarPagamentoServico(RelatorioForm frm, long idInfoServico, long idFormaPagto) {

		Infoservico infoservico = infoservicoService.recuperarInfoServicoPorId(idInfoServico);         
        frm.setValorServico(infoservico.getValorServico());
        if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo());
        }    
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo());
        }
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
            frm.setQuantidadeServico(infoservico.getQuantidade());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo());
        }
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo());
        }   
        Formapagamento formapagamento = paramservicoService.recuperaTipoFormaPagtoPorId(idFormaPagto);
        frm.setFormaPagtoSelecionado(formapagamento.getLabel() );
        frm.setNomeFormaPagto(formapagamento.getNome());		
	}


	@Override
	@Transactional
	public void cadastrarSolicitacaoPagtoServico(RelatorioForm frm,	String opcaoTempoPagto) {		
		Servico servico = new Servico();
        servico.setDataSolicitacao(new Date());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());
        servico.setFormaPgto(frm.getNomeFormaPagto());        
        servico.setUsuario(usuarioDao.findUsuario(frm.getIdUsuarioSelecionado()));
        servico.setOpcaoTempoPagto(frm.getOpcaoTempoPagto());
        servico.setDescServico(frm.getAcao());         
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(frm.getAcao()));        
        servico.setValorServico(frm.getValorServico());
        servico.setTempoDuracao(frm.getTempoDuracao());
        servico.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()); // checar ainda se para Relatorio eh este o tipo de servico
        servico.setAssociadoPlano("N");
        dao.save(servico);		
	}


	@Override
	public void selecionarPagamentoServico(MensagemForm frm, long idInfoServico, long idFormaPagto) {
		
		Infoservico infoservico = infoservicoService.recuperarInfoServicoPorId(idInfoServico);         
        frm.setValorServico(infoservico.getValorServico());
        if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo());
        }    
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_RELATORIO.getRotulo());
        }
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo())){
            frm.setQuantidadeServico(infoservico.getQuantidade());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo());
        }
        else if ( infoservico.getTipoInfoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo())){
            frm.setTempoDuracao(infoservico.getTempoDuracao());        
            frm.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_ASSINATURA.getRotulo());
        }   
        Formapagamento formapagamento = paramservicoService.recuperaTipoFormaPagtoPorId(idFormaPagto);
        frm.setFormaPagtoSelecionado(formapagamento.getLabel() );
        frm.setNomeFormaPagto(formapagamento.getNome());
		
	}


	@Override
	@Transactional
	public void cadastrarSolicitacaoPagtoServico(MensagemForm frm, String opcaoTempoPagto) {		
		Servico servico = new Servico();
        servico.setDataSolicitacao(new Date());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());
        servico.setFormaPgto(frm.getNomeFormaPagto());        
        servico.setUsuario(usuarioDao.findUsuario(frm.getIdUsuarioSelecionado()));
        servico.setOpcaoTempoPagto(frm.getOpcaoTempoPagto());
        servico.setDescServico(frm.getAcao());
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(frm.getAcao()));        
        servico.setValorServico(frm.getValorServico());
        servico.setTempoDuracao(frm.getTempoDuracao());
        servico.setTipoServico(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_USUARIO.getRotulo()); // checar ainda se para Relatorio eh este o tipo de servico
        servico.setAssociadoPlano("N");
        dao.save(servico);		
	}


	@Override
	public void carregarInfoPlanoSelecionado(ServicoForm form) {
		Plano plano = planoService.recuperarPlanoPorId(form.getIdPlanoSelecionado());
		form.setNomePlanoSelecionado(plano.getNome());
		form.setValorPlanoSelecionado(plano.getValorPlano());
	}
	
	@Override
	public ServicoForm carregarInfoPlanoSelecionadoPorId(Long idPlano) {
		Plano plano = planoService.recuperarPlanoPorId(idPlano);
		ServicoForm form = new ServicoForm();		
		form.setNomePlanoSelecionado(plano.getNome());
		form.setValorPlanoSelecionado(plano.getValorPlano());
		return form;
	}


	@Override
	@Transactional
	public void atualizarServico(Servico servico) {
		dao.save(servico);		
	}


	@Override
	@Transactional
	public void cadastrarServico(Servico servico) {
		dao.save(servico);		
	}


	@Override
	public long checarTotalAssinaturas(AdministracaoForm form) {
		return dao.findTotalTipoServicoPorPeriodo(form);
	}
	
	@Override
	public List<RelatorioQuantAssinatura> checarVolFinanceiroAssinaturasPorPeriodo(AdministracaoForm form) {
		
		/*List lista = dao.calcularVolumeFinanceiroMaisSolicitadosMaisPagosPorPeriodoPorTipoServico(dataInicio, dataFim, statusAssinatura, "A");
		List<RelatorioQuantAssinatura> listaFinal = new ArrayList<RelatorioQuantAssinatura>();
		 if ( lista != null && lista.size() > 0 ){
                RelatorioQuantAssinatura relatorio = null;
                Object[] obj = null;
                for (Iterator iter = lista.iterator();iter.hasNext();){
                    obj = (Object[])iter.next(); 
                    relatorio = new RelatorioQuantAssinatura();
                    relatorio.setTipoAssinatura(obj[1].toString());
                    relatorio.setValorFinanceiro(Double.parseDouble(obj[0].toString()));
                    listaFinal.add(relatorio);
                }
            } 
		return listaFinal;*/
		return null;
	}

	@Override
	public Collection<? extends RelatorioQuantAssinatura> checarTotalAssinaturasPorUsuario(Long idUsuario, AdministracaoForm form) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Payment confirmarPagamentoPayPal(HttpServletRequest req,	HttpServletResponse resp, ServicoForm form) {
		Payment createdPayment = null;
		Map<String, String> map = new HashMap<String, String>();
		// ###AccessToken
		// Retrieve the access token from
		// OAuthTokenCredential by passing in
		// ClientID and ClientSecret
		APIContext apiContext = null;
		String accessToken = null;
		try {
			accessToken = GenerateAccessToken.getAccessToken();

			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			apiContext = new APIContext(accessToken);
			// Use this variant if you want to pass in a request id
			// that is meaningful in your application, ideally
			// a order id.
			/*
			 * String requestId = Long.toString(System.nanoTime(); APIContext
			 * apiContext = new APIContext(accessToken, requestId ));
			 */
		} catch (PayPalRESTException e) {
			req.setAttribute("error", e.getMessage());
		}
		if (req.getParameter("PayerID") != null) {
			Payment payment = new Payment();
			if (req.getParameter("guid") != null) {
				payment.setId(map.get(req.getParameter("guid")));
			}

			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(req.getParameter("PayerID"));
			try {
				createdPayment = payment.execute(apiContext, paymentExecution);				
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
		} else {

			// ###Details
			// Let's you specify details of a payment amount.
			Details details = new Details();
			details.setShipping("0");			
			details.setSubtotal(String.format(Locale.US, "%.2f", form.getValorServico()));
			details.setTax("0");			
			
			// ###Amount
			// Let's you specify a payment amount.
			Amount amount = new Amount();
			amount.setCurrency("BRL");
			// Total must be equal to sum of shipping, tax and subtotal.			
			amount.setTotal(String.format(Locale.US, "%.2f", form.getValorServico()));
			amount.setDetails(details);
			
			

			// ###Transaction
			// A transaction defines the contract of a
			// payment - what is the payment for and who
			// is fulfilling it. Transaction is created with
			// a `Payee` and `Amount` types
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setDescription("This is the payment transaction description.");

			// ### Items
			Item item = new Item();			
			if ( form.getTipoServico().equals(TipoParamServicoOpcaoEnum.TIPO_PARAM_SERVICO_IMOVEL.getRotulo()))
				item.setName(form.getLabelServicoFmt()).setQuantity("1").setCurrency("BRL").setPrice(String.format(Locale.US, "%.2f", form.getValorServico()));
			else
				item.setName(form.getLabelServico()).setQuantity("1").setCurrency("BRL").setPrice(String.format(Locale.US, "%.2f", form.getValorServico()));
		
			
			ItemList itemList = new ItemList();
			List<Item> items = new ArrayList<Item>();
			items.add(item);
			itemList.setItems(items);
			
			transaction.setItemList(itemList);
			
			
			// The Payment creation API requires a list of
			// Transaction; add the created `Transaction`
			// to a List
			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			// ###Payer
			// A resource representing a Payer that funds a payment
			// Payment Method
			// as 'paypal'
			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			// ###Payment
			// A Payment Resource; create one using
			// the above types and intent as 'sale'
			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);

			// ###Redirect URLs
			RedirectUrls redirectUrls = new RedirectUrls();
			String guid = UUID.randomUUID().toString().replaceAll("-", "");
			redirectUrls.setCancelUrl(req.getScheme() + "://"
					+ req.getServerName() + ":" + req.getServerPort()
					+ req.getContextPath() + "/servico/cancelarServico?guid=" + guid);
			redirectUrls.setReturnUrl(req.getScheme() + "://"
					+ req.getServerName() + ":" + req.getServerPort()
					+ req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
			payment.setRedirectUrls(redirectUrls);

			
			// Create a payment by posting to the APIService
			// using a valid AccessToken
			// The return object contains the status;
			try {
				createdPayment = payment.create(apiContext);				
				// ###Payment Approval Url
				Iterator<Links> links = createdPayment.getLinks().iterator();
				while (links.hasNext()) {
					Links link = links.next();
					if (link.getRel().equalsIgnoreCase("approval_url")) {
						req.setAttribute("redirectURL", link.getHref());
					}
				}
				
				map.put(guid, createdPayment.getId());
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
			// atualizar status na tabela Servico
			Servico servico = recuperarServicoPorId(form.getIdServicoGerado());
			servico.setStatusPgto(StatusPagtoOpcaoEnum.AGUARDANDO.getRotulo());
			servico.setDataAguardandoPagto(new Date());
			servico.setToken(guid);
			atualizarServico(servico);
			
		}
		
		return createdPayment;		
	}


	@Override
	public ServicoForm carregaServicoForm(Long idServico) {
		Servico servico = dao.findServicoById(idServico);
		ServicoForm form = new ServicoForm();
		BeanUtils.copyProperties(servico, form);
		form.setAcao(servico.getDescServico());
		if ( form.getFormaPgto().equals("payPal"))
			form.setFormaPagtoSelecionado("Pay Pal");
		else if ( form.getFormaPgto().equals("pagSeguro"))
			form.setFormaPagtoSelecionado("Pag Seguro");
		
		form.setIdServicoGerado(idServico);
		return form;
	}
	
	@Override
	public void carregaInfoServicoSelecionado(ServicoForm form) {
		Servico servico = dao.findServicoById(form.getIdServicoSelecionado());		
		BeanUtils.copyProperties(servico, form);
		
		form.setDataSolicitacao(new Date());
		form.setListaOpcoesFormaPagamento(paramservicoService.listarTodasFormasPagamento());
		Paramservico paramservico = paramservicoService.recuperarParamServicoPorId(form.getIdServicoSelecionado());
		form.setAcao(paramservico.getValueServico());
		form.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(paramservico.getValueServico()));
		
	}


	@Override
	public ServicoForm carregaSolicitacaoAssinaturaPorUsuario(UsuarioForm usuario) {
		
		Servico servico = null;
		if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
			servico = dao.findSolAssinaturaByIdUsuario(usuario.getId(), ServicoValueEnum.ASSINATURA_PADRAO.getRotulo());
		else if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
			servico = dao.findSolAssinaturaByIdUsuario(usuario.getId(), ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo());
		else if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
			servico = dao.findSolAssinaturaByIdUsuario(usuario.getId(), ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo());
		
		if (servico != null){
			ServicoForm form = new ServicoForm();
			BeanUtils.copyProperties(servico, form);
			return form;
		}			
		else	
			return null;
	}


	@Override
	@Transactional
	public void cancelarServico(HttpServletRequest req) {
		Servico servico = dao.findServicoByToken(req.getParameter("guid"));
		servico.setStatusPgto(StatusPagtoOpcaoEnum.CANCELADO.getRotulo());
		dao.save(servico);		
	}


	@Override
	public List<Servico> recuperarServicosPorIdPlanoUsuario(Long idPlanoUsuario) {
		return (List<Servico>) dao.findServicosByIdPlanoUsuario(idPlanoUsuario);
	}


	@Override
	public void revogarServicoUsuario(Long idUsuario, Long idServicoSelecionado) {
		Servico servico = dao.findServicoById(idServicoSelecionado);
		Usuario usuario = usuarioService.recuperarUsuarioPorId(idUsuario);
		
		if ( servico.getDescServico().equals(ServicoValueEnum.ADICIONAR_FOTOS.getRotulo())) {	             
            usuario.setQuantMaxFotosPorImovel(usuario.getQuantMaxFotosPorImovel() - servico.getQuantidadeServico());
       }
       else if ( servico.getDescServico().equals(ServicoValueEnum.ADICIONAR_IMOVEIS.getRotulo())) {	            
           usuario.setQuantCadastroImoveis(usuario.getQuantCadastroImoveis() - servico.getQuantidadeServico());
       }
       else if ( servico.getDescServico().equals(ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo())) {
           usuario.setQuantMaxIndicacoesImovel(usuario.getQuantMaxIndicacoesImovel() - servico.getQuantidadeServico());
       }
       else if ( servico.getDescServico().equals(ServicoValueEnum.INDICACOES_EMAIL.getRotulo())) {	            
           usuario.setQuantMaxIndicacoesImovelEmail(usuario.getQuantMaxIndicacoesImovelEmail() - servico.getQuantidadeServico());
       }
	   
	   dao.delete(servico);		 
	}
	
	@Override
	public boolean validarSelecaoPagamentoServico(ServicoForm form, BindingResult result){
		
		boolean obrigatoriedade = false;			
	
		if ( StringUtils.isEmpty(form.getIdInfoServicoPagamentoSelecionada()) && form.getIdInfoServicoPagamentoSelecionada() == -1){
			result.rejectValue("idInfoServicoPagamentoSelecionada", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
		}

		if ( StringUtils.isEmpty(form.getIdFormapagamentoSelecionada()) && form.getIdFormapagamentoSelecionada().longValue() == -1 ){
			result.rejectValue("idFormapagamentoSelecionada", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
		}		
		
		return obrigatoriedade;	
	}
	
	@Override
	public boolean validarSelecaoPagamentoServico(MensagemForm form, BindingResult result){
		
		boolean obrigatoriedade = false;			
	
		if ( StringUtils.isEmpty(form.getIdInfoServicoPagamentoSelecionada()) || form.getIdInfoServicoPagamentoSelecionada() == -1){
			result.rejectValue("idInfoServicoPagamentoSelecionada", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
		}

		if ( StringUtils.isEmpty(form.getIdFormapagamentoSelecionada()) || form.getIdFormapagamentoSelecionada().longValue() == -1 ){
			result.rejectValue("idFormapagamentoSelecionada", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
		}		
		
		return obrigatoriedade;
	
	}


	@Override
	public boolean validarSelecaoPagamentoServico(RelatorioForm form, BindingResult result) {

		boolean obrigatoriedade = false;			
		
		if ( StringUtils.isEmpty(form.getIdInfoServicoPagamentoSelecionada()) || form.getIdInfoServicoPagamentoSelecionada() == -1){
			result.rejectValue("idInfoServicoPagamentoSelecionada", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
		}

		if ( StringUtils.isEmpty(form.getIdFormapagamentoSelecionada()) || form.getIdFormapagamentoSelecionada().longValue() == -1 ){
			result.rejectValue("idFormapagamentoSelecionada", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
		}		
		
		return obrigatoriedade;
	}
}
