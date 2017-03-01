package com.busqueumlugar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.SeguidorDao;
import com.busqueumlugar.dao.UsuarioDao;
import com.busqueumlugar.enumerador.StatusLeituraEnum;
import com.busqueumlugar.model.Recomendacao;
import com.busqueumlugar.model.Seguidor;
import com.busqueumlugar.service.SeguidorService;
import com.busqueumlugar.service.UsuarioService;

@Service
public class SeguidorServiceImpl implements SeguidorService {

	private static final Logger log = LoggerFactory.getLogger(SeguidorServiceImpl.class);
	
	@Autowired
	private SeguidorDao dao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public Seguidor recuperarSeguidorPorId(Long id) {
		return dao.findSeguidorById(id);
	}

	@Override
	public String checarUsuarioEstaSeguindo(Long idUsuarioSessao, Long idUsuario) {
		Seguidor seguidor = dao.findSeguidorByIdUsuarioSeguidorByIdUsuario(idUsuarioSessao, idUsuario);
		if (seguidor != null){
			return "S";
		}
		else
			return "N";
	}

	@Override
	@Transactional
	public void iniciarSeguidor(Long idUsuarioSeguido, Long idUsuario) {		
		Seguidor seguidor = new Seguidor();
		seguidor.setDataInicio(new Date());
		seguidor.setStatus(StatusLeituraEnum.NOVO.getRotulo());
		seguidor.setUsuarioSeguido(usuarioDao.findUsuario(idUsuarioSeguido));
		seguidor.setUsuario(usuarioDao.findUsuario(idUsuario));
		seguidor.setStatus(StatusLeituraEnum.NOVO.getRotulo());
		dao.save(seguidor);
	}

	@Override
	@Transactional
	public void cancelarIniciarSeguidor(Long idUsuario1, Long idUsuario2) {
		
		Seguidor seguidor = dao.findSeguidorByIdUsuarioSeguidorByIdUsuario(idUsuario2 , idUsuario1 );
		if ( seguidor != null )
			dao.delete(seguidor);		
	}

	@Override
	public List<Seguidor> recuperarSeguidoresPorIdUsuarioSeguido(Long idUsuario) {	
		return dao.findSeguidoresByIdUsuarioSeguido(idUsuario);	
	}

	@Override
	public List recuperarIdsSeguidores(Long idUsuario) {
		return dao.findIdsSeguidoresByIdUsuario(idUsuario);
	}

	@Override
	public long checarQuantidadeSeguidores(Long idUsuario) {
		return dao.findQuantSeguidoresByIdUsuarioSeguido(idUsuario);
	}

}
