package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelcompartilhadoForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelcompartilhado;
import com.busqueumlugar.model.Usuario;

public interface ImovelcompartilhadoService {
	
	Imovelcompartilhado recuperarImovelcompartilhadoPorId(Long id);
	
	void cadastrarSolicitacaoCompartilhamento(Long idUsuario, String usuarioSolicitante, Long idImovel, String descricaoCompart);
	
	List<Imovelcompartilhado> recuperarMinhasSolicitacoesCompartilhamento(Long idUsuarioSolicitante, ImovelcompartilhadoForm form);
	
	List<Imovelcompartilhado> recuperarMinhasSolicitacoesIntermediacoes(Long idUsuarioSolicitante, ImovelcompartilhadoForm form);
	
	List<Imovelcompartilhado> recuperarSolicitacoesCompartilhamentoRecebidas(Long idDonoImovel, ImovelcompartilhadoForm form);
	
	List<Imovelcompartilhado> recuperarSolicitacoesIntermediacoesRecebidas(Long idDonoImovel, ImovelcompartilhadoForm form);
	
	List<Imovelcompartilhado> selecionarNovasSolicitacoesCompartilhamento(List<Imovelcompartilhado> lista);
	
	boolean atualizarStatusImovelCompartilhado(Imovelcompartilhado imovelcompartilhado);
	
	long checarQuantidadeNovasSolImoveisCompartilhamentoPorTipoCompartilhamento(Long idDonoImovel, String tipoCompartilhamento);
	
	List<Imovelcompartilhado> recuperarSolicitacoesCompartilhamentoRecebidasPorIdImovel(Long idImovel);
	
	void atualizarStatusCompartilhamento(Long idImovelcompartilhado, String status, String tipoCompart);
	
	void excluiImovelCompartilhado(Long id);
	
	void excluiImovelCompartilhado(Imovelcompartilhado imovelcompartilhado);
	
	Imovelcompartilhado recuperarImovelCompartilhadoPorIdImovel(Long idImovelCompartilhado);
	
	Imovelcompartilhado recuperarImovelCompartilhadoSelecionadoPorIdImovel(Long idImovel);
	
	Imovelcompartilhado recuperarImovelIntermediadoSelecionadoPorIdImovel(Long idImovel);
	
	List<Imovelcompartilhado> recuperarMinhasSolCompartilhamentoAceitasPorUsuarioSolicitante(Long idUsuarioSolicitante, ImovelcompartilhadoForm form);
	
	List<Imovelcompartilhado> recuperarMinhasSolCompartilhamentoAceitasPorDonoImovel(Long idDonoImovel, ImovelcompartilhadoForm form);
		
	List<Imovelcompartilhado> recuperarTodasSolCompartilhamentoAceitas(Long idUsuario, ImovelcompartilhadoForm form);
	
	List<Imovelcompartilhado> recuperarTodasSolCompartilhamentoAceitasDistintas(Long idUsuario, ImovelcompartilhadoForm form);
	
	List<Imovelcompartilhado> recuperarTodasSolIntermediacoesAceitasDistintas(Long idUsuario, ImovelcompartilhadoForm form);
	
	Imovelcompartilhado recuperarMinhasSolicitacoesPorUsuarioSolPorImovel(Long idUsuario, Long idImovel);
	
	Imovelcompartilhado recuperarMinhasSolicitacoesPorUsuarioSolPorImovelIntermediacao(Long idUsuario, Long idImovel);
	
	String isImovelCompartilhado(Long idImovel);
	
	List<Usuario> recuperarUsuariosImoveisCompartilhados(Long idImovel);
	
	List<Imovel> checarImoveisMaisReceberamSolCompartilhamentoPorPeriodo(AdministracaoForm form, String tipoCompartilhamento);
	
	List<Imovelcompartilhado> recuperarTodosImoveisCompartilhadosAceitos(Imovelcompartilhado imovelCompartilhado);
	
	List<Imovelcompartilhado> recuperarTodosImoveisCompartilhadosAceitos(Long idImovel);
	
