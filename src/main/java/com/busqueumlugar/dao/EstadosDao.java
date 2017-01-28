package com.busqueumlugar.dao;


import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Estados;

public interface EstadosDao extends GenericDAO<Estados, Long> {
	
	Estados findEstadosById(int id);
	
	List<Estados> findEstadosPorIdEstado(int idEstado);
	
	List<Object> relatorioSobreEstados(RelatorioForm form);
	
}
