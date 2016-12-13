package com.busqueumlugar.service;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImoveldestaqueForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imoveldestaque;
import com.busqueumlugar.model.Imovelfotos;

public interface ImoveldestaqueService {
	
	Imoveldestaque recuperarImoveldestaquePorId(Long id);
		
	void cadastrarImovelDestaque(Imoveldestaque imovelDestaque);
	
	List<Imoveldestaque> recuperarImoveisDestaquePorIdImovel(Long idImovel);
	
	void excluirImovelDestaque(Long idImovelDestaque);
	
	List<Imoveldestaque> recuperarImoveisDestaquePorDataDestaque(Date dataDestaque, int quantImoveis);
	
	List<Imoveldestaque> recuperarImoveisDestaqueAnuncioPorDataDestaque(int quantImoveis);

	long checarQuantidadeImoveisAnuncioNoDia();

	Imovel recuperarImovelAnuncio(int posicao);

	String validarCadastroAnuncioImovel(String dataInicio, String dataFim, ImovelForm form);

	List<Imoveldestaque> recuperarListaAnuncioPorImovel(Long idUsuario);

	void cadastrarAnuncioImovel(String dataInicio, String dataFim, ImovelForm form);

	List<Imoveldestaque> recuperarImoveisDestaqueAnuncioPorDataDestaque(ImoveldestaqueForm form);

	List<Imoveldestaque> recuperarImoveisAnuncioPorImovel(Long idImovel);
}
