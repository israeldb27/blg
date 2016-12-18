package com.busqueumlugar.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.MensagemDao;
import com.busqueumlugar.dao.ServicoDao;
import com.busqueumlugar.enumerador.StatusPagtoOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.model.Mensagem;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ServicoDaoImpl extends GenericDAOImpl<Servico, Long> implements ServicoDao {

	private static final Logger log = LoggerFactory.getLogger(ServicoDaoImpl.class);
    
	public ServicoDaoImpl() {
		super(Servico.class);
	}


	@Override
	public Servico findServicoById(Long id) {
		return (Servico)session().createCriteria(Servico.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Servico> findServicoPagoPorIdUsuario(Long idUsuario, String descServico, Date dataAtual) {
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("descServico", descServico));
		crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
		crit.add(Restrictions.ge("dataFimServico", dataAtual));		
		return crit.list();
	}

	@Override
	public List<Servico> findServicoPorIdImovel(Long idImovel) {		
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));				
		return crit.list();
	}

	@Override
	public List<Servico> findServicosByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		return crit.list();	
	}

	
	@Override
	public List findServicosMaisPorPeriodo(Date dataInicio,	Date dataFim, String statusServico) {
		Criteria crit = session().createCriteria(Servico.class);
		if ( statusServico.equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", dataInicio));
			crit.add(Restrictions.le("dataSolicitacao", dataFim));	
		}
		else if ( statusServico.equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", dataInicio));
			crit.add(Restrictions.le("dataPagto", dataFim));
			crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			crit.add(Restrictions.ne("formaPgto", "concessao"));
		}
		else if ( statusServico.equals("concessao")){
			crit.add(Restrictions.ge("dataPagto", dataInicio));
			crit.add(Restrictions.le("dataPagto", dataFim));		
			crit.add(Restrictions.eq("formaPgto", "concessao"));
		}		
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("descServico"));
		projList.add(Projections.countDistinct(("descServico")).as("quant"));
		crit.setProjection(projList);
		crit.addOrder(Order.desc("quant"));
		return crit.list();		
	}
	
	
	@Override
	public double calcularVolumeFinanceiroMaisSolicitadosMaisPagosPorPeriodoPorTipoServico(Date dataInicio, Date dataFim, String statusServico, String tipoServico) {

		Criteria crit = session().createCriteria(Servico.class);
		crit.add(Restrictions.eq("statusPgto", statusServico));
		if (!StringUtils.isNullOrEmpty(tipoServico))
			crit.add(Restrictions.eq("tipoServico", tipoServico));		
		
		if ( statusServico.equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", dataInicio));
			crit.add(Restrictions.le("dataSolicitacao", dataFim));	
		}
		else if ( statusServico.equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", dataInicio));
			crit.add(Restrictions.le("dataPagto", dataFim));
			crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			crit.add(Restrictions.ne("formaPgto", "concessao"));
		}
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.sum(("valorServico")).as("valorTotal"));		
		crit.setProjection(projList);
		return (Double) crit.uniqueResult();
	}

	@Override
	public List findUsuariosVolFinanceiroServico(AdministracaoForm form) {		
		
		Criteria crit = session().createCriteria(Servico.class);			
		if ( form.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));	
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));
			crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			crit.add(Restrictions.ne("formaPgto", "concessao"));
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));			
			crit.add(Restrictions.eq("formaPgto", "concessao"));
		}		
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuario.id"));
		projList.add(Projections.sum(("valorServico")).as("valorTotal"));
		crit.setProjection(projList);
		return crit.list();		
	}


	@Override
	public List findServicosVolFinanceiroServico(Date dataInicio, Date dataFim, String statusServico) {

		Criteria crit = session().createCriteria(Servico.class);
		if (! StringUtils.isNullOrEmpty(statusServico))
			crit.add(Restrictions.eq("statusPgto", statusServico));
		
		if ( statusServico.equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", dataInicio));
			crit.add(Restrictions.le("dataSolicitacao", dataFim));
		}
		else if ( statusServico.equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", dataInicio));
			crit.add(Restrictions.le("dataPagto", dataFim));
		}	
		else if ( statusServico.equals(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", dataInicio));
			crit.add(Restrictions.le("dataPagto", dataFim));
			crit.add(Restrictions.eq("formaPgto", "concessao"));
		}
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("descServico"));
		projList.add(Projections.sum(("valorServico")).as("valorTotal"));
		crit.setProjection(projList);
		return crit.list();
	}

	@Override
	public Servico findServicoByIdParamServicoByData(long idParamServico, long idUsuario, Date dtAtual) {

		Criteria crit = session().createCriteria(Servico.class);		
		crit.createCriteria("paramServico").add(Restrictions.eq("id", idParamServico));
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
		crit.add(Restrictions.le("dataPagto", dtAtual));
		crit.add(Restrictions.ge("dataFimServico", dtAtual));
		crit.add(Restrictions.eq("tipoServico", "U"));
		return (Servico)crit.uniqueResult();
	}

	@Override
	public Servico findServicoByLabelServicoServicoByData(String nomeServico,long idUsuario, Date dtAtual) {
		Criteria crit = session().createCriteria(Servico.class);
		crit.add(Restrictions.ge("descServico", nomeServico));
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
		crit.add(Restrictions.le("dataPagto", dtAtual));
		crit.add(Restrictions.ge("dataFimServico", dtAtual));
		return (Servico)crit.uniqueResult();
	}

	@Override
	public double calcularVolumeFinanceiroMaisConcedidosPorPeriodo(	Date dataInicio, Date dataFim) {

		Criteria crit = session().createCriteria(Servico.class);
		crit.add(Restrictions.ge("dataPagto", dataInicio));
		crit.add(Restrictions.le("dataPagto", dataFim));		
		crit.add(Restrictions.eq("formaPgto", "concessao"));
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.sum(("valorServico")).as("valorTotal"));		
		crit.setProjection(projList);
		return (Double) crit.uniqueResult();
	}


	@Override
	public List<Servico> findServicosByIdUsuarioByAssocPlano(Long idUsuario, String assocPlano) {		
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("associadoPlano", assocPlano));
		return crit.list();
	}

	@Override
	public List<Servico> findServicosByIdUsuarioByTipoServico(Long idUsuario, String tipoServico) {
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("tipoServico", tipoServico));
		return crit.list();
	}
	
	@Override
	public List<Servico> findServicosByPeriodoByTipoServico(AdministracaoForm form, String tipoServico) {
		
		Criteria crit = session().createCriteria(Servico.class);
		crit.add(Restrictions.eq("tipoServico", tipoServico));		
		crit.add(Restrictions.eq("statusPgto", form.getStatus()));		
		
		if ( form.getStatus().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));
		}		
		return (List<Servico>) crit.list();
	}

	@Override
	public Servico findServicoAssinaturaSolicitadaByIdUsuario(Long idUsuario, String tipoAssinatura) {		
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()));
		crit.add(Restrictions.eq("tipoServico", tipoAssinatura));
		return (Servico)crit.uniqueResult();
	}

	@Override
	public Servico findServicoRelatorioByIdUsuario(Long idUsuario,	String tipoRelatorio, Date dtCorrente) {		
		Criterion crit1 = Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo());
		Criterion crit2 = Restrictions.eq("statusPgto", "concedido");
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.ge("dataFimServico", dtCorrente));
		crit.add(Restrictions.eq("descServico", tipoRelatorio));
		crit.add(Restrictions.or(crit1, crit2));
		return (Servico)crit.uniqueResult();
	}
	
	@Override
	public List findTotalServicosPorPeriodo(AdministracaoForm form) {

		Criteria crit = session().createCriteria(Servico.class);
		
		if (! StringUtils.isNullOrEmpty(form.getStatus()))
			crit.add(Restrictions.eq("statusPgto", form.getStatus()));
		
		if ( form.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));		
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));			
			crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			crit.add(Restrictions.ne("formaPgto", "concessao"));
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));			
			crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			crit.add(Restrictions.eq("formaPgto", "concessao"));			
		}
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("descServico"));										   
		projList.add(Projections.count(("descServico")).as("quant"));
		crit.setProjection(projList);
		crit.addOrder(Order.desc("quant"));
		return crit.list();
	}

	
	
	@Override
	public List findVolumeFinanceiroServicos(AdministracaoForm form) {

		Criteria crit = session().createCriteria(Servico.class);
		crit.add(Restrictions.eq("statusPgto", form.getStatus()));
		if ( form.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));		
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));		
			crit.add(Restrictions.eq("statusPgto", form.getStatus()));
			crit.add(Restrictions.ne("formaPgto", "concessao"));
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));		
			crit.add(Restrictions.eq("statusPgto", form.getStatus()));
			crit.add(Restrictions.eq("formaPgto", "concessao"));
		}
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("descServico"));		
		projList.add(Projections.sum(("valorServico")).as("valorTotal"));
		projList.add(Projections.groupProperty("descServico"));		
		crit.setProjection(projList);
		crit.addOrder(Order.desc("valorTotal"));
		return crit.list();
	}

	@Override
	public Servico findLastServicoGeradoByIdUsuario(long idUsuario,	String descServico) {
		
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()));
		crit.add(Restrictions.eq("descServico", descServico));
		crit.add(Restrictions.eq("dataSolicitacao", this.recuperarDataSolicitacaoMaxPorUsuarioPorStatusPagtoPorDescServico(idUsuario, StatusPagtoOpcaoEnum.SOLICITADO.getRotulo(), descServico )));
		crit.setMaxResults(1);
        return (Servico)crit.uniqueResult();
	}

	private Date recuperarDataSolicitacaoMaxPorUsuarioPorStatusPagtoPorDescServico(Long idUsuario, String statusFormaPagto, String descServico) {

		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()));
		crit.add(Restrictions.eq("descServico", descServico));		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.max(("dataSolicitacao")).as("dataSolicitacaoMax"));			
		crit.setProjection(projList);
		return (Date)crit.uniqueResult();
	}

	@Override
	public List<Servico> findServicoByIdUsuarioByStatus(Long idUsuario,	String status) {		
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusPgto", status));
		return (List<Servico>)crit.list();
	}

	@Override
	public Servico findServicoByIdParamServicoByIdUsuarioByStatusByTipoServico(	long idParamServico, 
																				long idUsuario, 
																				String status,
																				String tipoServico) {
		Criteria crit = session().createCriteria(Servico.class);		
		crit.createCriteria("paramServico").add(Restrictions.eq("id", idParamServico));
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("statusPgto", status));	
		crit.add(Restrictions.eq("tipoServico", tipoServico));
		return (Servico)crit.uniqueResult();
	}

	@Override
	public Servico findSolAssinaturaByIdUsuario(Long idUsuario,	String tipoAssinatura) {
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		Criterion statusPagto1 	   = Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.SOLICITADO.getRotulo());
		Criterion statusPagto2 	   = Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.AGUARDANDO.getRotulo());
		LogicalExpression orExp    = Restrictions.or(statusPagto1, statusPagto2);
		crit.add(Restrictions.eq("descServico", tipoAssinatura));
		crit.setMaxResults(1);
		return (Servico) crit.uniqueResult();
	}

	@Override
	public Servico findServicoByToken(String token) {		
		Criteria crit = session().createCriteria(Servico.class);
		crit.add(Restrictions.eq("token", token));	
		return (Servico) crit.uniqueResult();
	}

	@Override
	public List<Servico> findServicosByIdPlanoUsuario(Long idPlanoUsuario) {
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("planoUsuario").add(Restrictions.eq("id", idPlanoUsuario));		
		return (List<Servico>)crit.list();	
	}


	@Override
	public long findTotalTipoServicoPorPeriodo(AdministracaoForm form) {
		Criteria crit = session().createCriteria(Servico.class);
		
		if (! StringUtils.isNullOrEmpty(form.getStatus()))
			crit.add(Restrictions.eq("statusPgto", form.getStatus()));
		
		if ( form.getStatus().equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
			crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));		
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));			
			crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			crit.add(Restrictions.ne("formaPgto", "concessao"));
		}
		else if ( form.getStatus().equals(StatusPagtoOpcaoEnum.CONCEDIDO.getRotulo())){
			crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
			crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));			
			crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			crit.add(Restrictions.eq("formaPgto", "concessao"));			
		}
		
		ProjectionList projList = Projections.projectionList();												   
		projList.add(Projections.count(("descServico")).as("quant"));
		crit.setProjection(projList);
		crit.addOrder(Order.desc("quant"));
		return (long)crit.uniqueResult();
	}

}
