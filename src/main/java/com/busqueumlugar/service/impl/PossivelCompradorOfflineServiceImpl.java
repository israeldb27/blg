package com.busqueumlugar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.busqueumlugar.dao.PossivelCompradorOfflineDao;
import com.busqueumlugar.model.PossivelCompradorOffline;
import com.busqueumlugar.service.PossivelCompradorOfflineService;

@Service
public class PossivelCompradorOfflineServiceImpl implements	PossivelCompradorOfflineService {
	
	private static final Logger log = LoggerFactory.getLogger(PossivelCompradorOfflineServiceImpl.class);
	
	@Autowired
	private PossivelCompradorOfflineDao dao;

	@Override
	public PossivelCompradorOffline recuperarPossivelCompradorOfflinePorId(	Long id) {
		return dao.findPossivelCompradorOfflineById(id);
	}

}
