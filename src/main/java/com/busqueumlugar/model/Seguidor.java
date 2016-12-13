package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "seguidor")
public class Seguidor implements Serializable {

	private static final long serialVersionUID = 1624262185175888349L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;	
	
	@ManyToOne
    @JoinColumn(name="idUsuarioSeguido")    
    private Usuario usuarioSeguido;

	@ManyToOne
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;
	
	@Column(name = "status")
    private String status;
	
	@Column(name = "dataInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
	
	@Transient
	private String imageUsuarioSeguidor;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuarioSeguido() {
		return usuarioSeguido;
	}

	public void setUsuarioSeguido(Usuario usuarioSeguido) {
		this.usuarioSeguido = usuarioSeguido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public String getImageUsuarioSeguidor() {
		return imageUsuarioSeguidor;
	}

	public void setImageUsuarioSeguidor(String imageUsuarioSeguidor) {
		this.imageUsuarioSeguidor = imageUsuarioSeguidor;
	}
	
	

}
