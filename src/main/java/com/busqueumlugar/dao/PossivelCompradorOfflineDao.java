package com.busqueumlugar.dao;


import com.busqueumlugar.model.PossivelCompradorOffline;

public interface PossivelCompradorOfflineDao extends GenericDAO<PossivelCompradorOffline, Long> {
	
	PossivelCompradorOffline findPossivelCompradorOfflineById(Long id);

}
