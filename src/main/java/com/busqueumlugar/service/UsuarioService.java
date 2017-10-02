package com.busqueumlugar.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.busqueumlugar.form.AdministracaoForm;
import com.busqueumlugar.form.ImovelForm;
import com.busqueumlugar.form.RelatorioForm;
import com.busqueumlugar.form.ServicoForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Estados;
import com.busqueumlugar.model.Usuario;


public interface UsuarioService {
	
	String ACESSO_VALIDO = "acessoValido";
	String CONTADOR_TELA_INICIAL = "contadorTelaInicial";
	String HABILITA_FUNC_PLANOS   = "habilitaFuncPlanos";
	String HABILITA_FUNC_SERVICOS = "habilitaFuncServicos";
	String HABILITA_ENVIO_EMAIL = "habilitaEnvioEmail";
	

	boolean validarLoginPassword(UsuarioForm form);
	
	Usuario recuperarUsuarioPorId(Long idUsuario);
	
	List<Usuario> listarUsuarios();
	
	void atualizarPontuacaoNegociacaoImovel(Usuario usuario, String situacaoNegociacao);
	
	UsuarioForm carregaUsuarioByLogin(UsuarioForm form);
	
	UsuarioForm cadastrarUsuario(UsuarioForm frm)throws EmailException;
	
	String gerarCodigoIdentificacao(Usuario usuario, Estados estado);
	
	UsuarioForm editarUsuario(UsuarioForm frm);
	
	String md5(String senha);
	
	List<Usuario> buscarUsuarios(AdministracaoForm form);
	
	String validarCadastroUsuario(UsuarioForm frm);
	
	String validarCadastroUsuario2(UsuarioForm frm);
	
	String validarEdicaoUsuario(UsuarioForm frm);
	
	List<Usuario> buscarUsuarios(UsuarioForm frm, String tipoVisualizar, Long idUsuarioSessao);
	
	List<Usuario> buscarUsuarios(UsuarioForm frm, String tipoVisualizar);
	
	void atualizarStatusUsuario(Long idUsuario, String statusUsuario);
	
	Usuario adicionarFotoPrincipalUsuario(Long idUsuario, byte[] foto);
	
	List<Usuario> recuperarUsuariosInteressadosPorImovelPorDonoImovel(UsuarioForm user);
	
	int checarQuantidadeTotalUsuarios();
	
	long checarQuantidadeTotalUsuariosPorPeriodo(AdministracaoForm form);
	
	List<Usuario> checarUsuariosPorUltimoAcesso(AdministracaoForm form);
	
	String gerarNovaSenha(Usuario usuario);
	
	EmailImovel notificarGeracaoNovaSenha(String emailDestinatario, String novaSenha);
	
	String validarCadastroInicial(UsuarioForm frm);
	
	void editarUsuario(Usuario usuario);
	
	boolean checarPermissaoServico(Long idUsuario, String nomeServico);
	
	void atualizarIndicacoesImoveisPorEmail(Long idUsuario);
	
	String validarMudancaSenha(UsuarioForm frm);
	
	void atualizarAssinaturaUsuario(long idUsuario, Date dataFimAssinatura);
	
	String validarCadastroLocalidadeUsuario(UsuarioForm frm);
	
	String validarCadastroIdentificacaoUsuario(UsuarioForm frm);
	
	String validarCadastroPwDUsuario(UsuarioForm frm);
	
	String validarCadastroContato(UsuarioForm frm);
	
	void avaliarServicoUsuario(Long idPerfilUsuario, String avaliacao);
	
	String validarAtualizacaoInfoUsuario(UsuarioForm frm);
	
	UsuarioForm carregaUsuario(Long idUsuario);
	
	String validarAtualizarContato(UsuarioForm frm);	
	
	String validarBuscaUsuarios(UsuarioForm frm, String tipoVisualizar);
	
	void atualizarStatusAtivadoUsuario(Long idUsuario);
	
	List<Usuario> relatorioUsuarioMaisCompartilhamentosAceitos(RelatorioForm frm, String tipoImovelCompart);
	
	String validarIndicarAmigos(String email);
	
	EmailImovel indicarAmigos(String emailAmigo);

	void carregarDadosInfoUsuario(UsuarioForm user, HttpSession session, boolean atualizaUltimoAcesso);
	
	void tratarTelaInicial(UsuarioForm user, HttpSession session, ModelMap map);
	
	List<Usuario> ordenarBuscaUsuarios(UsuarioForm usuarioSessao, UsuarioForm form, String tipoVisualizar);

	String validarAcessoUsuario(UsuarioForm user);

	void prepararEdicaoUsuario(UsuarioForm user);
	
	UsuarioForm prepararEdicaoUsuarioAdmin(Long idUsuarioAdmin);

	UsuarioForm prepararDetalhesUsuarioForm(Long idUsuario, Long idUsuarioSessao);
	
	boolean validarDadosCadastroUsuario(UsuarioForm form, BindingResult result);

	boolean validarDadosCadastroEdicaoUsuarioAdmin(UsuarioForm form, BindingResult result);
	
	boolean validarDadosEdicaoUsuario(UsuarioForm form, BindingResult result);

	UsuarioForm prepararDetalhesUsuarioFormAdmin(Long idUsuario);

	UsuarioForm alterarFotoUsuario(UsuarioForm form);

	void preparararSolicitacaoRenovacaoAssinaturaUsuario(UsuarioForm user, ServicoForm servicoForm);

	void preparaCampoDataNascimento(UsuarioForm form);
	
	void preparaCampoDataNascimentoEditarUsuario(UsuarioForm form);

	UsuarioForm cadastrarUsuarioAdmin(UsuarioForm form);

	UsuarioForm prepararDetalhesUsuarioAdmin(Long idUsuario);

	void suspenderUsuario(Long idUsuario, String tempo, String motivo);

	void cancelarSuspensaoUsuario(Long idUsuario);
	
	List<Usuario> pesquisarTodosUsuarios (UsuarioForm form);
	
	boolean validarBuscarUsuarios(UsuarioForm form, BindingResult result);

	List<Usuario> pesquisarTodosUsuariosAdmin(String valorBusca);

	void prepararParaCarregarTimeLine(UsuarioForm user, HttpSession session, ModelMap map);

	List<String> carregarTimeline(UsuarioForm usuarioForm, HttpSession session);

	boolean validarEnviarSenhaTemporarioEsqueceuSenha(UsuarioForm form, BindingResult result);
	
	void enviarSenhaTemporarioEsqueceuSenha(UsuarioForm form)throws EmailException;

	boolean validarConfirmaEnviarSenhaTemporarioEsqueceuSenha(UsuarioForm form, BindingResult result);

	void confirmarSenhaTemporarioEsqueceuSenha(UsuarioForm form);

	boolean validarEditarSenha(UsuarioForm form, BindingResult result);

	void editarSenha(Long idUsuario, UsuarioForm form);

	boolean validarIndicarAmigo(UsuarioForm form, BindingResult result);

	void enviarIndicarAmigo(UsuarioForm form) throws EmailException;

	List<Usuario> relatorioUsuariosImoveisMaisVisualizados(RelatorioForm form);

	List<Usuario> relatorioUsuariosImoveisMaisFavoritos(RelatorioForm form);

	List<Usuario> relatorioUsuariosImoveisMaisPropostas(RelatorioForm form);
}

