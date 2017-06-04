package com.busqueumlugar.dao;


import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.model.Imovelindicado;

public interface ImovelindicadoDao extends GenericDAO<Imovelindicado, Long> {
	
	Imovelindicado findImovelindicadoById(Long id);
	
	List<Imovelindicado> findImoveisPorUsuario(Long idUsuario, ImovelindicadoForm form);
	
	List<Imovelindicado> findImoveisIndicacoesPorUsuario(Long idUsuario, ImovelindicadoForm form);
	
	Imovelindicado findImovelIndicadoByIdImovel(Long idImovel);
	
	Imovelindicado findImovelIndicacaoByIdImovelIdUsuario(Long idImovel, Long idUsuario);
	
	Imovelindicado findImovelIndicadoByIdImovelIdUsuario(Long idImovel, Long idUsuario);
	
	List<Imovelindicado> findImoveisIndicadosNovos(Long idUsuario);
	
	List checarImoveisComMaisIndicacoesPeriodo(Date dataInicio, Date dataFim, int quant);

	List checarImoveisComMaisIndicacoesPeriodo(AdministracaoForm form);
	
	List<Imovelindicado> filterImoveisIndicacoes(Long idUsuario, ImovelindicadoForm form);

	List<Imovelindicado> filterImoveisIndicados(Long idUsuario, ImovelindicadoForm form);

	List findInfoAgruparUsuariosImoveisIndicacoes(Long idUsuarioIndicacao);

	List findImoveisPorUsuarioDistinct(Long idUsuario, ImovelindicadoForm form);

	List findImoveisIndicacoesPorUsuarioDistinct(Long idUsuario, ImovelindicadoForm form);

	List findImoveisIndicacoesByIdUsuarioDistinct(Long idUsuario, ImovelindicadoForm form);

	long findQuantidadeNovosImoveisIndicacoes(Long idImovel);

	List<Imovelindicado> findImoveisIndicadosByIdImovel(Long idImovel);

	List<Imovelindicado> findImoveisIndicadosParaMimByIdUsuarioSessaoByIdUsuario(Long idUsuarioSessao, Long idUsuario);
	
	List filtrarAgruparImoveis(Long idUsuario, ImovelindicadoForm form);

	List<Imovelindicado> findImoveisIndicadosByIdImovelPorIdUsuarioIndicador(Long idImovel, Long idUsuarioIndicador);

	List<Imovelindicado> findImoveisNovosPorUsuario(Long idUsuario);	
	
	void updateStatusLeituraByIdUsuarioIndicado(Long idUsuario);

	long findQuantImoveisIndicadosByIdUsuarioByStatusLeitura(Long idUsuario, String statusLeitura);

	long findQuantImoveisIndicacoesByIdUsuarioByStatusLeitura(Long idUsuario, String statusLeitura);

}
