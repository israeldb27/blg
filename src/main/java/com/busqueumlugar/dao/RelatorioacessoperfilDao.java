package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.Relatorioacessoperfil;

public interface RelatorioacessoperfilDao extends GenericDAO<Relatorioacessoperfil, Long> {
	
	Relatorioacessoperfil findRelatorioacessoperfilById(Long id);
	
	List<Relatorioacessoperfil> findRelatorioAcessoPerfilByIdRelatorio(Long idRelatorio);

}
