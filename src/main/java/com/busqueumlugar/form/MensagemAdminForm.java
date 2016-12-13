package com.busqueumlugar.form;

import com.busqueumlugar.enumerador.TipoMensagemAdminEnum;
import com.busqueumlugar.model.Usuario;

public class MensagemAdminForm extends BaseForm{

	private Long id;       
	// {D - duvida, R - reclamacao, S- sugestao, O - outro} - {N -notificação, A - aviso}
    private String tipoMensagem;    
    private String titulo;
    private String descricaoUltimaMensagem;    
    private String possuiNovaMensagem; // S, N
    private Usuario usuario;
    private Long idUsuario;	
    private String novaMensagem = "";
	private String nomeUsuarioPara;
	private String opcaoOrdenacao = "";
	private String tipoMensagemFmt;

	public String getNomeUsuarioPara() {
		return nomeUsuarioPara;
	}
	public void setNomeUsuarioPara(String nomeUsuarioPara) {
		this.nomeUsuarioPara = nomeUsuarioPara;
	}
    
	public String getNovaMensagem() {
		return novaMensagem;
	}
	public void setNovaMensagem(String novaMensagem) {
		this.novaMensagem = novaMensagem;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getDescricaoUltimaMensagem() {
		return descricaoUltimaMensagem;
	}
	public void setDescricaoUltimaMensagem(String descricaoUltimaMensagem) {
		this.descricaoUltimaMensagem = descricaoUltimaMensagem;
	}
	public String getPossuiNovaMensagem() {
		return possuiNovaMensagem;
	}
	public void setPossuiNovaMensagem(String possuiNovaMensagem) {
		this.possuiNovaMensagem = possuiNovaMensagem;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoMensagem() {
		return tipoMensagem;
	}
	public void setTipoMensagem(String tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getOpcaoOrdenacao() {
		return opcaoOrdenacao;
	}
	public void setOpcaoOrdenacao(String opcaoOrdenacao) {
		this.opcaoOrdenacao = opcaoOrdenacao;
	}
	
	public String getTipoMensagemFmt() {
		return TipoMensagemAdminEnum.getLabel(tipoMensagem);
	}

	public void setTipoMensagemFmt(String tipoMensagemFmt) {
		this.tipoMensagemFmt = tipoMensagemFmt;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}    
}
