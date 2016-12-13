package com.busqueumlugar.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.MensagemDao;
import com.busqueumlugar.dao.PlanousuarioDao;
import com.busqueumlugar.enumerador.StatusPagtoOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.model.Mensagem;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.RelatorioQuantPlano;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PlanousuarioDaoImpl extends GenericDAOImpl<Planousuario, Long> implements PlanousuarioDao {
	
	private static final Logger log = LoggerFactory.getLogger(PlanousuarioDaoImpl.class);

	
	public PlanousuarioDaoImpl() {
		super(Planousuario.class);
	}



	@Override
	public Planousuario findPlanousuarioById(Long id) {
		return (Planousuario)session().createCriteria(Planousuario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}


	@Override
	public List<Planousuario> findPlanoUsuarioByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Planousuario.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
		return crit.list();
	}

	
	@Override
	public List findTotalQuantidadePlanos(AdministracaoForm form) {
		
		Criteria crit = session().createCriteria(Planousuario.class);
		crit.add(Restrictions.eq("status", form.getStatus()));
		if (form.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));
		}			
		else if (form.getStatus().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));
		}
			
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("id"));
		projList.add(Projections.count(("id")).as("quant"));
		projList.add(Projections.groupProperty("id"));		
		crit.setProjection(projList);
		crit.addOrder(Order.desc("quant"));        
        return crit.list();
	}
	
	@Override
	public List<RelatorioQuantPlano> findPlanosByNomeUsuario(String buscarUsuario) {
		
		Criteria crit = session().createCriteria(Planousuario.class);
		crit.createCriteria("usuario").add(Restrictions.ilike("nome", "%" + buscarUsuario + "%"));
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("usuario.nome"));
		projList.add(Projections.property("nomePlano"));
		projList.add(Projections.count(("id")).as("quant"));
		projList.add(Projections.groupProperty("usuario.nome"));
		projList.add(Projections.groupProperty("nomePlano"));		
		crit.setProjection(projList);
		crit.addOrder(Order.desc("quant"));
		return (List<RelatorioQuantPlano>)crit.list();
	}

	
	@Override
	public List<RelatorioQuantPlano> findPlanosVolFinanceiro(AdministracaoForm form) {		
		Criteria crit = session().createCriteria(Planousuario.class);
		crit.add(Restrictions.eq("status", form.getStatus()));
		if (form.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));
		}			
		else if (form.getStatus().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));
		}
		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.property("nomePlano"));
		projList.add(Projections.sum(("valorPlano")).as("quant"));		
		projList.add(Projections.groupProperty("nomePlano"));		
		crit.setProjection(projList);
		crit.addOrder(Order.desc("quant"));
		return (List<RelatorioQuantPlano>)crit.list();
	}
	
	@Override
	public List findPlanosUsuariosVolFinanceiro(AdministracaoForm form) {
		
		Criteria crit = session().createCriteria(Planousuario.class);
		crit.add(Restrictions.eq("status", form.getStatus()));
		if (form.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));
		}			
		else if (form.getStatus().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));
		}
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("usuario.id"));
		projList.add(Projections.property("nomePlano"));
		projList.add(Projections.sum(("valorPlano")).as("quant"));
		projList.add(Projections.groupProperty("usuario.id"));
		projList.add(Projections.groupProperty("nomePlano"));		
		crit.setProjection(projList);
		crit.addOrder(Order.desc("quant"));
		return crit.list();
	}


	@Override
	public Planousuario findLastPlanoGeradoByIdUsuario(Long idUsuario) {		
		Criteria crit = session().createCriteria(Planousuario.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()));
		crit.add(Restrictions.eq("dataSolicitacao", this.recuperarMaxDataSolicitacaoPlanoPorUsuarioPorStatus(idUsuario, StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())));
		crit.setMaxResults(1);
        return (Planousuario)crit.uniqueResult();
	}


	private Date recuperarMaxDataSolicitacaoPlanoPorUsuarioPorStatus(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(Planousuario.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", status));
		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.max(("dataSolicitacao")).as("dataSolicitacaoMax"));				
		crit.setProjection(projList);
		return (Date)crit.uniqueResult();
	}


	@Override
	public Planousuario findPlanoUsuarioByIdPlanoByIdUsuario(Long idUsuario, Long idPlanoSelecionado) {
		Criteria crit = session().createCriteria(Planousuario.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("plano").add(Restrictions.eq("id", idPlanoSelecionado));		
		return (Planousuario)crit.uniqueResult();		
	}

}
