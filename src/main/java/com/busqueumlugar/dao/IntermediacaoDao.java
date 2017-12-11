package com.busqueumlugar.dao;

import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.IntermediacaoForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Intermediacao;

public interface IntermediacaoDao extends GenericDAO<Intermediacao, Long> {
	
	Intermediacao findIntermediacaoById(Long id);
	
	List<Intermediacao> findIntermediacaoByIdImovel(Long idImovel);
	
	List<Intermediacao> findIntermediacaoByIdImovelByStatus(Long idImovel, String status);
	
	List<Intermediacao> findIntermediacaoByIdDonoImovelByStatus(Long idDonoImovel, String status, IntermediacaoForm form);
	
	Intermediacao findIntermediacaoByIdUsuarioSolicitanteByIdImovel(Long idUsuario, Long idImovel);
	
	List checarImoveisComMaisSolIntermediacaoPeriodo(AdministracaoForm form);
	
	List<Intermediacao> findIntermediacaoBySolAceitasDistintas(Long idUsuario, String status, IntermediacaoForm form);
	
	List<Imovel> findIntermediacaoAceitosPorUsuarioSolicitantePorPerfil(Long idPerfil, String status, PerfilForm form);
	
	Intermediacao findLastImovelParceriaMeusImoveisByIdImovel(Long idImovel);
	
	List<Intermediacao> findAllIntermediacaoByIdUsuarioByStatus(Long idUsuario, String status);
	
	List<Intermediacao> findIntermediacaoByIdUsuarioSolicitanteByStatus(Long idUsuarioSolicitante, String status, IntermediacaoForm form);
	
	List filterFindIntermediacaoByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, IntermediacaoForm form);
	
	List filterFindIntermediacaoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status,  IntermediacaoForm form);	
	
	List filterFindIntermediacaoMinhasSolicitacoesByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status,  IntermediacaoForm form);
	
	int findQuantidadeNovosImoveisCompartilhados(Long idImovel);

	List findImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, IntermediacaoForm form);

	List findUsuariosParceriaByStatusByIdUsuario(Long idUsuarioSessao, String status, IntermediacaoForm form);

	List findUsuariosParceriaByStatusByIdDonoImovel(Long idUsuarioSessao, String status, IntermediacaoForm form);

	List<Intermediacao> findIntermediacaoByIdUsuarioByIdUsuario2(Long idUsuario, Long idUsuario2);

	List<Intermediacao> filterSolIntermediacao(Long idUsuario, IntermediacaoForm form);
	
	List<Intermediacao> filterMinhasSolIntermediacao(Long idUsuario, IntermediacaoForm form);
	
	List<Intermediacao> filterIntermediacaoAceitas(Long idUsuario, IntermediacaoForm form);

	long findQuantidadeIntermediacaoPorUsuarioPorStatus(Long idUsuario, String status);

	void updateStatusLeitura(Long idDonoImovel);

	long checarQuantidadeIntermediacaoSolRecebidaByDonoImovelByStatusByStatusLeitura(Long idDonoImovel, String statusLeitura, String status);

	long findQuantIntermediacaoByIdImovelByStatus(Long idImovel, String status);

	List<Intermediacao> findIntermediacaoByIdImovelByStatusByQuant(Long idImovel, String status, int quantMaxLista);
	
}
