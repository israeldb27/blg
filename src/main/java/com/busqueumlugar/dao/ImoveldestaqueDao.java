package com.busqueumlugar.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.ImoveldestaqueForm;
import com.busqueumlugar.model.Imoveldestaque;

public interface ImoveldestaqueDao extends GenericDAO<Imoveldestaque, Long> {

	Imoveldestaque findImoveldestaqueById(Long id);
	
	List<Imoveldestaque> findAllImoveisDestaqueByStatus(String status);

	List<Imoveldestaque> findImovelDestaquePorAnuncio(Date dataAtual, String tipo, String status);
	
	List<Imoveldestaque> findImoveldestaqueByIdImovel(Long idImovel);
	
	List<Imoveldestaque> findImoveisDestaqueByDataDestaqueByQuantidade(Date dataDestaque, int quantImoveis);	
	
	List<Imoveldestaque> findImoveisDestaqueAnuncioByDataDestaqueByQuantidade(int quantImoveis);

	long findQuantImoveisAnunciosByDia();

	Imoveldestaque findImovelAnuncioByDiaByPosicao(int posicao);

	List<Imoveldestaque> findImoveisAnuncios(ImoveldestaqueForm form);

	List<Imoveldestaque> findImoveisAnunciosPorImovel(Long idImovel);

	Imoveldestaque findImovelAnuncioByData(Date dtIncio, Date dtFim, Long idImovel);	
	
}
