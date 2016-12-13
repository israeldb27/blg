package com.busqueumlugar.dao;


import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelfavoritosForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelfavoritos;

public interface ImovelfavoritosDao  extends GenericDAO<Imovelfavoritos, Long> {
	
	Imovelfavoritos findImovelfavoritosById(Long id);
	
	List<Imovelfavoritos> findImovelFavoritosByUsuario(Long idUsuario, ImovelfavoritosForm form);
	
	Imovelfavoritos findImovelFavoritosByidImovelbyIdUsuario(Long idUsuario, Long idImovel);
	
	List<Imovelfavoritos> findUsuariosInteressadosByIdDonoImovel(Long idUsuario, ImovelfavoritosForm form);
	
	List<Imovelfavoritos> findNovoUsuariosInteressadosByIdDonoImovel(Long idUsuario);
	
	List<Imovelfavoritos> findUsuariosInteressadosPorIdImovel(Long idImovel);
	
	List findImoveisfavoritosMaisInteressadosPorPeriodo(Date dataInicio, Date dataFim, int quantImoveis);
	
	List relatorioImovelMaisAdotadosInteressadosPeriodo(RelatorioForm form);
	
	List relatorioImovelMaisAdotadosInteressadosPeriodo(AdministracaoForm form);	
	
	Imovelfavoritos findLastUsuarioInteressadoByIdImovel(Long idImovel);

	List<Imovelfavoritos> filterImoveisInteresse(Long idUsuario, ImovelfavoritosForm form);

	List<Imovelfavoritos> filterUsuariosInteressados(Long idUsuario, ImovelfavoritosForm form);

	List findImoveisFavoritosUsuariosInteressadosByIdUsuarioDistinct(Long idUsuario, ImovelfavoritosForm form);

	int findQuantidadeNovosUsuariosInteressados(Long idImovel);

	List findUsuariosImoveisFavoritosUsuariosInteressadosByIdUsuario(Long idUsuarioSessao, ImovelfavoritosForm form);

	List findUsuariosImoveisFavoritosImoveisMeuInteresseByIdUsuario(Long idUsuarioSessao, ImovelfavoritosForm form);

	List findImoveisInteressadosByIdUsuarioByIdDonoImovel(Long idUsuario,	Long idDonoImovel);

	List filtrarAgruparImoveis(Long idUsuario, ImovelfavoritosForm form);

	long findQuantUsuariosInteressadosByIdUsuarioByStatus(Long idUsuario, String status);

	void updateStatusLeitura(Long idUsuario);
}
