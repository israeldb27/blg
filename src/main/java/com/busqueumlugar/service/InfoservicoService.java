package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.InfoservicoForm;
import com.busqueumlugar.model.Infoservico;

public interface InfoservicoService {
	
	Infoservico recuperarInfoservicoPorId(Long id);
	
	List<Infoservico> recuperaDetalhesParamServico(Long idParametro);
	
	void cadastrarInfoServico(InfoservicoForm infoservicoForm);
	
	void excluirInfoServico(Long idInfoServico);
	
	void atualizarInfoServico(Infoservico infoServicoSelecionado);
	
	List<Infoservico> filtrarDetalhesParamServico(Long idParam);
	
	Infoservico recuperarInfoServico(Long idParam, String opcaoTempoPagto, String formaPagto);
	
	Infoservico recuperarInfoServico(Long idParam, String opcaoTempoPagto);
	
	Infoservico recuperarInfoServicoAdicionarFoto(Long idParam, String opcaoQuantFoto);
	
	List<Infoservico> recuperarInfoServicoPagamentoPorNomeServico(String nome);
	
	Infoservico recuperarInfoServicoPorId(long idInfoServico);
	
	

}
