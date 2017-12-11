package com.busqueumlugar.dao;


import java.util.List;

import com.busqueumlugar.model.PossivelCompradorOffline;

public interface PossivelCompradorOfflineDao extends GenericDAO<PossivelCompradorOffline, Long> {
	
	PossivelCompradorOffline findPossivelCompradorOfflineById(Long id);

	List<PossivelCompradorOffline> findPossivelCompradorOfflineByIdImovel(Long idImovel);

	List<PossivelCompradorOffline> findPossivelCompradorOfflineByIdImovelByQuant(Long idImovel, int quantMaxLista);

}
