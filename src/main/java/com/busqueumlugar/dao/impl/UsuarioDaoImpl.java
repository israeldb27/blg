package com.busqueumlugar.dao.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.PerfilUsuarioOpcaoEnum;
import com.busqueumlugar.enumerador.StatusImovelCompartilhadoEnum;
import com.busqueumlugar.enumerador.TipoContatoOpcaoEnum;
import com.busqueumlugar.enumerador.TipoImovelCompartilhadoEnum;
import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.model.Imovelfavoritos;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Intermediacao;
import com.busqueumlugar.model.Parceria;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class UsuarioDaoImpl extends GenericDAOImpl<Usuario, Long> implements  UsuarioDao {
	
	@Autowired
	private ContatoDao contatoDao;

	@Autowired
	private SeguidorDao seguidorDao;
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioDaoImpl.class);
	
	public UsuarioDaoImpl() {
		super(Usuario.class);
	}
	
	
	@Override
	@Transactional
	public List<Usuario> listaUsuarios() {		
		return session().createCriteria(Usuario.class).list();
	}


	@Override
	public Usuario findUsuario(Long idUsuario) {
		return (Usuario) session().createCriteria(Usuario.class)
									.add(Restrictions.eq("id", idUsuario)).uniqueResult();		
	}
	
	
	@Override
	public Usuario findUsuarioByCampo(UsuarioForm form, String nomeCampo) {

		
		if (! StringUtils.isNullOrEmpty(nomeCampo)){
			Criteria crit = session().createCriteria(Usuario.class);
			
			if (nomeCampo.equals("email")){
				crit.add(Restrictions.eq("email", form.getEmail()));
			}
			else if (nomeCampo.equals("login")){
				crit.add(Restrictions.eq("login", form.getLogin()));	
			}
			else if (nomeCampo.equals("loginPassword")){
				crit.add(Restrictions.eq("login", form.getLogin()));
				crit.add(Restrictions.eq("password", md5(form.getPassword())));
			}
			else if (nomeCampo.equals("emailPassword")){
				crit.add(Restrictions.eq("email", form.getLogin()));
				crit.add(Restrictions.eq("password", md5(form.getPassword())));
			}
			else if (nomeCampo.equals("nome")){
				crit.add(Restrictions.eq("nome", form.getNome()));
			}
			else if (nomeCampo.equals("nomeLike")){
				crit.add(Restrictions.ilike("nome", "%" + form.getNome() + "%"));
			}			
			else if (nomeCampo.equals("creci")){
				crit.add(Restrictions.eq("creci", form.getCreci()));
			}
			else if (nomeCampo.equals("cnpj")){
				crit.add(Restrictions.eq("cpf", form.getCnpj()));
			}
			else if (nomeCampo.equals("cpf")){
				crit.add(Restrictions.eq("cpf", form.getCpf()));
			}
			else if (nomeCampo.equals("emailEsqueceuSenha")){
				crit.add(Restrictions.eq("email", form.getEmailEsqueceu()));
			}
			else if (nomeCampo.equals("cnpjEsqueceuSenha")){
				crit.add(Restrictions.eq("cpf", form.getCpfCnpjEsqueceuSenha()));
			}
			else if (nomeCampo.equals("cpfEsqueceuSenha")){
				crit.add(Restrictions.eq("cpf", form.getCpfCnpjEsqueceuSenha()));
			}
			else if (nomeCampo.equals("emailCpfCnpjEsqueceuSenha")){
				crit.add(Restrictions.eq("email", form.getEmailEsqueceu()));
				crit.add(Restrictions.eq("cpf", form.getCpfCnpjEsqueceuSenha()));
			}
			else if (nomeCampo.equals("emailSenhaTemporariaEsqueceuSenha")){
				crit.add(Restrictions.eq("email", form.getEmailEsqueceu()));
				crit.add(Restrictions.eq("password", md5(form.getSenhaTemporariaEsqueceu())));
			}
			
			crit.setMaxResults(1);
			 return (Usuario) crit.uniqueResult();
		}
		else
			return null;       
	}
	
	@Override
	public List<Usuario> findUsuariosByCampo(UsuarioForm form, String nomeCampo, boolean isAdmin) {

		
		if (! StringUtils.isNullOrEmpty(nomeCampo)){
			Criteria crit = session().createCriteria(Usuario.class);
			
			if (nomeCampo.equals("email")){
				crit.add(Restrictions.eq("email", form.getEmail()));
			}
			else if (nomeCampo.equals("login")){
				crit.add(Restrictions.eq("login", form.getLogin()));	
			}
			else if (nomeCampo.equals("loginPassword")){
				crit.add(Restrictions.eq("login", form.getLogin()));
				crit.add(Restrictions.eq("password", form.getPassword()));
			}
			else if (nomeCampo.equals("nome")){
				crit.add(Restrictions.eq("nome", form.getNome()));
			}
			else if (nomeCampo.equals("nomeLike")){
				crit.add(Restrictions.ilike("nome", "%" + form.getNome() + "%"));
			}
			else if (nomeCampo.equals("creci")){
				crit.add(Restrictions.eq("creci", form.getCreci()));
			}
			else if (nomeCampo.equals("cnpj")){
				crit.add(Restrictions.eq("cpf", form.getCnpj()));
			}
			else if (nomeCampo.equals("cpf")){
				crit.add(Restrictions.eq("cpf", form.getCpf()));
			}
			
			if (isAdmin)
				crit.add(Restrictions.eq("perfil", PerfilUsuarioOpcaoEnum.ADMIN.getRotulo()));
			else
				crit.add(Restrictions.ne("perfil", PerfilUsuarioOpcaoEnum.ADMIN.getRotulo()));
			
			 return (List<Usuario>) crit.list();
		}
		else
			return null;       
	}
	
	@Override
	public List<Usuario> findUsuarios(UsuarioForm form) {

		Criteria crit = session().createCriteria(Usuario.class);
		crit.add(Restrictions.ne("perfil", PerfilUsuarioOpcaoEnum.ADMIN.getRotulo()));
		
		 if ( form.getIdEstado() > 0 ) {
			 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			 
			 if ( form.getIdCidade() > 0 )
				 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
		        
		     if ( form.getIdBairro() > 0 )
		    	 crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		 }	 	
        
        if ( ! StringUtils.isNullOrEmpty(form.getPerfil() ))
        	crit.add(Restrictions.eq("perfil", form.getPerfil()));
        
        
        if (!StringUtils.isNullOrEmpty(form.getOpcaoOrdenacao())) {
        	if (form.getOpcaoOrdenacao().equals("maiorDataCadastrado")){
        		crit.addOrder(Order.desc("dataCadastro"));
            }
            else if (form.getOpcaoOrdenacao().equals("menorDataCadastrado")){
            	crit.addOrder(Order.asc("dataCadastro"));
            } 
            else if (form.getOpcaoOrdenacao().equals("nomeImovelCrescente")){
            	crit.addOrder(Order.asc("nome"));
            }
             else if (form.getOpcaoOrdenacao().equals("nomeImovelDeCrescente")){
            	 crit.addOrder(Order.desc("nome"));
            }
        }
       
        form.setQuantRegistros(AppUtil.recuperarQuantidadeLista(crit.list()));
    	if ( form.isVisible()){
	        crit.setFirstResult((Integer.parseInt((StringUtils.isNullOrEmpty(form.getOpcaoPaginacao())) ? "1": form.getOpcaoPaginacao()) - 1) * form.getQuantMaxRegistrosPerPage());        
	        crit.setMaxResults(form.getQuantMaxRegistrosPerPage());
	        form.setListaPaginas(AppUtil.carregarQuantidadePaginas(form.getQuantRegistros(), form.getQuantMaxRegistrosPerPage()));
		}   
        return (List<Usuario>) crit.list();
	}
	
	@Override
	public List<Usuario> findUsuarios(AdministracaoForm form) {

		Criteria crit = session().createCriteria(Usuario.class);
		
		 if ( form.getIdEstado() > 0 ) {
			 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			 
			 if ( form.getIdCidade() > 0 )
				 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
		        
		     if ( form.getIdBairro() > 0 )
		    	 crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		 }	 	
        
        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario() ))
        	crit.add(Restrictions.eq("perfil", form.getPerfilUsuario()));   
        
        crit.add(Restrictions.ge("dataUltimoAcesso", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataUltimoAcesso", DateUtil.formataDataBanco(form.getDataFim())));
        return (List<Usuario>) crit.list();
	}
	
	@Override
	public List<Usuario> findUsuariosByDataCadastro(AdministracaoForm form) {
		
		Criteria crit = session().createCriteria(Usuario.class);
				
		if ( form.getIdEstado() > 0 ) {
			crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			
			if ( form.getIdCidade() > 0 )
        		crit.add(Restrictions.eq("idCidade", form.getIdCidade()));    
        
			if ( form.getIdBairro() > 0 )
				crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		}
		
        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
        	crit.add(Restrictions.eq("perfil", form.getPerfilUsuario()));        	
        	
        
        crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFim())));
        return (List<Usuario>) crit.list();
	}

	@Override
	public Usuario findUsuarioByCodigoIdentificacao(String codigo) {
		return (Usuario) session().createCriteria(Usuario.class)				
				.add(Restrictions.eq("codigoIdentificacao", codigo)).uniqueResult();
	}

	@Override
	public void updateStatusAtivadoByIdUsuario(Long idUsuario) {
		session().beginTransaction();
		Query query = session().createSQLQuery("CALL atualizatStatusAtivado(:idUsuario)")
							.addEntity(Usuario.class)
							.setParameter("idUsuario", idUsuario);
		int rows = query.executeUpdate();
		session().beginTransaction().commit();		
	}

	@Override
	public List recuperarUsuariosComMaisCompartilhamentoAceitos(RelatorioForm form, String tipoCompart) {		
		
		Criteria crit = session().createCriteria(Usuario.class, "u");
		
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
			
			crit.createCriteria("usuario").add(Restrictions.in("id", listaIds));
		}
		else {
			if ( form.getIdEstado() > 0 ) {
				 crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
				 
				 if ( form.getIdCidade() > 0 )
					 crit.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
			        
			     if ( form.getIdBairro() > 0 )
			    	 crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
			 }	 	
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario() ))
	        	crit.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 
		}       
		
		if (! StringUtils.isNullOrEmpty(tipoCompart)){
			if (tipoCompart.equals(TipoImovelCompartilhadoEnum.INTERMEDIACAO.getRotulo()) ){
				DetachedCriteria dtIc = DetachedCriteria.forClass(Intermediacao.class, "ic");		        
		        dtIc.add(Restrictions.eq("status", StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));
		        dtIc.add(Restrictions.ge("dataResposta", DateUtil.formataDataBanco(form.getDataInicio())));
		        dtIc.add(Restrictions.le("dataResposta", DateUtil.formataDataBanco(form.getDataFim())));	
		        dtIc.add(Restrictions.or(Restrictions.eqProperty("ic.usuarioDonoImovel.id", "u.id") ,Restrictions.eqProperty("ic.usuarioSolicitante.id", "u.id") ));
		        crit.add(Subqueries.exists(dtIc.setProjection(Projections.property("ic.id"))));
			}
			else if (tipoCompart.equals(TipoImovelCompartilhadoEnum.PARCERIA.getRotulo()) ){
				DetachedCriteria dtIc = DetachedCriteria.forClass(Parceria.class, "ic");		        
		        dtIc.add(Restrictions.eq("status", StatusImovelCompartilhadoEnum.ACEITA.getRotulo()));
		        dtIc.add(Restrictions.ge("dataResposta", DateUtil.formataDataBanco(form.getDataInicio())));
		        dtIc.add(Restrictions.le("dataResposta", DateUtil.formataDataBanco(form.getDataFim())));	
		        dtIc.add(Restrictions.or(Restrictions.eqProperty("ic.usuarioDonoImovel.id", "u.id") ,Restrictions.eqProperty("ic.usuarioSolicitante.id", "u.id") ));
		        crit.add(Subqueries.exists(dtIc.setProjection(Projections.property("ic.id"))));
			}
		}
        
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("id"));
		projList.add(Projections.count("id").as("quant"));
		crit.setProjection(projList);				
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();
	}

	@Override
	public List<Usuario> findUsuarioByValorAdmin(String valor){
		Criteria crit = session().createCriteria(Usuario.class);		
		crit.add(Restrictions.like("nome", "%" + valor + "%"));		
		return (List<Usuario>) crit.list(); 
	}
	
	
	public String md5(String senha) {	
		String sen = "";  
        MessageDigest md = null;  
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        sen = hash.toString(16);      
        return sen;
	}


	@Override
	public List<Usuario> findUsuariosByDataVisita(AdministracaoForm form) {
		Criteria crit = session().createCriteria(Usuario.class);
		
		if ( form.getIdEstado() > 0 ) {
			crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			
			if ( form.getIdCidade() > 0 )
        		crit.add(Restrictions.eq("idCidade", form.getIdCidade()));    
        
			if ( form.getIdBairro() > 0 )
				crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		}
		
        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
        	crit.add(Restrictions.eq("perfil", form.getPerfilUsuario()));        	
        	
        
        crit.add(Restrictions.ge("dataUltimoAcesso", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataUltimoAcesso", DateUtil.formataDataBanco(form.getDataFim())));
        return (List<Usuario>) crit.list();
	}


	@Override
	public Usuario findUsuarioByUsuarioByIndex(List<Long> listaIds, UsuarioForm user, int index) {
		Criteria crit = session().createCriteria(Usuario.class);
		crit.add(Restrictions.ne("perfil", PerfilUsuarioOpcaoEnum.ADMIN.getRotulo()));   
		if ( ! CollectionUtils.isEmpty(listaIds)){
			listaIds.add(user.getId());
			crit.add(Restrictions.not(Restrictions.in("id", listaIds)));
		}
		else 
			crit.add(Restrictions.ne("id", user.getId()));
			
		crit.addOrder(Order.desc("dataUltimoAcesso"));
		crit.setFirstResult(index);
		crit.setMaxResults(1);
		return (Usuario)crit.uniqueResult();
	}


	@Override
	public long findQuantidadeTotalUsuarioPorPeriodo(AdministracaoForm form) {
		Criteria crit = session().createCriteria(Usuario.class);
		
		if ( form.getIdEstado() > 0 ) {
			crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			
			if ( form.getIdCidade() > 0 )
        		crit.add(Restrictions.eq("idCidade", form.getIdCidade()));    
        
			if ( form.getIdBairro() > 0 )
				crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		}
		
        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
        	crit.add(Restrictions.eq("perfil", form.getPerfilUsuario()));        	
        	
        
        crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFim())));
		
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.rowCount());
		crit.setProjection(projList);
		return (long) crit.uniqueResult();
	}


	@Override
	public long findQuantidadeUsuariosByDataVisita(AdministracaoForm form) {
		Criteria crit = session().createCriteria(Usuario.class);
		
		if ( form.getIdEstado() > 0 ) {
			crit.add(Restrictions.eq("idEstado", form.getIdEstado()));
			
			if ( form.getIdCidade() > 0 )
        		crit.add(Restrictions.eq("idCidade", form.getIdCidade()));    
        
			if ( form.getIdBairro() > 0 )
				crit.add(Restrictions.eq("idBairro", form.getIdBairro()));
		}
		
        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario()) )
        	crit.add(Restrictions.eq("perfil", form.getPerfilUsuario()));  
        
        crit.add(Restrictions.ge("dataUltimoAcesso", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataUltimoAcesso", DateUtil.formataDataBanco(form.getDataFim())));
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.rowCount());
		crit.setProjection(projList);
		return (long) crit.uniqueResult();
	}


	@Override
	public List findUsuariosImoveisMaisVisualizados(RelatorioForm form) {
		
		Criteria crit = session().createCriteria(Imovelvisualizado.class);
		
		Criteria critUsuario = null;
		
		boolean isCritUsuarioExist = (!StringUtils.isNullOrEmpty(form.getOpcaoFiltroContato()) ||									
				 					 (form.getIdEstado() > 0));
		
		if ( isCritUsuarioExist ){
			critUsuario = crit.createCriteria("usuario");
			
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
				
				critUsuario.add(Restrictions.in("id", listaIds));
			}
			
			if ( form.getIdEstado() > 0 ) {
				critUsuario.add(Restrictions.eq("idEstado", form.getIdEstado()));
				 
				 if ( form.getIdCidade() > 0 )
					 critUsuario.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
			        
			     if ( form.getIdBairro() > 0 )
			    	 critUsuario.add(Restrictions.eq("idBairro", form.getIdBairro()));
			 }	 	
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario()))
	        	critUsuario.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 			 
		}
		
        
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("usuario.id"));
		projList.add(Projections.count("usuario.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataVisita", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataVisita", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();
	}


	@Override
	public List findUsuariosImoveisMaisFavoritos(RelatorioForm form) {
		
		Criteria crit = session().createCriteria(Imovelfavoritos.class);		
		Criteria critUsuario = null;		
		boolean isCritUsuarioExist = (!StringUtils.isNullOrEmpty(form.getOpcaoFiltroContato()) ||									
				 					 (form.getIdEstado() > 0));
		
		if ( isCritUsuarioExist ){
			critUsuario = crit.createCriteria("usuario");
			
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
				
				critUsuario.add(Restrictions.in("id", listaIds));
			}
			
			if ( form.getIdEstado() > 0 ) {
				critUsuario.add(Restrictions.eq("idEstado", form.getIdEstado()));
				 
				 if ( form.getIdCidade() > 0 )
					 critUsuario.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
			        
			     if ( form.getIdBairro() > 0 )
			    	 critUsuario.add(Restrictions.eq("idBairro", form.getIdBairro()));
			 }	 	
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario() ))
	        	critUsuario.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 			 
		}
		
        
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("usuario.id"));
		projList.add(Projections.count("usuario.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataInteresse", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataInteresse", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();
	}


	@Override
	public List findUsuariosImoveisMaisPropostas(RelatorioForm form) {
		
        Criteria crit = session().createCriteria(ImovelPropostas.class);		
		Criteria critUsuario = null;		
		boolean isCritUsuarioExist = (!StringUtils.isNullOrEmpty(form.getOpcaoFiltroContato()) ||									
				 					 (form.getIdEstado() > 0));
		
		if ( isCritUsuarioExist ){
			critUsuario = crit.createCriteria("usuario");
			
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
				
				critUsuario.add(Restrictions.in("id", listaIds));
			}
			
			if ( form.getIdEstado() > 0 ) {
				critUsuario.add(Restrictions.eq("idEstado", form.getIdEstado()));
				 
				 if ( form.getIdCidade() > 0 )
					 critUsuario.add(Restrictions.eq("idCidade", form.getIdCidade()));		            
			        
			     if ( form.getIdBairro() > 0 )
			    	 critUsuario.add(Restrictions.eq("idBairro", form.getIdBairro()));
			 }	 	
	        
	        if ( ! StringUtils.isNullOrEmpty(form.getPerfilUsuario() ))
	        	critUsuario.add(Restrictions.eq("perfil", form.getPerfilUsuario())); 			 
		}
		
        
		ProjectionList projList = Projections.projectionList();
        projList.add(Projections.groupProperty("usuario.id"));
		projList.add(Projections.count("usuario.id").as("quant"));
		crit.setProjection(projList);
		crit.add(Restrictions.ge("dataCadastro", DateUtil.formataDataBanco(form.getDataInicio())));
		crit.add(Restrictions.le("dataCadastro", DateUtil.formataDataBanco(form.getDataFim())));		
		crit.addOrder(Order.desc("quant"));
		crit.setMaxResults(form.getQuantMaxRegistrosResultado());
		return crit.list();
	}

}
