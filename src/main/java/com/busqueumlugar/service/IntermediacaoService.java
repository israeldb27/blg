package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.IntermediacaoForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Intermediacao;
import com.busqueumlugar.model.Usuario;

public interface IntermediacaoService {
	
	Intermediacao  recuperarIntermediacaoPorId(Long id);
	
	void cadastrarSolicitacaoIntermediacao(Long idUsuario, String usuarioSolicitante, Long idImovel, String descricaoCompart);
	
	List<Intermediacao> recuperarMinhasSolicitacoesIntermediacoes(Long idUsuarioSolicitante, IntermediacaoForm form);
	
	List<Intermediacao> recuperarSolicitacoesIntermediacoesRecebidas(Long idDonoImovel, IntermediacaoForm form);
	
	List<Intermediacao> selecionarNovasSolicitacoesCompartilhamento(List<Intermediacao> lista);
	
	boolean atualizarStatusIntermediacao(Intermediacao intermediacao);
	
	long checarQuantidadeNovasSolIntermediacao(Long idDonoImovel);
	
	void atualizarStatusIntermediacao(Long idImovelcompartilhado, String status);
	
	void excluiIntermediacao(Long id);
	
	void excluirIntermediacao(Intermediacao intermediacao);
	
	Intermediacao  recuperarIntermediacaoSelecionadoPorIdImovel(Long idImovel);
	
	Intermediacao  recuperarImovelIntermediadoSelecionadoPorIdImovel(Long idImovel);
	
	List<Intermediacao> recuperarMinhasSolIntermediacaoAceitasPorUsuarioSolicitante(Long idUsuarioSolicitante, IntermediacaoForm form);
	
	List<Intermediacao> recuperarMinhasSolIntermediacaoAceitasPorDonoImovel(Long idDonoImovel, IntermediacaoForm form);
		
	List<Intermediacao> recuperarTodasSolIntermediacaoAceitas(Long idUsuario, IntermediacaoForm form);
	
	List<Intermediacao> recuperarTodasSolIntermediacoesAceitasDistintas(Long idUsuario, IntermediacaoForm form);
	
	Intermediacao  recuperarMinhasSolicitacoesPorUsuarioSolPorImovel(Long idUsuario, Long idImovel);
	
	String isIntermediacao(Long idImovel);
	
	List<Usuario> recuperarUsuariosIntermediacao(Long idImovel);
	
	List<Imovel> checarImoveisMaisReceberamSolIntermediacaoPorPeriodo(AdministracaoForm form);
	
	List<Intermediacao> recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(long idImovel);
	
	List<Imovel> recuperarMinhasSolIntermediacaoAceitasPorUsuarioSolicitantePorPerfil(Long idPerfil, PerfilForm frm);
	
	boolean checarEnviouSolicitacaoIntermediacao(Long idUsuario, Long idImovel);
	
	String validarSolicitacaoIntermediacao(Long idUsuario, Long idImovel, String perfilDonoImovel, String descCompartilhamento);
	
	EmailImovel notificarSolicitacaoIntermediacao(Long idImovel);
	
	EmailImovel notificarAceiteIntermediacao(Intermediacao intermediacao);
	
	Intermediacao  recuperarUltimaSolicitacaoParceriaMeusImoveisPorIdImovel(Long idImovel);
	
	void atualizarPontuacaoNegocioParceria(Long idImovel, String novoStatus);
	
	List<Intermediacao> recuperarIntermediacoesPorIdImovel(Long idImovel, String aceita);
	
	boolean checarExisteParceriaAceitaPorIdUsuario(Long idUsuario, IntermediacaoForm form);
	
	List<Intermediacao> recuperarTodosIntermediacaoPorUsuario(Long idPerfil, String status);

	List<Intermediacao> filtrarIntermediacao(Long idUsuario, IntermediacaoForm form);
	
	List<Imovel> agruparImoveisIntermediacao(Long idUsuario, IntermediacaoForm form);

	List<Usuario> agruparUsuariosIntermediacao(Long idUsuario, IntermediacaoForm form);

	List<Intermediacao> recuperarImoveisCompartilhadoPorUsuario(Long idUsuario, Long idUsuario2);

	List<Intermediacao> recuperarTodosImovelIntermediacaoPorImovel(Long idImovel);
	
	long checarQuantidadeIntermediacaoAceitaPorIdUsuario(Long idUsuario);

	void excluirSolicitacaoIntermediacao(Long idUsuario, long idImovelCompartilhado);

	Usuario recuperarUsuarioIntermediador(Long idImovel);
	
	List<Usuario> filtrarAgruparUsuario(Long idUsuario, IntermediacaoForm form);		
	
	List<Imovel>  filtrarAgruparImoveis(Long idUsuario, IntermediacaoForm form);

	List<?> ordenarAgruparIntermediacoes(Long idUsuario, IntermediacaoForm form);		
	
	void atualizarStatusSolRecebidasImovelCompartilhado(Long idUsuario);

	List<Intermediacao> recuperarTodosIntermediacaoPorUsuario(Long idUsuario, Long idUsuario2);


}
