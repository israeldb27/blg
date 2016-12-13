package com.busqueumlugar.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.MensagemAdminForm;
import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.model.MensagemAdmin;

public interface MensagemAdminService {
	
	String QUANT_NOVAS_MENSAGENS_ADMIN = "quantNovasMensagensAdmin";
	String LISTA_NOVAS_MENSAGENS_ADMIN = "listaNovasMensagensAdmin";

	int checarQuantidadeNovasMensagensFromAdmin(Long idUsuario);

	List<MensagemAdmin> recuperaTodasMensagensPorUsuario(Long idUsuario);

	void criarMensagemParaAdmin(MensagemAdminForm form, Long idUsuario);
	
	void criarMensagemAdminEnviadoPorAdministrador(MensagemAdminForm form);

	MensagemAdminForm carregaFormPorIdMensagemAdmin(Long idMensagemAdmin);

	List<MensagemAdmin> listarTodasMensagensOrdenadasPorData();

	MensagemAdmin recuperarMensagemAdminPorId(Long idMensagemAdmin);

	void atualizarMensagemAdmin(MensagemAdmin mensagem);

	List<MensagemAdmin> filtrarMensagensPorTipo(String tipoMensagem);

	boolean validarCriarMensagemAdmin(MensagemAdminForm form, BindingResult result);

	boolean validarCriarMensagemParaAdmin(MensagemAdminForm form, BindingResult result);

	List<MensagemAdmin> listarNovasMensagensOrdenadasPorDataPorQuantidade(int i);

	List<MensagemAdmin> recuperaTodasMensagensNovasPorUsuario(Long idUsuario);

	
	

}
