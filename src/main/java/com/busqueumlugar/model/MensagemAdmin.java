package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.TipoMensagemAdminEnum;

@Entity
@Table(name = "mensagemadmin")
public class MensagemAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
    @Column(name = "id")
 	private Long id;
	/*
	@Column(name = "idUsuario")
	private Long idUsuario;	*/
	
	@ManyToOne
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;
  
	@Column(name = "tipoMensagem")
    private String tipoMensagem; // {D - duvida, R - reclamacao, S- sugestao, O - outro}  

    @Column(name = "titulo")
    private String titulo;   
    
    @Column(name = "dataUltimaMensagem")
    private Date dataUltimaMensagem;
    
    @Column(name = "descricaoUltimaMensagem")
    private String descricaoUltimaMensagem;
    
    @Transient
    private String possuiNovaMensagem = "N"; // S, N    
	
	@Transient
	private String tipoMensagemFmt;
	
	@Transient
	private String existeItemMensagemNovo;
    
	public String getPossuiNovaMensagem() {
		return possuiNovaMensagem;
	}

	public void setPossuiNovaMensagem(String possuiNovaMensagem) {
		this.possuiNovaMensagem = possuiNovaMensagem;
	}

	public Date getDataUltimaMensagem() {
		return dataUltimaMensagem;
	}

	public void setDataUltimaMensagem(Date dataUltimaMensagem) {
		this.dataUltimaMensagem = dataUltimaMensagem;
	}

	public String getDescricaoUltimaMensagem() {
		return descricaoUltimaMensagem;
	}

	public void setDescricaoUltimaMensagem(String descricaoUltimaMensagem) {
		this.descricaoUltimaMensagem = descricaoUltimaMensagem;
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
		
	public String getTipoMensagemFmt() {
		return TipoMensagemAdminEnum.getLabel(tipoMensagem);
	}

	public void setTipoMensagemFmt(String tipoMensagemFmt) {
		this.tipoMensagemFmt = tipoMensagemFmt;
	}

	public String getExisteItemMensagemNovo() {
		return existeItemMensagemNovo;
	}

	public void setExisteItemMensagemNovo(String existeItemMensagemNovo) {
		this.existeItemMensagemNovo = existeItemMensagemNovo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}    
    
}
