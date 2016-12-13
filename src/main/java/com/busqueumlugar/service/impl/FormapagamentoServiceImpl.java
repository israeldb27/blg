package com.busqueumlugar.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busqueumlugar.dao.FormapagamentoDao;
import com.busqueumlugar.model.Formapagamento;
import com.busqueumlugar.service.FormapagamentoService;

@Service
public class FormapagamentoServiceImpl implements FormapagamentoService {	
	
	private static final Logger log = LoggerFactory.getLogger(FormapagamentoServiceImpl.class);

	@Autowired(required = true)
	private FormapagamentoDao dao;	
	
	public Formapagamento recuperarFormapagamentoPorId(Long id) {		
		return dao.findFormapagamentoById(id);
	}

}
