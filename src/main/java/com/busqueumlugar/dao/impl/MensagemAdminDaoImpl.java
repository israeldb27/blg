package com.busqueumlugar.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.MensagemAdminDao;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.ItemMensagemAdmin;
import com.busqueumlugar.model.Mensagem;
import com.busqueumlugar.model.MensagemAdmin;


@Repository
public class MensagemAdminDaoImpl extends GenericDAOImpl<MensagemAdmin, Long> implements MensagemAdminDao{
	
	private static final Logger log = LoggerFactory.getLogger(MensagemAdminDaoImpl.class);

	
	public MensagemAdminDaoImpl() {
		super(MensagemAdmin.class);
	}

	@Override
	public MensagemAdmin findMensagemAdminById(Long id) {
		return (MensagemAdmin)session().createCriteria(MensagemAdmin.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MensagemAdmin> findMensagemAdminByIdUsuario(Long idUsuario) {		
		Criteria crit = session().createCriteria(MensagemAdmin.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.addOrder(Order.desc("dataUltimaMensagem"));	
		return (List<MensagemAdmin>)crit.list();				
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MensagemAdmin> findAllMensagensAdminOrderByDataMensagem() {		
		Criteria crit = session().createCriteria(MensagemAdmin.class);
		crit.addOrder(Order.desc("dataUltimaMensagem")); 
		return (List<MensagemAdmin>)crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MensagemAdmin> findAllMensagensAdminOrderByDataMensagemByTipoMensagem(String tipoMensagem) {
		Criteria crit = session().createCriteria(MensagemAdmin.class);
		crit.add(Restrictions.eq("tipoMensagem", tipoMensagem));
		crit.addOrder(Order.desc("dataUltimaMensagem"));			
		return (List<MensagemAdmin>)crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MensagemAdmin> findAllMensagensNovasAdminOrderByDataMensagemBYQuant(int quant) {
		Criteria crit = session().createCriteria(MensagemAdmin.class);
		crit.addOrder(Order.desc("dataUltimaMensagem")); 
		return (List<MensagemAdmin>)crit.list();	
	}

	@Override
	public List<MensagemAdmin> findMensagemAdminByIdUsuarioBYQuant(Long idUsuario, int quantMensagens) {
		Criteria crit = session().createCriteria(MensagemAdmin.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
		crit.addOrder(Order.desc("dataUltimaMensagem"));
		crit.setMaxResults(quantMensagens);
		return (List<MensagemAdmin>)crit.list();
	}

}
