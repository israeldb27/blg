package com.busqueumlugar.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.RelatorioDao;
import com.busqueumlugar.enumerador.ServicoValueEnum;
import com.busqueumlugar.enumerador.StatusPagtoOpcaoEnum;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Relatorio;
import com.busqueumlugar.model.RelatorioQuantAssinatura;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Repository
public class RelatorioDaoImpl extends GenericDAOImpl<Relatorio, Long>  implements RelatorioDao {
	
	private static final Logger logger = LoggerFactory.getLogger(RelatorioDaoImpl.class);


	public RelatorioDaoImpl() {
		super(Relatorio.class);
	}


	public Relatorio findRelatorioById(Long id) {
		return (Relatorio)session().createCriteria(Relatorio.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Relatorio> findRelatorioSistemaByNome(String nome) {
		return (List<Relatorio>)session().createCriteria(Relatorio.class)
				.add(Restrictions.like("nome", "%" + nome + "%")).list();
	}

	@Override
	public List listarQuantImoveisCriadosLocalizacaoAcaoTipoImovel(RelatorioForm form, List listaIds) {		

		Criteria crit = session().createCriteria(Imovel.class);
		ProjectionList projList = Projections.projectionList();
		
		if (! CollectionUtils.isEmpty(listaIds) )
			crit.createCriteria("usuario").add(Restrictions.in ("id", listaIds));	
		
		crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFim())));	
		
		if ( form.getIdEstado() > 0 ){
			crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			projList.add(Projections.groupProperty("estado"));
			
			if ( form.getIdCidade() > 0 ) { 
				crit.add(Restrictions.eq("idCidade", form.getIdCidade()));
				projList.add(Projections.groupProperty("cidade"));
			}	
	        
	        if ( form.getIdBairro() > 0 ){
				crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
				projList.add(Projections.groupProperty("bairro"));
	        }	
		}
		
