package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.MensagemAdmin;

public interface MensagemAdminDao extends GenericDAO<MensagemAdmin, Long>{
	
	MensagemAdmin findMensagemAdminById(Long id);

	List<MensagemAdmin> findMensagemAdminByIdUsuario(Long idUsuario);

	List<MensagemAdmin> findAllMensagensAdminOrderByDataMensagem();

	List<MensagemAdmin> findAllMensagensAdminOrderByDataMensagemByTipoMensagem(String tipoMensagem);

	List<MensagemAdmin> findAllMensagensNovasAdminOrderByDataMensagemBYQuant(int quant);



	

}
