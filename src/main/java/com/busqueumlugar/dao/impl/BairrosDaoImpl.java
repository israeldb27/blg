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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.BairrosDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Bairros;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class BairrosDaoImpl extends GenericDAOImpl<Bairros, Long>  implements BairrosDao {
	
	private static final Logger log = LoggerFactory.getLogger(BairrosDaoImpl.class);


	public BairrosDaoImpl() {
		super(Bairros.class);
	}

	
	public Bairros findBairrosById(int id) {
		return (Bairros)session().createCriteria(Bairros.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}


	@Override
	public List<Bairros> findBairrosPorIdCidade(int idCidade) {
		return (List<Bairros>)session().createCriteria(Bairros.class)
				.add(Restrictions.eq("cidade", String.valueOf(idCidade))).list();
	}


	@Override
	public List relatorioSobreBairros(RelatorioForm form) {
		
		Criteria crit = session().createCriteria(Imovel.class);
		if ( form.getIdEstado() > 0 ) {
			 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			 
			 if ( form.getIdCidade() > 0 )
				 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));		
		 }
		
		 if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
	        crit.add(Restrictions.eq("acao", form.getAcao()));            

	     if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        crit.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
	        
	    if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        crit.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 
	    
	    ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.groupProperty("idBairro"));
		projList.add(Projections.sum("valorImovel").as("valor"));
		crit.setProjection(projList);
		if (form.getOpcaoRelatorioSobreLocalidade().equals("B"))
			crit.addOrder(Order.desc("valor"));
	    else if (form.getOpcaoRelatorioSobreLocalidade().equals("C"))
	    	crit.addOrder(Order.desc("valor"));
		
		crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFim())));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();			
	}

}
