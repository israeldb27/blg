package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.PossivelInteressado;

public interface PossivelInteressadoDao extends GenericDAO<PossivelInteressado, Long> {
	
	PossivelInteressado findPossivelInteressadoById(Long id);

	List<PossivelInteressado> findPossivelInteressadoByIdImovel(Long idImovel);

	PossivelInteressado findPossivelInteressadoByIdUsuarioByIdImovel(Long idUsuario, Long idImovel);

	List<PossivelInteressado> findPossivelInteressadoByIdImovelByQuant(Long idImovel, int quantMaxLista);	
	
}
