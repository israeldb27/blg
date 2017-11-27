package com.busqueumlugar.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.PossivelCompradorDao;
import com.busqueumlugar.model.PossivelComprador;
import com.busqueumlugar.service.PossivelCompradorService;

@Service
public class PossivelCompradorServiceImpl implements PossivelCompradorService {
	
	@Autowired
	private PossivelCompradorDao dao;

	@Override
	public PossivelComprador recuperarPossivelCompradorPorId(Long id) {
		return dao.findPossivelCompradorById(id);
	}

}
