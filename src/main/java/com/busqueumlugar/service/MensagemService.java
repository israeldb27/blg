package com.busqueumlugar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.busqueumlugar.form.MensagemForm;
import com.busqueumlugar.form.UsuarioForm;
import com.busqueumlugar.model.EmailImovel;
import com.busqueumlugar.model.Mensagem;
import com.busqueumlugar.model.Usuario;
import com.paypal.api.payments.Payment;

public interface MensagemService {
	
	String QUANT_NOVAS_MENSAGENS = "quantNovasMensagens";	
	String LISTA_MENSAGENS = "listaMensagens";
	
	Mensagem recuperarMensagemPorId(Long id);
	
	void prepararMensagem(Long idUsuarioDe, Usuario usuarioPara, boolean pago);
	
	MensagemForm prepararTodasMensagens(Long idUsuarioDe, Long idUsuarioPara);
	
	void enviarMensagem(MensagemForm frm, UsuarioForm user);
	
	void criarMensagem(MensagemForm frm);
	
	List<Mensagem> listaMensagensDE(UsuarioForm user);
	
	List<Mensagem> listaMensagensPARA(UsuarioForm user);
	
	List<Mensagem> recuperaTodasMensagens(Long idUsuario);
	
	List<MensagemForm> recuperaTodasMensagensPorDataMensagem(Long idUsuario);
	
	List<Mensagem> recuperaTodasMensagens(long idUsuarioDe, long idUsuarioPara, String loginExibicao);
	
	boolean checarExisteNovaMensagem(Long idUsuario);
	
	long checarQuantidadeNovasMensagens(Long idUsuario);
	
	List<MensagemForm> recuperarNovasMensagens(List<MensagemForm> lista, UsuarioForm usuario);
	
	boolean atualizarStatusMensagem(Long idMensagem);	
	
	EmailImovel notificarNovaMensagem(MensagemForm frm);
	
	Payment confirmarPagamentoPayPal(HttpServletRequest req, HttpServletResponse resp, MensagemForm form);

	String validarCriarMensagem(MensagemForm form);

	List<MensagemForm> recuperaTodasMensagensPorDataMensagem(Long idUsuario, int quantMensagens);

	List<MensagemForm> recuperaTodasMensagensNovasPorDataMensagem(Long id);

	void atualizarTodasMensagensRecebidas(long idUsuarioDe, long idUsuarioPara);

}
