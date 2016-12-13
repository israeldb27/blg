package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.BairrosDao;
import com.busqueumlugar.dao.NotificacaoDao;
import com.busqueumlugar.form.NotificacaoForm;
import com.busqueumlugar.model.Bairros;
import com.busqueumlugar.model.Notificacao;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;
import com.mysql.jdbc.StringUtils;


@Repository
public class NotificacaoDaoImpl extends GenericDAOImpl<Notificacao, Long>  implements NotificacaoDao {
	
	private static final Logger log = LoggerFactory.getLogger(NotificacaoDaoImpl.class);


	public NotificacaoDaoImpl() {
		super(Notificacao.class);
	}


	@Override
	public Notificacao findNotificacaoById(Long id) {
		return (Notificacao)session().createCriteria(Notificacao.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}


	@Override
	public List findQuantNovasNotificacoes(Long idUsuario) {
		Criteria crit = session().createCriteria(Notificacao.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("id"));
		crit.add(Restrictions.eq("usuario.id", idUsuario));
		crit.add(Restrictions.eq("statusLeitura", "novo"));
		crit.setProjection(projList);		
		return crit.list();
	}


	@Override
	public List<Notificacao> findNotificacoesByIdUsuario(Long idUsuario, NotificacaoForm form) {
		Criteria crit = session().createCriteria(Notificacao.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}        
		return crit.addOrder(Order.desc("dataNotificacao")).list();		
	}
	
	@Override
	public List<Notificacao> findNotificacoesByIdUsuarioByStatusLeitura(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(Notificacao.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusLeitura", status));
		return crit.addOrder(Order.desc("dataNotificacao")).list();	
	}


	@Override
	public List<Notificacao> findNotificacoesByIdUsuarioByStatus(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(Notificacao.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("id"));
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusLeitura", status));
		crit.setProjection(projList);
		crit.addOrder(Order.desc("dataNotificacao"));
		return crit.list();
	}


	@Override
	public List<Notificacao> filterNotificacoesByIdUsuario(Long idUsuario, NotificacaoForm form) {
		Criteria crit = session().createCriteria(Notificacao.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("acao", form.getOpcaoFiltro()));
		return crit.addOrder(Order.desc("dataNotificacao")).list();		
	}


	@Override
	public void atualizarStatusLeituraNotificacaoByIdUsuario(Long idUsuario) {
		Session session = this.openSession();		
		session.beginTransaction();
		Query query = session.createSQLQuery("CALL atualizatStatusLeitura(:idUsuario, :tabela)")
							.addEntity(Usuario.class)
							.setParameter("idUsuario", idUsuario)
							.setParameter("tabela", "notificacao");
		int rows = query.executeUpdate();
		session.close();
		
	}

}
