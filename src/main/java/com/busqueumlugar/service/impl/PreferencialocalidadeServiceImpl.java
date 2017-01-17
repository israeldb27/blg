package com.busqueumlugar.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.dao.BairrosDao;
import com.busqueumlugar.dao.CidadesDao;
import com.busqueumlugar.dao.EstadosDao;
import com.busqueumlugar.dao.PreferencialocalidadeDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.form.PreferencialocalidadeForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Estados;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Preferencialocalidade;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.service.ImovelFavoritosService;
import com.busqueumlugar.service.NotaService;
import com.busqueumlugar.service.PreferencialocalidadeService;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.MessageUtils;

@Service
public class PreferencialocalidadeServiceImpl implements PreferencialocalidadeService {
	
	private static final Logger log = LoggerFactory.getLogger(PreferencialocalidadeServiceImpl.class);
	
	@Autowired
	private PreferencialocalidadeDao dao;	
	
	@Autowired
	private EstadosDao estadosDao;	
	
	@Autowired
	private CidadesDao cidadesDao;	
	
	@Autowired
	private BairrosDao bairrosDao;	
	
	@Autowired
	private NotaService notaService;	
	
	@Autowired
	private UsuarioDao usuarioDao;		
	
	@Autowired
	private ImovelFavoritosService imovelFavoritosService;
		
	public Preferencialocalidade recuperarPreferencialocalidadePorId(Long id) {
		return dao.findPreferencialocalidadeById(id);
	}

	
	public Preferencialocalidade recuperarPreferenciaLocalidadePorId(long idPrefLocalidade) {		
		return dao.findPreferencialocalidadeById(idPrefLocalidade);
	}

	
	@Transactional
	public void adicionarPreferencia(Long idUsuario, PreferencialocalidadeForm form) {
		
		Preferencialocalidade local = new Preferencialocalidade();
		BeanUtils.copyProperties(form, local);
        local.setUsuario(usuarioDao.findUsuario(idUsuario));
        Estados estado = estadosDao.findEstadosById(form.getIdEstado());
        local.setEstado(estado.getUf());
        local.setNomeEstado(estado.getNome());        
        
        if ( form.getIdCidade() > 0 ) {             
           local.setNomeCidade(cidadesDao.findCidadesById(form.getIdCidade()).getNome());
        }
        
        if ( form.getIdBairro() > 0 ){            
           local.setNomeBairro(bairrosDao.findBairrosById(form.getIdBairro()).getNome());
        }         
        dao.save(local);             
     //   notaService.cadastrarNotaPreferenciaLocalidade(getBundle().getString("msg.usuario.para.adicionar.preferencia.nota.imovel"), idUsuario, local.getId(), new Date(),"preferencia");    
	}

	
	public List<Preferencialocalidade> listarPreferenciaPorUsuario(Long idUsuario) {		
		return dao.findPreferencialocalidadeByIdUsuario(idUsuario);
	}

	@Transactional
	public void excluirPreferencia(Preferencialocalidade local) {		
		dao.delete(local);
	}
	
	@Transactional
	public void excluirPreferencia(Long idPreferencia) {		
		dao.delete(dao.findPreferencialocalidadeById(idPreferencia));
	}

	@Override
	public List<Preferencialocalidade> buscarPreferencia(Imovel imovel) {
		return dao.findPreferencialocalidade(imovel);
	}

	@Override
	public List<Usuario> buscarPreferenciaSemDuplicidadeUsuario(UsuarioForm form) {		
		return (List<Usuario>) dao.findPreferencialocalidadeSemDuplicidadeUsuario(form);
	}

	@Override
	public Preferencialocalidade findPreferencialocalidadeByIdUsuarioByRandom(Long idUsuario) {
		
		List<Preferencialocalidade> lista = dao.findPreferencialocalidadeByIdUsuario(idUsuario);
        Preferencialocalidade preferencia = null;
        if ( !CollectionUtils.isEmpty(lista)){ // selecionando randomicamente um elemento da lista
            int size = lista.size();
            int i =  (int)(Math.random() * size);
            preferencia = lista.get(i);
        }
        
        return preferencia;
	}
	

	@Override
	public boolean validarCadastroPreferencia (PreferencialocalidadeForm form, BindingResult result){
		
		boolean obrigatoriedade = false;	
		
		if ( form.getIdEstado() == -1 ) {
			result.rejectValue("idEstado", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
	    }

		if ( StringUtils.isEmpty(form.getTipoImovel())){
			result.rejectValue("tipoImovel", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
		}

		if ( StringUtils.isEmpty(form.getAcao())){
			result.rejectValue("acao", "msg.erro.campo.obrigatorio");
			obrigatoriedade = true;
		}		
		
		return obrigatoriedade;
	}


	@Override
	public List<Imovel> recuperarImoveisPrefLocalidade(UsuarioForm user, int index, int regra) {
		return dao.findImoveisByPrefLocalidadeByUsuarioByIndex(user, index, regra);
	}


	@Override
	public Preferencialocalidade recuperarPrefLocalidadeByUsuarioByIndexByAleatoriamente(List<Long> listaIds, int index, boolean isAleatorio) {
		return dao.findPreferencialocalidadeByUsuarioByIndexByAleatorio(listaIds, index, isAleatorio);
	}
	
	@Override
	public Preferencialocalidade recuperarPrefLocalidadeByUsuarioByIndexByAleatoriamente(int index) {
		return dao.findPreferencialocalidadeByUsuarioByIndexByAleatorio(index);
	}


	@Override
	public String validarExclusaoPrefencia(UsuarioForm user) {
		List<Preferencialocalidade> lista = dao.findPreferencialocalidadeByIdUsuario(user.getId());
		if (AppUtil.recuperarQuantidadeLista(lista) <= 1)
			return MessageUtils.getMessage("msg.erro.limite.minimo.pref.imoveis");
		else
			return null;
	}
	
}
