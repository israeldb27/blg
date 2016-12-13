package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.EstadosDao;
import com.busqueumlugar.dao.FormapagamentoDao;
import com.busqueumlugar.model.Estados;
import com.busqueumlugar.model.Formapagamento;


@Repository
public class FormapagamentoDaoImpl extends GenericDAOImpl<Formapagamento, Long> implements FormapagamentoDao {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioDaoImpl.class);
	
	public FormapagamentoDaoImpl() {
		super(Formapagamento.class);
	}
	

	@Override
	public Formapagamento findFormapagamentoById(Long id) {
		return (Formapagamento)session().createCriteria(Formapagamento.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	

}
