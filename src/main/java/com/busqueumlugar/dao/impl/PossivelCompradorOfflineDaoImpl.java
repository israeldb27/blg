package com.busqueumlugar.dao.impl;

import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.PossivelCompradorOfflineDao;
import com.busqueumlugar.model.PossivelCompradorOffline;


@Repository
public class PossivelCompradorOfflineDaoImpl extends GenericDAOImpl<PossivelCompradorOffline, Long> implements PossivelCompradorOfflineDao {

	public PossivelCompradorOfflineDaoImpl() {
		super(PossivelCompradorOffline.class);
	}
	
	@Override
	public PossivelCompradorOffline findPossivelCompradorOfflineById(Long id) {
		return null;
	}

}
