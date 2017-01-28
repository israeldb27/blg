package com.busqueumlugar.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.IntermediacaoDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.enumerador.StatusImovelCompartilhadoEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.TipoContatoOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.IntermediacaoForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Intermediacao;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;


@Repository
public class IntermediacaoDaoImpl extends GenericDAOImpl<Intermediacao, Long>  implements IntermediacaoDao {
	
	private static final Logger log = LoggerFactory.getLogger(IntermediacaoDaoImpl.class);
	
	@Autowired
	private ContatoDao contatoDao;

	@Autowired
	private SeguidorDao seguidorDao;

	public IntermediacaoDaoImpl() {
		super(Intermediacao.class);
	}

	@Override
	public Intermediacao findIntermediacaoById(Long id) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.add(Restrictions.eq("id", id));
		return (Intermediacao)crit.uniqueResult();		
	}

	@Override
	public List<Intermediacao> findIntermediacaoByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		return crit.list();	
	}

	@Override
	public List<Intermediacao> findIntermediacaoByIdImovelByStatus(Long idImovel, String status) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("status", status));		
		return crit.list();	
	}

	@Override
	public List<Intermediacao> findIntermediacaoByIdUsuarioSolicitanteByStatus(Long idUsuarioSolicitante, String status, IntermediacaoForm form) {		
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.createCriteria("usuarioSolicitante").add(Restrictions.eq("id", idUsuarioSolicitante));
		crit.add(Restrictions.eq("status", status));		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
		return crit.list();
	}

	@Override
	public List<Intermediacao> findIntermediacaoByIdDonoImovelByStatus(Long idDonoImovel, String status, IntermediacaoForm form) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idDonoImovel));
		crit.add(Restrictions.eq("status", status));		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
		return crit.list();
	}


	@Override
	public Intermediacao findIntermediacaoByIdUsuarioSolicitanteByIdImovel(Long idUsuario, Long idImovel) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.createCriteria("usuarioSolicitante").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		return (Intermediacao)crit.uniqueResult();
	}


	@Override
	public List checarImoveisComMaisSolIntermediacaoPeriodo(AdministracaoForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);
		Criteria critImovel = null;
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

        ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));		
		projList.add(Projections.groupProperty("imovel.id"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(10);
		return crit.list();
	}


	@Override
	public List findIntermediacaoBySolAceitasDistintas( Long idUsuario, String status, IntermediacaoForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);

		Criterion critUsuarioDonoImovel 	= Restrictions.eq("usuarioDonoImovel.id", idUsuario) ;
		Criterion critStatus1 				= Restrictions.eq("status", status) ;
		LogicalExpression andExp1 			= Restrictions.and(critUsuarioDonoImovel,critStatus1);
				
		Criterion critUsuarioSolicitante    = Restrictions.eq("usuarioSolicitante.id", idUsuario);
		Criterion critStatus2 				= Restrictions.eq("status", status) ;
		LogicalExpression andExp2 			= Restrictions.and(critUsuarioSolicitante,critStatus2);
		
		LogicalExpression orExp = Restrictions.or(andExp1, andExp2);
		crit.add(orExp);
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.distinct(Projections.property("imovel.id")));
		crit.setProjection(projList);
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
        return crit.list();
	}


	@Override
	public List<Imovel> findIntermediacaoAceitosPorUsuarioSolicitantePorPerfil(Long idPerfil, String status, PerfilForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);
		Criterion critUsuarioDonoImovel 	= Restrictions.eq("usuarioDonoImovel.id", idPerfil) ;
		Criterion critUsuarioSolicitante    = Restrictions.eq("usuarioSolicitante.id", idPerfil);
		LogicalExpression orExp = Restrictions.or(critUsuarioDonoImovel,critUsuarioSolicitante);		
		crit.add(orExp);
		crit.add(Restrictions.eq("status", status));
		
		Criteria critImovel = null;
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(!StringUtils.isNullOrEmpty(form.getStatusNegociacao())) ||
									(form.getQuantQuartos() >= 0) ||
									(form.getQuantGaragem() >= 0) ||
									(form.getQuantSuites()  >= 0) ||									
									(form.getValorImovelMax()  >= 0) ||
									(form.getValorImovelMin()  >= 0) ||									
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
	        
	        
	        if ( form.getQuantQuartos() >= 0 ){
	            if (form.getQuantQuartos() >= 6 )
	            	critImovel.add(Restrictions.ge("quantQuartos", form.getQuantQuartos()));	                
	            else
	            	critImovel.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));	                                    
	        }
	        
	        if ( form.getQuantGaragem() >= 0 ){
	            if (form.getQuantGaragem() >= 6 )
	            	critImovel.add(Restrictions.ge("quantGaragem", form.getQuantGaragem()));	                
	            else
	            	critImovel.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));	                                    
	        }
	        
	        if ( form.getQuantSuites() >= 0 ){
	            if (form.getQuantSuites() >= 6 )
	            	critImovel.add(Restrictions.ge("quantSuites", form.getQuantSuites()));	                
	            else
	            	critImovel.add(Restrictions.eq("quantSuites", form.getQuantSuites()));	                                    
	        }
	        
	        if ( form.getValorImovelMin() >= 0 ){
	            critImovel.add(Restrictions.ge("valorImovel", form.getValorImovelMin()));
	        }
	        
	        if ( form.getValorImovelMax() >= 0 ){
	            critImovel.add(Restrictions.le("valorImovel", form.getValorImovelMax()));
	        }
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getStatusNegociacao()) && ! form.getStatusNegociacao().equals("-1") )
	        	critImovel.add(Restrictions.eq("negociacaoImovel", form.getStatusNegociacao())); 
		}

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("imovel"));
		crit.setProjection(projList);		
		return crit.list();
	}


	@Override
	public Intermediacao findLastImovelParceriaMeusImoveisByIdImovel(Long idImovel) {		
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("dataSolicitacao", this.recuperarMaxDataSolicitacaoImovel(idImovel)));
        return (Intermediacao)crit.uniqueResult();       
	}

	private Date recuperarMaxDataSolicitacaoImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.max("dataSolicitacao").as("dataSolicitacao"));
		crit.setProjection(projList);		
		crit.setMaxResults(1);
		return (Date)crit.uniqueResult();		
	}

	@Override
	public List<Intermediacao> findAllIntermediacaoByIdUsuarioByStatus(	Long idUsuario, String status) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.add(Restrictions.eq("status", status));
		
		Criterion critUsuarioDonoImovel 	= Restrictions.eq("usuarioDonoImovel.id", idUsuario) ;		
		Criterion critUsuarioSolicitante    = Restrictions.eq("usuarioSolicitante.id", idUsuario);
		
		LogicalExpression orExp = Restrictions.or(critUsuarioDonoImovel, critUsuarioSolicitante);
		crit.add(orExp);		          
        return (List<Intermediacao>)crit.list();
	}


	@Override
	public List<Intermediacao> filterSolIntermediacao(Long idDonoImovel, IntermediacaoForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.add(Restrictions.eq("status", StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idDonoImovel));
		
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
		
		 if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ){ // fazer a ordenacao ainda
			 
			 boolean isOrdenaCamposImoveis = form.getOpcaoOrdenacao().equals("maiorValor") ||
					 						 form.getOpcaoOrdenacao().equals("menorValor") || 
					 						 form.getOpcaoOrdenacao().equals("maiorDataCadastro") || 
					 						 form.getOpcaoOrdenacao().equals("menorDataCadastro") ||
					 						 form.getOpcaoOrdenacao().equals("tituloImovelCrescente") ||
					 						 form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente");
			 if ( isOrdenaCamposImoveis ){
				 if ( critImovel == null ) {
					 critImovel = crit.createCriteria("imovel");
				 }	 
			 }
			 	
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
            else if (form.getOpcaoOrdenacao().equals("maiorDataSolicitacao"))
            	crit.addOrder(Order.desc("dataSolicitacao"));
            else if (form.getOpcaoOrdenacao().equals("menorDataSolicitacao"))
            	crit.addOrder(Order.asc("dataSolicitacao"));
            else if (form.getOpcaoOrdenacao().equals("maiorDataAceitacao"))
            	crit.addOrder(Order.desc("dataAceitacao"));
            else if (form.getOpcaoOrdenacao().equals("menorDataAceitacao"))
            	crit.addOrder(Order.asc("dataAceitacao"));	    	
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
	public List<Intermediacao> filterMinhasSolIntermediacao(Long idUsuarioSolicitante, IntermediacaoForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.add(Restrictions.eq("status", StatusImovelCompartilhadoEnum.SOLICITADO.getRotulo()));
		crit.createCriteria("usuarioSolicitante").add(Restrictions.eq("id", idUsuarioSolicitante));
		
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

		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ){ // fazer a ordenacao ainda
			 
			 boolean isOrdenaCamposImoveis = form.getOpcaoOrdenacao().equals("maiorValor") ||
					 						 form.getOpcaoOrdenacao().equals("menorValor") || 
					 						 form.getOpcaoOrdenacao().equals("maiorDataCadastro") || 
					 						 form.getOpcaoOrdenacao().equals("menorDataCadastro") ||
					 						 form.getOpcaoOrdenacao().equals("tituloImovelCrescente") ||
					 						 form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente");
			 if ( isOrdenaCamposImoveis ){
				 if ( critImovel == null ) {
					 critImovel = crit.createCriteria("imovel");
				 }	 
			 }
			 	
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
           else if (form.getOpcaoOrdenacao().equals("maiorDataSolicitacao"))
           		crit.addOrder(Order.desc("dataSolicitacao"));
           else if (form.getOpcaoOrdenacao().equals("menorDataSolicitacao"))
        	   crit.addOrder(Order.asc("dataSolicitacao"));
           else if (form.getOpcaoOrdenacao().equals("maiorDataAceitacao"))
        	   crit.addOrder(Order.desc("dataAceitacao"));
           else if (form.getOpcaoOrdenacao().equals("menorDataAceitacao"))
        	   crit.addOrder(Order.asc("dataAceitacao"));	    	
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
	public List<Intermediacao> filterIntermediacaoAceitas(Long idDonoImovel, IntermediacaoForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.add(Restrictions.eq("status", StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));
		
		Criterion critUsuarioDonoImovel 	= Restrictions.eq("usuarioDonoImovel.id", idDonoImovel) ;		
		Criterion critUsuarioSolicitante    = Restrictions.eq("usuarioSolicitante.id", idDonoImovel);
		LogicalExpression orExp = Restrictions.or(critUsuarioDonoImovel, critUsuarioSolicitante);
		crit.add(orExp);		
		
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
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ){ // fazer a ordenacao ainda
			 
			 boolean isOrdenaCamposImoveis = form.getOpcaoOrdenacao().equals("maiorValor") ||
					 						 form.getOpcaoOrdenacao().equals("menorValor") || 
					 						 form.getOpcaoOrdenacao().equals("maiorDataCadastro") || 
					 						 form.getOpcaoOrdenacao().equals("menorDataCadastro") ||
					 						 form.getOpcaoOrdenacao().equals("tituloImovelCrescente") ||
					 						 form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente");
			 if ( isOrdenaCamposImoveis ){
				 if ( critImovel == null ) {
					 critImovel = crit.createCriteria("imovel");
				 }	 
			 }
			 	
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
           else if (form.getOpcaoOrdenacao().equals("maiorDataSolicitacao"))
           		crit.addOrder(Order.desc("dataSolicitacao"));
           else if (form.getOpcaoOrdenacao().equals("menorDataSolicitacao"))
           		crit.addOrder(Order.asc("dataSolicitacao"));
           else if (form.getOpcaoOrdenacao().equals("maiorDataAceitacao"))
           		crit.addOrder(Order.desc("dataAceitacao"));
           else if (form.getOpcaoOrdenacao().equals("menorDataAceitacao"))
           		crit.addOrder(Order.asc("dataAceitacao"));	    	
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
	public List filterFindIntermediacaoByStatusByIdUsuarioDistinct(Long idUsuario, String status, IntermediacaoForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.add(Restrictions.eq("status", status));
		
		Criterion critUsuarioDonoImovel 	= Restrictions.eq("usuarioDonoImovel.id", idUsuario) ;		
		Criterion critUsuarioSolicitante    = Restrictions.eq("usuarioSolicitante.id", idUsuario);
		LogicalExpression orExp = Restrictions.or(critUsuarioDonoImovel, critUsuarioSolicitante);
		crit.add(orExp);	
		
		Criteria critImovel = null;
		boolean isCritImovelExist = (form.getIdEstadoAgruparImoveis() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcaoAgruparImoveis())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovelAgruparImoveis())) ||
									(! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
									(form.getQuantQuartos() > 0 ) ||
									(form.getQuantGaragem() > 0 ) ||
									(form.getQuantSuites() > 0 )  ||
									(form.getQuantBanheiro() > 0 ) ||
									(! StringUtils.isNullOrEmpty(form.getValorMin()))||
									(! StringUtils.isNullOrEmpty(form.getValorMax()));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstadoAgruparImoveis() > 0 ) {
				 critImovel.add(Restrictions.eq("idEstado", form.getIdEstadoAgruparImoveis()));
				 
				 if ( form.getIdCidadeAgruparImoveis() > 0 )
					 critImovel.add(Restrictions.eq("idCidade", form.getIdCidadeAgruparImoveis()));		            
			        
			     if ( form.getIdBairroAgruparImoveis() > 0 )
			    	 critImovel.add(Restrictions.eq("idBairro", form.getIdBairroAgruparImoveis()));
			 }	          
			
	        if ( ! StringUtils.isNullOrEmpty(form.getAcaoAgruparImoveis()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcaoAgruparImoveis()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovelAgruparImoveis()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovelAgruparImoveis()));
	        
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
		return crit.list();
	}

	@Override
	public int findQuantidadeNovosImoveisCompartilhados(Long idImovel) {

		Criteria crit = session().createCriteria(Intermediacao.class);
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.eq("imovel.id", idImovel));						
		crit.add(Restrictions.eq("statusLeitura", StatusLeituraEnum.NOVO.getRotulo()));
		List lista = crit.list();
		if ( lista == null )
			return 0;
		else {
			Object obj = lista.get(0); 
			return Integer.parseInt(obj.toString());
		}
	}

	@Override
	public List findImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, IntermediacaoForm form) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuarioSessao));				
		crit.add(Restrictions.eq("status", status));
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao() )){
		  if (form.getOpcaoOrdenacao().equals("maiorValor"))
        	crit.addOrder(Order.desc("imovel.valorImovel"));    
	   	  else if (form.getOpcaoOrdenacao().equals("menorValor"))
        	crit.addOrder(Order.asc("imovel.valorImovel"));        
	   	  else if (form.getOpcaoOrdenacao().equals("maiorDataCadastro"))
        	crit.addOrder(Order.desc("imovel.dataCadastro"));
	   	  else if (form.getOpcaoOrdenacao().equals("menorDataCadastro"))
	   		  crit.addOrder(Order.asc("imovel.dataCadastro"));
	   	  else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente"))
	   		  crit.addOrder(Order.asc("imovel.titulo"));
	   	  else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente"))
	   		  crit.addOrder(Order.desc("imovel.titulo"));
		}  	
		
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
		return crit.list();
	}
	
	@Override
	public List filterFindIntermediacaoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(Long idUsuario, String status, IntermediacaoForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.add(Restrictions.eq("status", status));
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));
		
		Criteria critImovel = null;
		boolean isCritImovelExist = (form.getIdEstadoAgruparImoveis() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcaoAgruparImoveis())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovelAgruparImoveis())) ||
									(! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
									(form.getQuantQuartos() > 0 ) ||
									(form.getQuantGaragem() > 0 ) ||
									(form.getQuantSuites() > 0 )  ||
									(form.getQuantBanheiro() > 0 ) ||
									(! StringUtils.isNullOrEmpty(form.getValorMin()))||
									(! StringUtils.isNullOrEmpty(form.getValorMax()));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstadoAgruparImoveis() > 0 ) {
				 critImovel.add(Restrictions.eq("idEstado", form.getIdEstadoAgruparImoveis()));
				 
				 if ( form.getIdCidadeAgruparImoveis() > 0 )
					 critImovel.add(Restrictions.eq("idCidade", form.getIdCidadeAgruparImoveis()));		            
			        
			     if ( form.getIdBairroAgruparImoveis() > 0 )
			    	 critImovel.add(Restrictions.eq("idBairro", form.getIdBairroAgruparImoveis()));
			 }	          
			
	        if ( ! StringUtils.isNullOrEmpty(form.getAcaoAgruparImoveis()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovelAgruparImoveis()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovelAgruparImoveis()));
	        
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
		return crit.list();		
	}	
	
	@Override
	public List filterFindIntermediacaoMinhasSolicitacoesByStatusByIdUsuarioDistinct(Long idUsuario, String status, IntermediacaoForm form) {
		
		Criteria crit = session().createCriteria(Intermediacao.class);		
		crit.add(Restrictions.eq("status", status));
		crit.createCriteria("usuarioSolicitante").add(Restrictions.eq("id", idUsuario));
		
		Criteria critImovel = null;
		boolean isCritImovelExist = (form.getIdEstadoAgruparImoveis() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcaoAgruparImoveis())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovelAgruparImoveis())) ||
									(! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
									(form.getQuantQuartos() > 0 ) ||
									(form.getQuantGaragem() > 0 ) ||
									(form.getQuantSuites() > 0 )  ||
									(form.getQuantBanheiro() > 0 ) ||
									(! StringUtils.isNullOrEmpty(form.getValorMin()))||
									(! StringUtils.isNullOrEmpty(form.getValorMax()));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstadoAgruparImoveis() > 0 ) {
				 critImovel.add(Restrictions.eq("idEstado", form.getIdEstadoAgruparImoveis()));
				 
				 if ( form.getIdCidadeAgruparImoveis() > 0 )
					 critImovel.add(Restrictions.eq("idCidade", form.getIdCidadeAgruparImoveis()));		            
			        
			     if ( form.getIdBairroAgruparImoveis() > 0 )
			    	 critImovel.add(Restrictions.eq("idBairro", form.getIdBairroAgruparImoveis()));
			 }	          
			
	        if ( ! StringUtils.isNullOrEmpty(form.getAcaoAgruparImoveis()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcaoAgruparImoveis()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovelAgruparImoveis()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovelAgruparImoveis()));
	        
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
		return crit.list();		
	}

	@Override
	public List findUsuariosParceriaByStatusByIdUsuario(Long idUsuarioSessao, String status, IntermediacaoForm form) {
		
		Criteria crit1 = session().createCriteria(Intermediacao.class);
		ProjectionList projList1 = Projections.projectionList();		
		projList1.add(Projections.count("usuarioDonoImovel.id").as("quant"));
		projList1.add(Projections.groupProperty("usuarioDonoImovel.id"));
		crit1.setProjection(projList1);		
		crit1.createCriteria("usuarioSolicitante").add(Restrictions.eq("id", idUsuarioSessao));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {
			
			Criteria critUsuario1 = crit1.createCriteria("usuarioDonoImovel");
			if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) && (! form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())) ){		
				List<?> listaIds = null;
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
					critUsuario1.add(Restrictions.in("id", listaIds));
				else
					critUsuario1.add(Restrictions.isNull("id"));					
			}
			else if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) &&
					(! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios()) &&
					(form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo()))) ){
				critUsuario1.add(Restrictions.eq("perfil", form.getOpcaoPerfilContatoAgruparUsuarios()));
			}	
			
			if (!StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())) {			
	        	if (form.getOpcaoOrdenacao().equals("maiorDataCadastrado")){
	        		critUsuario1.addOrder(Order.desc("dataCadastro"));
	            }
	            else if (form.getOpcaoOrdenacao().equals("menorDataCadastrado")){
	            	critUsuario1.addOrder(Order.asc("dataCadastro"));
	            } 
	            else if (form.getOpcaoOrdenacao().equals("nomeImovelCrescente")){
	            	critUsuario1.addOrder(Order.asc("nome"));
	            }
	             else if (form.getOpcaoOrdenacao().equals("nomeImovelDeCrescente")){
	            	 critUsuario1.addOrder(Order.desc("nome"));
	            }
		    }
		}
		crit1.add(Restrictions.eq("status", status));		
		List<?> list1 = crit1.list();		
		
		Criteria crit2 = session().createCriteria(Intermediacao.class);
		ProjectionList projList2 = Projections.projectionList();		
		projList2.add(Projections.count("usuarioSolicitante.id").as("quant"));
		projList2.add(Projections.groupProperty("usuarioSolicitante.id"));		
		crit2.setProjection(projList2);			
		crit2.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuarioSessao));
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {
			
			Criteria critUsuario2 = crit1.createCriteria("usuarioDonoImovel");
			if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) && (! form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())) ){		
				List<?> listaIds = null;
				if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){
					listaIds = contatoDao.filterListaIds(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguindo(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUIDORES.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguidores(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}				
				if (! CollectionUtils.isEmpty(listaIds))
					critUsuario2.add(Restrictions.in("id", listaIds));
				else
					critUsuario2.add(Restrictions.isNull("id"));					
			}
			else if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) &&
					(! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios()) &&
					(form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo()))) ){
				critUsuario2.add(Restrictions.eq("perfil", form.getOpcaoPerfilContatoAgruparUsuarios()));
			}
			
			if (!StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())) {			
	        	if (form.getOpcaoOrdenacao().equals("maiorDataCadastrado")){
	        		critUsuario2.addOrder(Order.desc("dataCadastro"));
	            }
	            else if (form.getOpcaoOrdenacao().equals("menorDataCadastrado")){
	            	critUsuario2.addOrder(Order.asc("dataCadastro"));
	            } 
	            else if (form.getOpcaoOrdenacao().equals("nomeImovelCrescente")){
	            	critUsuario2.addOrder(Order.asc("nome"));
	            }
	             else if (form.getOpcaoOrdenacao().equals("nomeImovelDeCrescente")){
	            	 critUsuario2.addOrder(Order.desc("nome"));
	            }
		    }
		}
		crit2.add(Restrictions.eq("status", status));
		List<?> list2 = crit2.list();
		
		List<Object> lista = new ArrayList<Object>();		
		List<Object> listaFinal =  new ArrayList<Object>();
		
		if (!CollectionUtils.isEmpty(list1))
			lista.addAll(list1);
		
		if (!CollectionUtils.isEmpty(list2))
			lista.addAll(list2);
		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(lista));
		if ( form.isVisible()){
			form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
			int numPaginas = form.getListaPaginas().size(); 
			int minIndex = (Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage();
			int maxIndex = 0;
			if ( numPaginas == Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao())){
				maxIndex = form.getQuantRegistros() - 1;
			}
			else {
				maxIndex = (form.getQuantMaxRegistrosPerPage()*Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao())) - 1 ;
			}
			
			listaFinal = lista.subList(minIndex, maxIndex);	        
		}
		else 
			listaFinal.addAll(lista);
		
		return listaFinal;
	}

	@Override
	public List findUsuariosParceriaByStatusByIdDonoImovel(Long idUsuarioSessao, String status, IntermediacaoForm form) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		ProjectionList projList = Projections.projectionList();				
		projList.add(Projections.count("usuarioSolicitante.id").as("quant"));
		projList.add(Projections.groupProperty("usuarioSolicitante.id"));		
		crit.setProjection(projList);		
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuarioSessao));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {
			Criteria critUsuario = crit.createCriteria("usuarioSolicitante");
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) && (! form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())) ){		
				List<?> listaIds = null;
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
		
		crit.add(Restrictions.eq("status", status));
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
		return crit.list();
	}

	@Override
	public List<Intermediacao> findIntermediacaoByIdUsuarioByIdUsuario2(Long idUsuario, Long idUsuario2) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("usuarioSolicitante").add(Restrictions.eq("id", idUsuario2));
		return crit.list();
	}

	@Override
	public long findQuantidadeIntermediacaoPorUsuarioPorStatus(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(Intermediacao.class);
		Criterion critUsuarioDonoImovel 	   = Restrictions.eq("usuarioDonoImovel.id", idUsuario);
		Criterion critUsuarioSolicitante 	   = Restrictions.eq("usuarioSolicitante.id", idUsuario); 
		LogicalExpression orExp = Restrictions.or(critUsuarioDonoImovel, critUsuarioSolicitante);
		crit.add(orExp);
		crit.add(Restrictions.eq("status", status));
		crit.setProjection(Projections.rowCount());	
		return (long) crit.uniqueResult();
	}

	@Override
	public void updateStatusLeitura(Long idDonoImovel) {

			Session session = this.openSession();		
			session.beginTransaction();
			Query query = session.createSQLQuery("CALL atualizatStatusLeitura(:idUsuario, :tabela)")
								.addEntity(Usuario.class)
								.setParameter("idUsuario", idDonoImovel)
								.setParameter("tabela", "intermediacao");
			int rows = query.executeUpdate();
			session.close();		
	}

	@Override
	public long checarQuantidadeIntermediacaoSolRecebidaByDonoImovelByStatusByStatusLeitura(Long idDonoImovel, 
																							String statusLeitura, 
																							String status) {
		Criteria crit = session().createCriteria(Intermediacao.class);		
		crit.add(Restrictions.eq("status", status));
		crit.add(Restrictions.eq("statusLeitura", statusLeitura));
		crit.createCriteria("usuarioDonoImovel").add(Restrictions.eq("id", idDonoImovel));		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.rowCount());
		crit.setProjection(projList);
		return (long)crit.uniqueResult();
	}

	@Override
	public long findQuantIntermediacaoByIdImovelByStatus(Long idImovel, String status) {
		Criteria crit = session().createCriteria(Intermediacao.class);		
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("status", status));		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.rowCount());
		crit.setProjection(projList);
		return (long)crit.uniqueResult();
	}

}
