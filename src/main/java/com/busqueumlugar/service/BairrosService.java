package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Bairros;
import com.busqueumlugar.util.Select;

public interface BairrosService {
	
	Bairros recuperarBairrosPorId(int id);
	
	List<Bairros> relatorioSobreBairros(RelatorioForm frm);
		
	List<Bairros> selecionarBairrosPorIdCidade(int idCidade);
	
	Bairros selecionarBairroPorId(int id);

	List<Select> selecionarBairrosPorIdCidadeSelect(Integer idCidade);

	

}
