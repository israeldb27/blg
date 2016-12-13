package com.busqueumlugar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.ImovelcompartilhadoDao;
import com.busqueumlugar.service.ParceriaService;

@Service
public class ParceriaServiceImpl implements ParceriaService {
	
private static final Logger log = LoggerFactory.getLogger(ParceriaServiceImpl.class);
	
	@Autowired
	private ImovelcompartilhadoDao dao;

}
