package com.busqueumlugar.dao.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.CidadesDao;
import com.busqueumlugar.dao.NotaDao;
import com.busqueumlugar.form.NotaForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.model.Nota;
import com.busqueumlugar.util.AppUtil;
import com.mysql.jdbc.StringUtils;


@Repository
public class NotaDaoImpl extends GenericDAOImpl<Nota, Long>  implements NotaDao {

	private static final Logger log = LoggerFactory.getLogger(NotaDaoImpl.class);
	
	public NotaDaoImpl() {
		super(Nota.class);
	}   

	@Override
	public Nota findNotaById(Long id) {
		return (Nota)session().createCriteria(Nota.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public int destroyNotasByIdImovel(Long idImovel) {
	
		StringBuffer sql = new StringBuffer("delete FROM Nota n ");            
        sql.append(" where n.idImovel = :idImovel ");
        session().beginTransaction();
        Query query = session().createQuery(sql.toString());
        query.setParameter("idImovel", idImovel);                 
        int rows = query.executeUpdate();
        session().beginTransaction().commit();
		return rows;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Nota> filterNotasByIdUsuario(Long idUsuario, NotaForm form) {
		Criteria crit = session().createCriteria(Nota.class);
		crit.createCriteria("usuario").add(Restrictions.eq("id", idUsuario));
		
		if ( form != null){
			if (! StringUtils.isNullOrEmpty(form.getOpcaoFiltro()))
				crit.add(Restrictions.eq("acao", form.getOpcaoFiltro()));
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
				if (form.getOpcaoOrdenacao().equals("maiorDataNota"))
					crit.addOrder(Order.desc("dataNota"));
			    else if (form.getOpcaoOrdenacao().equals("menorDataNota"))
			    	crit.addOrder(Order.asc("dataNota"));
			    else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente"))
			    	crit.createCriteria("imovel").addOrder(Order.desc("titulo"));
			    else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente"))
			    	crit.createCriteria("imovel").addOrder(Order.asc("titulo"));
			}
			else		
				crit.addOrder(Order.desc("dataNota")).list();
			
			form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
			if ( form.isVisible()){
		        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
		        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
		        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
			}
		}
		else
			crit.addOrder(Order.desc("dataNota")).list();
		   
		return (List<Nota>)crit.list();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Nota> findNotasContatosByListaIdsUsuarioQuant(List<?> listaIdsContatos, NotaForm form, int quant) {
		Criteria crit = session().createCriteria(Nota.class);
		crit.createCriteria("usuario").add(Restrictions.in("id", listaIdsContatos));
		crit.setMaxResults(quant);
		form.setQuantRegistros(quant);
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
		return (List<Nota>)crit.list();
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Nota> findNotasContatosByListaIdsUsuario(List<?> listaIdsContatos, NotaForm form) {
		Criteria crit = session().createCriteria(Nota.class);
		crit.createCriteria("usuario").add(Restrictions.in("id", listaIdsContatos));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoFiltro()))
			crit.add(Restrictions.eq("acao", form.getOpcaoFiltro()));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
			if (form.getOpcaoOrdenacao().equals("maiorDataNota"))
				crit.addOrder(Order.desc("dataNota"));
		    else if (form.getOpcaoOrdenacao().equals("menorDataNota"))
		    	crit.addOrder(Order.asc("dataNota"));
		    else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente"))
		    	crit.createCriteria("imovel").addOrder(Order.desc("titulo"));
		    else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente"))
		    	crit.createCriteria("imovel").addOrder(Order.asc("titulo"));
		}
		else		
			crit.addOrder(Order.desc("dataNota")).list();
		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
		return (List<Nota>)crit.list();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Nota> filterNotasByListaIdsUsuario(List<?> listaIdsContatos, NotaForm form) {
		Criteria crit = session().createCriteria(Nota.class);
		crit.createCriteria("usuario").add(Restrictions.in("id", listaIdsContatos));	
		crit.add(Restrictions.eq("acao", form.getOpcaoFiltro()));
		crit.addOrder(Order.desc("dataNota")).list();
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}
		return (List<Nota>)crit.list();
	}

	@Override
	public Nota findNotaByUsuarioByIndex(List<Long> listaIds, int index) {
		Criteria crit = session().createCriteria(Nota.class);		
		crit.createCriteria("usuario").add(Restrictions.in("id", listaIds));		
		crit.setFirstResult(index);
		crit.setMaxResults(1);
		return (Nota)crit.uniqueResult();
	}


}
