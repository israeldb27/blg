package com.busqueumlugar.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelcomentarioDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.enumerador.TipoContatoOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelcomentarioForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelcomentario;
import com.busqueumlugar.model.Imovelindicado;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;


@Repository
public class ImovelcomentarioDaoImpl extends GenericDAOImpl<Imovelcomentario, Long>  implements ImovelcomentarioDao  {
	
	private static final Logger log = LoggerFactory.getLogger(ImovelcomentarioDaoImpl.class);
	
	@Autowired
	private ContatoDao contatoDao;

	@Autowired
	private SeguidorDao seguidorDao;

	
	public ImovelcomentarioDaoImpl() {
		super(Imovelcomentario.class);
	}

	@Override
	public Imovelcomentario findImovelcomentarioById(Long id) {
		return (Imovelcomentario)session().createCriteria(Imovelcomentario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}
	

	@Override
	public List<Imovelcomentario> findImovelcomentariosByIdImovel(Long idImovel, ImovelcomentarioForm form) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		if ( form != null){
			form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
			if ( form.isVisible()){
		        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
		        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
		        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
			}
		}		  
		return crit.list();
	}

	@Override
	public List<Imovelcomentario> findImovelcomentariosByIdImovelStatus(Long idImovel, String status) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("status", status));
		return crit.list();
	}

	@Override
	public List<Imovelcomentario> findImovelcomentariosByIdUsuario(	Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("usuarioComentario").add(Restrictions.eq("id", idUsuario));
		return crit.list();
	}

	@Override
	public List<Imovelcomentario> findImovelcomentariosOutrosByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		return crit.list();
	}

	@Override
	public List<Imovelcomentario> findNovoImovelcomentariosOutrosByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.add(Restrictions.eq("status", "novo"));
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		return crit.list();		
	}

	@Override
	public List checarImoveisComMaisComentariosPeriodo(Date dataInicio,	Date dataFim, int quant) {		
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));		
		projList.add(Projections.groupProperty("imovel.id"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataComentario", dataInicio));
		crit.add(Restrictions.le("dataComentario", dataFim));			
		return crit.list();
	}

	@Override
	public List relatorioImovelMaisComentadosPeriodo(RelatorioForm form) {
		
		Criteria crit = session().createCriteria(Imovelcomentario.class);
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
		crit.add(Restrictions.ge("dataComentario", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataComentario", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();			
	}
	
	@Override
	public List relatorioImovelMaisComentadosPeriodo(AdministracaoForm form) {
		
		Criteria crit = session().createCriteria(Imovelcomentario.class);
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
		crit.add(Restrictions.ge("dataComentario", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataComentario", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(10);
		return crit.list();
	}

	@Override
	public Imovelcomentario findLastImovelcomentarioByIdImovel(Long idImovel) {		
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("dataComentario", this.recuperarMaxDataComentarioPorImovel(idImovel)));
		crit.addOrder(Order.desc("dataComentario"));
		crit.setMaxResults(1);
		return (Imovelcomentario)crit.uniqueResult();		 
	}

	private Date recuperarMaxDataComentarioPorImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.max("dataComentario").as("dataComentario"));
		crit.setProjection(projList);
		crit.addOrder(Order.desc("dataComentario"));
		crit.setMaxResults(1);
		return (Date)crit.uniqueResult();		
	}

	@Override
	public List findImoveisComentariosSobreMeusImoveisDistinct(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));						
		return crit.list();
	}

	@Override
	public List findMeusComentariosByIdUsuarioDistinct(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.createCriteria("usuarioComentario").add(Restrictions.eq("id", idUsuario));						
		return crit.list();
	}

	@Override
	public List findUsuariosImoveisComentariosSobreMeusImoveisDistinct(Long idUsuarioSessao) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuarioComentario.id"));		
		projList.add(Projections.count("usuarioComentario.id").as("quant"));
		crit.setProjection(projList);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuarioSessao));
		return crit.list();
	}

	@Override
	public List findUsuariosMeusComentariosDistinct(Long idUsuarioSessao) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuario.id"));		
		projList.add(Projections.count("usuario.id").as("quant"));				
		crit.setProjection(projList);
		crit.add(Restrictions.eq("usuarioComentario.id", idUsuarioSessao));
		return crit.list();
	}

	@Override
	public List<Imovelcomentario> findImoveisComentariosSobreMeusImoveisPorUsuario(Long idUsuarioSessao, Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("usuarioComentario").add(Restrictions.eq("id", idUsuarioSessao));
		return crit.list();
	}
	

	@Override
	public List<Imovelcomentario> filterMeusComentarios(ImovelcomentarioForm form, Long idUsuario) {		
		
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		
		if ( idUsuario != null && idUsuario.longValue() > 0 )
			crit.createCriteria("usuarioComentario").add(Restrictions.eq("id", idUsuario));

		
		 if ( form.getIdEstado() > 0 ) {
			 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			 
			 if ( form.getIdCidade() > 0 )
				 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
		        
		     if ( form.getIdBairro() > 0 )
		    	 crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		 }
		 
		    if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
	        	crit.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	crit.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
	       
	        return crit.list();
	}

	@Override
	public List findImoveisComentariosSobreMeusImoveisInfoTotais(ImovelcomentarioForm form, Long idUsuario) {
		
		Criteria crit = session().createCriteria(Imovelcomentario.class, "i");
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel()));	
		if ( form != null ){
			Criteria critImovel = null;
			if (isCritImovelExist)
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
		} 		
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);				
		crit.addOrder(Order.desc("quant"));	
		if ( form != null ){
			form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
			if ( form.isVisible()){
		        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
		        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
		        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
			}
		}
		return crit.list();
	}

	

	@Override
	public List<Imovelcomentario> findImoveisComentariosNovosSobreMeusImoveis(Long idUsuario) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);		
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", "novo"));
		return (List<Imovelcomentario>)crit.list();
	}

	@Override
	public void updateStatusLeitura(Long idUsuario) {		
		Session session = this.openSession();		
		session.beginTransaction();
		Query query = session.createSQLQuery("CALL atualizatStatusLeitura(:idUsuario, :tabela)")
							.addEntity(Usuario.class)
							.setParameter("idUsuario", idUsuario)
							.setParameter("tabela", "imovelcomentario");
		int rows = query.executeUpdate();
		session.close();
		
	}

	@Override
	public long findQuantImoveisComentariosRecebidos(Long idUsuario, String statusLeitura) {

		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		if (! StringUtils.isEmptyOrWhitespaceOnly(statusLeitura))
			crit.add(Restrictions.eq("status", statusLeitura));

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.rowCount());
		crit.setProjection(projList);		
		return (long)crit.uniqueResult();
		
	}

	@Override
	public long findQuantImovelcomentariosByIdImovelStatus(Long idImovel, String statusLeitura) {

		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		if (! StringUtils.isEmptyOrWhitespaceOnly(statusLeitura))
			crit.add(Restrictions.eq("status", statusLeitura));

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.rowCount());
		crit.setProjection(projList);
		return (long)crit.uniqueResult();
	}

	@Override
	public List<Imovelcomentario> findImovelcomentariosByIdImovelByQuant(Long idImovel, int quantMaxLista) {
		Criteria crit = session().createCriteria(Imovelcomentario.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.addOrder(Order.desc("dataComentario"));		
		crit.setMaxResults(quantMaxLista);
		return crit.list();
	}

}
