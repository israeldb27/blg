package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ParceriaForm;
import com.busqueumlugar.form.PerfilForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Parceria;
import com.busqueumlugar.model.Usuario;

public interface ParceriaService {
	
	Parceria recuperarParceriaId(Long id);
	
	void cadastrarSolicitacaoParceria(Long idUsuario, String usuarioSolicitante, Long idImovel, String descricaoCompart);
	
	List<Parceria> recuperarMinhasSolicitacoesParcerias(Long idUsuarioSolicitante, ParceriaForm form);
	
	List<Parceria> recuperarSolicitacoesParceriasRecebidas(Long idDonoImovel, ParceriaForm form);
	
	List<Parceria> selecionarNovasSolicitacoesParceria(List<Parceria> lista);
	
	boolean atualizarStatusParceria(Parceria  parceria);
	
	long checarQuantidadeNovasSolParceria(Long idDonoImovel);
	
	void atualizarStatusParceria(Long idImovelcompartilhado, String status );
	
	void excluirParceria(Long id);
	
	void excluirParceria(Parceria  parceria);
	
	Parceria  recuperarImovelParceriaSelecionadoPorIdImovel(Long idImovel);
	
	List<Parceria> recuperarMinhasSolParceriaoAceitasPorUsuarioSolicitante(Long idUsuarioSolicitante, ParceriaForm form);
	
	List<Parceria> recuperarMinhasSolParceriaAceitasPorDonoImovel(Long idDonoImovel, ParceriaForm form);
		
	List<Parceria> recuperarTodasSolParceriaAceitas(Long idUsuario, ParceriaForm form);
	
	List<Parceria> recuperarTodasSolParceriasAceitasDistintas(Long idUsuario, ParceriaForm form);
	
	Parceria  recuperarMinhasSolicitacoesPorUsuarioSolPorImovelParceria(Long idUsuario, Long idImovel);
	
	String isParceria(Long idImovel);
	
	List<Usuario> recuperarUsuariosParceria(Long idImovel);
	
	List<Imovel> checarImoveisMaisReceberamSolParceriaPorPeriodo(AdministracaoForm form );
	
	List<Parceria> recuperarSolicitacoesParceriaRecebidasPorIdImovelPorStatus(long idImovel);
	
	List<Imovel> recuperarMinhasSolParceriaAceitasPorUsuarioSolicitantePorPerfil(Long idPerfil, PerfilForm frm);
	
	boolean checarEnviouSolicitacaoParceria(Long idUsuario, Long idImovel );
	
	String validarSolicitacaoParceria(Long idUsuario, Long idImovel, String perfilDonoImovel, String descCompartilhamento);
	
	EmailImovel notificarSolicitacaoParceria(Long idImovel);
	
	EmailImovel notificarAceiteCompartilhamento(Parceria  parceria);
	
	Parceria  recuperarUltimaSolicitacaoParceriaMeusImoveisPorIdImovel(Long idImovel);
	
	void atualizarPontuacaoNegocioParceria(Long idImovel, String novoStatus);
	
	List<Parceria> recuperarUsuarioParceiroPorIdImovel(Long idImovel, String aceita);
	
	boolean checarExisteParceriaAceitaPorIdUsuario(Long idUsuario, ParceriaForm form);
	
	List<Parceria> recuperarTodasParceriasPorUsuario(Long idPerfil, String status);

	List<Parceria> filtrarParceria(Long idUsuario, ParceriaForm form);
	
	List<Imovel> agruparImoveisParceria(Long idUsuarioSessao, ParceriaForm form);
	
	List<Usuario> agruparUsuariosParceria(Long idUsuarioSessao, ParceriaForm form);

	List<Parceria> recuperarParceriaPorUsuario(Long idUsuario, Long idUsuario2);

	List<Parceria> recuperarTodosImovelParceriaPorImovel(Long idImovel);
	
	long checarQuantidadeParceriaAceitaPorIdUsuario(Long idUsuario);

	void excluirSolicitacaoParceria(Long idUsuario, long idImovelCompartilhado);

	Usuario recuperarUsuarioParceria(Long idImovel);
	
	List<Usuario> filtrarAgruparUsuario(Long idUsuario, ParceriaForm form);		
	
	List<Imovel>  filtrarAgruparImoveis(Long idUsuario, ParceriaForm form);
	
	List<?> ordenarAgruparParcerias(Long idUsuario, ParceriaForm form);

	void atualizarStatusSolRecebidasParceria(Long idUsuario);

	List<Parceria> recuperarTodosParceriaPorUsuario(Long idUsuario, Long idUsuario2);


}
