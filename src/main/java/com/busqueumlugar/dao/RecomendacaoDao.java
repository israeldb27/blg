package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.Recomendacao;
import com.busqueumlugar.model.Seguidor;

public interface RecomendacaoDao extends GenericDAO<Recomendacao, Long>  {
	
	Recomendacao findRecomendacaoById(Long idRecomendacao);

	List<Recomendacao> findListRecomendacaoByIdUsuario(Long idUsuario);
	
	List<Recomendacao> findListRecomendacaoByIdUsuario(Long idUsuario, int quantMaxExibeMaisListaRecomendacoes);

	List<Recomendacao> findListRecomendacaoByIdUsuarioNovas(Long idUsuario);

	List<Recomendacao> findListRecomendacaoByIdUsuarioByStatus(Long idUsuarioRecomendado, String status);

	long findQuantidadeRecomendacoesByUsuarioByStatusByStatusLeitura(Long idUsuario, String status, String statusLeitura);

}
