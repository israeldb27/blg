package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.form.ContatoForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Usuario;

public interface ContatoDao extends GenericDAO<Contato, Long>{
	
	Contato findContatoById(Long id);
	
	List<Contato> findConvites(Long idUsuario);
	
	List<Contato> findConvitesEnviados(Long idUsuario);
	
	List<Contato> findContatos(Long idUsuario, ContatoForm form);
	
	List<Contato> findContatos(Long idUsuario, ContatoForm form, int quant); 
	
	Contato findContatos(Long idUsuarioConvidado, Long idUsuarioHost);
	
	Contato findContatosByStatus(Long idUsuarioConvidado, Long idUsuarioHost, String status);
	
	Contato findAnyContatoByStatus(Long idUsuarioConvidado, Long idUsuarioHost, String status);
	
	Contato findConvite(Long idUsuarioConvidado, Long idUsuarioHost);
	
	List<Contato> findOthersContatosByIdUsuario(Long idUsuario);

	List<Contato> findConvitesBydIdUsuarioByStatus(Long idUsuario, String status);

	List<Contato> findContatosHostFiltroRelatorio(RelatorioForm form,Long idUsuario);
	
	List<Contato> findContatosConvidadoFiltroRelatorio(RelatorioForm form,Long idUsuario);

	List<Contato> findConvites(Long idUsuario, int quant);

	Contato findContatoRandomByIdUsuarioByStatus(Long idUsuario, String status);

	List<Contato> findContatosByPerfilUsuario(Long idUsuario, String tipoPerfil);

	List findIdsUsuariosContatosByIdUsuarioByStatus(Long idUsuario, String status);

	long findQuantidadeConvitesPorUsuarioPorStatus(Long idUsuario, String status);

	long findQuantidadeTotalContatosByIdUsuarioByStatus(Long idUsuario, String status);

	List filterListaIds(RelatorioForm form);
	
	List filterListaIds(Long idUsuario, String perfilUsuario);

	List<Contato> findContatosByIndicacao(Long idUsuario, ImovelindicadoForm form);
}