		projList.add(Projections.groupProperty("acao"));
		projList.add(Projections.groupProperty("tipoImovel"));
		projList.add(Projections.rowCount(), "quant");
		crit.addOrder(Order.desc("quant"));
        crit.setProjection(projList);
        crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();
	}

	@Override
	public List recuperarTiposImoveisQuantidade(RelatorioForm form, Long idUsuario) {		
		
		Criteria crit = session().createCriteria(Imovelvisualizado.class, "imv");
		ProjectionList projList = Projections.projectionList();
		
		if ( idUsuario != null && idUsuario.longValue() > 0 )
			crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));		
		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(!StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos"));
		
		if ( isCritImovelExist ){
			Criteria critImovel = crit.createCriteria("imovel", "imovel");
			if ( form.getIdEstado() > 0 ){	
				critImovel.add(Restrictions.eq("idEstado", form.getIdEstado()));
				projList.add(Projections.groupProperty("imovel.estado"));
				
				if ( form.getIdCidade() > 0 ) {
					critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));
					projList.add(Projections.groupProperty("imovel.cidade"));
				}	
		        
		        if ( form.getIdBairro() > 0 ) {
		        	critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
		        	projList.add(Projections.groupProperty("imovel.bairro"));
		        }	
			}
			
			if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));

	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        	critImovel.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
		}
		
		boolean isCritUsuarioExist = (!StringUtils.isNullOrEmpty(form.getSexo())) || 
								     (!StringUtils.isNullOrEmpty(form.getFaixaSalarial())) ||
								     (!StringUtils.isNullOrEmpty(form.getPerfilUsuario()));

		if ( isCritUsuarioExist ) {
			Criteria critUsuario = crit.createCriteria("usuario");
			
			if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
	        	critUsuario.add(Restrictions.eq("sexo", form.getSexo()));         
				
	        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) )
	        	critUsuario.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
	        
	        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
	        	critUsuario.add(Restrictions.eq("perfil", form.getPerfilUsuario()));			
		}		
		
		projList.add(Projections.groupProperty("imovel.acao"));
		projList.add(Projections.groupProperty("imovel.tipoImovel"));
		projList.add(Projections.rowCount(), "quant");		
		crit.setProjection(projList);		
		crit.add(Restrictions.ge("dataVisita", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataVisita", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();	
	}
	

	@Override
	public List recuperarVariacaoPrecoImovel(RelatorioForm form, List listaIds) {
		
		Criteria crit = session().createCriteria(Imovel.class, "imovel");
		ProjectionList projList = Projections.projectionList();	
		if (! CollectionUtils.isEmpty(listaIds) )
			crit.createCriteria("usuario").add(Restrictions.in ("id", listaIds));	
		
		if ( form.getIdEstado() > 0 ){
			crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			projList.add(Projections.groupProperty("estado"));
			
			if ( form.getIdCidade() > 0 ) {
				crit.add(Restrictions.eq("idCidade", form.getIdCidade()));
				projList.add(Projections.groupProperty("cidade"));
			}	
	        
	        if ( form.getIdBairro() > 0 ){
				crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
				projList.add(Projections.groupProperty("bairro"));
	        }	
		}
		
		if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
        	crit.add(Restrictions.eq("acao", form.getAcao()));            

        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
        	crit.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));

        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
        	crit.add(Restrictions.eq("perfilImovel", form.getPerfilImovel()));         
		
		projList.add(Projections.groupProperty("acao"));
		projList.add(Projections.groupProperty("perfilImovel"));
		projList.add(Projections.groupProperty("tipoImovel"));
		projList.add(Projections.sum("valorImovel"), "valorImovel");		
		projList.add(Projections.rowCount(), "quant");
		crit.setProjection(projList);		
		crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("valorImovel"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();	
	}

	@Override
	public List<Relatorio> findRelatorioUsuarioSistemaByItem(String itemRelatorio, String perfil) {

		StringBuffer sql = new StringBuffer("SELECT r FROM Relatorio r, Relatorioacessoperfil rp where ");
        sql.append(" r.id = rp.idRelatorio ");
        sql.append(" and r.tipo = :tipo ");
        sql.append(" and rp.perfilUsuario = :perfil ");

        Query query = session().createQuery(sql.toString());
        query.setParameter("tipo", itemRelatorio); 
        query.setParameter("perfil", perfil);                 
        return  query.list();
	}
	
	
	@Override
	public List findQuantidadeTotalAssinaturasByStatus(RelatorioForm form, String status) {
		
		Criteria crit = session().createCriteria(Servico.class);
		
		if ( !StringUtils.isNullOrEmpty(status) ){
			if ( status.equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
				crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
				crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));
				crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()));
			}
			else if ( status.equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
				crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
				crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));
				crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			}	
		}
				
		crit.add(Restrictions.in("descServico", new String[] {ServicoValueEnum.ASSINATURA_PADRAO.getRotulo(),
															  ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo(),
															  ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo()}));		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("descServico"));
		projList.add(Projections.groupProperty("descServico"));
		projList.add(Projections.rowCount());
		crit.setProjection(projList);
		return crit.list();
	}
	
	
	@Override
	public List findAssinaturaVolFinanceiroByStatus(RelatorioForm form, String status){
		
		Criteria crit = session().createCriteria(Servico.class);
		if ( !StringUtils.isNullOrEmpty(status) ){
			if ( status.equals(StatusPagtoOpcaoEnum.SOLICITADO.getRotulo())){
				crit.add(Restrictions.ge("dataSolicitacao", DateUtil.formataDataBanco(form.getDataInicio())));
				crit.add(Restrictions.le("dataSolicitacao", DateUtil.formataDataBanco(form.getDataFim())));
				crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.SOLICITADO.getRotulo()));
			}
			else if ( status.equals(StatusPagtoOpcaoEnum.PAGO.getRotulo())){
				crit.add(Restrictions.ge("dataPagto", DateUtil.formataDataBanco(form.getDataInicio())));
				crit.add(Restrictions.le("dataPagto", DateUtil.formataDataBanco(form.getDataFim())));
				crit.add(Restrictions.eq("statusPgto", StatusPagtoOpcaoEnum.PAGO.getRotulo()));
			}	
		}
		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.groupProperty("descServico"));
		projList.add(Projections.sum("valorServico"));					
		crit.setProjection(projList);			
		return crit.list();
	}	

	@Override
	public List<RelatorioQuantAssinatura> findAssinaturasByUsuario(	String buscarUsuario) {
		
		Criteria crit = session().createCriteria(Servico.class);
		crit.createCriteria("usuario").add(Restrictions.ilike("nome", "%" + buscarUsuario + "%"));
		crit.add(Restrictions.in("descServico", new String[] {ServicoValueEnum.ASSINATURA_PADRAO.getRotulo(),
															  ServicoValueEnum.ASSINATURA_CORRETOR.getRotulo(),
															  ServicoValueEnum.ASSINATURA_IMOBILIARIA.getRotulo()}));	
		
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.groupProperty("usuario.nome"));
		projList.add(Projections.groupProperty("descServico"));
		projList.add(Projections.rowCount());		
		crit.setProjection(projList);
		return crit.list();	
	}


	@Override
	public List recuperarTiposImoveisQuantidadesPorIdsUsuarios(RelatorioForm form, List listaIds) {
		Criteria crit = session().createCriteria(Imovelvisualizado.class, "imv");
		crit.createCriteria("usuario").add(Restrictions.in ("id", listaIds));		
		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(!StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos"));
		
		if ( isCritImovelExist ){
			Criteria critImovel = crit.createCriteria("imovel", "imovel");
			if ( form.getIdEstado() > 0 ){			
				
				critImovel.add(Restrictions.eq("idEstado", form.getIdEstado()));
				
				if ( form.getIdCidade() > 0 ) 
					critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));
		        
		        if ( form.getIdBairro() > 0 )
		        	critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
			}
			
			if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
	        	critImovel.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));

	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        	critImovel.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
		}
		
		ProjectionList projList = Projections.projectionList();
		if ( isCritImovelExist ){
			if ( form.getIdEstado() > 0 )
				projList.add(Projections.groupProperty("imovel.estado"));
			
			if ( form.getIdCidade() > 0 )
				projList.add(Projections.groupProperty("imovel.cidade"));
			
			if ( form.getIdBairro() > 0 )
				projList.add(Projections.groupProperty("imovel.bairro"));
			
		}
		projList.add(Projections.groupProperty("imovel.acao"));
		projList.add(Projections.groupProperty("imovel.tipoImovel"));
		projList.add(Projections.rowCount(), "quant");		
		crit.setProjection(projList);		
		crit.add(Restrictions.ge("dataVisita", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataVisita", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();	
	}

}
