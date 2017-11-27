package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.AtividadesDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.model.Atividades;
import com.busqueumlugar.model.Imovelvisualizado;

@Repository
public class AtividadesDaoImpl extends GenericDAOImpl<Atividades, Long>  implements AtividadesDao {

	public AtividadesDaoImpl() {
		super(Atividades.class);
	}

	@Override
	public Atividades findAtividadesById(Long id) {
		return (Atividades)session().createCriteria(Atividades.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Atividades> findAtividadesByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Atividades.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		return (List<Atividades>)crit.list();	
	}

	@Override
	public List<Atividades> findAtividadesByIdImovelByIdUsuario(Long idImovel,	Long idUsuario) {
		Criteria crit = session().createCriteria(Atividades.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		return (List<Atividades>)crit.list();	
	}

}
