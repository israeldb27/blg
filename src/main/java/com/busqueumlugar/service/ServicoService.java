package com.busqueumlugar.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.form.ParamservicoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.ServicoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.RelatorioQuantAssinatura;
import com.busqueumlugar.model.Servico;
import com.paypal.api.payments.Payment;

import org.springframework.validation.BindingResult;

public interface ServicoService {
	
	String SERVICO_MENSAGENS = "mensagens";
	
	Servico recuperarServicoPorId(Long id);
	
	void cadastrarSolicitacaoPagtoServico(ServicoForm frm, String opcaoTempoPagto);
	
	void cadastrarSolicitacaoPagtoAssinatura(ServicoForm frm);
	
    void cadastrarSolicitacaoPagtoServico(ServicoForm frm, ParamservicoForm paramservicoForm, String opcaoQuantFoto, Long idImovel);
	 
    void cadastrarSolicitacaoPagtoServicoImovel(ServicoForm frm, Long idImovel);
    
    void simulaRetornoPagto(long idServico);
    
    void simulaRetornoPagtoImovel(long idServico);
    
    boolean checarPossuiServicoPago(Long idUsuario, String descServico);
    
    void concederServicoImovel(Long idImovel, String tipoServico, String tempoDuracao, int quant);
    
    List<Servico> recuperarServicosDisponiveisPorImovel(Long idImovel);
    
    void excluirServicoDisponivelImovel(Servico servico);
    
    void excluirServico(Long idServico);
    
    List<Servico> recuperarServicosDisponiveisPorUsuario(Long idUsuario);
    
    void concederServicoUsuario(Long idUsuario, String opcaoServico, String opcaoTempo, String opcaoQuantidade);
    
    void revogarServicoDisponivelUsuario(Servico servico);
    
    void selecionarPagamentoServico(ServicoForm frm, long idInfoServico, Long idFormaPagto);
    
    void selecionarPagamentoPlano(ServicoForm frm, Long idFormaPagto);
    
    double checarVolumeFnanceiroServMaisPorStatus(Date dataInicio,	Date dataFim, String statusServico);
    
    double checarBalancoFinanceiro(Date dataInicio, Date dataFim);
    
	List<Servico> checarUsuariosVolFinanceiroServicoPorStatus(AdministracaoForm form);
    
	List<Servico> checarServicosVolFinanceiroServicoPorStatus(Date dataInicio, Date dataFim, String statusServico);
	
    void simulaRetornoPagtoAdicionarCompartilhamento(Long idServico);
    
    String validarPagamentoServico(long idInfoServico, Long formaPgto);
    
    String validarSolicitacaoServico(long idParamServico, long idUsuario, Date dtCorrente);
    
    String validarSolicitacaoServicoPorLabelServico(String nomeServico, long idUsuario, Date dtCorrente);
    
    List<Servico> checarServicosMaisConcedidos(Date dataInicio, Date dataFim);    
    
    List<Servico> recuperarServicosDisponiveisPorUsuarioAssociadoPlano(Long idUsuario, String assocPlano);
    
    List<Servico> recuperarServicosDisponiveisPorUsuarioPorTipo(Long idUsuario, String tipoServico);
    
    void atualizarServicoImovelPlano(Planousuario p, Plano plano, String descServico);
    
    void simulaRetornoPagtoAssinaturaUsuario(Long idServico);
    
    ServicoForm recuperarAssinaturaSolicitaPorIdUsuario(Long idUsuario, String perfilUsuario);
    
    void excluirAssinaturaUsuarioSolicitada(Long idUsuario, String perfilUsuario);
    
    Servico checarHabilitadoModuloRelatorio(Long idUsuario, String tipoRelatorio, Date dtCorrente);
    
    void atualizarServicoRelatorioPlano(Planousuario p, Plano plano);
    
    List<Servico> checarTotalServicos(AdministracaoForm form);
    
    List<Servico> checarVolumeFinanceirosServicos(AdministracaoForm form);
    
    Long recuperarUltimoIdServicoPorUsuario(long idUsuario, String descServico);
    
    List<Servico> recuperarServicosDisponiveisPorUsuarioPorStatus(Long idUsuario, String status);
    
	void selecionarPagamentoServico(RelatorioForm form, long idInfoServico,	long idFormaPagto);

	void cadastrarSolicitacaoPagtoServico(RelatorioForm form, String opcaoTempoPagto);
    
	void selecionarPagamentoServico(MensagemForm form, long idInfoServico,	long idFormaPagto);

	void cadastrarSolicitacaoPagtoServico(MensagemForm form, String opcaoTempoPagto);

	void carregarInfoPlanoSelecionado(ServicoForm form);
	
	ServicoForm carregarInfoPlanoSelecionadoPorId(Long idPlano);

	void atualizarServico(Servico servico);

	void cadastrarServico(Servico servico);

	long checarTotalAssinaturas(AdministracaoForm form);

	List<RelatorioQuantAssinatura> checarVolFinanceiroAssinaturasPorPeriodo(AdministracaoForm form);

	Collection<? extends RelatorioQuantAssinatura> checarTotalAssinaturasPorUsuario(Long idUsuario, AdministracaoForm form);

	Payment confirmarPagamentoPayPal(HttpServletRequest req, HttpServletResponse resp, ServicoForm form);

	ServicoForm carregaServicoForm(Long idServico);

	ServicoForm carregaSolicitacaoAssinaturaPorUsuario(UsuarioForm form);

	void carregaInfoServicoSelecionado(ServicoForm form);

	void cancelarServico(HttpServletRequest request);

	List<Servico> recuperarServicosPorIdPlanoUsuario(Long idPlanoUsuario);

	void revogarServicoUsuario(Long idUsuario, Long idServicoSelecionado);
	
	boolean validarSelecaoPagamentoServico(ServicoForm form, BindingResult result);

	boolean validarSelecaoPagamentoServico(MensagemForm form, BindingResult result);

	boolean validarSelecaoPagamentoServico(RelatorioForm form, BindingResult result);

    
}
