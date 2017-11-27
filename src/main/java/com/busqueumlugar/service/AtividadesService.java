package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Atividades;

public interface AtividadesService {
	
	Atividades recuperarAtividadePorId(Long id);
	
	List<Atividades> recuperarAtividadesPorIdImovel(Long idImovel);
	
	List<Atividades> recuperarAtividadesPorIdImovelPorIdUsuario(Long idImovel, Long idUsuario);

	void cadastrarAtividade(ImovelForm form, String novaAtividade,	String novaDescricaoAtividade, UsuarioForm user);

	void excluirAtividade(Long idAtividade);

}
