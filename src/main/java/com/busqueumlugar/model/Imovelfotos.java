package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.map.deser.ValueInstantiators.Base;

import com.paypal.base.codec.binary.Base64;

@Entity
@Table(name = "imovelfotos")
public class Imovelfotos extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 8702630257544784742L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
 	private Long id; 	
        
    @Lob
    @Column(name = "img")
    private byte[] img;    
        
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;   
    
    //@Size(min = 1, max = 45)
    @Column(name = "descricao")
    private String descricao;    
    
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    
    @Transient
    private String nomeArquivo;	
	

    public Imovelfotos() {
    }

    public Imovelfotos(Long id) {
        this.id = id;
    }

    public Imovelfotos(Long id, byte[] img, long idImovel, String descricao) {
        this.id = id;
        this.img = img;        
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof Imovelfotos)) {
            return false;
        }
        Imovelfotos other = (Imovelfotos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imovelfotos.Imovelfotos[ id=" + id + " ]";
    }
	

    /**
     * @return the nomeArquivo
     */
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    /**
     * @param nomeArquivo the nomeArquivo to set
     */
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    /**
     * @return the dataCadastro
     */
    public Date getDataCadastro() {
        return dataCadastro;
    }

    /**
     * @param dataCadastro the dataCadastro to set
     */
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}
	
	public String getImagemArquivo(){
		
		if ( this != null && this.getImg() != null)
    		return Base64.encodeBase64String(this.getImg());
    	else
    		return "";
		
	}
}
