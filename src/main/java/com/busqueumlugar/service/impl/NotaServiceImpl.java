package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.busqueumlugar.dao.ContatoDao;
import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.NotaDao;
import com.busqueumlugar.dao.PreferencialocalidadeDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.ContatoStatusEnum;
import com.busqueumlugar.enumerador.NotaAcaoEnum;
import com.busqueumlugar.form.NotaForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Nota;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.ImovelService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.UsuarioService;

@Service
public class NotaServiceImpl implements NotaService {
	
	private static final Logger log = LoggerFactory.getLogger(NotaServiceImpl.class);

	@Autowired
	private NotaDao dao;
	
	@Autowired
	private ImovelDao imovelDao;

	@Autowired
	private ContatoDao contatoDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PreferencialocalidadeDao prefLocalDao;
	
	public Nota recuperarNotaPorId(Long id) {
		return dao.findNotaById(id);
	}

	
	@Transactional
	public void cadastrarNota(String descricao, Usuario usuario, Imovel imovel, Date dataAtual, String acao) {		
		Nota nota = new Nota();
        nota.setDescricao(descricao);
        nota.setUsuario(usuario);
        nota.setImovel(imovel);        
        nota.setDataNota(dataAtual);    
        nota.setAcao(acao);
        dao.save(nota);
	}

	@Transactional
	public void cadastrarNota(String descricao, Long idUsuario, Date dataAtual,	String acao) {		
		Nota nota = new Nota();
        nota.setDescricao(descricao);
        nota.setUsuario(usuarioDao.findUsuario(idUsuario));        
        nota.setDataNota(dataAtual);  
        nota.setAcao(acao);
        dao.save(nota);
	}
	
	@Transactional
	public void cadastrarNota(String descricao, Usuario usuario, Date dataAtual, String acao) {		
		Nota nota = new Nota();
        nota.setDescricao(descricao);
        nota.setUsuario(usuario);        
        nota.setDataNota(dataAtual);  
        nota.setAcao(acao);
        dao.save(nota);
	}

	@Transactional
	public void cadastrarNotaPreferenciaLocalidade(String descricao, Long idUsuario, long idPrefLocalidade, Date dataAtual, String acao) {		
		Nota nota = new Nota();
        nota.setDescricao(descricao);
        nota.setUsuario(usuarioDao.findUsuario(idUsuario));      
        nota.setDataNota(dataAtual);  
        nota.setAcao(acao);
        nota.setPrefLocal(prefLocalDao.findPreferencialocalidadeById(idPrefLocalidade));                
        dao.save(nota);
	}

	
	public List<Nota> listarTodasNotasPorPerfil(Long idUsuario,	NotaForm form) {		
        return carregaListaNota(dao.filterNotasByIdUsuario(idUsuario, form));
	}

	
	public List<Nota> recuperarNotasContatosUsuario(Long idUsuario, NotaForm form) {            
        List listaIds = contatoDao.findIdsUsuariosContatosByIdUsuarioByStatus(idUsuario, ContatoStatusEnum.OK.getRotulo());
        if (! CollectionUtils.isEmpty(listaIds) ){
        	return carregaListaNota(dao.findNotasContatosByListaIdsUsuario(listaIds, form));
        }   
        return null;
	}
	
	public List<Nota> recuperarNotasContatosUsuario(Long idUsuario, int quant) {             
        List listaIds = contatoDao.findIdsUsuariosContatosByIdUsuarioByStatus(idUsuario, ContatoStatusEnum.OK.getRotulo());
        if (! CollectionUtils.isEmpty(listaIds) ){        
        	return carregaListaNota(dao.findNotasContatosByListaIdsUsuarioQuant(listaIds, new NotaForm(), quant));
        }     
        return null;
	}
	
	@Transactional
	public void excluirNotasImovel(Long idImovel) {		
		dao.destroyNotasByIdImovel(idImovel);
	}


	@Override
	public List<Nota> ordenarNotas(String tipoLista, Long idUsuario, String nomeUsuario, NotaForm form) {
		
		 List<Nota> lista = null;  
		 if ( tipoLista.equals("minhasNotas")) {
			 if ( StringUtils.isEmpty(form.getOpcaoFiltro()) || (form.getOpcaoFiltro().equals("todos")) ) {
				 lista = listarTodasNotasPorPerfil(idUsuario, form);
			 }
			 else {
				 lista = filtrarMinhasNotas(idUsuario, form);
			 }
		 }	 
		 else {
			 
			 if ( StringUtils.isEmpty(form.getOpcaoFiltro()) || (form.getOpcaoFiltro().equals("todos"))) {
				 lista = recuperarNotasContatosUsuario(idUsuario, form);				 
			 }
			 else {
				 lista = filtrarNotasContatos(idUsuario, form);
			 }	
		 }	
		 
		 if (! CollectionUtils.isEmpty(lista)){
			 return carregaListaNota(lista);
		 }

		return null;
	}
	

