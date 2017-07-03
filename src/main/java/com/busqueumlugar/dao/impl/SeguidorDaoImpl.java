package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Seguidor;
import com.mysql.jdbc.StringUtils;

@Repository
public class SeguidorDaoImpl extends GenericDAOImpl<Seguidor, Long>  implements SeguidorDao {
	
	private static final Logger log = LoggerFactory.getLogger(SeguidorDaoImpl.class);  

	public SeguidorDaoImpl() {
		super(Seguidor.class);
	}

	@Override
	public Seguidor findSeguidorById(Long id) {
		return (Seguidor)session().createCriteria(Seguidor.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public Seguidor findSeguidorByIdUsuarioSeguidorByIdUsuario(Long idUsuarioSessao, Long idUsuario) {
		Criteria crit = session().createCriteria(Seguidor.class);
		crit.createCriteria("usuarioSeguido").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuarioSessao));				
		return (Seguidor)crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Seguidor> findSeguidoresByIdUsuarioSeguido(Long idUsuario) {
		Criteria crit = session().createCriteria(Seguidor.class);
		crit.createCriteria("usuarioSeguido").add(Restrictions.eq("id", idUsuario));
		return (List<Seguidor>)crit.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Seguidor> findSeguindoByIdUsuarioSeguido(Long idUsuario) {
		Criteria crit = session().createCriteria(Seguidor.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		return (List<Seguidor>)crit.list();
	}

	@Override
	public List<?> findIdsSeguidoresByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Seguidor.class);
		crit.createCriteria("usuarioSeguido").add(Restrictions.eq("id", idUsuario));
		crit.setProjection(Projections.property("usuario.id"));				
		return crit.list();
	}
	
	@Override
	public List<?> findIdsSeguindoByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Seguidor.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.setProjection(Projections.property("usuarioSeguido.id"));				
		return crit.list();
	}

	@Override
	public List<?> filterListaIdsUsuariosSeguindo(RelatorioForm form) { //  lista todos os Ids dos Usuarios que estao me seguindo
		Criteria crit = session().createCriteria(Seguidor.class);
		crit.createCriteria("usuarioSeguido").add(Restrictions.eq("id", form.getUsuarioSessao().getId()));
		
		Criteria critUsuarioSeguidor = crit.createCriteria("usuario");		
		if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
			critUsuarioSeguidor.add(Restrictions.eq("sexo", form.getSexo()));
			
        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) ) 
        	critUsuarioSeguidor.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
        
        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
        	critUsuarioSeguidor.add(Restrictions.eq("perfil", form.getPerfilUsuario()));        	
        
        crit.setProjection(Projections.property("usuario.id"));        
		return crit.list();
	}
	
	@Override
	public List<?> filterListaIdsUsuariosSeguindo(Long idUsuario , String perfilUsuario) { //  lista todos os Ids dos Usuarios que estao me seguindo
		Criteria crit = session().createCriteria(Seguidor.class);
		Criteria critUsuario = crit.createCriteria("usuarioSeguido");
		critUsuario.add(Restrictions.eq("id", idUsuario));
        crit.setProjection(Projections.property("usuario.id"));        
        if (! StringUtils.isNullOrEmpty(perfilUsuario) ){
        	critUsuario.add(Restrictions.eq("perfil", perfilUsuario));        	
        }
		return crit.list();
	}

	@Override
	public long findQuantSeguidoresByIdUsuarioSeguido(Long idUsuario) {
		Criteria crit = session().createCriteria(Seguidor.class);
		crit.createCriteria("usuarioSeguido").add(Restrictions.eq("id", idUsuario));		
		ProjectionList projList = Projections.projectionList();		
		crit.setProjection(Projections.rowCount());
		return (long)crit.uniqueResult();
	}

	@Override
	public List<?> filterListaIdsUsuariosSeguidores(RelatorioForm form) {
		Criteria crit = session().createCriteria(Seguidor.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", form.getUsuarioSessao().getId()));
		
		Criteria critUsuarioSeguidor = crit.createCriteria("usuario");		
		if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
			critUsuarioSeguidor.add(Restrictions.eq("sexo", form.getSexo()));
			
        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) ) 
        	critUsuarioSeguidor.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
        
        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
        	critUsuarioSeguidor.add(Restrictions.eq("perfil", form.getPerfilUsuario()));        	
        
        crit.setProjection(Projections.property("usuarioSeguido.id"));        
		return crit.list();
	}
	
	@Override
	public List<?> filterListaIdsUsuariosSeguidores(Long idUsuario, String perfilUsuario) {
		Criteria crit = session().createCriteria(Seguidor.class);        
        Criteria critUsuario = crit.createCriteria("usuario");
		critUsuario.add(Restrictions.eq("id", idUsuario));
		crit.setProjection(Projections.property("usuarioSeguido.id"));        
        if (! StringUtils.isNullOrEmpty(perfilUsuario) ){
        	critUsuario.add(Restrictions.eq("perfil", perfilUsuario));        	
        }        
		return crit.list();
	}

}
