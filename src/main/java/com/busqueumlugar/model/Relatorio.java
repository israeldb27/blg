package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.RelatorioEnum;
import com.busqueumlugar.util.DateUtil;
import com.mysql.jdbc.StringUtils;


@Entity
@Table(name = "relatorio")
public class Relatorio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;    
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "descricao")
    private String descricao;    
    
    @Column(name = "dataCriacao")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;    
    
    @Column(name = "status")
    private String status;        
    
    @Column(name = "item")
    private String item;        
    
    @Column(name = "tipo")
    private String tipo;        
    
    @Column(name = "cobranca")
    private String cobranca;   
    
    @Column(name = "path")
    private String path;
	
	@Transient
	private String itemFmt; 
	
	@Transient
	private String dataCriacaoFmt; 
    

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Relatorio() {
    }

    public Relatorio(Long id) {
        this.id = id;
    }

    public Relatorio(Long id, String nome, Date dataCriacao, String status, String tipo) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.tipo = tipo;
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
	
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relatorio)) {
            return false;
        }
        Relatorio other = (Relatorio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.relatorio.Relatorio[ id=" + id + " ]";
    }

    /**
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * @return the cobranca
     */
    public String getCobranca() {
        return cobranca;
    }

    /**
     * @param cobranca the cobranca to set
     */
    public void setCobranca(String cobranca) {
        this.cobranca = cobranca;
    }
	
	 /**
     * @return the itemFmt
     */
    public String getItemFmt() {
    	if (!	StringUtils.isNullOrEmpty(this.item))
    		return  RelatorioEnum.getLabel(this.item);
    	else 
    		return null; 
    }

    /**
     * @param item the itemFmt to set
     */
    public void setItemFmt(String itemFmt) {
        this.itemFmt = itemFmt;
    }

	public String getDataCriacaoFmt() {
		if ( this.dataCriacao != null )
			return DateUtil.formataData(this.dataCriacao);
		else
			return dataCriacaoFmt;
	}

	public void setDataCriacaoFmt(String dataCriacaoFmt) {
		this.dataCriacaoFmt = dataCriacaoFmt;
	}

    
}
