package com.busqueumlugar.form;

import java.io.Serializable;
import java.util.Date;

public class SeguidorForm extends BaseForm implements Serializable{

	private static final long serialVersionUID = 9165851369986023184L;
	
	private Long id;	
    private String status;	
    private Date dataInicio;
        
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

}
