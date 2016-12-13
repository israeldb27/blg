package com.busqueumlugar.dao;

import com.busqueumlugar.model.Avaliacaousuario;


public interface AvaliacaousuarioDao extends GenericDAO<Avaliacaousuario, Long>{
	
	Avaliacaousuario findAvaliacaousuarioById(Long id);	
	Avaliacaousuario findAvaliacaoUsuarioByIdUsuarioClienteByIdPerfilByStatusAvaliacao(Long idUsuario, Long  idPerfil, String statusAvaliacao);	

}
