package com.busqueumlugar.util;

import java.io.Serializable;

public class EmailJms implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idUsuario;
	private Long idImovel;
	private String subject;
	private String to;
	private String texto;	
	private String tipoMensagem;
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdImovel() {
		return idImovel;
	}
	public void setIdImovel(Long idImovel) {
		this.idImovel = idImovel;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getTipoMensagem() {
		return tipoMensagem;
	}
	public void setTipoMensagem(String tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}
	

}
