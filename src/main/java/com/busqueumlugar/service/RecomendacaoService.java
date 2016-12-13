package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.model.Recomendacao;

public interface RecomendacaoService {
	
	String QUANT_NOVAS_RECOMENDACOES = "quantNovasRecomendacoes";
	String QUANT_RECOMENDACOES = "quantRecomendacoes";
	String LISTA_RECOMENDACOES = "listaRecomendacoes";
	
	Recomendacao recuperarRecomendacaoPorId(Long idRecomendacao);

	List<Recomendacao> recuperarRecomendacoesPorIdUsuarioRecomendado(Long idUsuario);

	void cadastrarRecomendacao(Long idUsuario, Long idUsuarioSessao, String novaRecomendacao);

	void respostaRecomendacao(Long idUsuario, Long idRecomendacao, String respostaRecomendacao);

	long checarNovasRecomendacoesRecebidas(Long idUsuario);

	List<Recomendacao> recuperarRecomendacoesPorIdUsuarioRecomendadoPorStatus(Long id, String status);

	void atualizarStatusLeituraRecomendacoesPorIdUsuario(List<Recomendacao> lista);

	void atualizarStatusLeituraRecomendacaoSelecionada(Recomendacao rec);

	long checarQuantidadeTotalRecomendacaoRecebidaPorStatus(Long idUsuario, String status);

}
