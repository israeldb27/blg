package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.PlanoDao;
import com.busqueumlugar.dao.RelatorioacessoperfilDao;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.Relatorioacessoperfil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class RelatorioacessoperfilDaoImpl extends GenericDAOImpl<Relatorioacessoperfil, Long> implements RelatorioacessoperfilDao {
	
	private static final Logger log = LoggerFactory.getLogger(RelatorioacessoperfilDaoImpl.class);


	public RelatorioacessoperfilDaoImpl() {
		super(Relatorioacessoperfil.class);
	}


	public Relatorioacessoperfil findRelatorioacessoperfilById(Long id) {
		return (Relatorioacessoperfil)session().createCriteria(Relatorioacessoperfil.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Relatorioacessoperfil> findRelatorioAcessoPerfilByIdRelatorio(Long idRelatorio) {
		return (List<Relatorioacessoperfil>)session().createCriteria(Relatorioacessoperfil.class)
				.add(Restrictions.eq("idRelatorio", idRelatorio)).list();
	}

}
