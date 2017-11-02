package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
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

import com.busqueumlugar.dao.MensagemDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.StatusPagtoOpcaoEnum;
import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.messaging.MessageSender;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Mensagem;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.MensagemService;
import com.busqueumlugar.service.ParametrosIniciaisService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.EmailJms;
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
public class MensagemServiceImpl implements MensagemService {
	
	private static final Logger log = LoggerFactory.getLogger(MensagemServiceImpl.class);

	@Autowired
	private MensagemDao dao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ServicoService servicoService;	
	
	@Autowired
	private  MessageSender messageSender;
	
	@Autowired
	private ParametrosIniciaisService parametrosIniciaisService;
	
	public Mensagem recuperarMensagemPorId(Long id) {
		return dao.findMensagemById(id);
	}
	
	public void prepararMensagem(Long idUsuarioDe, Usuario usuarioPara,	boolean pago) {
				
	}

	public MensagemForm prepararTodasMensagens(Long idUsuarioDe, Long idIsuarioPara) {
		
		MensagemForm msgForm = new MensagemForm();
        
        msgForm.setIdUsuarioDe(idUsuarioDe);
        msgForm.setIdUsuarioPara(idIsuarioPara);      
        
        Usuario usuarioDe   =  usuarioDao.findUsuario(idUsuarioDe);
        Usuario usuarioPara =  usuarioDao.findUsuario(idIsuarioPara);
        
        msgForm.setLoginUsuarioDe(usuarioDe.getLogin());
        msgForm.setLoginUsuarioPara(usuarioPara.getLogin()); 
        msgForm.setLoginExibicao(usuarioPara.getLogin());
        
        msgForm.setNomeUsuarioDe(usuarioDe.getNome());
        msgForm.setNomeUsuarioPara(usuarioPara.getNome());
        
        return msgForm;    
	}


	@Transactional
	public void enviarMensagem(MensagemForm frm, UsuarioForm user) {		
		Mensagem mensagem = new Mensagem();

        BeanUtils.copyProperties(mensagem, frm);
        Usuario usuarioDe   =  usuarioDao.findUsuario(frm.getIdUsuarioDe());
        Usuario usuarioPara =  usuarioDao.findUsuario(frm.getIdUsuarioPara());
        
        mensagem.setDataMensagem(new Date());
        mensagem.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        mensagem.setUsuarioDe(usuarioDe);
        mensagem.setUsuarioPara(usuarioPara);
        dao.save(mensagem);
        
        boolean isHabilitado = parametrosIniciaisService.isHabilitadoEnvioEmail();
    	if ( isHabilitado){
    		try {	            	
                EmailJms email = new EmailJms();
                email.setSubject(MessageUtils.getMessage("msg.email.subject.mensagem"));
                email.setTo(mensagem.getUsuarioPara().getEmail());
                email.setTexto(MessageUtils.getMessage("msg.email.texto.mensagem"));			            
                //messageSender.sendMessage(email);
    		} catch (Exception e) {		
    			log.error("Mensagem - Erro envio email");
				log.error("Mensagem erro: " + e.getMessage());
    			e.printStackTrace();
    		}
    	}
	}


	@Transactional
	public void criarMensagem(MensagemForm frm) {		
		Mensagem mensagem = new Mensagem();
        mensagem.setDataMensagem(new Date());
        mensagem.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        mensagem.setUsuarioDe(usuarioDao.findUsuario(frm.getIdUsuarioDe()));
        mensagem.setUsuarioPara(usuarioDao.findUsuario(frm.getIdUsuarioPara()));  
        mensagem.setDescricao(frm.getEntradaMensagem());  
        dao.save(mensagem); 
        
        boolean isHabilitado = parametrosIniciaisService.isHabilitadoEnvioEmail();
    	if ( isHabilitado){
    		try {	            	
                EmailJms email = new EmailJms();
                email.setSubject(MessageUtils.getMessage("msg.email.subject.mensagem"));
                email.setTo(mensagem.getUsuarioPara().getEmail());
                email.setTexto(MessageUtils.getMessage("msg.email.texto.mensagem"));			            
                //messageSender.sendMessage(email);
    		} catch (Exception e) {	
    			log.error("Mensagem - Erro envio email");
				log.error("Mensagem erro: " + e.getMessage());
    			e.printStackTrace();
    		}
    	}
	}
	
