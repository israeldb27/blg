package com.busqueumlugar.model;

import javax.persistence.Transient;

public class BaseEntity {
	
	@Transient
	private String imagemImovel = "";
	
	@Transient
	private String imagemUsuario = "";

	public String getImagemImovel() {
		return imagemImovel;
	}

	public void setImagemImovel(String imagemImovel) {
		this.imagemImovel = imagemImovel;
	}

	public String getImagemUsuario() {
		return imagemUsuario;
	}

	public void setImagemUsuario(String imagemUsuario) {
		this.imagemUsuario = imagemUsuario;
	}



}
