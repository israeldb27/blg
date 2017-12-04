package com.busqueumlugar.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelPropostasDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.enumerador.TipoContatoOpcaoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelPropostasForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.model.Imovelfavoritos;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ImovelPropostasDaoImpl extends GenericDAOImpl<ImovelPropostas, Long> implements ImovelPropostasDao {
	
	@Autowired
	private ContatoDao contatoDao;

	@Autowired
	private SeguidorDao seguidorDao;
	
	private static final Logger log = LoggerFactory.getLogger(ImovelPropostasDaoImpl.class);
	
	public ImovelPropostasDaoImpl() {
		super(ImovelPropostas.class);
	}	
    

	public ImovelPropostas findImovelPropostas(Long id) {
		return (ImovelPropostas)session().createCriteria(ImovelPropostas.class)
				.add(Restrictions.eq("id", id)).uniqueResult();	
	}

	@Override
	public List<ImovelPropostas> findImovelPropostaByIdImovel(Long idImovel) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);		
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		return (List<ImovelPropostas>)crit.list();		
	}

	@Override
	public List<ImovelPropostas> findImoveisPropostasLancadasByIdUsuario(Long idUsuario, ImovelPropostasForm form) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioLancador").add(Restrictions.eq("id", idUsuario));
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return (List<ImovelPropostas>)crit.list();				
	}

	@Override
	public List<ImovelPropostas> findImoveisPropostasLancadasByIdUsuarioByIdImovel(Long idUsuario, Long idImovel) {		
		Criteria crit = session().createCriteria(ImovelPropostas.class);		
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.createCriteria("usuarioLancador").add(Restrictions.eq("id", idUsuario));		
		return (List<ImovelPropostas>)crit.list();	
	}

	@Override
	public List<ImovelPropostas> findImoveisPropostasRecebidasByIdUsuario(Long idUsuario, ImovelPropostasForm form) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioReceptor").add(Restrictions.eq("id", idUsuario));
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return (List<ImovelPropostas>)crit.list();
	}

	@Override
	public List<ImovelPropostas> findNovaImoveisPropostasRecebidasByIdUsuario(Long idUsuario) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioReceptor").add(Restrictions.eq("id", idUsuario));		
		crit.add(Restrictions.eq("status", StatusLeituraEnum.NOVO.getRotulo()));
		return (List<ImovelPropostas>)crit.list();		
	}

	@Override
	public List checarImoveisComMaisPropostasPeriodo(Date dataInicio, Date dataFim,  int quantImoveis) {
		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataCadastro", dataInicio));
		crit.add(Restrictions.le("dataCadastro", dataFim));
		crit.setMaxResults(quantImoveis);		
		return crit.list();
	}

	@Override
	public List relatorioImovelMaisPropostadoPeriodo(RelatorioForm form) {
	
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		Criteria critUsuario = null;
		Criteria critImovel = null;	
		
		boolean isCritUsuarioExist = (!StringUtils.isNullOrEmpty(form.getSexo())) || 
									 (!StringUtils.isNullOrEmpty(form.getFaixaSalarial())) ||
									 (!StringUtils.isNullOrEmpty(form.getPerfilUsuario()));
		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(!StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos"));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstado() > 0 ) {
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

		if (! StringUtils.isNullOrEmpty(form.getOpcaoFiltroContato()) && ! form.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())){		
			List listaIds = null;
			if (form.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){
				listaIds = contatoDao.filterListaIds(form);
			}
			else if (form.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){
				listaIds = seguidorDao.filterListaIdsUsuariosSeguindo(form);
			}
			else if (form.getOpcaoFiltroContato().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUIDORES.getRotulo())){
				listaIds = seguidorDao.filterListaIdsUsuariosSeguidores(form);
			}
			if (! CollectionUtils.isEmpty(listaIds))
				crit.createCriteria("usuario").add(Restrictions.in("id", listaIds));
		}
		else { // vai pegar todos os usuarios da aplicacao
			
			if (isCritUsuarioExist) {
				critUsuario = crit.createCriteria("usuario");
				
				if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
		        	critUsuario.add(Restrictions.eq("sexo", form.getSexo()));         
					
		        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) )
		        	critUsuario.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
		        
		        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
		        	critUsuario.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 
			}
		}	  

        ProjectionList projList = Projections.projectionList();		
        projList.add(Projections.groupProperty("imovel.id"));
        projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();	
	}
	
	@Override
	public List relatorioImovelMaisPropostadoPeriodo(AdministracaoForm form) {        
		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		Criteria critUsuario = null;
		Criteria critImovel = null;	
		
		boolean isCritUsuarioExist = (!StringUtils.isNullOrEmpty(form.getSexo())) || 
									 (!StringUtils.isNullOrEmpty(form.getFaixaSalarial())) ||
									 (!StringUtils.isNullOrEmpty(form.getPerfilUsuario()));
		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(!StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos"));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstado() > 0 ) {
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

		if (isCritUsuarioExist) {
			critUsuario = crit.createCriteria("usuario");
			
			if ( ! StringUtils.isNullOrEmpty(form.getSexo())) 
	        	critUsuario.add(Restrictions.eq("sexo", form.getSexo()));         
				
	        if (! StringUtils.isNullOrEmpty(form.getFaixaSalarial()) )
	        	critUsuario.add(Restrictions.eq("faixaSalarial", form.getFaixaSalarial()));        	
	        
	        if (! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
	        	critUsuario.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 
		}	  

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(10);
		return crit.list();	
	}

	@Override
	public ImovelPropostas findLastImovelPropostaByIdImovel(Long idImovel) {		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));
		crit.add(Restrictions.eq("dataCadastro", this.recuperarDataCadastroMax(idImovel)));				
		return (ImovelPropostas)crit.uniqueResult();
	}


	private Date recuperarDataCadastroMax(Long idImovel) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("imovel").add(Restrictions.eq("id", idImovel));		
		crit.setProjection(Projections.max("dataCadastro"));
		return (Date)crit.uniqueResult();
	}


	@Override
	public List findImoveisPropostasRecebidasByIdUsuarioDistinct(Long idUsuario, ImovelPropostasForm form ) {		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioReceptor").add(Restrictions.eq("id", idUsuario));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));		
		projList.add(Projections.groupProperty("imovel.id"));
		crit.setProjection(projList);	
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		
		 if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ){ // fazer a ordenacao ainda
			 Criteria critImovel= crit.createCriteria("imovel");	
	    	if (form.getOpcaoOrdenacao().equals("maiorValor"))
	    		critImovel.addOrder(Order.desc("valorImovel"));    
	    	else if (form.getOpcaoOrdenacao().equals("menorValor"))
	    		critImovel.addOrder(Order.asc("valorImovel"));        
	    	else if (form.getOpcaoOrdenacao().equals("maiorDataCadastro"))
	    		critImovel.addOrder(Order.desc("dataCadastro"));
	    	else if (form.getOpcaoOrdenacao().equals("menorDataCadastro"))
	    		critImovel.addOrder(Order.asc("dataCadastro"));
	    	else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente"))
	    		critImovel.addOrder(Order.asc("titulo"));
            else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente"))
            	critImovel.addOrder(Order.desc("titulo"));
	    }
		
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   		
		return crit.list();
	}


	@Override
	public int findQuantidadeNovasPropostasRecebidas(Long idImovel) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		ProjectionList projList = Projections.projectionList();		
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.eq("imovel.id", idImovel));						
		crit.add(Restrictions.eq("status", "novo"));
		List lista = crit.list();
		if ( lista == null )
			return 0;
		else {
			Object obj = lista.get(0); 
			return Integer.parseInt(obj.toString());
		}	
	}


	@Override
	public List findUsuariosImoveisPropostasRecebidasByIdUsuario(Long idUsuario, ImovelPropostasForm form) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuarioLancador.id"));
		projList.add(Projections.count("usuarioLancador.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.eq("usuarioReceptor.id", idUsuario));
		
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {
			
			Criteria critUsuario = crit.createCriteria("usuarioLancador");
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) && (! form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())) ){		
				List listaIds = null;
				if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){
					listaIds = contatoDao.filterListaIds(idUsuario, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguidores(idUsuario, form.getOpcaoPerfilContatoAgruparUsuarios());					
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUIDORES.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguindo(idUsuario, form.getOpcaoPerfilContatoAgruparUsuarios());
				}				
				if (! CollectionUtils.isEmpty(listaIds))
					critUsuario.add(Restrictions.in("id", listaIds));
				else
					critUsuario.add(Restrictions.isNull("id"));					
			}
			if ((! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios()))){
				critUsuario.add(Restrictions.eq("perfil", form.getOpcaoPerfilContatoAgruparUsuarios()));
			}
			
			if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
				if (form.getOpcaoOrdenacao().equals("maiorDataCadastrado")){
	        		critUsuario.addOrder(Order.desc("dataCadastro"));
	            }
	            else if (form.getOpcaoOrdenacao().equals("menorDataCadastrado")){
	            	critUsuario.addOrder(Order.asc("dataCadastro"));
	            } 
	            else if (form.getOpcaoOrdenacao().equals("nomeImovelCrescente")){
	            	critUsuario.addOrder(Order.asc("nome"));
	            }
	             else if (form.getOpcaoOrdenacao().equals("nomeImovelDeCrescente")){
	            	 critUsuario.addOrder(Order.desc("nome"));
	            }
			}
	    }
		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();
	}


	@Override
	public List findImoveisPropostasLancadasByIdUsuarioDistinct(Long idUsuario, ImovelPropostasForm form ) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));		
		projList.add(Projections.groupProperty("imovel.id"));
		crit.setProjection(projList);
		crit.add(Restrictions.eq("usuarioLancador.id", idUsuario));		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();
	}


	@Override
	public List findUsuariosImoveisPropostasLancadasByIdUsuarioDistinct(Long idUsuarioSessao, ImovelPropostasForm form) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("usuarioReceptor.id"));			
		projList.add(Projections.count("usuarioReceptor.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.eq("usuarioLancador.id", idUsuarioSessao));
			
		if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao()) ||
		   (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) ||		
		   (! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios())))) {		
			
			Criteria critUsuario = crit.createCriteria("usuarioReceptor");
			
		    if (! StringUtils.isNullOrEmpty(form.getOpcaoContatoAgruparUsuarios()) && 
		       (! form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.TODOS_USUARIOS.getRotulo())) ){
				List listaIds = null;
				if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.MEUS_CONTATOS.getRotulo())){
					listaIds = contatoDao.filterListaIds(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUINDO.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguidores(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
					//listaIds = seguidorDao.filterListaIdsUsuariosSeguindo(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				else if (form.getOpcaoContatoAgruparUsuarios().equals(TipoContatoOpcaoEnum.USUARIOS_SEGUIDORES.getRotulo())){
					listaIds = seguidorDao.filterListaIdsUsuariosSeguindo(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
					//listaIds = seguidorDao.filterListaIdsUsuariosSeguidores(idUsuarioSessao, form.getOpcaoPerfilContatoAgruparUsuarios());
				}
				
				if (! CollectionUtils.isEmpty(listaIds))
					critUsuario.add(Restrictions.in("id", listaIds));
				else	
					critUsuario.add(Restrictions.isNull("id"));			
				
			}
		    
		    if ((! StringUtils.isNullOrEmpty(form.getOpcaoPerfilContatoAgruparUsuarios()))){
				critUsuario.add(Restrictions.eq("perfil", form.getOpcaoPerfilContatoAgruparUsuarios()));
		    }
		    
		    if (!StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())) {		
	        	if (form.getOpcaoOrdenacao().equals("maiorDataCadastrado")){
	        		critUsuario.addOrder(Order.desc("dataCadastro"));
	            }
	            else if (form.getOpcaoOrdenacao().equals("menorDataCadastrado")){
	            	critUsuario.addOrder(Order.asc("dataCadastro"));
	            } 
	            else if (form.getOpcaoOrdenacao().equals("nomeImovelCrescente")){
	            	critUsuario.addOrder(Order.asc("nome"));
	            }
	             else if (form.getOpcaoOrdenacao().equals("nomeImovelDeCrescente")){
	            	 critUsuario.addOrder(Order.desc("nome"));
	            }
		    }
		}
		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return crit.list();
	}


	@Override
	public List<ImovelPropostas> findImovelPropostasByIdUsuarioByIdUsuarioSessaoRecebidas(Long idUsuario, Long idUsuarioSessao) {
		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioLancador").add(Restrictions.eq("id", idUsuarioSessao));
		crit.createCriteria("usuarioReceptor").add(Restrictions.eq("id", idUsuario));
		return (List<ImovelPropostas>)crit.list();
	}


	@Override
	public List<ImovelPropostas> filterPropostasRecebidas(Long idUsuario, ImovelPropostasForm form) {
		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioReceptor").add(Restrictions.eq("id", idUsuario));
		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) ||
									(! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
									(form.getQuantQuartos() > 0 ) ||
									(form.getQuantGaragem() > 0 ) ||
									(form.getQuantSuites() > 0 )  ||
									(form.getQuantBanheiro() > 0 ) ||
									(! StringUtils.isNullOrEmpty(form.getValorMin()))||
									(! StringUtils.isNullOrEmpty(form.getValorMax()));
		
		Criteria critLocal = null;
		if (isCritImovelExist){
			critLocal = crit.createCriteria("imovel");
			
			if ( form.getIdEstado() > 0 ){			
				critLocal.add(Restrictions.eq("idEstado", form.getIdEstado()));
				
				if ( form.getIdCidade() > 0 )
		        	critLocal.add(Restrictions.eq("idCidade", form.getIdCidade()));    
		        
		        if ( form.getIdBairro() > 0 )
		        	critLocal.add(Restrictions.eq("idBairro", form.getIdBairro()));
			}
			
			if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
				critLocal.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critLocal.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));	 
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        	critLocal.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 

	        if ( form.getQuantQuartos() > 0 ){
	            if (form.getQuantQuartos() >= 6 )
	            	critLocal.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
	            else
	            	critLocal.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
	        }
	        
	        if ( form.getQuantGaragem() > 0 ){
	           if (form.getQuantGaragem() >= 6 )
	        	   critLocal.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
	            else
	            	critLocal.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
	        }
	        
	        if ( form.getQuantSuites() > 0 ){
	           if (form.getQuantSuites() >= 6 )
	        	   critLocal.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
	            else
	            	critLocal.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
	        }
	        
	        if ( form.getQuantBanheiro() > 0 ){
	            if (form.getQuantBanheiro()  >= 6 )
	            	critLocal.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
	             else
	            	 critLocal.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
	         }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMin() ) ){
	        	critLocal.add(Restrictions.ge("valorImovel", AppUtil.formatarMoeda(form.getValorMin())));
	        }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMax()) ){
	        	critLocal.add(Restrictions.le("valorImovel", AppUtil.formatarMoeda(form.getValorMax())));
	        }
		}
		
		 if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
        	 if (form.getOpcaoOrdenacao().equals("maiorValorImovel")){
        		 critLocal.addOrder(Order.desc("valorImovel")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("menorValorImovel")){
 	        	critLocal.addOrder(Order.asc("valorImovel"));  
 	        }    
 	         if (form.getOpcaoOrdenacao().equals("maiorValorProposta")){
 	        	crit.addOrder(Order.desc("valorProposta")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("menorValorProposta")){
 	        	crit.addOrder(Order.asc("valorProposta")); 
 	        }         
 	        
 	        else if (form.getOpcaoOrdenacao().equals("maiorDataCadastro")){
 	        	critLocal.addOrder(Order.desc("dataCadastro")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("menorDataCadastro")){
 	        	critLocal.addOrder(Order.asc("dataCadastro")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente")){
 	        	critLocal.addOrder(Order.asc("titulo")); 
 	        }
 	         else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente")){
 	        	critLocal.addOrder(Order.desc("titulo")); 
 	        } 
        }
		
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
		return (List<ImovelPropostas>)crit.list();
	}
	
	@Override
	public List<ImovelPropostas> filterPropostasEnviadas(Long idUsuario, ImovelPropostasForm form) {		
		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioLancador").add(Restrictions.eq("id", idUsuario));
		
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) ||
									(! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
									(form.getQuantQuartos() > 0 ) ||
									(form.getQuantGaragem() > 0 ) ||
									(form.getQuantSuites() > 0 )  ||
									(form.getQuantBanheiro() > 0 ) ||
									(! StringUtils.isNullOrEmpty(form.getValorMin()))||
									(! StringUtils.isNullOrEmpty(form.getValorMax()));		
		
		Criteria critLocal = null;
		if (isCritImovelExist){
			critLocal = crit.createCriteria("imovel");
			
			if ( form.getIdEstado() > 0 ){			
				critLocal.add(Restrictions.eq("idEstado", form.getIdEstado()));
				
				if ( form.getIdCidade() > 0 )
		        	critLocal.add(Restrictions.eq("idCidade", form.getIdCidade() ));    
		        
		        if ( form.getIdBairro() > 0 )
		        	critLocal.add(Restrictions.eq("idBairro", form.getIdBairro()));
			}
			
			if ( ! StringUtils.isNullOrEmpty(form.getAcao()))         
				critLocal.add(Restrictions.eq("acao", form.getAcao()));            

	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critLocal.add(Restrictions.eq("tipoImovel", form.getTipoImovel()));
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critLocal.add(Restrictions.eq("tipoImovel", form.getTipoImovel())); 
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        	critLocal.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 

	        if ( form.getQuantQuartos() > 0 ){
	            if (form.getQuantQuartos() >= 6 )
	            	critLocal.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
	            else
	            	critLocal.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
	        }
	        
	        if ( form.getQuantGaragem() > 0 ){
	           if (form.getQuantGaragem() >= 6 )
	        	   critLocal.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
	            else
	            	critLocal.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
	        }
	        
	        if ( form.getQuantSuites() > 0 ){
	           if (form.getQuantSuites() >= 6 )
	        	   critLocal.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
	            else
	            	critLocal.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
	        }
	        
	        if ( form.getQuantBanheiro() > 0 ){
	            if (form.getQuantBanheiro()  >= 6 )
	            	critLocal.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
	             else
	            	 critLocal.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
	         }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMin() ) ){
	        	critLocal.add(Restrictions.ge("valorImovel", AppUtil.formatarMoeda(form.getValorMin())));
	        }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMax()) ){
	        	critLocal.add(Restrictions.le("valorImovel", AppUtil.formatarMoeda(form.getValorMax())));
	        }
		}
		
		 if (! StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())){
        	 if (form.getOpcaoOrdenacao().equals("maiorValorImovel")){
        		 critLocal.addOrder(Order.desc("valorImovel")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("menorValorImovel")){
 	        	critLocal.addOrder(Order.asc("valorImovel"));  
 	        }    
 	         if (form.getOpcaoOrdenacao().equals("maiorValorProposta")){
 	        	crit.addOrder(Order.desc("valorProposta")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("menorValorProposta")){
 	        	crit.addOrder(Order.asc("valorProposta")); 
 	        }  
 	        else if (form.getOpcaoOrdenacao().equals("maiorDataCadastro")){
 	        	critLocal.addOrder(Order.desc("dataCadastro")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("menorDataCadastro")){
 	        	critLocal.addOrder(Order.asc("dataCadastro")); 
 	        }
 	        else if (form.getOpcaoOrdenacao().equals("tituloImovelCrescente")){
 	        	critLocal.addOrder(Order.asc("titulo")); 
 	        }
 	         else if (form.getOpcaoOrdenacao().equals("tituloImovelDeCrescente")){
 	        	critLocal.addOrder(Order.desc("titulo")); 
 	        } 
        }
		 
		form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
		if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}    
		return (List<ImovelPropostas>)crit.list();
	}
	
	@Override
	public List filterFindImoveisPropostasRecebidasByIdUsuarioDistinct(Long idUsuario, ImovelPropostasForm form) {
		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioReceptor").add(Restrictions.eq("id", idUsuario));		
		Criteria critImovel = null;			

		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||	
									(! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
									(form.getQuantQuartos() > 0 ) ||
									(form.getQuantGaragem() > 0 ) ||
									(form.getQuantSuites() > 0 )  ||
									(form.getQuantBanheiro() > 0 ) ||
									(! StringUtils.isNullOrEmpty(form.getValorMin()))||
									(! StringUtils.isNullOrEmpty(form.getValorMax()));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstado() > 0 ) {
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
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getTipoImovel()))
	        	critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel())); 
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilImovel()) && ! form.getPerfilImovel().equals("todos") )
	        	critImovel.add(Restrictions.eq("perfilImovel", form.getPerfilImovel())); 

	        if ( form.getQuantQuartos() > 0 ){
	            if (form.getQuantQuartos() >= 6 )
	            	critImovel.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
	            else
	            	critImovel.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
	        }
	        
	        if ( form.getQuantGaragem() > 0 ){
	           if (form.getQuantGaragem() >= 6 )
	        	   critImovel.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
	            else
	            	critImovel.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
	        }
	        
	        if ( form.getQuantSuites() > 0 ){
	           if (form.getQuantSuites() >= 6 )
	        	   critImovel.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
	            else
	            	critImovel.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
	        }
	        
	        if ( form.getQuantBanheiro() > 0 ){
	            if (form.getQuantBanheiro()  >= 6 )
	            	critImovel.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
	             else
	            	 critImovel.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
	         }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMin() ) ){
	        	critImovel.add(Restrictions.ge("valorImovel", AppUtil.formatarMoeda(form.getValorMin())));
	        }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMax()) ){
	        	critImovel.add(Restrictions.le("valorImovel", AppUtil.formatarMoeda(form.getValorMax())));
	        }
		}

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);			
		crit.addOrder(Order.desc("quant"));		
		return crit.list();			
	}
	
	
	@Override
	public List filterFindImoveisPropostasEnviadasByIdUsuarioDistinct(Long idUsuario, ImovelPropostasForm form) {
		
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioLancador").add(Restrictions.eq("id", idUsuario));		
		Criteria critImovel = null;
		boolean isCritImovelExist = (form.getIdEstado() > 0) || 
									(!StringUtils.isNullOrEmpty(form.getAcao())) || 
									(!StringUtils.isNullOrEmpty(form.getTipoImovel())) ||
									(! StringUtils.isNullOrEmpty(form.getPerfilImovel())) ||
									(form.getQuantQuartos() > 0 ) ||
									(form.getQuantGaragem() > 0 ) ||
									(form.getQuantSuites() > 0 )  ||
									(form.getQuantBanheiro() > 0 ) ||
									(! StringUtils.isNullOrEmpty(form.getValorMin()))||
									(! StringUtils.isNullOrEmpty(form.getValorMax()));

		if ( isCritImovelExist){
			critImovel = crit.createCriteria("imovel");
			
			 if ( form.getIdEstado() > 0 ) {
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

	        if ( form.getQuantQuartos() > 0 ){
	            if (form.getQuantQuartos() >= 6 )
	            	critImovel.add(Restrictions.gt("quantQuartos", form.getQuantQuartos()));
	            else
	            	critImovel.add(Restrictions.eq("quantQuartos", form.getQuantQuartos()));                                    
	        }
	        
	        if ( form.getQuantGaragem() > 0 ){
	           if (form.getQuantGaragem() >= 6 )
	        	   critImovel.add(Restrictions.gt("quantGaragem", form.getQuantGaragem()));           
	            else
	            	critImovel.add(Restrictions.eq("quantGaragem", form.getQuantGaragem()));
	        }
	        
	        if ( form.getQuantSuites() > 0 ){
	           if (form.getQuantSuites() >= 6 )
	        	   critImovel.add(Restrictions.gt("quantSuites", form.getQuantSuites()));                
	            else
	            	critImovel.add(Restrictions.eq("quantSuites", form.getQuantSuites()));                     
	        }
	        
	        if ( form.getQuantBanheiro() > 0 ){
	            if (form.getQuantBanheiro()  >= 6 )
	            	critImovel.add(Restrictions.gt("quantBanheiro", form.getQuantBanheiro()));                
	             else
	            	 critImovel.add(Restrictions.eq("quantBanheiro", form.getQuantBanheiro()));                     
	         }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMin() ) ){
	        	critImovel.add(Restrictions.ge("valorImovel", AppUtil.formatarMoeda(form.getValorMin())));
	        }
	        
	        if (! StringUtils.isNullOrEmpty(form.getValorMax()) ){
	        	critImovel.add(Restrictions.le("valorImovel", AppUtil.formatarMoeda(form.getValorMax())));
	        }
		}

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("imovel.id"));
		projList.add(Projections.count("imovel.id").as("quant"));
		crit.setProjection(projList);			
		crit.addOrder(Order.desc("quant"));		
		return crit.list();	
	}
	
	@Override	
	public void updateStatusLeituraByIdUsuarioReceptor(Long idUsuario) {
		Session session = this.openSession();		
		session.beginTransaction();
		Query query = session.createSQLQuery("CALL atualizatStatusLeitura(:idUsuario, :tabela)")
							.addEntity(Usuario.class)
							.setParameter("idUsuario", idUsuario)
							.setParameter("tabela", "imovelpropostas");
		int rows = query.executeUpdate();
		session.close();		
	}


	@Override
	public long findQuantPropostasRecebidasByIdUsuarioByStatus(Long idUsuario, String status) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);	
		crit.createCriteria("usuarioReceptor").add(Restrictions.eq("id", idUsuario));
		if (!StringUtils.isNullOrEmpty(status))
			crit.add(Restrictions.eq("status", status));
		crit.setProjection(Projections.rowCount());	
		return (long) crit.uniqueResult();
	}


	@Override
	public List findUsuariosImoveisPropostasSemelhantes(Long idUsuario, ImovelForm form) {

		boolean isCritExist = (form.getIdEstado() > 0 ) || 
				  (! StringUtils.isNullOrEmpty(form.getTipoImovel())) || 
				  (! StringUtils.isNullOrEmpty(form.getAcao())) ||
				  (! StringUtils.isNullOrEmpty(form.getPerfilImovel()));		

		if (isCritExist) {
			Criteria crit = session().createCriteria(ImovelPropostas.class);
			crit.createCriteria("usuarioReceptor").add(Restrictions.ne("id", idUsuario));
			crit.createCriteria("usuarioLancador").add(Restrictions.ne("id", idUsuario));
			
			Criteria critImovel = crit.createCriteria("imovel");
			critImovel.add(Restrictions.ne("id",  form.getId()));	  
			
			if (  form.getIdEstado() > 0 )
				critImovel.add(Restrictions.eq("idEstado",  form.getIdEstado()));	        	
			
			if ( form.getIdCidade() > 0 )
				critImovel.add(Restrictions.eq("idCidade", form.getIdCidade()));
			
			if ( form.getIdBairro() > 0 )
				critImovel.add(Restrictions.eq("idBairro", form.getIdBairro()));
			
			if (! StringUtils.isNullOrEmpty(form.getTipoImovel()))
				critImovel.add(Restrictions.eq("tipoImovel", form.getTipoImovel())); 
			
			crit.addOrder(Order.desc("dataCadastro"));
			crit.setMaxResults(10);			
			ProjectionList projList = Projections.projectionList();
		    projList.add(Projections.distinct(Projections.property("usuarioLancador.id")));			
			crit.setProjection(projList);		
			return crit.list();
		}	
		else	
			return null;
	}


	@Override
	public List findUsuariosImoveisProposta(Long idUsuario, ImovelForm form) {
		Criteria crit = session().createCriteria(ImovelPropostas.class);
		crit.createCriteria("usuarioReceptor").add(Restrictions.ne("id", idUsuario));
		crit.createCriteria("usuarioLancador").add(Restrictions.ne("id", idUsuario));
		
		Criteria critImovel = crit.createCriteria("imovel");
		critImovel.add(Restrictions.ne("id",  form.getId()));
		
		crit.addOrder(Order.desc("dataCadastro"));	
		ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.distinct(Projections.property("usuarioLancador.id")));			
		crit.setProjection(projList);		
		return crit.list();
	}

}
