package com.busqueumlugar.dao;

import com.busqueumlugar.model.Formapagamento;

public interface FormapagamentoDao extends GenericDAO<Formapagamento, Long> {
	
	Formapagamento findFormapagamentoById(Long id);

}
