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
import com.busqueumlugar.service.SeguidorService;
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
	private SeguidorService seguidorService;
	
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
        return dao.filterNotasByIdUsuario(idUsuario, form);
	}

	public List<Nota> listarTodasNotasPorPerfil(Long idUsuario,	NotaForm form, int quantMaxExibeMaisListaNotas) {		
        return dao.filterNotasByIdUsuario(idUsuario, form, quantMaxExibeMaisListaNotas);
	}
	
	public List<Nota> recuperarNotasContatosUsuario(Long idUsuario, NotaForm form) {            
        List listaIdsContato = contatoDao.findIdsUsuariosContatosByIdUsuarioByStatus(idUsuario, ContatoStatusEnum.OK.getRotulo());
        List listaIdsSeguidores =  seguidorService.recuperarIdsSeguindo(idUsuario);
        List listaIdsFinal = null;
        if ((! CollectionUtils.isEmpty(listaIdsContato) || (! CollectionUtils.isEmpty(listaIdsSeguidores)))){ 
        	listaIdsFinal = new ArrayList<Long>();
        	
        	if (! CollectionUtils.isEmpty(listaIdsContato) )
        		listaIdsFinal.addAll(listaIdsContato);
        	
        	if (! CollectionUtils.isEmpty(listaIdsSeguidores) )
        		listaIdsFinal.addAll(listaIdsSeguidores);        		
        }
        
        if (! CollectionUtils.isEmpty(listaIdsFinal) ){
        	return dao.findNotasContatosByListaIdsUsuario(listaIdsFinal, form);
        }   
        return null;
	}
	
	public List<Nota> recuperarNotasContatosUsuario(Long idUsuario, int quant) {             
        List listaIds = contatoDao.findIdsUsuariosContatosByIdUsuarioByStatus(idUsuario, ContatoStatusEnum.OK.getRotulo());
        if (! CollectionUtils.isEmpty(listaIds) ){        
        	return dao.findNotasContatosByListaIdsUsuarioQuant(listaIds, new NotaForm(), quant);
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
			 return lista;
		 }

		return null;
	}
	

	@Override
	public List<Nota> filtrarMinhasNotas(Long idUsuario, NotaForm form) {		
		List<Nota> lista = dao.filterNotasByIdUsuario(idUsuario, form);	
		if (! CollectionUtils.isEmpty(lista)){
			 return lista;
		 }		
		return null;
	}


	@Override
	public List<Nota> filtrarNotasContatos(Long idUsuario, NotaForm form) {

        List<Nota> listaNotasContatos = new ArrayList<Nota>();
                
        // recuperar Ids dos contatos
    	List listaIdsContatos = contatoDao.findIdsUsuariosContatosByIdUsuarioByStatus(idUsuario, ContatoStatusEnum.OK.getRotulo());
        
        if ( StringUtils.isEmpty(form.getOpcaoFiltro())) {
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
        return dao.filterNotasByIdUsuario(idUsuario, form);
	}


	@Override
	public Nota recuperarNotaByUsuarioByIndex(List<Long> listaIds, int index) {
		return dao.findNotaByUsuarioByIndex( listaIds, index);
	}


	@Override
	public long checarQuantidadeNotasPorUsuario(Long idUsuario) {	
		return dao.findQuantNotasByIdUsuario(idUsuario);
	}


}
