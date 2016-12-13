	package com.busqueumlugar.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.PlanousuarioForm;
import com.busqueumlugar.form.ServicoForm;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.RelatorioQuantPlano;
import com.paypal.api.payments.Payment;

public interface PlanousuarioService {
	
	Planousuario recuperarPlanousuarioPorId(Long id);
	
	void cadastrarSolicitacaoPlano(Long idPlanoSelecionado, Long idUsuario, String nomeFormaPagto);
	
	void simulaRetornoPagtoPlano(Long idPlanoUsuario);
	
	List<Planousuario> recuperarPlanosPorUsuario(Long idUsuario);
	
	List<Planousuario> recuperarPlanosPorUsuarioPorStatus(Long idUsuario, AdministracaoForm form);

	Long recuperarUltimoIdPlanoGerado(Long idUsuario);

	long checarQuantTotalPlanosPorData(AdministracaoForm form);

	List checarVolumeFinanceirosPlanoPorDataPorStatus(AdministracaoForm form);

	Payment confirmarPagamentoPayPalPlano(HttpServletRequest req, HttpServletResponse resp, ServicoForm form);

	void excluirPlanousuario(Long idPlano);

	ServicoForm carregarPlanousuario(Long idPlano);

	String validarSolicitacaoPlano(Long idUsuario, ServicoForm form);
	
	String validarSolicitacaoPlano(Long idUsuario, Long idPlano);

	List<RelatorioQuantPlano> recuperarUsuariosPlanosVolFinanceiro(AdministracaoForm form);

	void concedePlano(Long idUsuario, long idPLanoSelecionado);

	void revogarPlano(Long idUsuario, Long idPlanoSelecionado);

	String validarCadastroSolicitacaoPlano(Long idUsuario, ServicoForm form);

}