	@Override
	public List<Nota> filtrarMinhasNotas(Long idUsuario, NotaForm form) {
		
		List<Nota> lista = null;		
		if ( form.getOpcaoFiltro().equals("todos"))
			lista = dao.filterNotasByIdUsuario(idUsuario, form);
		else 
			lista = dao.filterNotasByIdUsuario(idUsuario, form);	
		
		if (! CollectionUtils.isEmpty(lista)){
			 return carregaListaNota(lista);
		 }
		
		return null;
	}


	@Override
	public List<Nota> filtrarNotasContatos(Long idUsuario, NotaForm form) {

        List<Nota> listaNotasContatos = new ArrayList<Nota>();
                
        // recuperar Ids dos contatos
    	List listaIdsContatos = contatoDao.findIdsUsuariosContatosByIdUsuarioByStatus(idUsuario, ContatoStatusEnum.OK.getRotulo());
        
        if ( StringUtils.isEmpty(form.getOpcaoFiltro()) || (form.getOpcaoFiltro().equals("todos"))) {
        	if (! CollectionUtils.isEmpty(listaIdsContatos))
        		listaNotasContatos = dao.findNotasContatosByListaIdsUsuario(listaIdsContatos, form);
        }
        else {
        	if (! CollectionUtils.isEmpty(listaIdsContatos))
        		listaNotasContatos = dao.filterNotasByListaIdsUsuario(listaIdsContatos, form);
        }    
        
        if (! CollectionUtils.isEmpty(listaNotasContatos)){
			 return carregaListaNota(listaNotasContatos);
		 }
		
		return null;
	}


	private List<Nota> filtrarListaTodasNotasPorPerfil(Long idUsuario, NotaForm form) {
        return carregaListaNota(dao.filterNotasByIdUsuario(idUsuario, form));
	}


	@Override
	public Nota recuperarNotaByUsuarioByIndex(List<Long> listaIds, int index) {
		Nota nota = dao.findNotaByUsuarioByIndex( listaIds, index);
		if ( NotaAcaoEnum.IMOVEL.getRotulo().equals(nota.getAcao()) ||
		     NotaAcaoEnum.INTERMEDIACAO.getRotulo().equals(nota.getAcao())	||
		     NotaAcaoEnum.PARCERIA.getRotulo().equals(nota.getAcao())){
			nota.getImovel().setImagemArquivo(imovelService.carregaFotoPrincipalImovel(nota.getImovel()));
		}
		else if ( NotaAcaoEnum.USUARIO.getRotulo().equals(nota.getAcao()) || 
				  NotaAcaoEnum.PREFERENCIA.getRotulo().equals(nota.getAcao())	||
				  NotaAcaoEnum.PESSOAL.getRotulo().equals(nota.getAcao())){
			nota.getUsuario().setImagemArquivo(usuarioService.carregaFotoPrincipalUsuario(nota.getUsuario()));				
		}
		return nota;
	}
	
	private List<Nota> carregaListaNota(List<Nota> lista){
		List<Nota> listaFinal = new ArrayList<Nota>();
		if (! CollectionUtils.isEmpty(lista)){
			for (Nota nota : lista){
				if ( NotaAcaoEnum.IMOVEL.getRotulo().equals(nota.getAcao()) ||
				     NotaAcaoEnum.INTERMEDIACAO.getRotulo().equals(nota.getAcao())	||
				     NotaAcaoEnum.PARCERIA.getRotulo().equals(nota.getAcao())){
					nota.getImovel().setImagemArquivo(imovelService.carregaFotoPrincipalImovel(nota.getImovel()));
				}
				else if ( NotaAcaoEnum.USUARIO.getRotulo().equals(nota.getAcao()) || 
						  NotaAcaoEnum.PREFERENCIA.getRotulo().equals(nota.getAcao())	||
						  NotaAcaoEnum.PESSOAL.getRotulo().equals(nota.getAcao())){
					nota.getUsuario().setImagemArquivo(usuarioService.carregaFotoPrincipalUsuario(nota.getUsuario()));				
				}
				listaFinal.add(nota);
			}
		}		
		
		return listaFinal;
	}


}