	public List<Mensagem> listaMensagensDE(UsuarioForm user) {
		return (List<Mensagem>)dao.findMensagensDE(user.getId());
	}


	
	public List<Mensagem> listaMensagensPARA(UsuarioForm user) {		
		return dao.findMensagensPARA(user.getId()); 
	}


	
	public List<Mensagem> recuperaTodasMensagens(Long idUsuario) {
        return dao.findAllMensagemByIdUsuarioOrderByDataMensagem(idUsuario);
	}


	
	public List<MensagemForm> recuperaTodasMensagensPorDataMensagem(Long idUsuario) {		
		List<Mensagem> lista = dao.findAllMensagemByIdUsuarioOrderByDataMensagem(idUsuario);
        List<MensagemForm> listaFinal = new ArrayList<MensagemForm>();        
        for (Mensagem msg : lista){            
            MensagemForm msgForm = new MensagemForm();
            BeanUtils.copyProperties(msg, msgForm);
            
            if ( msg.getUsuarioDe().getId().longValue() == idUsuario.longValue()){
                msgForm.setLoginExibicao(msg.getUsuarioPara().getNome());
                msgForm.setIdUsuarioExibicao((msg.getUsuarioPara().getId()));    
            }
            else if ( msg.getUsuarioPara().getId() == idUsuario.longValue()){
                msgForm.setLoginExibicao(msg.getUsuarioDe().getNome());
                msgForm.setIdUsuarioExibicao((msg.getUsuarioDe().getId()));
            }
         
            listaFinal.add(msgForm);
        }
        
        Hashtable mapFinal = new Hashtable();
        for (MensagemForm msgForm : listaFinal ){            
            if (! mapFinal.containsKey(msgForm.getIdUsuarioExibicao()))
            	mapFinal.put(msgForm.getIdUsuarioExibicao(), msgForm);
        }
        
        List<MensagemForm> listaFinal2 = new ArrayList<MensagemForm>();
        for (Iterator iter = mapFinal.values().iterator();iter.hasNext();){
        	listaFinal2.add((MensagemForm) iter.next());
        }                 
        return listaFinal2; 
	}
	
	public List<MensagemForm> recuperaTodasMensagensNovasPorDataMensagem(Long idUsuario) {		
		List<Mensagem> lista = dao.findAllMensagemNovasByIdUsuarioOrderByDataMensagem(idUsuario);
        List<MensagemForm> listaFinal = new ArrayList<MensagemForm>();        
        for (Mensagem msg : lista){            
            MensagemForm msgForm = new MensagemForm();
            BeanUtils.copyProperties(msg, msgForm);
            
            if ( msg.getUsuarioDe().getId().longValue() == idUsuario.longValue()){
                msgForm.setLoginExibicao(msg.getUsuarioPara().getNome());
                msgForm.setIdUsuarioExibicao((msg.getUsuarioPara().getId()));
                msgForm.setImagemUsuario(msg.getUsuarioPara().getImagemArquivo());
            }
            else if ( msg.getUsuarioPara().getId() == idUsuario.longValue()){
                msgForm.setLoginExibicao(msg.getUsuarioDe().getNome());
                msgForm.setIdUsuarioExibicao((msg.getUsuarioDe().getId()));
                msgForm.setImagemUsuario(msg.getUsuarioDe().getImagemArquivo());
            }
         
            listaFinal.add(msgForm);
        }
        
        Hashtable mapFinal = new Hashtable();
        for (MensagemForm msgForm : listaFinal ){            
            if (! mapFinal.containsKey(msgForm.getIdUsuarioExibicao()))
            	mapFinal.put(msgForm.getIdUsuarioExibicao(), msgForm);
        }
        
        List<MensagemForm> listaFinal2 = new ArrayList<MensagemForm>();
        for (Iterator iter = mapFinal.values().iterator();iter.hasNext();){
        	listaFinal2.add((MensagemForm) iter.next());
        }                 
        return listaFinal2; 
	}
	
	public List<MensagemForm> recuperaTodasMensagensPorDataMensagem(Long idUsuario, int quantMensagens) {		
		List<Mensagem> lista = dao.findAllMensagemByIdUsuarioOrderByDataMensagem(idUsuario, quantMensagens);
        List<MensagemForm> listaFinal = new ArrayList<MensagemForm>();
        Hashtable map = new Hashtable<Object, Object>();
        for (Mensagem msg : lista){            
            MensagemForm msgForm = new MensagemForm();
            BeanUtils.copyProperties(msg, msgForm);            
            if ( msg.getUsuarioDe().getId().longValue() == idUsuario.longValue()){
                msgForm.setLoginExibicao(msg.getUsuarioPara().getNome());
                msgForm.setIdUsuarioExibicao((msg.getUsuarioPara().getId()));
                msgForm.setImagemUsuario(msg.getUsuarioPara().getImagemArquivo());
            }
            else if ( msg.getUsuarioPara().getId() == idUsuario.longValue()){
                msgForm.setLoginExibicao(msg.getUsuarioDe().getNome());
                msgForm.setIdUsuarioExibicao((msg.getUsuarioDe().getId()));
                msgForm.setImagemUsuario(msg.getUsuarioDe().getImagemArquivo());
            }
         
            listaFinal.add(msgForm);
        }
        
        Hashtable mapFinal = new Hashtable();
        for (MensagemForm msgForm : listaFinal ){            
            if (! mapFinal.containsKey(msgForm.getIdUsuarioExibicao()))
            	mapFinal.put(msgForm.getIdUsuarioExibicao(), msgForm);
        }
        
        List<MensagemForm> listaFinal2 = new ArrayList<MensagemForm>();
        for (Iterator iter = mapFinal.values().iterator();iter.hasNext();){
        	listaFinal2.add((MensagemForm) iter.next());
        }                 
        return listaFinal2; 
	}

	
	public List<Mensagem> recuperaTodasMensagens(long idUsuarioDe, long idUsuarioPara, String loginExibicao) {
        /*List<Mensagem> lista1 = dao.findAllMensagemByIdUsuarioDePara(idUsuarioDe, idUsuarioPara);             
        List<MensagemForm> listaFinal = new ArrayList<MensagemForm>();        
        MensagemForm msgForm = null;  
        if ( ! CollectionUtils.isEmpty(lista1) ){
        	for (Mensagem msg : lista1){             
                msgForm = new MensagemForm();            
                msgForm.setImagemUsuario(msg.getUsuarioDe().getImagemArquivo());
                msgForm.setLoginExibicao(msg.getUsuarioDe().getNome() );            
                listaFinal.add(msgForm);
            }
        }
        
   */
        return dao.findAllMensagemByIdUsuarioDePara(idUsuarioDe, idUsuarioPara);
	}
	
