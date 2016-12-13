package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.model.Servico;

public interface ServicoDao extends GenericDAO<Servico, Long> {
	
	Servico findServicoById(Long id);
	
	List<Servico> findServicoPagoPorIdUsuario(Long idUsuario, String descServico, Date dataAtual);
	
	List<Servico> findServicoPorIdImovel(Long idImovel);
	
	List<Servico> findServicosByIdUsuario(Long idUsuario);
	
	List findServicosMaisPorPeriodo(Date dataInicio, Date dataFim, String statusServico);
	
	double calcularVolumeFinanceiroMaisSolicitadosMaisPagosPorPeriodoPorTipoServico(Date dataInicio, Date dataFim, String statusServico, String tipoServico);
	
	List findUsuariosVolFinanceiroServico(AdministracaoForm form);
	
	List findServicosVolFinanceiroServico(Date dataInicio, Date dataFim, String statusServico);
	
	Servico findServicoByIdParamServicoByData(long idParamServico, long idUsuario, Date dtAtual);
	
	Servico findServicoByLabelServicoServicoByData(String nomeServico, long idUsuario, Date dtAtual);	
	
	double calcularVolumeFinanceiroMaisConcedidosPorPeriodo(Date dataInicio, Date dataFim);
	
	List<Servico> findServicosByIdUsuarioByAssocPlano(Long idUsuario, String assocPlano);
	
	List<Servico> findServicosByIdUsuarioByTipoServico(Long idUsuario, String tipoServico);
	
	Servico findServicoAssinaturaSolicitadaByIdUsuario(Long idUsuario, String tipoAssinatura);
	
	Servico findServicoRelatorioByIdUsuario(Long idUsuario, String tipoRelatorio, Date dtCorrente);
	
	List findTotalServicosPorPeriodo(AdministracaoForm form);
	
	List findVolumeFinanceiroServicos(AdministracaoForm form);
	
	Servico findLastServicoGeradoByIdUsuario(long idUsuario, String descServico);
	
	List<Servico> findServicoByIdUsuarioByStatus(Long idUsuario, String status);

	Servico findServicoByIdParamServicoByIdUsuarioByStatusByTipoServico(long idParamServico, long idUsuario, String status, String tipoServico);

	Servico findSolAssinaturaByIdUsuario(Long idUsuario, String tipoAssinatura);
	
	List<Servico> findServicosByPeriodoByTipoServico(AdministracaoForm form, String tipoServico);

	Servico findServicoByToken(String parameter);

	List<Servico> findServicosByIdPlanoUsuario(Long idPlanoUsuario);

	long findTotalTipoServicoPorPeriodo(AdministracaoForm form);
}
