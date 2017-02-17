package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.EstadosDao;
import com.busqueumlugar.dao.ParamservicoDao;
import com.busqueumlugar.dao.PlanoDao;
import com.busqueumlugar.dao.PlanousuarioDao;
import com.busqueumlugar.dao.ServicoDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.AcaoNotificacaoEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.enumerador.StatusPagtoOpcaoEnum;
import com.busqueumlugar.enumerador.TipoNotificacaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ParamservicoForm;
import com.busqueumlugar.form.PlanousuarioForm;
import com.busqueumlugar.form.ServicoForm;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.RelatorioQuantPlano;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.ParamservicoService;
import com.busqueumlugar.service.PlanoService;
import com.busqueumlugar.service.PlanousuarioService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.GenerateAccessToken;
import com.busqueumlugar.util.MessageUtils;
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

@Service
public class PlanousuarioServiceImpl implements PlanousuarioService{
	
	private static final Logger log = LoggerFactory.getLogger(PlanousuarioServiceImpl.class);
	
	@Autowired
	private PlanousuarioDao dao;
	
	@Autowired
	private PlanoDao planoDao;
	
	@Autowired
	private ParamservicoDao paramservicoDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	@Autowired
	private ServicoDao servicoDao;

	public Planousuario recuperarPlanousuarioPorId(Long id) {
		return dao.findPlanousuarioById(id);
	}

