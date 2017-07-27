package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.form.NotaForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Nota;

public interface NotaDao extends GenericDAO<Nota, Long> {
	
	Nota findNotaById(Long id);
	
	int destroyNotasByIdImovel(Long idImovel);

	List<Nota> filterNotasByIdUsuario(Long idUsuario, NotaForm form);

	List<Nota> findNotasContatosByListaIdsUsuario(List<?> listaIdsContatos, NotaForm form);
	
	List<Nota> findNotasContatosByListaIdsUsuarioQuant(List<?> listaIdsContatos, NotaForm form, int quant);

	List<Nota> filterNotasByListaIdsUsuario(List<?> listaIdsContatos, NotaForm form);

	Nota findNotaByUsuarioByIndex(List<Long> listaIds, int index);

	List<Nota> filterNotasByIdUsuario(Long idUsuario, NotaForm form, int quantMaxExibeMaisListaNotas);

	long findQuantNotasByIdUsuario(Long idUsuario);
	
	

}
