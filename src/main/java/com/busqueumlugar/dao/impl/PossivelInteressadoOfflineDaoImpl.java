package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.PossivelInteressadoOfflineDao;
import com.busqueumlugar.model.Atividades;
import com.busqueumlugar.model.PossivelInteressadoOffline;


@Repository
public class PossivelInteressadoOfflineDaoImpl extends GenericDAOImpl<PossivelInteressadoOffline, Long> implements PossivelInteressadoOfflineDao {

	public PossivelInteressadoOfflineDaoImpl() {
		super(PossivelInteressadoOffline.class);
	}
	
	@Override
	public PossivelInteressadoOffline findPossivelInteressadoOfflineById(Long id) {
		return (PossivelInteressadoOffline)session().createCriteria(PossivelInteressadoOffline.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<PossivelInteressadoOffline> findPossivelInteressadoOfflineByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(PossivelInteressadoOffline.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		return (List<PossivelInteressadoOffline>)crit.list();	
	}

	@Override
	public List<PossivelInteressadoOffline> findPossivelInteressadoOfflineByIdImovelByQuant(Long idImovel, int quantMaxLista) {
		Criteria crit = session().createCriteria(PossivelInteressadoOffline.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.addOrder(Order.desc("dataCadastro"));
		crit.setMaxResults(quantMaxLista);
		return (List<PossivelInteressadoOffline>)crit.list();	
	}

}
