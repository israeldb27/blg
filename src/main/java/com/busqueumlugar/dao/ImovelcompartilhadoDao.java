package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelcompartilhadoForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelcompartilhado;

public interface ImovelcompartilhadoDao extends GenericDAO<Imovelcompartilhado, Long> {
	
	Imovelcompartilhado findImovelcompartilhadoById(Long id);
	
	List<Imovelcompartilhado> findImovelcompartilhadoByIdUsuarioSolicitante(Long idUsuarioSolicitante);
	
	List<Imovelcompartilhado> findImovelcompartilhadoByIdDonoImovel(Long idDonoImovel);
	
	List<Imovelcompartilhado> findImovelcompartilhadoByIdDonoImovelByStatusLeituraByTipoCompartilhamento(Long idDonoImovel, String status, String tipoCompart);
	
	List<Imovelcompartilhado> findImovelcompartilhadoByIdImovel(Long idImovel);
	
	List<Imovelcompartilhado> findImovelCompartilhadoByIdImovelByStatusByTipoCompartilhamento(Long idImovel, String status, String tipoCompar);
	
	List<Imovelcompartilhado> findImovelcompartilhadoByIdDonoImovelByStatusTipoCompartilhamento(Long idDonoImovel, String status, String tipoCompart, ImovelcompartilhadoForm form);
	
	Imovelcompartilhado findImovelcompartilhadoByIdUsuarioSolicitanteByIdImovelTipoCompart(Long idUsuario, Long idImovel, String tipoCompart);
	
	List checarImoveisComMaisSolCompartilhamentoPeriodo(AdministracaoForm form, String tipoCompartilhamento);
	
	List<Imovelcompartilhado> findImovelcompartilhadoBySolAceitasDistintasTipoImovelCompartilhado(Long idUsuario, String status, String tipoImovelCompart, ImovelcompartilhadoForm form);
	
	List<Imovel> findImovelCompartilhadoAceitosPorUsuarioSolicitantePorPerfil(Long idPerfil, String status, PerfilForm form);
	
	Imovelcompartilhado findLastImovelParceriaMeusImoveisByIdImovel(Long idImovel);
	
	List<Imovelcompartilhado> findAllImovelCompartilhadoByIdUsuarioByStatus(Long idUsuario, String status);
	
	List<Imovelcompartilhado> findImovelCompartilhadoByIdImovelByStatus(Long idImovel, String status);

	List<Imovelcompartilhado> findImovelcompartilhadoByIdUsuarioSolicitanteByStatusTipoCompartilhado(Long idUsuarioSolicitante, String status, String tipoCompart, ImovelcompartilhadoForm form);

	List findImoveisCompartilhadoByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, String tipoCompartilhamento, ImovelcompartilhadoForm form);
	
	List filterFindImoveisCompartilhadoByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, String tipoCompartilhamento, ImovelcompartilhadoForm form);
	
	List filterFindImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, String tipoCompartilhamento,  ImovelcompartilhadoForm form);	
	
	List filterFindImoveisCompartilhadoMinhasSolicitacoesByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, String tipoCompartilhamento,  ImovelcompartilhadoForm form);
	
	int findQuantidadeNovosImoveisCompartilhados(Long idImovel);

	List findImoveisCompartilhadoSolicitacoesRecebidasByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, String tipoCompartilhamento, ImovelcompartilhadoForm form);

	List findImoveisCompartilhadoMinhasSolicitacoesByStatusByIdUsuarioDistinct(Long idUsuarioSessao, String status, String tipoCompartilhamento, ImovelcompartilhadoForm form);

	List findUsuariosImoveisCompartilhadosByStatusByIdUsuario(Long idUsuarioSessao, String status, String tipoCompartilhamento, ImovelcompartilhadoForm form);

	List findUsuariosImoveisCompartilhadosByStatusByIdDonoImovel(Long idUsuarioSessao, String status, String tipoCompartilhamento, ImovelcompartilhadoForm form);

	List<Imovelcompartilhado> findImoveisCompartilhadosByIdUsuarioByIdUsuario2(Long idUsuario, Long idUsuario2, String tipoCompart);

	List<Imovelcompartilhado> findImovelcompartilhadoByIdDonoImovelByStatusTipoCompartilhamentoNovos(Long idDonoImovel, String status, String statusLeitura);

	List<Imovelcompartilhado> filterSolImoveisCompartilhado(Long idUsuario, ImovelcompartilhadoForm form, String tipoCompart);
	
	List<Imovelcompartilhado> filterMinhasSolImoveisCompartilhado(Long idUsuario, ImovelcompartilhadoForm form, String tipoCompart);
	
	List<Imovelcompartilhado> filterImoveisCompartilhadoAceitas(Long idUsuario, ImovelcompartilhadoForm form, String tipoCompart);

	long findQuantidadeImovelCompartilhadoPorUsuarioPorStatusPorTipoCompartilhado(Long idUsuario, String status, String tipoCompartilhado);

	void updateStatusLeitura(Long idDonoImovel, String tipoCompart);

	long checarQuantidadeImovelCompartilhadoSolRecebidaByDonoImovelByStatusByStatusLeituraByTipoCompart(Long idDonoImovel, String statusLeitura, String status, String tipoCompartilhamento);
	
}
