package com.busqueumlugar.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.FormapagamentoForm;
import com.busqueumlugar.form.ParamservicoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Formapagamento;
import com.busqueumlugar.model.Paramservico;

public interface ParamservicoService {
	
	Paramservico recuperarParamservicoPorId(Long id);
	
	void cadastrarParametro(ParamservicoForm paramservicoForm);
	
	List<Paramservico> recuperaTodosParametros();
	
	List<Paramservico> recuperaTodosParametrosPorTipo(String tipo);
	
	List<Paramservico> recuperaTodosParametrosPorTipoSemAssinatura(UsuarioForm user);
	
	List<Paramservico> recuperaTodosParametrosPorPerfilUsuario(String perfilUsuario);
	
	void excluirParametro(Long idParam);
	
	Paramservico recuperarParamServicoPorId(Long idParam);
	
	ParamservicoForm recuperarParamServicoPorNome(String acao);
	
	Paramservico recuperarParamServicoPorNome2(String acao);
	
	List<Formapagamento> listarTodasFormasPagamento();
	
	void cadastrarTipoFormaPagamento(FormapagamentoForm formaPagtoForm);
	
	void excluirTipoFormaPagamento(Long idFormaPagto);
	
	Formapagamento recuperaTipoFormaPagtoPorId(long idFormaPagamento);
	
	void atualizarOpcaoCobranca(ParamservicoForm paramservicoForm, String opcaoCobranca);
	
	boolean checarServicoCobrado(String descServico);

	void atualizarParametroServico(ParamservicoForm form);
	
	List<Paramservico> pesquisarTodosServicos(String valor);

	boolean validarAtualizacaoParamServic(ParamservicoForm form, BindingResult result);

	boolean validarCadastroParamServico(ParamservicoForm form, BindingResult result);

	boolean validarCadastroTipoFormaPagamento(FormapagamentoForm form, BindingResult result);

	boolean validarAtualizarTipoFormaPagamento(FormapagamentoForm form, BindingResult result);

	void atualizarFormasPagamento(FormapagamentoForm form);

}
