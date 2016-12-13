package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Bairros;


public interface BairrosDao extends GenericDAO<Bairros, Long>  {
	
	Bairros findBairrosById(int id);
	
	List<Bairros> findBairrosPorIdCidade(int idCidade);
	
	List relatorioSobreBairros(RelatorioForm form);
	
	
	

}
