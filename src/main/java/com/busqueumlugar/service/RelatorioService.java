package com.busqueumlugar.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.FiltroRelatorioForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Relatorio;
import com.busqueumlugar.model.RelatorioQuantAssinatura;
import com.busqueumlugar.model.RelatorioQuantidadeImoveisCriados;
import com.busqueumlugar.model.RelatorioVariacaoPrecoTipoImovel;
import com.busqueumlugar.model.Relatorioacessoperfil;
import com.busqueumlugar.util.Select;
import com.paypal.api.payments.Payment;

public interface RelatorioService {
	
	Relatorio recuperarRelatorioPorId(Long id);	
	
	List<RelatorioQuantidadeImoveisCriados> listarQuantidadeImoveisPorLocalAcaoTipoImovel(Long idUsuario, RelatorioForm frm);
	
	List<RelatorioQuantidadeImoveisCriados> listarTipoImoveisMaisProcuradosPorLocalizacao(Long idUsuario, RelatorioForm frm);
	
	List<RelatorioVariacaoPrecoTipoImovel> recuperarVariacaoPrecoTipoImovel(Long idUsuario, RelatorioForm frm);
	
	List<Select>listarRelatoriosUsuarioSistemaPorOpcaoRelatorio(String itemRelatorio, String perfilUsuario);
	
	List<Relatorioacessoperfil> listarAcessoPerfisRelatorioSistema(Long idRelatorio);
	
	void adicionarPerfilRelatorio(RelatorioForm frm);
	
	String validarAdicionarPerfilRelatorio(RelatorioForm frm);
	
	void cadastrarRelatorioSistema(RelatorioForm frm);
	
	String validarCadastroNovoRelatorioSistema(RelatorioForm frm);
	
	void atualizarRelatorioUsuarioSistema(RelatorioForm frm);
	
	void excluirRelatorioUsuarioSistema(RelatorioForm frm);
	
	List<Relatorio> listarTodosRelatoriosSistemaDisponiveis();
	
	RelatorioForm recuperarRelatorioSistemaPorIdRelatorio(Long idRelatorioSelecionado);
	
	void excluirPerfilRelatorio(Long idRelatorioPerfil);
	
	boolean checarCobrancaModuloRelatorios(Long idUsuario, String perfil, Date dataCadastroUsuario);
	
	List<RelatorioQuantAssinatura> gerarRelatorioAdminQuantTotalAssinaturas(RelatorioForm frm);
	
	List<RelatorioQuantAssinatura> gerarRelatorioAdminAssinaturaVolFinanceiro(RelatorioForm frm);
	
	List<RelatorioQuantAssinatura> gerarRelatorioAdminAssinaturasPorUsuario(RelatorioForm frm);
	
	List<RelatorioQuantidadeImoveisCriados> listarTipoImoveisMaisProcuradosPorTodosUsuarios(Long idUsuario, RelatorioForm frm);

	void carregaRelatorioFormPorPerfilAcessoRelatorio(Long idPerfilAcessoRelatorio, RelatorioForm form);

	Payment confirmarPagamentoPayPal(HttpServletRequest req, HttpServletResponse resp, RelatorioForm form);

	List<FiltroRelatorioForm> checarFiltrosUtilizados(RelatorioForm form);

	boolean validarFiltrosRelatorio(RelatorioForm form, BindingResult result, UsuarioForm usuarioForm);
}
