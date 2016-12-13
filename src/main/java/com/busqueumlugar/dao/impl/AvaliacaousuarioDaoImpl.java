package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.AvaliacaousuarioDao;
import com.busqueumlugar.model.Avaliacaousuario;
import com.busqueumlugar.model.Contato;


@Repository
public class AvaliacaousuarioDaoImpl extends GenericDAOImpl<Avaliacaousuario, Long> implements AvaliacaousuarioDao {

	private static final Logger log = LoggerFactory.getLogger(AvaliacaousuarioDaoImpl.class);
	
	public AvaliacaousuarioDaoImpl() {
		super(Avaliacaousuario.class);
		// TODO Auto-generated constructor stub
	}
   
	@Autowired
    private SessionFactory sessionFactory;    
	
	public Session session(){
        return  this.sessionFactory.getCurrentSession();
     }

    
    public Avaliacaousuario findAvaliacaousuarioById(Long id) {
		return (Avaliacaousuario)session().createCriteria(Avaliacaousuario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	
	public Avaliacaousuario findAvaliacaoUsuarioByIdUsuarioClienteByIdPerfilByStatusAvaliacao(Long idUsuario, Long idPerfil, String statusAvaliacao) {
		Criteria criteria = session().createCriteria(Avaliacaousuario.class);		
		criteria.add(Restrictions.eq("idUsuarioCliente", idUsuario));              
		criteria.add(Restrictions.eq("idUsuario", idPerfil));
		criteria.add(Restrictions.eq("status", statusAvaliacao));
		return (Avaliacaousuario)criteria.uniqueResult();
	}



}
