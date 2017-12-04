package com.busqueumlugar.dao;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.ImovelPropostasForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.model.ImovelPropostas;

public interface ImovelPropostasDao extends GenericDAO<ImovelPropostas, Long> {
	
	ImovelPropostas findImovelPropostas(Long id);
	
	List<ImovelPropostas> findImovelPropostaByIdImovel(Long idImovel);
	
	List<ImovelPropostas> findImoveisPropostasLancadasByIdUsuario(Long idUsuario, ImovelPropostasForm form);
	
	List<ImovelPropostas> findImoveisPropostasLancadasByIdUsuarioByIdImovel(Long idUsuario, Long idImovel);
	
	List<ImovelPropostas> findImoveisPropostasRecebidasByIdUsuario(Long idUsuario, ImovelPropostasForm form);
	
	List<ImovelPropostas> findNovaImoveisPropostasRecebidasByIdUsuario(Long idUsuario);
	
	List checarImoveisComMaisPropostasPeriodo(Date dataInicio, Date dataFim, int quantImoveis);
	
	List relatorioImovelMaisPropostadoPeriodo(RelatorioForm form);
	
	List relatorioImovelMaisPropostadoPeriodo(AdministracaoForm form);
	
	ImovelPropostas findLastImovelPropostaByIdImovel(Long idImovel);

	List findImoveisPropostasRecebidasByIdUsuarioDistinct(Long idUsuario, ImovelPropostasForm form);

	int findQuantidadeNovasPropostasRecebidas(Long idImovel);

	List findUsuariosImoveisPropostasRecebidasByIdUsuario(Long idUsuario, ImovelPropostasForm form );

	List findImoveisPropostasLancadasByIdUsuarioDistinct(Long idUsuario, ImovelPropostasForm form);

	List findUsuariosImoveisPropostasLancadasByIdUsuarioDistinct(Long idUsuarioSessao, ImovelPropostasForm form);

	List<ImovelPropostas> findImovelPropostasByIdUsuarioByIdUsuarioSessaoRecebidas(Long idUsuario, Long idUsuarioSessao);

	List<ImovelPropostas> filterPropostasRecebidas(Long idUsuario, ImovelPropostasForm form);

	List<ImovelPropostas> filterPropostasEnviadas(Long idUsuario, ImovelPropostasForm form);

	List filterFindImoveisPropostasRecebidasByIdUsuarioDistinct(Long idUsuario, ImovelPropostasForm form);
	
	List filterFindImoveisPropostasEnviadasByIdUsuarioDistinct(Long idUsuario, ImovelPropostasForm form);
	
	void updateStatusLeituraByIdUsuarioReceptor(Long idUsuario);

	long findQuantPropostasRecebidasByIdUsuarioByStatus(Long idUsuario, String status);

	List findUsuariosImoveisPropostasSemelhantes(Long idUsuario, ImovelForm form);

	List findUsuariosImoveisProposta(Long idUsuario, ImovelForm form);	

}
