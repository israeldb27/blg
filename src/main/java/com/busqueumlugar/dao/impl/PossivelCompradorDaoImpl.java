package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.PossivelCompradorDao;
import com.busqueumlugar.model.Atividades;
import com.busqueumlugar.model.PossivelComprador;
import com.busqueumlugar.model.PossivelCompradorOffline;


@Repository
public class PossivelCompradorDaoImpl extends GenericDAOImpl<PossivelComprador, Long>  implements PossivelCompradorDao{

	public PossivelCompradorDaoImpl() {
		super(PossivelComprador.class);
	}

	@Override
	public PossivelComprador findPossivelCompradorById(Long id) {
		return (PossivelComprador)session().createCriteria(PossivelComprador.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<PossivelComprador> findPossivelCompradorByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(PossivelComprador.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		return (List<PossivelComprador>)crit.list();	
	}

	@Override
	public PossivelComprador findPossivelCompradorByIdUsuarioByIdImovel(Long idUsuario, Long idImovel) {
		Criteria crit = session().createCriteria(PossivelComprador.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.createCriteria("usuarioComprador").add(Restrictions.eq("id", idUsuario));		
		return (PossivelComprador) crit.uniqueResult();
	}


}
