package com.busqueumlugar.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.ImovelvisualizadoDao;
import com.busqueumlugar.dao.PreferencialocalidadeDao;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioNormalEnum;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Preferencialocalidade;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ImovelDaoImpl extends GenericDAOImpl<Imovel, Long>   implements ImovelDao {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelDaoImpl.class);

	
	@Autowired
	private PreferencialocalidadeDao preferencialocalidadeDao;
	
	@Autowired	
	private ImovelvisualizadoDao imovelvisitadoDao;
	
	@Autowired
	private ContatoDao contatoDao;
	
	public ImovelDaoImpl() {
		super(Imovel.class);
	}
	
	public Imovel findImovelById(Long idImovel) {
		return (Imovel)session().createCriteria(Imovel.class)
								.add(Restrictions.eq("id", idImovel)).uniqueResult();	
	}

	@Override
	public List<Imovel> findImovel(String valorBusca) {		
		return (List<Imovel>)session().createCriteria(Imovel.class)
				.add(Restrictions.like("descricao", "%" + valorBusca + "%")).list();	
	}

	@Override
	public List<Imovel> findImovelByUsuario(Long idUsuario, ImovelForm form) {
		Criteria crit = session().createCriteria(Imovel.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
 		if ( form.isVisible()){
 	        crit.setFirstResult((Integer.parseInt((StringUtils.isEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
 	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
 	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
 		}
		return crit.list();	
	}
	
	@Override
	public List<Imovel> findImovelByUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovel.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
		return crit.list();	
	}

	@Override
	public List<Imovel> buscarImoveis(AdministracaoForm form, Long idUsuario, String opcaoOrdenacao){
		
		Criteria crit = session().createCriteria(Imovel.class);
		
		if ( idUsuario != null && idUsuario.longValue() > 0 )
			crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		
		if (! StringUtils.isEmpty(form.getValorBusca()))
			 crit.add(Restrictions.ilike("descricao",  "%" + form.getValorBusca() + "%"));
		
		 if ( form.getIdEstado() > 0 ) {
			 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			 
			 if ( form.getIdCidade() > 0 )
				 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
		        
		     if ( form.getIdBairro() > 0 )
		    	 crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		 }	          
		
        if ( ! StringUtils.isEmpty(form.getAcao()))         
        	crit.add(Restrictions.eq("acao", form.getAcao()));            

        if ( ! StringUtils.isEmpty(form.getTipoImovel()))
        	crit.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
        
        if ( ! StringUtils.isEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
        	crit.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
        
        if ( ! StringUtils.isEmpty(form.getStatusNegociacao()))
        	crit.add(Restrictions.eq("statusNegociacaoImovel", form.getStatusNegociacao()));
        
        if ( form.getQuantQuartos() >= 0 ){
            if (form.getQuantQuartos() >= 6 )
            	crit.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
            else
            	crit.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
        }
        
        if ( form.getQuantGaragem() >= 0 ){
           if (form.getQuantGaragem() >= 6 )
        	   crit.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
            else
            	crit.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
        }
        
        if ( form.getQuantSuites() >= 0 ){
           if (form.getQuantSuites() >= 6 )
        	   crit.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
            else
            	crit.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
        }
        
        if (! StringUtils.isEmpty(opcaoOrdenacao) ){ // fazer a ordenacao ainda
        	  if (opcaoOrdenacao.equals("maiorValor"))
              	crit.addOrder(Order.desc("valorImovel"));    
              else if (opcaoOrdenacao.equals("menorValor"))
              	crit.addOrder(Order.asc("valorImovel"));        
              else if (opcaoOrdenacao.equals("maiorDataCadastro"))
              	crit.addOrder(Order.desc("dataCadastro"));
              else if (opcaoOrdenacao.equals("menorDataCadastro"))
              	crit.addOrder(Order.asc("dataCadastro"));
              else if (opcaoOrdenacao.equals("tituloImovelCrescente"))
              	crit.addOrder(Order.asc("titulo"));
              else if (opcaoOrdenacao.equals("tituloImovelDeCrescente"))
              	crit.addOrder(Order.desc("titulo"));
        }
        
        crit.add(Restrictions.eq("habilitaBusca", "S"));
        return (List<Imovel>) crit.list();		
	}
	
	
	@Override
	public List<Imovel> buscarImoveis(ImovelForm form, Long idUsuario, String opcaoOrdenacao){
		
		Criteria crit = session().createCriteria(Imovel.class);
		crit.add(Restrictions.eq("habilitaBusca", "S"));
		
		if ( idUsuario != null && idUsuario.longValue() > 0 )
			crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		
		if (! StringUtils.isEmpty(form.getValorBusca()))
			 crit.add(Restrictions.ilike("titulo",  "%" + form.getValorBusca() + "%"));
		
		 if ( form.getIdEstado() > 0 ) {
			 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			 
			 if ( form.getIdCidade() > 0 )
				 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
		        
		     if ( form.getIdBairro() > 0 )
		    	 crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		 }	          
		
        if ( ! StringUtils.isEmpty(form.getAcao()))         
        	crit.add(Restrictions.eq("acao", form.getAcao()));            

        if ( ! StringUtils.isEmpty(form.getTipoImovel()))
        	crit.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
        
        if ( ! StringUtils.isEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
        	crit.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
        
        if ( ! StringUtils.isEmpty(form.getStatusNegociacao()))
        	crit.add(Restrictions.eq("statusNegociacaoImovel", form.getStatusNegociacao()));
        
        if ( form.getQuantQuartos() > 0 ){
            if (form.getQuantQuartos() >= 6 )
            	crit.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
            else
            	crit.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
        }
        
        if ( form.getQuantGaragem() > 0 ){
           if (form.getQuantGaragem() >= 6 )
        	   crit.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
            else
            	crit.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
        }
        
        if ( form.getQuantSuites() > 0 ){
           if (form.getQuantSuites() >= 6 )
        	   crit.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
            else
            	crit.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
        }
        
        if ( form.getQuantBanheiro() > 0 ){
            if (form.getQuantBanheiro()  >= 6 )
         	   crit.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
             else
             	crit.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
         }
        
        if (! StringUtils.isEmpty(form.getValorMin() ) ){
        	crit.add(Restrictions.ge("valorImovel", AppUtil.formatarMoeda(form.getValorMin())));
        }
        
        if (! StringUtils.isEmpty(form.getValorMax()) ){
        	crit.add(Restrictions.le("valorImovel", AppUtil.formatarMoeda(form.getValorMax())));
        }
        
        if (! StringUtils.isEmpty(opcaoOrdenacao) ){ // fazer a ordenacao ainda
        	  if (opcaoOrdenacao.equals("maiorValor"))
              	crit.addOrder(Order.desc("valorImovel"));    
              else if (opcaoOrdenacao.equals("menorValor"))
              	crit.addOrder(Order.asc("valorImovel"));        
              else if (opcaoOrdenacao.equals("maiorDataCadastrado"))
              	crit.addOrder(Order.desc("dataCadastro"));
              else if (opcaoOrdenacao.equals("menorDataCadastrado"))
              	crit.addOrder(Order.asc("dataCadastro"));
              else if (opcaoOrdenacao.equals("maiorDataAtualizado"))
                	crit.addOrder(Order.desc("dataUltimaAtualizacao"));
              else if (opcaoOrdenacao.equals("menorDataAtualizado"))
                	crit.addOrder(Order.asc("dataUltimaAtualizacao"));
              else if (opcaoOrdenacao.equals("tituloImovelCrescente"))
              	crit.addOrder(Order.asc("titulo"));
              else if (opcaoOrdenacao.equals("tituloImovelDeCrescente"))
              	crit.addOrder(Order.desc("titulo"));
        }       
        
        form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
    	if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}    
        return (List<Imovel>) crit.list();		
	}
	
	@Override
	public List<Imovel> buscarImoveis(PerfilForm form, Long idUsuario, String opcaoOrdenacao){
		
		Criteria crit = session().createCriteria(Imovel.class);
		
		if ( idUsuario != null && idUsuario.longValue() > 0 )
			crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		
		 if ( form.getIdEstado() > 0 ) {
			 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			 
			 if ( form.getIdCidade() > 0 )
				 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
		        
		     if ( form.getIdBairro() > 0 )
		    	 crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		 }	          
		
        if ( ! StringUtils.isEmpty(form.getAcao()))         
        	crit.add(Restrictions.eq("acao", form.getAcao()));            

        if ( ! StringUtils.isEmpty(form.getTipoImovel()))
        	crit.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
        
        if ( ! StringUtils.isEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
        	crit.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
        
        if ( ! StringUtils.isEmpty(form.getStatusNegociacao()))
        	crit.add(Restrictions.eq("statusNegociacaoImovel", form.getStatusNegociacao()));
        
        if ( form.getQuantQuartos() >= 0 ){
            if (form.getQuantQuartos() >= 6 )
            	crit.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
            else
            	crit.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
        }
        
        if ( form.getQuantGaragem() >= 0 ){
           if (form.getQuantGaragem() >= 6 )
        	   crit.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
            else
            	crit.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
        }
        
        if ( form.getQuantSuites() >= 0 ){
           if (form.getQuantSuites() >= 6 )
        	   crit.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
            else
            	crit.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
        }
        
       /* if ( form.getQuantBanheiro() >= 0 ){
            if (form.getQuantBanheiro()  >= 6 )
         	   crit.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
             else
             	crit.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
         }
        */
        
        if (! StringUtils.isEmpty(opcaoOrdenacao) ){ // fazer a ordenacao ainda
        	  if (opcaoOrdenacao.equals("maiorValor"))
              	crit.addOrder(Order.desc("valorImovel"));    
              else if (opcaoOrdenacao.equals("menorValor"))
              	crit.addOrder(Order.asc("valorImovel"));        
              else if (opcaoOrdenacao.equals("maiorDataCadastro"))
              	crit.addOrder(Order.desc("dataCadastro"));
              else if (opcaoOrdenacao.equals("menorDataCadastro"))
              	crit.addOrder(Order.asc("dataCadastro"));
              else if (opcaoOrdenacao.equals("tituloImovelCrescente"))
              	crit.addOrder(Order.asc("titulo"));
              else if (opcaoOrdenacao.equals("tituloImovelDeCrescente"))
              	crit.addOrder(Order.desc("titulo"));
        }        
        
        crit.add(Restrictions.eq("habilitaBusca", "S"));
        return (List<Imovel>) crit.list();		
	}
		

	@Override
	public List<Imovel> findImovelByAutorizacaoOutroUsuario() {		
		Criteria crit = session().createCriteria(Imovel.class);		
		crit.add(Restrictions.eq("autorizacaoOutroUsuario", "S"));
		int size = crit.list().size();
        int opcao =  (int)(Math.random() * size);		
        crit.setFirstResult(opcao);        
        crit.setMaxResults(2);
        return (List<Imovel>) crit.list();
	}

	@Override
	public Imovel findImovelByCodigoIdentificacao(String codigo) {		
		return (Imovel)session().createCriteria(Imovel.class)
				.add(Restrictions.eq("codigoIdentificacao", codigo)).uniqueResult();	
	}

	@Override
	public List<Imovel> findImovelByUsuarioOrdenacao(Long idUsuario, String opcaoOrdenacao) {
		
		Criteria crit = session().createCriteria(Imovel.class);		
		if ( idUsuario > 0 )
			crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		
		if (opcaoOrdenacao.equals("maiorValor"))
        	crit.addOrder(Order.desc("valorImovel"));    
        else if (opcaoOrdenacao.equals("menorValor"))
        	crit.addOrder(Order.asc("valorImovel"));        
        else if (opcaoOrdenacao.equals("maiorDataCadastro"))
        	crit.addOrder(Order.desc("dataCadastro"));
        else if (opcaoOrdenacao.equals("menorDataCadastro"))
        	crit.addOrder(Order.asc("dataCadastro"));
        else if (opcaoOrdenacao.equals("tituloImovelCrescente"))
        	crit.addOrder(Order.asc("dataCadastro"));
        else if (opcaoOrdenacao.equals("tituloImovelDeCrescente"))
        	crit.addOrder(Order.desc("dataCadastro"));
		
		return (List<Imovel>) crit.list();
	}

	@Override
	public List<Imovel> findImoveisByDataCadastro(Date dataInicio, Date dataFim) {		
		Criteria crit = session().createCriteria(Imovel.class);		
		crit.add(Restrictions.ge("dataCadastro", dataInicio));
		crit.add(Restrictions.le("dataCadastro", dataFim));		
		return (List<Imovel>) crit.list();
	}
	
	@Override
	public List<Imovel> findImoveisByDataCadastro(AdministracaoForm form) {

		Criteria crit = session().createCriteria(Imovel.class);		
		
		if ( form.getIdEstado() > 0 )
			crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
        
        if ( form.getIdCidade() > 0 )
        	crit.add(Restrictions.eq("idCidade", form.getIdCidade()));    
        
        if ( form.getIdBairro() > 0 )
        	crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
        
        if ( form.getTipoImovel() != null && ! form.getTipoImovel().equals("") && ! form.getTipoImovel().equals("-1"))
        	crit.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
         
        if ( form.getAcao() != null && ! form.getAcao().equals("") && ! form.getAcao().equals("-1"))
        	crit.add(Restrictions.eq("acao", form.getAcao()));
        
        if ( ! StringUtils.isEmpty(form.getPerfilImovel()))
        	crit.add(Restrictions.eq("perfilImovel", form.getPerfilImovel()));
                   
        crit.add(Restrictions.ge("dataCadastro", form.getDataInicio()));
		crit.add(Restrictions.le("dataCadastro", form.getDataFim()));
		return (List<Imovel>) crit.list();
	}

	@Override
	public List<Imovel> findImoveisDestaqueByIdUsuario(Long idUsuario) {		
		Criteria crit = session().createCriteria(Imovel.class);		
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("destaque", "S"));		
		return (List<Imovel>) crit.list();
	}

	@Override
	public Imovel findImovelByCodigoIdentificacaoBydIdimovel(Long idUsuario, String codigo) {
		Criteria crit = session().createCriteria(Imovel.class);		
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("codigoIdentificacao", codigo));		
		return (Imovel) crit.uniqueResult();
	}

	@Override
	public void reativaImoveisUsuario(long idUsuario) {		
		session().beginTransaction();
		Query query = session().createQuery("UPDATE Imovel i set i.ativado = :statusAtivado where i.idUsuario = :idUsuario");		                        
        query.setParameter("statusAtivado", "S");  
        query.setParameter("idUsuario", idUsuario);
        query.executeUpdate(); 
        session().beginTransaction().commit();
	}


	@Override
	public List<Imovel> findImoveisNaoDestaqueByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovel.class);		
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("destaque", "N"));		
		return (List<Imovel>) crit.list();
	}	
	
	@Override
	public List<Imovel> findImoveisByValor(String valor){
		Criteria crit = session().createCriteria(Imovel.class);		
		crit.add(Restrictions.like("titulo", "%" + valor + "%"));		
		return (List<Imovel>) crit.list();
	}
	
	
	@Override
	public long findQuantMeusImoveis(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovel.class);	
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.setProjection(Projections.rowCount());	
		return (long)crit.uniqueResult();
	}

	@Override
	public List<Imovel> findImoveisByListaIdsByIndex(List<Long> listaIds, int index) {
		Criteria crit = session().createCriteria(Imovel.class);			
		crit.createCriteria("usuario").add(Restrictions.in("id", listaIds));		
		crit.addOrder(Order.desc("dataUltimaAtualizacao"));
		crit.setFirstResult(index);
		crit.setMaxResults(5);
		return crit.list();
	}

	@Override
	public List<Imovel> findImoveisAleatoriamenteByUsuarioByIndex( UsuarioForm user, int index ) {
		Criteria crit = session().createCriteria(Imovel.class);			
		crit.createCriteria("usuario").add(Restrictions.ne("id", user.getId()));		
		crit.addOrder(Order.desc("dataUltimaAtualizacao"));
		crit.setFirstResult(index);
		crit.setMaxResults(4);
		//crit.add(Restrictions.sqlRestriction("1=1 order by rand()"));
		return (List<Imovel>) crit.list();
	}

	@Override
	public List<Imovel> findImoveisByListaIdsByIndexByAceitaCompartilhado(List<Long> listaIds, int index, String aceitaCompartilhado) {
		Criteria crit = session().createCriteria(Imovel.class);			
		crit.createCriteria("usuario").add(Restrictions.in("id", listaIds));		
		crit.add(Restrictions.eq("autorizacaoOutroUsuario", aceitaCompartilhado));
		crit.addOrder(Order.desc("dataUltimaAtualizacao"));
		crit.setFirstResult(index);
		crit.setMaxResults(5);
		return crit.list();
	}

	@Override
	public List<Imovel> findImoveisAleatoriamenteByListaIdsByIndex(List<Long> listaIds, int index) {

		Criteria crit = session().createCriteria(Imovel.class);			
		crit.createCriteria("usuario").add(Restrictions.not(Restrictions.in("id", listaIds )));		
		crit.addOrder(Order.desc("dataUltimaAtualizacao"));
		crit.setFirstResult(index);
		crit.setMaxResults(4);
		return (List<Imovel>) crit.list();
	}

	@Override
	public List<Imovel> findImoveisByListaIdsByIndexByAceitaCompartilhado(Long idUsuario, int index, String aceitaCompartilhado) {
		Criteria crit = session().createCriteria(Imovel.class);			
		crit.createCriteria("usuario").add(Restrictions.ne("id", idUsuario));		
		crit.add(Restrictions.eq("autorizacaoOutroUsuario", aceitaCompartilhado));
		crit.addOrder(Order.desc("dataUltimaAtualizacao"));
		crit.setFirstResult(index);
		crit.setMaxResults(5);
		return crit.list();
	}

	@Override
	public List<Imovel> findImoveisByListaIdsByIndex(Long idUsuario, int index) {

		Criteria crit = session().createCriteria(Imovel.class);			
		crit.createCriteria("usuario").add(Restrictions.ne("id", idUsuario));		
		crit.addOrder(Order.desc("dataUltimaAtualizacao"));
		crit.setFirstResult(index);
		crit.setMaxResults(5);
		return crit.list();
	}

}
