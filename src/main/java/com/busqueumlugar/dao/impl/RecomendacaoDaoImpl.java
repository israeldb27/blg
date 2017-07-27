package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.RecomendacaoDao;
import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.model.Recomendacao;
import com.busqueumlugar.model.Seguidor;
import com.mysql.jdbc.StringUtils;

@Repository
public class RecomendacaoDaoImpl extends GenericDAOImpl<Recomendacao, Long>  implements RecomendacaoDao{

	private static final Logger log = LoggerFactory.getLogger(RecomendacaoDaoImpl.class);
    

	public RecomendacaoDaoImpl() {
		super(Recomendacao.class);
	}

	@Override
	public Recomendacao findRecomendacaoById(Long idRecomendacao) {
		return (Recomendacao)session().createCriteria(Recomendacao.class)
				.add(Restrictions.eq("id", idRecomendacao)).uniqueResult();		
	}

	@Override
	public List<Recomendacao> findListRecomendacaoByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Recomendacao.class);
		crit.createCriteria("usuarioRecomendado").add(Restrictions.eq("id", idUsuario));
		return (List<Recomendacao>)crit.list();
	}
	
	@Override
	public List<Recomendacao> findListRecomendacaoByIdUsuario(Long idUsuario, int quantMaxExibeMaisListaRecomendacoes) {
		Criteria crit = session().createCriteria(Recomendacao.class);
		crit.createCriteria("usuarioRecomendado").add(Restrictions.eq("id", idUsuario));
		crit.setMaxResults(quantMaxExibeMaisListaRecomendacoes);
		return (List<Recomendacao>)crit.list();
	}
	
	@Override
	public List<Recomendacao> findListRecomendacaoByIdUsuarioNovas(Long idUsuario) {
		Criteria crit = session().createCriteria(Recomendacao.class);
		crit.createCriteria("usuarioRecomendado").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", RecomendacaoStatusEnum.ENVIADO.getRotulo()));
		crit.add(Restrictions.eq("statusLeitura", StatusLeituraEnum.NOVO.getRotulo()));				
		return (List<Recomendacao>)crit.list();
	}

	@Override
	public List<Recomendacao> findListRecomendacaoByIdUsuarioByStatus(Long idUsuarioRecomendado, String status) {
		Criteria crit = session().createCriteria(Recomendacao.class);
		crit.add(Restrictions.eq("status", status));		
		crit.createCriteria("usuarioRecomendado").add(Restrictions.eq("id", idUsuarioRecomendado));		
		return (List<Recomendacao>)crit.list();
	}

	@Override
	public long findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(Long idUsuario, String status, String statusLeitura) {
		Criteria crit = session().createCriteria(Recomendacao.class);
		crit.createCriteria("usuarioRecomendado").add(Restrictions.eq("id", idUsuario));
		
		if (! StringUtils.isNullOrEmpty(status))
			crit.add(Restrictions.eq("status", status));
		
		if (! StringUtils.isNullOrEmpty(statusLeitura))		
			crit.add(Restrictions.eq("statusLeitura", statusLeitura));
		
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.rowCount());		
		crit.setProjection(projList);
		return (long)crit.uniqueResult();		
	}

}
