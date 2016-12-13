package com.busqueumlugar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.RelatorioacessoperfilDao;
import com.busqueumlugar.model.Relatorioacessoperfil;
import com.busqueumlugar.service.RelatorioacessoperfilService;

@Service
public class RelatorioacessoperfilServiceImpl implements RelatorioacessoperfilService {
	
	private static final Logger log = LoggerFactory.getLogger(RelatorioacessoperfilServiceImpl.class);

	@Autowired(required = true)
	private RelatorioacessoperfilDao dao;	
	
	public Relatorioacessoperfil recuperarRelatorioacessoperfilPorId(Long id) {
		return dao.findRelatorioacessoperfilById(id);
	}

}
