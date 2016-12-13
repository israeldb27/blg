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
	
	Imovelvisualizado findImovelvisitadoById(Long id);
	
	List<Imovelvisualizado> findImoveisMaisVisitadosOrderDesc();
	
	List<Imovelvisualizado> filterImovelVisitadoHoje(DateUtil dtHoje, ImovelForm imovelForm);
	
	List relatorioImovelVisitadoPeriodo(RelatorioForm form);
	
	List relatorioImovelVisitadoPeriodo(AdministracaoForm form);
	
	List<Imovelvisualizado> filterImovelVisitadoPeriodo(DateUtil dataHoje, DateUtil dataSemana, ImovelForm imovelForm);
	
	List<Imovelvisualizado> findImoveisvisitadosByIdDonoImovel(Long idUsuario, ImovelvisualizadoForm form);
	
	List<Imovelvisualizado> findImoveisvisitadosByIdDonoImovelNovos(Long idUsuario);
	
	List<Imovelvisualizado> findImoveisVisitadosPorIdImovel(Long idImovel);
	
	List filterImovelVisitadoPorDataPorQuantImoveis(Date dataInicio, Date dataFim, int quantImoveis);
	
	List checarImoveisComMaisVisitasPeriodo(Date dataInicio, Date dataFim, int quant);
	
	Imovelvisualizado findImovelVisitadoPorUsuarioPorImovel(Long idUsuario, Long idImovel);	
	
	Imovelvisualizado findLastImoveloVisitadoByIdImovel(Long idImovel);

	List<Imovelvisualizado> findImoveisvisitadosByIdUsuario(Long idUsuario, ImovelvisualizadoForm form);

	List findMeusImoveisVisitadosByIdUsuarioDistinct(Long idUsuario, ImovelvisualizadoForm form);

	int findQuantidadeNovosImoveisVisitados(Long idImovel);

	List findUsuariosMeusImoveisVisitadosByIdUsuarioDistinct(Long idUsuario, ImovelvisualizadoForm form);

	List findUsuariosImoveisVisitadosByIdUsuarioDistinct(Long idUsuarioSessao, ImovelvisualizadoForm form);

	List<Imovelvisualizado> findImoveisvisitadosByIdUsuarioByIdDonoImovel(Long idUsuarioSessao, Long idUsuario);

	List<Imovelvisualizado> filterUsuariosVisitantes(Long idUsuario, ImovelvisualizadoForm form);

	List<Imovelvisualizado> filterImoveisVisitados(Long idUsuario, ImovelvisualizadoForm form);
	
	Imovelvisualizado findImovelVisitadoRandomByIdUsuario(Long idUsuario);

	List filterAgruparMeusImoveisVisitadosByIdUsuarioDistinct(Long idUsuario, ImovelvisualizadoForm form);

	long findQuantidadeVisitantesByIdUsuarioByStatus(Long idUsuario, String status);

	void updateStatusLeitura(Long idUsuario);	
	
}
