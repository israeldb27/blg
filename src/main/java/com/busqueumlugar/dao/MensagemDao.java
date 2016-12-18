package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.model.Mensagem;

public interface MensagemDao extends GenericDAO<Mensagem, Long>{
	
	Mensagem findMensagemById(Long id);	
	
	List<Mensagem> findMensagensDE(Long idUsuarioDE);
	
	List<Mensagem> findMensagensPARA(Long idUsuarioPARA);
	
	List<Mensagem> findAllMensagemByIdUsuarioOrderByDataMensagem(Long idUsuario);
	
	List<Mensagem> findAllMensagemByIdUsuarioDePara(Long idUsuarioDe, Long idUsuarioPara);
	
	List<Mensagem> findAllMensagemByIdUsuarioDeParaNova(Long idUsuarioDe, Long idUsuarioPara);
	
	List<Mensagem> findExistsNovaMensagem(Long idUsuario);
	
	List<Mensagem> findAllMensagemByIdUsuarioOrderByDataMensagem(Long idUsuario, int quantMensagens);

	List<Mensagem> findAllMensagemNovasByIdUsuarioOrderByDataMensagem(Long idUsuario);

	long findQuantMensagensByIdUsuarioByStatusLeitura(Long idUsuario, String statusLeitura);

}
