package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.PreferencialocalidadeForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Preferencialocalidade;
import com.busqueumlugar.model.Usuario;

import org.springframework.validation.BindingResult;

public interface PreferencialocalidadeService {
	
	Preferencialocalidade recuperarPreferencialocalidadePorId(Long id);
	
	Preferencialocalidade recuperarPreferenciaLocalidadePorId(long idPrefLocalidade);
	
	void adicionarPreferencia(Long idUsuario, PreferencialocalidadeForm form);
	
	List<Preferencialocalidade> listarPreferenciaPorUsuario(Long idUsuario);
	
	void excluirPreferencia(Preferencialocalidade local);
	
	void excluirPreferencia(Long idPreferencia);
	
	List<Preferencialocalidade> buscarPreferencia(Imovel imovel);
	
	List<Usuario> buscarPreferenciaSemDuplicidadeUsuario(UsuarioForm form);
	
	Preferencialocalidade findPreferencialocalidadeByIdUsuarioByRandom(Long idUsuario);
	
	boolean validarCadastroPreferencia (PreferencialocalidadeForm form, BindingResult result);

	List<Imovel> recuperarImoveisPrefLocalidade(UsuarioForm user, int index, int regra);

	Preferencialocalidade recuperarPrefLocalidadeByUsuarioByIndexByAleatoriamente(List<Long> listaIds, int index, boolean isAleatorio);

	Preferencialocalidade recuperarPrefLocalidadeByUsuarioByIndexByAleatoriamente(int index);

	String validarExclusaoPrefencia(UsuarioForm user);

	boolean validarAdicionarPreferenciaCadUsuario(PreferencialocalidadeForm form, BindingResult result);
	
}
