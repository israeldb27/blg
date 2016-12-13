package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelfotosDao;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelfotos;
import com.busqueumlugar.model.Imovelindicado;
import com.busqueumlugar.model.Nota;

@Repository
public class ImovelfotosDaoImpl extends GenericDAOImpl<Imovelfotos, Long>  implements ImovelfotosDao {

	private static final Logger log = LoggerFactory.getLogger(ImovelfotosDaoImpl.class);

	public ImovelfotosDaoImpl() {
		super(Imovelfotos.class);
	}
	
	@Override
	public Imovelfotos findImovelfotosById(Long id) {
		return (Imovelfotos)session().createCriteria(Imovelfotos.class)
				.add(Restrictions.eq("id", id)).uniqueResult();	
	}

	@Override
	public List<Imovelfotos> findImovelfotosByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Imovelfotos.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		return crit.list();	
	}

}
