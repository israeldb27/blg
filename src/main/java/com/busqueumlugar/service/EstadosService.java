package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Estados;
import com.busqueumlugar.util.Select;

public interface EstadosService {
	
	Estados rescuperarEstadosPorId(int id);
	
	List<Estados> relatorioSobreEstados(RelatorioForm frm);
	
	List<Estados> listarTodosEstados();
	
	List<Select> listarTodosEstadosSelect();
	
	Estados selecionaEstadoPorId(int idEstado);
	
	List<Estados> selecionaEstadosPorId(int idEstado);
	
}
