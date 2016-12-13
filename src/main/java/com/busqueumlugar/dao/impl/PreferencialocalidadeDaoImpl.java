package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.CidadesDao;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.dao.PreferencialocalidadeDao;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.form.PreferencialocalidadeForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.model.Imovelcompartilhado;
import com.busqueumlugar.model.Nota;
import com.busqueumlugar.model.Preferencialocalidade;
import com.busqueumlugar.model.Usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PreferencialocalidadeDaoImpl extends GenericDAOImpl<Preferencialocalidade, Long> implements PreferencialocalidadeDao {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioDaoImpl.class);
	
	public PreferencialocalidadeDaoImpl() {
		super(Preferencialocalidade.class);
	}
	
	public Preferencialocalidade findPreferencialocalidadeById(Long id) {
		return (Preferencialocalidade)session().createCriteria(Preferencialocalidade.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Preferencialocalidade> findPreferencialocalidadeByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Preferencialocalidade.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		return crit.list();		
	}

	@Override
	public List<Preferencialocalidade> findPreferencialocalidade(Imovel imovel) {
		
		Criteria crit = session().createCriteria(Preferencialocalidade.class);
		
		if ( imovel.getIdEstado() > 0 ) {
			 crit.add(Restrictions.eq("idEstado", imovel.getIdEstado()));
			 
			 if ( imovel.getIdCidade() > 0 )
				 crit.add(Restrictions.eq("idCidade", imovel.getIdCidade()));		            
		        
		     if ( imovel.getIdBairro() > 0 )
		    	 crit.add(Restrictions.eq("idBairro", imovel.getIdBairro()));
		 }	

         if ( imovel.getTipoImovel() != null )
        	 crit.add(Restrictions.eq("tipoImovel", imovel.getTipoImovel()));
         
         if ( imovel.getAcao() != null )
        	 crit.add(Restrictions.eq("acao", imovel.getAcao() ));

		return (List<Preferencialocalidade>) crit.list();
	}

	@Override
	public List<Usuario> findPreferencialocalidadeSemDuplicidadeUsuario(UsuarioForm form) {

		Criteria crit = session().createCriteria(Preferencialocalidade.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.distinct(Projections.property("usuario")));

		if ( form.getIdEstado() > 0 )
			 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
		 
        if ( form.getIdCidade() > 0 )
       	 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));
        
        if ( form.getIdBairro() > 0 )
       	 crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
        
        if ( form.getQtdGaragem() != null &&  ! form.getQtdGaragem().equals(""))
          	 crit.add(Restrictions.eq("quantGaragem", Integer.parseInt(form.getQtdGaragem())));
        
        if ( form.getQtdQuartos() != null &&  ! form.getQtdQuartos().equals(""))
         	 crit.add(Restrictions.eq("quantQuartos", Integer.parseInt(form.getQtdQuartos())));
        
        if ( form.getQtdGaragem() != null &&  ! form.getQtdGaragem().equals(""))
         	 crit.add(Restrictions.eq("quantGaragem", Integer.parseInt(form.getQtdGaragem())));

        if ( form.getQtdSuites() != null &&  ! form.getQtdSuites().equals(""))
        	 crit.add(Restrictions.eq("quantSuites", Integer.parseInt(form.getQtdSuites())));
        
        if ( form.getTipoImovel() != null && ! form.getTipoImovel().equals("") && ! form.getTipoImovel().equals("-1"))
       	 crit.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
        
        if ( form.getAcao() != null && ! form.getAcao().equals("") && ! form.getAcao().equals("-1"))
       	 crit.add(Restrictions.eq("acao", form.getAcao() ));
        
        Criteria critUsuario = crit.createCriteria("usuario");
        critUsuario.add(Restrictions.ne("perfil", PerfilUsuarioOpcaoEnum.ADMIN.getRotulo()));
        critUsuario.add(Restrictions.eq("ativado", "S"));        
        
        crit.setProjection(projList);
		return crit.list();
	}

	@Override
	public Preferencialocalidade findPreferenciaRandomByIdUsuario(Long idUsuario) {

		Criteria crit = session().createCriteria(Preferencialocalidade.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.sqlRestriction("1=1 order by rand()"));
		crit.setMaxResults(1);
		return (Preferencialocalidade)crit.uniqueResult();
	}



	@Override
	public List<Imovel> findImoveisByPrefLocalidadeByUsuarioByIndex(UsuarioForm user, int index, int regra) {
		
		Criteria critImovel = session().createCriteria(Imovel.class, "im" );	
		critImovel.createCriteria("usuario").add(Restrictions.ne("id", user.getId()));
		critImovel.addOrder(Order.desc("dataUltimaAtualizacao"));
		
		if ( regra == 1){
			DetachedCriteria dtPref1 = DetachedCriteria.forClass(Preferencialocalidade.class, "pref1");
		    dtPref1.createCriteria("usuario").add(Restrictions.eq("id", user.getId()));	    
		    dtPref1.add(Restrictions.eqProperty("im.idEstado",   "pref1.idEstado"));
		    dtPref1.add(Restrictions.eqProperty("im.tipoImovel", "pref1.tipoImovel"));
		    
		    critImovel.add(Subqueries.exists(dtPref1.setProjection(Projections.property("pref1.idEstado"))));
		    
		    critImovel.setFirstResult(index);
			critImovel.setMaxResults(4);
			return critImovel.list();		    
		}
		else if ( regra == 2){
			DetachedCriteria dtPref2 = DetachedCriteria.forClass(Preferencialocalidade.class, "pref2");
			dtPref2.createCriteria("usuario").add(Restrictions.eq("id", user.getId()));	    
			dtPref2.add(Restrictions.eqProperty("im.idEstado",   "pref2.idEstado"));
			dtPref2.add(Restrictions.eqProperty("im.idCidade",   "pref2.idCidade"));
			dtPref2.add(Restrictions.eqProperty("im.tipoImovel", "pref2.tipoImovel"));
		    
		    critImovel.add(Subqueries.exists(dtPref2.setProjection(Projections.property("pref2.idEstado"))));
		    
		    critImovel.setFirstResult(index);
			critImovel.setMaxResults(4);
			return critImovel.list();	
		}
		else if ( regra == 3){
			DetachedCriteria dtPref3 = DetachedCriteria.forClass(Preferencialocalidade.class, "pref3");
			dtPref3.createCriteria("usuario").add(Restrictions.eq("id", user.getId()));	    
			dtPref3.add(Restrictions.eqProperty("im.idEstado",   "pref3.idEstado"));
			dtPref3.add(Restrictions.eqProperty("im.idCidade",   "pref3.idCidade"));
			dtPref3.add(Restrictions.eqProperty("im.tipoImovel", "pref3.tipoImovel"));
			dtPref3.add(Restrictions.eqProperty("im.acao", 		 "pref3.acao"));	    
		    
		    critImovel.add(Subqueries.exists(dtPref3.setProjection(Projections.property("pref3.idEstado"))));
		    
		    critImovel.setFirstResult(index);
			critImovel.setMaxResults(4);
			return critImovel.list();		    
		}
		
		else if ( regra == 4){

			DetachedCriteria dtPref4 = DetachedCriteria.forClass(Preferencialocalidade.class, "pref4");
		    dtPref4.createCriteria("usuario").add(Restrictions.eq("id", user.getId()));	    
		    dtPref4.add(Restrictions.eqProperty("im.idEstado",   "pref4.idEstado"));
		    dtPref4.add(Restrictions.eqProperty("im.idCidade",   "pref4.idCidade"));
		    dtPref4.add(Restrictions.eqProperty("im.idBairro",   "pref4.idBairro"));
		    dtPref4.add(Restrictions.eqProperty("im.tipoImovel", "pref4.tipoImovel"));
		    dtPref4.add(Restrictions.eqProperty("im.acao", 		 "pref4.acao"));
				    
		    critImovel.add(Subqueries.exists(dtPref4.setProjection(Projections.property("pref4.idEstado"))));
		    
		    critImovel.setFirstResult(index);
			critImovel.setMaxResults(4);
			return critImovel.list();
		}
		
		else if ( regra == 5){
			DetachedCriteria dtPref5 = DetachedCriteria.forClass(Preferencialocalidade.class, "pref5");
		    dtPref5.createCriteria("usuario").add(Restrictions.eq("id", user.getId()));	    
		    dtPref5.add(Restrictions.eqProperty("im.idEstado",   	"pref5.idEstado"));
		    dtPref5.add(Restrictions.eqProperty("im.idCidade",   	"pref5.idCidade"));
		    dtPref5.add(Restrictions.eqProperty("im.idBairro",   	"pref5.idBairro"));
		    dtPref5.add(Restrictions.eqProperty("im.tipoImovel", 	"pref5.tipoImovel"));
		    dtPref5.add(Restrictions.eqProperty("im.acao", 		 	"pref5.acao"));
		    dtPref5.add(Restrictions.eqProperty("im.quantQuartos",  "pref5.quantQuartos"));
		    dtPref5.add(Restrictions.eqProperty("im.quantGaragem",  "pref5.quantGaragem"));
		    dtPref5.add(Restrictions.eqProperty("im.quantBanheiro", "pref5.quantBanheiro"));
		    
		    critImovel.add(Subqueries.exists(dtPref5.setProjection(Projections.property("pref5.idEstado"))));
		    
		    critImovel.setFirstResult(index);
			critImovel.setMaxResults(4);
			return critImovel.list();
		}
		else
			return null;

	}

	@Override
	public Preferencialocalidade findPreferencialocalidadeByUsuarioByIndexByAleatorio(List<Long> listaIds, int index, boolean isAleatorio) {

		Criteria crit = session().createCriteria(Preferencialocalidade.class);
		
		if ( isAleatorio ){
			crit.createCriteria("usuario").add(Restrictions.not(Restrictions.in("id",  listaIds)));	
		}
		else
			crit.createCriteria("usuario").add(Restrictions.in("id",  listaIds));
		
		crit.addOrder(Order.desc("dataCadastro"));
		crit.setFirstResult(index);
		crit.setMaxResults(1);
		return (Preferencialocalidade)crit.uniqueResult();
	}
	
	@Override
	public Preferencialocalidade findPreferencialocalidadeByUsuarioByIndexByAleatorio(int index) {

		Criteria crit = session().createCriteria(Preferencialocalidade.class);
		crit.addOrder(Order.desc("dataCadastro"));
		crit.setFirstResult(index);
		crit.setMaxResults(1);
		return (Preferencialocalidade)crit.uniqueResult();
	}

}
