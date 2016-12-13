package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.Cidades;
import com.busqueumlugar.model.Infoservico;

public interface InfoservicoDao extends GenericDAO<Infoservico, Long> {

	Infoservico findInfoservicoById(Long id);
	
	List<Infoservico> findInfoServicoByIdParametroServico(Long idParametro);
	
	Infoservico findInfoservicoByIdParamByOpcaoTempoByFormaPagto(Long idParam, String opcaoTempoPagto);
	
	List<Infoservico> filterInfoServicoByIdParamServicoPorIdFormaPagto(Long idParam, Long idFormaPagto);
	
}
