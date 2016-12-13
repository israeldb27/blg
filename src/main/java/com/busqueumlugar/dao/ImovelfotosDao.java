package com.busqueumlugar.dao;

import java.util.List;
import com.busqueumlugar.model.Imovelfotos;

public interface ImovelfotosDao extends GenericDAO<Imovelfotos, Long> {

	Imovelfotos findImovelfotosById(Long id);
	
	List<Imovelfotos> findImovelfotosByIdImovel(Long idImovel);
}
