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

import com.busqueumlugar.dao.ItemMensagemAdminDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.ItemMensagemAdmin;
import com.busqueumlugar.model.Mensagem;
import com.busqueumlugar.util.AppUtil;


@Repository
public class ItemMensagemAdminDaoImpl  extends GenericDAOImpl<ItemMensagemAdmin, Long> implements ItemMensagemAdminDao {
	
	private static final Logger log = LoggerFactory.getLogger(ItemMensagemAdminDaoImpl.class);


	public ItemMensagemAdminDaoImpl() {
		super(ItemMensagemAdmin.class);
	}


	@Override
	public List<ItemMensagemAdmin> findItemMensagemAdminByIdUsuarioByStatusLeitura(	Long idUsuario, String status) {		
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusLeitura", status));
		return crit.list();
	}


	@Override
	public int findQuantNovasItensMensagensByIdMensagemAdmin(Long idMensagemAdmin) {
		
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("mensagemAdmin.id").as("quant"));	
		crit.setProjection(projList);
		crit.add(Restrictions.eq("mensagemAdmin.id", idMensagemAdmin));								  
		crit.add(Restrictions.eq("statusLeitura", StatusLeituraEnum.NOVO.getRotulo()));
		crit.add(Restrictions.eq("remetenteAdmin", "S"));
		return AppUtil.recuperarQuantidadeLista(crit.list());
	}


	@Override
	public List<ItemMensagemAdmin> findItemMensagensAdminByIdMensagemAdmin(Long idMensagemAdmin) {		
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);		
		crit.createCriteria("mensagemAdmin").add(Restrictions.eq("id", idMensagemAdmin));
		crit.addOrder(Order.desc("dataMensagem"));				 
		return crit.list();
	}
	
	@Override
	public List<ItemMensagemAdmin> findItemMensagensAdminNovoByIdMensagemAdmin(Long idMensagemAdmin) {		
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);
		crit.createCriteria("mensagemAdmin.id").add(Restrictions.eq("id", idMensagemAdmin));		
		crit.add(Restrictions.eq("statusLeitura", StatusLeituraEnum.NOVO.getRotulo()));
		crit.add(Restrictions.eq("remetenteAdmin", "N"));						 
		return crit.list();
	}


	@Override
	public int findItemMensagensNovasByIdUsuario(Long idUsuario) {
		
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);
		crit.createCriteria("usuario.id").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusLeitura", StatusLeituraEnum.NOVO.getRotulo()));
		crit.add(Restrictions.eq("remetenteAdmin", "S"));		
		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.count("id").as("quant"));		
		projList.add(Projections.groupProperty("id"));
		crit.setProjection(projList);
		return AppUtil.recuperarQuantidadeLista(crit.list());
	}


	@Override
	public List<ItemMensagemAdmin> findItemMensagensNovasEnviadasParaAdmin() {
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);
		crit.add(Restrictions.eq("statusLeitura", StatusLeituraEnum.NOVO.getRotulo()));
		crit.add(Restrictions.eq("remetenteAdmin", "N"));
		crit.addOrder(Order.desc("dataMensagem"));				 
		return crit.list();
	}

	@Override
	public List<ItemMensagemAdmin> findItemMensagemAdminByStatusLeituraByIdUsuario(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusLeitura", status));
		crit.add(Restrictions.eq("remetenteAdmin", "S"));
		crit.addOrder(Order.desc("dataMensagem"));				 
		return crit.list();
	}


	@Override
	public long findQuantItemMensagemAdminByIdUsuarioByStatusLeitura(Long idUsuario, String statusLeitura) {
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusLeitura", statusLeitura));
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.count("id").as("quant"));	
		crit.setProjection(projList);		
		return (long)crit.uniqueResult();
	}


	@Override
	public long findQuantidadeMensagensAdminEnviadasParaAdmin() {
		Criteria crit = session().createCriteria(ItemMensagemAdmin.class);
		crit.add(Restrictions.eq("statusLeitura", StatusLeituraEnum.NOVO.getRotulo()));
		crit.add(Restrictions.eq("remetenteAdmin", "N"));
		crit.addOrder(Order.desc("dataMensagem"));				 
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.rowCount());	
		crit.setProjection(projList);		
		return (long)crit.uniqueResult();
	}

}
