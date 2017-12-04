package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.model.PossivelComprador;

public interface PossivelCompradorService {
	
	PossivelComprador recuperarPossivelCompradorPorId(Long id);

	void cadastrarPossivelComprador(Long idUsuario, Long idImovel);

	List<PossivelComprador> recuperarListaPossivelCompradorPorIdImovel(	Long idImovel);

	boolean checarUsuarioPossivelCompradorImovel(Long idUsuarioRec, Long idImovel);

	void editarPossivelComprador(Long id, String chanceCompra, String observacao);
	

}
