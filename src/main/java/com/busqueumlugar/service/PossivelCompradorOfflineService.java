package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.model.PossivelCompradorOffline;

public interface PossivelCompradorOfflineService {

	PossivelCompradorOffline recuperarPossivelCompradorOfflinePorId(Long id);
	
	List<PossivelCompradorOffline> recuperarListaPossivelCompradorOfflinePorIdImovel(Long idImovel);

	void cadastrarPossivelCompradorOffline(ImovelForm form, String nome, String telefone, String email, String chanceCompra, String observacao);
}
