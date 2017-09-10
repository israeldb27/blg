package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.NotificacaoForm;
import com.busqueumlugar.model.Notificacao;
import com.busqueumlugar.model.Usuario;

public interface NotificacaoService {
	
	String QUANT_NOVAS_NOTIFICACOES = "quantNovasNotificacoes";
	
	Notificacao recuperarNotificacaoPorId(Long id);
	
	List<Notificacao> recuperarMinhasNotificacoes(Long idUsuario, NotificacaoForm form);
	
	List<Notificacao> recuperarNotificacoesNovas(List<Notificacao> lista);
	
	int checarQuantidadeNovasNotificacoes(Long idUsuario);
	
	void cadastrarNotificacao(Long idUsuario, String acao, String descricao, String tipoNotificacao, Long idUsuarioConvite);
	
	void cadastrarNotificacao(Usuario usuario, String acao, String descricao, String tipoNotificacao, Long idUsuarioConvite);
	
	void cadastrarNotificacao(Long idUsuario, Long idImovel, String acao, String descricao, String tipoNotificacao, Long idUsuarioConvite);
	
	boolean atualizarStatus(Notificacao notificacao);
	
	List<Notificacao> recuperarListaNotificacoesNovas(Long idUsuario);
	
	List<Notificacao> ordenarNotificacoes(Long idUsuario, NotificacaoForm form);

	List<Notificacao> filtrarNotificacoes(Long idUsuario, NotificacaoForm form);

	List<Notificacao> recuperarNotificacaoPorIdSelecionada(Long idNotificacao);

	void atualizarStatusLeituraNotificacao(List<Notificacao> lista);

	void atualizarStatusLeituraNotificacaoByIdUsuario(Long idUsuario);

	void cadastrarNotificacao(Long idImovel, String acaoNotificacao, Long idUsuarioSolicitante, String tipoNotificacao);

}
