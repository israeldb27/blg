package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.Atividades;


public interface AtividadesDao extends GenericDAO<Atividades, Long>{
	
	Atividades findAtividadesById(Long id);

	List<Atividades> findAtividadesByIdImovel(Long idImovel);

	List<Atividades> findAtividadesByIdImovelByIdUsuario(Long idImovel,	Long idUsuario);
	

}
