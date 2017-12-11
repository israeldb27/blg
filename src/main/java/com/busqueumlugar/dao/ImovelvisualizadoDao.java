package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelvisualizadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.util.DateUtil;

public interface ImovelvisualizadoDao extends GenericDAO<Imovelvisualizado, Long> {
	
	Imovelvisualizado findImovelvisualizadoById(Long id);
	
	List<Imovelvisualizado> findImoveisMaisVisitadosOrderDesc();
	
	List<Imovelvisualizado> filterImovelVisualizadoHoje(DateUtil dtHoje, ImovelForm imovelForm);
	
	List relatorioImovelVisualizadoPeriodo(RelatorioForm form);
	
	List relatorioImovelVisitadoPeriodo(AdministracaoForm form);
	
	List<Imovelvisualizado> filterImovelVisualizadoPeriodo(DateUtil dataHoje, DateUtil dataSemana, ImovelForm imovelForm);
	
	List<Imovelvisualizado> findImoveisvisualizadosByIdDonoImovel(Long idUsuario, ImovelvisualizadoForm form);
	
	List<Imovelvisualizado> findImoveisvisualizadosByIdDonoImovelNovos(Long idUsuario);
	
	List<Imovelvisualizado> findImoveisVisitadosPorIdImovel(Long idImovel);
	
	List filterImovelVisualizadoPorDataPorQuantImoveis(Date dataInicio, Date dataFim, int quantImoveis);
	
	List checarImoveisComMaisVisitasPeriodo(Date dataInicio, Date dataFim, int quant);
	
	Imovelvisualizado findImovelVisualizadoPorUsuarioPorImovel(Long idUsuario, Long idImovel);	
	
	Imovelvisualizado findLastImovelVisualizadoByIdImovel(Long idImovel);

	List<Imovelvisualizado> findImoveisvisualizadosByIdUsuario(Long idUsuario, ImovelvisualizadoForm form);

	List findMeusImoveisVisualizadoByIdUsuarioDistinct(Long idUsuario, ImovelvisualizadoForm form);

	long findQuantidadeImoveisVisitadosPorImovelPorStatus(Long idImovel, String statusLeitura);

	List findUsuariosMeusImoveisVisitadosByIdUsuarioDistinct(Long idUsuario, ImovelvisualizadoForm form);

	List findUsuariosImoveisVisitadosByIdUsuarioDistinct(Long idUsuarioSessao, ImovelvisualizadoForm form);

	List<Imovelvisualizado> findImoveisvisitadosByIdUsuarioByIdDonoImovel(Long idUsuarioSessao, Long idUsuario);

	List<Imovelvisualizado> filterUsuariosVisitantes(Long idUsuario, ImovelvisualizadoForm form);

	List<Imovelvisualizado> filterImoveisVisitados(Long idUsuario, ImovelvisualizadoForm form);
	
	Imovelvisualizado findImovelVisitadoRandomByIdUsuario(Long idUsuario);

	List filterAgruparMeusImoveisVisitadosByIdUsuarioDistinct(Long idUsuario, ImovelvisualizadoForm form);

	long findQuantidadeVisitantesByIdUsuarioByStatus(Long idUsuario, String status);

	void updateStatusLeitura(Long idUsuario);

	Imovelvisualizado findImoveisVisitadosByIdUsuarioByIndex(Long idUsuario, int idIndex);

	List findUsuariosImoveisVisitadosSemelhantes(Long idUsuario, ImovelForm form);

	List findUsuariosImoveisVisitados(Long idUsuario, ImovelForm form);

	List<Imovelvisualizado> findImoveisVisitadosPorIdImovelByQuant(Long idImovel, int quantMaxLista);	
	
}
