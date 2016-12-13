package com.busqueumlugar.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ImovelPropostasDao;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelPropostasForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelPropostasService;
import com.busqueumlugar.service.RecomendacaoService;
import com.busqueumlugar.service.SeguidorService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.EnviaEmailHtml;
import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

@Service
public class ImovelPropostasServiceImpl implements ImovelPropostasService {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelPropostasServiceImpl.class);
	
	@Autowired
	private ImovelPropostasDao dao;

	@Autowired
	private ImovelService imovelService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ImovelFavoritosService imovelFavoritosService;
	
	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private SeguidorService seguidorService;
	
	@Autowired
	private RecomendacaoService recomendacaoService;
		
	
	public ImovelPropostas recuperarImovelImovelPropostasPorId(Long id) {
		return dao.findImovelPropostas(id);
	}

	@Override
	public List<Imovel> relatorioImoveisMaisPropostadosPorPeriodo(RelatorioForm frm) {

        List lista = dao.relatorioImovelMaisPropostadoPeriodo(frm);		
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        if (! CollectionUtils.isEmpty(lista)){
             Imovel imovel = null;       
             for (Iterator iter = lista.iterator();iter.hasNext();){                
                 Object[] obj = (Object[]) iter.next();
                 imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));                 
                 imovel.setQuantidade(Integer.parseInt(obj[1].toString()));
                 listaFinal.add(imovel);
             }
         }   
        return listaFinal;
	}

	@Override
	@Transactional
	public void cadastrarProposta(Long idImovel, ImovelPropostasForm imovelPropostasForm) {		
		ImovelPropostas imovelPropostas = new ImovelPropostas();
        BeanUtils.copyProperties(imovelPropostas, imovelPropostasForm);
        imovelPropostas.setDataCadastro(new Date()); 
        imovelPropostas.setUsuarioLancador(usuarioService.recuperarUsuarioPorId(imovelPropostasForm.getIdUsuarioLancador()));
        Imovel imovel = imovelService.recuperarImovelPorid(idImovel);
        imovelPropostas.setImovel(imovel);
        imovelPropostas.setUsuarioReceptor(imovel.getUsuario());        
        imovelPropostas.setDescricao(imovel.getDescricao());
        imovelPropostas.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        dao.save(imovelPropostas);		
	}
	
	@Override
	@Transactional
	public void cadastrarProposta(Long idImovel, Long idUsuario, String novaObs, BigDecimal valorProposta ) {
		
		Usuario usuario = usuarioService.recuperarUsuarioPorId(idUsuario);
		Imovel imovel = imovelService.recuperarImovelPorid(idImovel);
		
		ImovelPropostas imovelPropostas = new ImovelPropostas();
        imovelPropostas.setDataCadastro(new Date());
        imovelPropostas.setUsuarioLancador(usuario);
        imovelPropostas.setImovel(imovel);
        imovelPropostas.setUsuarioReceptor(imovel.getUsuario());        
        imovelPropostas.setValorProposta(valorProposta);
        imovelPropostas.setStatus(StatusLeituraEnum.NOVO.getRotulo());
        imovelPropostas.setObservacao(novaObs);
        dao.save(imovelPropostas);	
      
        /*try {
        	
        	// enviar email
            EnviaEmailHtml enviaEmail = new EnviaEmailHtml();
            enviaEmail.setSubject(MessageUtils.getMessage("msg.email.subject.imovel.proposta"));
            enviaEmail.setTo("israeldb27@gmail.com");
            enviaEmail.setTexto(MessageUtils.getMessage("msg.email.texto.imovel.proposta"));		            	
            enviaEmail.enviaEmail(enviaEmail.getEmail());
		} catch (Exception e) {		
			e.printStackTrace();
		}*/
	}
	
	@Override
	@Transactional
	public void excluirProposta(Long id){	
		ImovelPropostas imovelPropostas = dao.findImovelPropostas(id);
		dao.delete(imovelPropostas);
	}

	@Override
	public List<ImovelPropostas> recuperarPropostasLancadasPorUsuario(Long idUsuario, ImovelPropostasForm form) {
        return dao.findImoveisPropostasLancadasByIdUsuario(idUsuario, form);
	}

	@Override
	public List<ImovelPropostas> recuperarPropostasImovel(Long idImovel) {
		return  dao.findImovelPropostaByIdImovel(idImovel);
	}

	@Override
	public List<ImovelPropostas> recuperarPropostasImovelPorUsuario(Long idUsuario,	Long idImovel) {
		return dao.findImoveisPropostasLancadasByIdUsuarioByIdImovel(idUsuario, idImovel);
	}

	@Override
	public List<ImovelPropostas> listarMinhasPropostasSobreImovel(Long idUsuario, Long idImovel) {		
		return dao.findImoveisPropostasLancadasByIdUsuarioByIdImovel(idUsuario, idImovel);
	}

	@Override
	public List<ImovelPropostas> recuperarPropostasRecebidasPorUsuario(Long idUsuario, ImovelPropostasForm form) {		
        return dao.findImoveisPropostasRecebidasByIdUsuario(idUsuario, form);
	}

	@Override
	public int quantidadePropostasRecebidasPorUsuario(Long idUsuario, ImovelPropostasForm form) {		
        return AppUtil.recuperarQuantidadeLista(dao.findImoveisPropostasRecebidasByIdUsuario(idUsuario, form));
	}

	@Override
	public int checaQuantidadeOfertNova(Long idUsuario) {
		return AppUtil.recuperarQuantidadeLista(dao.findNovaImoveisPropostasRecebidasByIdUsuario(idUsuario));		
	}

	@Override
	public List<ImovelPropostas> recuperarImoveisPropostasNovas(List<ImovelPropostas> lista) {
		List<ImovelPropostas> listaFinal = new ArrayList<ImovelPropostas>();
        for (ImovelPropostas imovelPropostas : lista ){            
            if (imovelPropostas.getStatus().equals(StatusLeituraEnum.NOVO.getRotulo()))
                listaFinal.add(imovelPropostas);
        }        
        return listaFinal;
	}

	@Override
	public List<ImovelPropostas> recuperarImoveisPropostasNovas(Long idUsuario) {
		return dao.findNovaImoveisPropostasRecebidasByIdUsuario(idUsuario);        
	}

	@Override
	@Transactional
	public boolean atualizarStatusImovelProposta(ImovelPropostas imovelPropostas) {
		if (imovelPropostas.getStatus().equals(StatusLeituraEnum.NOVO.getRotulo()))               
            imovelPropostas.setStatus(StatusLeituraEnum.LIDO.getRotulo());                        
        else 
            imovelPropostas.setStatus(StatusLeituraEnum.NOVO.getRotulo());            
        
        dao.save(imovelPropostas);
        return true;
	}

	@Override
	public List<Imovel> checarImoveisMaisReceberamPropostasPorPeriodo(AdministracaoForm form) {
		List lista = dao.relatorioImovelMaisPropostadoPeriodo(form);
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        
        if ( ! CollectionUtils.isEmpty(lista) ){
            Imovel imovel = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));                
                listaFinal.add(imovel);
            }
        }        
        return listaFinal;
	}

	@Override
	public EmailImovel notificarLancamentoPropostas(Long idImovel) {
		
		Imovel imovel = imovelService.recuperarImovelPorid(idImovel);
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioDonoImovel = imovel.getUsuario();
        // assunto        
      //  email.setAssunto(getBundle().getString("msg.assunto.propostas.imoveis"));                                
        // para
        email.setTo(usuarioDonoImovel.getEmail());
        
        // Texto do Email
       /* texto.append(getBundle().getString("msg.inicio.corpo.email.propostas.imoveis") + "\n");        
        texto.append(getBundle().getString("msg.corpo.email.titulo.imovel") + imovel.getTitulo() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.tipo.imovel") + imovel.getTipoImovel() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.estado.imovel") + imovel.getEstado() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.cidade.imovel") + imovel.getCidade() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.bairro.imovel") + imovel.getBairro() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.data.proposta.imoveis") + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());      
        */
        email.setAcao("lancarPropostas");
        return email;
	}

	@Override
	public List<ImovelPropostas> ordenarImoveisPropostas(Long idUsuario,
													 	 ImovelPropostasForm form) { 	
		   List<ImovelPropostas> lista = null;
		   if ( form.getTipoLista().equals("propostasRecebidas")) 	   
			   lista = filterPropostasRecebidas(idUsuario, form);
		   else 
			   lista = filterPropostasEnviadas(idUsuario, form);
		   
	        return lista;
	}
	
	@Override
	public List<?> ordenarAgruparImoveisProposta(Long idUsuario, ImovelPropostasForm form) {

		if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){			
			return this.agruparUsuarios(idUsuario, form);			
		}
		else if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){			
			return this.agruparImoveis(idUsuario, form);
		} 		
		return null;
	}
	
	@Override
	public List<ImovelPropostas> agruparImoveis(List<ImovelPropostas> listaImoveisPropostasRecebidas) {
		
	  TreeSet<Long> set = new TreeSet();
       // agrupando por idImovel e eliminando duplicidades
       for (ImovelPropostas imovelPropostas : listaImoveisPropostasRecebidas){
           set.add(imovelPropostas.getImovel().getId());
       }
       
       List<ImovelPropostas> lista = new ArrayList<ImovelPropostas>();
       ImovelPropostas imovelPropostas = null;
       for(Long idImovel : set){
           imovelPropostas = recuperarUltimaPropostaPorIdImovel(idImovel);
           lista.add(imovelPropostas);
       }
       
       List<ImovelPropostas> listaFinal = new ArrayList<ImovelPropostas>(lista);
       return listaFinal;
	}

	@Override
	public ImovelPropostas recuperarUltimaPropostaPorIdImovel(Long idImovel) {		
        return dao.findLastImovelPropostaByIdImovel(idImovel);
	}

	@Override
	public String validarCadastroProposta(Long idImovel,ImovelPropostasForm imovelPropostasForm, UsuarioForm userSession) {
		
		 String msg = "";
	        
	        Imovel imovel = imovelService.recuperarImovelPorIdImovel(idImovel);
	        if ( imovel.getUsuario().getId().longValue() == userSession.getId().longValue())
	            msg = MessageUtils.getMessage("msg.erro.propostas.enviar.para.proprio.usuario");
	        
	        if ( msg.equals("")){
	            if ( imovel.getAtivado().equals("N"))	            	
	                msg = MessageUtils.getMessage("msg.erro.propostas.imovel.desativado");
	        }
	        
	        if ( msg.equals("")){
	            if ( imovelPropostasForm.getValorProposta().doubleValue() <= 0.0d )
	                msg = MessageUtils.getMessage("msg.erro.proposta.vazia");
	        }        
	        return msg;
	}
	
	@Override
	public String validarCadastroProposta(Long idImovel, String valorProposta,  UsuarioForm userSession) {
		
		 	String msg = "";	        
	        Imovel imovel = imovelService.recuperarImovelPorIdImovel(idImovel);
	        if ( imovel.getUsuario().getId().longValue() == userSession.getId().longValue())
	            msg = MessageUtils.getMessage("msg.erro.propostas.enviar.para.proprio.usuario");
	        
	        if ( msg.equals("")){
	            if ( imovel.getAtivado().equals("N"))	            	
	                msg = MessageUtils.getMessage("msg.erro.propostas.imovel.desativado");
	        }
	        
	        if ( msg.equals("")){
	        	try{
	        		double vl = Double.parseDouble(valorProposta);
	        		if ( vl <= 0.0d )
		                msg = MessageUtils.getMessage("msg.erro.proposta.vazia");
	        	}
	        	catch(NumberFormatException e ){
	        		msg = MessageUtils.getMessage("msg.erro.valor.proposta.formato.invalido");
	        	}	            
	        }        
	        return msg;
	}


	@Override
	public List<ImovelPropostas> filterPropostasRecebidas(Long idUsuario,  ImovelPropostasForm form) {
		return dao.filterPropostasRecebidas(idUsuario, form); 
	}

	@Override
	public List<ImovelPropostas> filterPropostasEnviadas(Long idUsuario, ImovelPropostasForm form) {		
		return dao.filterPropostasEnviadas(idUsuario, form);
	}
	
	@Override
	public List<Usuario> filtrarAgruparUsuarios(Long idUsuario, ImovelPropostasForm form) {
		return this.agruparUsuarios(idUsuario, form);
	}
	
	@Override
	public List<Imovel> filtrarAgruparImoveis(Long idUsuario, ImovelPropostasForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		if ( form.getTipoLista().equals("propostasRecebidas")){
			// lista dos IdImovel cujo usuario sessao tenha recebido pelo menos uma Proposta
			List lista = dao.filterFindImoveisPropostasRecebidasByIdUsuarioDistinct(idUsuario, form);
			Object[] obj = null;			
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();				
				imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));				
				imovel.setQuantPropostas(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovasPropostas(dao.findQuantidadeNovasPropostasRecebidas(Long.parseLong(obj[0].toString())));
				listaFinal.add(imovel);
			}
		}
		else if ( form.getTipoLista().equals("propostasLancadas")){
			// lista dos IdImovel cujo usuario sessao tenha enviado para algum imóvel do sistema
			List lista = dao.filterFindImoveisPropostasEnviadasByIdUsuarioDistinct(idUsuario,  form);
			Object[] obj = null;
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));				
				imovel.setQuantPropostas(Integer.parseInt(obj[1].toString()));				
				listaFinal.add(imovel);
			}
		}		
		return listaFinal;
	}


	@Override
	public List<Imovel> agruparImoveis(Long idUsuario, ImovelPropostasForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		if ( form.getTipoLista().equals("propostasRecebidas")){
			// lista dos IdImovel cujo usuario sessao tenha recebido pelo menos uma Proposta
			List lista = dao.findImoveisPropostasRecebidasByIdUsuarioDistinct(idUsuario, form);
			Object[] obj = null;			
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();				
				imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));				
				imovel.setQuantPropostas(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovasPropostas(dao.findQuantidadeNovasPropostasRecebidas(Long.parseLong(obj[0].toString())));
				listaFinal.add(imovel);
			}
		}
		else if ( form.getTipoLista().equals("propostasLancadas")){
			// lista dos IdImovel cujo usuario sessao tenha enviado para algum imóvel do sistema
			List lista = dao.findImoveisPropostasLancadasByIdUsuarioDistinct(idUsuario, form);
			Object[] obj = null;
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();
				imovel = imovelService.recuperarImovelPorid(Long.parseLong(obj[0].toString()));				
				imovel.setQuantPropostas(Integer.parseInt(obj[1].toString()));				
				listaFinal.add(imovel);
			}
		}		
		return listaFinal;
	}


	@Override
	public List<Usuario> agruparUsuarios(Long idUsuarioSessao, ImovelPropostasForm form) {
		List<Usuario> listaFinal = new ArrayList<Usuario>();
		if ( form.getTipoLista().equals("propostasRecebidas")){
			// lista dos IdUsuario que enviou pelo menos uma Proposta para o usuario sessao
			List lista = dao.findUsuariosImoveisPropostasRecebidasByIdUsuario(idUsuarioSessao, form);
			Object[] obj = null;			
			Usuario usuario = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next(); //[0] - usuarioRecep, [1] - usuarioEmissor, [2] - quantidade (count)				
				usuario = usuarioService.recuperarUsuarioPorId(Long.parseLong(obj[0].toString()));
				usuario.setQuantPropostas(Integer.parseInt(obj[1].toString())); // quantidade de Propostas que o usuário enviou para os imóveis do usuario sessao								
				listaFinal.add(usuario);
			}			
		}
		else {
			// lista dos IdUsuario na qual usuario sessao enviou pelo menos uma Proposta sobre o imovel dele
			List lista = dao.findUsuariosImoveisPropostasLancadasByIdUsuarioDistinct(idUsuarioSessao, form);
			Object[] obj = null;			
			Usuario usuario = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next(); //[0] - usuarioRecep,  [1] - quantidade (count)				
				usuario = usuarioService.recuperarUsuarioPorId(Long.parseLong(obj[0].toString()));
				usuario.setQuantPropostas(Integer.parseInt(obj[1].toString())); // quantidade de Propostas que o usuário enviou para os imóveis do usuario sessao
				usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
				usuario.setQuantTotalContatos(contatoService.checarTotalContatosPorUsuarioPorStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
				usuario.setQuantTotalRecomendacoes(recomendacaoService.checarQuantidadeTotalRecomendacaoRecebidaPorStatus(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo()));
				usuario.setQuantTotalImoveis(imovelService.checarQuantMeusImoveis(usuario.getId()));
				usuario.setQuantTotalSeguidores(seguidorService.checarQuantidadeSeguidores(usuario.getId()));				
				listaFinal.add(usuario);
			}
		}
		return listaFinal;
	}


	@Override
	public List<ImovelPropostas> recuperarUsuarioPropostasImovelUsuarioSessaoRecebidas(Long idUsuario, Long idUsuarioSessao) {		
		return dao.findImovelPropostasByIdUsuarioByIdUsuarioSessaoRecebidas(idUsuario, idUsuarioSessao);
	}
	

	@Override
	@Transactional
	public void atualizarStatusLeituraImoveisPropostas(Long idUsuario) {
		dao.updateStatusLeituraByIdUsuarioReceptor(idUsuario);
	}

	@Override
	public long checarQuantidadesPropostasRecebidasPorUsuarioPorStatus(	Long idUsuario, String status) {
		return dao.findQuantPropostasRecebidasByIdUsuarioByStatus(idUsuario, status);
	}	
}
