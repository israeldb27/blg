package com.busqueumlugar.service;

import java.util.Hashtable;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.ParametrosIniciaisForm;
import com.busqueumlugar.model.ParametrosIniciais;

public interface ParametrosIniciaisService {

	ParametrosIniciais recuperarParamservicoPorId(Long id);

	void cadastrarParametroInicial(ParametrosIniciaisForm form, Long idUsuarioAdmin);

	List<ParametrosIniciais> listarTodosParametrosIniciais();

	void excluirParametroInicial(Long idParametro);

	Hashtable carregarParametrosIniciais();

	String findParametroInicialPorNome(String nomeParametro);

	boolean validarCadastroParametrosIniciais(ParametrosIniciaisForm form, BindingResult bindResult);

	boolean validarEdicaoParametrosIniciais(ParametrosIniciaisForm form, BindingResult bindResult);

	ParametrosIniciaisForm carregarParametrosIniciaisPorIdParametro(Long idParametro);

	void editarParametroInicial(ParametrosIniciaisForm form, Long idUsuario);

	boolean isHabilitadoEnvioEmail();
}
