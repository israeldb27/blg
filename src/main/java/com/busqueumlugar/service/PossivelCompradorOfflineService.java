package com.busqueumlugar.service;

import com.busqueumlugar.model.PossivelCompradorOffline;

public interface PossivelCompradorOfflineService {

	PossivelCompradorOffline recuperarPossivelCompradorOfflinePorId(Long id);
	
	List<PossivelCompradorOffline> recuperarListaPossivelCompradorOfflinePorIdImovel(Long idImovel);
}
