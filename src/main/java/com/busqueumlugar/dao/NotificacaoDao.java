package com.busqueumlugar.dao;


import java.util.List;

import com.busqueumlugar.form.NotificacaoForm;
import com.busqueumlugar.model.Notificacao;

public interface NotificacaoDao extends GenericDAO<Notificacao, Long> {

	Notificacao findNotificacaoById(Long id);
	
	List<?> findQuantNovasNotificacoes(Long idUsuario);
	
	List<Notificacao> findNotificacoesByIdUsuario(Long idUsuario, NotificacaoForm form);
	
	List<Notificacao> findNotificacoesByIdUsuarioByStatus(Long idUsuario, String status);

	List<Notificacao> filterNotificacoesByIdUsuario(Long idUsuario, NotificacaoForm form);

	List<Notificacao> findNotificacoesByIdUsuarioByStatusLeitura(Long idUsuario, String status);

	void atualizarStatusLeituraNotificacaoByIdUsuario(Long idUsuario);
	
	
	
	
}
