package com.busqueumlugar.form;

import javax.persistence.Column;

public class ImovelMapaForm extends BaseForm{
	
	private Long idImovel;
	private Double latitude;    
    private Double longitude;
    private String titulo;
    
    
	public Long getIdImovel() {
		return idImovel;
	}
	public void setIdImovel(Long idImovel) {
		this.idImovel = idImovel;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
    

}
