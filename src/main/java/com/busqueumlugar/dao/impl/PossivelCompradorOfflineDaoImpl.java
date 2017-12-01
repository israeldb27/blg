package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.PossivelCompradorOfflineDao;
import com.busqueumlugar.model.Atividades;
import com.busqueumlugar.model.PossivelCompradorOffline;


@Repository
public class PossivelCompradorOfflineDaoImpl extends GenericDAOImpl<PossivelCompradorOffline, Long> implements PossivelCompradorOfflineDao {

	public PossivelCompradorOfflineDaoImpl() {
		super(PossivelCompradorOffline.class);
	}
	
	@Override
	public PossivelCompradorOffline findPossivelCompradorOfflineById(Long id) {
		return (PossivelCompradorOffline)session().createCriteria(PossivelCompradorOffline.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<PossivelCompradorOffline> findPossivelCompradorOfflineByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(PossivelCompradorOffline.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		return (List<PossivelCompradorOffline>)crit.list();	
	}

}
