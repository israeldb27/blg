package com.busqueumlugar.dao.impl;

import org.springframework.stereotype.Repository;

import com.busqueumlugar.dao.PossivelCompradorDao;
import com.busqueumlugar.model.Atividades;
import com.busqueumlugar.model.PossivelComprador;


@Repository
public class PossivelCompradorDaoImpl extends GenericDAOImpl<PossivelComprador, Long>  implements PossivelCompradorDao{

	public PossivelCompradorDaoImpl() {
		super(PossivelComprador.class);
	}

	@Override
	public PossivelComprador findPossivelCompradorById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


}
