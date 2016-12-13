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

import com.busqueumlugar.enumerador.RecomendacaoStatusEnum;


@Entity
@Table(name = "recomendacao")
public class Recomendacao  implements Serializable{

	private static final long serialVersionUID = 3398351499279887549L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   
		 
	@Column(name = "descricao")
    private String descricao;
	
	@Column(name = "dataRecomendacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRecomendacao;
	
	@Column(name = "dataResposta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataResposta;
	
	@Column(name = "status")
    private String status;
	
	@Column(name = "statusLeitura")
    private String statusLeitura;
	
	@ManyToOne
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;

	@ManyToOne
    @JoinColumn(name="idUsuarioRecomendado")    
    private Usuario usuarioRecomendado;
	
	@Transient
	private String imagemUsuario;
	
	
	@Transient
	private boolean isStatusAceito;
	
	@Transient
	private boolean isStatusEnviado;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataRecomendacao() {
		return dataRecomendacao;
	}

	public void setDataRecomendacao(Date dataRecomendacao) {
		this.dataRecomendacao = dataRecomendacao;
	}

	public Date getDataResposta() {
		return dataResposta;
	}

	public void setDataResposta(Date dataResposta) {
		this.dataResposta = dataResposta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusLeitura() {
		return statusLeitura;
	}

	public void setStatusLeitura(String statusLeitura) {
		this.statusLeitura = statusLeitura;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioRecomendado() {
		return usuarioRecomendado;
	}

	public void setUsuarioRecomendado(Usuario usuarioRecomendado) {
		this.usuarioRecomendado = usuarioRecomendado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImagemUsuario() {
		return imagemUsuario;
	}

	public void setImagemUsuario(String imagemUsuario) {
		this.imagemUsuario = imagemUsuario;
	}

	public boolean isStatusAceito() {
		return RecomendacaoStatusEnum.ACEITO.getRotulo().equals(status);
	}

	public void setStatusAceito(boolean isStatusAceito) {
		this.isStatusAceito = isStatusAceito;
	}

	public boolean isStatusEnviado() {
		return RecomendacaoStatusEnum.ENVIADO.getRotulo().equals(status);
	}

	public void setStatusEnviado(boolean isStatusEnviado) {
		this.isStatusEnviado = isStatusEnviado;
	}
		
	 
}
