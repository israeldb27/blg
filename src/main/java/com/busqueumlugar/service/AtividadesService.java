package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Atividades;

public interface AtividadesService {
	
	Atividades recuperarAtividadePorId(Long id);
	
	List<Atividades> recuperarAtividadesPorIdImovel(Long idImovel);
	
	List<Atividades> recuperarAtividadesPorIdImovelPorIdUsuario(Long idImovel, Long idUsuario);

	void cadastrarAtividade(ImovelForm form, String statusAtividade, String novaDescricaoAtividade, String tipoAtividade, UsuarioForm user);

	void excluirAtividade(Long idAtividade);

	void editarAtividade(ImovelForm form, Long idAtividade,	String novaAtividade, String novaDescricaoAtividade, UsuarioForm user);

	List<Atividades> recuperarAtividadesPorIdImovelPorQuant(Long idImovel,int quantMaxLista);
	
	long recuperarTotalAtividadesPorImovelPorTipoAtividade(Long idImovel, String tipoAtividade);
	
	long recuperarTotalAtividadesPorDonoImovelPorTipoAtividade(Long idUsuario, String tipoAtividade);

}
