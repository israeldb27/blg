package com.busqueumlugar.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.form.ContatoForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Usuario;
import com.mysql.jdbc.StringUtils;


@Repository
public class ContatoDaoImpl extends GenericDAOImpl<Contato, Long>  implements ContatoDao {

	private static final Logger log = LoggerFactory.getLogger(ContatoDaoImpl.class);

	
	public ContatoDaoImpl() {
		super(Contato.class);
	}
  
	
	@Override
	public Contato findContatoById(Long id) {
		Criteria crit = session().createCriteria(Contato.class).add(Restrictions.eq("id", id));
		return (Contato) crit.uniqueResult();
	}


	@Override
	public List<Contato> findConvites(Long idUsuario) {
		Criteria crit = session().createCriteria(Contato.class);
		crit.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuario));				
		crit.add(Restrictions.eq("status", ContatoStatusEnum.CONVIDADO.getRotulo())).list();
		return crit.list();				
	}

	@Override
	public List<Contato> findConvitesEnviados(Long idUsuario) {
		Criteria crit = session().createCriteria(Contato.class);
		crit.createCriteria("usuarioHost").add(Restrictions.eq("id", idUsuario));				
		crit.add(Restrictions.eq("status", ContatoStatusEnum.CONVIDADO.getRotulo())).list();		
		return (List<Contato>) crit.list();
	}

	@Override
	public List<Contato> findContatos(Long idUsuario, ContatoForm form) {
		Criteria crit = session().createCriteria(Contato.class);
		Criterion usuarioHost 	   = Restrictions.eq("usuarioHost.id", idUsuario);
		Criterion usuarioConvidado = Restrictions.eq("usuarioConvidado.id", idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioHost,usuarioConvidado); 
		crit.add(orExp);
		
		if ( form != null){
			if (! StringUtils.isNullOrEmpty(form.getOpcaoFiltro()))
				crit.add(Restrictions.eq("status", form.getOpcaoFiltro()));
			else
				crit.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
				if (form.getOpcaoOrdenacao().equals("maiorDataContato"))
					crit.addOrder(Order.desc("dataConvite"));	        
				else if (form.getOpcaoOrdenacao().equals("menorDataContato"))
					crit.addOrder(Order.asc("dataConvite"));	       
			}
		}
		else {
			crit.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));
			crit.addOrder(Order.asc("dataConvite"));
		}
		return (List<Contato>) crit.list();			
	}
	
	@Override
	public List<Contato> findContatos(Long idUsuario, ContatoForm form, int quant) {
		
		Criteria crit = session().createCriteria(Contato.class);
		Criterion usuarioHost 	   = Restrictions.eq("usuarioHost.id", idUsuario);
		Criterion usuarioConvidado = Restrictions.eq("usuarioConvidado.id", idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioHost,usuarioConvidado); 
		crit.add(orExp);
		if (! StringUtils.isNullOrEmpty(form.getOpcaoFiltro()))
			crit.add(Restrictions.eq("status", form.getOpcaoFiltro()));
		else
			crit.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));
		
		crit.setMaxResults(quant);
		return (List<Contato>) crit.list();		
	}

	@Override
	public Contato findContatosByStatus(Long idUsuarioConvidado, Long idUsuarioHost, String status) {
		Criteria crit = session().createCriteria(Contato.class);
		crit.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuarioConvidado));
		crit.createCriteria("usuarioHost").add(Restrictions.eq("id", idUsuarioHost));		
		crit.add(Restrictions.eq("status", status));
		return (Contato) crit.uniqueResult();
	}

	@Override
	public Contato findAnyContatoByStatus(Long idUsuarioConvidado,	Long idUsuarioHost, String status) {

		Criteria crit = session().createCriteria(Contato.class);
		Criterion usuarioHost1 	    = Restrictions.eq("usuarioHost.id", idUsuarioHost);
		Criterion usuarioConvidado1 = Restrictions.eq("usuarioConvidado.id", idUsuarioConvidado);
		LogicalExpression andExp1 = Restrictions.and(usuarioHost1, usuarioConvidado1); 
		
		Criterion usuarioHost2 	    = Restrictions.eq("usuarioHost.id", idUsuarioConvidado);
		Criterion usuarioConvidado2 = Restrictions.eq("usuarioConvidado.id", idUsuarioHost);
		LogicalExpression andExp2 = Restrictions.and(usuarioHost2, usuarioConvidado2);		
		
		LogicalExpression orExp = Restrictions.or(andExp1 ,andExp2);		
		crit.add(Restrictions.eq("status", status));
		crit.add(orExp);
		return (Contato) crit.uniqueResult();	
	}

	@Override
	public List<Contato> findConvitesBydIdUsuarioByStatus(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(Contato.class);
		crit.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", ContatoStatusEnum.CONVIDADO.getRotulo()));
		crit.add(Restrictions.eq("statusLeitura", status));
		return (List<Contato>) crit.list();
	}

	@Override
	public List<Contato> findContatosHostFiltroRelatorio(RelatorioForm form, Long idUsuario) {				
		
		Criteria crit = session().createCriteria(Contato.class);		
		crit.createCriteria("usuarioHost").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));		
		
		Criteria critUsuarioConvidado = null;		
		boolean isCritUsuarioConvidadoExist = (!StringUtils.isNullOrEmpty(form.getSexo())) || 
									 		  (!StringUtils.isNullOrEmpty(form.getFaixaSalarial())) ||
									 		  (!StringUtils.isNullOrEmpty(form.getPerfilUsuario()));
		
		if ( isCritUsuarioConvidadoExist ){
			critUsuarioConvidado = crit.createCriteria("usuarioConvidado");
			
			if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
				critUsuarioConvidado.add(Restrictions.eq("sexo", form.getSexo()));         
				
	        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) )
	        	critUsuarioConvidado.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
	        
	        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
	        	critUsuarioConvidado.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 
		}

		crit.setMaxResults(10);
		return crit.list();	
	}
	
	@Override
	public List<Contato> findContatosConvidadoFiltroRelatorio(RelatorioForm form, Long idUsuario) {
		
		Criteria crit = session().createCriteria(Contato.class);		
		crit.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));		
		
		Criteria critUsuarioHost = null;		
		boolean isCritUsuarioHostExist = (!StringUtils.isNullOrEmpty(form.getSexo())) || 
									 	 (!StringUtils.isNullOrEmpty(form.getFaixaSalarial())) ||
									 	 (!StringUtils.isNullOrEmpty(form.getPerfilUsuario()));
		
		if ( isCritUsuarioHostExist ){
			critUsuarioHost = crit.createCriteria("usuarioHost");
			
			if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
				critUsuarioHost.add(Restrictions.eq("sexo", form.getSexo()));         
				
	        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) )
	        	critUsuarioHost.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
	        
	        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
	        	critUsuarioHost.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 
		}

		crit.setMaxResults(10);
		return crit.list();	
	}

	@Override
	public List<Contato> findConvites(Long idUsuario, int quant) {
		Criteria crit = session().createCriteria(Contato.class);
		crit.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuario));
		crit.add(Restrictions.eq("status", ContatoStatusEnum.CONVIDADO.getRotulo()));
		crit.setMaxResults(quant);
		return (List<Contato>) crit.list();
	}

	@Override
	public List<Contato> findContatosByPerfilUsuario(Long idUsuario, String tipoPerfil) {
		Criteria crit = session().createCriteria(Contato.class);
		crit.createCriteria("usuarioHost").add(Restrictions.eq("id", idUsuario));
		crit.createCriteria("usuarioConvidado").add(Restrictions.eq("perfil", tipoPerfil));
		return (List<Contato>) crit.list();
	}


	@Override
	public List findIdsUsuariosContatosByIdUsuarioByStatus(Long idUsuario, String status) {
		
		List lista = new ArrayList();
		Criteria crit1 = session().createCriteria(Contato.class);
		crit1.add(Restrictions.eq("status", status));
		crit1.createCriteria("usuarioHost").add(Restrictions.eq("id", idUsuario));		
		crit1.setProjection(Projections.property("usuarioConvidado.id"));
		lista.addAll(crit1.list());
		
		Criteria crit2 = session().createCriteria(Contato.class);
		crit2.add(Restrictions.eq("status", status));
		crit2.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuario));		
		crit2.setProjection(Projections.property("usuarioHost.id"));
		lista.addAll(crit2.list());
				
		return lista;
	}


	@Override
	public long findQuantidadeConvitesPorUsuarioPorStatus(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(Contato.class);
		crit.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuario));		
		if (!StringUtils.isNullOrEmpty(status))
			crit.add(Restrictions.eq("statusLeitura", status));
		
		crit.add(Restrictions.eq("status", ContatoStatusEnum.CONVIDADO.getRotulo()));
		
		crit.setProjection(Projections.rowCount());
		return (long) crit.uniqueResult();
	}


	@Override
	public long findQuantidadeTotalContatosByIdUsuarioByStatus(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(Contato.class);
		Criterion usuarioHost 	   = Restrictions.eq("usuarioHost.id", idUsuario);
		Criterion usuarioConvidado = Restrictions.eq("usuarioConvidado.id", idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioHost,usuarioConvidado); 
		crit.add(orExp);		
		if (!StringUtils.isNullOrEmpty(status))
			crit.add(Restrictions.eq("status", status));
		crit.setProjection(Projections.rowCount());	
		return (long) crit.uniqueResult();
	}


	@Override
	public List filterListaIds(RelatorioForm form) {
		
		List lista = new ArrayList();
		Criteria crit1 = session().createCriteria(Contato.class);
		crit1.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));
		crit1.createCriteria("usuarioHost").add(Restrictions.eq("id", form.getUsuarioSessao().getId()));		
		Criteria critUsuarioHost = crit1.createCriteria("usuarioConvidado");
		
		Criteria crit2 = session().createCriteria(Contato.class);
		crit2.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));		
		crit2.createCriteria("usuarioConvidado").add(Restrictions.eq("id", form.getUsuarioSessao().getId()));		
		Criteria critUsuarioConvidado = crit2.createCriteria("usuarioHost");
		
		if ( ! StringUtils.isNullOrEmpty(form.getSexo())) {
			critUsuarioHost.add(Restrictions.eq("sexo", form.getSexo()));
			critUsuarioConvidado.add(Restrictions.eq("sexo", form.getSexo()));			
		}	
			
        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) ) {
        	critUsuarioHost.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));
        	critUsuarioConvidado.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));
        }	
        
        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) ){
        	critUsuarioHost.add(Restrictions.eq("perfil", form.getPerfilUsuario()));
        	critUsuarioConvidado.add(Restrictions.eq("perfil", form.getPerfilUsuario()));
        }			
		
		crit1.setProjection(Projections.property("usuarioConvidado.id"));
		lista.addAll(crit1.list());		
				
		crit2.setProjection(Projections.property("usuarioHost.id"));
		lista.addAll(crit2.list());
		
		return lista;
	}
	
	@Override
	public List filterListaIds(Long idUsuario, String perfilUsuario) {
		
		List lista = new ArrayList();
		Criteria crit1 = session().createCriteria(Contato.class);
		crit1.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));
		crit1.createCriteria("usuarioHost").add(Restrictions.eq("id", idUsuario));		
		Criteria critUsuarioHost = crit1.createCriteria("usuarioConvidado");
		
		Criteria crit2 = session().createCriteria(Contato.class);
		crit2.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));		
		crit2.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuario));		
		Criteria critUsuarioConvidado = crit2.createCriteria("usuarioHost");		
		
		if (! StringUtils.isNullOrEmpty(perfilUsuario) ){
        	critUsuarioHost.add(Restrictions.eq("perfil", perfilUsuario));
        	critUsuarioConvidado.add(Restrictions.eq("perfil", perfilUsuario));
        }	
		
		crit1.setProjection(Projections.property("usuarioConvidado.id"));
		lista.addAll(crit1.list());		
				
		crit2.setProjection(Projections.property("usuarioHost.id"));
		lista.addAll(crit2.list());
		
		return lista;
	}


	@Override
	public List<Contato> findContatosByIndicacao(Long idUsuario, ImovelindicadoForm form) {
		Criteria crit = session().createCriteria(Contato.class);
		Criterion usuarioHost 	   = Restrictions.eq("usuarioHost.id", idUsuario);
		Criterion usuarioConvidado = Restrictions.eq("usuarioConvidado.id", idUsuario); 
		LogicalExpression orExp = Restrictions.or(usuarioHost,usuarioConvidado); 
		crit.add(orExp);
		
		if ( form != null ){
			if (! StringUtils.isNullOrEmpty(form.getOpcaoFiltro()))
				crit.add(Restrictions.eq("perfilContato", form.getOpcaoFiltro()));
		}		
		
		crit.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));
		return (List<Contato>) crit.list();
	}


	@Override
	public List<Contato> filterContatos(Long idUsuario, ContatoForm form) {	
		
		Criteria crit1 = session().createCriteria(Contato.class);
		crit1.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));
		crit1.createCriteria("usuarioHost").add(Restrictions.eq("id", idUsuario));		
		Criteria critUsuarioHost = crit1.createCriteria("usuarioConvidado");
		
		Criteria crit2 = session().createCriteria(Contato.class);
		crit2.add(Restrictions.eq("status", ContatoStatusEnum.OK.getRotulo()));		
		crit2.createCriteria("usuarioConvidado").add(Restrictions.eq("id", idUsuario));		
		Criteria critUsuarioConvidado = crit2.createCriteria("usuarioHost");
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoFiltro()) && ! form.getOpcaoFiltro().equals("T")){			
			critUsuarioHost.add(Restrictions.eq("perfil", form.getOpcaoFiltro()));
			critUsuarioConvidado.add(Restrictions.eq("perfil", form.getOpcaoFiltro()));
		}
		List<Contato> listaFinal = new ArrayList<Contato>();
		if (! CollectionUtils.isEmpty(crit1.list())){
			listaFinal.addAll(crit1.list());
		}
		
		if (! CollectionUtils.isEmpty(crit2.list())){
			listaFinal.addAll(crit2.list());
		}
		return (List<Contato>) listaFinal;	
	}	
}
