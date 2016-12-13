package com.busqueumlugar.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.ParametrosIniciaisDao;
import com.busqueumlugar.model.ParametrosIniciais;

@Repository
public class ParametrosIniciaisDaoImpl extends GenericDAOImpl<ParametrosIniciais, Long> implements ParametrosIniciaisDao{
	
	private static final Logger log = LoggerFactory.getLogger(ParamservicoDaoImpl.class);

	public ParametrosIniciaisDaoImpl() {
		super(ParametrosIniciais.class);
	}


	@Override
	public ParametrosIniciais findParametrosIniciaisById(Long id) {

		return (ParametrosIniciais)session().createCriteria(ParametrosIniciais.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}


	@Override
	public ParametrosIniciais findParametrosIniciaisByNome(String nomeParametro) {
		return (ParametrosIniciais)session().createCriteria(ParametrosIniciais.class)
				.add(Restrictions.eq("label", nomeParametro)).uniqueResult();
	}

}
