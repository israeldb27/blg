package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.Plano;

public interface PlanoDao extends GenericDAO<Plano, Long> {
	
	Plano findPlanoById(Long id);
	
	Plano findPlanoPorNome(String nome);
	
	List<Plano> findPlanoByValor(String valor);	

}
