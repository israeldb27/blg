package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.model.PossivelInteressado;

public interface PossivelInteressadoService {
	
	PossivelInteressado recuperarPossivelInteressadoPorId(Long id);

	void cadastrarPossivelInteressado(Long idUsuario, Long idImovel);

	List<PossivelInteressado> recuperarListaPossivelInteressadoPorIdImovel(	Long idImovel);

	boolean checarUsuarioPossivelInteressadoImovel(Long idUsuarioRec, Long idImovel);

	void editarPossivelInteressado(Long id, String chanceInteresse, String observacao);

	List<PossivelInteressado> recuperarListaPossivelInteressadoPorIdImovelPorQuant(	Long idImovel, int quantMaxLista);
	

}
