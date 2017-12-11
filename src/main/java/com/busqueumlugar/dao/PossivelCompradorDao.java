package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.PossivelComprador;

public interface PossivelCompradorDao extends GenericDAO<PossivelComprador, Long> {
	
	PossivelComprador findPossivelCompradorById(Long id);

	List<PossivelComprador> findPossivelCompradorByIdImovel(Long idImovel);

	PossivelComprador findPossivelCompradorByIdUsuarioByIdImovel(Long idUsuario, Long idImovel);

	List<PossivelComprador> findPossivelCompradorByIdImovelByQuant(Long idImovel, int quantMaxLista);	
	
}
