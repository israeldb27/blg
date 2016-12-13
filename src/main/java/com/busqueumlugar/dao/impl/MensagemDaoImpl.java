package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.MensagemDao;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Mensagem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class MensagemDaoImpl extends GenericDAOImpl<Mensagem, Long> implements MensagemDao{

	private static final Logger log = LoggerFactory.getLogger(MensagemDaoImpl.class);

	public MensagemDaoImpl() {
		super(Mensagem.class);
	}

	public Mensagem findMensagemById(Long id) {
		return (Mensagem)session().createCriteria(Mensagem.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Mensagem> findMensagensDE(Long idUsuarioDE) {
		Criteria crit = session().createCriteria(Mensagem.class);
		crit.createCriteria("usuarioDe").add(Restrictions.eq("id", idUsuarioDE));		
		return crit.list();
	}

	@Override
	public List<Mensagem> findMensagensPARA(Long idUsuarioPARA) {
		Criteria crit = session().createCriteria(Mensagem.class);
		crit.createCriteria("usuarioPara").add(Restrictions.eq("id", idUsuarioPARA));		
		return crit.list();		
	}

	@Override
	public List<Mensagem> findAllMensagemByIdUsuario(Long idUsuario) {		
		Criteria crit = session().createCriteria(Mensagem.class);
		Criterion usuarioDe = Restrictions.eq("usuarioDe.id",idUsuario); 
		Criterion usuarioPara = Restrictions.eq("usuarioPara.id",idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioDe,usuarioPara);
		crit.add(orExp); 
		return (List<Mensagem>) crit.list();
	}

	@Override
	public List<Mensagem> findAllMensagemByIdUsuarioOrderByDataMensagem(Long idUsuario) {
		Criteria crit = session().createCriteria(Mensagem.class);
		Criterion usuarioDe = Restrictions.eq("usuarioDe.id",idUsuario); 
		Criterion usuarioPara = Restrictions.eq("usuarioPara.id",idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioDe,usuarioPara);
		crit.add(orExp);
		crit.addOrder(Order.desc("dataMensagem"));
		return (List<Mensagem>) crit.list();
	}
	
	
	@Override
	public List<Mensagem> findAllMensagemNovasByIdUsuarioOrderByDataMensagem(Long idUsuario) {
		Criteria crit = session().createCriteria(Mensagem.class);
		crit.add(Restrictions.eq("status","novo"));
		Criterion usuarioDe = Restrictions.eq("usuarioDe.id",idUsuario); 
		Criterion usuarioPara = Restrictions.eq("usuarioPara.id",idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioDe,usuarioPara);
		crit.add(orExp);
		crit.addOrder(Order.desc("dataMensagem"));
		return (List<Mensagem>) crit.list();
	}
	
	@Override
	public List<Mensagem> findAllMensagemByIdUsuarioOrderByDataMensagem(Long idUsuario, int quantMensagens) {
		Criteria crit = session().createCriteria(Mensagem.class);
		Criterion usuarioDe = Restrictions.eq("usuarioDe.id",idUsuario); 
		Criterion usuarioPara = Restrictions.eq("usuarioPara.id",idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioDe,usuarioPara);
		crit.add(orExp);
		crit.addOrder(Order.desc("dataMensagem"));
		crit.setMaxResults(quantMensagens);
		return (List<Mensagem>) crit.list();
	}

	@Override
	public List<Mensagem> findAllMensagemByIdUsuarioDePara(Long idUsuarioDe, Long idUsuarioPara) {
		
		Criteria crit = session().createCriteria(Mensagem.class);
		Criterion usuarioDe1 	    = Restrictions.eq("usuarioDe.id", idUsuarioDe);
		Criterion usuarioPara1 		= Restrictions.eq("usuarioPara.id", idUsuarioPara);
		LogicalExpression andExp1 	= Restrictions.and(usuarioDe1, usuarioPara1); 
		
		Criterion usuarioDe2 	    = Restrictions.eq("usuarioDe.id", idUsuarioPara);
		Criterion usuarioPara2 		= Restrictions.eq("usuarioPara.id", idUsuarioDe);
		LogicalExpression andExp2 	= Restrictions.and(usuarioDe2, usuarioPara2);		
		
		LogicalExpression orExp = Restrictions.or(andExp1 ,andExp2);
		crit.add(orExp);
		crit.addOrder(Order.desc("dataMensagem"));
		return (List<Mensagem>) crit.list();
	}

	@Override
	public List<Mensagem> findAllMensagemByIdUsuarioDeParaNova(Long idUsuarioDe, Long idUsuarioPara) {		
		Criteria crit = session().createCriteria(Mensagem.class);
		crit.createCriteria("usuarioPara").add(Restrictions.eq("id", idUsuarioPara));		
		crit.createCriteria("usuarioDe").add(Restrictions.eq("id", idUsuarioDe));
		crit.add(Restrictions.eq("status", "novo"));
		return crit.list();
	}

	@Override
	public List<Mensagem> findAllMensagensByIdUsuarioParaAdmin(Long idUsuario) {		
		Criteria crit = session().createCriteria(Mensagem.class);
		crit.add(Restrictions.eq("perfilUsuario", "admin"));
		Criterion usuarioDe = Restrictions.eq("usuarioDe.id",idUsuario); 
		Criterion usuarioPara = Restrictions.eq("usuarioPara.id",idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioDe,usuarioPara);
		crit.add(orExp);		
		crit.addOrder(Order.desc("dataMensagem"));
		return (List<Mensagem>) crit.list();
	}

	@Override
	public List<Mensagem> findAllMensagensTrocadasComAdmin(long idUsuarioPara) {
		Criteria crit = session().createCriteria(Mensagem.class);
		crit.add(Restrictions.eq("perfilUsuario", "admin"));
		Criterion usuarioDe = Restrictions.eq("usuarioDe.id",idUsuarioPara); 
		Criterion usuarioPara = Restrictions.eq("usuarioPara.id",idUsuarioPara); 
		LogicalExpression orExp = Restrictions.or(usuarioDe,usuarioPara);
		crit.add(orExp);		
		crit.addOrder(Order.desc("dataMensagem"));
		return (List<Mensagem>) crit.list();
	}

	@Override
	public List<Mensagem> findExistsNovaMensagem(Long idUsuario) {
		Criteria crit = session().createCriteria(Mensagem.class);
		crit.createCriteria("usuarioPara").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", "novo"));		
		return crit.list();
	}


	@Override
	public List<Mensagem> findAllMensagensByIdUsuarioParaAdminNovas(Long idUsuario) {
		return (List<Mensagem>)session().createCriteria(Mensagem.class)				
				.add(Restrictions.eq("idUsuarioPara", 0))
				.add(Restrictions.eq("perfilUsuario", "admin"))
				.add(Restrictions.eq("status", "novo")).list();
	}

	@Override
	public List<Mensagem> findExistsNovaMensagemAdmin() {
		return (List<Mensagem>)session().createCriteria(Mensagem.class)
				.add(Restrictions.eq("loginUsuarioPara", "admin"))
				.add(Restrictions.eq("status", "novo")).list();
	}

	@Override
	public List<Mensagem> findNovasMensagensByAdmin(Long idUsuario) {
		return (List<Mensagem>)session().createCriteria(Mensagem.class)				
				.add(Restrictions.eq("idUsuarioDe", 0))
				.add(Restrictions.eq("idUsuarioPara", idUsuario))
				.add(Restrictions.eq("perfilUsuario", "admin"))
				.add(Restrictions.eq("status", "novo")).list();
	}

	@Override
	public List<Mensagem> findNovasMensagensToAdmin2(Long idUsuario) {
		return (List<Mensagem>)session().createCriteria(Mensagem.class)
				.add(Restrictions.eq("idUsuarioPara", idUsuario))
				.add(Restrictions.eq("remetenteAdmin", "S"))
				.add(Restrictions.eq("perfilUsuario", "admin"))
				.add(Restrictions.eq("status", "novo")).list();
	}

	@Override
	public List<Mensagem> findAllMensagemByIdUsuarioDeParaNovas(long idUsuarioDe, long idUsuarioPara) {
		
		StringBuffer sql = new StringBuffer(" SELECT m FROM Mensagem m ");
		sql.append(" WHERE (( m.idUsuarioDe = :idUsuarioDe and m.idUsuarioPara = :idUsuarioPara) or  "); 
		sql.append("        ( m.idUsuarioDe = :idUsuarioDe2 and m.idUsuarioPara = :idUsuarioPara2)) and ");
		sql.append(" m.status = :status " );
		sql.append(" order by m.dataMensagem desc ");
		
		Query query = session().createQuery(sql.toString());
        query.setParameter("idUsuarioDe", idUsuarioDe);            
        query.setParameter("idUsuarioPara", idUsuarioPara);
        
        query.setParameter("idUsuarioDe2", idUsuarioPara);            
        query.setParameter("idUsuarioPara2", idUsuarioDe);
        
        query.setParameter("status", "novo");        
		return (List<Mensagem>) query.list();
	}



}
