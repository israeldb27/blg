package com.busqueumlugar.service;

import com.busqueumlugar.model.Avaliacaousuario;

public interface AvaliacaousuarioService {
	
	Avaliacaousuario recuperarAvaliacaousuarioPorId(Long id);
	
	boolean checarAvaliacaoUsuario(Long idUsuario, Long idPerfil, String statusAvaliacao);
	
	void cadastrarAvaliacaoUsuario(Long idUsuarioCliente, Long idPerfil, String status);

}
