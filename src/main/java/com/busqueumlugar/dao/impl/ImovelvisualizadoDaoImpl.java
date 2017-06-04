package com.busqueumlugar.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelvisualizadoDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.TipoContatoOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelvisualizadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ImovelvisualizadoDaoImpl extends GenericDAOImpl<Imovelvisualizado, Long>  implements ImovelvisualizadoDao {	
		
	@Autowired
	private ContatoDao contatoDao;

	@Autowired
	private SeguidorDao seguidorDao;
	
	private static final Logger log = LoggerFactory.getLogger(ImovelvisualizadoDaoImpl.class);

	public ImovelvisualizadoDaoImpl() {
		super(Imovelvisualizado.class);
	}    

	
	public Imovelvisualizado findImovelvisitadoById(Long id) {
		return (Imovelvisualizado)session().createCriteria(Imovelvisualizado.class)
				.add(Restrictions.eq("id", id)).uniqueResult();	
	}

	@Override
	public List<Imovelvisualizado> findImoveisMaisVisitadosOrderDesc() {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.addOrder(Order.desc("dataVisita"));
		return (List<Imovelvisualizado>)crit.list();
	}

	@Override
	public List<Imovelvisualizado> filterImovelVisitadoHoje(DateUtil dtHoje, ImovelForm imovelForm) {
		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		
		if ( imovelForm.getIdEstado() > 0 ) {
			Criteria critImovel = crit.createCriteria("imovel");	
			critImovel.add(Restrictions.eq("idEstado", imovelForm.getIdEstado()));
			
			if ( imovelForm.getIdCidade() > 0 )
				critImovel.add(Restrictions.eq("idCidade", imovelForm.getIdCidade()));    
	        
	        if ( imovelForm.getIdBairro() > 0 )
	        	critImovel.add(Restrictions.eq("idBairro", imovelForm.getIdBairro()));
		}	
        
        crit.add(Restrictions.eq("dataVisita", dtHoje.toSqlDate()));        
        return (List<Imovelvisualizado>) crit.list();
	}

	@Override
	public List relatorioImovelVisitadoPeriodo(RelatorioForm form) {
		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		Criteria critUsuario = null;
		Criteria critImovel = null;	
		
		boolean isCritUsuarioExist = (!StringUtils.isNullOrEmpty(form.getSexo())) || 
									 (!StringUtils.isNullOrEmpty(form.getFaixaSalarial())) ||
									 (!StringUtils.isNullOrEmpty(form.getPerfilUsuario()));
		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(!StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos"));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstado() > 0 ) {
				 critImovel.add(Restrictions.eq("idEstado", form.getIdEstado()));
				 
				 if ( form.getIdCidade() > 0 )
					 critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
			        
			     if ( form.getIdBairro() > 0 )
			    	 critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
			 }	          
			
	        if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));

	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        	critImovel.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
		}
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoFiltroContato()) && ! form.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())){		
			List listaIds = null;
			if (form.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){
				listaIds = contatoDao.filterListaIds(form);
			}
			else if (form.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){
				listaIds = seguidorDao.filterListaIdsUsuariosSeguindo(form);
			}
			else if (form.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUIDORES.getRotulo())){
				listaIds = seguidorDao.filterListaIdsUsuariosSeguidores(form);
			}
			
			crit.createCriteria("usuario").add(Restrictions.in("id", listaIds));
		}
		else { // vai pegar todos os usuarios da aplicacao
			
			if (isCritUsuarioExist) {
				critUsuario = crit.createCriteria("usuario");
				
				if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
		        	critUsuario.add(Restrictions.eq("sexo", form.getSexo()));         
					
		        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) )
		        	critUsuario.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
		        
		        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
		        	critUsuario.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 
			}
		}

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataVisita", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataVisita", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();		
	}
	
	@Override
	public List relatorioImovelVisitadoPeriodo(AdministracaoForm form) {       
        
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		Criteria critUsuario = null;
		Criteria critImovel = null;	
		
		boolean isCritUsuarioExist = (!StringUtils.isNullOrEmpty(form.getSexo())) || 
									 (!StringUtils.isNullOrEmpty(form.getFaixaSalarial())) ||
									 (!StringUtils.isNullOrEmpty(form.getPerfilUsuario()));
		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(!StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos"));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstado() > 0 ) {
				 critImovel.add(Restrictions.eq("idEstado", form.getIdEstado()));
				 
				 if ( form.getIdCidade() > 0 )
					 critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
			        
			     if ( form.getIdBairro() > 0 )
			    	 critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
			 }	          
			
	        if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));

	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        	critImovel.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
		}

		if (isCritUsuarioExist) {
			critUsuario = crit.createCriteria("usuario");
			
			if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
	        	critUsuario.add(Restrictions.eq("sexo", form.getSexo()));         
				
	        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) )
	        	critUsuario.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
	        
	        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
	        	critUsuario.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 
		}	  

        ProjectionList projList = Projections.projectionList();	
        projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataVisita", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataVisita", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(10);
		return crit.list();		
	}

	@Override
	public List<Imovelvisualizado> filterImovelVisitadoPeriodo(DateUtil dataHoje, DateUtil dataSemana, ImovelForm imovelForm) {
		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		
		if ( imovelForm.getIdEstado() > 0 ) {
			Criteria critImovel = crit.createCriteria("imovel");	
			critImovel.add(Restrictions.eq("idEstado", imovelForm.getIdEstado()));
			
			if ( imovelForm.getIdCidade() > 0 )
				critImovel.add(Restrictions.eq("idCidade", imovelForm.getIdCidade()));    
	        
	        if ( imovelForm.getIdBairro() > 0 )
	        	critImovel.add(Restrictions.eq("idBairro", imovelForm.getIdBairro()));
		}		
        
        crit.add(Restrictions.ge("dataVisita", dataSemana.toSqlDate()));
        crit.add(Restrictions.le("dataVisita", dataHoje.toSqlDate()));        
        return (List<Imovelvisualizado>) crit.list();
	}

	@Override
	public List<Imovelvisualizado> findImoveisvisitadosByIdDonoImovel(	Long idUsuario, ImovelvisualizadoForm form) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));	
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();	
	}

	@Override
	public List<Imovelvisualizado> findImoveisvisitadosByIdDonoImovelNovos(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusVisita", StatusLeituraEnum.NOVO.getRotulo())).list();
		return crit.list();				
	}

	@Override
	public List<Imovelvisualizado> findImoveisVisitadosPorIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		return crit.list();
	}

	@Override
	public List filterImovelVisitadoPorDataPorQuantImoveis(Date dataInicio,	Date dataFim, int quantImoveis) {		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataVisita", dataInicio));
		crit.add(Restrictions.le("dataVisita", dataFim));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(quantImoveis);
		return crit.list();
	}

	@Override
	public List checarImoveisComMaisVisitasPeriodo(Date dataInicio,	Date dataFim, int quant) {		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataVisita", dataInicio));
		crit.add(Restrictions.le("dataVisita", dataFim));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(quant);
		return crit.list();
	}

	@Override
	public Imovelvisualizado findImovelVisitadoPorUsuarioPorImovel(Long idUsuario,	Long idImovel) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		return (Imovelvisualizado)crit.uniqueResult();
	}

	@Override
	public Imovelvisualizado findLastImoveloVisitadoByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("dataCadastro", this.recuperarDataVisitaMax(idImovel)));				
		return (Imovelvisualizado)crit.uniqueResult();  
	}


	private Date recuperarDataVisitaMax(Long idImovel) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		crit.setProjection(Projections.max("dataCadastro"));
		return (Date)crit.uniqueResult();
	}
	

	@Override
	public List<Imovelvisualizado> findImoveisvisitadosByIdUsuario(Long idUsuario, ImovelvisualizadoForm form) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();	
	}

	@Override
	public List findMeusImoveisVisitadosByIdUsuarioDistinct(Long idUsuario, ImovelvisualizadoForm form) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		ProjectionList projList = Projections.projectionList();	
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));
		 if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ){ // fazer a ordenacao ainda
			 Criteria critImovel= crit.createCriteria("imovel");	
	    	if (form.getOpcaoOrdenacao().equals("maiorValor"))
	    		critImovel.addOrder(Order.desc("valorImovel"));    
	    	else if (form.getOpcaoOrdenacao().equals("menorValor"))
	    		critImovel.addOrder(Order.asc("valorImovel"));        
	    	else if (form.getOpcaoOrdenacao().equals("maiorDataCadastro"))
	    		critImovel.addOrder(Order.desc("dataCadastro"));
	    	else if (form.getOpcaoOrdenacao().equals("menorDataCadastro"))
	    		critImovel.addOrder(Order.asc("dataCadastro"));
	    	else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente"))
	    		critImovel.addOrder(Order.asc("titulo"));
            else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente"))
            	critImovel.addOrder(Order.desc("titulo"));
	    }
		return crit.list();
	}
	
	@Override
	public List filterAgruparMeusImoveisVisitadosByIdUsuarioDistinct( Long idUsuario, ImovelvisualizadoForm form) {
		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));		
		Criteria critImovel = null;			

		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
									(form.getQuantQuartos() > 0 ) ||
									(form.getQuantGaragem() > 0 ) ||
									(form.getQuantSuites() > 0 )  ||
									(form.getQuantBanheiro() > 0 ) ||
									(! StringUtils.isNullOrEmpty(form.getValorMin()))||
									(! StringUtils.isNullOrEmpty(form.getValorMax()));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstado() > 0 ) {
				 critImovel.add(Restrictions.eq("idEstado", form.getIdEstado()));
				 
				 if ( form.getIdCidade() > 0 )
					 critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
			        
			     if ( form.getIdBairro() > 0 )
			    	 critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
			 }	          
			
	        if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        	critImovel.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
	        	        
	        if ( form.getQuantQuartos() > 0 ){
	            if (form.getQuantQuartos() >= 6 )
	            	critImovel.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
	            else
	            	critImovel.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
	        }
	        
	        if ( form.getQuantGaragem() > 0 ){
	           if (form.getQuantGaragem() >= 6 )
	        	   critImovel.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
	            else
	            	critImovel.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
	        }
	        
	        if ( form.getQuantSuites() > 0 ){
	           if (form.getQuantSuites() >= 6 )
	        	   critImovel.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
	            else
	            	critImovel.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
	        }
	        
	        if ( form.getQuantBanheiro() > 0 ){
	            if (form.getQuantBanheiro()  >= 6 )
	            	critImovel.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
	             else
	            	 critImovel.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
	         }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMin() ) ){
	        	critImovel.add(Restrictions.ge("valorImovel", AppUtil.formatarMoeda(form.getValorMin())));
	        }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMax()) ){
	        	critImovel.add(Restrictions.le("valorImovel", AppUtil.formatarMoeda(form.getValorMax())));
	        }
		}

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);			
		crit.addOrder(Order.desc("quant"));
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();	
	}

	@Override
	public long findQuantidadeImoveisVisitadosPorImovelPorStatus(Long idImovel, String statusLeitura) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);		
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));	
		if (! StringUtils.isNullOrEmpty(statusLeitura))
			crit.add(Restrictions.eq("statusVisita", StatusLeituraEnum.NOVO.getRotulo()));
		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.setMaxResults(1);
		return (long) crit.uniqueResult();
	}

	@Override
	public List findUsuariosMeusImoveisVisitadosByIdUsuarioDistinct(Long idUsuario, ImovelvisualizadoForm form) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuario.id"));
		projList.add(Projections.count("usuario.id").as("quant"));
		crit.setProjection(projList);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {
			
			Criteria critUsuario = crit.createCriteria("usuario");
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) && (! form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())) ){		
				List listaIds = null;
				if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){
					listaIds = contatoDao.filterListaIds(idUsuario, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguidores(idUsuario, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUIDORES.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguindo(idUsuario, form.getOpcaoPerfilContatoAgruparUsuarios());
				}				
				if (! CollectionUtils.isEmpty(listaIds))
					critUsuario.add(Restrictions.in("id", listaIds));
				else
					critUsuario.add(Restrictions.isNull("id"));					
			}
			else if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) &&
					(! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios()) &&
					(form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo()))) ){
				critUsuario.add(Restrictions.eq("perfil", form.getOpcaoPerfilContatoAgruparUsuarios()));
			}
			
			if (!StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())) {				
	        	if (form.getOpcaoOrdenacao().equals("maiorDataCadastrado")){
	        		critUsuario.addOrder(Order.desc("dataCadastro"));
	            }
	            else if (form.getOpcaoOrdenacao().equals("menorDataCadastrado")){
	            	critUsuario.addOrder(Order.asc("dataCadastro"));
	            } 
	            else if (form.getOpcaoOrdenacao().equals("nomeImovelCrescente")){
	            	critUsuario.addOrder(Order.asc("nome"));
	            }
	             else if (form.getOpcaoOrdenacao().equals("nomeImovelDeCrescente")){
	            	 critUsuario.addOrder(Order.desc("nome"));
	            }
		    }			
		}	
		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();
	}

	@Override
	public List findUsuariosImoveisVisitadosByIdUsuarioDistinct(Long idUsuarioSessao, ImovelvisualizadoForm form) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuarioDonoImovel.id"));
		projList.add(Projections.count("usuarioDonoImovel.id").as("quant"));
		crit.setProjection(projList);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuarioSessao));		
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {
			
			Criteria critUsuario = crit.createCriteria("usuarioDonoImovel");
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) && (! form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())) ){		
				List listaIds = null;
				if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){
					listaIds = contatoDao.filterListaIds(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguidores(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUIDORES.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguindo(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}				
				if (! CollectionUtils.isEmpty(listaIds))
					critUsuario.add(Restrictions.in("id", listaIds));
				else
					critUsuario.add(Restrictions.isNull("id"));					
			}
			else if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) &&
					(! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios()) &&
					(form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo()))) ){
				critUsuario.add(Restrictions.eq("perfil", form.getOpcaoPerfilContatoAgruparUsuarios()));
			}
			
			if (!StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())) {
				
	        	if (form.getOpcaoOrdenacao().equals("maiorDataCadastrado")){
	        		critUsuario.addOrder(Order.desc("dataCadastro"));
	            }
	            else if (form.getOpcaoOrdenacao().equals("menorDataCadastrado")){
	            	critUsuario.addOrder(Order.asc("dataCadastro"));
	            } 
	            else if (form.getOpcaoOrdenacao().equals("nomeImovelCrescente")){
	            	critUsuario.addOrder(Order.asc("nome"));
	            }
	             else if (form.getOpcaoOrdenacao().equals("nomeImovelDeCrescente")){
	            	 critUsuario.addOrder(Order.desc("nome"));
	            }
		    }					
		}		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();
	}

	@Override
	public List<Imovelvisualizado> findImoveisvisitadosByIdUsuarioByIdDonoImovel(Long idUsuarioSessao, Long idUsuario) {		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuarioSessao));		
		return crit.list();
	
	}

	@Override
	public List<Imovelvisualizado> filterUsuariosVisitantes(Long idUsuario, ImovelvisualizadoForm form) {		
		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		Criteria critImovel = null;
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));
		boolean isCritExist = (form.getIdEstado() > 0 ) || 
							  (! StringUtils.isNullOrEmpty(form.getTipoImovel())) || 
							  (! StringUtils.isNullOrEmpty(form.getAcao())) ||
							  (! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
							  (form.getQuantQuartos() > 0 ) ||
							  (form.getQuantGaragem() > 0 ) ||
							  (form.getQuantSuites() > 0 )  ||
							  (form.getQuantBanheiro() > 0 ) ||
							  (! StringUtils.isNullOrEmpty(form.getValorMin()))||
							  (! StringUtils.isNullOrEmpty(form.getValorMax()));
							
		if (isCritExist)
			critImovel = crit.createCriteria("imovel");
			
        if ( form.getIdEstado() > 0 ) {
        	critImovel.add(Restrictions.eq("idEstado", form.getIdEstado()));
        	
        	if ( form.getIdCidade() > 0 )
            	critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));
            
            if ( form.getIdBairro() > 0 )
            	critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
        }	        
   				
        if (! StringUtils.isNullOrEmpty(form.getAcao()))
        	critImovel.add(Restrictions.eq("acao", form.getAcao()));
  
        if (! StringUtils.isNullOrEmpty(form.getTipoImovel()))
        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
        
        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
        	critImovel.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 

        if ( form.getQuantQuartos() > 0 ){
            if (form.getQuantQuartos() >= 6 )
            	critImovel.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
            else
            	critImovel.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
        }
        
        if ( form.getQuantGaragem() > 0 ){
           if (form.getQuantGaragem() >= 6 )
        	   critImovel.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
            else
            	critImovel.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
        }
        
        if ( form.getQuantSuites() > 0 ){
           if (form.getQuantSuites() >= 6 )
        	   critImovel.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
            else
            	critImovel.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
        }
        
        if ( form.getQuantBanheiro() > 0 ){
            if (form.getQuantBanheiro()  >= 6 )
            	critImovel.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
             else
            	 critImovel.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
         }
        
        if (! StringUtils.isNullOrEmpty(form.getValorMin() ) ){
        	critImovel.add(Restrictions.ge("valorImovel", AppUtil.formatarMoeda(form.getValorMin())));
        }
        
        if (! StringUtils.isNullOrEmpty(form.getValorMax()) ){
        	critImovel.add(Restrictions.le("valorImovel", AppUtil.formatarMoeda(form.getValorMax())));
        }
        
        if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
       	 if (form.getOpcaoOrdenacao().equals("maiorValor")){
       		critImovel.addOrder(Order.desc("valorImovel")); 
	        }
	        else if (form.getOpcaoOrdenacao().equals("menorValor")){
	        	critImovel.addOrder(Order.asc("valorImovel"));  
	        }
	        else if (form.getOpcaoOrdenacao().equals("maiorDataVisita")){
	        	crit.addOrder(Order.desc("dataVisita")); 
	        }
	        else if (form.getOpcaoOrdenacao().equals("menorDataVisita")){
	        	crit.addOrder(Order.asc("dataVisita")); 
	        }
	        else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente")){
	        	critImovel.addOrder(Order.asc("titulo")); 
	        }
	         else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente")){
	        	 critImovel.addOrder(Order.desc("titulo")); 
	        } 
       }
      form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
	  if ( form.isVisible()){
		  crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
		  crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
		  form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
	  }           
      return(List<Imovelvisualizado>) crit.list();
	}

	@Override
	public List<Imovelvisualizado> filterImoveisVisitados(Long idUsuario, ImovelvisualizadoForm form) {
		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		Criteria critImovel = null;
		boolean isCritExist = (form.getIdEstado() > 0 ) || 
							  (! StringUtils.isNullOrEmpty(form.getTipoImovel())) || 
							  (! StringUtils.isNullOrEmpty(form.getAcao())) ||
							  (! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
							  (form.getQuantQuartos() > 0 ) ||
							  (form.getQuantGaragem() > 0 ) ||
							  (form.getQuantSuites() > 0 )  ||
							  (form.getQuantBanheiro() > 0 );		
		
		if (isCritExist)
			critImovel = crit.createCriteria("imovel");
		
        if (  form.getIdEstado() > 0 )
        	critImovel.add(Restrictions.eq("idEstado",  form.getIdEstado()));	        	
          
        if ( form.getIdCidade() > 0 )
        	critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));
        
        if ( form.getIdBairro() > 0 )
        	critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
   				
        if (! StringUtils.isNullOrEmpty(form.getAcao()))
        	critImovel.add(Restrictions.eq("acao", form.getAcao()));
  
        if (! StringUtils.isNullOrEmpty(form.getTipoImovel()))
        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel())); 
        
        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
        	critImovel.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 

        if ( form.getQuantQuartos() > 0 ){
            if (form.getQuantQuartos() >= 6 )
            	critImovel.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
            else
            	critImovel.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
        }
        
        if ( form.getQuantGaragem() > 0 ){
           if (form.getQuantGaragem() >= 6 )
        	   critImovel.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
            else
            	critImovel.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
        }
        
        if ( form.getQuantSuites() > 0 ){
           if (form.getQuantSuites() >= 6 )
        	   critImovel.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
            else
            	critImovel.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
        }
        
        if ( form.getQuantBanheiro() > 0 ){
            if (form.getQuantBanheiro()  >= 6 )
            	critImovel.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
             else
            	 critImovel.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
         }
        
        if (! StringUtils.isNullOrEmpty(form.getValorMin() ) ){
        	critImovel.add(Restrictions.ge("valorImovel", AppUtil.formatarMoeda(form.getValorMin())));
        }
        
        if (! StringUtils.isNullOrEmpty(form.getValorMax()) ){
        	critImovel.add(Restrictions.le("valorImovel", AppUtil.formatarMoeda(form.getValorMax())));
        }
        
        
        if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
          	if (form.getOpcaoOrdenacao().equals("maiorValor")){
          		critImovel.addOrder(Order.desc("valorImovel")); 
   	        }
   	        else if (form.getOpcaoOrdenacao().equals("menorValor")){
   	        	critImovel.addOrder(Order.asc("valorImovel"));  
   	        }
   	        else if (form.getOpcaoOrdenacao().equals("maiorDataVisita")){
   	        	crit.addOrder(Order.desc("dataVisita")); 
   	        }
   	        else if (form.getOpcaoOrdenacao().equals("menorDataVisita")){
   	        	crit.addOrder(Order.asc("dataVisita")); 
   	        }
   	        else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente")){
   	        	critImovel.addOrder(Order.asc("titulo")); 
   	        }
   	         else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente")){
   	        	 critImovel.addOrder(Order.desc("titulo")); 
   	        } 
        }
        
       form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
   		if ( form.isVisible()){
   			crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
   			crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
   			form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
   	   }   
       return(List<Imovelvisualizado>) crit.list();
	}
	
	public Imovelvisualizado findImovelVisitadoRandomByIdUsuario(Long idUsuario){
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.sqlRestriction("1=1 order by rand()"));
		crit.setMaxResults(1);		
		return (Imovelvisualizado) crit.uniqueResult();
	}


	@Override
	public long findQuantidadeVisitantesByIdUsuarioByStatus(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));
		if (!StringUtils.isNullOrEmpty(status))
			crit.add(Restrictions.eq("statusVisita", status));
		crit.setProjection(Projections.rowCount());	
		return (long) crit.uniqueResult();		
	}


	@Override
	public void updateStatusLeitura(Long idUsuario) {
		Session session = this.openSession();		
		session.beginTransaction();
		Query query = session.createSQLQuery("CALL atualizatStatusLeitura(:idUsuario, :tabela)")
							.addEntity(Usuario.class)
							.setParameter("idUsuario", idUsuario)
							.setParameter("tabela", "imovelvisitado");
		int rows = query.executeUpdate();
		session.close();		
	}
	

	@Override
	public Imovelvisualizado findImoveisVisitadosByIdUsuarioByIndex(Long idUsuario, int idIndex) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.setFirstResult(idIndex);
		crit.setMaxResults(1);		
		return (Imovelvisualizado) crit.uniqueResult();		
	}


	@Override
	public List findUsuariosImoveisVisitadosSemelhantes(Long idUsuario, ImovelForm form) {		
		
		boolean isCritExist = (form.getIdEstado() > 0 ) || 
				  (! StringUtils.isNullOrEmpty(form.getTipoImovel())) || 
				  (! StringUtils.isNullOrEmpty(form.getAcao())) ||
				  (! StringUtils.isNullOrEmpty(form.getPerfilImovel()));		

		if (isCritExist) {
			Criteria crit = session().createCriteria(Imovelvisualizado.class);
			crit.createCriteria("usuarioDonoImovel").add(Restrictions.ne("id", idUsuario));
			crit.createCriteria("usuario").add(Restrictions.ne("id", idUsuario));
			
			Criteria critImovel = crit.createCriteria("imovel");
			critImovel.add(Restrictions.ne("id",  form.getId()));	  
			
			if (  form.getIdEstado() > 0 )
				critImovel.add(Restrictions.eq("idEstado",  form.getIdEstado()));	        	
			
			if ( form.getIdCidade() > 0 )
				critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));
			
			if ( form.getIdBairro() > 0 )
				critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
			
			if (! StringUtils.isNullOrEmpty(form.getTipoImovel()))
				critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel())); 
			
			crit.addOrder(Order.desc("dataVisita"));
			crit.setMaxResults(10);				
			ProjectionList projList = Projections.projectionList();
		    projList.add(Projections.distinct(Projections.property("usuario.id")));			
			crit.setProjection(projList);		
			return crit.list();
		}	
		else	
			return null;
	}

}
