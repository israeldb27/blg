package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.util.Select;

public interface CidadesService {
		
	Cidades recuperarCidadesPorId(int id);
	
	List<Cidades> relatorioSobreCidades(RelatorioForm frm);
	
	Cidades selecionarCidadesPorId(int idCidade);
	
	List<Cidades> selecionarCidadesPorIdEstado(int idEstado);
	
	List<Select> selecionarCidadesPorIdEstadoSelect(int idEstado);

}
