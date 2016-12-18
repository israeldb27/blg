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
import com.busqueumlugar.dao.ImovelindicadoDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.TipoContatoOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.model.Imovelindicado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ImovelindicadoDaoImpl extends GenericDAOImpl<Imovelindicado, Long>  implements ImovelindicadoDao {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelindicadoDaoImpl.class);
	
	@Autowired
	private ContatoDao contatoDao;

	@Autowired
	private SeguidorDao seguidorDao;

	
	public ImovelindicadoDaoImpl() {
		super(Imovelindicado.class);
	}


	public Imovelindicado findImovelindicadoById(Long id) {
		return (Imovelindicado)session().createCriteria(Imovelindicado.class)
				.add(Restrictions.eq("id", id)).uniqueResult();	
	}
	
	@Override
	public List<Imovelindicado> findImoveisPorUsuario(Long idUsuario, ImovelindicadoForm form) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return (List<Imovelindicado>)crit.list();
	}

	@Override
	public List<Imovelindicado> findImoveisIndicacoesPorUsuario(Long idUsuario, ImovelindicadoForm form) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuarioIndicador").add(Restrictions.eq("id", idUsuario));		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return (List<Imovelindicado>)crit.list();
	}

	@Override
	public Imovelindicado findImovelIndicadoByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		return (Imovelindicado)crit.uniqueResult();			
	}

	@Override
	public Imovelindicado findImovelIndicadoByIdImovelIdUsuario(Long idImovel, Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		return (Imovelindicado)crit.uniqueResult();
	}
	
	@Override
	public Imovelindicado findImovelIndicacaoByIdImovelIdUsuario(Long idImovel, Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		crit.createCriteria("usuarioIndicador").add(Restrictions.eq("id", idUsuario));
		return (Imovelindicado)crit.uniqueResult();			
	}

	@Override
	public List<Imovelindicado> findImoveisIndicadosNovos(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusIndicado", StatusLeituraEnum.NOVO.getRotulo()));				
		return (List<Imovelindicado>)crit.list();			
	}

	@Override
	public List checarImoveisComMaisIndicacoesPeriodo(Date dataInicio,Date dataFim, int quant) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataIndicacao", dataInicio));
		crit.add(Restrictions.le("dataIndicacao", dataFim));			
		return crit.list();		
	}

	@Override
	public List<Imovelindicado> filterImoveisIndicacoes(Long idUsuario, ImovelindicadoForm form) {
		
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuarioIndicador").add(Restrictions.eq("id", idUsuario));		
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
				 
				 if ( form.getIdCidade()> 0 )
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
		
		 if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
        	 if (form.getOpcaoOrdenacao().equals("maiorValor")){
        		 critImovel.addOrder(Order.desc("valorImovel")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("menorValor")){
 	        	critImovel.addOrder(Order.asc("valorImovel"));  
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("maiorDataIndicado")){
 	        	crit.addOrder(Order.desc("dataIndicacao")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("menorDataIndicado")){
 	        	crit.addOrder(Order.asc("dataIndicacao")); 
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
		return crit.list();
	}

	@Override
	public List<Imovelindicado> filterImoveisIndicados(Long idUsuario, ImovelindicadoForm form) {

		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
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
				 
				 if ( form.getIdCidade()> 0 )
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
		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();
	}
	
	@Override
	public List filtrarAgruparImoveis(Long idUsuario, ImovelindicadoForm form){
		
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuarioIndicador").add(Restrictions.eq("id", idUsuario));		
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
				 
				 if ( form.getIdCidade()> 0 )
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
	public List findInfoAgruparUsuariosImoveisIndicacoes(Long idUsuarioIndicacao) {
		return null;
	}

	@Override
	public List findImoveisPorUsuarioDistinct(Long idUsuario, ImovelindicadoForm form) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuarioIndicador.id"));		
		projList.add(Projections.count("usuarioIndicador.id").as("quant"));				
		crit.setProjection(projList);
		crit.add(Restrictions.eq("usuario.id", idUsuario));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {
			
			Criteria critUsuario = crit.createCriteria("usuarioIndicador");
			
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
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
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
	public List findImoveisIndicacoesPorUsuarioDistinct(Long idUsuario, ImovelindicadoForm form) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuario.id"));			
		projList.add(Projections.count("usuario.id").as("quant"));			
		crit.setProjection(projList);	
		crit.add(Restrictions.eq("usuarioIndicador.id", idUsuario));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {
			
			Criteria critUsuario = crit.createCriteria("usuario");
			//critUsuario.add(Restrictions.eq("id", idUsuario));
			
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
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
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
	public List findImoveisIndicacoesByIdUsuarioDistinct(Long idUsuario, ImovelindicadoForm form) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));				
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.eq("usuarioIndicador.id", idUsuario));
		
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
	public int findQuantidadeNovosImoveisIndicacoes(Long idImovel) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("statusIndicado", StatusLeituraEnum.NOVO.getRotulo()));
		
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.rowCount());		
		crit.setProjection(projList);
		crit.setMaxResults(1);
		return (int)crit.uniqueResult();
	}

	@Override
	public List<Imovelindicado> findImoveisIndicadosByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		return (List<Imovelindicado>)crit.list();
	}
	
	@Override
	public List<Imovelindicado> findImoveisIndicadosByIdImovelPorIdUsuarioIndicador(Long idImovel, Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.createCriteria("usuarioIndicador").add(Restrictions.eq("id", idUsuario));		
		return (List<Imovelindicado>)crit.list();		
	}


	@Override
	public List<Imovelindicado> findImoveisIndicadosParaMimByIdUsuarioSessaoByIdUsuario(Long idUsuarioSessao, Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("usuarioIndicador").add(Restrictions.eq("id", idUsuarioSessao));		
		return (List<Imovelindicado>)crit.list();
	}
	
	

	@Override
	public List checarImoveisComMaisIndicacoesPeriodo(AdministracaoForm form) {
		
		Criteria crit = session().createCriteria(Imovelindicado.class);				
		Criteria critImovel = null;	
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getPerfilImovel())  && ! form.getPerfilImovel().equals("todos")) ||
									(!StringUtils.isNullOrEmpty(form.getTipoImovel()));									

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstado() > 0 ) {
				 critImovel.add(Restrictions.eq("idEstado", form.getIdEstado()));
				 
				 if ( form.getIdCidade()> 0 )
					 critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
			        
			     if ( form.getIdBairro() > 0 )
			    	 critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
			 }	          
			
	        if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
		}

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);		
		crit.add(Restrictions.ge("dataIndicacao", form.getDataInicio()));
		crit.add(Restrictions.le("dataIndicacao", form.getDataFim()));	
		crit.addOrder(Order.desc("quant"));	
		crit.setMaxResults(10);
		return crit.list();
		
	}

	@Override
	public List<Imovelindicado> findImoveisNovosPorUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
		crit.add(Restrictions.eq("statusIndicado", StatusLeituraEnum.NOVO.getRotulo()));
		return (List<Imovelindicado>)crit.list();	
	}
	
	@Override	
	public void updateStatusLeituraByIdUsuarioIndicado(Long idUsuario) {
		Session session = this.openSession();		
		session.beginTransaction();
		Query query = session.createSQLQuery("CALL atualizatStatusLeitura(:idUsuario, :tabela)")
							.addEntity(Usuario.class)
							.setParameter("idUsuario", idUsuario)
							.setParameter("tabela", "imovelindicado");
		int rows = query.executeUpdate();
		session.close();		
	}


	@Override
	public long findQuantImoveisIndicadosByIdUsuarioByStatusLeitura(Long idUsuario, String statusLeitura) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));	
		if (! StringUtils.isNullOrEmpty(statusLeitura))
			crit.add(Restrictions.eq("statusIndicado", statusLeitura));
		
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.rowCount());		
		crit.setProjection(projList);
		crit.setMaxResults(1);
		return (long)crit.uniqueResult();
	}


	
	@Override
	public long findQuantImoveisIndicacoesByIdUsuarioByStatusLeitura(Long idUsuario, String statusLeitura) {
		Criteria crit = session().createCriteria(Imovelindicado.class);
		crit.createCriteria("usuarioIndicador").add(Restrictions.eq("id", idUsuario));
		if (! StringUtils.isNullOrEmpty(statusLeitura))
			crit.add(Restrictions.eq("statusIndicado", statusLeitura));
		
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.rowCount());		
		crit.setProjection(projList);
		crit.setMaxResults(1);
		return (long)crit.uniqueResult();
	}


}