	@Override
	@Transactional
	public void cadastrarSolicitacaoPlano(Long idPlanoSelecionado, Long idUsuario, String nomeFormaPagto) {		
		Planousuario p = new Planousuario();
        p.setPlano(planoDao.findPlanoById(idPlanoSelecionado));
        p.setDataSolicitacao(new Date());
        p.setUsuario(usuarioDao.findUsuario(idUsuario));
        p.setStatus(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());
        p.setFormaPagto(nomeFormaPagto);
        p.setValorPlano(p.getPlano().getValorPlano());
        p.setNomePlano(p.getPlano().getNome());
        dao.save(p);		
	}

	
	public void simulaRetornoPagtoPlano(Long idPlanoUsuario) {	
		Planousuario p = dao.findPlanousuarioById(idPlanoUsuario);
        
        int quantDias = p.getPlano().getDuracaoPlano() * 30 ; // duracao do plano esta em meses
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 
        cal.add(Calendar.DAY_OF_MONTH, quantDias);         
        
        // atualizar os servicos
        atualizarServicoImovelPlano(p, p.getPlano(), ServicoValueEnum.ADICIONAR_FOTOS.getRotulo());        
        atualizarServicoImovelPlano(p, p.getPlano(), ServicoValueEnum.ADICIONAR_IMOVEIS.getRotulo());
        atualizarServicoImovelPlano(p, p.getPlano(), ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo());
        atualizarServicoImovelPlano(p, p.getPlano(), ServicoValueEnum.INDICACOES_EMAIL.getRotulo());
        atualizarServicoRelatorioPlano(p, p.getPlano());
 
        p.setDataPagto(new Date());
        p.setStatus(StatusPagtoOpcaoEnum.PAGO.getRotulo());        
        dao.save(p); 
        
        //inserindo notificacao de pagamento do plano
        notificacaoService.cadastrarNotificacao(p.getUsuario().getId(), 
        										AcaoNotificacaoEnum.PLANO.getRotulo(),
        										MessageUtils.getMessage("msg.sucesso.recebimento.pagto.plano") +": " + p.getPlano().getNome(), 
        										TipoNotificacaoEnum.PLANO.getRotulo(),
        										0l);
		
	}
	
	
	private void atualizarServicoImovelPlano(Planousuario p, Plano plano, String descServico)  {            
        Servico servico = new Servico();
        servico.setDataSolicitacao(p.getDataSolicitacao());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());
        servico.setFormaPgto(p.getFormaPagto()); 
        servico.setUsuario(p.getUsuario());        
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(descServico));        
        servico.setValorServico(0); // tratar ainda
        servico.setTempoDuracao(0);
        servico.setDataPagto(new Date());  
        servico.setAssociadoPlano("S");
        servico.setDescServico(servico.getParamServico().getValueServico());
        Usuario usuario = servico.getUsuario();
        servico.setPlanousuario(p);
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
            Date dtAtual = new Date();                
            int quantDias = plano.getDuracaoPlano() * 30 ; // duracao do plano esta em meses
            Calendar cal = Calendar.getInstance(); 
            cal.setTime(new Date()); 
            cal.add(Calendar.DAY_OF_MONTH, quantDias);
            servico.setDataPagto(dtAtual);
            servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());        
            servico.setDataFimServico(cal.getTime());        
            //controller.edit(servico);
            servicoDao.save(servico);
            
        }
        usuarioDao.save(usuario);
        servicoDao.save(servico);
	
	}
	
	private void atualizarServicoRelatorioPlano(Planousuario p, Plano plano)  {
        Servico servico = new Servico();
        servico.setDataSolicitacao(p.getDataSolicitacao());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.PAGO.getRotulo());
        servico.setFormaPgto(p.getFormaPagto()); 
        servico.setUsuario(p.getUsuario());  
        servico.setPlanousuario(p);
        Usuario user = p.getUsuario();
        String descServico = "";
        if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
            descServico = (ServicoValueEnum.RELATORIO_PADRAO.getRotulo());
        else if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
            descServico = (ServicoValueEnum.RELATORIO_CORRETOR.getRotulo());
        else if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
            descServico = (ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo());
        
        
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(descServico));
        servico.setDescServico(servico.getParamServico().getValueServico());
        servico.setValorServico(0); // tratar ainda            
        servico.setDataPagto(new Date());
        servico.setAssociadoPlano("S");            
       
        Date dtAtual = new Date();                
        int quantDias = plano.getDuracaoPlano() * 30 ; // duracao do plano esta em meses
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 
        cal.add(Calendar.DAY_OF_MONTH, quantDias);
        servico.setDataPagto(dtAtual);     
        servico.setDataFimServico(cal.getTime());                    
        servicoDao.save(servico);
        
	}
	
	private void atualizarConcessaoServicoImovelPlano(Planousuario p,	Plano plano, String descServico) {
		Servico servico = new Servico();
        servico.setDataSolicitacao(p.getDataSolicitacao());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo());
        servico.setFormaPgto(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo()); 
        servico.setUsuario(p.getUsuario());
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(descServico));        
        servico.setValorServico(0); // tratar ainda
        servico.setTempoDuracao(0);
        servico.setAssociadoPlano("S");
        servico.setPlanousuario(p);
        servico.setDescServico(servico.getParamServico().getValueServico());
        servico.setPlanousuario(p);
        
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
            //Date dtAtual = new Date();                
            int quantDias = plano.getDuracaoPlano() * 30 ; // duracao do plano esta em meses
            Calendar cal = Calendar.getInstance(); 
            cal.setTime(new Date()); 
            cal.add(Calendar.DAY_OF_MONTH, quantDias);
            //servico.setDataPagto(dtAtual);            
            servico.setStatusPgto(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo());        
            servico.setDataFimServico(cal.getTime());
            servicoDao.save(servico);
        }
        usuarioDao.save(usuario);
        servicoDao.save(servico);		
	}
	
	private void atualizarConcessaoServicoRelatorioPlano(Planousuario p,	Plano plano) {
		Servico servico = new Servico();
        servico.setDataSolicitacao(p.getDataSolicitacao());
        servico.setStatusPgto(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo());
        servico.setFormaPgto(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo()); 
        servico.setUsuario(p.getUsuario());
        servico.setPlanousuario(p);
        
        Usuario user = p.getUsuario();
        String descServico = "";
        if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
            descServico = (ServicoValueEnum.RELATORIO_PADRAO.getRotulo());
        else if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()))
            descServico = (ServicoValueEnum.RELATORIO_CORRETOR.getRotulo());
        else if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
            descServico = (ServicoValueEnum.RELATORIO_IMOBILIARIA.getRotulo());
        
        servico.setParamServico(paramservicoDao.findParamservicoPorNome(descServico));        
        servico.setValorServico(0); // tratar ainda
        servico.setAssociadoPlano("S");            
       
        //Date dtAtual = new Date();                
        int quantDias = plano.getDuracaoPlano() * 30 ; // duracao do plano esta em meses
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 
        cal.add(Calendar.DAY_OF_MONTH, quantDias);
        //servico.setDataPagto(dtAtual);     
        servico.setDataFimServico(cal.getTime());                    
        servicoDao.save(servico);
		
	}

	
	public List<Planousuario> recuperarPlanosPorUsuario(Long idUsuario) {	
		return dao.findPlanoUsuarioByIdUsuario(idUsuario);
	}


	public List<Planousuario> recuperarPlanosPorUsuarioPorStatus(Long idUsuario, AdministracaoForm form) {
		return dao.findPlanoUsuarioByIdUsuario(idUsuario);
	}
	
	@Override
	public List<RelatorioQuantPlano> recuperarUsuariosPlanosVolFinanceiro(AdministracaoForm form) {
		List lista = dao.findPlanosUsuariosVolFinanceiro(form);
		List<RelatorioQuantPlano> listaFinal = new ArrayList<RelatorioQuantPlano>();
		if (!CollectionUtils.isEmpty(lista)){
			RelatorioQuantPlano rel = null;
			Usuario usuario = null;
			Object[] obj = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				usuario = usuarioDao.findUsuario(Long.parseLong(obj[0].toString()));
				rel = new RelatorioQuantPlano();
				rel.setNomeUsuario(usuario.getNome());
				rel.setNomePlano(obj[1].toString());
				rel.setValorFinanceiro(Double.parseDouble(obj[2].toString()));
				listaFinal.add(rel);
			} 
		}		
		return listaFinal;
	}

	@Override
	public Long recuperarUltimoIdPlanoGerado(Long idUsuario) {
		Planousuario planousuario = dao.findLastPlanoGeradoByIdUsuario(idUsuario);
        return planousuario.getId();
	}

	@Override
	public long checarQuantTotalPlanosPorData(AdministracaoForm form) {
		
		List lista = dao.findTotalQuantidadePlanos(form);
		long quantTotal = 0;
		if (!CollectionUtils.isEmpty(lista)){
			Object[] obj = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				quantTotal = Long.parseLong(obj[1].toString());
			}                
			return quantTotal;
		}
		else			
			return 0;
	}

	@Override
	public List<RelatorioQuantPlano> checarVolumeFinanceirosPlanoPorDataPorStatus(AdministracaoForm form) {
		List lista = dao.findPlanosVolFinanceiro(form);
		List<RelatorioQuantPlano> listaFinal = new ArrayList<RelatorioQuantPlano>();
		if (!CollectionUtils.isEmpty(lista)){
			Object[] obj = null;
			RelatorioQuantPlano relatorio = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				relatorio = new RelatorioQuantPlano();
				relatorio.setNomePlano(obj[0].toString());
				relatorio.setValorFinanceiro(Double.parseDouble(obj[1].toString()));
				listaFinal.add(relatorio);
			}			
		}
		return listaFinal;
	}

	@Override
	public Payment confirmarPagamentoPayPalPlano(HttpServletRequest req, HttpServletResponse resp, ServicoForm form) {
 
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
			details.setSubtotal(String.format(Locale.US, "%.2f", form.getValorPlanoSelecionado()));
			
			details.setTax("0");

			// ###Amount
			// Let's you specify a payment amount.
			Amount amount = new Amount();
			amount.setCurrency("BRL");
			// Total must be equal to sum of shipping, tax and subtotal.
			amount.setTotal(String.format(Locale.US, "%.2f", form.getValorPlanoSelecionado()));
			
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
			item.setName(form.getNomePlanoSelecionado()).setQuantity("1").setCurrency("BRL").setPrice(String.format(Locale.US, "%.2f", form.getValorPlanoSelecionado()));
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
					+ req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
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
			// atualizar status na tabela Planousuario
			Planousuario planousuario = recuperarPlanousuarioPorId(form.getIdPlanoSelecionado());
			planousuario.setStatus(StatusPagtoOpcaoEnum.AGUARDANDO.getRotulo());
			planousuario.setDataAguardando(new Date());
			dao.save(planousuario);
			
		}
		
		return createdPayment;	
	}

	@Override
	@Transactional
	public void excluirPlanousuario(Long idPlano) {
		Planousuario plano = dao.findPlanousuarioById(idPlano);
		dao.delete(plano);		
	}

	@Override
	public ServicoForm carregarPlanousuario(Long idPlano) {
		Planousuario plano = dao.findPlanousuarioById(idPlano);
		ServicoForm form = new ServicoForm();
		form.setIdPlanoSelecionado(idPlano);
		
		if ( plano.getFormaPagto().equals("payPal"))
			form.setFormaPagtoSelecionado("Pay Pal");
		else if ( plano.getFormaPagto().equals("pagSeguro"))
			form.setFormaPagtoSelecionado("Pag Seguro");
		
		form.setNomePlanoSelecionado(plano.getPlano().getNome());
		form.setValorPlanoSelecionado(plano.getValorPlano());
		return form;
	}

	@Override
	public String validarSolicitacaoPlano(Long idUsuario, ServicoForm form) {
		
		if (( form.getIdPlanoSelecionado() == null ) || (form.getIdPlanoSelecionado() != null && form.getIdPlanoSelecionado().longValue() == -1)) 
			return MessageUtils.getMessage("msg.erro.nenhum.plano.selecionado");
		
		Planousuario plano = dao.findPlanoUsuarioByIdPlanoByIdUsuario(idUsuario, form.getIdPlanoSelecionado());			
		if ( plano != null ){
			if ( plano.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()))
				return MessageUtils.getMessage("msg.erro.plano.selecionado.anteriormente");
			else if ( plano.getStatus().equals(StatusPagtoOpcaoEnum.AGUARDANDO.getRotulo()))
				return  MessageUtils.getMessage("msg.erro.plano.aguardando.pagto");							
		}					
				
		return "";
	}
	
	@Override
	public String validarSolicitacaoPlano(Long idUsuario, Long idPlano) {
		
		if (( idPlano == null ) || (idPlano != null && idPlano.longValue() == -1)) 
			return MessageUtils.getMessage("msg.erro.nenhum.plano.selecionado");
		
		Planousuario plano = dao.findPlanoUsuarioByIdPlanoByIdUsuario(idUsuario, idPlano);			
		if ( plano != null ){
			if ( plano.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()))
				return MessageUtils.getMessage("msg.erro.plano.selecionado.anteriormente");
			else if ( plano.getStatus().equals(StatusPagtoOpcaoEnum.AGUARDANDO.getRotulo()))
				return  MessageUtils.getMessage("msg.erro.plano.aguardando.pagto");							
		}					
		
		return "";
	}

	@Override
	public void concedePlano(Long idUsuario, long idPLanoSelecionado) {
		Planousuario p = new Planousuario();
        p.setPlano(planoDao.findPlanoById(idPLanoSelecionado));
        p.setDataSolicitacao(new Date());
        p.setUsuario(usuarioDao.findUsuario(idUsuario));
        p.setStatus(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo());
        p.setFormaPagto(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo());
        p.setValorPlano(p.getPlano().getValorPlano());
        dao.save(p);        
        
        int quantDias = p.getPlano().getDuracaoPlano() * 30 ; // duracao do plano esta em meses
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 
        cal.add(Calendar.DAY_OF_MONTH, quantDias);         
        
        // atualizar os servicos
        atualizarConcessaoServicoImovelPlano(p, p.getPlano(), ServicoValueEnum.ADICIONAR_FOTOS.getRotulo());        
        atualizarConcessaoServicoImovelPlano(p, p.getPlano(), ServicoValueEnum.ADICIONAR_IMOVEIS.getRotulo());
        atualizarConcessaoServicoImovelPlano(p, p.getPlano(), ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo());
        atualizarConcessaoServicoImovelPlano(p, p.getPlano(), ServicoValueEnum.INDICACOES_EMAIL.getRotulo());
        atualizarConcessaoServicoRelatorioPlano(p, p.getPlano());
                        
        dao.save(p); 		
	}

	

	@Override
	public void revogarPlano(Long idUsuario, Long idPlanoSelecionado) {

		Planousuario planoUsuario = dao.findPlanousuarioById(idPlanoSelecionado);
		
		List<Servico> listaServicos = servicoDao.findServicosByIdPlanoUsuario(planoUsuario.getId());
		Usuario usuario = planoUsuario.getUsuario();
		for (Servico servico : listaServicos){			
	        
	        if ( servico.getDescServico().equals(ServicoValueEnum.ADICIONAR_FOTOS.getRotulo())) {	             
	             usuario.setQuantMaxFotosPorImovel(usuario.getQuantMaxFotosPorImovel() - planoUsuario.getPlano().getQuantFotosImovel());
	        }
	        else if ( servico.getDescServico().equals(ServicoValueEnum.ADICIONAR_IMOVEIS.getRotulo())) {	            
	            usuario.setQuantCadastroImoveis(usuario.getQuantCadastroImoveis() - planoUsuario.getPlano().getQuantCadastroImoveis());
	        }
	        else if ( servico.getDescServico().equals(ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo())) {
	            usuario.setQuantMaxIndicacoesImovel(usuario.getQuantMaxIndicacoesImovel() - planoUsuario.getPlano().getQuantImoveisIndicacao());
	        }
	        else if ( servico.getDescServico().equals(ServicoValueEnum.INDICACOES_EMAIL.getRotulo())) {	            
	            usuario.setQuantMaxIndicacoesImovelEmail(usuario.getQuantMaxIndicacoesImovelEmail() - planoUsuario.getPlano().getQuantEmailsPorImovel());
	        }
	      	        
	        servicoDao.delete(servico);
		}			
		usuarioDao.save(usuario);
		planoUsuario.setStatus(StatusPagtoOpcaoEnum.REVOGADO.getRotulo());
		dao.save(planoUsuario);
	}

	@Override
	public String validarCadastroSolicitacaoPlano(Long idUsuario, ServicoForm form) {
		
		
		if ((form.getIdFormapagamentoSelecionada() == null ) || (form.getIdFormapagamentoSelecionada() != null && form.getIdFormapagamentoSelecionada().longValue() == -1))
			return  MessageUtils.getMessage("msg.erro.plano.nenhum.forma.pagto");		
			
		return "";
	}	

}
