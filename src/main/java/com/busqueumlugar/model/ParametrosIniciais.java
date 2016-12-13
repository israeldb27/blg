package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "parametrosiniciais")
public class ParametrosIniciais implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
	@Column(name = "id")
	private Long id;

	@Column(name = "nome")
    private String nome;    

	@Column(name = "label")
    private String label;
	
	@Column(name = "tipoParam")
    private String tipoParam; // {N, C}    N: numerico, C: caractere
	
	@Column(name = "idUsuarioCadastro")
    private Long idUsuarioCadastro;  
	
	@Column(name = "valorHabilita")
    private String valorHabilita; // {S, N}   Sim, Nao
	
	@Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro; 
	
	@Column(name = "dataUltimaAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimaAtualizacao; 
	
	@Column(name = "valorQuantidade")
    private Long valorQuantidade;
	
	@Column(name = "observacao")
    private String observacao;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTipoParam() {
		return tipoParam;
	}

	public void setTipoParam(String tipoParam) {
		this.tipoParam = tipoParam;
	}

	public Long getIdUsuarioCadastro() {
		return idUsuarioCadastro;
	}

	public void setIdUsuarioCadastro(Long idUsuarioCadastro) {
		this.idUsuarioCadastro = idUsuarioCadastro;
	}

	public String getValorHabilita() {
		return valorHabilita;
	}

	public void setValorHabilita(String valorHabilita) {
		this.valorHabilita = valorHabilita;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public Long getValorQuantidade() {
		return valorQuantidade;
	}

	public void setValorQuantidade(Long valorQuantidade) {
		this.valorQuantidade = valorQuantidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}  
	
	
	
	
	
	
	

}
