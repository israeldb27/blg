package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.form.ContatoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Seguidor;


public interface SeguidorDao extends GenericDAO<Seguidor, Long>{
	
	Seguidor findSeguidorById(Long id);

	Seguidor findSeguidorByIdUsuarioSeguidorByIdUsuario(Long idUsuarioSessao, Long idUsuario);

	List<Seguidor> findSeguidoresByIdUsuarioSeguido(Long idUsuario);
	
	List<Seguidor> findSeguidoresByIdUsuarioSeguido(Long idUsuario, int quantMaxExibeMaisListaSeguidores);
	
	List<Seguidor> findSeguindoByIdUsuarioSeguido(Long idUsuario);

	List<?> findIdsSeguidoresByIdUsuario(Long idUsuario);	
	
	List<?> findIdsSeguindoByIdUsuario(Long idUsuario);

	List<?> filterListaIdsUsuariosSeguindo(RelatorioForm form);
	
	List<?> filterListaIdsUsuariosSeguindo(Long idUsuario, String perfilUsuario);

	long findQuantSeguidoresByIdUsuarioSeguido(Long idUsuario);

	List<?> filterListaIdsUsuariosSeguidores(RelatorioForm form);
	
	List<?> filterListaIdsUsuariosSeguidores(Long idUsuario, String perfilUsuario);

	List<Seguidor> findSeguidoresByIdUsuarioSeguido(Long idUsuario, ContatoForm form);

	List<Seguidor> findSeguindoByIdUsuarioSeguido(Long idUsuario, ContatoForm form);

	
}
