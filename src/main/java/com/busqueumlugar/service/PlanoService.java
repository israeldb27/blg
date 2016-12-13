package com.busqueumlugar.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.PlanoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.RelatorioQuantPlano;

public interface PlanoService {
	
	Plano recuperarPlanoPorId(Long id);
	
	void cadastrarNovoPlano(PlanoForm planoForm);
	
	void atualizarPlano(PlanoForm planoForm);
	
	List<Plano> listarTodosDisponiveis();
	
	 void excluirPlano(Long idPlano);
	 
	 PlanoForm preparaPlanoAdicionarServicos(Plano plano);
	 
	 PlanoForm preparaPlanoAdicionarRelatorio(Plano plano);
	 
	 String validarCadastroPlano(PlanoForm planoForm);
	 
	 Plano recuperarPlanoPorId(long idPlano);
	 
	 List<Plano> pesquisarTodosPlanos(String valor);

	boolean validarCadastroPlano(PlanoForm form, BindingResult result);

	boolean validarEdicaoPlano(PlanoForm form, BindingResult result);

	PlanoForm carregaPlanoFormPorIdPlano(Long idPlano);

}
