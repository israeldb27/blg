package com.busqueumlugar.service;

import java.util.List;

import com.busqueumlugar.form.ContatoForm;
import com.busqueumlugar.form.ImovelindicadoForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Usuario;

public interface ContatoService {
	
	String QUANT_TOTAL_CONTATOS = "quantContatos";
	String QUANT_NOVOS_CONVITES_RECEBIDOS = "quantNovosConviteRecebidos";
	String QUANT_CONVITES_RECEBIDOS = "quantConviteRecebidos";
	String LISTA_CONVITES_RECEBIDOS = "listaConviteRecebidos";
	
	Contato recuperarContatoPorId(Long id);	
	
	void enviarConvite(UsuarioForm user, Usuario usuarioConvidado);	
	
	void enviarConvite(UsuarioForm user, Long idUsuarioConvidado);	
	
	//List<Usuario> recuperarConvites(Long idUsuario);	
	List<Contato> recuperarConvites(Long idUsuario);
	
	List<Contato> recuperarConvidados(Long idUsuario, ContatoForm form);	
	
	List<Contato> recuperarConvidadosHabilitados(Long idUsuario, ContatoForm form);
	
	List<Contato> recuperarConvidadosPorPerfil(Long idUsuario, String tipoPerfil);	
	
	void responderConvite(Long idUsuarioConvidado, Long idUsuarioHost, String resposta);
	
	void excluirContato(Long idUsuarioContato, Long idUsuarioSessao);	
	
	List<Usuario> recuperarConvitesEnviados(Long idUsuario);
	
	void cancelarConviteEnviado(Long idUsuarioHost, Usuario usuarioConvidado); 	
	
	List<Contato> recuperarOutrosContatos(Long idUsuario);		
	
	String validarEnvioConvite(UsuarioForm userSession, Usuario usuarioConvidado);
	
	String validarEnvioConvite(UsuarioForm userSession, Long  idUsuarioConvidado);	
	
	String carregaFotoPrincipalImovel(Usuario usuario);	
	
	boolean checarExisteContato(Long idUsuario, Long idUsuarioDonoImovel);	
	
	EmailImovel notificarSolicitacaoContato(Usuario usuario);	
	
	EmailImovel notificarSolicitacaoContato(Long idUsuario);	
	
	EmailImovel notificarAceiteConvite(Usuario usuario);
	
	List<Contato> ordenarContatos(Long idUsuario, ContatoForm form);
	
	long checarConvitesRecebidosPorUsuarioPorStatus(Long idUsuario, String status);

	List<Contato> recuperarConvidadosMarcandoCheck(Long idUsuario);

	boolean atualizarStatusLeituraContato(Long idUsuarioConvidado, Long idUsuarioHost);

	String checarTipoContato(Long idUsuario, Long idUsuarioSessao);

	//List<Usuario> filtrarContatos(Long idUsuario, String opcaoFiltro);
	List<Contato> filtrarContatos(Long idUsuario, ContatoForm form);

	List<Usuario> recuperarConvidadosIndicacaoImovel(Long idUsuario, Long idImovel, ImovelindicadoForm form);

	List<Contato> recuperaContatosOkPorUsuarioFiltroRelatorio(RelatorioForm frm, Long idUsuario);

	List<Usuario> recuperarConvites(Long idUsuario, int quant);

	List<Usuario> recuperarConviteSelecionado(Long idUsuario, Long idContatoConvite);

	void cancelarConviteEnviado(Long idUsuarioHost, Long idUsuarioConvidado);

	void cancelarContato(Long idUsuarioHost, Long idUsuarioConvidado);

	List recuperarIDsMeusContatos(Long idUsuario);

	long checarTotalContatosPorUsuarioPorStatus(Long idUsuario, String status);

	List<Contato> filtrarRecuperacaoConvidadosParaIndicacao(Long idUsuario, ImovelindicadoForm form);
		
}
