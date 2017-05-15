package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.PreferencialocalidadeForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Preferencialocalidade;
import com.busqueumlugar.model.Usuario;

public interface PreferencialocalidadeDao extends GenericDAO<Preferencialocalidade, Long>{
	
	Preferencialocalidade findPreferencialocalidadeById(Long id);
	
	List<Preferencialocalidade> findPreferencialocalidadeByIdUsuario(Long idUsuario);

	List<Preferencialocalidade> findPreferencialocalidade(Imovel imovel);
	
	List<Usuario> findPreferencialocalidadeSemDuplicidadeUsuario(UsuarioForm form);

	Preferencialocalidade findPreferenciaRandomByIdUsuario(Long idUsuario);

	List<Imovel> findImoveisByPrefLocalidadeByUsuarioByIndex(UsuarioForm user, int index, int regra);

	Preferencialocalidade findPreferencialocalidadeByUsuarioByIndexByAleatorio(List<Long> listaIds, int index, boolean isAleatorio);

	Preferencialocalidade findPreferencialocalidadeByUsuarioByIndexByAleatorio(int index);

	List findUsuariosPreferenciaisImoveisSemelhantes(Long idUsuario,	ImovelForm form);
}
