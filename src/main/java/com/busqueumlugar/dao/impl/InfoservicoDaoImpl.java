package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.CidadesDao;
import com.busqueumlugar.dao.InfoservicoDao;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.model.Imovelcompartilhado;
import com.busqueumlugar.model.Infoservico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class InfoservicoDaoImpl extends GenericDAOImpl<Infoservico, Long> implements InfoservicoDao {

	private static final Logger log = LoggerFactory.getLogger(InfoservicoDaoImpl.class);
	
	public InfoservicoDaoImpl() {
		super(Infoservico.class);
	}
	
	public Infoservico findInfoservicoById(Long id) {
		return (Infoservico)session().createCriteria(Infoservico.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Infoservico> findInfoServicoByIdParametroServico(Long idParametro) {
		return (List<Infoservico>)session().createCriteria(Infoservico.class)
				.add(Restrictions.eq("idParamServico", idParametro)).list();
	}

	@Override
	public Infoservico findInfoservicoByIdParamByOpcaoTempoByFormaPagto(Long idParam, String opcaoTempoPagto) {
		return (Infoservico)session().createCriteria(Infoservico.class)
				.add(Restrictions.eq("idParamServico", idParam))
				.add(Restrictions.eq("itemInfoServico", opcaoTempoPagto)).uniqueResult();
	}

	@Override
	public List<Infoservico> filterInfoServicoByIdParamServicoPorIdFormaPagto(Long idParam, Long idFormaPagto) {
		return (List<Infoservico>)session().createCriteria(Infoservico.class)
				.add(Restrictions.eq("idParamServico", idParam))
				.add(Restrictions.eq("idFormaPagamento", idFormaPagto)).list();
	}

}
