package com.busqueumlugar.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
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
import org.springframework.util.StringUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImoveldestaqueDao;
import com.busqueumlugar.form.ImoveldestaqueForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Imoveldestaque;
import com.busqueumlugar.model.Imovelfotos;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;


@Repository
public class ImoveldestaqueDaoImpl extends  GenericDAOImpl<Imoveldestaque, Long>  implements ImoveldestaqueDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ImoveldestaqueDaoImpl.class);

	
	public ImoveldestaqueDaoImpl() {
		super(Imoveldestaque.class);
	}

	@Override
	public Imoveldestaque findImoveldestaqueById(Long id) {
		return (Imoveldestaque)session().createCriteria(Imoveldestaque.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Imoveldestaque> findAllImoveisDestaqueByStatus(String status) {
		return (List<Imoveldestaque>)session().createCriteria(Imoveldestaque.class)
				.add(Restrictions.eq("status", status)).list();
	}

	@Override
	public List<Imoveldestaque> findImovelDestaquePorAnuncio(Date dataAtual, String tipo, String status) {		
		return (List<Imoveldestaque>)session().createCriteria(Imoveldestaque.class)
				.add(Restrictions.le("dataInicio", dataAtual))
				.add(Restrictions.ge("dataFim", dataAtual))
				.add(Restrictions.ge("tipo", tipo))
				.add(Restrictions.eq("status", status)).list();
	}
	
	@Override
	public List<Imoveldestaque> findImoveldestaqueByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Imoveldestaque.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		return crit.list();
	}
	
	@Override
	public List<Imoveldestaque> findImoveisDestaqueByDataDestaqueByQuantidade(Date dataDestaque, int quantImoveis) {
		return (List<Imoveldestaque>)session().createCriteria(Imoveldestaque.class)
				.add(Restrictions.eq("tipo", "A"))				 
				.add(Restrictions.eq("dataDestaque", dataDestaque)).setMaxResults(quantImoveis).list();
	}
	
	@Override
	public List<Imoveldestaque> findImoveisDestaqueAnuncioByDataDestaqueByQuantidade(int quantImoveis) {
		Calendar cal = Calendar.getInstance();
		return (List<Imoveldestaque>)session().createCriteria(Imoveldestaque.class)
				.add(Restrictions.eq("tipo", "D"))				
				.add(Restrictions.eq("dataDestaque", cal.getTime())).setMaxResults(quantImoveis).list();
	}

	@Override
	public long findQuantImoveisAnunciosByDia() {
		Calendar cal = Calendar.getInstance();
		Criteria crit = session().createCriteria(Imoveldestaque.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.rowCount());		
		crit.setProjection(projList);
		crit.add(Restrictions.eq("dataDestaque", cal.getTime()));
		return (long)crit.uniqueResult();
	}

	@Override
	public Imoveldestaque findImovelAnuncioByDiaByPosicao(int posicao) {
		Calendar cal = Calendar.getInstance();
		Criteria crit = session().createCriteria(Imoveldestaque.class);		
		crit.add(Restrictions.eq("dataDestaque", cal.getTime()));
		crit.setFirstResult(posicao);
		crit.setMaxResults(1);
		return (Imoveldestaque) crit.uniqueResult();
	}

	@Override
	public List<Imoveldestaque> findImoveisAnuncios(ImoveldestaqueForm form) {
		Criteria crit = session().createCriteria(Imoveldestaque.class);
		crit.add(Restrictions.eq("tipo", "A"));
		
		if (! StringUtils.isEmpty(form.getDataInicioAnuncio())){
			crit.add(Restrictions.ge("dataDestaque", DateUtil.formataDataBanco(form.getDataInicioAnuncio())));
		}
		
		if (! StringUtils.isEmpty(form.getDataFimAnuncio())){
			crit.add(Restrictions.le("dataDestaque", DateUtil.formataDataBanco(form.getDataFimAnuncio())));
		}
		
		if (! StringUtils.isEmpty(form.getDataInicioCadastro())){
			crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicioCadastro())));
		}
		
		if (! StringUtils.isEmpty(form.getDataFimCadastro())){
			crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFimCadastro())));
		}
		
		crit.addOrder(Order.desc("dataCadastro"));   
		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
    	if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
		return crit.list();
	}

	@Override
	public List<Imoveldestaque> findImoveisAnunciosPorImovel(Long idImovel) {
		Criteria crit = session().createCriteria(Imoveldestaque.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("tipo", "A"));
		crit.addOrder(Order.desc("dataDestaque"));  
		return crit.list();
	}

	@Override
	public Imoveldestaque findImovelAnuncioByData(Date dtInicio, Date dtFim, Long idImovel) {
		Criteria crit = session().createCriteria(Imoveldestaque.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("tipo", "A"));		
		crit.add(Restrictions.ge("dataDestaque",  dtInicio));
		crit.add(Restrictions.le("dataDestaque",  dtFim));		
		crit.setMaxResults(1);
		return (Imoveldestaque)crit.uniqueResult();
	}

	



}
