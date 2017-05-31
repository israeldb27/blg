package com.busqueumlugar.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.busqueumlugar.service.BairrosService;
import com.busqueumlugar.service.CidadesService;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.EstadosService;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImoveldestaqueService;
import com.busqueumlugar.service.InfoservicoService;
import com.busqueumlugar.service.MensagemAdminService;
import com.busqueumlugar.service.MensagemService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.NotificacaoService;
import com.busqueumlugar.service.ParametrosIniciaisService;
import com.busqueumlugar.service.RecomendacaoService;
import com.busqueumlugar.service.SeguidorService;
import com.busqueumlugar.service.ServicoService;
import com.busqueumlugar.service.TimelineService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.EnviaEmailHtml;
import com.busqueumlugar.util.JsfUtil;
import com.busqueumlugar.util.MessageUtils;
import com.busqueumlugar.util.PasswordGenerator;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.util.UsuarioInterface;
import com.busqueumlugar.dao.BairrosDao;
import com.busqueumlugar.dao.CidadesDao;
import com.busqueumlugar.dao.EstadosDao;
import com.busqueumlugar.dao.FormapagamentoDao;
import com.busqueumlugar.dao.ImovelPropostasDao;
import com.busqueumlugar.dao.ImovelcomentarioDao;
import com.busqueumlugar.dao.ImovelfavoritosDao;
import com.busqueumlugar.dao.ImovelindicadoDao;
import com.busqueumlugar.dao.ImovelvisualizadoDao;
import com.busqueumlugar.dao.IntermediacaoDao;
import com.busqueumlugar.dao.ItemMensagemAdminDao;
import com.busqueumlugar.dao.ParamservicoDao;
import com.busqueumlugar.dao.ParceriaDao;
import com.busqueumlugar.dao.PlanoDao;
import com.busqueumlugar.dao.PlanousuarioDao;
import com.busqueumlugar.dao.PreferencialocalidadeDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ContatoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.NotaForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.ServicoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.messaging.MessageSender;
import com.busqueumlugar.model.*;
import com.busqueumlugar.enumerador.AcaoNotificacaoEnum;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.NotaAcaoEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.enumerador.StatusImovelCompartilhadoEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.StatusUsuarioEnum;
import com.busqueumlugar.enumerador.TipoNotificacaoEnum;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private UsuarioDao dao;	
		
	@Autowired
	private EstadosDao estadosDao;	
	
	@Autowired
	private CidadesDao cidadesDao;	
	
	@Autowired
	private BairrosDao bairrosDao;
	
	@Autowired
	private EstadosService estadosService;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Autowired
	private BairrosService bairrosService;
	
	@Autowired
	private NotaService notaService;	
	
	@Autowired
	private PreferencialocalidadeDao preferenciaLocalidadeDao;	
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ImovelfavoritosDao imovelFavoritosDao;
	
	@Autowired
	private ImovelFavoritosService imovelFavoritosService;
	
	@Autowired
	private ImovelcomentarioDao imovelcomentarioDao;
	
	@Autowired
	private ImovelPropostasDao imovelPropostasDao;
	
	@Autowired
	private ImovelvisualizadoDao imovelvisualizadoDao;
	
	@Autowired
	private ImovelindicadoDao imovelindicadoDao;	
	
	@Autowired
	private IntermediacaoDao intermediacaoDao;
	
	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private MensagemService mensagemService;
	
	@Autowired
	private MensagemAdminService mensagemAdminService;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	@Autowired
	private ParamservicoDao paramservicoDao;
	
	@Autowired
	private FormapagamentoDao formapagamentoDao;
	
	@Autowired
	private ItemMensagemAdminDao itemMensagemAdminDao;
	
	@Autowired
	private InfoservicoService infoservicoService;
	
	@Autowired
	private ParametrosIniciaisService parametrosIniciaisService;
	
	@Autowired
	private ServicoService servicoService;
	
	
	@Autowired
	private PlanousuarioDao planousuarioDao;
	
	@Autowired
	private PlanoDao planoDao;
	
	@Autowired
	private SeguidorService seguidorService;
	
	@Autowired
	private ParceriaDao	parceriaDao;
	
	@Autowired
	private RecomendacaoService recomendacaoService;
	
	@Autowired
	private ImoveldestaqueService imovelDestaqueService;
	
	@Autowired
	private ServletContext context;	
	
	
	 @Autowired
	 private  MessageSender messageSender;
	
	
	@Override
	public boolean validarLoginPassword(UsuarioForm form) {
		Usuario usuario = dao.findUsuarioByCampo(form, "loginPassword");
		if ( usuario != null )
			return true;
		else
			return false;
	}
	
	
	public List<Usuario> listarUsuarios() {		
		return dao.listaUsuarios();
	}

	
	@Transactional
	public void atualizarPontuacaoNegociacaoImovel(Usuario usuario,	String situacaoNegociacao) {		
        if ( ! usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
            if ( situacaoNegociacao.equals("sucesso") )
            usuario.setPontosNegociacaoImovel(usuario.getPontosNegociacaoImovel() + 1 );
            else if ( situacaoNegociacao.equals("revogado") ){	
                if ( usuario.getPontosNegociacaoImovel() > 0 )
                    usuario.setPontosNegociacaoImovel(usuario.getPontosNegociacaoImovel() - 1 );            
            }    
            dao.save(usuario);
        } 
	}

	
	public UsuarioForm carregaUsuarioByLogin(UsuarioForm form) {	
		Usuario  usuario = null;
        if ( JsfUtil.isEmail(form.getLogin()))
            usuario = dao.findUsuarioByCampo(form, "emailPassword");
        else
            usuario = dao.findUsuarioByCampo(form, "loginPassword");
        
        
        String cobrarAssinatura = parametrosIniciaisService.findParametroInicialPorNome("cobrarAssinatura");
        if ( cobrarAssinatura.equals("S")){
        	Date dtAtual = new Date();
            if ((usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.ADMIN.getRotulo())) || (usuario.getDataValidadeAcesso().compareTo(dtAtual) > 0))
            	usuario.setAcessoValido("S");
            else
            	usuario.setAcessoValido("N");
        }
        else 
        	usuario.setAcessoValido("S");
        
        UsuarioForm usuarioForm = new UsuarioForm();
        if ( usuario != null) {            
            dao.save(usuario);
            BeanUtils.copyProperties(usuario, usuarioForm);            
        }                
        return usuarioForm;
	}


	
	public Usuario recuperarUsuarioPorId(Long idUsuario) {
		return dao.findUsuario(idUsuario);
	}


	@Transactional
	public UsuarioForm cadastrarUsuario(UsuarioForm frm) throws EmailException {	
		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(frm, usuario);
        usuario.setPassword(md5(frm.getPassword()));
        usuario.setStatusUsuario(StatusUsuarioEnum.CRIADO.getRotulo());
        usuario.setDataSuspensao(new Date());
        usuario.setDataCadastro(new Date());
        usuario.setDataUltimoAcesso(null);
        usuario.setAtivado("S");
        usuario.setHabilitaBusca("S");
        usuario.setHabilitaDetalhesInfoUsuario("S");
        usuario.setHabilitaRecebeSeguidor("S");
               
        usuario.setDataNascimento(DateUtil.formataDataBanco(frm.getDataNascimentoFmt()));
        
        if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()))
        	usuario.setCpf(frm.getCnpj());
        else 	
        	usuario.setCpf(frm.getCpf());       
        
        // carregar aqui o objeto de parametros iniciais
        Hashtable map = parametrosIniciaisService.carregarParametrosIniciais();
                        
        usuario.setQuantCadastroImoveis(Integer.parseInt(map.get(UsuarioInterface.QUANT_CADASTRO_IMOVEIS_INICIAL).toString()));
        usuario.setQuantMaxFotosPorImovel(Integer.parseInt(map.get(UsuarioInterface.QUANT_FOTOS_IMOVEL_INICIAL).toString()));
        usuario.setQuantMaxIndicacoesImovel(Integer.parseInt(map.get(UsuarioInterface.QUANT_INDICACOES_IMOVEL).toString()));
        usuario.setQuantMaxIndicacoesImovelEmail(Integer.parseInt(map.get(UsuarioInterface.QUANT_INDICACOES_IMOVEL_EMAIL).toString()));
        usuario.setHabilitaEnvioSolParceria(map.get(UsuarioInterface.HABILITA_ENVIAR_SOL_PARCERIA).toString());
        usuario.setHabilitaEnvioMensagens(map.get(UsuarioInterface.HABILITA_ENVIAR_MENSAGENS).toString());        
        
        Estados estado = estadosDao.findEstadosById(frm.getIdEstado());
        Cidades cidade = cidadesDao.findCidadesById(frm.getIdCidade());
        Bairros bairro = bairrosDao.findBairrosById(frm.getIdBairro());
        
        usuario.setUf(estado.getUf());
        usuario.setEstado(estado.getNome());
        usuario.setCidade(cidade.getNome());
        usuario.setBairro(bairro.getNome()); 
        usuario.setCodigoIdentificacao(this.gerarCodigoIdentificacao(usuario, estado));
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 
        //cal.add(Calendar.DAY_OF_MONTH, UsuarioInterface.QUANT_DIAS_FIM_SERVICO);
        usuario.setDataValidadeAcesso(cal.getTime());

        dao.save(usuario);
        frm = new UsuarioForm();        
        BeanUtils.copyProperties(usuario, frm);                
        notaService.cadastrarNota(MessageUtils.getMessage("msg.usuario.para.cadastrou.usuario"),
        						  usuario, 
        						  new Date(),
        						  NotaAcaoEnum.USUARIO.getRotulo());
        notificacaoService.cadastrarNotificacao(usuario, 
        										AcaoNotificacaoEnum.USUARIO.getRotulo(),  
												MessageUtils.getMessage("lbl.bem.vindo.plataforma"), 
												TipoNotificacaoEnum.USUARIO.getRotulo(),
												0l);
        
        // 
       /* EnviaEmailHtml enviaEmail = new EnviaEmailHtml();
        enviaEmail.setSubject(MessageUtils.getMessage("msg.email.subject.confirmacao.cadastro.usuario"));
        enviaEmail.setTo(frm.getEmail());
        enviaEmail.setTexto(MessageUtils.getMessage("msg.email.texto.confirmacao.cadastro.usuario") + "<link> ");		            	
        enviaEmail.enviaEmail(enviaEmail.getEmail());        
        */
        return frm;
	}


	
	public String gerarCodigoIdentificacao(Usuario usuario, Estados estado) {	
		String uf = estado.getUf().toUpperCase();
        DateUtil dt = new DateUtil();
        int ano = dt.getYear();
        int mes = dt.getMonth();
        int seq =  (int)(Math.random() * 1000000);
        String resultado = uf + ano + mes + seq;
        Usuario usuarioExiste = dao.findUsuarioByCodigoIdentificacao(resultado);
        if (  usuarioExiste == null )
            return resultado;
        else  {      
            return this.gerarCodigoIdentificacao(usuario, estado);        
        }
	}


	@Transactional
	public UsuarioForm editarUsuario(UsuarioForm frm) {	
		Usuario usuarioAux = dao.findUsuario(frm.getId());
        Usuario usuario = new Usuario();
        usuario.setPassword(usuarioAux.getPassword());        
        BeanUtils.copyProperties(frm, usuario);
   
        Estados estado = estadosDao.findEstadosById(frm.getIdEstado());
        Cidades cidade = cidadesDao.findCidadesById(frm.getIdCidade());
        Bairros bairro = bairrosDao.findBairrosById(frm.getIdBairro());

        usuario.setUf(estado.getUf());
        usuario.setEstado(estado.getNome());
        usuario.setCidade(cidade.getNome());
        usuario.setBairro(bairro.getNome());       
        
        usuario.setDataNascimento(DateUtil.formataDataBanco(frm.getDataNascimentoFmt()));
        
        dao.save(usuario);
          
        notaService.cadastrarNota(MessageUtils.getMessage("msg.usuario.para.atualizou.usuario"), frm.getId(), new Date(), NotaAcaoEnum.USUARIO.getRotulo());        
        return frm; 
	}
	
	public String md5(String senha) {	
		String sen = "";  
        MessageDigest md = null;  
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        sen = hash.toString(16);      
        return sen;
	}


	
	public List<Usuario> buscarUsuarios(AdministracaoForm form) {	
		return dao.findUsuarios(form);
	}
	
	@Override
	public boolean validarDadosCadastroUsuario(UsuarioForm form, BindingResult result){

		boolean filtroValido = false;

        if (result.hasErrors()) {            
            return false;
        }
		
		// inicio - validacao minhas informacoes
		if ( StringUtils.isEmpty(form.getNome())){
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getDescSobreMim())){
			result.rejectValue("descSobreMim", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getLogin())){
			result.rejectValue("login", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else {
			Usuario usuario = dao.findUsuarioByCampo(form, "login");
			if ( usuario != null){
				result.rejectValue("login", "msg.erro.login.existente");
				filtroValido = true;                   
			}
		}
		
		if ( StringUtils.isEmpty(form.getPerfil())){
			result.rejectValue("perfil", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else {
			if ( form.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || form.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ) {
				if ( StringUtils.isEmpty(form.getCpf())){
					result.rejectValue("cpf", "msg.erro.campo.obrigatorio");
					filtroValido = true;                   
				}
				else if ( ! JsfUtil.isValidoCPF(form.getCpf())){
					result.rejectValue("cpf", "msg.erro.cpf.invalido");
					filtroValido = true;
				}
				
				if ( StringUtils.isEmpty(form.getSexo())){
					result.rejectValue("sexo", "msg.erro.campo.obrigatorio");
					filtroValido = true;                   
				}
				
				if ( StringUtils.isEmpty(form.getFaixaSalarial())){
					result.rejectValue("faixaSalarial", "msg.erro.campo.obrigatorio");
					filtroValido = true;                   
				}
				
			}
			else if ( form.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo())){
				if ( StringUtils.isEmpty(form.getCnpj())){
					result.rejectValue("cnpj", "msg.erro.campo.obrigatorio");
					filtroValido = true;                   
				}
				else if ( ! JsfUtil.isValidoCNPJ(form.getCnpj())){
					result.rejectValue("cnpj", "msg.erro.cnpj.invalido");
					filtroValido = true;
				}
			}
			
			if ( form.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ){
				if ( StringUtils.isEmpty(form.getCreci())){
					result.rejectValue("creci", "msg.erro.campo.obrigatorio");
					filtroValido = true;                   
				}
				else {					
					Usuario usuario = dao.findUsuarioByCampo(form, "creci");
					if ( usuario != null ){				   
					   result.rejectValue("creci", "msg.erro.creci.existente");
					   filtroValido = true;
					}   
				}
			}
		}		
		
		if (( form.getFotoPrincipal() == null ) || (form.getFotoPrincipal() != null && form.getFotoPrincipal().length == 0)){
			result.rejectValue("fotoPrincipal", "msg.erro.campo.obrigatorio");
			filtroValido = true;
		}

		if ( StringUtils.isEmpty(form.getDataNascimentoFmt())){
			result.rejectValue("dataNascimentoFmt", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else {
			try{				
				DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
				df.setLenient (false); 
				df.parse (form.getDataNascimentoFmt());   
			}
			catch(ParseException e){
				result.rejectValue("dataNascimentoFmt", "msg.erro.data.nascimento.invalida");
				filtroValido = true;
			}
		}
		
		/*
		if ( StringUtils.isEmpty(form.getDescSobreMim())){
			result.rejectValue("descSobreMim", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}*/
		
		// fim - validacao minhas informacoes
		
		// inicio - validacao localizacao
		 if ( form.getIdEstado() == -1 ) {
			result.rejectValue("idEstado", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
	    }
		
		if ( form.getIdCidade() == -1 ) {
			result.rejectValue("idCidade", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( form.getIdBairro() == -1 ) {
			result.rejectValue("idBairro", "msg.erro.campo.obrigatorio");
			filtroValido = true;
		}				
		// fim - validacao localizacao
		
		// inicio - validacao contato
		if ( StringUtils.isEmpty(form.getEmail())){
			result.rejectValue("email", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		// fim - validacao contato
		
		// inicio - validacao senha
		boolean isVazio = false;
		if ( StringUtils.isEmpty(form.getPassword())) {
			result.rejectValue("password", "msg.erro.campo.obrigatorio");
			filtroValido = false;
			isVazio = true;
		}
		
		if ( StringUtils.isEmpty(form.getConfirmaPassword())) {
			result.rejectValue("confirmaPassword", "msg.erro.campo.obrigatorio");
			filtroValido = true;
			isVazio = true;
		}
		
		if ( !isVazio ){
			
			if ( !org.apache.commons.lang3.StringUtils.isAlphanumeric(form.getPassword())){				
				result.rejectValue("password", "msg.erro.senha.caractere.alfanumerico");
				filtroValido = true;
			}
			
			if ( ! org.apache.commons.lang3.StringUtils.isAlphanumeric(form.getConfirmaPassword())){				
				result.rejectValue("confirmaPassword", "msg.erro.senha.caractere.alfanumerico");
				filtroValido = true;
			}
			
			if ( form.getPassword().length() < 6 ){
				result.rejectValue("password", "msg.erro.senha.tamanho.minimo");
				filtroValido = true;
			}
			
			if ( form.getConfirmaPassword().length() < 6 ){
				result.rejectValue("confirmaPassword", "msg.erro.senha.tamanho.minimo");
				filtroValido = true;
			}			
			
			if (! form.getPassword().equals(form.getConfirmaPassword())){
				result.rejectValue("confirmaPassword", "msg.erro.nova.senha.confirma.senha.esquece");
				filtroValido = true;
			}					
		}
				
		// fim - validacao senha
		
		// inicio - validacao 
/*		if ( StringUtils.isEmpty(form.getHabilitaBusca())){
			result.rejectValue("habilitaBusca", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getHabilitaDetalhesInfoUsuario())){
			result.rejectValue("habilitaDetalhesInfoUsuario", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		// fim - validacao
		*/
		return filtroValido;
	}	


	@Override
	public boolean validarDadosEdicaoUsuario(UsuarioForm form, BindingResult result){

		boolean filtroValido = false;

        if (result.hasErrors()) {            
            return false;
        }		
		// inicio - validacao minhas informacoes
		if ( StringUtils.isEmpty(form.getNome())){
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");			
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getLogin())){
			result.rejectValue("login", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else {			
			Usuario usuario = dao.findUsuarioByCampo(form, "login");
			if ( usuario != null && usuario.getId().longValue() != form.getId().longValue() ){
				result.rejectValue("login", "msg.erro.login.existente");
				filtroValido = true;                   
			}
		}
		
		if ( StringUtils.isEmpty(form.getDescSobreMim() )){
			result.rejectValue("descSobreMim", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getPerfil())){
			result.rejectValue("perfil", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( form.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || form.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ) {
			if ( StringUtils.isEmpty(form.getCpf())){
				result.rejectValue("cpf", "msg.erro.campo.obrigatorio");
				filtroValido = true;                   
			}
			else if ( ! JsfUtil.isValidoCPF(form.getCpf())){
				result.rejectValue("cpf", "msg.erro.cpf.invalido");
				filtroValido = true;
			}		
		}
		else if ( form.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo())){
			if ( StringUtils.isEmpty(form.getCnpj())){
				result.rejectValue("cnpj", "msg.erro.campo.obrigatorio");
				filtroValido = true;                   
			}
			else if ( ! JsfUtil.isValidoCNPJ(form.getCnpj())){
				result.rejectValue("cnpj", "msg.erro.cnpj.invalido");
				filtroValido = true;
			}
		}
		
		if ( form.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ){
			if ( StringUtils.isEmpty(form.getCreci())){
				result.rejectValue("creci", "msg.erro.campo.obrigatorio");
				filtroValido = true;                   
			}
			else {
				Usuario usuario = dao.findUsuarioByCampo(form, "creci");
				if ( usuario != null && usuario.getId().longValue() != form.getId().longValue()){				   
				   result.rejectValue("creci", "msg.erro.creci.existente");
				   filtroValido = true;
				}   
			}
		}
		
		if ( StringUtils.isEmpty(form.getDataNascimentoFmt())){
			result.rejectValue("dataNascimentoFmt", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else {
			try{				
				DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
				df.setLenient (false); 
				df.parse (form.getDataNascimentoFmt());   
			}
			catch(ParseException e){
				result.rejectValue("dataNascimentoFmt", "msg.erro.data.nascimento.invalida");
				filtroValido = true;
			}
		}	
		
		if ( StringUtils.isEmpty(form.getDescSobreMim())){
			result.rejectValue("descSobreMim", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		// fim - validacao minhas informacoes
		
		// inicio - validacao localizacao
		 if ( form.getIdEstado() == -1 ) {
			result.rejectValue("idEstado", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
	    }
		
		if ( form.getIdCidade() == -1 ) {
			result.rejectValue("idCidade", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( form.getIdBairro() == -1 ) {
			result.rejectValue("idBairro", "msg.erro.campo.obrigatorio");
			filtroValido = true;
		}				
		// fim - validacao localizacao
		
		// inicio - validacao contato
		if ( StringUtils.isEmpty(form.getEmail())){
			result.rejectValue("email", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		// fim - validacao contato
		
		// inicio - validacao senha
		/*if ( StringUtils.isEmpty(form.getPassword())){
			result.rejectValue("password", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else {
			if ( StringUtils.isEmpty(form.getConfirmaPassword())){
				result.rejectValue("confirmaPassword", "msg.erro.campo.obrigatorio");
				filtroValido = true;                   
			}
			else {
				if ( ! form.getPassword().equals(form.getConfirmaPassword())){
					result.rejectValue("confirmaPassword", "msg.erro.pwd.confirma.pwd.nao.conferem");
					filtroValido = true;
				}	
			}
		}		*/
		// fim - validacao senha
		
		// inicio - validacao 
		if ( StringUtils.isEmpty(form.getHabilitaBusca())){
			result.rejectValue("habilitaBusca", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getHabilitaDetalhesInfoUsuario())){
			result.rejectValue("habilitaDetalhesInfoUsuario", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		// fim - validacao 
		
		return filtroValido;
	}
	
	
	@Override
	public boolean validarDadosCadastroEdicaoUsuarioAdmin(UsuarioForm form, BindingResult result){

		boolean filtroValido = false;

        if (result.hasErrors()) {            
            return false;
        }
		
		// inicio - validacao minhas informacoes
		if ( StringUtils.isEmpty(form.getNome())){
			result.rejectValue("nome", "msg.erro.campo.obrigatorio");			
			filtroValido = true;                   
		}
		
		if ( StringUtils.isEmpty(form.getLogin())){
			result.rejectValue("login", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else {
			Usuario usuario = dao.findUsuarioByCampo(form, "login");
			if ( usuario != null && usuario.getId().longValue() != form.getId().longValue() ){
				result.rejectValue("login", "msg.erro.login.existente");
				filtroValido = true;                   
			}
		}
		
		if ( StringUtils.isEmpty(form.getCpf())){
			result.rejectValue("cpf", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else if ( ! JsfUtil.isValidoCPF(form.getCpf())){
			result.rejectValue("cpf", "msg.erro.cpf.invalido");
			filtroValido = true;
		}
	
		
		boolean estaVaziaDataNascimento = false;
		if ( StringUtils.isEmpty(form.getDiaNascimento()) || StringUtils.isEmpty(form.getMesNascimento()) || StringUtils.isEmpty(form.getAnoNascimento())){
			result.rejectValue("dataNascimento", "msg.erro.data.nascimento.invalida");
			filtroValido = true;
			estaVaziaDataNascimento = true;
		}		
		
		
		if ( ! estaVaziaDataNascimento){			
			try{
				String dtNascimento = form.getDiaNascimento() + "/" + form.getMesNascimento() + "/" + form.getAnoNascimento();
				DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
				df.setLenient (false); 
				df.parse (dtNascimento);   
			}
			catch(ParseException e){
				result.rejectValue("dataNascimento", "msg.erro.data.nascimento.invalida");
				filtroValido = true;
			}
		}

		
		// fim - validacao minhas informacoes
		
		// inicio - validacao localizacao
		 if ( form.getIdEstado() == -1 ) {
			result.rejectValue("idEstado", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
	    }
		
		if ( form.getIdCidade() == -1 ) {
			result.rejectValue("idCidade", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		
		if ( form.getIdBairro() == -1 ) {
			result.rejectValue("idBairro", "msg.erro.campo.obrigatorio");
			filtroValido = true;
		}				
		// fim - validacao localizacao
		
		// inicio - validacao contato
		if ( StringUtils.isEmpty(form.getEmail())){
			result.rejectValue("email", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		// fim - validacao contato
		
		// inicio - validacao senha
		/*if ( StringUtils.isEmpty(form.getPassword())){
			result.rejectValue("password", "msg.erro.campo.obrigatorio");
			filtroValido = true;                   
		}
		else {
			if ( StringUtils.isEmpty(form.getConfirmaPassword())){
				result.rejectValue("confirmaPassword", "msg.erro.campo.obrigatorio");
				filtroValido = true;                   
			}
			else {
				if ( ! form.getPassword().equals(form.getConfirmaPassword())){
					result.rejectValue("confirmaPassword", "msg.erro.pwd.confirma.pwd.nao.conferem");
					filtroValido = true;
				}	
			}
		}		*/
		// fim - validacao senha
		
		
		return filtroValido;
	}

	
	public String validarCadastroUsuario(UsuarioForm frm) {	
		 String msg = "";
	        Usuario usuario = null;
	        if ( frm != null){            
	            if ( ! frm.getPassword().equals(frm.getConfirmaPassword()))
	                msg = MessageUtils.getMessage("msg.erro.pwd.confirma.pwd.nao.conferem");	                
	            else
	            	usuario = dao.findUsuarioByCampo(frm, "login");
	            
	            if ( msg.equals("")){                
	                if ( usuario != null) 	                
	                    msg = MessageUtils.getMessage("msg.erro.login.existente");
	            }
	            
	            if ( msg.equals("")){                
	                if (! JsfUtil.isEmail(frm.getEmail()))  
	                	msg = MessageUtils.getMessage("msg.erro.email.invalido");	                    
	            }
	            
	            // validando cpf ou cnpj
	            if ( msg.equals("")){
	                if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ) {
	                    if ( ! JsfUtil.isValidoCPF(frm.getCpf()))
	                    	msg = MessageUtils.getMessage("msg.erro.cpf.invalido");	                                    
	                }
	                else if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()) ){
	                    if (! JsfUtil.isValidoCNPJ(frm.getCpf())){
	                    	msg = MessageUtils.getMessage("msg.erro.cnpj.invalido");
	                    }
	                }
	            }
	            
	            // validando se existe ja um usuario com o cpf ou cnpj informado
	            if ( msg.equals("")){                
	                usuario = dao.findUsuarioByCampo(frm, "cnpj");                
	                if ( usuario != null ){
	                     if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) )
	                    	 msg = MessageUtils.getMessage("msg.erro.cpf.existente");    
	                     else  
	                    	 msg = MessageUtils.getMessage("msg.erro.cnpj.existente");
	                }                
	            }
	            
	            // para o caso corretor verficar se jaexistem alguem com o creci informado
	            if ( msg.equals("")){
	                if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ){
	                    usuario = dao.findUsuarioByCampo(frm, "creci");
	                    if ( usuario != null )
	                    	msg = MessageUtils.getMessage("msg.erro.creci.existente");
	                }                
	            }
	        }
	        
	        return msg;
	}


	
	public String validarCadastroUsuario2(UsuarioForm frm) {	
		if ( frm.getIdEstado() <= 0  || frm.getIdBairro() <= 0 || frm.getIdCidade() <= 0)			
            return MessageUtils.getMessage("msg.erro.cadastro.localidade.usuario");                     
        else
            return "";
	}


	
	public String validarEdicaoUsuario(UsuarioForm frm) {	
		String msg = "";
        Usuario usuario = null;
        if ( frm != null){            
                   
            if ( msg.equals("")){                
                if ( usuario != null) {                    
                    if (! usuario.getId().equals(frm.getId()))
                        msg = MessageUtils.getMessage("msg.usuario.campo.login.existente");                     
                }                    
            }
            
            if ( msg.equals("")){                
                if (! JsfUtil.isEmail(frm.getEmail()))                	
                    msg = MessageUtils.getMessage("msg.usuario.campo.email.valido");                     
            }
            
                 // validando cpf ou cnpj
            if ( msg.equals("")){
                if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ) {
                    if ( ! JsfUtil.isValidoCPF(frm.getCpf()))                    	
                        msg = MessageUtils.getMessage("msg.usuario.campo.cpf.valido");                                         
                }
                else if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()) ){
                    if (! JsfUtil.isValidoCNPJ(frm.getCpf())){
                    	msg = MessageUtils.getMessage("msg.usuario.campo.cnpj.valido");                     
                    }
                }
            }
            
            // validando se existe ja um usuario com o cpf ou cnpj informado
            if ( msg.equals("")){                
                usuario = dao.findUsuarioByCampo(frm, "cpf");                
                if ( usuario != null && ! usuario.getId().equals(frm.getId())) {
                     if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) )
                    	 msg = MessageUtils.getMessage("msg.usuario.campo.cpf.existente");                     
                     else                     	 
                         msg = MessageUtils.getMessage("msg.usuario.campo.cnpj.existente");
                }                
            }
        }        
        return msg;
	}


	
	public List<Usuario> buscarUsuarios(UsuarioForm frm, String tipoVisualizar, Long idUsuarioSessao) {
		
        List<Usuario> listaUsuario = new ArrayList<Usuario>();
        List<Usuario> listaFinal = new ArrayList<Usuario>();
        Usuario usuarioSessao = dao.findUsuario(idUsuarioSessao);
        
        if ( tipoVisualizar.equals("infoPreferenciais")) {
            if ( frm.getIdEstado() != 0 && frm.getIdCidade() != 0 && frm.getIdBairro() != 0 ){                
                listaUsuario = preferenciaLocalidadeDao.findPreferencialocalidadeSemDuplicidadeUsuario(frm);
                if (! CollectionUtils.isEmpty(listaUsuario)){
                    for (Usuario user : listaUsuario){
                        user.setIsContato(contatoService.checarTipoContato(user.getId(), idUsuarioSessao));
                        if ( user.getIsContato().equals("N")) {
                        	user.setIsSeguindo(seguidorService.checarUsuarioEstaSeguindo(idUsuarioSessao, user.getId()));                        	
                        }	
                        user.setQuantTotalImoveis(imovelService.checarQuantMeusImoveis(user.getId()));
                        user.setQuantTotalContatos(contatoService.checarTotalContatosPorUsuarioPorStatus(user.getId(), ContatoStatusEnum.OK.getRotulo()));
                        user.setQuantTotalSeguidores(seguidorService.checarQuantidadeSeguidores(user.getId()));
                        user.setQuantTotalRecomendacoes(recomendacaoService.checarQuantidadeTotalRecomendacaoRecebidaPorStatus(user.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo()));
                        listaFinal.add(user);
                    } 
                }
            }
        }
        else if ( tipoVisualizar.equals("infoPessoais")) {
            if (! StringUtils.isEmpty(frm.getValorBusca()) && JsfUtil.isEmail(frm.getValorBusca())){
               listaUsuario = dao.findUsuariosByCampo(frm, "email", false);
            }            
            else if ( frm.getIdEstado() > 0 || frm.getIdCidade() > 0 || frm.getIdBairro() > 0 ){                                       
                    listaUsuario = dao.findUsuarios(frm);
            }
            else {                
                if (! StringUtils.isEmpty(frm.getValorBusca())) 
                    listaUsuario = dao.findUsuariosByCampo(frm, "nome", false);                
            } 
            
            if ( ! CollectionUtils.isEmpty(listaUsuario)){
            	if (! usuarioSessao.getPerfil().equals(PerfilUsuarioOpcaoEnum.ADMIN.getRotulo())){
            		for (Usuario user :  listaUsuario ){
        			    user.setIsContato(contatoService.checarTipoContato(user.getId(), idUsuarioSessao));
                        if ( user.getIsContato().equals("N")) {
                        	user.setIsSeguindo(seguidorService.checarUsuarioEstaSeguindo(idUsuarioSessao, user.getId()));                        	
                        }
                        user.setIsSeguindo(seguidorService.checarUsuarioEstaSeguindo(idUsuarioSessao, user.getId()));
                        user.setQuantTotalImoveis(imovelService.checarQuantMeusImoveis(user.getId()));
                        user.setQuantTotalContatos(contatoService.checarTotalContatosPorUsuarioPorStatus(user.getId(), ContatoStatusEnum.OK.getRotulo()));
                        user.setQuantTotalSeguidores(seguidorService.checarQuantidadeSeguidores(user.getId()));
                        user.setQuantTotalRecomendacoes(recomendacaoService.checarQuantidadeTotalRecomendacaoRecebidaPorStatus(user.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo()));
                        listaFinal.add(user);
                    }
            	}
            	else
            		listaFinal.addAll(listaUsuario);                
            }
        }               
        frm.setListaPaginas(AppUtil.carregarQuantidadePaginas(frm.getQuantRegistros(), frm.getQuantMaxRegistrosPerPage() ));
        return listaFinal;
	}

	public List<Usuario> buscarUsuarios(UsuarioForm frm, String tipoVisualizar) {		
        List<Usuario> listaUsuario = new ArrayList<Usuario>();
        if ( tipoVisualizar.equals("infoPreferenciais")) {
            if ( frm.getIdEstado() != 0 && frm.getIdCidade() != 0 && frm.getIdBairro() != 0 ){                
                listaUsuario = preferenciaLocalidadeDao.findPreferencialocalidadeSemDuplicidadeUsuario(frm);          
            }
        }
        else if ( tipoVisualizar.equals("infoPessoais")) {        	
            if ( frm.getValorBusca()!= null && ! frm.getValorBusca().equals("") && JsfUtil.isEmail(frm.getValorBusca())){
               listaUsuario = dao.findUsuariosByCampo(frm, "email", false);
            }            
            else if ( frm.getIdEstado() > 0 || frm.getIdCidade() > 0 || frm.getIdBairro() > 0 ){                                       
                    listaUsuario = dao.findUsuarios(frm);
            }
            else {                
                if ( frm.getValorBusca() != null && ! frm.getValorBusca().equals("")) 
                    listaUsuario = dao.findUsuariosByCampo(frm, "nome", false);               
            }          
        }        
        return listaUsuario;
	}

	@Transactional
	public void atualizarStatusUsuario(Long idUsuario, String statusUsuario) {	
		Usuario usuario = dao.findUsuario(idUsuario);
        usuario.setStatusUsuario(statusUsuario);
        usuario.setDataSuspensao(new Date());
        dao.save(usuario);
	}


	@Transactional
	public Usuario adicionarFotoPrincipalUsuario(Long idUsuario, byte[] foto) {	
		Usuario usuario = dao.findUsuario(idUsuario);
        usuario.setFotoPrincipal(foto);
        dao.save(usuario);  
        return usuario;
	}


	public List<Usuario> recuperarUsuariosInteressadosPorImovelPorDonoImovel(UsuarioForm user) {	
		List<Imovel> listaMeusImoveis = imovelService.listarMeusImoveis(user.getId(), new ImovelForm());
        int size = AppUtil.recuperarQuantidadeLista(listaMeusImoveis);
        
        if ( size == 0 )
         return new ArrayList<Usuario>();
        else {
            int opcao =  (int)(Math.random() * size);
            Imovel imovel = listaMeusImoveis.get(opcao);
            imovel.setAcao("compra");
            imovel.setIdBairro(0);
            List<Preferencialocalidade> listaPref = preferenciaLocalidadeDao.findPreferencialocalidade(imovel);
            if ( ! CollectionUtils.isEmpty(listaPref))
                return new ArrayList<Usuario>();
            else {
                List<Usuario> lista = new ArrayList<Usuario>();                
                for (Preferencialocalidade pref :  listaPref){                                        
                    lista.add(pref.getUsuario());
                }               
               
                return lista;
            }               
        }
	}


	
	public int checarQuantidadeTotalUsuarios() {	
		return dao.listAll().size();
	}

	
	public long checarQuantidadeTotalUsuariosPorPeriodo(AdministracaoForm form) {	
		return dao.findQuantidadeTotalUsuarioPorPeriodo(form);
	}
	
	@Override
	public List<Usuario> checarUsuariosPorUltimoAcesso(AdministracaoForm form) {
		return dao.findUsuariosByDataVisita(form);
	}
	
	public String gerarNovaSenha(Usuario usuario) {	
		String novaSenha = "";
        DateUtil dtCorrente = new DateUtil();
        novaSenha = usuario.getLogin() + dtCorrente.getYear()+ dtCorrente.getMonth() + dtCorrente.getDayOfMonth() + dtCorrente.getHour() + dtCorrente.getMinute();
        usuario.setPassword(md5(novaSenha));
        dao.save(usuario);        
        return novaSenha;
	}

	public EmailImovel notificarGeracaoNovaSenha(String emailDestinatario, String novaSenha) {	
		EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
        
        // assunto        
      //  email.setAssunto(MessageUtils.getMessage("msg.assunto.email.reenvio.senha"));        
        // para
        email.setTo(emailDestinatario);
        
        // Texto do Email
       /* texto.append(MessageUtils.getMessage("msg.corpo.inicio.email.reenvio.senha") + "\n");                
        texto.append(MessageUtils.getMessage("msg.corpo.novasenha.email.reenvio.senha")  + novaSenha +"\n");        
        texto.append(MessageUtils.getMessage("msg.corpo.datasolicitacao.email.reenvio.senha") + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());*/
        email.setAcao("novaSenha");        
        return email;
	}


	
	public String validarCadastroInicial(UsuarioForm frm) {	
		
        Usuario usuario = null;
        if ( frm != null){           
            
        	usuario = dao.findUsuarioByCampo(frm, "login");             
            if ( usuario != null)
               return MessageUtils.getMessage("msg.usuario.campo.login.existente");                        
            
                           
            if (! JsfUtil.isEmail(frm.getEmail()))                	
            	return MessageUtils.getMessage("msg.usuario.campo.email.valido");            
        
       
            usuario = dao.findUsuarioByCampo(frm, "email");
            if ( usuario != null)                	
            	return MessageUtils.getMessage("msg.usuario.campo.email.existente");            
        
       
            List<Usuario> lista  = dao.findUsuariosByCampo(frm, "nomeLike", false);
            if (! CollectionUtils.isEmpty(lista))                	
            	return MessageUtils.getMessage("msg.usuario.campo.nome.existente");           
        }        
        return "";
	}


	@Transactional
	public void editarUsuario(Usuario usuario) {	
		dao.save(usuario);
	}


	
	public boolean checarPermissaoServico(Long idUsuario, String nomeServico) {	
		try {         
            boolean ultrapassou = false;
            Paramservico paramservico = paramservicoDao.findParamservicoPorNome(nomeServico);
            if ( paramservico.getCobranca().equals("S")){
                Usuario usuario =  dao.findUsuario(idUsuario);
                         
                if (nomeServico.equals(ServicoValueEnum.ADICIONAR_IMOVEIS.getRotulo())){                    
                    int quantImoveisCadastrados = imovelService.quantMeusImoveis(idUsuario, new ImovelForm());             
                    if (quantImoveisCadastrados >= usuario.getQuantCadastroImoveis())
                        ultrapassou = true;
                 }                
                else if (nomeServico.equals(ServicoValueEnum.INDICACOES_IMOVEIS.getRotulo())){
                    long quantIndicacoes = imovelindicadoDao.findQuantImoveisIndicadosByIdUsuarioByStatusLeitura(idUsuario, null);
                    if (quantIndicacoes >= usuario.getQuantMaxIndicacoesImovel())
                        ultrapassou = true;                    
                }
                if (nomeServico.equals(ServicoValueEnum.INDICACOES_EMAIL.getRotulo())){                    
                    int quantIndicacoesEmail = recuperarUsuarioPorId(idUsuario).getQuantMaxIndicacoesImovelEmail();
                    if (quantIndicacoesEmail <= 0)
                        ultrapassou = true;
                 }
            }            
            
            return ultrapassou;
        } catch (Exception e) {
            e.printStackTrace();;
            return false;
        }   
	}


	@Transactional
	public void atualizarIndicacoesImoveisPorEmail(Long idUsuario) {	
		Paramservico paramservico = paramservicoDao.findParamservicoPorNome(ServicoValueEnum.INDICACOES_EMAIL.getRotulo());
        if ( paramservico.getCobranca().equals("S")){
            Usuario usuario = dao.findUsuario(idUsuario);
            usuario.setQuantMaxIndicacoesImovelEmail(usuario.getQuantMaxIndicacoesImovelEmail() - 1);
            dao.save(usuario);
        }
	}


	
	public String validarMudancaSenha(UsuarioForm frm) {	
		        
        if ( ! frm.getPassword().equals(frm.getConfirmaPassword()))        	
        	return MessageUtils.getMessage("msg.usuario.campo.confirma.password.conferem");
        
        // acrescentar depois outras regras para formacao de senha         
        
        if ( frm.getPassword().length() < 8 || frm.getPassword().length() > 10)            	
        	return MessageUtils.getMessage("msg.usuario.campo.password.tamanho.minimo.maximo");             
        
        boolean isCaractereAlfa = this.validaCaractereAlfa(frm.getPassword());
        boolean isCaractereNumerico = this.validaCaractereNumerico(frm.getPassword());
        if ( ! isCaractereAlfa || ! isCaractereNumerico )            	
        	return  MessageUtils.getMessage("msg.usuario.campo.password.caractere.alfanumerico");                                                   
        
       return "";
	}

	@Transactional
	public void atualizarAssinaturaUsuario(long idUsuario,Date dataFimAssinatura) {	
		Usuario usuario = dao.findUsuario(idUsuario);
        usuario.setStatusUsuario(StatusUsuarioEnum.LIBERADO.getRotulo()); 
        if ( usuario.getAtivado().equals("N")) // chama procedure para reativar os imoveis ligados ao usuario
            imovelService.reativaImoveisUsuario(idUsuario);
        
        usuario.setAtivado("S");
        usuario.setDataValidadeAcesso(dataFimAssinatura);
        dao.save(usuario);
	}


	
	public String validarCadastroLocalidadeUsuario(UsuarioForm frm) {	
		try {
            if ( frm.getIdEstado() == -1 ) {
                   return MessageUtils.getMessage("msg.erro.campo.obrigatorio");
             }
             else {
                   if ( frm.getIdCidade() == -1 ) {                	   
                       return MessageUtils.getMessage("msg.erro.campo.obrigatorio");
                   }
                   else {
                       if ( frm.getIdBairro() == -1 ) {
                            return MessageUtils.getMessage("msg.erro.campo.obrigatorio");
                       }
                   }                   
               }
            return "";
        } catch (Exception e) {                        
            return MessageUtils.getMessage("erro.generico.cadastro.usuario.validar.localidade");                    
        } 
	}


	
	public String validarCadastroIdentificacaoUsuario(UsuarioForm frm) {	
		try {
            if ( frm.getPerfil().equals("-1")  ) {         	   
                return MessageUtils.getMessage("msg.usuario.campo.perfil.obrigatorio");                   
            }
            
            if (StringUtils.isEmpty(frm.getNome())){
                return MessageUtils.getMessage("msg.usuario.campo.nome.obrigatorio");                   
            }
            else{
            	List<Usuario> lista  = dao.findUsuariosByCampo(frm, "nomeLike", false);
                if ( ! CollectionUtils.isEmpty(lista) )               
                	return MessageUtils.getMessage("msg.usuario.campo.nome.existente");                                       
            }
            
            if (StringUtils.isEmpty(frm.getLogin())){
                return MessageUtils.getMessage("msg.usuario.campo.login.obrigatorio");                   
            }
            else{
                Usuario usuario = dao.findUsuarioByCampo(frm, "login");               
                if ( usuario != null)             	
                	return MessageUtils.getMessage("msg.usuario.campo.nome.existente");
            }
            
            if (StringUtils.isEmpty(frm.getCpf())){
                if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ) 
                	return MessageUtils.getMessage("msg.usuario.campo.cpf.obrigatorio");                                      
                else if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()) )             	   
                    return MessageUtils.getMessage("msg.usuario.campo.cpnj.obrigatorio");                                   
            }
            
            if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ) {
                if ( ! JsfUtil.isValidoCPF(frm.getCpf()))             	   
                	return MessageUtils.getMessage("msg.usuario.campo.cpf.valido");                    
            }
            else if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()) ){
                if (! JsfUtil.isValidoCNPJ(frm.getCpf()))             	   
                	 return MessageUtils.getMessage("msg.usuario.campo.cpnj.valido");
            }
            
            if (! frm.getPerfil().equals("-1") ){
                // validando se existe ja um usuario com o cpf ou cnpj informado                         
                Usuario usuario = dao.findUsuarioByCampo(frm, "cpf");                
                if ( usuario != null ){
                    if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) )                       
                 		return MessageUtils.getMessage("msg.usuario.campo.cpf.existente");
                    else
                    	return MessageUtils.getMessage("msg.usuario.campo.cnpj.existente");
                } 
                
                if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ){
                    if ( ( frm.getCreci() == null) || ( frm.getCreci() != null && frm.getCreci().equals("")))                        
                    	return MessageUtils.getMessage("msg.usuario.campo.creci.obrigatorio");   
                    else {
                        usuario = dao.findUsuarioByCampo(frm, "creci");
                        if ( usuario != null )                     	
                            return MessageUtils.getMessage("msg.usuario.campo.creci.existente");
                    }                    
                } 
            }
            return "";
        } catch (Exception e) {
           return MessageUtils.getMessage("erro.generico.cadastro.usuario.validar.identificacao=");
        } 
	}


	
	public String validarCadastroPwDUsuario(UsuarioForm frm) {	
		try {
            if (StringUtils.isEmpty(frm.getPassword())){
                return MessageUtils.getMessage("msg.usuario.campo.password.obrigatorio");                  
            }
            
            if (StringUtils.isEmpty(frm.getConfirmaPassword())){
                return MessageUtils.getMessage("msg.usuario.campo.confirma.password.obrigatorio");                                     
            }
            
            if ( ! frm.getPassword().equals(frm.getConfirmaPassword()))
                return MessageUtils.getMessage("msg.usuario.campo.confirma.password.conferem");                                                             
             
            return "";
        } catch (Exception e) {
            e.printStackTrace();            
            return MessageUtils.getMessage("erro.generico.cadastro.usuario.validar.password");                                                             
        } 
	}


	
	public String validarCadastroContato(UsuarioForm frm) {	
		try {
            if (StringUtils.isEmpty(frm.getEmail())){
                return MessageUtils.getMessage("msg.usuario.campo.email.obrigatorio");                    
            }
            
            if (! JsfUtil.isEmail(frm.getEmail()))
                return MessageUtils.getMessage("msg.usuario.campo.email.valido");
            else {
                Usuario usuario = dao.findUsuarioByCampo(frm, "email");
                if ( usuario != null )
                    return MessageUtils.getMessage("msg.usuario.campo.email.existente");
            } 
            
            if (StringUtils.isEmpty(frm.getTelefone())){
                return MessageUtils.getMessage("msg.usuario.campo.telefone.obrigatorio");                   
            }
             
            return "";
        } catch (Exception e) {
            return MessageUtils.getMessage("erro.generico.cadastro.usuario.validar.contato");                               
        }
	}

	
	@Transactional
	public void avaliarServicoUsuario(Long idPerfilUsuario, String avaliacao) {	
		Usuario usuario = dao.findUsuario(idPerfilUsuario);
        if ( usuario != null ){
            if ( avaliacao.equals("aprova"))
                usuario.setQuantUsuarioAprovServico(usuario.getQuantUsuarioAprovServico() + 1);
            else if ( avaliacao.equals("desaprova"))
                usuario.setQuantUsuarioDesaprovServico(usuario.getQuantUsuarioDesaprovServico() + 1);
           
            dao.save(usuario);           
        }
	}


	
	public String validarAtualizacaoInfoUsuario(UsuarioForm frm) {	
		try {            
            if (StringUtils.isEmpty(frm.getNome())){
                return MessageUtils.getMessage("msg.usuario.campo.nome.obrigatorio");                   
            }
            else{
                List<Usuario> lista  = dao.findUsuariosByCampo(frm, "nome", false);
                if ( !CollectionUtils.isEmpty(lista )){
                    for (Usuario usuario : lista) {
                        if ( usuario.getId().longValue() != 0/*JsfUtil.getUserSession().getId().longValue() */)
                            return MessageUtils.getMessage("msg.usuario.campo.nome.existente");
                    }
                }                        
            }
            
            if (StringUtils.isEmpty(frm.getLogin())){
                return MessageUtils.getMessage("msg.usuario.campo.login.obrigatorio");                     
            }
            else{
                Usuario usuario = dao.findUsuarioByCampo(frm, "login");               
                if ( usuario != null && usuario.getId().longValue() != 0 /*!= JsfUtil.getUserSession().getId().longValue() */)
                     return MessageUtils.getMessage("msg.usuario.campo.login.existente");                     
            }
            
            if (StringUtils.isEmpty(frm.getCpf())){
                if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ) 
                    return MessageUtils.getMessage("msg.usuario.campo.cpf.obrigatorio");                                        
                else if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()) )
                    return MessageUtils.getMessage("msg.usuario.campo.cnpj.obrigatorio");                     
            }
            
            if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) || frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo()) ) {
                if ( ! JsfUtil.isValidoCPF(frm.getCpf()))
                    return MessageUtils.getMessage("msg.usuario.campo.cpf.valido");                     
                else {
                    Usuario usuario = dao.findUsuarioByCampo(frm, "cpf");
                    if ( usuario != null && usuario.getId().longValue() != 0 /*JsfUtil.getUserSession().getId().longValue() */)
                        return MessageUtils.getMessage("msg.usuario.campo.cpf.existente");                                         
                }
            }
            else if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo()) ){
                if (! JsfUtil.isValidoCNPJ(frm.getCpf()))
                    return MessageUtils.getMessage("msg.usuario.campo.cnpj.valido");                     
                else {
                    Usuario usuario = dao.findUsuarioByCampo(frm, "cnpj");
                    if ( usuario != null && usuario.getId().longValue() != 0 /*JsfUtil.getUserSession().getId().longValue() */)
                        return MessageUtils.getMessage("msg.usuario.campo.cnpj.existente");           
                }                
            }
            
            if ( frm.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo())){                
                if ( ( frm.getCreci() == null) || ( frm.getCreci() != null && frm.getCreci().equals("")))
                    return MessageUtils.getMessage("msg.usuario.campo.creci.obrigatorio");     
                else {
                    Usuario usuario = dao.findUsuarioByCampo(frm, "cnpj");                
                    if ( usuario != null && usuario.getId().longValue() != 0 /*JsfUtil.getUserSession().getId().longValue()*/ )
                        return MessageUtils.getMessage("msg.usuario.campo.creci.existente");     
                }                 
            }
            return "";
        } catch (Exception e) {            
            return MessageUtils.getMessage("erro.generico.cadastro.usuario.validar.identificacao");     
        } 
	}


	
	public UsuarioForm carregaUsuario(Long idUsuario) {	
		Usuario usuario = dao.findUsuario(idUsuario);
        UsuarioForm frm = new UsuarioForm();
        BeanUtils.copyProperties(usuario, frm);
        return frm;
	}


	
	public String validarAtualizarContato(UsuarioForm frm) {	
		try {
            if (StringUtils.isEmpty(frm.getEmail())){                 
                return MessageUtils.getMessage("msg.usuario.campo.email.obrigatorio");  
            }
            
            if (! JsfUtil.isEmail(frm.getEmail()))
                return MessageUtils.getMessage("msg.usuario.campo.email.valido");  
            else {
                Usuario usuario = dao.findUsuarioByCampo(frm, "email");
                if (( usuario != null ) && ( usuario.getId().intValue() != frm.getId().intValue() ))
                    return MessageUtils.getMessage("msg.usuario.campo.email.existente");  
            } 
            
            if (StringUtils.isEmpty(frm.getTelefone())){
                return MessageUtils.getMessage("msg.usuario.campo.telefone.obrigatorio");                     
            }
             
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtils.getMessage("erro.generico.cadastro.usuario.validar.password");                     
        }
	}

	
	public String validarBuscaUsuarios(UsuarioForm frm, String tipoVisualizar) {	
		String msg = "";
        
        if ( tipoVisualizar.equals("infoPessoais")) {            
            if ( frm.getIdEstado() == -1 && 
                 frm.getIdCidade() == -1 && 
                 frm.getIdBairro() == -1 &&
             //    frm.getPerfilPessoal().equals("-1") &&
                 frm.getValorBusca().equals(""))
                msg = MessageUtils.getMessage("msg.erro.buscar.sem.filtro.info.pessoal");                            
        }    
        else if ( tipoVisualizar.equals("infoPrerenciais")) {            
            if ( frm.getIdEstado() == -1 && 
                 frm.getIdCidade() == -1 && 
                 frm.getIdBairro() == -1 &&
                 frm.getTipoImovel().equals("-1") &&
                 frm.getAcao().equals("-1"))                                
                msg = MessageUtils.getMessage("msg.erro.buscar.sem.filtro.info.pessoal"); 
            else if ( ! frm.getTipoImovel().equals("-1")){
                   if ( frm.getIdEstado() == -1 && 
                        frm.getIdCidade() == -1 && 
                        frm.getIdBairro() == -1 )
                    msg = MessageUtils.getMessage("msg.erro.buscar.apenas.filtro.perfil.info.pessoal");                                            
            }
            else if ( ! frm.getAcao().equals("-1")){
                   if ( frm.getIdEstado() == -1 && 
                        frm.getIdCidade() == -1 && 
                        frm.getIdBairro() == -1 )
                    msg = MessageUtils.getMessage("msg.erro.buscar.apenas.filtro.perfil.info.pessoal");                                            
            }
        }
        return msg;
	}


	@Transactional
	public void atualizarStatusAtivadoUsuario(Long idUsuario) {	
		dao.updateStatusAtivadoByIdUsuario(idUsuario);        
        Usuario usuario = dao.findUsuario(idUsuario);
        usuario.setAtivado("N");
        dao.save(usuario);
        imovelService.desativarImoveisUsuario(idUsuario);
	}


	
	public List<Usuario> relatorioUsuarioMaisCompartilhamentosAceitos(RelatorioForm frm, String tipoCompartilhamento) {
		
        List<Usuario> listaUsuarioFinal = new ArrayList<Usuario>();        
        List listaUsuario = dao.recuperarUsuariosComMaisCompartilhamentoAceitos(frm, tipoCompartilhamento);        
        if ( ! CollectionUtils.isEmpty(listaUsuario) ){
            Usuario usuario = null;
            Object[] obj = null;
            for (Iterator iter = listaUsuario.iterator();iter.hasNext();){
                obj = (Object[]) iter.next();
                usuario = recuperarUsuarioPorId(Long.parseLong(obj[0].toString()));                
                usuario.setQuantCompartilhamentoAceitos(Integer.parseInt(obj[1].toString()));
                listaUsuarioFinal.add(usuario);
            }
        }
        return listaUsuarioFinal;
	}


	
	public String validarIndicarAmigos(String email) {	
		
        if ( ! JsfUtil.isEmail(email))
            return MessageUtils.getMessage("msg.usuario.campo.email.valido");
        
        return "";
	}


	@Transactional
	public EmailImovel indicarAmigos(String emailAmigo) {	
		 //  EmailImovel email = ImovelindicadoService.getInstance().indicarImovelPorEmail(frm);            
        //sendJMS(email); 
        
        EmailImovel email = new EmailImovel();
        StringBuilder texto = new StringBuilder();       
        
        texto.append(MessageUtils.getMessage("lbl.email.convite.cadastrar.sistema") + "\n");      
        
        email.setTo(emailAmigo);
        email.setAssunto(MessageUtils.getMessage("lbl.email.convite.texto.cadastrar.sistema"));
        email.setTextoEmail(texto.toString());
        email.setAcao("indicarAmigo");
        return email;
	}
	
	private boolean validaCaractereAlfa(String password) {
        String s = password;
        int i = s.length();                          
        char c;
        boolean isValido = false;
        for (int j = 0; j < i; j++){
            c = s.charAt(j);
            if (  Character.isLetter(c)){
                isValido = true;
                break;
            }    
        }
        return isValido;
    }
    
    private boolean validaCaractereNumerico(String password) {
        String s = password;
        int i = s.length();                          
        char c;
        boolean isValido = false;
        for (int j = 0; j < i; j++){
            c = s.charAt(j);
            if (  Character.isDigit(c)){
                isValido = true;
                break;
            }                
        }
        return isValido;
    }


	@Override
	public void carregarDadosInfoUsuario(UsuarioForm user, HttpSession session, boolean atualizaUltAcesso) {
		
		if ( atualizaUltAcesso ){
			user.setDataUltimoAcesso(new Date());
			Usuario usuario = new Usuario();
			BeanUtils.copyProperties(user, usuario);		
			dao.save(usuario);
			session.setAttribute(UsuarioInterface.USUARIO_SESSAO, user);
		}   
		
		if (!  user.getPerfil().equals(PerfilUsuarioOpcaoEnum.ADMIN.getRotulo())){			
			session.setAttribute(MensagemService.LISTA_MENSAGENS, mensagemService.recuperaTodasMensagensPorDataMensagem(user.getId(), 4));
			session.setAttribute(MensagemService.QUANT_NOVAS_MENSAGENS, mensagemService.checarQuantidadeNovasMensagens(user.getId()));
			List<MensagemAdmin> listaNovasMensagensAdmin = mensagemAdminService.recuperaTodasMensagensNovasPorUsuario(user.getId());
			session.setAttribute(MensagemAdminService.LISTA_NOVAS_MENSAGENS_ADMIN, listaNovasMensagensAdmin);
			session.setAttribute(MensagemAdminService.QUANT_NOVAS_MENSAGENS_ADMIN, AppUtil.recuperarQuantidadeLista(itemMensagemAdminDao.findItemMensagemAdminByStatusLeituraByIdUsuario(user.getId(), StatusLeituraEnum.NOVO.getRotulo())));
		}
		
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
		
		session.setAttribute(UsuarioService.HABILITA_FUNC_PLANOS  , parametrosIniciaisService.findParametroInicialPorNome(UsuarioService.HABILITA_FUNC_PLANOS));
		session.setAttribute(UsuarioService.HABILITA_FUNC_SERVICOS, parametrosIniciaisService.findParametroInicialPorNome(UsuarioService.HABILITA_FUNC_SERVICOS));
						
		session.setAttribute(UsuarioService.ACESSO_VALIDO, user.getAcessoValido());		
		session.setAttribute(UsuarioService.CONTADOR_TELA_INICIAL, 1L);
		session.setAttribute(ImovelService.QUANT_LISTA_IMOVEL_COMPARATIVO, 0L);
		session.setAttribute(ImovelService.QUANT_MEUS_IMOVEIS, imovelService.checarQuantMeusImoveis(user.getId()));
		
		session.setAttribute(ImovelService.QUANT_TOTAL_IMOVEIS_PropostaS,  imovelPropostasDao.findQuantPropostasRecebidasByIdUsuarioByStatus(user.getId() , null));		
		session.setAttribute(ImovelService.QUANT_IMOVEIS_PropostaS, imovelPropostasDao.findQuantPropostasRecebidasByIdUsuarioByStatus(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));		
		
		session.setAttribute(ImovelService.QUANT_TOTAL_IMOVEIS_COMENTARIOS, imovelcomentarioDao.findQuantImoveisComentariosRecebidos(user.getId(), null));
		session.setAttribute(ImovelService.QUANT_NOVOS_IMOVEIS_COMENTARIOS, imovelcomentarioDao.findQuantImoveisComentariosRecebidos(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));
				
		session.setAttribute(ImovelService.QUANT_TOTAL_USUARIOS_INTERESSADOS, imovelFavoritosDao.findQuantUsuariosInteressadosByIdUsuarioByStatus(user.getId(), null) );
		session.setAttribute(ImovelService.QUANT_NOVOS_USUARIOS_INTERESSADOS, imovelFavoritosDao.findQuantUsuariosInteressadosByIdUsuarioByStatus(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));
		
		session.setAttribute(ImovelService.QUANT_IMOVEIS_INDICADOS, imovelindicadoDao.findQuantImoveisIndicadosByIdUsuarioByStatusLeitura(user.getId(),  StatusLeituraEnum.NOVO.getRotulo()));		
		session.setAttribute(ImovelService.QUANT_NOVOS_VISITANTES,  imovelvisualizadoDao.findQuantidadeVisitantesByIdUsuarioByStatus(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));
		session.setAttribute(ImovelService.QUANT_TOTAL_VISITANTES,  imovelvisualizadoDao.findQuantidadeVisitantesByIdUsuarioByStatus(user.getId(), null));
		
		List<Notificacao> listaNovasNotificacoes = notificacaoService.recuperarListaNotificacoesNovas(user.getId());
		session.setAttribute(ImovelService.LISTA_NOVAS_NOTIFICACOES, listaNovasNotificacoes);
		session.setAttribute(ImovelService.QUANT_NOVAS_NOTIFICACOES, AppUtil.recuperarQuantidadeLista(listaNovasNotificacoes));
		//
		
		session.setAttribute(ContatoService.LISTA_CONVITES_RECEBIDOS, contatoService.recuperarConvites(user.getId(), 4));		
		session.setAttribute(ContatoService.QUANT_NOVOS_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), StatusLeituraEnum.NOVO.getRotulo()));
		session.setAttribute(ContatoService.QUANT_CONVITES_RECEBIDOS, contatoService.checarConvitesRecebidosPorUsuarioPorStatus(user.getId(), null));		
		session.setAttribute(ContatoService.QUANT_TOTAL_CONTATOS, contatoService.checarTotalContatosPorUsuarioPorStatus(user.getId(), ContatoStatusEnum.OK.getRotulo() ));
		
		if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()) ){
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, intermediacaoDao.checarQuantidadeIntermediacaoSolRecebidaByDonoImovelByStatusByStatusLeitura(user.getId(), 
																																									    StatusLeituraEnum.NOVO.getRotulo(),
																																									    StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
		}
		else {
			session.setAttribute(ImovelService.QUANT_NOVAS_PARCERIAS, parceriaDao.checarQuantidadeParceriaSolRecebidaByDonoImovelByStatusByStatusLeitura(user.getId(), 
																																						 StatusLeituraEnum.NOVO.getRotulo(),
																																						 StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
			
			session.setAttribute(ImovelService.QUANT_NOVAS_INTERMEDIACOES, intermediacaoDao.checarQuantidadeIntermediacaoSolRecebidaByDonoImovelByStatusByStatusLeitura(user.getId(), 
																																									    StatusLeituraEnum.NOVO.getRotulo(),
																																									    StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));	
		}
		
		//session.setAttribute(ImovelService.LISTA_IMOVEL_ANUNCIO_DESTAQUE, imovelService.recuperarImovelDestaqueParaAnuncio(3));
	}
	
	
	@Override
	public void tratarTelaInicial(UsuarioForm user, HttpSession session, ModelMap map) {
		/*
		* Obs.: A sugestao de imoveis e/ou usuarios devem ser no maximo de 4 registros (por enquanto).
		*
		* Obs.: Se , por exemplo, no perfil Cliente se recuperar apenas 1 imovel de acordo com a preferencia de imoveis do usuario entao
		*       completar a lista com outros 3 imoveis sugerindo-o aleatoriamente 
		*
		* Obs.: Estes imoveis sugeridos nao podem pertencer ao usuario. E os usuarios sugeridos nao podem ser contato do usuario.
		*/
		
		if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
			map.addAttribute("listaImoveisDestaque", imovelService.recuperarImovelDestaqueParaTelaInicial(4));					
			map.addAttribute("listaNotasContatos", notaService.recuperarNotasContatosUsuario(user.getId(), 5));
		}
		else if (! user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
			if ( AppUtil.getRandomBoolean()){
				map.addAttribute("listaImoveisDestaque", imovelService.recuperarImovelDestaqueParaTelaInicial(4));
			}			
			
			map.addAttribute("listaNotasContatos", notaService.recuperarNotasContatosUsuario(user.getId(), 6));
			
			if ( AppUtil.getRandomBoolean()){
				map.addAttribute("listaFiqueDeOlho", this.listaUsuariosFiqueOlho(user) );
			}
			
		}
			
	//	map.addAttribute("listaSugestaoUsuarios", this.sugerirUsuarios(user) );			
	//	map.addAttribute("listaSugestaoImoveis", imovelService.sugerirImoveis(user));
				
	//	map.addAttribute("listaNotasContatos", notaService.recuperarNotasContatosUsuario(4));		
	}		


	private List<Usuario> listaUsuariosFiqueOlho(UsuarioForm user) {		
		
		return null;
	}

	@Override
	public List<Usuario> ordenarBuscaUsuarios(UsuarioForm usuarioSessao, UsuarioForm form, String tipoVisualizar) {

		if ( ! usuarioSessao.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))
			return buscarUsuarios(form, form.getOpcaoTipoBuscaUsuarios());
		else
			return buscarUsuarios(form, "infoPessoais");
	}
		
	
	@Override
	public String validarAcessoUsuario(UsuarioForm user) {
		
		if (user.getDataValidadeAcesso().compareTo(new Date()) < 0)
			return "N";
		
	    return "";
	}


	@Override
	public void prepararEdicaoUsuario(UsuarioForm form) {
		Usuario usuario = dao.findUsuario(form.getId());
		BeanUtils.copyProperties(usuario, form);		
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(form.getIdEstado()));
		form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(form.getIdCidade()));
		form.setDataNascimentoFmt(DateUtil.formataData(usuario.getDataNascimento()));
	}
	
	@Override
	public UsuarioForm prepararEdicaoUsuarioAdmin(Long idUsuarioAdmin) {
		Usuario usuario = dao.findUsuario(idUsuarioAdmin);		
		UsuarioForm form = new UsuarioForm();
		BeanUtils.copyProperties(usuario, form);
		form.setListaEstados(estadosService.listarTodosEstadosSelect());
		form.setListaCidades(cidadesService.selecionarCidadesPorIdEstadoSelect(form.getIdEstado()));
		form.setListaBairros(bairrosService.selecionarBairrosPorIdCidadeSelect(form.getIdCidade()));
		if ( form.getDataNascimento() != null )
			preparaCampoDataNascimentoEditarUsuario(form);
		
		return form;
	}


	@Override
	public UsuarioForm prepararDetalhesUsuarioForm(Long idUsuario, Long idUsuarioSessao) {
		Usuario usuario = dao.findUsuario(idUsuario);
		UsuarioForm form = new UsuarioForm();		
		BeanUtils.copyProperties(usuario, form);
		
		if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
			form.setListaPreferenciaImoveis(preferenciaLocalidadeDao.findPreferencialocalidadeByIdUsuario(idUsuario));
			form.setQuantTotalPrefImoveis(AppUtil.recuperarQuantidadeLista(form.getListaPreferenciaImoveis()));
		}
		else {			
			form.setQuantTotalParcerias(parceriaDao.findQuantidadeParceriaPorUsuarioPorStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));
			form.setQuantTotalIntermediacoes(intermediacaoDao.findQuantidadeIntermediacaoPorUsuarioPorStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));			
			form.setQuantUsuarioAprovServico(usuario.getQuantUsuarioAprovServico());
			form.setQuantUsuarioDesaprovServico(usuario.getQuantUsuarioDesaprovServico());
		}
		
		List<Imovel> listaImoveisUsuario = imovelService.listarMeusImoveis(idUsuario);
		List<Imovel> listaImoveisFinal = new ArrayList<Imovel>();
		if ( ! CollectionUtils.isEmpty(listaImoveisUsuario) ){			
			for (Imovel imovel : listaImoveisUsuario){
				imovel.setInteressadoImovel(imovelFavoritosService.checarUsuarioEstaInteressadoImovel(idUsuario, imovel.getId()));	
				listaImoveisFinal.add(imovel);
			}
			form.setListaImoveisUsuario(listaImoveisFinal);
		}
		else
			form.setListaImoveisUsuario(null);
		
		form.setDataNascimentoFmt(DateUtil.formataData(usuario.getDataNascimento()));
		
		form.setListaNotasUsuario(notaService.listarTodasNotasPorPerfil(idUsuario, new NotaForm()));
		form.setListaSeguidores(seguidorService.recuperarSeguidoresPorIdUsuarioSeguido(idUsuario));		
		form.setListaRecomendacoes(recomendacaoService.recuperarRecomendacoesPorIdUsuarioRecomendado(idUsuario));		
		form.setListaContatosUsuario(contatoService.recuperarConvidadosHabilitados(idUsuario, new ContatoForm()));		
		
		form.setQuantTotalSeguidores(AppUtil.recuperarQuantidadeLista(form.getListaSeguidores()));
		form.setQuantTotalImoveis(AppUtil.recuperarQuantidadeLista(form.getListaImoveisUsuario()));
		form.setQuantTotalInteressadosImoveis(imovelFavoritosDao.findQuantUsuariosInteressadosByIdUsuarioByStatus(idUsuario, null));
		form.setQuantTotalVisitasImoveis(imovelvisualizadoDao.findQuantidadeVisitantesByIdUsuarioByStatus(idUsuario, null));
		form.setQuantTotalNotas(AppUtil.recuperarQuantidadeLista(form.getListaNotasUsuario()));
		form.setQuantTotalContatos(AppUtil.recuperarQuantidadeLista(form.getListaContatosUsuario()));
		form.setQuantTotalRecomendacoes(AppUtil.recuperarQuantidadeLista(form.getListaRecomendacoes()));		
		
		
		if (idUsuarioSessao != null && idUsuarioSessao.longValue() > 0 ){
			form.setPossuiContatoUsuarioSessao(contatoService.checarTipoContato(idUsuario, idUsuarioSessao));
			form.setIsSeguindoUsuario(seguidorService.checarUsuarioEstaSeguindo(idUsuarioSessao, idUsuario));
		}
		
		return form;
	}


	@Override
	public UsuarioForm prepararDetalhesUsuarioFormAdmin(Long idUsuario) {
		Usuario usuario = dao.findUsuario(idUsuario);
		UsuarioForm form = new UsuarioForm();		
		BeanUtils.copyProperties(usuario, form);
		form.setListaPlanosDisponiveis(planoDao.listAll());
		form.setListaServicosDisponiveis(paramservicoDao.findParametrosSemTipoAssinatura(form));
		
		if ( usuario.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){
			form.setListaImoveisUsuario(imovelService.listarMeusImoveis(idUsuario));
			form.setListaPreferenciaImoveis(preferenciaLocalidadeDao.findPreferencialocalidadeByIdUsuario(idUsuario));		
		}
		else {
			form.setListaImoveisUsuario(imovelService.listarMeusImoveis(idUsuario));
			form.setQuantTotalParcerias(parceriaDao.findQuantidadeParceriaPorUsuarioPorStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));
			form.setQuantTotalIntermediacoes(intermediacaoDao.findQuantidadeIntermediacaoPorUsuarioPorStatus(idUsuario, StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));			
			form.setQuantUsuarioAprovServico(usuario.getQuantUsuarioAprovServico());
			form.setQuantUsuarioDesaprovServico(usuario.getQuantUsuarioDesaprovServico());
		}
		
		form.setListaNotasUsuario(notaService.listarTodasNotasPorPerfil(idUsuario, new NotaForm()));
		form.setListaMensagemAdmin(mensagemAdminService.recuperaTodasMensagensPorUsuario(idUsuario));
		form.setListaServicos(servicoService.recuperarServicosDisponiveisPorUsuario(idUsuario));
		form.setListaPlanos(planousuarioDao.findPlanoUsuarioByIdUsuario(idUsuario));		
		form.setListaContatosUsuario(contatoService.recuperarConvidadosHabilitados(idUsuario, new ContatoForm()));
		
		form.setQuantTotalContatos(AppUtil.recuperarQuantidadeLista(form.getListaContatosUsuario()));
		form.setQuantTotalImoveis(AppUtil.recuperarQuantidadeLista(form.getListaImoveisUsuario()));
		form.setQuantTotalInteressadosImoveis(imovelFavoritosDao.findQuantUsuariosInteressadosByIdUsuarioByStatus(idUsuario, null));
		form.setQuantTotalVisitasImoveis(imovelvisualizadoDao.findQuantidadeVisitantesByIdUsuarioByStatus(idUsuario, null));
		return form;
	}
	
	@Override
	public UsuarioForm prepararDetalhesUsuarioAdmin(Long idUsuario) {
		Usuario usuario = dao.findUsuario(idUsuario);
		UsuarioForm form = new UsuarioForm();		
		BeanUtils.copyProperties(usuario, form);
		return form;
	}



	@Override
	public UsuarioForm alterarFotoUsuario(UsuarioForm form) {

		Usuario usuario = dao.findUsuario(form.getId());
		usuario.setFotoPrincipal(form.getFotoPrincipal());
		dao.save(usuario);		
		BeanUtils.copyProperties(usuario, form);
		return form;
	}


	@Override
	public void preparararSolicitacaoRenovacaoAssinaturaUsuario(UsuarioForm user, ServicoForm servicoForm) {
		
		servicoForm.setIdUsuario(user.getId());
		servicoForm.setListaOpcoesFormaPagamento(formapagamentoDao.listAll());
		if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo()))	{		
			servicoForm.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.ASSINATURA_PADRAO.getRotulo()));
			servicoForm.setAcao(ServicoValueEnum.ASSINATURA_PADRAO.getRotulo());
		}	
		else if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.CORRETOR.getRotulo())){
			servicoForm.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo()));
			servicoForm.setAcao(ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo());
		}	
		else if ( user.getPerfil().equals(PerfilUsuarioOpcaoEnum.IMOBILIARIA.getRotulo())){	
			servicoForm.setListaInfoServicoPagamento(infoservicoService.recuperarInfoServicoPagamentoPorNomeServico(ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo()));
			servicoForm.setAcao(ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo());
		}	
	}


	@Override
	public void preparaCampoDataNascimento(UsuarioForm form) {

		// carregando a lista de dias
		form.setListaDiaNascimento(new ArrayList<Select>());
		form.setListaMesNascimento(new ArrayList<Select>());
		form.setListaAnoNascimento(new ArrayList<Select>());
		
		// carregando a lista de dias
		List<Select> lista = new ArrayList<Select>();		
		for (int i = 1 ;i <= 31;i++){
			lista.add(new Select(String.valueOf(i), String.valueOf(i)));
		}
		form.setListaDiaNascimento(lista);		
		
		// carregando a lista de meses
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("1", MessageUtils.getMessage("lbl.mes.janeiro"));
		map.put("2", MessageUtils.getMessage("lbl.mes.fevereiro"));
		map.put("3", MessageUtils.getMessage("lbl.mes.marco"));
		map.put("4", MessageUtils.getMessage("lbl.mes.abril"));
		map.put("5", MessageUtils.getMessage("lbl.mes.maio"));
		map.put("6", MessageUtils.getMessage("lbl.mes.junho"));
		map.put("7", MessageUtils.getMessage("lbl.mes.julho"));
		map.put("8", MessageUtils.getMessage("lbl.mes.agosto"));
		map.put("9", MessageUtils.getMessage("lbl.mes.setembro"));		
		map.put("10", MessageUtils.getMessage("lbl.mes.outubro"));
		map.put("11", MessageUtils.getMessage("lbl.mes.novembro"));
		map.put("12", MessageUtils.getMessage("lbl.mes.dezembro"));
		lista = new ArrayList<Select>();
		for (int i = 1 ;i <= 12;i++){
			lista.add(new Select(String.valueOf(i), map.get(String.valueOf(i))));
		}
		form.setListaMesNascimento(lista);
		
		// carregando a lista de anos
		lista = new ArrayList<Select>();
		for (int i = 1900 ;i <= 2000;i++){
			lista.add(new Select(String.valueOf(i), String.valueOf(i)));
		}
		form.setListaAnoNascimento(lista);
		
		
	}


	@Override
	public void preparaCampoDataNascimentoEditarUsuario(UsuarioForm form) {

		// carregando a lista de dias
		form.setListaDiaNascimento(new ArrayList<Select>());
		form.setListaMesNascimento(new ArrayList<Select>());
		form.setListaAnoNascimento(new ArrayList<Select>());
		
		DateUtil dt = new DateUtil();
		dt.setTime(form.getDataNascimento());		
		form.setDiaNascimento(String.valueOf(dt.getDayOfMonth()));
		form.setMesNascimento(String.valueOf(dt.getMonth()));
		form.setAnoNascimento(String.valueOf(dt.getYear()));
		
		// carregando a lista de dias
		List<Select> lista = new ArrayList<Select>();		
		for (int i = 1 ;i <= 31;i++){
			lista.add(new Select(String.valueOf(i), String.valueOf(i)));
		}
		form.setListaDiaNascimento(lista);
			
		
		// carregando a lista de meses
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("1", MessageUtils.getMessage("lbl.mes.janeiro"));
		map.put("2", MessageUtils.getMessage("lbl.mes.fevereiro"));
		map.put("3", MessageUtils.getMessage("lbl.mes.marco"));
		map.put("4", MessageUtils.getMessage("lbl.mes.abril"));
		map.put("5", MessageUtils.getMessage("lbl.mes.maio"));
		map.put("6", MessageUtils.getMessage("lbl.mes.junho"));
		map.put("7", MessageUtils.getMessage("lbl.mes.julho"));
		map.put("8", MessageUtils.getMessage("lbl.mes.agosto"));
		map.put("9", MessageUtils.getMessage("lbl.mes.setembro"));		
		map.put("10", MessageUtils.getMessage("lbl.mes.outubro"));
		map.put("11", MessageUtils.getMessage("lbl.mes.novembro"));
		map.put("12", MessageUtils.getMessage("lbl.mes.dezembro"));
		lista = new ArrayList<Select>();
		for (int i = 1 ;i <= 12;i++){
			lista.add(new Select(String.valueOf(i), map.get(String.valueOf(i))));
		}
		form.setListaMesNascimento(lista);
		
		// carregando a lista de anos
		lista = new ArrayList<Select>();
		for (int i = 1900 ;i <= 2000;i++){
			lista.add(new Select(String.valueOf(i), String.valueOf(i)));
		}
		form.setListaAnoNascimento(lista);		
		
	}


	@Override
	public UsuarioForm cadastrarUsuarioAdmin(UsuarioForm frm) {
		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(frm, usuario);
        usuario.setPassword(md5(frm.getPassword()));
        usuario.setStatusUsuario(StatusUsuarioEnum.LIBERADO.getRotulo());
        usuario.setDataSuspensao(new Date());
        usuario.setDataCadastro(new Date());
        usuario.setDataUltimoAcesso(null);
        usuario.setAtivado("S");
        usuario.setPerfil(PerfilUsuarioOpcaoEnum.ADMIN.getRotulo());
        
        DateUtil dtNascimento = new DateUtil(frm.getDiaNascimento(), frm.getMesNascimento(), frm.getAnoNascimento());
        usuario.setDataNascimento(dtNascimento.getTime());
      	
        usuario.setCpf(frm.getCpf());       
        
        Estados estado = estadosDao.findEstadosById(frm.getIdEstado());
        Cidades cidade = cidadesDao.findCidadesById(frm.getIdCidade());
        Bairros bairro = bairrosDao.findBairrosById(frm.getIdBairro());
        
        usuario.setUf(estado.getUf());
        usuario.setEstado(estado.getNome());
        usuario.setCidade(cidade.getNome());
        usuario.setBairro(bairro.getNome()); 
        usuario.setCodigoIdentificacao(this.gerarCodigoIdentificacao(usuario, estado));
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 
        //cal.add(Calendar.DAY_OF_MONTH, UsuarioInterface.QUANT_DIAS_FIM_SERVICO);
        usuario.setDataValidadeAcesso(cal.getTime());

        dao.save(usuario);
        frm = new UsuarioForm();
        BeanUtils.copyProperties(usuario, frm);
        return frm;

	}
	
	@Override
	@Transactional
	public void suspenderUsuario(Long idUsuario, String tempo, String motivo){
		
		Usuario usuario = dao.findUsuario(idUsuario);
		usuario.setStatusUsuario(StatusUsuarioEnum.SUSPENSO.getRotulo());
		Calendar cal = Calendar.getInstance(); 
        cal.setTime(new Date()); 
        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(tempo));
		usuario.setDataSuspensao(cal.getTime());
		usuario.setMotivoSuspensao(motivo);
		dao.save(usuario);
	}


	@Override
	@Transactional
	public void cancelarSuspensaoUsuario(Long idUsuario) {
		Usuario usuario = dao.findUsuario(idUsuario);
		usuario.setStatusUsuario(StatusUsuarioEnum.LIBERADO.getRotulo());
		usuario.setDataSuspensao(null);
		usuario.setMotivoSuspensao("");
	}
	
	@Override
	public List<Usuario> pesquisarTodosUsuarios (UsuarioForm form){		
		form.setNome(form.getValorBusca());
		return dao.findUsuariosByCampo(form, "nomeLike", false);
	}
	
	@Override
	public List<Usuario> pesquisarTodosUsuariosAdmin (String valor){		
		return dao.findUsuarioByValorAdmin(valor);
	}


	@Override
	public boolean validarBuscarUsuarios(UsuarioForm form, BindingResult result) {
		Usuario usuario = new Usuario();
		usuario.setLogin("Israel Teste");
		// messageSender.sendMessage(usuario);

		boolean possuiErro = false;
		if (! StringUtils.isEmpty(form.getOpcaoTipoBuscaUsuarios())){
			
			if (form.getOpcaoTipoBuscaUsuarios().equals("infoPessoais")){
				if ( form.getIdEstado() < 0 ){
					result.rejectValue("idEstado", "msg.erro.campo.obrigatorio");
					possuiErro = true;
				}
			}
			else if (form.getOpcaoTipoBuscaUsuarios().equals("infoPreferenciais")){
					boolean isEmtpy = StringUtils.isEmpty(form.getTipoImovel()) && StringUtils.isEmpty(form.getAcao());
					boolean isSemSelecao = form.getTipoImovel().equals("-1") && form.getAcao().equals("-1");
					if ( form.getIdEstado() < 0 ) {
						result.rejectValue("idEstado", "msg.erro.campo.obrigatorio");
						possuiErro = true;
					}
			}			
		}		
		
		return possuiErro;
	}


	@Override
	public void prepararParaCarregarTimeLine(UsuarioForm user, HttpSession session, ModelMap map) {
		
		// Carregando a regra inicial 0 
		session.setAttribute(TimelineService.ULTIMA_REGRA_TIMELINE, 0);

		// carregando os Ids de contato e usuarios que esteja seguindo
		List listaIdsContatos = contatoService.recuperarIDsMeusContatos(user.getId());
		List listaIdsUsuariosSeguindo = seguidorService.recuperarIdsSeguidores(user.getId());
		List<Long> listaIds = new ArrayList<Long>();
		
		if (! CollectionUtils.isEmpty(listaIdsContatos) )
			listaIds.addAll(listaIdsContatos);
		
		if (! CollectionUtils.isEmpty(listaIdsUsuariosSeguindo) )
			listaIds.addAll(listaIdsUsuariosSeguindo);		
		
		/**
		 * Utilizar uma Hashtable para tambem carregar os Ids dos usuarios e para cada entrada na Hashtable carrega 
		 * a respectiva "Index Posicao" que sera usado para carregar um imovel do usuario e minimizar a repeticao na exibicao de imoveis 
		 *   
		 */		
		
		ParametrosTimeline paramTimeline = new ParametrosTimeline();
		paramTimeline.setListaIds(listaIds);
		if (! CollectionUtils.isEmpty(listaIds))
			paramTimeline.setExisteIdUsuario("S");		
		else
			paramTimeline.setExisteIdUsuario("N");	
		
		
		// as variaveis 'inicioRegra' e 'fimRegra' sao usadas para evitar que uma determinada regra seja chamada já que não há dados para ela	
		if (! user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){ // Inicio Timeline para Corretores e Imobiliarias 
			paramTimeline.setInicioRegra(1);
			paramTimeline.setFimRegra(22);
		}
		else {
			paramTimeline.setInicioRegra(1);
			paramTimeline.setFimRegra(23);
		}
		
		paramTimeline.setRegraPrefImovel(1);	
		
		// carregar a quantidade de imoveis anuncios do dia
		paramTimeline.setQuantAnuncioImovel(imovelDestaqueService.checarQuantidadeImoveisAnuncioNoDia());		
		
		session.setAttribute(TimelineService.PARAMETROS_TIMELINE, paramTimeline);	
	}


	@Override
	public List<String> carregarTimeline(UsuarioForm user, HttpSession session) {	
		List<String> listaFinal = new ArrayList<String>();
		listaFinal = this.carregar(user, session);
		return listaFinal;
	}
	
	public List<String> carregar(UsuarioForm user, HttpSession session) {
		
				/**		  
				 * Por default, exibir sempre no primeiro registro um Imovel Anuncio (se houver). Exibindo primeiramente o anuncio com maior relevancia e/ou mais recente
				 */
				
				/***
				 * Talvez separar cada regra por metodo para reunir o Imovel ou lista de Imoveis para depois montar a TimeLine	
				 */
				
				List<Imovel> listaFinal = new ArrayList<Imovel>();
				boolean isNotaExiste = false;	
				Nota notaTimeLine = null;
				Usuario usuarioTimeline = null;
				boolean isUsuarioTimeLine = false;
				Preferencialocalidade prefLocalidadeTimeline = new Preferencialocalidade();
				boolean isPrefLocalidadeExiste = false;
				List<String> listaFinalTimeLine = new ArrayList<String>();		
				
				List<Imovel> lista = null;
				int regraSel = 0;
				Imovel imovel = null;
				ParametrosTimeline paramTimeline = (ParametrosTimeline)session.getAttribute(TimelineService.PARAMETROS_TIMELINE);
				
				
				int regra = (int)session.getAttribute(TimelineService.ULTIMA_REGRA_TIMELINE);			
				if ( regra == 0 ){ // usuario acabou de se logar no sistema entao primeiro registro na timeline sera um Anuncio (se houver)	-- Teoricamente vai ser um anuncio de grande Importancia			
					imovel = this.regraTimeLineRecuperarImovelAnuncio(paramTimeline);
					if ( imovel != null )
						listaFinal.add(imovel);
					
					regra = 1; // para nao repetir em seguida a exibicao de um novo anuncio
					session.setAttribute(TimelineService.ULTIMA_REGRA_TIMELINE, regra);
				}
				
				if (! user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){ // Inicio Timeline para Corretores e Imobiliarias 					
					
					if ( regra == 1 ){	
						
						while ( (AppUtil.recuperarQuantidadeLista(listaFinal) < 2)){  // para o caso de o usuario ter algum contato ou usuario seguindo
							
							regraSel = paramTimeline.getInicioRegra() + (int) (Math.random() * paramTimeline.getFimRegra());	
							
							if ( ( regraSel >= 1 ) && ( regraSel <= 4 )) { // Exibir preferencia de imóveis de usuários com contato ou que esteja seguindo
								prefLocalidadeTimeline = this.regraTimelineRecuperarPreferenciaLocalidadeUsuario(paramTimeline, false);
								if ( prefLocalidadeTimeline != null ){
									isPrefLocalidadeExiste = true;
									break;
								}
								else
									paramTimeline.setInicioRegra(4);
								
							} // Exibir imoveis de seus contatos e /ou usuario que esteja seguindo que deseje ser intermediado ou queira parceria
							else if ( ( regraSel >= 4 ) && ( regraSel <= 8 ) && ! paramTimeline.isEmptyImoveisIntermediacaoParceria()   ) { 
								lista = this.regraTimeLineRecuperarImoveisCompartilhadosIdsUsuarios("S", paramTimeline);
								if (! CollectionUtils.isEmpty(lista)){
									listaFinal.addAll(lista);
									break;
								}
								else if ( CollectionUtils.isEmpty(lista)){ // para o caso do usuario ainda nao tiver contatos
									lista = this.regraTimeLineRecuperarImoveisCompartilhadosIdsUsuarios("N", paramTimeline);
									if (! CollectionUtils.isEmpty(lista)){
										listaFinal.addAll(lista);
										break;
									}
								}
								else {
									paramTimeline.setEmptyImoveisIntermediacaoParceria(true);									
									if ( paramTimeline.getInicioRegra() == 4 )
										paramTimeline.setInicioRegra(9);  // afunilando ainda mais o range de regras
								}	
							}
							// Exibir Notas de seus contatos e usuarios que esteja seguindo
							else if ( ( regraSel >= 9 ) && ( regraSel <= 11 ) && ! CollectionUtils.isEmpty(paramTimeline.getListaIds()) && ! paramTimeline.isEmptyNotas() ) { 
								notaTimeLine = this.regraTimeLineRecuperarNota(user, paramTimeline);
								if ( notaTimeLine != null ){
									isNotaExiste = true;
									break;
								}
								else {
									paramTimeline.setEmptyNotas(true);
									if ( paramTimeline.getInicioRegra() == 9 )
										paramTimeline.setInicioRegra(12);  // afunilando ainda mais o range de regras
								}
							}
							
							// Exibir imoveis de seus contatos e usuarios que esteja seguindo
							else if ( ( regraSel >= 12 ) && ( regraSel <= 14 ) && ! paramTimeline.isEmptyImoveisContatos()) { 
								lista = this.regraTimeLineRecuperarImoveisCompartilhadosIdsUsuarios( "N", paramTimeline);
								if (! CollectionUtils.isEmpty(lista)){
									listaFinal.addAll(lista);
									break;
								}
								else if ( CollectionUtils.isEmpty(lista)){
									lista = this.regraTimeLineRecuperarImoveisAleatoriamente(user, paramTimeline);
									if (! CollectionUtils.isEmpty(lista)){
										listaFinal.addAll(lista);
										break;
									}
								}
								else {
									paramTimeline.setEmptyImoveisContatos(true);	
									if ( paramTimeline.getInicioRegra() == 12 )
										paramTimeline.setInicioRegra(15);  // afunilando ainda mais o range de regras							
								}
							}	
							
							// Exibir imoveis que deseje ser intermediado ou queira parceria, mas de usuarios que nao sejam seus contatos ou esteja seguindo
							else if ( ( regraSel >= 15 ) && ( regraSel <= 17 ) && ! paramTimeline.isEmptyImoveisIntermediacaoParceriaSemContato()) { 
								lista = this.regraTimeLineRecuperarImoveisCompartilhadosIdsUsuarios("N", paramTimeline);
								if (! CollectionUtils.isEmpty(lista)){
									listaFinal.addAll(lista);
									break;
								}
								else if (CollectionUtils.isEmpty(lista)){ // para o caso do usuario ainda nao tiver contatos
									lista = this.regraTimeLineRecuperarImoveisCompartilhadosIdsUsuarios("S", paramTimeline);
									if (! CollectionUtils.isEmpty(lista)){
										listaFinal.addAll(lista);
										break;
									}
								}
								else {
									paramTimeline.setEmptyImoveisIntermediacaoParceriaSemContato(true);	
									if ( paramTimeline.getInicioRegra() == 15 )
										paramTimeline.setInicioRegra(18);  // afunilando ainda mais o range de regras									
								}	
							}	
								
							else if ( ( regraSel >= 18 ) && ( regraSel <= 20 ) && ! paramTimeline.isEmptyImoveisAnuncios()) { // Exibir uma anuncio imovel  
								imovel = this.regraTimeLineRecuperarImovelAnuncio(paramTimeline);
								if ( imovel != null){
									listaFinal.add(imovel);
									break;
								}
								else {
									paramTimeline.setEmptyImoveisAnuncios(true);									
									if ( paramTimeline.getInicioRegra() == 18 )
										paramTimeline.setInicioRegra(21);  // afunilando ainda mais o range de regras
								}
							}	
							else if (  regraSel == 21  && !paramTimeline.isEmptyImoveisAleatorios()) { // Exibir aleatoriamente algum imovel da plataforma  
								lista = this.regraTimeLineRecuperarImoveisAleatoriamente(user, paramTimeline);
								if (! CollectionUtils.isEmpty(lista)){
									listaFinal.addAll(lista);
									break;
								}
								else {
									paramTimeline.setEmptyImoveisAleatorios(true);									
									if ( paramTimeline.getInicioRegra() == 21 )
										paramTimeline.setInicioRegra(22);  // afunilando ainda mais o range de regras
								}
							}	
							else if ( ( regraSel == 22 )  ) { // Sugerir algum usuario para formalizar contato ou para seguir
								usuarioTimeline = this.regraTimelineRecuperarSugestaoUsuario(paramTimeline, user);
								if ( usuarioTimeline != null){									
									paramTimeline.setUsuarioTimeLine(true);
									break;
								}
							}								
						}		
					}
					
					session.setAttribute(TimelineService.PARAMETROS_TIMELINE, paramTimeline);
					
					if (isUsuarioTimeLine){
						listaFinalTimeLine.add(carregarTimeLineUsuario(usuarioTimeline, user));
						if (! CollectionUtils.isEmpty(listaFinal)){					
							for (Imovel imovelFinal : listaFinal){
								listaFinalTimeLine.add(carregarTimeLineImovel(user, imovelFinal));
							}
						}
						return listaFinalTimeLine;
					}					
					else if ( isPrefLocalidadeExiste ){
						listaFinalTimeLine.add(this.carregarTimeLinePreferenciaLocalidade(prefLocalidadeTimeline));
						
						if (! CollectionUtils.isEmpty(listaFinal)){					
							for (Imovel imovelFinal : listaFinal){
								listaFinalTimeLine.add(carregarTimeLineImovel(user, imovelFinal));
							}
						}
						return listaFinalTimeLine;
						
					}					
					else if ( isNotaExiste ){
						listaFinalTimeLine.add(carregarTimeLineNota(notaTimeLine, user));
						if (! CollectionUtils.isEmpty(listaFinal)){					
							for (Imovel imovelFinal : listaFinal){
								listaFinalTimeLine.add(carregarTimeLineImovel(user, imovelFinal));
							}
						}
						return listaFinalTimeLine;
					}
					else {
						if (! CollectionUtils.isEmpty(listaFinal)){					
							for (Imovel imovelFinal : listaFinal){
								listaFinalTimeLine.add(carregarTimeLineImovel(user, imovelFinal));
							}
						}
						return listaFinalTimeLine;
					}
					
					
				} // Fim Timeline para Corretores e Imobiliaris 
				
				else if (user.getPerfil().equals(PerfilUsuarioOpcaoEnum.PADRAO.getRotulo())){ // Inicio Timeline para corretores
										
					if ( regra == 1 ){ // podera nao ser exibido um novo anuncio de imovel consecutivo 
				
						regraSel = 0;	
						
							while ( (AppUtil.recuperarQuantidadeLista(listaFinal) < 2)){
								regraSel = paramTimeline.getInicioRegra() + (int) (Math.random() * paramTimeline.getFimRegra());								
								
								// Exibir alguma Nota de Contato
								 if ( ( regraSel >= 1 ) && ( regraSel <= 3 ) && ! paramTimeline.isEmptyNotas() ) { 
									notaTimeLine = this.regraTimeLineRecuperarNota(user,  paramTimeline);
									if ( notaTimeLine != null ){
										isNotaExiste = true;
										break;
									}	
									else {
										paramTimeline.setEmptyNotas(true);
										if ( paramTimeline.getInicioRegra() == 1)
											paramTimeline.setInicioRegra(4);
									}	
								}
								 
								// Exibir um anucio imovel
									else if ( ( regraSel >= 4 ) && ( regraSel <= 6 ) && ! paramTimeline.isEmptyImoveisAnuncios()) { 
										imovel = this.regraTimeLineRecuperarImovelAnuncio(paramTimeline);
										if ( imovel != null) {
											listaFinal.add(imovel);
											break;
										}
										else {
											paramTimeline.setEmptyImoveisAnuncios(true);
											if ( paramTimeline.getInicioRegra() == 4)
												paramTimeline.setInicioRegra(7);
										}	
									}
								 
								// Exibir imóveis de usuarios que tenha contato ou que esteja seguindo
								 else if ( ( regraSel >= 7 ) && ( regraSel <= 9 ) && !paramTimeline.isEmptyImoveisContatos() ) { 
								 	lista = this.regraTimeLineRecuperarImoveisCompartilhadosIdsUsuarios( "N", paramTimeline);
									if (! CollectionUtils.isEmpty(lista)){
										listaFinal.addAll(lista);
										break;
									}
									else if ( CollectionUtils.isEmpty(lista)){ // para o caso do usuario ainda nao tiver contatos
										lista = this.regraTimeLineRecuperarImoveisAleatoriamente(user, paramTimeline);
										if (! CollectionUtils.isEmpty(lista)){
											listaFinal.addAll(lista);
											break;
										}
									}
									else {
										paramTimeline.setEmptyImoveisContatos(true);
										if ( paramTimeline.getInicioRegra() == 7)
											paramTimeline.setInicioRegra(10);
									}
								 } 		
								
								// Recuperar Imoveis de acordo com a Preferencia de Imoveis
								else if ( ( regraSel >= 10 ) && ( regraSel <= 16 )  ) { 
									lista = this.regraTimeLineRecuperarImoveisPreferencia(user, paramTimeline);
									if (! CollectionUtils.isEmpty(lista)){
										listaFinal.addAll(lista);	
										break;
									}
									else if ( CollectionUtils.isEmpty(lista)){ // para o caso do usuario ainda nao tiver contatos
										lista = this.regraTimeLineRecuperarImoveisAleatoriamente(user, paramTimeline);
										if (! CollectionUtils.isEmpty(lista)){
											listaFinal.addAll(lista);
											break;
										}
									}
								}
								
								// Recuperar Imoveis semelhantes àqueles que o usuário tenha visitado anteriormente
								else if ( ( regraSel >= 17) && ( regraSel <= 19 )  ) { 
									lista = this.regraTimeLineRecuperarImoveisVisitadosSemelhantes(user, paramTimeline);
									if (! CollectionUtils.isEmpty(lista)){
										listaFinal.addAll(lista);			
										break;
									}
									else if ( CollectionUtils.isEmpty(lista)){ // para o caso do usuario ainda nao tiver contatos
										lista = this.regraTimeLineRecuperarImoveisAleatoriamente(user, paramTimeline);
										if (! CollectionUtils.isEmpty(lista)){
											listaFinal.addAll(lista);
											break;
										}
									}
								}
								
								// Exibir algum imovel selecionado aleatoriamente da base
								else if ( ( regraSel >= 20 ) && ( regraSel <= 21)) { 
									lista = this.regraTimeLineRecuperarImoveisAleatoriamente(user, paramTimeline);
									if (! CollectionUtils.isEmpty(lista)){
										listaFinal.addAll(lista);
										break;
									}
								}
								
								// Sugerir algum contato para realizar contato ou para seguir
								else if ( ( regraSel >= 22 ) || ( regraSel <= 23 )) {   
									usuarioTimeline = this.regraTimelineRecuperarSugestaoUsuario(paramTimeline, user);
									if ( usuarioTimeline != null){
										isUsuarioTimeLine = true;
										break;
									}	
								}
							}							
						}
						
					}	
				
					session.setAttribute(TimelineService.PARAMETROS_TIMELINE, paramTimeline);
					
					if ( isNotaExiste ){
						listaFinalTimeLine.add(carregarTimeLineNota(notaTimeLine, user));
						if (! CollectionUtils.isEmpty(listaFinal)){					
							for (Imovel imovelFinal : listaFinal){
								listaFinalTimeLine.add(carregarTimeLineImovel(user, imovelFinal));
							}
						}
						return listaFinalTimeLine;
					}
					else if (isUsuarioTimeLine){
						listaFinalTimeLine.add(carregarTimeLineUsuario(usuarioTimeline, user));
						if (! CollectionUtils.isEmpty(listaFinal)){					
							for (Imovel imovelFinal1 : listaFinal){
								listaFinalTimeLine.add(carregarTimeLineImovel(user, imovelFinal1));
							}
						}
						return listaFinalTimeLine;
					}
					else {
						if (! CollectionUtils.isEmpty(listaFinal)){					
							for (Imovel imovelFinal2 : listaFinal){
								listaFinalTimeLine.add(carregarTimeLineImovel(user, imovelFinal2));
							}
						}
						return listaFinalTimeLine;
					}
				 // Fim Timeline para usuario comuns				
				
	}
	

	private List<Imovel> regraTimeLineRecuperarImoveisVisitadosSemelhantes(UsuarioForm user, ParametrosTimeline paramTimeline) {		
		
		Imovelvisualizado imovelvisualizado = imovelvisualizadoDao.findImoveisVisitadosByIdUsuarioByIndex(user.getId(), paramTimeline.getIdIndexImovelVisitado());
		if (imovelvisualizado != null){
			ImovelForm form = new ImovelForm();
			form.setIdEstado(imovelvisualizado.getImovel().getIdEstado());
			form.setIdCidade(imovelvisualizado.getImovel().getIdCidade());
			form.setIdBairro(imovelvisualizado.getImovel().getIdBairro());
			form.setTipoImovel(imovelvisualizado.getImovel().getTipoImovel());
			return imovelService.buscarImovelParaTimeline(form, user.getId() );
		}
		return null;
	}


	private Usuario regraTimelineRecuperarSugestaoUsuario(ParametrosTimeline param,	UsuarioForm user) {
	
		Usuario usuario = dao.findUsuarioByUsuarioByIndex(param.getListaIds(), 
														  user, 
														  param.getIdIndexSugestaoUsuario());
		if ( usuario != null) {
			param.setIdIndexSugestaoUsuario(param.getIdIndexSugestaoUsuario() + 1);	
			return usuario;
		}
		return null;
	}


	private Preferencialocalidade regraTimelineRecuperarPreferenciaLocalidadeUsuario(ParametrosTimeline param, boolean isAleatorio) {
		
		if ( isAleatorio ){					
			Preferencialocalidade pref= preferenciaLocalidadeDao.findPreferencialocalidadeByUsuarioByIndexByAleatorio(param.getListaIds(), 
																													  param.getIdIndexPrefImovelAleatoriamente() , 
																													  isAleatorio);
			if ( pref != null ){
				param.setIdIndexPrefImovelAleatoriamente(param.getIdIndexPrefImovelAleatoriamente() + 1);
				return pref;
			}
			else 
				return null;
		}
		else {			
			Preferencialocalidade pref= preferenciaLocalidadeDao.findPreferencialocalidadeByUsuarioByIndexByAleatorio(param.getListaIds(), 
																													  param.getIdIndexPrefImovel(), 
																													  isAleatorio);
			if ( pref != null ){
				param.setIdIndexPrefImovel(param.getIdIndexPrefImovel() + 1);
				return pref;
			}
			else 
				return null;
		}		

	}
	
	public Imovel regraTimeLineRecuperarImovelAnuncio(ParametrosTimeline param){	
		if ( param.getQuantAnuncioImovel() > 0 )	
			return imovelDestaqueService.recuperarImovelAnuncio(param.getIdUltimoAnuncioImovel());	
		else
			return null;
	}
	
	
	public List<Imovel> regraTimeLineRecuperarImoveisIdsUsuarios(ParametrosTimeline param){		
		if ( ! CollectionUtils.isEmpty(param.getListaIds())) {					
			List<Imovel> listaImovel = imovelService.recuperarImovelPorIdsUsuarioPorPosicao(param.getListaIds(), 
																							param.getUltimoIndexIdsUsuario()); // o parametro informado e index para o setFirstResult no Hibernate 
	
			param.setUltimoIndexIdsUsuario(param.getUltimoIndexIdsUsuario() + AppUtil.recuperarQuantidadeLista(listaImovel));
			return listaImovel;
		}
		else
			return null;
	}
	
	public List<Imovel> regraTimeLineRecuperarImoveisCompartilhadosIdsUsuarios(String aceitaCompartilhado, ParametrosTimeline param){// o parametro aceitaCompartilhado filtra se o Imovel e compartilhado ou nao
		
		if ( ! CollectionUtils.isEmpty(param.getListaIds())) {			
			if ( aceitaCompartilhado.equals("S")){								
				List<Imovel> listaImovel = imovelService.recuperarImovelPorIdsUsuarioPorPosicaoPorAceitaCompartilhado(param.getListaIds(), 
																													  param.getUltimoIndexIdsUsuarioCompartilhado(), 
																													  aceitaCompartilhado); // o parametro informado e index para o setFirstResult no Hibernate 

				param.setUltimoIndexIdsUsuarioCompartilhado(param.getUltimoIndexIdsUsuarioCompartilhado() + AppUtil.recuperarQuantidadeLista(listaImovel));
				return listaImovel;
			}
			else {					
				List<Imovel> listaImovel = imovelService.recuperarImovelPorIdsUsuarioPorPosicao(param.getListaIds(), 
																								param.getUltimoIndexIdsUsuario()); // o parametro informado e index para o setFirstResult no Hibernate 

				param.setUltimoIndexIdsUsuario(param.getUltimoIndexIdsUsuario() + AppUtil.recuperarQuantidadeLista(listaImovel));
				return listaImovel;
			}
		}
		else
			return null;
	}
	
	
	public List<Imovel> regraTimeLineRecuperarImoveisCompartilhadosSemContato(UsuarioForm user, ParametrosTimeline param, String aceitaCompartilhado){ // o parametro aceitaCompartilhado filtra se o Imovel e compartilhado ou nao		 
				
			if ( aceitaCompartilhado.equals("S")){				
				List<Imovel> listaImovel = imovelService.recuperarImovelPorIdsUsuarioPorPosicaoPorAceitaCompartilhado(user.getId(), 
																													  param.getUltimoIndexIdsUsuarioCompartilhado(), 
																													  aceitaCompartilhado); // o parametro informado e index para o setFirstResult no Hibernate 
		
				param.setUltimoIndexIdsUsuarioCompartilhado(param.getUltimoIndexIdsUsuarioCompartilhado() + AppUtil.recuperarQuantidadeLista(listaImovel));
				return listaImovel;
			}
			else {							
				List<Imovel> listaImovel = imovelService.recuperarImovelPorIdsUsuarioPorPosicao(user.getId(), 
																								param.getUltimoIndexIdsUsuario()); // o parametro informado e index para o setFirstResult no Hibernate 

				param.setUltimoIndexIdsUsuario(param.getUltimoIndexIdsUsuario() + AppUtil.recuperarQuantidadeLista(listaImovel));
				return listaImovel;
			}
	}
	
	public List<Imovel> regraTimeLineRecuperarImoveisPreferencia(UsuarioForm user, ParametrosTimeline param) {
		
		int index = param.getIdIndexPrefImovel();
		int regra = param.getRegraPrefImovel();	
		List<Imovel> lista = preferenciaLocalidadeDao.findImoveisByPrefLocalidadeByUsuarioByIndex(user, index, regra);
		if ( ! CollectionUtils.isEmpty(lista)){
			int quant = AppUtil.recuperarQuantidadeLista(lista);
			param.setIdIndexPrefImovel(index + quant); 
		}
		else {
			regra++;
			if ( regra > 5)
				regra = 1;
			
			param.setRegraPrefImovel(regra);
			param.setIdIndexPrefImovel(0);	
		}
		return lista;
	}
	
	public List<Imovel> regraTimeLineRecuperarImoveisAleatoriamente(UsuarioForm user, ParametrosTimeline param){
		
		int index = param.getIdIndexImovelAleatoriamente();
		List<Imovel> lista = imovelService.recuperarImoveisAleatoriamente(user, index);
		if ( ! CollectionUtils.isEmpty(lista))
			param.setIdIndexImovelAleatoriamente(index + AppUtil.recuperarQuantidadeLista(lista) );
			
		return lista;
	}
	
	public Nota regraTimeLineRecuperarNota(UsuarioForm user, ParametrosTimeline param){
		
		int index = param.getIdIndexNota();
		Nota nota = notaService.recuperarNotaByUsuarioByIndex( param.getListaIds(), index);
		if ( nota != null ){
			index +=  1;
			param.setIdIndexNota(index);	
			return nota;
		}
		else
			return null;
		
	}

	public String carregarTimeLineNota(Nota nota, UsuarioForm user){
		
		 StringBuffer buf = new StringBuffer("");
		 buf.append(" <div class='timeline-item last-timeline'> ");
		 buf.append(" 		<div class='timeline-badge'>  ");  
		 buf.append("			<a href='" + context.getContextPath() + "/usuario/detalhesUsuario/"+ nota.getUsuario().getId() +"' > ");
		 buf.append("    			<img class='timeline-badge-userpic' src='data:image/jpeg;base64," + nota.getUsuario().getImagemArquivo() + "' style='width: 72px; height: 72px; ' />  ");
		 buf.append(" 			</a>");
		 buf.append("		</div>  ");
		 buf.append("	<div class='timeline-body'>   ");
		 buf.append("    	<div class='timeline-body-arrow'>  "); 
		 buf.append("    	</div>   ");
		 buf.append("    <div class='timeline-body-head'>  ");
		 buf.append("        <div class='timeline-body-head-caption'>   ");
		 buf.append("            <a href='" + context.getContextPath() + "/usuario/detalhesUsuario/" + nota.getUsuario().getId()  +"' class='timeline-body-title font-blue-madison'>" + nota.getUsuario().getNome() + " : </a>  ");
		 buf.append("            <span class='timeline-body-time font-grey-cascade' style='font-style: italic;' >" + MessageUtils.getMessage("lbl.nota.timeline")  + " </span>  ");
		 buf.append("        </div>  ");
		 buf.append("    </div> ");
		 buf.append("     <div class='timeline-body-content'> ");                                              
		 buf.append("        <div class='media inner-all'> ");		
		 buf.append("              <div class='media-body'>  ");
		 
		 if ( nota.getAcao().equals(NotaAcaoEnum.PARCERIA.getRotulo())) {														    			    	
			 buf.append(" ");					  
			 buf.append("					<small class='block text-muted'> <font size='3px;'> " + nota.getDescricao() +" <a href='" + context.getContextPath() + "/imovel/detalhesImovel/ "+ nota.getImovel().getId() + "' ><strong> " + nota.getImovel().getTitulo()  +" </strong></a></font></small> ");
		 }
		 else if ( nota.getAcao().equals(NotaAcaoEnum.PREFERENCIA.getRotulo())) {											    			    	
			 buf.append(" ");					  
			 buf.append("					<small class='block text-muted'> <font size='3px;'> " + MessageUtils.getMessage("lbl.notas.contato.add.preferencia.p1")  + " <a href='" + context.getContextPath() + "/usuario/detalhesUsuario/ "+ nota.getUsuario().getId() + "' ><strong> " + nota.getUsuario().getNome()  + " </strong></a>" +  MessageUtils.getMessage("lbl.notas.contato.add.preferencia.p2") + "</font></small> ");	
		 }
		 else if ( nota.getAcao().equals(NotaAcaoEnum.USUARIO.getRotulo())) {											    			    	
			 buf.append(" ");					  
			 buf.append("					<small class='block text-muted'> <font size='3px;'> " + MessageUtils.getMessage("lbl.notas.contato.informacoes.usuario.p1")  + " <a href='" + context.getContextPath() + "/usuario/detalhesUsuario/ "+ nota.getUsuario().getId() + "' ><strong> " + nota.getUsuario().getNome()  + " </strong></a>" +  MessageUtils.getMessage("lbl.notas.contato.informacoes.usuario.p2") + "</font></small> ");
		 }
		 else if ( nota.getAcao().equals(NotaAcaoEnum.PESSOAL.getRotulo())) {														    			    	
			 buf.append(" ");					  
			 buf.append("					<small class='block text-muted'> <font size='3px;'> " + nota.getDescricao() +" </font></small> ");
		 }
		 else if ( nota.getAcao().equals(NotaAcaoEnum.IMOVEL.getRotulo())) {											    			    	
			 buf.append(" ");					  
			 buf.append("					<small class='block text-muted'> <font size='3px;'> " + MessageUtils.getMessage("lbl.notas.contato.informacoes.imovel.p1")  + " <a href='" + context.getContextPath() + "/imovel/detalhesImovel/ "+ nota.getImovel().getId() + "' ><strong> " + nota.getImovel().getTitulo()  + " </strong></a>" +  MessageUtils.getMessage("lbl.notas.contato.informacoes.imovel.p2") + " <a href='" + context.getContextPath() + "/usuario/detalhesUsuario/ "+ nota.getUsuario().getId() + "' ><strong> " + nota.getUsuario().getNome()  + " </strong></a></font></small> ");
		 }
		 															
		 buf.append(" </br> ");              	  
		 buf.append("                   <em class='text-xs text-muted'> " + MessageUtils.getMessage("lbl.data.nota") + ": <span class='text-danger'> " + DateUtil.formataData(nota.getDataNota()) + "</span></em> ");
		 buf.append("              </div><!-- /.media-body -->  ");
		 buf.append("          </div><!-- /.media --> ");
		 buf.append("    	</div> ");
		 buf.append("	 </div> ");
		 buf.append("  </div> ");		 

		 return buf.toString();
	}
	
	
	public String carregarTimeLineImovel(UsuarioForm user, Imovel imovel) {
		
			String isInteressado = imovelFavoritosService.checarUsuarioEstaInteressadoImovel(user.getId(), imovel.getId());

		   StringBuffer buf = new StringBuffer("");
		   buf.append("<div class='timeline-item last-timeline'>");
		   buf.append("   <div class='timeline-badge'> ");
		   buf.append("			<a href='" + context.getContextPath() + "/usuario/detalhesUsuario/"+ imovel.getUsuario().getId() +"' > ");
		   buf.append("    			<img class='timeline-badge-userpic' src='data:image/jpeg;base64," + imovel.getUsuario().getImagemArquivo() + "' style='width: 72px; height: 72px; ' />  ");
		   buf.append(" 		</a>");
		   buf.append("   </div> ");
		   buf.append("   <div class='timeline-body'> ");
		   buf.append("    	<div class='timeline-body-arrow'>  "); 
		   buf.append("    	</div>   ");
		   buf.append("      <div class='timeline-body-head'> ");
		   buf.append("           <div class='timeline-body-head-caption'> ");
		   buf.append("               <a href='" + context.getContextPath() + "/usuario/detalhesUsuario/"+ imovel.getUsuario().getId() +"' class='timeline-body-title font-blue-madison'>" + imovel.getUsuario().getNome() + ": </a> ");		   
		   buf.append("               <span class='timeline-body-time font-grey-cascade' style='font-style: italic;'>" +  MessageUtils.getMessage("lbl.imovel.timeline")  +"</span> ");   
		   buf.append("           </div> ");
		   buf.append("      </div> ");
		   buf.append("       <div class='timeline-body-content'> ");
		   buf.append("          <div class='panel panel-theme shadow blog-list-slider'> ");
		   buf.append("                <div id='carousel-blog-list' class='carousel slide' data-ride='carousel'> ");		   
		   buf.append("                    <div class='carousel-inner'> ");
		   buf.append("                       <div class='item active'> ");
		   buf.append("                           <div class='blog-list'> ");
		   buf.append("                                <div class='media-list list-search'> ");
		   buf.append("                                   <div class='media rounded shadow no-overflow'> ");
		   buf.append("                                        <div class='media-left'> ");
		   buf.append("                                            <a href='" + context.getContextPath() + "/imovel/detalhesImovel/" +  imovel.getId() + "' > ");
		   buf.append("                                               <span class='meta-provider' style='font-size:13px;'>"+ imovel.getAcaoFmt() + " <br><strong>  R$ " + AppUtil.formataMoedaString(imovel.getValorImovel()) + " </strong></span><br> ");
		   buf.append("                                                <img src='data:image/jpeg;base64," + imovel.getImagemArquivo() + "' class='img-responsive' style='width: 230px; height: 290px; alt='admin'/> ");
		   buf.append("                                            </a> ");
		   buf.append("                                        </div> ");
		   buf.append("                              <div class='media-body'> ");
		   buf.append("                                           <span class='label pull-right' style='background-color: #9d2428; font-size: 12px'>Casa</span></br> ");		   
		   buf.append("                                            <h4 class='media-heading' style='margin-bottom:20px;'><a href='" + context.getContextPath() + "/imovel/detalhesImovel/" +  imovel.getId() + "' style='color : #9d2428;'>"+ imovel.getTitulo() + "</a></h4> ");
		   buf.append("                                            <h5 class='media-heading' style='margin-bottom:12px;'><i class='fa fa-map-marker'></i> " + imovel.getEndereco() + " - " + imovel.getBairro()  + " - " +  imovel.getCidade() + " - " + imovel.getUf() + " </h1> ");
		   buf.append("                                 <div class='col-md-5' > ");		
		   buf.append("                                           </div> ");
		   buf.append("                                            <div class='col-md-7'> ");
		   buf.append("                                               <table class='table table-condensed'> ");
		   buf.append("                                                   <tbody style='font-size: 13px;'> ");
		   buf.append("                                                       <tr> ");
		   buf.append("                                                           <td class='text-left'>" + MessageUtils.getMessage("lbl.area.m2.resum") + "</td> ");
		   buf.append("                                                            <td class='text-right'>"+ imovel.getArea().intValue() +"m<sup>2</sup></td> ");
		   buf.append("                                                        </tr> ");
		   buf.append("                                                        <tr> ");
		   buf.append("                                                           <td class='text-left'> "+ MessageUtils.getMessage("lbl.vagas.garagem.resum") +" </td> ");
		   buf.append("                                                            <td class='text-right'>" + imovel.getQuantGaragem() + " vaga(s)</td> ");
		   buf.append("                                                        </tr> ");		
		   buf.append("                                                        <tr> ");
		   buf.append("                                                           <td class='text-left'> "+ MessageUtils.getMessage("lbl.banheiros") +" </td> ");
		   buf.append("                                                            <td class='text-right'>" + imovel.getQuantBanheiro() + "</td> ");
		   buf.append("                                                        </tr> ");
		   buf.append("                                                        <tr> ");
		   buf.append("                                                           <td class='text-left'> "+ MessageUtils.getMessage("lbl.quartos.dormitorios.resum") + "</td> ");
		   buf.append("                                                            <td class='text-right'>"+ imovel.getQuantQuartos() +"</td> ");
		   buf.append("                                                        </tr> ");
		   buf.append("                                                        <tr> ");
		   buf.append("                                                           <td class='text-left'> "+  MessageUtils.getMessage("lbl.suites")  +" </td> ");
		   buf.append("                                                            <td class='text-right'>"+  imovel.getQuantSuites() + "</td> ");
		   buf.append("                                                        </tr> ");		   
		   buf.append("                                                    </tbody> ");
		   buf.append("                                                </table> ");
		   buf.append("                                                <br> ");
		   
		   if ( isInteressado.equals("N") && imovel.getUsuario().getId().longValue() != user.getId().longValue()) {
			   buf.append("                                                <a href='#a' id='idMeInteressei_" + imovel.getId() + "' onClick='adicionarInteresse("+ imovel.getId() + ")' style='font-size:x-large; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-star-o'> </i> <font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;'>" + MessageUtils.getMessage("lbl.me.interessei") + "  &nbsp;&nbsp; </a> ");
		   }
		   else  if ( isInteressado.equals("S")) {		   
			   buf.append("                                                <a href='#a' id='idNovoInteressado_" + imovel.getId() + "' onClick='retirarInteresse("+ imovel.getId() + ")' style='font-size:x-large; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-star'> </i> <font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;'>" + MessageUtils.getMessage("lbl.interessado") + "  &nbsp;&nbsp; </a> ");
		   } 		   
		   
		   buf.append("                                                <a href='#a' id='idNovoMeInteressei_" + imovel.getId() + "' onClick='adicionarInteresse("+ imovel.getId() + ")' style='font-size:x-large; color: rgb(99, 110, 123);display: none;' class='meta-action'><i class='fa fa-star-o'> </i> <font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;'>" + MessageUtils.getMessage("lbl.me.interessei") + "  &nbsp;&nbsp; </a> ");		   
		   buf.append("                                                <a href='#a' id='idInteressado_" + imovel.getId() + "' onClick='retirarInteresse("+ imovel.getId() + ")' style='font-size:x-large; color: rgb(99, 110, 123);display: none;' class='meta-action'><i class='fa fa-star'> </i> <font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;'>" + MessageUtils.getMessage("lbl.interessado") + "  &nbsp;&nbsp; </a> ");
		   
		   buf.append("                                                <a href='#a'  onClick='adicionarComparativo("+ imovel.getId() + ")' style='font-size:x-large; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-eye'> </i> <font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom:  22px;'>" + MessageUtils.getMessage("lbl.title.link.comparar") + "  &nbsp;&nbsp; </a> ");		   						
		   
		   buf.append("                                            </div> ");
		   buf.append("                                        </div> ");
		   buf.append("                                    </div> ");
		   buf.append("                            </div> ");
		   buf.append("                    </div> ");
		   buf.append("            </div> ");
		   buf.append("      </div> ");
		   buf.append("   </div> ");
		   buf.append("</div> ");

		   return buf.toString();
		}	
	
	public String carregarTimeLineUsuario(Usuario usuario, UsuarioForm user) {
		
		this.carregaAlgunsDetalhesUsuario(usuario);
		String  tipoContato  = contatoService.checarTipoContato(usuario.getId(), user.getId());
		String  isSeguindo   = seguidorService.checarUsuarioEstaSeguindo(user.getId(), usuario.getId());
			
		StringBuffer buf = new StringBuffer("");
		buf.append("	  <div class='timeline-item last-timeline'> ");
		buf.append("          <div class='timeline-badge'> ");
		buf.append("                  <a href='"+  context.getContextPath() + "/usuario/detalhesUsuario/"+ usuario.getId()  + "' >");
	    buf.append("				  		<img class='timeline-badge-userpic' src='data:image/jpeg;base64," + usuario.getImagemArquivo() + "' style='width: 90px; height: 90px; ' /> &nbsp; ");
	    buf.append("				  </a> ");                                                          
	    buf.append("      	  </div> ");
	    buf.append("      <div class='timeline-body'> ");    
	    buf.append("		  <div class='timeline-body-arrow'> </div> ");
	    buf.append("          <div class='timeline-body-head'> ");
	    buf.append("              <div class='timeline-body-head-caption'> ");
	    buf.append("                  <a href='"+  context.getContextPath() + "/usuario/detalhesUsuario/"+ usuario.getId()  +"' class='timeline-body-title font-blue-madison'> "+ usuario.getNome()  +" </a> ");
	    buf.append("                      <span class='timeline-body-time font-grey-cascade' style='font-style: italic;' >" + MessageUtils.getMessage("lbl.usuario.voce.conhece") + "</span> ");
	    buf.append("               </div> ");
	    buf.append("               <div class='timeline-body-head-actions'> ");
	    buf.append("                  <div class='btn-group'> ");		                                                            
	    buf.append("               </div> ");
	    buf.append("          </div> ");
	    buf.append("      </div> ");	    
	    buf.append("      <div class='timeline-body-content'> ");                                                    
	    buf.append("	 <p align='left' > ");
	    buf.append("	    <br> ");
	    buf.append("     	<strong> " + MessageUtils.getMessage("lbl.sobre.mim") + " : </strong> <br> "); 
	    buf.append("        	 <font size='2'> " + usuario.getDescSobreMim() +  " </font> ");
	    buf.append("     </p> ");                             
	    buf.append("     <div class='media'> ");     
	    buf.append("     <div class='media-body'> ");                                           
	    buf.append("          <span class='label pull-right' style='background-color: #9d2428; font-size: 12px; margin-top: 25px; '>" + usuario.getPerfilFmt() + "</span></br> ");
	    buf.append("			   <h4 class='media-heading' style='margin-bottom:20px;'><a href='"+  context.getContextPath() + "/usuario/detalhesUsuario/"+ usuario.getId()  +"' style='color : #9d2428;'>"+ usuario.getNome()  +"</a></h4> ");
	    buf.append("			   <h5 class='media-heading' style='margin-bottom:12px;'><i class='fa fa-map-marker'></i> "+ usuario.getUf() + " - "+ usuario.getCidade() + "</h1> ");                                 
	    buf.append("			   <div class='col-md-5' > ");  	                                                
	    buf.append("					<div class='media-body' > ");
	    buf.append("						 <em class='text-xs text-muted'> <font style='font-size:13px; font-style: normal;'>" + MessageUtils.getMessage("lbl.data.cadastro.usuario") + " : </font><span class='text-success'><font style='font-size:11px; font-style: normal;'> " + DateUtil.formataData(usuario.getDataCadastro()) + "</font></span></em> ");
	    buf.append("					</div> ");                                                 
	    buf.append("			    <br/> <br/> <br/> "); 											                                          
	    buf.append("			   </div> ");                                 
	    buf.append("		  <div class='col-md-7'> ");
	    buf.append("			    <table class='table table-condensed'> ");
	    buf.append("                      <tbody style='font-size: 13px;'> ");
	    buf.append("                        <tr> ");    
	    buf.append("                            <td class='text-left'>" + MessageUtils.getMessage("lbl.total.imoveis") + "</td> ");
	    buf.append("                            <td class='text-right'> " + usuario.getQuantTotalImoveis() + " </td> ");
	    buf.append("                        </tr> ");
	    buf.append("                        <tr> ");
	    buf.append("                            <td class='text-left'>" + MessageUtils.getMessage("lbl.total.contato") + "</td> ");
	    buf.append("                            <td class='text-right'> " + usuario.getQuantTotalContatos() + "</td> ");
	    buf.append("                        </tr> ");
	    buf.append("                        <tr> ");
	    buf.append("                            <td class='text-left'>" + MessageUtils.getMessage("lbl.total.seguidores") + "</td> ");
	    buf.append("                            <td class='text-right'> " + usuario.getQuantTotalSeguidores() + " </td> ");
	    buf.append("                        </tr> ");	    
	    buf.append("                        <tr> ");
	    buf.append("                            <td class='text-left'>" + MessageUtils.getMessage("lbl.total.recomendacoes") + "</td> ");
	    buf.append("                            <td class='text-right'> " + usuario.getQuantTotalRecomendacoes() + " </td> ");
	    buf.append("                        </tr> ");
	    buf.append("                      </tbody> ");
	    buf.append("                </table> ");
	    buf.append("                <br> ");	    	
	    
	    buf.append("    <a href='" + context.getContextPath() + "/imovel/visualizarImoveisPerfilUsuario/"+ usuario.getId()  +  "' style='font-size:x-large; color: rgb(99, 110, 123);' class='meta-action' ><i class='fa fa-home pull-left' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'>" + MessageUtils.getMessage("lbl.visualizar.imoveis.perfil.usuario") + "</font> </i>  </a> ");
	    
	    if ( user.getId().longValue() != usuario.getId().longValue()){
	    	
	    	if ( tipoContato.equals("S") || tipoContato.equals("C") || usuario.getHabilitaRecebeSeguidor().equals("S")){
	    		if ( isSeguindo.equals("S")){
	    			buf.append("    <a href='#a' id='idCancelarSeguidor' onclick='cancelarSeguirUsuario("+ usuario.getId() +")' style='font-size:x-large; color: rgb(99, 110, 123); ' class='meta-action'><i class='fa fa-outdent pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.link.cancelar.seguir.usuario") + " </font> &nbsp; &nbsp; </i> </a> ");
	    			buf.append("    <a href='#a' id='idIniciarSeguidor' onclick='iniciarSeguirUsuario("+ usuario.getId() +")' style='font-size:x-large; color: rgb(99, 110, 123); display: none;' class='meta-action'><i class='fa fa-list-ul pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.link.iniciar.seguir.usuario") + " </font> &nbsp; &nbsp; </i> </a> ");
	    		}
	    		else {
	    			buf.append("    <a href='#a' id='idIniciarSeguidor' onclick='iniciarSeguirUsuario("+ usuario.getId() +")' style='font-size:x-large; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-list-ul pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.link.iniciar.seguir.usuario") + " </font> &nbsp; &nbsp; </i> </a> ");
	    			buf.append("    <a href='#a' id='idCancelarSeguidor' onclick='cancelarSeguirUsuario("+ usuario.getId() +")' style='font-size:x-large; color: rgb(99, 110, 123); display: none;' class='meta-action'><i class='fa fa-outdent pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.link.cancelar.seguir.usuario") + " </font> &nbsp; &nbsp; </i> </a> ");
	    		}
	    	}
	    	
	    	if ( tipoContato.equals("S")){	    		
	    		buf.append("    <a href='#a' id='idCancelarContato' onclick='prepararModalCancelarContato("+ usuario.getId() +")' style='font-size:x-large; color: rgb(99, 110, 123); ' class='meta-action'><i class='fa fa-user-times pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.canceler.contato") + " </font> &nbsp; &nbsp; </i> </a> ");	    		
	    		buf.append("    <a href='#a' id='idEnviarConvite' onclick='enviarConvite("+ usuario.getId() +")' style='font-size:x-large; display: none; ' class='meta-action'><i class='fa fa-user-times pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.enviar.convite") + " </font> &nbsp; &nbsp; </i> </a> ");	    		
	    		buf.append("    <a href='#a' id='idCancelarConvite' onclick='prepararModalCancelarConvite("+ usuario.getId() +")' style='font-size:x-large; display: none; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-user-times pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.canceler.enviar.convite") + " </font> &nbsp; &nbsp; </i> </a> ");
	    	}
	    	else if ( tipoContato.equals("C")){
	    		buf.append("    <a href='#a' id='idCancelarConvite' onclick='prepararModalCancelarConvite("+ usuario.getId() +")' style='font-size:x-large; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-user-times pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.canceler.enviar.convite") + " </font> &nbsp; &nbsp; </i> </a> ");
	    		buf.append("    <a href='#a' id='idEnviarConvite' onclick='enviarConvite("+ usuario.getId() +")' style='font-size:x-large; display: none; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-user-times pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.enviar.convite") + " </font> &nbsp; &nbsp; </i> </a> ");
	    	}
	    	else if ( tipoContato.equals("N")){
	    		buf.append("    <a href='#a' id='idEnviarConvite' onclick='enviarConvite("+ usuario.getId() +")' style='font-size:x-large; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-user-times pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.enviar.convite") + " </font> &nbsp; &nbsp; </i> </a> ");
	    		buf.append("    <a href='#a' id='idCancelarConvite' onclick='prepararModalCancelarConvite("+ usuario.getId() +")' style='font-size:x-large; display: none; color: rgb(99, 110, 123);' class='meta-action'><i class='fa fa-user-times pull-right' style='color:gray'><font style='color: rgb(99, 110, 123); font-size: 12px; margin-bottom: 22px;'> " + MessageUtils.getMessage("lbl.canceler.enviar.convite") + " </font> &nbsp; &nbsp; </i> </a> ");
	    	}
	    }
	    
	    buf.append("           </div> ");
	    buf.append("     </div> ");
	    buf.append("   </div> ");
	    buf.append("  </div> ");
	    buf.append(" </div> ");
	    buf.append("</div> "); 
		
		return buf.toString();
	}
	
	private void carregaAlgunsDetalhesUsuario(Usuario usuario) {
		usuario.setQuantTotalImoveis(imovelService.checarQuantMeusImoveis(usuario.getId()));
		usuario.setQuantTotalContatos(contatoService.checarTotalContatosPorUsuarioPorStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
		usuario.setQuantTotalSeguidores(seguidorService.checarQuantidadeSeguidores(usuario.getId()));
		usuario.setQuantTotalRecomendacoes(recomendacaoService.checarQuantidadeTotalRecomendacaoRecebidaPorStatus(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo()));		
	}


	public String carregarTimeLinePreferenciaLocalidade(Preferencialocalidade pref) {
		
		StringBuffer buf = new StringBuffer("");
		buf.append("	  <div class='timeline-item last-timeline'> ");
		buf.append("          <div class='timeline-badge'> ");
		buf.append("                  <a href='"+  context.getContextPath() + "/usuario/detalhesUsuario/"+ pref.getUsuario().getId()  + "' >");
	    buf.append("				  		<img class='timeline-badge-userpic' src='data:image/jpeg;base64," + pref.getUsuario().getImagemArquivo() + "' style='width: 90px; height: 90px; ' /> &nbsp; ");
	    buf.append("				  </a> ");                                                          
	    buf.append("      	  </div> ");
	    buf.append("      <div class='timeline-body'> ");    
	    buf.append("		  <div class='timeline-body-arrow'> </div> ");
	    buf.append("          <div class='timeline-body-head'> ");
	    buf.append("              <div class='timeline-body-head-caption'> ");
	    buf.append("                  <a href='"+  context.getContextPath() + "/usuario/detalhesUsuario/"+ pref.getUsuario().getId()  +"' class='timeline-body-title font-blue-madison'> "+ pref.getUsuario().getNome()  +" </a> ");
	    buf.append("                      <span class='timeline-body-time font-grey-cascade' style='font-style: italic;' >" + MessageUtils.getMessage("lbl.preferencia.localidade.timeline") + "</span> ");
	    buf.append("               </div> ");
	    buf.append("               <div class='timeline-body-head-actions'> ");
	    buf.append("                  <div class='btn-group'> ");		                                                            
	    buf.append("               </div> ");
	    buf.append("          </div> ");
	    buf.append("      </div> ");	    
	    buf.append("     <div class='timeline-body-content'> "); 
	    buf.append("     <div class='media'> ");     
	    buf.append("     <div class='media-body'> "); 
	    buf.append("		  <div class='col-md-13'> ");
	    buf.append("			    <table class='table table-striped'> ");
	    buf.append("                      <tbody style='font-size: 15px;'> ");
	    buf.append("                        <tr> ");    
	    buf.append("                            <td class='text-left'> <strong>" + MessageUtils.getMessage("lbl.tipo.imovel") + "</strong></td> ");
	    buf.append("                            <td class='text-left'> <strong>" + MessageUtils.getMessage("lbl.acao.imovel") + "</strong></td> ");
	    if ( ! StringUtils.isEmpty(pref.getPerfilImovel() ) )
	    	buf.append("                            <td class='text-left'> <strong>" + MessageUtils.getMessage("lbl.buscar.imovel.status.imovel") + "</strong></td> ");	    
	    buf.append("                            <td class='text-left'> <strong>" + MessageUtils.getMessage("lbl.estado") + "</strong></td> ");
	    if ( pref.getIdCidade() > 0 )
	    	buf.append("                            <td class='text-left'><strong>" + MessageUtils.getMessage("lbl.cidade") + "</strong></td> ");
	    if ( pref.getIdBairro() > 0 )
	    	buf.append("                            <td class='text-left'><strong>" + MessageUtils.getMessage("lbl.bairro") + "</strong></td> ");
	    buf.append("                        </tr> ");
	    
	    buf.append("                        <tr> "); 
	    buf.append("                            <td class='text-left' style='font-size: 12px;'>" + pref.getTipoImovelFmt() + "</td> ");
	    buf.append("                            <td class='text-left' style='font-size: 12px;'>" + pref.getAcaoFmt() + "</td> ");
	    if ( ! StringUtils.isEmpty(pref.getPerfilImovel() ) )	 
		    buf.append("                            <td class='text-left' style='font-size: 12px;'>" + pref.getPerfilImovelFmt() + "</td> ");
	    buf.append("                            	<td class='text-left' style='font-size: 12px;'>" + pref.getSiglaEstado() + "</td> ");
	    if ( pref.getIdCidade() > 0 )
	    	buf.append("                            <td class='text-left' style='font-size: 12px;'>" + pref.getNomeCidade() + "</td> ");	
	    if ( pref.getIdBairro() > 0 )
	    	buf.append("                            <td class='text-left' style='font-size: 12px;'>" + pref.getNomeBairro() + "</td> ");	
	    buf.append("                        </tr> ");	    	 	                                                       
	    buf.append("                      </tbody> ");
	    buf.append("                </table> ");
	    buf.append("           </div> ");
	    buf.append("     </div> ");
	    buf.append("   </div> ");
	    buf.append("  </div> ");
	    buf.append(" </div> ");
	    buf.append("</div> "); 
		
		return buf.toString();
	}

	
	
	@Override
	public boolean validarEnviarSenhaTemporarioEsqueceuSenha(UsuarioForm form, BindingResult result) {
	
		boolean filtroValido = true;
		
		if ( StringUtils.isEmpty(form.getEmailEsqueceu())) {
			result.rejectValue("emailEsqueceu", "msg.erro.campo.obrigatorio");
			filtroValido = false;
		}
		else {
			if (! JsfUtil.isEmail(form.getEmailEsqueceu())){
				result.rejectValue("emailEsqueceu", "msg.erro.email.invalido");
				filtroValido = false;
			}
			else if (dao.findUsuarioByCampo(form, "emailEsqueceuSenha") == null) {
				result.rejectValue("emailEsqueceu", "msg.erro.email.inexistente.esqueceu.senha");
				filtroValido = false;		
			}
		}
		
		if ( StringUtils.isEmpty(form.getCpfCnpjEsqueceuSenha())) {
			result.rejectValue("cpfCnpjEsqueceuSenha", "msg.erro.campo.obrigatorio");
			filtroValido = false;
		}
		else {
			if ( ! JsfUtil.isValidoCNPJ(form.getCpfCnpjEsqueceuSenha()) && ! JsfUtil.isValidoCPF(form.getCpfCnpjEsqueceuSenha())){
				result.rejectValue("cpfCnpjEsqueceuSenha", "msg.erro.cpf.cnpj.invalido.esqueceu.senha");
				filtroValido = false;
			}
			else if (( dao.findUsuarioByCampo(form, "cnpjEsqueceuSenha") == null) && (dao.findUsuarioByCampo(form, "cpfEsqueceuSenha") == null)) {
				result.rejectValue("cpfCnpjEsqueceuSenha", "msg.erro.cpf.cnpj.invalido.esqueceu.senha");
				filtroValido = false;
			}
		}	
		
		if ( filtroValido ){
			if ( dao.findUsuarioByCampo(form, "emailCpfCnpjEsqueceuSenha") == null ){
				result.rejectValue("cpfCnpjEsqueceuSenha", "msg.erro.cpf.cnpj.email.inexistente.esqueceu.senha");
				filtroValido = false;
			}
		}
		
		return filtroValido;	
	}
	
	
	@Override
	public void enviarSenhaTemporarioEsqueceuSenha(UsuarioForm form) throws EmailException {
		String novaSenhaTemporaria = PasswordGenerator.getRandomPassword();
		if (! StringUtils.isEmpty(novaSenhaTemporaria)){
			Usuario usuario = dao.findUsuarioByCampo(form, "emailCpfCnpjEsqueceuSenha");
			usuario.setPassword(md5(novaSenhaTemporaria));
			usuario.setStatusUsuario("senhaTemporaria");
			dao.save(usuario);
			
			EnviaEmailHtml enviaEmail = new EnviaEmailHtml();
	        enviaEmail.setSubject(MessageUtils.getMessage("msg.email.subject.esqueceu.senha"));
	        enviaEmail.setTo(form.getEmailEsqueceu());
	        enviaEmail.setTexto(MessageUtils.getMessage("msg.email.texto.esqueceu.senha") + ": " + novaSenhaTemporaria);		            	
	        enviaEmail.enviaEmail(enviaEmail.getEmail());
		}
		else 
			throw new EmailException(MessageUtils.getMessage("msg.erro.enviar.nova.senha.temporaria"));
				
	}


	@Override
	public boolean validarConfirmaEnviarSenhaTemporarioEsqueceuSenha(UsuarioForm form, BindingResult result) {
		
		boolean filtroValido = true;
		
		if ( StringUtils.isEmpty(form.getEmailEsqueceu())) {
			result.rejectValue("emailEsqueceu", "msg.erro.campo.obrigatorio");
			filtroValido = false;
		}
		else {
			if (! JsfUtil.isEmail(form.getEmailEsqueceu())){
				result.rejectValue("emailEsqueceu", "msg.erro.email.invalido");
				filtroValido = false;
			}
			else if (dao.findUsuarioByCampo(form, "emailEsqueceuSenha") == null) {
				result.rejectValue("emailEsqueceu", "msg.erro.email.inexistente.esqueceu.senha");
				filtroValido = false;		
			}
		}
		
		if ( StringUtils.isEmpty(form.getSenhaTemporariaEsqueceu())) {
			result.rejectValue("senhaTemporariaEsqueceu", "msg.erro.campo.obrigatorio");
			filtroValido = false;
		}
		else {
			if ( dao.findUsuarioByCampo(form, "emailSenhaTemporariaEsqueceuSenha") == null ){
				result.rejectValue("senhaTemporariaEsqueceu", "msg.erro.email.senha.temporaria.usuario.inexistente");
				filtroValido = false;
			}
		}
		
		boolean isNovaSenhaConfirmaSenhaVazio = false;
		if ( StringUtils.isEmpty(form.getNovaSenhaEsqueceu())) {
			result.rejectValue("novaSenhaEsqueceu", "msg.erro.campo.obrigatorio");
			filtroValido = false;
			isNovaSenhaConfirmaSenhaVazio = true;
		}
		
		if ( StringUtils.isEmpty(form.getConfirmarNovaSenhaEsqueceu())) {
			result.rejectValue("confirmarNovaSenhaEsqueceu", "msg.erro.campo.obrigatorio");
			filtroValido = false;
			isNovaSenhaConfirmaSenhaVazio = true;
		}
		
		if (! isNovaSenhaConfirmaSenhaVazio){
			if (! form.getNovaSenhaEsqueceu().equals(form.getConfirmarNovaSenhaEsqueceu())){
				result.rejectValue("confirmarNovaSenhaEsqueceu", "msg.erro.nova.senha.confirma.senha.esquece");
				filtroValido = false;
			}
		}

		return filtroValido;
	}


	@Override
	@Transactional
	public void confirmarSenhaTemporarioEsqueceuSenha(UsuarioForm form) {
		Usuario usuario = dao.findUsuarioByCampo(form, "emailSenhaTemporariaEsqueceuSenha");
		usuario.setPassword(md5(form.getConfirmarNovaSenhaEsqueceu()));
		usuario.setStatusUsuario(StatusUsuarioEnum.LIBERADO.getRotulo());
		dao.save(usuario);
	}


	@Override
	public boolean validarEditarSenha(UsuarioForm form, BindingResult result) {

		boolean filtroValido = true;
		
		boolean isVazio = false;
		if ( StringUtils.isEmpty(form.getPassword())) {
			result.rejectValue("password", "msg.erro.campo.obrigatorio");
			filtroValido = false;
			isVazio = true;
		}
		
		if ( StringUtils.isEmpty(form.getConfirmaPassword())) {
			result.rejectValue("confirmaPassword", "msg.erro.campo.obrigatorio");
			filtroValido = false;
			isVazio = true;
		}
		
		if ( !isVazio ){
			
			if ( !org.apache.commons.lang3.StringUtils.isAlphanumeric(form.getPassword())){				
				result.rejectValue("password", "msg.erro.senha.caractere.alfanumerico");
				filtroValido = false;
			}
			
			if ( ! org.apache.commons.lang3.StringUtils.isAlphanumeric(form.getConfirmaPassword())){				
				result.rejectValue("confirmaPassword", "msg.erro.senha.caractere.alfanumerico");
				filtroValido = false;
			}
			
			if ( form.getPassword().length() < 6 ){
				result.rejectValue("password", "msg.erro.senha.tamanho.minimo");
				filtroValido = false;
			}
			
			if ( form.getConfirmaPassword().length() < 6 ){
				result.rejectValue("confirmaPassword", "msg.erro.senha.tamanho.minimo");
				filtroValido = false;
			}			
			
			if (! form.getPassword().equals(form.getConfirmaPassword())){
				result.rejectValue("confirmaPassword", "msg.erro.nova.senha.confirma.senha.esquece");
				filtroValido = false;
			}					
		}	
		
		return filtroValido;
	}


	@Override
	@Transactional
	public void editarSenha(Long idUsuario, UsuarioForm form) {
		Usuario usuario = dao.findUsuario(idUsuario);
        usuario.setPassword(md5(form.getPassword()));
        dao.save(usuario);
	}


	@Override
	public boolean validarIndicarAmigo(UsuarioForm form, BindingResult result) {
		boolean filtroValido = true;
		
		if ( StringUtils.isEmpty(form.getEmailIndicaAmigos())) {
			result.rejectValue("emailIndicaAmigos", "msg.erro.campo.obrigatorio");
			filtroValido = false;
		}
		else {
			if (! JsfUtil.isEmail(form.getEmailIndicaAmigos())){
				result.rejectValue("emailIndicaAmigos", "msg.erro.email.invalido");
				filtroValido = false;
			}		
		}
		
		return filtroValido;
	}


	@Override
	public void enviarIndicarAmigo(UsuarioForm form) throws EmailException {
		EnviaEmailHtml enviaEmail = new EnviaEmailHtml();
        enviaEmail.setSubject(MessageUtils.getMessage("msg.email.subject.indicar.amigo"));
        enviaEmail.setTo(form.getEmailIndicaAmigos()); 
        enviaEmail.setTexto(MessageUtils.getMessage("msg.email.texto.indicar.amigo") + ": " + MessageUtils.getMessage("msg.email.texto.indicar.amigo.link"));		            	
        enviaEmail.enviaEmail(enviaEmail.getEmail());		
	}


	@Override
	public List<Usuario> relatorioUsuariosImoveisMaisVisualizados(RelatorioForm form) {
		
		 List<Usuario> listaUsuarioFinal = new ArrayList<Usuario>();        
	        List listaUsuario = dao.findUsuariosImoveisMaisVisualizados(form);        
	        if ( ! CollectionUtils.isEmpty(listaUsuario) ){
	            Usuario usuario = null;
	            Object[] obj = null;
	            for (Iterator iter = listaUsuario.iterator();iter.hasNext();){
	                obj = (Object[]) iter.next();
	                usuario = recuperarUsuarioPorId(Long.parseLong(obj[0].toString()));                
	                usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
	                listaUsuarioFinal.add(usuario);
	            }
	        }
	        return listaUsuarioFinal;
	}


	@Override
	public List<Usuario> relatorioUsuariosImoveisMaisFavoritos(	RelatorioForm form) {
		List<Usuario> listaUsuarioFinal = new ArrayList<Usuario>();        
        List listaUsuario = dao.findUsuariosImoveisMaisFavoritos(form);        
        if ( ! CollectionUtils.isEmpty(listaUsuario) ){
            Usuario usuario = null;
            Object[] obj = null;
            for (Iterator iter = listaUsuario.iterator();iter.hasNext();){
                obj = (Object[]) iter.next();
                usuario = recuperarUsuarioPorId(Long.parseLong(obj[0].toString()));                
                usuario.setQuantImovelFavoritos(Integer.parseInt(obj[1].toString()));
                listaUsuarioFinal.add(usuario);
            }
        }
        return listaUsuarioFinal;
	}


	@Override
	public List<Usuario> relatorioUsuariosImoveisMaisPropostas(RelatorioForm form) {
		List<Usuario> listaUsuarioFinal = new ArrayList<Usuario>();        
        List listaUsuario = dao.findUsuariosImoveisMaisPropostas(form);        
        if ( ! CollectionUtils.isEmpty(listaUsuario) ){
            Usuario usuario = null;
            Object[] obj = null;
            for (Iterator iter = listaUsuario.iterator();iter.hasNext();){
                obj = (Object[]) iter.next();
                usuario = recuperarUsuarioPorId(Long.parseLong(obj[0].toString()));                
                usuario.setQuantImovelFavoritos(Integer.parseInt(obj[1].toString()));
                listaUsuarioFinal.add(usuario);
            }
        }
        return listaUsuarioFinal;
	}		
  

}