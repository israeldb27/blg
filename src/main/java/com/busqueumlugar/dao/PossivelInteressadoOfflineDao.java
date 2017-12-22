package com.busqueumlugar.dao;


import java.util.List;

import com.busqueumlugar.model.PossivelInteressadoOffline;

public interface PossivelInteressadoOfflineDao extends GenericDAO<PossivelInteressadoOffline, Long> {
	
	PossivelInteressadoOffline findPossivelInteressadoOfflineById(Long id);

	List<PossivelInteressadoOffline> findPossivelInteressadoOfflineByIdImovel(Long idImovel);

	List<PossivelInteressadoOffline> findPossivelInteressadoOfflineByIdImovelByQuant(Long idImovel, int quantMaxLista);

}
