package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.busqueumlugar.dao.ImovelDao;
import com.busqueumlugar.dao.NotaDao;
import com.busqueumlugar.dao.PreferencialocalidadeDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.form.NotaForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Nota;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ContatoService;
import com.busqueumlugar.service.NotaService;

@Service
public class NotaServiceImpl implements NotaService {
	
	private static final Logger log = LoggerFactory.getLogger(NotaServiceImpl.class);

	@Autowired
	private NotaDao dao;
	
	@Autowired
	private ImovelDao imovelDao;

	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
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
	public void cadastrarNota(String descricao, Usuario usuario, Date dataAtual,	String acao) {		
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
        return dao.findNotasByIdUsuario(idUsuario, form);
	}

	
	public List<Nota> recuperarNotasContatosUsuario(Long idUsuario, NotaForm form) {
        List<Nota> listaNotasContatos = new ArrayList<Nota>();
        List listaIds = contatoService.recuperarIDsMeusContatos(idUsuario);
        if (! CollectionUtils.isEmpty(listaIds) ){
        	listaNotasContatos = dao.findNotasContatosByListaIdsUsuario(listaIds, form);
        }   
        return listaNotasContatos;
	}
	
	public List<Nota> recuperarNotasContatosUsuario(Long idUsuario, int quant) {
        List<Nota> listaNotasContatos = new ArrayList<Nota>();        
        List listaIds = contatoService.recuperarIDsMeusContatos(idUsuario);
        if (! CollectionUtils.isEmpty(listaIds) ){
        	listaNotasContatos = dao.findNotasContatosByListaIdsUsuarioQuant(listaIds, new NotaForm(), quant);
        }
     
        return listaNotasContatos;
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

		return lista;
	}
	

	@Override
	public List<Nota> filtrarMinhasNotas(Long idUsuario, NotaForm form) {
		
		List<Nota> lista = null;		
		if ( form.getOpcaoFiltro().equals("todos"))
			lista = dao.findNotasByIdUsuario(idUsuario, form);
		else 
			lista = dao.findNotasByIdUsuario(idUsuario, form);		
		
		return lista;
	}


	@Override
	public List<Nota> filtrarNotasContatos(Long idUsuario, NotaForm form) {

        List<Nota> listaNotasContatos = new ArrayList<Nota>();
                
        // recuperar Ids dos contatos
    	List listaIdsContatos = contatoService.recuperarIDsMeusContatos(idUsuario);
        
        if ( StringUtils.isEmpty(form.getOpcaoFiltro()) || (form.getOpcaoFiltro().equals("todos"))) {
        	if (! CollectionUtils.isEmpty(listaIdsContatos))
        		listaNotasContatos = dao.findNotasContatosByListaIdsUsuario(listaIdsContatos, form);
        }
        else {
        	if (! CollectionUtils.isEmpty(listaIdsContatos))
        		listaNotasContatos = dao.filterNotasByListaIdsUsuario(listaIdsContatos, form);
        }       
        
        return listaNotasContatos;
	}


	private List<Nota> filtrarListaTodasNotasPorPerfil(Long idUsuario, NotaForm form) {
        return dao.findNotasByIdUsuario(idUsuario, form);
	}


	@Override
	public Nota recuperarNotaByUsuarioByIndex(List<Long> listaIds, int index) {
		return dao.findNotaByUsuarioByIndex( listaIds, index);
	}


}