	List<Imovelcompartilhado> recuperarSolicitacoesIntermediacoesRecebidasPorIdImovelPorStatus(long idImovel);
	
	List<Imovelcompartilhado> recuperarSolicitacoesParceriasRecebidasPorIdImovelPorStatus(long idImovel);
	
	List<Imovel> recuperarMinhasSolCompartilhamentoAceitasPorUsuarioSolicitantePorPerfil(Long idPerfil, PerfilForm frm);
	
	boolean checarEnviouSolicitacaoCompartilhamento(Long idUsuario, Long idImovel, String tipoCompart);
	
	String validarSolicitacaoCompartilhamento(Long idUsuario, Long idImovel, String perfilDonoImovel, String descCompartilhamento);
	
	EmailImovel notificarSolicitacaoCompartilhamento(Long idImovel);
	
	EmailImovel notificarAceiteCompartilhamento(Imovelcompartilhado imovelCompartilhado);
	
	Imovelcompartilhado recuperarUltimaSolicitacaoParceriaMeusImoveisPorIdImovel(Long idImovel);
	
	void atualizarPontuacaoNegocioParceria(Long idImovel, String novoStatus);
	
	List<Imovelcompartilhado> recuperarUsuarioParceiroImovelCompartilhadoPorIdImovel(Long idImovel, String aceita);
	
	boolean checarExisteParceriaAceitaPorIdUsuario(Long idUsuario, ImovelcompartilhadoForm form);
	
	List<Imovelcompartilhado> recuperarTodosImoveisCompartilhadosPorUsuario(Long idPerfil, String status);

	List<Imovelcompartilhado> filtrarIntermediacao(Long idUsuario, ImovelcompartilhadoForm form);

	List<Imovelcompartilhado> filtrarParceria(Long idUsuario, ImovelcompartilhadoForm form);
	
	List<Imovel> agruparImoveisParceria(Long idUsuarioSessao, ImovelcompartilhadoForm form);
	
	List<Usuario> agruparUsuariosParceria(Long idUsuarioSessao, ImovelcompartilhadoForm form);

	List<Imovel> agruparImoveisIntermediacao(Long idUsuario, ImovelcompartilhadoForm form);

	List<Usuario> agruparUsuariosIntermediacao(Long idUsuario, ImovelcompartilhadoForm form);

	List<Imovelcompartilhado> recuperarImoveisCompartilhadoPorUsuario(Long idUsuario, Long idUsuario2, String tipoCompart);

	List<Imovelcompartilhado> recuperarUsuariosCompartilhadoPorImovel(Long idImovel);

	List<Imovelcompartilhado> recuperarTodosImovelParceriaPorImovel(Long idImovel);
	
	long checarQuantidadeImovelCompartilhadoAceitaPorIdUsuarioPorTipoCompartilhamento(Long idUsuario, String tipoCompartilhado);

	List<Imovelcompartilhado> recuperarTodasSolicitacoesPorIdImovel(Long idImovel);

	void excluirSolicitacaoImovelCompartilhado(Long idUsuario, long idImovelCompartilhado, String tipoCompart);

	Usuario recuperarUsuarioIntermediador(Long idImovel);
	
	List<Usuario> filtrarAgruparUsuario(Long idUsuario, ImovelcompartilhadoForm form, String tipoCompart);		
	
	List<Imovel>  filtrarAgruparImoveis(Long idUsuario, ImovelcompartilhadoForm form, String tipoCompart);

	List<?> ordenarAgruparIntermediacoes(Long idUsuario, ImovelcompartilhadoForm form);		
	
	List<?> ordenarAgruparParcerias(Long idUsuario, ImovelcompartilhadoForm form);

	void atualizarStatusSolRecebidasImovelCompartilhado(Long idUsuario, String tipoCompart);

	List<Imovelcompartilhado> recuperarTodosImoveisCompartilhadoPorUsuario(Long idUsuario, Long idUsuario2, String tipoCompart);

}