	@Transactional
	public void atualizarTodasMensagensRecebidas(long idUsuarioDe, long idUsuarioPara) {		
             
    /*  List<Mensagem> lista1 = dao.findAllMensagemByIdUsuarioDeParaNovas(idUsuarioDe, idUsuarioPara);
      for (Mensagem msg : lista1){             
          if ( msg.getIdUsuarioPara() == idUsuarioDe ){
        	  msg.setStatus("lido");
        	  dao.save(msg);
          }          
      } */
	}

	
	public boolean checarExisteNovaMensagem(Long idUsuario) {		
		if (! CollectionUtils.isEmpty(dao.findExistsNovaMensagem(idUsuario))){
			return true;
		}
		else
			return false;	
	}


	
	public long checarQuantidadeNovasMensagens(Long idUsuario) {	
		return dao.findQuantMensagensByIdUsuarioByStatusLeitura(idUsuario, StatusLeituraEnum.NOVO.getRotulo());
	}


	
	public List<MensagemForm> recuperarNovasMensagens(List<MensagemForm> lista,	UsuarioForm usuario) {		
		List<MensagemForm> listaFinal = new ArrayList<MensagemForm>();
        for (MensagemForm mensagemForm : lista){            
            if ( mensagemForm.getStatus().equals(StatusLeituraEnum.NOVO.getRotulo()) && mensagemForm.getIdUsuarioPara() == usuario.getId().longValue())                 
                listaFinal.add(mensagemForm);            
        }
        return listaFinal;
	}


	@Transactional
	public boolean atualizarStatusMensagem(Long idMensagem) {		
		boolean resultado = false;
        Mensagem mensagem = dao.findMensagemById(idMensagem);
        if ( mensagem.getStatus().equals(StatusLeituraEnum.NOVO.getRotulo())){
            mensagem.setStatus(StatusLeituraEnum.LIDO.getRotulo());
            resultado = false;
        }
        else {
            mensagem.setStatus(StatusLeituraEnum.NOVO.getRotulo());
            resultado = true;
        }
        dao.save(mensagem);
        return resultado;    
	}

	public EmailImovel notificarNovaMensagem(MensagemForm frm) {		
		EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioDestino = usuarioDao.findUsuario(frm.getIdUsuarioPara());
        // assunto        
        email.setAssunto(MessageUtils.getMessage("msg.email.notificacao.nova.mensagem"));
        // para
        email.setTo(usuarioDestino.getEmail());
        
        // Texto do Email
        texto.append(MessageUtils.getMessage("msg.email.notiicacao.nova.mensagem.texto1") + "\n");        
        texto.append(MessageUtils.getMessage("msg.email.notiicacao.nova.mensagem.texto2") + "\n");        
        texto.append(MessageUtils.getMessage("msg.email.notiicacao.nova.data.mensagem") + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());      
        
        email.setAcao("novaMensagem");        
        return email;
	}


	@Override
	public Payment confirmarPagamentoPayPal(HttpServletRequest req, HttpServletResponse resp, MensagemForm form) {

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
			details.setSubtotal(String.valueOf(form.getValorServico()));
			details.setTax("0");

			// ###Amount
			// Let's you specify a payment amount.
			Amount amount = new Amount();
			amount.setCurrency("BRL");
			// Total must be equal to sum of shipping, tax and subtotal.
			amount.setTotal(String.valueOf(form.getValorServico()));
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
			item.setName("Mensagens").setQuantity("1").setCurrency("BRL").setPrice(String.valueOf(form.getValorServico()));
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
			// atualizar status na tabela Servico
			Servico servico = servicoService.recuperarServicoPorId(form.getIdServicoGerado());
			servico.setStatusPgto(StatusPagtoOpcaoEnum.AGUARDANDO.getRotulo());
			servico.setDataAguardandoPagto(new Date());
			servicoService.atualizarServico(servico);
			
		}
		return createdPayment;
	}

	@Override
	public String validarCriarMensagem(MensagemForm form) {
		
		if ((form.getEntradaMensagem() == null) || ( form.getEntradaMensagem() != null && form.getEntradaMensagem().equals("")))
			return MessageUtils.getMessage("msg.erro.descricao.mensagem.obrigatorio");		
				
		return "";
	}

}
