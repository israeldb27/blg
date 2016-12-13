package com.busqueumlugar.service.impl;

import java.util.Date;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busqueumlugar.dao.AvaliacaousuarioDao;
import com.busqueumlugar.model.Avaliacaousuario;
import com.busqueumlugar.service.AvaliacaousuarioService;

@Service
public class AvaliacaousuarioServiceImpl implements AvaliacaousuarioService {
	
	private static final Logger log = LoggerFactory.getLogger(AvaliacaousuarioServiceImpl.class);

	@Autowired(required = true)
	private AvaliacaousuarioDao dao;

	
	public Avaliacaousuario recuperarAvaliacaousuarioPorId(Long id) {		
		return dao.findAvaliacaousuarioById(id);
	}


	public boolean checarAvaliacaoUsuario(Long idUsuario, Long idPerfil, String statusAvaliacao) {
		return false;
	}

	@Transactional
	public void cadastrarAvaliacaoUsuario(Long idUsuarioCliente, Long idPerfil,	String status) {
		Avaliacaousuario aval = new Avaliacaousuario();
    	aval.setDataAvaliacao(new Date());
    	aval.setIdUsuario(idPerfil);
    	aval.setIdUsuarioCliente(idUsuarioCliente);
    	aval.setStatusAvaliacao(status);
		dao.save(aval);
	}

}
