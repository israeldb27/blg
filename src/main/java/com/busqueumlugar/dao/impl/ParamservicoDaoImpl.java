package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.busqueumlugar.dao.EstadosDao;
import com.busqueumlugar.dao.ParamservicoDao;
import com.busqueumlugar.model.Estados;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Paramservico;



@Repository
public class ParamservicoDaoImpl extends GenericDAOImpl<Paramservico, Long> implements ParamservicoDao{

	private static final Logger log = LoggerFactory.getLogger(ParamservicoDaoImpl.class);

	public ParamservicoDaoImpl() {
		super(Paramservico.class);
	}

	public Paramservico findParamservicoById(Long id) {
		return (Paramservico)session().createCriteria(Paramservico.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public Paramservico findParamservicoPorNome(String nome) {
		return (Paramservico)session().createCriteria(Paramservico.class)
				.add(Restrictions.eq("valueServico", nome)).uniqueResult();
	}

	@Override
	public List<Paramservico> findParamservicoPorTipo(String tipo) {
		return (List<Paramservico>)session().createCriteria(Paramservico.class)
				.add(Restrictions.eq("tipoParamServico", tipo)).list();
	}

	@Override
	public List<Paramservico> findAllParamServicos() {
		return (List<Paramservico>)session().createCriteria(Paramservico.class).list();
	}
	
	@Override
	public List<Paramservico> findParamServicoByValor(String valor) {
		return (List<Paramservico>)session().createCriteria(Paramservico.class)
				.add(Restrictions.like("valueServico", "%" + valor + "%")).list();
	}


	@Override
	public List<Paramservico> findAllParametrosServicos() {
		Criteria criteria = session().createCriteria(Paramservico.class);
		criteria.addOrder(Order.desc("dataCriacao"));
		return (List<Paramservico>)criteria.list();
	}

}
