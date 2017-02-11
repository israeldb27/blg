package com.busqueumlugar.model;

import javax.persistence.Transient;

public class BaseEntity {
	
	
	@Transient
	private String imagemArquivo = "";


	public String getImagemArquivo() {
		return imagemArquivo;
	}

	public void setImagemArquivo(String imagemArquivo) {
		this.imagemArquivo = imagemArquivo;
	}


}
