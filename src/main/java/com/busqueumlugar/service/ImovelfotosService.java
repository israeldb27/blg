package com.busqueumlugar.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.model.Imovelfotos;

public interface ImovelfotosService {
	
	Imovelfotos recuperarImovelfotosPorId(Long id);
	
	List<Imovelfotos> recuperarFotosImovel(Long idImovel);
	
	String carregaImovelFoto(Imovelfotos imovel);
	
	void adicionarFotos(Long idImovel, byte[] img, String descricao);
	
	void excluirFoto(Imovelfotos imovelFoto);
	
	int checarQuantidadeFotosImovel(Long idImovel);

	void excluirFoto(Long idImovelFoto);

	String validarAdicionarFotos(MultipartFile file);
	

}
