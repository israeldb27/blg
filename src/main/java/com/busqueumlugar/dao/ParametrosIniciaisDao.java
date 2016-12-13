package com.busqueumlugar.dao;

import com.busqueumlugar.model.ParametrosIniciais;


public interface ParametrosIniciaisDao extends GenericDAO<ParametrosIniciais, Long> {
	
	ParametrosIniciais findParametrosIniciaisById(Long id);

	ParametrosIniciais findParametrosIniciaisByNome(String nomeParametro);

}
