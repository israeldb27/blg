package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.Paramservico;

public interface ParamservicoDao extends GenericDAO<Paramservico, Long> {
	
	Paramservico findParamservicoById(Long id);
	
	Paramservico findParamservicoPorNome(String nome);
	
	List<Paramservico> findParamservicoPorTipo(String tipo);
	
	List<Paramservico> findAllParamServicos();
	
	List<Paramservico> findParamServicoByValor(String valor);

	List<Paramservico> findAllParametrosServicos();
	

}
