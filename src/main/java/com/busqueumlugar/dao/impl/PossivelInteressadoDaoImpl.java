package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.PossivelInteressadoDao;
import com.busqueumlugar.model.Atividades;
import com.busqueumlugar.model.PossivelInteressado;
import com.busqueumlugar.model.PossivelInteressadoOffline;


@Repository
public class PossivelInteressadoDaoImpl extends GenericDAOImpl<PossivelInteressado, Long>  implements PossivelInteressadoDao{

	public PossivelInteressadoDaoImpl() {
		super(PossivelInteressado.class);
	}

	@Override
	public PossivelInteressado findPossivelInteressadoById(Long id) {
		return (PossivelInteressado)session().createCriteria(PossivelInteressado.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<PossivelInteressado> findPossivelInteressadoByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(PossivelInteressado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		return (List<PossivelInteressado>)crit.list();	
	}

	@Override
	public PossivelInteressado findPossivelInteressadoByIdUsuarioByIdImovel(Long idUsuario, Long idImovel) {
		Criteria crit = session().createCriteria(PossivelInteressado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.createCriteria("usuarioInteressado").add(Restrictions.eq("id", idUsuario));		
		return (PossivelInteressado) crit.uniqueResult();
	}

	@Override
	public List<PossivelInteressado> findPossivelInteressadoByIdImovelByQuant(Long idImovel, int quantMaxLista) {
		Criteria crit = session().createCriteria(PossivelInteressado.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.addOrder(Order.desc("dataCadastro"));
		crit.setMaxResults(quantMaxLista);
		return (List<PossivelInteressado>)crit.list();
	}


}
