package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ParceriaForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Parceria;

public interface ParceriaDao extends GenericDAO<Parceria, Long> {
	
	Parceria findParceriaById(Long id);
	
	List<Parceria> findParceriaByIdImovel(Long idImovel);
	
	List<Parceria> findParceriaByIdDonoImovelByStatus(Long idDonoImovel, String status, ParceriaForm form);
	
	Parceria findParceriaByIdUsuarioSolicitanteByIdImovel(Long idUsuario, Long idImovel);
	
	List checarImoveisComMaisSolParceriaPeriodo(AdministracaoForm form);
	
	List<Parceria> findParceriaBySolAceitasDistintas(Long idUsuario, String status, ParceriaForm form);
	
	List<Imovel> findParceriaAceitosPorUsuarioSolicitantePorPerfil(Long idPerfil, String status, PerfilForm form);
	
	Parceria findLastImovelParceriaMeusImoveisByIdImovel(Long idImovel);
	
	List<Parceria> findAllParceriaByIdUsuarioByStatus(Long idUsuario, String status);
	
	List<Parceria> findParceriaByIdImovelByStatus(Long idImovel, String status);

	List<Parceria> findParceriaByIdUsuarioSolicitanteByStatus(Long idUsuarioSolicitante, String status, ParceriaForm form);

	List findParceriasByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, ParceriaForm form);
	
	List filterFindParceriaByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, ParceriaForm form);
	
	List filterFindParceriaSolicitacoesRecebidasByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, ParceriaForm form);	
	
	List filterFindParceriaMinhasSolicitacoesByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status,  ParceriaForm form);
	
	int findQuantidadeNovosParcerias(Long idImovel);

	List findParceriasSolicitacoesRecebidasByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, ParceriaForm form);

	List findParceriasMinhasSolicitacoesByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, ParceriaForm form);

	List findUsuariosImoveisCompartilhadosByStatusByIdUsuario(Long idUsuarioSessao, String status, ParceriaForm form);

	List findUsuariosParceriaByStatusByIdDonoImovel(Long idUsuarioSessao, String status, ParceriaForm form);

	List<Parceria> findParceriasByIdUsuarioByIdUsuario2(Long idUsuario, Long idUsuario2);

	List<Parceria> filterSolParceria(Long idUsuario, ParceriaForm form);
	
	List<Parceria> filterMinhasSolParceria(Long idUsuario, ParceriaForm form);
	
	List<Parceria> filterParceriaAceitas(Long idUsuario, ParceriaForm form);

	long findQuantidadeParceriaPorUsuarioPorStatus(Long idUsuario, String status);

	void updateStatusLeitura(Long idDonoImovel);

	long checarQuantidadeParceriaSolRecebidaByDonoImovelByStatusByStatusLeitura(Long idDonoImovel, String statusLeitura, String status);

	long findQuantParceriaByIdImovelByStatus(Long idImovel, String status);

	List<Parceria> findParceriaByIdImovelByStatusByQuant(Long idImovel,	String status, int quantMaxLista);
	
}
