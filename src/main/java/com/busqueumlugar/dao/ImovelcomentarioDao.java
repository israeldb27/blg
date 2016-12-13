package com.busqueumlugar.dao;


import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelcomentarioForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Imovelcomentario;

public interface ImovelcomentarioDao extends GenericDAO<Imovelcomentario, Long> {
	
	Imovelcomentario findImovelcomentarioById(Long id);
	
	List<Imovelcomentario> findImovelcomentariosByIdImovel(Long idImovel, ImovelcomentarioForm form);
	
	List<Imovelcomentario> findImovelcomentariosByIdImovelStatus(Long idImovel, String status);
	
	List<Imovelcomentario> findImovelcomentariosByIdUsuario(Long idUsuario);
	
	List<Imovelcomentario> findImovelcomentariosOutrosByIdUsuario(Long idUsuario);
	
	List<Imovelcomentario> findNovoImovelcomentariosOutrosByIdUsuario(Long idUsuario);
	
	List checarImoveisComMaisComentariosPeriodo(Date dataInicio, Date dataFim, int quant);
	
	List relatorioImovelMaisComentadosPeriodo(RelatorioForm form);
	
	List relatorioImovelMaisComentadosPeriodo(AdministracaoForm form);
	
	Imovelcomentario findLastImovelcomentarioByIdImovel(Long idImovel);

	List findImoveisComentariosSobreMeusImoveisDistinct(Long idUsuario);

	List findMeusComentariosByIdUsuarioDistinct(Long idUsuario);

	List findUsuariosImoveisComentariosSobreMeusImoveisDistinct(Long idUsuarioSessao);

	List findUsuariosMeusComentariosDistinct(Long idUsuarioSessao);

	List<Imovelcomentario> findImoveisComentariosSobreMeusImoveisPorUsuario(Long idUsuarioSessao, Long idUsuario);

	List<Imovelcomentario> filterMeusComentarios(ImovelcomentarioForm form, Long idUsuario);

	List findImoveisComentariosSobreMeusImoveisInfoTotais(ImovelcomentarioForm form, Long idUsuario);

	List<Imovelcomentario> findImoveisComentariosNovosSobreMeusImoveis(Long idUsuario);

	void updateStatusLeitura(Long idUsuario);

	long findQuantImoveisComentariosRecebidos(Long idUsuario, String statusLeitura);

	long findQuantImovelcomentariosByIdImovelStatus(Long idImovel, String statusLeitura);	

}
