package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.BairrosDao;
import com.busqueumlugar.dao.PlanoDao;
import com.busqueumlugar.model.Bairros;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Plano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class PlanoDaoImpl extends GenericDAOImpl<Plano, Long> implements PlanoDao {
	
	private static final Logger log = LoggerFactory.getLogger(PlanoDaoImpl.class);

	public PlanoDaoImpl() {
		super(Plano.class);
	}


	
	public Plano findPlanoById(Long id) {
		return (Plano)session().createCriteria(Plano.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public Plano findPlanoPorNome(String nome) {
		return (Plano)session().createCriteria(Plano.class)
				.add(Restrictions.eq("nome", nome)).uniqueResult();
	}
	
	
	@Override
	public List<Plano> findPlanoByValor(String valor) {
		return (List<Plano>)session().createCriteria(Plano.class)
				.add(Restrictions.like("nome", "%" + valor + "%")).list();
	}

}
