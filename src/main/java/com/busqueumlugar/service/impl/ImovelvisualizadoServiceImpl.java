package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ImovelvisualizadoDao;
import com.busqueumlugar.dao.RecomendacaoDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelvisualizadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.messaging.MessageSender;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.ImovelvisualizadoService;
import com.busqueumlugar.service.UsuarioService;
import com.busqueumlugar.util.DateUtil;

@Service
public class ImovelvisualizadoServiceImpl implements ImovelvisualizadoService{
	
	private static final Logger log = LoggerFactory.getLogger(ImovelvisualizadoServiceImpl.class);

	@Autowired
	private ImovelvisualizadoDao dao;
	
	@Autowired
	private ImovelDao imovelDao;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService; 
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ContatoDao contatoDao;
	
	@Autowired
	private SeguidorDao seguidorDao;
	
	@Autowired
	private RecomendacaoDao recomendacaoDao;
	
	@Autowired
	private  MessageSender messageSender;

	
	
	public Imovelvisualizado recuperarImovelvisitadoPorId(Long id) {
		return dao.findImovelvisitadoById(id);
	}

	public List<Imovel> relatorioImoveisMaisVisitadosPorPeriodo(RelatorioForm frm) {	
  		
        List lista = dao.relatorioImovelVisitadoPeriodo(frm);
            List<Imovel> listaFinal = new ArrayList<Imovel>();
            if (! CollectionUtils.isEmpty(lista)){
                Imovel imovel = null;       
                for (Iterator iter = lista.iterator();iter.hasNext();){                
                    Object[] obj = (Object[]) iter.next();
                    imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));                    
                    imovel.setQuantidade(Integer.parseInt(obj[1].toString()));
                    imovel.setImagemArquivo(imovelService.carregaFotoPrincipalImovel(imovel));
                    listaFinal.add(imovel);
                }
            }
        return listaFinal; 	
	}

	@Override
	public List<Imovel> relatorioImoveisMaisVisitadosPorPeriodo(AdministracaoForm frm){	
		
        List lista = dao.relatorioImovelVisitadoPeriodo(frm);		
            List<Imovel> listaFinal = new ArrayList<Imovel>();
             if ( lista != null && lista.size() > 0 ){
                Imovel imovel = null;       
                for (Iterator iter = lista.iterator();iter.hasNext();){                
                    Object[] obj = (Object[]) iter.next();
                    imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));                    
                    imovel.setQuantidade(Integer.parseInt(obj[1].toString()));
                    listaFinal.add(imovel);
                }
            }
                
        return listaFinal; 	
	}
	
	public List<Imovel> filtrarImovelMaisVisitado(String opcaoPeriodo, ImovelForm imovelForm) {
        
        DateUtil dtCorrente = new DateUtil();
        List<Imovelvisualizado> lista = null;
        if ( opcaoPeriodo != null) {
            if ( opcaoPeriodo.equals("hoje"))                
                lista = dao.filterImovelVisitadoHoje(dtCorrente, imovelForm);
            else if ( opcaoPeriodo.equals("semana")){
                DateUtil dtSemana = new DateUtil();
                dtSemana.add(Calendar.DAY_OF_MONTH, -7);
                lista = dao.filterImovelVisitadoPeriodo(dtCorrente, dtSemana, imovelForm);
            }                  
            else if ( opcaoPeriodo.equals("hoje")){
                DateUtil dtMes = new DateUtil();
                dtMes.add(Calendar.DAY_OF_MONTH, -30);
                lista = dao.filterImovelVisitadoPeriodo(dtCorrente, dtMes, imovelForm);
            }    
        }
        
        List<Imovel> listaFinal = new ArrayList<Imovel>();        
        for (Imovelvisualizado imovelVisit : lista){
            if ( ! listaFinal.contains(imovelVisit.getImovel())){
                listaFinal.add(imovelVisit.getImovel());
            }            
        }                
        return listaFinal;  
	}

	
	public List<Imovel> recuperarImoveisMaisVisitados() {		
		List<Imovelvisualizado> listaVisitados = dao.findImoveisMaisVisitadosOrderDesc();
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        for (Imovelvisualizado imovelvisitado : listaVisitados){
            listaFinal.add(imovelvisitado.getImovel());
        }        
        return listaFinal;
	}

	
	@Transactional
	public void adicionarVisitaImovel(Long idImovel, Long idUsuario) {
		Imovel imovel = imovelDao.findImovelById(idImovel);
		if (! imovel.getUsuario().getId().equals(idUsuario)){
			//checar se este imovel ja foi visitado anteriormente
	        Imovelvisualizado imovelVisitadoAntes = dao.findImovelVisitadoPorUsuarioPorImovel(idUsuario, idImovel);
	        if ( imovelVisitadoAntes == null ){
	            Imovelvisualizado imovelvisitado = new Imovelvisualizado();   
	            imovelvisitado.setImovel(imovel);
	            imovelvisitado.setUsuario(usuarioDao.findUsuario(idUsuario));
	            imovelvisitado.setDataVisita(new Date());
	            imovelvisitado.setUsuarioDonoImovel(imovel.getUsuario());
	            imovelvisitado.setStatusVisita(StatusLeituraEnum.NOVO.getRotulo());
	            dao.save(imovelvisitado);	     
	            
	        /*    try {
	            	
	            	//messageSender.sendMessage(imovel);
	            	
	            	// enviar email
		            EnviaEmailHtml enviaEmail = new EnviaEmailHtml();
		            enviaEmail.setSubject(MessageUtils.getMessage("msg.email.subject.imovel.visualizado"));
		            enviaEmail.setTo("israeldb27@gmail.com");
		            enviaEmail.setTexto(MessageUtils.getMessage("msg.email.texto.imovel.visualizado"));		            	
		            enviaEmail.enviaEmail(enviaEmail.getEmail());
				} catch (Exception e) {		
					e.printStackTrace();
				}*/
	        }
		}		 
		
	}

	
	public List<Imovelvisualizado> recuperarUsuariosVisitantesPorImovel(Long idUsuario, ImovelvisualizadoForm form) {
		List<Imovelvisualizado> lista = dao.findImoveisvisitadosByIdDonoImovel(idUsuario, form);
		List<Imovelvisualizado> listaFinal = new ArrayList<Imovelvisualizado>();
		for (Imovelvisualizado imovelvisualizado : lista){
			imovelvisualizado.getImovel().setImagemArquivo(imovelService.carregaFotoPrincipalImovel(imovelvisualizado.getImovel()));
			imovelvisualizado.getUsuario().setImagemArquivo(usuarioService.carregaFotoPrincipalUsuario(imovelvisualizado.getUsuario()));
			listaFinal.add(imovelvisualizado);			
		}
		
		return listaFinal;
	}

	
	public List<Imovelvisualizado> carregaListaNovosUsuariosVisitantes(List<Imovelvisualizado> lista) {		
		List<Imovelvisualizado> listaFinal = new ArrayList<Imovelvisualizado>();
        for (Imovelvisualizado imovelvisitado : lista){             
            if ( imovelvisitado.getStatusVisita().equals(StatusLeituraEnum.NOVO.getRotulo()))
                listaFinal.add(imovelvisitado);
        }
        return listaFinal;
	}

	
	@Transactional
	public boolean atualizarStatus(Imovelvisualizado imovelVisitado) {		
		if ( imovelVisitado.getStatusVisita().equals(StatusLeituraEnum.NOVO.getRotulo()))
            imovelVisitado.setStatusVisita(StatusLeituraEnum.LIDO.getRotulo());
        else 
            imovelVisitado.setStatusVisita(StatusLeituraEnum.NOVO.getRotulo());

        dao.save(imovelVisitado);
        return true;
	}

	
	public List<Imovelvisualizado> recuperarUsuariosVisitantesPorImovelNovos(Long idUsuario) {		
		return dao.findImoveisvisitadosByIdDonoImovelNovos(idUsuario);
	}
	
	public List<Imovelvisualizado> recuperarUsuariosVisitantesPorIdImovel(Long idImovel) {
		
		List<Imovelvisualizado> lista = dao.findImoveisVisitadosPorIdImovel(idImovel);
		List<Imovelvisualizado> listaFinal = new ArrayList<Imovelvisualizado>();
		for (Imovelvisualizado imovelvisualizado : lista){			
			imovelvisualizado.getUsuario().setImagemArquivo(usuarioService.carregaFotoPrincipalUsuario(imovelvisualizado.getUsuario()));
			listaFinal.add(imovelvisualizado);			
		}		
		return listaFinal;
	}

	
	public List<Imovel> checarImoveisMaisAcessadosPorPeriodo(Date dataInicio, Date dataFim, int quantImoveis) {		
		List lista = dao.filterImovelVisitadoPorDataPorQuantImoveis(dataInicio, dataFim, quantImoveis);
        List<Imovel> listaFinal = new ArrayList<Imovel>();
        if ( !CollectionUtils.isEmpty(lista) ){
            Imovel imovel = null;       
            for (Iterator iter = lista.iterator();iter.hasNext();){                
                Object[] obj = (Object[]) iter.next();
                imovel = imovelDao.findImovelById(new Long(obj[0].toString()));                
                listaFinal.add(imovel);
            }
        }        
        return listaFinal;
	}

	
	public List<Imovel> checarImoveisMaisReceberamVisitasPorPeriodo(Date dataInicio, Date dataFim, int quant) {		
		List lista = dao.checarImoveisComMaisVisitasPeriodo(dataInicio, dataFim, quant);
        List<Imovel> listaFinal = new ArrayList<Imovel>();        
        if (! CollectionUtils.isEmpty(lista) ){
            Imovel imovel = null;
            for (Iterator iter = lista.iterator();iter.hasNext();){
                Object[] obj = (Object[]) iter.next();
                imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
                listaFinal.add(imovel);
            }
        }
        return listaFinal;
	}


	public EmailImovel notificarVisitaImovel(Long idImovel) {		
		Imovel imovel = imovelDao.findImovelById(idImovel);
        EmailImovel email = new EmailImovel();        
        StringBuilder texto = new StringBuilder(); 
                
        Usuario usuarioDonoImovel = imovel.getUsuario();
        // assunto        
        //email.setAssunto(getBundle().getString("msg.assunto.visita.imoveis"));                                        
        // para
        email.setTo(usuarioDonoImovel.getEmail());
        
        // Texto do Email        
      /*  texto.append(getBundle().getString("msg.inicio.corpo.email.visita.imoveis") + "\n");        
        texto.append(getBundle().getString("msg.corpo.email.titulo.imovel") + imovel.getTitulo() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.tipo.imovel") + imovel.getTipoImovel() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.estado.imovel") + imovel.getEstado() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.cidade.imovel") + imovel.getCidade() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.bairro.imovel") + imovel.getBairro() + "\n");
        texto.append(getBundle().getString("msg.corpo.email.data.visita.imoveis") + new DateUtil().getStrDate() + "\n");
        email.setTextoEmail(texto.toString());       */       
        email.setAcao("visitaImovel");        
        return email;
	}

	
	public Imovelvisualizado recuperarUltimaVisitaPorIdImovel(Long idImovel) {
        return dao.findLastImoveloVisitadoByIdImovel(idImovel);
	}

	
	public List<Imovelvisualizado> agruparImoveis(List<Imovelvisualizado> listaUsuariosVisitantes) {		
	   TreeSet<Long> set = new TreeSet();
       // agrupando por idImovel e eliminando duplicidades
        
       for (Imovelvisualizado imovel : listaUsuariosVisitantes ) {
           set.add(imovel.getImovel().getId());
       }
       
       List<Imovelvisualizado> lista = new ArrayList<Imovelvisualizado>();
       Imovelvisualizado imovelvisitado = null;
       for(Long idImovel : set){
           lista.add(recuperarUltimoUsuarioVisitantePorIdImovel(idImovel));
       } 
              
       List<Imovelvisualizado> listaFinal = new ArrayList<Imovelvisualizado>(lista);
       return listaFinal;
	}

	
	public Imovelvisualizado recuperarUltimoUsuarioVisitantePorIdImovel(Long idImovel) {
        return  dao.findLastImoveloVisitadoByIdImovel(idImovel);
	}


	@Override
	public List<Imovelvisualizado> recuperarImoveisVisitadorPorIdUsuario(Long idUsuario, ImovelvisualizadoForm form) {
		List<Imovelvisualizado> lista = dao.findImoveisvisitadosByIdUsuario(idUsuario, form);
		List<Imovelvisualizado> listaFinal = new ArrayList<Imovelvisualizado>();
		for (Imovelvisualizado imovelvisualizado : lista){
			imovelvisualizado.getImovel().setImagemArquivo(imovelService.carregaFotoPrincipalImovel(imovelvisualizado.getImovel()));
			listaFinal.add(imovelvisualizado);			
		}		
		return listaFinal;
	}


	@Override
	public List<Imovelvisualizado> ordenarImoveis(Long idUsuario,	ImovelvisualizadoForm form, String tipoLista) {

		List<Imovelvisualizado> lista = null;
		if ( tipoLista.equals("meusImoveisVisitados"))
			lista = filtrarUsuariosVisitantes(idUsuario, form);
		else
			lista = filtrarImoveisVisitados(idUsuario, form);
		
		return lista;
	}
	
	
	@Override
	public List<?> ordenarAgruparImoveisVisitados(Long idUsuario, ImovelvisualizadoForm form) {

		if ( form.getOpcaoVisualizacao().equals("agruparUsuarios")){			
			return this.agruparUsuarios(idUsuario, form);			
		}
		else if ( form.getOpcaoVisualizacao().equals("agruparImoveis")){			
			return this.agruparImoveis(idUsuario, form);
		} 		
		return null;
	}
	
	@Override
	public List<Imovel> agruparImoveis(Long idUsuario, ImovelvisualizadoForm form) {
		List<Imovel> listaFinal = new ArrayList<Imovel>();
		if ( form.getTipoLista().equals("meusImoveisVisitados")){
			// lista dos IdImovel do usuario sessao na qual tenha recebido pelo menos uma visita
			List lista = dao.findMeusImoveisVisitadosByIdUsuarioDistinct(idUsuario, form);
			Object[] obj = null;			
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();				
				imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
				imovel.setQuantImoveisVisitados(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovosImoveisVisitados(dao.findQuantidadeImoveisVisitadosPorImovelPorStatus(Long.parseLong(obj[0].toString()), StatusLeituraEnum.NOVO.getRotulo()));				
				imovel.setImagemArquivo(imovelService.carregaFotoPrincipalImovel(imovel));
				listaFinal.add(imovel);
			}
		}				
		return listaFinal;
	}
	
	@Override
	public List<Imovel> filtrarAgruparImoveis(Long idUsuario, ImovelvisualizadoForm form) {

		List<Imovel> listaFinal = new ArrayList<Imovel>();
		if ( form.getTipoLista().equals("meusImoveisVisitados")){
			// lista dos IdImovel do usuario sessao na qual tenha recebido pelo menos uma visita
			List lista = dao.filterAgruparMeusImoveisVisitadosByIdUsuarioDistinct(idUsuario, form);
			Object[] obj = null;			
			Imovel imovel = null;
			for (Iterator iter = lista.iterator();iter.hasNext();){
				obj = (Object[]) iter.next();				
				imovel = imovelDao.findImovelById(Long.parseLong(obj[0].toString()));
				imovel.setQuantImoveisVisitados(Integer.parseInt(obj[1].toString()));
				imovel.setQuantNovosImoveisVisitados(dao.findQuantidadeImoveisVisitadosPorImovelPorStatus(Long.parseLong(obj[0].toString()), StatusLeituraEnum.NOVO.getRotulo()));				
				imovel.setImagemArquivo(imovelService.carregaFotoPrincipalImovel(imovel));
				listaFinal.add(imovel);
			}
		}				
		return listaFinal;
	}


	@Override
	public List<Usuario> agruparUsuarios(Long idUsuarioSessao, ImovelvisualizadoForm form) {

		List<Usuario> listaFinal = new ArrayList<Usuario>();
		List lista = null;
		if ( form.getTipoLista().equals("meusImoveisVisitados")){
			// lista dos idUsuario que visitaram pelo menos um imovel do usuario sessao 
			lista = dao.findUsuariosMeusImoveisVisitadosByIdUsuarioDistinct(idUsuarioSessao, form);			
		}
		else {
			// lista dos idUsuario na qual o usuario sessao visitou um de seus imoveis 
			lista = dao.findUsuariosImoveisVisitadosByIdUsuarioDistinct(idUsuarioSessao, form);			
		}
		
		Object[] obj = null;
		Long idUsuario = null;
		Usuario usuario = null;
		for (Iterator iter = lista.iterator();iter.hasNext();){
			obj = (Object[]) iter.next();
			idUsuario = Long.parseLong(obj[0].toString());
			usuario = usuarioDao.findUsuario(idUsuario);
			usuario.setQuantImovelVisitado(Integer.parseInt(obj[1].toString()));
			usuario.setQuantTotalContatos(contatoDao.findQuantidadeTotalContatosByIdUsuarioByStatus(usuario.getId(), ContatoStatusEnum.OK.getRotulo()));
			usuario.setQuantTotalRecomendacoes(recomendacaoDao.findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(usuario.getId(), RecomendacaoStatusEnum.ACEITO.getRotulo(), null));
			usuario.setQuantTotalImoveis(imovelDao.findQuantMeusImoveis(usuario.getId()));
			usuario.setQuantTotalSeguidores(seguidorDao.findQuantSeguidoresByIdUsuarioSeguido(usuario.getId()));
			usuario.setImagemArquivo(usuarioService.carregaFotoPrincipalUsuario(usuario));
			listaFinal.add(usuario);
		}
		
		return listaFinal;
	}


	@Override
	public List<Imovelvisualizado> recuperarImoveisVisitadosPorUsuario(Long idUsuarioSessao, Long idUsuario) {
		List<Imovelvisualizado> lista = dao.findImoveisvisitadosByIdUsuarioByIdDonoImovel(idUsuarioSessao, idUsuario);
		List<Imovelvisualizado> listaFinal = new ArrayList<Imovelvisualizado>();
		for (Imovelvisualizado imovelVisualizado : lista){
			imovelVisualizado.getImovel().setImagemArquivo(imovelService.carregaFotoPrincipalImovel(imovelVisualizado.getImovel()));
			listaFinal.add(imovelVisualizado);
		}
		
		return listaFinal;
	}


	@Override
	public List<Imovelvisualizado> filtrarUsuariosVisitantes(Long idUsuario, ImovelvisualizadoForm form) {
		
		List<Imovelvisualizado> lista = dao.filterUsuariosVisitantes(idUsuario, form );
		List<Imovelvisualizado> listaFinal = new ArrayList<Imovelvisualizado>();
		for (Imovelvisualizado imovelvisualizado : lista){
			imovelvisualizado.getImovel().setImagemArquivo(imovelService.carregaFotoPrincipalImovel(imovelvisualizado.getImovel()));
			imovelvisualizado.getUsuario().setImagemArquivo(usuarioService.carregaFotoPrincipalUsuario(imovelvisualizado.getUsuario()));
			listaFinal.add(imovelvisualizado);			
		}
		
		return listaFinal;
	}


	@Override
	public List<Imovelvisualizado> filtrarImoveisVisitados(Long idUsuario, ImovelvisualizadoForm form) {
		List<Imovelvisualizado> lista = dao.filterImoveisVisitados(idUsuario, form );
		List<Imovelvisualizado> listaFinal = new ArrayList<Imovelvisualizado>();
		for (Imovelvisualizado imovelvisualizado : lista){
			imovelvisualizado.getImovel().setImagemArquivo(imovelService.carregaFotoPrincipalImovel(imovelvisualizado.getImovel()));
			imovelvisualizado.getUsuario().setImagemArquivo(usuarioService.carregaFotoPrincipalUsuario(imovelvisualizado.getUsuario()));
			listaFinal.add(imovelvisualizado);			
		}
		
		return listaFinal;
	}

	@Override
	public List<Usuario> filtrarAgruparUsuarios(Long idUsuario, ImovelvisualizadoForm form) {
		return this.agruparUsuarios(idUsuario, form);
	}

	@Override
	@Transactional
	public void atualizarStatusLeituraMeusImoveisVisitados(Long idUsuario) {		
		dao.updateStatusLeitura(idUsuario);
	}

	@Override
	public long checarQuantidadeVisitantesPorUsuarioPorStatus(Long idUsuario, String status) {
		return dao.findQuantidadeVisitantesByIdUsuarioByStatus(idUsuario, status);
	}

	@Override
	public long checarQuantidadeImoveisVisualizadosPorImovel(Long idImovel, String statusLeitura) {
		return dao.findQuantidadeImoveisVisitadosPorImovelPorStatus(idImovel, statusLeitura);
	}

}
