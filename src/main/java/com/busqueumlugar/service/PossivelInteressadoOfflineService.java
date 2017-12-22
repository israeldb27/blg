package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.model.PossivelInteressadoOffline;

public interface PossivelInteressadoOfflineService {

	PossivelInteressadoOffline recuperarPossivelInteressadoOfflinePorId(Long id);
	
	List<PossivelInteressadoOffline> recuperarListaPossivelInteressadoOfflinePorIdImovel(Long idImovel);

	void cadastrarPossivelInteressadoOffline(ImovelForm form, String nome, String telefone, String email, String chanceInteresse, String observacao);

	void excluirPossivelInteressadoOffline(Long id);

	void editarPossivelInteressadoOffline(Long id, String nome, String telefone, String email, String chanceInteresse, String observacao);

	List<PossivelInteressadoOffline> recuperarListaPossivelInteressadoOfflinePorIdImovelPorQuant(Long idImovel, int quantMaxLista);
}
