package com.busqueumlugar.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.MensagemAdminForm;
import com.busqueumlugar.model.ItemMensagemAdmin;

public interface ItemMensagemAdminService {

	List<ItemMensagemAdmin> checarNovasMensagemAdminPorStatusLeitura(Long idUsuario, String status);

	int recuperarQuantNovasMensagensParaUsuarioPorIdMensagemAdmin(Long idMensagemAdmin);

	void cadastrarItemMensagem(ItemMensagemAdmin itemMensagem);

	List<ItemMensagemAdmin> recuperaItemMensagensPorIdMensagemAdmin(Long idMensagemAdmin);

	void adicionarNovoItemMensagemAdmin(MensagemAdminForm form, Long idUsuario);

	List<ItemMensagemAdmin> recuperaItemMensagensPorIdMensagemAdminParaAdministrador(Long idMensagemAdmin);

	void adicionarNovoItemMensagemAdminPorAdministrador(MensagemAdminForm form);
	
	void adicionarNovoItemMensagemAdminEnviadoPorAdministrador(MensagemAdminForm form);
	
	void adicionarNovaMaisMensagemEnviadoPorAdmin(MensagemAdminForm form);

	int checarQuantidadeNovasMensagensFromAdmin(Long idUsuario);

	boolean validarAdicionarNovoItemMensagemAdminEnviadoPorAdministrador(MensagemAdminForm form, BindingResult result);

	boolean validarAdicionarNovoItemMensagemAdmin(MensagemAdminForm form, BindingResult result);

	List<ItemMensagemAdmin> recuperarQuantidadesNovasMensagensEnviadasParaAdmin();

	void atualizarStatusLeituraItemMensagensPorIdMensagemAdminParaAdministrador(Long idMensagemAdmin);

	String checarExisteNovoItemMensagemByIdMensagem(Long idMensagemAdmin);

	void atualizarItemMensagensPorId(Long idMensagemAdmin);

	List<ItemMensagemAdmin> recuperarItemMensagemAdminPorIdUsuarioStatusLeitura(Long idUsuario, String status);

	int checarQuantidadeMensagensAdminPorStatusLeitura(Long idUsuario, String statusLeitura);

}
