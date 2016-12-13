package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Cidades;

public interface CidadesDao extends GenericDAO<Cidades, Long> {

	Cidades findCidadesById(int id);
	
	List<Cidades> findCidadesPorIdEstado(int idEstado);
	
	List relatorioSobreCidades(RelatorioForm form);
}
