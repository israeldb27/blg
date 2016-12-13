package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.ItemMensagemAdmin;


public interface ItemMensagemAdminDao extends GenericDAO<ItemMensagemAdmin, Long>{

	List<ItemMensagemAdmin> findItemMensagemAdminByIdUsuarioByStatusLeitura(Long idUsuario, String status);

	int findQuantNovasItensMensagensByIdMensagemAdmin(Long idMensagemAdmin);

	List<ItemMensagemAdmin> findItemMensagensAdminByIdMensagemAdmin(Long idMensagemAdmin);

	int findItemMensagensNovasByIdUsuario(Long idUsuario);

	List<ItemMensagemAdmin> findItemMensagensNovasEnviadasParaAdmin();

	List<ItemMensagemAdmin> findItemMensagensAdminNovoByIdMensagemAdmin(Long idMensagemAdmin);

	List<ItemMensagemAdmin> findItemMensagemAdminByStatusLeituraByIdUsuario(Long idUsuario, String status);

	int findQuantItemMensagemAdminByIdUsuarioByStatusLeitura(Long idUsuario, String statusLeitura);

}
