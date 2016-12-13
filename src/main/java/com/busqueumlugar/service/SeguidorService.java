package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.model.Seguidor;

public interface SeguidorService {
	
	Seguidor recuperarSeguidorPorId(Long id);

	String checarUsuarioEstaSeguindo(Long idUsuarioSessao, Long idUsuario);

	void iniciarSeguidor(Long idUsuarioSeguido, Long idUsuario);

	void cancelarIniciarSeguidor(Long idUsuarioSeguido, Long idUsuario);

	List<Seguidor> recuperarSeguidoresPorIdUsuarioSeguido(Long idUsuario);

	List recuperarIdsSeguidores(Long idUsuario);

	long checarQuantidadeSeguidores(Long idUsuario);

}