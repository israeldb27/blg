package com.busqueumlugar.dao;

import com.busqueumlugar.model.PossivelComprador;

public interface PossivelCompradorDao extends GenericDAO<PossivelComprador, Long> {
	
	PossivelComprador findPossivelCompradorById(Long id);
	
	

}
